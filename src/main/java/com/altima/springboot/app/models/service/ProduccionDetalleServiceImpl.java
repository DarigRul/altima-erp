package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoForro;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoTela;
import com.altima.springboot.app.repository.ProduccionDetallePedidoRepository;
import com.altima.springboot.app.repository.PrudduccionDetallePedidoForroRepository;
import com.altima.springboot.app.repository.PrudduccionDetallePedidoTelaRepository;

@Service
public class ProduccionDetalleServiceImpl implements IProduccionDetalleService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ProduccionDetallePedidoRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<ProduccionDetallePedido> findAll() {
		// TODO Auto-generated method stub
		return (List<ProduccionDetallePedido>) repository.findAll();
	}

	@Override
	public void save(ProduccionDetallePedido Orden) {
		repository.save(Orden);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public ProduccionDetallePedido findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	} //

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> ListarMuestras(Long id, String tipo) {
		List<Object[]> re = em.createNativeQuery(
				"Select pedido.id_detalle_pedido , pedido.id_text, prenda.descripcion_prenda, pedido.talla,\r\n"
						+ "telas.nombre_tela, prenda.id_text AS text, telas.id_text as tela_id_text from alt_produccion_detalle_pedido as pedido\r\n"
						+ "	INNER JOIN alt_disenio_prenda prenda ON pedido.id_prenda = prenda.id_prenda\r\n"
						+ "	INNER JOIN alt_disenio_tela telas ON pedido.id_tela = telas.id_tela\r\n"
						+ "where not exists\r\n"
						+ "   (select muestra.id_pedido from alt_control_produccion_muestra as muestra\r\n"
						+ "     where muestra.id_pedido= pedido.id_detalle_pedido and muestra.tipo=" + tipo + ")\r\n"
						+ "      and pedido.estatus='1' \r\n" + "     and pedido.id_pedido=" + id)
				.getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> Terminados(Long id, Long tipo) {
		// TODO Auto-generated method stub

		System.out.println(
				"select orden.id_detalle_pedido, orden.id_text from alt_produccion_detalle_pedido as orden  \r\n"
						+ "								where 1=1 \r\n"
						+ "								and  EXISTS (select muestra.id_pedido from alt_control_produccion_muestra as muestra \r\n"
						+ "								           where 1=1\r\n"
						+ "								           and muestra.id_pedido= orden.id_detalle_pedido\r\n"
						+ "								           and orden.id_pedido=" + id + " \r\n"
						+ "								           and muestra.estatus_tiempo='Stop' \r\n"
						+ "								           and muestra.tipo=" + (tipo - 1) + ") \r\n"
						+ "				                and not  exists\r\n"
						+ "								   			(select muestra.id_pedido from alt_control_produccion_muestra as muestra \r\n"
						+ "								     		where muestra.id_pedido= orden.id_detalle_pedido and muestra.tipo="
						+ tipo + ")\r\n" + "								     and orden.id_pedido=" + id);

		List<Object[]> re = em.createNativeQuery(
				"select orden.id_detalle_pedido, orden.id_text from alt_produccion_detalle_pedido as orden  \r\n"
						+ "								where 1=1 \r\n"
						+ "								and  EXISTS (select muestra.id_pedido from alt_control_produccion_muestra as muestra \r\n"
						+ "								           where 1=1\r\n"
						+ "								           and muestra.id_pedido= orden.id_detalle_pedido\r\n"
						+ "								           and orden.id_pedido=" + id + " \r\n"
						+ "								           and muestra.estatus_tiempo='Stop' \r\n"
						+ "								           and muestra.tipo=" + (tipo - 1) + ") \r\n"
						+ "				                and not  exists\r\n"
						+ "								   			(select muestra.id_pedido from alt_control_produccion_muestra as muestra \r\n"
						+ "								     		where muestra.id_pedido= orden.id_detalle_pedido and muestra.tipo="
						+ tipo + ")\r\n" + "								     and orden.id_pedido=" + id)
				.getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> PrendaOrdenes(Long id) {
		// TODO Auto-generated method stub
		List<Object[]> re = em.createNativeQuery(
				"select lookup.nombre_lookup, prenda.descripcion_prenda, orden.costo, SUM(orden.cantidad), orden.talla, orden.largo, orden.id_detalle_pedido, orden.id_prenda, orden.id_pedido\r\n"
						+ "				from alt_disenio_lookup as lookup, \r\n"
						+ "					 alt_disenio_prenda as prenda,  \r\n"
						+ "				   alt_produccion_detalle_pedido   as orden,\r\n"
						+ "				   alt_produccion_pedido as pedido \r\n" + "				where 1=1\r\n"
						+ "				and lookup.id_lookup= prenda.id_familia_prenda \r\n"
						+ "				and pedido.id_pedido=orden.id_pedido \r\n"
						+ "				and prenda.id_prenda = orden.id_prenda\r\n"
						+ "				and orden.estatus='1' \r\n" + "				and pedido.id_pedido=" + id + "\r\n"
						+ "				GROUP BY orden.id_prenda")
				.getResultList();
		return re;
	}

	@Override
	@Transactional
	public void bajasOrdenes(String fecha, String edito, String idPrenda, String idPedido, String talla, String largo,
			String costo) {
		// TODO Auto-generated method stub
		Query query = em.createNativeQuery("UPDATE alt_produccion_detalle_pedido   as orden\r\n" + "SET \r\n"
				+ "estatus=0, \r\n" + "ultima_fecha_modificacion='" + fecha + "',\r\n" + "actualizado_por='" + edito
				+ "'\r\n" + "WHERE  orden.id_prenda=" + idPrenda + "\r\n" + "and orden.id_pedido=" + idPedido + "\r\n"
				+ "and orden.talla='" + talla + "'\r\n" + "and orden.largo='" + largo + "'\r\n" + "and orden.costo="
				+ costo + "\r\n");

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> muestrariosCatalogo() {
		List<Object[]> quer = em.createNativeQuery(
				"SELECT	COUNT(po.cantidad) AS stock,	po.id_detalle_pedido AS Id,	po.id_text AS codigo,	dl2.nombre_lookup AS Prenda,	po.talla AS talla,	po.largo AS largo,	dt.nombre_tela AS tela,	dl.nombre_lookup AS Genero,	po.costo AS price,	po.estatus_confeccion AS estatus_confeccion,	pre3.id_prenda,	MAX( po.estatus ) AS estatus,	dt.id_tela AS idTela,	imagen.ruta_prenda AS imagen,	precio.precio_muestrario,IF	( pre3.estatus_recepcion_muestra = 'Definitivo', pre3.id_text, pre3.id_text_prospecto ) AS modelote,	pre3.descripcion_prenda FROM	alt_produccion_detalle_pedido po	INNER JOIN alt_disenio_prenda pre3 ON pre3.id_prenda = po.id_prenda 	AND estatus_confeccion = '2'	AND po.estatus ='1'	INNER JOIN alt_disenio_tela dt ON dt.id_tela = po.id_tela	INNER JOIN alt_disenio_lista_precio_prenda precio ON precio.id_prenda = po.id_prenda	INNER JOIN alt_disenio_lookup dl2 ON dl2.id_lookup = pre3.id_familia_prenda	INNER JOIN alt_disenio_lookup dl ON dl.id_lookup = pre3.id_genero	LEFT JOIN alt_comercial_imagen_inventario imagen ON imagen.id_prenda = po.id_prenda 	AND imagen.id_tela = dt.id_tela GROUP BY	pre3.id_prenda,	dt.id_tela")
				.getResultList();
		return quer;
	}

	@Transactional(readOnly = true)
	@Override
	public Integer Contador() {

		String re = em.createNativeQuery(
				"SELECT IFNULL(MAX(alt_produccion_detalle_pedido.id_detalle_pedido), 0) FROM alt_produccion_detalle_pedido")
				.getSingleResult().toString();
		return Integer.parseInt(re);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProduccionDetallePedido> tabla(Long Id) {

		return em.createNativeQuery("SELECT\r\n" + "    lk.nombre_lookup AS Famili,\r\n"
				+ "    p.detalle_prenda AS Modelo,\r\n" + "    t.nombre_tela AS tela,\r\n" + "    dp.descripcion,\r\n"
				+ "    dp.largo,\r\n" + "    dp.talla,\r\n" + "    dp.id_detalle_pedido,\r\n" + "    COUNT(*),\r\n"
				+ "    p.id_prenda,\r\n" + "    p.id_text_prospecto\r\n" + "FROM\r\n"
				+ "    alt_produccion_detalle_pedido dp\r\n" + "INNER JOIN alt_disenio_prenda p ON\r\n"
				+ "    dp.id_prenda = p.id_prenda\r\n" + "INNER JOIN alt_disenio_lookup lk ON\r\n"
				+ "    p.id_familia_prenda = lk.id_lookup\r\n" + "INNER JOIN alt_disenio_tela t ON\r\n"
				+ "    dp.id_tela = t.id_tela\r\n" + "WHERE\r\n" + "    dp.id_pedido =" + Id + "\r\n" + "GROUP BY\r\n"
				+ "    p.id_prenda,\r\n" + "    dp.talla,\r\n" + "    dp.largo,\r\n" + "    t.id_tela;")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> detallesMatariales(Long id) {
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + "    material.nombre_material AS material,\r\n"
				+ "    CM.color AS color,\r\n" + "    CM.color_codigo AS codigo\r\n" + "FROM\r\n"
				+ "    alt_disenio_material AS material\r\n"
				+ "INNER JOIN alt_produccion_detalle_pedido_material AS CM\r\n" + "ON\r\n"
				+ "    material.id_material = CM.id_material\r\n" + "WHERE\r\n" + "    CM.id_pedido_detalle = " + id
				+ "\r\n" + "UNION\r\n" + "SELECT\r\n" + "    tela.nombre_tela AS material,\r\n"
				+ "    tela.color AS color,\r\n" + "    tela.codigo_color AS codigo\r\n" + "FROM\r\n"
				+ "    alt_produccion_detalle_pedido_tela AS pdt\r\n" + "INNER JOIN alt_disenio_tela AS tela\r\n"
				+ "ON\r\n" + "    pdt.id_tela = tela.id_tela\r\n" + "WHERE\r\n" + "    pdt.id_detalle_pedido =" + id
				+ "\r\n" + "    \r\n" + "    \r\n" + "    UNION\r\n" + "SELECT\r\n"
				+ "    forro.nombre_forro AS material,\r\n" + "    forro.color AS color,\r\n"
				+ "    forro.codigo_color AS codigo\r\n" + "FROM\r\n"
				+ "    alt_produccion_detalle_pedido_forro AS pdf\r\n" + "INNER JOIN alt_disenio_forro AS forro\r\n"
				+ "ON\r\n" + "    pdf.id_forro = forro.id_forro\r\n" + "WHERE\r\n" + "    pdf.id_detalle_pedido =" + id)
				.getResultList();
		return re;
	}

	@Override
	@Transactional
	public void deleteByIdDetalle(Long id, Long idp) {
		System.out.println("ola pinche putita te pones bien cachonda ija tu puta madre ");
		em.createNativeQuery(
				"delete from alt_produccion_detalle_pedido where id_pedido=" + idp + " and id_prenda=" + id)
				.executeUpdate();

	}

	@Override
	@Transactional
	public String nombreAgente(Long id) {
		return em.createNativeQuery(
				"SELECT concat(ahe.nombre_persona,' ',ahe.apellido_paterno,' ',ahe.apellido_materno) FROM alt_comercial_cliente_sucursal accs INNER JOIN alt_hr_empleado ahe on accs.id_agente=ahe.id_empleado where accs.id_cliente_sucursal=2")
				.getSingleResult().toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> materialesPorPrendaExtra(Long id) {
		System.out.println("vamos a mnodificar la entretela");
		
		
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	material.id_material,\r\n" + 
				"	material.nombre_material ,\r\n" + 
				"	look.nombre_lookup\r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_material_prenda AS material_prenda,\r\n" + 
				"	alt_disenio_material AS material,\r\n" + 
				"	alt_disenio_lookup adl ,\r\n" + 
				"	alt_disenio_lookup AS look \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND look.id_lookup = material.id_clasificacion\r\n" + 
				"	AND look.nombre_lookup  IN ( 'Combinación' ) \r\n" + 
				"	AND ( adl.nombre_lookup = 'Corte' OR adl.nombre_lookup = 'Confección' ) \r\n" + 
				"	AND material.id_material = material_prenda.id_material \r\n" + 
				"	AND material.id_proceso = adl.id_lookup \r\n" + 
				"	AND material_prenda.id_prenda = "+id).getResultList();
		return re;
		// AND material.nombre_material NOT IN ('Tela principal')
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> materialesPorPrendaExtra2(Long id) {
		List<Object[]> re = em.createNativeQuery("SELECT material.id_material,material.nombre_material  \r\n"
				+ "				FROM alt_disenio_material_prenda as material_prenda , alt_disenio_material as material , alt_disenio_lookup adl\r\n"
				+ "				WHERE 1=1\r\n" + "			\r\n"
				+ "            AND  material.nombre_material ='Forro principal' \r\n"
				+ "			AND (adl.nombre_lookup='Corte' OR adl.nombre_lookup='Confección')\r\n"
				+ "			AND material.id_material = material_prenda.id_material \r\n"
				+ "			AND material.id_proceso = adl.id_lookup\r\n" + "			AND material_prenda.id_prenda="
				+ id).getResultList();
		return re;
		// ('Tela principal')
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllTela(Long id) {
		System.out.println("este es el que saca las telas");
		
	
		List<Object[]> re = em.createNativeQuery("Select\r\n" + "tela.id_tela,\r\n"
				+ "CONCAT(tela.id_text,' ', tela.nombre_tela),\r\n" + "tela.codigo_color\r\n" + "\r\n"
				+ "				from alt_disenio_tela as tela ,alt_disenio_tela_prenda as tela_prenda\r\n"
				+ "				WHERE 1=1 \r\n" + "				and tela.estatus=1\r\n"
				+ "				and tela.estatus_tela=1\r\n"
				+ "				and tela.id_tela = tela_prenda.id_tela\r\n" + "				and tela_prenda.id_prenda="
				+ id).getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllForro(Long id) {
		List<Object[]> re = em.createNativeQuery("\r\n" + "				SELECT \r\n"
				+ "				f.id_forro as id,\r\n"
				+ "				CONCAT(f.id_text,' ', f.nombre_forro) as Forro,\r\n"
				+ "                f.codigo_color\r\n" + "				\r\n"
				+ "				FROM alt_disenio_tela_forro dt INNER JOIN alt_disenio_forro f on dt.id_forro=f.id_forro \r\n"
				+ "				\r\n" + "				WHERE id_tela=" + id).getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findListMatEx(Long id) {
		
		System.out.println("entre a este 1227 y el id   " + id);
		
	
		List<Object[]> re = em.createNativeQuery("SELECT\n" + 
				"	material.id_material,\n" + 
				"	material.nombre_material,\n" + 
				"	material_prenda.id_material_prenda \n" + 
				"FROM\n" + 
				"	alt_disenio_material_prenda AS material_prenda,\n" + 
				"	alt_disenio_material AS material,\n" + 
				"	alt_disenio_lookup adl \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND material.nombre_material NOT IN ( 'Tela principal' ) \n" + 
				"	AND material.nombre_material NOT IN ( 'Forro principal' ) \n" + 
				"	AND material.nombre_material NOT IN ( 'Tela combinacion' ) \n" + 
	
				"	AND ( adl.nombre_lookup = 'Corte' OR adl.nombre_lookup = 'Confección' ) \n" + 
				"	AND material.id_material = material_prenda.id_material \n" + 
				"	AND material.id_proceso = adl.id_lookup \n" + 
				"	AND material_prenda.id_material_prenda = "+id+"     \n" + 
				"\n" + 
				"\n" + 
				"\n" + 
				"UNION\n" + 
				"\n" + 
				"SELECT\n" + 
				"	material.id_material,\n" + 
				"  material.nombre_material,\n" + 
				"	material_prenda.id_material_prenda \n" + 
				"FROM\n" + 
				"	alt_disenio_material_extra_prenda AS material_prenda,\n" + 
				"	alt_disenio_material AS material,\n" + 
				"	alt_disenio_lookup adl \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND material.nombre_material NOT IN ( 'Tela principal' ) \n" + 
				"	AND material.nombre_material NOT IN ( 'Forro principal' ) \n" + 
				"	AND material.nombre_material NOT IN ( 'Tela combinacion' ) \n" + 

				"	AND ( adl.nombre_lookup = 'Corte' OR adl.nombre_lookup = 'Confección' ) \n" + 
				"	AND material.id_material = material_prenda.id_material \n" + 
				"	AND material.id_proceso = adl.id_lookup \n" + 
				"	AND material_prenda.id_material_prenda = " + id)
				.getResultList();
		
		
		return re;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllTelaPrimera(Long id) {
		
		System.out.println("este es el bueno para las telas de coordinados");
		List<Object[]> re = em.createNativeQuery("Select  tela.id_tela, CONCAT(tela.id_text,' ', tela.nombre_tela) \r\n"
				+ "				from alt_disenio_tela as tela ,alt_disenio_tela_prenda as tela_prenda\r\n"
				+ "				WHERE 1=1 \r\n" + "				\r\n" + "				and tela.estatus_tela=1\r\n"
				+ "				and tela.id_tela = tela_prenda.id_tela \r\n"
				+ "				and tela_prenda.id_prenda=" + id).getResultList();
		return re;
	}

	@Override
	@Transactional
	public boolean buscar_prenda(String detalle) {

		try {
			String re = em.createNativeQuery("SELECT\r\n" + "alt_disenio_prenda.descripcion_prenda   \r\n" + "FROM \r\n"
					+ "alt_disenio_prenda \r\n" + "WHERE \r\n" + "alt_disenio_prenda.descripcion_prenda='" + detalle
					+ "'").getSingleResult().toString();

			System.out.println(re);
			return true;
		} catch (Exception e) {

			return false;
		}

	}

	///////////////////////////// COLECCION

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProduccionDetallePedido> tablaColeccion(Long Id) {

		return em.createNativeQuery(" \r\n" + "\r\n" + "SELECT\r\n" + "    lk.nombre_lookup AS Famili,\r\n" + "  \r\n"
				+ "    COUNT(*)\r\n" + "\r\n" + "FROM\r\n" + "    alt_produccion_detalle_pedido dp\r\n" + "\r\n"
				+ "INNER JOIN alt_disenio_lookup lk ON\r\n" + "    dp.id_familia_prenda = lk.id_lookup\r\n"
				+ "WHERE\r\n" + "    dp.id_pedido =" + Id + "\r\n" + "GROUP BY\r\n" + "      lk.nombre_lookup ;")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> selectPrenda(Long id) {

		System.out.println("entre al implent de prenda para el lesct");
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + "    dp.id_familia_prenda,\r\n"
				+ "    concat(lk.nombre_lookup,'--', lk2.nombre_lookup) as nombre,\r\n"
				+ "    lk2.nombre_lookup as genero\r\n" + "FROM\r\n" + "    alt_produccion_pedido_coleccion dp\r\n"
				+ "INNER JOIN alt_disenio_lookup lk ON\r\n" + "    dp.id_familia_prenda = lk.id_lookup\r\n" + "    \r\n"
				+ "    INNER JOIN alt_disenio_lookup lk2 ON\r\n" + "    dp.id_familia_genero = lk2.id_lookup\r\n"
				+ "WHERE\r\n" + "    dp.id_pedido = " + id + "\r\n" + "GROUP BY\r\n" + "    lk.nombre_lookup,\r\n"
				+ "      lk2.nombre_lookup").getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> selectDinamicPrenda() {
		return em.createNativeQuery(
				"SELECT fp.id_lookup, fp.nombre_lookup FROM alt_disenio_lookup fp WHERE fp.tipo_lookup = 'Familia Prenda'")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> selectGenero() {
		return em.createNativeQuery("SELECT nombre_lookup FROM alt_disenio_lookup WHERE `tipo_lookup` = 'Familia Genero'")
				.getResultList();
	}

}
