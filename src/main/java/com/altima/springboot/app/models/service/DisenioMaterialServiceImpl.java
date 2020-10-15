
package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
//import com.altima.springboot.app.models.entity.DisenioProceso;
import com.altima.springboot.app.repository.DisenioMaterialRepository;

@Service
public class DisenioMaterialServiceImpl implements IDisenioMaterialService {
	@Autowired
	private DisenioMaterialRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional(readOnly = true)
	public List<DisenioMaterial> findAll() {
		// TODO Auto-generated method stub
		return (List<DisenioMaterial>) repository.findAll();
	}
	
	@Override
	@Transactional
	public void save(DisenioMaterial diseniomaterial) throws DataIntegrityViolationException {
		// TODO Auto-generated method stub
		repository.save(diseniomaterial);
		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	@Transactional
	public DisenioMaterial findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findListaLookupMat() {
		
		return em.createQuery(
				"SELECT idLookup, nombreLookup, tipoLookup FROM DisenioLookup WHERE tipoLookup= 'Material' and   estatus=1 order by nombreLookup")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findListaLookupMed() {
		
		return em.createQuery(
				"SELECT idLookup, nombreLookup, tipoLookup FROM DisenioLookup WHERE tipoLookup= 'Unidad Medida' and   estatus=1 order by nombreLookup")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findListaMarcas() {
		
		return em.createQuery(
				"SELECT idLookup, nombreLookup, tipoLookup FROM DisenioLookup WHERE tipoLookup= 'Marca' and   estatus=1 order by nombreLookup")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findListaClasificacion() {
		
		return em.createQuery(
				"SELECT idLookup, nombreLookup, tipoLookup FROM DisenioLookup WHERE tipoLookup= 'Clasificacion' and   estatus=1 order by nombreLookup")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> disenioMaterial() {
		
		List<Object[]> mv;
		mv = em.createNativeQuery("{call  alt_pr_materiales}").getResultList();
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DisenioLookup> findLookUps() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT * FROM alt_disenio_lookup WHERE tipo_lookup = 'Pieza Trazo' AND estatus = 1 ;")
				.getResultList();
	}
	
	@Override
	public Object findLookUp(Long id) {
		// TODO Auto-generated method stubString
		return (Object) em
				.createNativeQuery("SELECT * FROM alt_disenio_lookup WHERE tipo_lookup = 'Pieza Trazo' AND id_lookup = " + id
						+ " order by nombre_lookup")
				.getSingleResult();
	}
	
	@Override
	public Object findUno(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("call alt_pr_onematerial(" + id + ");").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DisenioMaterial> findAllForCreate() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT adm.id_material,adm.nombre_material FROM alt_disenio_material adm LEFT JOIN alt_disenio_calidad adc on adc.id_material=adm.id_material AND adc.tipo_material=2 AND (adc.estatus=2 OR adm.calidad=0) where adm.estatus=1").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findListaColor() {
		
		return em.createQuery(
				"SELECT idLookup, nombreLookup, tipoLookup,atributo1 FROM DisenioLookup WHERE tipoLookup= 'Color' and   estatus=1 order by nombreLookup")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DisenioLookup> findListaLookupPro() {
		
		return em.createQuery(
				"SELECT idLookup, nombreLookup, tipoLookup FROM DisenioLookup WHERE tipoLookup= 'Proceso' and   estatus=1 order by nombreLookup")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DisenioMaterial> findAllFromPrenda(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("call alt_pr_materials_from_prenda(" + id + ");").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DisenioLookup> findAllPatronajeFromPrenda(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("call alt_pr_patronajes_from_prenda(" + id + ");").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DisenioLookup> findAllFamiliaPrenda() {
		// TODO Auto-generated method stub
		return em.createQuery(
				"SELECT idLookup, nombreLookup, tipoLookup, atributo1 FROM DisenioLookup WHERE tipoLookup= 'Familia Prenda' and   estatus=1 order by nombreLookup")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findAllByTipoMaterial(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT idMaterial, nombreMaterial FROM DisenioMaterial WHERE idTipoMaterial=" + id
				+ " AND estatus = 1 order by nombreMaterial").getResultList();
	}
	
	@Override
	public Object findByIdMaterial(Long id) {
		// TODO Auto-generated method stub
		return em.createNativeQuery(
				"SELECT adm.id_text, adm.nombre_material, adl5.nombre_lookup nl6, ifnull(adl6.nombre_lookup,'Sin color') nl1, adm.tamanio, adl2.nombre_lookup nl2, adm.modelo, adl1.nombre_lookup nl3, adm.precio_unitario, adl4.nombre_lookup nl4, adl3.nombre_lookup nl5, adm.descripcion_material, ifnull(adl6.atributo_1,'#ffffff'), adm.imagen, adm.id_text_prospecto, adm.estatus_material from alt_disenio_material adm INNER JOIN alt_disenio_lookup adl1 on adm.id_proceso=adl1.id_lookup and adl1.tipo_lookup='Proceso' INNER JOIN alt_disenio_lookup adl2 on adm.id_unidad_medida=adl2.id_lookup and adl2.tipo_lookup='Unidad Medida' LEFT JOIN alt_disenio_lookup adl3 on adm.id_marca=adl3.id_lookup and adl3.tipo_lookup='Marca' INNER JOIN alt_disenio_lookup adl4 on adm.id_clasificacion=adl4.id_lookup and adl4.tipo_lookup='Clasificación' INNER JOIN alt_disenio_lookup adl5 on adm.id_tipo_material=adl5.id_lookup and adl5.tipo_lookup='Material' LEFT JOIN alt_disenio_lookup adl6 on adm.id_color=adl6.id_lookup and adl6.tipo_lookup='Color' where adm.id_material="+ id)
				.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findLastMaterial(Long tipo_material) {
		
		return em.createNativeQuery("call alt_idtextprospecto_material('" + tipo_material + "');").getResultList();
		
	}
	
	
	
	@Override
	@Transactional
	public int count(Long id) {
		// TODO Auto-generated method stub
		String auxs = em.createNativeQuery ("SELECT COUNT(*) FROM alt_disenio_material WHERE id_tipo_material =" + id).getSingleResult().toString();
		int aux = Integer.parseInt(auxs);	
		return aux ;
	}




	@Override
	@Transactional
	public int count2(Long id) {
		// TODO Auto-generated method stub
		String auxs = em.createNativeQuery ("SELECT COUNT(*) FROM alt_disenio_material WHERE id_tipo_material =" + id+" and estatus_material=1 ").getSingleResult().toString();
		int aux = Integer.parseInt(auxs);	
		return aux + 1;
	}



	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String findunique(Long id) {

		return em.createNativeQuery("SELECT DISTINCT nombre_lookup from alt_disenio_material dm INNER JOIN alt_disenio_lookup dl on dm.id_tipo_material = dl.id_lookup WHERE dm.id_tipo_material="+ id).getResultList().toString();

	}
	
	@Override
	@Transactional
	public DisenioMaterial findMaterialPorId(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
	
	
	@Transactional
	@Override	
	public String Exist2(String comparacion) {
		
		String val =em.createNativeQuery("SELECT if ((SELECT COUNT(*) FROM alt_disenio_material WHERE nombre_material='"+comparacion+"')>0, 1 , 0);").getSingleResult().toString();
		return val;
	}

	
	
	@Transactional
	@Override	
	public String Exist3(Long id) {
		
		String val =em.createNativeQuery("SELECT if ((SELECT COUNT(*)  FROM alt_disenio_material WHERE id_material="+id+")>0, 1 , 0);").getSingleResult().toString();
		return val;
	}
	
	//Metodo para contar cuantas telas activas existen
	@Override
	public int countTelasActivas() {
		// TODO Auto-generated method stub
		String count = em.createNativeQuery ("SELECT COUNT(*) FROM alt_disenio_tela WHERE estatus_tela = 1;").getSingleResult().toString();
		return Integer.parseInt(count);	
	}

	@Override
	public int countForrosActivos() {
		// TODO Auto-generated method stub
		String count = em.createNativeQuery ("SELECT COUNT(*) FROM alt_disenio_forro WHERE estatus_forro = 1;").getSingleResult().toString();
		return Integer.parseInt(count);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMaterialByTipo(Long idTipoMaterial, Long idMaterial) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("SELECT adm.id_material,adm.nombre_material FROM alt_disenio_material adm LEFT JOIN alt_disenio_calidad adc on adc.id_material=adm.id_material AND adc.tipo_material=2 AND (adc.estatus=2 OR adm.calidad=0) INNER JOIN alt_disenio_lookup adl on adl.id_lookup=adm.id_tipo_material AND adl.tipo_lookup='Material' and adl.id_lookup="+idTipoMaterial+" where adm.id_material<>"+idMaterial+" AND adm.estatus=1").getResultList();
	}

	@Override
	@Transactional
	public Integer disponibles (Long id) {
		System.out.println("SELECT "+
		" IF (SUM( multi.existencia ) IS NULL,0,SUM( multi.existencia)) \r\n" + 
		" FROM "+
		" alt_amp_multialmacen as multi "+
		" WHERE "+
		" 1=1 "+
		" AND multi.id_articulo = "+id+
		" AND multi.tipo='material'");
		String re = em.createNativeQuery("SELECT "+
		" IF (SUM( multi.existencia ) IS NULL,0,SUM( multi.existencia)) \r\n" + 
		" FROM "+
		" alt_amp_multialmacen as multi "+
		" WHERE "+
		" 1=1 "+
		" AND multi.id_articulo = "+id+
		" AND multi.tipo='material'")
				.getSingleResult().toString();

		if (re.isEmpty() || re== null) {
			return 0;
		} else {
			double d = Double.parseDouble(re);
			return (int) d;
		}

	}
}
