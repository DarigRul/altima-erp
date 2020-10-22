package com.altima.springboot.app.controller;

import java.util.Formatter;
import java.util.List;

import com.altima.springboot.app.dto.ComercialSolicitudCambioFechaDTO;
import com.altima.springboot.app.dto.PedidoInformacionDTO;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComercialSolicitudCambioFecha;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialSolicitudCambioFechaService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolicitudCambioFechaRestController {

    @Autowired
    ICargaPedidoService pedidoService;

    @Autowired
    IComercialSolicitudCambioFechaService cambioFechaService;

    @GetMapping("getPedidoByEmpleado")
    public List<PedidoInformacionDTO> getPedidoByEmpleado(@RequestParam Long idEmpleado) {
        return pedidoService.findByEmpleado(idEmpleado);
    }

    @GetMapping("getSolicitud")
    public ComercialSolicitudCambioFecha getSolicitud(@RequestParam Long idSolicitudCambioFecha) {
        return cambioFechaService.findOne(idSolicitudCambioFecha);
    }

    @PostMapping("postSolicitudCambioFecha")
    public String postSolicitudCambioFecha(@RequestParam String solicitud) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONArray solicitudArray = new JSONArray(solicitud);
        JSONObject solicitudJson = solicitudArray.getJSONObject(0);
        Formatter fmt = new Formatter();

        System.out.println(solicitud);
        try {
            ComercialSolicitudCambioFecha solicitudCambio = new ComercialSolicitudCambioFecha();
            solicitudCambio.setCreadoPor(auth.getName());
            solicitudCambio.setActualizadoPor(auth.getName());
            solicitudCambio.setIdPedidoInformacion(Long.parseLong(solicitudJson.get("selectPedido").toString()));
            solicitudCambio.setFechaNueva(solicitudJson.getString("nuevaFechaEntrega"));
            solicitudCambio.setMotivo(solicitudJson.getString("motivo"));
            solicitudCambio.setFechaAnterior(solicitudJson.getString("fechaEntrega"));
            solicitudCambio.setEstatus("0");
            solicitudCambio.setIdText("idText");
            cambioFechaService.save(solicitudCambio);
            solicitudCambio.setIdText("SOL" + fmt.format("%05d", solicitudCambio.getIdSolicitudCambioFecha()));
            cambioFechaService.save(solicitudCambio);
            fmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return "Error";
        }
        return "Success";
    }

    @PatchMapping("patchSolicitudCambioFechaAceptar")
    public String patchSolicitudCambioFechaAceptar(@RequestParam Long idSolicitudCambioFecha) {
        try {
            ComercialSolicitudCambioFecha solicitud = cambioFechaService.findOne(idSolicitudCambioFecha);
            ComercialPedidoInformacion pedido = pedidoService.findOne(solicitud.getIdPedidoInformacion());
            solicitud.setEstatus("1");
            pedido.setFechaEntrega(solicitud.getFechaNueva());
            cambioFechaService.save(solicitud);
            pedidoService.save(pedido);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return "Error";
        }
        return "Success";
    }

    @PatchMapping("patchSolicitudCambioFechaRechazar")
    public String patchSolicitudCambioFechaRechazar(@RequestParam Long idSolicitudCambioFecha) {
        try {
            ComercialSolicitudCambioFecha solicitud = cambioFechaService.findOne(idSolicitudCambioFecha);
            solicitud.setEstatus("2");
            cambioFechaService.save(solicitud);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return "Error";
        }
        return "Success";
    }

    @PatchMapping("patchSolicitudCambioFecha")
    public String patchSolicitudCambioFecha(@RequestParam Long idSolicitudCambioFecha, @RequestParam String fecha) {
        try {
            ComercialSolicitudCambioFecha solicitud = cambioFechaService.findOne(idSolicitudCambioFecha);
            solicitud.setFechaNueva(fecha);
            cambioFechaService.save(solicitud);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return "Error";
        }
        return "Success";
    }

}
