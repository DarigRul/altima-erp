package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialCoordinadoPreapartado;
import com.altima.springboot.app.models.entity.ComercialPreApartado;
import com.altima.springboot.app.models.entity.ComercialPrendaPreapartado;
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
	public List<Object[]> findPreapartados() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT preapartado.id_preapartado, \r\n" + 
											"preapartado.id_text AS Folio, \r\n" + 
											"CONCAT(cliente.nombre, \" \", IFNULL(cliente.apellido_paterno, \" \"), \" \", IFNULL(cliente.apellido_materno, \" \")) AS Nombre_cliente,\r\n" + 
											"preapartado.fecha_preapartado,\r\n" + 
											"preapartado.num_personas,\r\n" + 
											"CONCAT(empleado.nombre_persona,\" \",IFNULL(empleado.apellido_paterno, \" \"),\" \",IFNULL(empleado.apellido_materno, \" \")) AS Nombre_empleado,\r\n" + 
											"preapartado.estatus,\r\n" + 
											"preapartado.estatus_pedido,\r\n" + 
											"preapartado.referencia_pedido\r\n" + 
											"\r\n" + 
									"FROM alt_comercial_preapartado AS preapartado\r\n" + 
									"\r\n" + 
									"INNER JOIN alt_comercial_cliente cliente ON preapartado.id_cliente = cliente.id_cliente\r\n" + 
									"INNER JOIN alt_hr_empleado empleado ON empleado.id_empleado = preapartado.id_empleado").getResultList();
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
		
		return em.createNativeQuery("SELECT cpp.id_preapartado,\r\n" + 
				"		cpp.id_cliente,\r\n" + 
				"		cpp.id_empleado,\r\n" + 
				"		telas.id_text,\r\n" + 
				"		telas.id_tela,\r\n" + 
				"		prenPre.id_prenda,\r\n" + 
				"		CONCAT(cliente.nombre, \" \", IFNULL(cliente.apellido_paterno, \" \"), \" \", IFNULL(cliente.apellido_materno, \" \")) AS Nombre_cliente,\r\n" + 
				"		cpp.referencia_pedido,\r\n" + 
				"		cpp.fecha_preapartado,\r\n" + 
				"		telas.nombre_tela,\r\n" + 
				"		telas.color,\r\n" + 
				"		coorPre.total_prendas,\r\n" +  
				"		IFNULL(((cpp.num_personas*coorPre.total_prendas)-(-((1.5 - telas.ancho)*(cpp.num_personas*coorPre.total_prendas))) \r\n" + 
				"		+\r\n" + 
				"		IF((telas.estampado!=\"Liso\" AND telas.estampado!=\"Fantasia\"),(cpp.num_personas)* 0.1 ,0) 		\r\n" + 
				"		+\r\n" + 
				"		(telas.prueba_encogimiento/100)*(\r\n" + 
				"			(matPrenda.cantidad)-(-((1.5 - telas.ancho)*(cpp.num_personas*coorPre.total_prendas))) \r\n" + 
				"		+\r\n" + 
				"		IF((telas.estampado!=\"Liso\" AND telas.estampado!=\"Fantasia\"),(cpp.num_personas*coorPre.total_prendas)* 0.1 ,0))), 0) as consumo, \r\n" + 
				"		\"\" as d,\r\n" + 
				"		telas.foto,\r\n" + 
				"		cpp.num_personas \r\n" + 
				"\r\n" + 
				"FROM alt_comercial_preapartado AS cpp\r\n" + 
				"\r\n" + 
				"INNER JOIN alt_comercial_coordinado_preapartado coorPre ON cpp.id_preapartado = coorPre.id_preapartado\r\n" + 
				"INNER JOIN alt_comercial_prendas_preapartado prenPre ON coorPre.id_coordinado = prenPre.id_coordinado\r\n" + 
				"INNER JOIN alt_comercial_cliente cliente ON cpp.id_cliente = cliente.id_cliente\r\n" + 
				"INNER JOIN alt_disenio_tela telas ON prenPre.id_tela = telas.id_tela\r\n" + 
				"INNER JOIN alt_disenio_prenda prenda ON prenPre.id_prenda = prenda.id_prenda\r\n" + 
				"INNER JOIN alt_disenio_material_prenda matPrenda ON prenda.id_prenda = matPrenda.id_prenda\r\n" + 
				"INNER JOIN alt_disenio_material mat ON matPrenda.id_material = mat.id_material AND mat.nombre_material = \"Tela Principal\" \r\n" +
				"\r\n" + 
				"WHERE cpp.id_preapartado = "+id+"\r\n" + 
				"GROUP BY telas.id_tela \r\n" + 
				"ORDER BY id_tela").getResultList();
	}
	
}
