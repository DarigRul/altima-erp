package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpSalida;
import com.altima.springboot.app.repository.AmpSalidaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpSalidaServiceImpl implements IAmpSalidaService {

    @Autowired
    AmpSalidaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AmpSalida> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpSalida>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(AmpSalida salida) {
        // TODO Auto-generated method stub
        repository.save(salida);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AmpSalida findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }


    
}
