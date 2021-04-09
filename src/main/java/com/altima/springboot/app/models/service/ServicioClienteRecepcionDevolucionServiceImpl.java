package com.altima.springboot.app.models.service;

import java.rmi.server.ObjID;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.altima.springboot.app.models.entity.ServicioClienteRecepcionDevolucion;
import com.altima.springboot.app.models.entity.ServicioClienteRecepcionDevolucionHistorico;
import com.altima.springboot.app.repository.ServicioClienteRecepcionDevolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioClienteRecepcionDevolucionServiceImpl implements IServicioClienteRecepcionDevolucionService{

    @PersistenceContext
	private EntityManager em;

	@Autowired
	private ServicioClienteRecepcionDevolucionRepository repository;
    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> maquileros() {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
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
    public List<Object[]> pedido(Long idMaquilero) {
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
    public List<Object[]> op(Long idPedido, Long idMaquilero) {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
            "SELECT DISTINCT\r\n" +
                "explosionProceso.id_explosion_procesos,\r\n" +
                "COUNT( explosionPrenda.id_explosion_prenda ) AS cantidad\r\n" +
            "FROM\r\n" +
                "alt_produccion_explosion_procesos AS explosionProceso\r\n" +
                "INNER JOIN alt_produccion_explosion_prendas explosionPrenda ON explosionPrenda.id_explosion_proceso = explosionProceso.id_explosion_procesos\r\n" +
                "INNER JOIN alt_comercial_pedido_informacion pedido ON pedido.id_pedido_informacion = explosionProceso.id_pedido\r\n" +
                "INNER JOIN alt_produccion_lookup look ON look.id_lookup = explosionProceso.clave_proceso\r\n" +
            "WHERE\r\n" +
                "1 = 1\r\n" +
                "AND look.tipo_lookup = 'Proceso'\r\n" +
                "AND look.descripcion_lookup = 'Externo'\r\n" +
                "AND explosionPrenda.realizo = "+idMaquilero+"\r\n" +
                "AND pedido.id_pedido_informacion = "+idPedido+"\r\n" +
                "AND explosionProceso.id_explosion_procesos NOT IN (\r\n" +
                "SELECT\r\n" +
                    "id_op\r\n" +
                "FROM\r\n" +
                "alt_servicio_cliente_recepcion_devolucion )").getResultList();
		return re;
    }

    @Override
    public void save(ServicioClienteRecepcionDevolucion obj) {
        repository.save(obj);
        
    }

    @Override
    public ServicioClienteRecepcionDevolucion findOne(Long id) {
        return repository.findById(id).orElse(null);
    }

	@Transactional
	@Override
    public void savehistorico(ServicioClienteRecepcionDevolucionHistorico obj) {
        if (obj.getIdHistorico() != null && obj.getIdHistorico()> 0) {
			em.merge(obj);

		} else {
			em.persist(obj);
		}
        
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> viewPricipal() {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
                "SELECT\r\n" +
                    "RD.num_movimiento,\r\n" +
                    "RD.id_maquilero,\r\n" +
                    "(\r\n" +
                    "SELECT\r\n" +
                        "historico.movimiento\r\n" +
                    "FROM\r\n" +
                        "alt_servicio_cliente_recepcion_devolucion_historico AS historico\r\n" +
                        "INNER JOIN alt_servicio_cliente_recepcion_devolucion RD2 ON RD2.id_recepcion_devolucion = historico.id_recepcion_devolucion\r\n" +
                    "WHERE\r\n" +
                        "1 = 1\r\n" +
                        "AND RD.num_movimiento = RD2.num_movimiento\r\n" +
                    "ORDER BY\r\n" +
                        "historico.id_historico DESC\r\n" +
                        "LIMIT 1\r\n" +
                    "),\r\n" +
                    "maquilador.nombre,\r\n" +
                    "(\r\n" +
                    "SELECT\r\n" +
                        "historico.fecha\r\n" +
                    "FROM\r\n" +
                        "alt_servicio_cliente_recepcion_devolucion_historico AS historico\r\n" +
                        "INNER JOIN alt_servicio_cliente_recepcion_devolucion RD2 ON RD2.id_recepcion_devolucion = historico.id_recepcion_devolucion\r\n" +
                    "WHERE\r\n" +
                        "1 = 1\r\n" +
                        "AND RD.num_movimiento = RD2.num_movimiento\r\n" +
                    "ORDER BY\r\n" +
                        "historico.id_historico DESC\r\n" +
                        "LIMIT 1\r\n" +
                    "),\r\n" +
                    "sum(\r\n" +
                    "RD.recibida + IFNULL( RD.pendiente, 0 ) + IFNULL( RD.dev, 0 )) AS total\r\n" +
                "FROM\r\n" +
                    "alt_servicio_cliente_recepcion_devolucion AS RD\r\n" +
                    "INNER JOIN alt_produccion_maquilador maquilador ON maquilador.id_maquilador = RD.id_maquilero").getResultList();
		return re;
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view(Long idMaquilero, Long num_movimiento) {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
            "SELECT\r\n" +
                "RD.id_recepcion_devolucion,\r\n" +
                "pedido.id_text,\r\n" +
                "EP.id_explosion_procesos,\r\n" +
                "EP.coordinado,\r\n" +
                "look.nombre_lookup,\r\n" +
                "prenda.descripcion_prenda,\r\n" +
                "reporte.CodigoTela,\r\n" +
                "RD.recibida,\r\n" +
                "RD.pendiente,\r\n" +
                "RD.dev,\r\n" +
                "(RD.recibida + IFNULL( RD.pendiente, 0 ) + IFNULL( RD.dev, 0 ) ) as total\r\n" +
            "FROM\r\n" +
                "alt_servicio_cliente_recepcion_devolucion AS RD\r\n" +
                "INNER JOIN alt_produccion_explosion_procesos EP ON RD.id_op = EP.id_explosion_procesos\r\n" +
                "INNER JOIN alt_comercial_pedido_informacion pedido ON pedido.id_pedido_informacion = EP.id_pedido\r\n" +
                "INNER JOIN alt_disenio_prenda prenda ON EP.clave_prenda = prenda.id_prenda\r\n" +
                "INNER JOIN alt_disenio_lookup look ON look.id_lookup = prenda.id_familia_prenda\r\n" +
                "INNER JOIN alt_view_apartado_telas_reporte reporte ON reporte.id_coordinado_prenda = EP.coordinado\r\n" +
            "WHERE\r\n" +
                "1 = 1\r\n" + 
                "AND RD.id_maquilero = "+idMaquilero+"\r\n" +
                "AND RD.num_movimiento = "+num_movimiento+"\r\n" +
            "GROUP BY\r\n" +
                "RD.id_recepcion_devolucion").getResultList();
		return re;
    }

    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> viewHistorico(Long idMaquilero, Long num_movimiento) {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
                "SELECT\r\n" +
                    "pedido.id_text,\r\n" +
                    "EP.id_explosion_procesos,\r\n" +
                    "EP.coordinado,\r\n" +
                    "look.nombre_lookup,\r\n" +
                    "prenda.descripcion_prenda,\r\n" +
                    "reporte.CodigoTela,\r\n" +
                    "his.cantidad,\r\n" +
                    "his.movimiento,\r\n" +
                    "DATE_FORMAT(his.fecha ,'%Y-%m-%d %T')\r\n" +
                "FROM\r\n" +
                    "alt_servicio_cliente_recepcion_devolucion_historico AS his\r\n" +
                    "INNER JOIN alt_servicio_cliente_recepcion_devolucion RD ON his.id_recepcion_devolucion = RD.id_recepcion_devolucion\r\n" +
                    "INNER JOIN alt_produccion_explosion_procesos EP ON RD.id_op = EP.id_explosion_procesos\r\n" +
                    "INNER JOIN alt_comercial_pedido_informacion pedido ON pedido.id_pedido_informacion = EP.id_pedido\r\n" +
                    "INNER JOIN alt_disenio_prenda prenda ON EP.clave_prenda = prenda.id_prenda\r\n" +
                    "INNER JOIN alt_disenio_lookup look ON look.id_lookup = prenda.id_familia_prenda\r\n" +
                    "INNER JOIN alt_view_apartado_telas_reporte reporte ON reporte.id_coordinado_prenda = EP.coordinado\r\n" +
                "WHERE\r\n" +
                    "1 = 1\r\n" + 
                    "AND RD.id_maquilero = "+idMaquilero+"\r\n" +
                    "AND RD.num_movimiento = "+num_movimiento+"\r\n" + 
                "GROUP BY\r\n" +
                    "his.id_historico").getResultList();
		return re;
    }

    @Transactional
	@Override
    public String num_movimiento() {
        String re = em.createNativeQuery(""+
            "SELECT\r\n" +
                "IFNULL( NULLIF( max( RD.num_movimiento + 1 ), 1 ), 1 )\r\n" +
            "FROM\r\n" +
                "alt_servicio_cliente_recepcion_devolucion AS RD").getSingleResult().toString();
	
		return re;
    }
    
}
