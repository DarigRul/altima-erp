package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.TelaFaltanteListDto;
import com.altima.springboot.app.models.entity.AmpTelaFaltante;

public interface IAmpTelaFaltanteService {

    List<AmpTelaFaltante> findAll();

    void save(AmpTelaFaltante tela);

    void delete(Long id);

    AmpTelaFaltante findOne(Long id);

    List<TelaFaltanteListDto> findAllTelasFaltantes(); 
}
