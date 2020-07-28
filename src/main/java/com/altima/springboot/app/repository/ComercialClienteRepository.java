package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.altima.springboot.app.models.entity.ComercialCliente;

public interface ComercialClienteRepository extends CrudRepository<ComercialCliente, Long> {

}
