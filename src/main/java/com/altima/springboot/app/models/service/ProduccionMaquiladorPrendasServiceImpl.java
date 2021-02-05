package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionMaquiladorPrendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionMaquiladorPrendasServiceImpl implements IProduccionMaquiladorPrendasService {
    
    @Autowired
    ProduccionMaquiladorPrendasRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionMaquiladorPrendas> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public void save(ProduccionMaquiladorPrendas maquiladorPrendas) {
        // TODO Auto-generated method stub
        repository.save(maquiladorPrendas);

    }

    @Override
    @Transactional
    public void delete(Long idMaquilador,Long idFamiliaPrenda) {
        // TODO Auto-generated method stub
        repository.deleteByIdMaquiladorAndIdFamiliaPrenda(idMaquilador, idFamiliaPrenda);
    }

    @Override
    @Transactional
    public ProduccionMaquiladorPrendas findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }
    
}
