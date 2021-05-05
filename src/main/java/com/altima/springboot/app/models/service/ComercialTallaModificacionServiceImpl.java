package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.ModificacionDto;
import com.altima.springboot.app.models.entity.ComercialTallaModificacion;
import com.altima.springboot.app.repository.ComercialTallaModificacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ComercialTallaModificacionServiceImpl implements IComercialTallaModificacionService{

    @Autowired
    ComercialTallaModificacionRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ComercialTallaModificacion> findAll(Long id) {
        // TODO Auto-generated method stub
        return (List<ComercialTallaModificacion>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(ComercialTallaModificacion modificacion) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (modificacion.getId()==null) {
            modificacion.setCreadoPor(auth.getName());
            modificacion.setActualizadoPor(auth.getName());
        }
        else{
            modificacion.setUltimaFechaModificacion(null);
            modificacion.setActualizadoPor(auth.getName());
        }
        repository.save(modificacion);
        // repository.save(modificacion);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ComercialTallaModificacion findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ModificacionDto> findModificacionesByidConcentradoTalla(Long idConcentradoTalla) {
        // TODO Auto-generated method stub
        return repository.findModificacionesByIdConcentradoTalla(idConcentradoTalla);
    }
    
}
