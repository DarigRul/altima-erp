package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionPrendaHistorico;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionPrendaHistoricoRepository extends CrudRepository<ProduccionPrendaHistorico,Long>{
    
    List<ProduccionPrendaHistorico> findByIdPrenda(Long idPrenda);
}
