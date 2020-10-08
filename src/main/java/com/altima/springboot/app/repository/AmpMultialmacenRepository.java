package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.altima.springboot.app.models.entity.AmpMultialmacen;

public interface AmpMultialmacenRepository extends CrudRepository<AmpMultialmacen, Long>{

    AmpMultialmacen findByIdAlmacenLogicoAndTipoAndIdArticulo(Long idAlmacenLogico,String tipo,Long idArticulo);
}
