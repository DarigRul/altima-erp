package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionPrendaHistorico;

public interface IProduccionPrendaHistoricoService {

    List<ProduccionPrendaHistorico> findAll();

	void save(ProduccionPrendaHistorico historico);

	void delete(Long id);

	ProduccionPrendaHistorico findOne(Long id);

    List<ProduccionPrendaHistorico> findByIdPrenda(Long idPrenda);
    
}
