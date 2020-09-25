package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.entity.AmpEntrada;
import com.altima.springboot.app.models.entity.AmpEntradaDetalle;
import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.models.entity.AmpSalida;
import com.altima.springboot.app.models.entity.AmpSalidaDetalle;
import com.altima.springboot.app.models.service.IAmpEntradaDetalleService;
import com.altima.springboot.app.models.service.IAmpEntradaService;
import com.altima.springboot.app.models.service.IAmpMultialmacenService;
import com.altima.springboot.app.models.service.IAmpSalidaDetalleService;
import com.altima.springboot.app.models.service.IAmpSalidaService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EntradaSalidaAMPController {

	@Autowired
	IAmpEntradaService entradaService;
	@Autowired
	IAmpEntradaDetalleService entradaDetalleService;
	@Autowired
	IAmpSalidaService salidaService;
	@Autowired
	IAmpSalidaDetalleService salidaDetalleService;
	@Autowired
	IAmpMultialmacenService multialmacenService;

	@GetMapping("/movimientos-amp")
	public String Index(Model m) {
		m.addAttribute("movimientos", multialmacenService.findAllMovimientos());
		return "movimientos-amp";
	}

	@GetMapping("/agregar-movimientos-amp")
	public String Store() {
		return "agregar-movimientos-amp";
	}

	@Transactional
	@PostMapping("/postMovimientosEntradaAlmacen")
	public String postMovimientosEntradaAlmacen(@RequestParam String cabecero, @RequestParam String movimientos) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONArray cabeceroArray = new JSONArray(cabecero);
		JSONObject cabeceroJson = cabeceroArray.getJSONObject(0);
		JSONArray movimientosArray = new JSONArray(movimientos);
		try {
			AmpEntrada entrada = new AmpEntrada();
			entrada.setIdAlmacenLogico(Long.parseLong(cabeceroJson.getString("idAlmacenLogico")));
			entrada.setObservaciones(cabeceroJson.getString("observaciones"));
			entrada.setIdConceptoEntrada(Long.parseLong(cabeceroJson.getString("concepto")));
			entrada.setFechaDocumento(cabeceroJson.getString("fechaMovimiento"));
			entrada.setEstatus("1");
			entrada.setCreadoPor(auth.getName());
			entrada.setActualizadoPor(auth.getName());
			entrada.setIdText("idText");
			entradaService.save(entrada);
			entrada.setIdText("ENT" + (10000 + entrada.getIdEntrada()));
			entradaService.save(entrada);
			for (int i = 0; i < movimientosArray.length(); i++) {
				Long idMultialmacen=null;
				AmpEntradaDetalle entradaDetalle = new AmpEntradaDetalle();
				JSONObject movimientosJson = movimientosArray.getJSONObject(i);
				entradaDetalle.setTipo(movimientosJson.getString("tipo"));
				entradaDetalle.setCantidad(Long.parseLong(movimientosJson.getString("cantidad")));
				entradaDetalle.setIdEntrada(entrada.getIdEntrada());
				entradaDetalle.setIdArticulo(Long.parseLong(movimientosJson.getString("id")));
				entradaDetalleService.save(entradaDetalle);
				idMultialmacen=multialmacenService.findIdMultialmacen(entrada.getIdAlmacenLogico(), entradaDetalle.getIdArticulo(), entradaDetalle.getTipo());
				AmpMultialmacen multialmacen = multialmacenService.findById(idMultialmacen);
				multialmacen.setExistencia(multialmacen.getExistencia()+entradaDetalle.getCantidad());
				multialmacenService.save(multialmacen);
			}
			
		} catch (Exception e) {
			return "redirect:/movimientos-amp";
		}
		return "redirect:/movimientos-amp";
	}

	@Transactional
	@PostMapping("/postMovimientosSalidaAlmacen")
	public String postMovimientosSalidaAlmacen(@RequestParam String cabecero, @RequestParam String movimientos)
			throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONArray cabeceroArray = new JSONArray(cabecero);
		JSONObject cabeceroJson = cabeceroArray.getJSONObject(0);
		JSONArray movimientosArray = new JSONArray(movimientos);
		try {
			AmpSalida salida = new AmpSalida();
			salida.setIdAlmacenLogico(Long.parseLong(cabeceroJson.getString("idAlmacenLogico")));
			salida.setObservaciones(cabeceroJson.getString("observaciones"));
			salida.setIdConceptoSalida(Long.parseLong(cabeceroJson.getString("concepto")));
			salida.setFechaDocumento(cabeceroJson.getString("fechaMovimiento"));
			salida.setEstatus("1");
			salida.setCreadoPor(auth.getName());
			salida.setActualizadoPor(auth.getName());
			salida.setIdText("idText");
			salidaService.save(salida);
			salida.setIdText("SAL" + (10000 + salida.getIdSalida()));
			salidaService.save(salida);
			for (int i = 0; i < movimientosArray.length(); i++) {
				Long idMultialmacen = null;
				AmpSalidaDetalle salidaDetalle = new AmpSalidaDetalle();
				JSONObject movimientosJson = movimientosArray.getJSONObject(i);
				salidaDetalle.setTipo(movimientosJson.getString("tipo"));
				salidaDetalle.setCantidad(Long.parseLong(movimientosJson.getString("cantidad")));
				salidaDetalle.setIdSalida(salida.getIdSalida());
				salidaDetalle.setIdArticulo(Long.parseLong(movimientosJson.getString("id")));
				salidaDetalleService.save(salidaDetalle);
				idMultialmacen = multialmacenService.findIdMultialmacen(salida.getIdAlmacenLogico(),
						salidaDetalle.getIdArticulo(), salidaDetalle.getTipo());
				AmpMultialmacen multialmacen = multialmacenService.findById(idMultialmacen);
				multialmacen.setExistencia(multialmacen.getExistencia() - salidaDetalle.getCantidad());
				multialmacenService.save(multialmacen);
			}
		} catch (Exception e) {
			//TODO: handle exception
			return "redirect:/movimientos-amp";
		}


		
		return "redirect:/movimientos-amp";

	}
}
