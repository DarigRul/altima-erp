package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.Rol;
import com.altima.springboot.app.repository.RolRepository;
@Service
public class RolServiceImpl implements IRolService {

	@Autowired	
	EntityManager em;
	@Autowired	
	private RolRepository repository;
	
	@Override
	public List<Rol> findAll() {
		// TODO Auto-generated method stub
		return (List<Rol>) repository.findAll();
	}

	@Override
	public Rol findOne(Long id_rol) {
		// TODO Auto-generated method stub
		return repository.findById(id_rol).orElse(null);
	}

	@Override
	public void save(Rol rol) {
		// TODO Auto-generated method stub
		repository.save(rol);
	}

	@Override
	public void delete(Long id_rol) {
		// TODO Auto-generated method stub
		repository.deleteById(id_rol);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList findBySeccionRol(String seccion) {
		// TODO Auto-generated method stub
		ArrayList secciones =new ArrayList();
		List<String> seccionNombre=new ArrayList<String>();
		List<String> seccionId=new ArrayList<String>();
		for(Rol sec: repository.findBySeccionRol(seccion)) {
			seccionNombre.add(sec.getPermisoRol());
			seccionId.add(sec.getIdRol().toString());
		}
		secciones.add(seccionNombre);
		secciones.add(seccionId);
		return secciones;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Rol> FindByDepartamento (){
		return em.createQuery("SELECT DISTINCT departamentoRol FROM Rol \r\n" + 
				"WHERE nombreRol!='ADMINISTRADOR' \r\n" +
				"AND permisoRol!=''" +
				 "ORDER BY departamentoRol").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Rol> FindBySeccion (){
		return em.createQuery("SELECT DISTINCT departamentoRol, seccionRol FROM Rol \r\n" + 
				"WHERE nombreRol!='ADMINISTRADOR'\r\n" + 
				"AND permisoRol!=''").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Rol> FindByPermiso(String Departamento, String seccion){
		return em.createQuery("SELECT DISTINCT idRol, permisoRol FROM Rol \r\n" + 
				"WHERE departamentoRol='"+Departamento+"' AND seccionRol='"+seccion+"'").getResultList(); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> FindOneByDates(Long id){
		return  em.createNativeQuery("SELECT DISTINCT rol.id_rol, rol.seccion_rol, rol.permiso_rol from alt_hr_rol rol,\r\n" + 
									"(SELECT DISTINCT roles.departamento_rol, roles.seccion_rol  FROM alt_hr_usuario_rol AS roluser\r\n" + 
									"				INNER JOIN alt_hr_rol roles ON roluser.id_rol = roles.id_rol\r\n" + 
									"				WHERE roluser.id_usuario ="+id+") AS secciones\r\n" + 
									"	WHERE rol.departamento_rol = secciones.departamento_rol\r\n" + 
									"	AND rol.seccion_rol = secciones.seccion_rol").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> FindRolesGenerales(){
		return  em.createNativeQuery("SELECT id_rol, departamento_rol, seccion_rol FROM alt_hr_rol\r\n" + 
									 "	WHERE permiso_rol = ''\r\n" + 
									 "	AND nombre_rol != 'ADMINISTRADOR'").getResultList();
	}
	
	
}
