package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.transaction.Transactional;

import com.altima.springboot.app.dto.ExplosionTelaDto;
import com.altima.springboot.app.models.entity.AmpAlmacenLogico;
import com.altima.springboot.app.models.entity.AmpAlmacenUbicacionArticulo;
import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.models.entity.AmpRolloTela;
import com.altima.springboot.app.models.entity.AmpTraspaso;
import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;
import com.altima.springboot.app.models.service.IAmpAlmacenLogicoService;
import com.altima.springboot.app.models.service.IAmpAlmacenUbicacionArticuloService;
import com.altima.springboot.app.models.service.IAmpExplosionTelaService;
import com.altima.springboot.app.models.service.IAmpLoookupService;
import com.altima.springboot.app.models.service.IAmpMultialmacenService;
import com.altima.springboot.app.models.service.IAmpRolloTelaService;
import com.altima.springboot.app.models.service.IAmpTraspasoDetalleService;
import com.altima.springboot.app.models.service.IAmpTraspasoService;
import com.cloudinary.http44.api.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExplosionMateriaPrimaRestController {

    @Autowired
    IAmpExplosionTelaService explosionTelaService;

    @Autowired
    IAmpTraspasoService traspasoService;

    @Autowired
    IAmpTraspasoDetalleService traspasoDetalleService;

    @Autowired
    IAmpMultialmacenService multialmacenService;

    @Autowired
    IAmpRolloTelaService rolloTelaService;

    @Autowired
    IAmpAlmacenUbicacionArticuloService almacenUbicacionArticuloService;

    @Autowired
    IAmpAlmacenLogicoService almacenLogicoService;

    @GetMapping("/getApartadoTelasById")
    public List<ExplosionTelaDto> getApartadoTelasById(@RequestParam Long idPedido) {
        return explosionTelaService.findAllExplosion(idPedido);
    }

    @GetMapping("/getApartadoTelasByIdTela")
    public float getApartadoTelasByIdTela(@RequestParam Long idPedido,@RequestParam Long idTela) {
        return explosionTelaService.findAllExplosionByIdTela(idPedido, idTela);
    }

    @Transactional
    @PostMapping("/postExplosionTelas")
    public ResponseEntity<?> postMovimientosEntradaAlmacen(@RequestParam Long idPedido, @RequestParam String rollos,
            @RequestParam String idAlmacenes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(rollos);
        Map<String, Object> response = new HashMap<>();
        JSONArray rollosArray = new JSONArray(rollos);
        JSONArray idAlmacenesArray = new JSONArray(idAlmacenes);
        AmpAlmacenLogico almacenLogicoDestino = almacenLogicoService
                .findOne(almacenLogicoService.findByTipo("2", "Apartados").getIdAlmacenLogico());
        try {
            for (int i = 0; i < idAlmacenesArray.length(); i++) {
                AmpAlmacenLogico almacenLogicoOrigen = almacenLogicoService.findOne(Long.parseLong(idAlmacenesArray.get(i).toString()));
                AmpTraspaso traspaso = new AmpTraspaso();
                traspaso.setIdAlmacenLogicoOrigen(almacenLogicoOrigen.getIdAlmacenLogico());
                traspaso.setIdAlmacenLogicoDestino(almacenLogicoDestino.getIdAlmacenLogico());
                traspaso.setIdConceptoEntrada(almacenLogicoDestino.getIdMovimientoEntrada());
                traspaso.setIdConceptoSalida(almacenLogicoOrigen.getIdMovimientoSalida());
                traspaso.setObservaciones(null);
                traspaso.setReferencia(idPedido.toString());
                traspaso.setFechaDocumento(currentDate());
                traspaso.setEstatus("1");
                traspaso.setTipo("1");
                traspaso.setCreadoPor(auth.getName());
                traspaso.setActualizadoPor(auth.getName());
                traspasoService.save(traspaso);
                traspaso.setIdText("TRA" + (1000 + traspaso.getIdTraspaso()));
                traspasoService.save(traspaso);
                for (int j = 0; j < rollosArray.length(); j++) {
                    Long idMultialmacenSalida = null;
                    Long idMultialmacenEntrada = null;
                    AmpTraspasoDetalle traspasoDetalle = new AmpTraspasoDetalle();
                    JSONObject rollosJson = rollosArray.getJSONObject(j);
                    if (traspaso.getIdAlmacenLogicoOrigen().equals(rollosJson.getLong("idAlmacenLogico"))) {
                        traspasoDetalle.setTipo("tela");
                        traspasoDetalle.setCantidad(rollosJson.getFloat("cantidad"));
                        traspasoDetalle.setIdTraspaso(traspaso.getIdTraspaso());
                        traspasoDetalle.setIdArticulo(rollosJson.getLong("idTela"));
                        traspasoDetalleService.save(traspasoDetalle);
                        idMultialmacenSalida = multialmacenService.findIdMultialmacen(
                                traspaso.getIdAlmacenLogicoOrigen(), traspasoDetalle.getIdArticulo(),
                                traspasoDetalle.getTipo());
                        idMultialmacenEntrada = multialmacenService.findIdMultialmacen(
                                traspaso.getIdAlmacenLogicoDestino(), traspasoDetalle.getIdArticulo(),
                                traspasoDetalle.getTipo());
                        System.out.println("break");
                        AmpMultialmacen multialmacenSalida = multialmacenService.findById(idMultialmacenSalida);
                        AmpMultialmacen multialmacenEntrada = multialmacenService.findById(idMultialmacenEntrada);
                        multialmacenSalida
                                .setExistencia(multialmacenSalida.getExistencia() - traspasoDetalle.getCantidad());
                        multialmacenEntrada
                                .setExistencia(multialmacenEntrada.getExistencia() + traspasoDetalle.getCantidad());
                        multialmacenService.save(multialmacenSalida);
                        System.out.println("entra al trans: "+rollosJson.getLong("idRollo"));
                        AmpRolloTela rollo = rolloTelaService.findOne(rollosJson.getLong("idRollo"));
                        rollo.setEstatus("0");
                        rollo.setIdPedido(idPedido);
                        rolloTelaService.save(rollo);
                    }

                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            response.put("mensaje", "Error");
			response.put("error", e.getLocalizedMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Success");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
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
