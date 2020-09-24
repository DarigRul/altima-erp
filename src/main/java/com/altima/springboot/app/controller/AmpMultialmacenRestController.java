package com.altima.springboot.app.controller;

import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.dto.ArticulosMultialmacenDto;
import com.altima.springboot.app.models.entity.AmpEntrada;
import com.altima.springboot.app.models.entity.AmpEntradaDetalle;
import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.models.service.IAmpEntradaDetalleService;
import com.altima.springboot.app.models.service.IAmpEntradaService;
import com.altima.springboot.app.models.service.IAmpMultialmacenService;
import com.fasterxml.jackson.core.JsonParser;

@RestController
public class AmpMultialmacenRestController {
	@Autowired
	IAmpMultialmacenService AmpMultialmacenService;
	@Autowired
	IAmpEntradaService entradaService;
	@Autowired
	IAmpEntradaDetalleService entradaDetalleService;


	@GetMapping("/multialmacen-articulos")
	public List<Object[]> MultialmacenArticulos(Long articulo, String tipo) {

		return AmpMultialmacenService.findAllAMPLogicItem(articulo, tipo);
	}

	@PostMapping("/guardar-multialmacen")
	public boolean GuardarMultialmacen(Long AlmacenLogico, String Tipo, Long Articulo) {
		boolean result = false;
		String TipoPost = null;
		if (Tipo.contains("m")) {
			TipoPost = "material";
		} else if (Tipo.contains("aa")) {
			TipoPost = "materialAlmacen";

		} else if (Tipo.contains("f")) {
			TipoPost = "forro";

		} else if (Tipo.contains("t")) {

			TipoPost = "tela";
		}

		if (AmpMultialmacenService.findDuplicates(TipoPost, AlmacenLogico, Articulo).size() <= 0) {
			try {
				AmpMultialmacen multialmacen = new AmpMultialmacen();
				multialmacen.setIdAlmacenLogico(AlmacenLogico);
				multialmacen.setIdArticulo(Articulo);
				multialmacen.setTipo(TipoPost);
				multialmacen.setEstatus("1");
				AmpMultialmacenService.save(multialmacen);
				result = true;
			} catch (Exception e) {
				result = false;
			}

		} else {

			result = false;
		}

		return result;
	}

	@DeleteMapping("/eliminar-multialmacen")
	public boolean EliminarMultialmacen(Long idmultialmacen) {
		boolean result = false;
		try {
			AmpMultialmacenService.deleteById(idmultialmacen);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			result = false;
		}
		return result;
	}

	@GetMapping("/getArticulosMultialmacen")
	public List<ArticulosMultialmacenDto> MultialmacenArticulos(Long idAlmacenLogico) {

		return AmpMultialmacenService.findArticulosByMultialmacen(idAlmacenLogico);
	}

	@PostMapping("/postMovimientosAlmacen")
	public String postMovimientosAlmacen(@RequestParam String cabecero,@RequestParam String movimientos) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONArray cabeceroArray = new JSONArray(cabecero);
		JSONObject cabeceroJson = cabeceroArray.getJSONObject(0);
		JSONArray movimientosArray = new JSONArray(movimientos);
			AmpEntrada entrada=new AmpEntrada();
			entrada.setIdAlmacenLogico(Long.parseLong(cabeceroJson.getString("idAlmacenLogico")));
			entrada.setObservaciones(cabeceroJson.getString("observaciones"));
			entrada.setIdConceptoEntrada(Long.parseLong(cabeceroJson.getString("concepto")));
			entrada.setFechaDocumento(cabeceroJson.getString("fechaMovimiento"));
			entrada.setEstatus("1");
			entrada.setCreadoPor(auth.getName());
			entrada.setActualizadoPor(auth.getName());
			entrada.setIdText("idText");
			entradaService.save(entrada);
			entrada.setIdText("ENT"+(10000+entrada.getIdEntrada()));
			entradaService.save(entrada);
			for (int i = 0; i < movimientosArray.length(); i++) {
				AmpEntradaDetalle entradaDetalle=new AmpEntradaDetalle();
				JSONObject movimientosJson = movimientosArray.getJSONObject(i);
				entradaDetalle.setTipo(movimientosJson.getString("tipo"));
				entradaDetalle.setCantidad(Long.parseLong(movimientosJson.getString("cantidad")));
				entradaDetalle.setIdEntrada(entrada.getIdEntrada());
				entradaDetalle.setIdArticulo(Long.parseLong(movimientosJson.getString("id")));
				entradaDetalleService.save(entradaDetalle);
			}


		return "Success";

	}
}
