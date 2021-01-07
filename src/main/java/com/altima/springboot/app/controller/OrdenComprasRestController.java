package com.altima.springboot.app.controller;

import java.util.Formatter;

import com.altima.springboot.app.models.entity.ComprasOrden;
import com.altima.springboot.app.models.entity.ComprasOrdenDetalle;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdenComprasRestController {

    @Autowired
    IComprasOrdenService ordenService;

    @Autowired
    IComprasOrdenDetalleService ordenDetalleService;
    
    @Transactional
    @PostMapping("/postOrdenCompra")
    public ResponseEntity<?> postOrdenCompra(@RequestParam String ordenCompraDetalle,@RequestParam Long idProveedor) {
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
            ordenService.save(orden);
            orden.setIdText("ORDC"+fmt.format("%05d", orden.getIdOrdenCompras()));
            ordenService.save(orden);
            for (int i = 0; i < ordenArray.length(); i++) {
                JSONObject ordenJson = ordenArray.getJSONObject(i);
                ComprasOrdenDetalle ordenDetalle =new ComprasOrdenDetalle();
                ordenDetalle.setCreadoPor(auth.getName());
                ordenDetalle.setActualizadoPor(auth.getName());
                ordenDetalle.setTipoMaterial(ordenJson.getString("tipo"));
                ordenDetalle.setIdMaterial(ordenJson.getLong("idMaterial"));
                ordenDetalle.setIdOrdenCompras(orden.getIdOrdenCompras());
                ordenDetalle.setCantidad(ordenJson.getFloat("cantidad"));
                ordenDetalle.setPrecioUnitario(ordenJson.getFloat("precioU"));
                ordenDetalle.setMontoCargoDescuento(ordenJson.getFloat("montoCD"));
                ordenDetalle.setIva(ordenJson.getFloat("iva"));
                ordenDetalle.setIdColor(ordenJson.getLong("idColor"));
                ordenDetalleService.save(ordenDetalle);
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
