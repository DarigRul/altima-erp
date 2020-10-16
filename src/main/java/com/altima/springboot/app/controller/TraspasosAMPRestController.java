package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.models.entity.AmpTraspaso;
import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;
import com.altima.springboot.app.models.service.IAmpEntradaDetalleService;
import com.altima.springboot.app.models.service.IAmpEntradaService;
import com.altima.springboot.app.models.service.IAmpMultialmacenService;
import com.altima.springboot.app.models.service.IAmpSalidaDetalleService;
import com.altima.springboot.app.models.service.IAmpSalidaService;
import com.altima.springboot.app.models.service.IAmpTraspasoDetalleService;
import com.altima.springboot.app.models.service.IAmpTraspasoService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class TraspasosAMPRestController {

	@Autowired
	IAmpMultialmacenService multialmacenService;
	@Autowired
	IAmpTraspasoService traspasoService;
	@Autowired
	IAmpTraspasoDetalleService traspasoDetalleService;

	@Transactional
	@PostMapping("/postTraspasos")
	public String postMovimientosEntradaAlmacen(@RequestParam String cabecero, @RequestParam String movimientos,
			RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONArray cabeceroArray = new JSONArray(cabecero);
		JSONObject cabeceroJson = cabeceroArray.getJSONObject(0);
		JSONArray movimientosArray = new JSONArray(movimientos);
		try {
			AmpTraspaso traspaso = new AmpTraspaso();
			traspaso.setIdAlmacenLogicoOrigen(Long.parseLong(cabeceroJson.getString("almacenOrigenTraspaso")));
			traspaso.setIdAlmacenLogicoDestino(Long.parseLong(cabeceroJson.getString("almacenDestinoTraspaso")));
			traspaso.setIdConceptoEntrada(Long.parseLong(cabeceroJson.getString("movimientoEntrada")));
			traspaso.setIdConceptoSalida(Long.parseLong(cabeceroJson.getString("movimientoSalida")));
			traspaso.setObservaciones(cabeceroJson.getString("observaciones"));
			traspaso.setFechaDocumento(cabeceroJson.getString("fechaMovimiento"));
			traspaso.setEstatus("1");
			traspaso.setCreadoPor(auth.getName());
			traspaso.setActualizadoPor(auth.getName());
			traspasoService.save(traspaso);
			traspaso.setIdText("TRA" + (1000 + traspaso.getIdTraspaso()));
			traspasoService.save(traspaso);
			for (int i = 0; i < movimientosArray.length(); i++) {
				Long idMultialmacenSalida = null;
				Long idMultialmacenEntrada = null;
				AmpTraspasoDetalle traspasoDetalle = new AmpTraspasoDetalle();
				JSONObject movimientosJson = movimientosArray.getJSONObject(i);
				traspasoDetalle.setTipo(movimientosJson.getString("tipo"));
				traspasoDetalle.setCantidad(Long.parseLong(movimientosJson.getString("cantidad")));
				traspasoDetalle.setIdTraspaso(traspaso.getIdTraspaso());
				traspasoDetalle.setIdArticulo(Long.parseLong(movimientosJson.getString("id")));
				traspasoDetalleService.save(traspasoDetalle);
				idMultialmacenSalida = multialmacenService.findIdMultialmacen(traspaso.getIdAlmacenLogicoOrigen(),
						traspasoDetalle.getIdArticulo(), traspasoDetalle.getTipo());
				idMultialmacenEntrada = multialmacenService.findIdMultialmacen(traspaso.getIdAlmacenLogicoDestino(),
						traspasoDetalle.getIdArticulo(), traspasoDetalle.getTipo());
				AmpMultialmacen multialmacenSalida = multialmacenService.findById(idMultialmacenSalida);
				AmpMultialmacen multialmacenEntrada = multialmacenService.findById(idMultialmacenEntrada);
				multialmacenSalida.setExistencia(multialmacenSalida.getExistencia() - traspasoDetalle.getCantidad());
				multialmacenEntrada.setExistencia(multialmacenEntrada.getExistencia() + traspasoDetalle.getCantidad());
				multialmacenService.save(multialmacenSalida);
			}
		} catch (Exception e) {
			return "Error";
		}
		return "Success";
	}

}
