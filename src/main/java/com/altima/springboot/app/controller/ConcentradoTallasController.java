package com.altima.springboot.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.IComercialConcentradoTallaService;
import com.altima.springboot.app.models.service.IProduccionLookupService;
import com.altima.springboot.app.models.service.IServicioClienteLookupService;

@Controller
public class ConcentradoTallasController {  
	@Autowired
	ComercialClienteEmpleadoService ComClienteEmpleadoService;
	@Autowired
	IComercialConcentradoTallaService ConcentradoTallaService;
	@Autowired
	IServicioClienteLookupService ServicioClienteLookupService;

	@Autowired
	IProduccionLookupService ProduccionLookupService;

	/*
	 * este componente(@authComponent) funciona mandando el id del registro como
	 * parametro para hacer un findone y obtener el id del usuario de registro y el
	 * id del usuario de la sesion actual para asi compararlos y aprobar o denegar
	 * el acceso a editar cierto registro
	 */
	//@PreAuthorize("@authComponent.hasPermissionpedido(idpedido)")
	@PreAuthorize("@authComponent.hasPermission(#idpedido,{'pedido'})")
	@GetMapping("/concentrado-de-tallas/{idpedido}")
	public String listConcentradoTallas(Model model, @PathVariable("idpedido") Long idpedido) {

		List<String> list = new ArrayList<>();

		for (Object[] d : ConcentradoTallaService.findPrendaCliente(idpedido)) {

			list.add((String) d[1]);
		}
		ConcentradoTallaService.genpivot(list);
		List<String> list2 = new ArrayList<>();
		list2.add("Empleado");
		list2.addAll(list);
		model.addAttribute("head", list2);
		model.addAttribute("prendastallas",
				ConcentradoTallaService.findPrendaTalla2(ConcentradoTallaService.genpivot(list), idpedido));
		model.addAttribute("empleados10", ConcentradoTallaService.findPrendaTalla3(idpedido));
		model.addAttribute("idpedido", idpedido);
		return "concentrado-de-tallas";
	}

	@GetMapping("/agregar-concentrado-de-tallas/{idpedido}")
	public String addConcentradoTallas(Model model, @PathVariable("idpedido") Long idpedido) {
		ComClienteEmpleadoService.findAllEmpleadosEmpresa(idpedido);
		ConcentradoTallaService.findPrendaCliente(idpedido);

		model.addAttribute("idpedido", idpedido);
		model.addAttribute("empleados", ComClienteEmpleadoService.findAllEmpleadosEmpresa(idpedido));
		model.addAttribute("prendas", ConcentradoTallaService.findPrendaCliente(idpedido));
		model.addAttribute("talla", ProduccionLookupService.findAllByType("Talla"));
		model.addAttribute("largo", ProduccionLookupService.findAllByType("Largo"));
		model.addAttribute("especificacion", ServicioClienteLookupService.findAllByType("Especificacion"));
		return "agregar-concentrado-de-tallas";
	}

	@GetMapping("/editar-concentrado-de-tallas/{idpedido}/{idempleado}")
	public String editConcentradoTallas(Model model, @PathVariable("idpedido") Long idpedido,
			@PathVariable("idempleado") Long idempleado) {
		model.addAttribute("idempleado", idempleado);
		model.addAttribute("idpedido", idpedido);
		model.addAttribute("empleados", ComClienteEmpleadoService.findAllEmpleadosEmpresa(idpedido));
		model.addAttribute("prendas", ConcentradoTallaService.findPrendaCliente(idpedido));
		model.addAttribute("talla", ProduccionLookupService.findAllByType("Talla"));
		model.addAttribute("largo", ProduccionLookupService.findAllByType("Largo"));
		model.addAttribute("especificacion", ServicioClienteLookupService.findAllByType("Especificacion"));
		return "agregar-concentrado-de-tallas";
	}
}
