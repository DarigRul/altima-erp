package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionConsumoTalla;

public interface IProduccionConsumoTallaService {
	
	List<ProduccionConsumoTalla> findAll();

	void save(ProduccionConsumoTalla ProduccionConsumoTalla);

	void delete(Long id);

	ProduccionConsumoTalla findOne(Long id);
	
	List<Object []> tallas(Long id);
	
	List<String> largo();
	
	String genpivot(List<String> list);
	
	List<Object []> Consumo_Talla(Long id , String Cabezal);
	
	List<String> Consumo_Talla_id(Long id );
	
	List<Object[]> ConsumoTalla_Tallas(Long idTalla , Long idPrenda );
	
	ProduccionConsumoTalla buscar_consumo(Long idTalla , Long idPrenda , Long idLargo);
}
