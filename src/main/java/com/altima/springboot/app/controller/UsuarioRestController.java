package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.dto.ChangePasswordForm;
import com.altima.springboot.app.models.entity.Rol;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IRolService;
import com.altima.springboot.app.models.service.IUsuarioService;

@RestController
public class UsuarioRestController {
	
	@Autowired
	IRolService rolService;
	@Autowired
	private IUsuarioService usuarioService;
	

	
	@RequestMapping (value="/listRoles", method=RequestMethod.GET)
	private List<Rol> listRoles(){
		
		return rolService.FindByDepartamento();
	}
	
	@RequestMapping (value="/listSecciones", method=RequestMethod.GET)
	private List<Rol> listSecciones(){
		
		return rolService.FindBySeccion();
	}

	@RequestMapping (value="/listPermisos", method=RequestMethod.GET)
	private List<Rol> listPermisos(@RequestParam(name = "departamento", required = false) String departamento,
			 					   @RequestParam(name = "seccion", required = false) String seccion){
		
		System.out.println(rolService.FindByPermiso(departamento, seccion));
		return rolService.FindByPermiso(departamento, seccion);
	}
	
	@RequestMapping(value="/guardarUser", method= RequestMethod.POST)
	private int guardarUser(@RequestParam(name = "DatosJson", required = false) String rol_value,
							 @RequestParam(name = "Empleado", required = false) String empleado,
							 @RequestParam(name = "NombreUser", required = false) String nombreUser,
							 @RequestParam(name = "Password", required = false) String password,
							 @RequestParam(name = "ConfirmPass", required = false) String confirmPass,
							 @RequestParam(name = "StatusUser", required = false) String statusUser,
							 @RequestParam(name = "Permisos", required = false)String permisos,
							 @RequestParam(name = "idUser", required = false)Long idUser,
							 Usuario usuario, ChangePasswordForm passwordForm,
							 RedirectAttributes redirectAttrs) {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		LocalDate localDate = LocalDate.now();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = localDate + " " + dateFormat.format(date);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String[] datosPermiso;
		JSONArray muest = new JSONArray(permisos);
		if (rol_value.equals("")) {

			if (usuario.getIdUsuario() == null) {
				redirectAttrs.addFlashAttribute("title", "Agrega al menos un permiso").addFlashAttribute("icon", "error");
				
			} else {
				redirectAttrs.addFlashAttribute("title", "Agrega al menos un permiso").addFlashAttribute("icon", "error");
			}
		}

// Condición para validar que no existe un usuario (agregar nuevo)
		if(idUser==null) {
			passwordForm.setNewPassword(password);
			passwordForm.setConfirmPassword(confirmPass);
			for (int i = 0; i < muest.length(); i++) {
					datosPermiso = muest.get(i).toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
					
					for (int p = 0; p < datosPermiso.length; p++) {
						System.out.println(datosPermiso);
						usuario.getRoles().add(rolService.findOne(Long.parseLong(datosPermiso[p])));
					}
			}
			
			try {
				
				usuario.setCreadoPor(auth.getName());
				usuario.setActualizadoPor(auth.getName());
				usuario.setFechaCreacion(formattedDate);
				usuario.setUltimaFechaModificacion(formattedDate);
				usuario.setNombreUsuario(nombreUser);
				usuario.setIdEmpleado(Long.parseLong(empleado));
				usuario.setEstatus(statusUser);
				usuario.setIdText("Id");
				usuario.setCreadoPor(auth.getName());
				usuarioService.save(usuario, passwordForm);
				usuario.setIdText("user_" + (1000 + usuario.getIdUsuario()));
				usuarioService.save(usuario, passwordForm);
				return Integer.parseInt(usuario.getIdUsuario().toString());
			} catch (Exception e) {
				redirectAttrs.addFlashAttribute("title", usuarioService.getMensajeError()).addFlashAttribute("icon", "error");
				e.printStackTrace();
				return 2;
			}finally {
				System.out.println("se terminó de crear");
			}
		}
		
		
// Condición para validar que ya existe un usuario (editar usuario)
		else {
			
			System.out.println("Si esta entrando al de editar");
			
			usuario = usuarioService.findOne(idUser);	
			usuario.removeRol(usuario.getRoles());
			passwordForm.setNewPassword(usuario.getContraseña());
			passwordForm.setConfirmPassword(usuario.getContraseña());			
			for (int i = 0; i < muest.length(); i++) {	
					datosPermiso = muest.get(i).toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
						for (int p = 0; p < datosPermiso.length; p++) {
								System.out.println(datosPermiso[p]);
								usuario.getRoles().add(rolService.findOne(Long.parseLong(datosPermiso[p])));
						}	
			}

			try {
				usuario.setActualizadoPor(auth.getName());
				usuario.setUltimaFechaModificacion(formattedDate);
				usuario.setNombreUsuario(nombreUser);
				usuario.setIdEmpleado(Long.parseLong(empleado));
				usuario.setEstatus(statusUser);
				usuarioService.save(usuario, passwordForm);
				return Integer.parseInt(usuario.getIdUsuario().toString());
			} catch (Exception e) {
				
				redirectAttrs.addFlashAttribute("title", usuarioService.getMensajeError()).addFlashAttribute("icon", "error");
				e.printStackTrace();
				return 2;
			}finally {
				System.out.println("se terminó de editar");
			}
		}
	}
}
