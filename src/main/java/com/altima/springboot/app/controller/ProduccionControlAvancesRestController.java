package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ProduccionConsumoReal;
import com.altima.springboot.app.models.entity.ProduccionExplosionPrendas;
import com.altima.springboot.app.models.entity.ProduccionExplosionProcesos;
import com.altima.springboot.app.models.service.IProduccionExplosionProcesosService;

@RestController
public class ProduccionControlAvancesRestController {
	
	@Autowired
	IProduccionExplosionProcesosService explosionService;

	@RequestMapping(value="/listarExplosion", method = RequestMethod.GET)
	public List<ProduccionExplosionProcesos> listarExplosion (@RequestParam(name="idProceso")Long idProceso){
		
		return explosionService.listExplosionByProceso(idProceso);
	}

	@RequestMapping(value="/listar_select_realizo", method = RequestMethod.GET)
	public List<Object []> listarEmpleados (@RequestParam(value="idProceso")Long idProceso, @RequestParam(value="tipoProceso")String tipoProceso){
		if ( tipoProceso.equals("Interno")){
			return explosionService.listarEmpleadosbyProduccion();
		}
		else if (tipoProceso.equals("Externo")){
			return explosionService.listarMaquilerosbyProceso(idProceso);
		}
		else{
			return null;
		}
		
	}
	
	@RequestMapping(value="/explosionarPrendas", method = RequestMethod.GET)
	public List<Object[]> explosionarPrendas(@RequestParam(value="idExplosion")Long idExplosion, @RequestParam(value="tipo")String tipo){
		
		
		List<Object[]> listaPrendasExplosionar = explosionService.prendasExplosionarByProceso(idExplosion);
		ProduccionExplosionProcesos explosion = explosionService.findOne(idExplosion);
		if(explosion.getEstatus().equals("0") ) {
			
			explosion.setEstatus("1");
			explosionService.save(explosion);
			
			for (Object[] i : listaPrendasExplosionar) {
				ProduccionExplosionPrendas explosionPrenda = new ProduccionExplosionPrendas();
				explosionPrenda.setTalla(i[5].toString());
				explosionPrenda.setIdExplosionProceso(idExplosion);
				explosionPrenda.setIdText("");
				explosionService.saveExplosionPrendas(explosionPrenda);
				explosionPrenda.setIdText("EXPREN"+(1000000+explosionPrenda.getIdExplosionPrenda()));
				explosionService.saveExplosionPrendas(explosionPrenda);
			}
		
			return explosionService.listarPrendasByExplosionProceso(idExplosion,tipo);
		}
		else {
			return explosionService.listarPrendasByExplosionProceso(idExplosion,tipo);
		}
	}
	@RequestMapping(value="/guardar_realizo_produccion_prendas", method=RequestMethod.GET)
	public Long folio (@RequestParam(name = "ids") String[] ids, String fechainicio, String fechafin,String realizo, String ubicacion){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Long data = null;
		for (int i = 0; i < ids.length; i++) {
			ProduccionExplosionPrendas obj = explosionService.findOnePrendas(Long.parseLong(ids[i]));
			obj.setRealizo(realizo);
			obj.setFechaInicio(fechainicio);
			obj.setFechaFin(fechafin);
			obj.setUbicacion(ubicacion);
			explosionService.saveExplosionPrendas(obj);
			data=obj.getIdExplosionProceso();
		}
		return data;
	}


	
	@RequestMapping(value="/listar_consumo_real", method = RequestMethod.GET)
	public List<Object[]> listarConsumoReal(@RequestParam(value="idExplosion")Long idExplosion){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		List<Object[]> listaQuery = explosionService.queryParaInsertarTelas(idExplosion);
		
		for (Object[] i : listaQuery) {
			if (explosionService.validarExistenciaConsumo(i[0].toString(), i[1].toString(), i[2].toString(), i[3].toString()).equals("0")){
				ProduccionConsumoReal consumo = new ProduccionConsumoReal();
				consumo.setIdTela(Integer.parseInt(i[0].toString()));
				consumo.setPrograma(i[1].toString());
				consumo.setIdCoordinadoPrenda(Integer.parseInt(i[2].toString()));
				consumo.setConsumoReal("0");
				consumo.setTipoTela(i[3].toString());
				consumo.setCreadoPor(auth.getName());
				consumo.setFechaCreacion(hourdateFormat.format(date));

				explosionService.saveConsumo(consumo);

			}
		}
		
			return explosionService.view(idExplosion);
	}

	
	@RequestMapping(value="/guardar_consumo_real", method=RequestMethod.GET)
	public boolean consumoReal (@RequestParam(name = "id") Long id, String consumo){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		ProduccionConsumoReal obj = explosionService.findOneConsumoReal(id);

		obj.setConsumoReal(consumo);
		obj.setActualizadoPor(auth.getName());
		obj.setUltimaFechaModificacion(hourdateFormat.format(date));
		explosionService.saveConsumo(obj);
		return true;
	}

	@RequestMapping(value="/validar_no_nulos_explosion_prendas", method=RequestMethod.GET)
	public String validarNoNulos (@RequestParam(name = "id") Long id){
		
		return explosionService.validarNoNulos(id);
	}
}
