package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.ComprasOrdenDetalle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprasOrdenDetalleRepository extends CrudRepository<ComprasOrdenDetalle,Long>{
    
    List<ComprasOrdenDetalle> findByIdOrdenCompras(Long idOrdenCompras);
}
