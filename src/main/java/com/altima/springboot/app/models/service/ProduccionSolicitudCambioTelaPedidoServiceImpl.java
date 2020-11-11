package com.altima.springboot.app.models.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoForro;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoTela;
import com.altima.springboot.app.models.entity.ComercialCoordinadoTela;
import com.altima.springboot.app.models.entity.ProduccionSolicitudCambioTelaPedido;
import com.altima.springboot.app.repository.ProduccionCoordinadoForroRepository;
import com.altima.springboot.app.repository.ProduccionCoordinadoMaterialRepository;
import com.altima.springboot.app.repository.ProduccionCoordinadoPrendaRepository;
import com.altima.springboot.app.repository.ProduccionCoordinadoTelaRepository;
import com.altima.springboot.app.repository.ProduccionSolicitudCambioTelaPedidoRepository;
@Service
public class ProduccionSolicitudCambioTelaPedidoServiceImpl implements IProduccionSolicitudCambioTelaPedidoService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	ProduccionCoordinadoPrendaRepository repositoryPrenda;
	
	@Autowired
	ProduccionCoordinadoMaterialRepository repositoryMaterial;
	
	@Autowired
	ProduccionCoordinadoTelaRepository repositoryTelaMaterial;
	
	@Autowired
	ProduccionCoordinadoForroRepository repositoryForroMaterial;

	@Autowired
	private ProduccionSolicitudCambioTelaPedidoRepository repository;
	@Override
	public List<ProduccionSolicitudCambioTelaPedido> findAll() {
		// TODO Auto-generated method stub
		return (List<ProduccionSolicitudCambioTelaPedido>) repository.findAll();
	}

	@Override
	public void save(ProduccionSolicitudCambioTelaPedido obj) {
		// TODO Auto-generated method stub
		repository.save(obj);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public ProduccionSolicitudCambioTelaPedido findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> pedidosCerrados(Long idUser) {

		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	acpi.id_pedido_informacion,\r\n" + 
				"	acpi.id_text \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_pedido_informacion acpi\r\n" + 
				"	INNER JOIN alt_hr_usuario ahu ON ahu.id_usuario = acpi.id_usuario\r\n" + 
				"	INNER JOIN alt_hr_empleado ahe ON ahe.id_empleado = ahu.id_empleado\r\n" + 
				"	INNER JOIN alt_comercial_cliente acc ON acc.id_cliente = acpi.id_empresa \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND acpi.estatus = 3 \r\n" + 
				"AND IF("+idUser+"=0,1=1,ahe.id_empleado="+idUser+")   "+
				"ORDER BY\r\n" + 
				"	id_pedido_informacion DESC").getResultList();
		return re;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> infPedido(Long id) {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT \r\n" + 
				"cliente.nombre,\r\n" + 
				"	DATE_FORMAT(pedido.fecha_entrega, '%Y-%m-%d' )\n" + 
				"FROM \r\n" + 
				"alt_comercial_pedido_informacion as pedido ,\r\n" + 
				"alt_comercial_cliente as cliente\r\n" + 
				"WHERE\r\n" + 
				"1=1\r\n" + 
				"AND cliente.id_cliente = pedido.id_empresa\r\n" + 
				"AND pedido.id_pedido_informacion="+id).getResultList();
		return re;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> View(Long idAgente) {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	solicitud.id_tela_pedido,\r\n" + 
				"	solicitud.id_text AS idSolicitud,\r\n" + 
				"	CONCAT(ahe.nombre_persona,' ',ahe.apellido_paterno,' ',ahe.apellido_materno) agente,\r\n" + 
				"	solicitud.fecha_creacion,\r\n" + 
				"	concat(acc.nombre,' ',ifnull(acc.apellido_paterno,''),'',ifnull(acc.apellido_materno,'')) cliente,\r\n" + 
				"	acpi.id_text AS idPeido,\r\n" + 
				"	acpi.fecha_entrega,\r\n" + 
				"	CASE\r\n" + 
				"    WHEN solicitud.estatus_envio = 0 THEN \"No enviado\"\r\n" + 
				"    WHEN solicitud.estatus_envio = 1 THEN \"Enviado\"\r\n" + 
				"		WHEN solicitud.estatus_envio = 2 THEN \"Aceptado\"\r\n" + 
				"		WHEN solicitud.estatus_envio = 3 THEN \"Rechazado\"\r\n" + 
				"END,\r\n" + 
				"	acpi.id_pedido_informacion \r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_solicitud_cambio_tela_pedido solicitud\r\n" + 
				"	INNER JOIN alt_comercial_pedido_informacion acpi ON acpi.id_pedido_informacion = solicitud.id_pedido\r\n" + 
				"	INNER JOIN alt_hr_usuario ahu ON ahu.id_usuario = acpi.id_usuario\r\n" + 
				"	INNER JOIN alt_hr_empleado ahe ON ahe.id_empleado = ahu.id_empleado\r\n" + 
				"	INNER JOIN alt_comercial_cliente acc ON acc.id_cliente = acpi.id_empresa "+
				"WHERE IF("+idAgente+"=0,1=1,ahe.id_empleado="+idAgente+")   "
						+ "ORDER BY id_tela_pedido desc").getResultList();
		return re;
	}

	@Override
	public void saveCoorPrenda(ProduccionCoordinadoPrenda prenda) {
		repositoryPrenda.save(prenda);
		
	}
	
	@Override
	public void saveCoorMaterial(ProduccionCoordinadoMaterial material) {
		repositoryMaterial.save(material);
		
	}
	@Override
	public  void saveTelaMaterial(ProduccionCoordinadoTela telamaterial){
    	
    	repositoryTelaMaterial.save(telamaterial);
		
	}
	@Override
  	public  void saveForroMaterial(ProduccionCoordinadoForro forromaterial){
      	
      	repositoryForroMaterial.save(forromaterial);
  		
  	}
	@Override
	public ProduccionCoordinadoPrenda BuscarCambio(Long id, Long idSolicitud) {
		try {
			
			return  (ProduccionCoordinadoPrenda)em.createQuery("from ProduccionCoordinadoPrenda  where id_coordinado_prenda_cambio="+id +" AND id_solicitud_cambio_tela="+idSolicitud).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
		
		 
				
	}
	
	@Override
	@Transactional
	public void deletePrenda(Long id) {
		// eliminar materales 
		  Query query = em.createNativeQuery("DELETE\r\n" + 
		  		"	material\r\n" + 
		  		"FROM\r\n" + 
		  		"	alt_produccion_coordinado_prenda AS prenda,\r\n" + 
		  		"	alt_produccion_coordinado_material AS material \r\n" + 
		  		"WHERE\r\n" + 
		  		"	1 = 1 \r\n" + 
		  		"	AND material.id_coordinado_prenda= prenda.id_coordinado_prenda\r\n" + 
		  		"	AND prenda.id_coordinado_prenda_cambio = "+id);
		 query.executeUpdate();
		 
		// eliminar tela 
		  Query queryTela = em.createNativeQuery("DELETE\r\n" + 
		  		"tela\r\n" + 
		  		"FROM\r\n" + 
		  		"	alt_produccion_coordinado_prenda AS prenda,\r\n" + 
		  		"	alt_produccion_coordinado_tela AS tela \r\n" + 
		  		"WHERE\r\n" + 
		  		"	1 = 1 \r\n" + 
		  		"	AND tela.id_coordinado_prenda= prenda.id_coordinado_prenda\r\n" + 
		  		"	AND prenda.id_coordinado_prenda_cambio = "+id);
		 queryTela.executeUpdate();
		 
		// eliminar forro 
		  Query queryForro = em.createNativeQuery("DELETE\r\n" + 
		  		"forro\r\n" + 
		  		"FROM\r\n" + 
		  		"	alt_produccion_coordinado_prenda AS prenda,\r\n" + 
		  		"	alt_produccion_coordinado_forro AS forro \r\n" + 
		  		"WHERE\r\n" + 
		  		"	1 = 1 \r\n" + 
		  		"	AND forro.id_coordinado_prenda= prenda.id_coordinado_prenda\r\n" + 
		  		"	AND prenda.id_coordinado_prenda_cambio = "+id);
		 queryForro.executeUpdate();
		 
		 
		// eliminar forro 
		  Query queryPrenda = em.createNativeQuery(""
		  		+ "DELETE\r\n" + 
		  		"prenda\r\n" + 
		  		"FROM\r\n" + 
		  		"	alt_produccion_coordinado_prenda AS prenda\r\n" + 
		  		"WHERE\r\n" + 
		  		"	1 = 1 \r\n" + 
		  		"	AND prenda.id_coordinado_prenda_cambio = "+id);
		  queryPrenda.executeUpdate();
	
	}

	@Override
	public ProduccionCoordinadoPrenda findOnePrenda(Long id, Long idSolicitud) {
		try {
			return  (ProduccionCoordinadoPrenda)em.createQuery("from ProduccionCoordinadoPrenda  where id_coordinado_prenda ="+id +" AND  id_solicitud_cambio_tela="+idSolicitud).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
	}

	@Override
	public ProduccionCoordinadoMaterial findOneMateril(Long idMaterial, Long idCoorPrenda) {

		try {
			return  (ProduccionCoordinadoMaterial)em.createQuery("from ProduccionCoordinadoMaterial  where id_coordinado_prenda ="+idCoorPrenda +" AND  id_material="+idMaterial).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
	}

	@Override
	public ProduccionCoordinadoTela findOneTela(Long idTela, Long idCoorPrenda) {
		try {
			return  (ProduccionCoordinadoTela)em.createQuery("from ProduccionCoordinadoTela  where id_coordinado_prenda ="+idCoorPrenda +" AND  id_tela="+idTela).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
	}

	@Override
	public ProduccionCoordinadoForro findOnedForro(Long idForro, Long idCoorPrenda) {
		try {
			return  (ProduccionCoordinadoForro)em.createQuery("from ProduccionCoordinadoForro  where id_coordinado_prenda ="+idCoorPrenda +" AND  id_forro	="+idForro).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> QueryExtracionCambios(Long id) {
		// TODO Auto-generated method stub
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"		coor_prenda.id_coordinado_prenda_cambio as actual,\r\n" + 
				"		coor_prenda.id_coordinado_prenda as cambio\r\n" + 
				"		FROM\r\n" + 
				"		alt_produccion_solicitud_cambio_tela_pedido as solicitud,\r\n" + 
				"		alt_produccion_coordinado_prenda as coor_prenda\r\n" + 
				"		WHERE\r\n" + 
				"		1=1\r\n" + 
				"		and coor_prenda.id_solicitud_cambio_tela= solicitud.id_tela_pedido\r\n" + 
				"		and solicitud.id_tela_pedido="+id).getResultList();
		return re;
		
		
	}

	@Override
	@Transactional
	public void actualizar(Long idActual, Long idCambio , String actualizo, String fecha) {
		
		Query queryMaterial = em.createNativeQuery(""
				+ "UPDATE alt_comercial_coordinado_material AS actual\r\n" + 
				"INNER JOIN alt_produccion_coordinado_material AS cambio ON ( actual.id_material = cambio.id_material ) \r\n" + 
				"SET actual.id_material = cambio.id_material,\r\n" + 
				"actual.color = cambio.color,\r\n" + 
				"actual.color_codigo = cambio.color_codigo,\r\n" + 
				"actual.actualizado_por = '"+actualizo+"',\r\n" + 
				"actual.ultima_fecha_modificacion = '"+fecha+"'\r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND actual.id_coordinado_prenda = "+idActual+" \r\n" + 
				"	AND cambio.id_coordinado_prenda ="+idCambio);
		queryMaterial.executeUpdate();
		Query queryTelaDelete = em.createNativeQuery(""+
			"DELETE FROM alt_comercial_coordinado_tela "+
			"WHERE  "+
			"id_coordinado_prenda ="+idActual);
		queryTelaDelete.executeUpdate();
		Query queryTela = em.createNativeQuery(""
				+ "INSERT INTO alt_comercial_coordinado_tela\r\n" + 
				"	( id_coordinado_tela,\r\n" + 
				"	id_coordinado_prenda,\r\n" + 
				"	id_tela, \r\n" + 
				"	descripcion,\r\n" + 
				"	creado_por,actualizado_por,\r\n" + 
				"	fecha_creacion,\r\n" + 
				"	ultima_fecha_modificacion, \r\n" + 
				"	estatus ) \r\n" + 
				"	SELECT\r\n" + 
				"	null,\r\n" + 
				"	457,\r\n" + 
				"	tela2.id_tela,\r\n" + 
				"	null,\r\n" + 
				"	tela2.creado_por,\r\n" + 
				"	'"+actualizo+"',\r\n" + 
				"	tela2.fecha_creacion,\r\n" + 
				"	'"+fecha+"' ,\r\n" + 
				"	1\r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_coordinado_tela AS tela2 \r\n" + 
				"WHERE\r\n" + 
				"	tela2.id_coordinado_prenda="+idCambio);
		queryTela.executeUpdate();
		
		Query queryForroDelete = em.createNativeQuery(""
				+ "DELETE FROM alt_comercial_coordinado_forro\r\n" + 
				"		WHERE\r\n" + 
				"		id_coordinado_prenda ="+idActual);
		queryForroDelete.executeUpdate();
		
		Query queryForro = em.createNativeQuery(""
				+ "INSERT INTO alt_comercial_coordinado_forro\r\n" + 
				"	( id_coordinado_forro,\r\n" + 
				"	id_coordinado_prenda,\r\n" + 
				"	id_forro, \r\n" + 
				"	descripcion,\r\n" + 
				"	creado_por,actualizado_por,\r\n" + 
				"	fecha_creacion,\r\n" + 
				"	ultima_fecha_modificacion, \r\n" + 
				"	estatus ) \r\n" + 
				"	SELECT\r\n" + 
				"	null,\r\n" + 
				"	457,\r\n" + 
				"	forro2.id_forro,\r\n" + 
				"	null,\r\n" + 
				"	forro2.creado_por,\r\n" + 
				"	'"+actualizo+"',\r\n" + 
				"	forro2.fecha_creacion,\r\n" + 
				"	'"+fecha+"' ,\r\n" + 
				"	1\r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_coordinado_forro AS forro2 \r\n" + 
				"WHERE\r\n" + 
				"	forro2.id_coordinado_prenda="+idCambio);
		queryForro.executeUpdate();
		
		Query queryPrenda = em.createNativeQuery(""
				+ "UPDATE alt_comercial_coordinado_prenda \r\n" + 
				"		SET id_tela = ( SELECT id_tela FROM alt_produccion_coordinado_prenda WHERE id_coordinado_prenda = "+idCambio+" ) ,\r\n" + 
				"		actualizado_por='"+actualizo+"',\r\n" + 
				"		ultima_fecha_modificacion='"+fecha+"'\r\n" + 
				"		WHERE\r\n" + 
				"			id_coordinado_prenda = "+idActual);
		queryPrenda.executeUpdate();
		
		
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> detalles(Long id) {
		List<Object[]> re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	CONCAT( 'Principal ', tela.nombre_tela ),\r\n" + 
				"	tela.color,\r\n" + 
				"	tela.codigo_color \r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_coordinado_prenda AS prenda,\r\n" + 
				"	alt_produccion_solicitud_cambio_tela_pedido AS solicitud,\r\n" + 
				"	alt_disenio_tela AS tela \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND solicitud.id_tela_pedido ="+id+"\r\n" + 
				"	AND solicitud.id_tela_pedido = prenda.id_solicitud_cambio_tela\r\n" + 
				"	AND prenda.id_tela = tela.id_tela \r\n" + 
				"	union all\r\n" + 
				"	SELECT\r\n" + 
				"	material.nombre_material,\r\n" + 
				"	CM.color,\r\n" + 
				"	CM.color_codigo \r\n" + 
				"FROM\r\n" + 
				"	alt_disenio_material AS material,\r\n" + 
				"	alt_produccion_coordinado_material AS CM,\r\n" + 
				"	alt_produccion_coordinado_prenda AS CP,\r\n" + 
				"	alt_produccion_solicitud_cambio_tela_pedido AS sol \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND CM.id_material = material.id_material \r\n" + 
				"	AND CM.id_coordinado_prenda = CP.id_coordinado_prenda \r\n" + 
				"	AND CP.id_solicitud_cambio_tela = sol.id_tela_pedido \r\n" + 
				"	AND sol.id_tela_pedido = "+id+"\r\n" + 
				"	AND CM.color != (\r\n" + 
				"	SELECT\r\n" + 
				"		MaterialActual.color \r\n" + 
				"	FROM\r\n" + 
				"		alt_comercial_coordinado_material AS MaterialActual \r\n" + 
				"	WHERE\r\n" + 
				"		MaterialActual.id_coordinado_prenda = CP.id_coordinado_prenda_cambio \r\n" + 
				"	AND MaterialActual.id_material = material.id_material \r\n" + 
				"	)\r\n" + 
				"	union all\r\n" + 
				"	SELECT\r\n" + 
				"	CONCAT( 'Combinación ', tela.nombre_tela ),\r\n" + 
				"	tela.color,\r\n" + 
				"	tela.codigo_color \r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_coordinado_tela AS coorTela,\r\n" + 
				"	alt_disenio_tela AS tela,\r\n" + 
				"	alt_produccion_coordinado_prenda AS CP,\r\n" + 
				"	alt_produccion_solicitud_cambio_tela_pedido AS sol \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND tela.id_tela = coorTela.id_tela \r\n" + 
				"	AND coorTela.id_coordinado_prenda = CP.id_coordinado_prenda \r\n" + 
				"	AND CP.id_solicitud_cambio_tela = sol.id_tela_pedido \r\n" + 
				"	AND sol.id_tela_pedido ="+id+"\r\n" + 
				"	union all\r\n" + 
				"	SELECT\r\n" + 
				"	CONCAT( 'Forro ', forro.nombre_forro ),\r\n" + 
				"	forro.color,\r\n" + 
				"	forro.codigo_color \r\n" + 
				"FROM\r\n" + 
				"	alt_produccion_coordinado_forro AS coorForro,\r\n" + 
				"	alt_disenio_forro AS forro,\r\n" + 
				"	alt_produccion_coordinado_prenda AS CP,\r\n" + 
				"	alt_produccion_solicitud_cambio_tela_pedido AS sol \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND forro.id_forro = coorForro.id_forro \r\n" + 
				"	AND coorForro.id_coordinado_prenda = CP.id_coordinado_prenda \r\n" + 
				"	AND CP.id_solicitud_cambio_tela = sol.id_tela_pedido \r\n" + 
				"	AND sol.id_tela_pedido ="+id+"\r\n").getResultList();
		return re;//hooool munf
		//hollla
	}



	@SuppressWarnings("unchecked")
	@Transactional
	public List <Object []> buscarTelaConv  (Long idPrenda, Long idCoorPrenda ) {
		List<Object[]> re = em.createNativeQuery(""
		+ "SELECT\r\n" + 
		"material.id_material,  \r\n" + 
		"material.nombre_material , \r\n" + 
		"look.nombre_lookup,\r\n" + 
		"tela.id_tela\r\n" + 
		"FROM\r\n" + 
		"alt_disenio_material_prenda AS material_prenda,\r\n" + 
		"alt_disenio_material AS material,\r\n" + 
		"alt_disenio_lookup adl ,\r\n" + 
		"alt_disenio_lookup AS look , \r\n" + 
		"alt_comercial_coordinado_tela as tela\r\n" + 
		"\r\n" + 
		"WHERE \r\n" + 
		"1 = 1\r\n" + 
		"AND look.id_lookup = material.id_clasificacion\r\n" + 
		"AND look.nombre_lookup  IN ( 'Combinación' )\r\n" + 
		"AND material.id_material = material_prenda.id_material\r\n" + 
		"AND material.id_proceso = adl.id_lookup\r\n" + 
		"AND material_prenda.id_prenda = "+idPrenda+"\r\n" + 
		"AND tela.descripcion = material.id_material \r\n"+
		"AND tela.id_coordinado_prenda="+idCoorPrenda  ).getResultList();
		
		 return re;
				
	}
}
