package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.altima.springboot.app.models.entity.ProduccionIncidencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionIncidenciaServiceImpl implements IProduccionIncidenciaService {

    @Autowired
    private EntityManager em;

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> maquileros() {
        List<Object []> re = null;
        re=em.createNativeQuery(""+
            "SELECT\r\n" +
                "m.id_maquilador,\r\n" +
                "m.nombre\r\n" +
            "FROM\r\n" +
                "alt_produccion_maquilador AS m\r\n" +
            "WHERE\r\n" +
            "1 = 1\r\n" +
            "AND m.estatus =1\r\n" +
            "ORDER BY m.nombre asc").getResultList();
        return re;
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> pedidos(Long idMaquilero) {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
            "SELECT\r\n" +
                "DISTINCT pedido.id_pedido_informacion,\r\n" +
                "pedido.id_text\r\n" +
            "FROM\r\n" +
                "alt_produccion_explosion_prendas AS explosionPrenda\r\n" +
                "INNER JOIN alt_produccion_explosion_procesos explosionProceso ON explosionPrenda.id_explosion_proceso = explosionProceso.id_explosion_procesos\r\n" +
                "INNER JOIN alt_comercial_pedido_informacion pedido ON pedido.id_pedido_informacion = explosionProceso.id_pedido\r\n" +
           "WHERE\r\n" +
                "1 = 1\r\n" +
                "AND explosionPrenda.realizo = "+idMaquilero +"\r\n" +
                "ORDER BY pedido.id_text desc").getResultList();
		return re;
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> OP(Long idPedido) {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
            "SELECT\r\n" +
                "cp.id_coordinado_prenda, cp.creado_por\r\n" +
            "FROM\r\n" +
                "alt_comercial_pedido_informacion AS pedido\r\n" +
                "INNER JOIN alt_comercial_coordinado coor ON coor.id_pedido = pedido.id_pedido_informacion\r\n" +
                "INNER JOIN alt_comercial_coordinado_prenda cp ON cp.id_coordinado = coor.id_coordinado\r\n" +
            "WHERE\r\n" +
                "1 = 1\r\n" +
                "AND pedido.id_pedido_informacion ="+idPedido).getResultList();
        return re;
    }

    @Transactional
	@Override
    public void save(ProduccionIncidencia obj) {
        if (obj.getIdIncidencia() != null && obj.getIdIncidencia()> 0) {
			em.merge(obj);

		} else {
			em.persist(obj);
		}
        
    }

    @Override
    public ProduccionIncidencia findOne(Long id) {
        // TODO Auto-generated method stub
        return em.find(ProduccionIncidencia.class, id);
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view() {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
                "SELECT\r\n" +
                    "inci.id_incidencia,\r\n" +
                    "inci.folio,\r\n" +
                    "maquilador.nombre,\r\n" +
                    "inci.fecha_creacion,\r\n" +
                    "pedido.id_text,\r\n" +
                    "coor_pre.id_coordinado_prenda,\r\n" +
                    "coor.id_text as coor,\r\n" +
                    "prenda.descripcion_prenda,\r\n" +
                    "look.nombre_lookup,\r\n" +
                    "tela.id_tela,\r\n" +
                    "inci.cantidad,\r\n" +
                    "if(inci.descuento=1, 'Si','No'),\r\n" +
                    "inci.cantidad_descuento ,inci.creado_por, inci.estatus\r\n" +
                "FROM\r\n" +
                    "alt_produccion_incidencia inci\r\n" +
                    "INNER JOIN alt_produccion_maquilador maquilador on maquilador.id_maquilador = inci.id_maquilero\r\n" +
                    "INNER JOIN alt_comercial_pedido_informacion pedido on pedido.id_pedido_informacion = inci.id_pedido\r\n" +
                    "INNER JOIN alt_comercial_coordinado_prenda  coor_pre on coor_pre.id_coordinado_prenda = inci.id_op\r\n" +
                    "INNER JOIN alt_comercial_coordinado coor on coor.id_coordinado = coor_pre.id_coordinado\r\n" +
                    "INNER JOIN alt_disenio_prenda prenda on prenda.id_prenda = coor_pre.id_prenda\r\n" +
                    "INNER JOIN alt_disenio_lookup look on prenda.id_familia_prenda = look.id_lookup\r\n" +
                    "INNER JOIN alt_disenio_tela tela on tela.id_tela = coor_pre.id_tela\r\n" +
                    "ORDER BY inci.id_incidencia desc").getResultList();
        return re;
    }
    
}
