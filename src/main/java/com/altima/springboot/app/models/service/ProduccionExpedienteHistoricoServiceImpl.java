package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionExpedienteHistorico;
import com.altima.springboot.app.repository.ProduccionExpedienteHistoricoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionExpedienteHistoricoServiceImpl implements IProduccionExpedienteHistoricoService {

    @Autowired
    ProduccionExpedienteHistoricoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionExpedienteHistorico> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionExpedienteHistorico>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(ProduccionExpedienteHistorico historico) {
        // TODO Auto-generated method stub
        repository.save(historico);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ProduccionExpedienteHistorico findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionExpedienteHistorico> findByIdPedido(Long idPedido) {
        // TODO Auto-generated method stub
        return repository.findByIdPedido(idPedido);
    }
    
}
