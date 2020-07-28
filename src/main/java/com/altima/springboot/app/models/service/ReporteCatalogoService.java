package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioLookup;

public interface ReporteCatalogoService {
	
	
     List<DisenioLookup> findFiltroReporte(String cata, String palabra, String fecha1, String fecha2);
	
	List<DisenioLookup> findFiltroReporteSinpalabra(String cata, String fecha1, String fecha2);
	
	List<DisenioLookup> findFiltroReporteSinFechas(String cata, String palabra);
	
	List<DisenioLookup> findFiltroReporteSinFePa(String cata);
	
	List<String[]> findAllLookup();
	
	
	
	

}
