package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.TiempoCantidadProcesoDto;
import com.altima.springboot.app.dto.TiemposProcesosDto;
import com.altima.springboot.app.models.entity.ProduccionTiempoFamiliaPrenda;
import com.altima.springboot.app.repository.ProduccionTiempoFamiliaPrendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionTiempoFamiliaPrendaServiceImpl implements IProduccionTiempoFamiliaPrendaService {

    @Autowired
    ProduccionTiempoFamiliaPrendaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ProduccionTiempoFamiliaPrenda> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionTiempoFamiliaPrenda>) repository.findAll();
    }

    @Override
    @Transactional
    public ProduccionTiempoFamiliaPrenda findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(ProduccionTiempoFamiliaPrenda produccionTiempoFamiliaPrenda) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // TODO Auto-generated method stub
        if (produccionTiempoFamiliaPrenda.getIdTiempoFamiliaPrenda()==null) {
            produccionTiempoFamiliaPrenda.setCreadoPor(auth.getName());
            produccionTiempoFamiliaPrenda.setActualizadoPor(auth.getName());
        }
        else{
            produccionTiempoFamiliaPrenda.setUltimaFechaModificacion(null);
            produccionTiempoFamiliaPrenda.setActualizadoPor(auth.getName());
        }
        repository.save(produccionTiempoFamiliaPrenda);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiemposProcesosDto> findTiempoFamiliaPrenda() {
        return repository.findTiempoFamiliaPrenda();
    }

}
