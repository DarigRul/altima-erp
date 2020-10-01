package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.altima.springboot.app.models.entity.ComercialAgentesVenta;
import com.altima.springboot.app.models.entity.ComercialLookup;
import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.HrLookup;
import com.altima.springboot.app.models.entity.HrPuesto;
import com.altima.springboot.app.models.service.IComercialAgentesVentaService;
import com.altima.springboot.app.models.service.IComercialLookupService;
import com.altima.springboot.app.models.service.IHrDepartamentoService;
import com.altima.springboot.app.models.service.IHrEmpleadoService;
import com.altima.springboot.app.models.service.IHrLookupService;
import com.altima.springboot.app.models.service.IHrPuestoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class ComercialCatalogosRestController {

    @Autowired
    IComercialLookupService comercialLookupService;

    @Autowired
    private IHrEmpleadoService empleadoService;
    
    @Autowired
    private IHrPuestoService puestoService;
    
    @Autowired
    private IHrDepartamentoService depaService;
    
    @Autowired
    private IHrLookupService lookupService;
    
    @Autowired
    private IComercialAgentesVentaService agentesVenta;
    
    @RequestMapping(value = "/puestoAgentes", method = RequestMethod.GET)
    public List<HrPuesto> puestoAgentes(@RequestParam(name="idDepartamento")Long idDepartamento){
    	
    	return puestoService.findAllPuestoByDepartamento(idDepartamento);
    }
    
    @RequestMapping(value = "/departamentoAgentes", method = RequestMethod.GET)
    public List<HrDepartamento> departamentoAgentes(@RequestParam(name="idArea")Long idArea){
    	
    	return depaService.findAllDepartamentosByArea(idArea);
    }
    
    @RequestMapping(value = "/areaAgentes", method = RequestMethod.GET)
    public List<HrLookup> areaAgentes(){
    	return lookupService.findAllByTipoLookup("Area");
    }
    
    @RequestMapping(value ="/EmpleadosCatalogoComercial", method = RequestMethod.GET)
    public List<Object[]> listEmpleados(@RequestParam(name="idPuesto")Long idPuesto,
    									  @RequestParam(name="idDepartamento")Long idDepartamento,
    									  @RequestParam(name="idLookup")Long idLookup){
    	
    	return empleadoService.findAllByPuestoDepartamentoArea(idPuesto, idDepartamento, idLookup);
    }
    
    
    @RequestMapping(value = "/empleadosAgentes", method = RequestMethod.GET)
    public List<Object[]> findAllEmpleados(){
    	
    	return agentesVenta.findAllByNombreEmpleado();
    }
    
    @RequestMapping(value = "/ExtraerDatosAgenteCatalogo", method = RequestMethod.GET)
    public ComercialAgentesVenta ExtraerDatosAgenteCatalogo(@RequestParam(name="idEmpleado")Long idEmpleado){
    	
    	return agentesVenta.findOne(idEmpleado);
    }
    
    @RequestMapping(value = "/ExtraerPuestoAgenteCatalogo", method = RequestMethod.GET)
    public Object[] ExtraerPuestoAgenteCatalogo(@RequestParam(name="idEmpleado")Long idEmpleado){
    	
    	return empleadoService.findDatosPuesto(idEmpleado);
    }
    
    
    @RequestMapping(value ="/guardarAgenteCatalogo", method = RequestMethod.GET)
    public int guardarAgenteCatalogo(@RequestParam(name="idEmpleado")Long idEmpleado,
    									  		@RequestParam(name="foraneo", required=false)Long foraneo,
    									  		@RequestParam(name="licitacion", required=false)Long licitacion){
    	
    	System.out.println(idEmpleado);
    	System.out.println(foraneo);
    	System.out.println(licitacion);
    	try {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		Date date = new Date();
    		
    		if(agentesVenta.finduplicated(idEmpleado).equals("1")) {
    			return 2;
    		}
    		else {
    			ComercialAgentesVenta ventas = new ComercialAgentesVenta();
        		
        		ventas.setIdEmpleado(idEmpleado.toString());
        		ventas.setForaneo((foraneo==null)?"0":"1");
        		ventas.setLicitacion((licitacion==null)?"0":"1");
        		ventas.setCreadoPor(auth.getName());
        		ventas.setActualizadoPor(auth.getName());
        		ventas.setFechaCreacion(dateFormat.format(date));
        		ventas.setUltimaFechaModificacion(dateFormat.format(date));
        		ventas.setEstatus("1");
        		
        		agentesVenta.save(ventas);
        		
        		return 1;
    		}
    	}
    	catch(Exception e) {
    		return 3;
    	}
    	finally {
    		
    	}
    }
    
    @RequestMapping(value ="/editarAgenteCatalogo", method = RequestMethod.GET)
    public int editarAgenteCatalogo(@RequestParam(name="idEmpleado")Long idEmpleado,
    								@RequestParam(name="foraneo", required=false)Long foraneo,
    								@RequestParam(name="licitacion", required=false)Long licitacion){
    	
    	System.out.println(idEmpleado);
    	System.out.println(foraneo);
    	System.out.println(licitacion);
    	try {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		Date date = new Date();
    		
    		if(agentesVenta.finduplicated(idEmpleado).equals("1")) {
    			
    			ComercialAgentesVenta ventas = agentesVenta.findOne(idEmpleado);
        		System.out.println("si");
        		ventas.setIdEmpleado(idEmpleado.toString());
        		System.out.println("si");
        		ventas.setForaneo((foraneo==null)?"0":"1");
        		System.out.println("si");
        		ventas.setLicitacion((licitacion==null)?"0":"1");
        		System.out.println("si");
        		ventas.setActualizadoPor(auth.getName());
        		System.out.println("si");
        		ventas.setUltimaFechaModificacion(dateFormat.format(date));
        		
        		agentesVenta.save(ventas);
    			return 1;
    		}
    		else {
        		
        		return 2;
    		}
    	}
    	catch(Exception e) {
    		System.out.println(e);
    		return 3;
    	}
    	finally {
    		
    	}
    }
    
    @RequestMapping(value = "/bajaAgenteVentasCatalogoComercial", method = RequestMethod.GET)
    public int bajaAgenteVentasCatalogoComercial(@RequestParam(name="idEmpleado")Long idEmpleado){
    	
    	try {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		Date date = new Date();
    		ComercialAgentesVenta agente = agentesVenta.findOne(idEmpleado);
    		agente.setEstatus("0");
    		agente.setActualizadoPor(auth.getName());
    		agente.setUltimaFechaModificacion(dateFormat.format(date));
    		
    		agentesVenta.save(agente);
    		return 1;
    	}
    	catch(Exception e) {
    		System.out.println(e);
    		return 0;
    	}
    }
    
    @RequestMapping(value = "/altaAgenteVentasCatalogoComercial", method = RequestMethod.GET)
    public int altaAgenteVentasCatalogoComercial(@RequestParam(name="idEmpleado")Long idEmpleado){
    	
    	try {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		Date date = new Date();
    		ComercialAgentesVenta agente = agentesVenta.findOne(idEmpleado);
    		agente.setEstatus("1");
    		agente.setActualizadoPor(auth.getName());
    		agente.setUltimaFechaModificacion(dateFormat.format(date));
    		
    		agentesVenta.save(agente);
    		return 1;
    	}
    	catch(Exception e) {
    		System.out.println(e);
    		return 0;
    	}
    }
    
    @GetMapping("getModelos")
    public List<ComercialLookup> getModelos(@RequestParam String tipoLookup){
        return comercialLookupService.findByTipoLookup(tipoLookup);
    }

    @GetMapping("getModelo")
    public ComercialLookup getModelo(@RequestParam Long id){
        ComercialLookup modelo = comercialLookupService.findOne(id);
        return modelo;
    }

    @PostMapping("postModelo")
    public String postModelo(@RequestParam String modelo) {
        //TODO: process POST request
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ComercialLookup modeloLookup =new ComercialLookup();
        String[] modeloAtributos = modelo.split(",");
        modeloLookup.setCreadoPor(auth.getName());
        modeloLookup.setTipoLookup("Modelo");
        modeloLookup.setNombreLookup(modeloAtributos[0]);
        modeloLookup.setAtributo1(modeloAtributos[1]);
        modeloLookup.setAtributo2(modeloAtributos[2]);
        modeloLookup.setEstatus(1);
        
        try {
            modeloLookup.setIdText("");
            comercialLookupService.save(modeloLookup);
            modeloLookup.setIdText("MOD"+(10000+modeloLookup.getIdLookup()));
            comercialLookupService.save(modeloLookup);

        } catch (Exception e) {
            //TODO: handle exception
            return "Error "+e;
        }
        return "Success";
    }

    @PatchMapping("patchModelo")
    public String  patchModelo(@RequestParam String modelo) {
        String[] modeloAtributos = modelo.split(",");
        ComercialLookup modeloLookup = comercialLookupService.findOne(Long.parseLong(modeloAtributos[0]));
        modeloLookup.setNombreLookup(modeloAtributos[1]);
        modeloLookup.setAtributo1(modeloAtributos[2]);
        modeloLookup.setAtributo2(modeloAtributos[3]);
        try {
            comercialLookupService.save(modeloLookup);
        } catch (Exception e) {
            //TODO: handle exception
            return "Error "+e;
        }
        return "Success";
    }
    
    @GetMapping("/bajarModelo")
    public Object bajarModelo(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup modelo = comercialLookupService.findOne(id);
        modelo.setEstatus(0);
        comercialLookupService.save(modelo);
        //System.out.println(preciobaja.getEstatus());
        return comercialLookupService.findOne(id);
        
    }

    @GetMapping("/altaModelo")
    public Object altaModelo(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup modelo = comercialLookupService.findOne(id);
        modelo.setEstatus(1);
        comercialLookupService.save(modelo);
        return comercialLookupService.findOne(id);
    }

    @GetMapping("getPrecios")
    public List<ComercialLookup> getPrecios(@RequestParam String tipoLookup){
        return comercialLookupService.findByTipoLookup(tipoLookup);
    }
    @GetMapping("getPrecio")
    public ComercialLookup getPrecio(@RequestParam Long id){
        ComercialLookup precio = comercialLookupService.findOne(id);
        return precio;
    }
    @PostMapping("postPrecio")
    public String postPrecio(@RequestParam String precio){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ComercialLookup precioLookup =new ComercialLookup();
        String[] precioAtributos = precio.split(",");
        precioLookup.setCreadoPor(auth.getName());
        precioLookup.setTipoLookup("Personalizado");
        precioLookup.setNombreLookup(precioAtributos[0]);
        precioLookup.setAtributo1(precioAtributos[1]);
        precioLookup.setEstatus(1);

        try{
            precioLookup.setIdText("");
            comercialLookupService.save(precioLookup);
            precioLookup.setIdText("BORD"+(10000+precioLookup.getIdLookup()));
            comercialLookupService.save(precioLookup);
        }catch (Exception e){
            return "Error "+e;
        }
        return "success";
    }

    @GetMapping("/bajarPrecio")
    public Object bajarPrecio(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup preciobaja = comercialLookupService.findOne(id);
        preciobaja.setEstatus(0);
        comercialLookupService.save(preciobaja);
        //System.out.println(preciobaja.getEstatus());
        return comercialLookupService.findOne(id);
        
    }

    @GetMapping("/altaPrecio")
    public Object altaPrecio(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup precioalta = comercialLookupService.findOne(id);
        precioalta.setEstatus(1);
        comercialLookupService.save(precioalta);
        return comercialLookupService.findOne(id);
    }



    @PatchMapping("patchPrecio")
    public String patchPrecio(@RequestParam String precio){
        String[] precioAtributos = precio.split(",");
        ComercialLookup precioLookup = comercialLookupService.findOne(Long.parseLong(precioAtributos[0]));
        precioLookup.setNombreLookup(precioAtributos[1]);
        precioLookup.setAtributo1(precioAtributos[2]);
        try{
            comercialLookupService.save(precioLookup);
            
        }catch (Exception e){
            return "Error "+e;
        }
        return "success";

    }
    


    @GetMapping("getIvas")
    public List<ComercialLookup> getIva(@RequestParam String tipoLookup){
        return comercialLookupService.findByTipoLookup(tipoLookup);
    }
    @GetMapping("getIva")
    public ComercialLookup getIva(@RequestParam Long id){
        ComercialLookup iva = comercialLookupService.findOne(id);
        return iva;
    }
    
    @PostMapping("postIva")
    public String postIva(@RequestParam String iva){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ComercialLookup ivaLookup =new ComercialLookup();
        String[] ivaAtributos = iva.split(",");
        ivaLookup.setCreadoPor(auth.getName());
        ivaLookup.setTipoLookup("Iva");
        ivaLookup.setAtributo1(ivaAtributos[0]);
        ivaLookup.setEstatus(1);
        try{
            ivaLookup.setIdText("");
            comercialLookupService.save(ivaLookup);
            ivaLookup.setIdText("IVA"+(10000+ivaLookup.getIdLookup()));
            comercialLookupService.save(ivaLookup);
        }catch (Exception e){
            return "Error "+e;
        }
        return "success";
    }
    @GetMapping("/bajarIVA")
    public Object bajarIva(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup ivabaja = comercialLookupService.findOne(id);
        ivabaja.setEstatus(0);
        comercialLookupService.save(ivabaja);
        //System.out.println(ivabaja.getEstatus());
        return comercialLookupService.findOne(id);
        
    }

    @GetMapping("/altaIVA")
    public Object altaIva(@RequestParam(name = "idLookup")Long id) throws Exception{
        ComercialLookup ivaalta = comercialLookupService.findOne(id);
        ivaalta.setEstatus(1);
        comercialLookupService.save(ivaalta);
        return comercialLookupService.findOne(id);
    }

    @PatchMapping("patchIva")
    public String patchIva(@RequestParam String iva){
        String[] ivaAtributos = iva.split(",");
        ComercialLookup ivaLookup = comercialLookupService.findOne(Long.parseLong(ivaAtributos[0]));
        ivaLookup.setAtributo1(ivaAtributos[1]);
        try{
            comercialLookupService.save(ivaLookup);
            
        }catch (Exception e){
            return "Error "+e;
        }
        return "success";

    }
    
    
  //Capmpos para Ticket
    @GetMapping("getTickets")
    public List<ComercialLookup> getTickets(@RequestParam String tipoLookup){
        return comercialLookupService.findByTipoLookup(tipoLookup);
    }

    @GetMapping("getTicket")
    public ComercialLookup getTicket(@RequestParam Long id){
        ComercialLookup ticket= comercialLookupService.findOne(id);
        return ticket;
    }

    @PostMapping("postTicket")
    public String postTicket(@RequestParam String ticket,
    		@RequestParam(name="auxiliarTicket", required=false)Long auxiliarTicket,
	  		@RequestParam(name="solicitanteTicket", required=false)Long solicitanteTicket) {
        //TODO: process POST request
    	
    	
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ComercialLookup ticketLookup=new ComercialLookup();
        String[] ticketAtributos = ticket.split(",");
        
        System.out.println(auxiliarTicket);
        System.out.println(solicitanteTicket);
    	
        ticketLookup.setCreadoPor(auth.getName());
        ticketLookup.setTipoLookup("Ticket");
        ticketLookup.setNombreLookup(ticketAtributos[0]);
        ticketLookup.setAtributo1((auxiliarTicket==null)?"0":"1");
        ticketLookup.setAtributo2((solicitanteTicket==null)?"0":"1");
        ticketLookup.setEstatus(1);

        try {
            ticketLookup.setIdText("");
            comercialLookupService.save(ticketLookup);
            ticketLookup.setIdText("TICKET"+(10000+ticketLookup.getIdLookup()));
            comercialLookupService.save(ticketLookup);
            
        } catch (Exception e) {
            //TODO: handle exception
            return "Error "+e;
        }
        return "Success";
    }

    @GetMapping("/bajarTicket")
    public Object bajarTicket(@RequestParam(name = "idLookup") Long id) throws Exception {
        ComercialLookup ticketbaja= comercialLookupService.findOne(id);
        ticketbaja.setEstatus(0);
        comercialLookupService.save(ticketbaja);
        return comercialLookupService.findOne(id);
    }

    @GetMapping("/altaTicket")
    public Object altaTicket(@RequestParam(name="idLookup")Long id) throws Exception{
        ComercialLookup ticketalta= comercialLookupService.findOne(id);
        ticketalta.setEstatus(1);
        comercialLookupService.save(ticketalta);
        return comercialLookupService.findOne(id);
    }

    @PatchMapping("patchTicket")
    public String patchTicket(@RequestParam String ticket,
    		@RequestParam(name="auxiliarTicket", required=false)Long auxiliarTicket,
	  		@RequestParam(name="solicitanteTicket", required=false)Long solicitanteTicket){
        String[] ticketAtributos = ticket.split(",");
        
        ComercialLookup ticketLookup = comercialLookupService.findOne(Long.parseLong(ticketAtributos[0]));
        ticketLookup.setNombreLookup(ticketAtributos[1]);
        ticketLookup.setAtributo1((auxiliarTicket==null)?"0":"1");
        ticketLookup.setAtributo2((solicitanteTicket==null)?"0":"1");
        try {
            comercialLookupService.save(ticketLookup);
        } catch (Exception e) {
            //TODO: handle exception
            return "Error "+e;
        }
        return "Success";
    }
    
}
