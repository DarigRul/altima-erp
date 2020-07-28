package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.models.entity.ComercialCoordinadoForro;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinadoTela;
import com.altima.springboot.app.models.entity.ProduccionDetallePedido;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoForro;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoMaterial;
import com.altima.springboot.app.models.entity.ProduccionDetallePedidoTela;
import com.altima.springboot.app.models.entity.ProduccionPedido;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;

@CrossOrigin(origins = { "*" })
@Controller
public class CoordinadoController {
	@Autowired
	private  IComercialCoordinadoService CoordinadoService;
	
	@GetMapping("/coordinados/{id}")
	public String listCoordinados(@PathVariable(value = "id") Long id,Model model) {
		model.addAttribute("coordinados", CoordinadoService.findAllEmpresa(id));
	
		model.addAttribute("id_pedido", id);
		return "coordinados";
	}
	
	@RequestMapping(value = "/mostrar-modelo", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> modelo(Long id) {
		return  CoordinadoService.findAllModelo(id);
	}
	
	
	
	@RequestMapping(value = "/mostrar-tela", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> tela(Long id) {
		
		return  CoordinadoService.findAllTela(id);
	}
	
	@RequestMapping(value = "/mostrar-materiales-prenda", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> materiales(Long id) {
		return  CoordinadoService.materialesPorPrenda(id);
	}
	
	
	@RequestMapping(value = "/mostrar-ruta-imagen", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []>  imagen(Long id , String tipo, String tipo2) {
		return  CoordinadoService.ImagenesRuta(id, tipo, tipo2);
	}
	
	@RequestMapping(value = "/mostrar-ruta-imagen-tela", method = RequestMethod.GET)
	@ResponseBody
	public String imagen(Long id) {
		return  CoordinadoService.ImagenesRutaTela(id);
	}
	
	
	@RequestMapping(value = "/mostrar-materiales-colores", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> colores(Long idMaterial, Long idTela , Long idCoorPrenda) {
		
		
		System.out.println("material: "+idMaterial);
		System.out.println("tela: "+idTela);
		System.out.println("idCoorPrenda: "+idCoorPrenda);
		return  CoordinadoService.coloresMateriales(idMaterial, idTela, idCoorPrenda);
	}
	
	@RequestMapping(value = "/detalles-material", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> detalles_material(Long id) {
		System.out.println("Detalles materiales");
		return  CoordinadoService.detallesMatariales(id);
	}
	
	@GetMapping("/agregar-coordinado/{id}")
	public String addCoordinados(@PathVariable (value="id") Long id, Map<String, Object> model) {
		ComercialCoordinadoPrenda prenda = new ComercialCoordinadoPrenda();
		prenda.setIdCoordinado(id);
		model.put("prenda", prenda);
		model.put("listPrendas", CoordinadoService.findAllPrenda());
		
		model.put("listCoorPrenda", CoordinadoService.findAllCoorPrenda(id));
		return "agregar-coordinado";
	}
	
	@GetMapping("/guardar-coordinado/{id}")
	public String editar(@PathVariable (value="id") Long id, ComercialCoordinado coor, RedirectAttributes redirectAttrs) {
		
		Integer contador = CoordinadoService.ContadorCoordinadoCliente(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		coor.setEstatus("1");
		coor.setIdPedido(id);
		//coor.setIdCliente(id);
		coor.setNumeroCoordinado(String.valueOf((contador+1)));
		coor.setCreadoPor(auth.getName());
		coor.setFechaCreacion(hourdateFormat.format(date));
		coor.setUltimaFechaModificacion(hourdateFormat.format(date));
		coor.setIdText("COOR"+ ((contador+1) +100));
		CoordinadoService.save(coor);
		
		redirectAttrs
        .addFlashAttribute("title", "Agregado correctamente")
        .addFlashAttribute("icon", "success");
		return "redirect:/coordinados/"+coor.getIdPedido();
	}
	
	
	@GetMapping("/eliminar-coordinado/{id}")
	public String elminar(@PathVariable (value="id") Long id, RedirectAttributes redirectAttrs) {
		
		ComercialCoordinado coor= CoordinadoService.findOne(id);
		Long pedido = coor.getIdPedido();
		
		CoordinadoService.deleteTotal(id);
		redirectAttrs
        .addFlashAttribute("title", "Eliminado correctamente")
        .addFlashAttribute("icon", "success");
		
		
		return "redirect:/coordinados/"+pedido;
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/guardar-coordinado-prenda", method = RequestMethod.POST)
	@ResponseBody
	public List<Object []> guardar(@RequestParam(name = "datosMateriales") String datosMateriales, Long  idPrenda, Long idModelo, Long idTela, Long idCoor,HttpServletRequest request
			,Long id_coordinado_prenda) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("Si entro");
		
		System.out.println("El id de prenda es: "+idPrenda);
		System.out.println("El id de modelo es: "+idModelo);
		System.out.println("El id de tela es: "+idTela);
		System.out.println("El id del coor es: "+idCoor);
		
		System.out.println("El id del coor es: "+datosMateriales);
		
		System.out.println("El id_coordinado_prenda es: "+id_coordinado_prenda); 
		
		if (id_coordinado_prenda ==null ) {
			ComercialCoordinadoPrenda prenda = new  ComercialCoordinadoPrenda();
			prenda.setIdFamilaGenero(idPrenda);// es la famila
			prenda.setIdPrenda(idModelo);
			prenda.setIdTela(idTela);
			prenda.setIdCoordinado(idCoor);
			prenda.setCreadoPor(auth.getName());
			prenda.setActualizadoPor(null);
			prenda.setEstatus("1");
			prenda.setAdicional("0.00");
			prenda.setMontoAdicional("0.00");
			prenda.setPrecioFinal("0.00");
			prenda.setFechaCreacion(hourdateFormat.format(date));
			prenda.setUltimaFechaModificacion(null);
			CoordinadoService.saveCoorPrenda(prenda);
			
			JSONArray json = new JSONArray (datosMateriales);
			for (int i = 0; i < json.length(); i++) {
				ComercialCoordinadoMaterial material = new  ComercialCoordinadoMaterial();
	            JSONObject object =(JSONObject) json.get(i);
	            String id = object.get("id_material").toString();  
	            String color = object.get("color").toString();  
	            String[] parts = color.split("_");
	            
	            material.setIdMaterial(Long.parseLong(id));
	            material.setColor(parts[0]);
	            material.setColorCodigo(parts[1]);
	            material.setIdCoordinadoPrenda(prenda.getIdCoordinadoPrenda());
	            material.setCreadoPor(auth.getName());
	            material.setActualizadoPor(null);
	    		material.setFechaCreacion(hourdateFormat.format(date));
	    		material.setUltimaFechaModificacion(null);
	    		CoordinadoService.saveCoorMaterial(material);
	             }
		}
		else {
			ComercialCoordinadoPrenda prenda=  CoordinadoService.findOneCoorPrenda(id_coordinado_prenda);
			prenda.setIdFamilaGenero(idPrenda);// es la famila
			prenda.setIdPrenda(idModelo);
			prenda.setIdTela(idTela);
			prenda.setIdCoordinado(idCoor);
			prenda.setActualizadoPor(auth.getName());
			prenda.setUltimaFechaModificacion(hourdateFormat.format(date));
			CoordinadoService.saveCoorPrenda(prenda);
			
			
			JSONArray json = new JSONArray (datosMateriales);
			for (int i = 0; i < json.length(); i++) {
				JSONObject object =(JSONObject) json.get(i);
	            String id = object.get("id_material").toString();  
	            String color = object.get("color").toString();  
	            String[] parts = color.split("_");
	          
	            System.out.println("id: "+id);
			    System.out.println("color: "+color);
			    ComercialCoordinadoMaterial material=  CoordinadoService.findOneCoorMaterial(prenda.getIdCoordinadoPrenda(), Long.valueOf(id));
				if( material != null) {
					material.setIdMaterial(Long.parseLong(id));
					material.setColor(parts[0]);
					material.setColorCodigo(parts[1]);
					material.setActualizadoPor(auth.getName());
					material.setUltimaFechaModificacion(hourdateFormat.format(date));
		    		CoordinadoService.saveCoorMaterial(material);
				}
				else{
							
						System.out.println("no existe registrado");
						ComercialCoordinadoMaterial material2 = new  ComercialCoordinadoMaterial();
						 material2.setIdMaterial(Long.parseLong(id));
				         material2.setColor(parts[0]);
				         material2.setColorCodigo(parts[1]);
				         material2.setIdCoordinadoPrenda(prenda.getIdCoordinadoPrenda());
				         material2.setCreadoPor(auth.getName());
				         material2.setActualizadoPor(null);
				         material2.setFechaCreacion(hourdateFormat.format(date));
				         material2.setUltimaFechaModificacion(null);
				    	CoordinadoService.saveCoorMaterial(material2);
				}
	            
	           
			   
	             }
			
			
					
			/* List<ComercialCoordinadoMaterial> list = CoordinadoService.findAllCoorMaterial(prenda.getIdCoordinadoPrenda());
			 JSONArray json = new JSONArray (datosMateriales);
			 JSONArray array = new JSONArray();
			 Integer i = 0;
			 String omitir = "";
				for (ComercialCoordinadoMaterial  a : list) {
			            JSONObject object =(JSONObject) json.get(i);
			            String id = object.get("id_material").toString();  
			            String color = object.get("color").toString();  
			            String[] parts = color.split("_");
			          
			            if (id.equals(Long.toString(a.getIdMaterial())) ) {
			      
			            	a.setIdMaterial(Long.parseLong(id));
				            a.setColor(parts[0]);
				            a.setColorCodigo(parts[1]);
				            a.setActualizadoPor(auth.getName());
				    		a.setUltimaFechaModificacion(hourdateFormat.format(date));
				    		CoordinadoService.saveCoorMaterial(a);
				    		
				    		omitir+=""+i+",";
				    		
				    		
				    		System.out.println("if del json con el objeto lista :"+i);
			            }else {
			            	System.out.println("else del json con el objeto lista");
			            	CoordinadoService.deleteMaterial(a);;
			            }
			            System.out.println(i);
			            
			    	
			             i++;
				}
				
				
				
				 
				 System.out.println("-------------------------------------------------------");
				 System.out.println(omitir);
				 String[] arrayOmitir = omitir.split(",");
				
				 for (int j = 0; j < arrayOmitir.length; j++) {
					ComercialCoordinadoMaterial material = new  ComercialCoordinadoMaterial();
		            JSONObject object =(JSONObject) json.get(Integer.parseInt(arrayOmitir[j]));
		            String id = object.get("id_material").toString();  
		            String color = object.get("color").toString();  
		            String[] parts = color.split("_");
		          
		            	 System.out.println("id: "+id);
				         System.out.println("color: "+color);
		            
		           
				    material.setIdMaterial(Long.parseLong(id));
		            material.setColor(parts[0]);
		            material.setColorCodigo(parts[1]);
		            material.setIdCoordinadoPrenda(prenda.getIdCoordinadoPrenda());
		            material.setCreadoPor(auth.getName());
		            material.setActualizadoPor(null);
		    		material.setFechaCreacion(hourdateFormat.format(date));
		    		material.setUltimaFechaModificacion(null);
		    		CoordinadoService.saveCoorMaterial(material);
		             }*/
				
		} 
		return  CoordinadoService.findAllTela(idPrenda);
	}
	
	@GetMapping("delete-modelo/{id}") 
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) throws Exception {
		
		ComercialCoordinadoPrenda prenda = CoordinadoService.findOneCoorPrenda(id);
		
		CoordinadoService.deletePrenda(id);
		
		Long id_coordinado = prenda.getIdCoordinado();
		redirectAttrs
        .addFlashAttribute("title", "Eliminado correctamente")
        .addFlashAttribute("icon", "success");
		
		
		  return "redirect:/agregar-coordinado/"+id_coordinado ;
	}
	
	@RequestMapping(value = "/update-coor-prenda", method = RequestMethod.GET)
	@ResponseBody
	public ComercialCoordinadoPrenda update(Long id) {
		System.out.println("hola Humanoo soy update el id es : "+id);
		ComercialCoordinadoPrenda prenda= CoordinadoService.findOneCoorPrenda(id);
		return  prenda;
	}
	
	
	
//////////////////////////////////********NUEVO COORDINADO LOCO*********///////////////////


	@RequestMapping(value = "/guardar-pedido-interno-rest-coordinado", method = RequestMethod.POST)
	@ResponseBody
	public Integer guardar(@RequestParam(name = "datosMateriales") String datosMateriales,
			@RequestParam(name = "datosMateriales22") String datosMateriales22,
			@RequestParam(name = "datosMateriales222") String datosMateriales222,

			@RequestParam(name = "arrayId") String arrayid,

			Long idPrenda,

			Long idTela,
			
			Long idModelo,

			Long idCoordinado,

			HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		System.out.println("Si entro al rest guardar guar guardarrrr coordinado");

		System.out.println("El id de prenda es: " + idPrenda);

		System.out.println("El id de tela es: " + idTela);

		System.out.println("Este es el arreglo: " + arrayid);

//ProduccionPedido objetoPedido= servicePedido.findOne(idPedido);

//String texto= objetoPedido.getIdText();

		ComercialCoordinadoPrenda objetoCoodinadoPrenda = new ComercialCoordinadoPrenda();
		
		objetoCoodinadoPrenda.setIdFamilaGenero(idPrenda);		
		objetoCoodinadoPrenda.setIdPrenda(idModelo);
		objetoCoodinadoPrenda.setIdTela(idTela);
		objetoCoodinadoPrenda.setIdCoordinado(idCoordinado);
		objetoCoodinadoPrenda.setAdicional("0");
		objetoCoodinadoPrenda.setMontoAdicional("0");
		objetoCoodinadoPrenda.setPrecioFinal("0");
		objetoCoodinadoPrenda.setEstatus("1");	
		objetoCoodinadoPrenda.setCreadoPor(auth.getName());
		objetoCoodinadoPrenda.setFechaCreacion(hourdateFormat.format(date));
	
		
		CoordinadoService.saveCoorPrenda(objetoCoodinadoPrenda);
		
	//////seccion2 TELAS

			JSONArray json2 = new JSONArray(datosMateriales22);
			for (int k = 0; k < json2.length(); k++) {
				ComercialCoordinadoTela detalleTela = new ComercialCoordinadoTela();
				JSONObject object = (JSONObject) json2.get(k);
				String id = object.get("id_tela").toString();
				
			
				
				detalleTela.setIdTela(Long.parseLong(id));
				detalleTela.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
				detalleTela.setCreado_por(auth.getName());
				detalleTela.setActualizadoPor("User");
				detalleTela.setFechaCreacion(hourdateFormat.format(date));
				detalleTela.setUltimaFechaModificacion(null);
				CoordinadoService.saveTelaMaterial(detalleTela);
				
			

			}
			
			
			////////SECUION3 FORROS
			
			
			JSONArray json22 = new JSONArray(datosMateriales222);
			for (int h = 0; h < json22.length(); h++) {
				ComercialCoordinadoForro detalleForro = new ComercialCoordinadoForro();
				JSONObject object = (JSONObject) json22.get(h);
				String id = object.get("id_forro").toString();

				detalleForro.setIdForro(Long.parseLong(id));
				detalleForro.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
				
				detalleForro.setCreado_por(auth.getName());
				detalleForro.setActualizadoPor("user");
				detalleForro.setFechaCreacion(hourdateFormat.format(date));
				detalleForro.setUltimaFechaModificacion(null);
				CoordinadoService.saveForroMaterial(detalleForro);
			}
		
			//////parte 3 materiales
		
			JSONArray json = new JSONArray (datosMateriales);
			String[] parts2 = arrayid.split(",");
			for (int j = 0; j < json.length(); j++) {
				  
				ComercialCoordinadoMaterial material = new  ComercialCoordinadoMaterial();
	            JSONObject object =(JSONObject) json.get(j);
	          
	            
	            
	            
	            String color = object.get("color").toString();  
	            String[] parts = color.split("_");
	            
	            material.setIdMaterial(Long.parseLong(parts2[j]));
	            material.setColor(parts[0]);
	            material.setColorCodigo(parts[1]);
	            material.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
	            material.setCreadoPor(auth.getName());
	            material.setActualizadoPor(null);
	    		material.setFechaCreacion(hourdateFormat.format(date));
	    		material.setUltimaFechaModificacion(null);
	    		CoordinadoService.saveCoorMaterial(material);
	    		
			    
	             }
			
		
		
		
		
		
		

		
		
		
		
		
	
		
		
		

		return 1;

	}

}






