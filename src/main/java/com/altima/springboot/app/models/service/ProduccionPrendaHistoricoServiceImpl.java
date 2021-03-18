package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionPrendaHistorico;
import com.altima.springboot.app.repository.ProduccionPrendaHistoricoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionPrendaHistoricoServiceImpl implements IProduccionPrendaHistoricoService {

    @Autowired
    private ProduccionPrendaHistoricoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionPrendaHistorico> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionPrendaHistorico>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(ProduccionPrendaHistorico historico) {
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
    public ProduccionPrendaHistorico findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionPrendaHistorico> findByIdPrenda(Long idPrenda) {
        // TODO Auto-generated method stub
        return repository.findByIdPrenda(idPrenda);
    }
    
}
