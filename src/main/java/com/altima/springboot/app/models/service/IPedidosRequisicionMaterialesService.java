package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.MaterialFaltanteListDto;
import com.altima.springboot.app.models.entity.AmpMaterialFaltante;

public interface IPedidosRequisicionMaterialesService {

	List<AmpMaterialFaltante> findAll();

	void save(AmpMaterialFaltante tela);

	void delete(Long id);

	AmpMaterialFaltante findOne(Long id);

	List<MaterialFaltanteListDto> findAllMaterialesFaltantes();

	List<MaterialFaltanteListDto> findAllMaterialesFaltantes(String ids);

}
