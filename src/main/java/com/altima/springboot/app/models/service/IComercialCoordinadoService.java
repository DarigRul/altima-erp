package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.models.entity.ComercialCoordinadoForro;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinadoTela;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;

public interface IComercialCoordinadoService {

	List<ComercialCoordinado> findAll();
	void save(ComercialCoordinado coordinado);
	void delete(Long id);
	ComercialCoordinado findOne(Long id);
	List<Object []> findAllEmpresa(Long id);
	List<DisenioLookup> findAllPrenda();
	List<Object []> findAllModelo(Long id);
	List<Object []> findAllTela(Long id);
	List<Object []> materialesPorPrenda(Long id);
	List<Object []> coloresMateriales(Long idMaterial, Long idTela ,  Long idCoorPrenda);
	public Integer ContadorCoordinadoCliente (Long id);
	
	
	// Tabla Coordinado prenda 
	List<Object []> findAllCoorPrenda(Long id );
	void saveCoorPrenda(ComercialCoordinadoPrenda prenda);
	ComercialCoordinadoPrenda findOneCoorPrenda(Long id);
	
	//Tela Coordinado material 
	List<ComercialCoordinadoMaterial> findAllCoorMaterial( Long id );
	void saveCoorMaterial(ComercialCoordinadoMaterial material);
	ComercialCoordinadoMaterial findOneCoorMaterial(Long idCoorPrenda , Long idColor);
	List<Object []> detallesMatariales(Long id);
	void deleteMaterial(ComercialCoordinadoMaterial delete);
	
	// consulta pa eliminar todo lo relacionado con coordinado 
	public void deleteTotal(Long id);
	
	// consulta pa eliminar la prenda y sus materiales
		public void deletePrenda(Long id);
		
		
		// TABLA DE COORDINADO TELA 

		void saveTelaMaterial(ComercialCoordinadoTela telamaterial);


		//TABLA DE COORDINADO FORRO
		
		
		void saveForroMaterial(ComercialCoordinadoForro forromaterial);
		
		// Muesta de las fotos
		
		List<Object []> ImagenesRuta(Long id , String tipo1 , String  tipo2);
		 
		 String ImagenesRutaTela(Long id);
		 
		 String precioPrenda(Long idCoor , Long idPrenda);
}



