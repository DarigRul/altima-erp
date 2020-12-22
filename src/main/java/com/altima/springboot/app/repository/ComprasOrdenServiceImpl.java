package com.altima.springboot.app.repository;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.ComprasOrden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComprasOrdenServiceImpl implements IComprasOrdenService {

    @Autowired
    ComprasOrdenRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void save(ComprasOrden comprasOrden) {
        // TODO Auto-generated method stub
        repository.save(comprasOrden);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ComprasOrden> findAll() {
        // TODO Auto-generated method stub
        return (List<ComprasOrden>) repository.findAll();
    }

    @Transactional
    @Override
    public ComprasOrden findOne(Long id) {
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
