package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.ProduccionMaquiladorProceso;
import com.altima.springboot.app.repository.ProduccionMaquiladorProcesoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionMaquiladorProcesoServiceImpl implements IProduccionMaquiladorProcesoService {

    @Autowired
    ProduccionMaquiladorProcesoRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionMaquiladorProceso> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionMaquiladorProceso>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(ProduccionMaquiladorProceso maquiladorProceso) {
        // TODO Auto-generated method stub
        repository.save(maquiladorProceso);

    }

    @Override
    @Transactional
    public void delete(Long idProceso,Long idMaquilador) {
        // TODO Auto-generated method stub
        repository.deleteByIdProcesoAndIdMaquilador(idProceso, idMaquilador);
    }

    @Override
    @Transactional
    public ProduccionMaquiladorProceso findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

}
