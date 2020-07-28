package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioMaterialExtraPrenda;
import com.altima.springboot.app.repository.DisenioMaterialExtraPrendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisenioMaterialExtraPrendaServiceImpl implements IDisenioMaterialExtraPrendaService {
    @Autowired
    DisenioMaterialExtraPrendaRepository repository;
    @Autowired
    EntityManager em;

    @Override
    @Transactional
    public List<DisenioMaterialExtraPrenda> findAll() {
        // TODO Auto-generated method stub
        return (List<DisenioMaterialExtraPrenda>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(DisenioMaterialExtraPrenda materialExtraPrenda) throws Exception{
        // TODO Auto-generated method stub
        repository.save(materialExtraPrenda);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public DisenioMaterialExtraPrenda findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<Object[]> findAllByMaterial(Long idMaterialPrenda) {
        // TODO Auto-generated method stub
        return em.createNativeQuery("call alt_pr_material_extra("+idMaterialPrenda+")").getResultList();
    }
    
}