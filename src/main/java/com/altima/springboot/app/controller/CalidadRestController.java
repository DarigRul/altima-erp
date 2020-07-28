package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.DisenioCalidad;
import com.altima.springboot.app.models.entity.DisenioPruebaEncogimientoLavado;
import com.altima.springboot.app.models.entity.DisenioPruebaLavadoContaminacionCostura;
import com.altima.springboot.app.models.service.IDisenioCalidadService;
import com.altima.springboot.app.models.service.IDisenioPruebaEncogimientoLavadoService;
import com.altima.springboot.app.models.service.IDisenioPruebaLavadoContaminacionCosturaService;

@RestController
public class CalidadRestController {
	
	@Autowired
	private IDisenioPruebaEncogimientoLavadoService EncogimientoLavado;
	@Autowired
	private IDisenioCalidadService CalidadService;
	@Autowired
	private IDisenioPruebaLavadoContaminacionCosturaService LavadoContaCostura;

	
	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "/guardarPruebaEncogimiento", method = RequestMethod.POST)
	public int guardarPruebaEncogi(@RequestParam(name = "datos") String guardarEncogi) {
		
		String[] palabras = guardarEncogi.split(",");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DisenioPruebaEncogimientoLavado PruebaEncoLavado = new DisenioPruebaEncogimientoLavado();
		DisenioCalidad disenioCalidad = new DisenioCalidad();
		Calendar cal = Calendar.getInstance();
		double resultHilo;
		double resultTrama;
		Date date = cal.getTime();
		LocalDate localDate = LocalDate.now();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = localDate + " " + dateFormat.format(date);
		DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
		separadoresPersonalizados.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.##", separadoresPersonalizados);
		try {
		/////////////////////////////////////////////////
		/* Variables para condiciones*/
		final int ExistePruebaEncogiLavado = EncogimientoLavado.ifExist(Long.valueOf((palabras[19].equals(""))?"0":palabras[19])); //para saber si existe una prueba en encogimiento o lavado
		final int ExistePruebaFusion = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[19].equals(""))?"0":palabras[19]), "Prueba de Fusión");//para saber si existe una prueba de fusion
		final int ExistePruebaLavado = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[19].equals(""))?"0":palabras[19]), "Prueba de Lavado");//para saber si existe una prueba de Lavado
		final int ExistePruebaCostura = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[19].equals(""))?"0":palabras[19]), "Resultado Costura");//para saber si existe una prueba de costura
		final int ExistePruebaContaminacion = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[19].equals(""))?"0":palabras[19]), "Resultado de Contaminación");//para saber si existe una prueba de contaminacion
		final int ExistePruebaVapor = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[19].equals(""))?"0":palabras[19]), "Vapor");//para saber si existe una prueba de vapor
		/////////////////////////////////////////////////
		
		//Condición para verificar que no exista un registro en calidad
			if (palabras[19].equals("") || palabras[19] == null) {
				System.out.println("para checar si entra" + palabras[19]);
				disenioCalidad.setCreadoPor(auth.getName());
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setFechaCreacion(formattedDate);
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				disenioCalidad.setEstatus("0");
				disenioCalidad.setIdMaterial(Long.valueOf(palabras[21]));
				System.out.print("aqui esta el tipo "+palabras[22]);
				disenioCalidad.setTipoMaterial(palabras[22]);
				CalidadService.save(disenioCalidad);
				disenioCalidad.setIdText("CAL" + (disenioCalidad.getIdCalidad() + 100000));
				CalidadService.save(disenioCalidad);
				PruebaEncoLavado.setIdCalidad(disenioCalidad.getIdCalidad());
				PruebaEncoLavado.setCreadoPor(auth.getName());
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setFechaCreacion(formattedDate);
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
				
			}
			
			//condición para verificar que existe un registro en calidad pero no existe ninguna prueba en encogimiento
			else if (palabras[19] != null && ExistePruebaEncogiLavado == 0 /*no existe*/ || ExistePruebaFusion == 0 /*no existe*/) {
				PruebaEncoLavado.setIdCalidad(Long.valueOf(palabras[19]));
				PruebaEncoLavado.setCreadoPor(auth.getName());
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setFechaCreacion(formattedDate);
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[19]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				
				//Condición para verificar que ya existen todas las pruebas cuando es una tela para dar estatus terminado en calidad
				if (palabras[22].equals("1")/*es una tela*/ && ExistePruebaLavado == 1 /*si existe*/ && ExistePruebaCostura == 1 /*si existe*/ && ExistePruebaContaminacion == 1 /*si existe*/) {
					
					disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[19]));
					disenioCalidad.setActualizadoPor(auth.getName());
					disenioCalidad.setUltimaFechaModificacion(formattedDate);
					disenioCalidad.setEstatus("1");
					CalidadService.save(disenioCalidad);
				}
				//Condición para verificar que ya existen todas las pruebas cuando es un forro para dar estatus terminado en calidad
				if(palabras[22].equals("3")/*es un forro*/ && ExistePruebaLavado == 1 /*si existe*/ && ExistePruebaCostura == 1 /*si existe*/) {
					
					disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[19]));
					disenioCalidad.setActualizadoPor(auth.getName());
					disenioCalidad.setUltimaFechaModificacion(formattedDate);
					disenioCalidad.setEstatus("1");
					CalidadService.save(disenioCalidad);
				}
			}
			//Condición para verificar que ya existe una prueba de fusión
			else {
				PruebaEncoLavado = EncogimientoLavado.findByTipoPrueba("Prueba de Fusión", Long.valueOf(palabras[19]));
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[19]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
			}
			
			//Condición para verificar que sea una tela y guardar los datos
			if(palabras[22].equals("1")/*es una tela, en forros no aplica prueba de fusion*/) {
			resultHilo = ((Double.parseDouble(palabras[11]) * 100 / Double.parseDouble(palabras[9])) - 100);
			resultTrama = ((Double.parseDouble(palabras[12]) * 100 / Double.parseDouble(palabras[10])) - 100);
			PruebaEncoLavado.setIdOperario(Long.parseLong(palabras[1]));
			PruebaEncoLavado.setFechaRealizacion(palabras[2]);
			PruebaEncoLavado.setEntretelaPruebaVapor(palabras[3]);
			PruebaEncoLavado.setAdherenciaPruebaVapor(palabras[4]);
			PruebaEncoLavado.setProveedorPruebaVapor(palabras[5]);
			PruebaEncoLavado.setTemperaturaPruebaVapor(palabras[6]);
			PruebaEncoLavado.setTiempoPrueba(palabras[7]);
			PruebaEncoLavado.setPresionPrueba(palabras[8]);
			PruebaEncoLavado.setMedidaInicialHilo(palabras[9]);
			PruebaEncoLavado.setMedidaInicialTrama(palabras[10]);
			PruebaEncoLavado.setMedidaFinalHilo(palabras[11]);
			PruebaEncoLavado.setDiferenciaMedidaHilo(String.valueOf(df.format(resultHilo)));
			PruebaEncoLavado.setMedidaFinalTrama(palabras[12]);
			PruebaEncoLavado.setDiferenciaMedidaTrama(String.valueOf(df.format(resultTrama)));
			PruebaEncoLavado.setObservacionesResultados(palabras[13]);
			PruebaEncoLavado.setTipoPrueba("Prueba de Fusión");
			PruebaEncoLavado.setEstatus("1");
			
			EncogimientoLavado.save(PruebaEncoLavado);
			}
			
			//Condición para verificar que no exista un registro en calidad
			if (palabras[19].equals("") || palabras[19] == null) {
				PruebaEncoLavado = new DisenioPruebaEncogimientoLavado();
				PruebaEncoLavado.setIdCalidad(disenioCalidad.getIdCalidad());
				PruebaEncoLavado.setCreadoPor(auth.getName());
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setFechaCreacion(formattedDate);
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
			} 
			//condición para verificar que existe un registro en calidad pero no existe ninguna prueba en encogimiento
			else if (palabras[19] != null && ExistePruebaEncogiLavado == 0 || ExistePruebaVapor == 0) {
				PruebaEncoLavado = new DisenioPruebaEncogimientoLavado();
				PruebaEncoLavado.setIdCalidad(Long.valueOf(palabras[19]));
				PruebaEncoLavado.setCreadoPor(auth.getName());
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setFechaCreacion(formattedDate);
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
			}
			//Condición para verificar que ya existe una prueba de vapor
			else {
				PruebaEncoLavado = EncogimientoLavado.findByTipoPrueba("Vapor", Long.valueOf(palabras[19]));
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[19]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
			}
			
			resultHilo = ((Double.parseDouble(palabras[16]) * 100 / Double.parseDouble(palabras[14])) - 100);
			resultTrama = ((Double.parseDouble(palabras[17]) * 100 / Double.parseDouble(palabras[15])) - 100);
			
			PruebaEncoLavado.setIdOperario(Long.parseLong(palabras[1]));
			PruebaEncoLavado.setFechaRealizacion(palabras[2]);
			PruebaEncoLavado.setMedidaInicialHilo(palabras[14]);
			PruebaEncoLavado.setMedidaInicialTrama(palabras[15]);
			PruebaEncoLavado.setMedidaFinalHilo(palabras[16]);
			PruebaEncoLavado.setDiferenciaMedidaHilo(String.valueOf(df.format(resultHilo)));
			PruebaEncoLavado.setMedidaFinalTrama(palabras[17]);
			PruebaEncoLavado.setDiferenciaMedidaTrama(String.valueOf(df.format(resultTrama)));
			PruebaEncoLavado.setObservacionesResultados(palabras[18]);
			PruebaEncoLavado.setTipoPrueba("Vapor");
			PruebaEncoLavado.setEstatus("1");
			
			EncogimientoLavado.save(PruebaEncoLavado);
			
			return 1;
		}
		catch(Exception e) {
			System.out.println("Hay un error al guardar");
			System.out.println("Error:   "+e);
			return 0;
			
		}
		finally {
			System.out.println("Terminó el proceso guardarPruebaEncogi");
		}
	}
	
	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "/guardarPruebaLavado", method = RequestMethod.POST)
	public int guardarPruebaLavado(@RequestParam(name = "datos") String guardarEncogi) {
		String[] palabras = guardarEncogi.split(",");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DisenioPruebaEncogimientoLavado PruebaEncoLavado = new DisenioPruebaEncogimientoLavado();
		DisenioCalidad disenioCalidad = new DisenioCalidad();
		DisenioPruebaLavadoContaminacionCostura PruebaLavadoContaCostura = new DisenioPruebaLavadoContaminacionCostura();
		DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
		separadoresPersonalizados.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.##", separadoresPersonalizados);
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		LocalDate localDate = LocalDate.now();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = localDate + " " + dateFormat.format(date);
		
		try {
			/////////////////////////////////////////////////
			/* Variables para condiciones*/
			final int ExistePruebaEncogiLavado = EncogimientoLavado.ifExist(Long.valueOf((palabras[11].equals(""))?"0":palabras[11])); //para saber si existe una prueba en encogimiento o lavado
			final int ExistePruebaFusion = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[11].equals(""))?"0":palabras[11]), "Prueba de Fusión");//para saber si existe una prueba de fusion
			final int ExistePruebaLavado = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[11].equals(""))?"0":palabras[11]), "Prueba de Lavado");//para saber si existe una prueba de Lavado
			final int ExistePruebaCostura = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[11].equals(""))?"0":palabras[11]), "Resultado Costura");//para saber si existe una prueba de costura
			final int ExistePruebaContaminacion = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[11].equals(""))?"0":palabras[11]), "Resultado de Contaminación");//para saber si existe una prueba de contaminacion
			final int ExistePruebaVapor = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[11].equals(""))?"0":palabras[11]), "Vapor");//para saber si existe una prueba de vapor
			final int ExistePruebaPilling = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[11].equals(""))?"0":palabras[11]), "Solidez/Color/Pilling");
			final int ExistePruebaContaCostura = LavadoContaCostura.ifExist(Long.valueOf((palabras[11].equals(""))?"0":palabras[11]));//para saber si existe una prueba de lavado, contaminación o costura
			/////////////////////////////////////////////////
		
			//Condición para verificar que no exista un registro en calidad
			if (palabras[11].equals("") || palabras[11] == null) {
				disenioCalidad.setCreadoPor(auth.getName());
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setFechaCreacion(formattedDate);
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				disenioCalidad.setEstatus("0");
				disenioCalidad.setIdMaterial(Long.valueOf(palabras[13]));
				disenioCalidad.setTipoMaterial(palabras[14]);
				CalidadService.save(disenioCalidad);
				disenioCalidad.setIdText("CAL" + (disenioCalidad.getIdCalidad() + 100000));
				CalidadService.save(disenioCalidad);
				PruebaEncoLavado.setIdCalidad(disenioCalidad.getIdCalidad());
				PruebaEncoLavado.setCreadoPor(auth.getName());
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setFechaCreacion(formattedDate);
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
				
			}
			//Condición para verificar que existe un registro en calidad pero no hay ninguna prueba en lavado
			else if (palabras[11] != null && ExistePruebaEncogiLavado == 0 || ExistePruebaLavado == 0) {
				PruebaEncoLavado.setIdCalidad(Long.valueOf(palabras[11]));
				PruebaEncoLavado.setCreadoPor(auth.getName());
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setFechaCreacion(formattedDate);
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[11]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				
				//Condición para verificar que ya existen todas las pruebas cuando es una tela para dar estatus terminado en calidad
				if (palabras[14].equals("1") && ExistePruebaCostura == 1
						&& ExistePruebaContaminacion == 1 && (ExistePruebaFusion == 1 || ExistePruebaVapor == 1)) {
					
					disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[11]));
					disenioCalidad.setActualizadoPor(auth.getName());
					disenioCalidad.setUltimaFechaModificacion(formattedDate);
					disenioCalidad.setEstatus("1");
					CalidadService.save(disenioCalidad);
				}
				//Condición para verificar que ya existen todas las pruebas cuando es un forro para dar estatus terminado en calidad
				if (palabras[14].equals("3") && ExistePruebaCostura == 1 && ExistePruebaVapor == 1) {
					
					disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[11]));
					disenioCalidad.setActualizadoPor(auth.getName());
					disenioCalidad.setUltimaFechaModificacion(formattedDate);
					disenioCalidad.setEstatus("1");
					CalidadService.save(disenioCalidad);
				}
			}
			//Condición para verificar que ya existe una prueba de lavado
			else {
				PruebaEncoLavado = EncogimientoLavado.findByTipoPrueba("Prueba de Lavado", Long.valueOf(palabras[11]));
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[11]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				PruebaEncoLavado.setActualizadoPor(auth.getName());
				PruebaEncoLavado.setUltimaFechaModificacion(formattedDate);
			}
			
			
			double resultHilo = ((Double.parseDouble(palabras[5]) * 100 / Double.parseDouble(palabras[3])) - 100);
			double resultTrama = ((Double.parseDouble(palabras[6]) * 100 / Double.parseDouble(palabras[4])) - 100);
			
			PruebaEncoLavado.setIdOperario(Long.parseLong(palabras[1]));
			PruebaEncoLavado.setFechaRealizacion(palabras[2]);
			PruebaEncoLavado.setMedidaInicialHilo(palabras[3]);
			PruebaEncoLavado.setMedidaInicialTrama(palabras[4]);
			PruebaEncoLavado.setMedidaFinalHilo(palabras[5]);
			PruebaEncoLavado.setDiferenciaMedidaHilo(String.valueOf(df.format(resultHilo)));
			PruebaEncoLavado.setMedidaFinalTrama(palabras[6]);
			PruebaEncoLavado.setDiferenciaMedidaTrama(String.valueOf(df.format(resultTrama)));
			PruebaEncoLavado.setObservacionesResultados(palabras[7]);
			PruebaEncoLavado.setTipoPrueba("Prueba de Lavado");
			PruebaEncoLavado.setEstatus("1");
			
			EncogimientoLavado.save(PruebaEncoLavado);
			
			//Condición para verificar que no exista un registro en calidad
			if (palabras[11].equals("") || palabras[11] == null) {
				PruebaLavadoContaCostura.setIdCalidad(disenioCalidad.getIdCalidad());
				PruebaLavadoContaCostura.setCreadoPor(auth.getName());
				PruebaLavadoContaCostura.setFechaCreacion(formattedDate);
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
			}
			//Condición para verificar que existe un registro en calidad pero no hay ninguna prueba en lavado
			else if (palabras[11] != null && ExistePruebaContaCostura == 0 || ExistePruebaPilling == 0) {
				PruebaLavadoContaCostura.setIdCalidad(Long.valueOf(palabras[11]));
				PruebaLavadoContaCostura.setCreadoPor(auth.getName());
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setFechaCreacion(formattedDate);
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[11]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
			}
			//Condición para verificar que ya existe una prueba de pilling
			else {
				PruebaLavadoContaCostura = LavadoContaCostura.findByTipoPrueba("Solidez/Color/Pilling", Long.valueOf(palabras[11]));
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[11]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
			}
			
			PruebaLavadoContaCostura.setCreadoPor(palabras[1]);
			PruebaLavadoContaCostura.setFechaRealizacion(palabras[2]);
			PruebaLavadoContaCostura.setPruebaCalidad(palabras[8]);
			PruebaLavadoContaCostura.setPrueba_pilling(palabras[9]);
			PruebaLavadoContaCostura.setObservacionesResultados(palabras[10]);
			PruebaLavadoContaCostura.setTipoPrueba("Solidez/Color/Pilling");
			PruebaLavadoContaCostura.setEstatus("1");
			
			LavadoContaCostura.save(PruebaLavadoContaCostura);
			
			return 1;
		}
		catch(Exception e) {
			System.out.println("Hay un error al guardar");
			System.out.println("Error:   "+e);
			return 0;
			
		}
		finally {
			System.out.println("Terminó el proceso guardarPruebaLavado");
		}
	}
	
	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "/guardarPruebaCostura", method = RequestMethod.POST)
	public int guardarPruebaCostura(@RequestParam(name = "datos") String guardarEncogi) {
		String[] palabras = guardarEncogi.split(",");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DisenioCalidad disenioCalidad = new DisenioCalidad();
		DisenioPruebaLavadoContaminacionCostura PruebaLavadoContaCostura = new DisenioPruebaLavadoContaminacionCostura();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		LocalDate localDate = LocalDate.now();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = localDate + " " + dateFormat.format(date);
		
		try {
			/////////////////////////////////////////////////
			/* Variables para condiciones*/
			final int ExistePruebaFusion = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[7].equals(""))?"0":palabras[7]), "Prueba de Fusión");//para saber si existe una prueba de fusion
			final int ExistePruebaLavado = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[7].equals(""))?"0":palabras[7]), "Prueba de Lavado");//para saber si existe una prueba de Lavado
			final int ExistePruebaCostura = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[7].equals(""))?"0":palabras[7]), "Resultado Costura");//para saber si existe una prueba de costura
			final int ExistePruebaContaminacion = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[7].equals(""))?"0":palabras[7]), "Resultado de Contaminación");//para saber si existe una prueba de contaminacion
			final int ExistePruebaVapor = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[7].equals(""))?"0":palabras[7]), "Vapor");//para saber si existe una prueba de vapor
			final int ExistePruebaContaCostura = LavadoContaCostura.ifExist(Long.valueOf((palabras[7].equals(""))?"0":palabras[7]));//para saber si existe una prueba en lavado, contaminacion ocostura
			/////////////////////////////////////////////////
		
			//Condición para verificar que no exista un registro en calidad
			if (palabras[7].equals("") || palabras[7] == null) {
				disenioCalidad.setCreadoPor(auth.getName());
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setFechaCreacion(formattedDate);
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				disenioCalidad.setEstatus("0");
				disenioCalidad.setIdMaterial(Long.valueOf(palabras[9]));
				disenioCalidad.setTipoMaterial(palabras[10]);
				CalidadService.save(disenioCalidad);
				disenioCalidad.setIdText("CAL" + (disenioCalidad.getIdCalidad() + 100000));
				CalidadService.save(disenioCalidad);
				PruebaLavadoContaCostura.setIdCalidad(disenioCalidad.getIdCalidad());
				PruebaLavadoContaCostura.setCreadoPor(auth.getName());
				PruebaLavadoContaCostura.setFechaCreacion(formattedDate);
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
			} 
			//Condición para verificar que existe un registro en calidad pero no hay ninguna prueba en costura
			else if (palabras[7] != null && ExistePruebaContaCostura == 0 || ExistePruebaCostura == 0) {
				PruebaLavadoContaCostura.setIdCalidad(Long.valueOf(palabras[7]));
				PruebaLavadoContaCostura.setCreadoPor(auth.getName());
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setFechaCreacion(formattedDate);
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[7]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				
				//Condición para verificar que ya existen todas las pruebas cuando es una tela para dar estatus terminado en calidad
				if (palabras[10].equals("1") && ExistePruebaLavado == 1 && ExistePruebaContaminacion == 1 && (ExistePruebaFusion == 1 || ExistePruebaVapor == 1)) {
					
					disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[7]));
					disenioCalidad.setActualizadoPor(auth.getName());
					disenioCalidad.setUltimaFechaModificacion(formattedDate);
					disenioCalidad.setEstatus("1");
					CalidadService.save(disenioCalidad);
				}
				
				//Condición para verificar que ya existen todas las pruebas cuando es un forro para dar estatus terminado en calidad
				if (palabras[10].equals("3") && ExistePruebaLavado == 1 && (EncogimientoLavado.ifExistLavado(Long.valueOf(palabras[7]), "Vapor") == 1)) {
					
					disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[7]));
					disenioCalidad.setActualizadoPor(auth.getName());
					disenioCalidad.setUltimaFechaModificacion(formattedDate);
					disenioCalidad.setEstatus("1");
					CalidadService.save(disenioCalidad);
				}
				
				
			} 
			//Condición para verificar que ya existe una prueba de costura
			else {
				PruebaLavadoContaCostura = LavadoContaCostura.findByTipoPrueba("Resultado Costura", Long.valueOf(palabras[7]));
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[7]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
			}
			
			PruebaLavadoContaCostura.setIdOperario(Long.parseLong(palabras[1]));
			PruebaLavadoContaCostura.setFechaRealizacion(palabras[2]);
			PruebaLavadoContaCostura.setTipoAguja(palabras[3]);
			PruebaLavadoContaCostura.setDeslizamientoTela(palabras[4]);
			PruebaLavadoContaCostura.setTipoPrueba("Resultado Costura");
			PruebaLavadoContaCostura.setRasgadoTela(palabras[5]);
			PruebaLavadoContaCostura.setObservacionesResultados(palabras[6]);
			PruebaLavadoContaCostura.setEstatus("1");
			
			LavadoContaCostura.save(PruebaLavadoContaCostura);
			return 1;
		}
		catch(Exception e) {
			System.out.println("Hay un error al guardar");
			System.out.println("Error:   "+e);
			return 0;
			
		}
		finally {
			System.out.println("Terminó el proceso guardarPruebaCostura");
		}
	}
	
	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "/guardarPruebaContaminacion", method = RequestMethod.POST)
	public int guardarPruebaContaminacion(@RequestParam(name = "datos") String guardarEncogi) {
		String[] palabras = guardarEncogi.split(",");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DisenioCalidad disenioCalidad = new DisenioCalidad();
		DisenioPruebaLavadoContaminacionCostura PruebaLavadoContaCostura = new DisenioPruebaLavadoContaminacionCostura();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		LocalDate localDate = LocalDate.now();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = localDate + " " + dateFormat.format(date);
		
		try {
			/////////////////////////////////////////////////
			/* Variables para condiciones*/
			final int ExistePruebaFusion = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[5].equals(""))?"0":palabras[5]), "Prueba de Fusión");//para saber si existe una prueba de fusion
			final int ExistePruebaLavado = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[5].equals(""))?"0":palabras[5]), "Prueba de Lavado");//para saber si existe una prueba de Lavado
			final int ExistePruebaCostura = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[5].equals(""))?"0":palabras[5]), "Resultado Costura");//para saber si existe una prueba de costura
			final int ExistePruebaContaminacion = LavadoContaCostura.ifExistContaCostura(Long.valueOf((palabras[5].equals(""))?"0":palabras[5]), "Resultado de Contaminación");//para saber si existe una prueba de contaminacion
			final int ExistePruebaVapor = EncogimientoLavado.ifExistLavado(Long.valueOf((palabras[5].equals(""))?"0":palabras[5]), "Vapor");//para saber si existe una prueba de vapor
			final int ExistePruebaContaCostura = LavadoContaCostura.ifExist(Long.valueOf((palabras[5].equals(""))?"0":palabras[5]));//para saber si existe una prueba en lavado, contaminacion o costura
			/////////////////////////////////////////////////
		
			//Condición para verificar que no exista un registro en calidad
			if (palabras[5].equals("") || palabras[5] == null) {
				disenioCalidad.setCreadoPor(auth.getName());
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setFechaCreacion(formattedDate);
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				disenioCalidad.setEstatus("0");
				disenioCalidad.setEstatus("0");
				disenioCalidad.setIdMaterial(Long.valueOf(palabras[7]));
				disenioCalidad.setTipoMaterial(palabras[8]);
				CalidadService.save(disenioCalidad);
				disenioCalidad.setIdText("CAL" + (disenioCalidad.getIdCalidad() + 100000));
				CalidadService.save(disenioCalidad);
				PruebaLavadoContaCostura.setIdCalidad(disenioCalidad.getIdCalidad());
				PruebaLavadoContaCostura.setIdCalidad(disenioCalidad.getIdCalidad());
				PruebaLavadoContaCostura.setCreadoPor(auth.getName());
				PruebaLavadoContaCostura.setFechaCreacion(formattedDate);
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
			} 
			//Condición para verificar que existe un registro en calidad pero no hay ninguna prueba en contaminación
			else if (palabras[5] != null && ExistePruebaContaCostura == 0 || ExistePruebaContaminacion == 0) {
				PruebaLavadoContaCostura.setIdCalidad(Long.valueOf(palabras[5]));
				PruebaLavadoContaCostura.setCreadoPor(auth.getName());
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setFechaCreacion(formattedDate);
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[5]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				
				//Condición para verificar que ya existen todas las pruebas para dar estatus terminado en calidad
				if (ExistePruebaLavado == 1 && ExistePruebaCostura == 1 && (ExistePruebaFusion == 1 || ExistePruebaVapor == 1)) {
					
					disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[5]));
					disenioCalidad.setActualizadoPor(auth.getName());
					disenioCalidad.setUltimaFechaModificacion(formattedDate);
					disenioCalidad.setEstatus("1");
					CalidadService.save(disenioCalidad);
				}
			} 
			
			//Condición para verificar que ya existe una prueba de costura
			else {
				PruebaLavadoContaCostura = LavadoContaCostura.findByTipoPrueba("Resultado de Contaminación",Long.valueOf(palabras[5]));
				disenioCalidad = CalidadService.findOne(Long.valueOf(palabras[5]));
				disenioCalidad.setActualizadoPor(auth.getName());
				disenioCalidad.setUltimaFechaModificacion(formattedDate);
				CalidadService.save(disenioCalidad);
				PruebaLavadoContaCostura.setActualizadoPor(auth.getName());
				PruebaLavadoContaCostura.setUltimaFechaModificacion(formattedDate);
			}
			
			PruebaLavadoContaCostura.setIdOperario(Long.parseLong(palabras[1]));
			PruebaLavadoContaCostura.setFechaRealizacion(palabras[2]);
			PruebaLavadoContaCostura.setPruebaCalidad(palabras[3]);
			PruebaLavadoContaCostura.setObservacionesResultados(palabras[4]);
			PruebaLavadoContaCostura.setTipoPrueba("Resultado de Contaminación");
			PruebaLavadoContaCostura.setEstatus("1");
			
			LavadoContaCostura.save(PruebaLavadoContaCostura);
			return 1;
		}
		catch(Exception e) {
			System.out.println("Hay un error al guardar");
			System.out.println("Error:   "+e);
			return 0;
			
		}
		finally {
			System.out.println("Terminó el proceso guardarPruebaContaminacion");
		}
	}
}
