package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpRolloTela;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpRolloTelaRepository extends CrudRepository<AmpRolloTela,Long>{
    
    List<AmpRolloTela> findByIdAlmacenFisicoAndEstatusAndIdTela(Long idAlmacenFisico,String estatus,Long idTela);
}
