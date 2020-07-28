package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionPedidoColeccion;

public interface IProduccionPedidoColeccionService {
    
    public List<ProduccionPedidoColeccion> findAll();

	public void save(ProduccionPedidoColeccion coleccion) throws Exception;

	public void delete(Long id);

    public ProduccionPedidoColeccion findOne(Long id); 

    public List<Object[]> findAllDetail(Long idPedido);
}