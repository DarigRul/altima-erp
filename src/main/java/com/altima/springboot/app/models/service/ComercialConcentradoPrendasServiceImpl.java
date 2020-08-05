package com.altima.springboot.app.models.service;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ComercialConcetradoPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.models.entity.DisenioPrendaPatronaje;
import com.altima.springboot.app.repository.ComercialConcentradoPrendasRepository;
import com.altima.springboot.app.repository.DisenioPrendaMarcadorRepository;

@Service
public class ComercialConcentradoPrendasServiceImpl implements IComercialConcentradoPrendasService
{
	@Autowired
	private EntityManager em;
	@Autowired
	private ComercialConcentradoPrendasRepository repository;
	
	//Metodo para obtener todos los coordinados de un pedido
	@SuppressWarnings("unchecked")
	@Override
	public List<ComercialCoordinado> findCoordinadosfromPedido(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("from ComercialCoordinado where idPedido = " + id).getResultList();
	}	
	
	//Metodo para obtener todos los empleados en base a un pedido
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findAllEmpleadosByPedido(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT\n" + 
				"	cce2.id_empleado as ID,\n" + 
				"	cce2.nombre_empleado as Nombre\n" + 
				"	FROM alt_comercial_cliente_empleado cce2\n" + 
				"    \n" + 
				"	WHERE cce2.id_empleado NOT IN\n" + 
				"		( \n" + 
				"			SELECT\n" + 
				"				cce.id_empleado\n" + 
				"				FROM alt_comercial_cliente_empleado cce\n" + 
				"				RIGHT JOIN alt_comercial_concetrado_prenda ccp ON cce.id_empleado = ccp.id_empleado\n" + 
				"				WHERE cce.id_pedido_informacion = " + id + "\n" + 
				"		)\n" + 
				"	\n" + 
				"    AND cce2.id_pedido_informacion = " + id + ";").getResultList();
	}
	
	//Metodo para obtener las prendas, telas y materiales de un coordinado
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findMaterialPrendaTelafromCoordinado(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("call alt_pr_prendas_telas_materiales_de_coordinado(" + id + ")").getResultList();
	}
	
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Object> findCantidadesPrendasfromCoordinado(Long id, Long idPedido) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("call alt_pr_cantidades_de_empleados_concentrado_prendas(" + id + ", " + idPedido + ")").getResultList();
	}
	
	@Override
	public void save(ComercialConcetradoPrenda ccp) {
		// TODO Auto-generated method stub
		ComercialConcetradoPrenda ccpOld = null;
		try {
			ccpOld = (ComercialConcetradoPrenda) em.createQuery("FROM ComercialConcetradoPrenda WHERE idEmpleado = " + ccp.getIdEmpleado() + " AND idCoordinadoPrenda = " + ccp.getIdCoordinadoPrenda()).getSingleResult();
		}
		catch(NoResultException nre) {
			//
		}
		
		if(ccpOld != null) {
			//Ya existe un registro asi, solo se modificara.
			ccpOld.setCantidad(ccp.getCantidad());
			ccpOld.setCantidadEspecial(ccp.getCantidadEspecial());
			ccpOld.setActualizadoPor(ccp.getActualizadoPor());
			ccpOld.setUltimaFechaModificacion(ccp.getUltimaFechaModificacion());
			repository.save(ccpOld);
			
		}
		else {
			repository.save(ccp);
		}	
	}

	@Override
	public void delete(String[] idModelos, Long id, Long idCoordinado) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Object[]> res = em.createNativeQuery("SELECT\r\n" + 
				"	\r\n" + 
				"    cp.*\r\n" + 
				"    \r\n" + 
				"    FROM alt_comercial_concetrado_prenda cp\r\n" + 
				"    \r\n" + 
				"    INNER JOIN alt_comercial_coordinado_prenda AS coor_pre ON cp.id_coordinado_prenda = coor_pre.id_coordinado_prenda\r\n" + 
				"    INNER JOIN alt_comercial_coordinado AS coor ON coor_pre.id_coordinado = coor.id_coordinado\r\n" + 
				"    \r\n" + 
				"    WHERE cp.id_empleado = " + id + " AND coor.id_coordinado = " + idCoordinado + ";").getResultList();
		
		@SuppressWarnings("rawtypes")
		Iterator it = res.iterator();
		if(idModelos.length != 0) {
			while(it.hasNext()){
			     Object[] line = (Object[]) it.next();
			     int Coincidencias = 0;
			     
					for(int i = 0; i < idModelos.length; i++) {
						if(line[2].toString().equalsIgnoreCase(idModelos[i].toString())) {
							Coincidencias++;
						}
					}
					
					if(Coincidencias == 0) {
						repository.deleteById(Long.valueOf(line[0].toString()));
					}
			}
		}else {
			System.out.println(res.size());
			while(it.hasNext()){
				Object[] line = (Object[]) it.next();
				repository.deleteById(Long.valueOf(line[0].toString()));
			}
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findPrendasFromEmpleado(Long id, Long idCoordinado) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT cce.nombre_empleado as NombreEmpleado, "
										 + "cc.numero_coordinado as NumCoordinado, "
										 + "CONCAT(dp.id_text, '-', dt.id_text) as Modelo, "
										 + "ccp.cantidad as Cantidad, "
										 + "ccp.cantidad_especial as CantidadEspecial, "
										 + "ccp.id_coordinado_prenda as ID," 
										 + "cc.id_coordinado as IDCoordinado,"
										 + "ccp2.id_coordinado_prenda as IDModelo,"
										 + "cce.id_empleado as IDEmpleado\r\n" +
										"\r\n" + 
										"FROM alt_comercial_concetrado_prenda ccp\r\n" + 
										"INNER JOIN alt_comercial_cliente_empleado cce ON ccp.id_empleado = cce.id_empleado\r\n" + 
										"INNER JOIN alt_comercial_coordinado_prenda ccp2 ON ccp.id_coordinado_prenda = ccp2.id_coordinado_prenda\r\n" + 
										"INNER JOIN alt_comercial_coordinado cc ON ccp2.id_coordinado = cc.id_coordinado\r\n" + 
										"INNER JOIN alt_disenio_prenda dp ON ccp2.id_prenda = dp.id_prenda\r\n" + 
										"INNER JOIN alt_disenio_tela dt ON ccp2.id_tela = dt.id_tela\r\n" + 
										"WHERE ccp.id_empleado = " + id + " AND cc.id_coordinado = " + idCoordinado + ";").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findEmpleadosParaExpediente(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT \r\n" + 
				"\r\n" + 
				"	 CLI_EMP.id_empleado AS ID,\r\n" + 
				"    CLI_EMP.nombre_empleado AS NOMBRE,\r\n" + 
				"    CON_PRE.id_coordinado_prenda AS IDModelo,\r\n" + 
				"    CON_PRE.cantidad AS CANT,\r\n" + 
				"    CON_PRE.cantidad_especial AS CANT_S\r\n" + 
				"    \r\n" + 
				"    FROM alt_comercial_cliente_empleado AS CLI_EMP\r\n" + 
				"    \r\n" + 
				"    INNER JOIN alt_comercial_concetrado_prenda AS CON_PRE ON CLI_EMP.id_empleado = CON_PRE.id_empleado\r\n" + 
				"    INNER JOIN alt_comercial_coordinado_prenda AS COR_PRE ON CON_PRE.id_coordinado_prenda = COR_PRE.id_coordinado_prenda\r\n" + 
				"    INNER JOIN alt_comercial_coordinado AS COR ON COR_PRE.id_coordinado = COR.id_coordinado\r\n" + 
				"    \r\n" + 
				"    WHERE COR.id_pedido = " + id + 
				"    \r\n" + 
				"    UNION \r\n" + 
				"    \r\n" + 
				"SELECT \r\n" + 
				"\r\n" + 
				"	CLI_EMP_2.id_empleado AS ID,\r\n" + 
				"    CLI_EMP_2.nombre_empleado AS NOMBRE,\r\n" + 
				"    'NULO' AS IDModelo,\r\n" + 
				"    '0' AS CANT,\r\n" + 
				"    '0' AS CANT_S\r\n" + 
				"    \r\n" + 
				"    FROM alt_comercial_cliente_empleado AS CLI_EMP_2\r\n" + 
				"    \r\n" + 
				"    WHERE CLI_EMP_2.id_empleado NOT IN (\r\n" + 
				"        \r\n" + 
				"        SELECT \r\n" + 
				"            \r\n" + 
				"        	CLI_EMP.id_empleado AS ID\r\n" + 
				"\r\n" + 
				"            FROM alt_comercial_cliente_empleado AS CLI_EMP\r\n" + 
				"\r\n" + 
				"            INNER JOIN alt_comercial_concetrado_prenda AS CON_PRE ON CLI_EMP.id_empleado = CON_PRE.id_empleado\r\n" + 
				"            INNER JOIN alt_comercial_coordinado_prenda AS COR_PRE ON CON_PRE.id_coordinado_prenda = COR_PRE.id_coordinado_prenda\r\n" + 
				"            INNER JOIN alt_comercial_coordinado AS COR ON COR_PRE.id_coordinado = COR.id_coordinado\r\n" + 
				"\r\n" + 
				"            WHERE COR.id_pedido = " + id + 
				"    ) AND CLI_EMP_2.id_pedido_informacion = " + id).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComercialCoordinado> findCoordinadofromPedido(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("from ComercialCoordinado where idCoordinado = " + id).getResultList();
	}
}

