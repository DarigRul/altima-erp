package com.altima.springboot.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.IAdminConfiguracionPedidoService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
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

	@Autowired
	IAdminConfiguracionPedidoService configService;

	@Autowired
	private ICargaPedidoService cargaPedidoService;

	/*
	 * este componente(@authComponent) funciona mandando el id del registro como
	 * parametro para hacer un findone y obtener el id del usuario de registro y el
	 * id del usuario de la sesion actual para asi compararlos y aprobar o denegar
	 * el acceso a editar cierto registro
	 */
	// @PreAuthorize("@authComponent.hasPermissionpedido(idpedido)")
	@PreAuthorize("@authComponent.hasPermission(#idpedido,{'pedido'})")
	@GetMapping("/concentrado-de-tallas/{idpedido}/{idspf}")
	public String listConcentradoTallas(Model model, @PathVariable("idpedido") Long idpedido,
			@PathVariable(required = false) Long idspf) {
		List<String> list = new ArrayList<>();
		if (idspf == 0) {
			for (Object[] d : ConcentradoTallaService.findPrendaCliente(idpedido)) {
				list.add((String) d[1]);
			}
		} else {
			for (Object[] d : ConcentradoTallaService.findPrendaCliente(idspf)) {
				list.add((String) d[1]);
			}

		}
		ConcentradoTallaService.genpivot(list);
		List<String> list2 = new ArrayList<>();
		list2.add("Empleado");
		list2.addAll(list);
		model.addAttribute("head", list2);
		if (idspf == 0) {
			model.addAttribute("prendastallas",
					ConcentradoTallaService.findPrendaTalla2(ConcentradoTallaService.genpivot(list), idpedido));
			model.addAttribute("empleados10", ConcentradoTallaService.findPrendaTalla3(idpedido));

		} else {
			///////// con spf
			model.addAttribute("prendastallas",
					ConcentradoTallaService.findPrendaTalla2(ConcentradoTallaService.genpivot(list), idpedido, idspf));
			model.addAttribute("empleados10", ConcentradoTallaService.findPrendaTalla3(idpedido, idspf));

		}
		model.addAttribute("idpedido", idpedido);
		if (idspf == 0) {
			Integer spf = 0;
			model.addAttribute("idspf", spf);
		} else {
			
			model.addAttribute("idspf", idspf);
		}
		return "concentrado-de-tallas";
	}

	@GetMapping("/agregar-concentrado-de-tallas/{idpedido}")
	public String addConcentradoTallas(Model model, @PathVariable("idpedido") Long idpedido) {
		ComClienteEmpleadoService.findAllEmpleadosEmpresa(idpedido);
		ConcentradoTallaService.findPrendaCliente(idpedido);
		model.addAttribute("idspf", ConcentradoTallaService.findSPF(idpedido));

		model.addAttribute("idpedido", idpedido);
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(idpedido);

		AdminConfiguracionPedido config = configService.findOne(Long.parseLong(pedido.getTipoPedido()));
		if (config.getTipoPedido() == 1) {
			model.addAttribute("empleados", ComClienteEmpleadoService.findAllEmpleadosEmpresa(idpedido));
			model.addAttribute("prendas", ConcentradoTallaService.findPrendaCliente(idpedido));
			model.addAttribute("talla", ProduccionLookupService.findAllByType("Talla"));
			model.addAttribute("largo", ProduccionLookupService.findAllByType("Largo"));
			model.addAttribute("especificacion", ServicioClienteLookupService.findAllByType("Especificacion"));
		} else if (config.getTipoPedido() == 2) {

			model.addAttribute("empleados", ComClienteEmpleadoService.findAllClientesSPF(idpedido));
			model.addAttribute("prendas", ConcentradoTallaService.findPrendaCliente(pedido.getIdPedido()));
			model.addAttribute("talla", ProduccionLookupService.findAllByType("Talla"));
			model.addAttribute("largo", ProduccionLookupService.findAllByType("Largo"));
			model.addAttribute("especificacion", ServicioClienteLookupService.findAllByType("Especificacion"));
		}

		return "agregar-concentrado-de-tallas";
	}

	@GetMapping("/editar-concentrado-de-tallas/{idpedido}/{idempleado}")
	public String editConcentradoTallas(Model model, @PathVariable("idpedido") Long idpedido,
			@PathVariable("idempleado") Long idempleado, Long idspf) {

		model.addAttribute("idspf", ConcentradoTallaService.findSPF(idpedido));
		model.addAttribute("idempleado", idempleado);
		model.addAttribute("idpedido", idpedido);
		if (ConcentradoTallaService.findSPF(idpedido) == null) {

			model.addAttribute("empleados", ComClienteEmpleadoService.findAllEmpleadosEmpresa(idpedido));
			model.addAttribute("prendas", ConcentradoTallaService.findPrendaCliente(idpedido));

		} else {

			model.addAttribute("empleados", ComClienteEmpleadoService.findAllClientesSPF(idpedido));

			model.addAttribute("prendas",
					ConcentradoTallaService.findPrendaCliente(ConcentradoTallaService.findSPF(idpedido)));
		}
		model.addAttribute("talla", ProduccionLookupService.findAllByType("Talla"));
		model.addAttribute("largo", ProduccionLookupService.findAllByType("Largo"));
		model.addAttribute("especificacion", ServicioClienteLookupService.findAllByType("Especificacion"));
		return "agregar-concentrado-de-tallas";
	}

}
