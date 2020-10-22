package com.altima.springboot.app.models.service;

import java.util.List;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoForro;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ProduccionCoordinadoTela;
import com.altima.springboot.app.models.entity.ProduccionSolicitudCambioTelaPedido;

public interface IProduccionSolicitudCambioTelaPedidoService {

	List<ProduccionSolicitudCambioTelaPedido> findAll();

	void save(ProduccionSolicitudCambioTelaPedido obj);

	void delete(Long id);

	ProduccionSolicitudCambioTelaPedido findOne(Long id);
	
	List<Object[]> pedidosCerrados ();
	
	List <Object []> infPedido (Long id);
	
	List <Object []> View ();
	
	//guardar los cambios
	void saveCoorPrenda(ProduccionCoordinadoPrenda prenda);
	
	void saveCoorMaterial(ProduccionCoordinadoMaterial material);
	
	void saveTelaMaterial(ProduccionCoordinadoTela telamaterial);

	void saveForroMaterial(ProduccionCoordinadoForro forromaterial);
	
	ProduccionCoordinadoPrenda BuscarCambio (Long id);
	
	void deletePrenda(Long id);
}
