package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.HrPermiso;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
import com.altima.springboot.app.models.service.IHrIncrementoPlazaService;
import com.altima.springboot.app.models.service.IHrPermisoService;

@Controller
public class HrPermisoController {

	@Autowired
	private IHrPermisoService servicePermiso;
	@Autowired
	private IHrEmpleadoService empleadoService;
	@Autowired
	private IHrIncrementoPlazaService incrementoPlazaService;

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_PERMISOS_LISTAR"})
	@GetMapping("/rh-permisos")
	public String permisos(Model model) {
		// System.out.println("Si estoy entrando al metodo de lista");
		// se crea un objeto para listar informacion en pantalla principal
		List<HrPermiso> rhPermiso = servicePermiso.findListaPermiso();
		model.addAttribute("listarPermisos", rhPermiso);
		return "/rh-permisos";
	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_PERMISOS_AGREGAR"})
	@GetMapping("/rh-agregar-permisos")
	public String agregarPermisos(Map<String, Object> model, Model m) {
		// System.out.println("Si entre jijiji agregar nuevo");
		// con el findAll puedo acceder a sus metodos sin necesidad de hacer consultas
		// accedo a la inf
		HrPermiso addPermiso = new HrPermiso();
		model.put("per", addPermiso);
		model.put("empleado", empleadoService.findAll());
		model.put("action", "Guardar");
		model.put("listEmpresas", incrementoPlazaService.findAllEmpresas());
		return "/rh-agregar-permisos";
	}

	@RequestMapping(value = "/agregar_permiso", method = RequestMethod.POST)
	// tengo la informacion y es la que mando en el html para que se muestre en la
	// vista
	public String guardarPermisos(Model model, @RequestParam("idEmpleado") Long empleado,
			@RequestParam("tipoPermiso") String tipoPermiso, @RequestParam("evento") String evento,
			@RequestParam("condiciones") String condiciones, @RequestParam("fechaInicial") String fechaInicial,
			@RequestParam("fechaFinal") String fechaFinal, @RequestParam("horaInicial") String horaInicial,
			@RequestParam("horaFinal") String horaFinal, @RequestParam("observaciones") String observaciones,
			RedirectAttributes redirectAttrs) {
		List<HrPermiso> rhPermiso = servicePermiso.findListaPermiso();
		model.addAttribute("listarPermisos", rhPermiso);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		// traigo la informacion
		HrPermiso guardarPermiso = new HrPermiso();
		guardarPermiso.setCondiciones(condiciones);
		guardarPermiso.setEvento(evento);
		guardarPermiso.setFechaFinal(fechaFinal);
		guardarPermiso.setFechaInicial(fechaInicial);
		guardarPermiso.setHoraFinal(horaFinal);
		guardarPermiso.setHoraInicial(horaInicial);
		guardarPermiso.setObservaciones(observaciones);
		guardarPermiso.setTipoPermiso(tipoPermiso);
		guardarPermiso.setCreadoPor(auth.getName());
		guardarPermiso.setActualizadoPor(auth.getName());
		guardarPermiso.setFechaCreacion(dtf.format(now));
		guardarPermiso.setUltimaFechaModificacion(dtf.format(now));
		guardarPermiso.setFechaAplicacion(dtf.format(now));
		guardarPermiso.setIdEmpleado(empleado);
		guardarPermiso.setEstatusPermiso("1");
		guardarPermiso.setUtilizado("No");
		// Al setear el estatus desde aqui ya no tengo que ponerlo en duro desde la BD
		guardarPermiso.setEstatus("1");
		// IDTEXT ponerlo vacio
		guardarPermiso.setIdText("");
		servicePermiso.save(guardarPermiso);
		guardarPermiso.setIdText("PERM" + (1000 + guardarPermiso.getIdPermiso()));
		servicePermiso.save(guardarPermiso);
		// linea para alert a la hora de agrega un nuevo registro
		// redirectAttrs.addFlashAttribute("title", "Permiso Insertado
		// Correctamente").addFlashAttribute("icon","primary");
		return "/rh-permisos";
	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_PERMISOS_EDITAR"})
	@RequestMapping(value = "/rh-editar-permisos/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m) {
		// System.out.println("Si estoy entrando al metodo findOne editar");
		HrPermiso addPermiso = servicePermiso.findOne(id);
		Long puesto = servicePermiso.puesto(addPermiso.getIdEmpleado());
		Long departamento = servicePermiso.departamento(puesto);
		Long area = servicePermiso.area(departamento);
		System.out.println("sssssssssssssss" + puesto);
		m.put("listEmpresas", incrementoPlazaService.findAllEmpresas());
		model.addAttribute("pue", puesto);
		model.addAttribute("dep", departamento);
		model.addAttribute("area", area);
		model.addAttribute("addPermiso2", id);
		model.addAttribute("per", addPermiso);
		model.addAttribute("empleado", empleadoService.findAll());
		model.addAttribute("action", "editar");
		return "/rh-agregar-permisos";
	}

	@RequestMapping(value = "/editar_permiso_guardado", method = RequestMethod.POST)
	// tengo la informacion y es la que mando en el html para que se muestre en la
	// vista
	public String editarPermisos(Model model, @RequestParam("idEmpleado") Long empleado,
			@RequestParam("tipoPermiso") String tipoPermiso, @RequestParam("evento") String evento,
			@RequestParam("condiciones") String condiciones, @RequestParam("fechaInicial") String fechaInicial,
			@RequestParam("fechaFinal") String fechaFinal, @RequestParam("horaInicial") String horaInicial,
			@RequestParam("horaFinal") String horaFinal, @RequestParam("id_permiso") String id,
			@RequestParam("observaciones") String observaciones) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		// traigo la informacion
		// System.out.println("Si entre" + id);
		HrPermiso guardarPermiso = servicePermiso.findOne(Long.parseLong(id));
		guardarPermiso.setCondiciones(condiciones);
		guardarPermiso.setEvento(evento);
		guardarPermiso.setFechaFinal(fechaFinal);
		guardarPermiso.setFechaInicial(fechaInicial);
		guardarPermiso.setHoraFinal(horaFinal);
		guardarPermiso.setHoraInicial(horaInicial);
		guardarPermiso.setObservaciones(observaciones);
		guardarPermiso.setTipoPermiso(tipoPermiso);
		guardarPermiso.setCreadoPor(auth.getName());
		guardarPermiso.setActualizadoPor(auth.getName());
		guardarPermiso.setFechaCreacion(dtf.format(now));
		guardarPermiso.setUltimaFechaModificacion(dtf.format(now));
		guardarPermiso.setFechaAplicacion(dtf.format(now));
		guardarPermiso.setIdEmpleado(empleado);
		guardarPermiso.setEstatusPermiso("1");
		guardarPermiso.setUtilizado("No");
		servicePermiso.save(guardarPermiso);
		// la informacion ya editada al volver a guardar se lista la nueva inf
		List<HrPermiso> rhPermiso = servicePermiso.findListaPermiso();
		model.addAttribute("listarPermisos", rhPermiso);
		return "/rh-permisos";
	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_PERMISOS_ELIMINAR"})
	@RequestMapping(value = "/rh-darBaja-permisos/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m,
			RedirectAttributes redirectAttrs) {
		// System.out.println("Si estoy entrando al metodo eliminar");
		HrPermiso addPermiso = servicePermiso.findOne(id);
		addPermiso.setEstatus("0");
		servicePermiso.save(addPermiso);
		redirectAttrs.addFlashAttribute("title", "Permiso Eliminado").addFlashAttribute("icon", "warning");
		return "redirect:/rh-permisos";
	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_PERMISOS_ACTIVAR"})
	@RequestMapping(value = "/activar-permiso/{id}") // , method = RequestMethod.POST
	public String activarPermiso(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m,
			RedirectAttributes redirectAttrs) {
		// System.out.println("Si estoy entrando al metodo findOne aceptado");
		HrPermiso addPermiso = servicePermiso.findOne(id);
		addPermiso.setEstatusPermiso("1");
		List<HrPermiso> rhPermiso = servicePermiso.findListaPermiso();
		model.addAttribute("listarPermisos", rhPermiso);
		servicePermiso.save(addPermiso);
		redirectAttrs.addFlashAttribute("title", "Permiso Activado").addFlashAttribute("icon", "success");
		return "redirect:/rh-permisos";
	}

	// este metodo me ayuda cuando quiero pasar informacion por medio de
	// post,update, etc, cuando necesita proporcionar una ruta de nivel superior
	// para un controlador
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_RECURSOSHUMANOS_PERMISOS_ACTIVAR"})
	@RequestMapping(value = "/rechazar-permiso/{id}") // , method = RequestMethod.POST
	public String rechazarPermiso(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m,
			RedirectAttributes redirectAttrs) {
		// System.out.println("Si estoy entrando al metodo findOne rechazar");
		// encuentro los datos en este caso el id
		HrPermiso addPermiso = servicePermiso.findOne(id);
		// el estatus por el cual debo actualizar mi campo en este caso activar o
		// rechazar
		addPermiso.setEstatusPermiso("0");
		// cuando le doy al boton me debe regresar a la vista a donde listo usando los
		// mismo dato de mi metodo listar
		List<HrPermiso> rhPermiso = servicePermiso.findListaPermiso();
		model.addAttribute("listarPermisos", rhPermiso);
		// guardar una ves encontrado el valor
		servicePermiso.save(addPermiso);
		// me regresa a la vista listar
		redirectAttrs.addFlashAttribute("title", "Permiso Rechazado").addFlashAttribute("icon", "warning");
		return "redirect:/rh-permisos";
	}
}
