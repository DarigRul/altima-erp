package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionExpedienteHistorico;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionExpedienteHistoricoRepository extends CrudRepository<ProduccionExpedienteHistorico,Long>{

    List<ProduccionExpedienteHistorico> findByIdPedido(Long idPedido);
}
