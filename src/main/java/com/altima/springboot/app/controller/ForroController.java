package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.DisenioFamiliaComposicionForro;
import com.altima.springboot.app.models.entity.DisenioForro;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
//import com.altima.springboot.app.models.entity.DisenioProceso;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.service.IAmpInventarioProovedorService;
import com.altima.springboot.app.models.service.IComprasProveedorService;
import com.altima.springboot.app.models.service.IDisenioCalidadService;
import com.altima.springboot.app.models.service.IDisenioFamiliaComposicionForroService;
import com.altima.springboot.app.models.service.IDisenioForroService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
//import com.altima.springboot.app.models.service.IDisenioProcesoService;
import com.altima.springboot.app.models.service.IDisenioTelaService;
import com.altima.springboot.app.models.service.IUploadService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@CrossOrigin(origins = { "*" })
@Controller
public class ForroController {
	
	@Autowired
	private IDisenioMaterialService disenioMaterialService;
	//@Autowired
	//private IDisenioProcesoService disenioProcesoService;
	@Autowired
	private IDisenioForroService forroService;
	@Autowired
	private IDisenioTelaService disenioTelaService;
	@Autowired
	private IUploadService UploadService;
	@Autowired
	private IDisenioFamiliaComposicionForroService ComposicionForroService;
	@Autowired
	private IAmpInventarioProovedorService ProveedorSerivice;
	@Autowired
	IDisenioCalidadService disenioCalidad;
	

	@PostMapping("/guardar-forro")
	public String guardar_forro(
			DisenioForro forro,
			@RequestParam("txtTablaf") String composicion,
			@RequestParam("txtTabla2f") String idComposicion,
			@RequestParam(value="imagenForro", required=false) MultipartFile imagenForro,
			RedirectAttributes redirectAttrs) throws IOException {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Cloudinary cloudinary = UploadService.CloudinaryApi();
		if ( forro.getIdForro() == null || forro.getEstatusForro().equals("0")){
			if (forro.getIdForro() == null) {
				forro.setIdText(null);
				forro.setCreadoPor(auth.getName());
				forro.setIdUnidadMedida(Long.valueOf(1));
				forro.setExistenciaForro("1");
				forro.setFechaCreacion(hourdateFormat.format(date));
				forro.setUltimaFechaModificacion(null);
				forro.setEstatus("1");
				forro.setEstatusForro("0");
				forro.setFoto("imagen");
				if (!imagenForro.isEmpty()){
					if ( forro.getFoto() != null && forro.getFoto().length() > 0) {
						UploadService.deleteForro(forro.getFoto());
						cloudinary.uploader().destroy("forros/"+forro.getFoto().substring(0,forro.getFoto().length()-4), ObjectUtils.emptyMap());
					}
					String uniqueFilename = null;
					try {
						uniqueFilename = UploadService.copyForro(imagenForro);
						cloudinary.uploader().upload(UploadService.fileForro(uniqueFilename), ObjectUtils.asMap("public_id", "forros/"+uniqueFilename.substring(0,uniqueFilename.length()-4)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					forro.setFoto(uniqueFilename);
				}
				forroService.save(forro);
				forro.setIdTextProspecto("PROSFOR"+(forro.getIdForro()+10000));
				forroService.save(forro);
				redirectAttrs
	            .addFlashAttribute("title", "Forro guardado correctamente")
	            .addFlashAttribute("icon", "success");
			}
			else {
				forro.setIdUnidadMedida(Long.valueOf(1));
				forro.setFoto("imagen");
				forro.setUltimaFechaModificacion(hourdateFormat.format(date));
				forro.setActualizadoPor(auth.getName());
				if (!imagenForro.isEmpty()){
					if ( forro.getFoto() != null && forro.getFoto().length() > 0) {
						UploadService.deleteForro(forro.getFoto());
						cloudinary.uploader().destroy("forros/"+forro.getFoto().substring(0,forro.getFoto().length()-4), ObjectUtils.emptyMap());
					}
					String uniqueFilename = null;
					try {
						uniqueFilename = UploadService.copyForro(imagenForro);
						cloudinary.uploader().upload(UploadService.fileForro(uniqueFilename), ObjectUtils.asMap("public_id", "forros/"+uniqueFilename.substring(0,uniqueFilename.length()-4)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					forro.setFoto(uniqueFilename);
				}
				forroService.save(forro);
					redirectAttrs
		            .addFlashAttribute("title", "Forro editado correctamente")
		            .addFlashAttribute("icon", "success");
			}
		
			ComposicionForroService.deleteLista(forro.getIdForro());
			if ( composicion.length()>1) {
			String [] vect = composicion.split(",");
			String [] vect2 = idComposicion.split(",");
			for(int i= 0 ; i<vect.length -1;i++) {
				DisenioFamiliaComposicionForro ff = new DisenioFamiliaComposicionForro();
				ff.setIdFamiliaComposicion(Long.valueOf(vect2[i]));
				ff.setIdForro(forro.getIdForro());
				ff.setCreadoPor(auth.getName());
				ff.setActualizadoPor("null");
				ff.setFechaCreacion(hourdateFormat.format(date));
				ff.setUltimaFechaModificacion(hourdateFormat.format(date));
				ff.setComposicion(vect[i]);
				ComposicionForroService.save(ff);
			}
			} 	
		}
		else {
			forro.setIdText("FORRO"+(forro.getIdForro()+10000));
			if (!imagenForro.isEmpty()){
				if ( forro.getFoto() != null && forro.getFoto().length() > 0) {
					UploadService.deleteForro(forro.getFoto());
					cloudinary.uploader().destroy("forros/"+forro.getFoto().substring(0,forro.getFoto().length()-4), ObjectUtils.emptyMap());
				}
				String uniqueFilename = null;
				try {
					uniqueFilename = UploadService.copyForro(imagenForro);
					cloudinary.uploader().upload(UploadService.fileForro(uniqueFilename), ObjectUtils.asMap("public_id", "forros/"+uniqueFilename.substring(0,uniqueFilename.length()-4)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				forro.setFoto(uniqueFilename);
			}
			ComposicionForroService.deleteLista(forro.getIdForro());
			forro.setActualizadoPor(auth.getName());
			forro.setUltimaFechaModificacion(hourdateFormat.format(date));
			forroService.save(forro);
			
			String [] vect = composicion.split(",");
			String [] vect2 = idComposicion.split(",");
			for(int i= 0 ; i<vect.length -1;i++) {
				DisenioFamiliaComposicionForro ff = new DisenioFamiliaComposicionForro();
				ff.setIdFamiliaComposicion(Long.valueOf(vect2[i]));
				ff.setIdForro(forro.getIdForro());
				ff.setCreadoPor(auth.getName());
				ff.setActualizadoPor("null");
				ff.setFechaCreacion(hourdateFormat.format(date));
				ff.setUltimaFechaModificacion(hourdateFormat.format(date));
				ff.setComposicion(vect[i]);
				ComposicionForroService.save(ff);
			}
			
			redirectAttrs
	        .addFlashAttribute("title", "Forro Editado correctamente")
	        .addFlashAttribute("icon", "success");
		}
			
			
		return "redirect:materiales";
	}
	
	
	@GetMapping("/editar-forro/{id}")
	public String editar(@PathVariable(value="id") Long id , Model model) {
		

		// Comienza erik
		DisenioForro forro;
		forro=forroService.findOne(id);
		model.addAttribute("forro", forro);
		model.addAttribute("editar", "true");
		model.addAttribute("listForroComposicon", disenioTelaService.ComposicionForroMN(id));
		model.addAttribute("lisCom",disenioTelaService.findAllComposicion());
		model.addAttribute("EstatusCalidadForro", forroService.EstatusCalidadForro(id));
		
		// model.addAttribute("listForrosAutori", forroService.forrosAutorizados());
		List<DisenioLookup> listLookupsMed = disenioMaterialService.findListaLookupMed();
		model.addAttribute("listLookupsMed", listLookupsMed);
		System.out.println("Entra a la consulta de forro composicion");
		model.addAttribute("form", "forro");
		model.addAttribute("proveedor", ProveedorSerivice.Proveedores());
		
		return"agregar-material";   
	}
	
	@GetMapping(value = "/uploads/forros/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = UploadService.loadForro(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@RequestMapping(value = "/buscar-forro-nombre", method = RequestMethod.POST)
	@ResponseBody
	public String buscarNombreForro( String nombre) {
			return  forroService.buscar_forro(nombre);
	}
	
	//Metodo para dar de baja un forro
	@GetMapping("/delete-forro/{id}") 
	public String deleteMaterial(@PathVariable("id") Long idForro, RedirectAttributes redirectAttrs) {
		if (forroService.disponibles(idForro) ==0 ){
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DisenioForro forro = forroService.findOne(idForro);
			forro.setEstatus("0");
			forro.setActualizadoPor(auth.getName());
			forro.setUltimaFechaModificacion(hourdateFormat.format(date));
			forroService.save(forro);
			redirectAttrs
        	.addFlashAttribute("title", "Forro dado de baja correctamente")
        	.addFlashAttribute("icon", "success");
			return "redirect:/materiales";
		}
		else{
			redirectAttrs
			.addFlashAttribute("title", "No es posible eliminar, cuenta con existencias.")
			.addFlashAttribute("icon", "error");
			  return "redirect:/materiales";
		}
		
	}
	
	//Metodo para dar de alta un forro
	@GetMapping("/dar-alta-forro/{id}") 
	public String darAltaForro(@PathVariable("id") Long idForro, RedirectAttributes redirectAttrs) {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DisenioForro forro = forroService.findOne(idForro);
		forro.setEstatus("1");
		forro.setActualizadoPor(auth.getName());
		forro.setUltimaFechaModificacion(hourdateFormat.format(date));
		forroService.save(forro);
		redirectAttrs
        .addFlashAttribute("title", "Forro dado de alta correctamente")
        .addFlashAttribute("icon", "success");
		return "redirect:/materiales";
	}
	

}