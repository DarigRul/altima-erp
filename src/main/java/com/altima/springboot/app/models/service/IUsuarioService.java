package com.altima.springboot.app.models.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.altima.springboot.app.dto.ChangePasswordForm;
import com.altima.springboot.app.models.entity.Rol;
import com.altima.springboot.app.models.entity.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll();
	
	public List<Object[]> findUserRol();

	public void save(Usuario usuario,ChangePasswordForm form) throws Exception;

	public void delete(Long id_usuario);

	public Usuario findOne(Long id_usuario);
	
	public String getMensajeError();
	
	Usuario changePassword(ChangePasswordForm form) throws Exception;

	List<Object[]> FindRolesByUserId(Long id);

	List<Object[]> FindPermisosByUserId(Long id);

	Usuario FindAllUserAttributes(String username, Collection<? extends GrantedAuthority> rol);

}
