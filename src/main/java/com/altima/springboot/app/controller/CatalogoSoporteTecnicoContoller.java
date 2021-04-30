package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import com.altima.springboot.app.models.entity.SoporteTecnicoLookup;
import com.altima.springboot.app.models.service.ISoporteTecnicoLookupService;
import org.springframework.util.StringUtils;
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
public class CatalogoSoporteTecnicoContoller {
	@Autowired
	private ISoporteTecnicoLookupService soporteService;

    @GetMapping("/catalogos-servicio-tecnico")
    public String catalogos (){
        return "catalogos-soporte-tecnico";
    }
    
    
	@RequestMapping(value = "/verificar_duplicado_soporte_tecnico", method = RequestMethod.POST)
	@ResponseBody
	public boolean verificaduplicado(String Lookup, String Tipo,String descripcion,String atributo1, String atributo2, String atributo3 ) {
		return soporteService.findDuplicate(Lookup, Tipo,  descripcion, atributo1,  atributo2,  atributo3 );
	}

	@RequestMapping(value = "listar_lookup_soporte_tecnico", method = RequestMethod.GET)
	@ResponseBody
	public List<SoporteTecnicoLookup> listarLookuop(String Tipo) {
		return soporteService.findAllLookup(Tipo);
	}

	@RequestMapping(value = "cambio_estatus_soporte_tecnico", method = RequestMethod.POST)
	@ResponseBody
	public String cambioEstatus(Long idLookup, Integer estatus) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		SoporteTecnicoLookup editar = soporteService.findOne(idLookup);
		editar.setEstatus(estatus);
		editar.setActualizadoPor(auth.getName());
		editar.setUltimaFechaModificacion(dateFormat.format(date));
		soporteService.save(editar);
		return editar.getTipoLookup();
	}

	@PostMapping("/guardar_lookup_soporte_tecnico")
	public String guardacatalogo(Long idLook, String nombre, String tipo , String descripcion, String atributo1, String atributo2,String atributo3, String clave) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();
		if (idLook == null){
			SoporteTecnicoLookup nuevo = new SoporteTecnicoLookup();
			SoporteTecnicoLookup ultimoid = null;
			try {
				ultimoid = soporteService.findLastLookupByType(tipo);

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
			soporteService.save(nuevo);
		}else{
			SoporteTecnicoLookup editar = soporteService.findOne(idLook);
			editar.setNombreLookup(nombre);
			editar.setDescripcionLookup(descripcion);
			editar.setAtributo1(atributo1);
			editar.setAtributo2(atributo2);
			editar.setAtributo3(atributo3);
			editar.setActualizadoPor(auth.getName());
			editar.setUltimaFechaModificacion(dateFormat.format(date));
			soporteService.save(editar);
		}
		fmt.close();
		return "redirect:catalogos";
	}
}
