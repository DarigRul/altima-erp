package com.altima.springboot.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.ProduccionLookup;
import com.altima.springboot.app.models.service.IProduccionLookupService;

@CrossOrigin(origins = { "*" })
@Controller
public class ProduccionCatalogosController {
	
	@Autowired
	IProduccionLookupService LookupService;
	
	@GetMapping("/catalogos-produccion")
	public String listCatalogos() {
		return "catalogos-produccion";
	}
	
	@PostMapping("/baja-catalogo-produccion")
	public String bajacatalogo(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		ProduccionLookup lookup = null;
		lookup = LookupService.findOne(id);
		lookup.setEstatus("0");
		lookup.setUltimaFechaModificacion(dateFormat.format(date));
		lookup.setActualizadoPor(auth.getName());
		LookupService.save(lookup);
		return "redirect:catalogos";

	}
	
	@PostMapping("/reactivar-catalogo-produccion")
	@ResponseBody
	public String reactivarcatalogo(Long idcatalogo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		ProduccionLookup lookup = null;
		lookup = LookupService.findOne(idcatalogo);
		lookup.setEstatus("1");
		lookup.setUltimaFechaModificacion(dateFormat.format(date));
		lookup.setActualizadoPor(auth.getName());
		LookupService.save(lookup);
		return lookup.getTipoLookup();

	}
	
	
	@PostMapping("/guardar-catalogo-produccion")
	public String guardacatalogo(String nomenclatura , String descripcion,
			String num_talla, String id_genero,String genero,String descripcionProceso ,String origenProceso,
			String maquilero, String telefono) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();
		if (nomenclatura != null && descripcion != null) {
			ProduccionLookup largo = new ProduccionLookup();
			ProduccionLookup ultimoid = null;
			try {
				ultimoid = LookupService.findLastLookupByType("Largo");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				largo.setIdText("LARG" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				largo.setIdText("LARG" + fmt.format("%04d",(cont + 1)));
			}

			largo.setNombreLookup(StringUtils.capitalize(nomenclatura));
			largo.setTipoLookup("Largo");
			largo.setCreadoPor(auth.getName());
			largo.setFechaCreacion(dateFormat.format(date));
			largo.setEstatus("1");
			largo.setDescripcionLookup(descripcion);
			LookupService.save(largo);
			return "catalogos";
		}
		
		if (num_talla!= null ) {
			
			System.out.println("hoola--->"+num_talla);
			ProduccionLookup talla = new ProduccionLookup();
			ProduccionLookup ultimoid = null;
			try {
				ultimoid = LookupService.findLastLookupByType("Talla");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				talla.setIdText("TALLA" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				talla.setIdText("TALLA" + fmt.format("%04d",(cont + 1)));
			}

			talla.setNombreLookup(StringUtils.capitalize(num_talla));
			talla.setTipoLookup("Talla");
			talla.setCreadoPor(auth.getName());
			talla.setFechaCreacion(dateFormat.format(date));
			talla.setEstatus("1");
			//talla.setDescripcionLookup(ubicacion);
			talla.setAtributo1(id_genero);
			talla.setAtributo2(genero);
			LookupService.save(talla);
			
			
			return "catalogos";
		}
		
		if ( descripcionProceso != null) {
			System.out.println("hoola--->"+descripcionProceso);
			ProduccionLookup proceso = new ProduccionLookup();
			ProduccionLookup ultimoid = null;
			try {
				ultimoid = LookupService.findLastLookupByType("Proceso");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				proceso.setIdText("PROC" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				proceso.setIdText("PROC" + fmt.format("%04d", cont + 1));
			}

			proceso.setNombreLookup(StringUtils.capitalize(descripcionProceso));
			proceso.setTipoLookup("Proceso");
			proceso.setCreadoPor(auth.getName());
			proceso.setFechaCreacion(dateFormat.format(date));
			proceso.setEstatus("1");
			proceso.setDescripcionLookup(origenProceso);
			LookupService.save(proceso);
			
			
			return "catalogos";
		}
		
		if ( maquilero != null) {
			System.out.println("hoola--->"+maquilero);
			ProduccionLookup maqui = new ProduccionLookup();
			ProduccionLookup ultimoid = null;
			try {
				ultimoid = LookupService.findLastLookupByType("Maquilero");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				maqui.setIdText("MAQUI" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				maqui.setIdText("MAQUI" + fmt.format("%04d",(cont + 1)));
			}

			maqui.setNombreLookup(StringUtils.capitalize(maquilero));
			maqui.setTipoLookup("Maquilero");
			maqui.setCreadoPor(auth.getName());
			maqui.setFechaCreacion(dateFormat.format(date));
			maqui.setEstatus("1");
			maqui.setDescripcionLookup(telefono);
			LookupService.save(maqui);
			
			
			return "catalogos";
		}
		fmt.close();
		return "redirect:catalogos";

	}
	
	@RequestMapping(value = "/verificar-duplicado-produccion", method = RequestMethod.GET)
	@ResponseBody
	public boolean verificaduplicado(String Lookup, String Tipo,
			@RequestParam(required=false) String Atributo1, 
			@RequestParam(required=false) String Atributo2,  
			@RequestParam(required=false) String descripcion) {
		
		boolean resp;
		
			resp=LookupService.findDuplicate(Lookup, Tipo);
			
			if ( Tipo.equals("Talla")) {
				resp=LookupService.findDuplicate(Lookup, Tipo, Atributo1, Atributo2 );
			}
			if (Tipo.equals("Proceso")) {
				resp=LookupService.findDuplicate(Lookup, Tipo, descripcion);
			}
			if (Tipo.equals("Maquilero")) {
				resp=LookupService.findDuplicate(Lookup, Tipo, descripcion);
			}
		return  resp;

	}

	@RequestMapping(value = "/listar-catalogo-produccion", method = RequestMethod.GET)
	@ResponseBody
	public List<ProduccionLookup> listarlookup(String Tipo) {

		return LookupService.findAllLookup(Tipo);
	}
	
	@PostMapping("/editar-catalogo-produccion")
	public String editacatalogo(final Long idLookup, String descripcionProceso, String origenProceso, String maquilero,String telefono) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		
		if (descripcionProceso != null && idLookup > 0) {
			ProduccionLookup proceso = null;
			proceso = LookupService.findOne(idLookup);
			proceso.setNombreLookup(StringUtils.capitalize(descripcionProceso));
			proceso.setDescripcionLookup(origenProceso);
			proceso.setUltimaFechaModificacion(currentDate());
			proceso.setActualizadoPor(auth.getName());
			LookupService.save(proceso);
			return "redirect:catalogos";
		}
		
		if (maquilero != null && idLookup > 0) {
			ProduccionLookup proceso = null;
			proceso = LookupService.findOne(idLookup);
			proceso.setNombreLookup(StringUtils.capitalize(maquilero));
			proceso.setDescripcionLookup(telefono);
			proceso.setUltimaFechaModificacion(currentDate());
			proceso.setActualizadoPor(auth.getName());
			LookupService.save(proceso);
			return "redirect:catalogos";
		}
		
		return "redirect:catalogos";
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
