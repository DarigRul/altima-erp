package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.MaquilaAsignacionTickets;
import com.altima.springboot.app.models.service.IMaquilaAsignacionTicketsService;

@RestController
public class TallerMaquilaAsignacionTicketsRestController {
@Autowired
IMaquilaAsignacionTicketsService maquilaAsignacionTicketsService;
	
@GetMapping("/obtener-operarios")
public List<HrEmpleado> ListarOperarios(){
	
	return maquilaAsignacionTicketsService.ListarOperarios();
	
}

@GetMapping("/obtener-tickets-operarios")
public List<Object[]> ListarTicketsOperarios(Long idoperario){
	
	
	return maquilaAsignacionTicketsService.ListarTicketsOperarios(idoperario);
}

@GetMapping("/obtener-numero-ticket")
public List<MaquilaAsignacionTickets>ObtenerNumeroTickets(){
	
	return maquilaAsignacionTicketsService.ListarTickesNoAsignados();
}

@PutMapping("/asignar-operario-ticket")
public Boolean AsignarOperarioTicket(Long idticket, Long idoperario) {
	Boolean response=false;
	Date date = new Date();
	DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	try {
		MaquilaAsignacionTickets TicketAsignar=maquilaAsignacionTicketsService.findOne(idticket,idoperario);
		TicketAsignar.setOperario(idoperario.toString());
		TicketAsignar.setFechaAsignacion(hourdateFormat.format(date));
		maquilaAsignacionTicketsService.save(TicketAsignar);
	response=true;
	} catch (Exception e) {
		// TODO: handle exception
		response=false;
	}
	return response;
	
	
}

@PutMapping("/eliminar-asignacion")
public Boolean EliminarAsignacion(Long idticket) {
	Boolean response=false;
	
	try {
		MaquilaAsignacionTickets TicketAsignar=maquilaAsignacionTicketsService.findOne(idticket);
		TicketAsignar.setOperario(null);
		TicketAsignar.setFechaAsignacion(null);
		maquilaAsignacionTicketsService.save(TicketAsignar);
	response=true;
	} catch (Exception e) {
		// TODO: handle exception
		response=false;
	}
	return response;
	
	
}





}
