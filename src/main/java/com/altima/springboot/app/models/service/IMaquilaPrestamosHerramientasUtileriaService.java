package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.MaquilaPrestamosHerramientasUtileria;

public interface IMaquilaPrestamosHerramientasUtileriaService {

	void save(MaquilaPrestamosHerramientasUtileria maquilaprestamosherramientasutileria);


	List<Object[]> ListarPrestamosHerramientas();

}
