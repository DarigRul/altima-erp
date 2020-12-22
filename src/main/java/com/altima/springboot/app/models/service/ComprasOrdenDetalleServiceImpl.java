package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.ComprasOrdenDetalle;
import com.altima.springboot.app.repository.ComprasOrdenDetalleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComprasOrdenDetalleServiceImpl implements IComprasOrdenDetalleService {

    @Autowired
    ComprasOrdenDetalleRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void save(ComprasOrdenDetalle comprasOrdenDetalle) {
        // TODO Auto-generated method stub
        repository.save(comprasOrdenDetalle);
    }

    @Override    
    @Transactional(readOnly = true)
    public List<ComprasOrdenDetalle> findAll() {
        // TODO Auto-generated method stub
        return (List<ComprasOrdenDetalle>) repository.findAll();
    }

    @Override
    @Transactional
    public ComprasOrdenDetalle findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }
    
}
