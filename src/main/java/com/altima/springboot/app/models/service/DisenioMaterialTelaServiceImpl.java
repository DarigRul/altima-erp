package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioFamiliaComposicionTela;
import com.altima.springboot.app.models.entity.DisenioMaterialTela;
import com.altima.springboot.app.repository.DisenioMaterialTelaRepository;

@Service
public class DisenioMaterialTelaServiceImpl implements IDisenioMaterialTelaService {
	@Autowired
	private DisenioMaterialTelaRepository repository;
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioMaterialTela> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioMaterialTela>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioMaterialTela diseniomaterialtela) {
		// TODO Auto-generated method stub
		DisenioMaterialTela dmtOld = null;
		try {
			dmtOld = (DisenioMaterialTela) em.createQuery("FROM DisenioMaterialTela WHERE idTela = " + diseniomaterialtela.getIdTela() + " AND idTipoMaterial = " + diseniomaterialtela.getIdTipoMaterial() + " AND posicion = '" + diseniomaterialtela.getPosicion() + "'"+ " AND color = '" + diseniomaterialtela.getColor() + "'").getSingleResult();
		}
		catch(NoResultException nre) {
			//
		}
		
		if(dmtOld != null) {
			//Ya existe un registro, solo se actualiza
			dmtOld.setColor(diseniomaterialtela.getColor());
			dmtOld.setCodigocolor(diseniomaterialtela.getCodigocolor());
			dmtOld.setPosicion(diseniomaterialtela.getPosicion());
			dmtOld.setActualizadoPor(diseniomaterialtela.getActualizadoPor());
			dmtOld.setUltimaFechaModificacion(diseniomaterialtela.getUltimaFechaModificacion());
			repository.save(dmtOld);
		}
		else {
			//No existe un registro, se guarda todo completo.
			repository.save(diseniomaterialtela);
		}		
	}
	
	@Override
	public void deleteEliminados(String[] idMateriales, String[] colorMateriales, String[] codigoMateriales, Long idTela) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<DisenioMaterialTela> listaActuales = (List<DisenioMaterialTela>) em.createQuery("FROM DisenioMaterialTela WHERE idTela = " + idTela).getResultList();
		
		for(DisenioMaterialTela dmt : listaActuales){
			int Coincidencias = 0;
			for(int i = 0; i < (idMateriales.length); i++) {
				if(dmt.getIdTipoMaterial().toString().equalsIgnoreCase(idMateriales[i].toString()) && dmt.getColor().toString().equalsIgnoreCase(colorMateriales[i].toString())) 
				{
					Coincidencias++;
				}
			}
			
			if(Coincidencias == 0) {
				System.out.println("Voy a borrar id: " + dmt.getIdMaterialTela());
				repository.deleteById(dmt.getIdMaterialTela());
			}
		}
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioMaterialTela findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Object[]> findAllById(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT "+
		"adl.id_lookup,dmt.id_tela,adl.nombre_lookup,dmt.color,dmt.codigo_color,dmt.posicion "+
	"FROM "+
		"alt_disenio_lookup adl "+
		"INNER JOIN alt_disenio_material_tela dmt on dmt.id_tipo_material=adl.id_lookup "+
	"WHERE "+
		"tipo_lookup = 'Material' "+
		"and dmt.id_tela="+id+" order by adl.nombre_lookup").getResultList();
	}
}
