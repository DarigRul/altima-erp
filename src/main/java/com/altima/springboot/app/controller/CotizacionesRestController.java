package com.altima.springboot.app.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ComercialCotizacion;
import com.altima.springboot.app.models.entity.ComercialCotizacionPrenda;
import com.altima.springboot.app.models.entity.ComercialCotizacionTotal;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialCotizacionPrendaService;
import com.altima.springboot.app.models.service.IComercialCotizacionService;
import com.altima.springboot.app.models.service.IComercialCotizacionTotalService;

@RestController
public class CotizacionesRestController {

	//Services
	@Autowired
	private IComercialCotizacionService cotizacionService;
	@Autowired
	private IComercialCotizacionTotalService cotizacionTotalService;
	@Autowired
	private  IComercialCoordinadoService CoordinadoService;
	@Autowired
	private IComercialCotizacionPrendaService cotizacionPrendaService;

	
	@RequestMapping(value="/ExtraerModelos", method=RequestMethod.GET)
	public List<Object []> mostrarModelos (@RequestParam (name="idFamPrenda") Long idFamPrenda){
		return CoordinadoService.findAllModelo(idFamPrenda);
	}
	
	@RequestMapping(value="/ExtraerTelas", method=RequestMethod.GET)
	public List<Object []> mostrarTelas (@RequestParam (name="idFamPrenda") Long idFamPrenda){
		return CoordinadoService.findAllTela(idFamPrenda);
	}
	
	@RequestMapping(value="/agregarCotizacionPrendaTablita", method=RequestMethod.POST)
	public Object[] agregarCotizacionPrendaTablita(@RequestParam (name="idModelo") String idPrenda,
												   @RequestParam (name="idTela") String idTela){
		try {
		return cotizacionPrendaService.FindDatosCotizacionPrenda(Long.parseLong(idTela), Long.parseLong(idPrenda));
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
		}
		finally {
			System.out.println("Fin de proceso agregarCotizacionPrendaTablita");
		}
	}
	
	@RequestMapping(value="/GuardarCotizacionInfo", method=RequestMethod.POST)
	public int GuardarCotizacionInformacion (@RequestParam(name="lista") String lista) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			ComercialCotizacion cotizacion = new ComercialCotizacion();
			ComercialCotizacionTotal cotiTotal = new ComercialCotizacionTotal();
			System.out.println(lista);
			String[] datos = lista.split(",");
			
			//Para guardar una nueva cotización con lo que trae la lista
			cotizacion.setIdText(datos[0]);
			cotizacion.setTituloCotizacion(datos[1]);
			cotizacion.setTipoCotizacion(datos[2]);
			cotizacion.setTipoPrecio(datos[3]);
			cotizacion.setIdGerente(Long.parseLong(datos[4]));
			cotizacion.setIdAgenteVentas(Long.parseLong(datos[5]));
			cotizacion.setIdCliente(Long.parseLong(datos[6]));
			cotizacion.setObservaciones(datos[7]);
			
			cotizacion.setCreadoPor(auth.getName());
			cotizacion.setActualizadoPor(auth.getName());
			cotizacion.setFechaCreacion(dtf.format(now));
			cotizacion.setUltimaFechaModificacion(dtf.format(now));
			cotizacionService.save(cotizacion);
			
			//Para guardar Los precios de la cotización vacíos
			cotiTotal.setIdCotizacion(cotizacion.getIdCotizacion());
			cotiTotal.setSubtotal("0");
			cotiTotal.setAnticipoPorcentaje("0");
			cotiTotal.setAnticipoMonto("0");
			cotiTotal.setDescuentoPorcentaje("0");
			cotiTotal.setDescuentoMonto("0");
			cotiTotal.setDescuentoCargo("0");
			cotiTotal.setIva(datos[8]);
			cotiTotal.setIvaMonto("0");
			cotiTotal.setTotal("0");
			cotiTotal.setCreadoPor(auth.getName());
			cotiTotal.setActualizadoPor(auth.getName());
			cotiTotal.setFechaCreacion(dtf.format(now));
			cotiTotal.setUltimaFechaModificacion(dtf.format(now));
			cotizacionTotalService.save(cotiTotal);
			
			return Integer.parseInt(cotizacion.getIdCotizacion().toString());//Es el id de Cotizacion para editar sin recargar
		}catch(Exception e) {
			System.out.println("Algo salió mal al extraer datos en GuardarCotizacionInfo, Mas info:  "+e);
			return 0;
		}
		finally{
			System.out.println("Fin de proceso de guardar \'GuardarCotizacionInformacion\'");
		}
	}
	
	
	@RequestMapping(value="/EditarCotizacionInfo", method=RequestMethod.POST)
	public int EditarCotizacionInformacion (@RequestParam(name="lista") String lista) {
		try {
			String[] datos = lista.split(",");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			ComercialCotizacion cotizacion = cotizacionService.findOne(Long.parseLong(datos[8]));

			System.out.println(lista);
			cotizacion.setIdText("CAL"+(cotizacion.getIdCotizacion() + 10000));
			cotizacion.setTituloCotizacion(datos[1]);
			cotizacion.setTipoCotizacion(datos[2]);
			cotizacion.setTipoPrecio(datos[3]);
			cotizacion.setIdGerente(Long.parseLong(datos[4]));
			cotizacion.setIdAgenteVentas(Long.parseLong(datos[5]));
			cotizacion.setIdCliente(Long.parseLong(datos[6]));
			cotizacion.setObservaciones(datos[7]);
			
			cotizacion.setActualizadoPor(auth.getName());
			cotizacion.setUltimaFechaModificacion(dtf.format(now));
			cotizacionService.save(cotizacion);
			
			
			return 1;
		}catch(Exception e) {
			System.out.println("Algo salió mal al extraer datos en EditarCotizacionInfo, Mas info:  "+e);
			return 0;
		}
	}
	
	@RequestMapping(value="/GuardarCotizacionPrendas", method=RequestMethod.POST)
	public int GuardarCotizacionPrendas (@RequestParam(name="lista") String lista,
										 @RequestParam(name="idCotizacion") String idCotizacion) {
		
		
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			JSONArray datos = new JSONArray(lista);
			
			for (int i=0;i<datos.length();i++) {
				ComercialCotizacionPrenda cotiPrenda = new ComercialCotizacionPrenda();
				JSONObject dato = datos.getJSONObject(i);
				System.out.println(dato);
				cotiPrenda.setIdCotizacion(Long.parseLong(idCotizacion));
				cotiPrenda.setIdFamiliaPrenda(Long.parseLong(dato.get("idPrenda").toString()));
				cotiPrenda.setIdPrenda(Long.parseLong(dato.get("idModelo").toString()));
				cotiPrenda.setIdTela(Long.parseLong(dato.get("idTela").toString()));
				cotiPrenda.setCoordinado(dato.get("coordinado").toString());
				cotiPrenda.setCantidad(dato.get("cantidad").toString());
				cotiPrenda.setPrecioBordado(dato.get("precioBordado").toString());
				cotiPrenda.setPorcentajeAdicional(dato.get("porcentajeCotizacion").toString());
				cotiPrenda.setMontoAdicional(dato.get("montoCotizacion").toString());
				cotiPrenda.setPrecioUnitarioFinal(dato.get("precioFinal").toString());
				cotiPrenda.setImporte(dato.get("importeFinal").toString());
				cotiPrenda.setCreadoPor(auth.getName());
				cotiPrenda.setActualizadoPor(auth.getName());
				cotiPrenda.setFechaCreacion(dtf.format(now));
				cotiPrenda.setUltimaFechaModificacion(dtf.format(now));
				
				cotizacionPrendaService.save(cotiPrenda);
				
			}
			return 1;
		
	}
	
	@RequestMapping(value="/EditarCotizacionPrendas", method=RequestMethod.POST)
	public int EditarCotizacionPrendas (@RequestParam(name="lista") String lista,
									 	@RequestParam(name="idCotizacionPrendas") String idCotizacionPrendas) {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			JSONArray datos = new JSONArray(lista);
			
			//Es para borrar registros eliminados en la tabla del usuario
			int contadorsillo=0;
			List<Object[]> AllPrendas = cotizacionPrendaService.FindCotizacionPrendas(Long.parseLong(idCotizacionPrendas));
			List<ComercialCotizacionPrenda> AllPrendasToDelete = new ArrayList<ComercialCotizacionPrenda>();
			
			for (Object[] a: AllPrendas) {
				for (int i=0;i<datos.length();i++) {
					JSONObject dato = datos.getJSONObject(i);
					if(dato.getInt("idCotizacionPrenda")==Integer.parseInt(a[0].toString())) {
						contadorsillo=1;
					}
				}
				if(contadorsillo==0){
					AllPrendasToDelete.add(cotizacionPrendaService.findOne(Long.parseLong(a[0].toString())));
				}
				else {
					contadorsillo=0;
				}
			}
			try {
			cotizacionPrendaService.removePrendas(AllPrendasToDelete);
			System.out.println("Prendas eliminadas");
			}
			catch (Exception e) {
				System.out.println("No hay prendas a eliminar");
			}
			
			//Es para agregar los nuevos registros creados en la tabla del usuario
			for (int i=0;i<datos.length();i++) {
				JSONObject dato = datos.getJSONObject(i);
				System.out.println(dato);
				if(dato.get("idCotizacionPrenda").toString().equals("-1")) {
					ComercialCotizacionPrenda cotiPrenda = new ComercialCotizacionPrenda();
					cotiPrenda.setIdCotizacion(Long.parseLong(idCotizacionPrendas));
					cotiPrenda.setIdFamiliaPrenda(Long.parseLong(dato.get("idPrenda").toString()));
					cotiPrenda.setIdPrenda(Long.parseLong(dato.get("idModelo").toString()));
					cotiPrenda.setIdTela(Long.parseLong(dato.get("idTela").toString()));
					cotiPrenda.setCoordinado(dato.get("coordinado").toString());
					cotiPrenda.setCantidad(dato.get("cantidad").toString());
					cotiPrenda.setPrecioBordado(dato.get("precioBordado").toString());
					cotiPrenda.setPorcentajeAdicional(dato.get("porcentajeCotizacion").toString());
					cotiPrenda.setMontoAdicional(dato.get("montoCotizacion").toString());
					cotiPrenda.setPrecioUnitarioFinal(dato.get("precioFinal").toString());
					cotiPrenda.setImporte(dato.get("importeFinal").toString());
					cotiPrenda.setCreadoPor(auth.getName());
					cotiPrenda.setActualizadoPor(auth.getName());
					cotiPrenda.setFechaCreacion(dtf.format(now));
					cotiPrenda.setUltimaFechaModificacion(dtf.format(now));
					
					cotizacionPrendaService.save(cotiPrenda);
				}
				
				//Es para editar los registros existentes en la tabla del usuario
				else {
					
					ComercialCotizacionPrenda cotiPrenda = cotizacionPrendaService.findOne(dato.getLong("idCotizacionPrenda"));
					cotiPrenda.setIdCotizacion(Long.parseLong(idCotizacionPrendas));
					cotiPrenda.setIdFamiliaPrenda(Long.parseLong(dato.get("idPrenda").toString()));
					cotiPrenda.setIdPrenda(Long.parseLong(dato.get("idModelo").toString()));
					cotiPrenda.setIdTela(Long.parseLong(dato.get("idTela").toString()));
					cotiPrenda.setCoordinado(dato.get("coordinado").toString());
					cotiPrenda.setCantidad(dato.get("cantidad").toString());
					cotiPrenda.setPrecioBordado(dato.get("precioBordado").toString());
					cotiPrenda.setPorcentajeAdicional(dato.get("porcentajeCotizacion").toString());
					cotiPrenda.setMontoAdicional(dato.get("montoCotizacion").toString());
					cotiPrenda.setPrecioUnitarioFinal(dato.get("precioFinal").toString());
					cotiPrenda.setImporte(dato.get("importeFinal").toString());
					cotiPrenda.setActualizadoPor(auth.getName());
					cotiPrenda.setUltimaFechaModificacion(dtf.format(now));
					
					cotizacionPrendaService.save(cotiPrenda);
				}
				System.out.println(dato.getInt("idCotizacionPrenda"));
			}
			
			return 1;
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}finally {
			
		}
		
	}
	
	@RequestMapping(value="/EditarCotizacionTotal", method=RequestMethod.POST)
	public int EditarCotizacionTotal (@RequestParam(name="lista") String lista) {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			String[] datos = lista.split(",");
			ComercialCotizacionTotal cotiTotal = cotizacionTotalService.findByCotizacion(Long.parseLong(datos[8]));
			cotiTotal.setAnticipoPorcentaje(datos[0]);
			cotiTotal.setAnticipoMonto(datos[1]);
			cotiTotal.setDescuentoPorcentaje(datos[2]);
			cotiTotal.setDescuentoMonto(datos[3]);
			cotiTotal.setSubtotal(datos[4]);
			cotiTotal.setDescuentoCargo(datos[5]);
			cotiTotal.setIvaMonto(datos[6]);
			cotiTotal.setTotal(datos[7]);
			cotiTotal.setActualizadoPor(auth.getName());
			cotiTotal.setUltimaFechaModificacion(dtf.format(now));
			
			cotizacionTotalService.save(cotiTotal);
		
			return 1;
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}finally {
			
		}
	}
}
