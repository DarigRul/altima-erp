package com.altima.springboot.app.controller;

import java.util.List;

import com.altima.springboot.app.dto.UbicacionListDTO;
import com.altima.springboot.app.models.entity.AmpRolloTela;
import com.altima.springboot.app.models.service.IAmpAlmacenUbicacion;
import com.altima.springboot.app.models.service.IAmpRolloTelaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RolloRestController {
    
    @Autowired
    IAmpRolloTelaService rolloService;

    @Autowired
    IAmpAlmacenUbicacion ubicacionService;

    @GetMapping("getRolloByidAlmacenFisico")
    public List<AmpRolloTela> getRolloByidAlmacenFisico(@RequestParam Long idAlmacenFisico,@RequestParam Long idTela) {
        return rolloService.findByIdAlmacenFisico(idAlmacenFisico,idTela);
    }

    @GetMapping("getUbicacionByRollo")
    public List<UbicacionListDTO> getUbicacionByRollo(@RequestParam Long idRollo) {
        return ubicacionService.findAllByRollo(idRollo);
    }
}
