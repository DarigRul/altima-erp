package com.altima.springboot.app.models.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialmagenInventario;
import com.altima.springboot.app.models.entity.HrPersona;
import com.altima.springboot.app.models.entity.HrSolicitud;
import com.altima.springboot.app.repository.ComercialInventarioImagenRepository;
import com.altima.springboot.app.repository.ComercialInventarioRepository;


@Service
public class IComercialInventarioServiceImpl  implements IComercialImagenInventarioService {
	
	@Autowired
	private  ComercialInventarioImagenRepository repository;
	
	@Autowired
	private EntityManager em;
	
	
	
	@Override
	@Transactional
	public ComercialmagenInventario findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	
	
	@Override
	@Transactional
	public void save(ComercialmagenInventario imagenInventario) {
		// TODO Auto-generated method stub
		repository.save(imagenInventario);

	}
	
	
	
	@Override
	@Transactional
	public Object findImagen(Long idPrenda, Long idTela){
		return em.createNativeQuery("SELECT * FROM alt_comercial_imagen_inventario WHERE id_prenda="+idPrenda+" and id_tela="+idTela).getSingleResult();
	}
	
	
	
	@Override
	@Transactional
	public  ComercialmagenInventario findByidPrendaAndidTela(Long idprenda, Long idTela){
		
	  boolean validacion=em.createQuery(" from ComercialmagenInventario WHERE idPrenda="+idprenda+" and idTela="+idTela).getResultList().isEmpty();
		
		return validacion?new ComercialmagenInventario():repository.findByIdPrendaAndIdTela(idprenda, idTela);
	}
	
	
	
	
	
	
	
	

}
