package com.altima.springboot.app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.bytebuddy.implementation.bytecode.Throw;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioMaterialExtraPrenda;
import com.altima.springboot.app.models.entity.DisenioMaterialPrenda;
import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.entity.DisenioPrendaCliente;
import com.altima.springboot.app.models.entity.DisenioPrendaMarcador;
import com.altima.springboot.app.models.entity.DisenioPrendaPatronaje;
import com.altima.springboot.app.models.entity.DisenioPrendaImagen;
import com.altima.springboot.app.models.service.DisenioImagenPrendaServiceImpl;
import com.altima.springboot.app.models.service.DisenioMaterialPrendaServiceImpl;
import com.altima.springboot.app.models.service.DisenioPrendaPatronajeServiceImpl;
import com.altima.springboot.app.models.service.DisenioPrendaServiceImpl;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialImagenInventarioService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioMaterialExtraPrendaService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
import com.altima.springboot.app.models.service.IDisenioPrendaClienteService;
import com.altima.springboot.app.models.service.IDisenioPrendaMarcadorService;
import com.altima.springboot.app.models.service.IInventarioService;
import com.altima.springboot.app.models.service.IProduccionDetalleService;
import com.altima.springboot.app.models.service.UploadServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@RestController
public class AgregarPrendaRestController {
	@Autowired
	IDisenioMaterialService disenioMaterialService;
	@Autowired
	IDisenioMaterialExtraPrendaService materialExtraService;
	@Autowired
	private UploadServiceImpl uService;
	@Autowired
	private DisenioPrendaServiceImpl prendaService;
	@Autowired
	private IDisenioLookupService disenioPrendaService;
	@Autowired
	private DisenioMaterialPrendaServiceImpl materialPrendaService;
	@Autowired
	private DisenioPrendaPatronajeServiceImpl prendaPatronajeService;
	@Autowired
	private DisenioImagenPrendaServiceImpl prendaImagenService;
	@Autowired
	private IInventarioService inventario;
	@Autowired
	private IDisenioPrendaMarcadorService disenioPrendaMarcadorService;
	@Autowired
	private IDisenioPrendaClienteService disenioPrendaClienteService;
	@Autowired
	IDisenioLookupService disenioLookupService;
	@Autowired
	IComercialClienteService clienteService;
	@Autowired
	IProduccionDetalleService serviceDetallePedido;
	@Autowired
	IComercialImagenInventarioService serviceInvetarioImg;

	public String file1;

	public String file2;

	@RequestMapping(value = "/detalle_material", method = RequestMethod.GET)
	public Object detalleMaterial(@RequestParam Long id) {
		Object dm = disenioMaterialService.findUno(id);

		return dm;
	}

	@RequestMapping(value = "/detalle_patronaje", method = RequestMethod.GET)
	public Object detallePatronaje(@RequestParam Long id) {
		Object dl = disenioMaterialService.findLookUp(id);
		return dl;
	}

	@RequestMapping(value = "/validacion_descripcion_prenda", method = RequestMethod.GET)
	public boolean validarDescripcionPrenda(@RequestParam(name = "valor") String desc) {
		return prendaService.validarDescripcionPrenda(desc);
	}

	@RequestMapping(value = "/confirmar_prenda", method = RequestMethod.GET)
	public Object confirmarPrenda(@RequestParam Long id) {
		Formatter fmt = new Formatter();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		DisenioPrenda diamantePerla = prendaService.findOne(id);
		DisenioLookup lookup = disenioPrendaService.findOne(diamantePerla.getIdFamiliaPrenda());

		int res2 = prendaService.count2(diamantePerla.getIdFamiliaPrenda());
		System.out.println(res2);
		System.out.println(lookup.getDescripcionLookup());
		diamantePerla.setIdText(lookup.getDescripcionLookup().toUpperCase() + fmt.format("%05d", (res2 + 1)));
		diamantePerla.setUltimaFechaModificacion(dtf.format(now));
		diamantePerla.setActualizadoPor(auth.getName());
		diamantePerla.setEstatusRecepcionMuestra("Definitivo");
		prendaService.save(diamantePerla);
		fmt.close();
		return diamantePerla;
	}

	@RequestMapping(value = "/alta_baja_prenda", method = RequestMethod.GET)
	public Object AltaBajaPrenda(@RequestParam Long id) {
		DisenioPrenda diamantePerla = prendaService.findOne(id);
		if (diamantePerla.getEstatus() == 0) {
			diamantePerla.setEstatus(1L);
		} else {
			diamantePerla.setEstatus(0L);
		}

		prendaService.save(diamantePerla);
		return diamantePerla;
	}

	@RequestMapping(value = "/guardar_prenda", method = RequestMethod.POST)
	public String guardarPrenda(@ModelAttribute DisenioPrenda prendas,
			@RequestParam(name = "disenioprenda") String disenioprenda) {
		// Coso del auth
		Formatter fmt = new Formatter();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONObject prenda = new JSONObject(disenioprenda.toString());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		DisenioPrenda dp = new DisenioPrenda();
		dp.setIdFamiliaPrenda(Long.valueOf((String) prenda.get("idFamiliaPrenda")));
		DisenioLookup lookup = disenioPrendaService.findOne(dp.getIdFamiliaPrenda());
		dp.setCreadoPor(auth.getName());
		dp.setActualizadoPor(auth.getName());
		dp.setFechaCreacion(dtf.format(now));
		dp.setUltimaFechaModificacion(dtf.format(now));
		dp.setNumeroPrenda(prenda.get("numeroPrenda").toString());
		dp.setDescripcionPrenda(prenda.get("descripcionPrenda").toString());
		dp.setDetallePrenda(prenda.get("detallePrenda").toString());
		dp.setNotaEspecial(prenda.get("notaEspecial").toString());
		dp.setDetalleConfeccion(prenda.get("detalleConfeccion").toString());
		dp.setConsumoTela(prenda.get("consumoTela").toString());
		dp.setConsumoForro(prenda.get("consumoForro").toString());
		dp.setTipoLargo(prenda.get("tipoLargo").toString());
		dp.setEstatusRecepcionMuestra("Definitivo");
		dp.setDevolucion(prenda.get("devolucion").toString());
		dp.setCategoria(prenda.get("categoria").toString());
		dp.setEstatus(1L);
		dp.setPrendaLocal("1");
		dp.setIdGenero(prenda.get("generoPrenda").toString());
		prendaService.save(dp);

		// Ides
		// Long envio = Long.valueOf(prenda.get("tipoPrenda").toString());
		int res = prendaService.count(dp.getIdFamiliaPrenda());
		System.out.println(res);
		System.out.println(lookup.getDescripcionLookup());
		// String[] res = prendaService.getExistencias(envio);
		// String aux1 = String.format("%05d", Integer.valueOf(res[0]));
		// dp.setIdText(res[1].toUpperCase().substring(0, 3) + aux1);
		dp.setIdTextProspecto("PROSP" + lookup.getDescripcionLookup().toUpperCase() + fmt.format("%05d", (res + 1)));
		dp.setEstatusRecepcionMuestra("Prospecto");
		// dp.setEstatus("Definitivo");
		prendaService.save(dp);

		return dp.getIdPrenda().toString();
	}

	@RequestMapping(value = "/editar_prenda", method = RequestMethod.POST)
	public String editarPrenda(@RequestParam(name = "disenioprenda") String disenioprenda) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONObject prenda = new JSONObject(disenioprenda.toString());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		DisenioPrenda dp = prendaService.findOne(Long.valueOf(prenda.get("idPrenda").toString()));
		dp.setIdFamiliaPrenda(Long.valueOf((String) prenda.get("idFamiliaPrenda")));
		dp.setActualizadoPor(auth.getName());
		dp.setUltimaFechaModificacion(dtf.format(now));
		dp.setNumeroPrenda(prenda.get("numeroPrenda").toString());
		dp.setDescripcionPrenda(prenda.get("descripcionPrenda").toString());
		dp.setDetallePrenda(prenda.get("detallePrenda").toString());
		dp.setNotaEspecial(prenda.get("notaEspecial").toString());
		dp.setDetalleConfeccion(prenda.get("detalleConfeccion").toString());
		// dp.setMarcadores(prenda.get("marcadores").toString());
		dp.setConsumoTela(prenda.get("consumoTela").toString());
		dp.setConsumoForro(prenda.get("consumoForro").toString());
		dp.setTipoLargo(prenda.get("tipoLargo").toString());
		// dp.setEstatusRecepcionMuestra("Definitivo");
		dp.setDevolucion(prenda.get("devolucion").toString());
		dp.setCategoria(prenda.get("categoria").toString());
		dp.setIdGenero(prenda.get("generoPrenda").toString());
		prendaService.save(dp);

		return dp.getIdPrenda().toString();
	}

	@RequestMapping(value = "/copiar_prenda", method = RequestMethod.POST)
	public String copiarPrenda(@RequestParam(name = "disenioprenda") String disenioprenda) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JSONObject prenda = new JSONObject(disenioprenda.toString());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		DisenioPrenda dp = new DisenioPrenda();
		dp.setIdFamiliaPrenda(Long.valueOf((String) prenda.get("idFamiliaPrenda")));
		dp.setCreadoPor(auth.getName());
		dp.setActualizadoPor(auth.getName());
		dp.setFechaCreacion(dtf.format(now));
		dp.setUltimaFechaModificacion(dtf.format(now));
		dp.setNumeroPrenda(prenda.get("numeroPrenda").toString());
		dp.setDescripcionPrenda(prenda.get("descripcionPrenda").toString());
		dp.setDetallePrenda(prenda.get("detallePrenda").toString());
		dp.setNotaEspecial(prenda.get("notaEspecial").toString());
		dp.setDetalleConfeccion(prenda.get("detalleConfeccion").toString());
		dp.setConsumoTela(prenda.get("consumoTela").toString());
		dp.setConsumoForro(prenda.get("consumoForro").toString());
		dp.setTipoLargo(prenda.get("tipoLargo").toString());
		dp.setEstatusRecepcionMuestra("Definitivo");
		dp.setDevolucion(prenda.get("devolucion").toString());
		dp.setCategoria(prenda.get("categoria").toString());
		dp.setEstatus(1L);
		dp.setPrendaLocal("1");
		dp.setIdGenero(prenda.get("generoPrenda").toString());

		prendaService.save(dp);

		// Ides
		Long envio = Long.valueOf(prenda.get("tipoPrenda").toString());
		String[] res = prendaService.getExistencias(envio);
		dp.setIdText(res[1].toUpperCase().substring(0, 3) + (10000 + (Long.valueOf(res[0]))));
		dp.setIdTextProspecto("PROSP" + res[1].toUpperCase().substring(0, 3) + (10000 + (Long.valueOf(res[0]))));
		dp.setEstatusRecepcionMuestra("Prospecto");
		prendaService.save(dp);
		return dp.getIdPrenda().toString();
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/guardar_final", method = RequestMethod.POST)
	public void guardarFinal(@RequestParam(name = "objeto_materiales") String objeto_materiales,
			@RequestParam(name = "objeto_marcadores") String objeto_marcadores,
			@RequestParam(name = "objeto_patronajes") String objeto_patronaje,
			@RequestParam(name = "clientes") String clientes, @RequestParam(name = "accion") String accion,
			@RequestParam(name = "idPrenda") Long idPrenda) throws NoSuchFieldException, SecurityException {

		// Coso del auth
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		// Lo de marcadores
		if (!objeto_marcadores.equals(null) || objeto_marcadores.equals("")) {
			Long IdMarcadores[] = new Long[objeto_marcadores.split(",").length];
			int contadorMarcadores = 0;
			for (String marcador_split : objeto_marcadores.split(",")) {
				DisenioPrendaMarcador dpm = new DisenioPrendaMarcador();
				int num1 = Integer.parseInt(marcador_split.replaceAll("^\"|\"$", ""));
				Long num = new Long(num1);
				dpm.setIdMarcador(num);
				dpm.setIdPrenda(idPrenda);
				dpm.setActualizadoPor(auth.getName());
				dpm.setCreadoPor(auth.getName());
				dpm.setFechaCreacion(dtf.format(now));
				dpm.setUltimaFechaModificacion(dtf.format(now));
				disenioPrendaMarcadorService.save(dpm);
				IdMarcadores[contadorMarcadores] = num;
				contadorMarcadores++;
			}

			// Se eliminan los registros que se borraron del html
			disenioPrendaMarcadorService.delete(IdMarcadores, Long.valueOf(idPrenda));
		}

		// Se guardan Muchos a Muchos de Materiales
		JSONArray materiales = new JSONArray(objeto_materiales);

		Long IdMateriales[] = new Long[materiales.length()];
		int contadorMateriales = 0;
		for (int i = 0; i < materiales.length(); i++) {
			JSONObject material = materiales.getJSONObject(i);
			DisenioMaterialPrenda mdp = new DisenioMaterialPrenda();
			mdp.setIdMaterial(Long.valueOf(material.get("id").toString()));
			mdp.setIdPrenda(Long.valueOf(idPrenda));
			mdp.setCreadoPor(auth.getName());
			mdp.setActualizadoPor(auth.getName());
			mdp.setCantidad(material.get("cantidad").toString());
			mdp.setCantidadRepuesto(material.get("cantidadRepuesto").toString());
			mdp.setFechaCreacion(dtf.format(now));
			mdp.setUltimaFechaModificacion(dtf.format(now));
			materialPrendaService.save(mdp);
			IdMateriales[contadorMateriales] = Long.valueOf(material.get("id").toString());
			contadorMateriales++;
		}

		// Ahora se eliminan los registros que se borraron desde el html.
		materialPrendaService.delete(IdMateriales, Long.valueOf(idPrenda));

		// Se guardan Muchos a Muchos de Patronaje
		JSONArray patronajes = new JSONArray(objeto_patronaje);

		Long IdPatronajes[] = new Long[patronajes.length()];
		int contadorPatronajes = 0;
		for (int i = 0; i < patronajes.length(); i++) {
			JSONObject patronaje = patronajes.getJSONObject(i);
			DisenioPrendaPatronaje dpp = new DisenioPrendaPatronaje();
			dpp.setIdPrenda(Long.valueOf(idPrenda));
			dpp.setIdPatronaje(patronaje.get("id").toString());
			dpp.setCantidadTela(patronaje.get("cantidadTela").toString());
			dpp.setCantidadTelaSecundaria(patronaje.get("cantidadTelaSecundaria").toString());
			dpp.setCantidadForro(patronaje.get("cantidadForro").toString());
			dpp.setCantidadForroSecundaria(patronaje.get("cantidadForroSecundario").toString());
			dpp.setCantidadEntretela(patronaje.get("cantidadEntretela").toString());
			dpp.setActualizadoPor(auth.getName());
			dpp.setCreadoPor(auth.getName());
			dpp.setFechaCreacion(dtf.format(now));
			dpp.setUltimaFechaModificacion(dtf.format(now));

			prendaPatronajeService.save(dpp);
			IdPatronajes[contadorPatronajes] = Long.valueOf(patronaje.get("id").toString());
			contadorPatronajes++;
		}

		// Se eliminan los registros que se eliminaron desde el html
		prendaPatronajeService.delete(IdPatronajes, Long.valueOf(idPrenda));

		// Lo de clientes
		Long IdClientes[] = new Long[clientes.split(",").length];
		int i = 0;
		for (String cliente_split : clientes.split(",")) {
			DisenioPrendaCliente dpc = new DisenioPrendaCliente();
			dpc.setIdCliente(
					Long.valueOf(cliente_split.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "")));
			dpc.setIdPrenda(Long.valueOf(idPrenda));
			dpc.setCreadoPor(auth.getName());
			dpc.setActualizadoPor(auth.getName());
			dpc.setFechaCreacion(dtf.format(now));
			dpc.setUltimaFechaModificacion(dtf.format(now));
			dpc.setEstatus("1");
			disenioPrendaClienteService.save(dpc);
			IdClientes[i] = Long
					.valueOf(cliente_split.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
			i++;
		}

		// Ahora se eliminan los registros que no se usan.
		disenioPrendaClienteService.delete(IdClientes, Long.valueOf(idPrenda));
	}

	@RequestMapping(value = "/prendas", method = RequestMethod.POST)
	public void guardar(HttpServletResponse response, DisenioPrenda dp, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, @RequestParam("file2") MultipartFile foto2,
			RedirectAttributes flash) throws InterruptedException, IOException {
		String[] uniqueFilename = null;
		try {
			uniqueFilename = uService.copy(foto, foto2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// this.dp.setDibujoEspalda(uniqueFilename[0]);
		// this.dp.setDibujoFrente(uniqueFilename[1]);

		Thread.sleep(2000);
		response.sendRedirect("/prendas");
	}

	@RequestMapping(value = "/prendas1", method = RequestMethod.POST)
	public void guardar1(HttpServletResponse response, DisenioPrenda dp, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash)
			throws InterruptedException, IOException {
		String uniqueFilename = null;
		try {
			uniqueFilename = uService.copy2(foto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// this.dp.setDibujoFrente(uniqueFilename);

		Thread.sleep(2000);
		response.sendRedirect("/prendas");
	}

	@RequestMapping(value = "/prendas2", method = RequestMethod.POST)
	public void guardar2(HttpServletResponse response, DisenioPrenda dp, BindingResult result, Model model,
			@RequestParam("file2") MultipartFile foto2, RedirectAttributes flash)
			throws InterruptedException, IOException {
		String uniqueFilename = null;
		try {
			uniqueFilename = uService.copy2(foto2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// this.dp.setDibujoEspalda(uniqueFilename);

		Thread.sleep(2000);
		response.sendRedirect("/prendas");
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/nuevas_listas", method = RequestMethod.GET)
	public List<Object> nuevasListast(Model model, Map<String, Object> m)
			throws NoSuchFieldException, SecurityException {
		List<Object> listaMaestra = new ArrayList<Object>();

		List<DisenioLookup> familias = disenioMaterialService.findAllFamiliaPrenda();
		List<DisenioMaterial> materiales = disenioMaterialService.findAllForCreate();
		List<DisenioLookup> patronajes = disenioMaterialService.findLookUps();
		List<ComercialCliente> clientes = clienteService.findAll(null);
		List<DisenioLookup> generos = disenioLookupService.findByTipoLookup("Familia Genero");
		List<DisenioLookup> marcadores = disenioLookupService.findByTipoLookup("Marcador");

		listaMaestra.add(familias);
		listaMaestra.add(generos);
		listaMaestra.add(clientes);
		listaMaestra.add(marcadores);
		listaMaestra.add(materiales);
		listaMaestra.add(patronajes);

		return listaMaestra;
	}

	// Este es cuando se agrega
	@RequestMapping(value = "/imagenes_prendas", method = RequestMethod.POST)
	public void guardarImagenes(HttpServletResponse response, DisenioPrenda dp, BindingResult result, Model model,
			RedirectAttributes flash, @RequestParam("file-input-1") MultipartFile foto1,
			@RequestParam("name-1") String nombre1, @RequestParam("file-input-2") MultipartFile foto2,
			@RequestParam("name-2") String nombre2, @RequestParam("file-input-3") MultipartFile foto3,
			@RequestParam("name-3") String nombre3, @RequestParam("file-input-4") MultipartFile foto4,
			@RequestParam("name-4") String nombre4, @RequestParam("file-input-5") MultipartFile foto5,
			@RequestParam("name-5") String nombre5, @RequestParam("file-input-6") MultipartFile foto6,
			@RequestParam("name-6") String nombre6, @RequestParam("idPrenda") String idPrenda)
			throws InterruptedException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Cloudinary cloudinary = uService.CloudinaryApi();
		if (nombre1.length() > 0) {
			DisenioPrendaImagen dpi = new DisenioPrendaImagen();
			dpi.setIdPrenda(Long.parseLong(idPrenda));
			dpi.setActualizadoPor(auth.getName());
			dpi.setCreadoPor(auth.getName());
			dpi.setEstatus("1");
			dpi.setFechaCreacion(dtf.format(now));
			dpi.setUltimaFechaModificacion(dtf.format(now));
			dpi.setNombrePrenda(nombre1);
			String uniqueFilename = uService.copy2(foto1);
			cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
					"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
			dpi.setRutaPrenda(uniqueFilename);
			prendaImagenService.save(dpi);
		}

		if (nombre2.length() > 0) {
			DisenioPrendaImagen dpi = new DisenioPrendaImagen();
			dpi.setIdPrenda(Long.parseLong(idPrenda));
			dpi.setActualizadoPor(auth.getName());
			dpi.setCreadoPor(auth.getName());
			dpi.setEstatus("1");
			dpi.setFechaCreacion(dtf.format(now));
			dpi.setUltimaFechaModificacion(dtf.format(now));
			dpi.setNombrePrenda(nombre2);
			String uniqueFilename = uService.copy2(foto2);
			cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
					"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
			dpi.setRutaPrenda(uniqueFilename);
			prendaImagenService.save(dpi);
		}

		if (nombre3.length() > 0) {
			DisenioPrendaImagen dpi = new DisenioPrendaImagen();
			dpi.setIdPrenda(Long.parseLong(idPrenda));
			dpi.setActualizadoPor(auth.getName());
			dpi.setCreadoPor(auth.getName());
			dpi.setEstatus("1");
			dpi.setFechaCreacion(dtf.format(now));
			dpi.setUltimaFechaModificacion(dtf.format(now));
			dpi.setNombrePrenda(nombre3);
			String uniqueFilename = uService.copy2(foto3);
			cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
					"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
			dpi.setRutaPrenda(uniqueFilename);
			prendaImagenService.save(dpi);
		}

		if (nombre4.length() > 0) {
			DisenioPrendaImagen dpi = new DisenioPrendaImagen();
			dpi.setIdPrenda(Long.parseLong(idPrenda));
			dpi.setActualizadoPor(auth.getName());
			dpi.setCreadoPor(auth.getName());
			dpi.setEstatus("1");
			dpi.setFechaCreacion(dtf.format(now));
			dpi.setUltimaFechaModificacion(dtf.format(now));
			dpi.setNombrePrenda(nombre4);
			String uniqueFilename = uService.copy2(foto4);
			cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
					"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
			dpi.setRutaPrenda(uniqueFilename);
			prendaImagenService.save(dpi);
		}

		if (nombre5.length() > 0) {
			DisenioPrendaImagen dpi = new DisenioPrendaImagen();
			dpi.setIdPrenda(Long.parseLong(idPrenda));
			dpi.setActualizadoPor(auth.getName());
			dpi.setCreadoPor(auth.getName());
			dpi.setEstatus("1");
			dpi.setFechaCreacion(dtf.format(now));
			dpi.setUltimaFechaModificacion(dtf.format(now));
			dpi.setNombrePrenda(nombre5);
			String uniqueFilename = uService.copy2(foto5);
			cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
					"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
			dpi.setRutaPrenda(uniqueFilename);
			prendaImagenService.save(dpi);
		}

		if (nombre6.length() > 0) {
			DisenioPrendaImagen dpi = new DisenioPrendaImagen();
			dpi.setIdPrenda(Long.parseLong(idPrenda));
			dpi.setActualizadoPor(auth.getName());
			dpi.setCreadoPor(auth.getName());
			dpi.setEstatus("1");
			dpi.setFechaCreacion(dtf.format(now));
			dpi.setUltimaFechaModificacion(dtf.format(now));
			dpi.setNombrePrenda(nombre6);
			String uniqueFilename = uService.copy2(foto6);
			cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
					"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
			dpi.setRutaPrenda(uniqueFilename);
			prendaImagenService.save(dpi);
		}

		DisenioPrendaImagen dpiInventario = new DisenioPrendaImagen();
		dpiInventario.setIdPrenda(Long.parseLong(idPrenda));
		dpiInventario.setActualizadoPor(auth.getName());
		dpiInventario.setCreadoPor(auth.getName());
		dpiInventario.setEstatus("1");
		dpiInventario.setFechaCreacion(dtf.format(now));
		dpiInventario.setUltimaFechaModificacion(dtf.format(now));
		dpiInventario.setNombrePrenda("Inventario");
		dpiInventario.setRutaPrenda("Prenda.jpg");
		prendaImagenService.save(dpiInventario);

		response.sendRedirect("/prendas");
	}

	// Este cuando se edita
	@RequestMapping(value = "/imagenes_prendas_editar", method = RequestMethod.POST)
	public void editarImagenes(HttpServletResponse response, DisenioPrenda dp, BindingResult result, Model model,
			RedirectAttributes flash, @RequestParam("file-input-edit-1") MultipartFile foto1,
			@RequestParam("name-edit-1") String nombre1, @RequestParam("id-input-edit-1") String id1,
			@RequestParam("status-input-edit-1") String status1, @RequestParam("file-input-edit-2") MultipartFile foto2,
			@RequestParam("name-edit-2") String nombre2, @RequestParam("id-input-edit-2") String id2,
			@RequestParam("status-input-edit-2") String status2, @RequestParam("file-input-edit-3") MultipartFile foto3,
			@RequestParam("name-edit-3") String nombre3, @RequestParam("id-input-edit-3") String id3,
			@RequestParam("status-input-edit-3") String status3, @RequestParam("file-input-edit-4") MultipartFile foto4,
			@RequestParam("name-edit-4") String nombre4, @RequestParam("id-input-edit-4") String id4,
			@RequestParam("status-input-edit-4") String status4, @RequestParam("file-input-edit-5") MultipartFile foto5,
			@RequestParam("name-edit-5") String nombre5, @RequestParam("id-input-edit-5") String id5,
			@RequestParam("status-input-edit-5") String status5, @RequestParam("file-input-edit-6") MultipartFile foto6,
			@RequestParam("name-edit-6") String nombre6, @RequestParam("id-input-edit-6") String id6,
			@RequestParam("status-input-edit-6") String status6, @RequestParam("idPrenda") String idPrenda,
			@RequestParam("accion") String accion) throws InterruptedException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Cloudinary cloudinary = uService.CloudinaryApi();
		if (accion.equalsIgnoreCase("editar")) {
			if (!id1.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpi = prendaImagenService.findOne(Long.parseLong(id1));
				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre1)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre1);
					prendaImagenService.save(dpi);
				}
				if (status1.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					String uniqueFilename = uService.copy2(foto1);
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					uService.deletePrenda(dpi.getRutaPrenda());
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
				if (status1.equalsIgnoreCase("delete")) {
					// Se va a borrar el registro
					uService.deletePrenda(dpi.getRutaPrenda());
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					prendaImagenService.delete(Long.parseLong(id1));
				}
			} else {
				if (!nombre1.isEmpty() && !foto1.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.parseLong(idPrenda));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre1);
					String uniqueFilename = uService.copy2(foto1);
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
			}

			if (!id2.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpi = prendaImagenService.findOne(Long.parseLong(id2));
				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre2)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre2);
					prendaImagenService.save(dpi);
				}
				if (status2.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					String uniqueFilename = uService.copy2(foto2);
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					uService.deletePrenda(dpi.getRutaPrenda());
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
				if (status2.equalsIgnoreCase("delete")) {
					// Se va a borrar el registro
					uService.deletePrenda(dpi.getRutaPrenda());
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					prendaImagenService.delete(Long.parseLong(id2));
				}
			} else {
				if (!nombre2.isEmpty() && !foto2.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.parseLong(idPrenda));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre2);
					String uniqueFilename = uService.copy2(foto2);
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
			}

			if (!id3.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpi = prendaImagenService.findOne(Long.parseLong(id3));
				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre3)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre3);
					prendaImagenService.save(dpi);
				}
				if (status3.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					String uniqueFilename = uService.copy2(foto3);
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					uService.deletePrenda(dpi.getRutaPrenda());
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
				if (status3.equalsIgnoreCase("delete")) {
					// Se va a borrar el registro
					uService.deletePrenda(dpi.getRutaPrenda());
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					prendaImagenService.delete(Long.parseLong(id3));
				}
			} else {
				if (!nombre3.isEmpty() && !foto3.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.parseLong(idPrenda));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre3);
					String uniqueFilename = uService.copy2(foto3);
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
			}

			if (!id4.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpi = prendaImagenService.findOne(Long.parseLong(id4));
				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre4)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre4);
					prendaImagenService.save(dpi);
				}
				if (status4.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					String uniqueFilename = uService.copy2(foto4);
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					uService.deletePrenda(dpi.getRutaPrenda());
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
				if (status4.equalsIgnoreCase("delete")) {
					// Se va a borrar el registro
					uService.deletePrenda(dpi.getRutaPrenda());
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					prendaImagenService.delete(Long.parseLong(id4));
				}
			} else {
				if (!nombre4.isEmpty() && !foto4.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.parseLong(idPrenda));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre4);
					String uniqueFilename = uService.copy2(foto4);
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
			}

			if (!id5.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpi = prendaImagenService.findOne(Long.parseLong(id5));
				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre5)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre5);
					prendaImagenService.save(dpi);
				}
				if (status5.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					String uniqueFilename = uService.copy2(foto5);
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					uService.deletePrenda(dpi.getRutaPrenda());
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
				if (status5.equalsIgnoreCase("delete")) {
					// Se va a borrar el registro
					uService.deletePrenda(dpi.getRutaPrenda());
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					prendaImagenService.delete(Long.parseLong(id5));
				}
			} else {
				if (!nombre5.isEmpty() && !foto5.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.parseLong(idPrenda));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre5);
					String uniqueFilename = uService.copy2(foto5);
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
			}

			if (!id6.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpi = prendaImagenService.findOne(Long.parseLong(id6));
				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre6)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre6);
					prendaImagenService.save(dpi);
				}
				if (status6.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					String uniqueFilename = uService.copy2(foto6);
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					uService.deletePrenda(dpi.getRutaPrenda());
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
				if (status6.equalsIgnoreCase("delete")) {
					// Se va a borrar el registro
					uService.deletePrenda(dpi.getRutaPrenda());
					cloudinary.uploader().destroy(
							"prendas/" + dpi.getRutaPrenda().substring(0, dpi.getRutaPrenda().length() - 4),
							ObjectUtils.emptyMap());
					prendaImagenService.delete(Long.parseLong(id6));
				}
			} else {
				if (!nombre6.isEmpty() && !foto6.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.parseLong(idPrenda));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre6);
					String uniqueFilename = uService.copy2(foto6);
					cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
							"prendas/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
					dpi.setRutaPrenda(uniqueFilename);
					prendaImagenService.save(dpi);
				}
			}
		}

		if (accion.equalsIgnoreCase("copiar")) {

			String[] ides = idPrenda.split(",");

			if (!id1.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpiViejo = prendaImagenService.findOne(Long.parseLong(id1));
				DisenioPrendaImagen dpi = new DisenioPrendaImagen();
				dpi.setIdPrenda(Long.valueOf(ides[0]));
				dpi.setNombrePrenda(dpiViejo.getNombrePrenda());
				dpi.setRutaPrenda(dpiViejo.getRutaPrenda());
				dpi.setCreadoPor(dpiViejo.getCreadoPor());
				dpi.setActualizadoPor(dpiViejo.getActualizadoPor());
				dpi.setFechaCreacion(dpiViejo.getFechaCreacion());
				dpi.setUltimaFechaModificacion(dpiViejo.getUltimaFechaModificacion());
				dpi.setEstatus(dpiViejo.getEstatus());

				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre1)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre1);
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}
				if (status1.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					dpi.setRutaPrenda(uService.copy2(foto1));
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}

				prendaImagenService.save(dpi);
			} else {
				if (!nombre1.isEmpty() && !foto1.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.valueOf(ides[0]));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre1);
					dpi.setRutaPrenda(uService.copy2(foto1));
					prendaImagenService.save(dpi);
				}
			}

			if (!id2.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpiViejo = prendaImagenService.findOne(Long.parseLong(id2));
				DisenioPrendaImagen dpi = new DisenioPrendaImagen();
				dpi.setIdPrenda(Long.valueOf(ides[0]));
				dpi.setNombrePrenda(dpiViejo.getNombrePrenda());
				dpi.setRutaPrenda(dpiViejo.getRutaPrenda());
				dpi.setCreadoPor(dpiViejo.getCreadoPor());
				dpi.setActualizadoPor(dpiViejo.getActualizadoPor());
				dpi.setFechaCreacion(dpiViejo.getFechaCreacion());
				dpi.setUltimaFechaModificacion(dpiViejo.getUltimaFechaModificacion());
				dpi.setEstatus(dpiViejo.getEstatus());

				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre2)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre2);
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}
				if (status2.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					dpi.setRutaPrenda(uService.copy2(foto2));
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}

				prendaImagenService.save(dpi);
			} else {
				if (!nombre2.isEmpty() && !foto2.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.valueOf(ides[0]));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre2);
					dpi.setRutaPrenda(uService.copy2(foto2));
					prendaImagenService.save(dpi);
				}
			}

			if (!id3.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpiViejo = prendaImagenService.findOne(Long.parseLong(id3));
				DisenioPrendaImagen dpi = new DisenioPrendaImagen();
				dpi.setIdPrenda(Long.valueOf(ides[0]));
				dpi.setNombrePrenda(dpiViejo.getNombrePrenda());
				dpi.setRutaPrenda(dpiViejo.getRutaPrenda());
				dpi.setCreadoPor(dpiViejo.getCreadoPor());
				dpi.setActualizadoPor(dpiViejo.getActualizadoPor());
				dpi.setFechaCreacion(dpiViejo.getFechaCreacion());
				dpi.setUltimaFechaModificacion(dpiViejo.getUltimaFechaModificacion());
				dpi.setEstatus(dpiViejo.getEstatus());

				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre3)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre3);
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}
				if (status3.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					dpi.setRutaPrenda(uService.copy2(foto3));
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}

				prendaImagenService.save(dpi);
			} else {
				if (!nombre3.isEmpty() && !foto3.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.valueOf(ides[0]));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre3);
					dpi.setRutaPrenda(uService.copy2(foto3));
					prendaImagenService.save(dpi);
				}
			}

			if (!id4.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpiViejo = prendaImagenService.findOne(Long.parseLong(id4));
				DisenioPrendaImagen dpi = new DisenioPrendaImagen();
				dpi.setIdPrenda(Long.valueOf(ides[0]));
				dpi.setNombrePrenda(dpiViejo.getNombrePrenda());
				dpi.setRutaPrenda(dpiViejo.getRutaPrenda());
				dpi.setCreadoPor(dpiViejo.getCreadoPor());
				dpi.setActualizadoPor(dpiViejo.getActualizadoPor());
				dpi.setFechaCreacion(dpiViejo.getFechaCreacion());
				dpi.setUltimaFechaModificacion(dpiViejo.getUltimaFechaModificacion());
				dpi.setEstatus(dpiViejo.getEstatus());

				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre4)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre4);
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}
				if (status4.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					dpi.setRutaPrenda(uService.copy2(foto4));
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}

				prendaImagenService.save(dpi);
			} else {
				if (!nombre4.isEmpty() && !foto4.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.valueOf(ides[0]));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre4);
					dpi.setRutaPrenda(uService.copy2(foto4));
					prendaImagenService.save(dpi);
				}
			}

			if (!id5.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpiViejo = prendaImagenService.findOne(Long.parseLong(id5));
				DisenioPrendaImagen dpi = new DisenioPrendaImagen();
				dpi.setIdPrenda(Long.valueOf(ides[0]));
				dpi.setNombrePrenda(dpiViejo.getNombrePrenda());
				dpi.setRutaPrenda(dpiViejo.getRutaPrenda());
				dpi.setCreadoPor(dpiViejo.getCreadoPor());
				dpi.setActualizadoPor(dpiViejo.getActualizadoPor());
				dpi.setFechaCreacion(dpiViejo.getFechaCreacion());
				dpi.setUltimaFechaModificacion(dpiViejo.getUltimaFechaModificacion());
				dpi.setEstatus(dpiViejo.getEstatus());

				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre5)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre5);
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}
				if (status5.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					dpi.setRutaPrenda(uService.copy2(foto5));
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}

				prendaImagenService.save(dpi);
			} else {
				if (!nombre5.isEmpty() && !foto5.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.valueOf(ides[0]));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre5);
					dpi.setRutaPrenda(uService.copy2(foto5));
					prendaImagenService.save(dpi);
				}
			}

			if (!id6.isEmpty()) {
				// Si existe la imagen ya.
				DisenioPrendaImagen dpiViejo = prendaImagenService.findOne(Long.parseLong(id6));
				DisenioPrendaImagen dpi = new DisenioPrendaImagen();
				dpi.setIdPrenda(Long.valueOf(ides[0]));
				dpi.setNombrePrenda(dpiViejo.getNombrePrenda());
				dpi.setRutaPrenda(dpiViejo.getRutaPrenda());
				dpi.setCreadoPor(dpiViejo.getCreadoPor());
				dpi.setActualizadoPor(dpiViejo.getActualizadoPor());
				dpi.setFechaCreacion(dpiViejo.getFechaCreacion());
				dpi.setUltimaFechaModificacion(dpiViejo.getUltimaFechaModificacion());
				dpi.setEstatus(dpiViejo.getEstatus());

				if (!dpi.getNombrePrenda().equalsIgnoreCase(nombre6)) {
					// Se va a cambiar el nombre de la prenda
					dpi.setNombrePrenda(nombre6);
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}
				if (status6.equalsIgnoreCase("Alter")) {
					// Se va a cambiar la imagen
					dpi.setRutaPrenda(uService.copy2(foto6));
					dpi.setActualizadoPor(auth.getName());
					dpi.setUltimaFechaModificacion(dtf.format(now));
					prendaImagenService.save(dpi);
				}

				prendaImagenService.save(dpi);
			} else {
				if (!nombre6.isEmpty() && !foto6.isEmpty()) {
					DisenioPrendaImagen dpi = new DisenioPrendaImagen();
					dpi.setIdPrenda(Long.valueOf(ides[0]));
					dpi.setActualizadoPor(auth.getName());
					dpi.setCreadoPor(auth.getName());
					dpi.setEstatus("1");
					dpi.setUltimaFechaModificacion(dtf.format(now));
					dpi.setNombrePrenda(nombre6);
					dpi.setRutaPrenda(uService.copy2(foto6));
					prendaImagenService.save(dpi);
				}
			}

			// Esta es la prenda de inventario
			DisenioPrendaImagen dpiInventario = new DisenioPrendaImagen();
			dpiInventario.setIdPrenda(Long.valueOf(ides[0]));
			dpiInventario.setActualizadoPor(auth.getName());
			dpiInventario.setCreadoPor(auth.getName());
			dpiInventario.setEstatus("1");
			dpiInventario.setFechaCreacion(dtf.format(now));
			dpiInventario.setUltimaFechaModificacion(dtf.format(now));
			dpiInventario.setNombrePrenda("Inventario");
			dpiInventario.setRutaPrenda("Prenda.jpg");
			prendaImagenService.save(dpiInventario);
		}

		response.sendRedirect("/prendas");
	}

	@RequestMapping(value = "/ima_prenda", method = RequestMethod.POST)
	public Object guardarImagenes(@RequestParam("id") Long id) {

		Object dpi = serviceDetallePedido.findOne(id);

		System.out.println("sI ENTRO AL REST");

		System.out.println("OBJETO" + dpi);

		return dpi;

	}

	@RequestMapping(value = "/ima_prenda_replace", method = RequestMethod.GET)
	public Object buscarimg(@RequestParam("idPrenda") Long idPrenda, @RequestParam("idTela") Long idTela) {

		Object dpi = serviceInvetarioImg.findImagen(idPrenda, idTela);

		System.out.println("sI ENTRO AL REST de laimg segundo");

		System.out.println("OBJETO" + dpi);

		return dpi;

	}

	@GetMapping("/getMaterialesByTipo")
	public List<Object[]> getMaterialesByTipo(@RequestParam(name = "idTipoMaterial") Long idTipoMaterial,
			@RequestParam(name = "idMaterial") Long idMaterial) {

		return disenioMaterialService.findMaterialByTipo(idTipoMaterial, idMaterial);
	}

	@GetMapping("/getMaterialesExtra")
	public List<Object[]> getMaterialesExtra(@RequestParam(name = "idMaterialPrenda") Long idMaterialPrenda) {

		return materialExtraService.findAllByMaterial(idMaterialPrenda);
	}

	@PostMapping("postMaterialesExtra")
	public List<Object[]> postMaterialesExtra(@RequestParam(name = "id_material") Long idMaterial,
			@RequestParam(name = "id_material_prenda") Long idMaterialPrenda) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		DisenioMaterialExtraPrenda materialExtra = new DisenioMaterialExtraPrenda();
		materialExtra.setIdMaterial(idMaterial);
		materialExtra.setIdMaterialPrenda(idMaterialPrenda);
		materialExtra.setCreadoPor(auth.getName());
		materialExtra.setActualizadoPor(auth.getName());
		try {
			materialExtraService.save(materialExtra);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("aqui si entra we");
			throw new Exception();

		}
		return materialExtraService.findAllByMaterial(idMaterialPrenda);
	}

	@DeleteMapping("/deleteMaterialesExtra")
	public List<Object[]> deleteMaterialesExtra(@RequestParam(name = "idMaterialExtra") Long idMaterialExtra,
			@RequestParam(name = "idMaterialPrenda") Long idMaterialPrenda) {
		materialExtraService.delete(idMaterialExtra);
		return materialExtraService.findAllByMaterial(idMaterialPrenda);
	}

	@GetMapping("getPrendaIdRuta/{id}")
	public ResponseEntity<?> getPrenda(@PathVariable(name = "id") Long id) {

		Map<String, Object> response = new HashMap<>();
		DisenioPrenda prenda = null;
		try {
			prenda = prendaService.findOne(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (prenda == null) {
			response.put("mensaje", "La empresa con el id " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Long>(prenda.getIdRuta(), HttpStatus.OK);
	}

}
