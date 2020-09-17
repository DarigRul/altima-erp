package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.AmpAlmacenLogico;
import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.repository.AmpMultialmacenRepository;

@Service
public class AmpMultialmacenServiceImpl implements IAmpMultialmacenService {
@Autowired 
AmpMultialmacenRepository repository;

@PersistenceContext
private EntityManager em;

@Override
@Transactional
public void save(AmpMultialmacen entity) {
	repository.save(entity);
}

@Override
@Transactional
public AmpMultialmacen findById(Long id) {
	return repository.findById(id).orElse(null);
}

@Override
@Transactional
public List<AmpMultialmacen> findAll() {
	return (List<AmpMultialmacen>) repository.findAll();
}

@Override
@Transactional
public void deleteById(Long id) {
	repository.deleteById(id);
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<AmpAlmacenLogico> findAllActiveAMPLogic() {
	return em.createQuery("From AmpAlmacenLogico where estatus='1'").getResultList();
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<Object[]> findAllAMPLogicItem(Long articulo,String tipo) {
	return em.createNativeQuery("SELECT am.id_multialmacen,al.nombre_almacen_logico,am.id_articulo FROM alt_amp_multialmacen  am, alt_amp_almacen_logico al where am.id_almacen_logico=al.id_almacen_logico and am.id_articulo="+articulo+" and am.tipo='"+tipo+"'").getResultList();
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<AmpMultialmacen> findDuplicates(String tipoPost, Long almacenLogico, Long articulo) {
	
	return em.createQuery("From AmpMultialmacen where idArticulo="+articulo+" and idAlmacenLogico="+almacenLogico+" and tipo='"+tipoPost+"'  ").getResultList();
}


}
