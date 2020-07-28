package com.altima.springboot.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoForro;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoMaterial;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoTela;
import com.altima.springboot.app.models.entity.ProduccionPedido;
import com.altima.springboot.app.models.entity.ProduccionPedidoColeccion;
import com.altima.springboot.app.models.service.IComercialClienteSucursalService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IProduccionDetallePedidoForroService;
import com.altima.springboot.app.models.service.IProduccionDetallePedidoMaterialService;
import com.altima.springboot.app.models.service.IProduccionDetallePedidoTelaService;
import com.altima.springboot.app.models.service.IProduccionDetalleService;
import com.altima.springboot.app.models.service.IProduccionPedidoColeccionService;
import com.altima.springboot.app.models.service.IProduccionPedidoService;

@CrossOrigin(origins = { "*" })
@Controller
public class GerencialComercialSolicitudController {
	
	
	@Autowired
	private IComercialClienteSucursalService serviceSucursal;
	
	@Autowired
	private IProduccionDetallePedidoMaterialService serviceProduccionDetalleMaterial;
	
	@Autowired
	private IProduccionPedidoService servicePedido;
	
	
	@Autowired
	private IProduccionDetalleService serviceDetallePedido;
	
	@Autowired
	private IProduccionDetallePedidoTelaService serviceDetalleTela;
	
	
	@Autowired
	private IProduccionDetallePedidoForroService serviceDetalleForro;
			 
			 @Autowired
			 private IProduccionPedidoColeccionService coleccionService;
		 
			 @Autowired
			 IDisenioLookupService disenioLookupService;

	@GetMapping("/solicitud-gerencial")
	public String listPre(Model model) {
		ProduccionPedido formpedido = new ProduccionPedido();
		List<ComercialClienteSucursal> listSucursales = serviceSucursal.findListaSucrusales();
		List<ProduccionPedido> lispedidos= servicePedido.findListapedidos();
		model.addAttribute("formpedido", formpedido);
		model.addAttribute("listSucur", listSucursales);		
		model.addAttribute("lispedidos", lispedidos);	
		return "solicitud-gerencial";
	}
	
	
	
	
	
	
	@GetMapping("/solicitud-gerencial-muestras/{id}")
	
	
	public String listPrexd(Model model,@PathVariable (value="id")	 Long id ) {
		
		
		ProduccionDetallePedido objetoDetalle = new ProduccionDetallePedido();
		
		objetoDetalle.setIdPedido(id);
		
		List<DisenioLookup> listPrendas = servicePedido.findAllPrenda();
		model.addAttribute("listPrendas", listPrendas);
		model.addAttribute("objetoDetalle", objetoDetalle);
		
		System.out.println("si se ejecuto el id" +id);
		
		
		List<ProduccionDetallePedido> listtabla = serviceDetallePedido.tabla(id);
		
		model.addAttribute("listtabla", listtabla);
		
		
		return "solicitud-gerencial-muestras";
	}
	
	
				
	@PostMapping("/guardar-pedido-solicitud")
	public String guardarPrePedido(@ModelAttribute ProduccionPedido formpedido, RedirectAttributes redirectAttrs,
			 MultipartFile imagenMaterial) {
		
		
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//	forro.setFechaCreacion(hourdateFormat.format(date));
		
		System.out.println("si entro al guardar" + formpedido.getTipoPedido());				
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String tipo = formpedido.getTipoPedido();
		
		
		
		if (tipo.equals("2")) {
			formpedido.setDescripcion("Pedido Coleccion");	
		} else {
			
			formpedido.setDescripcion("Pedido Gerencial");	

		}
		
			
		//formpedido.setEstatusDiferencia("2");	
		formpedido.setFechaCreacion(hourdateFormat.format(date));
		formpedido.setEstatusPedido("0");
		int count = servicePedido.contadorPedidos();	
		DecimalFormat df = new  DecimalFormat ("00000");
		//count= count+10000;
		formpedido.setIdText("PED" + df.format(count));	
		formpedido.setCreadoPor(auth.getName());

		servicePedido.save(formpedido);		
		System.out.println("si se ejecuto el save");	        
		return "redirect:solicitud-gerencial";
			
				
		
	}
	
	
	
	
	@RequestMapping(value ="/guardar-pedido-interno-rest", method = RequestMethod.POST)
	@ResponseBody
	public Integer  guardar(
    @RequestParam(name = "datosMateriales")String datosMateriales,
    @RequestParam(name = "datosMateriales22")String datosMateriales22,
    @RequestParam(name = "datosMateriales222")String datosMateriales222,
    
    @RequestParam(name = "arrayId") String arrayid,
    
    
	Long  idPrenda, 
	Long idModelo,
	int cantidad,
	Long idTela, 
	Long idGeneroinsert, 
	Long idPedido,
	String idLargo,
	HttpServletRequest request,
	String idTalla) {
		
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		
		System.out.println("Si entro al rest guardar guar guardarrrr");
		
		System.out.println("El id de prenda es: "+idPrenda);
		System.out.println("El id de modelo es: "+idModelo);
		System.out.println("El id de tela es: "+idTela);
		System.out.println("El id del pedido: "+idPedido);
		System.out.println("La cantidad: "+cantidad);
		System.out.println("La cantidad: "+cantidad);
		System.out.println("aqui est el genero inseert "+idGeneroinsert);
		
		
		System.out.println("Este es el arreglo: "+arrayid);
		
		ProduccionPedido objetoPedido= servicePedido.findOne(idPedido);
		
		String texto= objetoPedido.getIdText();
		
		
		for (int i = 0; i < cantidad; i++) {
			
			ProduccionDetallePedido objetoDetalle = new ProduccionDetallePedido();
			
			System.out.println("el for" + i);
			
			objetoDetalle.setIdPedido(idPedido);
			objetoDetalle.setIdTela(idTela);
			objetoDetalle.setTalla(idTalla);
			objetoDetalle.setLargo(idLargo);
			objetoDetalle.setDescripcion("Pedido Gerencial");
			objetoDetalle.setIdText(texto + "-" + i);
			objetoDetalle.setCreadoPor(auth.getName());
			objetoDetalle.setFechaCreacion(hourdateFormat.format(date));
			objetoDetalle.setEstatus_confeccion("0");
			objetoDetalle.setEstatus("1");
			objetoDetalle.setIdPrenda(idModelo);
			objetoDetalle.setIdFamiliaPrenda(idPrenda);
			objetoDetalle.setCosto("0");
			objetoDetalle.setCantidad("1");
			objetoDetalle.setActualizadoPor("user");
			objetoDetalle.setIdFamiliaGenero(idGeneroinsert);
			
			
			serviceDetallePedido.save(objetoDetalle);
			
			
			if (i==0) {
				
				JSONArray json2 = new JSONArray (datosMateriales22);
				for (int k = 0; k < json2.length(); k++) {
					ProduccionDetallePedidoTela detalleTela = new  ProduccionDetallePedidoTela();
		            JSONObject object =(JSONObject) json2.get(k);
		            String id = object.get("id_tela").toString();  		         	            
		            detalleTela.setIdTela(Long.parseLong(id));	           
		            detalleTela.setIdDetallePedido(objetoDetalle.getIdDetallePedido());
		            detalleTela.setCreado_por(auth.getName());
		            detalleTela.setActualizadoPor("User");
		            detalleTela.setFechaCreacion(hourdateFormat.format(date));
		            detalleTela.setUltimaFechaModificacion(null);
		            serviceDetalleTela.save(detalleTela);
		    		
		             }
				
				
				
				JSONArray json22 = new JSONArray (datosMateriales222);
				for (int h = 0; h < json22.length(); h++) {
					ProduccionDetallePedidoForro detalleForro = new  ProduccionDetallePedidoForro();
		            JSONObject object =(JSONObject) json22.get(h);
		            String id = object.get("id_forro").toString();  
		          
		            
		            detalleForro.setIdForro(Long.parseLong(id));
		         
		    
		            detalleForro.setIdDetallePedido(objetoDetalle.getIdDetallePedido());
		            detalleForro.setCreado_por(auth.getName());
		            detalleForro.setActualizadoPor("user");
		            detalleForro.setFechaCreacion(hourdateFormat.format(date));
		            detalleForro.setUltimaFechaModificacion(null);
		            serviceDetalleForro.save(detalleForro);
		             }
				
				
				JSONArray json = new JSONArray (datosMateriales);
				String[] parts2 = arrayid.split(",");
				for (int j = 0; j < json.length(); j++) {
					  
					ProduccionDetallePedidoMaterial material = new  ProduccionDetallePedidoMaterial();
		            JSONObject object =(JSONObject) json.get(j);
		          
		            
		            
		            
		            String color = object.get("color").toString();  
		            String[] parts = color.split("_");
		            
		            material.setIdMaterial(Long.parseLong(parts2[j]));
		            material.setColor(parts[0]);
		            material.setColorCodigo(parts[1]);
		            material.setIdPedidoDetalle(objetoDetalle.getIdDetallePedido());
		            material.setCreadoPor(auth.getName());
		            material.setActualizadoPor(null);
		    		material.setFechaCreacion(hourdateFormat.format(date));
		    		material.setUltimaFechaModificacion(null);
		    		serviceProduccionDetalleMaterial.save(material);
				    
		             }
				
			}
			
					
			
		}
		
		 
		return 1;
	
	}
	

	
	
	
	@RequestMapping(value = "/pedido-detalles-material", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> detalles_material(Long id) {
		System.out.println("Detalles materiales");
		return  serviceDetallePedido.detallesMatariales(id);
	}
	
	
	
	
	@RequestMapping(value = "/get_agente_ventas", method = RequestMethod.GET)
	@ResponseBody
	public String agente(Long id) {
		System.out.println("Detalles materiales");
		return  serviceDetallePedido.nombreAgente(id);
	}	
	@GetMapping("/eliminar-pedido-interno/{id}/{idp}")
	public String eliminarSolicitud(@PathVariable(value="id") Long id,@PathVariable(value="idp") Long idp) {
		serviceDetallePedido.deleteByIdDetalle(id, idp);
		return "redirect:/solicitud-gerencial-muestras/"+idp;
	}
	
	
	@RequestMapping(value = "/mostrar-materiales-prenda-extra", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> materiales(Long id) {
		
		
		System.out.println("Entro al resttttttttttttt");
		return  serviceDetallePedido.materialesPorPrendaExtra(id);	}
	
	
	
	
	@RequestMapping(value = "/mostrar-materiales-prenda-extra2", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> materiales2(Long id) {
		
		
		System.out.println("Entro al resttttttttttttt2");
		return  serviceDetallePedido.materialesPorPrendaExtra2(id);	}
	
	
	
	@RequestMapping(value = "/mostrar-listas-tela", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> tela(Long id) {
		
		System.out.println("entro al rest de  tela");
		
		return  serviceDetallePedido.findAllTela(id);
	}
	
	
	@RequestMapping(value = "/mostrar-listas-forro", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> forro(Long id) {
		
		System.out.println("entro al rest de  Forro");
		
		return  serviceDetallePedido.findAllForro(id);
	}
	
	
	@RequestMapping(value = "/mostrar-lista-materiales-extra", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> materialesEstraLista(Long idPrendaMaterial) {
		
		System.out.println("entro al rest de  Forro");
		
		return  serviceDetallePedido.findListMatEx(idPrendaMaterial);
	}
	
	
	@RequestMapping(value = "/mostrar-tela-primera", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> telaPrimera(Long id) {
		
		return  serviceDetallePedido.findAllTelaPrimera(id);
	}
	
	
	@RequestMapping(value = "/cantidad-prendas", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> detalleModalPrendas(Long id) {
		System.out.println("si entre al rest taabla");
		return  servicePedido.tablaModal(id);
	}
	
	
	@RequestMapping(value = "/total", method = RequestMethod.GET)
	@ResponseBody
	public int total(Long id2) {
		System.out.println("si entre al rest de total");
		return  servicePedido.total(id2);
	}
	
	
	
	
	/////////////////////////COLECCION
	
	
	@RequestMapping(value = "/mostrar-modelo-mejorado", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> modelo(Long id, String genero) {
		System.out.println("entre al res controller de modelo mejorado");
		return  servicePedido.findAllModeloColeccion(id, genero);
	}
	

	
	@RequestMapping(value ="/guardar-pedido-coleccion-rest", method = RequestMethod.POST)
	@ResponseBody
	public Integer  guardarColeccion(
   
	Long  idPrenda, 

	int cantidad,
	
	Long idPedido,
	
	HttpServletRequest request
	) {
		
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		
		System.out.println("Si entro al rest guardar guar guardarrrr");
		
		System.out.println("El id de prenda es: "+idPrenda);
		System.out.println("El id del pedido: "+idPedido);
		System.out.println("La cantidad: "+cantidad);

		
		

		
		ProduccionPedido objetoPedido= servicePedido.findOne(idPedido);
		
		String texto= objetoPedido.getIdText();
		
		
		for (int i = 0; i < cantidad; i++) {
			
			ProduccionDetallePedido objetoDetalle = new ProduccionDetallePedido();
			
			System.out.println("el for" + i);
			
			objetoDetalle.setIdPedido(idPedido);
	
			
			
			objetoDetalle.setDescripcion("Pedido Gerencial coleccion");
			objetoDetalle.setIdText(texto + "-" + i);
			objetoDetalle.setCreadoPor(auth.getName());
			objetoDetalle.setFechaCreacion(hourdateFormat.format(date));
			objetoDetalle.setEstatus_confeccion("0");
			objetoDetalle.setEstatus("1");			
			objetoDetalle.setIdFamiliaPrenda(idPrenda);
			objetoDetalle.setCosto("0");
			objetoDetalle.setCantidad("1");
			objetoDetalle.setActualizadoPor("user");
			
			
			serviceDetallePedido.save(objetoDetalle);
			
						
			
		}
		
		 
		return 1;
	
	}
	
	
	@GetMapping("/editar-pedido-coleccion")
	public String editColeccion1() {
		
		
		return"editar-pedido-coleccion";
	}
	
	
	
	@GetMapping("/editar-pedido-coleccion/{id}")
	public String editColeccion(@PathVariable (value="id")	 Long id, Model model) {
		
		System.out.println("entreal edita par mandar lista");
		
		
		
		ProduccionDetallePedido objetoDetalle = new ProduccionDetallePedido();
		
		objetoDetalle.setIdPedido(id);
		
		//List <Object[]> listSelectPrenda = serviceDetallePedido.selectPrenda(id);
		
		List <Object[]>listPrendas = serviceDetallePedido.selectPrenda(id);
		model.addAttribute("listPrendas", listPrendas);
		model.addAttribute("objetoDetalle", objetoDetalle);
		
		List<ProduccionDetallePedido> listtabla = serviceDetallePedido.tabla(id);
		
		model.addAttribute("listtabla", listtabla);
		//model.addAttribute("listSelectPrenda", listSelectPrenda);
		
		
		
		
		
		
		return"/editar-pedido-coleccion";
	}
	
	///////////////////////// COLECCION

	@GetMapping("solicitud-muestras-coleccion/{id}")
	public String SolicitudColeccion(Model model, @PathVariable(value = "id") Long id) {
		ProduccionPedidoColeccion coleccion = new ProduccionPedidoColeccion();

		coleccion.setIdPedido(id);
		model.addAttribute("listPrendas",  disenioLookupService.findByTipoLookup("Familia Prenda"));
		model.addAttribute("coleccion", coleccion);
		model.addAttribute("generos", disenioLookupService.findByTipoLookup("Familia Genero"));
		System.out.println("si se ejecuto el id de coleccion" + id);

		model.addAttribute("listtabla", coleccionService.findAllDetail(id));

		return "solicitud-muestras-coleccion";
	}

	@PostMapping("/guardar-coleccion")
	public String guardarColeccion(ProduccionPedidoColeccion coleccion) {

		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		// forro.setFechaCreacion(hourdateFormat.format(date));

		System.out.println("si entro al guardar");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		coleccion.setCreadoPor(auth.getName());
		coleccion.setEstatus("1");
		try {
			coleccionService.save(coleccion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("si se ejecuto el save");	        
		return "redirect:/solicitud-muestras-coleccion/"+coleccion.getIdPedido();

	}
	@GetMapping("eliminar-coleccion/{idp}/{id}")
	public String eliminarColeccion(Model model, @PathVariable(value = "id") Long id, @PathVariable(value = "idp") Long idp) {
		ProduccionPedidoColeccion coleccion = new ProduccionPedidoColeccion();
		coleccionService.delete(id);
		return "redirect:/solicitud-muestras-coleccion/"+idp;
	}
	
	
	@RequestMapping(value = "/maximo-coleccion", method = RequestMethod.GET)
	@ResponseBody
	public int maximo(Long idPedido, Long idFamPrenda, String genero) {
		
		System.out.println("si entre al rest de maximo"+ idPedido);
		System.out.println("si entre al rest de maximo"+ genero);
		System.out.println("si entre al rest de maximo"+ idFamPrenda);
		System.out.println("si entre al rest de maximo");
		return  servicePedido.contadorColeccion(idPedido, idFamPrenda, genero);
	}
	
	
	@RequestMapping(value = "/count-detalle-coleccion", method = RequestMethod.GET)
	@ResponseBody
	public int contadorDetalle(Long idPedido, Long idFamPrenda, String genero) {
		System.out.println("si entre al rest de contador e detalle");
		return  servicePedido.contadorDetallePedido(idPedido, idFamPrenda, genero);
	}
	
	@RequestMapping(value = "/obtener-id-genero", method = RequestMethod.GET)
	@ResponseBody
	public int idGenero
	(Long idPedido, Long idFamPrenda, String genero) {
		System.out.println("si entre al rest de contador e detalle");
		return  servicePedido.idGenero(idPedido, idFamPrenda, genero);
	}
	
	
	@GetMapping("/eliminar-pedido-interno-coleccion/{id}/{idp}")
	public String eliminarSolicitudColeccion(@PathVariable(value="id") Long id,@PathVariable(value="idp") Long idp) {
		//serviceDetallePedido.deleteByIdDetalle(id, idp);
		
		serviceDetallePedido.delete(id);
		return "redirect:/editar-pedido-coleccion/"+idp;
	}
	@RequestMapping(value = "/cantidad-prendas-coleccion", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> detalleModalPrendasColeccion(Long id) {
		System.out.println("si entre al rest taabla");
		return  coleccionService.findAllDetail(id);
	}
	
	@GetMapping("/solicitud-muestras-aceptar/{id}")
	public String aceptar(@PathVariable (value="id")	 Long id, Model model, RedirectAttributes redirectAttrs) {
		
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ProduccionPedido objetoPedido = servicePedido.findOne(id);
		objetoPedido.setEstatusPedido("1");
		objetoPedido.setActualizadoPor(auth.getName());
		objetoPedido.setUltimaFechaModificacion(hourdateFormat.format(date));
		servicePedido.save(objetoPedido);
		redirectAttrs.addFlashAttribute("title", "Actualizado correctamente").addFlashAttribute("icon", "success");
		
		
		return "redirect:/solicitud-gerencial";
	}
	
	
	@GetMapping("/solicitud-muestras-rechazar/{id}")
	public String rechazar(@PathVariable (value="id")	 Long id, Model model, RedirectAttributes redirectAttrs) {
		
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ProduccionPedido objetoPedido = servicePedido.findOne(id);
		objetoPedido.setEstatusPedido("2");
		objetoPedido.setActualizadoPor(auth.getName());
		objetoPedido.setUltimaFechaModificacion(hourdateFormat.format(date));
		servicePedido.save(objetoPedido);
		redirectAttrs.addFlashAttribute("title", "Actualizado correctamente").addFlashAttribute("icon", "success");
		
		
		return "redirect:/solicitud-gerencial";
	}
}



