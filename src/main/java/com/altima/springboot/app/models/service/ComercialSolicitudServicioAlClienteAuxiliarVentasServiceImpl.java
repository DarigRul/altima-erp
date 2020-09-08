package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteAuxiliarVentas;
import com.altima.springboot.app.repository.ComercialSolicitudServicioAlClienteAuxiliarVentasRepository;

@Service
public class ComercialSolicitudServicioAlClienteAuxiliarVentasServiceImpl implements IComercialSolicitudServicioAlClienteAuxiliarVentasService{
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComercialSolicitudServicioAlClienteAuxiliarVentasRepository repository;
	
	@Override
	public void save(ComercialSolicitudServicioAlClienteAuxiliarVentas ComercialSolicitudServicioAlClienteAuxiliarVentas) {
		// TODO Auto-generated method stub
		repository.save(ComercialSolicitudServicioAlClienteAuxiliarVentas);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComercialSolicitudServicioAlClienteAuxiliarVentas> findBySolicitud(Long idSolicitud) {
		// TODO Auto-generated method stub
		return (List<ComercialSolicitudServicioAlClienteAuxiliarVentas>) em.createQuery("FROM ComercialSolicitudServicioAlClienteAuxiliarVentas WHERE idSolicitudServicioAlCliente = " + idSolicitud).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> devolverSelectAuxiliarVentas(Long idSolicitud) {
		// TODO Auto-generated method stub
		ArrayList<String> generos = new ArrayList<>(Arrays.asList("Masculino", "Femenino", "Indistinto"));
		
		
		List<String> re = em.createNativeQuery("SELECT\r\n" + 
				"	 auxiliar.genero AS Genero\r\n" + 
				"    FROM alt_comercial_solicitud_servicio_al_cliente_auxiliar_ventas AS auxiliar\r\n" + 
				"    WHERE auxiliar.id_solicitud_servicio_al_cliente = " + idSolicitud).getResultList();
		
		for(int i = 0; i < re.size(); i++) {
			for(int j = 0; j < generos.size() ; j++) {
				if(generos.get(j).toString().equalsIgnoreCase(re.get(i).toString())) {
					generos.remove(j);
				}
			}
		}
		
		return generos;
	}

}
