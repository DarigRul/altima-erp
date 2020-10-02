package com.altima.springboot.app.controller;

import java.util.List;

import com.altima.springboot.app.dto.AgenteVentasListDTO;
import com.altima.springboot.app.dto.ComercialSolicitudModeloDTO;
import com.altima.springboot.app.dto.ComercialSolicitudModeloDetalleDTO;
import com.altima.springboot.app.models.entity.ComercialSolicitudModelo;
import com.altima.springboot.app.models.entity.ComercialSolicitudModeloDetalle;
import com.altima.springboot.app.models.service.IComercialSolicitudModeloDetalleService;
import com.altima.springboot.app.models.service.IComercialSolicitudModeloService;
import com.altima.springboot.app.models.service.IHrEmpleadoService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MuestrarioSolicitudModelosRestController {

    @Autowired
    IHrEmpleadoService empleadoService;

    @Autowired
    IComercialSolicitudModeloService solicitudModeloService;

    @Autowired
    IComercialSolicitudModeloDetalleService solicitudModeloDetalleService;

    @GetMapping("getAgenteVentas")
    public List<AgenteVentasListDTO> index() {

        return empleadoService.findAllAgenteVentas();
    }

    @PostMapping("postSolicitudModelo")
    public String postSolicitudModelo(@RequestParam String solicitudModelo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONArray solicitudArray = new JSONArray(solicitudModelo);
        JSONObject solicitudJson = solicitudArray.getJSONObject(0);
        try {
            ComercialSolicitudModelo solicitud = new ComercialSolicitudModelo();
            solicitud.setCreadoPor(auth.getName());
            solicitud.setActualizadoPor(auth.getName());
            solicitud.setIdAgente(Long.parseLong(solicitudJson.getString("idAgenteVentas")));
            solicitud.setIdCliente(Long.parseLong(solicitudJson.getString("idCliente")));
            solicitud.setFechaCita(solicitudJson.getString("fechaCita"));
            solicitud.setHoraSalidaAltima(solicitudJson.getString("horaSalida"));
            solicitud.setEstatus("1");
            solicitud.setIdText("SOLMOD");
            solicitudModeloService.save(solicitud);
            solicitud.setIdText("SOLMOD" + (10000 + solicitud.getIdSolicitudModelo()));
            solicitudModeloService.save(solicitud);
        } catch (Exception e) {
            return "Error";
        }
        return "Success";
    }

    @PostMapping("postSolicitudModeloDetalle")
    public String postSolicitudModeloDetalle(@RequestParam Long idModelo, @RequestParam Long idSolicitud) {

        try {
            ComercialSolicitudModeloDetalle solicitudDetalle = new ComercialSolicitudModeloDetalle();
            solicitudDetalle.setIdModelo(idModelo);
            solicitudDetalle.setIdSolicitudModelo(idSolicitud);
            solicitudModeloDetalleService.save(solicitudDetalle);
        } catch (Exception e) {
            // TODO: handle exception
            return "Error";
        }
        return "Success";
    }

    @GetMapping("getModelosByIdSolicitud")
    public List<ComercialSolicitudModeloDetalleDTO> getModelosByIdSolicitud(@RequestParam Long idSolicitud) {

        return solicitudModeloDetalleService.findAllByidSolicitud(idSolicitud);
    }

    @DeleteMapping("deleteSolicitudModeloDetalle")
    public String deleteSolicitudModeloDetalle(@RequestParam Long id) {
        try {
            solicitudModeloDetalleService.delete(id);
        } catch (Exception e) {
            // TODO: handle exception
            return "Error";
        }
        return "Success";
    }

    @GetMapping("getSolicitudModelos")
    public ComercialSolicitudModelo getSolicitudModelos(@RequestParam Long idSolicitud) {

        return solicitudModeloService.findOne(idSolicitud);
    }

    @PostMapping("postSolicitudModelosAgente")
    public String postSolicitudModelosAgente(@RequestParam String solicitudModelo,
            @RequestParam String solicitudModeloDetalle) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONArray solicitudArray = new JSONArray(solicitudModelo);
        JSONObject solicitudJson = solicitudArray.getJSONObject(0);
        JSONArray solicitudDetalleArray = new JSONArray(solicitudModeloDetalle);
        try {
            ComercialSolicitudModelo solicitud = new ComercialSolicitudModelo();
            solicitud.setCreadoPor(auth.getName());
            solicitud.setActualizadoPor(auth.getName());
            solicitud.setIdAgente(Long.parseLong(solicitudJson.getString("idAgente")));
            solicitud.setIdCliente(Long.parseLong(solicitudJson.getString("idCliente")));
            solicitud.setFechaCita(solicitudJson.getString("fechaCita"));
            solicitud.setHoraSalidaAltima(solicitudJson.getString("horaSalida"));
            solicitud.setEstatus("1");
            solicitud.setIdText("SOLMOD");
            solicitudModeloService.save(solicitud);
            solicitud.setIdText("SOLMOD" + (10000 + solicitud.getIdSolicitudModelo()));
            solicitudModeloService.save(solicitud);
            for (int i = 0; i < solicitudDetalleArray.length(); i++) {
                ComercialSolicitudModeloDetalle solicitudDetalle = new ComercialSolicitudModeloDetalle();
                solicitudDetalle.setIdModelo(Long.parseLong(solicitudDetalleArray.get(i).toString()));
                solicitudDetalle.setIdSolicitudModelo(solicitud.getIdSolicitudModelo());
                solicitudModeloDetalleService.save(solicitudDetalle);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
            return "Error";
        }
        return "Success";
    }

    @PatchMapping("patchSolicitudModeloEstatus")
    public String patchSolicitudModeloEstatus(@RequestParam Long idSolicitud) {

        try {
            ComercialSolicitudModelo solicitud =solicitudModeloService.findOne(idSolicitud);
            solicitud.setEstatus("2");
            solicitudModeloService.save(solicitud);
        } catch (Exception e) {
            // TODO: handle exception
            return "Error";
        }
        return "Success";
    }
}
