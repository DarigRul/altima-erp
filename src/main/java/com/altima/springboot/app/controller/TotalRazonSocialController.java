package com.altima.springboot.app.controller;

import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.altima.springboot.app.models.entity.ComercialTotalRazonSocial;
import com.altima.springboot.app.models.entity.DisenioTela;
import com.altima.springboot.app.models.service.IComercialTotalRazonSocialService;

@Controller
public class TotalRazonSocialController {

	@Autowired
	private IComercialTotalRazonSocialService totalService;

	@GetMapping("/totales-por-razon-social/{id}")
	public String listConcentradoTallas(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DecimalFormat floatFormat = new DecimalFormat("0.00");
		floatFormat.setMaximumFractionDigits(2);
		List<Object[]> aux = totalService.consultaX(id);
		
		
		for (Object[] a : aux) {
				
				ComercialTotalRazonSocial total = totalService.findOne(Long.parseLong(a[1].toString()),
						Long.parseLong(a[0].toString()));

				if (total != null) {
					
					float sub1 = Float.parseFloat(a[3].toString());
					
					
					float sub2= Float.parseFloat(total.getSubtotalTotal2());
					float iva = Float.parseFloat(total.getIva());
					float totalf= Float.parseFloat(total.getTotalPedido());
					float p_descuento = Float.parseFloat(total.getDescuPorcentaje());
					float p_ancicipo = Float.parseFloat(total.getAnticiPorcentaje());
					float p_entrega = Float.parseFloat(total.getEntregaPorcentaje());
					float p_saldo = Float.parseFloat(total.getAjustesPorcentaje());
					
					float m_ancicipo;
					float m_entrega;
					double m_saldo;
					float m_descuento;
					
					sub2 =  (sub1 * p_descuento/100)+ sub1;
					totalf = (sub2 * (iva/100) )+sub2;
					
					m_ancicipo = (totalf*(p_ancicipo/100));
					
					m_entrega = (totalf*(p_entrega/100));
					
					m_ancicipo =  (float) redondearDecimales(m_ancicipo, 2);
					m_entrega =  (float) redondearDecimales(m_entrega, 2);
					
					
					m_saldo = totalf-m_ancicipo-m_entrega;
					System.out.println(m_saldo);
					m_descuento = (sub1*(p_descuento/100));
				
					total.setIdPedido(Long.parseLong(a[1].toString()));
					total.setIdClienteFacturaF(Long.parseLong(a[0].toString()));
					
					total.setNumeroPrenda(a[2].toString());
					total.setSubtotalTotal(a[3].toString());
					total.setIva(a[4].toString());
					total.setDescuento(floatFormat.format(m_descuento));
					total.setAnticipo(floatFormat.format(m_ancicipo));
					total.setEntrega(floatFormat.format(m_entrega));
					total.setSaldo(floatFormat.format(m_saldo));
					total.setSubtotalTotal2(floatFormat.format(sub2));
					total.setTotalPedido(floatFormat.format(totalf));
					totalService.save(total);

				} else {
					ComercialTotalRazonSocial total2 = new ComercialTotalRazonSocial();
					float sub1 = Float.parseFloat(a[3].toString());
					float sub2= Float.parseFloat(a[3].toString());
					float iva = Float.parseFloat(a[4].toString());
					float totalf;
					sub2 = sub1;
					totalf = (sub2 * (iva/100) )+sub2;
					
					total2.setIdPedido(Long.parseLong(a[1].toString()));
					total2.setIdClienteFacturaF(Long.parseLong(a[0].toString()));
					total2.setNumeroPrenda(a[2].toString());
					total2.setSubtotalTotal(a[3].toString());
					total2.setDescuento("0.00");
					total2.setSubtotalTotal2(String.valueOf(sub2));
					total2.setIva(a[4].toString());
					total2.setTotalPedido(String.valueOf(totalf));
					total2.setAnticipo("0.00");
					total2.setEntrega("0.00");
					total2.setSaldo("0.00");
					total2.setAjustesPorcentaje("0.00");
					total2.setAnticiPorcentaje("0.00");
					total2.setDescuPorcentaje("0.00");
					total2.setEntregaPorcentaje("0.00");
					
					total2.setCreadoPor(auth.getName());
					total2.setFechaCreacion(hourdateFormat.format(date));
					total2.setActualizadoPor(auth.getName());
					total2.setUltimaFechaModificacion(hourdateFormat.format(date));
					totalService.save(total2);

				}
	

		}

		model.put("lisTotal", totalService.totalRazon(id));
		return "totales-por-razon-social";
	}

	@PostMapping("/guardar-total-razon")
	public String guardar_razon(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
			@RequestParam(value = "sub2", required = false, defaultValue = "0.00") String sub2,
			@RequestParam(value = "iva", required = false, defaultValue = "0.00") String iva,
			@RequestParam(value = "total", required = false, defaultValue = "0.00") String total_p,
			@RequestParam(value = "anticipo", required = false, defaultValue = "0.00") String anticipo,
			@RequestParam(value = "entrega", required = false, defaultValue = "0.00") String entrega,
			@RequestParam(value = "saldo", required = false, defaultValue = "0.00") String saldo,
			@RequestParam(value = "descuento", required = false, defaultValue = "0.00") String descuento,
			@RequestParam(value = "porcentaje1", required = false, defaultValue = "0.00") String porcentaje1,
			@RequestParam(value = "porcentaje2", required = false, defaultValue = "0.00") String porcentaje2,
			@RequestParam(value = "porcentaje3", required = false, defaultValue = "0.00") String porcentaje3,
			@RequestParam(value = "porcentaje4", required = false, defaultValue = "0.00") String porcentaje4,
			RedirectAttributes redirectAttrs) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialTotalRazonSocial total = totalService.findOne(id);
		total.setDescuento(descuento);
		total.setSubtotalTotal2(sub2);
		total.setIva(iva);
		total.setTotalPedido(total_p);
		total.setAnticipo(anticipo);
		total.setEntrega(entrega);
		total.setSaldo(saldo);
		total.setActualizadoPor(auth.getName());
		total.setUltimaFechaModificacion(hourdateFormat.format(date));
		total.setDescuPorcentaje(porcentaje1);
		total.setAnticiPorcentaje(porcentaje2);
		total.setEntregaPorcentaje(porcentaje3);
		total.setAjustesPorcentaje(porcentaje4);
		totalService.save(total);

		redirectAttrs.addFlashAttribute("title", "Guardado correctamente").addFlashAttribute("icon", "success");
		return "redirect:/totales-por-razon-social/" + total.getIdPedido();
	}
	
	 public static double redondearDecimales(float valorInicial, int numeroDecimales) {
	        double parteEntera, resultado;
	        resultado = valorInicial;
	        parteEntera = Math.floor(resultado);
	        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
	        resultado=Math.round(resultado);
	        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
	        return resultado;
	    }
}
