package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.component.AuthComponent;
import com.altima.springboot.app.dto.SelectPedidoInformacionDto;
import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.models.entity.ComercialCoordinadoForro;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinadoTela;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComercialPrendaBordado;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.IAdminConfiguracionPedidoService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialClienteFacturaService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialClienteSucursalService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialPrendaBordadoService;
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
	private IComercialPrendaBordadoService bordadoService;

	@Autowired
	private IComercialCoordinadoService CoordinadoService;

	@Autowired
	IAdminConfiguracionPedidoService configService;

	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IComercialPrendaBordadoService prendaBordadoService;

	@Autowired
	AuthComponent currentuserid;

	@RequestMapping(value = "/mostrar-pedidos", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> operadores(Long id) {

		return cargaPedidoService.PedidosExistenteIdEmpresa(id);
	}

	@PostMapping("/guardar-carga-pedido")
	@Transactional
	@ResponseBody
	public String guardacatalogo(Long cargaEmpresa, String cargaTipopedido, Long id_pedido, String fechaTallas,
			HttpServletRequest request, RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialPedidoInformacion pedido = new ComercialPedidoInformacion();

		AdminConfiguracionPedido config = cargaPedidoService.findOneConfig(cargaTipopedido);

		if (config.getTipoPedido() == 1) {
			System.out.println("eL ID de pedido es: " + id_pedido);
			pedido.setIdEmpresa(cargaEmpresa);
			pedido.setTipoPedido(cargaTipopedido);
			pedido.setIdPedido(id_pedido);
			pedido.setCreadoPor(auth.getName());
			pedido.setFechaCreacion(hourdateFormat.format(date));
			pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
			pedido.setEstatus("1");
			pedido.setIdUsuario(currentuserid.currentuserid());
			cargaPedidoService.save(pedido);
			pedido.setIdText(config.getNomenclatura() + (pedido.getIdPedidoInformacion() + 10000));
			cargaPedidoService.save(pedido);

			redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon",
					"success");
			return "1";
		} else if (config.getTipoPedido() == 2) {
			ComercialPedidoInformacion pedidoAux = cargaPedidoService.findOne(id_pedido);
			if (pedidoAux.getEstatus().equals("2")) {

				if (cargaPedidoService.validarNumStockPedido(id_pedido)
						&& cargaPedidoService.validarFechaStockPedido(id_pedido)) {
					System.out.println("eL ID de pedido es: " + id_pedido);
					pedido.setIdEmpresa(cargaEmpresa);
					pedido.setTipoPedido(cargaTipopedido);
					pedido.setIdPedido(id_pedido);
					pedido.setCreadoPor(auth.getName());
					pedido.setFechaCreacion(hourdateFormat.format(date));
					pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
					pedido.setEstatus("1");
					pedido.setFechaTomaTalla(fechaTallas);
					pedido.setIdUsuario(currentuserid.currentuserid());
					cargaPedidoService.save(pedido);
					pedido.setIdText("S" + cargaPedidoService.ContadorStock(id_pedido) + pedidoAux.getIdText());
					cargaPedidoService.save(pedido);

					redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon",
							"success");
					return "1";
				} else {

					if (!cargaPedidoService.validarNumStockPedido(id_pedido)) {
						redirectAttrs
								.addFlashAttribute("title", "Solo se puede realizar un maximo de 3 Stock por pedido")
								.addFlashAttribute("icon", "warning");
						return "3";
					}
					if (!cargaPedidoService.validarFechaStockPedido(id_pedido)) {
						redirectAttrs.addFlashAttribute("title", "Solo se puede realizar Stock un año antes")
								.addFlashAttribute("icon", "warning");
						return "4";
					}
				}
			} else {
				redirectAttrs.addFlashAttribute("title", "Solo se puede realizar Stock de pedidos cerrados")
						.addFlashAttribute("icon", "warning");
				return "2";
			}

		}
		else if(config.getTipoPedido() == 4) {
			
			ComercialPedidoInformacion pedidoAux = cargaPedidoService.findOne(id_pedido);
			List<Object[]> listCoordinados = CoordinadoService.findAllEmpresa(id_pedido);
			if (pedidoAux.getEstatus().equals("2")) {

				if (((cargaPedidoService.validarNumResurtidosPedido(id_pedido)<1 && cargaPedidoService.validarNumEmpleadosResurtidoPedido(id_pedido)==1) || 
						cargaPedidoService.validarNumResurtidosPedido(id_pedido)<2 && cargaPedidoService.validarNumEmpleadosResurtidoPedido(id_pedido)==2)
						&& cargaPedidoService.validarFechaStockPedido(id_pedido)) {
					System.out.println("eL ID de pedido es: " + id_pedido);
					pedido.setIdEmpresa(cargaEmpresa);
					pedido.setTipoPedido(cargaTipopedido);
					pedido.setIdPedido(id_pedido);
					pedido.setCreadoPor(auth.getName());
					pedido.setFechaCreacion(hourdateFormat.format(date));
					pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
					pedido.setEstatus("1");
					pedido.setFechaTomaTalla(fechaTallas);
					pedido.setIdUsuario(currentuserid.currentuserid());
					cargaPedidoService.save(pedido);
					pedido.setIdText("R" + cargaPedidoService.ContadorStock(id_pedido) + pedidoAux.getIdText());
					cargaPedidoService.save(pedido);
					
					for(int i=0;i<listCoordinados.size();i++) {
						ComercialCoordinado coordinado = new ComercialCoordinado();
						Integer contador = CoordinadoService.ContadorCoordinadoCliente(pedido.getIdPedidoInformacion());
						coordinado.setEstatus("1");
						coordinado.setIdPedido(pedido.getIdPedidoInformacion());
						// coor.setIdCliente(id);
						coordinado.setNumeroCoordinado(String.valueOf((contador + 1)));
						coordinado.setCreadoPor(auth.getName());
						coordinado.setFechaCreacion(hourdateFormat.format(date));
						coordinado.setUltimaFechaModificacion(hourdateFormat.format(date));
						coordinado.setIdText("COOR" + ((contador + 1) + 100));
						CoordinadoService.save(coordinado);
						
						List<ComercialCoordinadoPrenda> listaCoorPrendas = CoordinadoService.findAllCoorPrendasEntities(Long.parseLong(listCoordinados.get(i)[0].toString()));
						for(int j = 0; j< listaCoorPrendas.size();j++) {
							ComercialCoordinadoPrenda coorPrenda = new ComercialCoordinadoPrenda();
							
							coorPrenda.setIdFamilaGenero(listaCoorPrendas.get(j).getIdFamilaGenero());
							coorPrenda.setIdPrenda(listaCoorPrendas.get(j).getIdPrenda());
							coorPrenda.setIdTela(listaCoorPrendas.get(j).getIdTela());
							coorPrenda.setIdCoordinado(coordinado.getIdCoordinado());
							coorPrenda.setAdicional(listaCoorPrendas.get(j).getAdicional());
							coorPrenda.setMontoAdicional(listaCoorPrendas.get(j).getMontoAdicional());
							coorPrenda.setPrecioFinal(listaCoorPrendas.get(j).getPrecioFinal());
							coorPrenda.setObservaciones(listaCoorPrendas.get(j).getObservaciones());
							coorPrenda.setEstatus(listaCoorPrendas.get(j).getEstatus());
							coorPrenda.setPrecio(listaCoorPrendas.get(j).getPrecio());
							coorPrenda.setIdFamiliaComposicion(listaCoorPrendas.get(j).getIdFamiliaComposicion());
							coorPrenda.setTipoPrecioCotizacion(listaCoorPrendas.get(j).getTipoPrecioCotizacion());
							coorPrenda.setCreadoPor(auth.getName());
							coorPrenda.setActualizadoPor(auth.getName());
							coorPrenda.setFechaCreacion(hourdateFormat.format(date));
							coorPrenda.setUltimaFechaModificacion(hourdateFormat.format(date));
							
							CoordinadoService.saveCoorPrenda(coorPrenda);
							
							List<ComercialCoordinadoTela> ListCoorTelas = CoordinadoService.findAllCoorTelasEntities(listaCoorPrendas.get(j).getIdCoordinadoPrenda());
							for(int p = 0; p<ListCoorTelas.size();p++) {
								ComercialCoordinadoTela coorTela = new ComercialCoordinadoTela();
								
								coorTela.setIdTela(ListCoorTelas.get(p).getIdTela());
								coorTela.setIdCoordinadoPrenda(coorPrenda.getIdCoordinadoPrenda());
								coorTela.setCreado_por(auth.getName());
								coorTela.setActualizadoPor(auth.getName());
								coorTela.setDescripcion(ListCoorTelas.get(p).getDescripcion());
								coorTela.setFechaCreacion(hourdateFormat.format(date));
								coorTela.setUltimaFechaModificacion(null);
								CoordinadoService.saveTelaMaterial(coorTela);
								
							}
							List<ComercialCoordinadoMaterial> listCoorMateriales = CoordinadoService.findAllCoorMaterial(listaCoorPrendas.get(j).getIdCoordinadoPrenda());
							for(int m = 0; m<listCoorMateriales.size(); m++) {
								ComercialCoordinadoMaterial material = new ComercialCoordinadoMaterial();
								
								material.setIdMaterial(listCoorMateriales.get(m).getIdMaterial());
								material.setColor(listCoorMateriales.get(m).getColor());
								material.setColorCodigo(listCoorMateriales.get(m).getColorCodigo());
								material.setIdCoordinadoPrenda(coorPrenda.getIdCoordinadoPrenda());
								material.setCreadoPor(auth.getName());
								material.setActualizadoPor(auth.getName());
								material.setFechaCreacion(hourdateFormat.format(date));
								material.setUltimaFechaModificacion(hourdateFormat.format(date));
								CoordinadoService.saveCoorMaterial(material);
							}
							
							List<ComercialCoordinadoForro> listCoorForros = CoordinadoService.findAllCoorForrosEntities(listaCoorPrendas.get(j).getIdCoordinadoPrenda());
							for(int f = 0; f<listCoorForros.size();f++) {
								ComercialCoordinadoForro detalleForro = new ComercialCoordinadoForro();
								
								detalleForro.setDescripcion(listCoorForros.get(f).getDescripcion());
								detalleForro.setIdForro(listCoorForros.get(f).getIdForro());
								detalleForro.setIdCoordinadoPrenda(coorPrenda.getIdCoordinadoPrenda());

								detalleForro.setCreado_por(auth.getName());
								detalleForro.setActualizadoPor(auth.getName());
								detalleForro.setFechaCreacion(hourdateFormat.format(date));
								detalleForro.setUltimaFechaModificacion(hourdateFormat.format(date));
								CoordinadoService.saveForroMaterial(detalleForro);
							}
							
							List<ComercialPrendaBordado> listCoorBordados = prendaBordadoService.findAll(listaCoorPrendas.get(j).getIdCoordinadoPrenda());
							for(int b = 0; b<listCoorBordados.size(); b++) {
								ComercialPrendaBordado coorBordado = new ComercialPrendaBordado();
								
								coorBordado.setIdCoordinadoPrenda(coorPrenda.getIdCoordinadoPrenda());
								coorBordado.setPrecioBordado(listCoorBordados.get(b).getPrecioBordado());
								coorBordado.setArchivoBordado(listCoorBordados.get(b).getArchivoBordado());
								coorBordado.setIdBordado(listCoorBordados.get(b).getIdBordado());
								coorBordado.setCreadoPor(auth.getName());
								coorBordado.setActualizadoPor(auth.getName());
								coorBordado.setFechaCreacion(hourdateFormat.format(date));
								coorBordado.setUltimaFechaModificacion(hourdateFormat.format(date));
								
								prendaBordadoService.save(coorBordado);
								
							}
							
						}
						
					}

					redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon",
							"success");
					return "1";
				} else {

					if ((cargaPedidoService.validarNumResurtidosPedido(id_pedido)>=1 && cargaPedidoService.validarNumEmpleadosResurtidoPedido(id_pedido)==1)) {
						redirectAttrs
								.addFlashAttribute("title", "Solo se puede realizar un resurtido por pedido")
								.addFlashAttribute("icon", "warning");
						return "5";
					}
					if((cargaPedidoService.validarNumResurtidosPedido(id_pedido)>=2 && cargaPedidoService.validarNumEmpleadosResurtidoPedido(id_pedido)==2)) {
						redirectAttrs.addFlashAttribute("title", "Solo se puede realizar un máximo de 2 Resurtidos por pedido")
						.addFlashAttribute("icon", "warning");
						return "6";
					}
					if (!cargaPedidoService.validarFechaStockPedido(id_pedido)) {
						redirectAttrs.addFlashAttribute("title", "Solo se puede realizar Resurtido un año antes")
								.addFlashAttribute("icon", "warning");
						return "7";
					}
				}
			} else {
				redirectAttrs.addFlashAttribute("title", "Solo se puede realizar Resurtido de pedidos cerrados")
						.addFlashAttribute("icon", "warning");
				return "2";
			}
		}
		return cargaTipopedido;

	}

	@PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	@GetMapping("/informacion-general/{id}")
	public String listGeneral(@PathVariable(value = "id") Long id, Map<String, Object> model, Model m) {
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		AdminConfiguracionPedido config = cargaPedidoService.findOneConfig(pedido.getTipoPedido());

		if (config.getAnticipoTrueFalse().equals("Si")) {
			model.put("anticipo", true);
		}
		m.addAttribute("clientes", clienteservice.findAll(null));
		model.put("pedido", pedido);
		return "informacion-general";
	}

	@PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	@GetMapping("/agregar-empleado-empresa/{id}/{idcliente}")
	public String getEmpleadosInsert(@PathVariable(value = "id") Long id,
			@PathVariable(value = "idcliente") Long idcliente, Map<String, Object> model) {
		System.out.println("Init process" + idcliente);

		try {

			// System.out.println("las query
			// "+icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente).get(0).getNombreSucursal());
			model.put("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(id));

			model.put("form", new ArrayList<ComercialClienteEmpleado>());
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

	@RequestMapping(value = "/validar-cambio-precio", method = RequestMethod.GET)
	@ResponseBody
	public boolean validar(String estatusPrecios, Long id) {
		ComercialPedidoInformacion aux = cargaPedidoService.findOne(id);

		if (aux.getPrecioUsar() != null) {
			if (!aux.getPrecioUsar().equals(estatusPrecios)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
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
		pedido.setIdUsuario(currentuserid.currentuserid());

		cargaPedidoService.save(pedido);
		redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon", "success");
		return "redirect:/carga-de-pedidos";

	}

	@PostMapping("/guardar-informacion-general-pedido2")
	public String guardarCliente2(ComercialPedidoInformacion pedido, RedirectAttributes redirectAttrs) {

		if (pedido.getFechaAnticipo().equals("")) {
			pedido.setFechaAnticipo(null);
		}

		ComercialPedidoInformacion aux = cargaPedidoService.findOne(pedido.getIdPedidoInformacion());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		pedido.setActualizadoPor(auth.getName());
		pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
		pedido.setIdUsuario(currentuserid.currentuserid());
		if (!aux.getPrecioUsar().equals(pedido.getPrecioUsar())) {
			List<Object[]> auxlist = bordadoService.CambioPrecio(pedido.getIdPedidoInformacion());
			for (Object[] a : auxlist) {

				Long id_coor = Long.parseLong(a[0].toString());
				Float precio_bordado = Float.parseFloat(a[7].toString());
				Float precio_usar = Float.parseFloat(a[8].toString());

				ComercialCoordinadoPrenda prenda = CoordinadoService.findOneCoorPrenda(id_coor);
				Float preciofinal = precio_bordado + precio_usar;
				prenda.setPrecio(Float.toString(precio_usar));
				prenda.setPrecioFinal(Float.toString(preciofinal));
				prenda.setMontoAdicional("0.00");
				prenda.setAdicional("0.00");
				CoordinadoService.saveCoorPrenda(prenda);
			}
		}

		cargaPedidoService.save(pedido);

		redirectAttrs.addFlashAttribute("title", "Pedido guardado correctamente").addFlashAttribute("icon", "success");
		return "redirect:/carga-de-pedidos";

	}

	@PostMapping("/observaciones")
	public String guardarobservaciones(RedirectAttributes redirectAttrs, String observacion, Long idpedido) {
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(idpedido);
		pedido.setObservacion(observacion);
		cargaPedidoService.save(pedido);
		redirectAttrs.addFlashAttribute("title", "Observaciones guardadas correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/carga-de-pedidos";
	}

	@PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	@RequestMapping(value = "/cerrar-expediente", method = RequestMethod.GET)
	@ResponseBody
	public String cerrar(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		AdminConfiguracionPedido config = cargaPedidoService.findOneConfig(pedido.getTipoPedido());
		Integer CantidadPiezas = cargaPedidoService.validarPiezas(id);

		String list = cargaPedidoService.ValidarCantidadEspecial(id);
		if (list == null) {
			Integer sumaDias = 0;
			if (cargaPedidoService.validarBordado(id) == true) {
				sumaDias += Integer.parseInt(config.getDiasBordado());
			}

			if (CantidadPiezas <= Integer.parseInt(config.getCantidadPrenda())) {
				sumaDias += Integer.parseInt(config.getMinimoDias());
			} else {
				sumaDias += Integer.parseInt(config.getMaximoDias());
			}

			pedido.setEstatus("2");
			pedido.setFechaCierre(hourdateFormat.format(date));
			pedido.setFechaEntrega(cargaPedidoService.CalcularFecha(pedido.getFechaCierre(), sumaDias));
			pedido.setActualizadoPor(auth.getName());
			pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
			pedido.setDiaEstimados(Integer.toString(sumaDias));
			cargaPedidoService.save(pedido);

			return null;

		} else {
			System.out.println(list);
			return list;
		}

	}

	@RequestMapping(value = "/abrir-expediente", method = RequestMethod.GET)
	@ResponseBody
	public String abrir(Long id) {

		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);

		pedido.setEstatus("1");
		cargaPedidoService.save(pedido);
		return null;

	}

	@RequestMapping(value = "/validar-monto-pedido", method = RequestMethod.GET)
	@ResponseBody
	public String validarMontoPedio(Long id) {
		String CantidadMonto = cargaPedidoService.validarMonto(id);

		String Stock = cargaPedidoService.validarStock(id);

		System.out.println(Stock);
		if (CantidadMonto.equals("No cumple el monto total")
				|| CantidadMonto.equals("Error , no es posible calcular el monto")) {
			if (Stock.equals("No cumple con el stock") || Stock.equals("Error , no es posible calcular el stock")) {
				return CantidadMonto + "\n" + Stock;
			}
			return CantidadMonto;
		}

		else if (Stock.equals("No cumple con el stock") || Stock.equals("Error , no es posible calcular el stock")) {
			return Stock;
		} else {
			return null;

		}

	}

	@RequestMapping(value = "/validar-stock-disponibles", method = RequestMethod.GET)
	@ResponseBody
	public String validarStockDisponibles(Long id) {
		String vali = cargaPedidoService.CantidadStock(id);

		return vali;

	}

	@PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	@RequestMapping(value = "/cerrar-expediente-Stock", method = RequestMethod.GET)
	@ResponseBody
	public String cerrarStock(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);

		AdminConfiguracionPedido config = cargaPedidoService.findOneConfig(pedido.getTipoPedido());

		Integer CantidadPiezas = cargaPedidoService.validarPiezasStock(id);

		System.out.println("Esta es la consult de piezas Stock  " + CantidadPiezas);
		if (CantidadPiezas != 0) {
			Integer sumaDias = 0;
			if (cargaPedidoService.validarBordadoStock(id) == true) {
				sumaDias += Integer.parseInt(config.getDiasBordado());
			}

			if (CantidadPiezas <= Integer.parseInt(config.getCantidadPrenda())) {
				sumaDias += Integer.parseInt(config.getMinimoDias());
			} else {
				sumaDias += Integer.parseInt(config.getMaximoDias());
			}

			pedido.setEstatus("2");
			pedido.setFechaCierre(hourdateFormat.format(date));
			pedido.setFechaEntrega(cargaPedidoService.CalcularFecha(pedido.getFechaCierre(), sumaDias));
			pedido.setActualizadoPor(auth.getName());
			pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
			pedido.setDiaEstimados(Integer.toString(sumaDias));
			cargaPedidoService.save(pedido);

			return null;
		}

		else {
			return "No contiene empleados";
		}

	}
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR','ROLE_COMERCIAL_AGENTES_CARGA_VALIDACION_FALDA')")
	@GetMapping("patchPedidoValidacionFalda/{tipo}/{id}")
	public String patchPedidoValidacionFalda(@PathVariable Long id,@PathVariable Boolean tipo) {

		Date date = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		hourdateFormat.setTimeZone(timeZone);
        String sDate = hourdateFormat.format(date);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		pedido.setValidacion(tipo);
		pedido.setActualizadoPor(auth.getName());
		pedido.setUltimaFechaCreacion(sDate);
		cargaPedidoService.save(pedido);
		return ("redirect:/carga-de-pedidos");
	}

	@GetMapping("getEstatusValidacion/{id}")
	@ResponseBody
	public Boolean getEstatusValidacion(@PathVariable Long id) {
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		return pedido.getValidacion();
	}

	@GetMapping("/getPedidosByEstatus")
	@ResponseBody
	public List<SelectPedidoInformacionDto> getPedidosByEstatus(@RequestParam String estatus) {

		return cargaPedidoService.findByEstatus(estatus);
	}

	@RequestMapping(value = "/traerPedido", method = RequestMethod.GET)
	@ResponseBody
	public ComercialPedidoInformacion traerPedido(@RequestParam(value="idPedido") Long idPedido) {
		
		try {
			return cargaPedidoService.findOne(idPedido);
		}
		catch (Exception e) {
			return null;
		}
		
		finally{
			
		}
	}
	
	@RequestMapping(value = "/guardarExtras", method = RequestMethod.GET)
	@ResponseBody
	public int guardarExtra(@RequestParam(value="cubrePolvo", required=false) String cubrePolvo,
							@RequestParam(value="portaTraje", required=false) String portaTraje,
							@RequestParam(value="otros", required=false) String otros,
							@RequestParam(value="otrosTexto", required=false) String otrosTexto,
							@RequestParam(value="idPedido") Long idPedido) {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			ComercialPedidoInformacion pedido = cargaPedidoService.findOne(idPedido);
			
			if(!cubrePolvo.equals("")) {
				pedido.setCubrePolvo(cubrePolvo);
			}
			if(!portaTraje.equals("")) {
				pedido.setPortaTraje(portaTraje);
			}
			if(!otros.equals("")) {
				pedido.setOtros(otros);
				pedido.setOtrosTexto(otrosTexto);
			}
			
			pedido.setActualizadoPor(auth.getName());
			pedido.setUltimaFechaCreacion(hourdateFormat.format(date));
			cargaPedidoService.save(pedido);
			
			return 1;
		}
		catch (Exception e) {
			return 0;
		}
		
		finally{
			
		}
		
		
		
	}

}
