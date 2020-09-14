package com.altima.springboot.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altima.springboot.app.models.entity.ComprasProveedorCredito;
import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.models.service.IComprasProveedorContactoService;
import com.altima.springboot.app.models.service.IComprasProveedorCreditoService;
import com.altima.springboot.app.models.service.IComprasProveedorService;

@Controller
public class ComprasProveedoresController {
	
	@Autowired
	private IComprasProveedorService proveedorService;
	@Autowired
	private IComprasProveedorContactoService contactoService;
	@Autowired
	private IComprasProveedorCreditoService creditoService;
	
	
	
    @GetMapping("compras-proveedores")
	public String Proveedores(Model model) {
    	
    	model.addAttribute("proveedores", proveedorService.findAll());
    	
		return"compras-proveedores";
	}

	@GetMapping("compras-agregar-proveedores")
	public String addProveedores(Model model, Map<String, Object> m ) {
		ComprasProveedores proveedor = new ComprasProveedores();
		m.put("proveedor", proveedor);
		model.addAttribute("check", true);
		return"compras-agregar-proveedores";
	}
		
	@RequestMapping(value = "/editar_datos_generales/{id}", method= RequestMethod.GET)
	public String guardarDatosGenerales(Model model, Map<String, Object> m, ComprasProveedores proveedor,
										ComprasProveedorCredito credito,
										@PathVariable(value = "id") Long id) {
		
		proveedor = proveedorService.findOne(id);
		m.put("proveedor", proveedor);
		if(proveedor.getNumeroExterior().equalsIgnoreCase("0")) {
			model.addAttribute("check", true);
		}
		else {
			model.addAttribute("check", false);
		}
		
		
		try {
		credito = creditoService.findByProveedor(id);
		
		model.addAttribute("manejoCredito", (credito.getManejoCredito().equals("1"))?true:false);
		model.addAttribute("diasCredito", credito.getDiasCredito());
		model.addAttribute("limiteCredito", credito.getLimiteCredito());
		model.addAttribute("saldo", credito.getSaldo());
		model.addAttribute("formaPago", credito.getFormaPago());
		model.addAttribute("observaciones", credito.getObservaciones());
		model.addAttribute("datoCredito", "editar");
		}
		catch(Exception e) {
			System.out.println("no existe credito");
		}
		
		try {
		model.addAttribute("contactos", contactoService.findAllByProveedor(id));
		}catch(Exception e) {
			System.out.println("no existen contactos");
		}
		model.addAttribute("datoContacto", "editar");
		
		
		
		return "compras-agregar-proveedores";
	}
	
	@GetMapping("/baja-proveedor/{id}")
	public String bajaProveedor(@PathVariable(value = "id") Long id) {
		
		ComprasProveedores proveedor = proveedorService.findOne(id);
		proveedor.setEstatus("0");
		System.out.println(proveedor.getEstatus());
		proveedorService.save(proveedor);
		
		return "redirect:/compras-proveedores";
	}
	
	@GetMapping("/alta-proveedor/{id}")
	public String altaProveedor(@PathVariable(value = "id") Long id) {
		
		ComprasProveedores proveedor = proveedorService.findOne(id);
		proveedor.setEstatus("1");
		proveedorService.save(proveedor);
		
		return "redirect:/compras-proveedores";
	}
	
}