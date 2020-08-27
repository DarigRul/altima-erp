package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
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
	
	@RequestMapping(value = "/save_solicitud_servicio_cliente", method = RequestMethod.POST)
	public ComercialSolicitudServicioAlCliente saveSolicitudServicioAlCliente(@RequestParam(name = "Solicitud") String solicitud) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter fechaConHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		DateTimeFormatter fechaSinHora = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		JSONObject solicitudObjeto = new JSONObject(solicitud.toString());
		ComercialSolicitudServicioAlCliente ssc = new ComercialSolicitudServicioAlCliente();
		
		ssc.setIdText(" ");
		ssc.setIdPedidoInformacion(Long.valueOf(solicitudObjeto.get("idPedido").toString()));
		ssc.setIdCliente(Long.valueOf(solicitudObjeto.get("clienteID").toString()));
		ssc.setFechaHoraDeCita(solicitudObjeto.get("fechaSalida").toString());
		ssc.setHoraSalidaAltima(solicitudObjeto.get("fechaCita").toString());
		ssc.setActividad(solicitudObjeto.get("actividadCita").toString());
		ssc.setCaballerosPorAtender(Long.valueOf(solicitudObjeto.get("caballerosAtender").toString()));
		ssc.setDamasPorAtender(Long.valueOf(solicitudObjeto.get("damasAtender").toString()));
		ssc.setComentarios(solicitudObjeto.get("comentarios").toString());
		ssc.setCreadoPor(auth.getName());
		ssc.setActualizadoPor(auth.getName());
		ssc.setFechaCreacion(fechaConHora.format(now));
		ssc.setUltimaFechaModificacion(fechaConHora.format(now));
		ssc.setEstatus("1");
		
		solicitudServicioClienteService.save(ssc);
		
		ssc.setIdText("SOLSER" + (10000 + ssc.getIdSolicitudServicioAlCliente()));
		solicitudServicioClienteService.save(ssc);
		
		return ssc;
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
	
	@RequestMapping(value = "/save_servicio_cliente_material", method = RequestMethod.POST)
	public ComercialSolicitudServicioAlClienteMaterial saveServicioAlClienteMaterial(@RequestParam(name = "material") Long mat, @RequestParam(name = "cantidad") Long cantidad, @RequestParam(name = "idSolicitud") Long idSolicitud) {
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
		material.setIdText(" ");
		solicitudServicioClienteMaterialService.save(material);
		material.setIdText("SOLMAT" + (10000 + material.getIdSolicitudServicioAlClienteMaterial()));
		solicitudServicioClienteMaterialService.save(material);
		return material;
	}
	
	@RequestMapping(value = "/save_servicio_cliente_corrida", method = RequestMethod.POST)
	public ComercialSolicitudServicioAlClienteCorrida saveServicioAlClienteCorrida(@RequestParam(name = "genero") String genero, @RequestParam(name = "tipo") String tipo, @RequestParam(name = "idSolicitud") Long idSolicitud) {
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
	
}
