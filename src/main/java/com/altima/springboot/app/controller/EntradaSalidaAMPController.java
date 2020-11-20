package com.altima.springboot.app.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.altima.springboot.app.models.service.IAmpMultialmacenService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;

import com.lowagie.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EntradaSalidaAMPController {

	@Autowired
	IAmpMultialmacenService multialmacenService;

	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AGENTES_CATALOGOPRENDAS_LISTAR"})
	@GetMapping("/movimientos-amp")
	public String Index(Model m) {
		m.addAttribute("movimientos", multialmacenService.findAllMovimientos());
		return "movimientos-amp";
	}

	@Secured({"ROLE_ADMINISTRADOR","ROLE_COMERCIAL_AMP_ENTRADASSALIDAS_AGREGAR"})
	@GetMapping("/agregar-movimientos-amp")
	public String Store() {
		return "agregar-movimientos-amp";
	}

	@GetMapping("/rollotelabarcode")
	public String rolloTelaBarCode(@RequestParam String listIdText,Model m,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		JSONArray rolloArray = new JSONArray(listIdText);
		m.addAttribute("rolloArray", rolloArray);
		return "/rollotelabarcode";
	}

}
