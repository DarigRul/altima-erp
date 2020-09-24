package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpEntradaDetalle;
import com.altima.springboot.app.repository.AmpEntradaDetalleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpEntradaDetalleServiceImpl implements IAmpEntradaDetalleService {

    @Autowired
    AmpEntradaDetalleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AmpEntradaDetalle> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpEntradaDetalle>) repository.findAll();
    }
    @Transactional
    @Override
    public void save(AmpEntradaDetalle entradaDetalle) {
        // TODO Auto-generated method stub
        repository.save(entradaDetalle);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public AmpEntradaDetalle findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }
    
}
