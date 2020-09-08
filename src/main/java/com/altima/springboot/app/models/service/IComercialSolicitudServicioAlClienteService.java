package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlCliente;

public interface IComercialSolicitudServicioAlClienteService {
	
	List<Object[]> findAll();
	
	void save(ComercialSolicitudServicioAlCliente comercialSolicitudServicioAlCliente);
	
	void delete(Long id);
	
	ComercialSolicitudServicioAlCliente findOne(Long id);
	
	List<Object[]> pedidosDeCliente(Long id);
	
	List<Object[]> devolverSelectMateriales(Long idSolicitud);
}
