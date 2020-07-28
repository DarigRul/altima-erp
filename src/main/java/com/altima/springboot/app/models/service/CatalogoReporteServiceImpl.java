package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.altima.springboot.app.models.entity.DisenioLookup;

@Service
public class CatalogoReporteServiceImpl implements ReporteCatalogoService {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findFiltroReporte(String cata, String palabra, String fecha1, String fecha2) {
		return em.createQuery("from DisenioLookup  where tipoLookup = '" + cata + "'\r\n" + "and nombreLookup like '%"
				+ palabra + "%'  and fechaCreacion BETWEEN '" + fecha1 + "' and '" + fecha2 + "'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findFiltroReporteSinpalabra(String cata, String fecha1, String fecha2) {
		return em.createQuery("from DisenioLookup  where tipoLookup = '" + cata + "'\r\n" + " and fechaCreacion BETWEEN '"
				+ fecha1 + "' and '" + fecha2 + "'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findFiltroReporteSinFechas(String cata, String palabra) {
		return em
				.createQuery(
						"from DisenioLookup  where tipoLookup = '" + cata + "'\r\n" + "and nombreLookup like '%" + palabra + "%'")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findFiltroReporteSinFePa(String cata) {
		return em.createQuery("from DisenioLookup  where tipoLookup = '" + cata + "'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String[]> findAllLookup() {
		return em.createNativeQuery("SELECT DISTINCT tipo_lookup  FROM alt_disenio_lookup ").getResultList();
	}
}
