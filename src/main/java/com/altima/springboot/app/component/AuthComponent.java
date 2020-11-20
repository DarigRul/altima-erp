package com.altima.springboot.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IUsuarioService;

@Component
public class AuthComponent {
	@Autowired
	IUsuarioService usuarioService;
	@Autowired
	private IComercialClienteService ClienteService;
	@Autowired
	private ICargaPedidoService cargaPedidoService;

	public boolean hasPermission(Long id, String url) {
		System.out.println(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean respuesta = false;
		switch (url) {
		case "editar-cliente":
			System.out.println("aquientro");
			ComercialCliente cl = ClienteService.findOne(id);
			Long idusercliente = cl.getIdUsuario();
			currentuserid();
			if (idusercliente == currentuserid() || auth.getAuthorities().toString().contains("ROLE_ADMINISTRADOR")) {
				respuesta = true;
			} else {
				respuesta = false;
			}
			break;
		case "pedido":
			ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
			Long iduserpedido = pedido.getIdUsuario();
			currentuserid();
			if (iduserpedido == currentuserid() && pedido.getEstatus().equals("1") || auth.getAuthorities().toString().contains("ROLE_ADMINISTRADOR") && pedido.getEstatus().equals("1")) {
				respuesta = true;
			} else {
				respuesta = false;
			}
			break;
		case  "cambio-prenda":
			ComercialPedidoInformacion pedidoCierre = cargaPedidoService.findOne(id);
			if (pedidoCierre.getEstatus().equals("3") && auth.getAuthorities().toString().contains("ROLE_COMERCIAL_SOLICITUD_CAMBIO_PRENDA_GERENCIA")  || auth.getAuthorities().toString().contains("ROLE_ADMINISTRADOR") && pedidoCierre.getEstatus().equals("3")) {
				respuesta = true;
			} else {
				respuesta = false;
			}
			
			break;
			
		case  "listar-cambio-prenda":
			if (auth.getAuthorities().toString().contains("ROLE_COMERCIAL_SOLICITUD_CAMBIO_PRENDA_GERENCIA")  || auth.getAuthorities().toString().contains("ROLE_ADMINISTRADOR")) {
				respuesta = true;
			} else {
				respuesta = false;
			}
			
			break;
			

		default:
			break;
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

	public long currentemployeeid() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/* Obtener todos los datos del usuario logeado */
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long idemployee = user.getIdEmpleado();
		return idemployee;

	}
}