package com.altima.springboot.app.controller;

import java.util.List;


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


@RestController
public class ComercialCatalogosRestController {

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
        modeloLookup.setEstatus(1);
        
        try {
            modeloLookup.setIdText("");
            comercialLookupService.save(modeloLookup);
            modeloLookup.setIdText("MOD"+(10000+modeloLookup.getIdLookup()));
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
    
    @GetMapping("/bajarModelo")
    public Object bajarModelo(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup modelo = comercialLookupService.findOne(id);
        modelo.setEstatus(0);
        comercialLookupService.save(modelo);
        //System.out.println(preciobaja.getEstatus());
        return comercialLookupService.findOne(id);
        
    }

    @GetMapping("/altaModelo")
    public Object altaModelo(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup modelo = comercialLookupService.findOne(id);
        modelo.setEstatus(1);
        comercialLookupService.save(modelo);
        return comercialLookupService.findOne(id);
    }

    @GetMapping("getPrecios")
    public List<ComercialLookup> getPrecios(@RequestParam String tipoLookup){
        return comercialLookupService.findByTipoLookup(tipoLookup);
    }
    @GetMapping("getPrecio")
    public ComercialLookup getPrecio(@RequestParam Long id){
        ComercialLookup precio = comercialLookupService.findOne(id);
        return precio;
    }
    @PostMapping("postPrecio")
    public String postPrecio(@RequestParam String precio){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ComercialLookup precioLookup =new ComercialLookup();
        String[] precioAtributos = precio.split(",");
        precioLookup.setCreadoPor(auth.getName());
        precioLookup.setTipoLookup("Bordado");
        precioLookup.setNombreLookup(precioAtributos[0]);
        precioLookup.setAtributo1(precioAtributos[1]);
        precioLookup.setEstatus(1);

        try{
            precioLookup.setIdText("");
            comercialLookupService.save(precioLookup);
            precioLookup.setIdText("BORD"+(10000+precioLookup.getIdLookup()));
            comercialLookupService.save(precioLookup);
        }catch (Exception e){
            return "Error "+e;
        }
        return "success";
    }

    @GetMapping("/bajarPrecio")
    public Object bajarPrecio(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup preciobaja = comercialLookupService.findOne(id);
        preciobaja.setEstatus(0);
        comercialLookupService.save(preciobaja);
        //System.out.println(preciobaja.getEstatus());
        return comercialLookupService.findOne(id);
        
    }

    @GetMapping("/altaPrecio")
    public Object altaPrecio(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup precioalta = comercialLookupService.findOne(id);
        precioalta.setEstatus(1);
        comercialLookupService.save(precioalta);
        return comercialLookupService.findOne(id);
    }



    @PatchMapping("patchPrecio")
    public String patchPrecio(@RequestParam String precio){
        String[] precioAtributos = precio.split(",");
        ComercialLookup precioLookup = comercialLookupService.findOne(Long.parseLong(precioAtributos[0]));
        precioLookup.setNombreLookup(precioAtributos[1]);
        precioLookup.setAtributo1(precioAtributos[2]);
        try{
            comercialLookupService.save(precioLookup);
            
        }catch (Exception e){
            return "Error "+e;
        }
        return "success";

    }
    


    @GetMapping("getIvas")
    public List<ComercialLookup> getIva(@RequestParam String tipoLookup){
        return comercialLookupService.findByTipoLookup(tipoLookup);
    }
    @GetMapping("getIva")
    public ComercialLookup getIva(@RequestParam Long id){
        ComercialLookup iva = comercialLookupService.findOne(id);
        return iva;
    }
    
    @PostMapping("postIva")
    public String postIva(@RequestParam String iva){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ComercialLookup ivaLookup =new ComercialLookup();
        String[] ivaAtributos = iva.split(",");
        ivaLookup.setCreadoPor(auth.getName());
        ivaLookup.setTipoLookup("Iva");
        ivaLookup.setAtributo1(ivaAtributos[0]);
        ivaLookup.setEstatus(1);
        try{
            ivaLookup.setIdText("");
            comercialLookupService.save(ivaLookup);
            ivaLookup.setIdText("IVA"+(10000+ivaLookup.getIdLookup()));
            comercialLookupService.save(ivaLookup);
        }catch (Exception e){
            return "Error "+e;
        }
        return "success";
    }
    @GetMapping("/bajarIVA")
    public Object bajarIva(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup ivabaja = comercialLookupService.findOne(id);
        ivabaja.setEstatus(0);
        comercialLookupService.save(ivabaja);
        //System.out.println(ivabaja.getEstatus());
        return comercialLookupService.findOne(id);
        
    }

    @GetMapping("/altaIVA")
    public Object altaIva(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup ivaalta = comercialLookupService.findOne(id);
        ivaalta.setEstatus(1);
        comercialLookupService.save(ivaalta);
        return comercialLookupService.findOne(id);
    }

    @PatchMapping("patchIva")
    public String patchIva(@RequestParam String iva){
        String[] ivaAtributos = iva.split(",");
        ComercialLookup ivaLookup = comercialLookupService.findOne(Long.parseLong(ivaAtributos[0]));
        ivaLookup.setAtributo1(ivaAtributos[1]);
        try{
            comercialLookupService.save(ivaLookup);
            
        }catch (Exception e){
            return "Error "+e;
        }
        return "success";

    }
    
    
}
