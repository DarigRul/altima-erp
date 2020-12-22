package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.repository.CargaPedidoRepository;

@Service
public class EmpalmeTelasServiceImpl implements IEmpalmeTelasService {

    @PersistenceContext
	private EntityManager em;

	@Autowired
	CargaPedidoRepository repository;
    
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view() {
    	List<Object[]> re = null;

		re = em.createNativeQuery(""
				+ "SELECT\r\n" + 
				"	COO_PRENDA.id_coordinado_prenda,\r\n" + 
				"	PEDIDO.id_text,\r\n" + 
				"	PEDIDO.fecha_entrega,\r\n" + 
				"	COO_PRENDA.programa,\r\n" + 
				"	COOR.numero_coordinado,\r\n" + 
				"	PRENDA.descripcion_prenda,\r\n" + 
				"	LOOK_PRENDA.nombre_lookup,\r\n" + 
				"	(SELECT IF (SUM( bordado.precio_bordado )= 0, \"A\", \"N/A\" )  FROM alt_comercial_prenda_bordado AS bordado  WHERE bordado.id_coordinado_prenda = COO_PRENDA.id_coordinado_prenda ),\r\n" + 
				"	LOOK_RUTA.nombre_lookup AS RUTA,\r\n" + 
				"	'cofec',\r\n" + 
				"	TELA.id_text as idTextTela,\r\n" + 
				"	TELA.ancho,\r\n" + 
				"	LOOK_TELA.nombre_lookup as famTela,\r\n" + 
				"	TELA.prueba_encogimiento,\r\n" + 
				"	TELA.estampado,\r\n" + 
				"	'esta',\r\n" + 
				"	COO_PRENDA.folio \r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_pedido_informacion AS PEDIDO\r\n" + 
				"	INNER JOIN alt_comercial_coordinado COOR ON PEDIDO.id_pedido_informacion = COOR.id_pedido\r\n" + 
				"	INNER JOIN alt_comercial_coordinado_prenda COO_PRENDA ON COO_PRENDA.id_coordinado = COOR.id_coordinado\r\n" + 
				"	INNER JOIN alt_disenio_prenda PRENDA ON PRENDA.id_prenda = COO_PRENDA.id_prenda\r\n" + 
				"	INNER JOIN alt_disenio_lookup LOOK_PRENDA ON PRENDA.id_familia_prenda = LOOK_PRENDA.id_lookup\r\n" + 
				"	LEFT JOIN alt_produccion_lookup LOOK_RUTA ON COO_PRENDA.id_ruta = LOOK_RUTA.id_lookup\r\n" + 
				"	INNER JOIN alt_disenio_tela TELA ON COO_PRENDA.id_tela = TELA.id_tela\r\n" + 
				"	INNER JOIN alt_disenio_lookup LOOK_TELA ON LOOK_TELA.id_lookup = TELA.id_familia_composicion \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND PEDIDO.estatus = 3").getResultList();

		return re;
    }
    
}
