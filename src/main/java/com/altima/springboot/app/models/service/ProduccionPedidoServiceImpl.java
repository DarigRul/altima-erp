package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ProduccionPedido;
import com.altima.springboot.app.repository.ProduccionPedidoRepository;

@Service
public class ProduccionPedidoServiceImpl implements IProduccionPedidoService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ProduccionPedidoRepository repository;

	@Override
	public List<ProduccionPedido> findAll() {
		// TODO Auto-generated method stub
		return (List<ProduccionPedido>) repository.findAll();
	}

	@Override
	public void save(ProduccionPedido pedido) {
		// TODO Auto-generated method stub
		repository.save(pedido);
	}
	// agregar-control-muestra

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public ProduccionPedido findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProduccionPedido> findListapedidos() {

		return em.createNativeQuery("SELECT\n" + "    p.id_text,\n" + "    s.nombre,\n" + "    p.fecha_entrega,\n"
				+ "    p.id_pedido,\n" + "    p.descripcion,\n" + "    p.fecha_creacion,\n"
				+ "   CONCAT(he.nombre_persona,' ',he.apellido_paterno,' ',he.apellido_materno),\n"
				+ "    p.tipo_pedido,\n" + "	 p.estatus_pedido\n" + "FROM\n" + "    alt_produccion_pedido p\n"
				+ ", alt_comercial_cliente s \n" + ",alt_hr_usuario hu\n" + "LEFT JOIN alt_hr_empleado he\n"
				+ "on hu.id_empleado=he.id_empleado\n" + "where s.id_usuario=hu.id_usuario\n"
				+ "and s.id_cliente=p.id_cliente\n" + "\n" + "and\n" + "    p.id_cliente = s.id_cliente\n" + "and\n"
				+ "     descripcion !='Foráneo'\n" + "ORDER BY\n" + "    fecha_creacion\n" + "DESC").getResultList();
	}

	@Override
	@Transactional
	public int contadorPedidos() {
		// TODO Auto-generated method stub
		String auxs = em.createNativeQuery("SELECT COUNT(*) as total FROM alt_produccion_pedido").getSingleResult()
				.toString();
		int aux = Integer.parseInt(auxs);
		return aux + 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findAllPrenda() {
		// TODO Auto-generated method stub
		return em
				.createQuery(
						"from DisenioLookup where tipo_lookup='Familia Prenda' and estatus=1 ORDER BY nombre_lookup ")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllModelo(Long id) {
		List<Object[]> re = em.createNativeQuery(
				"Select  prenda.id_prenda, CONCAT(prenda.id_text,' ', prenda.detalle_prenda) as nombre \r\n"
						+ "				from alt_disenio_prenda as prenda  \r\n"
						+ "				where prenda.id_familia_prenda=" + id)
				.getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllModeloColeccion(Long id, String genero) {
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + "    prenda.id_prenda,\r\n" + "    CONCAT(\r\n"
				+ "        prenda.id_text_prospecto,\r\n" + "        ' ',\r\n" + "        prenda.detalle_prenda,\r\n"
				+ "        '-- ',\r\n" + "        lk2.nombre_lookup\r\n" + "    ) AS nombre\r\n" + "FROM\r\n"
				+ "    alt_disenio_prenda AS prenda\r\n" + "LEFT JOIN alt_disenio_lookup lk2 ON\r\n"
				+ "    prenda.id_genero = lk2.id_lookup\r\n" + "WHERE\r\n" + "    prenda.id_familia_prenda = " + id
				+ "\r\n" + "    AND\r\n" + "    lk2.nombre_lookup='" + genero + "'\r\n" + "    AND\r\n"
				+ "    prenda.estatus_recepcion_muestra='Prospecto'").getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllTela(Long id) {
		List<Object[]> re = em
				.createNativeQuery("Select  tela.id_tela, tela.nombre_tela \r\n"
						+ "from alt_disenio_tela as tela ,alt_disenio_tela_prenda as tela_prenda\r\n" + "WHERE 1=1 \r\n"
						+ "and tela.estatus=1\r\n" + "and tela.estatus_tela=1\r\n"
						+ "and tela.id_tela = tela_prenda.id_tela\r\n" + "and tela_prenda.id_prenda=" + id)
				.getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> materialesPorPrenda(Long id) {

		System.out.println("para la cascara" + id);

		System.out.println("este arroja los materiales");
		List<Object[]> re = em.createNativeQuery("SELECT material.id_material,material.nombre_material  \r\n"
				+ "FROM alt_disenio_material_prenda as material_prenda , alt_disenio_material as material\r\n"
				+ "WHERE 1=1\r\n" + "AND material.nombre_material NOT IN ('Tela principal')"
				+ "AND material.id_material = material_prenda.id_material\r\n" + "AND material_prenda.id_prenda=" + id)
				.getResultList();
		return re;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> coloresMateriales(Long idMaterial, Long idTela, Long idCoorPrenda) {
		List<Object[]> re = em.createNativeQuery("SELECT look.id_lookup,look.nombre_lookup,look.atributo_1 \r\n"
				+ "				FROM  alt_disenio_material as material , alt_disenio_lookup as look\r\n"
				+ "				where 1=1 \r\n" + "				and material.id_material=" + idMaterial + "\r\n"
				+ "				and look.id_lookup=material.id_color\r\n").getResultList();

		/*
		 * if(re == null || re.isEmpty()) { List<Object[]> re2 = em.
		 * createNativeQuery("SELECT MT.id_tipo_material, MT.color, MT.codigo_color\r\n"
		 * +
		 * "FROM  alt_disenio_material as material , alt_disenio_lookup as look, alt_disenio_material_tela as MT\r\n"
		 * + "where 1=1 \r\n" + "AND look.id_lookup=material.id_tipo_material \r\n"+
		 * "AND MT.id_tipo_material=material.id_tipo_material\r\n" +
		 * "AND material.id_material="+idMaterial+"\r\n"+
		 * "AND MT.id_tela="+idTela).getResultList(); return re2;
		 * 
		 * }
		 */

		if (re == null || re.isEmpty()) {
			List<Object[]> re2 = em
					.createNativeQuery("(SELECT material.id_tipo_material , CM.color , CM.color_codigo \r\n"
							+ "FROM  alt_disenio_material as material , alt_comercial_coordinado_material as CM\r\n"
							+ "where 1=1 \r\n" + "AND CM.id_coordinado_prenda=" + idCoorPrenda + "\r\n"
							+ "AND CM.id_material=" + idMaterial + "\r\n"
							+ "AND material.id_material=CM.id_material)\r\n" +

							"UNION \r\n" +

							"(SELECT MT.id_tipo_material , MT.color , MT.codigo_color \r\n"
							+ "FROM  alt_disenio_material as material , alt_disenio_lookup as look, alt_disenio_material_tela as MT \r\n"
							+ "where 1=1 \r\n" + "AND look.id_lookup=material.id_tipo_material\r\n"
							+ "AND MT.id_tipo_material=material.id_tipo_material \r\n" + "AND material.id_material="
							+ idMaterial + "\r\n" + "AND MT.id_tela=" + idTela + ")")
					.getResultList();
			return re2;

		} else {
			return re;
		}

		// AND material.nombre_material NOT IN ('Tela principal')
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> tablaModal(Long id) {
		List<Object[]> re = em.createNativeQuery("\r\n" + "SELECT\r\n" + "dp.id_prenda,\r\n"
				+ "lk.nombre_lookup as FAMILIA,\r\n" + "COUNT(*) as cantidad\r\n" + "\r\n" + "\r\n"
				+ "FROM alt_produccion_detalle_pedido dp inner join alt_disenio_prenda p\r\n"
				+ "on dp.id_prenda=p.id_prenda\r\n" + "inner join alt_disenio_lookup lk\r\n"
				+ "on p.id_familia_prenda=lk.id_lookup\r\n" + "WHERE\r\n" + "dp.id_pedido=" + id + "\r\n" + "\r\n"
				+ "GROUP by lk.nombre_lookup").getResultList();
		return re;
	}

	@Override
	@Transactional
	public int total(Long id) {
		// TODO Auto-generated method stub
		String auxs = em
				.createNativeQuery("SELECT\r\n" + "    COUNT(id_prenda) AS total\r\n" + "FROM\r\n"
						+ "    alt_produccion_detalle_pedido\r\n" + "WHERE\r\n" + "    id_pedido =" + id)
				.getSingleResult().toString();
		int aux = Integer.parseInt(auxs);
		return aux;
	}

	@Override
	@Transactional
	public int contadorColeccion(Long idPedido, Long idFamPrenda, String genero) {
		// TODO Auto-generated method stub
		String auxs = em
				.createNativeQuery("SELECT\r\n" + "    cantidad\r\n" + "FROM\r\n"
						+ "    alt_produccion_pedido_coleccion pc\r\n" + "INNER JOIN alt_disenio_lookup lk ON\r\n"
						+ "    pc.id_familia_genero = lk.id_lookup\r\n" + "WHERE\r\n" + "    pc.id_familia_prenda = "
						+ idFamPrenda + " AND lk.nombre_lookup = '" + genero + "' and pc.id_pedido=" + idPedido)
				.getSingleResult().toString();
		int aux = Integer.parseInt(auxs);
		return aux;
	}

	@Override
	@Transactional
	public int contadorDetallePedido(Long idPedido, Long idFamPrenda, String genero) {
		// TODO Auto-generated method stub
		String auxs = em
				.createNativeQuery("SELECT\r\n" + "    COUNT(*)\r\n" + "FROM\r\n"
						+ "    alt_produccion_detalle_pedido pc\r\n" + "INNER JOIN alt_disenio_lookup lk ON\r\n"
						+ "    pc.id_familia_genero = lk.id_lookup\r\n" + "WHERE\r\n" + "    pc.id_familia_prenda = "
						+ idFamPrenda + " AND lk.nombre_lookup = '" + genero + "' AND pc.id_pedido=" + idPedido)
				.getSingleResult().toString();
		int aux = Integer.parseInt(auxs);
		return aux;
	}

	@Override
	@Transactional
	public int idGenero(Long idPedido, Long idFamPrenda, String genero) {
		// TODO Auto-generated method stub
		String auxs = em.createNativeQuery("SELECT\r\n" + "pc.id_familia_genero as genero\r\n"
				+ "from alt_produccion_pedido_coleccion pc\r\n" + "inner join \r\n" + "alt_disenio_lookup lk ON\r\n"
				+ "pc.id_familia_genero= lk.id_lookup\r\n" + "WHERE \r\n" + "pc.id_pedido=" + idPedido
				+ " and lk.nombre_lookup='" + genero + "' and pc.id_familia_prenda= " + idFamPrenda).getSingleResult()
				.toString();
		int aux = Integer.parseInt(auxs);
		return aux;
	}

}
