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
	public Object[] FindDatosCotizacionPrenda(Long idTela, Long idModelo, Long idPrenda, Long idFamComposicion) {
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
			if(idTela==null) {
				return (Object[]) em.createNativeQuery("SELECT lookup.nombre_lookup, \n" + 
						   "		 'Por definir', \n" + 
						   "		 listaprenda.precio_local_nuevo, \n" + 
						   "			 listaprenda.precio_foraneo_nuevo, \n" + 
						   "		 listaprenda.precio_local_antiguo, \n" + 
						   "		 listaprenda.precio_foraneo_antiguo, \n" + 
						   "		 listaprenda.id_prenda, \n" + 
						   "		 listaprenda.id_familia_composicion \n" + 
						   " FROM alt_disenio_lista_precio_prenda AS listaprenda \n" + 
						   "	INNER JOIN alt_disenio_tela tela ON tela.id_familia_composicion = listaprenda.id_familia_composicion \n" + 
						   "	INNER JOIN alt_disenio_prenda prenda ON listaprenda.id_prenda = prenda.id_prenda \n" + 
						   "	INNER JOIN alt_disenio_lookup lookup ON lookup.id_lookup = listaprenda.id_familia_composicion \n" + 
						   "	WHERE 1=1 \n" +
						   " AND listaprenda.id_familia_composicion ="+idFamComposicion+" AND prenda.id_prenda =" +idModelo+"\n" +
						   "GROUP BY prenda.id_prenda").getSingleResult();
			}
			else {
				return (Object[]) em.createNativeQuery("SELECT lookup.nombre_lookup, \n" + 
						   "		 tela.color, \n" + 
						   "		 listaprenda.precio_local_nuevo, \n" + 
						   "			 listaprenda.precio_foraneo_nuevo, \n" + 
						   "		 listaprenda.precio_local_antiguo, \n" + 
						   "		 listaprenda.precio_foraneo_antiguo, \n" + 
						   "		 listaprenda.id_prenda, \n" + 
						   "		 listaprenda.id_familia_composicion \n" + 
						   " FROM alt_disenio_lista_precio_prenda AS listaprenda \n" + 
						   "	INNER JOIN alt_disenio_tela tela ON tela.id_familia_composicion = listaprenda.id_familia_composicion \n" + 
						   "	INNER JOIN alt_disenio_prenda prenda ON listaprenda.id_prenda = prenda.id_prenda \n" + 
						   "	INNER JOIN alt_disenio_lookup lookup ON lookup.id_lookup = listaprenda.id_familia_composicion \n" + 
						   "	WHERE 1=1 \n" +
						   " AND tela.id_tela ="+idTela+" AND prenda.id_prenda =" +idModelo).getSingleResult();
			}
			
		}
	}
	
	@Override
	@Transactional
	public List<Object[]> FindCotizacionPrendas(Long id, int tipoCotizacion) {
		if(tipoCotizacion==1 || tipoCotizacion==3) {
			return em.createNativeQuery("SELECT cotizacionPrenda.id_cotizacion_prenda,  \n" + 
										"		cotizacionPrenda.id_familia_prenda,  \n" + 
										"		lookupPrenda.nombre_lookup AS Familia,  \n" + 
										"		'' as idPrenda,  \n" + 
										"		'' as nombrePrenda,  \n" + 
										"		cotizacionPrenda.id_tela,  \n" + 
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
										"		cotizacionPrenda.id_familia_composicion \n" + 
										"FROM alt_comercial_cotizacion_prenda AS cotizacionPrenda \n" + 
										"\n" + 
										"INNER JOIN alt_disenio_lookup lookup ON lookup.id_lookup = cotizacionPrenda.id_familia_composicion \n" + 
										"INNER JOIN alt_disenio_precio_composicion precio ON lookup.id_lookup = precio.id_familia_composicion \n" + 
										"												AND cotizacionPrenda.id_familia_prenda = precio.id_prenda\n" + 
										"INNER JOIN alt_disenio_lookup lookupPrenda ON cotizacionPrenda.id_familia_prenda = lookupPrenda.id_lookup \n" + 
										"\n" + 
										"WHERE 1=1 \n" + 
										"AND cotizacionPrenda.id_cotizacion="+id+" \n" + 
										"\n" + 
										"ORDER BY cotizacionPrenda.coordinado ASC").getResultList();
		}
		else {
			return em.createNativeQuery("SELECT cotizacionPrenda.id_cotizacion_prenda,\n" + 
					"cotizacionPrenda.id_familia_prenda,\n" + 
					"lookup.nombre_lookup AS Familia,\n" + 
					"cotizacionPrenda.id_prenda,\n" + 
					"CONCAT(prenda.id_text,' ', prenda.descripcion_prenda) as nombrePrenda,\n" + 
					"cotizacionPrenda.id_tela, \n" + 
					"CONCAT(tela.id_text,' ', tela.nombre_tela) AS nombreTela,\n" + 
					"\n" + 
					"(SELECT lookup.nombre_lookup from alt_comercial_cotizacion_prenda AS cotiPrenda,\n" + 
					"alt_disenio_lookup AS lookup,\n" + 
					"alt_disenio_tela AS tela\n" + 
					"where (cotiPrenda.id_tela=tela.id_tela OR cotiPrenda.id_familia_composicion=tela.id_familia_composicion)\n" + 
					"AND tela.id_familia_composicion = lookup.id_lookup\n" + 
					"AND cotiPrenda.id_cotizacion_prenda=cotizacionPrenda.id_cotizacion_prenda\n" + 
					"GROUP BY cotiPrenda.id_cotizacion_prenda) AS composicion,\n" + 
					"\n" + 
					"(SELECT tela.color from alt_comercial_cotizacion_prenda AS cotiPrenda,\n" + 
					"alt_disenio_lookup AS lookup,\n" + 
					"alt_disenio_tela AS tela\n" + 
					"where cotiPrenda.id_tela=tela.id_tela\n" + 
					"AND tela.id_familia_composicion = lookup.id_lookup\n" + 
					"AND cotiPrenda.id_cotizacion_prenda=cotizacionPrenda.id_cotizacion_prenda) AS color,\n" + 
					"\n" + 
					"cotizacionPrenda.coordinado,\n" + 
					"cotizacionPrenda.cantidad,\n" + 
					"\n" + 
					"(SELECT listaprenda.precio_local_nuevo FROM alt_comercial_cotizacion_prenda AS cotiPrenda\n" + 
					"INNER JOIN alt_disenio_tela tela ON cotiPrenda.id_tela = tela.id_tela OR cotiPrenda.id_familia_composicion = tela.id_familia_composicion\n" + 
					"INNER JOIN alt_disenio_prenda prenda ON cotiPrenda.id_prenda = prenda.id_prenda\n" + 
					"INNER JOIN alt_disenio_lista_precio_prenda listaprenda ON tela.id_familia_composicion = listaprenda.id_familia_composicion\n" + 
					"AND prenda.id_prenda = listaprenda.id_prenda\n" + 
					"INNER JOIN alt_disenio_lookup lookup ON listaprenda.id_familia_composicion = lookup.id_lookup\n" + 
					"where cotiPrenda.id_cotizacion_prenda=cotizacionPrenda.id_cotizacion_prenda GROUP BY cotiPrenda.id_tela) AS precio_nuevo_local,\n" + 
					"\n" + 
					"(SELECT listaprenda.precio_foraneo_nuevo FROM alt_comercial_cotizacion_prenda AS cotiPrenda\n" + 
					"INNER JOIN alt_disenio_tela tela ON cotiPrenda.id_tela = tela.id_tela OR cotiPrenda.id_familia_composicion = tela.id_familia_composicion\n" + 
					"INNER JOIN alt_disenio_prenda prenda ON cotiPrenda.id_prenda = prenda.id_prenda\n" + 
					"INNER JOIN alt_disenio_lista_precio_prenda listaprenda ON tela.id_familia_composicion = listaprenda.id_familia_composicion\n" + 
					"AND prenda.id_prenda = listaprenda.id_prenda\n" + 
					"INNER JOIN alt_disenio_lookup lookup ON listaprenda.id_familia_composicion = lookup.id_lookup\n" + 
					"where cotiPrenda.id_cotizacion_prenda=cotizacionPrenda.id_cotizacion_prenda GROUP BY cotiPrenda.id_tela) AS precio_nuevo_foraneo,\n" + 
					"\n" + 
					"(SELECT listaprenda.precio_local_antiguo FROM alt_comercial_cotizacion_prenda AS cotiPrenda\n" + 
					"INNER JOIN alt_disenio_tela tela ON cotiPrenda.id_tela = tela.id_tela OR cotiPrenda.id_familia_composicion = tela.id_familia_composicion\n" + 
					"INNER JOIN alt_disenio_prenda prenda ON cotiPrenda.id_prenda = prenda.id_prenda\n" + 
					"INNER JOIN alt_disenio_lista_precio_prenda listaprenda ON tela.id_familia_composicion = listaprenda.id_familia_composicion\n" + 
					"AND prenda.id_prenda = listaprenda.id_prenda\n" + 
					"INNER JOIN alt_disenio_lookup lookup ON listaprenda.id_familia_composicion = lookup.id_lookup\n" + 
					"where cotiPrenda.id_cotizacion_prenda=cotizacionPrenda.id_cotizacion_prenda GROUP BY cotiPrenda.id_tela) AS precio_local_antiguo,\n" + 
					"\n" + 
					"(SELECT listaprenda.precio_foraneo_antiguo FROM alt_comercial_cotizacion_prenda AS cotiPrenda\n" + 
					"INNER JOIN alt_disenio_tela tela ON cotiPrenda.id_tela = tela.id_tela OR cotiPrenda.id_familia_composicion = tela.id_familia_composicion\n" + 
					"INNER JOIN alt_disenio_prenda prenda ON cotiPrenda.id_prenda = prenda.id_prenda\n" + 
					"INNER JOIN alt_disenio_lista_precio_prenda listaprenda ON tela.id_familia_composicion = listaprenda.id_familia_composicion\n" + 
					"AND prenda.id_prenda = listaprenda.id_prenda\n" + 
					"INNER JOIN alt_disenio_lookup lookup ON listaprenda.id_familia_composicion = lookup.id_lookup\n" + 
					"where cotiPrenda.id_cotizacion_prenda=cotizacionPrenda.id_cotizacion_prenda GROUP BY cotiPrenda.id_tela) AS precio_foraneo_antiguo,\n" + 
					"\n" + 
					"cotizacionPrenda.precio_bordado,\n" + 
					"cotizacionPrenda.porcentaje_adicional,\n" + 
					"cotizacionPrenda.monto_adicional,\n" + 
					"cotizacionPrenda.precio_unitario_final,\n" + 
					"cotizacionPrenda.importe,\n" + 
					"cotizacionPrenda.id_familia_composicion\n" + 
					"\n" + 
					"FROM alt_comercial_cotizacion_prenda AS cotizacionPrenda,\n" + 
					"alt_disenio_lookup AS lookup,\n" + 
					"alt_disenio_prenda AS prenda, \n" + 
					"alt_disenio_tela AS tela\n" + 
					"\n" + 
					"WHERE 1=1\n" + 
					"AND cotizacionPrenda.id_cotizacion="+id+"\n" + 
					"AND cotizacionPrenda.id_familia_prenda= lookup.id_lookup\n" + 
					"AND cotizacionPrenda.id_prenda=prenda.id_prenda\n" + 
					"AND (cotizacionPrenda.id_tela=tela.id_tela OR cotizacionPrenda.id_familia_composicion = tela.id_familia_composicion)\n" + 
					"\n" + 
					"GROUP BY cotizacionPrenda.id_cotizacion_prenda\n" + 
					"ORDER BY cotizacionPrenda.coordinado ASC").getResultList();
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
