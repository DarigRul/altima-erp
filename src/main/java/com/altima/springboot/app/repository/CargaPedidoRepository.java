package com.altima.springboot.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.altima.springboot.app.dto.PedidoInformacionDTO;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;

@Repository
public interface CargaPedidoRepository extends CrudRepository<ComercialPedidoInformacion, Long> {


}