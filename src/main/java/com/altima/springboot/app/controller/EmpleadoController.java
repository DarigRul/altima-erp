package com.altima.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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


@Controller

public class EmpleadoController {
	//emple
	
	@Autowired private IHrLookupService lookupService;
	@Autowired private IHrPuestoService puestoService;
	@Autowired private IHrDepartamentoService departamentoService;
	@Autowired private IHrEmpleadoService empleadoService;
	@Autowired private IHrIncrementoPlazaService incrementoPlazaService;
	
	@GetMapping("rh-agregar-empleados")
	public String agregarEmpleados(Model model) {
		HrEmpleado empleado =new HrEmpleado();
		model.addAttribute("listEmpresas", incrementoPlazaService.findAllEmpresas());
		model.addAttribute("empleado",empleado);
		model.addAttribute("Puestos", puestoService.findAll());
		model.addAttribute("empresas", lookupService.findAllByTipoLookup("Empresa"));
		return "rh-agregar-empleados";
	}
	
	
	@GetMapping("rh-empleados")
	public String rhempleados(Model model) {
		model.addAttribute("empleados", empleadoService.findEmpleadoPersona());
		return "rh-empleados";
	}

	@PostMapping("guardar-empleado") 
	public String guardarEmpleado(HrEmpleado empleado,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		empleado.setIdText("");
		empleado.setCreadoPor(auth.getName());
		empleado.setEstatus("1");
		empleadoService.save(empleado);
		empleado.setIdText("EMP"+(1000+empleado.getIdEmpleado()));
		empleadoService.save(empleado);
		return ("redirect:/rh-empleados");
	}

	@GetMapping("rh-editar-empleado/{id}")
	public String rhEditarEmpleados(Model model,@PathVariable(value="id") Long id) {
		HrEmpleado empleado =empleadoService.findOne(id);
		HrPuesto puesto =puestoService.findOne(empleado.getIdPuesto());
		HrDepartamento departamento =departamentoService.findOne(puesto.getIdDepartamento());
		HrLookup area=lookupService.findOne(departamento.getIdArea());
		model.addAttribute("listEmpresas", incrementoPlazaService.findAllEmpresas());
		model.addAttribute("empleado",empleado);
		model.addAttribute("area", area.getNombreLookup());
		model.addAttribute("editar", true);
		model.addAttribute("departamento", departamento.getNombreDepartamento());
		model.addAttribute("puesto", area.getNombreLookup());
		model.addAttribute("Puestos", puestoService.findAll());
		model.addAttribute("empresas", lookupService.findAllByTipoLookup("Empresa"));
		return "rh-agregar-empleados";
	}

	@GetMapping("rh-empleado/{accion}/{id}")
	public String rhEditarEmpleados(Model model,@PathVariable(value="id") Long id,@PathVariable(value="accion") String accion) {
		HrEmpleado empleado =empleadoService.findOne(id);
		if (accion.equals("alta")) {
			empleado.setEstatus("1");
		}
		else if(accion.equals("baja")){
			empleado.setEstatus("0");
		}
		else{
			return "redirect:/rh-empleados";
		}
		empleadoService.save(empleado);
		return "redirect:/rh-empleados";
	}
	
}

