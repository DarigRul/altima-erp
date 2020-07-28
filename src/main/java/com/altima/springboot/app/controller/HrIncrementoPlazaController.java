package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.internal.util.privilegedactions.GetInstancesFromServiceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrIncrementoPlaza;
import com.altima.springboot.app.models.entity.HrLookup;
import com.altima.springboot.app.models.entity.HrPuesto;
import com.altima.springboot.app.models.service.IHrIncrementoPlazaService;

@CrossOrigin(origins = { "*" })
@Controller
public class HrIncrementoPlazaController {

	
	@Autowired
	private IHrIncrementoPlazaService incrementoPlazaService;
	
	//Método para listar datos de la bd en la pantalla y para mostrar combo box
	@GetMapping("/rh-incrementos")
	public String listIncrementosPlazas(Model model) {
		//Listar Empresas
		
		List<HrLookup> empresas = incrementoPlazaService.findAllEmpresas();
		model.addAttribute("listEmpresas", incrementoPlazaService.findAllEmpresas());
	    System.out.println("Lista de empresas " + empresas);
	    
		//Listar Departamentos
	    
	    List<HrDepartamento> deparmentos = incrementoPlazaService.findAllDepartamentos();
		model.addAttribute("listarDepas", incrementoPlazaService.findAllDepartamentos());
		
		//Listar puestos
		
		List<HrPuesto> puestos = incrementoPlazaService.findAllPuestos();
		model.addAttribute("lisPuestos", incrementoPlazaService.findAllPuestos());
		System.out.println("Listar puestos" + puestos);
		
		//Listar plazas agregadas
		HrIncrementoPlaza plaza = new HrIncrementoPlaza ();
		List<Object[]> listarPlazas = incrementoPlazaService.incrementosPlazas();
		model.addAttribute("plaza", plaza);
		model.addAttribute("listarIncrementos", listarPlazas);
		System.out.println("listar plazas insertadas" + listarPlazas);
		return "rh-incrementos";
	}
	
	//Método para mostrar detalles de la plaza
	@GetMapping("rh-incrementos-detalle/{id}")
	public String infoMaterials(@PathVariable("id") Long id, Model model) {
		
			model.addAttribute("incrementos", incrementoPlazaService.findByIdIncrementoPlaza(id));	
			return "rh-incrementos-detalle";
	}
	
	//Método para guardar
	@PostMapping("/rh-incrementos")
	public String agregarIncrementosPlazas(HrIncrementoPlaza plaza)  {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		plaza.setCreadoPor(auth.getName());
		plaza.setEstatus("1");
		plaza.setActualizadoPor(auth.getName());
		plaza.setMotivoRechazo("Aprobación en proceso");
		
		if (plaza.getSueldo().equals("") || plaza.getNumeroPlaza().equals("")) {
			
			System.out.println("Campos vacíos");
		}
		else {
			incrementoPlazaService.save(plaza);
			
		}
		return "redirect:/rh-incrementos";
	}
	
	//Método para aprobar plazas
	@GetMapping("/rh-incrementos-aprobar/{id}")
	public String Approved(@PathVariable(name = "id") Long id, Model model) {
		try {
			Date date1 = new Date();
			Authentication authAprobados = SecurityContextHolder.getContext().getAuthentication();
			DateFormat ultimaActualizacionAprobados = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			HrIncrementoPlaza incrementosAprobados = new HrIncrementoPlaza();
			incrementosAprobados = incrementoPlazaService.findOne(id);
			incrementosAprobados.setEstatusPlaza("2");
			incrementosAprobados.setEstatus("2");
			incrementosAprobados.setFechaAutorizacion(ultimaActualizacionAprobados.format(date1));
			incrementosAprobados.setUltimaFechaModificacion(ultimaActualizacionAprobados.format(date1));
			incrementosAprobados.setMotivoRechazo("Aprobación aceptada");
			incrementosAprobados.setActualizadoPor(authAprobados.getName());
			incrementoPlazaService.save(incrementosAprobados);
			return "redirect:/rh-incrementos";
		}
		catch (Exception e) {
			System.out.println("Error:   "+e);
			return "redirect:/rh-incrementos";
		}
		finally {
			System.out.println("Terminó el proceso aprobado para incrementos");
		}
		
	}
	
	//Método para rechazar plazas
	@GetMapping("/rh-incrementos-rechazar/{id}")
	public String Rejected(@PathVariable(name = "id") Long id, Model model) {
		try {
			Date date2 = new Date();
			Authentication authRechazados = SecurityContextHolder.getContext().getAuthentication();
			DateFormat ultimaActualizacionRechazados = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			HrIncrementoPlaza incrementosRechazados = new HrIncrementoPlaza();
			incrementosRechazados = incrementoPlazaService.findOne(id);
			incrementosRechazados.setEstatusPlaza("3");
			incrementosRechazados.setEstatus("3");
			incrementosRechazados.setFechaAutorizacion(ultimaActualizacionRechazados.format(date2));
			incrementosRechazados.setUltimaFechaModificacion(ultimaActualizacionRechazados.format(date2));
			incrementosRechazados.setActualizadoPor(authRechazados.getName());
			incrementoPlazaService.save(incrementosRechazados);
			return "redirect:/rh-incrementos";
		}
		catch (Exception e) {
			System.out.println("Error:   "+e);
			return "redirect:/rh-incrementos";
		}
		finally {
			System.out.println("Terminó el proceso rechazo de incrementos");
		}
	}
	
	@GetMapping("/motivos-rechazos")
	public String motivosRechazos(@RequestParam(name = "descripcion2") String descripcion,
			@RequestParam(name = "motivosRechazos") Long id, Model model) {
		try {
			
			Date date2 = new Date();
			Authentication authMotivos = SecurityContextHolder.getContext().getAuthentication();
			DateFormat ultimaActualizacionRechazados = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			HrIncrementoPlaza motivos = incrementoPlazaService.findOne(id);
			motivos.setMotivoRechazo(descripcion);
			motivos.setEstatus("3");
			motivos.setEstatusPlaza("3");
			motivos.setFechaAutorizacion(ultimaActualizacionRechazados.format(date2));
			motivos.setUltimaFechaModificacion(ultimaActualizacionRechazados.format(date2));
			motivos.setActualizadoPor(authMotivos.getName());
			incrementoPlazaService.save(motivos);
			System.out.println(id+descripcion);
			return "redirect:/rh-incrementos";
		}
		catch (Exception e) {
			System.out.println("Error:   "+e);
			return "redirect:/rh-incrementos";
		}
		finally {
			System.out.println("Terminó el proceso de motivos");
		}
	}
	
	
	
	

}
