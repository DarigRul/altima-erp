package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpRolloTela;
import com.altima.springboot.app.repository.AmpRolloTelaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpRolloTelaServiceImpl implements IAmpRolloTelaService {

    @Autowired
    AmpRolloTelaRepository repository;
    

    @Override
    @Transactional(readOnly = true)
    public List<AmpRolloTela> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpRolloTela>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(AmpRolloTela rollo) {
        // TODO Auto-generated method stub
        repository.save(rollo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AmpRolloTela findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AmpRolloTela> findByIdAlmacenFisico(Long idAlmacenFisico) {
        // TODO Auto-generated method stub
        return repository.findByIdAlmacenFisicoAndEstatus(idAlmacenFisico,"1");
    }
    
}
