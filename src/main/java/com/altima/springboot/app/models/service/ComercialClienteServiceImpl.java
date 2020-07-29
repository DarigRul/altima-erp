package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialClienteFactura;
import com.altima.springboot.app.repository.ComercialClienteFacturacionRepository;
import com.altima.springboot.app.repository.ComercialClienteRepository;

@Service
public class ComercialClienteServiceImpl implements IComercialClienteService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ComercialClienteRepository repository;

	@Autowired
	private ComercialClienteFacturacionRepository repositoryFactura;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialCliente> findAll(Long iduser) {
		// TODO Auto-generated method stub
		List<ComercialCliente> result = null;
		if (iduser != null) {
			result = em
					.createQuery(
							"from ComercialCliente where IdUsuario=" + iduser + "  ORDER BY id_text, nombre,estatus")
					.getResultList();
		} else {
			result = em.createQuery("from ComercialCliente   ORDER BY id_text, nombre,estatus").getResultList();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> findAllAgentes() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT hu.id_usuario,he.nombre_persona,he.apellido_paterno,he.apellido_materno\n"
				+ "FROM `alt_hr_usuario` hu, alt_hr_empleado he where hu.id_empleado = he.id_empleado and he.id_puesto=1;\n"
				+ "").getResultList();
	}

	@Override
	public void save(ComercialCliente ComercialCliente) {
		repository.save(ComercialCliente);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public ComercialCliente findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Integer Contador(String tipo) {

		String re = em
				.createNativeQuery(
						"SELECT COUNT(*) FROM alt_comercial_cliente where alt_comercial_cliente.tipo_cliente=" + tipo)
				.getSingleResult().toString();
		return Integer.parseInt(re);

	}

	// F A C T U R A
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialClienteFactura> ListaFactura(Long id) {
		// TODO Auto-generated method stub
		return em
				.createQuery(
						"from ComercialClienteFactura where id_cliente =" + id + "  ORDER BY id_text,razon_social ")
				.getResultList();
	}

	@Override
	public void saveFactura(ComercialClienteFactura factura) {
		repositoryFactura.save(factura);

	}

	@Override
	public ComercialClienteFactura findOneFactura(Long id) {
		// TODO Auto-generated method stub
		return repositoryFactura.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Integer ContadorFacturas(Long id) {
		String re = em.createNativeQuery(
				"SELECT COUNT(*) FROM alt_comercial_cliente_factura where  alt_comercial_cliente_factura.id_cliente="
						+ id)
				.getSingleResult().toString();
		return Integer.parseInt(re);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ComercialCliente> findAllEstatus1(Long iduser) {
		// TODO Auto-generated method stub
		List<ComercialCliente> result = null;
		if (iduser != null) {
			result = em.createQuery("from ComercialCliente where estatus=1 and IdUsuario=" + iduser
					+ "  ORDER BY id_text, nombre,estatus").getResultList();
		} else {
			result = em.createQuery("from ComercialCliente where estatus=1   ORDER BY id_text, nombre,estatus")
					.getResultList();
		}
		return result;
	}
}
