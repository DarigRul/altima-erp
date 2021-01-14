package com.altima.springboot.app.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;

import com.altima.springboot.app.models.entity.AmpTelaFaltante;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComprasOrden;
import com.altima.springboot.app.models.entity.ComprasOrdenDetalle;
import com.altima.springboot.app.models.service.IAmpTelaFaltanteService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComprasOrdenDetalleService;
import com.altima.springboot.app.repository.IComprasOrdenService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComprasRequisicionTelasRestController {

    @Autowired
    IAmpTelaFaltanteService telaFaltanteService;

    @Autowired
    ICargaPedidoService pedidoService;

    @Autowired
    IComprasOrdenService ordenService;

    @Autowired
    IComprasOrdenDetalleService ordenDetalleService;

    @Transactional
    @PostMapping("/postFechaPromesa")
    public ResponseEntity<?> postFechaPromesa(@RequestParam String telasFaltantes,
            @RequestParam Boolean checkTelaAgotada, @RequestParam String fechaPromesa) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            String[] telasFaltantesArray = telasFaltantes.split(",");
            for (String idTelaFaltante : telasFaltantesArray) {
                AmpTelaFaltante telaFaltante = telaFaltanteService.findOne(Long.parseLong(idTelaFaltante));
                telaFaltante.setActualizadoPor(auth.getName());
                if (checkTelaAgotada) {
                    telaFaltante.setEstatus(3);
                } else {
                    ComercialPedidoInformacion pedido = pedidoService.findOne(telaFaltante.getIdPedido());
                    LocalDate dateEntrega = LocalDate.parse(pedido.getFechaEntrega());
                    LocalDate datePromesa = LocalDate.parse(fechaPromesa);
                    long noOfDaysBetween = ChronoUnit.DAYS.between(datePromesa, dateEntrega);
                    System.out.println(noOfDaysBetween);
                    System.out.println(noOfDaysBetween >= 15 ? "okas" : "no okas");
                    telaFaltante.setFechaPromesa(fechaPromesa);
                    if (noOfDaysBetween >= 15) {
                        telaFaltante.setEstatus(0);
                    } else {
                        telaFaltante.setEstatus(2);
                    }
                    telaFaltanteService.save(telaFaltante);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/patchTelaFaltanteEstatus")
    public ResponseEntity<?> patchTelaFaltanteEstatus(@RequestParam Long idTelaFaltante,
            @RequestParam boolean estatusComercial) {

        try {
            AmpTelaFaltante telaFaltante = telaFaltanteService.findOne(idTelaFaltante);
            if (estatusComercial) {
                telaFaltante.setEstatusComercial(1);
            } else if (!estatusComercial) {
                telaFaltante.setEstatusComercial(2);
            }
            telaFaltanteService.save(telaFaltante);
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/postOrdenCompraTela")
    public ResponseEntity<?> postOrdenCompra(@RequestParam String ordenCompraDetalle,@RequestParam Long idProveedor,@RequestParam float iva) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONArray ordenArray = new JSONArray(ordenCompraDetalle);
        Formatter fmt = new Formatter();
        try {
            ComprasOrden orden =new ComprasOrden();
            orden.setCreadoPor(auth.getName());
            orden.setActualizadoPor(auth.getName());
            orden.setEstatus(1);
            orden.setIdProveedor(idProveedor);
            orden.setIdText("idText");
            orden.setIva(iva);
            ordenService.save(orden);
            orden.setIdText("ORDC"+fmt.format("%05d", orden.getIdOrdenCompras()));
            ordenService.save(orden);
            for (int i = 0; i < ordenArray.length(); i++) {
                JSONObject ordenJson = ordenArray.getJSONObject(i);
                ComprasOrdenDetalle ordenDetalle =new ComprasOrdenDetalle();
                ordenDetalle.setCreadoPor(auth.getName());
                ordenDetalle.setActualizadoPor(auth.getName());
                ordenDetalle.setTipoMaterial("t");
                ordenDetalle.setIdMaterial(ordenJson.getLong("idTela"));
                ordenDetalle.setIdOrdenCompras(orden.getIdOrdenCompras());
                ordenDetalle.setCantidad(ordenJson.getFloat("cantidad")+ordenJson.getFloat("cantidadExtra"));
                ordenDetalle.setPrecioUnitario(ordenJson.getFloat("precioU")+ordenJson.getFloat("montoCD"));
                ordenDetalleService.save(ordenDetalle);
                AmpTelaFaltante telaFaltante=telaFaltanteService.findOne(ordenJson.getLong("idTelaFaltante"));
                telaFaltante.setEstatus(1);
                telaFaltanteService.save(telaFaltante);
            }
            fmt.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
