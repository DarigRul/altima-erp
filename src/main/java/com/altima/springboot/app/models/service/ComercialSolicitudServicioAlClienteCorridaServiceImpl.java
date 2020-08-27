package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlClienteCorrida;
import com.altima.springboot.app.repository.ComercialSolicitudServicioAlClienteCorridaRepository;

@Service
public class ComercialSolicitudServicioAlClienteCorridaServiceImpl implements IComercialSolicitudServicioAlClienteCorridaService{
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ComercialSolicitudServicioAlClienteCorridaRepository repository;
	
	@Override
	public void save(ComercialSolicitudServicioAlClienteCorrida ComercialSolicitudServicioAlClienteCorrida) {
		// TODO Auto-generated method stub
		repository.save(ComercialSolicitudServicioAlClienteCorrida);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComercialSolicitudServicioAlClienteCorrida> findBySolicitud(Long idSolicitud) {
		// TODO Auto-generated method stub
		return (List<ComercialSolicitudServicioAlClienteCorrida>) em.createQuery("FROM ComercialSolicitudServicioAlClienteCorrida WHERE idSolicitudServicioAlCliente = " + idSolicitud).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> devolverSelectCorridas(Long idSolicitud) {
		// TODO Auto-generated method stub
		ArrayList<String> generos = new ArrayList<>(Arrays.asList("Masculino", "Femenino", "Indistinto"));
		
		
		List<String> re = em.createNativeQuery("SELECT\r\n" + 
				"	 corrida.genero AS Genero\r\n" + 
				"    FROM alt_comercial_solicitud_servicio_al_cliente_corrida AS corrida\r\n" + 
				"    WHERE corrida.id_solicitud_servicio_al_cliente = " + idSolicitud).getResultList();
		
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
