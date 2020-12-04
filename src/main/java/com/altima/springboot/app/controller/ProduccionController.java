package com.altima.springboot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProduccionController {
	@GetMapping("/muestras")
	public String listMuestras() {
		return "muestras";
	}
	
	@GetMapping("/produccion-fechas-pedido")
	public String produccionFechas() {
		return "produccion-fechas-pedido";
	}
	
	@GetMapping("/prendas-faltantes")
	public String pedidosFaltantes() {
		return "prendas-faltantes";
	}
	
	@GetMapping("/pedidos-entregados")
	public String pedidosEntregados() {
		return "pedidos-entregados";
	}
	
	@GetMapping("/impresion-etiquetas")
	public String impresionEtiquetas() {
		return "impresion-etiquetas";
	}
	
	@GetMapping("/hojas-tonos")
	public String hojasTonos() {
		return "hojas-tonos";
	}
	
	@GetMapping("/consultas-telas-faltantes")
	public String telasFaltantes() {
		return "consultas-telas-faltantes";
	}
	
	@GetMapping("/pedidos-vencer")
	public String pedidosVencer() {
		return "pedidos-vencer";
	}
	
	@GetMapping("calculo-carga-produccion")
	public String calculoCarga() {
		return "calculo-carga-produccion";
	}
	
	@GetMapping("/avances-produccion")
	public String avanceProduccion() {
		return "avances-produccion";
	}
	
	@GetMapping("/orden-produccion-muestras")
	public String ordenProduccion() {
		return "orden-produccion-muestras";
	}
	
}
