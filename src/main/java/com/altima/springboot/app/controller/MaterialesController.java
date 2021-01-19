package com.altima.springboot.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.models.entity.DisenioForro;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
//import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.service.IAmpInventarioProovedorService;
import com.altima.springboot.app.models.service.ICatalogoService;
import com.altima.springboot.app.models.service.IComprasProveedorService;
import com.altima.springboot.app.models.service.IDisenioForroService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
//import com.altima.springboot.app.models.service.IDisenioProcesoService;
import com.altima.springboot.app.models.service.IDisenioTelaService;
import com.altima.springboot.app.models.service.IUploadService;
import com.altima.springboot.app.models.service.IDisenioMaterialTelaService;

@CrossOrigin(origins = { "*" })
@Controller
public class MaterialesController {

	@Autowired
	private IDisenioMaterialService disenioMaterialService;

	@Autowired
	private IDisenioLookupService disenioLookupService;
	
	@Autowired
	private IDisenioForroService forroService;
	@Autowired
	private IDisenioTelaService disenioTelaService;

	@Autowired
	private IUploadService UploadService;

	@Autowired
	private IDisenioMaterialTelaService MaterialService;
	
	@Autowired
	private IAmpInventarioProovedorService ProveedorSerivice;

	@Autowired
	private IComprasProveedorService proveedorService;

	@Autowired
	ICatalogoService catalogo;

	@GetMapping("/materiales")
	public String listMateriales(Model model) {

		List<Object[]> disenioMaterial = disenioMaterialService.disenioMaterial();

		model.addAttribute("listarMateriales", disenioMaterial);

		return "materiales";
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_LISTAR" })
	@GetMapping("detalle-material/{tipo}/{id}")
	public String infoMaterials(@PathVariable("id") Long id, @PathVariable("tipo") String tipo, Model model) {
		if (tipo.equals("material")) {
			model.addAttribute("tipo", "material");
			model.addAttribute("h3", "Consulta de Material");
			DisenioMaterial dm;
			dm = disenioMaterialService.findOne(id);
			model.addAttribute("h2", dm.getNombreMaterial());
			model.addAttribute("material", disenioMaterialService.findByIdMaterial(id));
			model.addAttribute("proveedor", ProveedorSerivice.Proveedores());
			

		} else if (tipo.equals("tela")) {
			model.addAttribute("listVistaTelaPrenda", disenioTelaService.VistaTelaPrenda(id));
			model.addAttribute("h3", "Consulta de Tela");
			model.addAttribute("tela", disenioTelaService.findPrendaById(id));
			model.addAttribute("tablemat", MaterialService.findAllById(id));
			model.addAttribute("listTelaComposicon", disenioTelaService.ComposicionTelaMN(id));
			model.addAttribute("tipo", "tela");
			model.addAttribute("listForroSelec", disenioTelaService.ForrosSeleccionados(id));
			model.addAttribute("EstatusCalidadTela", disenioTelaService.EstatusCalidadTela(id));
			model.addAttribute("listPrendas", disenioTelaService.findAllPrenda());
			model.addAttribute("proveedor", ProveedorSerivice.Proveedores());
		} else if (tipo.equals("forro")) {
			model.addAttribute("h3", "Consulta de Forro");
			DisenioForro forro;
			forro = forroService.findOne(id);
			model.addAttribute("forro", forro);
			model.addAttribute("listForroComposicon", disenioTelaService.ComposicionForroMN(id));
			model.addAttribute("h2", forro.getNombreForro());
			model.addAttribute("ForroUnidadMedida", forroService.ForroUnidadMedida(id));
			model.addAttribute("EstatusCalidadForro", forroService.EstatusCalidadForro(id));
			model.addAttribute("tipo", "forro");
			model.addAttribute("proveedor", ProveedorSerivice.Proveedores());

		} else {
			return "redirect:/materiales";
		}
		return "detalle-material";
	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_AGREGAR" })
	@GetMapping("/agregar-material")
	public String agregarMaterial(Model model) {
		model.addAttribute("form", "default");
		// DisenioMaterial material = new DisenioMaterial();
		// List<DisenioLookup> listLookupsMed =
		// disenioMaterialService.findListaLookupMed();
		// List<DisenioLookup> listLookupsMar =
		// disenioMaterialService.findListaMarcas();
		// List<DisenioLookup> listLookupsClasificacion =
		// disenioMaterialService.findListaClasificacion();
		// List<DisenioLookup> listClaveProceso =
		// disenioMaterialService.findListaLookupPro();
		// List<DisenioLookup> listLookupsMat =
		// disenioMaterialService.findListaLookupMat();
		// List<DisenioLookup> listLookupsCol = disenioMaterialService.findListaColor();
		// model.addAttribute("material", material);
		// model.addAttribute("listLookupsMed", listLookupsMed);
		// model.addAttribute("listLookupsMar", listLookupsMar);
		// model.addAttribute("listLookupsClasificacion", listLookupsClasificacion);
		// model.addAttribute("listClaveProceso", listClaveProceso);
		// model.addAttribute("listLookupsMat", listLookupsMat);
		// model.addAttribute("listLookupsCol", listLookupsCol);

		// // Comienza erik
		// DisenioForro forro = new DisenioForro();
		// model.addAttribute("forro", forro);
		// model.addAttribute("lisFam", disenioTelaService.findAllFamilaComposicion());

		// model.addAttribute("lisCom", disenioTelaService.findAllComposicion());

		// // agregar tela
		// DisenioTela tela = new DisenioTela();
		// model.addAttribute("listForro", forroService.findAll());
		// model.addAttribute("listBoton",
		// disenioTelaService.findAllBotones(tela.getIdTela()));
		// model.addAttribute("listColor", disenioTelaService.findAllColores());
		// model.addAttribute("listPrendas", disenioTelaService.findAllPrenda());
		// model.addAttribute("tela", tela);
		return "agregar-material";

	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_AGREGAR" })
	@GetMapping("/agregar-material/{tipo}")
	public String agregarMaterial(Model model, @PathVariable(name = "tipo") String tipo) {

		if (tipo.equals("material")) {

			DisenioMaterial material = new DisenioMaterial();
			List<DisenioLookup> listLookupsMed = disenioMaterialService.findListaLookupMed();
			List<DisenioLookup> listLookupsMar = disenioMaterialService.findListaMarcas();
			List<DisenioLookup> listLookupsClasificacion = disenioMaterialService.findListaClasificacion();
			List<DisenioLookup> listClaveProceso = disenioMaterialService.findListaLookupPro();
			List<DisenioLookup> listLookupsMat = disenioMaterialService.findListaLookupMat();
			List<DisenioLookup> listLookupsCol = disenioMaterialService.findListaColor();
			model.addAttribute("material", material);
			model.addAttribute("listLookupsMed", listLookupsMed);
			model.addAttribute("listLookupsMar", listLookupsMar);
			model.addAttribute("listLookupsClasificacion", listLookupsClasificacion);
			model.addAttribute("listClaveProceso", listClaveProceso);
			model.addAttribute("listLookupsMat", listLookupsMat);
			model.addAttribute("listLookupsCol", listLookupsCol);
			model.addAttribute("form", "material");
			model.addAttribute("proveedor", ProveedorSerivice.Proveedores());
		} else if (tipo.equals("forro")) {

			DisenioForro forro = new DisenioForro();
			model.addAttribute("forro", forro);
			model.addAttribute("lisFam", disenioTelaService.findAllFamilaComposicion());
			model.addAttribute("lisCom", disenioTelaService.findAllComposicion());
			model.addAttribute("form", "forro");
			model.addAttribute("proveedor", ProveedorSerivice.Proveedores());
		} else if (tipo.equals("tela")) {

			DisenioTela tela = new DisenioTela();
			model.addAttribute("listForro", forroService.findAll());

			model.addAttribute("listPrendas", disenioTelaService.findAllPrenda());
			model.addAttribute("lisFam", disenioTelaService.findAllFamilaComposicion());
			model.addAttribute("lisCom", disenioTelaService.findAllComposicion());
			model.addAttribute("tela", tela);
			model.addAttribute("form", "tela");
			model.addAttribute("proveedor", ProveedorSerivice.Proveedores());
		} else {
			return ("redirect:materiales");
		}

		
		
		
		return "agregar-material";

	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_AGREGAR", "ROLE_DISENIO_MATERIALES_EDITAR" })
	@PostMapping("/guardar")
	public String guardarMaterial(@ModelAttribute DisenioMaterial material, RedirectAttributes redirectAttrs,
			@RequestParam(value = "imagenMaterial", required = false) MultipartFile imagenMaterial)
			throws IllegalStateException, IOException {
		Cloudinary cloudinary = UploadService.CloudinaryApi();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Formatter fmt = new Formatter();
		String compara = material.getNombreMaterial();
		System.out.println("aqui esta la palabra que se va a comparar" + compara);
		String flag = disenioMaterialService.Exist2(compara);
		DisenioLookup lookup = disenioLookupService.findOne(material.getIdTipoMaterial());
		System.out.println(lookup.getIdLookup());
		Long compara2 = material.getIdMaterial();

		String flag2 = disenioMaterialService.Exist3(compara2);

		System.out.println("flag1//" + flag);
		System.out.println("flag2//" + flag2);

		String definitiva;

		if (flag.equals("1") && flag2.equals("1")) {

			definitiva = "si entra";
			System.out.println("primer if");
			System.out.println("var" + definitiva);

		} else {
			if (flag.equals("0") && flag2.equals("1")) {

				definitiva = "si entra";
				System.out.println("segundo if");
				System.out.println("var" + definitiva);

			} else {

				if (flag.equals("0") && flag2.equals("0")) {
					definitiva = "si entra";
					System.out.println("tercer if");
					System.out.println("var" + definitiva);

				} else {

					definitiva = "no entra";

					System.out.println("else");
					System.out.println("var" + definitiva);

				}

			}

		}

		if (definitiva == "si entra") {

			if (material.getIdMaterial() == null || material.getIdMaterial() <= 0) {// comienza if para insertar un
																					// registro nuevo

				System.out.println("la calidad guardar1" + material.getCalidad());
				if (material.getCalidad() == null) {
					material.setCalidad("0");

				}
				System.out.println(auth.getName() + "aqui esta el mero mero 1 ");
				material.setCreadoPor(auth.getName());
				material.setEstatusMaterial("0");
				material.setEstatus("1");

				if (!imagenMaterial.isEmpty()) {// imagen
					if (material.getImagen() != null && material.getImagen().length() > 0) {
						UploadService.deleteMaterial(material.getImagen());
						cloudinary.uploader().destroy("material/"+material.getImagen().substring(0,material.getImagen().length()-4), ObjectUtils.emptyMap());
					}
					String uniqueFilename = null;
					try {
						uniqueFilename = UploadService.copyMaterial(imagenMaterial);
						cloudinary.uploader().upload(UploadService.fileMaterial(uniqueFilename), ObjectUtils.asMap("public_id", "material/"+uniqueFilename.substring(0,uniqueFilename.length()-4)));
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					material.setImagen(uniqueFilename);
				} // imagen

				try {
					disenioMaterialService.save(material);
				} catch (DataIntegrityViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println(auth.getName() + "entro al if ");

				//System.out.println("vamos que pedo" + material.getDescripcionLookup());
				//String prefijo = disenioMaterialService.findunique(lookup.getDescripcionLookup());
				//String prefijo = lookup.getDescripcionLookup();
				//System.out.println("aqui esta el result de la query pref " + unique);
				//String prefijo = unique.substring(1, 4);
				//System.out.println("aqui esta el prefijo" + prefijo);
				int contador = disenioMaterialService.count(material.getIdTipoMaterial());
				System.out.println("aqui esta el contador de la query" + contador);
				material.setIdTextProspecto("PROSP" + lookup.getDescripcionLookup().toUpperCase() + fmt.format("%05d", (contador + 1)));
				material.setIdText("00");
				material.setEstatus("1");
				fmt.close();
				try {
					disenioMaterialService.save(material);
				} catch (DataIntegrityViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("epale eaple si entro al method posts");
				redirectAttrs.addFlashAttribute("title", "Material Insertado Correctamente").addFlashAttribute("icon",
						"success");
			} else { // comienza else para editar

				if (material.getCalidad() == null) {
					material.setCalidad("0");

				}

				if (!imagenMaterial.isEmpty()) {
					if (material.getImagen() != null && material.getImagen().length() > 0) {
						UploadService.deleteMaterial(material.getImagen());
						cloudinary.uploader().destroy("material/"+material.getImagen().substring(0,material.getImagen().length()-4), ObjectUtils.emptyMap());
					}
					String uniqueFilename = null;
					try {

						uniqueFilename = UploadService.copyMaterial(imagenMaterial);
						cloudinary.uploader().upload(UploadService.fileMaterial(uniqueFilename), ObjectUtils.asMap("public_id", "material/"+uniqueFilename.substring(0,uniqueFilename.length()-4)));
						System.out.println("api key"+cloudinary.toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					material.setImagen(uniqueFilename);
				}

				System.out.println(auth.getName() + "aqui esta el mero mero la editada");
				material.setActualizadoPor(auth.getName());

				System.out.println("epale eaple si entro al method posts");

				try {

					disenioMaterialService.save(material);
					System.out.println("epale eaple si entro al method posts de editar");
					redirectAttrs.addFlashAttribute("title", "Material Actualizado Correctamente")
							.addFlashAttribute("icon", "success");

				} catch (DataIntegrityViolationException e) {
					// TODO Auto-generated catch block

					redirectAttrs.addFlashAttribute("title", "Material ya existente").addFlashAttribute("icon",
							"warning");

				}

			}

		} else {

			redirectAttrs.addFlashAttribute("title", "Material ya existente").addFlashAttribute("icon", "warning");

		}

		return "redirect:materiales";

	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_EDITAR" })
	@GetMapping("/editar-material/{id}")
	public String editarMaterial(@PathVariable("id") Long idMaterial, Model model) {
		model.addAttribute("form", "material");
		DisenioMaterial material = disenioMaterialService.findOne(idMaterial);
		model.addAttribute("editar", "true");
		List<DisenioLookup> listLookupsMed = disenioMaterialService.findListaLookupMed();
		List<DisenioLookup> listLookupsMar = disenioMaterialService.findListaMarcas();
		List<DisenioLookup> listLookupsClasificacion = disenioMaterialService.findListaClasificacion();
		List<DisenioLookup> listClaveProceso = disenioMaterialService.findListaLookupPro();
		List<DisenioLookup> listLookupsMat = disenioMaterialService.findListaLookupMat();
		List<DisenioLookup> listLookupsCol = disenioMaterialService.findListaColor();

		model.addAttribute("material", material);

		model.addAttribute("listLookupsMed", listLookupsMed);
		model.addAttribute("listLookupsMar", listLookupsMar);
		model.addAttribute("listLookupsClasificacion", listLookupsClasificacion);
		model.addAttribute("listClaveProceso", listClaveProceso);
		model.addAttribute("listLookupsMat", listLookupsMat);
		model.addAttribute("listLookupsCol", listLookupsCol);
		model.addAttribute("proveedor", ProveedorSerivice.Proveedores());
		return "agregar-material";

	}

	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_AGREGAR", "ROLE_DISENIO_MATERIALES_EDITAR" })
	@GetMapping(value = "/uploads/material/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = UploadService.loadMaterial(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	// Metodo para aceptar un material
	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_ACEPTAR_DECLINAR" })
	@GetMapping("/aceptado-material/{id}")
	public String aceptadoMaterial(@PathVariable("id") Long idMaterial, RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Formatter fmt = new Formatter();
		DisenioMaterial material = disenioMaterialService.findOne(idMaterial);
		//DisenioLookup lookup = disenioLookupService.findOne(descripcionLookup);
		DisenioLookup lookup = disenioLookupService.findOne(material.getIdTipoMaterial());
		//String prefijo= disenioMaterialService.findunique(lookup.getDescripcionLookup());
		//String prefijo = unique.substring(1, 4);
		int contador2 = disenioMaterialService.count2(material.getIdTipoMaterial());
		material.setIdText(lookup.getDescripcionLookup().toUpperCase() + fmt.format("%05d", (contador2 + 1)));
		material.setEstatusMaterial("1");
		material.setUltimaFechaModificacion(currentDate());
		material.setActualizadoPor(auth.getName());
		disenioMaterialService.save(material);
		fmt.close();
		redirectAttrs.addFlashAttribute("title", "Material aceptado correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/materiales";
	}

	// Metodo para declinar un material
	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_ACEPTAR_DECLINAR" })
	@GetMapping("/declinado-material/{id}")
	public String declinadoMaterial(@PathVariable("id") Long idMaterial, RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DisenioMaterial material = disenioMaterialService.findOne(idMaterial);
		material.setEstatusMaterial("2");
		material.setIdText("");
		material.setUltimaFechaModificacion(currentDate());
		material.setActualizadoPor(auth.getName());
		disenioMaterialService.save(material);
		redirectAttrs.addFlashAttribute("title", "Material declinado correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/materiales";
	}

	// Metodo para aceptar una tela
	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_ACEPTAR_DECLINAR" })
	@GetMapping("/aceptado-tela/{id}")
	public String aceptadoTela(@PathVariable("id") Long idTela, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		Formatter fmt = new Formatter();
		Formatter fmt2 = new Formatter();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DisenioTela dt = disenioTelaService.findOne(idTela);
		ComprasProveedores provee = proveedorService.findOne(dt.getIdProveedor());
		dt.setEstatusTela("1");
		dt.setActualizadoPor(auth.getName());
		dt.setUltimaFechaModificacion(hourdateFormat.format(date));
		System.out.println(dt.getIdText());
		if (dt.getIdText() == null || dt.getIdText() == " " || dt.getIdText().equalsIgnoreCase("")
				|| dt.getIdText().equalsIgnoreCase(" ")) {

			dt.setIdText(provee.getNomenclatura()+fmt.format("%05d",disenioMaterialService.countTelasActivas() + 1));
			fmt.close();
			DisenioLookup color = new DisenioLookup();
			DisenioLookup ultimoid = null;
			try {
				ultimoid = catalogo.findLastLookupByType("Color");
System.out.println("asi nomas quedo");
			} catch (Exception e) {

				System.err.println(e);
			}

			if (ultimoid == null) {
				color.setIdText("COL" + "0001");
			} else {
				String str = ultimoid.getIdText();
				String[] part = str.split("(?<=\\D)(?=\\d)");
				Integer cont = Integer.parseInt(part[1]);
				color.setIdText("COL" + fmt2.format("%04d", (cont + 1)));
				fmt2.close();
			}

			color.setNombreLookup(dt.getIdText());
			color.setTipoLookup("Color");
			color.setCreadoPor(auth.getName());
			color.setEstatus(1);
			color.setAtributo1(dt.getCodigoColor());
			color.setAtributo2(dt.getIdProveedor().toString());
			catalogo.save(color);			
		}
		disenioTelaService.save(dt);
		redirectAttrs.addFlashAttribute("title", "Material aceptado correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/materiales";
	}

	// Metodo para declinar una tela
	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_ACEPTAR_DECLINAR" })
	@GetMapping("/declinado-tela/{id}")
	public String declinadoTela(@PathVariable("id") Long idTela, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DisenioTela dt = disenioTelaService.findOne(idTela);
		dt.setEstatusTela("2");
		dt.setIdText("");
		dt.setActualizadoPor(auth.getName());
		dt.setUltimaFechaModificacion(hourdateFormat.format(date));
		disenioTelaService.save(dt);
		redirectAttrs.addFlashAttribute("title", "Material declinado correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/materiales";
	}

	// Metodo para aceptar un forro
	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_ACEPTAR_DECLINAR" })
	@GetMapping("/aceptado-forro/{id}")
	public String aceptadoForro(@PathVariable("id") Long idForro, RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		Formatter fmt = new Formatter();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DisenioForro df = forroService.findOne(idForro);
		df.setEstatusForro("1");
		df.setActualizadoPor(auth.getName());
		df.setUltimaFechaModificacion(hourdateFormat.format(date));
		if (df.getIdText() == null || df.getIdText() == " " || df.getIdText().equalsIgnoreCase("")
				|| df.getIdText().equalsIgnoreCase(" ")) {
			df.setIdText("FORRO" + fmt.format("%05d",(disenioMaterialService.countForrosActivos()) + 1));
			fmt.close();
		}
		forroService.save(df);
		redirectAttrs.addFlashAttribute("title", "Material aceptado correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/materiales";
	}

	// Metodo para declinar un forro
	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_ACEPTAR_DECLINAR" })
	@GetMapping("/declinado-forro/{id}")
	public String declinadoForro(@PathVariable("id") Long idForro, RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DisenioForro df = forroService.findOne(idForro);
		df.setIdText("");
		df.setEstatusForro("2");
		df.setActualizadoPor(auth.getName());
		df.setUltimaFechaModificacion(hourdateFormat.format(date));
		forroService.save(df);
		redirectAttrs.addFlashAttribute("title", "Material declinado correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/materiales";
	}

	// Metodo para dar de alta un material
	@Secured({ "ROLE_ADMINISTRADOR", "ROLE_DISENIO_MATERIALES_ELIMINAR" })
	@GetMapping("/dar-alta-material/{id}")
	public String darAltaMaterial(@PathVariable("id") Long idMaterial, RedirectAttributes redirectAttrs) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		DisenioMaterial material = disenioMaterialService.findOne(idMaterial);
		material.setEstatus("1");
		material.setActualizadoPor(auth.getName());
		material.setUltimaFechaModificacion(hourdateFormat.format(date));
		disenioMaterialService.save(material);
		redirectAttrs.addFlashAttribute("title", "Material dado de alta correctamente").addFlashAttribute("icon",
				"success");
		return "redirect:/materiales";
	}

	// Metodo para dar de baja un material
	
	@GetMapping("/delete-material/{id}")
	public String deleteMaterial(@PathVariable("id") Long idMaterial, RedirectAttributes redirectAttrs) {
		if (disenioMaterialService.disponibles(idMaterial) ==0 ){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
			DisenioMaterial material = disenioMaterialService.findOne(idMaterial);
			material.setEstatus("0");
			material.setActualizadoPor(auth.getName());
			material.setUltimaFechaModificacion(hourdateFormat.format(date));
			disenioMaterialService.save(material);
			redirectAttrs.addFlashAttribute("title", "Material dado de baja correctamente").addFlashAttribute("icon",
					"success");
		
		}else{
			redirectAttrs
			.addFlashAttribute("title", "No es posible eliminar, cuenta con existencias.")
			.addFlashAttribute("icon", "error");
			  
		}
		return "redirect:/materiales";
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
