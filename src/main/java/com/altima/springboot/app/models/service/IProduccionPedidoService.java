package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ProduccionPedido;

public interface IProduccionPedidoService {
	
	
//lEO
	List<ProduccionPedido> findAll();

	void save(ProduccionPedido pedido);

	void delete(Long id);

	ProduccionPedido findOne(Long id);
	
	
	List<ProduccionPedido> findListapedidos();
	
	
	public int contadorPedidos();
	
	
	
	
	//Erick copy
	
	
	List<DisenioLookup> findAllPrenda();
	List<Object []> findAllModelo(Long id);
	List<Object []> findAllModeloColeccion(Long id, String genero);
	List<Object []> findAllTela(Long id);
	List<Object []> materialesPorPrenda(Long id);
	List<Object []> coloresMateriales(Long idMaterial, Long idTela ,  Long idCoorPrenda);
	
	List<Object []> tablaModal(Long id);
	
	
	
     public int total(Long id);
     
     
     public int contadorColeccion(Long idPedido, Long idFamPrenda, String genero);
     public int contadorDetallePedido(Long idPedido, Long idFamPrenda, String genero);
     public int idGenero(Long idPedido, Long idFamPrenda, String genero);
	
}



//GGGG
