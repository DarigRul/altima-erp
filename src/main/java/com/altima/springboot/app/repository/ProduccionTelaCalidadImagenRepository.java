package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionTelaCalidadImagen;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionTelaCalidadImagenRepository extends CrudRepository<ProduccionTelaCalidadImagen,Long>{
    
    List<ProduccionTelaCalidadImagen> findByIdTela(Long idPrenda);
}
