package com.altima.springboot.app.models.service;

import java.util.List;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import com.altima.springboot.app.dto.PrendaListDTO;
import com.altima.springboot.app.models.entity.DisenioPrenda;

public interface IDisenioPrendaService {

	List<DisenioPrenda> findAll();

	void save(DisenioPrenda disenioprenda);

	void delete(Long id);

	boolean validarDescripcionPrenda(String desc);

	DisenioPrenda findOne(Long id);

	public int count(Long id);

	String[] getExistencias(Long familiaPrenda);

	List<Object[]> BuscarPrendaById(Long id);

	List<Object[]> ListaClientesPrenda(Long id);

	List<Object[]> ImagenPrenda(Long id);

	List<Object[]> ListaMarcadoresPrendas(Long id);

	void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry);

	List<PrendaListDTO> findAllMin();

	List<PrendaListDTO> findAllMinR();

	int countRutas();

}
