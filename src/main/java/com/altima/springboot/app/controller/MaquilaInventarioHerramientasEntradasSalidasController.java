package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.models.entity.AmpTraspaso;
import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.MaquilaInventarioHerramientasEntradasSalidas;
import com.altima.springboot.app.models.entity.MaquilaLookup;
import com.altima.springboot.app.models.service.MaquilaInventarioHerramientaEntradasSalidasServiceImpl;

@Controller
public class MaquilaInventarioHerramientasEntradasSalidasController {
	@Autowired 
	MaquilaInventarioHerramientaEntradasSalidasServiceImpl maquilaInventarioHerramientaEntradasSalidasService;
	
	@GetMapping("/entradas-salidas-herramientas-utileria")
	public String EntradasSalidasHerramientaUtileria(Model model) {
		model.addAttribute("entradasalida", maquilaInventarioHerramientaEntradasSalidasService.listaMovimientos());
		
		return "entradas-salidas-herramientas-utileria";
	}
	
	@GetMapping("/entradas-salidas-herramientas-utileria-tabla")
	@ResponseBody
	public List<Object[]> EntradasSalidasHerramientaUtileriaTabla(Model model) {
		
		return maquilaInventarioHerramientaEntradasSalidasService.listaMovimientos();
	}
	
	@GetMapping("/listar-conceptos")
	@ResponseBody
	public List<MaquilaLookup>listarConceptos(String descripcion){
		
		
		return maquilaInventarioHerramientaEntradasSalidasService.listaConceptos(descripcion);
	}
	@GetMapping("/listar-articulos")
	@ResponseBody
	public List<MaquilaLookup>listarArticulos(){
		
		return maquilaInventarioHerramientaEntradasSalidasService.listaArticulos();
	}
	@DeleteMapping("/eliminar-entrada-salida")
	@ResponseBody
	public void Eliminar(Long id)
	{
		maquilaInventarioHerramientaEntradasSalidasService.delete(id);
	}
	
	
	 @Transactional
	 @ResponseBody
		@PostMapping("/guardar-inventario-herramientas-entradas-salidas")
		public boolean GuardarEntradasSalidas(@RequestParam(name = "Lista") String Lista) {
			boolean response;
			
			try {
				JSONArray json = new JSONArray(Lista);
				for (Object object : json) {
				    JSONObject object2 = (JSONObject) object;
					String articuloid = object2.get("articuloid").toString();
					String articulomarca = object2.get("articulomarca").toString();
					String articulonombre = object2.get("articulonombre").toString();
					String cantidad = object2.get("cantidad").toString();
					String concepto = object2.get("concepto").toString();
					String contador = object2.get("contador").toString();
					String movimiento = object2.get("movimiento").toString();
					String observacion = object2.get("observacion").toString();
					MaquilaInventarioHerramientasEntradasSalidas mihes = new MaquilaInventarioHerramientasEntradasSalidas();
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					mihes.setFecha(dateFormat.format(date));
					mihes.setIdMovimiento(Long.parseLong(concepto));
					mihes.setIdHerramienta(Long.parseLong(articuloid));
					mihes.setCantidad(Integer.parseInt(cantidad));
					maquilaInventarioHerramientaEntradasSalidasService.save(mihes);
					mihes.setFolio("MOV00"+mihes.getIdInventarioHerramienta());
					maquilaInventarioHerramientaEntradasSalidasService.save(mihes);
					mihes.setFolio("MOV00"+(mihes.getIdHerramienta()-1));
					maquilaInventarioHerramientaEntradasSalidasService.save(mihes);

					
			
					}
				response = true;
				
			
			} catch (Exception e) {
				// TODO: handle exception
				response = false;
			}

			return response;
		}
	
}
