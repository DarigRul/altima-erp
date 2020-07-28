package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;


import com.altima.springboot.app.models.entity.ComercialmagenInventario;

public interface ComercialInventarioImagenRepository extends CrudRepository<ComercialmagenInventario, Long>{
	
	
	public ComercialmagenInventario findByIdPrendaAndIdTela (Long idPrenda, Long idTela);

}
