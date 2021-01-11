package com.altima.springboot.app.models.service;

import java.util.List;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.ProduccionFechaCoordinadoPrenda;
import com.altima.springboot.app.repository.ProduccionFechaCoordinadoPrendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TiempoCorteServiceImpl implements ITiempoCorteService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
    private ProduccionFechaCoordinadoPrendaRepository repository;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> view() {
		// nueva query holaa
		List<Object[]> re = null;
		re = em.createNativeQuery("" + "SELECT\r\n" + "	COOR_PRENDA.folio,\r\n" + "	LOOKUP.nombre_lookup,\r\n"
				+ "	PRENDA.descripcion_prenda,\r\n" + "	TELA.estampado,\r\n"
				+ "	(SELECT (SUM(conse.cantidad)+SUM(conse.cantidad_especial)) FROM alt_comercial_concetrado_prenda AS conse, alt_comercial_coordinado_prenda as cp \r\n"
				+ "				 WHERE 1=1 AND conse.id_coordinado_prenda = cp.id_coordinado_prenda ANd cp.folio = COOR_PRENDA.folio) AS Confeccion,\r\n"
				+ "				 \r\n"
				+ "	( SELECT COUNT( cp.id_coordinado_prenda ) FROM alt_comercial_coordinado_prenda AS cp WHERE cp.folio = COOR_PRENDA.folio ) AS OP,\r\n"
				+ "	\r\n"
				+ "	(SELECT COUNT(DISTINCT tallas.id_talla) FROM alt_comercial_concentrado_tallas AS tallas, alt_comercial_coordinado_prenda AS cp WHERE\r\n"
				+ "			tallas.id_prenda_cliente = cp.id_coordinado_prenda AND cp.folio = COOR_PRENDA.folio ) AS tallas,\r\n"
				+ "			\r\n"
				+ "	( SELECT SUM( cp.tiempo ) FROM alt_comercial_coordinado_prenda AS cp WHERE cp.folio = COOR_PRENDA.folio ) AS tiempo, \r\n"
				+ "( SELECT SUM( cp.tiempo ) FROM alt_comercial_coordinado_prenda AS cp WHERE cp.folio = COOR_PRENDA.folio and cp.tiempo is not null and cp.tiempo != 0) AS validacion\r\n"
				+ "FROM\r\n" + "	alt_comercial_coordinado_prenda AS COOR_PRENDA\r\n"
				+ "	INNER JOIN alt_disenio_tela TELA ON TELA.id_tela = COOR_PRENDA.id_tela\r\n"
				+ "	INNER JOIN alt_disenio_prenda PRENDA ON PRENDA.id_prenda = COOR_PRENDA.id_prenda\r\n"
				+ "	INNER JOIN alt_disenio_lookup LOOKUP ON LOOKUP.id_lookup = PRENDA.id_familia_prenda\r\n"
				+ "	INNER JOIN alt_view_apartado_telas_reporte reporte ON reporte.id_coordinado_prenda = COOR_PRENDA.id_coordinado_prenda \r\n"
				+ "WHERE\r\n" + "	1 = 1 \r\n"
				+ "	AND ( COOR_PRENDA.folio IS NOT NULL OR COOR_PRENDA.folio != '' ) \r\n"
				+ "	AND reporte.Principal_Combinacion = 'Principal' \r\n" + "GROUP BY\r\n" + "	COOR_PRENDA.folio")
				.getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> detallesFolio(String folio) {

		List<Object[]> re = null;

		re = em.createNativeQuery("" + "SELECT\r\n" + "COO_PRENDA.id_coordinado_prenda,\r\n" + "PEDIDO.id_text,\r\n"
				+ "PEDIDO.fecha_entrega,\r\n" + "COOR.numero_coordinado,\r\n"
				+ "(SELECT ( SUM( conse.cantidad ) + SUM( conse.cantidad_especial ))  \r\n" + "FROM\r\n"
				+ "alt_comercial_concetrado_prenda AS conse \r\n" + "WHERE\r\n"
				+ "conse.id_coordinado_prenda = COO_PRENDA.id_coordinado_prenda ) AS num,\r\n"
				+ "TELA.id_text AS idTexTELA, \r\n" + "COO_PRENDA.tiempo AS tiempo \r\n" + "FROM\r\n"
				+ "alt_comercial_pedido_informacion AS PEDIDO\r\n"
				+ "INNER JOIN alt_comercial_coordinado COOR ON PEDIDO.id_pedido_informacion = COOR.id_pedido\r\n"
				+ "INNER JOIN alt_comercial_coordinado_prenda COO_PRENDA ON COO_PRENDA.id_coordinado = COOR.id_coordinado\r\n"
				+ "INNER JOIN alt_disenio_prenda PRENDA ON PRENDA.id_prenda = COO_PRENDA.id_prenda\r\n"
				+ "INNER JOIN alt_disenio_lookup LOOK_PRENDA ON PRENDA.id_familia_prenda = LOOK_PRENDA.id_lookup\r\n"
				+ "LEFT JOIN alt_produccion_lookup LOOK_RUTA ON COO_PRENDA.id_ruta = LOOK_RUTA.id_lookup\r\n"
				+ "INNER JOIN alt_disenio_tela TELA ON COO_PRENDA.id_tela = TELA.id_tela\r\n"
				+ "INNER JOIN alt_disenio_lookup LOOK_TELA ON LOOK_TELA.id_lookup = TELA.id_familia_composicion\r\n"
				+ "INNER JOIN alt_view_apartado_telas_reporte reporte ON reporte.id_coordinado_prenda = COO_PRENDA.id_coordinado_prenda\r\n"
				+ "WHERE\r\n" + "1 = 1\r\n" + "AND COO_PRENDA.folio= '" + folio + "'\r\n"
				+ "GROUP BY id_coordinado_prenda").getResultList();

		return re;
	}

	@Override
	public void save(ProduccionFechaCoordinadoPrenda obj) {
		repository.save(obj);

	}

	@Transactional(readOnly = true)
	@Override
	public ProduccionFechaCoordinadoPrenda findOneFechaCoorPrenda(Integer id) {
		try {
			return  (ProduccionFechaCoordinadoPrenda) em.createQuery("from ProduccionFechaCoordinadoPrenda where id_coordinado_prenda="+id).getSingleResult();
			}
			catch(Exception e) {
				return null;
			}
		
	}

	@Transactional(readOnly = true)
    @Override
	public Integer validarExistenciaHorasHombre(String fecha) {
		String re = em.createNativeQuery(""+
			"SELECT\r\n"+
			"COUNT(fecha)\r\n"+
			"FROM\r\n"+
				"alt_produccion_calendario\r\n"+
			"WHERE\r\n"+
				"1 = 1\r\n"+
				"AND hombre IS NOT NULL\r\n"+
				"AND fecha = '"+fecha+"'").getSingleResult().toString();

		return Integer.parseInt(re);
	}

	@Transactional(readOnly = true)
    @Override
	public Integer validarHorasHabiles(String fecha, String folio) {
		String re = em.createNativeQuery("" +
			"SELECT\r\n"+
			"if ( (calen.hombre-calen.adeudo) < ROUND( ( SUM( cp.tiempo )* 0.0166667 ), 2 ), 1, 0 )\r\n"+
			"FROM\r\n"+
				"alt_comercial_coordinado_prenda AS cp,\r\n"+
				"alt_produccion_calendario as calen\r\n"+
			"WHERE\r\n"+
				"cp.folio = '"+folio+"' \r\n"+	
				"AND calen.fecha = '"+fecha+"'").getSingleResult().toString();

		return Integer.parseInt(re);
	}

	@Transactional(readOnly = true)
    @Override
	public String recuperarIdPorFecha(String fecha) {
		//
		String re = em.createNativeQuery("SELECT id_calendario_fecha FROM alt_produccion_calendario WHERE fecha = '"+fecha+"'").getSingleResult().toString();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> detallesCalendario(String fecha1, String fecha2) {
		List<Object[]> re = null;
		re = em.createNativeQuery(""+
			"SELECT\r\n"+
				"ca.fecha,\r\n"+
				"IFNULL(ca.hombre,'00.00'),\r\n"+
				"IFNULL(ca.adeudo,'00.00'),\r\n"+
				"IFNULL(( SELECT SEC_TO_TIME(SUM(cp.tiempo)*60) FROM alt_produccion_fecha_coordinado_prenda AS fp, alt_comercial_coordinado_prenda AS cp WHERE 1 = 1 AND fp.id_fecha = ca.id_calendario_fecha and cp.id_coordinado_prenda = fp.id_coordinado_prenda ),'00.00') as programada\r\n"+
			"FROM\r\n"+
				"alt_produccion_calendario AS ca \r\n"+
			"WHERE\r\n"+
				"ca.fecha BETWEEN '"+fecha1+"' AND '"+fecha2+"'").getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String buscarFechaPorFolio (String folio){

		try {
			String re = em.createNativeQuery(""+
			"SELECT\r\n"+
				"ca.fecha\r\n"+
			"FROM\r\n"+
				"alt_produccion_fecha_coordinado_prenda AS fecha\r\n"+
				"INNER JOIN alt_produccion_calendario ca ON ca.id_calendario_fecha = fecha.id_fecha\r\n"+
				"INNER JOIN alt_comercial_coordinado_prenda cp on cp.id_coordinado_prenda = fecha.id_coordinado_prenda\r\n"+
				"WHERE\r\n"+
				"cp.folio='"+folio+"'\r\n"+	
				"GROUP BY ca.fecha").getSingleResult().toString();

		return re;
			
		} catch (Exception e) {
			return null;
		}

		

	}
	
    


}
