package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionExpedienteHistorico;

public interface IProduccionExpedienteHistoricoService {
    
    List<ProduccionExpedienteHistorico> findAll();

	void save(ProduccionExpedienteHistorico historico);

	void delete(Long id);

	ProduccionExpedienteHistorico findOne(Long id);

    List<ProduccionExpedienteHistorico> findByIdPedido(Long idPedido);
}
