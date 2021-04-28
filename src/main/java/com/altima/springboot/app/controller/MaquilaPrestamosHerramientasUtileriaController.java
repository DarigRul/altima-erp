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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.MaquilaInventarioHerramientasEntradasSalidas;
import com.altima.springboot.app.models.entity.MaquilaPrestamosHerramientasUtileria;
import com.altima.springboot.app.models.service.IMaquilaAsignacionTicketsService;
import com.altima.springboot.app.models.service.IMaquilaPrestamosHerramientasUtileriaService;

@Controller
public class MaquilaPrestamosHerramientasUtileriaController {
	@Autowired
	IMaquilaPrestamosHerramientasUtileriaService maquilaPrestamosHerramientasUtileriaService;
	@Autowired
	IMaquilaAsignacionTicketsService maquilaAsignacionTicketsService;
	
	@GetMapping("/prestamos-herramientas-utileria")
	public String ListarPrestamos(Model model) {
		model.addAttribute("prestamos1", maquilaPrestamosHerramientasUtileriaService.ListarPrestamosHerramientas());
		
		
		return "prestamos-herramientas-utileria";
	}
	
	@GetMapping("/listar-devoluciones-herramientas")
	@ResponseBody
	public List<Object[]>ListarDevolucionesHerramientas(String folio){
	return	maquilaPrestamosHerramientasUtileriaService.ListarDevolucionesHerramientas(folio);
		
		
	}
	
	@GetMapping("listar-operarios-prestamos")
	@ResponseBody
	public  List<HrEmpleado> ListarOperariosPrestamos(){
		
	return maquilaAsignacionTicketsService.ListarOperarios();
	}
	
	 @Transactional
	 @ResponseBody
		@PostMapping("/guardar-prestamo-herramientas-utileria")
		public boolean GuardarPrestamoHerramientasUtileria(@RequestParam(name = "Lista") String Lista) {
			boolean response;
			
			try {
				JSONArray json = new JSONArray(Lista);
				for (Object object : json) {
				    JSONObject object2 = (JSONObject) object;
					String articuloid = object2.get("articuloid").toString();
					String articulomarca = object2.get("articulomarca").toString();
					String articulonombre = object2.get("articulonombre").toString();
					String cantidad = object2.get("cantidad").toString();
					String contador = object2.get("contador").toString();
					String folio=object2.get("folio").toString();
					String operario= object2.get("operario").toString();
					MaquilaPrestamosHerramientasUtileria mphes = new MaquilaPrestamosHerramientasUtileria();
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					mphes.setFecha(dateFormat.format(date));
					mphes.setIdHerramienta(Long.parseLong(articuloid));
					mphes.setCantidad(Integer.parseInt(cantidad));
					mphes.setFolio("PREST00"+folio);
					mphes.setIdOperario(Long.parseLong(operario));
					maquilaPrestamosHerramientasUtileriaService.save(mphes);
					

					
			
					}
				response = true;
				
			
			} catch (Exception e) {
				// TODO: handle exception
				response = false;
			}

			return response;
		}
	
	
	
	
	
	
	
	
	
	
	
	}
