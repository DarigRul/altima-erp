package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.dto.ListUbicacionRolloDto;
import com.altima.springboot.app.models.entity.AmpAlmacenUbicacionArticulo;
import com.altima.springboot.app.repository.AmpAlmacenUbicacionArticuloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AmpAlmacenUbicacionArticuloServiceImpl implements IAmpAlmacenUbicacionArticuloService {

    @Autowired
    AmpAlmacenUbicacionArticuloRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<AmpAlmacenUbicacionArticulo> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpAlmacenUbicacionArticulo>) repository.findAll();
    }

    @Override
    @Transactional
    public AmpAlmacenUbicacionArticulo findByIdArticulo(Long idArticulo,String tipo) {
        // TODO Auto-generated method stub
        return repository.findByIdArticuloAndTipo(idArticulo, tipo).orElse(new AmpAlmacenUbicacionArticulo());
    }

    @Override
    @Transactional
    public void save(AmpAlmacenUbicacionArticulo ubicacionArticulo) {
        // TODO Auto-generated method stub
        repository.save(ubicacionArticulo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AmpAlmacenUbicacionArticulo findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<ListUbicacionRolloDto> findAllRollo() {
        // TODO Auto-generated method stub
        return em.createNativeQuery("CALL `alt_pr_ubicacion_rollo`();",ListUbicacionRolloDto.class).getResultList();
    }
    
}
