package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.ProduccionConsumoTallaCombinacionTela;

public interface IProduccionConsumoTallaCombinacionTelaService {
	List<ProduccionConsumoTallaCombinacionTela> findAll();

	void save(ProduccionConsumoTallaCombinacionTela obj);

	void delete(Long id);

	ProduccionConsumoTallaCombinacionTela findOne(Long id);
	List<Object []> tallas(Long id);
	
	List<Object []>largos ();
	
	String genpivot(List<String> list);
	
	List<Object []> Consumo_Talla(Long id , String Cabezal, Long idTela);
	
	List<String> Consumo_Talla_id(Long id , Long idTela);
	
	ProduccionConsumoTallaCombinacionTela buscar_consumo(Long idTalla , Long idPrenda , Long idLargo, Long idTela);
}
