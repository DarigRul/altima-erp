package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.MaquilaInventarioHerramientasEntradasSalidas;
import com.altima.springboot.app.models.entity.MaquilaLookup;

public interface IMaquilaInventarioHerramientaEntradasSalidasService {

	List<Object[]> listaMovimientos();

	List<MaquilaLookup> listaConceptos(String descripcion);

	List<MaquilaLookup> listaArticulos();

	void save(MaquilaInventarioHerramientasEntradasSalidas mihes);

	void delete(Long id);

}
