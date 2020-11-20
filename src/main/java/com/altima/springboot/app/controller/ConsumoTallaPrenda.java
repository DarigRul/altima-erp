package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ProduccionConsumoTalla;
import com.altima.springboot.app.models.entity.ProduccionConsumoTallaCombinacionTela;
import com.altima.springboot.app.models.entity.ProduccionConsumoTallaEntretela;
import com.altima.springboot.app.models.entity.ProduccionConsumoTallaForro;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaCombinacionTelaService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaEntretelaService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaForroService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaService;

@CrossOrigin(origins = { "*" })
@Controller
public class ConsumoTallaPrenda {

	@Autowired
	private IProduccionConsumoTallaService ConsumoService;
	@Autowired
	private IProduccionConsumoTallaForroService ConsumoForroService;
	@Autowired
	private IProduccionConsumoTallaCombinacionTelaService ConsumocombinacionService;
	
	@Autowired
	private IProduccionConsumoTallaEntretelaService EntretelaService;

	@Secured({"ROLE_ADMINISTRADOR","ROLE_DISENIO_PRENDAS_CONSUMO"})
	@GetMapping("/consumo-talla-prenda/{id}")
	public String vista (@PathVariable(value = "id") Long id, Model model) {
		
		model.addAttribute("materiales", ConsumoService.Materiales_Prenda(id));

		model.addAttribute("id_prenda", id);
		return "consumo-talla-prenda";
	}
	
	
	@GetMapping("/consumo-talla-prenda/{id}/{tipo}/{idExtra}")
	public String vistas (@PathVariable(value = "id") Long id, Model model, @PathVariable(value = "tipo") String tipo,@PathVariable(value = "idExtra") Long idExtra) {
		
		model.addAttribute("materiales", ConsumoService.Materiales_Prenda(id));
		model.addAttribute("id_prenda", id);
		
		model.addAttribute("id_tela_combinacion", idExtra);
		
		if ( tipo.equals("Tela")) {
			List<String> list = new ArrayList<>();

			for (String d : ConsumoService.largo()) {
				list.add(d);
			}
			model.addAttribute("head", list);
			model.addAttribute("headTam", list.size());
			model.addAttribute("listConsumo", ConsumoService.Consumo_Talla(id, ConsumoService.genpivot(list)));
			model.addAttribute("listConsumo_id", ConsumoService.Consumo_Talla_id(id));
			model.addAttribute("action",1);  
			return "consumo-talla-prenda";
		}
		if ( tipo.equals("Forro")) {
			List<String> list = new ArrayList<>();

			for (String d : ConsumoService.largo()) {
				list.add(d);
			}
			model.addAttribute("head", list);
			model.addAttribute("headTam", list.size());
			model.addAttribute("listConsumo", ConsumoForroService.Consumo_Talla(id, ConsumoForroService.genpivot(list)));
			model.addAttribute("listConsumo_id", ConsumoForroService.Consumo_Talla_id(id));
			model.addAttribute("action",2);  
			return "consumo-talla-prenda";
			
		}
		if ( tipo.equals("tela-combinacion")) {
			model.addAttribute("action",3);
			
			model.addAttribute("listLargo", ConsumoService.largo());
			List<String> list = new ArrayList<>();
			List<String> list2 = new ArrayList<>();
			for (String d : ConsumoService.largo()) {
				
				list.add(d);
				list2.add("Ancho");
				list2.add("Largo");
			}
			model.addAttribute("numTallas", list2);
			model.addAttribute("listConsumoExtras_id", ConsumocombinacionService.Consumo_Talla_id(id,idExtra));
			model.addAttribute("listConsumoExtras", ConsumocombinacionService.Consumo_Talla(id, ConsumocombinacionService.genpivot(list), idExtra));
			return "consumo-talla-prenda";
		}
		if ( tipo.equals("tela-entretela")) {
			model.addAttribute("action",4);
			
			model.addAttribute("listLargo", ConsumoService.largo());
			List<String> list = new ArrayList<>();
			List<String> list2 = new ArrayList<>();
			for (String d : ConsumoService.largo()) {
				
				list.add(d);
				list2.add("Ancho");
				list2.add("Largo");
			}
			model.addAttribute("numTallas", list2);
			model.addAttribute("listConsumoExtras_id", EntretelaService.Consumo_Talla_id(id,idExtra));
			model.addAttribute("listConsumoExtras", EntretelaService.Consumo_Talla(id, ConsumocombinacionService.genpivot(list), idExtra));
			return "consumo-talla-prenda";
		}
		return null;
	}

	@RequestMapping(value = "/listar-tallas-prenda", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listar(Long id, String tipo) {
		
		if ( tipo .equals("tela")) {
		return ConsumoService.tallas(id);
		}
		else if ( tipo .equals("forro")) {
			return ConsumoForroService.tallas(id);
		}
		
		else if ( tipo .equals("tela-combinacion")) {
			
			return ConsumocombinacionService.tallas(id);
		}
		else if ( tipo .equals("tela-entretela")) {
			return EntretelaService.tallas(id);
		}
		else {
			return null;
		}
	
	}
	
	@RequestMapping(value = "/listar-largos-prenda", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listar_Largo(String tipo) {
		
		
		 if ( tipo .equals("tela-combinacion")) {
			
			return ConsumocombinacionService.largos();
		}
		 else if  ( tipo .equals("tela-entretela")) {
				
				return ConsumocombinacionService.largos();
			}
		else {
			return null;
		}
	
	}

	@RequestMapping(value = "/editar-tallas", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> editar_tallas(Long idTalla, Long idPrenda , String tipo) {
		if ( tipo .equals("Tela")) {
			 return ConsumoService.ConsumoTalla_Tallas(idTalla, idPrenda);
			}
			else if ( tipo .equals("Forro")) {
			 return ConsumoForroService.ConsumoTalla_Tallas(idTalla, idPrenda);
			}
			else {
				return null;
			}
		
	}
	
	@PostMapping("/guardar-largo-prenda-extras")
	public String guardar(Long idPrenda, Long idTalla,Long idLargo , String tipo , String largo, String ancho , Long idMaterial) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (tipo.equals("tela-combinacion")) {
			ProduccionConsumoTallaCombinacionTela aux = ConsumocombinacionService.buscar_consumo(idTalla, idPrenda, idLargo, idMaterial);
			if ( aux == null) {
				ProduccionConsumoTallaCombinacionTela tela = new ProduccionConsumoTallaCombinacionTela();
				tela.setIdPrenda(idPrenda);
				tela.setConsumoLargo(largo);
				tela.setConsumoAncho(ancho);
				tela.setCreadoPor(auth.getName());
				tela.setFechaCreacion(hourdateFormat.format(date));
				tela.setEstatus("1");
				tela.setIdTalla(idTalla);
				tela.setIdMaterial(idMaterial);
				tela.setIdTipoLargo(idLargo);
				ConsumocombinacionService.save(tela);
			}
			else {
				aux.setConsumoLargo(largo);
				aux.setConsumoAncho(ancho);
				aux.setUltimaFechaModificacion(hourdateFormat.format(date));
				aux.setActualizadoPor(auth.getName());
				ConsumocombinacionService.save(aux);

			}
			
		}
		
		if (tipo.equals("tela-entretela")) {
			ProduccionConsumoTallaCombinacionTela aux = ConsumocombinacionService.buscar_consumo(idTalla, idPrenda, idLargo, idMaterial);
			if ( aux == null) {
				ProduccionConsumoTallaEntretela entre = new ProduccionConsumoTallaEntretela();
				entre.setIdPrenda(idPrenda);
				entre.setConsumoLargo(largo);
				entre.setConsumoAncho(ancho);
				entre.setCreadoPor(auth.getName());
				entre.setFechaCreacion(hourdateFormat.format(date));
				entre.setEstatus("1");
				entre.setIdTalla(idTalla);
				entre.setIdMaterial(idMaterial);
				entre.setIdTipoLargo(idLargo);
				EntretelaService.save(entre);
			}
			else {
				aux.setConsumoLargo(largo);
				aux.setConsumoAncho(ancho);
				aux.setUltimaFechaModificacion(hourdateFormat.format(date));
				aux.setActualizadoPor(auth.getName());
				ConsumocombinacionService.save(aux);

			}
			
		}
		
		
		return "redirect:/consumo-talla-prenda/"+idPrenda;
	}
	
	@PostMapping("/guardar-largo-prenda")
	public String guardar(@RequestParam(name = "datos") String datos, Long idPrenda, Long idTalla, String tipo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (tipo.equals("Tela")) {
			
			JSONArray json = new JSONArray(datos);
			for (int i = 0; i < json.length(); i++) {
				ProduccionConsumoTalla consumo = new ProduccionConsumoTalla();
				JSONObject object = (JSONObject) json.get(i);
				String id = object.get("id_material").toString();
				String color = object.get("color").toString();
				ProduccionConsumoTalla aux = ConsumoService.buscar_consumo(idTalla, idPrenda, Long.parseLong(id));
				if (aux == null) {
					System.out.println(id);
					System.out.println(color);
					consumo.setIdPrenda(idPrenda);
					consumo.setIdTipoTalla(Long.parseLong(id));
					consumo.setConsumo(color);
					consumo.setCreadoPor(auth.getName());
					consumo.setActualizadoPor(null);
					consumo.setFechaCreacion(hourdateFormat.format(date));
					consumo.setUltimaFechaModificacion(null);
					consumo.setIdTalla(idTalla);
					consumo.setEstatus("1");
					ConsumoService.save(consumo);
				} else {

					if (aux.getConsumo().equals(color)) {

					} else {
						System.out.println("el id de largo es --->" + id);
						aux.setConsumo(color);
						aux.setUltimaFechaModificacion(hourdateFormat.format(date));
						aux.setActualizadoPor(auth.getName());
						ConsumoService.save(aux);

					}
				}

			}
		}
		
		if (tipo.equals("Forro")) {
			
			JSONArray json = new JSONArray(datos);
			for (int i = 0; i < json.length(); i++) {
				ProduccionConsumoTallaForro consumo = new ProduccionConsumoTallaForro();
				JSONObject object = (JSONObject) json.get(i);
				String id = object.get("id_material").toString();
				String color = object.get("color").toString();
				ProduccionConsumoTallaForro aux = ConsumoForroService.buscar_consumo(idTalla, idPrenda, Long.parseLong(id));
				if (aux == null) {
					System.out.println(id);
					System.out.println(color);
					consumo.setIdPrenda(idPrenda);
					consumo.setIdTipoTalla(Long.parseLong(id));
					consumo.setConsumo(color);
					consumo.setCreadoPor(auth.getName());
					consumo.setActualizadoPor(null);
					consumo.setFechaCreacion(hourdateFormat.format(date));
					consumo.setUltimaFechaModificacion(null);
					consumo.setIdTalla(idTalla);
					consumo.setEstatus("1");
					ConsumoForroService.save(consumo);
				} else {

					if (aux.getConsumo().equals(color)) {

					} else {
						System.out.println("el id de largo es --->" + id);
						aux.setConsumo(color);
						aux.setUltimaFechaModificacion(hourdateFormat.format(date));
						aux.setActualizadoPor(auth.getName());
						ConsumoForroService.save(aux);

					}
				}

			}
		}
		 return "redirect:/consumo-talla-prenda/"+idPrenda;
		
	}
	
	
	@RequestMapping(value = "/buscar-largos-anchos", method = RequestMethod.GET)
	@ResponseBody
	public List<String> buscar_tallas(Long idTalla , Long idPrenda , Long idLargo, Long idMaterial, String tipo) {
		
		if (tipo.equals("tela-entretela")) {
			ProduccionConsumoTallaEntretela aux = EntretelaService.buscar_consumo(idTalla, idPrenda, idLargo,
					idMaterial);
			List<String> list = new ArrayList<>();
			if (aux == null) {
				list.add(null);
				list.add(null);
			} else {
				list.add(aux.getConsumoAncho());
				list.add(aux.getConsumoLargo());
			}

			return list;
		}
		
		if (tipo.equals("tela-combinacion")) {
			ProduccionConsumoTallaCombinacionTela aux = ConsumocombinacionService.buscar_consumo(idTalla, idPrenda, idLargo,
					idMaterial);
			List<String> list = new ArrayList<>();
			if (aux == null) {
				list.add(null);
				list.add(null);
			} else {
				list.add(aux.getConsumoAncho());
				list.add(aux.getConsumoLargo());
			}

			return list;
		}
			
			else {
				return null;
			}
		
	}

	// C O M I E N Z A L A P A R T E D E F O R R O

	@RequestMapping(value = "/validar-forro-prenda", method = RequestMethod.GET)
	@ResponseBody
	public boolean validar_forro(Long idPrenda) {
		return ConsumoForroService.buscar_forro_prenda(idPrenda);
	}
}
