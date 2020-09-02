package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.DisenioPrecioComposicion;
import com.altima.springboot.app.repository.DisenioPrecioComposicionRepository;

@Service
@SuppressWarnings("unchecked")
public class DisenioPrecioComposicionServiceImpl implements IDisenioPrecioComposicionService {

	@Autowired
	private DisenioPrecioComposicionRepository repository;
	@Autowired
	private EntityManager em;
	
	@Override
	public void save(DisenioPrecioComposicion disenioComposicion) {
		repository.save(disenioComposicion);
		
	}

	
	@Override
	public List<Object[]> findAll() {
		
		return em.createNativeQuery("SELECT precioCompos.id_precio_composicion, \r\n" + 
									"		prenda.nombre_lookup AS nombrePrenda, \r\n" + 
									"		lookup.nombre_lookup AS nombreComposicion, \r\n" +
									"		precioCompos.precio, \r\n" +
									"		precioCompos.creado_por,\r\n" +
									"		precioCompos.actualizado_por,\r\n" +
									"		precioCompos.fecha_creacion,\r\n" +
									"		precioCompos.ultima_fecha_modificacion,\r\n" +
									"		precioCompos.estatus,\r\n" +
									"  		precioCompos.id_prenda,\r\n" +
									"		precioCompos.id_familia_composicion\r\n" +
									"FROM alt_disenio_precio_composicion AS precioCompos\r\n" + 
									"INNER JOIN alt_disenio_lookup prenda ON precioCompos.id_prenda = prenda.id_lookup\r\n" + 
									"INNER JOIN alt_disenio_lookup lookup ON precioCompos.id_familia_composicion = lookup.id_lookup").getResultList();
	}

	@Override
	public DisenioPrecioComposicion findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	public void deleteOne(DisenioPrecioComposicion disenioComposicion) {
		// TODO Auto-generated method stub
		repository.delete(disenioComposicion);
	}
	

}
