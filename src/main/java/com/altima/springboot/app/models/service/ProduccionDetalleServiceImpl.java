package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.repository.ProduccionDetallePedidoRepository;

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
				"SELECT (SELECT (COUNT(pdetalle.cantidad)-(SELECT IFNULL(SUM(rack.cantidad),0) FROM alt_comercial_rack_prenda AS rack\n" + 
				"										WHERE rack.id_prenda = pre3.id_prenda AND rack.id_tela = dt.id_tela AND rack.estatus=1)) \n" + 
				"					FROM alt_produccion_detalle_pedido AS pdetalle \n" + 
				"					WHERE  pdetalle.id_prenda = pre3.id_prenda \n" + 
				"					AND pdetalle.id_tela = dt.id_tela \n" +	
				"					AND pdetalle.estatus_confeccion = 2 \n" + 
				"					AND pdetalle.estatus = 1) AS stock,	 \n" + 
				"			po.id_detalle_pedido AS Id,	 \n" + 
				"			po.id_text AS codigo,	 \n" + 
				"			dl2.nombre_lookup AS Prenda,	 \n" + 
				"			po.talla AS talla,	 \n" + 
				"			po.largo AS largo,	 \n" + 
				"			dt.nombre_tela AS tela,	 \n" + 
				"			dl.nombre_lookup AS Genero,	 \n" + 
				"			po.costo AS price,	 \n" + 
				"			po.estatus_confeccion AS estatus_confeccion,	 \n" + 
				"			pre3.id_prenda,	 \n" + 
				"			MAX( po.estatus ) AS estatus,	 \n" + 
				"			dt.id_tela AS idTela,  \n" + 
				"			imagen.ruta_prenda AS imagen,	 \n" + 
				"			precio.precio_muestrario,  \n" + 
				"			IF	( pre3.estatus_recepcion_muestra = 'Definitivo', pre3.id_text, pre3.id_text_prospecto ) AS modelote,	 \n" + 
				"			pre3.descripcion_prenda  \n" + 
				"			 \n" + 
			"			FROM	alt_produccion_detalle_pedido po	 \n" + 
			"			INNER JOIN alt_disenio_prenda pre3 ON pre3.id_prenda = po.id_prenda	AND estatus_confeccion = 2 AND po.estatus =1	 \n" + 
			"			INNER JOIN alt_disenio_tela dt ON dt.id_tela = po.id_tela	 \n" + 
			"			INNER JOIN alt_disenio_lista_precio_prenda precio ON precio.id_prenda = po.id_prenda AND dt.id_familia_composicion = precio.id_familia_composicion  \n" + 
			"			INNER JOIN alt_disenio_lookup dl2 ON dl2.id_lookup = pre3.id_familia_prenda	 \n" + 
			"			INNER JOIN alt_disenio_lookup dl ON dl.id_lookup = pre3.id_genero	 \n" + 
			"			LEFT JOIN alt_comercial_imagen_inventario imagen ON imagen.id_prenda = po.id_prenda AND imagen.id_tela = dt.id_tela  \n" + 
			"			\n" + 
			"			WHERE 9 !=0\n" + 
			"			GROUP BY	pre3.id_prenda,	dt.id_tela")
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

		
		return em.createNativeQuery(""
				+ "SELECT\n" + 
				"	lk.nombre_lookup AS Famili,\n" + 
				"	p.descripcion_prenda AS Modelo,\n" + 
				"	t.nombre_tela AS tela,\n" + 
				"	dp.descripcion,\n" + 
				"	dp.largo,\n" + 
				"	dp.talla,\n" + 
				"	dp.id_detalle_pedido,\n" + 
				"	COUNT(*),\n" + 
				"	p.id_prenda,\n" + 
				"	p.id_text_prospecto \n" + 
				"FROM\n" + 
				"	alt_produccion_detalle_pedido dp\n" + 
				"	INNER JOIN alt_disenio_prenda p ON dp.id_prenda = p.id_prenda\n" + 
				"	INNER JOIN alt_disenio_lookup lk ON p.id_familia_prenda = lk.id_lookup\n" + 
				"	INNER JOIN alt_disenio_tela t ON dp.id_tela = t.id_tela \n" + 
				"WHERE\n" + 
				"	dp.id_pedido = "+Id+" \n" + 
				"GROUP BY\n" + 
				"	p.id_prenda,\n" + 
				"	dp.talla,\n" + 
				"	dp.largo,\n" + 
				"	t.id_tela;")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> detallesMatariales(Long id) {
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	material.nombre_material AS material,\n" + 
				"	CM.color AS color,\n" + 
				"	CM.color_codigo AS codigo \n" + 
				"FROM\n" + 
				"	alt_disenio_material AS material\n" + 
				"	INNER JOIN alt_produccion_detalle_pedido_material AS CM ON material.id_material = CM.id_material \n" + 
				"WHERE\n" + 
				"	CM.id_pedido_detalle = "+id+" UNION ALL\n" + 
				"SELECT\n" + 
				"	tela.nombre_tela AS material,\n" + 
				"	tela.color AS color,\n" + 
				"	tela.codigo_color AS codigo \n" + 
				"FROM\n" + 
				"	alt_produccion_detalle_pedido_tela AS pdt\n" + 
				"	INNER JOIN alt_disenio_tela AS tela ON pdt.id_tela = tela.id_tela \n" + 
				"WHERE\n" + 
				"	pdt.id_detalle_pedido = "+id+" UNION ALL\n" + 
				"SELECT\n" + 
				"	forro.nombre_forro AS material,\n" + 
				"	forro.color AS color,\n" + 
				"	forro.codigo_color AS codigo \n" + 
				"FROM\n" + 
				"	alt_produccion_detalle_pedido_forro AS pdf\n" + 
				"	INNER JOIN alt_disenio_forro AS forro ON pdf.id_forro = forro.id_forro \n" + 
				"WHERE\n" + 
				"	pdf.id_detalle_pedido = "+id+" UNION ALL\n" + 
				"SELECT\n" + 
				"	CONCAT( 'Tela principal','-',tela.nombre_tela ) AS material,\n" + 
				"	tela.color AS color,\n" + 
				"	tela.codigo_color AS codigo \n" + 
				"FROM\n" + 
				"	alt_produccion_detalle_pedido AS pedido,\n" + 
				"	alt_disenio_tela AS tela \n" + 
				"WHERE\n" + 
				"	tela.id_tela = pedido.id_tela \n" + 
				"	AND pedido.id_detalle_pedido = "+id)
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
		return em
				.createNativeQuery("SELECT CONCAT(he.nombre_persona,' ',he.apellido_paterno,' ',he.apellido_materno)\n"
						+ "FROM `alt_comercial_cliente` cc,alt_hr_usuario hu\n" + "LEFT JOIN alt_hr_empleado he\n"
						+ "on hu.id_empleado=he.id_empleado\n" + "where cc.id_usuario=hu.id_usuario\n"
						+ "and cc.id_cliente=" + id + "\n" + "ORDER BY cc.id_cliente DESC")
				.getSingleResult().toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> materialesPorPrendaExtra(Long id) {
		System.out.println("vamos a mnodificar la entretela");

		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + "	material.id_material,\r\n"
				+ "	material.nombre_material ,\r\n" + "	look.nombre_lookup\r\n" + "FROM\r\n"
				+ "	alt_disenio_material_prenda AS material_prenda,\r\n" + "	alt_disenio_material AS material,\r\n"
				+ "	alt_disenio_lookup adl ,\r\n" + "	alt_disenio_lookup AS look \r\n" + "WHERE\r\n" + "	1 = 1 \r\n"
				+ "	AND look.id_lookup = material.id_clasificacion\r\n"
				+ "	AND look.nombre_lookup  IN ( 'Combinación' ) \r\n"
				+ "	AND material.id_material = material_prenda.id_material \r\n"
				+ "	AND material.id_proceso = adl.id_lookup \r\n" + "	AND material_prenda.id_prenda = " + id)
				.getResultList();
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
				+ "            AND  (material.nombre_material ='Forro Principal' or material.nombre_material ='Forro Combinación') \r\n"
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

		List<Object[]> re = em.createNativeQuery("SELECT\n" + "	material.id_material,\n"
				+ "	material.nombre_material,\n" + "	material_prenda.id_material_prenda \n" + "FROM\n"
				+ "	alt_disenio_material_prenda AS material_prenda,\n" + "	alt_disenio_material AS material,\n"
				+ "	alt_disenio_lookup adl \n" + "WHERE\n" + "	1 = 1 \n"
				+ "	AND material.nombre_material NOT IN ( 'Tela principal' ) \n"
				+ "	AND material.nombre_material NOT IN ( 'Forro principal' ) \n"
				+ "	AND material.nombre_material NOT IN ( 'Tela combinacion' ) \n" 

				+ "	AND material.id_material = material_prenda.id_material \n"
				+ "	AND material.id_proceso = adl.id_lookup \n" + "	AND material_prenda.id_material_prenda = " + id
				+ "     \n" + "\n" + "\n" + "\n" + "UNION\n" + "\n" + "SELECT\n" + "	material.id_material,\n"
				+ "  material.nombre_material,\n" + "	material_prenda.id_material_prenda \n" + "FROM\n"
				+ "	alt_disenio_material_extra_prenda AS material_prenda,\n" + "	alt_disenio_material AS material,\n"
				+ "	alt_disenio_lookup adl \n" + "WHERE\n" + "	1 = 1 \n"
				+ "	AND material.nombre_material NOT IN ( 'Tela principal' ) \n"
				+ "	AND material.nombre_material NOT IN ( 'Forro principal' ) \n"
				+ "	AND material.nombre_material NOT IN ( 'Tela combinacion' ) \n" 

				+ "	AND material.id_material = material_prenda.id_material \n"
				+ "	AND material.id_proceso = adl.id_lookup \n" + "	AND material_prenda.id_material_prenda = " + id)
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
		return em
				.createNativeQuery(
						"SELECT nombre_lookup FROM alt_disenio_lookup WHERE `tipo_lookup` = 'Familia Genero'")
				.getResultList();
	}
	
	
	@Override
	@Transactional
	public String validacion (Long idp ,Long id) {
		
		System.out.println("ando en el service ");
		System.out.println("id tela    " + id);
		System.out.println("id prenda   " + idp);
		System.out.println("SELECT\\r\\n\" + \r\n" + 
				"					\"	precio.id_prenda \\r\\n\" + \r\n" + 
				"					\"FROM\\r\\n\" + \r\n" + 
				"					\"	alt_disenio_lista_precio_prenda AS precio,\\r\\n\" + \r\n" + 
				"					\"	alt_disenio_tela AS tela \\r\\n\" + \r\n" + 
				"					\"WHERE\\r\\n\" + \r\n" + 
				"					\"	1 = 1 \\r\\n\" + \r\n" + 
				"					\"	AND precio.id_prenda = \"+idp+\" \\r\\n\" + \r\n" + 
				"					\"	AND tela.id_familia_composicion = precio.id_familia_composicion \\r\\n\" + \r\n" + 
				"					\"	AND tela.id_tela = ");
		
		
		
		try {
			String re = em.createNativeQuery("SELECT\r\n" + 
					"	precio.id_prenda \r\n" + 
					"FROM\r\n" + 
					"	alt_disenio_lista_precio_prenda AS precio,\r\n" + 
					"	alt_disenio_tela AS tela \r\n" + 
					"WHERE\r\n" + 
					"	1 = 1 \r\n" + 
					"	AND precio.id_prenda = "+idp+" \r\n" + 
					"	AND tela.id_familia_composicion = precio.id_familia_composicion \r\n" + 
					"	AND tela.id_tela ="+id).getSingleResult().toString();
					
			return re;
			}
			catch(Exception e) {
				
				return null;
			}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findListMatExCambioTela(Long idCooPrenda, Long idMaterial) {

		List<Object[]> re = em.createNativeQuery("SELECT\n" + 
				"	material.id_material,\n" + 
				"	material.nombre_material \n" + 
				"FROM\n" + 
				"	alt_comercial_coordinado_material AS coor_mat,\n" + 
				"	alt_comercial_coordinado_prenda AS coor_pre,\n" + 
				"	alt_disenio_material AS material \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND coor_mat.id_coordinado_prenda = coor_pre.id_coordinado_prenda \n" + 
				"	AND material.id_material = coor_mat.id_material \n" + 
				"	AND coor_pre.id_coordinado_prenda = "+idCooPrenda+" \n" + 
				"	AND material.id_material ="+idMaterial)
				.getResultList();

		return re;

	}
	
}
		



