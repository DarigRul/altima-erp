package com.altima.springboot.app.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altima.springboot.app.models.entity.ComercialCotizacion;
import com.altima.springboot.app.models.entity.ComercialCotizacionTotal;
import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.service.ComercialBordadoService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialCotizacionPrendaService;
import com.altima.springboot.app.models.service.IComercialCotizacionService;
import com.altima.springboot.app.models.service.IComercialCotizacionTotalService;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
import com.altima.springboot.app.models.service.IUsuarioService;

@Controller
public class CotizacionesController {
	
	@Autowired
	private IComercialCotizacionService cotizacionService;
	@Autowired
	private IHrEmpleadoService empleadoService;
	@Autowired
	private IComercialClienteService clienteService;
	@Autowired
	private IComercialCotizacionTotalService cotizacionTotalService;
	@Autowired
	private  IComercialCoordinadoService CoordinadoService;
	@Autowired
	private IComercialCotizacionPrendaService cotizacionPrendaService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ComercialBordadoService bordadoService;
	
	@GetMapping("/cotizaciones")
	public String listCotizaciones(Model model) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth.getName().equalsIgnoreCase("ADMIN")) {
				model.addAttribute("ListarCotizaciones", cotizacionService.findAllWithTotal(null));
				return "cotizaciones";
			}
			else {
				try {
					Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
					model.addAttribute("ListarCotizaciones", cotizacionService.findAllWithTotal(Long.parseLong(empleado[0].toString())));
					return "cotizaciones";
				}
				catch(Exception e) {
					model.addAttribute("ListarCotizaciones", cotizacionService.findAllWithTotal(null));
					return "cotizaciones";
				}
			}
		}
		catch(Exception e) {
			
			return "cotizaciones";
		}
		finally {
			
		}
	}
	
	@GetMapping("/agregar-cotizacion")
	public String addCotizaciones(Model model) {
		try {
			model.addAttribute("bordados", bordadoService.findListaLookupComercial());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			try {
				int cotizacion = Integer.parseInt(cotizacionService.findMax());
				model.addAttribute("numeroCotizacion","COT"+((cotizacion+1) + 10000));
			}catch(Exception e) {
				System.out.println("Es el primer registro en la tabla:  "+e);
				model.addAttribute("numeroCotizacion","COT"+(10001));
			}
			model.addAttribute("textArea", "Observaciones:\n" + 
									   "\n" + 
									   "Especificaciones:\n" + 
									   "\n" + 
									   "Nota especial:");
			
			if(auth.getName().equalsIgnoreCase("ADMIN")) {
				model.addAttribute("ListarAgentes", empleadoService.findAllByPuesto("Agente de Ventas"));
			}
			else {
				try {
					Object[] empleado = usuarioService.findEmpleadoByUserName(auth.getName());
					model.addAttribute("Agente", empleado[1]+ " " + empleado[2] + " " + empleado[3]);
					System.out.println("Es un agente de ventas");
					model.addAttribute("idAgente", empleado[0]);
					model.addAttribute("agente", "1");
					model.addAttribute("ListarClientes", clienteService.findClientesByAgenteVentas(Long.parseLong(empleado[0].toString())));
					
				}
				catch(Exception e) {
					System.out.println("No es un agente de ventas \n"+e);
					model.addAttribute("ListarAgentes", empleadoService.findAllByPuesto("Agente de Ventas"));
				}
			}
			
			model.addAttribute("prendasDiv", "#");
			model.addAttribute("preciosDiv", "#");
			model.addAttribute("pill", "");
			model.addAttribute("ListarGerentes", empleadoService.findAllByPuesto("Gerente de ventas"));
			model.addAttribute("ListarPrendas", CoordinadoService.findAllPrenda());
			
			return "agregar-cotizacion";
		}catch(Exception e) {
			System.out.println(e);
			return "agregar-cotizacion";
		}
	}

	@GetMapping("/agregar-cotizacion/{id}")
	public String EditarCotizaciones(@PathVariable(name = "id") Long id, Model model) {
		
		try {
			
			model.addAttribute("bordados", bordadoService.findListaLookupComercial());
			ComercialCotizacion cotizacion = cotizacionService.findOne(id);
			ComercialCotizacionTotal cotiTotal = cotizacionTotalService.findByCotizacion(id);
			DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
			separadoresPersonalizados.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("0.##", separadoresPersonalizados);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			//Esto hace referencia a la primera ventrana de agregar cotizacion "Cotizaciones-informacion.html"
			model.addAttribute("idCotizacion", cotizacion.getIdCotizacion());
			model.addAttribute("numeroCotizacion",cotizacion.getIdText());
			model.addAttribute("tituloCotizacion", cotizacion.getTituloCotizacion());
			model.addAttribute("tipoCotizacion", cotizacion.getTipoCotizacion());
			model.addAttribute("tipoPrecioVentas", cotizacion.getTipoPrecio());
			model.addAttribute("Gerente", cotizacion.getIdGerente());
			
			if(auth.getName().equalsIgnoreCase("ADMIN")) {
				model.addAttribute("ListarAgentes", empleadoService.findAllByPuesto("Agente de Ventas"));
				model.addAttribute("Agente", cotizacion.getIdAgenteVentas());
			}
			else {
				try {
					HrEmpleado empleado = empleadoService.findOne(cotizacion.getIdAgenteVentas());
					model.addAttribute("Agente", empleado.getNombrePersona()+ " " + empleado.getApellidoPaterno() + " " + empleado.getApellidoMaterno());
					System.out.println("Es un agente de ventas");
					model.addAttribute("agente", "1");
					model.addAttribute("idAgente", cotizacion.getIdAgenteVentas());
	
					
				}
				catch(Exception e) {
					System.out.println("No es un agente de ventas \n"+e);
					model.addAttribute("ListarAgentes", empleadoService.findAllByPuesto("Agente de Ventas"));
					model.addAttribute("Agente", cotizacion.getIdAgenteVentas());
					
				}
			}
			model.addAttribute("ListarClientes", clienteService.findClientesByAgenteVentas(cotizacion.getIdAgenteVentas()));
			model.addAttribute("Cliente", cotizacion.getIdCliente());
			model.addAttribute("prendasDiv", "#prendasDiv");
			model.addAttribute("pill", "pill");
			model.addAttribute("preciosDiv", "#preciosDiv");
			model.addAttribute("ListarGerentes", empleadoService.findAllByPuesto("Gerente de ventas"));
			model.addAttribute("textArea", cotizacion.getObservaciones());
			model.addAttribute("tipoCotizacionDisable", true);
			model.addAttribute("iva", cotiTotal.getIva());
			
			
			//Esto hace referencia a la segunda ventana de agregar cotización "Cotizaciones-prendas.html"
			model.addAttribute("ListarPrendas", CoordinadoService.findAllPrenda());
			model.addAttribute("idCotizacionToPrendas", cotizacion.getIdCotizacion());
			if(cotizacion.getTipoCotizacion().equalsIgnoreCase("1") || cotizacion.getTipoCotizacion().equalsIgnoreCase("3")) {
				model.addAttribute("ListaCotizacionPrendas", cotizacionPrendaService.FindCotizacionPrendas(id, 1));
				model.addAttribute("tipoPrecioVentasDisabled", true);
			}
			else if(cotizacion.getTipoCotizacion().equalsIgnoreCase("2")) {
				model.addAttribute("ListaCotizacionPrendas", cotizacionPrendaService.FindCotizacionPrendas(id, 2));
				model.addAttribute("tipoPrecioVentasDisabled", false);
			}
			
			
			
			//Esto hace referencia a la tercer ventana de agregar cotización "Cotizaciones-precios.html"
			model.addAttribute("anticipoCotizacion", cotiTotal.getAnticipoPorcentaje());
			model.addAttribute("anticipoMontoCotizacion", cotiTotal.getAnticipoMonto());
			model.addAttribute("descuentoCotizacion", cotiTotal.getDescuentoPorcentaje());
			model.addAttribute("descuentoMontoCotizacion", cotiTotal.getDescuentoMonto());
			double subtotal;
			try {
				subtotal = cotizacionPrendaService.subtotalPrendas(id);
			}
			catch(Exception e) {
				subtotal = 0;
			}
			double subtotalmasDescuento = Double.parseDouble(df.format(subtotal+Double.parseDouble(cotiTotal.getDescuentoMonto())));
			double ivaMonto = subtotalmasDescuento*(Double.parseDouble(cotiTotal.getIva())/100);
			double total = Double.parseDouble(df.format(subtotalmasDescuento+ivaMonto));
			double total2= total-Double.parseDouble(cotiTotal.getAnticipoMonto());
			
			model.addAttribute("Subtotal", subtotal);
			model.addAttribute("Subtotal2", subtotalmasDescuento);
			model.addAttribute("DescuentoCargo", cotiTotal.getDescuentoCargo());
			model.addAttribute("IVAMonto", ivaMonto);
			model.addAttribute("Total", total);
			model.addAttribute("Total2", total2);
			
			return "agregar-cotizacion";
		}
		catch(Exception e) {
			System.out.println(e);
			return "cotizaciones";
		}
		finally {
			System.out.println("Mapeo de editar una cotización");
		}
	}

	@GetMapping("/pedirAutorizacion/{id}")
	public String pedirAutorizacion(@PathVariable(name = "id") Long id) {
		try {
			if(cotizacionTotalService.findByCotizacion(id).getTotal().equals("0.0")) {
				return "redirect:/cotizaciones";
			}
			else {
				ComercialCotizacion cotizacion = cotizacionService.findOne(id);
				cotizacion.setEstatus(1);
				cotizacionService.save(cotizacion);
				return "redirect:/cotizaciones";
			}
			
		}catch(Exception e) {
			System.out.println(e);
			return "redirect:/cotizaciones";
		}
	}
	
	@GetMapping("/Autorizar/{id}")
	public String autorizar(@PathVariable(name = "id") Long id) {
		try {
			if(cotizacionTotalService.findByCotizacion(id).getTotal().equals("0.0")) {
				
				return "redirect:/cotizaciones";
			}
			else {
				ComercialCotizacion cotizacion = cotizacionService.findOne(id);
				cotizacion.setEstatus(2);
				cotizacionService.save(cotizacion);
				return "redirect:/cotizaciones";
			}
		}catch(Exception e) {
			System.out.println(e);
			return "redirect:/cotizaciones";
		}
	}
	
	// Metodo para imprimir los empleados de un pedido
	@RequestMapping(value = { "/imprimir-cotizacion/{id}/{totales}/{cv}/{mail}" }, method = RequestMethod.GET)
	public String listGeneral(@PathVariable(value = "id") Long id, @PathVariable(value = "totales") boolean totales, @PathVariable(value = "cv") boolean cv, @PathVariable(value = "mail") String mail, Model model) {
		
		model.addAttribute("tipo", cotizacionService.findOne(id).getTipoCotizacion());
		model.addAttribute("id", cotizacionService.findOne(id).getIdText());
		model.addAttribute("cotizacionTotal", cotizacionTotalService.findByCotizacion(id));
		model.addAttribute("totales", totales);
		model.addAttribute("cv", cv);
		model.addAttribute("mail", mail);
		model.addAttribute("ListaCotizacionPrendas", cotizacionPrendaService.FindCotizacionPrendas(id, Integer.parseInt(cotizacionService.findOne(id).getTipoCotizacion())));
		return "/imprimir-cotizacion";
	}
}
