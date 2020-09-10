package com.altima.springboot.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.DisenioFamiliaComposicionTela;
import com.altima.springboot.app.models.entity.DisenioForro;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.models.entity.DisenioMaterial;
import com.altima.springboot.app.models.entity.DisenioMaterialTela;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.entity.DisenioTelaForro;
import com.altima.springboot.app.models.entity.DisenioTelaPrenda;
import com.altima.springboot.app.models.service.IAmpInventarioProovedorService;
import com.altima.springboot.app.models.service.IDisenioFamiliaComposicionTelaService;
import com.altima.springboot.app.models.service.IDisenioForroService;
import com.altima.springboot.app.models.service.IDisenioLookupService;
import com.altima.springboot.app.models.service.IDisenioMaterialService;
import com.altima.springboot.app.models.service.IDisenioMaterialTelaService;
import com.altima.springboot.app.models.service.IDisenioTelaForroService;
import com.altima.springboot.app.models.service.IDisenioTelaService;
import com.altima.springboot.app.models.service.IUploadService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Controller
public class TelaController {
	@Autowired
	private IDisenioMaterialService disenioMaterialService;
	@Autowired
	private IDisenioLookupService lookupService; 
	@Autowired
	private IDisenioForroService forroService;
	@Autowired
	private IDisenioTelaService disenioTelaService;
	@Autowired
	private IDisenioFamiliaComposicionTelaService ComposicionTelaService;
	@Autowired
	private IUploadService UploadService;
	
	
	@Autowired
	private IDisenioTelaForroService TelaForroService;
	
	@Autowired
	private IDisenioMaterialTelaService MaterialService;
	
	@Autowired
	private IAmpInventarioProovedorService ProveedorSerivice;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	
	@PostMapping("guardar-tela")
	public String guardar_tela( DisenioTela tela,
			@RequestParam("txtTabla") String composicion,
			@RequestParam("txtTabla2") String idComposicion,
			@RequestParam("colormat") String colormat,
			@RequestParam("codcolor") String codcolor,
			@RequestParam("idtipomat") String idtipomat,
			@RequestParam("posicionm") String posicion,
			@RequestParam(value="botones" , required=false) String botones,
			@RequestParam( value="hilos" , required=false) String hilos,
			@RequestParam( value="cierres" , required=false) String cierres,
			@RequestParam( value="adornos" , required=false) String adornos,
			@RequestParam( value="forros" , required=false) String forros,
			@RequestParam( value="id_prendas" , required=false) String id_prendas,
			@RequestParam(value="imagenTela", required=false) MultipartFile imagenTela,
			RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Cloudinary cloudinary = UploadService.CloudinaryApi();
		if ( tela.getIdTela()== null || !disenioTelaService.EstatusCalidadTela(tela.getIdTela()).equals("2") ) {
			if ( tela.getIdTela() == null) {
				tela.setIdText(null);
				tela.setCreadoPor(auth.getName());
				tela.setFechaCreacion(hourdateFormat.format(date));
				tela.setUltimaFechaModificacion(null);
				tela.setEstatus("1"); //Estatus para ver el registro en el sistema
				tela.setEstatusTela("0");// estatus para la aprobacion de la tela
				tela.setIdUnidadMedida("1");
				if (!imagenTela.isEmpty()){
					if ( tela.getFoto() != null && tela.getFoto().length() > 0) {
	
						UploadService.delete(tela.getFoto());
						cloudinary.uploader().destroy("telas/"+tela.getFoto().substring(0,tela.getFoto().length()-4), ObjectUtils.emptyMap());
					}
					
					String uniqueFilename = null;
					try {
						uniqueFilename = UploadService.copyTela(imagenTela);
						cloudinary.uploader().upload(UploadService.fileTela(uniqueFilename), ObjectUtils.asMap("public_id", "telas/"+uniqueFilename.substring(0,uniqueFilename.length()-4)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tela.setFoto(uniqueFilename);
				}
				disenioTelaService.save(tela);
				tela.setIdTextProspecto("PROSTEL"+(tela.getIdTela()+10000));
				disenioTelaService.save(tela);
				redirectAttrs
	            .addFlashAttribute("title", "Tela guardada correctamente")
	            .addFlashAttribute("icon", "success");
			}
			else {
				if (!imagenTela.isEmpty()){
					if ( tela.getFoto() != null && tela.getFoto().length() > 0) {
						cloudinary.uploader().destroy("telas/"+tela.getFoto().substring(0,tela.getFoto().length()-4), ObjectUtils.emptyMap());
						UploadService.delete(tela.getFoto());
						
					}
					
					String uniqueFilename = null;
					try {
						uniqueFilename = UploadService.copyTela(imagenTela);{
					    cloudinary.uploader().upload(UploadService.fileTela(uniqueFilename), ObjectUtils.asMap("public_id", "telas/"+uniqueFilename.substring(0,uniqueFilename.length()-4)));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tela.setFoto(uniqueFilename);
				}
				tela.setIdUnidadMedida("1");
				tela.setActualizadoPor(auth.getName());
				tela.setUltimaFechaModificacion(hourdateFormat.format(date));
				disenioTelaService.save(tela);
				redirectAttrs
	            .addFlashAttribute("title", "Tela editada correctamente")
	            .addFlashAttribute("icon", "success");
			}
			
		
			//1/3 Parte de Tela-Composicion
			if ( composicion.length()>1) {
			String [] vect = composicion.split(",");
			String [] vect2 = idComposicion.split(",");
			for(int i= 0 ; i<vect.length -1;i++) {
				DisenioFamiliaComposicionTela fc = new DisenioFamiliaComposicionTela();
				fc.setIdFamiliaComposicion(Long.valueOf(vect2[i]));
				fc.setIdTela(tela.getIdTela());
				fc.setCreadoPor(auth.getName());
				fc.setActualizadoPor(auth.getName());
				fc.setFechaCreacion(hourdateFormat.format(date));
				fc.setUltimaFechaModificacion(hourdateFormat.format(date));
				fc.setComposicion(vect[i]);
				ComposicionTelaService.save(fc);
			}
			
			//Accion para borrar las composiciones que se borraron desde el html
			ComposicionTelaService.deleteBorrados(vect2, tela.getIdTela());
		}
			
			//2/3 Parte de Tela-Prenda
			if ( id_prendas.length()>1) {
				String [] vect = id_prendas.split(",");
				for(int i= 0 ; i<vect.length -1;i++) {
					DisenioTelaPrenda tp = new DisenioTelaPrenda();
					tp.setIdPrenda(Long.valueOf(vect[i]));
					tp.setIdTela(tela.getIdTela());
					tp.setCreadoPor(auth.getName());
					tp.setActualizadoPor(auth.getName());
					tp.setFechaCreacion(hourdateFormat.format(date));
					tp.setUltimaFechaModificacion(hourdateFormat.format(date));
					disenioTelaService.saveTelaPrenda(tp);
				}
				
				//Parte para eliminar las que se eliminaron del HTML
				disenioTelaService.deleteBorrados(vect, tela.getIdTela());
				
			}
		}
		else {
			tela.setIdText("TELA"+(tela.getIdTela()+10000));
			tela.setUltimaFechaModificacion(hourdateFormat.format(date));
			tela.setActualizadoPor(auth.getName());
			
			//Esto es de la imagen
			if (!imagenTela.isEmpty()){
				if ( tela.getFoto() != null && tela.getFoto().length() > 0) {

					UploadService.delete(tela.getFoto());
					cloudinary.uploader().destroy("telas/"+tela.getFoto().substring(0,tela.getFoto().length()-4), ObjectUtils.emptyMap());
				}
				
				String uniqueFilename = null;
				try {
					uniqueFilename = UploadService.copyTela(imagenTela);
					cloudinary.uploader().upload(UploadService.fileTela(uniqueFilename), ObjectUtils.asMap("public_id", "telas/"+uniqueFilename.substring(0,uniqueFilename.length()-4)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tela.setFoto(uniqueFilename);
			}
			 
			disenioTelaService.save(tela);
			
			//Esto de Forro y Tela
			if ( (forros != null) && (!forros.equals("")) ){
				String [] array = forros.split(",");
				for(int i= 0 ; i<array.length;i++) {
					DisenioTelaForro tf = new DisenioTelaForro();
					tf.setIdTela(tela.getIdTela());
					tf.setIdForro(Long.valueOf(array[i]));
					tf.setCreadoPor(auth.getName());
					tf.setActualizadoPor(auth.getName());
					tf.setFechaCreacion(hourdateFormat.format(date));
					tf.setUltimaFechaModificacion(hourdateFormat.format(date));
					TelaForroService.save(tf);	
				}
				
				//Accion que borra los forros que se eliminaron desde el html
				TelaForroService.deleteEliminados(array, tela.getIdTela());
			}
			
			//Esto de Tela material
			if ( (idtipomat != null) && (!idtipomat.equals("")) ){
				String [] colorMat = colormat.split(",");
				String [] codColor = codcolor.split(",");
				String [] idTipoMat = idtipomat.split(",");
				String [] pos = posicion.split(",");
				for(int i= 0 ; i<idTipoMat.length;i++) {
					DisenioMaterialTela tm = new DisenioMaterialTela();
					tm.setIdTela(tela.getIdTela());
					tm.setColor(colorMat[i]);
					tm.setCodigocolor(codColor[i]);
					tm.setIdTipoMaterial(idTipoMat[i]);
					tm.setPosicion(pos[i]);
					tm.setCreadoPor(auth.getName());
					tm.setActualizadoPor(auth.getName());
					tm.setFechaCreacion(hourdateFormat.format(date));
					tm.setUltimaFechaModificacion(hourdateFormat.format(date));
					MaterialService.save(tm);
				}
				//Accion para eliminar los mismos que se eliminaron del html si es que paso
				MaterialService.deleteEliminados(idTipoMat, colorMat, codColor, tela.getIdTela());
			}

			
			//Esto es de Composicion Tela
			if ( composicion.length()>1) {
				String [] vect = composicion.split(",");
				String [] vect2 = idComposicion.split(",");
				for(int i= 0 ; i<vect.length -1;i++) {
					DisenioFamiliaComposicionTela fc = new DisenioFamiliaComposicionTela();
					fc.setIdFamiliaComposicion(Long.valueOf(vect2[i]));
					fc.setIdTela(tela.getIdTela());
					fc.setCreadoPor(auth.getName());
					fc.setActualizadoPor("null");
					fc.setFechaCreacion(hourdateFormat.format(date));
					fc.setUltimaFechaModificacion(hourdateFormat.format(date));;
					fc.setComposicion(vect[i]);
					ComposicionTelaService.save(fc);
				}
				//Accion para borrar las composiciones que se borraron desde el html
				ComposicionTelaService.deleteBorrados(vect2, tela.getIdTela());
			}
			
			// prendas de muchos a muchos 
			//disenioTelaService.borrarTelaPrenda(tela.getIdTela());
			if ( id_prendas.length()>1) {
				String [] vect = id_prendas.split(",");
				for(int i= 0 ; i<vect.length -1;i++) {
					DisenioTelaPrenda tp = new DisenioTelaPrenda();
					tp.setIdPrenda(Long.valueOf(vect[i]));
					tp.setIdTela(tela.getIdTela());
					tp.setCreadoPor(auth.getName());
					tp.setActualizadoPor(auth.getName());
					tp.setFechaCreacion(hourdateFormat.format(date));
					tp.setUltimaFechaModificacion(hourdateFormat.format(date));
					disenioTelaService.saveTelaPrenda(tp);
				}
				//Parte para eliminar las que se eliminaron del HTML
				disenioTelaService.deleteBorrados(vect, tela.getIdTela());
			}
			
			redirectAttrs
            .addFlashAttribute("title", "Tela editada correctamente")
            .addFlashAttribute("icon", "success");
			
		}
		
		return "redirect:materiales";
	}
	
	@GetMapping("editar-tela/{id}")
	public String editar(@PathVariable(value="id") Long id , Model model) {
		model.addAttribute("form", "tela");
		DisenioTela tela ;
		model.addAttribute("editar", "true");
		tela= disenioTelaService.findOne(id);
		// Comienza erik
				model.addAttribute("lisFam",disenioTelaService.findAllFamilaComposicion());
				List<DisenioLookup> listLookupsMed = disenioMaterialService.findListaLookupMed();
				model.addAttribute("listLookupsMed", listLookupsMed);
				model.addAttribute("tablemat", MaterialService.findAllById(id));
		//Consulta de las composiciones
		model.addAttribute("lisCom",disenioTelaService.findAllComposicion());//Composiciones disponibles
		model.addAttribute("listTelaComposicon", disenioTelaService.ComposicionTelaMN(id));//Composiciones seleccionadas

		model.addAttribute("listForroSelec", disenioTelaService.ForrosSeleccionados(id));
		model.addAttribute("listForro",forroService.ForrosSelect(id)); 
		model.addAttribute("colores", lookupService.findByTipoLookup("Color"));
		// Consulta para telas auxiliares (telas autorizadas)
		model.addAttribute("listTela", disenioTelaService.TelasAutorizadas());//telas autorizadas		
		// prendas
		model.addAttribute("listPrendas", disenioTelaService.findAllPrenda());// prendas autorizadas
		model.addAttribute("listVistaTelaPrenda", disenioTelaService.VistaTelaPrenda(id));// telas seleccionadas
		model.addAttribute("EstatusCalidadTela", disenioTelaService.EstatusCalidadTela(id));
		model.addAttribute("tela", tela);
		model.addAttribute("proveedor", ProveedorSerivice.Proveedores());
		return"agregar-material";   
		
		
	}
	
	
	
	
	@GetMapping(value = "/uploads/telas/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = UploadService.loadTela(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@PostMapping("tela-sustituta")
	public String tela_sustituta(DisenioTela tela,RedirectAttributes redirectAttrs) throws Exception {
		System.out.println("jalale"+tela.getIdTela());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DisenioTela tela2 =new DisenioTela();
		tela2=disenioTelaService.findOne(tela.getIdTela());
		tela2.setAuxiliar1(tela.getAuxiliar1());
		tela2.setAuxiliar2(tela.getAuxiliar2());
		tela2.setAuxiliar3(tela.getAuxiliar3());
		tela2.setActualizadoPor(auth.getName());
		tela2.setUltimaFechaModificacion(hourdateFormat.format(date));
		disenioTelaService.save(tela2);
		redirectAttrs
		.addFlashAttribute("title", "Tela editada correctamente")
		.addFlashAttribute("icon", "success");
		return("redirect:materiales");
	}
	
	
	//Metodo que da de alta una tela
	@GetMapping("dar-alta-tela/{id}") 
	public String darAltaMaterial(@PathVariable("id") Long idTela, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DisenioTela tela = disenioTelaService.findOne(idTela);
		tela.setUltimaFechaModificacion(hourdateFormat.format(date));
		tela.setActualizadoPor(auth.getName());
		tela.setEstatus("1");
		
		disenioTelaService.save(tela);
		redirectAttrs
        .addFlashAttribute("title", "Tela dada de alta correctamente")
        .addFlashAttribute("icon", "success");
		  return "redirect:/materiales";
	}
	
	//Metodo que da de baja una Tela
	@GetMapping("delete-tela/{id}") 
	public String deleteMaterial(@PathVariable("id") Long idTela, RedirectAttributes redirectAttrs) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DisenioTela tela = disenioTelaService.findOne(idTela);
		tela.setUltimaFechaModificacion(hourdateFormat.format(date));
		tela.setActualizadoPor(auth.getName());
		tela.setEstatus("0");
		
		disenioTelaService.save(tela);
		redirectAttrs
        .addFlashAttribute("title", "Tela dada de baja correctamente")
        .addFlashAttribute("icon", "success");
		  return "redirect:/materiales";
	}
}
