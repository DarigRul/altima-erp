package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.AmpInventario;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialTicket;
import com.altima.springboot.app.models.entity.ComercialTicketEstatus;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.service.IComercialAuxiliarTicketsService;

@Controller
public class AuxiliarTicketsController {
	
	@Autowired
	IComercialAuxiliarTicketsService TicketService;
	
    @GetMapping("/tickets")
    public String Tickets(Model model , Map<String, Object> m){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	Integer id_empleado =TicketService.idUsuario(auth.getName());
    	
    	if ( auth.getName().equals("ADMIN") ) {
    		model.addAttribute("solicitante", TicketService.VentasDep("not in"));
    		model.addAttribute("auxiliar", TicketService.VentasDep("in"));
    		model.addAttribute("auxiliarSelected", 2);
    		model.addAttribute("solicitanteSelected", 2);
    	}else {
    		String puesto = TicketService.user(auth.getName());
    		if (puesto.equals("AYUDANTE DE VENTAS")) {
    			model.addAttribute("solicitante", TicketService.VentasDep("not in"));
        		model.addAttribute("auxiliar", TicketService.VentasDep("in"));
        		model.addAttribute("auxiliarSelected", 1);
        		
        		model.addAttribute("auxiliarEmpleado", id_empleado);
        	}
        	else if (TicketService.Verificar_Solicitante(puesto)) {
        		
        		model.addAttribute("solicitanteEmpleado", id_empleado);
        		model.addAttribute("solicitanteSelected", 1);
        		model.addAttribute("solicitante", TicketService.VentasDep("not in"));
        		model.addAttribute("auxiliar", TicketService.VentasDep("in"));
        		
        	}
    	}
    	
    	
    	model.addAttribute("categoria", TicketService.Categoria());
    	
    	ComercialTicket ticket = new ComercialTicket ();
    
    	m.put("ticket", ticket);
    	
    	model.addAttribute("view", TicketService.view());
    	
    	
    
    	
        return "tickets";
    }
    @RequestMapping(value = "/lista-seguimientos", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> modelo(Long id) {
		return  TicketService.viewEstatus(id);
	}
    @PostMapping("/guardar-ticket")
	public String guardarCliente(ComercialTicket ticket, RedirectAttributes redirectAttrs) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Integer id_empleado =TicketService.idUsuario(auth.getName());
		String puesto = TicketService.user(auth.getName());
		
		if ( ticket.getIdEmpleadoSolicitante() == null) {
			ticket.setIdEmpleadoSolicitante(id_empleado.toString());
		}
		if (ticket.getIdEmpleadoAuxiliar()== null) {
			if (puesto.equals("AYUDANTE DE VENTAS")) {
				ticket.setIdEmpleadoAuxiliar(id_empleado.toString());
			}
			
			else {
				String idAuxiliar = String.valueOf(TicketService.AleatorioAuxiliar(ticket.getFechaFin(),ticket.getFechaInicio() ));
			
				if ( idAuxiliar.equals("0")) {
				redirectAttrs.addFlashAttribute("title", "Lo sentimos no hay auxiliares disponibles en ese horario").addFlashAttribute("icon",
						"success");
				return "redirect:tickets";
				}
				else {
					ticket.setIdEmpleadoAuxiliar( idAuxiliar);
				}
			}
		}
			
			
    	if (ticket.getIdTicket() == null) {
    		
    		ticket.setIdText("ticket");
    		ticket.setCreadoPor(auth.getName());
    		ticket.setFechaCreacion(hourdateFormat.format(date));
    		ticket.setEstatus("1");
    		if ( ticket.getFechaCalendario() != null ) {
    			System.out.println(ticket.getFechaFin());
    			System.out.println(ticket.getFechaInicio());
    			
    		}
    		else {
    			ticket.setFechaFin(null);
    			ticket.setFechaInicio(null);
    		}
    		TicketService.save(ticket);
    		ticket.setIdText("TICKET"+(1000+ticket.getIdTicket()));
    		TicketService.save(ticket);
    		redirectAttrs.addFlashAttribute("title", "Ticket guardado correctamente").addFlashAttribute("icon",
					"success");
    	}
    	else {
    		ComercialTicket obj =TicketService.findOne(ticket.getIdTicket());
    		obj.setIdEmpleadoSolicitante(ticket.getIdEmpleadoSolicitante());
    		obj.setIdEmpleadoAuxiliar(ticket.getIdEmpleadoAuxiliar());
    		obj.setIdLookup(ticket.getIdLookup() );
    		
    		obj.setDescripcion(ticket.getDescripcion());
    		obj.setFechaCalendario(ticket.getFechaCalendario());
    		
    		obj.setFechaInicio(ticket.getFechaInicio());
    		obj.setFechaFin(ticket.getFechaFin());
    		if ( obj.getFechaCalendario() != null ) {
    			System.out.println(obj.getFechaFin());
    			System.out.println(obj.getFechaInicio());
    			
    		}
    		else {
    			obj.setFechaFin(null);
    			obj.setFechaInicio(null);
    		}
    		obj.setActualizadoPor(auth.getName());
    		obj.setUltimaFechaModificacion(hourdateFormat.format(date));
    		
    		TicketService.save(obj);	
    		redirectAttrs.addFlashAttribute("title", "Ticket editado correctamente").addFlashAttribute("icon",
					"success");
    	}
    	return "redirect:tickets";
    	
    }
    
    @PostMapping("/guardar-seguimiento-ticket")
	public String guardarCliente(Long idTicket,String estatus, String comentario ) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	ComercialTicketEstatus objestatus = new ComercialTicketEstatus ();
    	objestatus.setEstatusNombre(estatus);
    	objestatus.setComentario(comentario);
    	objestatus.setIdTicket(idTicket);
    	objestatus.setIdEmpleado(TicketService.idUsuario(auth.getName()));
    	objestatus.setEstatus("1");
    	objestatus.setCreadoPor(auth.getName());
    	objestatus.setFechaCreacion(hourdateFormat.format(date));
    	TicketService.saveSeguimiento(objestatus);	
    	return "redirect:tickets";
    }
    
    @RequestMapping(value = "/baja-seguimientos", method = RequestMethod.GET)
	@ResponseBody
	public boolean baja(Long id) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	ComercialTicketEstatus objestatus =TicketService.findOneEstatus(id);
    	objestatus.setEstatus("0");
    	objestatus.setActualizadoPor(auth.getName());
    	objestatus.setUltimaFechaModificacion(hourdateFormat.format(date));
    	TicketService.saveSeguimiento(objestatus);
		return  true;
	}
    
    @RequestMapping(value = "/buscar-ticket", method = RequestMethod.GET)
	@ResponseBody
	public ComercialTicket buscar(Long id) {
    	
		return  TicketService.findOne(id);
	}
    
    @RequestMapping(value = "/validar-ticket-estatus", method = RequestMethod.GET)
	@ResponseBody
	public String estatus(Long id) {
    	
		return  TicketService.Verificar_Estatus(id);
	}
    
    @RequestMapping(value = "/detalles-ticket", method = RequestMethod.GET)
	@ResponseBody
	public  List<Object[]> detalles(Long id) {
    	
		return  TicketService.detalles_estatus(id);
	}
}