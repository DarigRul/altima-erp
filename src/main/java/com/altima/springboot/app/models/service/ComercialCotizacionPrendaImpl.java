package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCotizacionPrenda;
import com.altima.springboot.app.repository.ComercialCotizacionPrendaRepository;

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
	public Object[] FindDatosCotizacionPrenda(Long idTela, Long idPrenda) {
		// TODO Auto-generated method stub
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
											   " AND prenda.id_prenda =" +idPrenda).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> FindCotizacionPrendas(Long id) {
		
	return em.createNativeQuery("{call alt_pr_cotizacion_prendas ("+id+")}").getResultList(); 
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
