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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altima.springboot.app.models.entity.ComercialCoordinadoPreapartado;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComercialPreApartado;
import com.altima.springboot.app.models.entity.ComercialPrendaPreapartado;
import com.altima.springboot.app.models.entity.ComercialTelasPreapartado;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialAgentesVentaService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialPreApartadoService;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
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
	
	@Autowired
	private IHrEmpleadoService empleadoService;
	
	@Transactional
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_APARTADO_TELAS_LISTAR"})
	@GetMapping("/apartado-de-telas")
	public String listApartado(Model model) {
		
		
		try {
			model.addAttribute("ListPedidos", agenteVentaService.findAllApartadoTelas());
			
			return "apartado-de-telas";
		}
		catch(Exception e) {
			
			return "apartado-de-telas";
		}
		
		finally {
			System.out.println("Fin de método listApartado");
		}
	}
	
	@Transactional
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_APARTADO_TELAS_CONFIRMAR"})
	@GetMapping("/editar-apartado-de-telas/{id}")
	public String editApartado(@PathVariable(value="id")Long id) {
	
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			ComercialPedidoInformacion pedidoInfo = pedidoService.findOne(id);
			
			pedidoInfo.setFechaApartadoTelas(dateFormat.format(date));
			pedidoInfo.setActualizadoPor(auth.getName());
			
			pedidoService.save(pedidoInfo);
			
			return "redirect:/apartado-de-telas";
		}
		catch(Exception e) {
			
			return "redirect:/apartado-de-telas";
		}
		
		finally {
			System.out.println("Fin de método editApartado");
		}
	}

	@Transactional
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_APARTADO_TELAS_REPORTE"})
	@RequestMapping(value = {"/detalle-reporte/{id}"}, method = RequestMethod.GET)
	public String detalleReporte(@PathVariable(value="id")Long id, Model model) {
		
		try {
			model.addAttribute("apartadoTelasReporte", agenteVentaService.findDatosReporteApartadoTelas(id,false));
			
			return "/detalle-reporte";
		}
		catch(Exception e) {
			
			return "/detalle-reporte";
		}
		
		finally {
			System.out.println("Fin de método detalleReporte");
		}
	}
	
	@Transactional
	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_APARTADO_TELAS_REPORTEGENERAL"})
	@RequestMapping(value = {"/detalle-reporte-general/{id}"}, method = RequestMethod.GET)
	public String detalleReporteGeneral(@PathVariable(value="id")Long id, Model model) {
		
		try {
			model.addAttribute("apartadoTelasReporte", agenteVentaService.findDatosReporteApartadoTelas(id,false));
			
			return "/detalle-reporte-general";
		}
		catch(Exception e) {
			
			return "/detalle-reporte-general";
		}
		
		finally {
			System.out.println("Fin de método detalleReporteGeneral");
		}
	}
	
	
	/////////////////////////////////// Metodos de pre-apartado de telas /////////////////////////////////
	@Transactional
	@GetMapping("/pre-apartado-telas")
	public String listPreApartado(Model model) {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			if(auth.getName().equalsIgnoreCase("ADMIN")) {
	
				
				model.addAttribute("ListaClientes", clienteService.findAll(null));
				model.addAttribute("ListaAgentesVenta", empleadoService.findAllByPuesto("Agente de Ventas"));
				model.addAttribute("ListPreapartados", preapartadoService.findPreapartados(""));
			}
			else {
				try {
					Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
					System.out.println("Es un agente de ventas");
					model.addAttribute("ListaClientes", clienteService.findClientesByAgenteVentas(Long.parseLong(empleado[0].toString())));
					model.addAttribute("ListPreapartados", preapartadoService.findPreapartados(auth.getName()));
					
				}
				catch(Exception e) {
					System.out.println("No es un agente de ventas \n"+e);
					model.addAttribute("ListPreapartados", preapartadoService.findPreapartados(""));
				}
			}
			
			
			return "pre-apartado-telas";
		}
		catch(Exception e) {
			
			return "pre-apartado-telas";
		}
		
		finally {
			System.out.println("Fin de método listPreApartado");
		}
	}
	
	@Transactional
	@GetMapping("confirmacion-pre-apartado/h58fhgkt673GSRF{idCliente}GH63{selectAgente}GS63dd{numPersonas}gresdr2")
	public String guardarPreapartado(@PathVariable(value="idCliente")Long idCliente,
									 @PathVariable(value="numPersonas")int numPersonas,
									 @PathVariable(value="selectAgente", required=false)Long selectAgente) {

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			ComercialPreApartado preApartado = new ComercialPreApartado();
			try {
				
				if(auth.getName().equalsIgnoreCase("ADMIN")) {
					preApartado.setIdEmpleado(selectAgente);
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
		catch(Exception e) {
			
			return "redirect:/pre-apartado-telas";
		}
		
		finally {
			System.out.println("Fin de método guardarPreapartado");
		}
	}
	
	@Transactional
	@RequestMapping("confirmacion-estatus-pre-apartado/h58fhg{idPreapartado}kt673GSRF{estatusPedido}GH63GS63dd{refPedido}gresdr2")
	public String cambioEstatusPedidoPreapartado (@PathVariable(value="idPreapartado")Long idPreapartado, 
												  @PathVariable(value="estatusPedido")int estatusPedido,
												  @PathVariable(value="refPedido")String refPedido) {
		
		try {
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
		catch(Exception e) {
			
			return "redirect:/pre-apartado-telas";
		}
		
		finally {
			System.out.println("Fin de método cambioEstatusPedidoPreapartado");
		}
	}
	
	@Transactional
	@RequestMapping("confirmacion-estatus-pre-apartado/h58fhg{idPreapartado}kt673GSRFGH63GS63dd{estatusValue}gresdr2")
	public String EstatusPedidoPreapartado (@PathVariable(value="idPreapartado")Long idPreapartado, 
											 @PathVariable(value="estatusValue")int estatusValue) {
		
		//si estatusValue es 1 se va a autorizar(estatus=1) si estatusValue es 2 se va a rechazar (estatus=2)
		
		try {
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
		catch(Exception e) {
			
			return "redirect:/pre-apartado-telas";
		}
		
		finally {
			System.out.println("Fin de método EstatusPedidoPreapartado");
		}
	}
	
	//Estos métodos son para los coordinados de un preapartado
	
	@Transactional
	@RequestMapping("/Coordinados-pre-apartado/{id}")
	public String coordinados (Model model, @PathVariable(value="id")Long idPreapartado) {
		
		try {
			model.addAttribute("idPreapartado", idPreapartado);
			model.addAttribute("listCoordinadosPreapartado", preapartadoService.findCoordinadosByPreapartado(idPreapartado));
			
			return "Coordinados-pre-apartado";
		}
		catch(Exception e) {
			
			return "Coordinados-pre-apartado";
		}
		
		finally {
			System.out.println("Fin de método coordinados");
		}
	}
	
	@Transactional
	@RequestMapping("/nuevo-coordinado/{idPreapartado}")
	public String guardarNuevoCoordinado(@PathVariable(value="idPreapartado")Long idPreapartado) {
		
		try {
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
		catch(Exception e) {
			
			return "redirect:/Coordinados-pre-apartado/"+idPreapartado;
		}
		
		finally {
			System.out.println("Fin de método guardarNuevoCoordinado");
		}
	}
	
	
	@Transactional
	@RequestMapping("/eliminar-coordinado-preapartado/{idCoordinado}/{idPreapartado}")
	public String eliminarCoordinado(@PathVariable(value="idCoordinado")Long idCoordinado,
									 @PathVariable(value="idPreapartado")Long idPreapartado) {
		
		try {
		preapartadoService.deleteCoordinado(idCoordinado);
		
		return "redirect:/Coordinados-pre-apartado/"+idPreapartado;
		
		}
		catch(Exception e) {
			
			return "redirect:/Coordinados-pre-apartado/"+idPreapartado;
		}
		
		finally {
			System.out.println("Fin de método eliminarCoordinado");
		}
	}
	
	//Estos métodos son para las prendas de los coordinados de un preapartado
	@Transactional
	@RequestMapping("/prendas-Coordinados-pre-apartado/{id}/{idPreapartado}")
	public String PrendasCoordinados (Model model, @PathVariable(value="id")Long idCoordinado,
												   @PathVariable(value="idPreapartado")Long idPreapartado, Map<String, Object> m) {
		
		try {
			System.out.println(idPreapartado);
			model.addAttribute("idPreapartado", idPreapartado);
			model.addAttribute("idCoordinado", idCoordinado);
			m.put("listPrendas", CoordinadoService.findAllPrenda());
			model.addAttribute("listPrendasCoordinadoPreapartado", preapartadoService.findPrendasByCoordinados(idCoordinado));
			
			return "prendas-Coordinado-pre-apartado";
		}
		catch(Exception e) {
			
			return "prendas-Coordinado-pre-apartado";
		}
		
		finally {
			System.out.println("Fin de método PrendasCoordinados");
		}
	}
	
	@Transactional
	@RequestMapping("/nueva-prenda-coordinado/{idCoordinado}FED{idsTelas}rREdw3{idsMateriales}232f3{idTela}5edcs3{idPrenda}fsc5FS3sd{idFamPrenda}")
	public String guardarNuevaPrendaCoordinado(@PathVariable(value="idCoordinado")Long idCoordinado,
												@PathVariable(value="idTela")Long idTela,
												@PathVariable(value="idPrenda")Long idPrenda,
												@PathVariable(value="idFamPrenda")Long idFamPrenda,
												@PathVariable(value="idsTelas", required=false)String idsTelas,
												@PathVariable(value="idsMateriales", required=false)String idsMateriales) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ComercialPrendaPreapartado coorPrenda = new ComercialPrendaPreapartado();
		
		String[] idTelas= idsTelas.split("id_tela");
		String[] idMats= idsMateriales.split("id_materialConv");

		try {
			coorPrenda.setIdPrenda(idPrenda);
			coorPrenda.setIdFamiliaPrenda(idFamPrenda);
			coorPrenda.setIdTela(idTela);
			coorPrenda.setIdCoordinado(idCoordinado);
			coorPrenda.setCreadoPor(auth.getName());
			coorPrenda.setActualizadoPor(auth.getName());
			coorPrenda.setFechaCreacion(dtf.format(now));
			coorPrenda.setUltimaFechaModificacion(dtf.format(now));
			
			preapartadoService.savePrendaCoordinado(coorPrenda);
		
			try {
				for(int i = 0; i<idTelas.length;i++) {
					ComercialTelasPreapartado telasPreapartado = new ComercialTelasPreapartado();
					 
						telasPreapartado.setIdPrendaPreapartado(coorPrenda.getIdPrendaPreapartado());
						telasPreapartado.setIdTela(Long.parseLong(idTelas[i]));
						telasPreapartado.setIdMaterial(Long.parseLong(idMats[i]));
						telasPreapartado.setCreadoPor(auth.getName());
						telasPreapartado.setActualizadoPor(auth.getName());
						telasPreapartado.setFechaCreacion(dtf.format(now));
						telasPreapartado.setUltimaFechaModificacion(dtf.format(now));
						
						preapartadoService.saveTelasCoordinado(telasPreapartado);
					}
			}
			catch(Exception p) {
				System.out.println("No contiene telas en combinación");
			}
		
			ComercialCoordinadoPreapartado coorPreapartado = preapartadoService.findCoordinado(idCoordinado);
			
			coorPreapartado.setTotalPrendas(preapartadoService.findPrendasByCoordinados(idCoordinado).size());
			
			preapartadoService.saveCoordinado(coorPreapartado);
			
			return "redirect:/prendas-Coordinados-pre-apartado/"+idCoordinado;
		}
		catch(Exception e) {
			
			return "redirect:/prendas-Coordinados-pre-apartado/"+idCoordinado;
		}
		
		finally {
			System.out.println("Fin de método guardarNuevaPrendaCoordinado");
		}
		
	}
	
	@Transactional
	@RequestMapping("/eliminar-prenda-coordinado-preapartado/{idCoordinado}/{idPrendaCoordinadoPreapartado}")
	public String eliminarPrendaCoordinado(@PathVariable(value="idCoordinado")Long idCoordinado,
									 @PathVariable(value="idPrendaCoordinadoPreapartado")Long idPrendaCoordinadoPreapartado) {
		
		try {
			preapartadoService.deletePrendaCoordinado(idPrendaCoordinadoPreapartado);
			
			ComercialCoordinadoPreapartado coorPreapartado = preapartadoService.findCoordinado(idCoordinado);
			
			coorPreapartado.setTotalPrendas(preapartadoService.findPrendasByCoordinados(idCoordinado).size());
			
			preapartadoService.saveCoordinado(coorPreapartado);
			
			return "redirect:/prendas-Coordinados-pre-apartado/"+idCoordinado;
		}
		catch(Exception e) {
			
			return "redirect:/prendas-Coordinados-pre-apartado/"+idCoordinado;
		}
		
		finally {
			System.out.println("Fin de método eliminarPrendaCoordinado");
		}
		
	}
	
	@Transactional
	@RequestMapping(value = {"/detalle-reporte-preapartado/{id}"}, method = RequestMethod.GET)
	public String detalleReportePreapartado(@PathVariable(value="id")Long id, Model model) {
		
		try {
			model.addAttribute("preapartadoTelasReporte", preapartadoService.reportePreapartados(id));
		}
		catch(Exception e) {
			
		}
		finally {
			System.out.println("Fin de método detalleReportePreapartado");
		}
		return "/detalle-reporte-preapartado";
	}
	
}
