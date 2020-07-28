package com.altima.springboot.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.Rol;


@Repository
public interface RolRepository extends CrudRepository<Rol, Long> {

	List<Rol> findBySeccionRol(String seccionRol);
}
