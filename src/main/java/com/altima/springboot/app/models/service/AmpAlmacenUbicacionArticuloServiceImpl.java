package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpAlmacenUbicacionArticulo;
import com.altima.springboot.app.repository.AmpAlmacenUbicacionArticuloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AmpAlmacenUbicacionArticuloServiceImpl implements IAmpAlmacenUbicacionArticuloService {

    @Autowired
    AmpAlmacenUbicacionArticuloRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AmpAlmacenUbicacionArticulo> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpAlmacenUbicacionArticulo>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(AmpAlmacenUbicacionArticulo ubicacionArticulo) {
        // TODO Auto-generated method stub
        repository.save(ubicacionArticulo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AmpAlmacenUbicacionArticulo findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }
    
}
