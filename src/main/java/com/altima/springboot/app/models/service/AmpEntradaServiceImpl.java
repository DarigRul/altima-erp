package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpEntrada;
import com.altima.springboot.app.repository.AmpEntradaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpEntradaServiceImpl implements IAmpEntradaService {

    @Autowired
    AmpEntradaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AmpEntrada> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpEntrada>) repository.findAll();
    }
    @Transactional
    @Override
    public void save(AmpEntrada entrada) {
        // TODO Auto-generated method stub
        repository.save(entrada);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }
    @Transactional
    @Override
    public AmpEntrada findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    
}
