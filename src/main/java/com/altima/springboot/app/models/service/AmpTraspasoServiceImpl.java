package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpTraspaso;
import com.altima.springboot.app.repository.AmpTraspasoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpTraspasoServiceImpl implements IAmpTraspasoService {

    @Autowired
    AmpTraspasoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AmpTraspaso> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpTraspaso>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(AmpTraspaso traspaso) {
        // TODO Auto-generated method stub
        repository.save(traspaso);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AmpTraspaso findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }
    
}
