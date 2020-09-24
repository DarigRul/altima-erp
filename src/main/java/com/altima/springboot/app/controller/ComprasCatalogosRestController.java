package com.altima.springboot.app.controller;

import java.util.List;

import javax.persistence.PostLoad;

import com.altima.springboot.app.models.entity.ComercialLookup;
import com.altima.springboot.app.models.service.IComercialLookupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ComprasCatalogosRestController {

    @Autowired
    IComercialLookupService comercialLookupService;

    @GetMapping("getModelos")
    public List<ComercialLookup> getModelos(@RequestParam String tipoLookup){
        return comercialLookupService.findByTipoLookup(tipoLookup);
    }

    @GetMapping("getModelo")
    public ComercialLookup getModelo(@RequestParam Long id){
        ComercialLookup modelo = comercialLookupService.findOne(id);
        return modelo;
    }

    @PostMapping("postModelo")
    public String postModelo(@RequestParam String modelo) {
        //TODO: process POST request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ComercialLookup modeloLookup =new ComercialLookup();
        String[] modeloAtributos = modelo.split(",");
        modeloLookup.setCreadoPor(auth.getName());
        modeloLookup.setTipoLookup("Modelo");
        modeloLookup.setNombreLookup(modeloAtributos[0]);
        modeloLookup.setAtributo1(modeloAtributos[1]);
        modeloLookup.setAtributo2(modeloAtributos[2]);
        
        try {
            modeloLookup.setIdText("");
            comercialLookupService.save(modeloLookup);
            modeloLookup.setIdText("Mod"+(10000+modeloLookup.getIdLookup()));
            comercialLookupService.save(modeloLookup);

        } catch (Exception e) {
            //TODO: handle exception
            return "Error "+e;
        }
        return "Success";
    }

    @PatchMapping("patchModelo")
    public String  patchModelo(@RequestParam String modelo) {
        String[] modeloAtributos = modelo.split(",");
        ComercialLookup modeloLookup = comercialLookupService.findOne(Long.parseLong(modeloAtributos[0]));
        modeloLookup.setNombreLookup(modeloAtributos[1]);
        modeloLookup.setAtributo1(modeloAtributos[2]);
        modeloLookup.setAtributo2(modeloAtributos[3]);
        try {
            comercialLookupService.save(modeloLookup);
        } catch (Exception e) {
            //TODO: handle exception
            return "Error "+e;
        }
        return "Success";
    }
    
    
}
