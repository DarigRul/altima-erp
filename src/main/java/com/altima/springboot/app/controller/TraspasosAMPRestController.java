package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.altima.springboot.app.models.entity.AmpAlmacenUbicacionArticulo;
import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.models.entity.AmpRolloTela;
import com.altima.springboot.app.models.entity.AmpTraspaso;
import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;
import com.altima.springboot.app.models.service.IAmpAlmacenLogicoService;
import com.altima.springboot.app.models.service.IAmpAlmacenUbicacionArticuloService;
import com.altima.springboot.app.models.service.IAmpMultialmacenService;
import com.altima.springboot.app.models.service.IAmpRolloTelaService;
import com.altima.springboot.app.models.service.IAmpTraspasoDetalleService;
import com.altima.springboot.app.models.service.IAmpTraspasoService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
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
	@Autowired
	IAmpAlmacenUbicacionArticuloService almacenUbicacionArticuloService;
	@Autowired
	IAmpRolloTelaService rolloTelaService;
	@Autowired
	IAmpAlmacenLogicoService almacenLogicoService;

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
			traspaso.setTipo("0");
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
				traspasoDetalle.setCantidad(movimientosJson.getFloat("cantidad"));
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
				multialmacenService.save(multialmacenEntrada);
				if (movimientosJson.getString("tipo").equals("tela")&&!movimientosJson.get("idRollo").equals(null)) {
					System.out.println("entra al trans");
					AmpRolloTela rollo=rolloTelaService.findOne(movimientosJson.getLong("idRollo"));
					if(almacenLogicoService.findByTipo("2", "Apartados").getIdAlmacenLogico().equals(cabeceroJson.getLong("almacenDestinoTraspaso"))){
						rollo.setEstatus("0");
					}
					else if(almacenLogicoService.findByTipo("2", "Preapartado").getIdAlmacenLogico().equals(cabeceroJson.getLong("almacenDestinoTraspaso"))){
						rollo.setEstatus("2");
					}
					else{
						rollo.setEstatus("1");
						rollo.setIdPedido(null);
						rollo.setIdAlmacenLogico(cabeceroJson.getLong("almacenDestinoTraspaso"));
					}
					if (movimientosJson.get("ubicacion").equals(null)) {
						rolloTelaService.save(rollo);
					} else {
						rollo.setIdAlmacenFisico(cabeceroJson.getLong("idAlmacenFisico"));
						
						rolloTelaService.save(rollo);
						AmpAlmacenUbicacionArticulo ubicacionArticulo = almacenUbicacionArticuloService.findByIdArticulo(movimientosJson.getLong("idRollo"), "tela");
						ubicacionArticulo.setIdUbicacion(movimientosJson.getLong("ubicacion"));
						ubicacionArticulo.setTipo("tela");
						ubicacionArticulo.setEstatus("1");
						ubicacionArticulo.setIdArticulo(movimientosJson.getLong("idRollo"));
						ubicacionArticulo.setCreadoPor(auth.getName());
						ubicacionArticulo.setActualizadoPor(auth.getName());
						ubicacionArticulo.setUltimaFechaModificacion(currentDate());
						almacenUbicacionArticuloService.save(ubicacionArticulo);
					}
				}

			}
		} catch (Exception e) {
			System.err.println(e);
			return "Error";
		}
		return "Success";
	}

	private String currentDate() {
		Date date = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		hourdateFormat.setTimeZone(timeZone);
		String sDate = hourdateFormat.format(date);
		return sDate;
	}
	
}
