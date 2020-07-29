package com.altima.springboot.app.models.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.dto.ChangePasswordForm;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.Rol;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired	
	private UsuarioRepository usuario;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public String mensajeError;
	
	@Override
	@Transactional(readOnly=true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuario.findAll();
	}
	
	@Override
	@Transactional
	public void save(Usuario usuarioxd,ChangePasswordForm form) throws Exception{
		// TODO Auto-generated method stub

		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			mensajeError=("Nueva Contraseña y Confirmar Contraseña no coinciden.");
			throw new Exception("Nueva Contraseña y Confirmar Contraseña no coinciden.");
		}
		if(usuarioxd.getIdUsuario()==null) {
			String encodePassword = passwordEncoder.encode(form.getNewPassword());
			usuarioxd.setContraseña(encodePassword);
		}
		else {
			Usuario usuario = findOne(usuarioxd.getIdUsuario());
			mapUser(usuario,usuarioxd);
		}
		usuario.save(usuarioxd);
		}
	protected void mapUser(Usuario from, Usuario to) {
		//to.setNombreUsuario(from.getNombreUsuario());
		//to.setFirstName(from.getFirstName());
		//to.setLastName(from.getLastName());
		//to.setEmail(from.getEmail());
		//to.setIdEmpleado(from.getIdEmpleado());
		to.setContraseña(from.getContraseña());
		//to.setEstatus(from.getEstatus());
	}
	
	@Override
	@Transactional
	public void delete(Long id_usuario) {
		// TODO Auto-generated method stub
		usuario.deleteById(id_usuario);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario findOne(Long id_usuario) {
		// TODO Auto-generated method stub
		return usuario.findById(id_usuario).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findUserRol() {
		// TODO Auto-generated method stub
		return em.createNativeQuery("call alt_pr_usuarios").getResultList();
	}
	@Override
	public Usuario changePassword(ChangePasswordForm form) throws Exception {
		Usuario user  = findOne(form.getId());
		
		if( passwordEncoder.matches(form.getNewPassword(), user.getContraseña())) {
			mensajeError=("Nueva Contraseña debe ser diferente a la contraseña actual.");
			throw new Exception("Nueva Contraseña debe ser diferente a la contraseña actual.");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			mensajeError=("Nueva Contraseña y Confirmar Contraseña no coinciden.");
			throw new Exception("Nueva Contraseña y Confirmar Contraseña no coinciden.");
		}
		
		String encodePassword = passwordEncoder.encode(form.getNewPassword());
		user.setContraseña(encodePassword);
		return usuario.save(user);
	}
	@Override
	public String getMensajeError() {
		return mensajeError;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> FindRolesByUserId(Long id){
		return em.createNativeQuery("SELECT roles.nombre_rol, roles.departamento_rol, roles.seccion_rol FROM alt_hr_usuario_rol AS roluser\r\n" + 
										"INNER JOIN alt_hr_rol roles ON roluser.id_rol = roles.id_rol\r\n" + 
										"WHERE roluser.id_usuario ="+id+"\r\n" +
										"AND permiso_rol!=''" +
											"GROUP BY roles.seccion_rol\r\n" + 
											"ORDER BY roles.departamento_rol, roles.seccion_rol").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> FindPermisosByUserId(Long id){
		return em.createNativeQuery("SELECT roles.id_rol, roles.seccion_rol, roles.permiso_rol  FROM alt_hr_usuario_rol AS roluser \r\n" + 
				"INNER JOIN alt_hr_rol roles ON roluser.id_rol = roles.id_rol\r\n" + 
				"WHERE roluser.id_usuario ="+id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> FindClienteProspecto(Long idcliente){
		return em.createNativeQuery("SELECT cc.id_cliente,hu.id_usuario,he.nombre_persona,he.apellido_paterno,he.apellido_materno\n" + 
				"FROM `alt_hr_usuario` hu, alt_hr_empleado he,alt_comercial_cliente cc where hu.id_empleado = he.id_empleado and he.id_puesto=1 and hu.id_usuario=cc.id_usuario and cc.id_cliente="+idcliente+"\n" + 
				"").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> FindClienteProspectoAgente(Long idagente){
		return em.createNativeQuery("SELECT hu.id_usuario,he.nombre_persona,he.apellido_paterno,he.apellido_materno\n" + 
				"FROM `alt_hr_usuario` hu, alt_hr_empleado he where hu.id_empleado = he.id_empleado and he.id_puesto=1 and \n" + 
				"hu.id_usuario<>"+idagente+"").getResultList();
	}
	
	
	
	@Override
	@Transactional
	public Usuario FindAllUserAttributes(String username, Collection<? extends GrantedAuthority> rol){
		String role=rol.toString();
		role = role.replaceAll("\\[", "").replaceAll("\\]","").replaceAll(",", "',").replaceAll(" ", "'");

		try {		
			
			return (Usuario) em.createNativeQuery("SELECT\r\n" + 
					"    alt_hr_usuario.*\r\n" + 
					"FROM\r\n" + 
					"    alt_hr_usuario_rol,\r\n" + 
					"    alt_hr_usuario,\r\n" + 
					"    alt_hr_rol\r\n" + 
					"WHERE\r\n" + 
					"    alt_hr_rol.id_rol = alt_hr_usuario_rol.id_rol AND alt_hr_usuario.id_usuario = alt_hr_usuario_rol.id_usuario AND alt_hr_usuario.nombre_usuario ='"+username+"' AND alt_hr_rol.descripcion_rol IN('"+role+"');",Usuario.class).getSingleResult();
			
		
			}
			catch(Exception e) {
				
				return null;
			
			}
		
		
	}

}
