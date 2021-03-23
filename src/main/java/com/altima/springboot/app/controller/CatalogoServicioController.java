package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.entity.ServicioClienteArregloPrenda;
import com.altima.springboot.app.models.entity.ServicioClienteLookup;
import com.altima.springboot.app.models.service.IServicioClienteLookupService;
import org.springframework.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CatalogoServicioController {
	@Autowired
	private IServicioClienteLookupService servicioClienteService;

	@GetMapping("/catalogos-servicio")
	public String listCat() {
		return "catalogos-servicio";
	}
	@RequestMapping(value = "listar_lookup_servicio_cliente", method = RequestMethod.GET)
	@ResponseBody
	public List<ServicioClienteLookup> listarProveedoresColores(String Tipo) {
		return servicioClienteService.findAllLookup(Tipo);
	}

	@RequestMapping(value = "/verificar_duplicado_servicio_cliente", method = RequestMethod.GET)
	@ResponseBody
	public boolean verificaduplicado(String Lookup, String Tipo,String descripcion,String atributo1, String atributo2, String atributo3 ) {

		
		return servicioClienteService.findDuplicate(Lookup, Tipo,  descripcion, atributo1,  atributo2,  atributo3 );
	}

	@RequestMapping(value = "listar_lookup_servicio_cliente_estatus_1", method = RequestMethod.GET)
	@ResponseBody
	public List<ServicioClienteLookup> listarEstatus1(String Tipo) {
		return servicioClienteService.findAllByType(Tipo);
	}
	@RequestMapping(value = "listar_lookup_servicio_prendas", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> listarPrendas() {
		return servicioClienteService.findAllPrendas();
	}

	@RequestMapping(value = "listar_prendas_arreglo", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> prendasArreglo(Long id) {
		return servicioClienteService.verPrendaArreglo(id);
	}


	@RequestMapping(value="/obtener_lookup_by_id", method=RequestMethod.GET)
	@ResponseBody
	public ServicioClienteLookup regresarLookup(Long id){
		return servicioClienteService.findOne(id);
	}



	@RequestMapping(value="/guardar_reporte_actividades", method=RequestMethod.GET)
	@ResponseBody
	public boolean save(Long idProceso, String observacion){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		ServicioClienteLookup obj = servicioClienteService.findOne(idProceso);
		if ( obj.getDescripcionLookup() != observacion){
			obj.setDescripcionLookup(observacion);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(dateFormat.format(date));
			servicioClienteService.save(obj);
			return true;
		}
		else{
			return false;
		}
		
	}
	@PostMapping("/guardar_lookup_servicio_cliente")
	public String guardacatalogo(Long idLook, String nombre, String tipo , String descripcion, String atributo1, String atributo2,String atributo3, String clave) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();
		if (idLook == null){
			ServicioClienteLookup nuevo = new ServicioClienteLookup();
			ServicioClienteLookup ultimoid = null;
			try {
				ultimoid = servicioClienteService.findLastLookupByType(tipo);

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				nuevo.setIdText(tipo.substring(0,3) .toUpperCase()+ "0001");
			} else {
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				nuevo.setIdText(clave+ fmt.format("%04d", (cont + 1)));
			}
			nuevo.setDescripcionLookup(descripcion);
			nuevo.setNombreLookup(StringUtils.capitalize(nombre));
			nuevo.setTipoLookup(tipo);
			nuevo.setCreadoPor(auth.getName());
			nuevo.setFechaCreacion(dateFormat.format(date));
			nuevo.setEstatus(1);
			nuevo.setAtributo1(atributo1);
			nuevo.setAtributo2(atributo2);
			nuevo.setAtributo3(atributo3);
			servicioClienteService.save(nuevo);
		}else{
			ServicioClienteLookup editar = servicioClienteService.findOne(idLook);
			editar.setNombreLookup(nombre);
			editar.setDescripcionLookup(descripcion);
			editar.setAtributo1(atributo1);
			editar.setAtributo2(atributo2);
			editar.setAtributo3(atributo3);
			editar.setActualizadoPor(auth.getName());
			editar.setUltimaFechaModificacion(dateFormat.format(date));
			servicioClienteService.save(editar);
		}
		fmt.close();
		return "redirect:catalogos";
	}

	
	@RequestMapping(value = "cambio_estatus_servicio_cliente", method = RequestMethod.POST)
	@ResponseBody
	public String cambioEstatus(Long idLookup, Integer estatus) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		ServicioClienteLookup editar = servicioClienteService.findOne(idLookup);
		editar.setEstatus(estatus);
		editar.setActualizadoPor(auth.getName());
		editar.setUltimaFechaModificacion(dateFormat.format(date));
		servicioClienteService.save(editar);
		return editar.getTipoLookup();

	}

	
	@PostMapping("/guardar_lookup_servicio_cliente_arreglo_prendas")
	public String guardacatalogoArregloPrendas(Long idLook, String nombre, String tipo , String[] atributo1, String[] atributo2,String clave) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();
		if (idLook == null){
			ServicioClienteLookup nuevo = new ServicioClienteLookup();
			ServicioClienteLookup ultimoid = null;
			try {
				ultimoid = servicioClienteService.findLastLookupByType(tipo);

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				nuevo.setIdText(tipo.substring(0,3) .toUpperCase()+ "0001");
			} else {
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				nuevo.setIdText(clave+ fmt.format("%04d", (cont + 1)));
			}
			nuevo.setNombreLookup(StringUtils.capitalize(nombre));
			nuevo.setTipoLookup(tipo);
			nuevo.setCreadoPor(auth.getName());
			nuevo.setFechaCreacion(dateFormat.format(date));
			nuevo.setEstatus(1);
			nuevo.setUltimaFechaModificacion(null);
			servicioClienteService.save(nuevo);
			for (int i = 0; i < atributo1.length; i++) {
				ServicioClienteArregloPrenda obj = new ServicioClienteArregloPrenda();
				obj.setIdPrenda(atributo1[i]);
				obj.setIdComplejidad(atributo2[i]);
				obj.setIdArreglo(String.valueOf(nuevo.getIdLookup()));
				obj.setCreadoPor(auth.getName());
				obj.setFechaCreacion(dateFormat.format(date));
				servicioClienteService.saveArregloPrenda(obj);
			}
		}else{
			ServicioClienteLookup editar = servicioClienteService.findOne(idLook);
			editar.setNombreLookup(nombre);
			editar.setActualizadoPor(auth.getName());
			editar.setUltimaFechaModificacion(dateFormat.format(date));
			servicioClienteService.save(editar);
			for (int i = 0; i < atributo1.length; i++) {
				if (servicioClienteService.findOnePrendaArreglo(idLook, atributo1[i], atributo2[i]) == false){
					ServicioClienteArregloPrenda obj = new ServicioClienteArregloPrenda();
					obj.setIdPrenda(atributo1[i]);
					obj.setIdComplejidad(atributo2[i]);
					obj.setIdArreglo(String.valueOf(idLook));
					obj.setCreadoPor(auth.getName());
					obj.setFechaCreacion(dateFormat.format(date));
					servicioClienteService.saveArregloPrenda(obj);
				}
				
			}
			
		}
		fmt.close();
		return "redirect:catalogos";
	}

	@RequestMapping(value = "/validar-arreglo-editar", method = RequestMethod.GET)
	@ResponseBody
	public boolean validarRutaenEditar(Long idLookup, String nombre) {
		return servicioClienteService.validarNombreArregloEditar(idLookup, nombre);
	}

	@RequestMapping(value = "/elimiar_MN_arreglo", method = RequestMethod.GET)
	@ResponseBody
	public boolean eliminar(Long id) {
		servicioClienteService.deleteArregloPrenda(id);
		return true;
	}
}
