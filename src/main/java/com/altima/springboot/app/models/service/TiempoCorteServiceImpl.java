package com.altima.springboot.app.models.service;

import java.util.List;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TiempoCorteServiceImpl implements ITiempoCorteService {
    @PersistenceContext
	private EntityManager em;


    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view() {
        
    	List<Object[]> re = null;
        re = em.createNativeQuery(""
        		+ "SELECT\r\n" + 
        		"COOR_PRENDA.folio,\r\n" + 
        		"LOOKUP.nombre_lookup,\r\n" + 
        		"PRENDA.descripcion_prenda,\r\n" + 
        		"TELA.estampado,\r\n" + 
        		"(SELECT (SUM( conse.cantidad ) + SUM( conse.cantidad_especial )) FROM alt_comercial_concetrado_prenda AS conse,alt_comercial_coordinado_prenda cp  WHERE conse.id_coordinado_prenda = cp.id_coordinado_prenda and cp.folio = COOR_PRENDA.folio) AS Confeccion,\r\n" + 
        		"(SELECT COUNT(cp.tiempo) FROM alt_comercial_coordinado_prenda as cp WHERE cp.folio =  COOR_PRENDA.folio) as OP,\r\n" + 
        		"(SELECT COUNT(tallas.id) from alt_comercial_concentrado_tallas as tallas, alt_comercial_coordinado_prenda cp WHERE tallas.id_prenda_cliente = cp.id_coordinado_prenda and cp.folio=COOR_PRENDA.folio) as tallas,\r\n" + 
        		"(SELECT SUM(cp.tiempo) FROM alt_comercial_coordinado_prenda as cp WHERE cp.folio =  COOR_PRENDA.folio) as tiempo\r\n" + 
        		"FROM\r\n" + 
        		"alt_comercial_coordinado_prenda AS COOR_PRENDA\r\n" + 
        		"INNER JOIN alt_disenio_tela TELA ON TELA.id_tela = COOR_PRENDA.id_tela\r\n" + 
        		"INNER JOIN alt_disenio_prenda PRENDA ON PRENDA.id_prenda = COOR_PRENDA.id_prenda\r\n" + 
        		"INNER JOIN alt_disenio_lookup LOOKUP ON LOOKUP.id_lookup = PRENDA.id_familia_prenda\r\n" + 
        		"INNER JOIN alt_view_apartado_telas_reporte reporte on reporte.id_coordinado_prenda = COOR_PRENDA.id_coordinado_prenda\r\n" + 
        		"WHERE\r\n" + 
        		"1 = 1\r\n" + 
        		"AND ( COOR_PRENDA.folio IS NOT NULL OR COOR_PRENDA.folio != '' )\r\n" + 
        		"AND reporte.Principal_Combinacion ='Principal'\r\n" + 
        		"GROUP BY COOR_PRENDA.folio").getResultList();

		return re;
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> detallesFolio(String folio) {
        
    	List<Object[]> re = null;

        re = em.createNativeQuery(""+
                "SELECT\r\n" + 
                "COO_PRENDA.id_coordinado_prenda,\r\n" + 
                "PEDIDO.id_text,\r\n" + 
                "PEDIDO.fecha_entrega,\r\n" + 
                "COOR.numero_coordinado,\r\n" + 
                "(SELECT ( SUM( conse.cantidad ) + SUM( conse.cantidad_especial ))  \r\n" + 
                                "FROM\r\n" + 
                                    "alt_comercial_concetrado_prenda AS conse \r\n" + 
                                "WHERE\r\n" + 
                                    "conse.id_coordinado_prenda = COO_PRENDA.id_coordinado_prenda ) AS num,\r\n" + 
                "TELA.id_text AS idTexTELA, \r\n" +
                "COO_PRENDA.tiempo AS tiempo \r\n" + 
            "FROM\r\n" + 
                "alt_comercial_pedido_informacion AS PEDIDO\r\n" + 
                "INNER JOIN alt_comercial_coordinado COOR ON PEDIDO.id_pedido_informacion = COOR.id_pedido\r\n" + 
                "INNER JOIN alt_comercial_coordinado_prenda COO_PRENDA ON COO_PRENDA.id_coordinado = COOR.id_coordinado\r\n" + 
                "INNER JOIN alt_disenio_prenda PRENDA ON PRENDA.id_prenda = COO_PRENDA.id_prenda\r\n" + 
                "INNER JOIN alt_disenio_lookup LOOK_PRENDA ON PRENDA.id_familia_prenda = LOOK_PRENDA.id_lookup\r\n" + 
                "LEFT JOIN alt_produccion_lookup LOOK_RUTA ON COO_PRENDA.id_ruta = LOOK_RUTA.id_lookup\r\n" + 
                "INNER JOIN alt_disenio_tela TELA ON COO_PRENDA.id_tela = TELA.id_tela\r\n" + 
                "INNER JOIN alt_disenio_lookup LOOK_TELA ON LOOK_TELA.id_lookup = TELA.id_familia_composicion\r\n" + 
                "INNER JOIN alt_view_apartado_telas_reporte reporte ON reporte.id_coordinado_prenda = COO_PRENDA.id_coordinado_prenda\r\n" + 
            "WHERE\r\n" + 
                "1 = 1\r\n" + 
                "AND COO_PRENDA.folio= '"+folio+"'\r\n" + 
                "GROUP BY id_coordinado_prenda").getResultList();

		return re;
    }
    
}
