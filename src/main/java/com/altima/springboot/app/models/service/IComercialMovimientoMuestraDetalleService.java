package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialMovimientoMuestraDetalle;
import com.altima.springboot.app.models.entity.ComercialTokenTraspaso;

public interface IComercialMovimientoMuestraDetalleService {
	
	void save(ComercialMovimientoMuestraDetalle muestraDetalle);
	
	public List<ComercialMovimientoMuestraDetalle> findAll ();
	
	public List<ComercialMovimientoMuestraDetalle> findAllbyMovimiento(Long id);

	List<ComercialMovimientoMuestraDetalle> findAllbyEstatus(Long id);

	ComercialMovimientoMuestraDetalle findOne(Long id);

	String ifExistCheckBox(Long id);

	List<ComercialMovimientoMuestraDetalle> findAllbyMovimientoEstatus(Long id);

	ComercialMovimientoMuestraDetalle FindMuestraByPedido(Long id, Long idDetalle);

	void removeEntities(List<ComercialMovimientoMuestraDetalle> Entities);

	ComercialTokenTraspaso findCode(Long id);
	
	void removeToken(ComercialTokenTraspaso TokenTraspaso);
	
	void saveToken(ComercialTokenTraspaso TokenTraspaso);

}
