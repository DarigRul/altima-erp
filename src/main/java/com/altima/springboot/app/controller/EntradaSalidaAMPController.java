package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;

import com.altima.springboot.app.models.entity.AmpEntrada;
import com.altima.springboot.app.models.entity.AmpEntradaDetalle;
import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.models.entity.AmpRolloTela;
import com.altima.springboot.app.models.entity.AmpSalida;
import com.altima.springboot.app.models.entity.AmpSalidaDetalle;
import com.altima.springboot.app.models.service.IAmpEntradaDetalleService;
import com.altima.springboot.app.models.service.IAmpEntradaService;
import com.altima.springboot.app.models.service.IAmpMultialmacenService;
import com.altima.springboot.app.models.service.IAmpRolloTelaService;
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
	@Autowired
	IAmpRolloTelaService rolloTelaService;

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
				Long idMultialmacen = null;
				AmpEntradaDetalle entradaDetalle = new AmpEntradaDetalle();
				JSONObject movimientosJson = movimientosArray.getJSONObject(i);
				entradaDetalle.setTipo(movimientosJson.getString("tipo"));
				entradaDetalle.setCantidad(movimientosJson.getFloat("cantidad"));
				entradaDetalle.setIdEntrada(entrada.getIdEntrada());
				entradaDetalle.setIdArticulo(Long.parseLong(movimientosJson.getString("id")));
				entradaDetalleService.save(entradaDetalle);
				idMultialmacen = multialmacenService.findIdMultialmacen(entrada.getIdAlmacenLogico(),
						entradaDetalle.getIdArticulo(), entradaDetalle.getTipo());
				AmpMultialmacen multialmacen = multialmacenService.findById(idMultialmacen);
				multialmacen.setExistencia(multialmacen.getExistencia() + entradaDetalle.getCantidad());
				multialmacenService.save(multialmacen);
				if (movimientosJson.getString("tipo").equals("tela")) {
					Formatter fmt = new Formatter();
					AmpRolloTela rollo = new AmpRolloTela();
					rollo.setCantidad(movimientosJson.getFloat("cantidad"));
					rollo.setEstatus("1");
					rollo.setLote(movimientosJson.getString("lote"));
					rollo.setCreadoPor(auth.getName());
					rollo.setActualizadoPor(auth.getName());
					rollo.setIdText("idText");
					rollo.setIdAlmacenFisico(cabeceroJson.getLong("idAlmacenFisico"));
					System.out.println(cabeceroJson.getInt("idAlmacenFisico"));
					rollo.setIdTela(Long.parseLong(movimientosJson.getString("id")));
					rolloTelaService.save(rollo);
					rollo.setIdText("ROLLO" + fmt.format("%05d", rollo.getIdRolloTela()));
					rolloTelaService.save(rollo);
					fmt.close();
				}
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
		Date date = new Date();

		TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		hourdateFormat.setTimeZone(timeZone);

        // change tz using formatter
        String sDate = hourdateFormat.format(date);
		// System.out.println("entra a la salida "+movimientosArray.toString()+" "+cabeceroJson.toString());
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
				salidaDetalle.setCantidad(movimientosJson.getFloat("cantidad"));
				salidaDetalle.setIdSalida(salida.getIdSalida());
				salidaDetalle.setIdArticulo(Long.parseLong(movimientosJson.getString("id")));
				salidaDetalleService.save(salidaDetalle);
				idMultialmacen = multialmacenService.findIdMultialmacen(salida.getIdAlmacenLogico(),
						salidaDetalle.getIdArticulo(), salidaDetalle.getTipo());
				AmpMultialmacen multialmacen = multialmacenService.findById(idMultialmacen);
				multialmacen.setExistencia(multialmacen.getExistencia() - salidaDetalle.getCantidad());
				multialmacenService.save(multialmacen);
				if (movimientosJson.getString("tipo").equals("tela")) {
					AmpRolloTela rollo = rolloTelaService.findOne(Long.parseLong(movimientosJson.getString("idRollo")));
					rollo.setUltimaFechaModificacion(sDate);
					rollo.setActualizadoPor(auth.getName());
					rollo.setEstatus("0");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/movimientos-amp";
		}

		return "redirect:/movimientos-amp";

	}
}
