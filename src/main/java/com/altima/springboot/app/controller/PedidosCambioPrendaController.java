package com.altima.springboot.app.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.altima.springboot.app.models.entity.ComercialCalendario;
import com.altima.springboot.app.models.entity.ComercialCliente;
import com.altima.springboot.app.models.entity.ComercialCoordinadoForro;
import com.altima.springboot.app.models.entity.ComercialCoordinadoMaterial;
import com.altima.springboot.app.models.entity.ComercialCoordinadoPrenda;
import com.altima.springboot.app.models.entity.ComercialCoordinadoTela;
import com.altima.springboot.app.models.entity.ProduccionPedido;
import com.altima.springboot.app.models.entity.Usuario;
import com.altima.springboot.app.models.service.ICargaPedidoService;
import com.altima.springboot.app.models.service.IComercialCalendarioService;
import com.altima.springboot.app.models.service.IComercialClienteService;
import com.altima.springboot.app.models.service.IComercialCoordinadoService;
import com.altima.springboot.app.models.service.IComercialMovimientoService;
import com.altima.springboot.app.models.service.IUsuarioService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PedidosCambioPrendaController {

    @Autowired
    IUsuarioService usuarioService;
    
    @Autowired
	private ICargaPedidoService cargaPedidoService;

	@Autowired
	private IComercialCoordinadoService CoordinadoService;
	
	@PreAuthorize("@authComponent.hasPermission(#null,{'listar-cambio-prenda'})")
    @GetMapping("/pedidos-cambio-prenda")
	public String listPedidos(Model model) {
		model.addAttribute("admin", true);
		
		model.addAttribute("pedidos", cargaPedidoService.pedidosCambioPrenda(null));
		return "pedidos-cambio-prenda";
	}// le movio erik

	@PreAuthorize("@authComponent.hasPermission(#id,{'cambio-prenda'})")
	@GetMapping("/detalle-coordinados/{id}")
	public String listCoordinados(@PathVariable(value = "id") Long id, Model model) {
		model.addAttribute("coordinados", CoordinadoService.findAllEmpresa(id));

		model.addAttribute("id_pedido", id);
		return "pedidos-cambio-prenda-coordinado";
	}

	@PreAuthorize("@authComponent.hasPermission(#id,{'cambio-prenda'})")
	@GetMapping("/editar-coordinado-prenda/{id}")
	public String addCoordinados(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		ComercialCoordinadoPrenda prenda = new ComercialCoordinadoPrenda();
		prenda.setIdCoordinado(id);
		model.put("prenda", prenda);
		model.put("listPrendas", CoordinadoService.findAllPrenda());

		model.put("listCoorPrenda", CoordinadoService.findAllCoorPrenda(id));
		return "pedidos-cambio-prenda-coordinado-prendas";
	}

	@RequestMapping(value = "/editar-pedido-interno-rest-coordinado", method = RequestMethod.POST)
	@ResponseBody
	public Integer guardar(
		@RequestParam(name = "datosMateriales") String datosMateriales,
		@RequestParam(name = "datosMateriales22") String datosMateriales22,
		@RequestParam(name = "datosMateriales222") String datosMateriales222,
		@RequestParam(name = "arrayId") String arrayid,
		Long idPrenda,
		Long idTela,
		Long idModelo,
		Long idCoordinado,
		Long idCoorPrenda,
		HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ComercialCoordinadoPrenda objetoCoodinadoPrenda = CoordinadoService.findOneCoorPrenda(idCoorPrenda);
		String fecha = hourdateFormat.format(date);
		
		objetoCoodinadoPrenda.setIdFamilaGenero(idPrenda);
		objetoCoodinadoPrenda.setIdPrenda(idModelo);
		objetoCoodinadoPrenda.setIdTela(idTela);
		objetoCoodinadoPrenda.setIdCoordinado(idCoordinado);
		objetoCoodinadoPrenda.setEstatus("1");
		objetoCoodinadoPrenda.setActualizadoPor(auth.getName());
		objetoCoodinadoPrenda.setUltimaFechaModificacion(fecha);
		CoordinadoService.saveCoorPrenda(objetoCoodinadoPrenda);
		
		////// seccion2 TELAS
		CoordinadoService.deleteTela(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
		JSONArray json2 = new JSONArray(datosMateriales22);
		for (int k = 0; k < json2.length(); k++) {
			JSONObject object = (JSONObject) json2.get(k);
			String id = object.get("id_tela").toString();
			String idMaterialConv = object.get("id_materialConv").toString();
			
			ComercialCoordinadoTela telaNew = new ComercialCoordinadoTela();
			telaNew.setIdTela(Long.parseLong(id));
			telaNew.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
			telaNew.setCreado_por(auth.getName());
			telaNew.setActualizadoPor("user");
			telaNew.setDescripcion(idMaterialConv);
			telaNew.setFechaCreacion(fecha);
			telaNew.setUltimaFechaModificacion(null);
			CoordinadoService.saveTelaMaterial(telaNew);
		}
		
		//////// SECUION3 FORROS
		CoordinadoService.deleteForro(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
		JSONArray json22 = new JSONArray(datosMateriales222);
		for (int h = 0; h < json22.length(); h++) {
			//ComercialCoordinadoForro detalleForro = new ComercialCoordinadoForro();
			JSONObject object = (JSONObject) json22.get(h);
			String id = object.get("id_forro").toString();
			String idMaterialForro = object.get("id_MaterialForro").toString();
			
			ComercialCoordinadoForro detalleForroNew = new ComercialCoordinadoForro();
			detalleForroNew.setDescripcion(idMaterialForro);
			detalleForroNew.setIdForro(Long.parseLong(id));
			detalleForroNew.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
			detalleForroNew.setCreado_por(auth.getName());
			detalleForroNew.setActualizadoPor("user");
			detalleForroNew.setFechaCreacion(hourdateFormat.format(date));
			detalleForroNew.setUltimaFechaModificacion(null);
			CoordinadoService.saveForroMaterial(detalleForroNew);
			
			
		}
		
		////// parte 3 materiales
		CoordinadoService.deleteMaterial(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
		JSONArray json = new JSONArray(datosMateriales);
		String[] parts2 = arrayid.split(",");
		for (int j = 0; j < json.length(); j++) {
			JSONObject object = (JSONObject) json.get(j);

			String color = object.get("color").toString();
			String[] parts = color.split("_");
			
			ComercialCoordinadoMaterial materialNew = new ComercialCoordinadoMaterial();
			materialNew.setIdMaterial(Long.parseLong(parts2[j]));
			materialNew.setColor(parts[0]);
			materialNew.setColorCodigo(parts[1]);
			materialNew.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
			materialNew.setCreadoPor(auth.getName());
			materialNew.setActualizadoPor(null);
			materialNew.setFechaCreacion(hourdateFormat.format(date));
			materialNew.setUltimaFechaModificacion(null);
			CoordinadoService.saveCoorMaterial(materialNew);
			
			

		}
		

		return 1;

	}

}

