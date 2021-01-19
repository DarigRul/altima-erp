package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCoordinadoPreapartado;
import com.altima.springboot.app.models.entity.ComercialPreApartado;
import com.altima.springboot.app.models.entity.ComercialPrendaPreapartado;
import com.altima.springboot.app.models.entity.ComercialTelasPreapartado;
import com.altima.springboot.app.repository.ComercialPreApartadoRepository;

@SuppressWarnings("unchecked")
@Service
public class ComercialPreApartadoServiceImpl implements IComercialPreApartadoService {

	@Autowired
	private ComercialPreApartadoRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Transactional
	@Override
	public void save(ComercialPreApartado comercialPreApartado) {
		// TODO Auto-generated method stub
		repository.save(comercialPreApartado);
		
	}

	@Transactional
	@Override
	public List<Object[]> findPreapartados(String NombreUsuario) {
		// TODO Auto-generated method stub
		if(NombreUsuario.equals("")) {
		return em.createNativeQuery("SELECT preapartado.id_preapartado, \r\n" + 
											"preapartado.id_text AS Folio, \r\n" + 
											"CONCAT(cliente.nombre, \" \", IFNULL(cliente.apellido_paterno, \" \"), \" \", IFNULL(cliente.apellido_materno, \" \")) AS Nombre_cliente,\r\n" + 
											"preapartado.fecha_preapartado,\r\n" + 
											"preapartado.num_personas,\r\n" + 
											"CONCAT(empleado.nombre_persona,\" \",IFNULL(empleado.apellido_paterno, \" \"),\" \",IFNULL(empleado.apellido_materno, \" \")) AS Nombre_empleado,\r\n" + 
											"preapartado.estatus,\r\n" + 
											"preapartado.estatus_pedido,\r\n" + 
											"preapartado.referencia_pedido,\r\n" + 
											"preapartado.fecha_vigencia\r\n" + 
									"FROM alt_comercial_preapartado AS preapartado\r\n" + 
									"\r\n" + 
									"INNER JOIN alt_comercial_cliente cliente ON preapartado.id_cliente = cliente.id_cliente\r\n" + 
									"INNER JOIN alt_hr_empleado empleado ON empleado.id_empleado = preapartado.id_empleado ORDER BY preapartado.id_preapartado DESC").getResultList();
		}
		else {
			return em.createNativeQuery("SELECT preapartado.id_preapartado,  \r\n" + 
					"											preapartado.id_text AS Folio,  \r\n" + 
					"											CONCAT(cliente.nombre, \" \", IFNULL(cliente.apellido_paterno, \" \"), \" \", IFNULL(cliente.apellido_materno, \" \")) AS Nombre_cliente, \r\n" + 
					"											preapartado.fecha_preapartado, \r\n" + 
					"											preapartado.num_personas, \r\n" + 
					"											CONCAT(empleado.nombre_persona,\" \",IFNULL(empleado.apellido_paterno, \" \"),\" \",IFNULL(empleado.apellido_materno, \" \")) AS Nombre_empleado, \r\n" + 
					"											preapartado.estatus, \r\n" + 
					"											preapartado.estatus_pedido, \r\n" + 
					"											preapartado.referencia_pedido \r\n" + 
					"											preapartado.fecha_vigencia \r\n" + 
					"									FROM alt_comercial_preapartado AS preapartado \r\n" + 
					"									 \r\n" + 
					"									INNER JOIN alt_comercial_cliente cliente ON preapartado.id_cliente = cliente.id_cliente \r\n" + 
					"									INNER JOIN alt_hr_empleado empleado ON empleado.id_empleado = preapartado.id_empleado \r\n" + 
					"									INNER JOIN alt_hr_usuario usuario ON empleado.id_empleado = usuario.id_empleado\r\n" + 
					"									\r\n" + 
					"									WHERE usuario.nombre_usuario ='"+NombreUsuario+"'"+ 
					"									ORDER BY preapartado.id_preapartado DESC").getResultList();
		}
	}

	
	@Transactional
	@Override
	public ComercialPreApartado findOne(Long id) {
		
		return repository.findById(id).orElse(null);
	}
	
	@Transactional
	@Override
	public List<ComercialCoordinadoPreapartado> findCoordinadosByPreapartado(Long id) {
		
		return em.createQuery("FROM ComercialCoordinadoPreapartado WHERE idPreapartado = "+id).getResultList();
	}

	@Transactional
	@Override
	public void saveCoordinado(ComercialCoordinadoPreapartado comercialCoordinadoPreapartado) {
		// TODO Auto-generated method stub
		if(comercialCoordinadoPreapartado.getIdCoordinado() !=null &&  comercialCoordinadoPreapartado.getIdCoordinado()>0) {
			em.merge(comercialCoordinadoPreapartado);

		}
		else {
			em.persist(comercialCoordinadoPreapartado);
		}
		
	}
	
	@Transactional
	@Override
	public ComercialCoordinadoPreapartado findCoordinado(Long id) {
		
		return em.find(ComercialCoordinadoPreapartado.class,id);
		
	}
	
	@Transactional
	@Override
	public void deleteCoordinado(Long id) {
		
		em.remove(findCoordinado(id));
		
	}
	
	@Transactional
	@Override
	public List<ComercialPrendaPreapartado> findPrendasByCoordinados(Long id) {
		
		return em.createNativeQuery("SELECT cpp.id_prenda_preapartado, \r\n" + 
				"		dl.nombre_lookup, \r\n" + 
				"		prenda.descripcion_prenda, \r\n" + 
				"		tela.nombre_tela, \r\n" + 
				"		cpp.id_coordinado FROM alt_comercial_prendas_preapartado AS cpp\r\n" + 
				"\r\n" + 
				"INNER JOIN alt_disenio_lookup dl ON cpp.id_familia_prenda = dl.id_lookup\r\n" + 
				"INNER JOIN alt_disenio_tela tela ON cpp.id_tela = tela.id_tela\r\n" + 
				"INNER JOIN alt_disenio_prenda prenda ON cpp.id_prenda = prenda.id_prenda WHERE cpp.id_coordinado = "+id).getResultList();
	}
	
	@Transactional
	@Override
	public void savePrendaCoordinado(ComercialPrendaPreapartado comercialPrendaPreapartado) {
		// TODO Auto-generated method stub
		if(comercialPrendaPreapartado.getIdPrendaPreapartado() !=null && comercialPrendaPreapartado.getIdPrendaPreapartado()>0) {
			em.merge(comercialPrendaPreapartado);

		}
		else {
			em.persist(comercialPrendaPreapartado);
		}
		
	}
	
	@Transactional
	@Override
	public ComercialPrendaPreapartado findPrendaCoordinado(Long id) {
		
		return em.find(ComercialPrendaPreapartado.class,id);
		
	}
	
	@Transactional
	@Override
	public void deletePrendaCoordinado(Long id) {
		
		em.remove(findPrendaCoordinado(id));
		
	}
	
	@Transactional
	@Override
	public List<Object[]> reportePreapartados(Long id) {
		
		return em.createNativeQuery("SELECT * FROM `alt_view_pre_apartado_telas_reporte` where id_preapartado = "+id+" ORDER BY id_tela").getResultList();
	}

	@Transactional
	@Override
	public void saveTelasCoordinado(ComercialTelasPreapartado comercialTelasPreapartado) {
		// TODO Auto-generated method stub
		
		if(comercialTelasPreapartado.getIdTelaPreapartado() !=null && comercialTelasPreapartado.getIdTelaPreapartado()>0) {
			em.merge(comercialTelasPreapartado);

		}
		else {
			em.persist(comercialTelasPreapartado);
		}
	}

	@Transactional
	@Override
	public void deleteTelasCoordinado(Long id) {
		// TODO Auto-generated method stub
		
		em.remove(findTelaCoordinado(id));
	}
	
	@Transactional
	@Override
	public ComercialTelasPreapartado findTelaCoordinado(Long id) {
		// TODO Auto-generated method stub
		
		return em.find(ComercialTelasPreapartado.class,id);
		
	}
	
	
}
