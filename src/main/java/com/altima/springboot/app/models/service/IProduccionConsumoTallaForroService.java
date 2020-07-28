package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionConsumoTallaForro;

public interface IProduccionConsumoTallaForroService {
	
	List<ProduccionConsumoTallaForro> findAll();

	void save(ProduccionConsumoTallaForro ProduccionConsumoTallaForro);

	void delete(Long id);

	ProduccionConsumoTallaForro findOne(Long id);
	
	List<Object []> tallas(Long id);
	
	List<String> largo();
	
	String genpivot(List<String> list);
	
	List<Object []> Consumo_Talla(Long id , String Cabezal);
	
	List<String> Consumo_Talla_id(Long id );
	
	List<Object[]> ConsumoTalla_Tallas(Long idTalla , Long idPrenda );
	
	ProduccionConsumoTallaForro buscar_consumo(Long idTalla , Long idPrenda , Long idLargo);
	
	boolean buscar_forro_prenda(Long idPrenda);

}
