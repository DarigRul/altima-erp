package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.AmpInventario;
import com.altima.springboot.app.models.entity.AmpInventarioProovedor;
import com.altima.springboot.app.models.entity.AmpInventarioProovedorPrecio;
import com.altima.springboot.app.models.entity.AmpLookup;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.entity.DisenioForro;
import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.service.IAmpInventarioProovedorService;
import com.altima.springboot.app.models.service.IAmpInventarioService;
import com.altima.springboot.app.models.service.IAmpLoookupService;
import com.altima.springboot.app.models.service.ICatalogoService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IDisenioForroService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
import com.altima.springboot.app.models.service.IDisenioTelaService;
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
	
	@Autowired
	private IAmpInventarioProovedorService ProveedorSerivice;
	
	@Autowired
	private IDisenioTelaService disenioTelaService;
	
	@Autowired
	private IDisenioForroService forroService;
	
	@Autowired
	private IDisenioMaterialService disenioMaterialService;
	
	
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
		String unique = LookService.nombreCategoria(inventario.getIdLinea());
		System.out.println("aqui esta el result de la query pref " + unique);
		String prefijo = unique.substring(1, 4);
		System.out.println("aqui esta el prefijo" + prefijo);
		
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
			
			//inventario.setIdText(inventario.getArticulo().substring(0,3) + df.format(inventario.getIdInventario()));
			inventario.setIdText(prefijo.toUpperCase() + (inventario.getIdInventario() + 10000));
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
		
		model.addAttribute("clasificacion", inventario.getIdClasificacion());
		model.addAttribute("linea", inventario.getIdLinea());
		return "agregar-inventario-amp";
	}
	

	@GetMapping("/proveedores-inventario-amp/{id}/{tipo}")
	public String addproveedores(@PathVariable (value="id") Long id,@PathVariable (value="tipo") String tipo, Model model)
	{
		model.addAttribute("idInventario", id);
		model.addAttribute("tipo", tipo);
		model.addAttribute("view", ProveedorSerivice.View(id, tipo));
		
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
	
	
	@GetMapping("baja-inventario-amp/{id}/{tipo}") 
	public String baja_sucursal(@PathVariable("id") Long id,@PathVariable("tipo") String tipo, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (tipo.equals("m")) {
			DisenioMaterial material = disenioMaterialService.findOne(id);
			material.setEstatus("0");
			material.setActualizadoPor(auth.getName());
			material.setUltimaFechaModificacion(hourdateFormat.format(date));
			disenioMaterialService.save(material);
			
		}
		if (tipo.equals("t")) {
			DisenioTela tela = disenioTelaService.findOne(id);
			tela.setUltimaFechaModificacion(hourdateFormat.format(date));
			tela.setActualizadoPor(auth.getName());
			tela.setEstatus("0");
			
			disenioTelaService.save(tela);
			
		}
		if (tipo.equals("f")) {
			DisenioForro forro = forroService.findOne(id);
			forro.setEstatus("0");
			forro.setActualizadoPor(auth.getName());
			forro.setUltimaFechaModificacion(hourdateFormat.format(date));
			forroService.save(forro);
			
		}
		if (tipo.equals("aa")) {
			AmpInventario inventario = null;
			inventario = InventarioSerivice.findOne(id);
			inventario.setEstatus("0");
			inventario.setActualizadoPor(auth.getName());
			inventario.setUltimaFechaModificacion(hourdateFormat.format(date));
			InventarioSerivice.save(inventario);
		}
		
	
		redirectAttrs
        .addFlashAttribute("title", "Inventario dada de baja correctamente")
        .addFlashAttribute("icon", "success");
		
		return "redirect:/inventario-amp";
		
	}
	
	@GetMapping("alta-inventario-amp/{id}/{tipo}") 
	public String alta_sucursal(@PathVariable("id") Long id, @PathVariable("tipo") String tipo, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		if (tipo.equals("m")) {
			DisenioMaterial material = disenioMaterialService.findOne(id);
			material.setEstatus("0");
			material.setActualizadoPor(auth.getName());
			material.setUltimaFechaModificacion(hourdateFormat.format(date));
			disenioMaterialService.save(material);
			
		}
		if (tipo.equals("t")) {
			DisenioTela tela = disenioTelaService.findOne(id);
			tela.setUltimaFechaModificacion(hourdateFormat.format(date));
			tela.setActualizadoPor(auth.getName());
			tela.setEstatus("0");
			
			disenioTelaService.save(tela);
			
		}
		if (tipo.equals("f")) {
			DisenioForro forro = forroService.findOne(id);
			forro.setEstatus("0");
			forro.setActualizadoPor(auth.getName());
			forro.setUltimaFechaModificacion(hourdateFormat.format(date));
			forroService.save(forro);
			
		}
		if (tipo.equals("aa")) {
			AmpInventario inventario = null;
			inventario = InventarioSerivice.findOne(id);
			inventario.setEstatus("0");
			inventario.setActualizadoPor(auth.getName());
			inventario.setUltimaFechaModificacion(hourdateFormat.format(date));
			InventarioSerivice.save(inventario);
		}
	
		redirectAttrs
        .addFlashAttribute("title", "Inventario dada de alta correctamente")
        .addFlashAttribute("icon", "success");
		return "redirect:/inventario-amp";
	}
	
	
	@RequestMapping(value = "/proveedores-activos", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> provedor() {
		return ProveedorSerivice.Proveedores();
	}
	
	@RequestMapping(value = "/historial-precio", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> precios(Long id) {
		System.out.println("id de precios "+id);
		return ProveedorSerivice.ViewPagos(id);
	}
	@RequestMapping(value = "/agregar-proveedor", method = RequestMethod.POST)
	@ResponseBody
	public String provedornew (Long id , Long idProveedor, String clave , Float costo, int dias , Long idInventario, String tipo ) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if ( id == null) {
			AmpInventarioProovedor obj = new AmpInventarioProovedor();
			AmpInventarioProovedorPrecio objPrecio = new AmpInventarioProovedorPrecio();
			
			obj.setIdProveedor(idProveedor);
			obj.setClaveProveedor(clave);
			obj.setIdInventario(idInventario);
			obj.setCreadoPor(auth.getName());
			obj.setFechaCreacion(hourdateFormat.format(date));
			obj.setEstatus("1");
			obj.setDias(dias);
			obj.setTipo(tipo);
			ProveedorSerivice.save(obj);
			objPrecio.setIdProveedor(obj.getIdInventarioProveedor());
			objPrecio.setPrecio(Float.toString(costo));
			objPrecio.setCreadoPor(auth.getName());
			objPrecio.setFechaCreacion(hourdateFormat.format(date));
			objPrecio.setEstatus("1");
			ProveedorSerivice.savePrecio(objPrecio);
			
			
		}
		else {
			AmpInventarioProovedor obj = ProveedorSerivice.findOne(id);
			
			obj.setIdProveedor(idProveedor);
			obj.setClaveProveedor(clave);
			obj.setIdInventario(idInventario);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			obj.setDias(dias);
			obj.setTipo(tipo);;
			ProveedorSerivice.save(obj);
			Float precio=  ProveedorSerivice.findOnePrecio(id);
			System.out.println("precio"+costo);
			System.out.println("precio de la base"+precio);
			
			if ( costo > precio || precio<costo ) {
				AmpInventarioProovedorPrecio objPrecio = new AmpInventarioProovedorPrecio();
				
				objPrecio.setIdProveedor(obj.getIdInventarioProveedor());
				objPrecio.setPrecio(Float.toString(costo));
				objPrecio.setCreadoPor(auth.getName());
				objPrecio.setFechaCreacion(hourdateFormat.format(date));
				objPrecio.setEstatus("1");
				ProveedorSerivice.savePrecio(objPrecio);
			}
			
			
		}
		return "hola";
	}
	
	@GetMapping("delete-proveedor-amp/{id}")
	public String deleteMaterial(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		AmpInventarioProovedor obj = ProveedorSerivice.findOne(id);
		obj.setEstatus("0");
		obj.setActualizadoPor(auth.getName());
		obj.setUltimaFechaModificacion(hourdateFormat.format(date));
		ProveedorSerivice.save(obj);
		redirectAttrs.addFlashAttribute("title", "Proveedor dado de baja correctamente").addFlashAttribute("icon", "success");
		return "redirect:/proveedores-inventario-amp/"+obj.getIdInventario()+"/"+obj.getTipo();
	}
	
	@RequestMapping(value = "/listar-linea", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> linea(Long id) {
		return LookService.listarLinea(id);
	}
}
