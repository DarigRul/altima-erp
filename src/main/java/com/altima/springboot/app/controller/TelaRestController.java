package com.altima.springboot.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.entity.ProduccionMiniTrazo;
import com.altima.springboot.app.models.service.IDisenioTelaService;
import com.altima.springboot.app.models.service.UploadServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TelaRestController {
	@Autowired
	private IDisenioTelaService disenioTelaService;

	@Autowired
	UploadServiceImpl uService;

	@GetMapping("/getMaterial")
	public List<DisenioMaterial> listarMaterial(@RequestParam(name = "tipo") String tipo) {
		System.out.println("el tepo" + tipo);
		return disenioTelaService.findAllMaterial(tipo);
	}

	@GetMapping("/getTipo")
	public List<DisenioLookup> listarTipo() {
		return disenioTelaService.findAllTipo();
	}

	@RequestMapping(value = "/buscar-tela-nombre", method = RequestMethod.POST)
	@ResponseBody
	public String buscarNombreTela(String nombre) {

		System.out.println("------->" + disenioTelaService.buscar_tela(nombre));

		return disenioTelaService.buscar_tela(nombre);
	}

	// guardar porcentaje de encogimiento/estiramiento de la tela en pantalla de
	// producción
	@RequestMapping(value = "/agregarPorcentajeEncogimiento", method = RequestMethod.POST)
	@ResponseBody
	public void guardarPorcentajeEncogimiento(@RequestParam(name = "encogiTela") int encogiTela,
			@RequestParam(name = "idTela") Long id) throws Exception {
		DisenioTela disenioTela = disenioTelaService.findOne(id);
		System.out.println(encogiTela);
		disenioTela.setPruebaEncogimientoLargo(encogiTela);

		disenioTelaService.save(disenioTela);

	}

	// @PostMapping("/guardarPdfEncogimiento")
	// @ResponseStatus(HttpStatus.CREATED)
	// @Transactional
	// public ResponseEntity<?> guardarImagenes(@RequestParam Long idTela, @RequestParam String descripcion,
	// 		@RequestParam MultipartFile archivoEncogimiento) throws Exception {

	// 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	// 	Cloudinary cloudinary = uService.CloudinaryApi();
	// 	String uniqueFilename;
	// 	Map<String, Object> response = new HashMap<>();
	// 	DisenioTela tela = disenioTelaService.findOne(idTela);
	// 	System.out.println(idTela+descripcion);
	// 	try {
	// 		if (tela.getRutaPdfEncogimiento()!=null) {
	// 			cloudinary.uploader().destroy("pdf/" + tela.getRutaPdfEncogimiento().substring(0, tela.getRutaPdfEncogimiento().length() - 4)
	// 			, ObjectUtils.asMap("resourceType", "pdf"));
	// 		}
	// 		uniqueFilename = uService.copy2(archivoEncogimiento);
	// 		tela.setDescripcionPdfEncogimiento(descripcion);
	// 		tela.setRutaPdfEncogimiento(uniqueFilename);
	// 		cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
	// 				"pdf/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
	// 		disenioTelaService.save(tela);

	// 	}

	// 	catch (IOException e) {
	// 		response.put("mensaje", "Error al insertar en la BD");
	// 		response.put("error", e.getMessage() + ": " + e.getMessage());
	// 		e.printStackTrace();
	// 		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}
	// 	return new ResponseEntity<DisenioTela>(tela, HttpStatus.CREATED);
	// }

}
