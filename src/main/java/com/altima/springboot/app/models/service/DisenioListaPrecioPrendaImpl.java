package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bytebuddy.asm.Advice.Return;

import com.altima.springboot.app.models.entity.DisenioListaPrecioPrenda;
import com.altima.springboot.app.repository.DisenioListaPrecioPrendaRepository;

@Service
public class DisenioListaPrecioPrendaImpl implements IDisenioListaPrecioPrendaService{
	@Autowired
	private DisenioListaPrecioPrendaRepository repository;
	@Autowired
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<DisenioListaPrecioPrenda> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioListaPrecioPrenda>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(DisenioListaPrecioPrenda disenioprenda) {
		// TODO Auto-generated method stub
		repository.save(disenioprenda);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioListaPrecioPrenda findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object BuscarPrecioPrendaById(Long id) {
		
		return em.createNativeQuery("SELECT adlpp.id_lista_precio_prenda, adlpp.id_prenda, \r\n" + 
						"				adlpp.precio_local_nuevo, adlpp.precio_local_antiguo, \r\n" + 
						"				adlpp.precio_foraneo_nuevo, adlpp.precio_foraneo_antiguo, \r\n" +
						"				adlpp.precio_maquila, adlpp.precio_muestrario  \r\n" +
						"			FROM alt_disenio_lista_precio_prenda AS adlpp \r\n" +
						"			INNER JOIN alt_disenio_prenda AS adp ON adlpp.id_prenda = adp.id_prenda \r\n" +
						"			WHERE adp.id_prenda="+id).getSingleResult();
		
	}

	@Override
	@Transactional
	public DisenioListaPrecioPrenda findByidPrenda(Long id) {
		// TODO Auto-generated method stub
		DisenioListaPrecioPrenda precio=new DisenioListaPrecioPrenda();
		return repository.findByidPrenda(id)==null?precio : repository.findByidPrenda(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> listaPrecioPrenda(Long id) {
		
		return em.createNativeQuery("call alt_pr_lista_precio("+id+")").getResultList();
		
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object> listaFamPrendaByidPrenda(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT DISTINCT adlfamcomp.nombre_lookup,adlfamcomp.id_lookup FROM alt_disenio_prenda adp INNER JOIN alt_disenio_tela_prenda adtp ON adp.id_familia_prenda=adtp.id_prenda INNER JOIN alt_disenio_tela adt ON adt.id_tela=adtp.id_tela INNER JOIN alt_disenio_lookup adlfamcomp ON adlfamcomp.id_lookup=adt.id_familia_composicion WHERE adp.id_prenda="+id).getResultList();
	}

	

}
