package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.altima.springboot.app.dto.ComercialSolicitudModeloDetalleDTO;
import com.altima.springboot.app.models.entity.ComercialSolicitudModeloDetalle;
import com.altima.springboot.app.repository.ComercialSolicitudModeloDetalleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComercialSolicitudModeloDetalleServiceImpl implements IComercialSolicitudModeloDetalleService {

    @Autowired
    ComercialSolicitudModeloDetalleRepository repository;

    @Autowired
    EntityManager em;

    @Override
    @Transactional
    public void save(ComercialSolicitudModeloDetalle solicitudModeloDetalle) {
        // TODO Auto-generated method stub
        repository.save(solicitudModeloDetalle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<ComercialSolicitudModeloDetalle> findAll() {
        // TODO Auto-generated method stub
        return (List<ComercialSolicitudModeloDetalle>) repository.findAll();
    }

    @Override
    @Transactional
    public ComercialSolicitudModeloDetalle findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<ComercialSolicitudModeloDetalleDTO> findAllByidSolicitud(Long id) {
        // TODO Auto-generated method stub
        return em.createNativeQuery("call alt_pr_modelo_solicitud("+ id +")",ComercialSolicitudModeloDetalleDTO.class).getResultList();
    }
    
}
