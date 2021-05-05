package com.altima.springboot.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.dto.ModificacionDto;
import com.altima.springboot.app.dto.TallasPivoteDto;
import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.models.entity.ComercialConcentradoTalla;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComercialTallaModificacion;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ProduccionLookup;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.IAdminConfiguracionPedidoService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialConcentradoTallaService;
import com.altima.springboot.app.models.service.IComercialTallaModificacionService;
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

	@Autowired
	private IComercialTallaModificacionService comercialTallaModificacionService;

	@PostMapping("/guardar-concentrado-tallas")
	public String guardarcontentradotallas(Model model, String Nombre,
			@RequestParam(value = "values[]", required = false) String[] values, String Empleado, Integer Largo,
			String PrendaCliente, Integer Talla, String Pulgadas, Long IdPedido) {
		System.out.println(values);
		try {
			ComercialConcentradoTalla ComercialConcentradoTalla = new ComercialConcentradoTalla();
			ComercialConcentradoTalla.setIdEmpleadoPedido(Empleado);
			ComercialConcentradoTalla.setIdLargo(Largo);
			ComercialConcentradoTalla.setIdFamiliaPrenda(PrendaCliente);
			ComercialConcentradoTalla.setIdTalla(Talla);
			ComercialConcentradoTalla.setIdPedido(IdPedido);
			ConcentradoTallaService.save(ComercialConcentradoTalla);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return "agregar-concentrado-de-tallas";
	}

	@RequestMapping(value = "/listar-especificacion", method = RequestMethod.GET)
	public List<Object[]> listar(Long idpedido, Long idempleado, Long idprenda) {
		List<Object[]> result = null;
		if (ConcentradoTallaService.findSPF(idpedido) == null) {
			result = ConcentradoTallaService.findTallasPrendaEspecificacion(idpedido, idempleado, idprenda);

		} else {
			result = ConcentradoTallaService.findTallasPrendaEspecificacion(ConcentradoTallaService.findSPF(idpedido),
					idempleado, idprenda);

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
	public List<DisenioLookup> prendasempleado(Long idempleado, Long idpedido) {

		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(idpedido);

		AdminConfiguracionPedido config = configService.findOne(Long.parseLong(pedido.getTipoPedido()));

		return DisenioLookupService.findByTipoLookup("Familia Prenda");
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

		} else if (talla == null && largo == null) {
			ConcentradoTallaService.updateall(empleado, pedido, prenda);
			respuesta = true;
		} else {
			ConcentradoTallaService.updateall(talla, largo, empleado, pedido, prenda);
			respuesta = true;
		}
		return respuesta;
	}

	@GetMapping("/obtener-posicion-prenda")
	public String PosicionPrenda(String Prenda) {

		Object posicionprenda = DisenioLookupService.findClothesPosition(Prenda);

		return posicionprenda.toString();

	}

	@GetMapping("/obtener-largo-talla")
	public List<ProduccionLookup> ObtenerLargoTalla() {
		return ProduccionLookupService.findAllByType("Largo");
	}

	@GetMapping("api/concentrado-de-tallas/{id}/0")
	public ResponseEntity<?> getPivoteByIdPedido(@PathVariable(name = "id") Long id) {

		Map<String, Object> response = new HashMap<>();
		List<TallasPivoteDto> pivote = null;
		try {
			pivote = ConcentradoTallaService.findPivoteByidPedido(id);
			System.out.println(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (pivote.size() == 0) {
			response.put("mensaje", "Los registros con el id " + id + " no existen");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("tallas", pivote);
		response.put("idPedido", id);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("api/editar-concentrado-de-tallas/{idPedido}/{idEmpleado}")
	public ResponseEntity<?> getPivoteByIdEmpleado(@PathVariable(name = "idPedido") Long idPedido,
			@PathVariable(name = "idEmpleado") Long idEmpleado) {

		Map<String, Object> response = new HashMap<>();
		List<TallasPivoteDto> pivote = null;
		try {
			pivote = ConcentradoTallaService.findPivoteByIdEmpleado(idEmpleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (pivote.size() == 0) {
			response.put("mensaje", "Los registros con el id " + idEmpleado + " no existen");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("tallas", pivote);
		response.put("idPedido", idPedido);
		response.put("idEmpleado", idEmpleado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/api/getTallasByGenero")
	public ResponseEntity<?> getTallasByGenero(@RequestParam Long idGenero) {

		Map<String, Object> response = new HashMap<>();
		List<ProduccionLookup> tallas = null;
		try {
			tallas = ProduccionLookupService.findByGenero(idGenero);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (tallas.size() == 0) {
			response.put("mensaje", "Los registros con el genero " + idGenero + " no existen");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ProduccionLookup>>(tallas, HttpStatus.OK);
	}

	@PostMapping("/api/postTallaPrenda")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> postTallaPrenda(@RequestBody ComercialConcentradoTalla concentradoTalla) {
		Map<String, Object> response = new HashMap<>();
		try {
			ConcentradoTallaService.save(concentradoTalla);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ComercialConcentradoTalla>(concentradoTalla, HttpStatus.CREATED);
	}

	@PostMapping("/api/postTallaModificacion")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> postTallaModificacion(@RequestBody ComercialTallaModificacion modificacion) {
		Map<String, Object> response = new HashMap<>();
		try {
			comercialTallaModificacionService.save(modificacion);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ComercialTallaModificacion>(modificacion, HttpStatus.CREATED);
	}

	@GetMapping("/api/getModificacionesByIdConcentradoTalla")
	public ResponseEntity<?> getModificacionesByIdConcentradoTalla(@RequestParam Long idConcentradoTalla) {

		Map<String, Object> response = new HashMap<>();
		List<ModificacionDto> modificaciones = null;
		try {
			modificaciones = comercialTallaModificacionService
					.findModificacionesByidConcentradoTalla(idConcentradoTalla);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (modificaciones.size() == 0) {
			response.put("mensaje", "Los registros con el id " + idConcentradoTalla + " no existen");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ModificacionDto>>(modificaciones, HttpStatus.OK);
	}

	@DeleteMapping("/api/deleteModificacion/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteModificacion(@PathVariable(name = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			comercialTallaModificacionService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar registro en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El registro con el id " + id + " fue eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/api/deletePrendaTalla/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deletePrendaTalla(@PathVariable(name = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			ConcentradoTallaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar registro en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El registro con el id " + id + " fue eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
