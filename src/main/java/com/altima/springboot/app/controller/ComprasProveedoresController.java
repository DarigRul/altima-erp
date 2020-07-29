package com.altima.springboot.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.models.service.IComprasProveedorService;

@Controller
public class ComprasProveedoresController {
	
	@Autowired
	private IComprasProveedorService proveedorService;
	
	
	
    @GetMapping("compras-proveedores")
	public String Proveedores(Model model) {
    	
    	model.addAttribute("proveedores", proveedorService.findAll());
    	
		return"compras-proveedores";
	}

	@GetMapping("compras-agregar-proveedores")
	public String addProveedores(Model model, Map<String, Object> m ) {
		ComprasProveedores proveedor = new ComprasProveedores();
		m.put("proveedor", proveedor);
		
		return"compras-agregar-proveedores";
	}
	
	@RequestMapping(value = "/guardar_datos_generales", method= RequestMethod.POST)
	public String guardarDatosGenerales(Model model, Map<String, Object> m, ComprasProveedores proveedor) {
		
		if(proveedor.getIdProveedor()!=null) {
			ComprasProveedores proveedores = proveedorService.findOne(proveedor.getIdProveedor());
			proveedores.setNombreProveedor(proveedor.getNombreProveedor());
			proveedores.setTipo(proveedor.getTipo());
			proveedores.setNumeroExterior(proveedor.getNumeroExterior());
			proveedores.setNumeroInterior(proveedor.getNumeroInterior());
			proveedores.setColonia(proveedor.getColonia());
			proveedores.setPoblacion(proveedor.getPoblacion());
			proveedores.setMunicipio(proveedor.getMunicipio());
			proveedores.setCodigoPostal(proveedor.getCodigoPostal());
			proveedores.setEstado(proveedor.getEstado());
			proveedores.setPais(proveedor.getPais());
			proveedores.setClasificacion(proveedor.getClasificacion());
			proveedores.setRfcProveedor(proveedor.getRfcProveedor());
			proveedores.setCurpProveedor(proveedor.getCurpProveedor());
			proveedores.setTelefonoProveedor(proveedor.getTelefonoProveedor());
			proveedores.setPaginaWebProveedor(proveedor.getPaginaWebProveedor());
			
			proveedorService.save(proveedores);
			m.put("proveedor", proveedores);
			return "redirect:/compras-proveedores";
		}
		else {
			proveedor.setIdText("PROV");
			proveedor.setTipo("Moral");
			proveedor.setEstatus("1");
			proveedorService.save(proveedor);
			proveedor.setIdText("PROV"+(10000 + proveedor.getIdProveedor()));
			proveedorService.save(proveedor);	
			
			m.put("proveedor", proveedor);
			return "redirect:/compras-proveedores";
		}
		
	}
	
	@RequestMapping(value = "/editar_datos_generales/{id}", method= RequestMethod.GET)
	public String guardarDatosGenerales(Model model, Map<String, Object> m, ComprasProveedores proveedor,
										@PathVariable(value = "id") Long id) {
		
		proveedor = proveedorService.findOne(id);	
		m.put("proveedor", proveedor);
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