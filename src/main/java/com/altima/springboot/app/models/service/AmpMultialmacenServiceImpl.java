package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.dto.ArticulosMultialmacenDto;
import com.altima.springboot.app.dto.EntradasSalidasDTO;
import com.altima.springboot.app.models.entity.AmpAlmacenLogico;
import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.repository.AmpMultialmacenRepository;

@Service
public class AmpMultialmacenServiceImpl implements IAmpMultialmacenService {
	@Autowired
	AmpMultialmacenRepository repository;

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void save(AmpMultialmacen multialmacen) {
		if (multialmacen.getExistencia() < 0) {
			System.out.println("si entra al error");
			throw new RuntimeException("La cantidad debe ser mayor o igual a 0");
		}
		repository.save(multialmacen);
	}

	@Override
	@Transactional
	public AmpMultialmacen findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public List<AmpMultialmacen> findAll() {
		return (List<AmpMultialmacen>) repository.findAll();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AmpAlmacenLogico> findAllActiveAMPLogic() {
		return em.createQuery("From AmpAlmacenLogico where estatus='1'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllAMPLogicItem(Long articulo, String tipo) {

		return em.createNativeQuery("" + "SELECT\r\n" + "	am.id_multialmacen,\r\n"
				+ "	al.nombre_almacen_logico,\r\n" + "	am.id_articulo,\r\n"
				+ "	am.existencia,al.tipo,al.id_almacen_fisico,al.id_almacen_logico \r\n" + "FROM\r\n"
				+ "	alt_amp_multialmacen am,\r\n" + "	alt_amp_almacen_logico al \r\n" + "WHERE\r\n"
				+ "	am.id_almacen_logico = al.id_almacen_logico \r\n" + "	AND am.id_articulo = " + articulo + " \r\n"
				+ "	AND am.tipo = '" + tipo + "'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AmpMultialmacen> findDuplicates(String tipoPost, Long almacenLogico, Long articulo) {

		return em.createQuery("From AmpMultialmacen where idArticulo=" + articulo + " and idAlmacenLogico="
				+ almacenLogico + " and tipo='" + tipoPost + "'  ").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ArticulosMultialmacenDto> findArticulosByMultialmacen(Long idAlmacenLogico) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String roles = auth.getAuthorities().toString();
		Integer tipoAlmacen=null;
		if (roles.contains("ROLE_COMERCIAL_AMP_ENTRADASSALIDAS_MATERIAL")) {
			tipoAlmacen=0;
		} else if (roles.contains("ROLE_COMERCIAL_AMP_ENTRADASSALIDAS_MP")) {
			tipoAlmacen=1;
		} else if (roles.contains("ROLE_ADMINISTRADOR")) {
			tipoAlmacen=2;
		}
		return em
				.createNativeQuery("call alt_pr_articulos_multialmacen(:idAlmacenLogico,:tipoAlamcen)",
						ArticulosMultialmacenDto.class)
				.setParameter("idAlmacenLogico", idAlmacenLogico).setParameter("tipoAlamcen", tipoAlmacen).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EntradasSalidasDTO> findAllMovimientos() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("call alt_pr_movimientos_amp()", EntradasSalidasDTO.class).getResultList();
	}

	@Override
	@Transactional
	public Long findIdMultialmacen(Long idAlmacenLogico, Long idArticulo, String tipo) {
		// TODO Auto-generated method stub
		return repository.findByIdAlmacenLogicoAndTipoAndIdArticulo(idAlmacenLogico, tipo, idArticulo)
				.getIdAMultialmacen();
	}

	@Override
	@Transactional
	public Integer disponibles(Long id, String materia) {

		String re = em.createNativeQuery(
				"" + "SELECT\r\n" + "	IF (SUM( multi.existencia ) IS NULL,0,SUM( multi.existencia)) \r\n" + "FROM\r\n"
						+ "	alt_amp_almacen_logico AS almacen,\r\n" + "	alt_amp_multialmacen AS multi \r\n"
						+ "WHERE\r\n" + "	multi.id_almacen_logico = almacen.id_almacen_logico \r\n"
						+ "	AND multi.tipo = '" + materia + "' \r\n" + "	AND multi.id_articulo =" + id + "\r\n"
						+ "	AND almacen.tipo !=2\r\n" + "	AND almacen.tipo !=3")
				.getSingleResult().toString();

		if (re.isEmpty() || re == null) {
			return 0;
		} else {
			double d = Double.parseDouble(re);
			return (int) d;
		}

	}

	@Override
	@Transactional
	public Float existenciaArticuloByAlmacen(Long idAlmacenLogico, Long idArticulo, String Tipo) {
		// TODO Auto-generated method stub
		return (Float) em
				.createNativeQuery(
						"call alt_pr_existencia_articulo(" + idAlmacenLogico + "," + idArticulo + ",'" + Tipo + "')")
				.getSingleResult();
	}

	@Override
	@Transactional
	public String existArticuloInAlmacen(Long idAlmacenLogico, Long idArticulo, String tipo) {
		// TODO Auto-generated method stub
		return (String) em.createNativeQuery(
				"SELECT CASE WHEN EXISTS (SELECT*FROM alt_amp_multialmacen aam WHERE aam.id_almacen_logico=:idAlmacenLogico AND aam.id_articulo=:idArticulo AND aam.tipo=:tipo) THEN 'true' ELSE 'false' END")
				.setParameter("idAlmacenLogico", idAlmacenLogico).setParameter("idArticulo", idArticulo)
				.setParameter("tipo", tipo).getSingleResult();
	}

}
