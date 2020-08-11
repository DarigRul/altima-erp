package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialPrendaBordado;
import com.altima.springboot.app.repository.ComercialPrendaBordadoRepository;

@Service
public class ComercialPrendaBordadoServiceImpl implements IComercialPrendaBordadoService {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComercialPrendaBordadoRepository repository;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialPrendaBordado> findAll(Long id) {
		return em.createQuery("from ComercialPrendaBordado  where id_coordinado_prenda ="+id).getResultList();
	
	}

	@Override
	public void save(ComercialPrendaBordado ComercialCliente) {
		repository.save(ComercialCliente);
		
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	public ComercialPrendaBordado findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllCoordinado(Long id) {
		
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	coor_prenda.id_coordinado_prenda,\r\n" + 
				"	coor.numero_coordinado,\r\n" + 
				"	look.nombre_lookup,\r\n" + 
				"	prenda.descripcion_prenda,\r\n" + 
				"	tela.nombre_tela,\r\n" + 
				"	look2.nombre_lookup as look2 ,\r\n" + 
				"	tela.color,\r\n" + 
				"	(SELECT  IFNULL(SUM( bordado.precio_bordado ) , 0)  FROM alt_comercial_prenda_bordado AS bordado WHERE bordado.id_coordinado_prenda = coor_prenda.id_coordinado_prenda ),\r\n" + 
				"CASE\r\n" + 
				"		WHEN pedido.precio_usar = 1 THEN\r\n" + 
				"		precio.precio_local_nuevo \r\n" + 
				"		WHEN pedido.precio_usar = 2 THEN\r\n" + 
				"		precio.precio_local_antiguo\r\n" + 
				"		WHEN pedido.precio_usar = 3 THEN\r\n" + 
				"		precio.precio_foraneo_nuevo\r\n" + 
				"		WHEN pedido.precio_usar = 4 THEN\r\n" + 
				"		precio.precio_foraneo_antiguo ELSE '000' \r\n" + 
				"	END,\r\n" + 
				"	coor_prenda.adicional,\r\n" + 
				"	coor_prenda.monto_adicional,\r\n" + 
				"	coor_prenda.precio_final,\r\n" + 
				"	coor_prenda.observaciones \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda,\r\n" + 
				"	alt_comercial_coordinado AS coor,\r\n" + 
				"	alt_disenio_prenda AS prenda,\r\n" + 
				"	alt_disenio_tela AS tela,\r\n" + 
				"	alt_disenio_lookup AS look,\r\n" + 
				"	alt_disenio_lookup AS look2,\r\n" + 
				"	alt_comercial_pedido_informacion AS pedido,\r\n" + 
				"    alt_disenio_lista_precio_prenda as precio\r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND coor.id_coordinado = coor_prenda.id_coordinado \r\n" + 
				"	AND coor_prenda.id_prenda = prenda.id_prenda \r\n" + 
				"	AND prenda.id_familia_prenda = look.id_lookup \r\n" + 
				"	AND coor_prenda.id_tela = tela.id_tela \r\n" + 
				"	AND tela.id_familia_composicion = look2.id_lookup \r\n" + 
				"	AND pedido.id_pedido_informacion = coor.id_pedido \r\n" + 
				"    AND precio.id_prenda = coor_prenda.id_prenda \r\n" + 
				"	AND coor.id_pedido = "+id+" \r\n" + 
				"	AND coor_prenda.estatus = 1 \r\n" + 
				"ORDER BY\r\n" + 
				"	coor.numero_coordinado,prenda.descripcion_prenda").getResultList();
		return re;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Float sumBordados(Long id) {
		
		String re = em.createNativeQuery("SELECT IFNULL( SUM( bordado.precio_bordado ) , 0)\r\n" + 
				"				FROM \r\n" + 
				"				alt_comercial_prenda_bordado AS bordado  \r\n" + 
				"				WHERE bordado.id_coordinado_prenda="+id).getSingleResult().toString();
		
		
			return Float.parseFloat(re); 
			
	}
	
	@Transactional(readOnly = true)
	@Override
	public Float precio_coor_prenda(Long id) {
		
		String re = em.createNativeQuery("select \r\n" + 
				"CASE\r\n" + 
				"						WHEN pedido.precio_usar = 1 THEN\r\n" + 
				"						precio.precio_local_nuevo  \r\n" + 
				"						WHEN pedido.precio_usar = 2 THEN \r\n" + 
				"						precio.precio_local_antiguo \r\n" + 
				"						WHEN pedido.precio_usar = 3 THEN \r\n" + 
				"						precio.precio_foraneo_nuevo \r\n" + 
				"						WHEN pedido.precio_usar = 4 THEN\r\n" + 
				"						precio.precio_foraneo_antiguo ELSE '000'\r\n" + 
				"					END\r\n" + 
				"from \r\n" + 
				"alt_disenio_lista_precio_prenda as precio, \r\n" + 
				"alt_comercial_coordinado as coor,\r\n" + 
				"alt_comercial_coordinado_prenda  coor_prenda,\r\n" + 
				"alt_comercial_pedido_informacion AS pedido\r\n" + 
				"where 1=1\r\n" + 
				"and coor_prenda.id_coordinado = coor.id_coordinado\r\n" + 
				"and coor.id_pedido = pedido.id_pedido_informacion\r\n" + 
				"and precio.id_prenda = coor_prenda.id_prenda\r\n" + 
				"and coor_prenda.id_coordinado_prenda="+id).getSingleResult().toString();
		  return Float.parseFloat(re); 
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> BordadosView(Long id) {
		
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	bordado.id_bordado,\n" + 
				"	bordado.descripcion \n" + 
				"FROM\n" + 
				"	alt_comercial_bordado AS bordado,\n" + 
				"	alt_comercial_pedido_informacion AS pedido \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND bordado.estatus_bordado = 1 \n" + 
				"	AND bordado.estatus = 1 \n" + 
				"	AND pedido.id_empresa = bordado.id_cliente \n" + 
				"	AND pedido.id_pedido_informacion = "+id).getResultList();
		return re;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Float precioBordado(Long id) {
		
		String re = em.createNativeQuery("SELECT\n" + 
				"	look.atributo_1\n" + 
				"FROM\n" + 
				"	alt_comercial_bordado AS bordado,\n" + 
				"	alt_comercial_lookup AS look \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND bordado.id_lookup = look.id_lookup \n" + 
				"	AND bordado.id_bordado="+id).getSingleResult().toString();
		
		
			return Float.parseFloat(re); 
			
	}
	
}