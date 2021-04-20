package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.TiempoCantidadProcesoDto;
import com.altima.springboot.app.dto.TiemposProcesosDto;
import com.altima.springboot.app.models.entity.ProduccionTiempoFamiliaPrenda;

public interface IProduccionTiempoFamiliaPrendaService {
    
    public List<ProduccionTiempoFamiliaPrenda> findAll();

    public List<TiemposProcesosDto> findTiempoFamiliaPrenda();
	
	public ProduccionTiempoFamiliaPrenda findOne(Long id);

	public void save(ProduccionTiempoFamiliaPrenda produccionTiempoFamiliaPrenda);

	public void delete(Long id);
}
