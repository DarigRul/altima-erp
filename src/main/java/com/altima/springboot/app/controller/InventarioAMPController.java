package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.AmpInventario;
import com.altima.springboot.app.models.entity.AmpLookup;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.service.IAmpInventarioService;
import com.altima.springboot.app.models.service.IAmpLoookupService;
import com.altima.springboot.app.models.service.ICatalogoService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IUploadService;

@Controller
public class InventarioAMPController {
	@Autowired
	private IAmpLoookupService  LookService;
	
	@Autowired
	private ICatalogoService  LookCatalogoService;
	
	@Autowired
	private IUploadService UploadService;
	
	@Autowired
	private IAmpInventarioService InventarioSerivice;
	
	
	@GetMapping("/inventario-amp")
	public String listInv(Model model)
	{
		model.addAttribute("inventario", InventarioSerivice.findAll());
		return"inventario-amp";
	}
	
	@GetMapping("/agregar-inventario-amp")
	public String addInv(Model model){
		AmpInventario inventario = new AmpInventario();
		model.addAttribute("listClasificacion", LookService.findAllLookup("Clasificacion"));
		model.addAttribute("listLinea", LookService.findAllLookup("Linea"));
		model.addAttribute("listUnidad", LookCatalogoService.findAllLookup("Unidad Medida"));
		model.addAttribute("inventario", inventario);
		return"agregar-inventario-amp";
	}
	
	
	@PostMapping("/guardar-inventario-amp")
	public String guardarCliente(AmpInventario inventario,RedirectAttributes redirectAttrs,
			@RequestParam(value="inputGroupFile01", required=false) MultipartFile imagen) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DecimalFormat df = new  DecimalFormat ("00000");
		if (!imagen.isEmpty()){
			if ( inventario.getImagen() != null && inventario.getImagen().length() > 0) {
				UploadService.deleteInventarioAMP(inventario.getImagen());
			}
			String uniqueFilename = null;
			try {
				uniqueFilename = UploadService.copyInventarioAMP(imagen);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inventario.setImagen(uniqueFilename);
		}
		
		if (inventario.getIdInventario() == null) {
			inventario.setCreadoPor(auth.getName());
			inventario.setFechaCreacion(hourdateFormat.format(date));
			inventario.setEstatus("1");
			inventario.setActualizadoPor(null);
			inventario.setUltimaFechaModificacion(null);
			
			
			InventarioSerivice.save(inventario);
			
			inventario.setIdText(inventario.getArticulo().substring(0,3) + df.format(inventario.getIdInventario()));
			InventarioSerivice.save(inventario);
			redirectAttrs.addFlashAttribute("title", "Inventario guardado correctamente").addFlashAttribute("icon", "success");
		}
		else {
			inventario.setUltimaFechaModificacion(hourdateFormat.format(date));
			inventario.setActualizadoPor(auth.getName());
			
			redirectAttrs.addFlashAttribute("title", "Inventario guardado correctamente").addFlashAttribute("icon", "success");
			InventarioSerivice.save(inventario);
		}
		
		return "redirect:inventario-amp";
	}
	
	@GetMapping("/editar-inventario-amp/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model) {
		AmpInventario inventario = null;
		inventario = InventarioSerivice.findOne(id);
		model.addAttribute("listClasificacion", LookService.findAllLookup("Clasificacion"));
		model.addAttribute("listLinea", LookService.findAllLookup("Linea"));
		model.addAttribute("listUnidad", LookCatalogoService.findAllLookup("Unidad Medida"));
		model.addAttribute("inventario", inventario);
		return "agregar-inventario-amp";
	}
	

	@GetMapping("/proveedores-inventario-amp")
	public String addproveedores()
	{
		return"proveedores-inventario-amp";
	}

	
	@GetMapping(value = "/uploads/InventarioAMP/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = UploadService.loadInventarioAMP(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	
	@GetMapping("baja-inventario-amp/{id}") 
	public String baja_sucursal(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		AmpInventario inventario = null;
		inventario = InventarioSerivice.findOne(id);
		inventario.setEstatus("0");
		inventario.setActualizadoPor(auth.getName());
		inventario.setUltimaFechaModificacion(hourdateFormat.format(date));
		InventarioSerivice.save(inventario);
	
		redirectAttrs
        .addFlashAttribute("title", "Inventario dada de baja correctamente")
        .addFlashAttribute("icon", "success");
		
		return "redirect:/inventario-amp";
		
	}
	
	@GetMapping("alta-inventario-amp/{id}") 
	public String alta_sucursal(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		AmpInventario inventario = null;
		inventario = InventarioSerivice.findOne(id);
		inventario.setEstatus("1");
		inventario.setActualizadoPor(auth.getName());
		inventario.setUltimaFechaModificacion(hourdateFormat.format(date));
		InventarioSerivice.save(inventario);
	
		redirectAttrs
        .addFlashAttribute("title", "Inventario dada de alta correctamente")
        .addFlashAttribute("icon", "success");
		return "redirect:/inventario-amp";
	}
}
