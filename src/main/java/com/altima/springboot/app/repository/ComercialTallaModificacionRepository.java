package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.dto.ModificacionDto;
import com.altima.springboot.app.models.entity.ComercialTallaModificacion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercialTallaModificacionRepository extends CrudRepository<ComercialTallaModificacion,Long>{
    
    @Query(name = "modificaciones",nativeQuery = true)
    List<ModificacionDto> findModificacionesByIdConcentradoTalla(Long idConcentradoTalla);
}
