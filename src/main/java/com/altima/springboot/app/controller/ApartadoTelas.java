package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

import com.altima.springboot.app.models.entity.ComercialCoordinadoPreapartado;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComercialPreApartado;
import com.altima.springboot.app.models.entity.ComercialPrendaPreapartado;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialAgentesVentaService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialPreApartadoService;
import com.altima.springboot.app.models.service.IUsuarioService;

@Controller
public class ApartadoTelas {
	
	@Autowired
	private IComercialAgentesVentaService agenteVentaService;
	
	@Autowired
	private ICargaPedidoService pedidoService;
	
	@Autowired
	private IComercialPreApartadoService preapartadoService;
	
	@Autowired
	private IComercialClienteService clienteService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IComercialCoordinadoService CoordinadoService;
	
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_APARTADO_TELAS_LISTAR"})
	@GetMapping("/apartado-de-telas")
	public String listApartado(Model model) {
		
		model.addAttribute("ListPedidos", agenteVentaService.findAllApartadoTelas());
		
		return "apartado-de-telas";
	}
	
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_APARTADO_TELAS_CONFIRMAR"})
	@GetMapping("/editar-apartado-de-telas/{id}")
	public String editApartado(@PathVariable(value="id")Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		ComercialPedidoInformacion pedidoInfo = pedidoService.findOne(id);
		
		pedidoInfo.setFechaApartadoTelas(dateFormat.format(date));
		pedidoInfo.setActualizadoPor(auth.getName());
		
		pedidoService.save(pedidoInfo);
		
		return "redirect:/apartado-de-telas";
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_APARTADO_TELAS_REPORTE"})
	@RequestMapping(value = {"/detalle-reporte/{id}"}, method = RequestMethod.GET)
	public String detalleReporte(@PathVariable(value="id")Long id, Model model) {
		
		model.addAttribute("apartadoTelasReporte", agenteVentaService.findDatosReporteApartadoTelas(id,false));
		
		return "/detalle-reporte";
	}
	
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_APARTADO_TELAS_REPORTEGENERAL"})
	@RequestMapping(value = {"/detalle-reporte-general/{id}"}, method = RequestMethod.GET)
	public String detalleReporteGeneral(@PathVariable(value="id")Long id, Model model) {
		
		model.addAttribute("apartadoTelasReporte", agenteVentaService.findDatosReporteApartadoTelas(id,false));
		
		return "/detalle-reporte-general";
	}
	
	
	/////////////////////////////////// Metodos de pre-apartado de telas /////////////////////////////////
	
	@GetMapping("/pre-apartado-telas")
	public String listPreApartado(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getName().equalsIgnoreCase("ADMIN")) {

			
			model.addAttribute("ListaClientes", clienteService.findAll(null));

		}
		else {
			try {
				Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
				System.out.println("Es un agente de ventas");
				model.addAttribute("ListaClientes", clienteService.findClientesByAgenteVentas(Long.parseLong(empleado[0].toString())));
				
			}
			catch(Exception e) {
				System.out.println("No es un agente de ventas \n"+e);
			}
		}
		
		model.addAttribute("ListPreapartados", preapartadoService.findPreapartados());
		return "pre-apartado-telas";
	}
	
	@GetMapping("confirmacion-pre-apartado/h58fhgkt673GSRF{idCliente}GH63GS63dd{numPersonas}gresdr2")
	public String guardarPreapartado(@PathVariable(value="idCliente")Long idCliente,@PathVariable(value="numPersonas")int numPersonas) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ComercialPreApartado preApartado = new ComercialPreApartado();
		try {
			
			if(auth.getName().equalsIgnoreCase("ADMIN")) {
				preApartado.setIdEmpleado(Long.parseLong("0"));
			}
			else {
				Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
				preApartado.setIdEmpleado(Long.parseLong(empleado[0].toString()));
			}
			
		}
		catch(Exception e) {
			System.out.println("No es un agente de ventas \n"+e);
			Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
			preApartado.setIdEmpleado(Long.parseLong(empleado[0].toString()));
		}

		preApartado.setIdText("");
		preApartado.setIdCliente(idCliente);
		preApartado.setNumPersonas(numPersonas);
		preApartado.setFechaPreapartado(dtf.format(now));
		preApartado.setEstatus(0);
		preApartado.setEstatusPedido(0);
		preApartado.setFechaCreacion(dtf.format(now));
		preApartado.setUltimaFechaModificacion(dtf.format(now));
		preApartado.setCreadoPor(auth.getName());
		preApartado.setActualizadoPor(auth.getName());
		
		preapartadoService.save(preApartado);
		preApartado.setIdText("PRE"+(100000+preApartado.getIdPreapatado()));
		preapartadoService.save(preApartado);
		
		return "redirect:/pre-apartado-telas";
		
	}
	
	@RequestMapping("confirmacion-estatus-pre-apartado/h58fhg{idPreapartado}kt673GSRF{estatusPedido}GH63GS63dd{refPedido}gresdr2")
	public String cambioEstatusPedidoPreapartado (@PathVariable(value="idPreapartado")Long idPreapartado, 
												  @PathVariable(value="estatusPedido")int estatusPedido,
												  @PathVariable(value="refPedido")String refPedido) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ComercialPreApartado preApartado = preapartadoService.findOne(idPreapartado);
		
		preApartado.setEstatusPedido(estatusPedido);
		preApartado.setReferenciaPedido((refPedido.equals(""))?null:refPedido);
		preApartado.setUltimaFechaModificacion(dtf.format(now));
		preApartado.setActualizadoPor(auth.getName());
		
		preapartadoService.save(preApartado);
		
		return "redirect:/pre-apartado-telas";
	}
	
	@RequestMapping("confirmacion-estatus-pre-apartado/h58fhg{idPreapartado}kt673GSRFGH63GS63dd{estatusValue}gresdr2")
	public String EstatusPedidoPreapartado (@PathVariable(value="idPreapartado")Long idPreapartado, 
											 @PathVariable(value="estatusValue")int estatusValue) {
		
		//si estatusValue es 1 se va a autorizar(estatus=1) si estatusValue es 2 se va a rechazar (estatus=2)
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ComercialPreApartado preApartado = preapartadoService.findOne(idPreapartado);
		
		preApartado.setEstatus(estatusValue);
		preApartado.setUltimaFechaModificacion(dtf.format(now));
		preApartado.setActualizadoPor(auth.getName());
		
		preapartadoService.save(preApartado);
		
		return "redirect:/pre-apartado-telas";
	}
	
	//Estos métodos son para los coordinados de un preapartado
	
	@RequestMapping("/Coordinados-pre-apartado/{id}")
	public String coordinados (Model model, @PathVariable(value="id")Long idPreapartado) {
		
		model.addAttribute("idPreapartado", idPreapartado);
		model.addAttribute("listCoordinadosPreapartado", preapartadoService.findCoordinadosByPreapartado(idPreapartado));
		
		return "Coordinados-pre-apartado";
	}
	
	@RequestMapping("/nuevo-coordinado/{idPreapartado}")
	public String guardarNuevoCoordinado(@PathVariable(value="idPreapartado")Long idPreapartado) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ComercialCoordinadoPreapartado coorPreapartado = new ComercialCoordinadoPreapartado();
		
		coorPreapartado.setIdText("");
		coorPreapartado.setTotalPrendas(0);
		coorPreapartado.setIdPreapartado(idPreapartado);
		coorPreapartado.setCreadoPor(auth.getName());
		coorPreapartado.setActualizadoPor(auth.getName());
		coorPreapartado.setFechaCreacion(dtf.format(now));
		coorPreapartado.setUltimaFechaModificacion(dtf.format(now));
		
		preapartadoService.saveCoordinado(coorPreapartado);
		coorPreapartado.setIdText("CPRE"+(10000+coorPreapartado.getIdCoordinado()));
		preapartadoService.saveCoordinado(coorPreapartado);
		
		return "redirect:/Coordinados-pre-apartado/"+idPreapartado;
	}
	
	
	@RequestMapping("/eliminar-coordinado-preapartado/{idCoordinado}/{idPreapartado}")
	public String eliminarCoordinado(@PathVariable(value="idCoordinado")Long idCoordinado,
									 @PathVariable(value="idPreapartado")Long idPreapartado) {
		
		preapartadoService.deleteCoordinado(idCoordinado);
		
		return "redirect:/Coordinados-pre-apartado/"+idPreapartado;
	}
	
	//Estos métodos son para las prendas de los coordinados de un preapartado
	
	@RequestMapping("/Prendas-Coordinados-pre-apartado/{id}")
	public String PrendasCoordinados (Model model, @PathVariable(value="id")Long idCoordinado, Map<String, Object> m) {
		
		model.addAttribute("idCoordinado", idCoordinado);
		m.put("listPrendas", CoordinadoService.findAllPrenda());
		model.addAttribute("listPrendasCoordinadoPreapartado", preapartadoService.findPrendasByCoordinados(idCoordinado));
		
		return "Prendas-Coordinado-pre-apartado";
	}
	
	@RequestMapping("/nueva-prenda-coordinado/{idCoordinado}FEDrf3{idTela}5edcs3{idPrenda}fsc5FS3sd{idFamPrenda}")
	public String guardarNuevvaPrendaCoordinado(@PathVariable(value="idCoordinado")Long idCoordinado,
												@PathVariable(value="idTela")Long idTela,
												@PathVariable(value="idPrenda")Long idPrenda,
												@PathVariable(value="idFamPrenda")Long idFamPrenda) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ComercialPrendaPreapartado coorPrenda = new ComercialPrendaPreapartado();
		
		coorPrenda.setIdPrenda(idPrenda);
		coorPrenda.setIdFamiliaPrenda(idFamPrenda);
		coorPrenda.setIdTela(idTela);
		coorPrenda.setIdCoordinado(idCoordinado);
		coorPrenda.setCreadoPor(auth.getName());
		coorPrenda.setActualizadoPor(auth.getName());
		coorPrenda.setFechaCreacion(dtf.format(now));
		coorPrenda.setUltimaFechaModificacion(dtf.format(now));
		
		preapartadoService.savePrendaCoordinado(coorPrenda);
		
		
		
		ComercialCoordinadoPreapartado coorPreapartado = preapartadoService.findCoordinado(idCoordinado);
		
		coorPreapartado.setTotalPrendas(preapartadoService.findPrendasByCoordinados(idCoordinado).size());
		
		preapartadoService.saveCoordinado(coorPreapartado);
		
		return "redirect:/Prendas-Coordinados-pre-apartado/"+idCoordinado;
	}
	
	
	@RequestMapping("/eliminar-prenda-coordinado-preapartado/{idCoordinado}/{idPrendaCoordinadoPreapartado}")
	public String eliminarPrendaCoordinado(@PathVariable(value="idCoordinado")Long idCoordinado,
									 @PathVariable(value="idPrendaCoordinadoPreapartado")Long idPrendaCoordinadoPreapartado) {
		
		preapartadoService.deletePrendaCoordinado(idPrendaCoordinadoPreapartado);
		
		ComercialCoordinadoPreapartado coorPreapartado = preapartadoService.findCoordinado(idCoordinado);
		
		coorPreapartado.setTotalPrendas(preapartadoService.findPrendasByCoordinados(idCoordinado).size());
		
		preapartadoService.saveCoordinado(coorPreapartado);
		
		return "redirect:/Prendas-Coordinados-pre-apartado/"+idCoordinado;
	}
	
}
