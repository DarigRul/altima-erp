package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoForro;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoTela;
import com.altima.springboot.app.models.entity.ProduccionSolicitudCambioTelaPedido;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IProduccionSolicitudCambioTelaPedidoService;

@RestController
public class CambioTelaRestController {
	
	@Autowired
	private IProduccionSolicitudCambioTelaPedidoService CambioTelaService;
	
	@Autowired
	private IComercialCoordinadoService CoordinadoService;
	
	@GetMapping("/listar-pedidos-cerrados")
    public List<Object []> getComercialLookupByTipo(){
        return CambioTelaService.pedidosCerrados();
    }
    
    @GetMapping("/info-pedido")
    public List<Object []> buscarPedido (Long id) {
    	return CambioTelaService.infPedido(id);
    }
    
    @PostMapping("/guardar-solicitud-tela")
    public boolean buscarPedido (Long idPedido, String motivo) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ProduccionSolicitudCambioTelaPedido cambio = new ProduccionSolicitudCambioTelaPedido();
		cambio.setId_pedido(idPedido);
		cambio.setIdText("Cambio");
		cambio.setMotivo(motivo);
		cambio.setCreadoPor(auth.getName());
		cambio.setFechaCreacion(hourdateFormat.format(date));
    	cambio.setEstatus("1");
    	cambio.setEstatusEnvio("0");
    	CambioTelaService.save(cambio);
    	
    	cambio.setIdText("CAMTE"+(cambio.getIdTelaPedido()+1000));
    	CambioTelaService.save(cambio);
    	return false;
    }
    
    @RequestMapping(value = "/guardar-cambio-tela-rest", method = RequestMethod.POST)
	@ResponseBody
	public Integer guardar(@RequestParam(name = "datosMateriales") String datosMateriales,
			@RequestParam(name = "datosMateriales22") String datosMateriales22,
			@RequestParam(name = "datosMateriales222") String datosMateriales222,

			@RequestParam(name = "arrayId") String arrayid,

			Long idPrenda,

			Long idTela,

			Long idModelo,

			Long idCoordinado,
			Long idSolicitud,

			HttpServletRequest request,
			Long idCoorPrendaCambio) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		System.out.println("Si entro al rest guardar guar guardarrrr coordinado");

		System.out.println("El id de prenda es: " + idPrenda);

		System.out.println("El id de tela es: " + idTela);

		System.out.println("Este es el arreglo: " + arrayid);

		// ProduccionPedido objetoPedido= servicePedido.findOne(idPedido);

		// String texto= objetoPedido.getIdText();

		ProduccionCoordinadoPrenda objetoCoodinadoPrenda = new ProduccionCoordinadoPrenda();

		objetoCoodinadoPrenda.setIdFamilaGenero(idPrenda);
		objetoCoodinadoPrenda.setIdPrenda(idModelo);
		objetoCoodinadoPrenda.setIdTela(idTela);
		objetoCoodinadoPrenda.setIdCoordinadoPrendaCambio(idCoorPrendaCambio);
		objetoCoodinadoPrenda.setAdicional("0");
		objetoCoodinadoPrenda.setMontoAdicional("0");
		objetoCoodinadoPrenda.setPrecioFinal("0");
		objetoCoodinadoPrenda.setEstatus("1");
		objetoCoodinadoPrenda.setCreadoPor(auth.getName());
		objetoCoodinadoPrenda.setFechaCreacion(hourdateFormat.format(date));
		objetoCoodinadoPrenda.setIdSolicitudCambioTela(idSolicitud);
		objetoCoodinadoPrenda.setPrecio(CoordinadoService.precioPrenda(idCoordinado, idModelo, idTela));
		objetoCoodinadoPrenda.setPrecioFinal(CoordinadoService.precioPrenda(idCoordinado, idModelo, idTela));
		CambioTelaService.saveCoorPrenda(objetoCoodinadoPrenda);

		////// seccion2 TELAS

		JSONArray json2 = new JSONArray(datosMateriales22);
		for (int k = 0; k < json2.length(); k++) {
			ProduccionCoordinadoTela detalleTela = new ProduccionCoordinadoTela();
			JSONObject object = (JSONObject) json2.get(k);
			String id = object.get("id_tela").toString();

			detalleTela.setIdTela(Long.parseLong(id));
			detalleTela.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
			detalleTela.setCreado_por(auth.getName());
			detalleTela.setActualizadoPor("User");
			detalleTela.setFechaCreacion(hourdateFormat.format(date));
			detalleTela.setUltimaFechaModificacion(null);
			CambioTelaService.saveTelaMaterial(detalleTela);

		}

		//////// SECUION3 FORROS

		JSONArray json22 = new JSONArray(datosMateriales222);
		for (int h = 0; h < json22.length(); h++) {
			ProduccionCoordinadoForro detalleForro = new ProduccionCoordinadoForro();
			JSONObject object = (JSONObject) json22.get(h);
			String id = object.get("id_forro").toString();

			detalleForro.setIdForro(Long.parseLong(id));
			detalleForro.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());

			detalleForro.setCreado_por(auth.getName());
			detalleForro.setActualizadoPor("user");
			detalleForro.setFechaCreacion(hourdateFormat.format(date));
			detalleForro.setUltimaFechaModificacion(null);
			CambioTelaService.saveForroMaterial(detalleForro);
		}

		////// parte 3 materiales

		JSONArray json = new JSONArray(datosMateriales);
		String[] parts2 = arrayid.split(",");
		for (int j = 0; j < json.length(); j++) {

			ProduccionCoordinadoMaterial material = new ProduccionCoordinadoMaterial();
			JSONObject object = (JSONObject) json.get(j);

			String color = object.get("color").toString();
			String[] parts = color.split("_");

			material.setIdMaterial(Long.parseLong(parts2[j]));
			material.setColor(parts[0]);
			material.setColorCodigo(parts[1]);
			material.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
			material.setCreadoPor(auth.getName());
			material.setActualizadoPor(null);
			material.setFechaCreacion(hourdateFormat.format(date));
			material.setUltimaFechaModificacion(null);
			CambioTelaService.saveCoorMaterial(material);

		}

		return 1;

	}
    
    @GetMapping("/buscar-cambio-existente")
    public ProduccionCoordinadoPrenda buscarCambioExistente (Long id, Long idSolicitud) {
    	return CambioTelaService.BuscarCambio(id, idSolicitud);
    }
    
    @GetMapping("/eliminar-cambio-existente")
    public void eliminar (Long id) {
    	 CambioTelaService.deletePrenda(id);
    }
    
    @PostMapping("/enviar-solicitud")
    public boolean enviar (Long id) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	ProduccionSolicitudCambioTelaPedido solicitud = CambioTelaService.findOne(id);
    	solicitud.setEstatusEnvio("1");
    	solicitud.setActualizadoPor(auth.getName());
    	solicitud.setUltimaFechaModificacion(hourdateFormat.format(date));
    	CambioTelaService.save(solicitud);
    	return false;
    	 //CambioTelaService.deletePrenda(id);
    }
    
    @PostMapping("/rechazar-solicitud")
    public boolean rechazar (Long id) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	ProduccionSolicitudCambioTelaPedido solicitud = CambioTelaService.findOne(id);
    	solicitud.setEstatusEnvio("3");
    	solicitud.setActualizadoPor(auth.getName());
    	solicitud.setUltimaFechaModificacion(hourdateFormat.format(date));
    	CambioTelaService.save(solicitud);
    	return false;
    	 //CambioTelaService.deletePrenda(id);
    }
    
    @PostMapping("/aceptar-solicitud")
    public boolean aceptar (Long id) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	
    	List<Object[]> aux = CambioTelaService.QueryExtracionCambios(id);
		for (Object[] a : aux) {
			// a[0] es el id actual
			//a[1] es el id cambio
			CambioTelaService.actualizar(Long.valueOf(a[0].toString()), Long.valueOf(a[1].toString()), auth.getName(), hourdateFormat.format(date));
		
		}
    	ProduccionSolicitudCambioTelaPedido solicitud = CambioTelaService.findOne(id);
    	solicitud.setEstatusEnvio("2");
    	solicitud.setActualizadoPor(auth.getName());
    	solicitud.setUltimaFechaModificacion(hourdateFormat.format(date));
    	CambioTelaService.save(solicitud);
    	return false;
    	 //CambioTelaService.deletePrenda(id);
    }
    

}
