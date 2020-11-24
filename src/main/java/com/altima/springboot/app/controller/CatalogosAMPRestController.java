package com.altima.springboot.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.altima.springboot.app.models.entity.AmpAlmacen;
import com.altima.springboot.app.models.entity.AmpAlmacenUbicacion;
import com.altima.springboot.app.models.entity.AmpLookup;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.service.IAmpAlmacenService;
import com.altima.springboot.app.models.service.IAmpAlmacenUbicacion;
import com.altima.springboot.app.models.service.IAmpLoookupService;

@RestController
public class CatalogosAMPRestController {
	@Autowired
	IAmpLoookupService LookupService;
	
	@Autowired
	IAmpAlmacenService AlmacenService;
	
	@Autowired
	IAmpAlmacenUbicacion UbicacionService;
	
	@RequestMapping(value = "/verificar-duplicado-amp", method = RequestMethod.GET)
	@ResponseBody
	public boolean verificaduplicado(String Lookup, String Tipo,@RequestParam(required=false) String atributo) {
		boolean resp;
		System.out.println("hola soy el look"+Lookup);
		System.out.println("hola soy el tipo"+Tipo);
		if (atributo == null ) {
			resp=LookupService.findDuplicate(Lookup, Tipo);
		}else {
			resp=LookupService.findDuplicate(Lookup, Tipo, atributo);
		}
		
		return  resp;
	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_LISTAR"})
	@RequestMapping(value = "/listar-amp", method = RequestMethod.GET)
	@ResponseBody
	public List<AmpLookup> listarlookup(String Tipo) {

	
		return LookupService.findAllLookup(Tipo);
	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_LISTAR"})
	@RequestMapping(value = "/listar-amp-linea", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> listarlookuplinea(String Tipo) {

		return LookupService.findAllLinea();
	}
	
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_ELIMINAR"})
	@PostMapping("/baja-catalogo-amp")
	public String bajacatalogo(Long idcatalogo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		AmpLookup lookp = null;
		lookp = LookupService.findOne(idcatalogo);
		lookp.setEstatus(0);
		lookp.setUltimaFechaModificacion(dateFormat.format(date));
		lookp.setActualizadoPor(auth.getName());
		LookupService.save(lookp);
		return "redirect:catalogos";

	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_ELIMINAR"})
	@PostMapping("/reactivar-catalogo-amp")
	@ResponseBody
	public String reactivarcatalogo(Long idcatalogo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		AmpLookup lookp = null;
		lookp = LookupService.findOne(idcatalogo);
		lookp.setEstatus(1);
		lookp.setUltimaFechaModificacion(dateFormat.format(date));
		lookp.setActualizadoPor(auth.getName());
		LookupService.save(lookp);
		return lookp.getTipoLookup();

	}
	
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_EDITAR", "ROLE_COMERCIAL_AMP_CATALOGOS_AGREGAR"})
	@PostMapping("/guardar-catalogo-amp")
	public String guardacatalogo(String clasificacion , String id_clasificacion, String atributo,
			String linea, String movimiento , String tipo, String pasillo, String idAlmacen) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();
		if (clasificacion != null) {
			AmpLookup clasificacionLook = new AmpLookup();
			AmpLookup ultimoid = null;
			try {
				ultimoid = LookupService.findLastLookupByType("Clasificacion");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				clasificacionLook.setIdText("CLAS" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				clasificacionLook.setIdText("CLAS" + fmt.format("%04d", (cont + 1)));
			}

			clasificacionLook.setNombreLookup(StringUtils.capitalize(clasificacion));
			clasificacionLook.setTipoLookup("Clasificacion");
			clasificacionLook.setCreadoPor(auth.getName());
			clasificacionLook.setFechaCreacion(dateFormat.format(date));
			clasificacionLook.setEstatus(1);
			clasificacionLook.setAtributo1(atributo);
			LookupService.save(clasificacionLook);
			return "catalogos";
		}
		
		if (linea != null) {
			AmpLookup clasificacionLook = new AmpLookup();
			AmpLookup ultimoid = null;
			try {
				ultimoid = LookupService.findLastLookupByType("Linea");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				clasificacionLook.setIdText("LINEA" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				clasificacionLook.setIdText("LINEA" + fmt.format("%04d", (cont + 1)));
			}

			clasificacionLook.setNombreLookup(StringUtils.capitalize(linea));
			clasificacionLook.setTipoLookup("Linea");
			clasificacionLook.setCreadoPor(auth.getName());
			clasificacionLook.setFechaCreacion(dateFormat.format(date));
			clasificacionLook.setEstatus(1);
			clasificacionLook.setDescripcionLookup(id_clasificacion);
			LookupService.save(clasificacionLook);
			return "catalogos";
		}
		
		if (movimiento != null) {
			AmpLookup clasificacionLook = new AmpLookup();
			AmpLookup ultimoid = null;
			try {
				ultimoid = LookupService.findLastLookupByType("Movimiento");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				clasificacionLook.setIdText("MOV" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				clasificacionLook.setIdText("MOV" + fmt.format("%04d", (cont + 1)));
			}

			clasificacionLook.setNombreLookup(StringUtils.capitalize(movimiento));
			clasificacionLook.setTipoLookup("Movimiento");
			clasificacionLook.setCreadoPor(auth.getName());
			clasificacionLook.setFechaCreacion(dateFormat.format(date));
			clasificacionLook.setEstatus(1);
			clasificacionLook.setDescripcionLookup(tipo);
			LookupService.save(clasificacionLook);
			return "catalogos";
		}
		
		if (pasillo != null) {
			AmpLookup pasilloLook = new AmpLookup();
			AmpLookup ultimoid = null;
			try {
				ultimoid = LookupService.findLastLookupByType("Pasillo");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				pasilloLook.setIdText("PASI" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				pasilloLook.setIdText("PASI" + fmt.format("%04d", (cont + 1)));
			}

			pasilloLook.setNombreLookup(StringUtils.capitalize(pasillo));
			pasilloLook.setTipoLookup("Pasillo");
			pasilloLook.setAtributo1(idAlmacen);
			pasilloLook.setCreadoPor(auth.getName());
			pasilloLook.setFechaCreacion(dateFormat.format(date));
			pasilloLook.setEstatus(1);
			pasilloLook.setDescripcionLookup(tipo);
			LookupService.save(pasilloLook);
			return "catalogos";
		}
		fmt.close();
		return "redirect:catalogos";

	}
	
	//  A L M A C E N  C O M I E N Z A 
	
	@RequestMapping(value = "/verificar-duplicado-almacen", method = RequestMethod.GET)
	@ResponseBody
	public boolean verificaduplicado_almacen(String almacen, String encargado) {
		boolean resp;
			resp=AlmacenService.findDuplicate(almacen, encargado);
		
		return  resp;
	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_EDITAR", "ROLE_COMERCIAL_AMP_CATALOGOS_AGREGAR"})
	@PostMapping("/guardar-catalogo-almacen")
	public String guarda_almacen(String almacen , String encargado, String entrada, String salida) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		AmpAlmacen almacenObj = new AmpAlmacen();
		almacenObj.setNombreAlmacen(almacen);
		almacenObj.setEncargado(encargado);
		almacenObj.setMovimientoEntrada(entrada);
		almacenObj.setMovimientoSalida(salida);
		almacenObj.setCreadoPor(auth.getName());
		almacenObj.setFechaCreacion(dateFormat.format(date));
		almacenObj.setActualizadoPor(null);
		almacenObj.setUltimaFechaModificacion(null);
		AlmacenService.save(almacenObj);
		
		
		return "redirect:catalogos";
		

	
	}
	
	
    @Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_EDITAR"})
	@PostMapping("/editar-catalogo-amp")
	public String editacatalogo(Long idLookup, String linea, String Clasificacion ,String atributo, String pasillo, String idAlmacen) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AmpLookup Linea = null;
		AmpLookup clas = null;
		AmpLookup Pasillo = null;
		if (linea != null && idLookup > 0) {
			Linea = LookupService.findOne(idLookup);
			Linea.setNombreLookup(StringUtils.capitalize(linea));
			Linea.setDescripcionLookup(Clasificacion);
			Linea.setUltimaFechaModificacion(currentDate());
			Linea.setActualizadoPor(auth.getName());
			LookupService.save(Linea);
			return "redirect:catalogos";
		}
		
		if (Clasificacion != null && idLookup > 0) {
			clas = LookupService.findOne(idLookup);
			clas.setNombreLookup(StringUtils.capitalize(Clasificacion));
			clas.setAtributo1(atributo);
			clas.setUltimaFechaModificacion(currentDate());
			clas.setActualizadoPor(auth.getName());
			LookupService.save(clas);
			return "redirect:catalogos";
		}
		if (pasillo != null && idLookup > 0) {
			Pasillo = LookupService.findOne(idLookup);
			Pasillo.setNombreLookup(StringUtils.capitalize(pasillo));
			Pasillo.setUltimaFechaModificacion(currentDate());
			Pasillo.setAtributo1(idAlmacen);
			Pasillo.setActualizadoPor(auth.getName());
			LookupService.save(Pasillo);
			return "redirect:catalogos";
		}
	
		return "redirect:catalogos";
	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_LISTAR"})
	@RequestMapping(value = "/listar-almacenes-fisicos", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> listar() {

		return LookupService.findAllAlmacen();
	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_COMERCIAL_AMP_CATALOGOS_LISTAR"})
	@RequestMapping(value = "/listar-amp-pasillos", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> listaPAsillo() {

		return LookupService.findAllPasillos();
	}
	
	@RequestMapping(value = "/agregar-ubicacion-almacen", method = RequestMethod.POST)
	@ResponseBody
	public boolean agregarUbicacion(Long idAlmacen , Integer fila , Integer casillero, Integer anaquel) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("el id almace: "+idAlmacen );
		System.out.println("FILA: "+fila );
		System.out.println("CASILLERO: "+casillero );
		
		System.out.println("anaquel "+ anaquel);
		
		//A02-F01-C01
		DecimalFormat decimalFormat = new DecimalFormat("000");  
		System.out.println();
		for (int i = 1; i <= fila; ++i) {
			for (int j = 1; j <= casillero; ++j) {
				
				if ( UbicacionService.findOne(idAlmacen, "A"+decimalFormat.format(anaquel)+"-F"+decimalFormat.format(i)+"-C"+decimalFormat.format(j)) == null)
				{
					AmpAlmacenUbicacion ubi = new AmpAlmacenUbicacion ();
					
					ubi.setIdAlmacenFisico(idAlmacen);
					ubi.setNombre("A"+decimalFormat.format(anaquel)+"-F"+decimalFormat.format(i)+"-C"+decimalFormat.format(j));
				    ubi.setCreadoPor(auth.getName());
				    ubi.setFechaCreacion(dateFormat.format(date));
				    ubi.setEstatus("1");
				    UbicacionService.save(ubi);
				}
					
			}
		}
		return true;
	}
	@RequestMapping(value = "/listar-ubicacion-almacen", method = RequestMethod.GET)
	@ResponseBody
	public List<AmpAlmacenUbicacion> listaUbicacion(Long id,@RequestParam(required = false) boolean estatus) {

		return UbicacionService.findAll(id,estatus);
	}
	
	@RequestMapping(value = "/baja-alta-ubicacion", method = RequestMethod.POST)
	@ResponseBody
	public boolean bajaUbicacion(Long id , String accion) {
		AmpAlmacenUbicacion ubicacion =	UbicacionService.findOne(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		if (accion.equals("baja")) {
			ubicacion.setEstatus("0");
			ubicacion.setActualizadoPor(auth.getName());
			ubicacion.setUltimaFechaModificacion(dateFormat.format(date));
			UbicacionService.save(ubicacion);
		}
		if (accion.equals("alta")) {
			ubicacion.setEstatus("1");
			ubicacion.setActualizadoPor(auth.getName());
			ubicacion.setUltimaFechaModificacion(dateFormat.format(date));
			UbicacionService.save(ubicacion);
		}
		
		return true;
	}

	private String currentDate() {
		Date date = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		hourdateFormat.setTimeZone(timeZone);
		String sDate = hourdateFormat.format(date);
		return sDate;
	}
}
