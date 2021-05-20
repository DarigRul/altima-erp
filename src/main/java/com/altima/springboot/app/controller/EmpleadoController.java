package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.HrLookup;
import com.altima.springboot.app.models.entity.HrPuesto;
import com.altima.springboot.app.models.service.IHrDepartamentoService;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
import com.altima.springboot.app.models.service.IHrIncrementoPlazaService;
import com.altima.springboot.app.models.service.IHrLookupService;
import com.altima.springboot.app.models.service.IHrPuestoService;
import com.altima.springboot.app.models.service.UploadServiceImpl;

@Controller

public class EmpleadoController {
	@Autowired
	private IHrLookupService lookupService;
	@Autowired
	private IHrPuestoService puestoService;
	@Autowired
	private IHrDepartamentoService departamentoService;
	@Autowired
	private IHrEmpleadoService empleadoService;
	@Autowired
	private IHrIncrementoPlazaService incrementoPlazaService;
	@Autowired
	private UploadServiceImpl uService;

	@GetMapping(value = "/uploads/empleados/{filename:.+}")
	public ResponseEntity<Resource> verFotoEmpleado(@PathVariable String filename) {
		Resource recurso = null;
		try {
			recurso = uService.loadEmpleado(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_EMPLEADOS_LISTAR" })
	@GetMapping("rh-empleados")
	public String rhempleados(Model model) {
		model.addAttribute("empleados", empleadoService.findEmpleadoPersona());
		return "rh-empleados";
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_EMPLEADOS_AGREGAR" })
	@GetMapping("rh-agregar-empleados")
	public String agregarEmpleados(Model model) {
		HrEmpleado empleado = new HrEmpleado();
		model.addAttribute("listEmpresas", incrementoPlazaService.findAllEmpresas());
		model.addAttribute("empleado", empleado);
		model.addAttribute("Puestos", puestoService.findAll());
		model.addAttribute("empresas", lookupService.findAllByTipoLookup("Empresa"));
		return "rh-agregar-empleados";
	}

	@PostMapping("guardar-empleado")
	public String guardarEmpleado(HrEmpleado empleado, Model model, @RequestParam("foto") MultipartFile foto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		if (!foto.isEmpty()) {
			if (empleado.getIdEmpleado() != null && empleado.getIdEmpleado() > 0 && empleado.getFotografia() != null
					&& empleado.getFotografia().length() > 0) {
				uService.deleteEmpleado(empleado.getFotografia());
			}
		}
		String uniqueFname = null;
		try {
			uniqueFname = uService.copyEmpleado(foto);
			empleado.setFotografia(uniqueFname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		empleado.setCreadoPor(auth.getName());
		empleado.setEstatus("1");
		empleadoService.save(empleado);
		return ("redirect:/rh-empleados");
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_EMPLEADOS_EDITAR" })
	@GetMapping("rh-editar-empleado/{id}")
	public String rhEditarEmpleados(Model model, @PathVariable(value = "id") Long id) {
		HrEmpleado empleado = empleadoService.findOne(id);
		HrPuesto puesto = puestoService.findOne(empleado.getIdPuesto());
		HrDepartamento departamento = departamentoService.findOne(puesto.getIdDepartamento());
		HrLookup area = lookupService.findOne(departamento.getIdArea());
		model.addAttribute("listEmpresas", incrementoPlazaService.findAllEmpresas());
		model.addAttribute("empleado", empleado);
		model.addAttribute("area", area.getNombreLookup());
		model.addAttribute("editar", true);
		model.addAttribute("departamento", departamento.getNombreDepartamento());
		model.addAttribute("puesto", area.getNombreLookup());
		model.addAttribute("Puestos", puestoService.findAll());
		model.addAttribute("empresas", lookupService.findAllByTipoLookup("Empresa"));
		return "rh-agregar-empleados";
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_EMPLEADOS_ELIMINAR" })
	@GetMapping("rh-empleado/alta/{id}")
	public String rhAltaEmpleados(Model model, @PathVariable(value = "id") Long id) {
		HrEmpleado empleado = empleadoService.findOne(id);
		empleado.setMotivoBaja(null);
		empleado.setFechaBaja(null);
		empleado.setEstatus("1");
		empleadoService.save(empleado);
		return "redirect:/rh-empleados";
	}
}