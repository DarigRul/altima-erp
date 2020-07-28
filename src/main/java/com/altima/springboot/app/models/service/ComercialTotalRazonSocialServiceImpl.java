package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ComercialTotalRazonSocial;
import com.altima.springboot.app.repository.ComercialTotalRazonRepository;

@Service
public class ComercialTotalRazonSocialServiceImpl implements IComercialTotalRazonSocialService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ComercialTotalRazonRepository repository;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialTotalRazonSocial> findAll(Long id ) {
		// TODO Auto-generated method stub
		return em.createQuery("from ComercialTotalRazonSocial  where id_pedido="+id).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	
	
	
	
	public List<Object []> consultaX(Long id) {
		
	
		
		
		// se consulta los totales por razon social:
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"		factura.id_cliente_factura,\r\n" + 
				"		cliente.id_pedido_informacion,\r\n" + 
				"		SUM( concentrado.cantidad ),\r\n" + 
				"	    \r\n" + 
				"	    ROUND(SUM( coor_prenda.precio_final * concentrado.cantidad ), 2),\r\n" + 
				"		\r\n" + 
				"		pedido.iva\r\n" + 
				"	FROM\r\n" + 
				"		alt_comercial_concetrado_prenda AS concentrado,\r\n" + 
				"		alt_comercial_cliente_factura AS factura,\r\n" + 
				"		alt_comercial_coordinado_prenda AS coor_prenda,\r\n" + 
				"		alt_comercial_cliente_empleado AS cliente,\r\n" + 
				"		alt_comercial_pedido_informacion AS pedido \r\n" + 
				"	WHERE\r\n" + 
				"		1 = 1 \r\n" + 
				"		AND concentrado.id_empleado = cliente.id_empleado \r\n" + 
				"		AND cliente.id_cliente_factura = factura.id_cliente_factura \r\n" + 
				"		AND coor_prenda.id_coordinado_prenda = concentrado.id_coordinado_prenda \r\n" + 
				"		AND pedido.id_pedido_informacion = cliente.id_pedido_informacion \r\n" + 
				"		AND cliente.id_pedido_informacion = "+id+" \r\n" + 
				"	GROUP BY\r\n" + 
				"		factura.id_cliente_factura").getResultList();
		
		
		List<Object[]> aux = em.createNativeQuery("SELECT\r\n" + 
				"	0,\r\n" + 
				"	cliente.id_pedido_informacion,\r\n" + 
				"   IFNULL(SUM( concentrado.cantidad_especial ), 0),\r\n" + 
				"	ROUND(SUM( coor_prenda.precio_final * concentrado.cantidad_especial ), 2),\r\n" +
				"IF( pedido.adicionales_iva = 1, pedido.iva, 0 )\r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_concetrado_prenda AS concentrado,\r\n" + 
				"	alt_comercial_cliente_factura AS factura,\r\n" + 
				"	alt_comercial_coordinado_prenda AS coor_prenda,\r\n" + 
				"	alt_comercial_cliente_empleado AS cliente,\r\n" + 
				"	alt_comercial_pedido_informacion AS pedido \r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND concentrado.id_empleado = cliente.id_empleado \r\n" + 
				"	AND cliente.id_cliente_factura = factura.id_cliente_factura \r\n" + 
				"	AND coor_prenda.id_coordinado_prenda = concentrado.id_coordinado_prenda \r\n" + 
				"	AND pedido.id_pedido_informacion = cliente.id_pedido_informacion \r\n" + 
				"	AND cliente.id_pedido_informacion = "+id).getResultList();
		for (Object[] a : aux) {
			
			
			float piezas = Float.parseFloat(a[2].toString());
			System.out.println("hoooooooooooolaa: "+piezas);
			if ( piezas >0 ) {
				
				re.addAll(aux);
			}
		}
		
		if(re == null || re.size() == 0){
			System.out.println("DELETE FROM alt_comercial_total_razon_social\r\n" + 
					"WHERE alt_comercial_total_razon_social.id_pedido=" +id);
			  // por si la lista esta vacia se va a eliminar todos los registros existentes en la tabla 
			Query query = em.createNativeQuery("DELETE FROM alt_comercial_total_razon_social\r\n" + 
					"WHERE alt_comercial_total_razon_social.id_pedido=" +id);
			query.executeUpdate();
			}
	
		
		return re;
	}

	@Override
	public void save(ComercialTotalRazonSocial total) {
		repository.save(total);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public ComercialTotalRazonSocial findOne(Long pedido , Long idFactura) {
		try {
			return  (ComercialTotalRazonSocial)em.createQuery("from ComercialTotalRazonSocial   where  id_pedido="+pedido +" and id_cliente_factura="+idFactura).getSingleResult();
			}
			catch(Exception e) {
				
				return null;
			}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object []> totalRazon(Long id) {
		
		List<Object[]> re = em.createNativeQuery("SELECT\r\n" + 
				"	razon.id_total_razon_social,\r\n" + 
				"	factura.id_text,\r\n" + 
				"	factura.razon_social,\r\n" + 
				"	razon.numero_prenda,\r\n" + 
				"	razon.subtotal_total,\r\n" + 
				"	razon.descuento,\r\n" + 
				"	razon.subtotal_total2,\r\n" + 
				"	razon.iva,\r\n" + 
				"	razon.total_pedido,\r\n" + 
				"	razon.anticipo,\r\n" + 
				"	razon.entrega,\r\n" + 
				"	razon.saldo,\r\n" + 
				"	IFNULL(pedido.pago_anticipo, 0),\r\n" + 
				"    razon.descuento_porcentaje,\r\n" + 
				"    razon.anticipo_porcentaje,\r\n" + 
				"    razon.entrega_porcentaje,\r\n" + 
				"    razon.ajustes_porcentaje\r\n" + 
				"	\r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_total_razon_social AS razon,\r\n" + 
				"	alt_comercial_cliente_factura AS factura, \r\n" + 
				"	alt_comercial_pedido_informacion AS pedido\r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND razon.id_cliente_factura = factura.id_cliente_factura \r\n" + 
				"	AND pedido.id_pedido_informacion = razon.id_pedido\r\n" + 
				"	AND razon.id_pedido = "+id+" \r\n" + 
				"	UNION\r\n" + 
				"SELECT\r\n" + 
				"	razon.id_total_razon_social,\r\n" + 
				"	'Adicciones',\r\n" + 
				"	'Adicciones',\r\n" + 
				"	razon.numero_prenda,\r\n" + 
				"	razon.subtotal_total,\r\n" + 
				"	razon.descuento,\r\n" + 
				"	razon.subtotal_total2,\r\n" + 
				"	razon.iva,\r\n" + 
				"	razon.total_pedido,\r\n" + 
				"	razon.anticipo,\r\n" + 
				"	razon.entrega,\r\n" + 
				"	razon.saldo,\r\n" + 
				"	IFNULL(pedido.pago_anticipo, 0),\r\n" + 
				"    razon.descuento_porcentaje,\r\n" + 
				"    razon.anticipo_porcentaje,\r\n" + 
				"    razon.entrega_porcentaje,\r\n" + 
				"    razon.ajustes_porcentaje\r\n" + 
				"FROM\r\n" + 
				"	alt_comercial_total_razon_social AS razon,\r\n" + 
				"	alt_comercial_pedido_informacion AS pedido\r\n" + 
				"WHERE\r\n" + 
				"	1 = 1 \r\n" + 
				"	AND razon.id_pedido = "+id+" \r\n" + 
				"	AND razon.id_cliente_factura =0\r\n" + 
				"	AND pedido.id_pedido_informacion = razon.id_pedido").getResultList();
		
		
		
		return re;
	}
	
	
	@Override
	public ComercialTotalRazonSocial findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	

}
