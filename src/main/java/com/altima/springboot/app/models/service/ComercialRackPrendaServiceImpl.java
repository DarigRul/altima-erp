package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialMovimientoMuestraDetalle;
import com.altima.springboot.app.models.entity.ComercialRackPrenda;
import com.altima.springboot.app.repository.ComercialRackPrendaRepository;

@Service
@SuppressWarnings("unchecked")
public class ComercialRackPrendaServiceImpl implements IComercialRackPrendaService{

	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComercialRackPrendaRepository repository;
	
	
	public void save(ComercialRackPrenda comercialRackPrenda) {
		repository.save(comercialRackPrenda);
	}

	@Override
	@Transactional
	public List<ComercialRackPrenda> findAllbyMovimientoEstatus(Long id){
		
		return em.createNativeQuery("select muest.id_rack_prenda, \n" + 
									"		muest.cantidad,  \n" + 
									"		muest.id_movimiento,  \n" + 
									"		prenda.descripcion_prenda, \n" + 
									"		prenda.id_text as modelo_prenda, \n" + 
									"		tela.id_text as codigo_tela, \n" + 
									"		(precio.precio_muestrario*muest.cantidad) AS precio, \n" + 
									"		muest.estatus \n" + 
									"from alt_comercial_rack_prenda as muest \n" + 
									"INNER JOIN alt_disenio_prenda prenda ON muest.id_prenda = prenda.id_prenda \n" + 
									"INNER JOIN alt_disenio_tela tela ON muest.id_tela = tela.id_tela \n" + 
									"INNER JOIN alt_disenio_lista_precio_prenda precio ON prenda.id_prenda = precio.id_prenda " +
									"AND tela.id_familia_composicion = precio.id_familia_composicion \n" + 
									"INNER JOIN alt_disenio_lookup lookup ON prenda.id_familia_prenda = lookup.id_lookup\n" + 
									" \n" + 
									"where muest.id_movimiento ="+id).getResultList();
		
	}
	
	@Override
	@Transactional
	public List<Object> datosMovimiento(Long id) {

		return em.createNativeQuery("SELECT movimiento.empresa,  \r\n" + 
									"		movimiento.vendedor,  \r\n" + 
									"		 prenda.descripcion_prenda,  \r\n" + 
									"		 rackPrenda.cantidad,  \r\n" + 
									"		 rackPrenda.id_rack_prenda,  \r\n" + 
									"		 prenda.id_text AS text,  \r\n" + 
									"		 telas.id_text,  \r\n" + 
									"		 prenda.id_prenda,  \r\n" + 
									"		 telas.id_tela,  \r\n" + 
									"		 rackPrenda.id_movimiento,  \r\n" + 
									"		 movimiento.encargado  \r\n" + 
									"FROM alt_comercial_movimiento AS movimiento \r\n" + 
									"\r\n" + 
									"INNER JOIN alt_comercial_rack_prenda rackPrenda ON movimiento.id_movimiento = rackPrenda.id_movimiento \r\n" + 
									"INNER JOIN alt_disenio_prenda prenda ON rackPrenda.id_prenda = prenda.id_prenda \r\n" + 
									"INNER JOIN alt_disenio_tela telas ON rackPrenda.id_tela = telas.id_tela \r\n" + 
									"WHERE movimiento.id_movimiento ="+id).getResultList();
	}

	@Override
	public List<ComercialRackPrenda> findAllById(Long id) {

		return em.createQuery("FROM ComercialRackPrenda WHERE idMovimiento="+id).getResultList();
	}


	@Override
	public ComercialRackPrenda findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
