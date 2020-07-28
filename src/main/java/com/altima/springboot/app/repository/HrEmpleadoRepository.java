package com.altima.springboot.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.altima.springboot.app.models.entity.HrEmpleado;

@Repository
public interface HrEmpleadoRepository extends CrudRepository<HrEmpleado, Long>{
    @Query(value = "CALL alt_pr_empleados;", nativeQuery = true)
    List<HrEmpleado> findAllEmpleados();
}
