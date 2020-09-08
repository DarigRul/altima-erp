package com.altima.springboot.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.util.FileSystemUtils;
import org.springframework.validation.BindingResult;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
/*MIAS LIBRIAS*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;
import com.altima.springboot.app.models.entity.ComercialClienteFactura;
import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.service.AlmacenamientoArchivoService;
import com.altima.springboot.app.models.service.ComercialClienteEmpleadoService;
import com.altima.springboot.app.models.service.IComercialClienteFacturaService;
import com.altima.springboot.app.models.service.IComercialClienteSucursalService;
import com.altima.springboot.app.models.service.PropiedadesAlmacenamientoService;
import com.altima.springboot.app.repository.ComercialClienteEmpleadoRepository;
import com.altima.springboot.app.repository.ComercialClienteFacturacionRepository;
import com.altima.springboot.app.repository.ComercialClienteSucursalRepository;

@EnableConfigurationProperties(PropiedadesAlmacenamientoService.class)
@Controller
public class AgregarEmpleadoEmpresaController {

	private String fileLocation;
	
	private final AlmacenamientoArchivoService almacenamientoArchivoService;

	@Autowired
	private ComercialClienteEmpleadoService cargaclienteempleadoservice;

	@Autowired
	private ComercialClienteEmpleadoRepository comercialClienteEmpleadoRepository;

	@Autowired
	private IComercialClienteSucursalService   icomercialclientesucursalservice;
	
	@Autowired
	private   IComercialClienteFacturaService  icomercialclientefacturaservice;

	
	

	
	private static final String template = "Hello, %s!";

	@Autowired
	public AgregarEmpleadoEmpresaController(AlmacenamientoArchivoService almacenamientoArchivoService,
			ComercialClienteEmpleadoRepository comercialClienteEmpleadoRepository) {
		this.almacenamientoArchivoService = almacenamientoArchivoService;
		this.comercialClienteEmpleadoRepository = comercialClienteEmpleadoRepository;
		
	}

	@RequestMapping(value = "/user/addUser", method = RequestMethod.GET)
	@ResponseBody
	public String addUser() {

		return "{\"success\":1}";
	}

	
	@RequestMapping(value="/registroempleadonombre/{idPedido}/{idcliente}", method = RequestMethod.POST)
	public String ingresarempleado(@PathVariable(value="idPedido") Long idPedido,
			@PathVariable(value = "idcliente") Long idcliente,
			@RequestParam("coorRegistroempleado") String coorRegistroempleado,
			@RequestParam("coorSucursalRegistro") String coorSucursalRegistro,
			@RequestParam("coorRazonsocialRegistro") String coorRazonsocialRegistro,
			ModelMap map,HttpServletRequest request) {
			ComercialClienteEmpleado ComercialClienteEmpleadoobj;
			System.out.println("HOLAAAAA "+ idcliente);
			System.out.println("este es el controller a modificar ");
			
			int count= 0;
			
			count = cargaclienteempleadoservice.countdeempleados(idPedido);
			
			ComercialClienteEmpleadoobj = new ComercialClienteEmpleado();
			ComercialClienteEmpleadoobj.setNombre_empleado(coorRegistroempleado);
			ComercialClienteEmpleadoobj.setIdPedidoInformacion(idPedido);
			ComercialClienteEmpleadoobj.setIdClienteSucursal(Long.parseLong(coorSucursalRegistro));
			ComercialClienteEmpleadoobj.setIdClienteFactura(Long.parseLong(coorRazonsocialRegistro));
			ComercialClienteEmpleadoobj.setCreadoPor("Shinigami loko lokoteeee");
			ComercialClienteEmpleadoobj.setActualizadoPor("Yaz");
			ComercialClienteEmpleadoobj.setIdText("EMP"+ (10000+count));
			comercialClienteEmpleadoRepository.save(ComercialClienteEmpleadoobj);
			
			map.put("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(idPedido));
			map.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
			map.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
		
		return "agregar-empleado-empresa";
	}
	
	
	
	
	
	@RequestMapping(value = "/stockfabrica/{idPedido}/{idcliente}", method = RequestMethod.POST)
	public String stock(@PathVariable(value = "idPedido") Long idpedido,
			@PathVariable(value = "idcliente") Long idcliente,
			@RequestParam("coorSPFStock") Integer coorSPFStock,
			@RequestParam("coorSucursalStock") String coorSucursalStock,
			@RequestParam("coorRazonsocialStock") String coorRazonsocialStock, ModelMap map,
			HttpServletRequest request) {
		ComercialClienteEmpleado ComercialClienteEmpleadoobj;
		int contador = cargaclienteempleadoservice.obtenerContador(idpedido);
		System.out.println("Valor del contador "+contador);
		
	
		for (int i =0; i <coorSPFStock; i++) {
			int count= 0;
			
			count = cargaclienteempleadoservice.countdeempleados(idpedido);
			 
			
			ComercialClienteEmpleadoobj = new ComercialClienteEmpleado();
			ComercialClienteEmpleadoobj.setIdText("EMP"+ (10000+count));
			/*ComercialClienteEmpleadoobj.setNombre_empleado(coorSucursalStock);*/
			ComercialClienteEmpleadoobj.setNombre_empleado("SPF"+(contador+(1+i)));	
			ComercialClienteEmpleadoobj.setIdPedidoInformacion(idpedido);
			ComercialClienteEmpleadoobj.setIdClienteSucursal(Long.parseLong(coorSucursalStock));
			ComercialClienteEmpleadoobj.setIdClienteFactura(Long.parseLong(coorRazonsocialStock));
			ComercialClienteEmpleadoobj.setCreadoPor("Shinigami");
			ComercialClienteEmpleadoobj.setActualizadoPor("Yaz");
			comercialClienteEmpleadoRepository.save(ComercialClienteEmpleadoobj);
			  
			  
		}

		map.put("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(idpedido));
		map.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
		map.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));

		return "agregar-empleado-empresa";
	}

	@RequestMapping(value = "/subirlista", method = RequestMethod.POST)
	public String ListaEmpleados(ModelMap map) {
		map.put("empleados", comercialClienteEmpleadoRepository.findAll());
		return "agregar-empleado-empresa";
	}
	 
	@RequestMapping(value = "/cargarregistroempleado/{idPedido}/{idcliente}", method = RequestMethod.POST)
	public String GuardarCargaEmpleado(@PathVariable(value = "idPedido") Long idpedido,
			@PathVariable(value = "idcliente") Long idcliente,
			Model model, ModelMap mp, @RequestParam(name="idText[]",required = false) String[] idText,HttpServletRequest request,
			@RequestParam(name="idPedidoInformacion[]",required = false) Long[] idPedidoInformacion,
			@RequestParam(name="nombreEmpleado[]",required = false) String[] nombreEmpleado,
			@RequestParam(name="idClienteFactura[]",required = false) Long[] idClienteFactura,
			@RequestParam(name="idClienteSucursal[]",required = false) Long[] idClienteSucursal,
			@RequestParam(name="creadoPor[]",required = false) String[] creadoPor,
			@RequestParam(name="actualizadoPor[]",required = false) String[] actualizadoPor
			) {
		
		
		System.out.println("id pedido " + idpedido);
		for (int i = 0; i < idText.length; i++) {
			ComercialClienteEmpleado empleado = new ComercialClienteEmpleado();
			System.out.println("nombre empleado "+nombreEmpleado[0]);
			empleado.setIdText(idText[i]);
			empleado.setNombre_empleado(nombreEmpleado[i]);
			empleado.setIdPedidoInformacion(idPedidoInformacion[i]);
			empleado.setIdClienteFactura(idClienteFactura[i]);
			empleado.setCreadoPor(creadoPor[i]);
			empleado.setActualizadoPor(actualizadoPor[i]);
			comercialClienteEmpleadoRepository.save(empleado);
		}

		mp.put("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(idpedido));
		mp.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
		mp.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
		/* mp.put("isPreviewView", "true"); */

		// Listaempleado.clear();

		return "agregar-empleado-empresa";
	}

	@GetMapping("/empleado/{idPedido}/{idcliente}")
	public String listEmpleado(@PathVariable(value = "idPedido") Long idpedido,
			@PathVariable(value = "idcliente") Long idcliente,ModelMap mp) {
		mp.put("empleadosEmpresa", comercialClienteEmpleadoRepository.findAll());
		mp.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
		mp.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
		return "agregar-empleado-empresa";
	}

	@PostMapping("/guardar-empleado-empresa/{idPedido}/{idcliente}")
	public String handleFileUpload(@RequestParam(value = "archivoss", required = false) MultipartFile file,
			@RequestParam(value = "get_factura_id_modal", required = false) Long id_factura,
			@RequestParam(value = "get_sucursal_id_modal", required = false) Long id_sucursal,
			RedirectAttributes redirectAttributes, ModelMap mp, @PathVariable(value = "idPedido") Long id,
			@PathVariable(value = "idcliente") Long idcliente) {

		System.out.println("mi id" + id);
		try {
			List<ComercialClienteEmpleado> Listaempleado = new ArrayList<ComercialClienteEmpleado>();
			Listaempleado.clear();
			almacenamientoArchivoService.store(file);
			InputStream in = file.getInputStream();
			File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			fileLocation = path.substring(0, path.length() - 1) + "upload-dir/" + file.getOriginalFilename();
			System.out.println("ruta" + fileLocation);
			Workbook workbook = WorkbookFactory.create(new File(fileLocation));

			System.out.println("Hoja de trabajo con num" + workbook.getNumberOfSheets() + " Hojas : ");

			Sheet sheet = workbook.getSheetAt(0);

			DataFormatter dataFormatter = new DataFormatter();

			int rowCount = 0;
			Iterator<Row> rowIter = sheet.rowIterator();
			ComercialClienteEmpleado ComercialClienteEmpleadoobj = new ComercialClienteEmpleado();
			ArrayList<String> datos = new ArrayList<String>();
			System.out.println(sheet.getPhysicalNumberOfRows());
			
           int count;
			
			
			while (rowIter.hasNext()) {
				System.out.println("incrementando "+ rowCount);
				Iterator cellIter = ((Row) rowIter.next()).cellIterator();
				rowCount++;

				if (rowCount > 1 && rowCount<sheet.getPhysicalNumberOfRows()) {
					ComercialClienteEmpleadoobj = new ComercialClienteEmpleado();
					count = cargaclienteempleadoservice.countdeempleados(id);
					while (cellIter.hasNext()) {
						Cell cell = (Cell) cellIter.next();

						datos.add(dataFormatter.formatCellValue(cell));
						

					}
					
					

					ComercialClienteEmpleadoobj.setNombre_empleado(datos.get(0));

					ComercialClienteEmpleadoobj.setIdClienteFactura((id_factura));
					ComercialClienteEmpleadoobj.setIdClienteSucursal(id_sucursal);
					ComercialClienteEmpleadoobj.setIdPedidoInformacion(id);
					ComercialClienteEmpleadoobj.setCreadoPor("Shinigami");
					ComercialClienteEmpleadoobj.setActualizadoPor("Yaz");
				
				
					ComercialClienteEmpleadoobj.setIdText("EMP"+ (10000+count+rowCount -2));

					Listaempleado.add(ComercialClienteEmpleadoobj);
					datos.clear();

				}

			}

			mp.put("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
			mp.put("empleadosEmpresa", Listaempleado);
			mp.put("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
			mp.put("isPreviewView", "true");
			mp.put("idPedido", id);

			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("message",
				"archivo subido correctamente " + file.getOriginalFilename() + "!");

		return "agregar-empleado-empresa";
	}

	@RequestMapping(value = "/eliminar/{idPedido}/{idcliente}", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, Model model, @PathVariable(value = "idPedido") Long idpedido,
			@PathVariable(value = "idcliente") Long idcliente) {
		
		String idtext;
		String flag;

		try {
			for (String id : request.getParameterValues("getdeletenombre_empleado")) {
				System.out.println(id);
				System.out.println("entre al elinar aqui es donde debes talachear");
				ComercialClienteEmpleado objeto = cargaclienteempleadoservice.findOne(Long.parseLong(id));
				  idtext= objeto.getIdText();
				 System.out.println("aqui esta id text que se va aborrar   " + idtext );
				 flag = cargaclienteempleadoservice.findMaxByidText(idpedido);
				 
				 System.out.println("aqui esta idText variable a borrar   " + idtext );
				 
				 System.out.println("aqui esta la flag  " + flag );
				 
				 if (idtext.equals(flag)) {
					 
					 System.out.println("entre al if de la flag perro");
						comercialClienteEmpleadoRepository.delete(comercialClienteEmpleadoRepository.findById(Long.parseLong(id)).orElse(null));

				} else {
					 System.out.println("entre al elseeeeeeeeeeeeee de la flag perro");
					
					comercialClienteEmpleadoRepository.delete(comercialClienteEmpleadoRepository.findById(Long.parseLong(id)).orElse(null));
					String max = cargaclienteempleadoservice.max(idpedido);
					System.out.println("aqui esta el id maximo"  + max);
					
					
					ComercialClienteEmpleado objeto2 = (ComercialClienteEmpleado) cargaclienteempleadoservice.findByidText(max, idpedido);
					System.out.println("aqui esta el ultimo idtext de ese pedido   "+ objeto2.getIdText());
					objeto2.setIdText(idtext);
					comercialClienteEmpleadoRepository.save(objeto2);
					

				}
				
			}

			model.addAttribute("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(idpedido));
			model.addAttribute("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
			model.addAttribute("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));

			return "agregar-empleado-empresa";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("empleadosEmpresa", comercialClienteEmpleadoRepository.findAll());
			model.addAttribute("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
			model.addAttribute("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));
			e.printStackTrace();
			return "redirect:/agregar-empleado-empresa/"+idpedido+"/"+idcliente;
		}

	}

	@RequestMapping(value = "/editarNombredgp/{idPedido}/{idcliente}", method = RequestMethod.POST)
	private String editarNombredgp(@PathVariable(value = "idPedido") Long idpedido,
			@PathVariable(value = "idcliente") Long idcliente,
			Model model,
			@RequestParam(value = "get_nombre_cambiar_dgp", required = true) String nuevonombre,
			@RequestParam(value = "get_sucursal_id_modal_nombre", required = true) Long get_sucursal_id_modal_nombre,
			@RequestParam(value = "get_factura_id_modal_nombre", required = true) Long get_factura_id_modal_nombre,
			@RequestParam(value = "get_id_cambiar_dgp", required = true) Long idempleado,
			ComercialClienteEmpleado ComercialClienteEmpleadoobj) {
		System.out.println("iduno"+get_sucursal_id_modal_nombre);
		System.out.println("iddos"+ get_factura_id_modal_nombre);
		System.out.println("idtres"+ idempleado);
		ComercialClienteEmpleado objetoeditarempleado = cargaclienteempleadoservice.findUno(idempleado);

		System.out.println("muestraa" + objetoeditarempleado.getNombre_empleado());
		objetoeditarempleado.setNombre_empleado(nuevonombre);
		objetoeditarempleado.setIdClienteFactura(get_factura_id_modal_nombre);
		objetoeditarempleado.setIdClienteSucursal(get_sucursal_id_modal_nombre);
		comercialClienteEmpleadoRepository.save(objetoeditarempleado);
			
			model.addAttribute("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(idpedido));
		
		model.addAttribute("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
		model.addAttribute("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));

		return "agregar-empleado-empresa";
	}

	@RequestMapping(value = "/editarsucursalyrazonsocial/{idPedido}/{idcliente}", method = RequestMethod.POST)
	public String editarSucursalyRazonso(@PathVariable(value = "idPedido") Long idpedido, 
			@PathVariable(value = "idcliente") Long idcliente,
			Model model,
			@RequestParam(value = "razonfacturamodal", required = true) Long razonfacturamodal,
			@RequestParam(value = "sucursalmodel", required = true) Long sucursalmodel,
			@RequestParam(value = "get_id_obterneridsempleado", required = true) String get_id_obterneridsempleado) {

		System.out.println("id pedido " + idpedido);
		System.out.println("id razon social " + razonfacturamodal);
		System.out.println("id sucursal " + sucursalmodel);
		System.out.println("lista empleados " + get_id_obterneridsempleado);

		String[] arrOfStr = get_id_obterneridsempleado.split(",");

		for (String a : arrOfStr) {
			if (Long.parseLong(a) > 0) {
				System.out.println(a);

				ComercialClienteEmpleado objetoeditarempleado = cargaclienteempleadoservice.findUno(Long.parseLong(a));
				objetoeditarempleado.setIdClienteFactura(razonfacturamodal);
				objetoeditarempleado.setIdClienteSucursal(sucursalmodel);
				comercialClienteEmpleadoRepository.save(objetoeditarempleado);

			}
		}
		model.addAttribute("empleadosEmpresa", cargaclienteempleadoservice.findAllEmpleadosEmpresa(idpedido));
		model.addAttribute("getlistSucursal", icomercialclientesucursalservice.findListaSucrusalesCliente(idcliente));
		model.addAttribute("getlistfactura", icomercialclientefacturaservice.findListaFacturaCliente(idcliente));

		return "agregar-empleado-empresa";
	}

}
