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
                "explosionProceso.id_explosion_procesos, \r\n" +
                "(\r\n" +
                "COUNT( explosionPrenda.id_explosion_prenda ) - (\r\n" +
                "SELECT\r\n" +
                    "IFNULL(SUM( RD.recibida ) ,0)\r\n" +
                "FROM\r\n" +
                    "alt_servicio_cliente_recepcion_devolucion RD\r\n" +
                "WHERE\r\n" +
                    "1 = 1\r\n" +
                    "AND RD.id_op = explosionProceso.id_explosion_procesos\r\n" +
                    "AND RD.id_maquilero = 1\r\n" +
                ")\r\n" +
            ") AS faltantes\r\n" +
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
                "AND pedido.id_pedido_informacion ="+idPedido).getResultList();
		return re;
    }

    @Override
    public void save(ServicioClienteRecepcionDevolucion obj) {
        repository.save(obj);
        
    }

    @Override
    public ServicioClienteRecepcionDevolucion findOneFechaCoorPrenda(Integer id) {
        // TODO Auto-generated method stub
        return null;
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
                "RD.id_op,\r\n" +
                "MAX( historico.movimiento ),\r\n" +
                "maquilador.nombre,\r\n" +
                "MAX( historico.fecha ),\r\n" +
                "SUM( RD.recibida )\r\n" +
            "FROM\r\n" +
                "alt_servicio_cliente_recepcion_devolucion AS RD\r\n" +
                "INNER JOIN alt_servicio_cliente_recepcion_devolucion_historico historico ON historico.id_recepcion_devolucion = RD.id_recepcion_devolucion\r\n" +
                "INNER JOIN alt_produccion_maquilador maquilador ON maquilador.id_maquilador = RD.id_maquilero\r\n" +
            "GROUP BY\r\n" +
                "RD.id_op ORDER BY RD.id_op").getResultList();
		return re;
    }
    
}
