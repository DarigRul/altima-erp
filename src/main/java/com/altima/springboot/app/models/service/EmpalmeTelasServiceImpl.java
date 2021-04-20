package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.dto.ProgramarTelasListDto;
import com.altima.springboot.app.repository.CargaPedidoRepository;

@Service
public class EmpalmeTelasServiceImpl implements IEmpalmeTelasService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	CargaPedidoRepository repository;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProgramarTelasListDto> view(String pedido) {

		List<ProgramarTelasListDto> re = null;

		re = em.createNativeQuery(
				"SELECT COO_PRENDA.id_coordinado_prenda,PEDIDO.id_text AS id_text_pedido,PEDIDO.fecha_entrega,COO_PRENDA.programa,COOR.numero_coordinado,PRENDA.id_text AS id_text_prenda,LOOK_PRENDA.nombre_lookup AS familia_prenda,(SELECT IF (SUM(bordado.precio_bordado)=0,'A','N/A') FROM alt_comercial_prenda_bordado AS bordado WHERE bordado.id_coordinado_prenda=COO_PRENDA.id_coordinado_prenda) AS bordado,LOOK_RUTA.nombre_lookup AS ruta,(SELECT (SUM(conse.cantidad)+SUM(conse.cantidad_especial)) FROM alt_comercial_concetrado_prenda AS conse WHERE conse.id_coordinado_prenda=COO_PRENDA.id_coordinado_prenda) AS confeccion,TELA.id_text AS id_text_tela,TELA.ancho,LOOK_TELA.nombre_lookup AS familia_composicion,TELA.prueba_encogimiento_largo,TELA.estampado,CASE PEDIDO.estatus_explosion_materia_prima WHEN 0 THEN 'Faltante' WHEN 1 THEN CASE (SELECT estatus FROM alt_amp_tela_faltante WHERE id_tela=TELA.id_tela AND id_pedido=PEDIDO.id_pedido_informacion) WHEN 0 THEN 'Faltante' WHEN 1 THEN 'Faltante' WHEN 2 THEN 'Faltante' WHEN 3 THEN 'Faltante' WHEN 4 THEN 'Completo' ELSE 'Completo' END ELSE '00' END AS estatus,COO_PRENDA.folio FROM alt_comercial_pedido_informacion AS PEDIDO INNER JOIN alt_comercial_coordinado COOR ON PEDIDO.id_pedido_informacion=COOR.id_pedido INNER JOIN alt_comercial_coordinado_prenda COO_PRENDA ON COO_PRENDA.id_coordinado=COOR.id_coordinado INNER JOIN alt_disenio_prenda PRENDA ON PRENDA.id_prenda=COO_PRENDA.id_prenda INNER JOIN alt_disenio_lookup LOOK_PRENDA ON PRENDA.id_familia_prenda=LOOK_PRENDA.id_lookup LEFT JOIN alt_produccion_lookup LOOK_RUTA ON COO_PRENDA.id_ruta=LOOK_RUTA.id_lookup INNER JOIN alt_disenio_tela TELA ON COO_PRENDA.id_tela=TELA.id_tela INNER JOIN alt_disenio_lookup LOOK_TELA ON LOOK_TELA.id_lookup=TELA.id_familia_composicion WHERE 1=1 AND PEDIDO.estatus=3 AND PEDIDO.id_text=:pedido GROUP BY COO_PRENDA.id_coordinado_prenda ORDER BY PEDIDO.fecha_entrega,PEDIDO.id_text DESC",
				ProgramarTelasListDto.class).setParameter("pedido", pedido).getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> detallesTelas(Long idCoorPrenda) {
		List<Object[]> re = null;

		re = em.createNativeQuery("" + "SELECT \r\n" + "tela.id_text,\r\n" + "tela.color,\r\n"
				+ "reporte.Principal_Combinacion,\r\n" + "'Fecha',\r\n" + "ROUND(SUM(reporte.Consumo),2),\r\n"
				+ "CASE\r\n" + "		pedido.estatus_explosion_materia_prima \r\n" + "		WHEN 0 THEN\r\n"
				+ "		'Faltante' \r\n" + "		WHEN 1 THEN\r\n" + "	CASE\r\n"
				+ "			( SELECT estatus FROM alt_amp_tela_faltante WHERE id_tela = tela.id_tela AND id_pedido = pedido.id_pedido_informacion ) \r\n"
				+ "			WHEN 0 THEN 'Faltante'\r\n" + "				 WHEN 1 THEN 'Faltante'\r\n"
				+ "				 WHEN 2 THEN 'Faltante'\r\n" + "				 WHEN 3 THEN 'Faltante'\r\n"
				+ "				 WHEN 4 THEN 'Completo'\r\n" + "	    ELSE 'Completo'\r\n" + "	\r\n"
				+ "		END ELSE '00' \r\n" + "	END AS estatus \r\n" + "\r\n" + "FROM\r\n"
				+ "alt_view_apartado_telas_reporte  as reporte,\r\n" + "alt_disenio_tela as tela,\r\n"
				+ "alt_comercial_pedido_informacion as pedido\r\n" + "WHERE\r\n" + "1=1\r\n"
				+ "and reporte.id_tela = tela.id_tela\r\n" + "and reporte.idPedido = pedido.id_pedido_informacion\r\n"
				+ "and reporte.id_coordinado_prenda= " + idCoorPrenda + " \r\n"
				+ "GROUP BY tela.id_tela ORDER BY reporte.Principal_Combinacion").getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> listarProcesosDisponiblesUser(Long idUser) {
		return em.createNativeQuery("" + "SELECT\r\n" + "look.id_lookup,\r\n"
				+ "CONCAT(look.nombre_lookup,' ',look.descripcion_lookup),\r\n" + "look.descripcion_lookup,\r\n"
				+ "look.nombre_lookup,\r\n" + "look.atributo_1 \r\n" + "FROM\r\n" + "alt_produccion_lookup AS look\r\n"
				+ "INNER JOIN alt_produccion_permiso_usuario_proceso AS permiso ON look.id_lookup = permiso.id_proceso\r\n"
				+ "WHERE\r\n" + "1 = 1\r\n" + "AND look.tipo_lookup = 'Proceso'\r\n" + "AND permiso.id_usuario = "
				+ idUser + "\r\n" + "AND look.estatus=1\r\n" + "GROUP BY look.id_lookup\r\n"
				+ "ORDER BY look.nombre_lookup").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> listarProcesosDisponiblesAdmin() {
		return em.createNativeQuery("" + "SELECT\r\n" + "look.id_lookup,\r\n"
				+ "CONCAT(look.nombre_lookup,' ',look.descripcion_lookup),\r\n" + "look.descripcion_lookup,\r\n"
				+ "look.nombre_lookup,\r\n" + "look.atributo_1 \r\n" + "FROM\r\n" + "alt_produccion_lookup AS look\r\n"
				+ "WHERE\r\n" + "1 = 1\r\n" + "AND look.tipo_lookup = 'Proceso'\r\n" + "AND look.estatus=1\r\n"
				+ "ORDER BY look.nombre_lookup").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> listarByProceso(Long idProceso, String programa) {
		List<Object[]> re = null;
		re = em.createNativeQuery("" + "SELECT explosionP.id_explosion_procesos, \r\n"
				+ "			 pedInfo.id_text AS pedido, \r\n" + "			 pedInfo.fecha_entrega,\r\n"
				+ "			 coor.numero_coordinado,\r\n" + "			 disLook.nombre_lookup AS prenda, \r\n"
				+ "			 prenda.id_text as id_text_prenda, \r\n"
				+ "			 (SELECT IF( SUM( bordado.precio_bordado )= 0, 'A', 'N/A' )\r\n"
				+ "				FROM alt_comercial_prenda_bordado AS bordado WHERE bordado.id_coordinado_prenda = coorPrenda.id_coordinado_prenda ) as PERSONALIZACION, \r\n"
				+ "				LOOK_RUTA.nombre_lookup AS RUTA,\r\n"
				+ "				(SELECT ( SUM( conse.cantidad ) + SUM( conse.cantidad_especial )) \r\n"
				+ "					FROM\r\n" + "						alt_comercial_concetrado_prenda AS conse \r\n"
				+ "					WHERE\r\n"
				+ "						conse.id_coordinado_prenda = coorPrenda.id_coordinado_prenda ) AS Confeccion,\r\n"
				+ "			 \r\n" + "			 tela.id_text, \r\n" + "			 tela.ancho,\r\n"
				+ "			 LOOK_TELA.nombre_lookup as famlia,\r\n"
				+ "			 tela.prueba_encogimiento_largo,\r\n" + "			 tela.estampado,\r\n"
				+ "			 CASE pedInfo.estatus_explosion_materia_prima\r\n" + "					WHEN 0 THEN \r\n"
				+ "						'Faltante' \r\n" + "					WHEN 1 THEN \r\n"
				+ "							CASE (SELECT estatus  FROM alt_amp_tela_faltante  WHERE id_tela = tela.id_tela and id_pedido = pedInfo.id_pedido_informacion)\r\n"
				+ "				         WHEN 0 THEN 'Faltante' \r\n"
				+ "								 WHEN 1 THEN 'Faltante' \r\n"
				+ "								 WHEN 2 THEN 'Faltante' \r\n"
				+ "								 WHEN 3 THEN 'Faltante' \r\n"
				+ "								 WHEN 4 THEN 'Completo'\r\n"
				+ "					      ELSE 'Completo' \r\n" + "							END \r\n"
				+ "					ELSE\r\n" + "						'Sin estatus'\r\n"
				+ "				END  as estatus,\r\n" + "				explosionP.tiempo_proceso,\r\n"
				+ "				explosionP.fecha_proceso,\r\n"
				+ "				explosionP.secuencia_proceso, coorPrenda.id_coordinado_prenda\r\n"
				+ "							\r\n" + "FROM alt_produccion_explosion_procesos AS explosionP\r\n" + "\r\n"
				+ "INNER JOIN alt_comercial_pedido_informacion pedInfo ON explosionP.id_pedido = pedInfo.id_pedido_informacion\r\n"
				+ "INNER JOIN alt_comercial_cliente cliente ON pedInfo.id_empresa = cliente.id_cliente\r\n"
				+ "INNER JOIN alt_disenio_prenda prenda ON explosionP.clave_prenda = prenda.id_prenda\r\n"
				+ "INNER JOIN alt_disenio_lookup disLook ON prenda.id_familia_prenda = disLook.id_lookup\r\n"
				+ "INNER JOIN alt_comercial_coordinado_prenda coorPrenda ON explosionP.coordinado = coorPrenda.id_coordinado_prenda\r\n"
				+ "INNER JOIN alt_comercial_coordinado coor ON coorPrenda.id_coordinado = coor.id_coordinado\r\n"
				+ "INNER JOIN alt_disenio_tela tela ON coorPrenda.id_tela = tela.id_tela\r\n"
				+ "INNER JOIN alt_comercial_concetrado_prenda\r\n"
				+ "INNER JOIN alt_produccion_lookup lookup ON explosionP.clave_proceso = lookup.id_lookup\r\n"
				+ "LEFT JOIN alt_produccion_lookup LOOK_RUTA ON coorPrenda.id_ruta = LOOK_RUTA.id_lookup\r\n"
				+ "INNER JOIN alt_disenio_lookup LOOK_TELA ON LOOK_TELA.id_lookup = tela.id_familia_composicion\r\n"
				+ "\r\n" + "WHERE explosionP.programa like :programa and explosionP.clave_proceso = " + idProceso
				+ "\r\n" + "\r\n" + "GROUP BY explosionP.id_explosion_procesos ORDER BY explosionP.secuencia_proceso,prenda.id_text,tela.id_text").setParameter("programa", programa)
				.getResultList();

		return re;
	}

}
