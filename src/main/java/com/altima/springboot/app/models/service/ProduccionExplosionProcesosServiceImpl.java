package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ProduccionConsumoReal;
import com.altima.springboot.app.models.entity.ProduccionExplosionPrendas;
import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.repository.ProduccionConsumoRealRepository;
import com.altima.springboot.app.repository.ProduccionExplosionPrendasRepository;
import com.altima.springboot.app.repository.ProduccionExplosionProcesosRepository;

@Service
@SuppressWarnings("unchecked")
public class ProduccionExplosionProcesosServiceImpl implements IProduccionExplosionProcesosService {

	@Autowired
	private ProduccionExplosionProcesosRepository repository;

	@Autowired
	private ProduccionExplosionPrendasRepository repositoryPrendas;

	@Autowired
	private ProduccionConsumoRealRepository repositoryConsumo;

	@Autowired
	private EntityManager em;

	@Transactional
	@Override
	public void save(ProduccionExplosionProcesos produccionExplosionProcesos) {
		// TODO Auto-generated method stub
		repository.save(produccionExplosionProcesos);

	}

	@Transactional
	@Override
	public ProduccionExplosionProcesos findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public List<ProduccionExplosionProcesos> findProgramas() {
		// TODO Auto-generated method stub
		return em.createQuery("FROM ProduccionExplosionProcesos GROUP BY programa").getResultList();
	}

	@Transactional
	@Override
	public List<Object[]> findAllByPrograma(String programa) {
		return em.createNativeQuery("SELECT coor.id_pedido, \r\n" + "		coorPrenda.id_coordinado_prenda, \r\n"
				+ "		coorPrenda.id_coordinado, \r\n" + "		coorPrenda.id_prenda, \r\n"
				+ "		coorPrenda.programa, \r\n" + "		coorPrenda.id_ruta, \r\n"
				+ "		lookupProceso.id_lookup AS id_proceso, \r\n"
				+ "		lookupRuta.nombre_lookup AS Nombre_ruta, \r\n"
				+ "		lookupProceso.nombre_lookup AS Nombre_proceso \r\n" + "   \r\n"
				+ "FROM alt_comercial_coordinado_prenda AS coorPrenda \r\n" + "   \r\n"
				+ "INNER JOIN alt_comercial_coordinado coor ON coor.id_coordinado = coorPrenda.id_coordinado \r\n"
				+ "INNER JOIN alt_produccion_lookup lookupRuta ON coorPrenda.id_ruta = lookupRuta.id_lookup \r\n"
				+ "INNER JOIN alt_produccion_proceso_ruta procesoRuta ON procesoRuta.id_lookup_ruta = lookupRuta.id_lookup \r\n"
				+ "LEFT JOIN alt_produccion_lookup lookupProceso ON lookupProceso.id_lookup = procesoRuta.id_lookup_proceso \r\n"
				+ "   \r\n" + "WHERE coorPrenda.programa = '" + programa + "' \r\n" + "   \r\n"
				+ "ORDER BY id_prenda, id_ruta, id_proceso").getResultList();
	}

	@Transactional
	@Override
	public List<ProduccionExplosionProcesos> listExplosionByProceso(Long idProceso) {

		return em.createNativeQuery("call alt_pr_listar_explosiones_por_proceso(" + idProceso + ")").getResultList();
	}

	// Métodos para el modal de explosion de prendas en la pantalla de control de
	// avances

	@Transactional
	@Override
	public void saveExplosionPrendas(ProduccionExplosionPrendas explosionPrendas) {
		if (explosionPrendas.getIdExplosionPrenda() != null && explosionPrendas.getIdExplosionPrenda() > 0) {
			em.merge(explosionPrendas);

		} else {
			em.persist(explosionPrendas);
		}
	}

	@Transactional
	@Override
	public List<Object[]> prendasExplosionarByProceso(Long idExplosionProceso) {

		return em.createNativeQuery("SELECT explosionP.id_explosion_procesos,\r\n"
				+ "		pedInfo.id_pedido_informacion,\r\n" + "		coorPrenda.id_coordinado_prenda,\r\n"
				+ "		concenPrenda.id_concentrado_prenda,\r\n" + "		concenTallas.id,\r\n"
				+ "		CONCAT(prodLookup.nombre_lookup, producLookup.nombre_lookup) AS Talla, FORMAT((IFNULL(concenPrenda.cantidad,0)  + IFNULL(concenPrenda.cantidad_especial,0)),0) as cantidad\r\n" + "		\r\n"
				+ "FROM alt_produccion_explosion_procesos AS explosionP \r\n" + "		\r\n"
				+ "INNER JOIN alt_comercial_pedido_informacion pedInfo ON explosionP.id_pedido = pedInfo.id_pedido_informacion\r\n"
				+ "INNER JOIN alt_disenio_prenda prenda ON explosionP.clave_prenda = prenda.id_prenda\r\n"
				+ "INNER JOIN alt_comercial_coordinado_prenda coorPrenda ON explosionP.coordinado = coorPrenda.id_coordinado_prenda\r\n"
				+ "INNER JOIN alt_comercial_coordinado coor ON coorPrenda.id_coordinado = coor.id_coordinado\r\n"
				+ "INNER JOIN alt_comercial_concetrado_prenda concenPrenda ON coorPrenda.id_coordinado_prenda = concenPrenda.id_coordinado_prenda\r\n"
				+ "INNER JOIN alt_comercial_concentrado_tallas concenTallas ON pedInfo.id_pedido_informacion = concenTallas.id_pedido AND concenPrenda.id_empleado = concenTallas.id_empleado_pedido\r\n"
				+ "INNER JOIN alt_comercial_cliente_empleado empleado ON concenTallas.id_empleado_pedido = empleado.id_empleado\r\n"
				+ "INNER JOIN alt_produccion_lookup producLookup ON concenTallas.id_largo = producLookup.id_lookup\r\n"
				+ "INNER JOIN alt_produccion_lookup prodLookup ON concenTallas.id_talla = prodLookup.id_lookup\r\n"
				+ "INNER JOIN alt_disenio_tela tela ON coorPrenda.id_tela = tela.id_tela\r\n" + "		\r\n"
				+ "WHERE explosionP.id_explosion_procesos = " + idExplosionProceso + " \r\n" + "		\r\n"
				+ "GROUP BY concenPrenda.id_concentrado_prenda").getResultList();
	}

	@Transactional
	@Override
	public List<Object[]> listarPrendasByExplosionProceso(Long idExplosionProceso, String tipo) {
		List<Object[]> re = null;
		if (tipo.equals("Interno")) {
			re = em.createNativeQuery("" + "SELECT\r\n" + "PEP.id_explosion_prenda,\r\n" + "PEP.id_text,\r\n"
					+ "PEP.talla,\r\n"
					+ "CONCAT(empleado.nombre_persona,' ',empleado.apellido_paterno, ' ', empleado.apellido_materno),\r\n"
					+ "DATE_FORMAT(PEP.fecha_inicio,'%Y-%m-%d %T'),\r\n"
					+ "DATE_FORMAT(PEP.fecha_fin,'%Y-%m-%d %T'),\r\n" + "'Interna', empleado.id_empleado\r\n"
					+ "FROM\r\n" + "alt_produccion_explosion_prendas AS PEP\r\n"
					+ "LEFT JOIN alt_hr_empleado empleado on empleado.id_empleado = PEP.realizo\r\n" + "WHERE\r\n"
					+ "1 = 1 \r\n" + "AND PEP.id_explosion_proceso =" + idExplosionProceso).getResultList();

			return re;

		} else if (tipo.equals("Externo")) {

			re = em.createNativeQuery("" + "SELECT\r\n" + "PEP.id_explosion_prenda,\r\n" + "PEP.id_text,\r\n"
					+ "PEP.talla,\r\n" + "maquilador.nombre,\r\n" + "DATE_FORMAT(PEP.fecha_inicio,'%Y-%m-%d %T'),\r\n"
					+ "DATE_FORMAT(PEP.fecha_fin,'%Y-%m-%d %T'),\r\n"
					+ "lookup.nombre_lookup, maquilador.id_maquilador\r\n" + "FROM\r\n"
					+ "alt_produccion_explosion_prendas AS PEP\r\n"
					+ "LEFT JOIN alt_produccion_maquilador maquilador on maquilador.id_maquilador = PEP.realizo\r\n"
					+ "LEFT JOIN alt_produccion_lookup lookup on lookup.id_lookup=PEP.ubicacion\r\n" + "WHERE\r\n"
					+ "1 = 1\r\n" + "AND PEP.id_explosion_proceso = " + idExplosionProceso).getResultList();

			return re;
		}else{
			return null;
		}
		// return em.createQuery("FROM ProduccionExplosionPrendas WHERE
		// idExplosionProceso ="+idExplosionProceso).getResultList();
	}

	@Transactional
	@Override
	public List<Object[]> listarEmpleadosbyProduccion() {
		List<Object[]> re = null;
		re = em.createNativeQuery("" + "SELECT\r\n" + "empleado.id_empleado,\r\n"
				+ "CONCAT( empleado.nombre_persona, ' ', empleado.apellido_paterno,' ',empleado.apellido_materno ),\r\n"
				+ "'0'\r\n" + "FROM\r\n" + "alt_hr_empleado empleado\r\n"
				+ "INNER JOIN alt_hr_puesto puesto ON empleado.id_puesto = puesto.id_puesto\r\n"
				+ "INNER JOIN alt_hr_departamento depa ON puesto.id_departamento = depa.id_departamento\r\n"
				+ "INNER JOIN alt_hr_lookup look ON look.id_lookup = depa.id_area\r\n" + "WHERE\r\n" + "1 = 1 \r\n"
				+ "AND look.nombre_lookup = 'PRODUCCION' \r\n"
				+ "AND empleado.estatus=1 	ORDER BY empleado.nombre_persona").getResultList();

		return re;
	}

	@Transactional
	@Override
	public List<Object[]> listarMaquilerosbyProceso(Long idProceso) {
		List<Object[]> re = null;
		re = em.createNativeQuery("" + "SELECT\r\n" + "maquilador.id_maquilador,\r\n" + "maquilador.nombre,\r\n"
				+ "maquilador.id_ubicacion\r\n" + "FROM\r\n" + "alt_produccion_maquilador AS maquilador\r\n"
				+ "INNER JOIN alt_produccion_maquilador_proceso MP ON maquilador.id_maquilador = MP.id_maquilador\r\n"
				+ "WHERE\r\n" + "1 = 1\r\n" + "AND MP.id_proceso = " + idProceso + "\r\n"
				+ "AND maquilador.estatus=1 	ORDER BY maquilador.nombre").getResultList();

		return re;
	}

	@Override
	@Transactional
	public ProduccionExplosionPrendas findOnePrendas(Long id) {

		return repositoryPrendas.findById(id).orElse(null);

	}
	// metodos de cosnnumo

	@Override
	public ProduccionConsumoReal findOneConsumoReal(Long id) {
		return repositoryConsumo.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public List<Object[]> queryParaInsertarTelas(Long id) {
		List<Object[]> re = null;
		re = em.createNativeQuery("" + 
			"SELECT\r\n"+
				"tela.id_tela,\r\n"+
				"EP.programa,\r\n"+
				"reporte.id_coordinado_prenda,\r\n"+
				"reporte.Principal_Combinacion\r\n"+
			"FROM\r\n"+
				"alt_view_apartado_telas_reporte AS reporte\r\n"+
				"INNER JOIN alt_disenio_tela tela ON reporte.id_tela = tela.id_tela\r\n"+
				"INNER JOIN alt_comercial_pedido_informacion pedido ON reporte.idPedido = pedido.id_pedido_informacion\r\n"+
				"INNER JOIN alt_produccion_explosion_procesos EP ON EP.coordinado = reporte.id_coordinado_prenda\r\n"+
			"WHERE\r\n"+
				"1 = 1\r\n"+
				"AND EP.id_explosion_procesos = "+id+"\r\n"+
			"GROUP BY\r\n"+
				"tela.id_tela,\r\n"+
				"reporte.Principal_Combinacion\r\n"+
			"ORDER BY\r\n"+
				"reporte.Principal_Combinacion").getResultList();

		return re;
	}

	@Transactional
	@Override
	public String validarExistenciaConsumo(String idTela, String programa, String idCoorPrenda, String tipo) {
		String re = null;
		re = (String) em.createNativeQuery("" + 
				"SELECT\r\n"+
					"COUNT(consumo.id_consumo_real)\r\n"+
				"FROM\r\n"+
					"alt_produccion_consumo_real AS consumo\r\n"+
				"WHERE\r\n"+
					"1 = 1\r\n"+ 
					"AND consumo.id_tela = "+idTela+" \r\n"+
					"AND consumo.programa = '"+programa+"'\r\n"+
					"AND consumo.id_coordinado_prenda = "+idCoorPrenda+"\r\n"+
					"AND consumo.tipo_tela = '"+tipo+"'").getSingleResult().toString();

		return re;
	}

	@Transactional
	@Override
	public void saveConsumo(ProduccionConsumoReal consumo) {
		
		repositoryConsumo.save(consumo);

	}

	@Transactional
	@Override
	public List<Object[]> view(Long id) {
		List<Object[]> re = null;
		re = em.createNativeQuery(""+
			"SELECT\r\n"+
				"consumo.id_consumo_real,\r\n"+
				"tela.id_text,\r\n"+
				"tela.nombre_tela,\r\n"+
				"consumo.tipo_tela,\r\n"+
				"consumo.consumo_real\r\n"+
			"FROM\r\n"+
				"alt_produccion_consumo_real AS consumo\r\n"+
				"INNER JOIN alt_disenio_tela tela ON consumo.id_tela = tela.id_tela\r\n"+
				"INNER JOIN alt_produccion_explosion_procesos EP ON EP.coordinado = consumo.id_coordinado_prenda\r\n"+
			"WHERE\r\n"+
				"1 = 1\r\n"+
				"AND EP.id_explosion_procesos = "+id).getResultList();

		return re;
		
	}

	@Transactional
	@Override
	public String validarNoNulos(Long id) {
		String re = null;
		re = (String) em.createNativeQuery("" +
			"SELECT\r\n"+
				"COUNT(prendas.id_explosion_prenda)\r\n"+
			"FROM\r\n"+
				"alt_produccion_explosion_prendas as prendas\r\n"+
			"WHERE \r\n"+
				"1=1\r\n"+
				"AND prendas.realizo is  null AND prendas.id_explosion_proceso="+id).getSingleResult().toString();

		return re;
	}
	
}
