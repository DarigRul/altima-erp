package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ControlHora;
import com.altima.springboot.app.models.entity.ControlProduccionMuestra;
import com.altima.springboot.app.models.entity.DisenioLookup;
public interface IControlProduccionMuestraService {
	
	List<ControlProduccionMuestra> findAll(Long id);
	void save(ControlProduccionMuestra DisenioControlProduccionMuestra);
	void delete(Long id);
	ControlProduccionMuestra findOne(Long id);
	List<Object []> Operadores();
	List<Object []> Operaciones(Long id, String tipo);
	List<Object []> ContadorHoras(Long id, String tipo);
	void saveHora(ControlHora DisenioControlHora);
	ControlHora findOneHora(Long id);
	public Integer Pausa(Long id);
	List<Object []> ListarPedidos();
	public Integer Contador (String tipo);
	
	List<Object []> PausarMuestras(Long id, String tipo);
	
	// para las prendas
	List<DisenioLookup> findAllPrenda();
	ControlProduccionMuestra findOne2(String id);
	List<DisenioLookup> generos();
	
	

	
}
