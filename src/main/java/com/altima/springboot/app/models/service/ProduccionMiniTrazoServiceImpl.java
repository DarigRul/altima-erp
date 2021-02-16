package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.ProduccionMiniTrazo;
import com.altima.springboot.app.repository.ProduccionMiniTrazoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionMiniTrazoServiceImpl implements IProduccionMiniTrazoService {

    @Autowired
    ProduccionMiniTrazoRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionMiniTrazo> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionMiniTrazo>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(ProduccionMiniTrazo miniTrazo) {
        // TODO Auto-generated method stub
        repository.save(miniTrazo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ProduccionMiniTrazo findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<ProduccionMiniTrazo> findByIdPrenda(Long idPrenda) {
        // TODO Auto-generated method stub
        return repository.findByIdPrenda(idPrenda);
    }
    
}
