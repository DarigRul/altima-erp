package com.altima.springboot.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IUsuarioService;

@Component
public class AuthComponent {
	@Autowired
	IUsuarioService usuarioService;
	@Autowired
	private IComercialClienteService ClienteService;

	public boolean hasPermission(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean respuesta;
		ComercialCliente cl = ClienteService.findOne(id);
		Long idc = cl.getIdUsuario();
		currentuserid();
		if (idc == currentuserid() || auth.getAuthorities().toString().contains("ROLE_ADMINISTRADOR")) {
			respuesta = true;
		} else {
			respuesta = false;
		}
		return respuesta;
	}

	/* metodo para obtener el id del usuario logeado actualmente */
	public long currentuserid() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/* Obtener todos los datos del usuario logeado */
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		return iduser;
	}
}