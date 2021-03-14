package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.dto.ProgramarTelasListDto;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ProduccionExplosionPrendas;
import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IEmpalmeTelasService;
import com.altima.springboot.app.models.service.IProduccionExplosionProcesosService;

import org.springframework.security.access.annotation.Secured;

@RestController
public class EmpalmeTelasRestController {

	@Autowired
	private IEmpalmeTelasService EmpalmeService;

	@Autowired
	private IComercialCoordinadoService coorService;

	@Autowired
	private IProduccionExplosionProcesosService explosionService;

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_EMPALME_TELAS_AÑADIR_RUTA" })
	@RequestMapping(value = "/guardar_ruta_a_coor_prenda", method = RequestMethod.POST)
	public boolean ruta(@RequestParam(name = "ids") String[] ids, Integer idRuta) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (int i = 0; i < ids.length; i++) {
			ComercialCoordinadoPrenda obj = coorService.findOneCoorPrenda(Long.parseLong(ids[i]));
			obj.setId_ruta(idRuta);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			coorService.saveCoorPrenda(obj);
		}
		return true;
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_EMPALME_TELAS_AÑADIR_PROGRAMA" })
	@RequestMapping(value = "/guardar_programa_a_coor_prenda", method = RequestMethod.POST)
	public boolean programa(@RequestParam(name = "ids") String[] ids, String programa) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (int i = 0; i < ids.length; i++) {
			ComercialCoordinadoPrenda obj = coorService.findOneCoorPrenda(Long.parseLong(ids[i]));
			obj.setPrograma(programa);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			coorService.saveCoorPrenda(obj);
		}
		return true;
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_EMPALME_TELAS_AÑADIR_FOLIO" })
	@RequestMapping(value = "/guardar_folio_a_coor_prenda", method = RequestMethod.POST)
	public boolean folio(@RequestParam(name = "ids") String[] ids, String folio) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (int i = 0; i < ids.length; i++) {
			ComercialCoordinadoPrenda obj = coorService.findOneCoorPrenda(Long.parseLong(ids[i]));
			obj.setFolio(folio);
			obj.setActualizadoPor(auth.getName());
			obj.setUltimaFechaModificacion(hourdateFormat.format(date));
			coorService.saveCoorPrenda(obj);
		}
		return true;
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_EMPALME_TELAS" })
	@RequestMapping(value = "/empalme_telas_detalles", method = RequestMethod.GET)
	public List<Object[]> detalles(@RequestParam(name = "id") Long id) {
		return EmpalmeService.detallesTelas(id);
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_EMPALME_TELAS" })
	@RequestMapping(value = "/empalme_telas_by_proceso", method = RequestMethod.GET)
	public List<Object[]> listarByProceso(@RequestParam(name = "idProceso") Long idProceso,@RequestParam String programa) {
		return EmpalmeService.listarByProceso(idProceso,programa);
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_EMPALME_TELAS" })
	@RequestMapping(value = "/guardar_empalme_by_proceso", method = RequestMethod.POST)
	public boolean save(@RequestParam(name = "ids") String[] ids, String secuencia) {
		for (int i = 0; i < ids.length; i++) {
			ProduccionExplosionProcesos obj = explosionService.findOne(Long.parseLong(ids[i]));
			obj.setSecuenciaProceso(secuencia);
			explosionService.save(obj);
		}
		return true;
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_PRODUCCION_EMPALME_TELAS" })
	@RequestMapping(value = "/guardar_empalme_by_proceso_fecha_tiempo", method = RequestMethod.POST)
	public boolean save(Long id, String tiempo, String fecha) {

		ProduccionExplosionProcesos obj = explosionService.findOne(id);
		if (tiempo != null) {
			obj.setTiempoProceso(tiempo);
		}
		if (fecha != null) {
			obj.setFechaProceso(fecha);
		}
		explosionService.save(obj);

		return true;
	}

	@GetMapping("getOrdenesProduccionByPedido/{pedido}")
	public ResponseEntity<?> getOrdenesProduccionByPedido(@PathVariable(name="pedido") String pedido) {

		
		Map<String, Object> response = new HashMap<>();
		List<ProgramarTelasListDto> ordenes =null;
		try {
			ordenes=EmpalmeService.view(pedido);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(ordenes.size()==0){
			response.put("mensaje", "La OP con el id no existe");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ProgramarTelasListDto>>(ordenes,HttpStatus.OK);
	}
}
