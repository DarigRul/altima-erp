package com.altima.springboot.app.models.service;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.boot.Metadata;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.dto.PrendaListDTO;
import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.repository.DisenioPrendaRepository;

@Service
public class DisenioPrendaServiceImpl implements IDisenioPrendaService {
	@Autowired
	private DisenioPrendaRepository repository;
	@Autowired
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<DisenioPrenda> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioPrenda>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(DisenioPrenda disenioprenda) {
		// TODO Auto-generated method stub
		repository.save(disenioprenda);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public DisenioPrenda findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String[] getExistencias(Long familiaPrenda) {
		String[] response = new String[2];

		// Consecutivo Nombre
		List<String> nombre = em
				.createNativeQuery("SELECT nombre_lookup FROM `alt_disenio_lookup` WHERE id_lookup =" + familiaPrenda)
				.getResultList();
		String[] soloUnaPalabra = nombre.get(0).split(" ");
		response[1] = soloUnaPalabra[0];

		// Consecutivo Numero
		List<BigInteger> existencias = em.createNativeQuery(
				"SELECT COUNT(DP.id_prenda) FROM alt_disenio_prenda AS DP INNER JOIN alt_disenio_lookup AS DL ON DP.id_familia_prenda = DL.id_lookup WHERE DL.nombre_lookup LIKE '%"
						+ response[1] + "%' AND DP.prenda_local != 0 AND DP.estatus = 1;")
				.getResultList();
		BigInteger res = existencias.get(0);
		Long retu = (res.longValue());
		response[0] = retu.toString();

		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> BuscarPrendaById(Long id) {

		List<Object[]> re = em.createNativeQuery(
				"SELECT adp.id_prenda, adp.id_familia_prenda, adp.id_text, adp.id_text_prospecto, adp.numero_prenda, \r\n"
						+ "				adp.detalle_prenda, adp.nota_especial, adp.precio_local_actual, adp.precio_local_anterior, \r\n"
						+ "				adp.precio_foraneo_actual, adp.precio_foraneo_anterior, adp.detalle_confeccion, adp.consumo_tela, \r\n"
						+ "				adp.consumo_forro, adp.precio, adp.id_ruta, adp.tipo_largo, adp.especificacion, adp.devolucion, \r\n"
						+ "				adp.precio_m_prod, adp.precio_m_muestra, adp.categoria, adp.total_prendas, adp.mostrar, adp.descripcion_prenda, \r\n"
						+ "				adp.estatus, adp.prenda_local, adp.id_genero, adl.nombre_lookup AS 'genero' \r\n"
						+ "			FROM alt_disenio_prenda adp INNER JOIN alt_disenio_lookup adl ON adp.id_genero = adl.id_lookup \r\n"
						+ "			WHERE 1=1 AND adp.estatus ='1' AND id_prenda=" + id)
				.getResultList();

		return re;
	}

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {

		metadata.getSqlFunctionMap().put("group_concat",
				(SQLFunction) new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> ListaClientesPrenda(Long id) {

		List<Object[]> re = em.createNativeQuery(
				"SELECT adpc.id_cliente, GROUP_CONCAT(DISTINCT(CONCAT_WS(acc.nombre,' ', acc.apellido_paterno, ' ', acc.apellido_materno))) AS 'Clientes' \r\n"
						+ "			FROM alt_disenio_prenda_cliente adpc INNER JOIN alt_disenio_prenda adp ON adpc.id_prenda = adp.id_prenda \r\n"
						+ "			INNER JOIN alt_comercial_cliente acc ON adpc.id_cliente = acc.id_cliente WHERE 1=1 AND adpc.estatus='1' AND adp.id_prenda="
						+ id)
				.getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> ImagenPrenda(Long id) {

		List<Object[]> re = em.createNativeQuery("SELECT adpi.id_prenda, adpi.nombre_prenda, adpi.ruta_prenda \r\n"
				+ "			FROM alt_disenio_prenda_imagen adpi INNER JOIN alt_disenio_prenda adp ON adpi.id_prenda = adp.id_prenda \r\n"
				+ "			WHERE 1=1 AND adp.id_prenda=" + id).getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> ListaMarcadoresPrendas(Long id) {

		List<Object[]> re = em.createNativeQuery("SELECT adp.id_prenda, GROUP_CONCAT(adl.nombre_lookup) \r\n"
				+ "			FROM alt_disenio_lookup adl INNER JOIN alt_disenio_prenda_marcador adpm ON adl.id_lookup = adpm.id_marcador \r\n"
				+ "			INNER JOIN alt_disenio_prenda adp ON adpm.id_prenda = adp.id_prenda \r\n"
				+ "			WHERE 1=1 AND adp.id_prenda=" + id).getResultList();

		return re;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validarDescripcionPrenda(String desc) {
		// TODO Auto-generated method stub
		boolean Bandera = false;
		List<DisenioPrenda> res = (List<DisenioPrenda>) em.createNativeQuery(
				"SELECT 'descripcion_prenda' FROM alt_disenio_prenda WHERE CAST(descripcion_prenda AS BINARY) = '"
						+ desc + "';")
				.getResultList();

		if (res.size() > 0) {
			Bandera = false;
		} else {
			Bandera = true;
		}

		return Bandera;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<PrendaListDTO> findAllMin() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT adp.id_prenda,adp.id_text,adp.id_text_prospecto,adp.descripcion_prenda,adlfampre.nombre_lookup tipo_prenda,adp.prenda_local,adp.estatus_recepcion_muestra,adp.estatus FROM `alt_disenio_prenda` adp INNER JOIN alt_disenio_lookup adlfampre ON adlfampre.id_lookup=adp.id_familia_prenda",PrendaListDTO.class).getResultList();
	}
	
	
}
