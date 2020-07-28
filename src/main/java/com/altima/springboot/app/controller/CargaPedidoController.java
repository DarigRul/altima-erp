package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialClienteFacturaService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialClienteSucursalService;
import com.altima.springboot.app.models.service.IUsuarioService;

@CrossOrigin(origins = { "*" })
@Controller
public class CargaPedidoController {

	@Autowired
	private ICargaPedidoService cargaPedidoService;

	@Autowired
	private ComercialClienteEmpleadoService cargaclienteempleadoservice;

	@Autowired
	private IComercialClienteFacturaService icomercialclientefacturaservice;

	@Autowired
	private IComercialClienteSucursalService icomercialclientesucursalservice;

	@Autowired
	private IComercialClienteService clienteservice;

	@Autowired
	IUsuarioService usuarioService;

	@RequestMapping(value = "/mostrar-pedidos", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> operadores(Long id) {

		return cargaPedidoService.PedidosExistenteIdEmpresa(id);
	}

	@PostMapping("/guardar-carga-pedido")
	public String guardacatalogo(Long cargaEmpresa, String cargaTipopedido, Long id_pedido, HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		/* Obtener todos los datos del usuario logeado */
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialPedidoInformacion pedido = new ComercialPedidoInformacion();
		System.out.println("eL ID de pedido es: " + id_pedido);
		pedido.setIdEmpresa(cargaEmpresa);
		pedido.setTipoPedido(cargaTipopedido);
		pedido.setIdPedido(id_pedido);
		pedido.setCreadoPor(auth.getName());
		pedido.setFechaCreacion(hourdateFormat.format(date));
		pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
		pedido.setEstatus("1");
		pedido.setIdUsuario(iduser);
		cargaPedidoService.save(pedido);
		pedido.setIdText("VENT" + (pedido.getIdPedidoInformacion() + 10000));
		cargaPedidoService.save(pedido);

		redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon", "success");
		return "redirect:/carga-de-pedidos";

	}

	@GetMapping("/informacion-general/{id}")
	public String listGeneral(@PathVariable(value = "id") Long id, Map<String, Object> model, Model m) {
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		m.addAttribute("clientes", clienteservice.findAll(null));
		model.put("pedido", pedido);
		return "informacion-general";
	}

	@GetMapping("/agregar-empleado-empresa/{id}/{idcliente}")
	public String getEmpleadosInsert(@PathVariable(value = "id") Long id,
			@PathVariable(value = "idcliente") Long idcliente, Map<String, Object> model) {
		System.out.println("Init process" + idcliente);

		try {

			// System.out.println("las query
			// "+icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente).get(0).getNombreSucursal());
			model.put("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(id));

			model.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
			model.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
			model.put("isPreviewView", "false");
			model.put("idPedido", id);
			return "agregar-empleado-empresa";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
			model.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
			return "agregar-empleado-empresa";

		} finally {
			System.out.println("Finalizar proceso");
		}

	}

	@PostMapping("/guardar-informacion-general-pedido")
	public String guardarCliente(ComercialPedidoInformacion pedido, RedirectAttributes redirectAttrs) {

		if (pedido.getFechaAnticipo().equals("")) {
			pedido.setFechaAnticipo(null);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		pedido.setActualizadoPor(auth.getName());
		pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
		cargaPedidoService.save(pedido);

		redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon", "success");
		return "redirect:/carga-de-pedidos";

	}
}
