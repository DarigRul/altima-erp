package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.HrDepartamento;

@Repository
public interface HrDepartamentoRepository extends CrudRepository<HrDepartamento, Long> {

}
