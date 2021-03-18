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
import com.altima.springboot.app.models.entity.ProduccionConsumoTallaForro;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaCombinacionTelaService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaForroService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaService;

@CrossOrigin(origins = { "*" })
@Controller
public class ConsumoTallaPrenda {

	@Autowired
	private IProduccionConsumoTallaService ConsumoService;
	@Autowired
	private IProduccionConsumoTallaCombinacionTelaService ConsumocombinacionService;
	
	@Autowired
	private IProduccionConsumoTallaForroService forroService;

	@Secured({"ROLE_ADMINISTRADOR","ROLE_DISENIO_PRENDAS_CONSUMO"})
	@GetMapping("/consumo-talla-prenda/{id}")
	public String vista (@PathVariable(value = "id") Long id, Model model) {
		
		model.addAttribute("materiales", ConsumoService.Materiales_Prenda(id));

		model.addAttribute("id_prenda", id);
		return "consumo-talla-prenda";
	}
	
	@PostMapping("/guardar-talla-especial-tela")
	public String guardarTallaEspecialTela(Long id_prenda, Long id_tela_combinacion, String tipo, Model model,
			Float consumo) {
		System.out.println("hola");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (tipo.equals("Tela")) {
			try {
				ProduccionConsumoTalla oneprodconsumo = ConsumoService.findOneSpecial(id_prenda);
				oneprodconsumo.setConsumo(consumo.toString());
				ConsumoService.save(oneprodconsumo);
			} catch (Exception e) {
				ProduccionConsumoTalla newoneprodconsumo = new ProduccionConsumoTalla();
				newoneprodconsumo.setConsumo(consumo.toString());
				newoneprodconsumo.setCreadoPor(auth.getName());
				newoneprodconsumo.setEstatus("1");
				newoneprodconsumo.setIdPrenda(id_prenda);
				newoneprodconsumo.setIdTallaEspecial(1);
				newoneprodconsumo.setFechaCreacion(hourdateFormat.format(date));
				ConsumoService.save(newoneprodconsumo);
			}

		} else if (tipo.equals("tela-combinacion")) {
			try {
				ProduccionConsumoTallaCombinacionTela oneprodconsumocombinacion = ConsumocombinacionService
						.findOneSpecial(id_prenda);
				oneprodconsumocombinacion.setConsumoAncho(consumo.toString());
				ConsumocombinacionService.save(oneprodconsumocombinacion);
			} catch (Exception e) {
				// TODO: handle exception
				ProduccionConsumoTallaCombinacionTela newoneprodconsumocombinacion = new ProduccionConsumoTallaCombinacionTela();
				newoneprodconsumocombinacion.setConsumoAncho(consumo.toString());
				newoneprodconsumocombinacion.setConsumoLargo("1");
				newoneprodconsumocombinacion.setEstatus("1");
				newoneprodconsumocombinacion.setFechaCreacion(hourdateFormat.format(date));
				newoneprodconsumocombinacion.setIdPrenda(id_prenda);
				newoneprodconsumocombinacion.setIdTallaEspecial(1);
				newoneprodconsumocombinacion.setCreadoPor(auth.getName());
				ConsumocombinacionService.save(newoneprodconsumocombinacion);
			}

		}
		return "redirect:/consumo-talla-prenda/" + id_prenda + "/" + tipo + "/" + id_tela_combinacion + "";
	}
	
	@GetMapping("/consumo-talla-prenda/{id}/{tipo}/{idExtra}")
	public String vistas (@PathVariable(value = "id") Long id, Model model, @PathVariable(value = "tipo") String tipo,@PathVariable(value = "idExtra") Long idExtra) {
		
		model.addAttribute("materiales", ConsumoService.Materiales_Prenda(id));
		model.addAttribute("id_prenda", id);
		
		model.addAttribute("id_tela_combinacion", idExtra);
		model.addAttribute("tipo", tipo);
		
		
		if ( tipo.equals("Tela")) {
			List<String> list = new ArrayList<>();

			for (String d : ConsumoService.largo()) {
				list.add(d);
			}
			try {
				
				model.addAttribute("consumo", ConsumoService.findOneSpecial(id).getConsumo());

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("no hay");
			}
			
			model.addAttribute("head", list);
			model.addAttribute("headTam", list.size());
			model.addAttribute("listConsumo", ConsumoService.Consumo_Talla(id, ConsumoService.genpivot(list)));
			model.addAttribute("listConsumo_id", ConsumoService.Consumo_Talla_id(id));
			model.addAttribute("action",1);  
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
            try {
				
				model.addAttribute("consumo", ConsumocombinacionService.findOneSpecial(id).getConsumoAncho());

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("no hay");
			}
			model.addAttribute("numTallas", list2);
			model.addAttribute("listConsumoExtras_id", ConsumocombinacionService.Consumo_Talla_id(id,idExtra));
			model.addAttribute("listConsumoExtras", ConsumocombinacionService.Consumo_Talla(id, ConsumocombinacionService.genpivot(list), idExtra));
			return "consumo-talla-prenda";
		}
		if ( tipo.equals("forro-combinacion")) {
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
			model.addAttribute("listConsumoExtras_id", forroService.Consumo_Talla_id(id,idExtra));
			model.addAttribute("listConsumoExtras", forroService.Consumo_Talla(id, ConsumocombinacionService.genpivot(list), idExtra));
			return "consumo-talla-prenda";
		}
		return null;
	}

	@RequestMapping(value = "/listar-tallas-prenda", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listar(Long id, String tipo, Long idMaterial) {
		System.out.print(idMaterial);
		
		if ( tipo .equals("tela")) {
		return ConsumoService.tallas(id);
		}

		
		else if ( tipo .equals("tela-combinacion")) {
			
			return ConsumocombinacionService.tallas(id, idMaterial);
		}
		else if ( tipo .equals("forro-combinacion")) {
			return forroService.tallas(id);
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
		 else if  ( tipo .equals("forro-combinacion")) {
				
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
		
		if (tipo.equals("forro-combinacion")) {
			ProduccionConsumoTallaCombinacionTela aux = ConsumocombinacionService.buscar_consumo(idTalla, idPrenda, idLargo, idMaterial);
			if ( aux == null) {
				ProduccionConsumoTallaForro entre = new ProduccionConsumoTallaForro();
				entre.setIdPrenda(idPrenda);
				entre.setConsumoLargo(largo);
				entre.setConsumoAncho(ancho);
				entre.setCreadoPor(auth.getName());
				entre.setFechaCreacion(hourdateFormat.format(date));
				entre.setEstatus("1");
				entre.setIdTalla(idTalla);
				entre.setIdMaterial(idMaterial);
				entre.setIdTipoLargo(idLargo);
				forroService.save(entre);
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
		
		 return "redirect:/consumo-talla-prenda/"+idPrenda;
		
	}
	
	
	@RequestMapping(value = "/buscar-largos-anchos", method = RequestMethod.GET)
	@ResponseBody
	public List<String> buscar_tallas(Long idTalla , Long idPrenda , Long idLargo, Long idMaterial, String tipo) {
		
		if (tipo.equals("forro-combinacion")) {
			ProduccionConsumoTallaForro aux = forroService.buscar_consumo(idTalla, idPrenda, idLargo,
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


}
