package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.ProduccionProcesoRuta;


public interface IProduccionProcesoRutaService {

    List<ProduccionProcesoRuta> findAll();

	void save(ProduccionProcesoRuta RP);

	void delete(Long id);

	ProduccionProcesoRuta findOne(Long id);

	List<Object []> MostrarProcesosRuta (Long id);

	boolean buscarProcesoRuta (Long idProceso,Long idRuta);

	boolean validarNombrerutaEditar (Long idLookup, String nombre);
	
	


    
}
