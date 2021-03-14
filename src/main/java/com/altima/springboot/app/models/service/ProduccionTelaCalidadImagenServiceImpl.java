package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.ProduccionTelaCalidadImagen;
import com.altima.springboot.app.repository.ProduccionTelaCalidadImagenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionTelaCalidadImagenServiceImpl implements IProduccionTelaCalidadImagenService {

    @Autowired
    ProduccionTelaCalidadImagenRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionTelaCalidadImagen> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionTelaCalidadImagen>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(ProduccionTelaCalidadImagen miniTrazo) {
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
    public ProduccionTelaCalidadImagen findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<ProduccionTelaCalidadImagen> findByIdTela(Long idTela) {
        // TODO Auto-generated method stub
        return repository.findByIdTela(idTela);
    }
    
}
