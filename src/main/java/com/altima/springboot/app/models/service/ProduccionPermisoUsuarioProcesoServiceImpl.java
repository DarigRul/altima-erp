package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.ProduccionPermisoUsuarioProceso;
import com.altima.springboot.app.repository.ProduccionPermisoUsuarioProcesoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduccionPermisoUsuarioProcesoServiceImpl implements IProduccionPermisoUsuarioProcesoService{

    @PersistenceContext
	private EntityManager em;

	@Autowired
	private ProduccionPermisoUsuarioProcesoRepository repository;

	@Override
	@Transactional
	public void save(ProduccionPermisoUsuarioProceso permiso) {
		repository.save(permiso);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	@Transactional
	public ProduccionPermisoUsuarioProceso findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> SelectUsuariosDisponibles(Long idProceso) {
		return em.createNativeQuery(""+
            "SELECT\r\n"+
                "usuario.id_usuario,\r\n"+
                "CONCAT(empleado.nombre_persona ,' ',empleado.apellido_paterno)\r\n"+
            "FROM\r\n"+
                "alt_hr_usuario as usuario\r\n"+
                "INNER JOIN  alt_hr_empleado  empleado on usuario.id_empleado = empleado.id_empleado\r\n"+
                "INNER JOIN alt_hr_puesto puesto    on empleado.id_puesto = puesto.id_puesto\r\n"+
                "INNER JOIN alt_hr_departamento  depa    on  puesto.id_departamento = depa.id_departamento\r\n"+
                "INNER JOIN alt_hr_lookup look on look.id_lookup=depa.id_area\r\n"+
            "WHERE\r\n"+
                "1=1\r\n"+
                "AND look.nombre_lookup= 'PRODUCCION'\r\n"+
                "AND usuario.estatus=1\r\n"+
                "AND usuario.id_usuario not in (SELECT id_usuario from alt_produccion_permiso_usuario_proceso as permisos WHERE id_proceso="+idProceso+")\r\n"+
                "ORDER BY CONCAT(empleado.nombre_persona ,' ',empleado.apellido_paterno)").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> view(Long idProceso) {
        return em.createNativeQuery(""+
            "SELECT\r\n"+
                "permiso.id_permiso,\r\n"+
                "CONCAT( empleado.nombre_persona, ' ', empleado.apellido_paterno )\r\n"+
            "FROM\r\n"+
                "alt_produccion_permiso_usuario_proceso AS permiso\r\n"+
                "INNER JOIN alt_hr_usuario usuario ON permiso.id_usuario = usuario.id_usuario\r\n"+
                "INNER JOIN alt_hr_empleado empleado ON usuario.id_empleado = empleado.id_empleado\r\n"+
            "WHERE\r\n"+
                "1 = 1\r\n"+
                "AND permiso.id_proceso = "+idProceso).getResultList();
	}

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> listarProcesosDisponiblesUser(Long idUser) {
        return em.createNativeQuery(""+
            "SELECT\r\n"+
                "look.id_lookup,\r\n"+
                "CONCAT(look.nombre_lookup,' ',look.descripcion_lookup),\r\n"+
                "look.descripcion_lookup,\r\n"+
                "look.nombre_lookup\r\n"+
            "FROM\r\n"+
                "alt_produccion_lookup AS look\r\n"+
                "INNER JOIN alt_produccion_permiso_usuario_proceso AS permiso ON look.id_lookup = permiso.id_proceso\r\n"+
            "WHERE\r\n"+
                "1 = 1\r\n"+
                "AND look.tipo_lookup = 'Proceso'\r\n"+
                "AND permiso.id_usuario = "+idUser+"\r\n"+
                "AND look.estatus=1\r\n"+
                "GROUP BY look.id_lookup\r\n"+
                "ORDER BY look.nombre_lookup").getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> listarProcesosDisponiblesAdmin() {
        return em.createNativeQuery(""+
        "SELECT\r\n"+
            "look.id_lookup,\r\n"+
            "CONCAT(look.nombre_lookup,' ',look.descripcion_lookup),\r\n"+
            "look.descripcion_lookup,\r\n"+
            "look.nombre_lookup\r\n"+
        "FROM\r\n"+
            "alt_produccion_lookup AS look\r\n"+
        "WHERE\r\n"+
            "1 = 1\r\n"+
            "AND look.tipo_lookup = 'Proceso'\r\n"+
            "AND look.estatus=1\r\n"+
            "ORDER BY look.nombre_lookup").getResultList();
    }
    
}
