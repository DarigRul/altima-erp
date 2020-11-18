package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.dto.ExplosionTelaDto;
import com.altima.springboot.app.models.entity.AmpExplosionTela;
import com.altima.springboot.app.repository.AmpExplosionTelaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpExplosionTelaServiceImpl implements IAmpExplosionTelaService {

    @Autowired
    AmpExplosionTelaRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<AmpExplosionTela> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpExplosionTela>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(AmpExplosionTela explosionTela) {
        repository.save(explosionTela);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AmpExplosionTela findOne(Long id) {

        return repository.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<ExplosionTelaDto> findAllExplosion(Long idPedido) {
        // TODO Auto-generated method stub
        return em.createNativeQuery("CALL alt_pr_explosion_tela(:idPedido);",ExplosionTelaDto.class).setParameter("idPedido", idPedido).getResultList();
    }
    
}
