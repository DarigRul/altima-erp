package com.altima.springboot.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.entity.ComercialPedidoInformacion;
import com.altima.springboot.app.models.entity.ComercialSolicitudServicioAlCliente;
import com.altima.springboot.app.models.entity.HrDireccion;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialClienteSucursalService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteAuxiliarVentasService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteCorridaService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteMaterialService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteSastreService;
import com.altima.springboot.app.models.service.IComercialSolicitudServicioAlClienteService;
import com.altima.springboot.app.models.service.IHrDireccionService;
import com.altima.springboot.app.models.service.IHrLookupService;

@Controller
public class ServicioClienteController {
	
	@Autowired
	private IComercialClienteService ClienteService;
	@Autowired
	private ICargaPedidoService cargaPedidoService;
	@Autowired 
	private IHrDireccionService direciconSercice;
	@Autowired
	private IComercialSolicitudServicioAlClienteService solicitudServicioClienteService;
	@Autowired
	private IComercialSolicitudServicioAlClienteSastreService solicitudServicioClienteSastreService;
	@Autowired
	private IComercialSolicitudServicioAlClienteAuxiliarVentasService solicitudServicioClienteAuxiliarVentasService;
	@Autowired
	private IComercialSolicitudServicioAlClienteMaterialService solicitudServicioClienteMaterialService;
	@Autowired
	private IComercialSolicitudServicioAlClienteCorridaService solicitudServicioClienteCorridaService;
	@Autowired
	private IHrLookupService hrLookupService;
	@Autowired
    private IComercialClienteSucursalService SucursalService;
	
	
    @Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_SERVICIOCLIENTE_LISTAR"})
    @GetMapping("/servicio-cliente")
    public String ServicioCliente(Model model)
    {
    	model.addAttribute("solicitudes", solicitudServicioClienteService.findAll());
        return"servicio-cliente";
    }
	
	
    @Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_SERVICIOCLIENTE_AGREGAR"})
    @GetMapping("/servicio-cliente-solicitud")
    public String ServicioClienteSol(Model model){
    	ComercialSolicitudServicioAlCliente solicitud =  new ComercialSolicitudServicioAlCliente();
    	model.addAttribute("solicitud", solicitud);
    	model.addAttribute("clientes", ClienteService.findAll(null));
    	ComercialCliente cliente = new ComercialCliente();
    	model.addAttribute("ElCliente", cliente);
    	model.addAttribute("DireccionDelCliente", "Direccion del cliente");
    	model.addAttribute("accion", "crear");
        return"servicio-cliente-solicitud";
    }
	
	
    @Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_SERVICIOCLIENTE_EDITAR"})
    @GetMapping("/servicio-cliente-editar-solicitud/{id}")
    public String ServicioClienteEditarSol(@PathVariable(name = "id") Long idSolicitud, Model model) throws ParseException{
    	ComercialSolicitudServicioAlCliente solicitud =solicitudServicioClienteService.findOne(idSolicitud);
    	model.addAttribute("solicitud", solicitud);
		model.addAttribute("clientes", ClienteService.findAll(null));
		String direccionCompleta;
		String telefono;
		if ( solicitud.getIdSucrsal().equals("0") ){
			ComercialCliente cliente = ClienteService.findOne(solicitud.getIdCliente());
    		HrDireccion direccion = direciconSercice.findOne(cliente.getIdDireccion());
    	 	direccionCompleta = direccion.getCalle() + ", #" + direccion.getNumeroExt() + ". Colonia: " + direccion.getColonia() + ". " + direccion.getMunicipio() + ", " + direccion.getEstado() + ". CP:" + direccion.getCodigoPostal();
			 telefono=cliente.getTelefono();
		}else{
			ComercialClienteSucursal sucursal =  SucursalService.findOne(Long.parseLong(solicitud.getIdSucrsal()));
			HrDireccion direccion = direciconSercice.findOne(sucursal.getIdDireccion());
    	 	direccionCompleta = direccion.getCalle() + ", #" + direccion.getNumeroExt() + ". Colonia: " + direccion.getColonia() + ". " + direccion.getMunicipio() + ", " + direccion.getEstado() + ". CP:" + direccion.getCodigoPostal();
			 telefono=sucursal.getTelefonoSucursal();

		}
    	
    	
    	//Selects
    	model.addAttribute("selectSastres", solicitudServicioClienteSastreService.devolverSelectSastre(idSolicitud));
    	model.addAttribute("selectAuxiliares", solicitudServicioClienteAuxiliarVentasService.devolverSelectAuxiliarVentas(idSolicitud));
    	//model.addAttribute("selectMateriales", solicitudServicioClienteService.devolverSelectMateriales(idSolicitud));
    	model.addAttribute("selectCorridas", solicitudServicioClienteCorridaService.devolverSelectCorridas(idSolicitud));
    	
    	//Objetos
    	model.addAttribute("sastres", solicitudServicioClienteSastreService.findBySolicitud(idSolicitud));
    	model.addAttribute("auxiliares", solicitudServicioClienteAuxiliarVentasService.findBySolicitud(idSolicitud));
    	model.addAttribute("materiales", solicitudServicioClienteMaterialService.findBySolicitud(idSolicitud));
    	model.addAttribute("corridas", solicitudServicioClienteCorridaService.findBySolicitud(idSolicitud));
		model.addAttribute("DireccionDelCliente", direccionCompleta);
		model.addAttribute("TelefonoDelCliente", telefono);
		model.addAttribute("pedidos", solicitudServicioClienteService.pedidosDeCliente(solicitud.getIdCliente()) );
		model.addAttribute("sucursal", solicitudServicioClienteService.direccionesSucursales(solicitud.getIdCliente()));
    	model.addAttribute("accion", "editar");
        return"servicio-cliente-solicitud";
    }
}