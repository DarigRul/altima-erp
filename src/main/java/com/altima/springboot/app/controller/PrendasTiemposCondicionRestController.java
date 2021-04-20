package com.altima.springboot.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.altima.springboot.app.models.entity.ProduccionPrendasTiemposCondicion;
import com.altima.springboot.app.models.service.IProduccionPrendasTiemposCondicionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prendasTiemposCondicion")
public class PrendasTiemposCondicionRestController {

    @Autowired
    IProduccionPrendasTiemposCondicionService prendasTiemposCondicionService;

    @GetMapping("/")
    public List<ProduccionPrendasTiemposCondicion> getPrendas() {
        return prendasTiemposCondicionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrenda(@PathVariable(name = "id") Long id) {
        Map<String, Object> response = new HashMap<>();
        ProduccionPrendasTiemposCondicion prenda = null;
        try {
            prenda = prendasTiemposCondicionService.findOne(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (prenda == null) {
            response.put("mensaje", "La registro con el id " + id + " no existe");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProduccionPrendasTiemposCondicion>(prenda, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> postPrenda(@RequestBody ProduccionPrendasTiemposCondicion prenda) {
        Map<String, Object> response = new HashMap<>();
        ProduccionPrendasTiemposCondicion newPrenda = null;
        try {
            prendasTiemposCondicionService.save(prenda);
        } catch (DataAccessException e) {
            System.out.println("error");
            response.put("mensaje", "Error al insertar en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProduccionPrendasTiemposCondicion>(newPrenda, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deletePrenda(@PathVariable(name = "id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            prendasTiemposCondicionService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar registro en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El registro con el id " + id + " fue eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
