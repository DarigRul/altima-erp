package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialMovimiento;
import com.altima.springboot.app.models.entity.ComercialMovimientoMuestraDetalle;
import com.altima.springboot.app.models.entity.ComercialRackPrenda;
import com.altima.springboot.app.models.entity.ComercialTokenTraspaso;
import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialMovimientoMuestraDetalleService;
import com.altima.springboot.app.models.service.IComercialMovimientoService;
import com.altima.springboot.app.models.service.IComercialRackPrendaService;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
import com.altima.springboot.app.models.service.IProduccionDetalleService;
import com.altima.springboot.app.models.service.IUsuarioService;

@RestController
public class ComercialMovimientoRestController {

	@Autowired
	private IComercialClienteService clienteService;
	
	@Autowired
	private IComercialMovimientoService movimientoService;
	
	@Autowired
	private IComercialMovimientoMuestraDetalleService moviDetalleService;
	
	@Autowired
	private IHrEmpleadoService empleadoService;
	
	@Autowired
	private IProduccionDetalleService detallePedidoService;
	
	@Autowired
	private IComercialRackPrendaService rackService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@RequestMapping(value ="/listarVendedores", method=RequestMethod.GET)
	public List<Object> listarVendedores(@RequestParam(name="EsTraspaso", required=false)Long EsTraspaso){
		
		if(EsTraspaso==null) {
			return empleadoService.findAllByPuesto("Agente de Ventas");
		}
		else{
			System.out.println(EsTraspaso);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
			Long idAgente = Long.parseLong(empleado[0].toString());
			return empleadoService.findAllByPuestoWithoutAgenteLogued("Agente de Ventas", idAgente);
		}
	}
	
	@RequestMapping(value="/listarEmpresasMovimiento", method=RequestMethod.GET)
	public List<Object[]> listarEmpresasMovimiento(@RequestParam(name="idAgente", required=false)Long idAgente){
		try {
			System.out.println(idAgente);
			return clienteService.findClientesByAgenteVentas(idAgente);
			
		}
		catch(Exception e) {
			return null;
		}
		
	}
	
	@RequestMapping(value="/listarMuestras", method = RequestMethod.GET)
	public List<Object> listarMuestras (){
		
		return movimientoService.listarMuestras();
	}
	
	@RequestMapping(value="/listarMuestrasTraspaso", method = RequestMethod.GET)
	public List<Object> listarMuestrasTraspaso (@RequestParam(name="idMovimiento") Long id){
		
		return movimientoService.listarMuestrasTraspaso(id);
	}
	
	@RequestMapping(value="/agregarMuestraTablita", method= RequestMethod.GET)
	public Object agregarMuestraTablita(@RequestParam(name="idMuestra") Long id) {
		
		return movimientoService.EncontrarMuestra(id);
	}
	
	@RequestMapping(value="/guardarNuevoMovimiento", method = RequestMethod.POST)
	public int guardarNuevoMovimiento (@RequestParam(name="vendedor")String vendedor,
										@RequestParam(name="empresa") String empresa,
										@RequestParam(name="encargado")String encargado,
										@RequestParam(name="idMovimiento", required=false)Long idMovimiento,
										@RequestParam(name="object_muestras") String objectmuestras) {
		
		
		//Para editar el movimiento y sus registros
		try {
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				for (ComercialMovimientoMuestraDetalle a: moviDetalleService.findAllbyMovimiento(idMovimiento)) {
					ProduccionDetallePedido detallePedido = detallePedidoService.findOne(a.getIdDetallePedido());
					
					detallePedido.setEstatus("1");
					detallePedido.setActualizadoPor(auth.getName());
					detallePedido.setUltimaFechaModificacion(dtf.format(now));
					detallePedidoService.save(detallePedido);
				}
			}catch(Exception e) {
				System.out.println(e);
			}
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			ComercialMovimiento comercialEntity = movimientoService.findOne(idMovimiento);
			
			//Esta condición es para crear un nuevo movimiento proveniente del catalogo de muestrario
			if(comercialEntity.getEstatus().equalsIgnoreCase("rack de prendas")) {
				Long idSolicitud = comercialEntity.getIdMovimiento();
				
				comercialEntity.setEstatus("Rack de prendas registrado");
				comercialEntity.setActualizadoPor(auth.getName());
				comercialEntity.setUltimaFechaModificacion(dtf.format(now));
				movimientoService.save(comercialEntity);
				
				comercialEntity = new ComercialMovimiento();
				
				comercialEntity.setIdText("MOV"+(idSolicitud +10000));
				comercialEntity.setEmpresa(empresa);
				comercialEntity.setVendedor(vendedor);
				comercialEntity.setEncargado(encargado);
				comercialEntity.setCreadoPor(auth.getName());
				comercialEntity.setActualizadoPor(auth.getName());
				comercialEntity.setFechaCreacion(dtf.format(now));
				comercialEntity.setUltimaFechaModificacion(dtf.format(now));
				comercialEntity.setEstatus("Pendiente de recoger");
				
				movimientoService.save(comercialEntity);
				
				
			}
			//Este es para editar un movimiento
			else {
				comercialEntity.setEmpresa(empresa);
				comercialEntity.setVendedor(vendedor);
				comercialEntity.setEncargado(encargado);
				comercialEntity.setActualizadoPor(auth.getName());
				comercialEntity.setUltimaFechaModificacion(dtf.format(now));
				
				movimientoService.save(comercialEntity);
			}
			
			
			moviDetalleService.removeEntities(moviDetalleService.findAllbyMovimiento(idMovimiento));
			List<ComercialRackPrenda> rackPrendas = rackService.findAllById(idMovimiento);
			JSONArray muestras = new JSONArray(objectmuestras);
			for (int i = 0; i < muestras.length(); i++) {
				ComercialMovimientoMuestraDetalle muestraDetalleEntity = new ComercialMovimientoMuestraDetalle();
				System.out.println(muestras);
				JSONObject muestra = muestras.getJSONObject(i);
				
				ProduccionDetallePedido detallePedido = detallePedidoService.findOne(Long.parseLong(muestra.get("idmuestra").toString()));
				
				if(detallePedido.getEstatus().equalsIgnoreCase("2")) {
					System.out.println("Muestra apartada justo en el mismo momento por otro agente");
				}
				else {
						
					detallePedido.setEstatus("2");
					detallePedido.setActualizadoPor(auth.getName());
					detallePedido.setUltimaFechaModificacion(dtf.format(now));
					detallePedidoService.save(detallePedido);
					
					System.out.println(muestra.get("idmuestra").toString());
					muestraDetalleEntity.setIdDetallePedido(Long.parseLong(muestra.get("idmuestra").toString()));
					muestraDetalleEntity.setCodigoBarras(muestra.getString("codigoBarras").toString());
					muestraDetalleEntity.setIdMovimiento(comercialEntity.getIdMovimiento());
					muestraDetalleEntity.setNombreMuestra(muestra.getString("nombreMuestra").toString());
					muestraDetalleEntity.setModeloPrenda(muestra.getString("idPrenda").toString());
					muestraDetalleEntity.setCodigoTela(muestra.getString("idTela").toString());
					muestraDetalleEntity.setFechaCreacion(dtf.format(now));
					muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
					muestraDetalleEntity.setCreadoPor(auth.getName());
					muestraDetalleEntity.setActualizadoPor(auth.getName());
					muestraDetalleEntity.setEstatus(1);
					
					moviDetalleService.save(muestraDetalleEntity);
					
					//Es para cambiar el estatus a 0 y el catalogo vuelva a coincidir con las cantidades
					try {
						ComercialRackPrenda rack = rackPrendas.get(i);
						System.out.println("si edita el estatus" + rack);
						rack.setEstatus(0);
						rack.setActualizadoPor(auth.getName());
						rack.setUltimaFechaModificacion(dtf.format(now));
						rackService.save(rack);
					}
					catch(Exception e) {
					}
				}
				
			}
			return 1;
		}
		
		//Para crear un nuevo movimiento y sus registros
		catch(Exception e) {
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				ComercialMovimiento comercialEntity = new ComercialMovimiento();
				
				
				comercialEntity.setVendedor(vendedor);
				comercialEntity.setEmpresa(empresa);
				comercialEntity.setEncargado(encargado);
				comercialEntity.setCreadoPor(auth.getName());
				comercialEntity.setActualizadoPor(auth.getName());
				comercialEntity.setFechaCreacion(dtf.format(now));
				comercialEntity.setUltimaFechaModificacion(dtf.format(now));
				comercialEntity.setEstatus("Pendiente de recoger");
				comercialEntity.setIdText(" ");
				movimientoService.save(comercialEntity);
				comercialEntity.setIdText("MOV"+(comercialEntity.getIdMovimiento() +10000));
				movimientoService.save(comercialEntity);
				
				 
				/* lista de estatus en la tabla de muestras
				 * 
				 * 1 = "Pendiente de recoger"
				 * 2 = "Cancelado"
				 * 3 = "Devolución"
				 * 4 = "Entregado a vendedor" con checkBox en la tabla
				 * 5 = "Entregado a vendedor" sin checkBox en la tabla
				 * 6 = "Traspaso" con checkBox en la tabla
				 * 7 = "Traspaso" sin checkBox en la tabla
				 * 8 = "Prestado a empresa" con checkBox en la tabla
				 * 9 = "Prestado a empresa" sin checkBox en la tabla
				 * 10= "Devolución con recargos"
				 **********/
				
				JSONArray muestras = new JSONArray(objectmuestras);
				for (int i = 0; i < muestras.length(); i++) {
					ComercialMovimientoMuestraDetalle muestraDetalleEntity = new ComercialMovimientoMuestraDetalle();
					System.out.println(muestras);
					JSONObject muestra = muestras.getJSONObject(i);
					System.out.println(muestra.get("idmuestra").toString());
					muestraDetalleEntity.setIdDetallePedido(Long.parseLong(muestra.get("idmuestra").toString()));
					muestraDetalleEntity.setCodigoBarras(muestra.getString("codigoBarras").toString());
					muestraDetalleEntity.setIdMovimiento(comercialEntity.getIdMovimiento());
					muestraDetalleEntity.setNombreMuestra(muestra.getString("nombreMuestra").toString());
					muestraDetalleEntity.setModeloPrenda(muestra.getString("idPrenda").toString());
					muestraDetalleEntity.setCodigoTela(muestra.getString("idTela").toString());
					muestraDetalleEntity.setFechaCreacion(dtf.format(now));
					muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
					muestraDetalleEntity.setCreadoPor(auth.getName());
					muestraDetalleEntity.setActualizadoPor(auth.getName());
					muestraDetalleEntity.setEstatus(1);
					
					moviDetalleService.save(muestraDetalleEntity);
				
					ProduccionDetallePedido detallePedido = detallePedidoService.findOne(muestraDetalleEntity.getIdDetallePedido());
					
					detallePedido.setEstatus("2");
					detallePedido.setActualizadoPor(auth.getName());
					detallePedido.setUltimaFechaModificacion(dtf.format(now));
					detallePedidoService.save(detallePedido);
				}
				return 2;
			}catch(Exception p) {
				System.out.println(p);
				return 3;
			}
		}
	
		
		finally {
			System.out.println("Fin de proceso guardarNuevoMovimiento");
		}
	}
	
	@RequestMapping(value="/guardarSolicitudMovimiento", method = RequestMethod.POST)
	public int guardarSolicitudMovimiento (@RequestParam(name="vendedor")String vendedor,
										@RequestParam(name="empresa") String empresa,
										@RequestParam(name="encargado")String encargado,
										@RequestParam(name="idMovimiento", required=false)Long idMovimiento,
										@RequestParam(name="object_muestras") String objectmuestras) {
		
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			ComercialMovimiento comercialEntity = new ComercialMovimiento();
			
			System.out.println(encargado);
			
			comercialEntity.setVendedor(vendedor);
			comercialEntity.setEmpresa(empresa);
			comercialEntity.setEncargado(encargado);
			comercialEntity.setCreadoPor(auth.getName());
			comercialEntity.setActualizadoPor(auth.getName());
			comercialEntity.setFechaCreacion(dtf.format(now));
			comercialEntity.setUltimaFechaModificacion(dtf.format(now));
			comercialEntity.setEstatus("Rack de prendas");
			comercialEntity.setIdText(" ");
			movimientoService.save(comercialEntity);
			comercialEntity.setIdText("SOL"+(comercialEntity.getIdMovimiento() +10000));
			movimientoService.save(comercialEntity);
			
			 
			/* lista de estatus en la tabla de muestras
			 * 
			 * 1 = "Pendiente de recoger"
			 * 2 = "Cancelado"
			 * 3 = "Devolución"
			 * 4 = "Entregado a vendedor" con checkBox en la tabla
			 * 5 = "Entregado a vendedor" sin checkBox en la tabla
			 * 6 = "Traspaso" con checkBox en la tabla
			 * 7 = "Traspaso" sin checkBox en la tabla
			 * 8 = "Prestado a empresa" con checkBox en la tabla
			 * 9 = "Prestado a empresa" sin checkBox en la tabla
			 * 10= "Devolución con recargos"
			 * 11= "Rack de prendas"
			 **********/
				JSONArray muestras = new JSONArray(objectmuestras);
				System.out.println(muestras);
				for (int i = 0; i < muestras.length(); i++) {
					ComercialRackPrenda muestraDetalleEntity = new ComercialRackPrenda();
					JSONObject muestra = muestras.getJSONObject(i);
					System.out.println(muestra.get("idmuestra").toString());
					muestraDetalleEntity.setCantidad(muestra.getLong("cantidad"));
					muestraDetalleEntity.setIdMovimiento(comercialEntity.getIdMovimiento());
					muestraDetalleEntity.setIdPrenda(muestra.getLong("idPrenda"));
					muestraDetalleEntity.setIdTela(muestra.getLong("idTela"));
					muestraDetalleEntity.setFechaCreacion(dtf.format(now));
					muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
					muestraDetalleEntity.setCreadoPor(auth.getName());
					muestraDetalleEntity.setActualizadoPor(auth.getName());
					muestraDetalleEntity.setEstatus(1);
					
					rackService.save(muestraDetalleEntity);
				}
				
//				ProduccionDetallePedido detallePedido = detallePedidoService.findOne(muestraDetalleEntity.getIdDetallePedido());
//				
//				detallePedido.setEstatus("2");
//				detallePedido.setActualizadoPor(auth.getName());
//				detallePedido.setUltimaFechaModificacion(dtf.format(now));
//				detallePedidoService.save(detallePedido);
			
			return 2;
		}catch(Exception p) {
			System.out.println(p);
			return 3;
		}
	}
	
	@RequestMapping(value="/listDetalleMuestras", method = RequestMethod.POST)
	public List<ComercialMovimientoMuestraDetalle> listarDetalleMuestras(@RequestParam(name="idMovi") Long idMovimiento){
		
		try {
			return moviDetalleService.findAllbyMovimientoEstatus(idMovimiento);
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
		finally {
			System.out.println("Fin de proceso listDetalleMuestras");
		}
	}
	
	@RequestMapping(value="/listDetalleMuestrasSolicitud", method = RequestMethod.POST)
	public List<ComercialRackPrenda> listarDetalleMuestrasSolicitud(@RequestParam(name="idMovi") Long idMovimiento){
		
		try {
			return rackService.findAllbyMovimientoEstatus(idMovimiento);
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
		finally {
			System.out.println("Fin de proceso listDetalleMuestras");
		}
	}
	
	
	
	@RequestMapping(value="/entregadoVendedor", method = RequestMethod.POST)
	public void entregadoVendedor(@RequestParam("idMovi") Long idMovimiento) {
		
		try {
			ComercialMovimiento movimientoEntity = movimientoService.findOne(idMovimiento);
			
			
			List<ComercialMovimientoMuestraDetalle> listamuestras = moviDetalleService.findAllbyMovimiento(idMovimiento); 
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			movimientoEntity.setEstatus("Entregado a vendedor");
			movimientoEntity.setFecha_salida(dtf.format(now));
			movimientoService.save(movimientoEntity);
			
			HrEmpleado empleado = empleadoService.findOne(Long.parseLong(movimientoEntity.getVendedor()));
			/* lista de estatus en la tabla de muestras
			 * 
			 * 1 = "Pendiente de recoger"
			 * 2 = "Cancelado"
			 * 3 = "Devolución"
			 * 4 = "Entregado a vendedor" con checkBox en la tabla
			 * 5 = "Entregado a vendedor" sin checkBox en la tabla
			 * 6 = "Traspaso" con checkBox en la tabla
			 * 7 = "Traspaso" sin checkBox en la tabla
			 * 8 = "Prestado a empresa" con checkBox en la tabla
			 * 9 = "Prestado a empresa" sin checkBox en la tabla
			 * 10= "Devolución con recargos"
			 **********/
			
			for (ComercialMovimientoMuestraDetalle muestra: listamuestras) {
				
				ComercialMovimientoMuestraDetalle muestraDetalleEntity = new ComercialMovimientoMuestraDetalle();
				
				muestraDetalleEntity.setIdDetallePedido(muestra.getIdDetallePedido());
				muestraDetalleEntity.setCodigoBarras(muestra.getCodigoBarras());
				muestraDetalleEntity.setIdMovimiento(muestra.getIdMovimiento());
				muestraDetalleEntity.setNombreMuestra(muestra.getNombreMuestra());
				muestraDetalleEntity.setModeloPrenda(muestra.getModeloPrenda());
				muestraDetalleEntity.setCodigoTela(muestra.getCodigoTela());
				muestraDetalleEntity.setFecha_salida(dtf.format(now));
				muestraDetalleEntity.setFechaCreacion(dtf.format(now));
				muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
				muestraDetalleEntity.setCreadoPor(auth.getName());
				muestraDetalleEntity.setActualizadoPor(auth.getName());
				muestraDetalleEntity.setEstatus(4);
				muestraDetalleEntity.setEntregadaPor("Muestrario");
				muestraDetalleEntity.setRecibidaPor(empleado.getApellidoPaterno()==null || 
													empleado.getApellidoMaterno()==null?
													empleado.getNombrePersona():empleado.getNombrePersona()+" "+ empleado.getApellidoPaterno()+" "+empleado.getApellidoMaterno());
				moviDetalleService.save(muestraDetalleEntity);
			}
		
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			System.out.println("Fin de proceso entregadoVendedor");
		}
		
		
		
	}
	
	@RequestMapping(value="/cancelarMovimiento", method = RequestMethod.POST)
	public void cancelado(@RequestParam("idMovi") Long idMovimiento) {
		
		try {
			ComercialMovimiento movimientoEntity = movimientoService.findOne(idMovimiento);
	
			List<ComercialMovimientoMuestraDetalle> listamuestras = moviDetalleService.findAllbyMovimiento(idMovimiento); 
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			movimientoEntity.setEstatus("Cancelado");
			movimientoService.save(movimientoEntity);
			
			/* lista de estatus en la tabla de muestras
			 * 
			 * 1 = "Pendiente de recoger"
			 * 2 = "Cancelado"
			 * 3 = "Devolución"
			 * 4 = "Entregado a vendedor" con checkBox en la tabla
			 * 5 = "Entregado a vendedor" sin checkBox en la tabla
			 * 6 = "Traspaso" con checkBox en la tabla
			 * 7 = "Traspaso" sin checkBox en la tabla
			 * 8 = "Prestado a empresa" con checkBox en la tabla
			 * 9 = "Prestado a empresa" sin checkBox en la tabla
			 * 10= "Devolución con recargos"
			 **********/
			
			for (ComercialMovimientoMuestraDetalle muestra: listamuestras) {
				muestra.setEstatus(2);
				muestra.setActualizadoPor(auth.getName());
				muestra.setUltimaFechaModificacion(dtf.format(now));
				moviDetalleService.save(muestra);
				
				ProduccionDetallePedido detallePedido = detallePedidoService.findOne(muestra.getIdDetallePedido());
				
				detallePedido.setEstatus("1");
				detallePedido.setActualizadoPor(auth.getName());
				detallePedido.setUltimaFechaModificacion(dtf.format(now));
				detallePedidoService.save(detallePedido);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			System.out.println("Fin de proceso cancelarMovimiento");
		}
	}

	@RequestMapping(value="/cancelarSolicitudMovimiento", method = RequestMethod.POST)
	public void cancelarSolicitudMovimiento(@RequestParam("idMovi") Long idMovimiento) {
		
		try {
			ComercialMovimiento movimientoEntity = movimientoService.findOne(idMovimiento);
	
			List<ComercialRackPrenda> listamuestras = rackService.findAllById(idMovimiento); 
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			movimientoEntity.setEstatus("Solicitud cancelada");
			movimientoService.save(movimientoEntity);
			
			for (ComercialRackPrenda muestra: listamuestras) {
				muestra.setEstatus(0);
				muestra.setActualizadoPor(auth.getName());
				muestra.setUltimaFechaModificacion(dtf.format(now));
				rackService.save(muestra);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			System.out.println("Fin de proceso cancelarMovimiento");
		}
	}
	
	
	@RequestMapping(value="/devolverMovimiento", method = RequestMethod.POST)
	public void devolverMovimiento(@RequestParam("idMovi") Long idMovimiento) {
		try {
			ComercialMovimiento movimientoEntity = movimientoService.findOne(idMovimiento);
	
			List<ComercialMovimientoMuestraDetalle> listamuestras = moviDetalleService.findAllbyEstatus(idMovimiento); 
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			movimientoEntity.setEstatus("Devolución");
			movimientoEntity.setFecha_entrega(dtf.format(now));
			movimientoService.save(movimientoEntity);
			
			/* lista de estatus en la tabla de muestras
			 * 
			 * 1 = "Pendiente de recoger"
			 * 2 = "Cancelado"
			 * 3 = "Devolución"
			 * 4 = "Entregado a vendedor" con checkBox en la tabla
			 * 5 = "Entregado a vendedor" sin checkBox en la tabla
			 * 6 = "Traspaso" con checkBox en la tabla
			 * 7 = "Traspaso" sin checkBox en la tabla
			 * 8 = "Prestado a empresa" con checkBox en la tabla
			 * 9 = "Prestado a empresa" sin checkBox en la tabla
			 *10 = "Devolución con recargos"
			 **********/
			for (ComercialMovimientoMuestraDetalle muestra: listamuestras) {
				ComercialMovimientoMuestraDetalle muestraDetalleEntity = new ComercialMovimientoMuestraDetalle();
				
				muestra.setEstatus(muestra.getEstatus()+1);
				
				moviDetalleService.save(muestra);
				
				muestraDetalleEntity.setIdDetallePedido(muestra.getIdDetallePedido());
				muestraDetalleEntity.setCodigoBarras(muestra.getCodigoBarras());
				muestraDetalleEntity.setIdMovimiento(muestra.getIdMovimiento());
				muestraDetalleEntity.setNombreMuestra(muestra.getNombreMuestra());
				muestraDetalleEntity.setModeloPrenda(muestra.getModeloPrenda());
				muestraDetalleEntity.setCodigoTela(muestra.getCodigoTela());
				muestraDetalleEntity.setActualizadoPor(auth.getName());
				muestraDetalleEntity.setFecha_salida(dtf.format(now));
				muestraDetalleEntity.setFecha_devolucion(dtf.format(now));
				muestraDetalleEntity.setEntregadaPor(muestra.getRecibidaPor());
				muestraDetalleEntity.setRecibidaPor("Muestrario");
				muestraDetalleEntity.setFechaCreacion(dtf.format(now));
				muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
				muestraDetalleEntity.setCreadoPor(auth.getName());
				muestraDetalleEntity.setEstatus(3);
				
				moviDetalleService.save(muestraDetalleEntity);
				
				ProduccionDetallePedido detallePedido = detallePedidoService.findOne(muestraDetalleEntity.getIdDetallePedido());
				
				detallePedido.setEstatus("1");
				detallePedido.setActualizadoPor(auth.getName());
				detallePedido.setUltimaFechaModificacion(dtf.format(now));
				detallePedidoService.save(detallePedido);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			System.out.println("Fin de proceso devolverMovimiento");
		}
	}
	
	
	@RequestMapping(value="/devolverIndividual", method = RequestMethod.POST)
	public void devolverIndividual(@RequestParam("idMuestras")String muestrasDevolver,
								   @RequestParam("movi")String idMovimiento) {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String[] listaids;
			listaids = muestrasDevolver.split(",");
			ComercialMovimiento movimientoEntity = movimientoService.findOne(Long.parseLong(idMovimiento));
			
			for (String i :listaids) {
				ComercialMovimientoMuestraDetalle muestra = moviDetalleService.findOne(Long.parseLong(i));
				
				muestra.setEstatus(muestra.getEstatus()+1);
				moviDetalleService.save(muestra);
				
				
				ComercialMovimientoMuestraDetalle muestraDetalleEntity = new ComercialMovimientoMuestraDetalle();
				
				muestraDetalleEntity.setIdDetallePedido(muestra.getIdDetallePedido());
				muestraDetalleEntity.setCodigoBarras(muestra.getCodigoBarras());
				muestraDetalleEntity.setIdMovimiento(muestra.getIdMovimiento());
				muestraDetalleEntity.setNombreMuestra(muestra.getNombreMuestra());
				muestraDetalleEntity.setModeloPrenda(muestra.getModeloPrenda());
				muestraDetalleEntity.setCodigoTela(muestra.getCodigoTela());
				muestraDetalleEntity.setActualizadoPor(auth.getName());
				muestraDetalleEntity.setFecha_salida(dtf.format(now));
				muestraDetalleEntity.setFecha_devolucion(dtf.format(now));
				muestraDetalleEntity.setEntregadaPor(muestra.getRecibidaPor());
				muestraDetalleEntity.setRecibidaPor("Muestrario");
				muestraDetalleEntity.setFechaCreacion(dtf.format(now));
				muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
				muestraDetalleEntity.setCreadoPor(auth.getName());
				muestraDetalleEntity.setEstatus(3);
				moviDetalleService.save(muestraDetalleEntity);
				
				ProduccionDetallePedido detallePedido = detallePedidoService.findOne(muestraDetalleEntity.getIdDetallePedido());
				
				detallePedido.setEstatus("1");
				detallePedido.setActualizadoPor(auth.getName());
				detallePedido.setUltimaFechaModificacion(dtf.format(now));
				detallePedidoService.save(detallePedido);
				
			}
			
			if(moviDetalleService.ifExistCheckBox(Long.parseLong(idMovimiento)).equals("1")) {
				
				movimientoEntity.setEstatus("Devolución parcial");
				movimientoService.save(movimientoEntity);
			}	else{
				movimientoEntity.setEstatus("Devolución");
				movimientoEntity.setFecha_entrega(dtf.format(now));
				movimientoService.save(movimientoEntity);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			System.out.println("Fin de proceso devolverIndividual");
		}
		
	}
	
	@RequestMapping(value="/prestamoEmpresa", method = RequestMethod.POST)
	public void prestadoEmpresa(@RequestParam("idMuestras")String muestrasDevolver,
								@RequestParam("movi")String idMovimiento) {
		
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			String[] listaids;
			listaids = muestrasDevolver.split(",");
			
			ComercialMovimiento movimientoEntity = movimientoService.findOne(Long.parseLong(idMovimiento));
			ComercialCliente clienteEmpresa = clienteService.findOne(Long.parseLong(movimientoEntity.getEmpresa()));
			for (String i :listaids) {
				ComercialMovimientoMuestraDetalle muestra = moviDetalleService.findOne(Long.parseLong(i));
				
				muestra.setEstatus(muestra.getEstatus()+1);
				moviDetalleService.save(muestra);
				
				
				ComercialMovimientoMuestraDetalle muestraDetalleEntity = new ComercialMovimientoMuestraDetalle();
				
				
				muestraDetalleEntity.setIdDetallePedido(muestra.getIdDetallePedido());
				muestraDetalleEntity.setCodigoBarras(muestra.getCodigoBarras());
				muestraDetalleEntity.setIdMovimiento(muestra.getIdMovimiento());
				muestraDetalleEntity.setNombreMuestra(muestra.getNombreMuestra());
				muestraDetalleEntity.setModeloPrenda(muestra.getModeloPrenda());
				muestraDetalleEntity.setCodigoTela(muestra.getCodigoTela());
				muestraDetalleEntity.setActualizadoPor(auth.getName());
				muestraDetalleEntity.setFecha_salida(dtf.format(now));
				muestraDetalleEntity.setEntregadaPor(muestra.getRecibidaPor());
				muestraDetalleEntity.setRecibidaPor(clienteEmpresa.getApellidoPaterno() == null || 
													clienteEmpresa.getApellidoMaterno() == null?clienteEmpresa.getNombre():
													clienteEmpresa.getNombre()+" "+clienteEmpresa.getApellidoPaterno()+" "+clienteEmpresa.getApellidoMaterno());
				muestraDetalleEntity.setFechaCreacion(dtf.format(now));
				muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
				muestraDetalleEntity.setCreadoPor(auth.getName());
				muestraDetalleEntity.setEstatus(8);
				moviDetalleService.save(muestraDetalleEntity);
				
			}
				if(moviDetalleService.ifExistCheckBox(Long.parseLong(idMovimiento)).equals("1")) {
				
				movimientoEntity.setEstatus("Prestamo empresa");
				movimientoService.save(movimientoEntity);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			System.out.println("Fin de proceso prestamoEmpresa");
		}	
	}
	
	@RequestMapping(value="/traspasoSolicitud", method = RequestMethod.POST)
	public int traspasoSolicitud(@RequestParam("idMuestras")String muestrasDevolver,
								  @RequestParam("movimiento")String idMovimiento,
								  @RequestParam("codigoTraspaso")Long codigoTraspaso,
								  @RequestParam("object_muestras")String objectmuestras) {
		
		try {
			ComercialTokenTraspaso token = moviDetalleService.findCode(codigoTraspaso);
			moviDetalleService.removeToken(token);
			
			try {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				String[] listaids;
				listaids = muestrasDevolver.split(",");
				ComercialMovimientoMuestraDetalle muestrita = new ComercialMovimientoMuestraDetalle();
				ComercialMovimiento movimientoEntity = movimientoService.findOne(Long.parseLong(idMovimiento));
				HrEmpleado nombrevendedor = empleadoService.findOne(token.getIdAgente());
				
				for (String i :listaids) {
					muestrita = moviDetalleService.FindMuestraByPedido(Long.parseLong(idMovimiento),Long.parseLong(i));
					
					muestrita.setEstatus(muestrita.getEstatus()+1);
					moviDetalleService.save(muestrita);
		
					ComercialMovimientoMuestraDetalle muestraDetalleEntity = new ComercialMovimientoMuestraDetalle();
					
					muestraDetalleEntity.setIdDetallePedido(muestrita.getIdDetallePedido());
					muestraDetalleEntity.setCodigoBarras(muestrita.getCodigoBarras());
					muestraDetalleEntity.setIdMovimiento(muestrita.getIdMovimiento());
					muestraDetalleEntity.setNombreMuestra(muestrita.getNombreMuestra());
					muestraDetalleEntity.setModeloPrenda(muestrita.getModeloPrenda());
					muestraDetalleEntity.setCodigoTela(muestrita.getCodigoTela());
					muestraDetalleEntity.setActualizadoPor(auth.getName());
					muestraDetalleEntity.setFecha_salida(dtf.format(now));
					muestraDetalleEntity.setEntregadaPor(muestrita.getRecibidaPor());
					muestraDetalleEntity.setRecibidaPor(nombrevendedor.getApellidoPaterno()==null || 
														nombrevendedor.getApellidoMaterno()==null?
														nombrevendedor.getNombrePersona():nombrevendedor.getNombrePersona()+" "+ nombrevendedor.getApellidoPaterno()+" "+nombrevendedor.getApellidoMaterno());
					muestraDetalleEntity.setFechaCreacion(dtf.format(now));
					muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
					muestraDetalleEntity.setCreadoPor(auth.getName());
					muestraDetalleEntity.setEstatus(11);
					moviDetalleService.save(muestraDetalleEntity);
					
					
				}
				
				if(moviDetalleService.ifExistCheckBox(muestrita.getIdMovimiento()).equals("1")) {
					
					movimientoEntity.setEstatus("Traspaso");
					movimientoEntity.setFecha_entrega(dtf.format(now));
					movimientoService.save(movimientoEntity);
				}	else {
					movimientoEntity.setEstatus("Traspasado");
					movimientoEntity.setFecha_entrega(dtf.format(now));
					movimientoService.save(movimientoEntity);
				}
				
				HrEmpleado empleado = empleadoService.findOne(Long.parseLong(movimientoEntity.getVendedor()));
				
				ComercialMovimiento comercialEntity = new ComercialMovimiento();
				
				
				comercialEntity.setVendedor(token.getIdAgente().toString());
				comercialEntity.setEmpresa(token.getIdEmpresa().toString());
				comercialEntity.setEncargado(empleado.getApellidoPaterno()==null || 
											 empleado.getApellidoMaterno()==null?
											 empleado.getNombrePersona():empleado.getNombrePersona()+" "+ empleado.getApellidoPaterno()+" "+empleado.getApellidoMaterno());
				comercialEntity.setCreadoPor(auth.getName());
				comercialEntity.setActualizadoPor(auth.getName());
				comercialEntity.setFechaCreacion(dtf.format(now));
				comercialEntity.setFecha_salida(dtf.format(now));
				comercialEntity.setUltimaFechaModificacion(dtf.format(now));
				comercialEntity.setEstatus("Traspaso");
				comercialEntity.setIdText(" ");
				movimientoService.save(comercialEntity);
				comercialEntity.setIdText("MOV"+(comercialEntity.getIdMovimiento() +10000));
				movimientoService.save(comercialEntity);
				
				 
				/* lista de estatus en la tabla de muestras
				 * 
				 * 1 = "Pendiente de recoger"
				 * 2 = "Cancelado"
				 * 3 = "Devolución"
				 * 4 = "Entregado a vendedor" con checkBox en la tabla
				 * 5 = "Entregado a vendedor" sin checkBox en la tabla
				 * 6 = "Traspaso" con checkBox en la tabla
				 * 7 = "Traspaso" sin checkBox en la tabla
				 * 8 = "Prestado a empresa" con checkBox en la tabla
				 * 9 = "Prestado a empresa" sin checkBox en la tabla
				 * 10= "Devolución con recargos"
				 * 11= "Traspasado"
				 **********/
				
				JSONArray muestras = new JSONArray(objectmuestras);
				for (int i = 0; i < muestras.length(); i++) {
					ComercialMovimientoMuestraDetalle muestraDetalleEntity = new ComercialMovimientoMuestraDetalle();
					System.out.println(muestras);
					JSONObject muestra = muestras.getJSONObject(i);
					System.out.println(muestra.get("idmuestra").toString());
					muestraDetalleEntity.setIdDetallePedido(Long.parseLong(muestra.get("idmuestra").toString()));
					muestraDetalleEntity.setCodigoBarras(muestra.getString("codigoBarras").toString());
					muestraDetalleEntity.setIdMovimiento(comercialEntity.getIdMovimiento());
					muestraDetalleEntity.setNombreMuestra(muestra.getString("nombreMuestra").toString());
					muestraDetalleEntity.setModeloPrenda(muestra.getString("idPrenda").toString());
					muestraDetalleEntity.setCodigoTela(muestra.getString("idTela").toString());
					muestraDetalleEntity.setEntregadaPor(empleado.getApellidoPaterno()==null || 
							 							 empleado.getApellidoMaterno()==null?
							 							 empleado.getNombrePersona():empleado.getNombrePersona()+" "+ empleado.getApellidoPaterno()+" "+empleado.getApellidoMaterno());
					muestraDetalleEntity.setRecibidaPor(nombrevendedor.getApellidoPaterno()==null || 
														nombrevendedor.getApellidoMaterno()==null?
														nombrevendedor.getNombrePersona():nombrevendedor.getNombrePersona()+" "+ nombrevendedor.getApellidoPaterno()+" "+nombrevendedor.getApellidoMaterno());
					muestraDetalleEntity.setFecha_salida(dtf.format(now));
					muestraDetalleEntity.setFechaCreacion(dtf.format(now));
					muestraDetalleEntity.setUltimaFechaModificacion(dtf.format(now));
					muestraDetalleEntity.setCreadoPor(auth.getName());
					muestraDetalleEntity.setActualizadoPor(auth.getName());
					muestraDetalleEntity.setEstatus(6);
					
					System.out.println(muestras);
					moviDetalleService.save(muestraDetalleEntity);
				
				
				}
			}catch(Exception e) {
				System.out.println(e);
			}
			finally {
				System.out.println("Fin de proceso traspasoSolicitud");
			}
			return 1;
		}
		catch(Exception e) {
			return 2;
		}
	}
	
	@RequestMapping(value="/codigoTraspasoSolicitud", method = RequestMethod.POST)
	public int codigoTraspasoSolicitud(@RequestParam("codigoTraspaso")Long codigoTraspaso,
									   @RequestParam("empresaTraspaso")Long empresaTraspaso) {
	System.out.println(codigoTraspaso);
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
			Long idAgente = Long.parseLong(empleado[0].toString());
			
			ComercialTokenTraspaso token = new ComercialTokenTraspaso();
			token.setCodigoTraspaso(codigoTraspaso);
			token.setIdAgente(idAgente);
			token.setIdEmpresa(empresaTraspaso);
			moviDetalleService.saveToken(token);
			
			return 1; 
		}
		catch(Exception e) {
			return 2;
		}
		finally {
			
		}
	
	}
	
	
	@RequestMapping(value="/datosMovimiento", method = RequestMethod.POST)
	public List<Object> datosMovimiento(@RequestParam("movimiento")String idMovimiento) {
	
		return movimientoService.datosMovimiento(Long.parseLong(idMovimiento));
	
	}
	
	@RequestMapping(value="/datosMovimientoSolicitud", method = RequestMethod.POST)
	public List<Object> datosMovimientoSolicitud(@RequestParam("movimiento")String idMovimiento) {
	
		return rackService.datosMovimiento(Long.parseLong(idMovimiento));
	
	}
}
