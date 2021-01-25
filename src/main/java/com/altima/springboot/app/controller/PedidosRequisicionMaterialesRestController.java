package com.altima.springboot.app.controller;

import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.dto.MaterialFaltanteListDto;
import com.altima.springboot.app.models.entity.AmpMaterialFaltante;
import com.altima.springboot.app.models.entity.ComprasOrden;
import com.altima.springboot.app.models.entity.ComprasOrdenDetalle;
import com.altima.springboot.app.models.service.IPedidosRequisicionMaterialesService;

@RestController
public class PedidosRequisicionMaterialesRestController {

	@Autowired
	public IPedidosRequisicionMaterialesService RequisicionMaterialesService;
	
	 @GetMapping("/getMaterialesFaltantesByIds")
		public ResponseEntity<?> getCliente(@RequestParam String ids) {
	        System.out.println(ids);
			Map<String, Object> response = new HashMap<>();
			List<MaterialFaltanteListDto> materiales =null;
			try {
				materiales =RequisicionMaterialesService.findAllMaterialesFaltantes(ids);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la BD");
				response.put("error", e.getMessage()+": "+e.getMostSpecificCause().getMessage());
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(materiales.size()==0){
				response.put("mensaje", "No existen materiales faltantes");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<MaterialFaltanteListDto>>(materiales,HttpStatus.OK);
		}
	
	 
	 @Transactional
	    @PostMapping("/postOrdenCompraMaterial")
	    public ResponseEntity<?> postOrdenCompra(@RequestParam String ordenCompraDetalle,@RequestParam Long idProveedor,@RequestParam float iva,@RequestParam String ids) {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        JSONArray ordenArray = new JSONArray(ordenCompraDetalle);
	        Formatter fmt = new Formatter();
	        try {
	            ComprasOrden orden =new ComprasOrden();
	            orden.setCreadoPor(auth.getName());
	            orden.setActualizadoPor(auth.getName());
	            orden.setEstatus(1);
	            orden.setIdProveedor(idProveedor);
	            orden.setIdText("idText");
	            orden.setIva(iva);
	            RequisicionMaterialesService.save(orden);
	            orden.setIdText("ORDC"+fmt.format("%05d", orden.getIdOrdenCompras()));
	            RequisicionMaterialesService.save(orden);
	            for (int i = 0; i < ordenArray.length(); i++) {
	                JSONObject ordenJson = ordenArray.getJSONObject(i);
	                ComprasOrdenDetalle ordenDetalle =new ComprasOrdenDetalle();
	                ordenDetalle.setCreadoPor(auth.getName());
	                ordenDetalle.setActualizadoPor(auth.getName());
	                ordenDetalle.setTipoMaterial("t");
	                ordenDetalle.setIdMaterial(ordenJson.getLong("idMaterial"));
	                ordenDetalle.setIdOrdenCompras(orden.getIdOrdenCompras());
	                ordenDetalle.setCantidad(ordenJson.getFloat("cantidad")+ordenJson.getFloat("cantidadExtra"));
	                ordenDetalle.setPrecioUnitario(ordenJson.getFloat("precioU")+ordenJson.getFloat("montoCD"));
	                RequisicionMaterialesService.save(ordenDetalle);
	            }
	            String[] idsArray=ids.split(",");
	            for (String id : idsArray) {
	                AmpMaterialFaltante materialFaltante=RequisicionMaterialesService.findOne(Long.parseLong(id));
	                materialFaltante.setEstatus(1);
	                materialFaltante.setIdOrdenCompras(orden.getIdOrdenCompras());
	                RequisicionMaterialesService.save(materialFaltante);
	            }
	            fmt.close();
	        } catch (Exception e) {
	            // TODO: handle exception
	            System.err.println(e);
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        return new ResponseEntity<>(HttpStatus.OK);
	    }

	
	
}
