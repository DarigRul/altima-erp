package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoForro;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoTela;

public interface IProduccionDetalleService {

	List<ProduccionDetallePedido> findAll();

	void save(ProduccionDetallePedido orden);

	void delete(Long id);

	void deleteByIdDetalle(Long id, Long idp);

	ProduccionDetallePedido findOne(Long id);

	// comienzan las consultas

	List<Object[]> ListarMuestras(Long id, String tipo);

	List<Object[]> Terminados(Long id, Long tipo);

	List<Object[]> PrendaOrdenes(Long id);

	// dar de baja las ordenes
	void bajasOrdenes(String fecha, String edito, String idPrenda, String idPedido, String talla, String largo,
			String costo);

	List<Object[]> muestrariosCatalogo();

	public Integer Contador();

	List<ProduccionDetallePedido> tabla(Long Id);

	List<Object[]> detallesMatariales(Long id);

	public String nombreAgente(Long id);

	List<Object[]> findAllTelaPrimera(Long id);

	List<Object[]> materialesPorPrendaExtra(Long id);

	List<Object[]> materialesPorPrendaExtra2(Long id);

	List<Object[]> findAllTela(Long id);

	List<Object[]> findAllForro(Long id);

	List<Object[]> findListMatEx(Long id);

	// buscar si la descripcion de la prenda no esta dublicada

	public boolean buscar_prenda(String detalle);

	/////// COLECCION

	List<ProduccionDetallePedido> tablaColeccion(Long Id);

	// List<Object []> selectPrenda(Long Id);

	List<Object[]> selectPrenda(Long id);

	List<Object[]> selectDinamicPrenda();

	List<Object[]> selectGenero();

}
