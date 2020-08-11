package com.altima.springboot.app.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.altima.springboot.app.models.entity.ComercialPrendaBordado;
import com.altima.springboot.app.models.entity.DisenioPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialPrendaBordadoService;
import com.altima.springboot.app.models.service.IDisenioPrendaService;
import com.altima.springboot.app.models.service.IUploadService;

@CrossOrigin(origins = { "*" })
@Controller
public class DetallePreciosCargaPedidosController {

	// tome esta service para no generar mas, ya que este va relacionado con la
	// pantalla
	@Autowired
	private IComercialPrendaBordadoService bordadoService;

	@Autowired
	private IUploadService UploadService;

	@Autowired
	private IComercialCoordinadoService CoordinadoService;

	@Autowired
	private IDisenioPrendaService prendaService;

	@PreAuthorize("@authComponent.hasPermission(#id,{'pedido'})")
	@GetMapping("/detalle-de-precios/{id}")
	public String listPrecios(@PathVariable(value = "id") Long id, Model model) {

		List<Object[]> aux = bordadoService.findAllCoordinado(id);
		for (Object[] a : aux) {

			Long id_coor = Long.parseLong(a[0].toString());
			Float precio_bordado = Float.parseFloat(a[7].toString());
			Float precio_usar = Float.parseFloat(a[8].toString());
			Float monto = Float.parseFloat(a[10].toString());
			ComercialCoordinadoPrenda prenda = CoordinadoService.findOneCoorPrenda(id_coor);
			Float preciofinal = precio_bordado + precio_usar + monto;
			prenda.setPrecioFinal(Float.toString(preciofinal));
			CoordinadoService.saveCoorPrenda(prenda);
		}

		model.addAttribute("listCoor", bordadoService.findAllCoordinado(id));

		model.addAttribute("selectBordado", bordadoService.BordadosView(id));
		return "detalle-de-precios";
	}

	@RequestMapping(value = "/mostrar-bordados", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []> modelo(Long id) {
		System.out.println("I am a label");
		return  bordadoService.findAllDescipcion(id);
	}
	
	
	@RequestMapping(value = "/select-bordados", method = RequestMethod.GET)
	@ResponseBody
	public List<Object []>  select_bordados(Long id) {
		System.out.println("I am a select_bordados");
		return bordadoService.BordadosView(id);
	}

	
	
	
	@PostMapping("/guardar-bordado")
	public String guardar_bor( 
			@RequestParam("bordadoPrecio") String bordadoPrecio,
			@RequestParam("id_coor_prenda") Long id_coor_prenda,
			@RequestParam("idBordado") Long idBordado,
			@RequestParam(value="imagenTela") MultipartFile imagenTela ) throws Exception {
		
		System.out.println("I am a save" +id_coor_prenda );
		System.out.println("I am a save" +bordadoPrecio );
		System.out.println("I am a save" +imagenTela );
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialPrendaBordado bordado = new ComercialPrendaBordado();
		bordado.setIdCoordinadoPrenda( id_coor_prenda );
		bordado.setPrecioBordado(bordadoPrecio);
		bordado.setIdBordado(idBordado);
		
		if (!imagenTela.isEmpty()){
			if ( bordado.getArchivoBordado() != null && bordado.getArchivoBordado() .length() > 0) {

				UploadService.deleteBordado(bordado.getArchivoBordado());
			}
			
			String uniqueFilename = null;
			try {
				uniqueFilename = UploadService.copyBordado(imagenTela);
				bordado.setArchivoBordado(uniqueFilename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		bordado.setCreadoPor(auth.getName());
		bordado.setActualizadoPor(null);
		bordado.setFechaCreacion(hourdateFormat.format(date));
		bordado.setUltimaFechaModificacion(hourdateFormat.format(date));
		bordadoService.save(bordado);
		
		Float precioBordado =bordadoService.sumBordados(id_coor_prenda) ;
		Float precioPrenda = bordadoService.precio_coor_prenda(id_coor_prenda);
		ComercialCoordinadoPrenda  prenda 	=CoordinadoService.findOneCoorPrenda(id_coor_prenda);
		Float preciofinal= precioBordado+ Float.parseFloat(prenda.getMontoAdicional()) +  precioPrenda;
		prenda.setPrecioFinal( String.valueOf(preciofinal)  );
		
		prenda.setActualizadoPor(auth.getName());
		prenda.setUltimaFechaModificacion(hourdateFormat.format(date));
		CoordinadoService.saveCoorPrenda(prenda);
		return"agregar-material";   
	
	}
	
	
	@PostMapping("/eliminar-bordado")
	public String eliminar_bor( Long id)  {
		
		
		ComercialPrendaBordado bordado = bordadoService.findOne(id); 
		Long id_coor_prenda = bordado.getIdCoordinadoPrenda();
		bordadoService.delete(id);
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		
		Float precioBordado =bordadoService.sumBordados(id_coor_prenda) ;
		Float precioPrenda = bordadoService.precio_coor_prenda(id_coor_prenda);
		ComercialCoordinadoPrenda  prenda 	=CoordinadoService.findOneCoorPrenda(id_coor_prenda);
		Float preciofinal= precioBordado+ Float.parseFloat(prenda.getMontoAdicional()) +  precioPrenda;
		prenda.setPrecioFinal( String.valueOf(preciofinal)  );
		
		prenda.setActualizadoPor(auth.getName());
		prenda.setUltimaFechaModificacion(hourdateFormat.format(date));
		CoordinadoService.saveCoorPrenda(prenda);
		return"agregar-material";   
	
	}
	
	
	@PostMapping("/precio-final")
	public String precio_final( Long id, Float porcentaje, Float monto , Float preciof)  {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("I am  end "+id  );
		System.out.println("I am  end "+porcentaje  );
		System.out.println("I am  end "+monto  );
		System.out.println("I am  end "+preciof  );
		
		ComercialCoordinadoPrenda  prenda 	=CoordinadoService.findOneCoorPrenda(id);
		
		prenda.setAdicional(String.valueOf(porcentaje));
		prenda.setMontoAdicional(String.valueOf(monto));
		prenda.setPrecioFinal(String.valueOf(preciof));
		
		prenda.setActualizadoPor(auth.getName());
		prenda.setUltimaFechaModificacion(hourdateFormat.format(date));
		CoordinadoService.saveCoorPrenda(prenda);
		return"agregar-material";   
	
	}
	
	
	
	@PostMapping("/observacion-prenda")
	public String observacion( Long id,String observacion)  {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("I am  end "+id  );
		System.out.println("I am  end "+observacion  );
		
		ComercialCoordinadoPrenda  prenda 	=CoordinadoService.findOneCoorPrenda(id);
		
		prenda.setObservaciones(observacion);
		
		prenda.setActualizadoPor(auth.getName());
		prenda.setUltimaFechaModificacion(hourdateFormat.format(date));
		CoordinadoService.saveCoorPrenda(prenda);
		 
		return"agregar-material";  
	
	}
	

	@RequestMapping(value = "/precio-bordado", method = RequestMethod.GET)
	@ResponseBody
	public Float precio(Long id) {
		
		return  bordadoService.precioBordado(id);
	}
	
}
