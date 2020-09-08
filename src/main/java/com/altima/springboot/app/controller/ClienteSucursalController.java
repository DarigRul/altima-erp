package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialClienteFactura;
import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialClienteSucursalService;
import com.altima.springboot.app.models.service.IHrDireccionService;
import com.altima.springboot.app.models.service.IUsuarioService;
import com.altima.springboot.app.models.service.JpaUserDetailsService;

@Controller
public class ClienteSucursalController {
	@Autowired
	private IUsuarioService UsuarioService;
	@Autowired
	private IComercialClienteService ClienteService;
	@Autowired
	private IHrDireccionService DireccionService;
	@Autowired
	private IComercialClienteSucursalService SucursalService;
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@GetMapping("/sucursales/{id}")
	public String listClients(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuarioagente=UsuarioService.FindAllUserAttributes(auth.getName(),auth.getAuthorities());
		ComercialCliente cliente = null;
		cliente = ClienteService.findOne(id);
		model.put("cliente", cliente);
		if (usuarioagente != null ) {
			model.put("sucursales", SucursalService.ClienteSucursales(id,usuarioagente.getIdUsuario()));
		}
		
		return "sucursales";
	}
	
	@GetMapping("/agregar-sucursal/{id}")
	public String crearCliente(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		ComercialCliente cliente = null;
		cliente = ClienteService.findOne(id);
		ComercialClienteSucursal sucursal = new ComercialClienteSucursal();
		HrDireccion direccion = new HrDireccion();
		model.put("sucursal", sucursal);
		model.put("cliente", cliente);
		model.put("direccion", direccion);
		model.put("subtitulo", "Nueva");
		return "agregar-sucursal";
	}
	
	@GetMapping("/guardar-sucursal")
	public String guardarCliente(ComercialClienteSucursal sucursal, HrDireccion direccion,
			RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Usuario usuarioagente=UsuarioService.FindAllUserAttributes(auth.getName(),auth.getAuthorities());
		if (sucursal.getIdClienteSucursal() == null && direccion.getIdDireccion() == null) {
			if ( direccion.getNumeroExt()== null || direccion.getNumeroExt().isEmpty()) {
				direccion.setNumeroExt("S/N");
			}
			direccion.setEstatus(1);
			direccion.setUltimaFechaModificacion(hourdateFormat.format(date));
			direccion.setFechaCreacion(hourdateFormat.format(date));
			direccion.setCreadoPor(auth.getName());
			DireccionService.save(direccion);
			direccion.setIdText("DIR" + direccion.getIdDireccion());
			DireccionService.save(direccion);
			// Guardamos los datos de la sucursal.
			sucursal.setIdAgente(usuarioagente.getIdUsuario());
			sucursal.setIdDireccion(direccion.getIdDireccion());
			sucursal.setSestatus("1");
			sucursal.setSCreadoPor(auth.getName());
			sucursal.setSFechaCreacion(hourdateFormat.format(date));
			sucursal.setSUltimaFechaModificacion(hourdateFormat.format(date));
			SucursalService.save(sucursal);
			sucursal.setSIdText("SUC" + (SucursalService.IdScursal(sucursal.getIdCliente())) );
			sucursal.setSCreadoPor(auth.getName());
			sucursal.setIdDireccion(direccion.getIdDireccion());
			redirectAttrs.addFlashAttribute("title", "Sucursal guardada correctamente").addFlashAttribute("icon", "success");
			SucursalService.save(sucursal);
		} else {
			if ( direccion.getNumeroExt()== null || direccion.getNumeroExt().isEmpty()) {
				direccion.setNumeroExt("S/N");
			}
			direccion.setActualizadoPor(auth.getName());
			direccion.setUltimaFechaModificacion(hourdateFormat.format(date));
			sucursal.setSIdText("SUC" + sucursal.getNoSucursal());
			sucursal.setSActualizadoPor(auth.getName());
			sucursal.setSUltimaFechaModificacion(hourdateFormat.format(date));;
			redirectAttrs.addFlashAttribute("title", "Sucursal editada correctamente").addFlashAttribute("icon", "success");
			DireccionService.save(direccion);
			SucursalService.save(sucursal);
		}
		
		return "redirect:sucursales/" + sucursal.getIdCliente();
	}
	
	@GetMapping("/editar-sucursal/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		
		ComercialCliente cliente = null;
		HrDireccion direccion = null;
		ComercialClienteSucursal sucursal = null;
		sucursal = SucursalService.findOne(id);
		direccion = DireccionService.findOne(sucursal.getIdDireccion());
		cliente = ClienteService.findOne(  Long.parseLong(sucursal.getIdCliente() ) );
		model.put("sucursal", sucursal);
		model.put("cliente", cliente);
		model.put("direccion", direccion);
		model.put("subtitulo", "Editar");
		
		return "agregar-sucursal";
	}
	
	@GetMapping("delete-sucursal/{id}") 
	public String delete_sucursal(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		ComercialClienteSucursal sucursal = null;
		sucursal = SucursalService.findOne(id);
		sucursal.setSestatus("0");
		sucursal.setSActualizadoPor(auth.getName());
		sucursal.setSUltimaFechaModificacion(hourdateFormat.format(date));
		SucursalService.save(sucursal);
	
		redirectAttrs
        .addFlashAttribute("title", "Sucursal dada de baja  correctamente")
        .addFlashAttribute("icon", "success");
		return "redirect:/sucursales/" + sucursal.getIdCliente();
	}
	
	@GetMapping("alta-sucursal/{id}") 
	public String alta_sucursal(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		ComercialClienteSucursal sucursal = null;
		sucursal = SucursalService.findOne(id);
		sucursal.setSestatus("1");
		sucursal.setSActualizadoPor(auth.getName());
		sucursal.setSUltimaFechaModificacion(hourdateFormat.format(date));
		SucursalService.save(sucursal);
	
		redirectAttrs
        .addFlashAttribute("title", "Sucursal dada de alta correctamente")
        .addFlashAttribute("icon", "success");
		return "redirect:/sucursales/" + sucursal.getIdCliente();
	}
	
}
