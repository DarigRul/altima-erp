package com.altima.springboot.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.altima.springboot.app.dto.TallasPivoteDto;
import com.altima.springboot.app.models.entity.ComercialConcentradoTalla;

@Repository
public interface ConcentradoTallaRepository extends CrudRepository<ComercialConcentradoTalla, Long> {

    @Query(name = "tallas_pivote",nativeQuery = true)
    List<TallasPivoteDto> findPivoteByidPedido(Long idPedido);

    @Query(name = "idEmpleado_pivote",nativeQuery = true)
    List<TallasPivoteDto> findPivoteByIdEmpleado(Long idEmpleado);
}