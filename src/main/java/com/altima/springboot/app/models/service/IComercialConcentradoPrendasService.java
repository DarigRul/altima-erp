package com.altima.springboot.app.models.service;

import java.util.List;

import org.json.JSONObject;

import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ComercialConcetradoPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinado;

public interface IComercialConcentradoPrendasService 
{
	//Metodo que obtiene los coordinadso
	List<ComercialCoordinado> findCoordinadosfromPedido(Long id);
	
	//Metodo que obtiene un solo coordinado
	List<ComercialCoordinado> findCoordinadofromPedido(Long id);
	
	//Metodo para obtener todos los empleados de un pedido
	List<Object> findAllEmpleadosByPedido(Long id);
	
	//Metodo para encontrar los modelos de un solo coordinado
	List<Object> findMaterialPrendaTelafromCoordinado(Long id);
	
	//Lista de las cantidades por empleado de los coordinados
	List<Object> findCantidadesPrendasfromCoordinado(Long id, Long idPedido);
	
	//Lista de las prendas de un empleado
	List<Object> findPrendasFromEmpleado(Long id, Long idCoordinado);
	
	//Esto devuelve todos los empleados de un pedido, con prendas o sin ellas
	List<Object> findEmpleadosParaExpediente(Long id);
	
	void save(ComercialConcetradoPrenda ccp);
	
	void delete(String[] idModelos, Long id, Long idCoordinado) throws NoSuchFieldException, SecurityException;
	
	ComercialConcetradoPrenda findOne (Long id);
}
