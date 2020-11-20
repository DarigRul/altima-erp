package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.models.entity.DisenioComposicionIcuidado;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioPrecioComposicion;
import com.altima.springboot.app.models.service.ICatalogoService;
import com.altima.springboot.app.models.service.IComprasProveedorService;
import com.altima.springboot.app.models.service.IDisenioComposicionCuidadoService;
import com.altima.springboot.app.models.service.IDisenioPrecioComposicionService;
import com.altima.springboot.app.models.service.IUploadService;

@CrossOrigin(origins = { "*" })
@Controller
public class CatalogoController {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	ICatalogoService catalogo;

	@Autowired
	IDisenioComposicionCuidadoService composicioncuidado;

	@Autowired
	private IUploadService uploadFileService;
	
	@Autowired
	private IComprasProveedorService proveedorService;
	
	@Autowired
	private IDisenioPrecioComposicionService disenioComposicion;

	@GetMapping(value = "/uploads/cuidados/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.loadfile(filename, 1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_LISTAR"})
	@RequestMapping(value = "listarProveedoresColores", method = RequestMethod.GET)
	@ResponseBody
	public List<ComprasProveedores> listarProveedoresColores() {
		
		return  proveedorService.findAll();

	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_LISTAR"})
	@RequestMapping(value = "/listarPrecioComposiciones", method = RequestMethod.GET)
	@ResponseBody
	public List<Object[]> listarPrecioComposiciones() {
		
		return  disenioComposicion.findAll();

	}
	
	@RequestMapping(value = "/verifduplicado", method = RequestMethod.GET)
	@ResponseBody
	public boolean verificaduplicado(String Lookup, String Tipo,@RequestParam(required=false) String atributo) {
		boolean resp;
		try {
			resp=catalogo.findDuplicate(Lookup, Tipo, atributo);
		} catch (Exception e) {
			resp=catalogo.findDuplicate(Lookup, Tipo);
		}
		return  resp;
	}
	
	@RequestMapping(value = "/verifduplicadoPrecioComposicion", method = RequestMethod.GET)
	@ResponseBody
	public boolean verifduplicadoPrecioComposicion(@RequestParam(name="idPrenda") Long idPrenda, @RequestParam(name="idFamComposicion") Long idFamPrenda) {
		boolean resp;
		try {
			resp=catalogo.findDuplicatePrecioComposicion(idPrenda, idFamPrenda);
		} catch (Exception e) {
			System.out.println(e);
			resp = false;
		}
		return  resp;

	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_LISTAR"})
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	@ResponseBody
	public List<DisenioLookup> listarlookup(String Tipo) {
		
		return catalogo.findAllLookup(Tipo);
		
	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_LISTAR"})
	@RequestMapping(value = "/listar-material-clasificacion", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> listarlookup2() {
		return catalogo.findAllMaterialClasificacion();
	
	}

	@RequestMapping(value = { "/catalogos" }, method = RequestMethod.GET)
	public String catalogo() {

		return "catalogos";
	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_ELIMINAR"})
	@PostMapping("/bajacatalogo")
	public String bajacatalogo(Long idcatalogo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		DisenioLookup catalogo2 = null;
		catalogo2 = catalogo.findOne(idcatalogo);
		catalogo2.setEstatus(0);
		catalogo2.setUltimaFechaModificacion(dateFormat.format(date));
		catalogo2.setActualizadoPor(auth.getName());
		catalogo.save(catalogo2);
		return "redirect:catalogos";

	}

	@Secured({"ROLE_ADMINISTRADOR"})
	@PostMapping("/reactivarcatalogo")
	@ResponseBody
	public String reactivarcatalogo(Long idcatalogo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		DisenioLookup catalogo2 = null;
		catalogo2 = catalogo.findOne(idcatalogo);
		catalogo2.setEstatus(1);
		catalogo2.setUltimaFechaModificacion(dateFormat.format(date));
		catalogo2.setActualizadoPor(auth.getName());
		catalogo.save(catalogo2);
		return catalogo2.getTipoLookup();

	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_ELIMINAR"})
	@PostMapping("/eliminarcomposicioncuidado")
	@ResponseBody
	public DisenioLookup eliminarcomposicioncuidado(Long id) {
		DisenioComposicionIcuidado composicioncuidado1 = composicioncuidado.findOne(id);
		DisenioLookup composicion = catalogo.findOne(composicioncuidado1.getIdComposicion());
		composicioncuidado.delete(id);
		return composicion;

	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_EDITAR", "ROLE_DISENIO_CATALOGOS_AGREGAR"})
	@PostMapping("/guardarcatalogo")
	public String guardacatalogo(String Descripcion, String Color, String PiezaTrazo, String FamiliaPrenda,
			String FamiliaGenero, String FamiliaComposicion, String InstruccionCuidado, String UnidadMedida,
			String proveedorColor, String Material, HttpServletRequest request, String Marcador, String CodigoColor, 
			String Posicion, @RequestParam(required = false) MultipartFile iconocuidado, Long Idcuidado, String Simbolo,
			String Composicion, String TipoMaterial, String CategoriaMaterial) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();
		if (Color != null) {
			DisenioLookup color = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Color");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				color.setIdText("COL" + "0001");
			} else {
				System.out.println("entra");
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				color.setIdText("COL" + fmt.format("%04d", (cont + 1)));
			}

			color.setNombreLookup(StringUtils.capitalize(Color));
			color.setTipoLookup("Color");
			color.setCreadoPor(auth.getName());
			color.setFechaCreacion(dateFormat.format(date));
			color.setEstatus(1);
			color.setAtributo1(CodigoColor);
			color.setAtributo2(proveedorColor);
			catalogo.save(color);
			return "catalogos";
		}
		if (PiezaTrazo != null) {
			DisenioLookup piezatrazo = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Pieza Trazo");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				piezatrazo.setIdText("PZTR" + "0001");
			} else {
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				piezatrazo.setIdText("PZTR" + fmt.format("%04d", (cont + 1)));
			}

			piezatrazo.setNombreLookup(StringUtils.capitalize(PiezaTrazo));
			piezatrazo.setTipoLookup("Pieza Trazo");
			piezatrazo.setCreadoPor(auth.getName());
			piezatrazo.setFechaCreacion(dateFormat.format(date));
			piezatrazo.setEstatus(1);
			catalogo.save(piezatrazo);
			return "catalogos";
		}
		if (FamiliaPrenda != null) {
			DisenioLookup familiaprenda = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Familia Prenda");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				familiaprenda.setIdText("FAMPR"+"0001");
			} else {
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				familiaprenda.setIdText("FAMPR" + fmt.format("%04d", (cont + 1)));
			}

			familiaprenda.setNombreLookup(StringUtils.capitalize(FamiliaPrenda));
			familiaprenda.setTipoLookup("Familia Prenda");
			familiaprenda.setCreadoPor(auth.getName());
			familiaprenda.setAtributo1(Posicion);
			familiaprenda.setFechaCreacion(dateFormat.format(date));
			familiaprenda.setEstatus(1);
			catalogo.save(familiaprenda);
			return "catalogos";
		}
		if (FamiliaGenero != null) {
			DisenioLookup familiagenero = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Familia Genero");

			} catch (Exception e) {

				System.out.println(e);

			}

			if (ultimoid == null) {
				familiagenero.setIdText("FAMGE" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				familiagenero.setIdText("FAMGE" + fmt.format("%04d", (cont + 1)));
			}

			familiagenero.setNombreLookup(StringUtils.capitalize(FamiliaGenero));
			familiagenero.setTipoLookup("Familia Genero");
			familiagenero.setCreadoPor(auth.getName());
			familiagenero.setFechaCreacion(dateFormat.format(date));
			familiagenero.setEstatus(1);
			catalogo.save(familiagenero);
			return "catalogos";
		}

		if (InstruccionCuidado != null) {
			DisenioLookup instruccioncuidado = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Instruccion Cuidado");

			} catch (Exception e) {

				System.out.println(e);

			}

			if (ultimoid == null) {
				instruccioncuidado.setIdText("INSTRCU" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				instruccioncuidado.setIdText("INSTRCU" + fmt.format("%04d", (cont + 1)));
			}

			instruccioncuidado.setNombreLookup(StringUtils.capitalize(InstruccionCuidado));
			instruccioncuidado.setTipoLookup("Instruccion Cuidado");
			instruccioncuidado.setCreadoPor(auth.getName());
			instruccioncuidado.setFechaCreacion(dateFormat.format(date));
			instruccioncuidado.setEstatus(1);
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copyfile(iconocuidado, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			instruccioncuidado.setAtributo1(uniqueFilename);
			catalogo.save(instruccioncuidado);
			return "catalogos";
		}
		if (UnidadMedida != null) {
			DisenioLookup unidadmedida = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Unidad Medida");

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
			catalogo.save(unidadmedida);
			return "catalogos";
		}
		if (Material != null) {
			DisenioLookup material = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Material");

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
			material.setCreadoPor(auth.getName());
			material.setFechaCreacion(dateFormat.format(date));
			material.setEstatus(1);
			material.setAtributo1(TipoMaterial);
			material.setAtributo2(CategoriaMaterial);
			catalogo.save(material);
			return "catalogos";
		}
		if (Marcador != null) {
			DisenioLookup marcador = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Marcador");

			} catch (Exception e) {

				System.out.println(e);

			}

			if (ultimoid == null) {
				marcador.setIdText("MARC" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				marcador.setIdText("MARC" + fmt.format("%04d", (cont + 1)));
			}

			marcador.setNombreLookup(StringUtils.capitalize(Marcador));
			marcador.setTipoLookup("Marcador");
			marcador.setCreadoPor(auth.getName());
			marcador.setFechaCreacion(dateFormat.format(date));
			marcador.setEstatus(1);
			catalogo.save(marcador);
			return "catalogos";
		}
		if (Composicion != null) {
			DisenioLookup composicion = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Composicion");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				composicion.setIdText("COMP" + "0001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				composicion.setIdText("COMP" + fmt.format("%04d", (cont + 1)));
			}

			composicion.setNombreLookup(StringUtils.capitalize(Composicion));
			composicion.setTipoLookup("Composicion");
			composicion.setCreadoPor(auth.getName());
			composicion.setFechaCreacion(dateFormat.format(date));
			composicion.setEstatus(1);
			catalogo.save(composicion);
			return "catalogos";
		}
		fmt.close();
		return "redirect:catalogos";

	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_LISTAR", "ROLE_DISENIO_CATALOGOS_AGREGAR"})
	@RequestMapping(value= "/agregarPrecioComposicion", method = RequestMethod.POST)
	public String guardarPrecioComposicion (@RequestParam("idPrenda")Long idPrenda, @RequestParam("idFamComposicion")Long idFamComposicion, @RequestParam("precio")String precio) {
		DisenioPrecioComposicion precioComposicion = new DisenioPrecioComposicion();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
		try {
			
			
			precioComposicion.setIdPrenda(idPrenda);
			precioComposicion.setIdFamiliaComposicion(idFamComposicion);
			precioComposicion.setPrecio(precio);
			precioComposicion.setCreadoPor(auth.getName());
			//precioComposicion.setActualizadoPor(auth.getName());
			precioComposicion.setFechaCreacion(dateFormat.format(date));
			//precioComposicion.setUltimaFechaModificacion(dateFormat.format(date));
			precioComposicion.setEstatus("1");
			
			disenioComposicion.save(precioComposicion);
			
			return "catalogos";
		}
		catch(Exception e) {
			System.out.println(e);
			return "redirect:catalogos";
		}
	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_LISTAR", "ROLE_DISENIO_CATALOGOS_EDITAR"})
	@RequestMapping(value= "/editarPrecioComposicion", method = RequestMethod.POST)
	public String editarPrecioComposicion (@RequestParam("idPrenda")Long idPrenda, 
										   @RequestParam("idFamComposicion")Long idFamComposicion, 
										   @RequestParam("precio")String precio,
										   @RequestParam("idPrecioComposicion")Long idPrecioComposicion) {
		
		try {
			DisenioPrecioComposicion precioComposicion = disenioComposicion.findOne(idPrecioComposicion);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			precioComposicion.setIdPrenda(idPrenda);
			precioComposicion.setIdFamiliaComposicion(idFamComposicion);
			precioComposicion.setPrecio(precio);
			precioComposicion.setActualizadoPor(auth.getName());
			precioComposicion.setUltimaFechaModificacion(currentDate());
			
			disenioComposicion.save(precioComposicion);
			
			return "catalogos";
		}
		catch(Exception e) {
			System.out.println(e);
			return "redirect:catalogos";
		}
	}
	
	@Secured({"ROLE_ADMINISTRADOR"})
	@RequestMapping(value= "/reactivarPrecioComposicion", method = RequestMethod.POST)
	public String reactivarPrecioComposicion (@RequestParam("idPrecioComposicion")Long idPrecioComposicion) {
		
		try {
			DisenioPrecioComposicion precioComposicion = disenioComposicion.findOne(idPrecioComposicion);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			precioComposicion.setActualizadoPor(auth.getName());
			precioComposicion.setUltimaFechaModificacion(dateFormat.format(date));
			precioComposicion.setEstatus("1");
			
			disenioComposicion.save(precioComposicion);
			
			return "catalogos";
		}
		catch(Exception e) {
			System.out.println(e);
			return "redirect:catalogos";
		}
	}
	
	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_LISTAR", "ROLE_DISENIO_CATALOGOS_ELIMINAR"})
	@RequestMapping(value= "/bajarPrecioComposicion", method = RequestMethod.POST)
	public String bajarPrecioComposicion (@RequestParam("idPrecioComposicion")Long idPrecioComposicion) {
		
		try {
			DisenioPrecioComposicion precioComposicion = disenioComposicion.findOne(idPrecioComposicion);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			precioComposicion.setActualizadoPor(auth.getName());
			precioComposicion.setUltimaFechaModificacion(dateFormat.format(date));
			precioComposicion.setEstatus("0");
			
			disenioComposicion.save(precioComposicion);
			
			return "catalogos";
		}
		catch(Exception e) {
			System.out.println(e);
			return "redirect:catalogos";
		}
	}
	
	
	@RequestMapping(value = "/composicioncuidadorest", method = RequestMethod.POST)
	@ResponseBody
	public String[] composicioncuidado(Long idcuidado, String FamiliaComposicion, Long idcomposicion) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Formatter fmt = new Formatter();
		String[] result = new String[2];
		if (FamiliaComposicion != null && FamiliaComposicion.length() > 0) {
			DisenioLookup familiacomposicion = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Familia Composicion");

			} catch (Exception e) {

				System.out.println(e);
			}

			if (ultimoid == null) {
				familiacomposicion.setIdText("FAMCOMP" + "1001");
			} else {

				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				familiacomposicion.setIdText("FAMCOMP" + fmt.format("%04d", (cont + 1)));
			}
			familiacomposicion.setNombreLookup(StringUtils.capitalize(FamiliaComposicion));
			familiacomposicion.setTipoLookup("Familia Composicion");
			familiacomposicion.setCreadoPor(auth.getName());
			familiacomposicion.setFechaCreacion(dateFormat.format(date));
			familiacomposicion.setEstatus(1);
			catalogo.save(familiacomposicion);
			DisenioComposicionIcuidado diseniocomposicioncuidado = new DisenioComposicionIcuidado();
			diseniocomposicioncuidado.setIdComposicion(familiacomposicion.getIdLookup());
			diseniocomposicioncuidado.setIdInstruccionesCuidado(idcuidado);
			diseniocomposicioncuidado.setCreadoPor(auth.getName());
			diseniocomposicioncuidado.setActualizadoPor(auth.getName());
			composicioncuidado.save(diseniocomposicioncuidado);
			result[0] = familiacomposicion.getIdLookup().toString();
			result[1] = familiacomposicion.getNombreLookup();
		} else {

			DisenioComposicionIcuidado diseniocomposicioncuidado = new DisenioComposicionIcuidado();
			diseniocomposicioncuidado.setIdComposicion(idcomposicion);
			diseniocomposicioncuidado.setIdInstruccionesCuidado(idcuidado);
			diseniocomposicioncuidado.setActualizadoPor(auth.getName());
			diseniocomposicioncuidado.setCreadoPor(auth.getName());
			composicioncuidado.save(diseniocomposicioncuidado);
			DisenioLookup famcomp = catalogo.findOne(diseniocomposicioncuidado.getIdComposicion());
			result[0] = famcomp.getIdLookup().toString();
			result[1] = famcomp.getNombreLookup();

		}

		return result;
	}

	@RequestMapping(value = "/composicionescuidadosrest", method = RequestMethod.POST)
	@ResponseBody
	public List<Object> composicionescuidados(Long FamiliaComposicion) {
		return composicioncuidado.composicioncuidado(FamiliaComposicion);

	}

	@Secured({"ROLE_ADMINISTRADOR", "ROLE_DISENIO_CATALOGOS_EDITAR"})
	@PostMapping("/editarcatalogo")
	public String editacatalogo(Model model, final Long idLookup, String Color, String PiezaTrazo, String FamiliaPrenda,
			String Descripcion, String FamiliaGenero, String FamiliaComposicion, String InstruccionCuidado,
			String UnidadMedida, String Material, String proveedor, String Marcador, String CodigoColor, String Posicion, String Simbolo,
			String Composicion, String TipoMaterial, String CategoriaMaterial, @RequestParam(required = false) MultipartFile iconocuidado) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DisenioLookup color = null;
		DisenioLookup piezatrazo = null;
		DisenioLookup familiaprenda = null;
		DisenioLookup familiagenero = null;
		DisenioLookup familiacomposicion = null;
		DisenioLookup instruccioncuidado = null;
		DisenioLookup unidadmedida = null;
		DisenioLookup material = null;
		DisenioLookup marcador = null;
		DisenioLookup composicion = null;
		if (Color != null && idLookup > 0) {
			color = catalogo.findOne(idLookup);
			color.setNombreLookup(StringUtils.capitalize(Color));
			color.setAtributo1(CodigoColor);
			color.setAtributo2(proveedor);
			color.setUltimaFechaModificacion(currentDate());
			color.setActualizadoPor(auth.getName());
			catalogo.save(color);
			return "redirect:catalogos";
		}
		if (PiezaTrazo != null && idLookup > 0) {
			piezatrazo = catalogo.findOne(idLookup);
			piezatrazo.setNombreLookup(StringUtils.capitalize(PiezaTrazo));
			piezatrazo.setUltimaFechaModificacion(currentDate());
			piezatrazo.setActualizadoPor(auth.getName());
			catalogo.save(piezatrazo);
			return "redirect:catalogos";
		}
		if (FamiliaPrenda != null && idLookup > 0) {
			familiaprenda = catalogo.findOne(idLookup);
			familiaprenda.setNombreLookup(StringUtils.capitalize(FamiliaPrenda));
			familiaprenda.setUltimaFechaModificacion(currentDate());
			familiaprenda.setAtributo1(Posicion);
			familiaprenda.setActualizadoPor(auth.getName());
			catalogo.save(familiaprenda);
			return "redirect:catalogos";
		}
		if (FamiliaGenero != null && idLookup > 0) {
			familiagenero = catalogo.findOne(idLookup);
			familiagenero.setNombreLookup(StringUtils.capitalize(FamiliaGenero));
			familiagenero.setUltimaFechaModificacion(currentDate());
			familiagenero.setActualizadoPor(auth.getName());
			catalogo.save(familiagenero);
			return "redirect:catalogos";
		}
		if (FamiliaComposicion != null && idLookup > 0) {
			familiacomposicion = catalogo.findOne(idLookup);
			familiacomposicion.setNombreLookup(StringUtils.capitalize(FamiliaComposicion));
			familiacomposicion.setUltimaFechaModificacion(currentDate());
			familiacomposicion.setActualizadoPor(auth.getName());
			catalogo.save(familiacomposicion);
			return "redirect:catalogos";
		}
		if (InstruccionCuidado != null && idLookup > 0) {
			instruccioncuidado = catalogo.findOne(idLookup);
			instruccioncuidado.setNombreLookup(StringUtils.capitalize(InstruccionCuidado));
			instruccioncuidado.setUltimaFechaModificacion(currentDate());
			instruccioncuidado.setActualizadoPor(auth.getName());
			String uniqueFilename = null;
			if(iconocuidado.getOriginalFilename().length()>1) {
				try {
				uniqueFilename = uploadFileService.copyfile(iconocuidado, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			instruccioncuidado.setAtributo1(uniqueFilename);}
			catalogo.save(instruccioncuidado);
			return "redirect:catalogos";
		}
		if (UnidadMedida != null && idLookup > 0) {
			unidadmedida = catalogo.findOne(idLookup);
			unidadmedida.setNombreLookup(StringUtils.capitalize(UnidadMedida));
			unidadmedida.setDescripcionLookup(Simbolo);
			unidadmedida.setUltimaFechaModificacion(currentDate());
			unidadmedida.setActualizadoPor(auth.getName());
			catalogo.save(unidadmedida);
			return "redirect:catalogos";
		}
		if (Material != null && idLookup > 0) {
			material = catalogo.findOne(idLookup);
			material.setNombreLookup(StringUtils.capitalize(Material));
			material.setUltimaFechaModificacion(currentDate());
			material.setActualizadoPor(auth.getName());
			material.setAtributo1(TipoMaterial);
			System.out.println("Esta es la categoria:"+ CategoriaMaterial);
			material.setAtributo2(CategoriaMaterial);
			catalogo.save(material);
			return "redirect:catalogos";
		}
		if (Marcador != null && idLookup > 0) {
			marcador = catalogo.findOne(idLookup);
			marcador.setNombreLookup(StringUtils.capitalize(Marcador));
			marcador.setUltimaFechaModificacion(currentDate());
			marcador.setActualizadoPor(auth.getName());
			catalogo.save(marcador);
			return "redirect:catalogos";
		}
		if (Composicion != null && idLookup > 0) {
			composicion = catalogo.findOne(idLookup);
			composicion.setNombreLookup(StringUtils.capitalize(Composicion));
			composicion.setUltimaFechaModificacion(currentDate());
			composicion.setActualizadoPor(auth.getName());
			catalogo.save(composicion);
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
