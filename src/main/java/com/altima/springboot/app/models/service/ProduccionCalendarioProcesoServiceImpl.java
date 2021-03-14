package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.ProduccionCalendarioProceso;
import com.altima.springboot.app.repository.ProduccionCalendarioProcesoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionCalendarioProcesoServiceImpl implements IProduccionCalendarioProcesoService{

    @Autowired
    ProduccionCalendarioProcesoRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void save(ProduccionCalendarioProceso calendarioProceso) {
        repository.save(calendarioProceso);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);  
    }

    @Override
    @Transactional
    public ProduccionCalendarioProceso findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ProduccionCalendarioProceso findByIdCalendarioFechaAndIdProceso(Long idCalendarioFecha, Long idProceso) {
        // TODO Auto-generated method stub
        return repository.findByIdCalendarioFechaAndIdProceso(idCalendarioFecha, idProceso).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionCalendarioProceso> findAll(Long id) {
        // TODO Auto-generated method stub
        return (List<ProduccionCalendarioProceso>) repository.findAll();
    }
    
}
