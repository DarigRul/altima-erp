package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.ArticulosMultialmacenDto;
import com.altima.springboot.app.dto.EntradasSalidasDTO;
import com.altima.springboot.app.models.entity.AmpAlmacenLogico;
import com.altima.springboot.app.models.entity.AmpMultialmacen;

public interface IAmpMultialmacenService {

	void save(AmpMultialmacen entity);

	AmpMultialmacen findById(Long id);

	List<AmpMultialmacen> findAll();

	void deleteById(Long id);

	List<AmpAlmacenLogico> findAllActiveAMPLogic();

	List<Object[]> findAllAMPLogicItem(Long articulo, String tipo);

	List<AmpMultialmacen> findDuplicates(String tipoPost, Long almacenLogico, Long articulo);

	List<ArticulosMultialmacenDto> findArticulosByMultialmacen(Long idAlmacenLogico);

	List<EntradasSalidasDTO> findAllMovimientos();

	Long findIdMultialmacen(Long idAlmacenLogico,Long idArticulo,String tipo);

	Integer disponibles(Long id, String material);

	Float existenciaArticuloByAlmacen(Long idAlmacenLogico,Long idArticulo,String tipo);

	String existArticuloInAlmacen(Long idAlmacenLogico,Long idArticulo,String tipo);
}
