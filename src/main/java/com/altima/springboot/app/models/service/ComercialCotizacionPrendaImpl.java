package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCotizacionPrenda;
import com.altima.springboot.app.repository.ComercialCotizacionPrendaRepository;

@SuppressWarnings("unchecked")
@Service
public class ComercialCotizacionPrendaImpl implements IComercialCotizacionPrendaService {

	@Autowired
	private ComercialCotizacionPrendaRepository repository;
	@Autowired
	private EntityManager em;
	
	@Override
	public void save(ComercialCotizacionPrenda comercialCotizacionPrenda) {
		repository.save(comercialCotizacionPrenda);
		
	}
	@Override
	@Transactional
	public ComercialCotizacionPrenda findOne (Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void removePrendas (List<ComercialCotizacionPrenda> comercialCotizacionPrenda) {
		repository.deleteAll(comercialCotizacionPrenda);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ComercialCotizacionPrenda> findAll(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Object[] FindDatosCotizacionPrenda(Long idTela, Long idModelo, Long idPrenda) {
		// TODO Auto-generated method stub
		if(idModelo==null) {
			return (Object[]) em.createNativeQuery("SELECT lookup.nombre_lookup AS nombreComposicion,\n" + 
												   "	\"\" AS color,\n" + 
												   "	lookupPrenda.nombre_lookup AS nombrePrenda,\n" + 
												   "	precio.id_prenda,\n" + 
												   "	precio.id_familia_composicion,\n" + 
												   "	precio.precio\n" + 
												   "FROM alt_disenio_precio_composicion AS precio\n" + 
												   "INNER JOIN alt_disenio_lookup lookup ON lookup.id_lookup = precio.id_familia_composicion \n" + 
												   "INNER JOIN alt_disenio_lookup lookupPrenda ON precio.id_prenda = lookupPrenda.id_lookup \n" + 
												   "WHERE 1=1  \n" + 
												   "AND precio.id_familia_composicion ="+idTela+"\n" + 
												   "AND precio.id_prenda ="+idPrenda).getSingleResult();
		}
		else {
	
			return (Object[]) em.createNativeQuery("SELECT lookup.nombre_lookup,\n" + 
												   "	   tela.color,\n" + 
												   "	   listaprenda.precio_local_nuevo,\n" + 
												   " 	   listaprenda.precio_foraneo_nuevo,\n" + 
												   "	   listaprenda.precio_local_antiguo,\n" + 
												   "	   listaprenda.precio_foraneo_antiguo,\n" + 
												   "	   listaprenda.id_prenda,\n" + 
												   "	   listaprenda.id_familia_composicion\n" + 
												   "FROM alt_disenio_tela AS tela\n" + 
												   " INNER JOIN alt_disenio_lista_precio_prenda listaprenda ON tela.id_familia_composicion = listaprenda.id_familia_composicion\n" + 
												   " INNER JOIN alt_disenio_prenda prenda ON listaprenda.id_prenda = prenda.id_prenda\n" + 
												   " INNER JOIN alt_disenio_lookup lookup ON lookup.id_lookup = listaprenda.id_familia_composicion\n" + 
												   " WHERE 1=1 \n" + 
												   " AND tela.id_tela ="+idTela+"\n" + 
												   " AND prenda.id_prenda =" +idModelo).getSingleResult();
			}
	}
	
	@Override
	@Transactional
	public List<Object[]> FindCotizacionPrendas(Long id, int tipoCotizacion) {
		if(tipoCotizacion==1) {
			return em.createNativeQuery("SELECT cotizacionPrenda.id_cotizacion_prenda,  \n" + 
										"		cotizacionPrenda.id_familia_prenda,  \n" + 
										"		lookupPrenda.nombre_lookup AS Familia,  \n" + 
										"		'' as idPrenda,  \n" + 
										"		'' as nombrePrenda,  \n" + 
										"		lookup.id_lookup,  \n" + 
										"		'' AS nombreTela,  \n" + 
										"		lookup.nombre_lookup AS composicion, \n" + 
										"		'' AS color, \n" + 
										"		cotizacionPrenda.coordinado, \n" + 
										"		cotizacionPrenda.cantidad, \n" + 
										"		precio.precio AS precio1, \n" + 
										"		precio.precio AS precio2, \n" + 
										"		precio.precio AS precio3, \n" + 
										"		precio.precio AS precio4, \n" + 
										"		cotizacionPrenda.precio_bordado, \n" + 
										"		cotizacionPrenda.porcentaje_adicional, \n" + 
										"		cotizacionPrenda.monto_adicional, \n" + 
										"		cotizacionPrenda.precio_unitario_final, \n" + 
										"		cotizacionPrenda.importe, \n" + 
										"		lookupPrenda.nombre_lookup AS nombreFamPrenda \n" + 
										"FROM alt_comercial_cotizacion_prenda AS cotizacionPrenda \n" + 
										"\n" + 
										"INNER JOIN alt_disenio_lookup lookup ON lookup.id_lookup = cotizacionPrenda.id_tela \n" + 
										"INNER JOIN alt_disenio_precio_composicion precio ON lookup.id_lookup = precio.id_familia_composicion \n" + 
										"																								AND cotizacionPrenda.id_familia_prenda = precio.id_prenda\n" + 
										"INNER JOIN alt_disenio_lookup lookupPrenda ON cotizacionPrenda.id_familia_prenda = lookupPrenda.id_lookup \n" + 
										"\n" + 
										"WHERE 1=1 \n" + 
										"AND cotizacionPrenda.id_cotizacion="+id+" \n" + 
										"\n" + 
										"ORDER BY cotizacionPrenda.coordinado ASC").getResultList();
		}
		else {
			return em.createNativeQuery("{call alt_pr_cotizacion_prendas ("+id+")}").getResultList();
		}
	}
	
	@Override
	@Transactional
	public List<Object[]> findPrendasByCotizacion(Long id) {

		return em.createNativeQuery("SELECT * FROM alt_comercial_cotizacion_prenda\r\n" + 
									"WHERE id_cotizacion="+id).getResultList();
	}
	
	@Override
	@Transactional
	public double findSubtotalCotizacionPrendas(Long id) {

		return Double.parseDouble(em.createNativeQuery("SELECT SUM(importe) FROM alt_comercial_cotizacion_prenda\r\n" + 
									"WHERE id_cotizacion="+id).getSingleResult().toString());
	}
	
	@Override
	@Transactional
	public double subtotalPrendas (Long idCotizacion) {
		return Double.parseDouble(em.createQuery("SELECT SUM(importe) FROM ComercialCotizacionPrenda WHERE idCotizacion="+idCotizacion).getSingleResult().toString());
	}
	
}
