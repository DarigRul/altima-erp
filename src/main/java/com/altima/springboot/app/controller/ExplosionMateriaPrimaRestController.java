package com.altima.springboot.app.controller;

import java.util.List;

import com.altima.springboot.app.dto.ExplosionTelaDto;
import com.altima.springboot.app.models.service.IAmpExplosionTelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ExplosionMateriaPrimaRestController {
    
    @Autowired
    IAmpExplosionTelaService explosionTelaService;

    @GetMapping("/getApartadoTelasById")
    public List<ExplosionTelaDto> getApartadoTelasById(@RequestParam Long idPedido) {
        return explosionTelaService.findAllExplosion(idPedido);
    }
}
