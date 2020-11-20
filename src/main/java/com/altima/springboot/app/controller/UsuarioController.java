package com.altima.springboot.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.dto.ChangePasswordForm;
import com.altima.springboot.app.models.entity.Rol;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
import com.altima.springboot.app.models.service.IRolService;
import com.altima.springboot.app.models.service.IUsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private IRolService rolService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IHrEmpleadoService empleadoService;
	
	@GetMapping("/administracion_usuarios")
	public String login(Model model, RedirectAttributes flash) {
		model.addAttribute("usuarios", usuarioService.findUserRol());
		return "administracion_usuarios";
	}
	
	@GetMapping("/agregar_usuario")
	public String getUser(Model model, Map<String, Object> m) {
		model.addAttribute("empleados", empleadoService.findEmpleadoPersona());
		model.addAttribute("usuarios", usuarioService.findUserRol());
		model.addAttribute("RolesGenerales", rolService.FindRolesGenerales());
		return "agregar_usuario";
	}
		
	@GetMapping("/editar_usuario/{id}")
	public String patchUser(Model model, Map<String, Object> m, @PathVariable Long id, RedirectAttributes redirectAttrs) {
		List<String> roles = new ArrayList<String>();
		Usuario usuario = new Usuario();
		usuario = usuarioService.findOne(id);
		
		m.put("usuario", usuario.getNombreUsuario());
		m.put("passwordForm", new ChangePasswordForm(usuario.getIdUsuario()));
		

		model.addAttribute("dataRol",usuarioService.FindPermisosByUserId(id));
		model.addAttribute("empleados", empleadoService.findEmpleadoPersona());
		model.addAttribute("empleadoSelected", usuario.getIdEmpleado());
		model.addAttribute("statusUser", usuario.getEstatus());
		model.addAttribute("idUser", id);
		model.addAttribute("roles", usuarioService.FindRolesByUserId(id));
		model.addAttribute("permisos", rolService.FindOneByDates(id));
		model.addAttribute("RolesGenerales", rolService.FindRolesGenerales());
		
		return "agregar_usuario";
	}
	
	@PostMapping("/editar_contra")
	public String patchContraseña(Model model, ChangePasswordForm passwordForm, Errors errors,
			RedirectAttributes redirectAttrs) {
		
		try {
			usuarioService.changePassword(passwordForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			redirectAttrs.addFlashAttribute("title", usuarioService.getMensajeError()).addFlashAttribute("icon", "error");
			return "redirect:/editar_usuario/" + passwordForm.getId();
		}
		return "redirect:/administracion_usuarios";
	}
}