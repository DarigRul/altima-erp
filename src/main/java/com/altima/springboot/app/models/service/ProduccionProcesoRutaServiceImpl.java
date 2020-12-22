package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altima.springboot.app.repository.ProduccionProcesoRutaRepository;
import com.altima.springboot.app.models.entity.ProduccionProcesoRuta;

@Service
public class ProduccionProcesoRutaServiceImpl implements IProduccionProcesoRutaService {

    @PersistenceContext
	private EntityManager em;

	@Autowired
	private ProduccionProcesoRutaRepository repository;

    @Override
    public List<ProduccionProcesoRuta> findAll() {
        // TODO Auto-generated method stub
        return (List<ProduccionProcesoRuta>) repository.findAll();
    }

    @Override
    public void save(ProduccionProcesoRuta RP) {
        repository.save(RP);

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }

    @Override
    public ProduccionProcesoRuta findOne(Long id) {
        return repository.findById(id).orElse(null);

    }

    @SuppressWarnings("unchecked")
	@Transactional
	public List<Object[]> MostrarProcesosRuta(Long id) {

        List<Object[]> re = em.createNativeQuery(""+
                "SELECT\r\n" + 
                "PR.id_ruta_proceso,\r\n" + 
                "look.nombre_lookup,\r\n" + 
                "look.descripcion_lookup,\r\n" + 
                "look.id_lookup \r\n" + 
            "FROM\r\n" + 
                "alt_produccion_lookup AS look,\r\n" + 
                "alt_produccion_proceso_ruta AS PR \r\n" + 
            "WHERE\r\n" + 
                "1 = 1 \r\n" + 
                "AND PR.id_lookup_proceso = look.id_lookup \r\n" + 
                "AND PR.estatus = 1 \r\n" + 
                "AND PR.id_lookup_ruta = "+id).getResultList();
		return re;
	}

    @Transactional(readOnly = true)
	@Override
    public boolean buscarProcesoRuta(Long idProceso, Long idRuta) {
        String re = em.createNativeQuery(" select COUNT(id_lookup_ruta) from alt_produccion_proceso_ruta where  " + " id_lookup_ruta="
        + idRuta + " " + "AND  id_lookup_proceso = " + idProceso).getSingleResult().toString();
        if ( re.equals("0")){
            return false;
        }else{
            return true;
        }
    }
    
    @Transactional(readOnly = true)
	@Override
    public boolean validarNombrerutaEditar(Long idLookup, String nombre) {
        String re = em.createNativeQuery(""+
            "SELECT \r\n" + 
            "COUNT(look.nombre_lookup)\r\n" + 
            "FROM \r\n" + 
            "alt_produccion_lookup as look \r\n" + 
           "WHERE\r\n" + 
            "1=1\r\n" + 
            "AND look.nombre_lookup = '"+nombre+"'\r\n" + 
            "AND look.tipo_lookup='Ruta'\r\n" + 
            "AND look.id_lookup != "+idLookup).getSingleResult().toString();
        if ( re.equals("0")){
            return false; // no existe
        }else{
            
            return true; // si existeS
        }
    }
    
    
}
