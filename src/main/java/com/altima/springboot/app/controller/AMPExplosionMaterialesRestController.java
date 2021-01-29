package com.altima.springboot.app.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.AmpMultialmacen;
import com.altima.springboot.app.models.entity.AmpTraspaso;
import com.altima.springboot.app.models.entity.AmpTraspasoDetalle;
import com.altima.springboot.app.models.service.IAmpExplosionMaterialesService;
import com.altima.springboot.app.models.service.IAmpMultialmacenService;

@RestController
public class AMPExplosionMaterialesRestController {
	@Autowired
	IAmpExplosionMaterialesService AmpExplosionMaterialesService;
	@Autowired
	IAmpMultialmacenService MultialmacenService;
	
	@GetMapping("/explosion-materiales-habilitacion")
	public List<Object[]> ExplosionMateriales(Model model, Long Idpedido, Long IdArticulo) {
		return AmpExplosionMaterialesService.findAvailableMaterials(IdArticulo, Idpedido);
	}

	@GetMapping("/explosion-materiales-habilitacion-header")
	public List<Object[]> ExplosionMaterialesModalHeader(Model model, Long IdArticulo, Long IdPedido) {
		return AmpExplosionMaterialesService.findMaterialsHeader(IdArticulo, IdPedido);
	}
	
	@GetMapping("/verificar-almacen-apartados")
	public boolean VerificarAlmacenApartados(String material) {
		boolean respuesta=AmpExplosionMaterialesService.VerificarAlmacenApartados(material);;
		return respuesta;
	}
    @Transactional
	@PostMapping("/guardar-habilitacion")
	public boolean GuardarHabilitacionExplosion(@RequestParam(name = "arrayReg") String arrayReg, String pedido) {
		boolean response;
		
		try {
			JSONArray json = new JSONArray(arrayReg);
			for (Object object : json) {
				AmpMultialmacen origenma=null;
				AmpMultialmacen apartadoma=null;

				JSONObject object2 = (JSONObject) object;
				String destino = object2.get("destino").toString();
				String origen = object2.get("origen").toString();
				String articulo = object2.get("articulo").toString();
				String traspasodetalle = object2.get("traspasodetalle").toString();
				String apartado = object2.get("apartado").toString();
				
				if (traspasodetalle.isEmpty()) {
					AmpTraspaso traspaso2 = new AmpTraspaso();
					traspaso2.setIdAlmacenLogicoDestino(Long.parseLong(destino));
					traspaso2.setIdAlmacenLogicoOrigen(Long.parseLong(origen));
					traspaso2.setTipo("2");
					traspaso2.setReferencia(pedido);
					traspaso2.setIdConceptoEntrada(AmpExplosionMaterialesService.EntradaSalida().getIdMovimientoEntrada());
					traspaso2.setIdConceptoSalida(AmpExplosionMaterialesService.EntradaSalida().getIdMovimientoSalida());
					AmpExplosionMaterialesService.SaveTraspaso(traspaso2);
					AmpTraspasoDetalle traspasodetalle2 = new AmpTraspasoDetalle();
					traspasodetalle2.setIdTraspaso(traspaso2.getIdTraspaso());
					traspasodetalle2.setIdArticulo(Long.parseLong(articulo));
					traspasodetalle2.setTipo("Material");
					traspasodetalle2.setCantidad(Float.parseFloat(apartado));
					AmpExplosionMaterialesService.SaveTraspasoDetalle(traspasodetalle2);
				    origenma=MultialmacenService.findById(MultialmacenService.findIdMultialmacen(traspaso2.getIdAlmacenLogicoOrigen(), traspasodetalle2.getIdArticulo(), traspasodetalle2.getTipo()));
					apartadoma=MultialmacenService.findById(MultialmacenService.findIdMultialmacen(AmpExplosionMaterialesService.EntradaSalida().getIdAlmacenLogico(), traspasodetalle2.getIdArticulo(), traspasodetalle2.getTipo()));
                origenma.setExistencia(origenma.getExistencia()-traspasodetalle2.getCantidad());
                apartadoma.setExistencia(apartadoma.getExistencia()+traspasodetalle2.getCantidad());
                MultialmacenService.save(origenma);
                MultialmacenService.save(apartadoma);
				} else {
					AmpTraspasoDetalle traspasodetalle3 = AmpExplosionMaterialesService
							.findOneTraspasoDetalle(Long.parseLong(traspasodetalle));
					System.out.println(Float.parseFloat(apartado) - traspasodetalle3.getCantidad());
					Float apartado4 = Float.parseFloat(apartado) - traspasodetalle3.getCantidad();
					traspasodetalle3.setCantidad(traspasodetalle3.getCantidad() + apartado4);
					AmpExplosionMaterialesService.SaveTraspasoDetalle(traspasodetalle3);
					//si lo que voy a ingresar es mayor a lo que hay
					if (Float.parseFloat(apartado) > traspasodetalle3.getCantidad().floatValue()) {
						 origenma=MultialmacenService.findById(MultialmacenService.findIdMultialmacen(AmpExplosionMaterialesService.findById(traspasodetalle3.getIdTraspaso()).getIdAlmacenLogicoOrigen(), traspasodetalle3.getIdArticulo(), traspasodetalle3.getTipo()));
							apartadoma=MultialmacenService.findById(MultialmacenService.findIdMultialmacen(AmpExplosionMaterialesService.EntradaSalida().getIdAlmacenLogico(), traspasodetalle3.getIdArticulo(), traspasodetalle3.getTipo()));
		                origenma.setExistencia(origenma.getExistencia()-traspasodetalle3.getCantidad());
		                apartadoma.setExistencia(apartadoma.getExistencia()+traspasodetalle3.getCantidad());
		                MultialmacenService.save(origenma);
		                MultialmacenService.save(apartadoma);
					} else if(Float.parseFloat(apartado) < traspasodetalle3.getCantidad().floatValue()) {
						
						 origenma=MultialmacenService.findById(MultialmacenService.findIdMultialmacen(AmpExplosionMaterialesService.findById(traspasodetalle3.getIdTraspaso()).getIdAlmacenLogicoOrigen(), traspasodetalle3.getIdArticulo(), traspasodetalle3.getTipo()));
							apartadoma=MultialmacenService.findById(MultialmacenService.findIdMultialmacen(AmpExplosionMaterialesService.EntradaSalida().getIdAlmacenLogico(), traspasodetalle3.getIdArticulo(), traspasodetalle3.getTipo()));
		                origenma.setExistencia(origenma.getExistencia()+traspasodetalle3.getCantidad());
		                apartadoma.setExistencia(apartadoma.getExistencia()-traspasodetalle3.getCantidad());
		                MultialmacenService.save(origenma);
		                MultialmacenService.save(apartadoma);
					}
		
				}
			}
			AmpExplosionMaterialesService.savemissingmaterials(pedido);
			response = true;
		} catch (Exception e) {
			// TODO: handle exception
			response = false;
		}

		return response;
	}

}
