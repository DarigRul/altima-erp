package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.DisenioCalidad;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioPruebaEncogimientoLavado;
import com.altima.springboot.app.models.entity.DisenioPruebaLavadoContaminacionCostura;
import com.altima.springboot.app.models.service.IDisenioCalidadService;
import com.altima.springboot.app.models.service.IDisenioForroService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
import com.altima.springboot.app.models.service.IDisenioPruebaEncogimientoLavadoService;
import com.altima.springboot.app.models.service.IDisenioPruebaLavadoContaminacionCosturaService;
import com.altima.springboot.app.models.service.IDisenioTelaService;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
import com.altima.springboot.app.models.service.IUploadService;

@Controller
public class CalidadController {
	
	@Autowired
	IDisenioCalidadService disenioCalidad;
	@Autowired
	IDisenioPruebaEncogimientoLavadoService pruebaEncogiLavado;
	@Autowired
	IDisenioPruebaLavadoContaminacionCosturaService pruebaContaCostura;
	@Autowired
	IDisenioLookupService disenioLookup;
	@Autowired
	private IHrEmpleadoService empleadoService;
	@Autowired
	IDisenioTelaService disenioTela;
	@Autowired
	IDisenioForroService disenioForro;
	@Autowired
	IDisenioMaterialService materialService;
	@Autowired
	private IUploadService uploadFileService;

	//Método para verificar el tipo de material
	private String tipo(String tipo){
		String tipoint="tipo";
		if(tipo.equals("tela")){
			tipoint="1";
		}
		else if(tipo.equals("forro")){
			tipoint="3";
		}
		return tipoint;

	}

	//Método para mepaar la pantalla de calidad
	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR", "ROLE_DISENIO_CALIDAD_APROBAR_RECHAZAR", "ROLE_DISENIO_CALIDAD_ELIMINAR", "ROLE_DISENIO_CALIDAD_LISTAR"})
	@GetMapping("/calidad")
	public String listCalidad(Model model) {
		model.addAttribute("listCalidades", disenioCalidad.findAllWithIdTextTela());
		return "calidad";
	}

	@GetMapping(value = "/uploads/plantillasCalidad/{filename:.+}")
	public ResponseEntity<Resource> descargarPlantillascalidad(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.loadfile(filename,4);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@GetMapping(value = "/uploads/calidadarchivos/{filename:.+}")
	public ResponseEntity<Resource> descargarcalidad(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.loadfile(filename,2);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping(value = "/uploads/calidadconsentimiento/{filename:.+}")
	public ResponseEntity<Resource> descargarcalidadconsentimiento(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.loadfile(filename,3);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "/calidad-nueva-prueba-otro")
	public String crear(Model model) {
		DisenioCalidad diseniocalidad = new DisenioCalidad();
		model.addAttribute("diseniocalidad", diseniocalidad);
		return "calidad-nueva-prueba-otro";
	}

	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "calidad-nueva-prueba-otro/{id}")
	public String addCalidadOtro(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttrs) {
		DisenioCalidad diseniocalidad = null;
		model.addAttribute("idMaterial", id);
		try {
			diseniocalidad = disenioCalidad.findPruebaCalidadOtro(id);
		} catch (Exception e) {
			System.out.println(e);
		}
		if (diseniocalidad == null) {
			diseniocalidad = new DisenioCalidad();
			diseniocalidad.setIdMaterial(id);
		}
		model.addAttribute("diseniocalidad", diseniocalidad);
		return "calidad-nueva-prueba-otro";
	}

	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "/calidad-nueva-prueba-otro", method = RequestMethod.POST)
	public String addCalidadOtroPrueba(Model model, @RequestParam("file") MultipartFile archivoRuta,
			@Valid DisenioCalidad diseniocalidad, SessionStatus status, RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			DisenioCalidad dc1 = disenioCalidad.findOne(diseniocalidad.getIdCalidad());
			diseniocalidad.setArchivoRuta(dc1.getArchivoRuta());
		} catch (Exception e) {
			System.out.println(e);
		}

		if (!archivoRuta.isEmpty()) {
			if (diseniocalidad.getIdCalidad() != null && diseniocalidad.getArchivoRuta() != null
					&& diseniocalidad.getArchivoRuta().length() > 0) {
				uploadFileService.delete(diseniocalidad.getArchivoRuta());

			}
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copyfile(archivoRuta, 2);

			} catch (IOException e) {
				e.printStackTrace();
			}
			diseniocalidad.setArchivoRuta(uniqueFilename);

		}
		
		if ( diseniocalidad.getIdCalidad() == null) {
			diseniocalidad.setFechaCreacion(null);
			diseniocalidad.setCreadoPor(auth.getName());
			diseniocalidad.setTipoMaterial("2");
			diseniocalidad.setEstatus("1");
			diseniocalidad.setUltimaFechaModificacion(null);
			redirectAttrs.addFlashAttribute("title", "Prueba de calidad guardada correctamente").addFlashAttribute("icon",
					"success");
			disenioCalidad.save(diseniocalidad);
			diseniocalidad.setIdText("CAL" + 100000 + diseniocalidad.getIdCalidad());
			disenioCalidad.save(diseniocalidad);
		}
		else {
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			diseniocalidad.setActualizadoPor(auth.getName());
			diseniocalidad.setUltimaFechaModificacion(hourdateFormat.format(date));
			
			disenioCalidad.save(diseniocalidad);
			redirectAttrs.addFlashAttribute("title", "Prueba de calidad guardada correctamente").addFlashAttribute("icon",
					"success");
		}

		
		return "redirect:calidad";
	}
	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "/calidad-consentimiento", method = RequestMethod.POST)
	public String guardaarchivoconsentimiento(@RequestParam("file") MultipartFile archivoConsentimiento, Long id) {

		DisenioCalidad dc1 = disenioCalidad.findOne(id);
		dc1.setArchivoConsentimiento(dc1.getArchivoConsentimiento());///

		if (!archivoConsentimiento.isEmpty()) {

			if (dc1.getIdCalidad() != null && dc1.getArchivoConsentimiento() != null
					&& dc1.getArchivoConsentimiento().length() > 0) {
				uploadFileService.delete(dc1.getArchivoConsentimiento());

			}
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copyfile(archivoConsentimiento, 3);

			} catch (IOException e) {
				e.printStackTrace();
			}
			dc1.setArchivoConsentimiento(uniqueFilename);

		}	
		disenioCalidad.save(dc1);
		return "redirect:calidad";
	}
	
	@GetMapping("detalle-calidad")
	public String infoCalidad() {
		return "detalle-calidad";
	}

	//Método para mapear la pantalla calidad_nueva_prueba
	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	@GetMapping("calidad-nueva-prueba/{tipo}/{id}")
	public String addCalidad(@PathVariable(name = "id") Long id,@PathVariable(name = "tipo") String tipo, Model model) {
		if(tipo(tipo).equals("tipo")){
			return "redirect:/calidad-nueva-prueba/"+tipo+"/"+id;
		}
		try {
			addPruebaCalidad(disenioCalidad.findOneById(id, tipo(tipo)).getIdCalidad(),id,tipo(tipo),model);
			return "calidad-nueva-prueba";
		} 
		
		catch (Exception e) {
			System.out.println("nuevo: "+e);
			return "calidad-nueva-prueba";
		}
		
		finally {
			//si es forro se mostrará la etiqueta de forro en el html
			if(tipo.equals("forro")) {
				System.out.println("si esta entrando aqui");
				model.addAttribute("forro", "true");
				model.addAttribute("tela", disenioForro.findOne(id).getNombreForro());
				
			}
			//si es tela se mostrará la etiqueta de tela en el html
			else {
				model.addAttribute("forro", "false");
				model.addAttribute("tela", disenioTela.findOne(id).getNombreTela());
			}
			
			//Listar entretelas
			List<DisenioLookup> lookupEntretela = disenioLookup.findByTipoLookup("Material");
			Long variable=0L;
			for (DisenioLookup i: lookupEntretela) {
				if (i.getNombreLookup().equalsIgnoreCase("Entretela")) {
					variable = i.getIdLookup();
				}
			}
			if(variable==null) {
				System.out.println("no existe entretela en el lockup");
			}
			else {
				System.out.println(variable);
				model.addAttribute("listEntretelas",materialService.findAllByTipoMaterial(variable));
			}
			
			//Listar operarios
			model.addAttribute("operarios", empleadoService.findAllByDepartamento("DISEÑO"));
			//Listar Medidas de Agujas
			model.addAttribute("medidaAgujas",disenioLookup.findByTipoLookup("Medida Aguja"));
			
			model.addAttribute("tipoMaterial", tipo(tipo));
			model.addAttribute("idMaterial", id);
			
			System.out.println("Terminó el proceso addCalidad");
		}
	}
	

	@Secured({"ROLE_DISENIO_CALIDAD_REGISTRAR", "ROLE_ADMINISTRADOR"})
	public String addPruebaCalidad(Long id,Long idM,String tipo, Model model) {
		List<DisenioPruebaEncogimientoLavado> pruebasEL = pruebaEncogiLavado.findAllByCalidad(id);
		List<DisenioPruebaLavadoContaminacionCostura> pruebasLCC = pruebaContaCostura.findAllByCalidad(id);
		//DisenioCalidad Calidad = disenioCalidad.findOne(id);
		//model.addAttribute("idTela", Calidad.getIdMaterial());		
		
		model.addAttribute("idCalidad", id);
		model.addAttribute("idMaterial", id);
		model.addAttribute("tipoMaterial", tipo);
		model.addAttribute("readtela", "true");
		
		try {
			//Verifica si existe al meno una prueba
			if (pruebaContaCostura.ifExist(id) == 1 || pruebaEncogiLavado.ifExist(id) == 1) {
				//for para recorrer las pruebas de encogimiento y lavado
				for (DisenioPruebaEncogimientoLavado u : pruebasEL) {
					if (u.getTipoPrueba().equalsIgnoreCase("Prueba de Fusión")) {
						// model.addAttribute("read", "true");
						model.addAttribute("operarioEncogi", u.getIdOperario());
						model.addAttribute("fechaRealizacionEncogi", u.getFechaRealizacion().replace(" ", "T"));
						
						if (u.getAdherenciaPruebaVapor().equals("buena")) {
							model.addAttribute("checkBFusion", "true");
						}
						if (u.getAdherenciaPruebaVapor().equals("regular")) {
							model.addAttribute("checkRFusion", "true");
						}
						if (u.getAdherenciaPruebaVapor().equals("mala")) {
							model.addAttribute("checkMFusion", "true");
						}
						
						model.addAttribute("proveedorEncogi", u.getProveedorPruebaVapor());
						model.addAttribute("temperaturaPruebaVapor", u.getTemperaturaPruebaVapor());
						model.addAttribute("tiempoPruebaVapor", u.getTiempoPrueba());
						model.addAttribute("presionPruebaVapor", u.getPresionPrueba());
						model.addAttribute("medidaHiloPruebaVapor", u.getMedidaInicialHilo());
						model.addAttribute("finalTramaPruebaVapor", u.getMedidaInicialTrama());
						model.addAttribute("diferenciaHiloPruebaVapor", u.getMedidaFinalHilo());
						model.addAttribute("finalHiloMedPruebaVapor", u.getDiferenciaMedidaHilo());
						model.addAttribute("diferenciaTramaPruebaVapor", u.getMedidaFinalTrama());
						model.addAttribute("finalTramaMedPruebaVapor", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPruebaVapor", u.getObservacionesResultados());
						model.addAttribute("proovedores", u.getProveedorPruebaVapor());
						model.addAttribute("entretelas", u.getEntretelaPruebaVapor());
						// model.addAttribute("displa", "true");
					}
					
					if (u.getTipoPrueba().equalsIgnoreCase("Vapor")) {
						model.addAttribute("operarioEncogi", u.getIdOperario());
						model.addAttribute("fechaRealizacionEncogi", u.getFechaRealizacion().replace(" ", "T"));
						model.addAttribute("medidaHiloPlanchaVapor", u.getMedidaInicialHilo());
						model.addAttribute("medidaTramaPlanchaVapor", u.getMedidaInicialTrama());
						model.addAttribute("diferenciaHiloPlanchaVapor", u.getMedidaFinalHilo());
						model.addAttribute("finalHiloMedPlanchaVapor", u.getDiferenciaMedidaHilo());
						model.addAttribute("diferenciaTramaPlanchaVapor", u.getMedidaFinalTrama());
						model.addAttribute("finalTramaMedPlanchaVapor", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPlanchaVapor", u.getObservacionesResultados());
					}
					
					if (u.getTipoPrueba().equalsIgnoreCase("Prueba de Lavado")) {
						 //model.addAttribute("readLavado", "false");
						model.addAttribute("operarioLavado", u.getIdOperario());
						model.addAttribute("fechaRealizacionLavado", u.getFechaRealizacion().replace(" ", "T"));
						model.addAttribute("medidaHiloPruebaLavado", u.getMedidaInicialHilo());
						model.addAttribute("medidaTramaPruebaLavado", u.getMedidaInicialTrama());
						model.addAttribute("diferenciaHiloPruebaLavado", u.getMedidaFinalHilo());
						model.addAttribute("finalHiloMedPruebaLavado", u.getDiferenciaMedidaHilo());
						model.addAttribute("diferenciaTramaPruebaLavado", u.getMedidaFinalTrama());
						model.addAttribute("finalTramaMedPruebaLavado", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPruebaLavado", u.getObservacionesResultados());
						// model.addAttribute("displaLavado", "true");
					}
				}
				//for para recorrer las pruebas de pilling, costura y contaminación
				for (DisenioPruebaLavadoContaminacionCostura cc : pruebasLCC) {
					if (cc.getTipoPrueba().equalsIgnoreCase("Solidez/Color/Pilling")) {
						model.addAttribute("observacionesReultPilling", cc.getObservacionesResultados());
						
						if (cc.getPruebaCalidad().equals("buena")) {
							model.addAttribute("checkBLavado", "true");
						}
						if (cc.getPruebaCalidad().equals("regular")) {
							model.addAttribute("checkRLavado", "true");
						}
						if (cc.getPruebaCalidad().equals("mala")) {
							model.addAttribute("checkMLavado", "true");
						}
						if (cc.getPrueba_pilling().equals("si")) {
							model.addAttribute("checkSLavado", "true");
						}
						if (cc.getPrueba_pilling().equals("no")) {
							model.addAttribute("checkNLavado", "true");
						}
					}
			
					if (cc.getTipoPrueba().equalsIgnoreCase("Resultado costura")) {
						model.addAttribute("operarioCostura", cc.getIdOperario());
						model.addAttribute("fechaRealizacionCostura", cc.getFechaRealizacion().replace(" ", "T"));
						// model.addAttribute("readCostura", "true");
						model.addAttribute("observacionesRasgado", cc.getObservacionesResultados());
						// model.addAttribute("displaCostura", "true");
						model.addAttribute("aguja", cc.getTipoAguja());
						
						if (cc.getDeslizamientoTela().equals("si")) {
							model.addAttribute("checkSDeslizamiento", "true");
						}
						if (cc.getDeslizamientoTela().equals("no")) {
							model.addAttribute("checkNDeslizamiento", "true");
						}
	
						if (cc.getRasgadoTela().equals("si")) {
							model.addAttribute("checkSCostura", "true");
						}
						if (cc.getRasgadoTela().equals("no")) {
							model.addAttribute("checkNCostura", "true");
						}
					}
					
					if (cc.getTipoPrueba().equalsIgnoreCase("Resultado de Contaminación")) {
						model.addAttribute("operarioContaminacion", cc.getIdOperario());
						model.addAttribute("fechaRealizacionContaminacion", cc.getFechaRealizacion().replace(" ", "T"));
						// model.addAttribute("readContamin", "true");
						model.addAttribute("observacionesReultContaminacion", cc.getObservacionesResultados());
						// model.addAttribute("displaConta", "true");
						
						if (cc.getPruebaCalidad().equals("buena")) {
							model.addAttribute("checkBConta", "true");
						}
						if (cc.getPruebaCalidad().equals("regular")) {
							model.addAttribute("checkRConta", "true");
						}
						if (cc.getPruebaCalidad().equals("mala")) {
							model.addAttribute("checkMConta", "true");
						}
						
					}
				}
				
				return "/calidad-nueva-prueba";
			} else {
				return "calidad-nueva-prueba";
			}
		}
		catch(Exception e) {
			System.out.println("Hubo un problema al mapear \n Error:   "+e);
			return "/calidad";
		}
		finally {
			System.out.println("Terminó el proceso addPruebaCalidad");
		}
	}
	
	@Secured({"ROLE_DISENIO_CALIDAD_LISTAR", "ROLE_ADMINISTRADOR"})
	@RequestMapping(value = "/detalle-calidad/{tipo}/{id}", method = RequestMethod.GET)
	public String infoPruebasCalidad(@PathVariable(name = "id") Long id,@PathVariable(name = "tipo") String tipo, Model model) {
		try {
			
			Long idCalidad = disenioCalidad.findOneById(id, tipo(tipo)).getIdCalidad();
			List<DisenioPruebaEncogimientoLavado> pruebasEL = pruebaEncogiLavado.findAllByCalidad(idCalidad);
			List<DisenioPruebaLavadoContaminacionCostura> pruebasLCC = pruebaContaCostura.findAllByCalidad(idCalidad);		
			
			
			//Si es forro se ocultan las pruebas de fusión y las pruebas de contaminación
			if(tipo.equalsIgnoreCase("forro")) {
				model.addAttribute("detallesForro", "display:none");
			}
			

			//Verifica si existe al meno una prueba
			if (pruebaContaCostura.ifExist(idCalidad) == 1 || pruebaEncogiLavado.ifExist(idCalidad) == 1) {
				//for para recorrer las pruebas de encogimiento y lavado
				for (DisenioPruebaEncogimientoLavado u : pruebasEL) {
					if (u.getTipoPrueba().equalsIgnoreCase("Prueba de Fusión")) {
						
						model.addAttribute("adherenciaEnco", u.getAdherenciaPruebaVapor());
						model.addAttribute("proveedorEncogi", u.getProveedorPruebaVapor());
						try {
							model.addAttribute("entretela",
									materialService.findOne(Long.valueOf(u.getEntretelaPruebaVapor())).getNombreMaterial());
						} catch (Exception e) {
							model.addAttribute("entretela", " ");
						}
						
						model.addAttribute("temperaturaVapor", u.getTemperaturaPruebaVapor());
						model.addAttribute("tiempoVapor", u.getTiempoPrueba());
						model.addAttribute("presionVapor", u.getPresionPrueba());
						model.addAttribute("finalMedHiloPruebaVapor", u.getMedidaFinalHilo());
						model.addAttribute("diferenciaMedPruebaVapor", u.getDiferenciaMedidaHilo());
						model.addAttribute("finalTramaMedPruebaVapor", u.getMedidaFinalTrama());
						model.addAttribute("diferenciaTramaPruebaVapor", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPruebaVapor", u.getObservacionesResultados());
					}

					if (u.getTipoPrueba().equalsIgnoreCase("Vapor")) {
						model.addAttribute("finalHiloMedPlanchaVapor", u.getMedidaFinalHilo());
						model.addAttribute("diferenciaHiloPlanchaVapor", u.getDiferenciaMedidaHilo());
						model.addAttribute("finalTramaMedPlanchaVapor", u.getMedidaFinalTrama());
						model.addAttribute("diferenciaTramaPlanchaVapor", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPlanchaVapor", u.getObservacionesResultados());
					}
					
					if (u.getTipoPrueba().equalsIgnoreCase("Prueba de Lavado")) {
						
						model.addAttribute("finalHiloMedPruebaLavado", u.getMedidaFinalHilo());
						model.addAttribute("diferenciaHiloPruebaLavado", u.getDiferenciaMedidaHilo());
						model.addAttribute("finalTramaMedPruebaLavado", u.getMedidaFinalTrama());
						model.addAttribute("diferenciaTramaPruebaLavado", u.getDiferenciaMedidaTrama());
						model.addAttribute("observacionesReultPruebaLavado", u.getObservacionesResultados());
					}
				}
				//for para recorrer las pruebas de pilling, castura y contaminación
				for (DisenioPruebaLavadoContaminacionCostura cc : pruebasLCC) {
					
					if (cc.getTipoPrueba().equalsIgnoreCase("Solidez/Color/Pilling")) {
						model.addAttribute("solidezColor", cc.getPruebaCalidad());
						model.addAttribute("resultadoPilling", cc.getPrueba_pilling());
						model.addAttribute("observacionesReultPilling", cc.getObservacionesResultados());
					}
					
					if (cc.getTipoPrueba().equalsIgnoreCase("Resultado costura")) {
						model.addAttribute("observacionescostura", cc.getObservacionesResultados());
						model.addAttribute("Deslizamiento", cc.getDeslizamientoTela());
						model.addAttribute("Costura", cc.getRasgadoTela());
						
					}

					
					if (cc.getTipoPrueba().equalsIgnoreCase("Resultado de contaminación")
							|| cc.getTipoPrueba().equalsIgnoreCase("Resultado de contaminacion")) {
						model.addAttribute("observacionesReultContaminacion", cc.getObservacionesResultados());
						model.addAttribute("calidadContaminacion", cc.getPruebaCalidad());
						
					}
					
				}
				return "/detalle-calidad";
			} else {
				return "/detalle-calidad";
			}
		} catch (Exception e) {
			System.out.println("nuevo: "+e);
			return "redirect:/calidad";
		}
		finally {
			System.out.println("Terminó el proceso infoPruebasCalidad");
		}
	}
	@GetMapping("/calidad-estatus-aprobado/{id}")
	public String Approved(@PathVariable(name = "id") Long id, Model model) {
		try {
			DisenioCalidad calidad = new DisenioCalidad();
			calidad = disenioCalidad.findOne(id);
			calidad.setEstatus("2");
			disenioCalidad.save(calidad);
			return "redirect:/calidad";
		}
		catch (Exception e) {
			System.out.println("Error:   "+e);
			return "redirect:/calidad";
		}
		finally {
			System.out.println("Terminó el proceso Approved");
		}
		
	}
	
	@GetMapping("/calidad-estatus-rechazado/{id}")
	public String Rejected(@PathVariable(name = "id") Long id, Model model) {
		try {
			DisenioCalidad calidad = new DisenioCalidad();
			calidad = disenioCalidad.findOne(id);
			calidad.setEstatus("3");
			disenioCalidad.save(calidad);
			return "redirect:/calidad";
		}
		catch (Exception e) {
			System.out.println("Error:   "+e);
			return "redirect:/calidad";
		}
		finally {
			System.out.println("Terminó el proceso Rejected");
		}
	}
}
