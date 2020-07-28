package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.DisenioPrendaImagen;

import com.altima.springboot.app.repository.DisenioImagenPrendaRepository;

@Service
public class DisenioImagenPrendaServiceImpl implements IDisenioImagenPrendaService
{
	@Autowired
	private EntityManager em;
	
	@Autowired
	private DisenioImagenPrendaRepository repository;
	
	@Override
	public List<DisenioPrendaImagen> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioPrendaImagen>) repository.findAll();
	}

	@Override
	public void save(DisenioPrendaImagen disenioImagenPrenda) {
		// TODO Auto-generated method stub
		repository.save(disenioImagenPrenda);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public DisenioPrendaImagen findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<DisenioPrendaImagen> findByPrenda(Long id) {
		// TODO Auto-generated method stub
		List<DisenioPrendaImagen> existentes = em.createQuery("FROM DisenioPrendaImagen WHERE idPrenda =" + id  + " AND nombrePrenda != 'Inventario'").getResultList();
		List<DisenioPrendaImagen> listaNueva = new ArrayList<>();
		
		for(int i = 0; i < 6; i++)
		{
			Long num = i + 1L;
			
			if(i < existentes.size())
			{
				DisenioPrendaImagen dpi = existentes.get(i);
				dpi.setEstatus(num.toString());
				listaNueva.add(dpi);
				dpi = new DisenioPrendaImagen();
			}
			else
			{
				DisenioPrendaImagen dpi = new DisenioPrendaImagen();
				dpi.setEstatus(num.toString());
				listaNueva.add(dpi);
			}
		}
		
		
		
		return listaNueva;
	}

}
