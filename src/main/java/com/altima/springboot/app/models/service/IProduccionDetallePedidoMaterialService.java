package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;

import com.altima.springboot.app.models.entity.ProduccionDetallePedidoMaterial;


public interface IProduccionDetallePedidoMaterialService {
	
	void save(ProduccionDetallePedidoMaterial clienteempleado);

	void delete(Long id);

	ProduccionDetallePedidoMaterial findOne(Long id);
	
     List<ProduccionDetallePedidoMaterial> findAllMaterial(Long id );

}
