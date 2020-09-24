package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.altima.springboot.app.models.entity.ComercialLookup;
import com.altima.springboot.app.repository.ComercialLookupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ComercialLookupServiceImpl implements IComercialLookupService {

    @Autowired
    ComercialLookupRepository repository;

    @Autowired
    EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<ComercialLookup> findAll() {
        // TODO Auto-generated method stub
        return (List<ComercialLookup>) repository.findAll();
    }

    @Override
    @Transactional
    public ComercialLookup findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(ComercialLookup comercialLookup) {
        // TODO Auto-generated method stub
        repository.save(comercialLookup);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComercialLookup> findByTipoLookup(String tipoLookup) {
        // TODO Auto-generated method stub
        return repository.findByTipoLookup(tipoLookup);
    }

    


    
}
