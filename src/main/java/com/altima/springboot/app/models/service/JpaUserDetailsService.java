package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.Rol;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.repository.UsuarioRepository;


@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	EntityManager em;

	
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	String x=null;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
        Usuario usuario = findBynombreUsuario(username);
        
        if(usuario == null) {
        	x="Usuario no encontrado";
        	logger.error("Usuario no encontrado");
        	throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for(Rol role: usuario.getRoles()) {
        	logger.info("Role: ".concat(role.getDescripcionRol()));
        	authorities.add(new SimpleGrantedAuthority(role.getDescripcionRol()));
        }
        if(usuario.getEstatus().equals("0")||usuario.getEstatus()==null) {
        	x="Usuario no activo";
        	logger.error("Usuario no activo");
        	throw new UsernameNotFoundException("Usuario no activo");
        }
        if(authorities.isEmpty()) {
        	x="Acceso denegado";
        	logger.error("Acceso denegado");
        	throw new UsernameNotFoundException("Acceso denegado");
        }
        x=null;
		return new User(usuario.getNombreUsuario(), usuario.getContraseña(), authorities);
	}
	
	protected Usuario findBynombreUsuario(String username) {
		return (Usuario) em.createQuery("from Usuario where nombreUsuario = '"+username+"'").getSingleResult();		
	}

	public String getX() {
		
		return x;
	}

}
