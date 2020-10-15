package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;
import com.altima.springboot.app.repository.AmpTraspasoDetalleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AmpTraspasoDetalleServiceImpl implements IAmpTraspasoDetalleService {

    @Autowired
    AmpTraspasoDetalleRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AmpTraspasoDetalle> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpTraspasoDetalle>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(AmpTraspasoDetalle traspasoDetalle) {
        // TODO Auto-generated method stub
        repository.save(traspasoDetalle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AmpTraspasoDetalle findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

}
