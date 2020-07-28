package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.ProduccionPedidoColeccion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import antlr.collections.List;
@Repository
public interface ProduccionPedidoColeccionRepository extends CrudRepository<ProduccionPedidoColeccion,Long>{
    
}