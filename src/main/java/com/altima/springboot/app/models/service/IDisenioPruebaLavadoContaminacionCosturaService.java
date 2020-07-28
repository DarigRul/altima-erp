package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPruebaLavadoContaminacionCostura;

public interface IDisenioPruebaLavadoContaminacionCosturaService {

	public void save(DisenioPruebaLavadoContaminacionCostura PruebasLCC);
	
	public int ifExist(Long id);
	
	public List<DisenioPruebaLavadoContaminacionCostura> findAllByCalidad(Long id);
	
	public DisenioPruebaLavadoContaminacionCostura findByTipoPrueba(String tipo, Long id);

	public int ifExistContaCostura(Long id, String tipo);
}
