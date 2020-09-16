package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ComercialSpfEmpleado;


public interface ComercialClienteEmpleadoService {
	
	List<ComercialClienteEmpleado> findAll();
	
	List<ComercialClienteEmpleado> findAllEmpleadosEmpresa(Long id); 
	
	//Metodo para Obtener Empleados, Razon Social y Sucursalj
	@SuppressWarnings("rawtypes")
	ArrayList findAllEmpleadosRazonSocialYSucursal(Long id);
	
	int obtenerContador(Long id);



	void save(ComercialClienteEmpleado clienteempleado);

	void delete(Long id);

	ComercialClienteEmpleado findOne(Long id);
	
	ComercialClienteEmpleado findUno(Long id);
	
	public int countdeempleados(Long id);
	
	public String max(Long id);
	
	public ComercialClienteEmpleado findByidText(String idText, Long idPedidoInformacion);
	
	public String findMaxByidText( Long idPedidoInformacion);
	
	
	List<ComercialSpfEmpleado> findAllClientesSPF(Long id);
	
	
	

}
