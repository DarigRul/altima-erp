package com.altima.springboot.app.controller;

import java.lang.ProcessBuilder.Redirect;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.DisenioListaPrecioPrenda;
import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.service.IDisenioListaPrecioPrendaService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioPrendaService;

@Controller
public class PrecioPrendaController {
	@Autowired
	IDisenioListaPrecioPrendaService disenioListaPrecioPrendaService;
	@Autowired
	IDisenioPrendaService prendaService;
	@Autowired
	IDisenioLookupService disenioLookupService;

	@GetMapping("/precios-prenda/{id}")
	public String infoClothesPrice(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m) {
		DisenioListaPrecioPrenda precio = new DisenioListaPrecioPrenda();
		DisenioPrenda prenda = prendaService.findOne(id);
		precio.setIdPrenda(id);
		model.addAttribute("idText", prenda.getIdText());
		model.addAttribute("precio", precio);
		model.addAttribute("id", id);
		model.addAttribute("composiciones",disenioListaPrecioPrendaService.listaFamPrendaByidPrenda(id));
		return "precios-prenda";
	}

	@PostMapping("/guardar-precio-prenda")
	public String guardarPrecioPrenda(DisenioListaPrecioPrenda precio, RedirectAttributes redirectAttrs) {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("aqui esta el id" + precio.getPrecioLocalNuevo());
		if (precio.getIdListaPrecioPrenda() != null) {
			precio.setActualizadoPor(auth.getName());
			precio.setUltimaFechaModificacion(hourdateFormat.format(date));
		} else {
			precio.setCreadoPor(auth.getName());
		}
		try {
			disenioListaPrecioPrendaService.save(precio);
		} catch (Exception e) {
			//TODO: handle exception
			redirectAttrs.addFlashAttribute("title", "Error al insertar precios").addFlashAttribute("icon",
			"error");
			return "redirect:/precios-prenda/"+precio.getIdPrenda();
		}
		
		redirectAttrs.addFlashAttribute("title", "Precios insertados correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/lista-precios/"+precio.getIdPrenda();
	}

	@GetMapping("costo-prenda/{id}")
	public String costoPrenda(@PathVariable(value = "id") Long id, Model model) {

		DisenioListaPrecioPrenda precio = disenioListaPrecioPrendaService.findByidPrenda(id);
		precio.setIdPrenda(id);
		model.addAttribute("precio", precio);
		return "costo-prenda";
	}

	@GetMapping("/lista-precios/{id}")
	public String listaPrecio(@PathVariable(value = "id") Long id, Model model, Map<String, Object> m) {
		model.addAttribute("listaPrecios", disenioListaPrecioPrendaService.listaPrecioPrenda(id));
		model.addAttribute("id", id);
		
		return "lista-precios";
	}

	@GetMapping("/precios-prenda/{id}/{idp}")
	public String infoClothesPrice(@PathVariable(value = "id") Long id,@PathVariable(value = "idp") Long idp, Model model, Map<String, Object> m) {
		DisenioListaPrecioPrenda precio = disenioListaPrecioPrendaService.findOne(idp);
		DisenioPrenda prenda = prendaService.findOne(id);
		precio.setIdPrenda(id);
		model.addAttribute("idText", prenda.getIdText());
		model.addAttribute("precio", precio);
		model.addAttribute("composiciones",disenioListaPrecioPrendaService.listaFamPrendaByidPrenda(id));
		return "precios-prenda";
	}
	@GetMapping("/eliminar-precios-prenda/{idp}")
	public String eliminarprecio(@PathVariable(value = "idp") Long idp, Model model, Map<String, Object> m) {
		DisenioListaPrecioPrenda precio = disenioListaPrecioPrendaService.findOne(idp);
		disenioListaPrecioPrendaService.delete(idp);
		return "redirect:/lista-precios/"+precio.getIdPrenda();
	}

    @GetMapping("/carga-masiva-precios")
    public String cargaMasivaPrecios(Model m){
		DisenioListaPrecioPrenda precio =new DisenioListaPrecioPrenda();
		m.addAttribute("precio", precio);
		m.addAttribute("composiciones", disenioLookupService.findByTipoLookup("Familia Composicion"));
		m.addAttribute("tipoPrendas", disenioLookupService.findByTipoLookup("Familia Prenda"));
        return"carga-masiva-precios";
	}
	
	@PostMapping("/guardar-precios-masivos")
	public String guardarPreciosMasivos(DisenioListaPrecioPrenda precio,@RequestParam(name="famPrenda",required = true) Long famPrenda,RedirectAttributes redirectAttrs){
		List<Object[]> prPrecios= disenioListaPrecioPrendaService.prMasivo(precio.getIdFamiliaComposicion(), famPrenda);
		List<DisenioListaPrecioPrenda> precios= new ArrayList<DisenioListaPrecioPrenda>();
		for(Object[] pr:prPrecios){
			DisenioListaPrecioPrenda preciothis= new DisenioListaPrecioPrenda();
			if(!pr[0].toString().isEmpty()){
				System.out.println("entra");
				preciothis.setIdListaPrecioPrenda(Long.parseLong(pr[0].toString()));
			}
			preciothis.setPrecioVentaInterna(precio.getPrecioVentaInterna());
			preciothis.setPrecioMuestrario(precio.getPrecioMuestrario());
			preciothis.setPrecioLocalNuevo(precio.getPrecioLocalNuevo());
			preciothis.setPrecioLocalAntiguo(precio.getPrecioLocalAntiguo());
			preciothis.setPrecioLineaExpressLocalNuevo(precio.getPrecioLineaExpressLocalNuevo());
			preciothis.setPrecioLineaExpressLocalAnterior(precio.getPrecioLineaExpressLocalAnterior());
			preciothis.setPrecioLineaExpressForaneoNuevo(precio.getPrecioLineaExpressForaneoNuevo());
			preciothis.setPrecioLineaExpressForaneoAnterior(precio.getPrecioLineaExpressForaneoAnterior());
			preciothis.setPrecioForaneoNuevo(precio.getPrecioForaneoNuevo());
			preciothis.setPrecioForaneoAntiguo(precio.getPrecioForaneoAntiguo());
			preciothis.setPrecioExtra3(precio.getPrecioExtra3());
			preciothis.setPrecioExtra2(precio.getPrecioExtra2());
			preciothis.setPrecioExtra1(precio.getPrecioExtra1());
			preciothis.setPrecioEcommerce(precio.getPrecioEcommerce());
			preciothis.setCreadoPor("");
			preciothis.setIdFamiliaComposicion(precio.getIdFamiliaComposicion());
			preciothis.setIdPrenda(Long.parseLong(pr[1].toString()));
			precios.add(preciothis);
		}
		try {
			disenioListaPrecioPrendaService.saveAll(precios);
		} catch (Exception e) {
			//TODO: handle exception
			redirectAttrs.addFlashAttribute("title", "Error al insertar precios").addFlashAttribute("icon",
			"error");
			return("redirect:/carga-masiva-precios");
		}
		
		redirectAttrs.addFlashAttribute("title", "Precios insertados correctamente").addFlashAttribute("icon",
		"success");
		return("redirect:/carga-masiva-precios");
	}
}
