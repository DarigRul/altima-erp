package com.altima.springboot.app.controller;

import java.util.List;

import com.altima.springboot.app.models.service.IComercialAgentesVentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ExplosionMateriaPrimaRestController {
    
    @Autowired
    IComercialAgentesVentaService comercialAgentesVentaService; 

    @GetMapping("/getApartadoTelasById")
    public List<Object[]> getApartadoTelasById(@RequestParam Long idPedido) {
        return comercialAgentesVentaService.findDatosReporteApartadoTelas(idPedido,true);
    }
}
