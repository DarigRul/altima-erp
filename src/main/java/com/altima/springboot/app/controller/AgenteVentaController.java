package com.altima.springboot.app.controller;

/*Realizando alguna prueba*/
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.altima.springboot.app.models.entity.ComercialCalendario;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialCalendarioService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialMovimientoService;
import com.altima.springboot.app.models.service.IUsuarioService;

@Controller
public class AgenteVentaController {
	@Autowired
	IComercialCalendarioService calendarioservice;

	@Autowired
	private IComercialClienteService clienteservice;

	@Autowired
	private ICargaPedidoService cargaPedidoService;

	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IComercialMovimientoService movimientoService;

	@GetMapping("/obtener-clientes")
	@ResponseBody
	public List<Integer> clientes() {
		List<Integer> list = new ArrayList<>();
		for (ComercialCliente cli : clienteservice.findAll(null)) {
			list.add(cli.getIdCliente().intValue());
		}
		return list;
	}

	@GetMapping("/llamadas-cliente")
	@ResponseBody
	public List<ComercialCalendario> llamadascliente(Long id) {
		return calendarioservice.findByClient(id);

	}

	@PostMapping("/editar-llamadas-cliente")
	@ResponseBody
	public String editarllamadascliente(Long id, String observacion) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		ComercialCalendario llamada = calendarioservice.findOne(id);
		llamada.setDescription(observacion);
		llamada.setActualizadoPor(auth.getName());
		llamada.setUltimaFechaModificacion(dateFormat.format(date));
		calendarioservice.save(llamada);
		return "seguimientos";
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_SEGUIMIENTOS_LISTAR"})
	@GetMapping("/seguimientos")
	public String listSeguimientos(Model model) {
		model.addAttribute("Listclientes", clienteservice.findAll(null));
		model.addAttribute("Listcalendario", calendarioservice.findAll());
		return "seguimientos";
	}

	@PostMapping("/guardar-seguimientos")
	@ResponseBody
	public String guardarseguimiento(String fecha, String observacion, Long idcliente, Integer duracion)
			throws ParseException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date date2 = (Date) formatter.parse(fecha);
		ZoneId zone = ZoneId.of("America/Mexico_City");
		ZonedDateTime fechafinform = date2.toInstant().plusSeconds(duracion * 60).atZone(zone);
		date2 = (Date) formatter.parse(fechafinform.toString());
		DateTime datetime = new DateTime(date2);
		org.joda.time.format.DateTimeFormatter formatterNoMillis = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");
		String end = datetime.toString(formatterNoMillis);
		ComercialCalendario llamadas = new ComercialCalendario();
		llamadas.setDescription(observacion);
		llamadas.setTitle("Llamada");
		llamadas.setStart(fecha);
		llamadas.setIdCliente(idcliente);
		llamadas.setCreadoPor(auth.getName());
		llamadas.setFechaCreacion(dateFormat.format(date));
		llamadas.setUltimaFechaModificacion(dateFormat.format(date));
		llamadas.setColor("green");
		llamadas.setEnd(end);
		llamadas.setEstatus("1");
		calendarioservice.save(llamadas);
		return "seguimientos";
	}

	@GetMapping("/carga-de-pedidos")
	public String listPedidos(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
		
		/* Obtener todos los datos del usuario logeado */
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		String role = "[ROLE_ADMINISTRADOR]";
		if (auth.getAuthorities().toString().equals(role)) {
			model.addAttribute("admin", true);
			model.addAttribute("clientes", clienteservice.findAll(null));
			model.addAttribute("pedidos", cargaPedidoService.CargaPedidoVista(null));
		} else {
			model.addAttribute("clientes", clienteservice.findAll(iduser));
			model.addAttribute("pedidos", cargaPedidoService.CargaPedidoVista(iduser));

		}
		model.addAttribute("lisp", cargaPedidoService.listPedidos());
		return "carga-de-pedidos";
	}// le movio erik

	@GetMapping("/agregar-empleado-empresa")
	public String addEmpleado() {
		return "agregar-empleado-empresa";
	}

	@GetMapping("/agregar-carga")
	public String addCarga() {
		return "agregar-carga";
	}

	@GetMapping("/movimientos-agentes")
	public String listMovi(Model model) {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth.getName().equalsIgnoreCase("ADMIN")) {
				model.addAttribute("listMovimientos", movimientoService.findAllWithNames());
				return "movimientos-agentes";
			}
			else {
				try {
					Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
					model.addAttribute("listMovimientos", movimientoService.findAllWithNamesByAgente(Long.parseLong(empleado[0].toString())));
					Long idAgente = Long.parseLong(empleado[0].toString());
					model.addAttribute("idAgente", idAgente);
					return "movimientos-agentes";
				}
				catch(Exception e) {
					model.addAttribute("listMovimientos", movimientoService.findAllWithNames());
					return "movimientos-agentes";
				}
			}
		}
		catch(Exception e) {
			
			return "movimientos-agentes";
		}
		finally {
			
		}
	}
}