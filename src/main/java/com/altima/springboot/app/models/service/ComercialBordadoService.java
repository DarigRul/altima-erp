package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialBordado;
import com.altima.springboot.app.models.entity.ComercialBordadoParteBordado;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialLookup;

public interface ComercialBordadoService {
	


	
	void save(ComercialBordado Bordado);
	
	void delete(Long id);
	
	ComercialBordado findOne(Long id);
	
	
   void saveParte(ComercialBordadoParteBordado ParteBordado);
	
	void deleteParteBordado(Long id);
	
	ComercialBordadoParteBordado findOneParteBordado(Long id);
	
	public List<ComercialCliente> findListaClientes(String creado);
	
	
	public List<ComercialLookup> findListaLookupComercial();
	
	public List<Object[]> findListaBordados();
	
	public List<Object[]> findListaParteBordados(Long id);
	
	
	
	
	

}
