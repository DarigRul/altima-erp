package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionMiniTrazo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionMiniTrazoRepository extends CrudRepository<ProduccionMiniTrazo,Long>{
    
    List<ProduccionMiniTrazo> findByIdPrenda(Long idPrenda);
}
