package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialClienteFactura;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IHrDireccionService;
import com.altima.springboot.app.models.service.IUploadService;
import com.altima.springboot.app.models.service.IUsuarioService;

@ComponentScan(basePackages = "com.altima.springboot.app.component")
@Controller
public class ClienteController {
	@Autowired
	private IComercialClienteService ClienteService;
	@Autowired
	private IHrDireccionService DireccionService;
	@Autowired
	private IUploadService UploadService;
	@Autowired
	IUsuarioService usuarioService;
	@Autowired
	AuthComponent currentuserid;

	protected final Log logger = LogFactory.getLog(this.getClass());

	@GetMapping("/clientes")
	public String listClients(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/* Obtener todos los datos del usuario logeado */
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		String role = "[ROLE_ADMINISTRADOR]";
		if (auth.getAuthorities().toString().equals(role)) {
			model.addAttribute("clientes", ClienteService.findAll(null));
			model.addAttribute("agentes", ClienteService.findAllAgentes());
		} else {
			model.addAttribute("clientes", ClienteService.findAll(iduser));
		}

		return "clientes";
	}

	@GetMapping("/agregar-cliente")
	public String crearCliente(Map<String, Object> model) {
		ComercialCliente cliente = new ComercialCliente();
		HrDireccion direccion = new HrDireccion();
		model.put("cliente", cliente);
		model.put("direccion", direccion);
		model.put("subtitulo", "Nuevo Cliente");
		return "agregar-cliente";
	}

	@RequestMapping(value = "/agentes-clientes", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listarclientesagentes(Long Idcliente, Model model) {
		// model.addAttribute("dfa", usuarioService.FindClienteProspecto(Idcliente));
		return usuarioService.FindClienteProspecto(Idcliente);
	}

	@RequestMapping(value = "/agentes-clientes1", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listarclientesagentes1(Long idagente, Model model) {
		// model.addAttribute("dfa", usuarioService.FindClienteProspecto(Idcliente));
		return usuarioService.FindClienteProspectoAgente(idagente);
	}

	@PostMapping("/guardar-cliente")
	public String guardarCliente(ComercialCliente cliente, HrDireccion direccion, RedirectAttributes redirectAttrs,
			@RequestParam(value = "imagenCliente", required = false) MultipartFile imagenCliente) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		if (cliente.getIdCliente() == null && direccion.getIdDireccion() == null) {
			direccion.setEstatus(1);
			if (direccion.getNumeroExt() == null || direccion.getNumeroExt().isEmpty()) {
				direccion.setNumeroExt("S/N");
			}
			direccion.setUltimaFechaModificacion(hourdateFormat.format(date));
			direccion.setFechaCreacion(hourdateFormat.format(date));
			DireccionService.save(direccion);
			direccion.setIdText("DIR" + direccion.getIdDireccion());
			direccion.setCreadoPor(auth.getName());
			DireccionService.save(direccion);

			// Guardamos los datos de prospecto.
			cliente.setEstatusCliente(1);/////////// 1 para prospecto
			cliente.setEstatusC(1);
			cliente.setCfechaCreacion(hourdateFormat.format(date));
			cliente.setCultimaFechaModificacion(hourdateFormat.format(date));

			cliente.setIdUsuario(currentuserid.currentuserid());
			ClienteService.save(cliente);

			/*
			 * Integer contador = ClienteService.Contador(cliente.getTipoCliente());
			 * 
			 * if ( cliente.getTipoCliente().equals("1")) { cliente.setCidText("CLTEM"
			 * +(contador+100)); } if ( cliente.getTipoCliente().equals("2")) {
			 * cliente.setCidText("CLTEF" +(contador+100)); }
			 */

			cliente.setCidText("PROSP" + (cliente.getIdCliente()));

			cliente.setCcreadoPor(auth.getName());
			cliente.setIdDireccion(direccion.getIdDireccion());

			if (!imagenCliente.isEmpty()) {
				if (cliente.getImagen() != null && cliente.getImagen().length() > 0) {
					UploadService.deleteForro(cliente.getImagen());
				}
				String uniqueFilename = null;
				try {
					uniqueFilename = UploadService.copyCliente(imagenCliente);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cliente.setImagen(uniqueFilename);
			}

			ClienteService.save(cliente);
			redirectAttrs.addFlashAttribute("title", "Cliente guardado correctamente").addFlashAttribute("icon",
					"success");
		} else {
			if (direccion.getNumeroExt() == null || direccion.getNumeroExt().isEmpty()) {
				direccion.setNumeroExt("S/N");
			}
			direccion.setActualizadoPor(auth.getName());
			direccion.setUltimaFechaModificacion(hourdateFormat.format(date));
			cliente.setCactualizadoPor(auth.getName());
			cliente.setCultimaFechaModificacion(hourdateFormat.format(date));
			cliente.setIdUsuario(currentuserid.currentuserid());
			if (!imagenCliente.isEmpty()) {
				if (cliente.getImagen() != null && cliente.getImagen().length() > 0) {
					UploadService.deleteForro(cliente.getImagen());
				}
				String uniqueFilename = null;
				try {
					uniqueFilename = UploadService.copyCliente(imagenCliente);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cliente.setImagen(uniqueFilename);
			}
			DireccionService.save(direccion);
			ClienteService.save(cliente);
			redirectAttrs.addFlashAttribute("title", "Cliente editado correctamente").addFlashAttribute("icon",
					"success");
		}

		return "redirect:clientes";
	}

	@PostMapping("/asignar-agente")
	public String asignaragente(Long idcliente, Long idagente) {
		ComercialCliente cliente = ClienteService.findOne(idcliente);
		cliente.setIdUsuario(idagente);
		ClienteService.save(cliente);
		return "redirect:/clientes";
	}

	@PostMapping("/convertir-cliente")
	public String convertircliente(Long idcliente) {
		ComercialCliente cliente = ClienteService.findOne(idcliente);
		cliente.setCidText("CLTE" + idcliente);
		cliente.setEstatusCliente(0);
		ClienteService.save(cliente);
		return "redirect:/clientes";
	}

	/*
	 * este componente(@authComponent) funciona mandando el id del registro como
	 * parametro para hacer un findone y obtener el id del usuario de registro y el
	 * id del usuario de la sesion actual para asi compararlos y aprobar o denegar
	 * el acceso a editar cierto registro
	 */
	@PreAuthorize("@authComponent.hasPermission(#cliente.IdCliente)")
	@GetMapping("/editar-cliente/{idCliente}")
	public String editar(Map<String, Object> model, ComercialCliente cliente) {
		HrDireccion direccion;
		cliente = ClienteService.findOne(cliente.getIdCliente());
		direccion = DireccionService.findOne(cliente.getIdDireccion());
		model.put("cliente", cliente);
		model.put("direccion", direccion);
		model.put("estatus", Integer.parseInt(cliente.getTipoCliente()));
		model.put("subtitulo", "Editar Cliente");
		return "agregar-cliente";
	}

	@GetMapping("delete-cliente/{id}")
	public String deleteMaterial(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		ComercialCliente cliente = ClienteService.findOne(id);
		cliente.setEstatusC(0);
		cliente.setCactualizadoPor(auth.getName());
		cliente.setCultimaFechaModificacion(hourdateFormat.format(date));
		ClienteService.save(cliente);
		redirectAttrs.addFlashAttribute("title", "Cliente eliminado correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/clientes";
	}

	@GetMapping("alta-cliente/{id}")
	public String altaMaterial(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialCliente cliente = ClienteService.findOne(id);
		cliente.setEstatusC(1);
		cliente.setCactualizadoPor(auth.getName());
		cliente.setCultimaFechaModificacion(hourdateFormat.format(date));
		ClienteService.save(cliente);
		redirectAttrs.addFlashAttribute("title", "Cliente dado de alta correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/clientes";
	}

	@GetMapping(value = "/uploads/clientes/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = UploadService.loadCliente(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping("/facturacion-clientes/{id}")
	public String listClients(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes redirectAttrs) {
		ComercialCliente cliente = null;
		cliente = ClienteService.findOne(id);
		model.put("cliente", cliente);
		model.put("facturas", ClienteService.ListaFactura(id));
		return "facturacion";
	}

	@GetMapping("/agregar-facturacion/{id}")
	public String crearCliente(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		ComercialCliente cliente = null;
		cliente = ClienteService.findOne(id);
		ComercialClienteFactura factura = new ComercialClienteFactura();
		HrDireccion direccion = new HrDireccion();
		model.put("factura", factura);
		model.put("cliente", cliente);
		model.put("direccion", direccion);
		model.put("subtitulo", "Nueva");
		return "facturacion-clientes";
	}

	@GetMapping("/guardar-facturacion")
	public String guardarCliente(ComercialClienteFactura factura, HrDireccion direccion,
			RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("Hola soy  facturacion");

		if (factura.getIdClienteFacturaF() == null && direccion.getIdDireccion() == null) {
			if (direccion.getNumeroExt() == null || direccion.getNumeroExt().isEmpty()) {
				direccion.setNumeroExt("S/N");
			}
			direccion.setFechaCreacion(hourdateFormat.format(date));
			direccion.setUltimaFechaModificacion(hourdateFormat.format(date));
			direccion.setCreadoPor(auth.getName());
			direccion.setEstatus(1);
			DireccionService.save(direccion);
			direccion.setIdText("DIR" + direccion.getIdDireccion());
			DireccionService.save(direccion);
			// Guardamos los datos de la facturacion ClienteService.save(cliente);

			factura.setCreadoPorF(auth.getName());
			factura.setFechaCreacionF(hourdateFormat.format(date));
			factura.setUltimaFechaModificacionF(hourdateFormat.format(date));
			factura.setIdDireccionF(direccion.getIdDireccion());
			factura.setEstatusF("1");
			ClienteService.saveFactura(factura);
			Integer contador = ClienteService.ContadorFacturas(factura.getIdClienteF());
			factura.setIdTextF("FAC" + contador);
			ClienteService.saveFactura(factura);
			redirectAttrs.addFlashAttribute("title", "Facturacion guardada correctamente").addFlashAttribute("icon",
					"success");

		} else {
			if (direccion.getNumeroExt() == null || direccion.getNumeroExt().isEmpty()) {
				direccion.setNumeroExt("S/N");
			}
			direccion.setActualizadoPor(auth.getName());
			direccion.setUltimaFechaModificacion(hourdateFormat.format(date));
			factura.setIdDireccionF(direccion.getIdDireccion());
			factura.setActualizadoPorF(auth.getName());
			factura.setUltimaFechaModificacionF(hourdateFormat.format(date));
			DireccionService.save(direccion);
			ClienteService.saveFactura(factura);
			redirectAttrs.addFlashAttribute("title", "Facturacion editada correctamente").addFlashAttribute("icon",
					"success");
		}

		return "redirect:facturacion-clientes/" + factura.getIdClienteF();
	}

	@GetMapping("/editar-facturacion/{id}")
	public String editar_facturacion(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		ComercialClienteFactura factura = null;
		HrDireccion direccion;
		ComercialCliente cliente = null;
		factura = ClienteService.findOneFactura(id);
		direccion = DireccionService.findOne(factura.getIdDireccionF());
		cliente = ClienteService.findOne(factura.getIdClienteF());
		model.put("factura", factura);
		model.put("direccion", direccion);
		model.put("cliente", cliente);
		model.put("subtitulo", "Editar Factura");
		return "facturacion-clientes";
	}

	@GetMapping("delete-facturacion/{id}")
	public String delete_facturacion(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialClienteFactura factura = null;
		factura = ClienteService.findOneFactura(id);
		factura.setEstatusF("0");
		factura.setUltimaFechaModificacionF(hourdateFormat.format(date));
		factura.setActualizadoPorF(auth.getName());
		ClienteService.saveFactura(factura);

		redirectAttrs.addFlashAttribute("title", "Facturacion elimnada correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/facturacion-clientes/" + factura.getIdClienteF();
	}

	@GetMapping("alta-facturacion/{id}")
	public String alta_facturacion(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialClienteFactura factura = null;
		factura = ClienteService.findOneFactura(id);
		factura.setEstatusF("1");
		factura.setUltimaFechaModificacionF(hourdateFormat.format(date));
		factura.setActualizadoPorF(auth.getName());
		ClienteService.saveFactura(factura);

		redirectAttrs.addFlashAttribute("title", "Facturacion se dio alta correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/facturacion-clientes/" + factura.getIdClienteF();
	}

}
