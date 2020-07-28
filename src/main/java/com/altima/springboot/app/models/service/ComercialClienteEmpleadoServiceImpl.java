package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.repository.ComercialClienteEmpleadoRepository;
@Service
public class ComercialClienteEmpleadoServiceImpl implements ComercialClienteEmpleadoService{
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComercialClienteEmpleadoRepository comercialclientefacturacionrepository; 
	
	@Autowired
	private ComercialClienteEmpleadoRepository  repositorioempleado;
	

	@Override
	public List<ComercialClienteEmpleado> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ComercialClienteEmpleado clienteempleado) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ComercialClienteEmpleado findOne(Long id) {
		// TODO Auto-generated method stub
		return comercialclientefacturacionrepository.findById(id).orElse(null);
		/*return null;*/
	}
	
		@SuppressWarnings("unchecked")
		@Transactional
		public List<ComercialClienteEmpleado> findAllEmpleadosEmpresa(Long id ) {
			
			return em.createQuery("from ComercialClienteEmpleado  where idPedidoInformacion = "+id).getResultList();
			//return (List<ComercialCoordinadoMaterial>) repositoryMaterial.findAll();
		}

		@Override
		public ComercialClienteEmpleado findUno(Long id) {
			// TODO Auto-generated method stub
			return repositorioempleado.findById(id).orElse(null);
		}

		@Override
		public int obtenerContador(Long id) {
			   return Integer.parseInt(em.createNativeQuery("SELECT count(*) "
			   		+ "from alt_comercial_cliente_empleado where id_pedido_informacion="+id+" and nombre_empleado like '%sp%'").getSingleResult().toString());
			
		}

		@SuppressWarnings("rawtypes")
		@Override
		public ArrayList findAllEmpleadosRazonSocialYSucursal(Long id) {
			// TODO Auto-generated method stub
			return (ArrayList) em.createNativeQuery("SELECT\r\n" +  
										"	 CE.id_empleado AS IdText,\r\n" + 
										"	 CE.nombre_empleado AS Nombre,\r\n" + 
										"	 CS.nombre_sucursal AS Sucursal,\r\n" + 
										"    CF.razon_social AS Razon_Social,\r\n" + 
										"    CE.creado_por AS CreadoPor\r\n" + 
										"FROM alt_comercial_cliente_empleado AS CE\r\n" + 
										"INNER JOIN alt_comercial_cliente_sucursal AS CS ON CE.id_cliente_sucursal = CS.id_cliente_sucursal\r\n" + 
										"INNER JOIN alt_comercial_cliente_factura AS CF ON CE.id_cliente_factura = CF.id_cliente_factura\r\n" + 
										"WHERE CE.id_pedido_informacion = " + id + ";").getResultList();
		}

}
