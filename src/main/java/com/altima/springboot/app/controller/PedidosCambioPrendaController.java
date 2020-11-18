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

    @GetMapping("/pedidos-cambio-prenda")
	public String listPedidos(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
		/* Obtener todos los datos del usuario logeado */
		Usuario user = usuarioService.FindAllUserAttributes(auth.getName(), auth.getAuthorities());
		Long iduser = user.getIdUsuario();
		String role = "[ROLE_ADMINISTRADOR]";
		if (auth.getAuthorities().toString().equals(role)) {
			model.addAttribute("admin", true);
			
			model.addAttribute("pedidos", cargaPedidoService.pedidosCambioPrenda(null));
		} else {
			model.addAttribute("pedidos", cargaPedidoService.pedidosCambioPrenda(iduser));

		}
		return "pedidos-cambio-prenda";
	}// le movio erik

	@GetMapping("/detalle-coordinados/{id}")
	public String listCoordinados(@PathVariable(value = "id") Long id, Model model) {
		model.addAttribute("coordinados", CoordinadoService.findAllEmpresa(id));

		model.addAttribute("id_pedido", id);
		return "pedidos-cambio-prenda-coordinado";
	}

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

		System.out.println("Si entro al rest guardar guar guardarrrr coordinado");

		System.out.println("El id de prenda es: " + idPrenda);

		System.out.println("El id de tela es: " + idTela);

		System.out.println("Este es el arreglo: " + arrayid);

		System.out.println("++"+idCoorPrenda);

		ComercialCoordinadoPrenda objetoCoodinadoPrenda = CoordinadoService.findOneCoorPrenda(idCoorPrenda);
		String fecha = hourdateFormat.format(date);
		
		objetoCoodinadoPrenda.setIdFamilaGenero(idPrenda);
		objetoCoodinadoPrenda.setIdPrenda(idModelo);
		objetoCoodinadoPrenda.setIdTela(idTela);
		objetoCoodinadoPrenda.setIdCoordinado(idCoordinado);
		objetoCoodinadoPrenda.setAdicional("0");
		objetoCoodinadoPrenda.setMontoAdicional("0");
		objetoCoodinadoPrenda.setPrecioFinal("0");
		objetoCoodinadoPrenda.setEstatus("1");
		objetoCoodinadoPrenda.setActualizadoPor(auth.getName());
		objetoCoodinadoPrenda.setUltimaFechaModificacion(fecha);
		objetoCoodinadoPrenda.setPrecio(CoordinadoService.precioPrenda(idCoordinado, idModelo, idTela));
		objetoCoodinadoPrenda.setPrecioFinal(CoordinadoService.precioPrenda(idCoordinado, idModelo, idTela));
		CoordinadoService.saveCoorPrenda(objetoCoodinadoPrenda);
		////// seccion2 TELAS
		JSONArray json2 = new JSONArray(datosMateriales22);
		for (int k = 0; k < json2.length(); k++) {
			JSONObject object = (JSONObject) json2.get(k);
			String id = object.get("id_tela").toString();
			String idMaterialConv = object.get("id_materialConv").toString();
			ComercialCoordinadoTela detalleTela =CoordinadoService.searchTela(objetoCoodinadoPrenda.getIdCoordinadoPrenda(),idMaterialConv);
			if ( detalleTela.getIdCoordinadoTela() >0){
				detalleTela.setIdTela(Long.parseLong(id));
				detalleTela.setActualizadoPor(auth.getName());
				detalleTela.setDescripcion(idMaterialConv);
				detalleTela.setUltimaFechaModificacion(fecha);
				CoordinadoService.saveTelaMaterial(detalleTela);
			}else{
				detalleTela.setIdTela(Long.parseLong(id));
				detalleTela.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
				detalleTela.setCreado_por(auth.getName());
				detalleTela.setActualizadoPor("user");
				detalleTela.setDescripcion(idMaterialConv);
				detalleTela.setFechaCreacion(hourdateFormat.format(date));
				detalleTela.setUltimaFechaModificacion(null);
				CoordinadoService.saveTelaMaterial(detalleTela);
			}
		}
		CoordinadoService.deleteTela(objetoCoodinadoPrenda.getIdCoordinadoPrenda(), fecha);
		//////// SECUION3 FORROS

		JSONArray json22 = new JSONArray(datosMateriales222);
		for (int h = 0; h < json22.length(); h++) {
			//ComercialCoordinadoForro detalleForro = new ComercialCoordinadoForro();
			JSONObject object = (JSONObject) json22.get(h);
			String id = object.get("id_forro").toString();
			String idMaterialForro = object.get("id_MaterialForro").toString();
			ComercialCoordinadoForro detalleForro =CoordinadoService.searchForro(objetoCoodinadoPrenda.getIdCoordinadoPrenda(),idMaterialForro);
			if ( detalleForro.getIdCoordinadoForro()>0){
				detalleForro.setDescripcion(idMaterialForro);
				detalleForro.setIdForro(Long.parseLong(id));
				detalleForro.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
				//detalleForro.setCreado_por(auth.getName());
				detalleForro.setActualizadoPor(auth.getName());
				//detalleForro.setFechaCreacion(hourdateFormat.format(date));
				detalleForro.setUltimaFechaModificacion(fecha);
				CoordinadoService.saveForroMaterial(detalleForro);
			}else{
				detalleForro.setDescripcion(idMaterialForro);
				detalleForro.setIdForro(Long.parseLong(id));
				detalleForro.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
				detalleForro.setCreado_por(auth.getName());
				detalleForro.setActualizadoPor("user");
				detalleForro.setFechaCreacion(hourdateFormat.format(date));
				detalleForro.setUltimaFechaModificacion(null);
				CoordinadoService.saveForroMaterial(detalleForro);
			}
			
		}
		CoordinadoService.deleteForro(objetoCoodinadoPrenda.getIdCoordinadoPrenda(), fecha);
		////// parte 3 materiales

		JSONArray json = new JSONArray(datosMateriales);
		String[] parts2 = arrayid.split(",");
		for (int j = 0; j < json.length(); j++) {
			JSONObject object = (JSONObject) json.get(j);

			String color = object.get("color").toString();
			String[] parts = color.split("_");
			ComercialCoordinadoMaterial material = CoordinadoService.searchMaterial(objetoCoodinadoPrenda.getIdCoordinadoPrenda(), Long.parseLong(parts2[j]));
			/*
			if ( material.getIdCoordinadoMaterial()>0){

				material.setIdMaterial(Long.parseLong(parts2[j]));
				material.setColor(parts[0]);
				material.setColorCodigo(parts[1]);
				material.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
				//material.setCreadoPor(auth.getName());
				material.setActualizadoPor(auth.getName());
				//material.setFechaCreacion(hourdateFormat.format(date));
				material.setUltimaFechaModificacion(fecha);
				CoordinadoService.saveCoorMaterial(material);

			}else{
				material.setIdMaterial(Long.parseLong(parts2[j]));
				material.setColor(parts[0]);
				material.setColorCodigo(parts[1]);
				material.setIdCoordinadoPrenda(objetoCoodinadoPrenda.getIdCoordinadoPrenda());
				material.setCreadoPor(auth.getName());
				material.setActualizadoPor(null);
				material.setFechaCreacion(hourdateFormat.format(date));
				material.setUltimaFechaModificacion(null);
				CoordinadoService.saveCoorMaterial(material);
			}*/
			

		}
		CoordinadoService.deleteMaterial(objetoCoodinadoPrenda.getIdCoordinadoPrenda(), fecha);

		return 1;

	}

}

