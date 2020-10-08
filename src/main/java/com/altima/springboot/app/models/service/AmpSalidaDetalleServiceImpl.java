package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpSalidaDetalle;
import com.altima.springboot.app.repository.AmpSalidaDetalleRepository;
import com.altima.springboot.app.repository.AmpSalidaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpSalidaDetalleServiceImpl implements IAmpSalidaDetalleService {

    @Autowired
    AmpSalidaDetalleRepository repository;

    @Override
    @Transactional
    public List<AmpSalidaDetalle> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpSalidaDetalle>) repository.findAll();
    }
    @Transactional
    @Override
    public void save(AmpSalidaDetalle salida) {
        // TODO Auto-generated method stub
        repository.save(salida);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public AmpSalidaDetalle findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }
    
}
