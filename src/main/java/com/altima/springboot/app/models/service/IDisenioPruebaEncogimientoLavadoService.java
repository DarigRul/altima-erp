package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPruebaEncogimientoLavado;

public interface IDisenioPruebaEncogimientoLavadoService {

	public List<DisenioPruebaEncogimientoLavado> findAll(DisenioPruebaEncogimientoLavado pruebaEncogimientoLavado);
	
	public void save (DisenioPruebaEncogimientoLavado pruebaEncogimientoLavado);	
	
	public int ifExist(Long id);
	
	public List<DisenioPruebaEncogimientoLavado> findAllByCalidad(Long id);
	
	public DisenioPruebaEncogimientoLavado findByTipoPrueba(String tipo, Long id);
	
	public int ifExistLavado(Long id, String tipo);

	Long findEntretelaByIdTela(Long id);
}
