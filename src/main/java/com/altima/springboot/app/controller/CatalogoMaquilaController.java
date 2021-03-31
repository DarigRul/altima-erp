package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.MaquilaLookup;
import com.altima.springboot.app.models.service.IMaquilaLookupService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = { "*" })
@Controller
public class CatalogoMaquilaController {

	@Autowired
	IMaquilaLookupService lookupService;

	@RequestMapping(value = { "/catalogo-maquila" }, method = RequestMethod.GET)
	public String catalogo() {
		return "catalogo-maquila";
	}

	@RequestMapping(value = "/verificar_duplicado_maquila_lookup", method = RequestMethod.GET)
	@ResponseBody
	public boolean verificaduplicado(String Lookup, String Tipo, String descripcion, String atributo1, String atributo2,
			String atributo3) {

		return lookupService.findDuplicate(Lookup, Tipo, descripcion, atributo1, atributo2, atributo3);
	}

	// listar-catalogo-produccion
	@RequestMapping(value = "listar_maquila_object", method = RequestMethod.GET)
	@ResponseBody
	public List<MaquilaLookup> listarProveedoresColores(String Tipo) {

		return lookupService.findAllLookup(Tipo);
	}

	// listar-catalogo-produccion
	@RequestMapping(value = "listar_AFI_maquila", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> AFI() {

		return lookupService.listarActivos();
	}

	// listar-catalogo-produccion
	@RequestMapping(value = "listar_color_maquila", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> color() {
		return lookupService.listarColor();
	}

	@RequestMapping(value = "debolver_lookup_maquila_by_id", method = RequestMethod.GET)
	@ResponseBody
	public MaquilaLookup by_id(Long id) {

		return lookupService.findOne(id);
	}

	// listar-catalogo-produccion por estatus
	@RequestMapping(value = "listar_maquila_object_estatus", method = RequestMethod.GET)
	@ResponseBody
	public List<MaquilaLookup> listar_maquila_object_estatus(String Tipo, String estatus) {
		return lookupService.findAll(Tipo, estatus);
	}

	// listar-catalogo-produccion por estatus
	@RequestMapping(value = "listar_maquila_operaciones", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listar_maquila_operaciones() {
		return lookupService.Operaciones();
	}

	@PostMapping("/guardar-lookup-maquila")
	public String guardacatalogo(Long idLook, String nombre, String tipo, String descripcion, String atributo1,
			String atributo2, String atributo3) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();
		if (idLook == null) {
			MaquilaLookup f = new MaquilaLookup();
			MaquilaLookup ultimoid = null;
			try {
				ultimoid = lookupService.findLastLookupByType(tipo);

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				f.setIdText(tipo.substring(0, 3).toUpperCase() + "0001");
			} else {
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				f.setIdText("" + tipo.substring(0, 3).toUpperCase() + fmt.format("%04d", (cont + 1)));
			}
			f.setDescripcionLookup(descripcion);
			f.setNombreLookup(StringUtils.capitalize(nombre));
			f.setTipoLookup(tipo);
			f.setCreadoPor(auth.getName());
			f.setFechaCreacion(dateFormat.format(date));
			f.setEstatus(1);
			f.setAtributo1(atributo1);
			f.setAtributo2(atributo2);
			f.setAtributo3(atributo3);
			lookupService.save(f);
		} else {
			MaquilaLookup editar = lookupService.findOne(idLook);
			editar.setNombreLookup(nombre);
			editar.setDescripcionLookup(descripcion);
			editar.setAtributo1(atributo1);
			editar.setAtributo2(atributo2);
			editar.setAtributo3(atributo3);
			editar.setActualizadoPor(auth.getName());
			editar.setUltimaFechaModificacion(dateFormat.format(date));
			lookupService.save(editar);
		}
		fmt.close();
		return "redirect:catalogos";
	}

	//
	// cambio_estatus_maquila

	@RequestMapping(value = "cambio_estatus_maquila", method = RequestMethod.POST)
	@ResponseBody
	public String cambioEstatus(Long idLookup, Integer estatus) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		MaquilaLookup editar = lookupService.findOne(idLookup);
		editar.setEstatus(estatus);
		editar.setActualizadoPor(auth.getName());
		editar.setUltimaFechaModificacion(dateFormat.format(date));
		lookupService.save(editar);
		return editar.getTipoLookup();

	}

	@RequestMapping(value = "get_listarFamiliabyMaquinaria", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listarFamiliabyMaquinaria() {

		return lookupService.listarFamiliabyMaquinaria();
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_LISTAR" })
	@RequestMapping(value = "/lista-maquila", method = RequestMethod.GET)
	@ResponseBody
	public List<MaquilaLookup> listarlookup(String Tipo) {

		return lookupService.findAllLookup(Tipo);

	}

	@RequestMapping(value = "/verifduplicadomaquila", method = RequestMethod.GET)
	@ResponseBody
	public boolean verificaduplicado(String Lookup, String Tipo, @RequestParam(required = false) String atributo,
			String CodigoPrenda,String Descripcion) {
		boolean resp;
		try {
			if(Descripcion!=null) {
				resp= lookupService.findDuplicateMaquila(Lookup, Tipo,Descripcion);

			}else {
				resp = lookupService.findDuplicateMaquila(Lookup, Tipo);

			}
		} catch (Exception e) {
			resp = false;
		}
		return resp;
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_EDITAR", "ROLE_DISENIO_CATALOGOS_AGREGAR" })
	@PostMapping("/guardarcatalogomaquila")
	public String guardacatalogo(String Descripcion, String Color, String PiezaTrazo, String FamiliaPrenda,
			String FamiliaGenero, String FamiliaComposicion, String InstruccionCuidado, String UnidadMedida,
			String proveedorColor, String Material, String Codigo, String CodigoPrenda, HttpServletRequest request,
			String Marcador, String CodigoColor, String Posicion,
			@RequestParam(required = false) MultipartFile iconocuidado, Long Idcuidado, String Simbolo,
			String Composicion, String TipoMaterial, String CategoriaMaterial,
			@RequestParam(required = false) MultipartFile imagen,String Articulo,String Marca,String Costo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();

		if (UnidadMedida != null) {
			MaquilaLookup unidadmedida = new MaquilaLookup();
			MaquilaLookup ultimoid = null;
			try {
				ultimoid = lookupService.findLastLookupByType("Unidad Medida");

			} catch (Exception e) {

				System.out.println(e);

			}

			if (ultimoid == null) {
				unidadmedida.setIdText("UMED" + "0001");
			} else {
				System.out.println("Entra");
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				unidadmedida.setIdText("UMED" + fmt.format("%04d", (cont + 1)));
			}

			unidadmedida.setNombreLookup(StringUtils.capitalize(UnidadMedida));
			unidadmedida.setTipoLookup("Unidad Medida");
			unidadmedida.setDescripcionLookup(Simbolo);
			unidadmedida.setCreadoPor(auth.getName());
			unidadmedida.setFechaCreacion(dateFormat.format(date));
			unidadmedida.setEstatus(1);
			lookupService.save(unidadmedida);
			return "catalogos";
		}
		if (Material != null) {
			MaquilaLookup material = new MaquilaLookup();
			MaquilaLookup ultimoid = null;
			try {
				ultimoid = lookupService.findLastLookupByType("Material");

			} catch (Exception e) {

				System.out.println(e);

			}

			if (ultimoid == null) {
				material.setIdText("MAT" + "0001");
			} else {
				System.out.println("Entra");
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				material.setIdText("MAT" + fmt.format("%04d", (cont + 1)));
			}

			material.setNombreLookup(StringUtils.capitalize(Material));
			material.setTipoLookup("Material");
			// material.setNombreLookup(StringUtils.capitalize(Codigo));
			material.setDescripcionLookup(Codigo);
			material.setCreadoPor(auth.getName());
			material.setFechaCreacion(dateFormat.format(date));
			material.setEstatus(1);
			// material.setAtributo1(TipoMaterial);
			// material.setAtributo2(CategoriaMaterial);
			lookupService.save(material);
			return "catalogos";
		}
		
		if (Articulo != null) {
			MaquilaLookup articulo = new MaquilaLookup();
			MaquilaLookup ultimoid = null;
			try {
				ultimoid = lookupService.findLastLookupByType("Herramientas");

			} catch (Exception e) {

				System.out.println(e);

			}

			if (ultimoid == null) {
				articulo.setIdText("HERRUT" + "0001");
			} else {
				System.out.println("Entra");
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				articulo.setIdText("HERRUT" + fmt.format("%04d", (cont + 1)));
			}

			articulo.setNombreLookup(StringUtils.capitalize(Articulo));
			articulo.setTipoLookup("Herramientas");
			articulo.setDescripcionLookup(Marca);
			articulo.setAtributo1(Costo);
			articulo.setCreadoPor(auth.getName());
			articulo.setFechaCreacion(dateFormat.format(date));
			articulo.setEstatus(1);
			lookupService.save(articulo);
			return "catalogos";
		}
		fmt.close();
		return "redirect:catalogos";

	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_ELIMINAR" })
	@PostMapping("/bajacatalogomaquila")
	public String bajacatalogo(Long idcatalogo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		MaquilaLookup catalogo2 = null;
		catalogo2 = lookupService.findOne(idcatalogo);
		catalogo2.setEstatus(0);
		catalogo2.setUltimaFechaModificacion(dateFormat.format(date));
		catalogo2.setActualizadoPor(auth.getName());
		lookupService.save(catalogo2);
		return "redirect:catalogos";

	}

	@Secured({ "ROLE_ADMINISTRADOR" })
	@PostMapping("/reactivarcatalogomaquila")
	@ResponseBody
	public String reactivarcatalogo(Long idcatalogo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		MaquilaLookup catalogo2 = null;
		catalogo2 = lookupService.findOne(idcatalogo);
		catalogo2.setEstatus(1);
		catalogo2.setUltimaFechaModificacion(dateFormat.format(date));
		catalogo2.setActualizadoPor(auth.getName());
		lookupService.save(catalogo2);
		return catalogo2.getTipoLookup();

	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_EDITAR" })
	@PostMapping("/editarcatalogomaquila")
	public String editacatalogo(Model model, final Long idLookup, String Color, String PiezaTrazo, String FamiliaPrenda,
			String Descripcion, String FamiliaGenero, String FamiliaComposicion, String InstruccionCuidado,
			String UnidadMedida, String Material, String Codigo, String CodigoPrenda, String proveedor, String Marcador,
			String CodigoColor, String Posicion, String Simbolo, String Composicion, String TipoMaterial,
			String CategoriaMaterial, @RequestParam(required = false) MultipartFile iconocuidado,
			@RequestParam(required = false) MultipartFile imagen,String Articulo,String Marca,String Costo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		MaquilaLookup unidadmedida = null;
		MaquilaLookup material = null;
		MaquilaLookup articulo = null;

		if (UnidadMedida != null && idLookup > 0) {
			unidadmedida = lookupService.findOne(idLookup);
			unidadmedida.setNombreLookup(StringUtils.capitalize(UnidadMedida));
			unidadmedida.setDescripcionLookup(Simbolo);
			unidadmedida.setUltimaFechaModificacion(currentDate());
			unidadmedida.setActualizadoPor(auth.getName());
			lookupService.save(unidadmedida);
			return "redirect:catalogos";
		}
		if (Material != null && idLookup > 0) {
			material = lookupService.findOne(idLookup);
			material.setNombreLookup(StringUtils.capitalize(Material));
			material.setDescripcionLookup(Codigo);
			material.setUltimaFechaModificacion(currentDate());
			material.setActualizadoPor(auth.getName());
			lookupService.save(material);
			return "redirect:catalogos";
		}
		if (Articulo != null && idLookup > 0) {
			articulo = lookupService.findOne(idLookup);
			articulo.setNombreLookup(StringUtils.capitalize(Articulo));
			articulo.setDescripcionLookup(Marca);
			articulo.setAtributo1(Costo);
			articulo.setUltimaFechaModificacion(currentDate());
			articulo.setActualizadoPor(auth.getName());
			lookupService.save(articulo);
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
