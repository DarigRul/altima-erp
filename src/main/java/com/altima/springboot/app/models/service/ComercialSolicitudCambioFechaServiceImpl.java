package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.dto.ComercialSolicitudCambioFechaDTO;
import com.altima.springboot.app.models.entity.ComercialSolicitudCambioFecha;
import com.altima.springboot.app.repository.ComercialSolicitudCambioFechaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComercialSolicitudCambioFechaServiceImpl implements IComercialSolicitudCambioFechaService {

    @Autowired
    ComercialSolicitudCambioFechaRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void save(ComercialSolicitudCambioFecha solicitud) {
        // TODO Auto-generated method stub
        repository.save(solicitud);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComercialSolicitudCambioFecha> findAll() {
        // TODO Auto-generated method stub
        return (List<ComercialSolicitudCambioFecha>) repository.findAll();
    }

    @Override
    @Transactional
    public ComercialSolicitudCambioFecha findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<ComercialSolicitudCambioFechaDTO> findAllDetalle(Long idAgente) {
        // TODO Auto-generated method stub
        return em.createNativeQuery("CALL `alt_pr_solicitud_cambio_fecha`(:idAgente);",ComercialSolicitudCambioFechaDTO.class).setParameter("idAgente", idAgente).getResultList();
    }

}
