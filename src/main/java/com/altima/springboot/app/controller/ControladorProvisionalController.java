package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorProvisionalController {
    @GetMapping("/asignacion-ruta-programa")
	public String asignacionRuta() {
		return "asignacion-ruta-programa";
	}
	@GetMapping("/asignacion-programa")
	public String asignacionPrograma() {
		return "asignacion-programa";
	}
	
	@GetMapping("/explosion-de-procesos")
	public String explosionProcesos() {
		return "explosion-de-procesos";
	}
	@GetMapping("/orden-de-compra")
	public String ordenCompra() {
		return "orden-de-compra";
	}
	@GetMapping("/orden-de-compra-nueva")
	public String ordenCompraNew() {
		return "orden-de-compra-nueva";
	}
	@GetMapping("/listado-de-requisiciones")
	public String ReqMatHab() {
		return "listado-de-requisiciones";
	}
	@GetMapping("/listado-de-requisiciones-goc")
	public String ReqMatHabGenerarOC() {
		return "listado-de-requisiciones-goc";
	}
	@GetMapping("/control-de-telas")
	public String CtrlTelas() {
		return "control-de-telas";
	}
	@GetMapping("/control-habilitacion")
	public String CtrlHab() {
		return "control-habilitacion";
	}
	@GetMapping("/control-habilitacion-material")
	public String CtrlHabMat() {
		return "control-habilitacion-material";
	}


}
