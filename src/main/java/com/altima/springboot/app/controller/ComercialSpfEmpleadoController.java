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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialSpfEmpleado;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialSpfEmpleadoService;

@CrossOrigin(origins = { "*" })
@Controller
public class ComercialSpfEmpleadoController {
	
	@Autowired
	private IComercialSpfEmpleadoService SPFService;
	
	@Autowired
	private IComercialCoordinadoService CoordinadoService;

	@GetMapping("/empleados-spf/{id}")
	public String ListaEmpleadosSPF(@PathVariable(value = "id") Long id,Model model){
		
		
		model.addAttribute("list", SPFService.findAll(id));
		model.addAttribute("idPedidoSpf", id);
    	return"empleados-spf";
	}
	
	 @PostMapping("/guardar-spf-individual")
		public String guardarCliente(Long idPedidoSpf,Long idEmpleado, String nombreEmpleado ) {
	    	
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			ComercialSpfEmpleado obj = new ComercialSpfEmpleado();
			
			obj.setIdEmpleado(idEmpleado);
			obj.setIdPedidoSpf(idPedidoSpf);
			obj.setNombre_empleado(nombreEmpleado);
			obj.setCreadoPor(auth.getName());
			obj.setFechaCreacion(hourdateFormat.format(date));
			obj.setEstatus("1");
			SPFService.save(obj);	
	    	return "redirect:tickets";
	    }
	 
	 @RequestMapping(value = "/clientes-spf-disponibles", method = RequestMethod.GET)
		@ResponseBody
		public  List<Object[]> detalles(Long id) {
	    	
			return SPFService.empleados(id);
		}
		
		@RequestMapping(value = "/clientes-spf-buscar", method = RequestMethod.GET)
		@ResponseBody
		public  ComercialSpfEmpleado buscarSPf(Long id) {
	    	
			return SPFService.findOne(id);
		}
		
		@RequestMapping(value = "/clientes-spf-editar", method = RequestMethod.POST)
		@ResponseBody
		public boolean editar(Long idSpfEmpleado , String nombre ) {
	    	
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			ComercialSpfEmpleado obj = SPFService.findOne(idSpfEmpleado);
			obj.setNombre_empleado(nombre);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			SPFService.save(obj);	
			return true;
		}
		@RequestMapping(value = "/eliminar-spf/{id}", method = RequestMethod.GET)
		@ResponseBody
		public boolean eliminar( Long id) {
	    	
			SPFService.delete(id);
			return true;
		}

		@RequestMapping(value = "/agregar-spf-masivo", method = RequestMethod.POST)
		@ResponseBody
		public String masivo(Long idPedidoSpf) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			List<Object[]> auxlist =SPFService.empleados(idPedidoSpf);
			
			if ( auxlist.isEmpty() ) {
				return "Lista vacia";
			}else {
				for (Object[] a : auxlist) {
					ComercialSpfEmpleado obj = new ComercialSpfEmpleado();
					
					obj.setIdEmpleado(Long.parseLong(a[0].toString()));
					obj.setIdPedidoSpf(idPedidoSpf);
					obj.setNombre_empleado(a[1].toString());
					obj.setCreadoPor(auth.getName());
					obj.setFechaCreacion(hourdateFormat.format(date));
					obj.setEstatus("1");
					SPFService.save(obj);
				}
				return "Correcto";
			}
				 
		}
		
		@GetMapping("/empleados-spf-cambio-modelo/{id}")
		public String ListaEmpleadosSPFCambio(@PathVariable(value = "id") Long id,Model model){
			
			
	    	return"cambio-modelo-falda";
		}
		
		@GetMapping("/agregar-falda-spf/{id}")
		public String addCoordinados(@PathVariable(value = "id") Long id,Map<String, Object> model) {
			ComercialCoordinado res = null;
			Integer contador = CoordinadoService.ContadorCoordinadoCliente(id);
			if ( contador ==0) {
				ComercialCoordinado coor = new ComercialCoordinado () ;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				Date date = new Date();
				DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				coor.setEstatus("1");
				coor.setIdPedido(id);
				coor.setNumeroCoordinado(String.valueOf((contador + 1)));
				coor.setCreadoPor(auth.getName());
				coor.setFechaCreacion(hourdateFormat.format(date));
				coor.setUltimaFechaModificacion(hourdateFormat.format(date));
				coor.setIdText("COORSPF" + ((contador + 1) + 100));
				CoordinadoService.save(coor);
			}
			
			else {
				 res =CoordinadoService.findOneCoorSPF(id);
				
			}
			ComercialCoordinadoPrenda prenda = new ComercialCoordinadoPrenda();
			prenda.setIdCoordinado(res.getIdCoordinado());
			model.put("prenda", prenda);
			model.put("listPrendas", CoordinadoService.findAllPrenda("Falda"));
			model.put("listCoorPrenda", CoordinadoService.findAllCoorPrenda(res.getIdCoordinado()));

			return "agregar-coordinado";
		}
		
}
