package com.altima.springboot.app.models.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.models.entity.ComercialCoordinadoForro;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinadoTela;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.repository.ComercialCoordinadoForroRepository;
import com.altima.springboot.app.repository.ComercialCoordinadoMaterialRepository;
import com.altima.springboot.app.repository.ComercialCoordinadoPrendaRepository;
import com.altima.springboot.app.repository.ComercialCoordinadoRepository;
import com.altima.springboot.app.repository.ComercialCoordinadoTelaRepository;

import javax.persistence.Query;

@Service
public class ComercialCoordinadoServiceImpl implements IComercialCoordinadoService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	ComercialCoordinadoRepository repository;
	
	@Autowired
	ComercialCoordinadoPrendaRepository repositoryPrenda;
	
	@Autowired
	ComercialCoordinadoMaterialRepository repositoryMaterial;
	
	@Autowired
	ComercialCoordinadoTelaRepository repositoryTelaMaterial;
	
	@Autowired
	ComercialCoordinadoForroRepository repositoryForroMaterial;
	
	@Autowired
	private IUploadService UploadService;
	
	@Override
	public List<ComercialCoordinado> findAll() {
		// TODO Auto-generated method stub
		return (List<ComercialCoordinado>) repository.findAll();
	}

	@Override
	public void save(ComercialCoordinado coordinado) {
		repository.save(coordinado);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public ComercialCoordinado findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> findAllEmpresa(Long id) {
		
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	coordinado.id_coordinado,\r\n" + 
				"	coordinado.id_text,\r\n" + 
				"	( SELECT COUNT( * ) FROM alt_comercial_coordinado_prenda AS CP WHERE CP.id_coordinado = coordinado.id_coordinado AND CP.estatus = 1 ) \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_coordinado AS coordinado \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"  AND coordinado.estatus = 1"+
				"	AND coordinado.id_pedido ="+id).getResultList();
		return re;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findAllPrenda() {
		// TODO Auto-generated method stub
		return em.createQuery("from DisenioLookup where tipo_lookup='Familia Prenda' and estatus=1 ORDER BY nombre_lookup ").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> findAllPrenda(String tipo, String idTextCoor) {
		List<Object []> lista = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	look.id_lookup as idLookup,\n" + 
				"	look.nombre_lookup as nombreLookup \n" + 
				"FROM\n" + 
				"	alt_disenio_prenda AS prenda,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda,\n" + 
				"	alt_disenio_lookup AS look \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND coor_prenda.id_coordinado = coor.id_coordinado \n" + 
				"	AND coor_prenda.id_prenda = prenda.id_prenda \n" + 
				"	AND prenda.id_familia_prenda = look.id_lookup \n" + 
				"	AND coor.id_text = '"+idTextCoor+"' \n" + 
				"GROUP BY\n" + 
				"	prenda.id_prenda").getResultList();
		if ( lista.isEmpty() ) {
			return em.createNativeQuery(""
					+ "SELECT\n" + 
					"	id_lookup,\n" + 
					"	nombre_lookup \n" + 
					"FROM\n" + 
					"	alt_disenio_lookup \n" + 
					"WHERE\n" + 
					"	tipo_lookup = 'Familia Prenda' \n" + 
					"	AND estatus = 1 \n" + 
					"	AND nombre_lookup LIKE '%"+tipo+"%' \n" + 
					"ORDER BY\n" + 
					"	nombre_lookup").getResultList();
		}
		else {
			return lista;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllModelo(Long id) {
		List<Object[]> re = em.createNativeQuery("Select  prenda.id_prenda, CONCAT(prenda.id_text,' ', prenda.descripcion_prenda) as nombre \r\n" + 
				"				from alt_disenio_prenda as prenda  \r\n" + 
				"				where prenda.id_familia_prenda="+id).getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllModeloSPF(Long id, Long idPedido) {
		List<Object[]> lista  = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	prenda.id_prenda,\n" + 
				"	CONCAT(prenda.id_text,' ',prenda.descripcion_prenda)\n" + 
				"	\n" + 
				"FROM\n" + 
				"	alt_disenio_prenda AS prenda,\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND coor_prenda.id_coordinado = coor.id_coordinado \n" + 
				"	AND coor_prenda.id_prenda = prenda.id_prenda \n" + 
				"	AND coor.id_text = 'COORSPF"+idPedido+"' \n" + 
				"GROUP BY\n" + 
				"	prenda.id_prenda").getResultList(); 
		
		if (lista.isEmpty()) {
			List<Object[]> re = em.createNativeQuery("Select  prenda.id_prenda, CONCAT(prenda.id_text,' ', prenda.descripcion_prenda) as nombre \r\n" + 
					"				from alt_disenio_prenda as prenda  \r\n" + 
					"				where prenda.id_familia_prenda="+id).getResultList();
			return re;
		}
		else {
			return lista;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllTela(Long id) {
		System.out.println("si corrio el metodo de la tela");
		List<Object[]> re = em.createNativeQuery("Select  tela.id_tela, CONCAT(tela.id_text,' ', tela.nombre_tela) \n" + 
				"							from alt_disenio_tela as tela ,alt_disenio_tela_prenda as tela_prenda\n" + 
				"								WHERE 1=1 and tela.estatus_tela=1\n" + 
				"								and tela.estatus=1\n" + 
				"								and tela.id_tela = tela_prenda.id_tela \n" + 
				"								and tela_prenda.id_prenda="+ id).getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllComposicion(Long id) {
		System.out.println("si corrio el metodo de la composicion");
		List<Object[]> re = em.createNativeQuery("SELECT famComposicion.id_lookup,\n" + 
												 "		 famComposicion.nombre_lookup\n" + 
												 "FROM alt_disenio_lookup AS famComposicion\n" + 
												 "INNER JOIN alt_disenio_precio_composicion precio ON famComposicion.id_lookup = precio.id_familia_composicion\n" + 
												 "WHERE precio.id_prenda ="+ id).getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> materialesPorPrenda(Long id) {
		
		List<Object[]> re = em.createNativeQuery("SELECT\n" + 
				"					material.id_material, \n" + 
				"					material.nombre_material, \n" + 
				"					material_prenda.id_material_prenda , \n" + 
				"					look.nombre_lookup\n" + 
				"				FROM\n" + 
				"					alt_disenio_material_prenda AS material_prenda,\n" + 
				"					alt_disenio_material AS material,\n" + 
				"					alt_disenio_lookup adl,\n" + 
				"					alt_disenio_lookup AS look \n" + 
				"				WHERE\n" + 
				"					1 = 1\n" + 
				"					AND look.id_lookup = material.id_tipo_material \n" + 
				"					AND look.atributo_1 = 1 \n" +
				"					AND material.estatus = 1 \n" + 
				"					AND look.nombre_lookup NOT IN ( 'Tela Material' ) \n" + 
				"					AND look.nombre_lookup NOT IN ( 'Forro Material' )  \n" + 
				"					AND material.id_material = material_prenda.id_material \n" + 
				"					AND material.id_proceso = adl.id_lookup \n" + 
				"					AND material_prenda.id_prenda = "+id).getResultList();
		return re;
		//AND material.nombre_material NOT IN ('Tela principal')
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> coloresMateriales(Long idMaterial, Long idTela ,  Long idCoorPrenda ) {
		
		String clasificacion = em.createNativeQuery("SELECT\r\n" + 
				"	look2.nombre_lookup\r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_material AS material,\r\n" + 
				"	alt_disenio_lookup AS look2 \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND look2.id_lookup = material.id_clasificacion\r\n" + 
				"    AND material.id_material ="+idMaterial).getSingleResult().toString();
		
		if (clasificacion.equals("Dependiente del Color")) {	
			List<Object[]> re2 = em.createNativeQuery("SELECT\r\n" + 
					"	id_tipo_material,\r\n" + 
					"	color,\r\n" + 
					"	color_codigo,\r\n" + 
					"	orden_alfa \r\n" + 
					"FROM\r\n" + 
					"	(\r\n" + 
					"	SELECT\r\n" + 
					"		material1.id_tipo_material AS id_tipo_material,\r\n" + 
					"		CM1.color AS color,\r\n" + 
					"		CM1.color_codigo AS color_codigo,\r\n" + 
					"		'principal' AS orden_alfa \r\n" + 
					"	FROM\r\n" + 
					"		alt_disenio_material AS material1,\r\n" + 
					"		alt_comercial_coordinado_material AS CM1 \r\n" + 
					"	WHERE\r\n" + 
					"		1 = 1 \r\n" + 
					"		AND CM1.id_coordinado_prenda = "+idCoorPrenda+" \r\n" + 
					"		AND CM1.id_material = "+idMaterial+" \r\n" + 
					"		AND material1.id_material = CM1.id_material UNION\r\n" + 
					"	SELECT\r\n" + 
					"		MT2.id_tipo_material AS id_tipo_material,\r\n" + 
					"		MT2.color AS color,\r\n" + 
					"		MT2.codigo_color AS color_codigo,\r\n" + 
					"		MT2.posicion AS orden_alfa \r\n" + 
					"	FROM\r\n" + 
					"		alt_disenio_material AS material2,\r\n" + 
					"		alt_disenio_lookup AS look2,\r\n" + 
					"		alt_disenio_material_tela AS MT2 \r\n" + 
					"	WHERE\r\n" + 
					"		1 = 1 \r\n" + 
					"		AND look2.id_lookup = material2.id_tipo_material \r\n" + 
					"		AND MT2.id_tipo_material = material2.id_tipo_material \r\n" + 
					"		AND material2.id_material = "+idMaterial+" \r\n" + 
					"		AND MT2.id_tela = "+idTela+" \r\n" + 
					"	) Colores \r\n" + 
					"GROUP BY\r\n" + 
					"	color \r\n" + 
					"ORDER BY\r\n" + 
					"	orden_alfa").getResultList();
		
			return re2;
		}
		if (clasificacion.equals("Independiente del color de la Tela")) {
			List<Object[]> re = em.createNativeQuery("SELECT look.id_lookup,look.nombre_lookup,look.atributo_1 \r\n" + 
					"				FROM  alt_disenio_material as material , alt_disenio_lookup as look\r\n" + 
					"				where 1=1 \r\n" + 
					"				and material.id_material="+idMaterial+"\r\n" + 
					"				and look.id_lookup=material.id_color\r\n").getResultList();
			return re;
		}
		
		return null;
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public Integer ContadorCoordinadoCliente(Long id) {
		String re = em.createNativeQuery("SELECT\r\n" + 
				"	IFNULL( MAX( coor.numero_coordinado ), '0' ) \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_coordinado AS coor \r\n" + 
				"WHERE\r\n" + 
				"	coor.id_pedido = "+id).getSingleResult().toString();
	
		return Integer.parseInt(re);
		
	}

	@Transactional(readOnly = true)
	@Override
	public ComercialCoordinado findOneCoorSPF(String idText) {
		

		ComercialCoordinado spf = null;
	
		try {
			return  (ComercialCoordinado) em.createQuery("from ComercialCoordinado where idText='"+idText+"'").getSingleResult();
			}
			catch(Exception e) {
				return spf;
			}
		

	}
	
	//P R E N D A  C O O R D I N A D O S
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []>findAllCoorPrenda(Long id ) {
		List<Object[]> re = em.createNativeQuery("SELECT  coor_prenda.id_coordinado_prenda,look.nombre_lookup, prenda.descripcion_prenda, tela.nombre_tela, IF(coor_prenda.estatus = true, '1', '0')\r\n" + 
				"From alt_disenio_lookup as look, alt_disenio_prenda as prenda, alt_disenio_tela as tela, alt_comercial_coordinado_prenda as coor_prenda\r\n" + 
				"where 1=1\r\n" + 
				"AND coor_prenda.id_tela = tela.id_tela\r\n" + 
				"AND coor_prenda.id_prenda= prenda.id_prenda\r\n" + 
				"AND coor_prenda.id_familia_genero=look.id_lookup\r\n" + 
				"AND coor_prenda.estatus=1\r\n" + 
				"AND coor_prenda.id_coordinado="+id).getResultList();
		return re;
	}

	@Override
	public void saveCoorPrenda(ComercialCoordinadoPrenda prenda) {
		repositoryPrenda.save(prenda);
		
	}

	@Override
	public ComercialCoordinadoPrenda findOneCoorPrenda(Long id) {
		return repositoryPrenda.findById(id).orElse(null);
	}

	//M A T E R I A L  C O O R D I N A D O S
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialCoordinadoMaterial> findAllCoorMaterial(Long id ) {
		
		return em.createQuery("from ComercialCoordinadoMaterial  where id_coordinado_prenda = "+id).getResultList();
		//return (List<ComercialCoordinadoMaterial>) repositoryMaterial.findAll();
	}

	@Override
	public void saveCoorMaterial(ComercialCoordinadoMaterial material) {
		repositoryMaterial.save(material);
		
	}

	
	@Override
	@Transactional(readOnly= true)
	public ComercialCoordinadoMaterial findOneCoorMaterial(Long idCoorPrenda , Long idColor) {
		
		

		try {
			return  (ComercialCoordinadoMaterial)em.createQuery("from ComercialCoordinadoMaterial  where id_coordinado_prenda ="+idCoorPrenda +" AND  id_material="+idColor).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
		
		 
				
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []>detallesMatariales(Long id ) {
		
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	material.nombre_material,\r\n" + 
				"	CM.color,\r\n" + 
				"	CM.color_codigo \r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_material AS material,\r\n" + 
				"	alt_comercial_coordinado_material AS CM \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND CM.id_material = material.id_material \r\n" + 
				"	AND CM.id_coordinado_prenda = "+id+" UNION ALL\r\n" + 
				"SELECT\r\n" + 
				"	CONCAT( 'Combinación ', tela.nombre_tela ),\r\n" + 
				"	tela.color,\r\n" + 
				"	tela.codigo_color \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_coordinado_tela AS coorTela,\r\n" + 
				"	alt_disenio_tela AS tela \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND tela.id_tela = coorTela.id_tela \r\n" + 
				"	AND coorTela.id_coordinado_prenda = "+id+" UNION ALL\r\n" + 
				"SELECT\r\n" + 
				"	CONCAT( 'Forro ', forro.nombre_forro ),\r\n" + 
				"	forro.color,\r\n" + 
				"	forro.codigo_color \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_coordinado_forro AS coorForro,\r\n" + 
				"	alt_disenio_forro AS forro \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND forro.id_forro = coorForro.id_forro \r\n" + 
				"	AND coorForro.id_coordinado_prenda = "+id).getResultList();
		return re;
	}

	
	@Override
	@Transactional
	public void deleteMaterial(ComercialCoordinadoMaterial delete) {
	
		em.remove(delete);
		
		
	}
	
	
	
	@Override
	@Transactional
	public void deleteTotal(Long id) {
		// TODO Auto-generated method stub
		System.out.println();
		
		  Query query = em.createNativeQuery("DELETE coor,\r\n" + 
					"prenda,\r\n" + 
					"material \r\n" + 
					"FROM\r\n" + 
					"	alt_comercial_coordinado AS coor,\r\n" + 
					"	alt_comercial_coordinado_prenda AS prenda,\r\n" + 
					"	alt_comercial_coordinado_material AS material \r\n" + 
					"WHERE\r\n" + 
					"	1 = 1 \r\n" + 
					"	AND coor.id_coordinado = prenda.id_coordinado \r\n" + 
					"	AND prenda.id_coordinado_prenda = material.id_coordinado_prenda \r\n" + 
					"	AND coor.id_coordinado = " +id);
		 
		 
		 query.executeUpdate();
		 
		 Query query2 = em.createNativeQuery("DELETE FROM alt_comercial_coordinado\r\n" + 
		 		"WHERE id_coordinado=" +id);
		 
		 
		 query2.executeUpdate();
	}
	
	
	@Override
	@Transactional
	public void deletePrenda(Long id) {
		// TODO Auto-generated method stub
		System.out.println("DELETE\r\n" + 
		  		"					prenda,\r\n" + 
		  		"					material\r\n" + 
		  		"					FROM \r\n" + 
		  		"						alt_comercial_coordinado_prenda AS prenda, \r\n" + 
		  		"						alt_comercial_coordinado_material AS material  \r\n" + 
		  		"					WHERE\r\n" + 
		  		"						1 = 1  \r\n" + 
		  		"						AND prenda.id_coordinado_prenda = material.id_coordinado_prenda \r\n" + 
		  		"						AND prenda.id_coordinado_prenda="+id);
		
		  Query query = em.createNativeQuery("DELETE\r\n" + 
		  		"					prenda,\r\n" + 
		  		"					material\r\n" + 
		  		"					FROM \r\n" + 
		  		"						alt_comercial_coordinado_prenda AS prenda, \r\n" + 
		  		"						alt_comercial_coordinado_material AS material  \r\n" + 
		  		"					WHERE\r\n" + 
		  		"						1 = 1  \r\n" + 
		  		"						AND prenda.id_coordinado_prenda = material.id_coordinado_prenda \r\n" + 
		  		"						AND prenda.id_coordinado_prenda="+id);
		 
		 
		 query.executeUpdate();
		 
		 Query query2 = em.createNativeQuery("DELETE FROM alt_comercial_coordinado_prenda\r\n" + 
			 		"WHERE id_coordinado_prenda=" +id);
			 
			 
			 query2.executeUpdate();
		 
		
	}
	
	
	
	
    @Override
	public  void saveTelaMaterial(ComercialCoordinadoTela telamaterial){
    	
    	repositoryTelaMaterial.save(telamaterial);
		
	}
    
    
    
    @Override
  	public  void saveForroMaterial(ComercialCoordinadoForro forromaterial){
      	
      	repositoryForroMaterial.save(forromaterial);
  		
  	}

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> ImagenesRuta(Long id, String tipo , String tipo2) {
		try {
			List<Object []> re = em.createNativeQuery("SELECT\r\n" + 
					"	imagen.ruta_prenda,\r\n" + 
					"	imagen.nombre_prenda \r\n" + 
					"FROM\r\n" + 
					"	alt_disenio_prenda_imagen AS imagen \r\n" + 
					"WHERE\r\n" + 
					"	1 = 1 \r\n" + 
					"	AND imagen.id_prenda = "+id+" \r\n" + 
					"	AND ( imagen.nombre_prenda = '"+tipo+"' || imagen.nombre_prenda = '"+tipo2+"' )").getResultList();
		
					
			return re;
			}
			catch(Exception e) {
				List<Object []> aux = null;
				return  aux;
			}
	
	}
    
    
	@Override
	public String ImagenesRutaTela(Long id) {
		try {
			String re = em.createNativeQuery("SELECT\r\n" + 
					"	tela.foto \r\n" + 
					"FROM\r\n" + 
					"	alt_disenio_tela AS tela \r\n" + 
					"WHERE\r\n" + 
					"	1 = 1 \r\n" + 
					"	AND tela.id_tela = "+id).getSingleResult().toString();
					
			return re;
			}
			catch(Exception e) {
				
				return null;
			}
	
	}
	
	@Override
	 public String precioPrenda(Long idCoor , Long idPrenda, Long idTela) {
		
		
		try {
			String re = em.createNativeQuery(""
					+ "SELECT\n" + 
					"CASE\n" + 
					"	WHEN\n" + 
					"		pedido.precio_usar = 1 THEN\n" + 
					"			precio.precio_local_nuevo \n" + 
					"			WHEN pedido.precio_usar = 2 THEN\n" + 
					"			precio.precio_local_antiguo \n" + 
					"			WHEN pedido.precio_usar = 3 THEN\n" + 
					"			precio.precio_foraneo_nuevo \n" + 
					"			WHEN pedido.precio_usar = 4 THEN\n" + 
					"			precio.precio_foraneo_antiguo \n" + 
					"			WHEN pedido.precio_usar = 5 THEN\n" + 
					"			precio.precio_linea_express_local_nuevo \n" + 
					"			WHEN pedido.precio_usar = 6 THEN\n" + 
					"			precio.precio_linea_express_local_anterior \n" + 
					"			WHEN pedido.precio_usar = 7 THEN\n" + 
					"			precio.precio_linea_express_foraneo_nuevo \n" + 
					"			WHEN pedido.precio_usar = 8 THEN\n" + 
					"			precio.precio_linea_express_foraneo_anterior \n" + 
					"			WHEN pedido.precio_usar = 9 THEN\n" + 
					"			precio.precio_muestrario \n" + 
					"			WHEN pedido.precio_usar = 10 THEN\n" + 
					"			precio.precio_venta_interna \n" + 
					"			WHEN pedido.precio_usar = 11 THEN\n" + 
					"			precio.precio_e_commerce \n" + 
					"			WHEN pedido.precio_usar = 12 THEN\n" + 
					"			precio.precio_extra_1 \n" + 
					"			WHEN pedido.precio_usar = 13 THEN\n" + 
					"			precio.precio_extra_2 \n" + 
					"			WHEN pedido.precio_usar = 14 THEN\n" + 
					"			precio.precio_extra_3 ELSE '000' \n" + 
					"		END precio "+
					"FROM\n" + 
					"	alt_comercial_pedido_informacion AS pedido,\n" + 
					"	alt_disenio_lista_precio_prenda AS precio,\n" + 
					"	alt_comercial_coordinado AS coor, \n" + 
					"	alt_disenio_tela as tela\n" + 
					"WHERE\n" + 
					"	1 = 1 \n" + 
					"	AND coor.id_coordinado = "+idCoor+" \n" + 
					"	AND coor.id_pedido = pedido.id_pedido_informacion \n" + 
					"	AND precio.id_prenda = "+idPrenda+"\n" + 
					"	AND tela.id_familia_composicion = precio.id_familia_composicion\n" + 
					"	and tela.id_tela="+idTela).getSingleResult().toString();
					
			return re;
			}
			catch(Exception e) {
				
				return null;
			}
	
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllTelaSPF(Long idPedido) {
	
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\n" + 
				"	tela.id_tela,\n" + 
				"	CONCAT( tela.id_text, ' ', tela.nombre_tela ) AS tela \n" + 
				"FROM\n" + 
				"	alt_comercial_coordinado AS coor,\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda,\n" + 
				"	alt_disenio_tela AS tela,\n" + 
				"	alt_disenio_prenda AS prenda,\n" + 
				"	alt_disenio_lookup AS look,\n" + 
				"	alt_disenio_lookup AS lookGenero \n" + 
				"WHERE\n" + 
				"	1 = 1 \n" + 
				"	AND coor.id_coordinado = coor_prenda.id_coordinado \n" + 
				"	AND tela.id_tela = coor_prenda.id_tela \n" + 
				"	AND prenda.id_prenda = coor_prenda.id_prenda \n" + 
				"	AND look.id_lookup = prenda.id_familia_prenda \n" + 
				"	AND look.nombre_lookup LIKE '%Pantalon%' \n" + 
				"	AND lookGenero.id_lookup = prenda.id_genero \n" + 
				"	AND lookGenero.nombre_lookup = 'Dama' \n" + 
				"	AND tela.estatus_tela = 1 \n" + 
				"	AND tela.estatus = 1 \n" + 
				"	AND coor.id_pedido = "+idPedido+" \n" + 
				"	AND tela.id_tela NOT IN (\n" + 
				"	SELECT\n" + 
				"		coor_prenda.id_tela \n" + 
				"	FROM\n" + 
				"		alt_comercial_coordinado AS coor,\n" + 
				"		alt_comercial_coordinado_prenda AS coor_prenda \n" + 
				"	WHERE\n" + 
				"		1 = 1 \n" + 
				"		AND coor.id_coordinado = coor_prenda.id_coordinado \n" + 
				"		AND coor.id_text = 'COORSPF"+idPedido+"' \n" + 
				"	) \n" + 
				"GROUP BY\n" + 
				"	tela.id_tela \n" + 
				"ORDER BY\n" + 
				"	tela").getResultList();
		return re;
	}
}