package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialHistorialCambioPrenda;

public interface IComercialHistorialCambioPrendaService {

List<ComercialHistorialCambioPrenda> findAll (Long id);
	
	void save (ComercialHistorialCambioPrenda ComercialHistorialCambioPrenda);
	
	ComercialHistorialCambioPrenda findOne (Long id);
	
	List<Object []> empleadosSPF(Long idSPF);
	
	List<Object []> coordinadoEmpleado(Long idEmpleado);
	
	List<Object []> modelo(Long idEmpleado, Long idCoor);
	
	List<Object []> cambio(Long idPedido, Long idExcluir);
	
	List<Object []> vista(Long idSPF);
	
}
