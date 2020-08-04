package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComercialTotalRazonSocial;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialCalendarioService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialConcentradoPrendasService;
import com.altima.springboot.app.models.service.IComercialConcentradoTallaService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialPrendaBordadoService;
import com.altima.springboot.app.models.service.IComercialTotalRazonSocialService;

@CrossOrigin(origins = { "*" })
@Controller
public class ExpedienteController {

	@Autowired
	IComercialCalendarioService calendarioservice;
	@Autowired
	private IComercialClienteService clienteservice;
	@Autowired
	private ICargaPedidoService cargaPedidoService;
	@Autowired
	private ComercialClienteEmpleadoService cargaclienteempleadoservice;
	@Autowired
	private IComercialPrendaBordadoService bordadoService;
	@Autowired
	private IComercialCoordinadoService CoordinadoService;
	@Autowired
	private IComercialConcentradoPrendasService concentradoPrendasService;
	@Autowired
	private IComercialConcentradoTallaService ConcentradoTallaService;
	@Autowired
	private IComercialTotalRazonSocialService totalService;

	// Metodo de Listar
	@GetMapping("/expediente")
	public String expediente(Model model) {
		model.addAttribute("clientes", clienteservice.findAll(null));
		model.addAttribute("pedidos", cargaPedidoService.CargaPedidoVista(null));
		return "expediente";
	}

	// Metodo para imprimir la informacion general
	@GetMapping("/expediente-imprimir-informacion-general/{id}")
	public String imprimirInformacionGeneral(@PathVariable(value = "id") Long id, Map<String, Object> model, Model m) {
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		m.addAttribute("clientes", clienteservice.findAll(null));
		model.put("pedido", pedido);
		return "/imprimir-expediente-informacion-general";
	}

	// Metodo para imprimir la informacion general
	@GetMapping("/expediente-imprimir-coordinados/{id}")
	public String imprimirCoordinados(@PathVariable(value = "id") Long id, Model model) {
		model.addAttribute("coordinados", CoordinadoService.findAllEmpresa(id));
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		model.addAttribute("pedido", pedido);
		model.addAttribute("cliente", clienteservice.findOne(cargaPedidoService.findOne(id).getIdEmpresa()));
		return "/imprimir-expediente-coordinado";
	}

	// Metodo para imprimir el detalle de precios de un pedido
	@GetMapping("/expediente-imprimir-detalle-precios/{id}")
	public String listPrecios(@PathVariable(value = "id") Long id, Model model) {
		List<Object[]> aux = bordadoService.findAllCoordinado(id);
		for (Object[] a : aux) {
			Long id_coor = Long.parseLong(a[0].toString());
			Float precio_bordado = Float.parseFloat(a[7].toString());
			Float precio_usar = Float.parseFloat(a[8].toString());
			Float monto = Float.parseFloat(a[10].toString());
			ComercialCoordinadoPrenda prenda = CoordinadoService.findOneCoorPrenda(id_coor);
			Float preciofinal = precio_bordado + precio_usar + monto;
			prenda.setPrecioFinal(Float.toString(preciofinal));
			CoordinadoService.saveCoorPrenda(prenda);
		}
		model.addAttribute("listCoor", bordadoService.findAllCoordinado(id));
		model.addAttribute("pedido", cargaPedidoService.findOne(id));
		model.addAttribute("cliente", clienteservice.findOne(cargaPedidoService.findOne(id).getIdEmpresa()));
		return "/imprimir-expediente-detalle-precios";
	}

	// Metodo para imprimir los empleados de un pedido
	@RequestMapping(value = { "/expediente-imprimir-empleados/{id}/{idcliente}" }, method = RequestMethod.GET)
	public String listGeneral(@PathVariable(value = "id") Long id, @PathVariable(value = "idcliente") Long idcliente,
			Model model) {
		model.addAttribute("empleados", cargaclienteempleadoservice.findAllEmpleadosRazonSocialYSucursal(id));
		model.addAttribute("pedido", cargaPedidoService.findOne(id));
		model.addAttribute("cliente", clienteservice.findOne(cargaPedidoService.findOne(id).getIdEmpresa()));
		return "/imprimir-expediente-empleados";
	}

	// Metodo para imprimir el concentrado de prenas
	@GetMapping("/expediente-imprimir-concentrado-prendas/{id}")
	public String imprimirConcentradoPrendas(@PathVariable(value = "id") Long id, Model model) {
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		model.addAttribute("pedido", pedido);
		model.addAttribute("coordinados", concentradoPrendasService.findCoordinadosfromPedido(id));
		model.addAttribute("cliente", clienteservice.findOne(cargaPedidoService.findOne(id).getIdEmpresa()));
		model.addAttribute("empleados", concentradoPrendasService.findEmpleadosParaExpediente(id));

		return "/imprimir-expediente-concentrado-prenda";
	}

	// Metodo para imprimir el concentrado de prenas
	@GetMapping("/expediente-imprimir-concentrado-prendas/{id}/{id2}")
	public String imprimirConcentradoPrendasIndividual(@PathVariable(value = "id") Long id,
			@PathVariable(value = "id2") Long id2, Model model) {
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		System.out.println("El id del pedido: " + id);
		System.out.println("El id del coordinado: " + id2);
		model.addAttribute("pedido", pedido);
		model.addAttribute("coordinados", concentradoPrendasService.findCoordinadofromPedido(id2));
		model.addAttribute("cliente", clienteservice.findOne(cargaPedidoService.findOne(id).getIdEmpresa()));
		model.addAttribute("empleados", concentradoPrendasService.findEmpleadosParaExpediente(id));
		return "/imprimir-expediente-concentrado-prenda-individual";
	}
	
	//Metodo para imprimir un concetrado de tallas 
	@GetMapping("/expediente-imprimir-concentrado-por-tallas/{id}")
	public String imprimirTallas(Model model, @PathVariable("id") Long idpedido, Model m) {
		List<String> list = new ArrayList<>();

		for (Object[] d : ConcentradoTallaService.findPrendaCliente(idpedido)) {

			list.add((String) d[1]);
		}
		ConcentradoTallaService.genpivot(list);
		List<String> list2 = new ArrayList<>();
		list2.add("Empleado");
		list2.addAll(list);
		model.addAttribute("head", list2);
		model.addAttribute("prendastallas", ConcentradoTallaService.findPrendaTalla2(ConcentradoTallaService.genpivot(list), idpedido));
		model.addAttribute("empleados10", ConcentradoTallaService.findPrendaTalla3(idpedido));
		model.addAttribute("idpedido", idpedido);
		
		//mio
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(idpedido);
		m.addAttribute("clientes", clienteservice.findAll(null));
		model.addAttribute("pedido", pedido);
		return "/imprimir-expediente-concentrado-de-tallas";
	}
	
	
	//Metodo para imprimir un concetrado de tallas 
	@GetMapping("/expediente-imprimir-totales-por-razon-social/{id}")
	public String imprimirTotalesPorRazonSocial(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DecimalFormat floatFormat = new DecimalFormat("0.00");
		floatFormat.setMaximumFractionDigits(2);
		List<Object[]> aux = totalService.consultaX(id);
		for (Object[] a : aux) {
			ComercialTotalRazonSocial total = totalService.findOne(Long.parseLong(a[1].toString()), Long.parseLong(a[0].toString()));
				if (total != null) {
					float sub1 = Float.parseFloat(a[3].toString());
					float sub2= Float.parseFloat(total.getSubtotalTotal2());
					float iva = Float.parseFloat(total.getIva());
					float totalf= Float.parseFloat(total.getTotalPedido());
					float p_descuento = Float.parseFloat(total.getDescuPorcentaje());
					float p_ancicipo = Float.parseFloat(total.getAnticiPorcentaje());
					float p_entrega = Float.parseFloat(total.getEntregaPorcentaje());
					float p_saldo = Float.parseFloat(total.getAjustesPorcentaje());
					float m_ancicipo;
					float m_entrega;
					double m_saldo;
					float m_descuento;
					sub2 =  (sub1 * p_descuento/100)+ sub1;
					totalf = (sub2 * iva )+sub2;
					m_ancicipo = (totalf*(p_ancicipo/100));
					m_entrega = (totalf*(p_entrega/100));
					m_ancicipo =  (float) redondearDecimales(m_ancicipo, 2);
					m_entrega =  (float) redondearDecimales(m_entrega, 2);
					m_saldo = totalf-m_ancicipo-m_entrega;
					m_descuento = (sub1*(p_descuento/100));
					total.setIdPedido(Long.parseLong(a[1].toString()));
					total.setIdClienteFacturaF(Long.parseLong(a[0].toString()));
					total.setNumeroPrenda(a[2].toString());
					total.setSubtotalTotal(a[3].toString());
					total.setIva(a[4].toString());
					total.setDescuento(floatFormat.format(m_descuento));
					total.setAnticipo(floatFormat.format(m_ancicipo));
					total.setEntrega(floatFormat.format(m_entrega));
					total.setSaldo(floatFormat.format(m_saldo));
					total.setSubtotalTotal2(floatFormat.format(sub2));
					total.setTotalPedido(floatFormat.format(totalf));
					totalService.save(total);
				} else {
					ComercialTotalRazonSocial total2 = new ComercialTotalRazonSocial();
					float sub1 = Float.parseFloat(a[3].toString());
					float sub2= Float.parseFloat(a[3].toString());
					float iva = Float.parseFloat(a[4].toString());
					float totalf;
					sub2 = sub1;
					totalf = (sub2 * iva )+sub2;
					total2.setIdPedido(Long.parseLong(a[1].toString()));
					total2.setIdClienteFacturaF(Long.parseLong(a[0].toString()));
					total2.setNumeroPrenda(a[2].toString());
					total2.setSubtotalTotal(a[3].toString());
					total2.setDescuento("0.00");
					total2.setSubtotalTotal2(String.valueOf(sub2));
					total2.setIva(a[4].toString());
					total2.setTotalPedido(String.valueOf(totalf));
					total2.setAnticipo("0.00");
					total2.setEntrega("0.00");
					total2.setSaldo("0.00");
					total2.setAjustesPorcentaje("0.00");
					total2.setAnticiPorcentaje("0.00");
					total2.setDescuPorcentaje("0.00");
					total2.setEntregaPorcentaje("0.00");
					total2.setCreadoPor(auth.getName());
					total2.setFechaCreacion(hourdateFormat.format(date));
					total2.setActualizadoPor(auth.getName());
					total2.setUltimaFechaModificacion(hourdateFormat.format(date));
					totalService.save(total2);
				}
		}
		model.put("lisTotal", totalService.totalRazon(id));
		ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
		model.put("clientes", clienteservice.findAll(null));
		model.put("pedido", pedido);
		return "/imprimir-expediente-totales-por-razon-social";
	}
	
	public static double redondearDecimales(float valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
	
	@GetMapping("/agregar-expediente")
	public String agregarExpediente() {
		return "agregar-expediente";
	}

	@GetMapping("/agregar-expediente-empleados")
	public String agregarEmpleadosExpediente() {
		return "agregar-expediente-empleados";
	}

	@GetMapping("/detalle-expediente")
	public String detalleExpediente() {
		return "detalle-expediente";
	}

}
