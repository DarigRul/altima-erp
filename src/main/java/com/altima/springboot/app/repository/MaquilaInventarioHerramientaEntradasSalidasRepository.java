package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.MaquilaInventarioHerramientasEntradasSalidas;

@Repository
public interface MaquilaInventarioHerramientaEntradasSalidasRepository extends CrudRepository<MaquilaInventarioHerramientasEntradasSalidas, Long>{

}
