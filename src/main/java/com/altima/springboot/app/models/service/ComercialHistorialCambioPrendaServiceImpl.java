package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialHistorialCambioPrenda;
import com.altima.springboot.app.repository.ComercialHistorialCambioPrendaRepository;


@Service
public class ComercialHistorialCambioPrendaServiceImpl implements IComercialHistorialCambioPrendaService {

	@Autowired
	ComercialHistorialCambioPrendaRepository repository;
	@Autowired
	EntityManager em;
	
	
	@Override
	public List<ComercialHistorialCambioPrenda> findAll(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ComercialHistorialCambioPrenda ComercialHistorialCambioPrenda) {
		repository.save(ComercialHistorialCambioPrenda);

	}

	@Override
	public ComercialHistorialCambioPrenda findOne(Long id) {
		// TODO Auto-generated method stub
				return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> empleadosSPF(Long idSPF) {
		List<Object[]> re= em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	empleado.id_empleado,\r\n" + 
				"	spf_empleado.nombre_empleado \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_spf_empleado AS spf_empleado,\r\n" + 
				"	alt_comercial_cliente_empleado AS empleado,\r\n" + 
				"	alt_comercial_concetrado_prenda AS consentrado,\r\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda,\r\n" + 
				"	alt_disenio_prenda AS prenda,\r\n" + 
				"	alt_disenio_lookup AS look \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND empleado.id_empleado = spf_empleado.id_empleado \r\n" + 
				"	AND consentrado.id_empleado = spf_empleado.id_empleado \r\n" + 
				"	AND consentrado.id_coordinado_prenda = coor_prenda.id_coordinado_prenda \r\n" + 
				"	AND prenda.id_prenda = coor_prenda.id_prenda \r\n" + 
				"	AND look.id_lookup = prenda.id_familia_prenda \r\n" + 
				"	AND ( look.nombre_lookup LIKE '%Falda%' || look.nombre_lookup LIKE '%Pantalon%' ) \r\n" + 
				"	AND spf_empleado.id_pedido_spf = "+idSPF+" \r\n" + 
				"GROUP BY\r\n" + 
				"	empleado.id_empleado \r\n" + 
				"ORDER BY\r\n" + 
				"	spf_empleado.nombre_empleado").getResultList();
		return re;
	}
	


@SuppressWarnings("unchecked")
@Override
@Transactional
public List<Object[]> coordinadoEmpleado(Long idEmpleado) {
	
	List<Object[]> re= em.createNativeQuery(""
			+ "SELECT\r\n" + 
			"	coor.id_coordinado,\r\n" + 
			"	coor.id_text \r\n" + 
			"FROM\r\n" + 
			"	alt_comercial_concetrado_prenda AS conse,\r\n" + 
			"	alt_comercial_coordinado_prenda AS coor_prenda,\r\n" + 
			"	alt_comercial_coordinado AS coor \r\n" + 
			"WHERE\r\n" + 
			"	1 = 1 \r\n" + 
			"	AND conse.id_coordinado_prenda = coor_prenda.id_coordinado_prenda \r\n" + 
			"	AND coor_prenda.id_coordinado = coor.id_coordinado \r\n" + 
			"	AND conse.id_empleado = "+idEmpleado+" \r\n" + 
			"GROUP BY\r\n" + 
			"	coor.id_coordinado \r\n" + 
			"ORDER BY\r\n" + 
			"	coor.id_coordinado").getResultList();
	return re;
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<Object[]> modelo(Long idEmpleado, Long idCoor) {
	
	List<Object[]> re= em.createNativeQuery(""
			+ "SELECT\r\n" + 
			"	conse.id_concentrado_prenda,\r\n" + 
			"	prenda.id_text \r\n" + 
			"FROM\r\n" + 
			"	alt_comercial_concetrado_prenda AS conse,\r\n" + 
			"	alt_comercial_coordinado_prenda AS coor_prenda,\r\n" + 
			"	alt_comercial_coordinado AS coor,\r\n" + 
			"	alt_disenio_prenda AS prenda,\r\n" + 
			"	alt_disenio_lookup AS look \r\n" + 
			"WHERE\r\n" + 
			"	1 = 1 \r\n" + 
			"	AND conse.id_coordinado_prenda = coor_prenda.id_coordinado_prenda \r\n" + 
			"	AND coor_prenda.id_coordinado = coor.id_coordinado \r\n" + 
			"	AND prenda.id_prenda = coor_prenda.id_prenda \r\n" + 
			"	AND look.id_lookup = prenda.id_familia_prenda \r\n" + 
			"	AND ( look.nombre_lookup LIKE '%Falda%' || look.nombre_lookup LIKE '%Pantalon%' ) \r\n" + 
			"	AND conse.id_empleado = "+idEmpleado+" \r\n" + 
			"	AND coor.id_coordinado = "+idCoor).getResultList();
	return re;
}

@SuppressWarnings("unchecked")
@Override
@Transactional
public List<Object[]> cambio(Long idPedido, Long idExcluir) {
	
	List<Object[]> re= em.createNativeQuery(""
			+ "SELECT\r\n" + 
			"	coor_prenda.id_coordinado_prenda,\r\n" + 
			"IF\r\n" + 
			"	( coor.id_text = 'COORSPF46', CONCAT('Extra ', prenda.id_text ), prenda.id_text ) \r\n" + 
			"FROM\r\n" + 
			"	alt_comercial_coordinado_prenda AS coor_prenda,\r\n" + 
			"	alt_comercial_coordinado AS coor,\r\n" + 
			"	alt_disenio_prenda AS prenda,\r\n" + 
			"	alt_disenio_lookup AS look \r\n" + 
			"WHERE\r\n" + 
			"	1 = 1 \r\n" + 
			"	AND coor_prenda.id_coordinado = coor.id_coordinado \r\n" + 
			"	AND prenda.id_prenda = coor_prenda.id_prenda \r\n" + 
			"	AND look.id_lookup = prenda.id_familia_prenda \r\n" + 
			"	AND ( look.nombre_lookup LIKE '%Falda%' || look.nombre_lookup LIKE '%Pantalon%' ) \r\n" + 
			"	AND coor.id_pedido = "+idPedido+" \r\n" + 
			"	AND coor_prenda.id_coordinado_prenda NOT IN ( SELECT conse.id_coordinado_prenda FROM alt_comercial_concetrado_prenda AS conse WHERE conse.id_concentrado_prenda = "+idExcluir+" ) \r\n" + 
			"ORDER BY\r\n" + 
			"	prenda.id_text").getResultList();
	return re;
}


@SuppressWarnings("unchecked")
@Override
@Transactional
public List<Object[]> vista(Long idSPF) {
	
	List<Object[]> re= em.createNativeQuery(""
			+ "SELECT\r\n" + 
			"	consentrado.id_concentrado_prenda,\r\n" + 
			"	empleado.id_text,\r\n" + 
			"	spf_empleado.nombre_empleado,\r\n" + 
			"	coor.id_text as coordinado,\r\n" + 
			"	prenda.descripcion_prenda,\r\n" + 
			"	(\r\n" + 
			"	SELECT\r\n" + 
			"		prenda2.descripcion_prenda \r\n" + 
			"	FROM\r\n" + 
			"		alt_comercial_historial_cambio_prenda AS cambio,\r\n" + 
			"		alt_comercial_coordinado_prenda AS coor_prenda2,\r\n" + 
			"		alt_disenio_prenda AS prenda2 \r\n" + 
			"	WHERE\r\n" + 
			"		1 = 1 \r\n" + 
			"		AND cambio.id_prenda = coor_prenda2.id_coordinado_prenda \r\n" + 
			"		AND coor_prenda2.id_prenda = prenda2.id_prenda \r\n" + 
			"		AND cambio.id_concentrado_prenda = consentrado.id_concentrado_prenda \r\n" + 
			"		LIMIT 1 \r\n" + 
			"	) AS cambio \r\n" + 
			"FROM\r\n" + 
			"	alt_comercial_spf_empleado AS spf_empleado,\r\n" + 
			"	alt_comercial_cliente_empleado AS empleado,\r\n" + 
			"	alt_comercial_concetrado_prenda AS consentrado,\r\n" + 
			"	alt_comercial_coordinado_prenda AS coor_prenda,\r\n" + 
			"	alt_disenio_prenda AS prenda,\r\n" + 
			"	alt_disenio_lookup AS look,\r\n" + 
			"	alt_comercial_coordinado AS coor \r\n" + 
			"WHERE\r\n" + 
			"	1 = 1 \r\n" + 
			"	AND empleado.id_empleado = spf_empleado.id_empleado \r\n" + 
			"	AND consentrado.id_empleado = spf_empleado.id_empleado \r\n" + 
			"	AND consentrado.id_coordinado_prenda = coor_prenda.id_coordinado_prenda \r\n" + 
			"	AND prenda.id_prenda = coor_prenda.id_prenda \r\n" + 
			"	AND look.id_lookup = prenda.id_familia_prenda \r\n" + 
			"	AND ( look.nombre_lookup LIKE '%Falda%' || look.nombre_lookup LIKE '%Pantalon%' ) \r\n" + 
			"	AND coor.id_coordinado = coor_prenda.id_coordinado \r\n" + 
			"	AND spf_empleado.id_pedido_spf = "+idSPF).getResultList();
	return re;
}

}
