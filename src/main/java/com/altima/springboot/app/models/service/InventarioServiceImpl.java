package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioPrendaImagen;
import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.repository.ComercialInventarioRepository;
import com.altima.springboot.app.repository.DisenioImagenPrendaRepository;

@Service
public class InventarioServiceImpl implements IInventarioService {
	
	@Autowired
	private  ComercialInventarioRepository repository;
	
	
	@Autowired
	private  DisenioImagenPrendaRepository diseño;

	@Autowired
	private EntityManager em;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProduccionDetallePedido> listInventario(){
	
		return em.createNativeQuery("SELECT\r\n" + 
				"    po.id_detalle_pedido AS Id,\r\n" + 
				"    po.id_text AS codigo,\r\n" + 
				"    dl2.nombre_lookup AS Prenda,\r\n" + 
				"    po.talla AS talla,\r\n" + 
				"    po.largo AS largo,\r\n" + 
				"    dt.nombre_tela AS tela,\r\n" + 
				"    dl.nombre_lookup AS Genero,\r\n" + 
				"    po.costo AS price,\r\n" + 
				"    po.estatus_confeccion AS estatus_confeccion,\r\n" + 
				"    pre3.id_prenda,\r\n" + 
				"    po.estatus AS estatus,\r\n" + 
				"    dt.id_tela AS idTela,\r\n" + 
				"    precio.precio_muestrario,\r\n" + 
				"    IF(pre3.estatus_recepcion_muestra ='Definitivo', pre3.id_text, pre3.id_text_prospecto) as modelote\r\n" + 
				"FROM\r\n" + 
				"    alt_produccion_detalle_pedido po\r\n" + 
				"INNER JOIN alt_disenio_prenda pre3 ON\r\n" + 
				"    pre3.id_prenda = po.id_prenda AND estatus_confeccion = '2' AND (po.estatus = '1' OR po.estatus = '2' OR po.estatus = '3')\r\n" + 
				"INNER JOIN alt_disenio_tela dt ON\r\n" + 
				"    dt.id_tela = po.id_tela\r\n" + 
				"INNER JOIN alt_disenio_lista_precio_prenda precio ON\r\n" + 
				"    precio.id_prenda = po.id_prenda AND dt.id_familia_composicion = precio.id_familia_composicion\r\n" + 
				"INNER JOIN alt_disenio_lookup dl2 ON\r\n" + 
				"    dl2.id_lookup = pre3.id_familia_prenda\r\n" + 
				"INNER JOIN alt_disenio_lookup dl ON\r\n" + 
				"    dl.id_lookup = pre3.id_genero").getResultList();
	}
	
	
	
	@Override
	public Object findOnePreImg(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT * FROM alt_disenio_prenda_imagen WHERE id_prenda="+id+" and nombre_prenda='Inventario';").getResultList();
	}
	
	
	@Override
	@Transactional
	public void save(DisenioPrendaImagen diseñoPrendaImagen) {
		// TODO Auto-generated method stub
		diseño.save(diseñoPrendaImagen);
	
}



	@Override
	public DisenioPrendaImagen findOne(Long Id) {
		// TODO Auto-generated method stub
		return diseño.findById(Id).orElse(null);
	}
	
	
	
	@Transactional
	@Override	
	public String Exist(Long id) {
		
		String val =em.createNativeQuery("SELECT if ((SELECT COUNT(*) FROM alt_disenio_prenda_imagen " +
				 					  	"	WHERE id_prenda="+id+" and nombre_prenda='Inventario')>0, 1 , 0);").getSingleResult().toString();
		return val;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ProduccionDetallePedido> listCatalogoInventario() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT 'stock' AS stock, \n" + 
				"		po.id_detalle_pedido AS Id, \n" + 
				"		po.id_text AS codigo, \n" + 
				"		dl2.nombre_lookup AS Prenda, \n" + 
				"		po.talla AS talla, \n" + 
				"		po.largo AS largo, \n" + 
				"		dt.nombre_tela AS tela, \n" + 
				"		dl.nombre_lookup AS Genero, \n" + 
				"		po.costo AS price, \n" + 
				"		po.estatus_confeccion AS estatus_confeccion, \n" + 
				"		pre3.id_prenda, \n" + 
				"		po.estatus AS estatus, \n" + 
				"		dt.id_tela AS idTela, \n" + 
				"		imagen.ruta_prenda AS imagen, \n" + 
				"		precio.precio_muestrario, \n" + 
				"		IF ( pre3.estatus_recepcion_muestra = 'Definitivo', pre3.id_text, pre3.id_text_prospecto ) AS modelote, \n" + 
				"		pre3.descripcion_prenda \n" + 
				"		\n" + 
				"	FROM alt_produccion_detalle_pedido po \n" + 
				"	\n" + 
				"	INNER JOIN alt_disenio_prenda pre3 ON pre3.id_prenda = po.id_prenda AND estatus_confeccion = '2' AND po.estatus = '1'\n" + 
				"	INNER JOIN alt_disenio_tela dt ON dt.id_tela = po.id_tela \n" + 
				"	INNER JOIN alt_disenio_lista_precio_prenda precio ON precio.id_prenda = po.id_prenda AND dt.id_familia_composicion = precio.id_familia_composicion\n" + 
				"	INNER JOIN alt_disenio_lookup dl2 ON dl2.id_lookup = pre3.id_familia_prenda \n" + 
				"	INNER JOIN alt_disenio_lookup dl ON dl.id_lookup = pre3.id_genero \n" + 
				"	LEFT JOIN alt_comercial_imagen_inventario imagen ON imagen.id_prenda = po.id_prenda AND imagen.id_tela = dt.id_tela").getResultList();
	}


	
	
	
}
