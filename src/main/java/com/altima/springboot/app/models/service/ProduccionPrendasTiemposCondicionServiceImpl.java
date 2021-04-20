package com.altima.springboot.app.models.service;

import java.io.Serializable;
import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionPrendasTiemposCondicion;
import com.altima.springboot.app.repository.ProduccionPrendasTiemposCondicionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionPrendasTiemposCondicionServiceImpl implements IProduccionPrendasTiemposCondicionService{

    @Autowired
    ProduccionPrendasTiemposCondicionRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionPrendasTiemposCondicion> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(ProduccionPrendasTiemposCondicion tiempos) {
        // TODO Auto-generated method stub
        repository.save(tiempos);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ProduccionPrendasTiemposCondicion findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.getOne(id);
    }
    
}
