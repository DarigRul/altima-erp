package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.altima.springboot.app.dto.ComercialSolicitudModeloDTO;
import com.altima.springboot.app.models.entity.ComercialSolicitudModelo;
import com.altima.springboot.app.repository.ComercialSolicitudModeloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComercialSolicitudModeloServiceImpl implements IComercialSolicitudModeloService {

    @Autowired
    EntityManager em;

    @Autowired
    ComercialSolicitudModeloRepository repository;

    @Override
    @Transactional
    public void save(ComercialSolicitudModelo solicitudModelo) {
        repository.save(solicitudModelo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ComercialSolicitudModelo> findAll() {
        // TODO Auto-generated method stub
        return (List<ComercialSolicitudModelo>) repository.findAll();
    }

    @Override
    @Transactional
    public ComercialSolicitudModelo findOne(Long idSolicitud) {
        // TODO Auto-generated method stub
        return repository.findById(idSolicitud).orElse(null);
    }
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<ComercialSolicitudModeloDTO> findAllSolicitud() {
        // TODO Auto-generated method stub
        return em.createNativeQuery("call alt_pr_solicitud_modelo()",ComercialSolicitudModeloDTO.class).getResultList();
    }


    
}
