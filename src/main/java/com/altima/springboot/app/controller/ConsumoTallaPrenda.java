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
import com.altima.springboot.app.models.entity.ProduccionConsumoTallaForro;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaForroService;
import com.altima.springboot.app.models.service.IProduccionConsumoTallaService;

@CrossOrigin(origins = { "*" })
@Controller
public class ConsumoTallaPrenda {

	@Autowired
	private IProduccionConsumoTallaService ConsumoService;
	@Autowired
	private IProduccionConsumoTallaForroService ConsumoForroService;

	@GetMapping("/consumo-talla-prenda/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model) {

		model.addAttribute("id_prenda", id);
		List<String> list = new ArrayList<>();

		for (String d : ConsumoService.largo()) {
			list.add(d);
		}
		model.addAttribute("head", list);
		model.addAttribute("headTam", list.size());
		model.addAttribute("listConsumo", ConsumoService.Consumo_Talla(id, ConsumoService.genpivot(list)));
		model.addAttribute("listConsumo_id", ConsumoService.Consumo_Talla_id(id));
		// C O M I E N Z A L A P A R T E D E F O R R O
		
		model.addAttribute("listConsumoForro", ConsumoForroService.Consumo_Talla(id, ConsumoForroService.genpivot(list)));
		model.addAttribute("listConsumo_idForro", ConsumoForroService.Consumo_Talla_id(id));
		
		return "consumo-talla-prenda";
	}

	@RequestMapping(value = "/listar-tallas-prenda", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listar(Long id, String tipo) {
		
		if ( tipo .equals("Tela")) {
		return ConsumoService.tallas(id);
		}
		else if ( tipo .equals("Forro")) {
			return ConsumoForroService.tallas(id);
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

	// C O M I E N Z A L A P A R T E D E F O R R O

	@RequestMapping(value = "/validar-forro-prenda", method = RequestMethod.GET)
	@ResponseBody
	public boolean validar_forro(Long idPrenda) {
		return ConsumoForroService.buscar_forro_prenda(idPrenda);
	}
}
