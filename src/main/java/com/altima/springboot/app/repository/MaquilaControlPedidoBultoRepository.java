package com.altima.springboot.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.MaquilaControlPedidoBulto;

@Repository
public interface MaquilaControlPedidoBultoRepository extends CrudRepository<MaquilaControlPedidoBulto, Long> {

	List<MaquilaControlPedidoBulto> findByIdControlPedidoOrderByIdControlPedidoEmbultadoAsc(Long idControlPedido);

}
