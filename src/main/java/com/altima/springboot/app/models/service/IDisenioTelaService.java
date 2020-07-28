package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.entity.DisenioTelaPrenda;

public interface IDisenioTelaService {

	List<DisenioTela> findAll();
	
	List<Object[]> findAllBotones(Long id );
	
	//Materiales(boton, cierre ,adornos de acuerdo al tipo )
	List<Object[]> materialesDisponibles(Long id, String tipo );
	//Materiales ya seleccionados (botone,cierre,adorno)
	List<Object []> materialesSeleccionados(Long id, String tipo );
	
	//telas opciones.
	List<Object []> TelasAutorizadas();
	
	List<DisenioLookup> findAllColores();
	
	List<DisenioLookup> findAllFamilaComposicion();
	
	List<DisenioLookup> findAllComposicion();
	
	List<DisenioLookup> findAllPrenda();
	
	public List<Object []> ComposicionTelaMN( Long id);
	
	public List<Object []> ComposicionForroMN( Long id);
	
	public void borrarBotonesTela( Long id);
	
	public void borrarForroTela( Long id);
	
	public void borrarComposicionTela( Long id);
	
	List<Object []> BonotesSeleccionados(Long id );
	
	List<Object []> ForrosSeleccionados(Long id);
	
	
	void save(DisenioTela diseniotela) throws Exception;

	void delete(Long id);

	DisenioTela findOne(Long id);
	
	// Muchos a muchos de tela y prenda 
	void saveTelaPrenda(DisenioTelaPrenda telaPrenda);
	void deleteBorrados(String[] idPrendas, Long idTela);
	public void borrarTelaPrenda( Long id);
	public List<Object []> VistaTelaPrenda( Long id);
	Object findPrendaById(Long id);
	List<DisenioMaterial> findAllMaterial(String tipo);
	List<DisenioLookup> findAllTipo();
	
	public String EstatusCalidadTela( Long id);
	public String buscar_tela(String nombre);
}
