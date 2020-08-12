package com.altima.springboot.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.ComprasProveedorContacto;
import com.altima.springboot.app.models.entity.ComprasProveedorCredito;
import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.models.service.IComprasProveedorContactoService;
import com.altima.springboot.app.models.service.IComprasProveedorCreditoService;
import com.altima.springboot.app.models.service.IComprasProveedorService;

@RestController
public class ComprasProveedoresRestController {
	@Autowired
	private IComprasProveedorService proveedorService;
	@Autowired
	private IComprasProveedorContactoService contactoService;
	@Autowired
	private IComprasProveedorCreditoService creditoService;
	
	
	
	
	@RequestMapping(value="/GuardarDatosGeneralesProveedor", method=RequestMethod.POST)
	private int guardarDatosGenerales(@RequestParam(name="lista")String lista){
		System.out.println(lista);
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			JSONObject datos = new JSONObject(lista); 
			ComprasProveedores proveedor = new ComprasProveedores();
			
			proveedor.setIdText("PROV");
			proveedor.setNombreProveedor(datos.getString("nombreProveedor"));
			proveedor.setTipo(datos.getString("tipo"));
			proveedor.setCalle(datos.getString("calle"));
			proveedor.setNumeroExterior(datos.getString("numeroExterior"));
			proveedor.setNumeroInterior(datos.getString("numeroInterior"));
			proveedor.setColonia(datos.getString("colonia"));
			proveedor.setPoblacion(datos.getString("poblacion"));
			proveedor.setCodigoPostal(datos.getString("codigoPostal"));
			proveedor.setMunicipio(datos.getString("municipio"));
			proveedor.setEstado(datos.getString("estado"));
			proveedor.setPais(datos.getString("pais"));
			proveedor.setClasificacion(datos.getString("clasificacion"));
			proveedor.setZona(datos.getString("zona"));
			proveedor.setRfcProveedor(datos.getString("rfcProveedor"));
			proveedor.setCurpProveedor(datos.getString("curpProveedor"));
			proveedor.setTelefonoProveedor(datos.getString("telefonoProveedor"));
			proveedor.setCorreoProveedor(datos.getString("correo"));
			proveedor.setPaginaWebProveedor(datos.getString("paginaWebProveedor"));
			proveedor.setCreadoPor(auth.getName());
			proveedor.setActualizadoPor(auth.getName());
			proveedor.setFechaCreacion(dtf.format(now));
			proveedor.setUltimaFechaModificacion(dtf.format(now));
			proveedor.setEstatus("1");
			proveedorService.save(proveedor);
			proveedor.setIdText("PROV"+(10000+proveedor.getIdProveedor()));
			
			return Integer.parseInt(proveedor.getIdProveedor().toString());
		}
		catch(Exception e) {
			return -1;
		}
		finally {
			System.out.println("fin de proceso GuardarDatosGeneralesProveedor");
		}
		
	}

	@RequestMapping(value="/EditarDatosGeneralesProveedor", method=RequestMethod.POST)
	private int editarDatosGenerales(@RequestParam(name="lista")String lista){
		System.out.println(lista);
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			JSONObject datos = new JSONObject(lista); 
			ComprasProveedores proveedor = proveedorService.findOne(Long.parseLong(datos.get("idProveedor").toString()));
			
			proveedor.setNombreProveedor(datos.getString("nombreProveedor"));
			proveedor.setTipo(datos.getString("tipo"));
			proveedor.setCalle(datos.getString("calle"));
			proveedor.setNumeroExterior(datos.getString("numeroExterior"));
			proveedor.setNumeroInterior(datos.getString("numeroInterior"));
			proveedor.setColonia(datos.getString("colonia"));
			proveedor.setPoblacion(datos.getString("poblacion"));
			proveedor.setCodigoPostal(datos.getString("codigoPostal"));
			proveedor.setMunicipio(datos.getString("municipio"));
			proveedor.setEstado(datos.getString("estado"));
			proveedor.setPais(datos.getString("pais"));
			proveedor.setClasificacion(datos.getString("clasificacion"));
			proveedor.setZona(datos.getString("zona"));
			proveedor.setRfcProveedor(datos.getString("rfcProveedor"));
			proveedor.setCurpProveedor(datos.getString("curpProveedor"));
			proveedor.setTelefonoProveedor(datos.getString("telefonoProveedor"));
			proveedor.setCorreoProveedor(datos.getString("correo"));
			proveedor.setPaginaWebProveedor(datos.getString("paginaWebProveedor"));
			proveedor.setActualizadoPor(auth.getName());
			proveedor.setUltimaFechaModificacion(dtf.format(now));
			proveedor.setEstatus("1");
			proveedorService.save(proveedor);
			
			return 1;
		}
		catch(Exception e) {
			return -1;
		}
		finally {
			System.out.println("fin de proceso EditarDatosGeneralesProveedor");
		}
	}
	
	@RequestMapping(value="/GuardarContactosProveedor", method=RequestMethod.POST)
	private List<String> guardarContactoProveedor(@RequestParam(name="lista")String lista,
										  @RequestParam(name="idProveedor", required=true)Long idProveedor){
		
		try {
			System.out.println(lista);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			List<String> ids = new ArrayList<>();
			JSONArray datos = new JSONArray(lista);
			
			for (int i=0;i<datos.length();i++) {
			JSONObject dato = datos.getJSONObject(i); 
			ComprasProveedorContacto contactoProveedor = new ComprasProveedorContacto();
			
			contactoProveedor.setIdProveedor(idProveedor);
			contactoProveedor.setNombreContacto(dato.getString("nombreContacto"));
			contactoProveedor.setCargoContacto(dato.getString("cargoContacto"));
			contactoProveedor.setCorreoContacto(dato.getString("correoContacto"));
			contactoProveedor.setTelefonoContacto(dato.getString("telefonoContacto"));
			contactoProveedor.setExtensionContacto(dato.getString("extensionContacto"));
			contactoProveedor.setCreadoPor(auth.getName());
			contactoProveedor.setActualizadoPor(auth.getName());
			contactoProveedor.setFechaCreacion(dtf.format(now));
			contactoProveedor.setUltimaFechaModificacion(dtf.format(now));
			contactoProveedor.setEstatus("1");
			
			contactoService.save(contactoProveedor);
			
			ids.add(contactoProveedor.getIdProveedorContacto().toString());
			}
			
			System.out.println(ids);
			return ids;
		}
		catch(Exception e) {
			
			System.out.println(e);
			List<String> ids = new ArrayList<>();
			ids.add("falso");
			System.out.println(ids);
			return ids;
		}
		finally {
			System.out.println("fin de proceso GuardarContactosProveedor");
		}
	}
	
	
	@RequestMapping(value="/EditarContactosProveedor", method=RequestMethod.POST)
	private List<String> editarContactoProveedor(@RequestParam(name="lista")String lista,
										  @RequestParam(name="idProveedor", required=true)Long idProveedor){
		
		System.out.println(lista );
		System.out.println(idProveedor);
		try {
			int contadorsillo=0;
			JSONArray datos = new JSONArray(lista);
			List<ComprasProveedorContacto> AllPrendas = contactoService.findAllByProveedor(idProveedor);
			List<ComprasProveedorContacto> AllPrendasToDelete = new ArrayList<ComprasProveedorContacto>();
			
			for (ComprasProveedorContacto a: AllPrendas) {
				for (int i=0;i<datos.length();i++) {
					JSONObject dato = datos.getJSONObject(i);
					if(dato.getString("idContactoProveedor").equals("")) {
						System.out.println("no aplica a nulos");
					}
					else {
						if(Integer.parseInt(dato.getString("idContactoProveedor"))==Integer.parseInt(a.getIdProveedorContacto().toString())) {
							contadorsillo=1;
						}
					}
				}
				if(contadorsillo==0){
					AllPrendasToDelete.add(contactoService.findOne(Long.parseLong(a.getIdProveedorContacto().toString())));
				}
				else {
					contadorsillo=0;
				}
			}
			
			System.out.println(AllPrendasToDelete.size()+" son los registros a eliminar");
			contactoService.deleteInexistentes(AllPrendasToDelete);
			
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			List<String> ids = new ArrayList<>();
			
			for (int i=0;i<datos.length();i++) {
			JSONObject dato = datos.getJSONObject(i); 
			if(dato.get("idContactoProveedor").equals("")) {
				System.out.println("registro nuevo");
				ComprasProveedorContacto proveedorContacto = new ComprasProveedorContacto();
				
				proveedorContacto.setIdProveedor(idProveedor);
				proveedorContacto.setNombreContacto(dato.getString("nombreContacto"));
				proveedorContacto.setCargoContacto(dato.getString("cargoContacto"));
				proveedorContacto.setCorreoContacto(dato.getString("correoContacto"));
				proveedorContacto.setTelefonoContacto(dato.getString("telefonoContacto"));
				proveedorContacto.setExtensionContacto(dato.getString("extensionContacto"));
				proveedorContacto.setCreadoPor(auth.getName());
				proveedorContacto.setActualizadoPor(auth.getName());
				proveedorContacto.setFechaCreacion(dtf.format(now));
				proveedorContacto.setUltimaFechaModificacion(dtf.format(now));
				proveedorContacto.setEstatus("1");
				
				contactoService.save(proveedorContacto);
				dato.put("idContactoProveedor", proveedorContacto.getIdProveedorContacto().toString());
				ids.add(dato.getString("idContactoProveedor"));
			}
			else {
				System.out.println("dato a editar");
				ComprasProveedorContacto proveedorContacto = contactoService.findOne(dato.getLong("idContactoProveedor"));
				
				proveedorContacto.setIdProveedor(idProveedor);
				proveedorContacto.setNombreContacto(dato.getString("nombreContacto"));
				proveedorContacto.setCargoContacto(dato.getString("cargoContacto"));
				proveedorContacto.setCorreoContacto(dato.getString("correoContacto"));
				proveedorContacto.setTelefonoContacto(dato.getString("telefonoContacto"));
				proveedorContacto.setExtensionContacto(dato.getString("extensionContacto"));
				proveedorContacto.setActualizadoPor(auth.getName());
				proveedorContacto.setUltimaFechaModificacion(dtf.format(now));
				
				contactoService.save(proveedorContacto);
				ids.add(dato.getString("idContactoProveedor"));
			}
			}
			System.out.println(ids+" son los registros a poner en los inputs");
			
			return ids;
		}
		catch(Exception e) {
			System.out.println(e);
			List<String> ids = new ArrayList<>();
			ids.add("falso");
			return ids;
		}
		finally {
			System.out.println("fin de proceso EditarContactosProveedor");
		}
		}
	
	
	@RequestMapping(value="/GuardarCreditoProveedor", method=RequestMethod.POST)
	private int guardarcreditoProveedor(@RequestParam(name="lista")String lista,
										  @RequestParam(name="idProveedor")Long idProveedor){
		System.out.println(lista);
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			JSONObject dato = new JSONObject(lista);
			ComprasProveedorCredito credito = new ComprasProveedorCredito();
			
			
			if(dato.getString("manejoCredito").equals("0")) {
				System.out.println("ignora los datos del credito");
				credito.setDiasCredito("0");
				credito.setLimiteCredito("0");
				credito.setSaldo("0");
			}
			else {
				System.out.println("esta entrando aqui?");
				credito.setDiasCredito(dato.getString("diasCredito"));
				credito.setLimiteCredito(dato.getString("limiteCredito"));
				credito.setSaldo(dato.getString("saldoCredito"));
			}
			credito.setIdProveedor(idProveedor);
			credito.setManejoCredito(dato.getString("manejoCredito"));
			credito.setFormaPago(dato.getString("formaPago"));
			credito.setObservaciones(dato.getString("observaciones"));
			credito.setCreadoPor(auth.getName());
			credito.setActualizadoPor(auth.getName());
			credito.setFechaCreacion(dtf.format(now));
			credito.setUltimaFechaModificacion(dtf.format(now));
			credito.setEstatus("1");
			
			creditoService.save(credito);
			
			
			return 1;
		}
		catch(Exception e) {
			System.out.println(e);
			return 2;
		}
		finally {
			System.out.println("fin de proceso GuardarContactosProveedor");
		}
	}
	
	@RequestMapping(value="/EditarCreditoProveedor", method=RequestMethod.POST)
	private int editarCreditoProveedor(@RequestParam(name="lista")String lista,
										  @RequestParam(name="idProveedor")Long idProveedor){
		
		System.out.println(lista );
		System.out.println(idProveedor);
		try {
			
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			JSONObject dato = new JSONObject(lista);
			ComprasProveedorCredito credito = creditoService.findByProveedor(idProveedor);
			
			if(dato.getString("manejoCredito").equals("0")) {
				System.out.println("ignora los datos del credito");
				credito.setDiasCredito("0");
				credito.setLimiteCredito("0");
				credito.setSaldo("0");
			}
			else {
				System.out.println("esta entrando aqui?");
				credito.setDiasCredito(dato.getString("diasCredito"));
				credito.setLimiteCredito(dato.getString("limiteCredito"));
				credito.setSaldo(dato.getString("saldoCredito"));
			}
			credito.setManejoCredito(dato.getString("manejoCredito"));
			credito.setFormaPago(dato.getString("formaPago"));
			credito.setObservaciones(dato.getString("observaciones"));
			credito.setActualizadoPor(auth.getName());
			credito.setUltimaFechaModificacion(dtf.format(now));
			credito.setEstatus("1");
			
			creditoService.save(credito);
			
			
			return 1;
		}
		catch(Exception e) {
			System.out.println(e);
			return 2;
		}
		finally {
			System.out.println("fin de proceso EditarContactosProveedor");
		}
		}
}
