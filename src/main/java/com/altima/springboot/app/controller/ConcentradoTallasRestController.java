package com.altima.springboot.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.models.entity.ComercialConcentradoTalla;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ProduccionLookup;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.IAdminConfiguracionPedidoService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialConcentradoTallaService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IProduccionLookupService;
import com.altima.springboot.app.models.service.IServicioClienteLookupService;

@RestController

public class ConcentradoTallasRestController {
	@Autowired
	ComercialClienteEmpleadoService ComClienteEmpleadoService;
	@Autowired
	IComercialConcentradoTallaService ConcentradoTallaService;
	@Autowired
	IServicioClienteLookupService ServicioClienteLookupService;

	@Autowired
	IProduccionLookupService ProduccionLookupService;

	@Autowired
	private IAdminConfiguracionPedidoService configService;

	@Autowired
	private ICargaPedidoService cargaPedidoService;
	
	@Autowired
	IDisenioLookupService DisenioLookupService;

	@PostMapping("/guardar-concentrado-tallas")
	public String guardarcontentradotallas(Model model, String Nombre,
			@RequestParam(value = "values[]", required = false) String[] values, String Empleado, String Largo,
			String PrendaCliente, String Talla, String Pulgadas, Long IdPedido) {
		String[] prenda=PrendaCliente.split("\\s");
		try {
			ComercialConcentradoTalla ComercialConcentradoTalla = new ComercialConcentradoTalla();
			ComercialConcentradoTalla.setIdEmpleadoPedido(Empleado);
			ComercialConcentradoTalla.setIdLargo(Largo);
			ComercialConcentradoTalla.setIdPrendaCliente(prenda[0]);
			ComercialConcentradoTalla.setIdFamiliaPrenda(prenda[1]);
			ComercialConcentradoTalla.setIdTalla(Talla);
			ComercialConcentradoTalla.setIdPedido(IdPedido);
			ConcentradoTallaService.save(ComercialConcentradoTalla);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return "agregar-concentrado-de-tallas";
	}

	@PostMapping("/guardar-concentrado-tallas1")
	public String guardarcontentradotallas1(Model model, String Nombre,
			@RequestParam(value = "values[]", required = false) String[] values, String Empleado, String Largo,
			String PrendaCliente, String Talla, String Pulgadas, Long IdPedido) {

		for (String especificacion : values) {

			try {
				ComercialConcentradoTalla ComercialConcentradoTalla = new ComercialConcentradoTalla();

				ComercialConcentradoTalla.setEspecificacion(especificacion);
				ComercialConcentradoTalla.setIdEmpleadoPedido(Empleado);
				ComercialConcentradoTalla.setIdLargo(Largo);
				ComercialConcentradoTalla.setIdPrendaCliente(PrendaCliente);
				ComercialConcentradoTalla.setIdTalla(Talla);
				ComercialConcentradoTalla.setIdPedido(IdPedido);
				ComercialConcentradoTalla.setPulgadas(Pulgadas);
				ConcentradoTallaService.save(ComercialConcentradoTalla);
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

		return "agregar-concentrado-de-tallas";
	}

	@PostMapping("/guardar-primer-concentrado-tallas")
	public String guardarprimercontentradotallas(Model model,
			@RequestParam(value = "values[]", required = false) String[] values, String Pulgadas, Long Id) {

		if (values.length == 1) {
			ComercialConcentradoTalla concentradotalla = ConcentradoTallaService.findOne(Id);
			concentradotalla.setEspecificacion(values[0]);
			concentradotalla.setPulgadas(Pulgadas);
			ConcentradoTallaService.save(concentradotalla);
		} else {

			ComercialConcentradoTalla concentradotalla = ConcentradoTallaService.findOne(Id);
			concentradotalla.setEspecificacion(values[0]);
			concentradotalla.setPulgadas(Pulgadas);
			ConcentradoTallaService.save(concentradotalla);

			for (int i = 1; i < values.length; i++) {

				ComercialConcentradoTalla concentradotalla2 = new ComercialConcentradoTalla();
				concentradotalla2.setEspecificacion(values[i]);
				concentradotalla2.setIdEmpleadoPedido(concentradotalla.getIdEmpleadoPedido());
				concentradotalla2.setIdLargo(concentradotalla.getIdLargo());
				concentradotalla2.setIdPedido(concentradotalla.getIdPedido());
				concentradotalla2.setIdTalla(concentradotalla.getIdTalla());
				concentradotalla2.setIdPrendaCliente(concentradotalla.getIdPrendaCliente());
				concentradotalla2.setPulgadas(concentradotalla.getPulgadas());

				ConcentradoTallaService.save(concentradotalla2);
			}

		}

		return "agregar-concentrado-de-tallas";
	}

	@RequestMapping(value = "/listar-especificacion", method = RequestMethod.GET)
	public List<Object[]> listar(Long idpedido, Long idempleado, Long idprenda) {
		List<Object[]> result = null;
		if(ConcentradoTallaService.findSPF(idpedido) == null) {
			result= ConcentradoTallaService.findTallasPrendaEspecificacion(idpedido, idempleado, idprenda);

		}
		else {
			result= ConcentradoTallaService.findTallasPrendaEspecificacion(ConcentradoTallaService.findSPF(idpedido), idempleado, idprenda);

			
		}
		return result;
	}

	@RequestMapping(value = "/verifduplicadoconcentradotalla", method = RequestMethod.GET)
	public boolean verificarduplicado(Model model, @RequestParam(value = "values[]", required = false) String[] values,
			String Empleado, String Pedido, String Largo, String PrendaCliente, String Talla, String Pulgadas) {
		boolean response;
		int contador = 0;
		for (String especificacion : values) {

			if (ConcentradoTallaService
					.findDuplicates(Empleado, Largo, PrendaCliente, Talla, Pulgadas, especificacion, Pedido)
					.size() > 0) {
				contador++;
			}
			if (ConcentradoTallaService.findDuplicates(Empleado, PrendaCliente, especificacion, Pedido).size() > 0) {
				contador++;
			}
		}

		if (contador > 0) {
			response = true;
		} else {
			response = false;
		}
		return response;
	}

	@DeleteMapping("/eliminar-especificacion")
	public boolean eliminar(Long id) {
		boolean response;
		try {
			ConcentradoTallaService.delete(id);
			response = true;
		} catch (Exception e) {
			response = false;
			System.out.println(e);
		}
		System.out.println(response);
		return response;

	}

	@DeleteMapping("/eliminar-prenda")
	public boolean eliminarprenda(Long id_empleado, Long id_prenda) {
		boolean response;
		try {
			System.out.println(id_empleado + "" + id_prenda);
			ConcentradoTallaService.deleteprenda(id_empleado, id_prenda);
			response = true;
		} catch (Exception e) {
			response = false;
			System.out.println(e);
		}
		System.out.println(response);
		return response;

	}

	@DeleteMapping("/eliminar-todo")
	public boolean eliminartodo(Long id_empleado) {
		boolean response;
		try {
			System.out.println(id_empleado);
			ConcentradoTallaService.deleteall(id_empleado);
			response = true;
		} catch (Exception e) {
			response = false;
			System.out.println(e);
		}
		System.out.println(response);
		return response;

	}

	@GetMapping("/prenda-empleado")
	public List<Object[]> prendaempleado(Long idpedido, Long idempleado) {
		List<Object[]> res = null;
		System.out.println(ConcentradoTallaService.findSPF(idpedido));
		try {

			if (ConcentradoTallaService.findSPF(idpedido) == null) {

				res = ConcentradoTallaService.findPrenda(idpedido, idempleado);
			} else {

				res = ConcentradoTallaService.findPrenda(ConcentradoTallaService.findSPF(idpedido), idempleado);

			}
			System.out.println("res" + res);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("res2" + res);
		}

		return res;
	}

	@GetMapping("/prenda-empleado-pivote")
	public List<Object[]> prendaempleadopivote(Long idpedido, Long idempleado) {

		return ConcentradoTallaService.findPrendapivote(idpedido, idempleado);
	}

	@GetMapping("/prendas-especificaciones")
	public String prendasespecificaciones(Long idpedido, Long idempleado, Long idprenda) {
		String respuesta;
		if (ConcentradoTallaService.findPrendasEspecificaciones(idempleado, idpedido, idprenda).size() == 1) {

			respuesta = "primero";
		} else {
			respuesta = "segundo";
		}
		return respuesta;
	}

	@GetMapping("/prenda-especificacion1")
	public List<Object[]> prendasespecificacionesone(Long idpedido, Long idempleado, Long idprenda) {

		return ConcentradoTallaService.findPrendasEspecificaciones(idempleado, idpedido, idprenda);
	}

	@GetMapping("/prenda-especificacion2")
	public List<Object[]> prendasespecificaciones2(Long idpedido, Long idempleado, Long idprenda) {

		return ConcentradoTallaService.findPrendasEspecificaciones2(idempleado, idpedido, idprenda);
	}

	@GetMapping("/prendas-empleado")
	public List<Object[]> prendasempleado(Long idempleado, Long idpedido) {

		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(idpedido);

		AdminConfiguracionPedido config = configService.findOne(Long.parseLong(pedido.getTipoPedido()));
		if (config.getTipoPedido() == 1) {
			return ConcentradoTallaService.findPrendasEmpleado(idempleado, idpedido);
		} else if (config.getTipoPedido() == 2) {
			return ConcentradoTallaService.findPrendasEmpleado(idempleado, pedido.getIdPedido());
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public boolean editar(Model model, @RequestParam(value = "talla", required = false) Long talla,
			@RequestParam(value = "largo", required = false) Long largo, Long pedido, Long prenda, Long empleado) {
		boolean respuesta = false;
		if (largo != null && talla == null) {
			ConcentradoTallaService.updateall(largo, empleado, pedido, prenda);
			respuesta = true;
		} else if (talla != null && largo == null) {
			ConcentradoTallaService.updateall1(talla, empleado, pedido, prenda);
			respuesta = true;
		} else {
			ConcentradoTallaService.updateall(talla, largo, empleado, pedido, prenda);
			respuesta = true;
		}
		return respuesta;
	}
   @GetMapping("/obtener-posicion-prenda")
   public String PosicionPrenda(String Prenda) {
	   
	  Object posicionprenda= DisenioLookupService.findClothesPosition(Prenda);
	  
	  return posicionprenda.toString();
	   
   }
	
   @GetMapping("/obtener-tallas")
   public List<ProduccionLookup> ObtenerTallas(String Posicion,String Genero) {
	   return ProduccionLookupService.findAllByType(Posicion, Genero, "Talla");
   }
   
   @GetMapping("/obtener-largo-talla")
   public List<ProduccionLookup> ObtenerLargoTalla() {
	   return ProduccionLookupService.findAllByType("Largo");
   }
   
}
