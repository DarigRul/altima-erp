package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altima.springboot.app.models.entity.ServicioClienteConversionTallas;
import com.altima.springboot.app.repository.ServicioClienteConversionTallasRepository;


@Service
public class ServicioClienteConversionTallasServiceImpl implements IServicioClienteConversionTallasService {

    @PersistenceContext
	private EntityManager em;
	
	@Autowired 
	private ServicioClienteConversionTallasRepository repository;

    @Override
    public void save (ServicioClienteConversionTallas objecto){
        repository.save(objecto);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> listarPedidosEstatus3() {
        List<Object[]> re = null;
		re = em.createNativeQuery(""+
                "SELECT\r\n" +
                    "pedido.id_pedido_informacion,\r\n" +
                    "pedido.id_text\r\n" +
                "FROM\r\n" +
                    "alt_comercial_pedido_informacion AS pedido\r\n" +
                "WHERE\r\n" +
                    "1 = 1\r\n" +
                    "AND pedido.estatus = 3\r\n" +
                    "AND pedido.id_pedido_informacion NOT IN (\r\n" +
                    "SELECT\r\n" +
                        "id_pedido\r\n" +
                    "FROM\r\n" +
                    "alt_servicio_cliente_conversion_tallas)").getResultList();

		return re;
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view() {
        List<Object[]> re = null;
		re = em.createNativeQuery(            "SELECT\r\n" +
        "conversion.id_conversion_tallas,\r\n" +
        "pedido.id_text,\r\n" +
        "cliente.nombre,\r\n" +
        "sum( reporte.numPersonas ) AS totalPersonas,\r\n" +
        "cast(SUM( reporte.Cantidad_prendas )as int) totalPrendas,\r\n" +
        "DATE_FORMAT(conversion.fecha_recepcion,'%d/%m/%Y'),\r\n" +
        "DATE_FORMAT(conversion.fecha_entrega,'%d/%m/%Y'),\r\n" +
        "conversion.cantidad_consersion,\r\n" +
        "conversion.observacion,\r\n" +
        "if(conversion.insidencia = 0 , 'NO','SI'),\r\n" +
        "if(conversion.ultima_fecha_modificacion is null , 'Activa', 'Modificada'),\r\n" +
        "conversion.creado_por,\r\n" +
        "DATE_FORMAT(conversion.fecha_creacion,'%d/%m/%Y %H:%i'),\r\n" +
        "IFNULL(conversion.actualizado_por,''),\r\n" +
        "IFNULL(DATE_FORMAT(conversion.actualizado_por,'%d/%m/%Y %H:%i'),''),\r\n" +
        "conversion.porcentaje\r\n" +
    "FROM\r\n" +
        "alt_servicio_cliente_conversion_tallas AS conversion\r\n" +
        "INNER JOIN alt_comercial_pedido_informacion pedido ON pedido.id_pedido_informacion = conversion.id_pedido\r\n" +
        "INNER JOIN alt_comercial_cliente cliente ON cliente.id_cliente = pedido.id_empresa\r\n" +
        "INNER JOIN alt_view_apartado_telas_reporte reporte ON reporte.idPedido = pedido.id_pedido_informacion\r\n" +
    " GROUP BY  pedido.id_text ORDER BY\r\n" +
        "pedido.id_text DESC").getResultList();

		return re;
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> devolverDatos(Long id) {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
        "SELECT\r\n" +
            "cliente.nombre,\r\n" +
            "(SELECT COUNT(*) FROM alt_comercial_cliente_empleado acce WHERE acce.id_pedido_informacion="+id+") as totalPersonas,\r\n" +
            "( SELECT SUM(accp.cantidad+accp.cantidad_especial) FROM alt_comercial_cliente_empleado acce INNER JOIN alt_comercial_concetrado_prenda accp ON accp.id_empleado=acce.id_empleado WHERE acce.id_pedido_informacion="+id+" ) totalPrendas,\r\n" +
            "reporte.fecha_entrega\r\n" +
        "FROM\r\n" +
            "alt_comercial_pedido_informacion as pedido\r\n" +
            "INNER JOIN alt_comercial_cliente cliente on cliente.id_cliente= pedido.id_empresa\r\n" +
            "INNER JOIN alt_view_apartado_telas_reporte reporte on reporte.idPedido = pedido.id_pedido_informacion\r\n" +
        "WHERE\r\n" +
            "1=1\r\n" +
            "AND pedido.id_pedido_informacion="+id).getResultList();

		return re;
    }
    
    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> devolverEditar(Long id) {
        List<Object[]> re = null;
		re = em.createNativeQuery(""+
            "SELECT\r\n" +
                "conversion.id_conversion_tallas,\r\n" +
                "pedido.id_pedido_informacion,\r\n" +
	            "pedido.id_text,\r\n" +
                "cliente.nombre,\r\n" +
                "sum(reporte.numPersonas) as totalPersonas,\r\n" +
                "SUM(reporte.Cantidad_prendas) totalPrendas,\r\n" +
                "conversion.fecha_recepcion,\r\n" +
                "conversion.fecha_entrega,\r\n" +
                "conversion.cantidad_consersion,\r\n" +
                "conversion.porcentaje,\r\n" +
                "conversion.observacion\r\n" +
            "FROM\r\n" +
                "alt_comercial_pedido_informacion as pedido\r\n" +
                "INNER JOIN alt_comercial_cliente cliente on cliente.id_cliente= pedido.id_empresa\r\n" +
                "INNER JOIN alt_view_apartado_telas_reporte reporte on reporte.idPedido = pedido.id_pedido_informacion\r\n" +
                "INNER JOIN alt_servicio_cliente_conversion_tallas conversion on conversion.id_pedido = pedido.id_pedido_informacion\r\n" +
            "WHERE\r\n" +
                "1=1\r\n" +
                "AND conversion.id_conversion_tallas="+id).getResultList();

		return re;
    }

    @Override
	public ServicioClienteConversionTallas findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}
