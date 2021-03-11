package com.altima.springboot.app.repository;

import com.altima.springboot.app.models.entity.ServicioClienteArregloPrenda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ServicioClienteArregloPrendaRepository extends CrudRepository<ServicioClienteArregloPrenda, Long> {
}
