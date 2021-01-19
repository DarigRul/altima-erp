package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
				"	(SELECT\r\n" + 
				"	IF( SUM( bordado.precio_bordado )= 0, \"A\", \"N/A\" ) \r\n" + 
				"	FROM\r\n" + 
				"		alt_comercial_prenda_bordado AS bordado \r\n" + 
				"	WHERE\r\n" + 
				"		bordado.id_coordinado_prenda = COO_PRENDA.id_coordinado_prenda ),\r\n" + 
				"	LOOK_RUTA.nombre_lookup AS RUTA,\r\n" + 
				"	(SELECT ( SUM( conse.cantidad ) + SUM( conse.cantidad_especial )) \r\n" + 
				"	FROM\r\n" + 
				"		alt_comercial_concetrado_prenda AS conse \r\n" + 
				"	WHERE\r\n" + 
				"		conse.id_coordinado_prenda = COO_PRENDA.id_coordinado_prenda ) AS Confeccion,\r\n" + 
				"	TELA.id_text as idTexTELA,\r\n" + 
				"	TELA.ancho,\r\n" + 
				"	LOOK_TELA.nombre_lookup as famlia,\r\n" + 
				"	TELA.prueba_encogimiento,\r\n" + 
				"	TELA.estampado,\r\n" + 
				"	CASE PEDIDO.estatus_explosion_materia_prima\r\n" + 
				"	WHEN 0 THEN\r\n" + 
				"		'Faltante'\r\n" + 
				"	WHEN 1 THEN \r\n" + 
				"			CASE (SELECT estatus  FROM alt_amp_tela_faltante  WHERE id_tela = TELA.id_tela and id_pedido = PEDIDO.id_pedido_informacion)\r\n" + 
				"         WHEN 0 THEN 'Faltante'\r\n" + 
				"				 WHEN 1 THEN 'Faltante'\r\n" + 
				"				 WHEN 2 THEN 'Faltante'\r\n" + 
				"				 WHEN 3 THEN 'Faltante'\r\n" + 
				"				 WHEN 4 THEN 'Completo'\r\n" + 
				"	      ELSE 'Completo'\r\n" + 
				"			END \r\n" + 
				"	ELSE\r\n" + 
				"		'00'\r\n" + 
				"END  as estatus,	\r\n" + 
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
				"	INNER JOIN alt_view_apartado_telas_reporte reporte on reporte.id_coordinado_prenda = COO_PRENDA.id_coordinado_prenda\r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND PEDIDO.estatus=3\r\n" + 
				"	GROUP BY reporte.id_coordinado_prenda").getResultList();

		return re;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> detallesTelas(Long idCoorPrenda) {
    	List<Object[]> re = null;

		re = em.createNativeQuery(""
				+ "SELECT \r\n" + 
				"tela.id_text,\r\n" + 
				"tela.color,\r\n" + 
				"reporte.Principal_Combinacion,\r\n" + 
				"'Fecha',\r\n" + 
				"ROUND(SUM(reporte.Consumo),2),\r\n" + 
				"CASE\r\n" + 
				"		pedido.estatus_explosion_materia_prima \r\n" + 
				"		WHEN 0 THEN\r\n" + 
				"		'Faltante' \r\n" + 
				"		WHEN 1 THEN\r\n" + 
				"	CASE\r\n" + 
				"			( SELECT estatus FROM alt_amp_tela_faltante WHERE id_tela = tela.id_tela AND id_pedido = pedido.id_pedido_informacion ) \r\n" + 
				"			WHEN 0 THEN 'Faltante'\r\n" + 
				"				 WHEN 1 THEN 'Faltante'\r\n" + 
				"				 WHEN 2 THEN 'Faltante'\r\n" + 
				"				 WHEN 3 THEN 'Faltante'\r\n" + 
				"				 WHEN 4 THEN 'Completo'\r\n" + 
				"	    ELSE 'Completo'\r\n" + 
				"	\r\n" + 
				"		END ELSE '00' \r\n" + 
				"	END AS estatus \r\n" + 
				"\r\n" + 
				"FROM\r\n" + 
				"alt_view_apartado_telas_reporte  as reporte,\r\n" + 
				"alt_disenio_tela as tela,\r\n" + 
				"alt_comercial_pedido_informacion as pedido\r\n" + 
				"WHERE\r\n" + 
				"1=1\r\n" + 
				"and reporte.id_tela = tela.id_tela\r\n" + 
				"and reporte.idPedido = pedido.id_pedido_informacion\r\n" + 
				"and reporte.id_coordinado_prenda= "+idCoorPrenda+" \r\n" + 
				"GROUP BY tela.id_tela ORDER BY reporte.Principal_Combinacion").getResultList();

		return re;
    }
    
}
