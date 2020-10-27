package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.AmpExplosionMateriales;
import com.altima.springboot.app.repository.AmpExplosionMaterialesRepository;

@Service
public class AmpExplosionMaterialesServiceImpl implements IAmpExplosionMaterialesService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AmpExplosionMaterialesRepository repository;

	@Override
	public List<AmpExplosionMateriales> findAll() {
		return (List<AmpExplosionMateriales>) repository.findAll();
	}

	@Override
	public void save(AmpExplosionMateriales explosionmateriales) {
		repository.save(explosionmateriales);

	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);

	}

	@Override
	public AmpExplosionMateriales findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Object[]> findTotalMaterials(Long idpedido) {
		return em.createNativeQuery("select\r\n" + 
				"   resultado2.id_material,\r\n" + 
				"   resultado2.cantidad,\r\n" + 
				"   resultado2.id_text,\r\n" + 
				"   resultado2.tamanio,\r\n" + 
				"   resultado2.nombre_material,\r\n" + 
				"   resultado2.proceso,\r\n" + 
				"   resultado2.uom,\r\n" + 
				"   resultado2.clasificacion,\r\n" + 
				"   resultado2.tipo_material,\r\n" + 
				"   resultado2.color,\r\n" + 
				"   resultado2.id_pedido_informacion,\r\n" + 
				"   resultado2.id_prenda,\r\n" + 
				"   resultado2.Modelo,\r\n" + 
				"   resultado2.Cantidad2,\r\n" + 
				"   resultado2.CantidadEspecial,\r\n" + 
				"   resultado2.TOTAL,\r\n" + 
				"   resultado2.total22,\r\n" + 
				"   resultado2.total22 t,\r\n" + 
				"   if(resultado2.disponible_inicio - alt_amp_traspaso_detalle2.apartados < 0 , 0 , resultado2.disponible_inicio - alt_amp_traspaso_detalle2.apartados )disponible_actual,\r\n" + 
				"   resultado2.disponible_inicio,\r\n" + 
				"   alt_amp_traspaso_detalle2.apartados,\r\n" + 
				"   (\r\n" + 
				"      resultado2.Surtir_actual - alt_amp_traspaso_detalle2.apartados\r\n" + 
				"   )\r\n" + 
				"   Surtir_actual,\r\n" + 
				"   (\r\n" + 
				"      resultado2.Faltante - alt_amp_traspaso_detalle2.apartados\r\n" + 
				"   )\r\n" + 
				"   Faltante,\r\n" + 
				"   resultado2.Surtir_inicio \r\n" + 
				"from\r\n" + 
				"   (\r\n" + 
				"      SELECT\r\n" + 
				"         resultado.*,\r\n" + 
				"         (\r\n" + 
				"            SUM( resultado.total22 ) \r\n" + 
				"         )\r\n" + 
				"         Surtir_actual,\r\n" + 
				"         (\r\n" + 
				"            SUM( resultado.total22 ) \r\n" + 
				"         )\r\n" + 
				"         Faltante,\r\n" + 
				"         SUM( resultado.total22 ) Surtir_inicio \r\n" + 
				"      FROM\r\n" + 
				"         (\r\n" + 
				"            SELECT\r\n" + 
				"               alt_disenio_material_prenda.id_material,\r\n" + 
				"               alt_disenio_material_prenda.cantidad,\r\n" + 
				"               alt_disenio_material.id_text,\r\n" + 
				"               alt_disenio_material.tamanio,\r\n" + 
				"               alt_disenio_material.nombre_material,\r\n" + 
				"               procesolook.nombre_lookup proceso,\r\n" + 
				"               uomlook.nombre_lookup uom,\r\n" + 
				"               clasificacionlook.nombre_lookup clasificacion,\r\n" + 
				"               tipolook.nombre_lookup tipo_material,\r\n" + 
				"               colorlook.nombre_lookup color,\r\n" + 
				"               total_prendas_pedido.*,\r\n" + 
				"               (\r\n" + 
				"                  total_prendas_pedido.TOTAL * alt_disenio_material_prenda.cantidad \r\n" + 
				"               )\r\n" + 
				"               AS total22,\r\n" + 
				"               if(SUM(IF(alt_amp_almacen_logico.tipo <> 2, alt_amp_multialmacen.existencia, 0)) < 0, 0, SUM(IF(alt_amp_almacen_logico.tipo <> 2, alt_amp_multialmacen.existencia, 0))) disponible_inicio \r\n" + 
				"            FROM\r\n" + 
				"               (\r\n" + 
				"                  SELECT\r\n" + 
				"                     emp2.id_pedido_informacion,\r\n" + 
				"                     cor_pre2.id_prenda,\r\n" + 
				"                     CONCAT( dp2.id_text, \" - \", dt2.id_text ) AS Modelo,\r\n" + 
				"                     con_pre2.cantidad AS Cantidad2,\r\n" + 
				"                     con_pre2.cantidad_especial AS CantidadEspecial,\r\n" + 
				"                     sum( Cantidad + cantidad_especial ) AS TOTAL \r\n" + 
				"                  FROM\r\n" + 
				"                     alt_comercial_coordinado AS cor2 \r\n" + 
				"                     INNER JOIN\r\n" + 
				"                        alt_comercial_coordinado_prenda AS cor_pre2 \r\n" + 
				"                        ON cor2.id_coordinado = cor_pre2.id_coordinado \r\n" + 
				"                     INNER JOIN\r\n" + 
				"                        alt_comercial_concetrado_prenda AS con_pre2 \r\n" + 
				"                        ON cor_pre2.id_coordinado_prenda = con_pre2.id_coordinado_prenda \r\n" + 
				"                     INNER JOIN\r\n" + 
				"                        alt_disenio_prenda AS dp2 \r\n" + 
				"                        ON cor_pre2.id_prenda = dp2.id_prenda \r\n" + 
				"                     INNER JOIN\r\n" + 
				"                        alt_disenio_tela AS dt2 \r\n" + 
				"                        ON cor_pre2.id_tela = dt2.id_tela \r\n" + 
				"                     INNER JOIN\r\n" + 
				"                        alt_comercial_cliente_empleado AS emp2 \r\n" + 
				"                        ON con_pre2.id_empleado = emp2.id_empleado \r\n" + 
				"                  WHERE\r\n" + 
				"                     emp2.id_pedido_informacion = "+idpedido+" \r\n" + 
				"                  GROUP BY\r\n" + 
				"                     cor_pre2.id_prenda \r\n" + 
				"               )\r\n" + 
				"               AS total_prendas_pedido,\r\n" + 
				"               alt_disenio_material_prenda,\r\n" + 
				"               alt_disenio_material \r\n" + 
				"               LEFT JOIN\r\n" + 
				"                  alt_disenio_lookup procesolook \r\n" + 
				"                  ON alt_disenio_material.id_proceso = procesolook.id_lookup \r\n" + 
				"               LEFT JOIN\r\n" + 
				"                  alt_disenio_lookup uomlook \r\n" + 
				"                  ON alt_disenio_material.id_unidad_medida = uomlook.id_lookup \r\n" + 
				"               LEFT JOIN\r\n" + 
				"                  alt_disenio_lookup clasificacionlook \r\n" + 
				"                  ON alt_disenio_material.id_clasificacion = clasificacionlook.id_lookup \r\n" + 
				"               LEFT JOIN\r\n" + 
				"                  alt_disenio_lookup tipolook \r\n" + 
				"                  ON alt_disenio_material.id_tipo_material = tipolook.id_lookup \r\n" + 
				"               LEFT JOIN\r\n" + 
				"                  alt_disenio_lookup colorlook \r\n" + 
				"                  ON alt_disenio_material.id_color = colorlook.id_lookup \r\n" + 
				"               LEFT JOIN\r\n" + 
				"                  alt_amp_multialmacen \r\n" + 
				"                  ON alt_amp_multialmacen.id_articulo = alt_disenio_material.id_material \r\n" + 
				"                  AND alt_amp_multialmacen.tipo = 'material' \r\n" + 
				"               LEFT JOIN\r\n" + 
				"                  alt_amp_almacen_logico \r\n" + 
				"                  ON alt_amp_multialmacen.id_almacen_logico = alt_amp_almacen_logico.id_almacen_logico \r\n" + 
				"                  AND alt_amp_almacen_logico.tipo <> 2 \r\n" + 
				"            WHERE\r\n" + 
				"               total_prendas_pedido.id_prenda = alt_disenio_material_prenda.id_prenda \r\n" + 
				"               AND alt_disenio_material_prenda.id_material = alt_disenio_material.id_material \r\n" + 
				"            GROUP BY\r\n" + 
				"               alt_disenio_material_prenda.id_material,\r\n" + 
				"               alt_disenio_material_prenda.id_prenda \r\n" + 
				"         )\r\n" + 
				"         AS resultado \r\n" + 
				"      GROUP BY\r\n" + 
				"         resultado.id_material\r\n" + 
				"   )\r\n" + 
				"   as resultado2 \r\n" + 
				"   LEFT JOIN\r\n" + 
				"      (\r\n" + 
				"         SELECT\r\n" + 
				"            *,\r\n" + 
				"            sum(alt_amp_traspaso_detalle.cantidad) as apartados \r\n" + 
				"         FROM\r\n" + 
				"            `alt_amp_traspaso_detalle` \r\n" + 
				"         where\r\n" + 
				"            alt_amp_traspaso_detalle.tipo = 'material' \r\n" + 
				"         GROUP BY\r\n" + 
				"            alt_amp_traspaso_detalle.id_articulo\r\n" + 
				"      )\r\n" + 
				"      alt_amp_traspaso_detalle2 \r\n" + 
				"      on resultado2.id_material = alt_amp_traspaso_detalle2.id_articulo \r\n" + 
				"   LEFT JOIN\r\n" + 
				"      alt_amp_traspaso \r\n" + 
				"      on alt_amp_traspaso.id_traspaso = alt_amp_traspaso_detalle2.id_traspaso \r\n" + 
				"   left join\r\n" + 
				"      alt_amp_almacen_logico al2 \r\n" + 
				"      on al2.id_almacen_logico = alt_amp_traspaso.id_almacen_logico_destino \r\n" + 
				"      and alt_amp_traspaso.id_almacen_logico_destino = \r\n" + 
				"      (\r\n" + 
				"         select\r\n" + 
				"            id_almacen_logico \r\n" + 
				"         from\r\n" + 
				"            alt_amp_almacen_logico \r\n" + 
				"         where\r\n" + 
				"            tipo = 2 LIMIT 1 \r\n" + 
				"      )\r\n" + 
				";").getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Object[]> findAvailableMaterials(Long IdArticulo) {
		return em.createNativeQuery("SELECT\r\n" + 
				"   am.id_multialmacen,\r\n" + 
				"   al2.id_almacen_logico id_destino,\r\n" + 
				"   al.id_almacen_logico id_origen,\r\n" + 
				"   al.nombre_almacen_logico,\r\n" + 
				"   am.id_articulo,\r\n" + 
				"   amt.id_traspaso,\r\n" + 
				"   alt_amp_traspaso_detalle.id_traspaso_detalle,\r\n" + 
				"   am.existencia,\r\n" + 
				"   IFNULL(alt_amp_traspaso_detalle.cantidad, 0) apartado,\r\n" + 
				"   (\r\n" + 
				"      existencia - IFNULL(alt_amp_traspaso_detalle.cantidad, 0)\r\n" + 
				"   )\r\n" + 
				"   disponible \r\n" + 
				"FROM\r\n" + 
				"   alt_amp_multialmacen am,\r\n" + 
				"   alt_amp_almacen_logico al \r\n" + 
				"   LEFT JOIN\r\n" + 
				"      alt_amp_almacen_logico al2 \r\n" + 
				"      on al2.id_almacen_logico = \r\n" + 
				"      (\r\n" + 
				"         select\r\n" + 
				"            id_almacen_logico \r\n" + 
				"         from\r\n" + 
				"            alt_amp_almacen_logico \r\n" + 
				"         where\r\n" + 
				"            tipo = 2 LIMIT 1\r\n" + 
				"      )\r\n" + 
				"   LEFT JOIN\r\n" + 
				"      alt_amp_traspaso amt \r\n" + 
				"      on al2.id_almacen_logico = amt.id_almacen_logico_destino \r\n" + 
				"      and al.id_almacen_logico = amt.id_almacen_logico_origen \r\n" + 
				"   LEFT JOIN\r\n" + 
				"      alt_amp_traspaso_detalle \r\n" + 
				"      on amt.id_traspaso = alt_amp_traspaso_detalle.id_traspaso \r\n" + 
				"      and alt_amp_traspaso_detalle.id_articulo = "+IdArticulo+" \r\n" + 
				"      and alt_amp_traspaso_detalle.tipo = 'material' \r\n" + 
				"WHERE\r\n" + 
				"   am.id_almacen_logico = al.id_almacen_logico \r\n" + 
				"   AND am.id_articulo = "+IdArticulo+" \r\n" + 
				"   AND am.tipo = 'material'").getResultList();
				
	}
	

}
