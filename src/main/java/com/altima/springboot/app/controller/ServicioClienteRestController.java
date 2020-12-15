package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlCliente;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteAuxiliarVentas;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteCorrida;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteMaterial;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteSastre;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteAuxiliarVentasService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteCorridaService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteMaterialService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteSastreService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteService;
import com.altima.springboot.app.models.service.IHrDireccionService;

@RestController
public class ServicioClienteRestController {
	
	@Autowired
	private ICargaPedidoService cargaPedidoService;
	@Autowired
	private IComercialClienteService ClienteService;
	@Autowired
	private IHrDireccionService DireccionService;
	@Autowired
	private IComercialSolicitudServicioAlClienteService solicitudServicioClienteService;
	@Autowired
	private IComercialSolicitudServicioAlClienteSastreService solicitudServicioClienteSastreService;
	@Autowired
	private IComercialSolicitudServicioAlClienteAuxiliarVentasService solicitudServicioClienteAuxiliarVentasService;
	@Autowired
	private IComercialSolicitudServicioAlClienteMaterialService solicitudServicioClienteMaterialService;
	@Autowired
	private IComercialSolicitudServicioAlClienteCorridaService solicitudServicioClienteCorridaService;
	@Autowired 
	private IHrDireccionService direciconSercice;
	
	@RequestMapping(value = "/get_datos_de_cliente", method = RequestMethod.GET)
	public String[] getDatosDeCliente(@RequestParam(name = "id") Long id) {
		String[] response = new String[4];
		HrDireccion direccion = DireccionService.findOne(Long.valueOf(ClienteService.findOne(id).getIdDireccion()));
		response[0] = direccion.getCalle() + ", #" + direccion.getNumeroExt() + ". Colonia: " + direccion.getColonia() + ". " + direccion.getMunicipio() + ", " + direccion.getEstado() + ". CP:" + direccion.getCodigoPostal();
		response[1] = ClienteService.findOne(id).getTelefono();
		response[2] = ClienteService.findOne(id).getNombreContacto();
		return response;
	}
	
	@RequestMapping(value = "/get_pedidos_de_cliente", method = RequestMethod.GET)
	public List<Object[]> getPedidosDeCliente(@RequestParam(name = "id") Long id) {
		List<Object[]> response = solicitudServicioClienteService.pedidosDeCliente(id);
		return response;
	}

	@RequestMapping(value = "/get_sucursal_direccion", method = RequestMethod.GET)
	public List<Object[]> sucursalDireccion (@RequestParam(name = "id") Long id){
		return solicitudServicioClienteService.direccionesSucursales(id);
	}

	@RequestMapping(value = "/get_sucursal_direccion2", method = RequestMethod.GET)
	public HrDireccion sucursalDireccion2 (@RequestParam(name = "id") Long id){
		return direciconSercice.findOne(id);
	}

	@RequestMapping(value = "/get_tipo_corrida_por_genero", method = RequestMethod.GET)
	public List<String> tiposPorGenero (@RequestParam(name = "id") Long id, @RequestParam (name="genero") String genero){
		return solicitudServicioClienteCorridaService.devolverSelectCorridasTipo(id, genero);
	}
	
	@RequestMapping(value = "/save_solicitud_servicio_cliente", method = RequestMethod.POST)
	public ComercialSolicitudServicioAlCliente saveSolicitudServicioAlCliente(@RequestParam(name = "Solicitud") String solicitud, Long idSolicitud) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		DateTimeFormatter fechaSinHora = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		JSONObject solicitudObjeto = new JSONObject(solicitud.toString());
		if ( idSolicitud==null){
			ComercialSolicitudServicioAlCliente ssc = new ComercialSolicitudServicioAlCliente();;
			ssc.setIdText(" ");
			if (solicitudObjeto.get("idPedido").toString().equals("0") ){
				ssc.setIdPedidoInformacion(null);
			}else{
				ssc.setIdPedidoInformacion(Long.valueOf(solicitudObjeto.get("idPedido").toString()));
			}
			
			ssc.setIdCliente(Long.valueOf(solicitudObjeto.get("clienteID").toString()));
			ssc.setFechaHoraDeCita(solicitudObjeto.get("fechaCita").toString());
			ssc.setHoraSalidaAltima(solicitudObjeto.get("fechaSalida").toString());
			ssc.setActividad(solicitudObjeto.get("actividadCita").toString());
			ssc.setCaballerosPorAtender(Long.valueOf(solicitudObjeto.get("caballerosAtender").toString()));
			ssc.setDamasPorAtender(Long.valueOf(solicitudObjeto.get("damasAtender").toString()));
			ssc.setComentarios(solicitudObjeto.get("comentarios").toString());
			ssc.setIdSucrsal(solicitudObjeto.get("sucur").toString());
			ssc.setDirigirseCon(solicitudObjeto.get("dirigirse").toString());
			ssc.setCreadoPor(auth.getName());
			ssc.setActualizadoPor(auth.getName());
			ssc.setFechaCreacion(fechaConHora.format(now));
			ssc.setUltimaFechaModificacion(fechaConHora.format(now));
			ssc.setCalle(solicitudObjeto.get("calle").toString());
			if ( solicitudObjeto.get("NumeroExt").toString().equals("") ){
				ssc.setNumeroExt("S/N");
			}else{
				ssc.setNumeroExt(solicitudObjeto.get("NumeroExt").toString());
			}
			ssc.setNumeroInt(solicitudObjeto.get("NumeroInt").toString());
			ssc.setEstado(solicitudObjeto.get("estado").toString());
			ssc.setMunicipio(solicitudObjeto.get("municipio").toString());
			ssc.setColonia(solicitudObjeto.get("colonia").toString());
			ssc.setCodigoPostal(solicitudObjeto.get("codigoPostal").toString());
			ssc.setTelefonoCita(solicitudObjeto.get("telefono").toString());
			ssc.setEstatus("0");
			solicitudServicioClienteService.save(ssc);
			
			ssc.setIdText("SOLSER" + (10000 + ssc.getIdSolicitudServicioAlCliente()));
			solicitudServicioClienteService.save(ssc);
			return ssc;
		}else{
			ComercialSolicitudServicioAlCliente ssc2 = solicitudServicioClienteService.findOne(idSolicitud);
			if (solicitudObjeto.get("idPedido").toString().equals("0") ){
				ssc2.setIdPedidoInformacion(null);
			}else{
				ssc2.setIdPedidoInformacion(Long.valueOf(solicitudObjeto.get("idPedido").toString()));
			}
			
			ssc2.setIdCliente(Long.valueOf(solicitudObjeto.get("clienteID").toString()));
			ssc2.setFechaHoraDeCita(solicitudObjeto.get("fechaCita").toString());
			ssc2.setHoraSalidaAltima(solicitudObjeto.get("fechaSalida").toString());
			ssc2.setActividad(solicitudObjeto.get("actividadCita").toString());
			ssc2.setCaballerosPorAtender(Long.valueOf(solicitudObjeto.get("caballerosAtender").toString()));
			ssc2.setDamasPorAtender(Long.valueOf(solicitudObjeto.get("damasAtender").toString()));
			ssc2.setComentarios(solicitudObjeto.get("comentarios").toString());
			
			ssc2.setIdSucrsal(solicitudObjeto.get("sucur").toString());
			ssc2.setCalle(solicitudObjeto.get("calle").toString());
			if ( solicitudObjeto.get("NumeroExt").toString().equals("") ){
				ssc2.setNumeroExt("S/N");
			}else{
				ssc2.setNumeroExt(solicitudObjeto.get("NumeroExt").toString());
			}
			ssc2.setNumeroInt(solicitudObjeto.get("NumeroInt").toString());
			ssc2.setEstado(solicitudObjeto.get("estado").toString());
			ssc2.setMunicipio(solicitudObjeto.get("municipio").toString());
			ssc2.setColonia(solicitudObjeto.get("colonia").toString());
			ssc2.setCodigoPostal(solicitudObjeto.get("codigoPostal").toString());
			ssc2.setTelefonoCita(solicitudObjeto.get("telefono").toString());
			ssc2.setTelefonoCita(solicitudObjeto.get("telefono").toString());
			ssc2.setDirigirseCon(solicitudObjeto.get("dirigirse").toString());
			ssc2.setActualizadoPor(auth.getName());
			ssc2.setUltimaFechaModificacion(fechaConHora.format(now));
			solicitudServicioClienteService.save(ssc2);
			return ssc2;
		}
		
		
	}
	
	@RequestMapping(value = "/save_servicio_cliente_sastre", method = RequestMethod.POST)
	public ComercialSolicitudServicioAlClienteSastre saveServicioAlClienteSastre(@RequestParam(name = "genero") String genero, @RequestParam(name = "cantidad") Long cantidad, @RequestParam(name = "idSolicitud") Long idSolicitud) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ComercialSolicitudServicioAlClienteSastre sastre = new ComercialSolicitudServicioAlClienteSastre();
		sastre.setGenero(genero);
		sastre.setCantidad(cantidad);
		sastre.setCreadoPor(auth.getName());
		sastre.setIdSolicitudServicioAlCliente(idSolicitud);
		sastre.setActualizadoPor(auth.getName());
		sastre.setFechaCreacion(fechaConHora.format(now));
		sastre.setUltimaFechaModificacion(fechaConHora.format(now));
		sastre.setEstatus("1");
		sastre.setIdText(" ");
		solicitudServicioClienteSastreService.save(sastre);
		sastre.setIdText("SOLSAS" + (10000 + sastre.getIdSolicitudServicioAlClienteSastre()));
		solicitudServicioClienteSastreService.save(sastre);
		return sastre;
	}
	
	@RequestMapping(value = "/save_servicio_cliente_auxiliar_ventas", method = RequestMethod.POST)
	public ComercialSolicitudServicioAlClienteAuxiliarVentas saveServicioAlClienteAuxiliarVentas(@RequestParam(name = "genero") String genero, @RequestParam(name = "cantidad") Long cantidad, @RequestParam(name = "idSolicitud") Long idSolicitud) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ComercialSolicitudServicioAlClienteAuxiliarVentas auxiliarVentas = new ComercialSolicitudServicioAlClienteAuxiliarVentas();
		auxiliarVentas.setGenero(genero);
		auxiliarVentas.setCantidad(cantidad);
		auxiliarVentas.setCreadoPor(auth.getName());
		auxiliarVentas.setIdSolicitudServicioAlCliente(idSolicitud);
		auxiliarVentas.setActualizadoPor(auth.getName());
		auxiliarVentas.setFechaCreacion(fechaConHora.format(now));
		auxiliarVentas.setUltimaFechaModificacion(fechaConHora.format(now));
		auxiliarVentas.setEstatus("1");
		auxiliarVentas.setIdText(" ");
		solicitudServicioClienteAuxiliarVentasService.save(auxiliarVentas);
		auxiliarVentas.setIdText("SOLAUX" + (10000 + auxiliarVentas.getIdSolicitudServicioAlClienteAuxiliarVentas()));
		solicitudServicioClienteAuxiliarVentasService.save(auxiliarVentas);
		return auxiliarVentas;
	}

	
	@RequestMapping(value = "/validarMaterial", method = RequestMethod.GET)
	public Integer  validarMaterial(Long id , String material) {
		return solicitudServicioClienteMaterialService.buscarMaterial(id, material);
	}
	
	@RequestMapping(value = "/save_servicio_cliente_material", method = RequestMethod.POST)
	public List<String> saveServicioAlClienteMaterial(@RequestParam(name = "material") Long mat, @RequestParam(name = "cantidad") Long cantidad, @RequestParam(name = "idSolicitud") Long idSolicitud) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ComercialSolicitudServicioAlClienteMaterial material = new ComercialSolicitudServicioAlClienteMaterial();
		material.setIdLookup(mat);
		material.setCantidad(cantidad);
		material.setIdSolicitudServicioAlCliente(idSolicitud);
		material.setCreadoPor(auth.getName());
		material.setActualizadoPor(auth.getName());
		material.setFechaCreacion(fechaConHora.format(now));
		material.setUltimaFechaModificacion(fechaConHora.format(now));
		material.setEstatus("1");
		solicitudServicioClienteMaterialService.save(material);
		List<String> listaMaestra = new ArrayList<String>();
		listaMaestra.add(""+material.getIdSolicitudServicioAlClienteMaterial());
		listaMaestra.add(solicitudServicioClienteMaterialService.nombreMaterial(material.getIdLookup()));
		listaMaestra.add(""+material.getCantidad());
		return listaMaestra;
	}
	
	@RequestMapping(value = "/save_servicio_cliente_corrida", method = RequestMethod.POST)
	public ComercialSolicitudServicioAlClienteCorrida saveServicioAlClienteCorrida(@RequestParam(name = "genero") String genero, @RequestParam(name = "tipo") String tipo, @RequestParam(name = "idSolicitud") Long idSolicitud, @RequestParam(name = "cantidad") Integer cantidad) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ComercialSolicitudServicioAlClienteCorrida corrida = new ComercialSolicitudServicioAlClienteCorrida();
		corrida.setIdSolicitudServicioAlCliente(0L);
		corrida.setGenero(genero);
		corrida.setTipo(tipo);
		corrida.setIdSolicitudServicioAlCliente(idSolicitud);
		corrida.setCreadoPor(auth.getName());
		corrida.setActualizadoPor(auth.getName());
		corrida.setFechaCreacion(fechaConHora.format(now));
		corrida.setUltimaFechaModificacion(fechaConHora.format(now));
		corrida.setEstatus("1");
		corrida.setIdText(" ");
		corrida.setCantidad(cantidad);
		solicitudServicioClienteCorridaService.save(corrida);
		corrida.setIdText("SOLCOR" + (10000 + corrida.getIdSolicitudServicioAlClienteCorrida()));
		solicitudServicioClienteCorridaService.save(corrida);
		return corrida;
	}
	
	//Este metodo se usa para todas las tablas relacionadas a solicitud
	@RequestMapping(value = "/delete_elemento_from_solicitud", method = RequestMethod.DELETE)
	public String deleteElementofromSolicitud(@RequestParam(name = "id") Long id, @RequestParam(name = "entidad") String entidad) {
		switch(entidad) {
			case "Sastre":
				solicitudServicioClienteSastreService.delete(id);
				break;
			case "AuxiliarVentas":
				solicitudServicioClienteAuxiliarVentasService.delete(id);
				break;
			case "Material":
				solicitudServicioClienteMaterialService.delete(id);
				break;
			case "Corrida":
				solicitudServicioClienteCorridaService.delete(id);
				break;
		}
		return "ok";
	}
	
	//Este metodo se usa para actualizar los selects
	@RequestMapping(value = "/get_nuevos_selects", method = RequestMethod.GET)
	public List<Object> actualizarSelects(@RequestParam(name = "idSolicitud") Long idSolicitud){
		List<Object> listaMaestra = new ArrayList<Object>();
			
		List<String> sastresSelect = solicitudServicioClienteSastreService.devolverSelectSastre(idSolicitud);
		List<String> auxiliaresSelect = solicitudServicioClienteAuxiliarVentasService.devolverSelectAuxiliarVentas(idSolicitud);
		List<String> corridasSelect = solicitudServicioClienteCorridaService.devolverSelectCorridas(idSolicitud);
		List<Object[]> materialesSelect = solicitudServicioClienteService.devolverSelectMateriales(idSolicitud);
		
		listaMaestra.add(sastresSelect);
		listaMaestra.add(auxiliaresSelect);
		listaMaestra.add(materialesSelect);
		listaMaestra.add(corridasSelect);
		
		return listaMaestra;
	}

	@RequestMapping(value = "/enviar_servicio_cliente", method = RequestMethod.POST)
	public boolean enviar (@RequestParam(name = "id") Long id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ComercialSolicitudServicioAlCliente ssc = solicitudServicioClienteService.findOne(id);
		ssc.setEstatus("1");
		ssc.setActualizadoPor(auth.getName());
		ssc.setUltimaFechaModificacion(fechaConHora.format(now));
		solicitudServicioClienteService.save(ssc);
		return true;
	}

	@RequestMapping(value = "/aceptar_servicio_cliente", method = RequestMethod.POST)
	public boolean aceptar (@RequestParam(name = "id") Long id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ComercialSolicitudServicioAlCliente ssc = solicitudServicioClienteService.findOne(id);
		ssc.setEstatus("2");
		ssc.setActualizadoPor(auth.getName());
		ssc.setUltimaFechaModificacion(fechaConHora.format(now));
		solicitudServicioClienteService.save(ssc);
		return true;
	}

	@RequestMapping(value = "/rechazar_servicio_cliente", method = RequestMethod.POST)
	public boolean rechazar (@RequestParam(name = "id") Long id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ComercialSolicitudServicioAlCliente ssc = solicitudServicioClienteService.findOne(id);
		ssc.setEstatus("3");
		ssc.setActualizadoPor(auth.getName());
		ssc.setUltimaFechaModificacion(fechaConHora.format(now));
		solicitudServicioClienteService.save(ssc);
		return true;
	}
	
}
