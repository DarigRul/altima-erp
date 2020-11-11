package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialCoordinadoTela;
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
	
	List<Object[]> pedidosCerrados (Long idUser);
	
	List <Object []> infPedido (Long id);
	
	List <Object []> View (Long id);
	
	//guardar los cambios
	void saveCoorPrenda(ProduccionCoordinadoPrenda prenda);
	ProduccionCoordinadoPrenda findOnePrenda(Long id, Long idSolicitud);
	
	void saveCoorMaterial(ProduccionCoordinadoMaterial material);
	ProduccionCoordinadoMaterial findOneMateril(Long idMaterial, Long idCoorPrenda);
	
	void saveTelaMaterial(ProduccionCoordinadoTela telamaterial);
	ProduccionCoordinadoTela findOneTela(Long idTela, Long idCoorPrenda);

	void saveForroMaterial(ProduccionCoordinadoForro forromaterial);
	ProduccionCoordinadoForro findOnedForro(Long idForro, Long idCoorPrenda);
	
	ProduccionCoordinadoPrenda BuscarCambio (Long id, Long idSolicitud);
	
	void deletePrenda(Long id);
	
	List <Object []> QueryExtracionCambios (Long id);
	void actualizar(Long idActual, Long idCambio , String actualizo, String fecha);
	
	List <Object []> detalles (Long id);

	List <Object []> buscarTelaConv (Long idPrenda, Long idCoorPrenda );
}
