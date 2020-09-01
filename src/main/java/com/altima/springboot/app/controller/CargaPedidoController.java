package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialClienteFacturaService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialClienteSucursalService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialPrendaBordadoService;
import com.altima.springboot.app.models.service.IUsuarioService;

@CrossOrigin(origins = { "*" })
@Controller
public class CargaPedidoController {

	@Autowired
	private ICargaPedidoService cargaPedidoService;

	@Autowired
	private ComercialClienteEmpleadoService cargaclienteempleadoservice;

	@Autowired
	private IComercialClienteFacturaService icomercialclientefacturaservice;

	@Autowired
	private IComercialClienteSucursalService icomercialclientesucursalservice;

	@Autowired
	private IComercialClienteService clienteservice;
	
	@Autowired
	private IComercialPrendaBordadoService bordadoService;
	
	@Autowired
	private IComercialCoordinadoService CoordinadoService;


	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	AuthComponent currentuserid;

	@RequestMapping(value = "/mostrar-pedidos", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> operadores(Long id) {

		return cargaPedidoService.PedidosExistenteIdEmpresa(id);
	}

	@PostMapping("/guardar-carga-pedido")
	public String guardacatalogo(Long cargaEmpresa, String cargaTipopedido, Long id_pedido, HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialPedidoInformacion pedido = new ComercialPedidoInformacion();
		System.out.println("eL ID de pedido es: " + id_pedido);
		pedido.setIdEmpresa(cargaEmpresa);
		pedido.setTipoPedido(cargaTipopedido);
		pedido.setIdPedido(id_pedido);
		pedido.setCreadoPor(auth.getName());
		pedido.setFechaCreacion(hourdateFormat.format(date));
		pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
		pedido.setEstatus("1");
		pedido.setIdUsuario(currentuserid.currentuserid());
		cargaPedidoService.save(pedido);
		pedido.setIdText("VENT" + (pedido.getIdPedidoInformacion() + 10000));
		cargaPedidoService.save(pedido);

		redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon", "success");
		return "redirect:/carga-de-pedidos";

	}

	@PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	@GetMapping("/informacion-general/{id}")
	public String listGeneral(@PathVariable(value = "id") Long id, Map<String, Object> model, Model m) {
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		m.addAttribute("clientes", clienteservice.findAll(null));
		model.put("pedido", pedido);
		return "informacion-general";
	}

	@PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	@GetMapping("/agregar-empleado-empresa/{id}/{idcliente}")
	public String getEmpleadosInsert(@PathVariable(value = "id") Long id,
			@PathVariable(value = "idcliente") Long idcliente, Map<String, Object> model) {
		System.out.println("Init process" + idcliente);

		try {

			// System.out.println("las query
			// "+icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente).get(0).getNombreSucursal());
			model.put("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(id));

			model.put("form", new ArrayList<ComercialClienteEmpleado>());
			model.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
			model.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
			model.put("isPreviewView", "false");
			model.put("idPedido", id);
			return "agregar-empleado-empresa";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
			model.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
			return "agregar-empleado-empresa";

		} finally {
			System.out.println("Finalizar proceso");
		}

	}

	
	@RequestMapping(value = "/validar-cambio-precio", method = RequestMethod.GET)
	@ResponseBody
	public boolean validar(String estatusPrecios, Long id) {
		ComercialPedidoInformacion aux = cargaPedidoService.findOne(id);
		
		if ( aux.getPrecioUsar() != null) {
			if (!  aux.getPrecioUsar().equals(estatusPrecios)) {
				return true ;
			}
			else {
				return false ;
			}
		}
		else {
			return false ;	
		}
	
		
		
	}
	
	@PostMapping("/guardar-informacion-general-pedido")
	public String guardarCliente(ComercialPedidoInformacion pedido, RedirectAttributes redirectAttrs) {

		if (pedido.getFechaAnticipo().equals("")) {
			pedido.setFechaAnticipo(null);
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		pedido.setActualizadoPor(auth.getName());
		pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
		pedido.setIdUsuario(currentuserid.currentuserid());
		
		cargaPedidoService.save(pedido);
		redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon", "success");
		return "redirect:/carga-de-pedidos";

	}
	
	@PostMapping("/guardar-informacion-general-pedido2")
	public String guardarCliente2(ComercialPedidoInformacion pedido, RedirectAttributes redirectAttrs) {
		
		if (pedido.getFechaAnticipo().equals("")) {
			pedido.setFechaAnticipo(null);
		}
		
		ComercialPedidoInformacion aux = cargaPedidoService.findOne(pedido.getIdPedidoInformacion());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		pedido.setActualizadoPor(auth.getName());
		pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
		pedido.setIdUsuario(currentuserid.currentuserid());
		if (! aux.getPrecioUsar().equals(pedido.getPrecioUsar())) {
			List<Object[]> auxlist = bordadoService.CambioPrecio(pedido.getIdPedidoInformacion());
			for (Object[] a : auxlist) {

				Long id_coor = Long.parseLong(a[0].toString());
				Float precio_bordado = Float.parseFloat(a[7].toString());
				Float precio_usar = Float.parseFloat(a[8].toString());
				
				
				ComercialCoordinadoPrenda prenda = CoordinadoService.findOneCoorPrenda(id_coor);
				Float preciofinal = precio_bordado + precio_usar ;
				prenda.setPrecio(Float.toString(precio_usar));
				prenda.setPrecioFinal(Float.toString(preciofinal));
				prenda.setMontoAdicional("0.00");
				prenda.setAdicional("0.00");
				CoordinadoService.saveCoorPrenda(prenda);
			}
		}
		
		cargaPedidoService.save(pedido);
		
		

		redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon", "success");
		return "redirect:/carga-de-pedidos";

	}
	
	 @PostMapping("/observaciones")
	    public String guardarobservaciones(RedirectAttributes redirectAttrs,String observacion,Long idpedido ) {
	        	ComercialPedidoInformacion pedido=cargaPedidoService.findOne(idpedido);
	        	pedido.setObservacion(observacion);
	        	cargaPedidoService.save(pedido);
	    	redirectAttrs.addFlashAttribute("title", "Observaciones guardadas correctamente").addFlashAttribute("icon", "success");
			return "redirect:/carga-de-pedidos";
	    }
	 
	 
	 @PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	 @RequestMapping(value = "/cerrar-expediente", method = RequestMethod.GET)
	@ResponseBody
		public String  cerrar(Long id) {
		 
		 String list =cargaPedidoService.ValidarCantidadEspecial(id);
		if ( list == null) {
			
			System.out.println("nuloooooooooooo");
			ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
			pedido.setEstatus("2");
			cargaPedidoService.save(pedido);
			return null;
			
		}else {
			return list;
		}
		
		}
	 
	 @RequestMapping(value = "/abrir-expediente", method = RequestMethod.GET)
		@ResponseBody
			public String  abrir(Long id) {
			 
				ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
				pedido.setEstatus("1");
				cargaPedidoService.save(pedido);
				return null;
			
			}
}
   

