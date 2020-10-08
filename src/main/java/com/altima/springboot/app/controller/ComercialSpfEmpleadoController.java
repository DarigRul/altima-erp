package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.altima.springboot.app.models.entity.AdminConfiguracionPedido;
import com.altima.springboot.app.models.entity.ComercialCoordinado;
import com.altima.springboot.app.models.entity.ComercialCoordinadoForro;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinadoTela;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComercialSpfEmpleado;
import com.altima.springboot.app.models.service.IAdminConfiguracionPedidoService;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialSpfEmpleadoService;

@CrossOrigin(origins = { "*" })
@Controller
public class ComercialSpfEmpleadoController {
	
	@Autowired
	private IComercialSpfEmpleadoService SPFService;
	
	@Autowired
	private IComercialCoordinadoService CoordinadoService;
	
	@Autowired
	private ICargaPedidoService cargaPedidoService;
	
	@Autowired
	private IAdminConfiguracionPedidoService configService;

	@GetMapping("/empleados-spf/{id}")
	public String ListaEmpleadosSPF(@PathVariable(value = "id") Long id,Model model){
		
		
		model.addAttribute("list", SPFService.findAll(id));
		model.addAttribute("idPedidoSpf", id);
    	return"empleados-spf";
	}
	
	 @PostMapping("/guardar-spf-individual")
		public String guardarCliente(Long idPedidoSpf,Long idEmpleado, String nombreEmpleado ) {
	    	
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			ComercialSpfEmpleado obj = new ComercialSpfEmpleado();
			
			obj.setIdEmpleado(idEmpleado);
			obj.setIdPedidoSpf(idPedidoSpf);
			obj.setNombre_empleado(nombreEmpleado);
			obj.setCreadoPor(auth.getName());
			obj.setFechaCreacion(hourdateFormat.format(date));
			obj.setEstatus("1");
			SPFService.save(obj);	
	    	return "redirect:tickets";
	    }
	 
	 @RequestMapping(value = "/clientes-spf-disponibles", method = RequestMethod.GET)
		@ResponseBody
		public  List<Object[]> detalles(Long id) {
	    	
			return SPFService.empleados(id);
		}
		
		@RequestMapping(value = "/clientes-spf-buscar", method = RequestMethod.GET)
		@ResponseBody
		public  ComercialSpfEmpleado buscarSPf(Long id) {
	    	
			return SPFService.findOne(id);
		}
		
		@RequestMapping(value = "/clientes-spf-editar", method = RequestMethod.POST)
		@ResponseBody
		public boolean editar(Long idSpfEmpleado , String nombre ) {
	    	
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			ComercialSpfEmpleado obj = SPFService.findOne(idSpfEmpleado);
			obj.setNombre_empleado(nombre);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			SPFService.save(obj);	
			return true;
		}
		@RequestMapping(value = "/eliminar-spf/{id}", method = RequestMethod.GET)
		@ResponseBody
		public boolean eliminar( Long id) {
	    	
			SPFService.delete(id);
			return true;
		}

		@RequestMapping(value = "/agregar-spf-masivo", method = RequestMethod.POST)
		@ResponseBody
		public String masivo(Long idPedidoSpf) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			
			List<Object[]> auxlist =SPFService.empleados(idPedidoSpf);
			
			if ( auxlist.isEmpty() ) {
				return "Lista vacia";
			}else {
				for (Object[] a : auxlist) {
					ComercialSpfEmpleado obj = new ComercialSpfEmpleado();
					
					obj.setIdEmpleado(Long.parseLong(a[0].toString()));
					obj.setIdPedidoSpf(idPedidoSpf);
					obj.setNombre_empleado(a[1].toString());
					obj.setCreadoPor(auth.getName());
					obj.setFechaCreacion(hourdateFormat.format(date));
					obj.setEstatus("1");
					SPFService.save(obj);
				}
				return "Correcto";
			}
				 
		}
		
		
		@GetMapping("/agregar-falda-spf/{id}")
		public String addCoordinados(@PathVariable(value = "id") Long id,Map<String, Object> model,
				RedirectAttributes redirectAttrs, Model m) {
			ComercialPedidoInformacion pedido = cargaPedidoService.findOne(id);
			ComercialCoordinado coorSPF = CoordinadoService.findOneCoorSPF("COORSPF"+pedido.getIdPedido());
			m.addAttribute("idPrincipal",pedido.getIdPedido());
		
			if ( coorSPF == null) {
				System.out.println("entro al iffff");
				ComercialCoordinado coor = new ComercialCoordinado () ;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				Date date = new Date();
				DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				coor.setEstatus("2");
				coor.setIdPedido( pedido.getIdPedido());
				coor.setNumeroCoordinado(String.valueOf((1)));
				coor.setCreadoPor(auth.getName());
				coor.setFechaCreacion(hourdateFormat.format(date));
				coor.setUltimaFechaModificacion(hourdateFormat.format(date));
				coor.setIdText("COORSPF" + pedido.getIdPedido());
				CoordinadoService.save(coor);
				coorSPF= coor;
				
			}
			
			
			ComercialCoordinadoPrenda prenda = new ComercialCoordinadoPrenda();
			prenda.setIdCoordinado(coorSPF.getIdCoordinado());
			model.put("prenda", prenda);
			
			
			model.put("listPrendas", CoordinadoService.findAllPrenda("Falda",coorSPF.getIdText() ));
			model.put("listCoorPrenda", CoordinadoService.findAllCoorPrenda(coorSPF.getIdCoordinado()));

			
			m.addAttribute("idSPF", id);

			return "agregar-modelo-falda";
		}
		
		@RequestMapping(value = "/guardar-falda-spf", method = RequestMethod.POST)
		@ResponseBody
		public Integer guardar(@RequestParam(name = "datosMateriales") String datosMateriales,
				@RequestParam(name = "datosMateriales22") String datosMateriales22,
				@RequestParam(name = "datosMateriales222") String datosMateriales222,

				@RequestParam(name = "arrayId") String arrayid,

				Long idPrenda,

				Long idTela,

				Long idModelo,

				Long idCoordinado,
				Long idSPF,

				HttpServletRequest request) {
			
			System.out.println("se guarda desde SPF repito SPS");

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			System.out.println("Si entro al rest guardar guar guardarrrr coordinado");

			System.out.println("El id de prenda es: " + idPrenda);

			System.out.println("El id de tela es: " + idTela);

			System.out.println("Este es el arreglo: " + arrayid);

			// ProduccionPedido objetoPedido= servicePedido.findOne(idPedido);

			// String texto= objetoPedido.getIdText();

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
			objetoCoodinadoPrenda.setObservaciones("creado por el idSPF"+idSPF);
			objetoCoodinadoPrenda.setPrecio(CoordinadoService.precioPrenda(idCoordinado, idModelo, idTela));
			objetoCoodinadoPrenda.setPrecioFinal(CoordinadoService.precioPrenda(idCoordinado, idModelo, idTela));
			CoordinadoService.saveCoorPrenda(objetoCoodinadoPrenda);

			////// seccion2 TELAS

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

			//////// SECUION3 FORROS

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

			////// parte 3 materiales

			JSONArray json = new JSONArray(datosMateriales);
			String[] parts2 = arrayid.split(",");
			for (int j = 0; j < json.length(); j++) {

				ComercialCoordinadoMaterial material = new ComercialCoordinadoMaterial();
				JSONObject object = (JSONObject) json.get(j);

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

		
		@GetMapping("delete-falda-spf/{id}/{idSPF}")
		public String delete(@PathVariable("id") Long id, 
				@PathVariable("idSPF") Long idSPF,RedirectAttributes redirectAttrs) throws Exception {

			ComercialCoordinadoPrenda prenda = CoordinadoService.findOneCoorPrenda(id);
		
			if ( prenda.getObservaciones().equals("creado por el idSPF"+idSPF)) {
				CoordinadoService.deletePrenda(id);

				redirectAttrs.addFlashAttribute("title", "Eliminado correctamente").addFlashAttribute("icon", "success");

				return "redirect:/agregar-falda-spf/" + idSPF;
			}
			else {
				redirectAttrs.addFlashAttribute("title", "No es posible eliminar").addFlashAttribute("icon", "error");
				return "redirect:/agregar-falda-spf/" + idSPF;
			}
			
		}
}
