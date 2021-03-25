package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.altima.springboot.app.models.entity.ServicioClienteObservacionProceso;
import com.altima.springboot.app.repository.ServicioClienteObservacionProcesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioClienteObservacionProcesoServiceIml implements IServicioClienteObservacionProcesoService{

    @PersistenceContext
	private EntityManager em;

	@Autowired
	private ServicioClienteObservacionProcesoRepository repository;
    
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view(Long iduser) {
        List<Object[]> re = null;
        re = em.createNativeQuery(""+
            "SELECT\r\n" +
                "obser.id_observacion_proceso,\r\n" +
                "obser.id_text,\r\n" +
                "usuario.nombre_usuario,\r\n" +
                "lookup.nombre_lookup,\r\n" +
                "obser.observacion,\r\n" +
                "obser.creado_por,\r\n" +
                "obser.fecha_creacion,\r\n" +
                "IFNULL( obser.actualizado_por, '' ),\r\n" +
                "IFNULL( obser.ultima_fecha_modificacion, '' )\r\n" +
            "FROM\r\n" +
                "alt_servicio_cliente_observacion_proceso AS obser\r\n" +
                "INNER JOIN alt_servicio_cliente_lookup lookup ON lookup.id_lookup = obser.id_proceso\r\n" +
                "INNER JOIN alt_hr_usuario usuario ON usuario.id_usuario = obser.id_usuario\r\n" +
                "WHERE\r\n" +
                "1=1\r\n" +
                "AND if("+iduser+"=1, obser.id_usuario>0, obser.id_usuario="+iduser+")").getResultList();

		return re;
    }
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Object []> fullUser(){
        List<Object[]> re = null;
        re= em.createNativeQuery(""+
            "SELECT\r\n" +
                "usuario.id_usuario,\r\n" +
                "usuario.nombre_usuario\r\n" +
            "FROM\r\n" +
                "alt_hr_usuario AS usuario\r\n" +
            "WHERE\r\n" +
                "1 = 1\r\n" +
                "AND usuario.estatus = 1\r\n" +
                "AND usuario.id_empleado >=1\r\n" +
            "ORDER BY\r\n" +
                "usuario.nombre_usuario").getResultList();

        return re;
    }
    @Override
    public ServicioClienteObservacionProceso findOne(Long id) {
        // TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
    }

    @Override
    public void save(ServicioClienteObservacionProceso obj) {
        repository.save(obj);
        
    }
    @Override
    public boolean validarDuplicadoIdIsNull(Long idUser, Long idProceso, String observacion) {
		try {
            ServicioClienteObservacionProceso obj= (ServicioClienteObservacionProceso) em.createQuery("from ServicioClienteObservacionProceso where id_proceso="+idProceso+" and observacion ="+observacion+" and id_usuario ="+idUser).getSingleResult();
			return  true;
		}
		catch(Exception e) {
			return false;
		}
      
    }
    @Override
    public boolean validarDuplicadoIdIsNotNul(Long idUser, Long idProceso, String observacion,Long idObservacion) {
        try {
            ServicioClienteObservacionProceso obj= (ServicioClienteObservacionProceso) em.createQuery("from ServicioClienteObservacionProceso where id_proceso="+idProceso+" and observacion ="+observacion+" and id_usuario ="+idUser+" and id_observacion_proceso != "+idObservacion).getSingleResult();
			return  true;
		}
		catch(Exception e) {
			return false;
		}
    }
    
}
