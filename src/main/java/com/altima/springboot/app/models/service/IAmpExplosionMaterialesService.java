package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpExplosionMateriales;
import com.altima.springboot.app.models.entity.AmpTraspaso;
import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;

public interface IAmpExplosionMaterialesService {

	List<AmpExplosionMateriales> findAll();

	void save(AmpExplosionMateriales explosionmateriales);

	void delete(Long id);

	AmpExplosionMateriales findOne(Long id);

	List<Object[]> findTotalMaterials(Long idpedido);

	List<Object[]> findAvailableMaterials(Long IdArticulo);

	List<Object[]> findMaterialsHeader(Long IdArticulo, Long IdPedido);

	AmpTraspasoDetalle findOneTraspasoDetalle(Long id);

	void SaveTraspaso(AmpTraspaso traspaso);

	void SaveTraspasoDetalle(AmpTraspasoDetalle traspasodetalle);

}
