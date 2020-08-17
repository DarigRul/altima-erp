package com.altima.springboot.app.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altima.springboot.app.models.entity.ComercialCotizacion;
import com.altima.springboot.app.models.entity.ComercialCotizacionTotal;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialCotizacionPrendaService;
import com.altima.springboot.app.models.service.IComercialCotizacionService;
import com.altima.springboot.app.models.service.IComercialCotizacionTotalService;
import com.altima.springboot.app.models.service.IHrEmpleadoService;

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
	
	@GetMapping("/cotizaciones")
	public String listCotizaciones(Model model) {
		model.addAttribute("ListarCotizaciones", cotizacionService.findAllWithTotal());
		return "cotizaciones";
	}
	
	@GetMapping("/agregar-cotizacion")
	public String addCotizaciones(Model model) {
		try {
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
			model.addAttribute("prendasDiv", "#");
			model.addAttribute("preciosDiv", "#");
			model.addAttribute("pill", "");
			model.addAttribute("ListarGerentes", empleadoService.findAllByPuesto("Gerente de ventas"));
			model.addAttribute("ListarAgentes", empleadoService.findAllByPuesto("Agente de Ventas"));
			model.addAttribute("ListarClientes", clienteService.findAll(null));
			model.addAttribute("ListarPrendas", CoordinadoService.findAllPrenda());
			
			return "agregar-cotizacion";
		}catch(Exception e) {
			System.out.println(e);
			return "agregar-cotizacion";
		}
	}

	@GetMapping("/agregar-cotizacion/{id}")
	public String EditarCotizaciones(@PathVariable(name = "id") Long id, Model model) {
		ComercialCotizacion cotizacion = cotizacionService.findOne(id);
		ComercialCotizacionTotal cotiTotal = cotizacionTotalService.findByCotizacion(id);
		DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
		separadoresPersonalizados.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.##", separadoresPersonalizados);
		
		//Esto hace referencia a la primera ventrana de agregar cotizacion "Cotizaciones-informacion.html"
		model.addAttribute("idCotizacion", cotizacion.getIdCotizacion());
		model.addAttribute("numeroCotizacion",cotizacion.getIdText());
		model.addAttribute("tituloCotizacion", cotizacion.getTituloCotizacion());
		model.addAttribute("tipoCotizacion", cotizacion.getTipoCotizacion());
		model.addAttribute("tipoPrecioVentas", cotizacion.getTipoPrecio());
		model.addAttribute("Gerente", cotizacion.getIdGerente());
		model.addAttribute("Agente", cotizacion.getIdAgenteVentas());
		model.addAttribute("Cliente", cotizacion.getIdCliente());
		model.addAttribute("prendasDiv", "#prendasDiv");
		model.addAttribute("pill", "pill");
		model.addAttribute("preciosDiv", "#preciosDiv");
		model.addAttribute("ListarGerentes", empleadoService.findAllByPuesto("Gerente de ventas"));
		model.addAttribute("ListarAgentes", empleadoService.findAllByPuesto("Agente de Ventas"));
		model.addAttribute("ListarClientes", clienteService.findAll(null));
		model.addAttribute("textArea", cotizacion.getObservaciones());
		model.addAttribute("iva", cotiTotal.getIva());
		
		
		//Esto hace referencia a la segunda ventana de agregar cotización "Cotizaciones-prendas.html"
		model.addAttribute("ListarPrendas", CoordinadoService.findAllPrenda());
		model.addAttribute("idCotizacionToPrendas", cotizacion.getIdCotizacion());
		model.addAttribute("ListaCotizacionPrendas", cotizacionPrendaService.FindCotizacionPrendas(id));
		
		
		//Esto hace referencia a la tercer ventana de agregar cotización "Cotizaciones-precios.html"
		model.addAttribute("anticipoCotizacion", cotiTotal.getAnticipoPorcentaje());
		model.addAttribute("anticipoMontoCotizacion", cotiTotal.getAnticipoMonto());
		model.addAttribute("descuentoCotizacion", cotiTotal.getDescuentoPorcentaje());
		model.addAttribute("descuentoMontoCotizacion", cotiTotal.getDescuentoMonto());
		
		try {
			double subtotal=Double.parseDouble(cotiTotal.getAnticipoMonto())+Double.parseDouble(cotiTotal.getDescuentoMonto())+cotizacionPrendaService.findSubtotalCotizacionPrendas(id);
			model.addAttribute("Subtotal", df.format(subtotal));
			model.addAttribute("DescuentoCargo", cotiTotal.getDescuentoCargo());
			double ivamonto = subtotal*(Double.parseDouble(cotiTotal.getIva())/100);
			model.addAttribute("IVAMonto", df.format(ivamonto));
			model.addAttribute("Total", df.format(cotizacionPrendaService.findSubtotalCotizacionPrendas(id)+ivamonto));
		}catch(Exception e) {
			model.addAttribute("Subtotal", cotiTotal.getSubtotal());
			model.addAttribute("DescuentoCargo", cotiTotal.getDescuentoCargo());
			model.addAttribute("IVAMonto", cotiTotal.getIvaMonto());
			model.addAttribute("Total", cotiTotal.getTotal());
		}
		
		return "agregar-cotizacion";
	}

	@GetMapping("/pedirAutorizacion/{id}")
	public String pedirAutorizacion(@PathVariable(name = "id") Long id) {
		try {
			ComercialCotizacion cotizacion = cotizacionService.findOne(id);
			cotizacion.setEstatus(1);
			cotizacionService.save(cotizacion);
			return "redirect:/cotizaciones";
		}catch(Exception e) {
			System.out.println(e);
			return "redirect:/cotizaciones";
		}
	}
	
	@GetMapping("/Autorizar/{id}")
	public String autorizar(@PathVariable(name = "id") Long id) {
		try {
			ComercialCotizacion cotizacion = cotizacionService.findOne(id);
			cotizacion.setEstatus(2);
			cotizacionService.save(cotizacion);
			return "redirect:/cotizaciones";
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
		model.addAttribute("totales", totales);
		model.addAttribute("cv", cv);
		model.addAttribute("mail", mail);
		model.addAttribute("ListaCotizacionPrendas", cotizacionPrendaService.FindCotizacionPrendas(id));
		return "/imprimir-cotizacion";
	}
}
