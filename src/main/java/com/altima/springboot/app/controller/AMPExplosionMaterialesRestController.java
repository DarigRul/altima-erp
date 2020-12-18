package com.altima.springboot.app.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.AmpTraspaso;
import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;
import com.altima.springboot.app.models.service.IAmpExplosionMaterialesService;

@RestController
public class AMPExplosionMaterialesRestController {
	@Autowired
	IAmpExplosionMaterialesService AmpExplosionMaterialesService;

	@GetMapping("/explosion-materiales-habilitacion")
	public List<Object[]> ExplosionMateriales(Model model,Long Idpedido, Long IdArticulo) {
		return AmpExplosionMaterialesService.findAvailableMaterials(IdArticulo,Idpedido);
	}

	@GetMapping("/explosion-materiales-habilitacion-header")
	public List<Object[]> ExplosionMaterialesModalHeader(Model model, Long IdArticulo, Long IdPedido) {
		return AmpExplosionMaterialesService.findMaterialsHeader(IdArticulo, IdPedido);
	}

	@PostMapping("/guardar-habilitacion")
	public boolean GuardarHabilitacionExplosion(@RequestParam(name = "arrayReg") String arrayReg) {
		boolean response;
		try {
			JSONArray json = new JSONArray(arrayReg);
			for (Object object : json) {
				JSONObject object2 = (JSONObject) object;
				String destino = object2.get("destino").toString();
				String origen = object2.get("origen").toString();
				;
				String articulo = object2.get("articulo").toString();
				;
				String traspasodetalle = object2.get("traspasodetalle").toString();
				;
				String apartado = object2.get("apartados").toString();
				;

				if (traspasodetalle.isEmpty()) {
					AmpTraspaso traspaso2 = new AmpTraspaso();
					traspaso2.setIdAlmacenLogicoDestino(Long.parseLong(destino));
					traspaso2.setIdAlmacenLogicoOrigen(Long.parseLong(origen));
					traspaso2.setTipo("2");
					AmpExplosionMaterialesService.SaveTraspaso(traspaso2);

					AmpTraspasoDetalle traspasodetalle2 = new AmpTraspasoDetalle();
					traspasodetalle2.setIdTraspaso(traspaso2.getIdTraspaso());
					traspasodetalle2.setIdArticulo(Long.parseLong(articulo));
					traspasodetalle2.setTipo("Material");
					traspasodetalle2.setCantidad(Float.parseFloat(apartado));
					AmpExplosionMaterialesService.SaveTraspasoDetalle(traspasodetalle2);

				} else {
					AmpTraspasoDetalle traspasodetalle3 = AmpExplosionMaterialesService
							.findOneTraspasoDetalle(Long.parseLong(traspasodetalle));
					System.out.println(Float.parseFloat(apartado) - traspasodetalle3.getCantidad());
					Float apartado4 = Float.parseFloat(apartado) - traspasodetalle3.getCantidad();
					traspasodetalle3.setCantidad(traspasodetalle3.getCantidad() + apartado4);
					AmpExplosionMaterialesService.SaveTraspasoDetalle(traspasodetalle3);
				}
			}
			response=true;
		} catch (Exception e) {
			// TODO: handle exception
			response=false;
		}
		
return response;
	}

}

