package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioMaterialExtraPrenda;

public interface IDisenioMaterialExtraPrendaService {
    List<DisenioMaterialExtraPrenda> findAll();

    void save(DisenioMaterialExtraPrenda materialExtraPrenda) throws Exception;
    
    void delete(Long id);
	
    DisenioMaterialExtraPrenda findOne(Long id);
    
    public List<Object[]> findAllByMaterial(Long idMaterialPrenda) ;
    
}