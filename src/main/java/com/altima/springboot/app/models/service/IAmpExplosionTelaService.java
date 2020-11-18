package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.ExplosionTelaDto;
import com.altima.springboot.app.models.entity.AmpExplosionTela;

public interface IAmpExplosionTelaService {
    
    List<AmpExplosionTela> findAll();

	void save(AmpExplosionTela explosionTela);

	void delete(Long id);

    AmpExplosionTela findOne(Long id);
    
    List<ExplosionTelaDto> findAllExplosion(Long idPedido);

}
