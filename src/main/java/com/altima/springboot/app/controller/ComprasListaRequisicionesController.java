package com.altima.springboot.app.controller;

import com.altima.springboot.app.models.entity.ComprasProveedores;
import com.altima.springboot.app.models.service.IAmpInventarioService;
import com.altima.springboot.app.models.service.IComprasProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComprasListaRequisicionesController {

    @Autowired
    IAmpInventarioService ampInventarioService;
    @Autowired
    IComprasProveedorService proveedorService;

    @GetMapping("/listado-de-requisiciones")
	public String ReqMatHab(Model m) {
        m.addAttribute("materiales", ampInventarioService.findAllRequisicion());
		return "listado-de-requisiciones";
    }
    
    @GetMapping("/listado-de-requisiciones-goc")
	public String ReqMatHabGenerarOC(@RequestParam String idMateriales,Model m,@RequestParam Long idProveedor) {
        System.out.println(idProveedor);
        ComprasProveedores p=proveedorService.findOne(idProveedor);
        System.out.println(p.getNombreProveedor());
        m.addAttribute("idProveedor", idProveedor);
        m.addAttribute("proveedor", p);
        m.addAttribute("materiales", ampInventarioService.findAllRequisicion(idMateriales));
		return "listado-de-requisiciones-goc";
	}
}
