package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@PostMapping("/guardar-inventario-herramientas-entradas-salidas")
	@ResponseBody
	public void GuardaEntradasSalidas(Long concepto,Long articulo,Integer cantidad) {
		MaquilaInventarioHerramientasEntradasSalidas mihes = new MaquilaInventarioHerramientasEntradasSalidas();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		mihes.setFecha(dateFormat.format(date));
		mihes.setIdMovimiento(concepto);
		mihes.setIdHerramienta(articulo);
		mihes.setCantidad(cantidad);
		maquilaInventarioHerramientaEntradasSalidasService.save(mihes);
		mihes.setFolio("MOV00"+mihes.getIdInventarioHerramienta());
		maquilaInventarioHerramientaEntradasSalidasService.save(mihes);
	}
	
}
