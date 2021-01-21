package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.dto.TelaFaltanteListDto;
import com.altima.springboot.app.models.entity.AmpTelaFaltante;
import com.altima.springboot.app.repository.AmpTelaFaltanteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmpTelaFaltanteServiceImpl implements IAmpTelaFaltanteService {

    @Autowired
    AmpTelaFaltanteRepository repository;

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<AmpTelaFaltante> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpTelaFaltante>) repository.findAll();
    }

    @Override
    @Transactional
    public void save(AmpTelaFaltante tela) {
        // TODO Auto-generated method stub
        repository.save(tela);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public AmpTelaFaltante findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<TelaFaltanteListDto> findAllTelasFaltantes() {
        // TODO Auto-generated method stub
        return em.createNativeQuery("select * from `alt_view_tela_faltante`",TelaFaltanteListDto.class).getResultList();
    }

    @Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<TelaFaltanteListDto> findAllTelasFaltantes(String ids) {
		// TODO Auto-generated method stub
		String[] idsArray=ids.split(",");
		String where="";
		for (String id : idsArray) {
			where=where+" or id_tela_faltante="+id;
		}

		return em.createNativeQuery("SELECT `id_tela_faltante`,`id_text_pedido`,`id_text_tela`,`cliente`,`fecha_entrega`,`agente`,sum(`cantidad`) cantidad,`fecha_orden_compra`,`folio_orden_compra`,`fecha_pomesa`,`estatus_compra`,`estatus_comercial`,`nombre_tela`,`id_tela`,`id_proveedor`,`nombre_proveedor`,`precio` FROM `alt_view_tela_faltante` where 1=2"+where+" GROUP by `id_tela`",TelaFaltanteListDto.class).getResultList();	
	}
    
}
