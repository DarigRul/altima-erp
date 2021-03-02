package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionTelaCalidad;
import com.altima.springboot.app.repository.ProduccionTelaCalidadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionTelaCalidadServiceImpl implements IProduccionTelaCalidadService {

    @Autowired
    ProduccionTelaCalidadRepository repository;

    @Override
    @Transactional
    public void save(ProduccionTelaCalidad telaCalidad) {
        // TODO Auto-generated method stub
        repository.save(telaCalidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionTelaCalidad> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionTelaCalidad>) repository.findAll();
    }

    @Override
    @Transactional
    public ProduccionTelaCalidad findOne(Long idTelaCalidad) {
        // TODO Auto-generated method stub
        return repository.findById(idTelaCalidad).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long idTelaCalidad) {
        // TODO Auto-generated method stub
        repository.deleteById(idTelaCalidad);
    }

    @Override
    @Transactional
    public ProduccionTelaCalidad findOneByIdTela(Long idTela) {
        // TODO Auto-generated method stub
        return repository.findByIdTela(idTela);
    }

}
