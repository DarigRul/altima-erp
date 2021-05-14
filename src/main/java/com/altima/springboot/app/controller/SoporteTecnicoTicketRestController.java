package com.altima.springboot.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EnumType;

import com.altima.springboot.app.dto.SoporteTecnicoTicketDto;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicket;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicketSeguimiento;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicket.Prioridad;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicketSeguimiento.Estatus;
import com.altima.springboot.app.models.service.ISoporteTecnicoTicketSeguimientoService;
import com.altima.springboot.app.models.service.ISoporteTecnicoTicketService;
import com.altima.springboot.app.models.service.UploadServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/soporte-tecnico-ticket")
public class SoporteTecnicoTicketRestController {

    @Autowired
    UploadServiceImpl uService;
    @Autowired
    ISoporteTecnicoTicketService soporteTecnicoTicketService;
    @Autowired
    ISoporteTecnicoTicketSeguimientoService soporteTecnicoTicketSeguimientoService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> postTicket(@RequestParam Long categoria, @RequestParam String titulo,
            @RequestParam String comentarios, @RequestParam MultipartFile archivoTicket) throws IOException {
        Map<String, Object> response = new HashMap<>();
        Cloudinary cloudinary = uService.CloudinaryApi();
        SoporteTecnicoTicket ticket = new SoporteTecnicoTicket();
        try {
            ticket.setCalificacion(0);
            ticket.setComentario(comentarios);
            ticket.setTitulo(titulo);
            ticket.setIdCategoria(categoria);
            ticket.setPrioridad(Prioridad.Normal);
            String uniqueFilename = uService.copy2(archivoTicket);
            ticket.setNombreArchivo(uniqueFilename);
            cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
                    "soporteTecnico/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
            soporteTecnicoTicketService.save(ticket);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al insertar en la BD");
            response.put("error",
                    e.getMessage() + ": " + ((NestedRuntimeException) e).getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> putTicket(@RequestParam Long idTicket, @RequestParam Long categoria,
            @RequestParam String titulo, @RequestParam String comentarios) throws IOException {
        Map<String, Object> response = new HashMap<>();
        SoporteTecnicoTicket ticket = soporteTecnicoTicketService.findOne(idTicket);
        try {
            ticket.setComentario(comentarios);
            ticket.setTitulo(titulo);
            ticket.setIdCategoria(categoria);
            // String uniqueFilename = uService.copy2(archivoTicket);
            // ticket.setNombreArchivo(uniqueFilename);
            soporteTecnicoTicketService.save(ticket);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al insertar en la BD");
            response.put("error",
                    e.getMessage() + ": " + ((NestedRuntimeException) e).getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable(name = "id") Long id) {
        Map<String, Object> response = new HashMap<>();
        SoporteTecnicoTicket ticket = null;
        try {
            ticket = soporteTecnicoTicketService.findOne(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (ticket == null) {
            response.put("mensaje", "El ticket con el id " + id + " no existe");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SoporteTecnicoTicket>(ticket, HttpStatus.OK);
    }

    @GetMapping("seguimiento/{id}")
    public ResponseEntity<?> getTicketDetalle(@PathVariable(name = "id") Long id) {
        Map<String, Object> response = new HashMap<>();
        SoporteTecnicoTicketDto ticket = null;
        try {
            ticket = soporteTecnicoTicketService.findTicket(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (ticket == null) {
            response.put("mensaje", "El ticket con el id " + id + " no existe");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SoporteTecnicoTicketDto>(ticket, HttpStatus.OK);
    }

    @PostMapping("seguimiento/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> postSeguimiento(@RequestBody String seguimientoString) throws IOException {
        Map<String, Object> response = new HashMap<>();
        SoporteTecnicoTicketSeguimiento seguimiento = new SoporteTecnicoTicketSeguimiento();
        try {
            JSONObject seguimientoJson = new JSONObject(seguimientoString);
            System.out.println(seguimientoJson.getString("comentario"));
            seguimiento.setComentarios(seguimientoJson.getString("comentario"));
            seguimiento.setIdTicket(seguimientoJson.getLong("idTicket"));
            seguimiento.setEstatus(Estatus.valueOf(seguimientoJson.getString("estado")));
            System.out.println(seguimiento.toString());
            soporteTecnicoTicketSeguimientoService.save(seguimiento);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al insertar en la BD");
            response.put("error",
                    e.getMessage() + ": " + ((NestedRuntimeException) e).getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("seguimientoTicket/{id}")
    public ResponseEntity<?> getSeguimientoTicket(@PathVariable(name = "id") Long id) {
        Map<String, Object> response = new HashMap<>();
        List<SoporteTecnicoTicketDto> seguimientoTicket = null;
        try {
            seguimientoTicket = soporteTecnicoTicketSeguimientoService.findSeguimientoByIdTicket(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (seguimientoTicket == null) {
            response.put("mensaje", "El seguimiento con el id " + id + " no existe");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<SoporteTecnicoTicketDto>>(seguimientoTicket, HttpStatus.OK);
    }

    @PutMapping("prioridad/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> putPrioridad(@PathVariable Long id) throws IOException {
        Map<String, Object> response = new HashMap<>();
        SoporteTecnicoTicket ticket = soporteTecnicoTicketService.findOne(id);
        try {
            ticket.setPrioridad(Prioridad.Alta);
            soporteTecnicoTicketService.save(ticket);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al insertar en la BD");
            response.put("error",
                    e.getMessage() + ": " + ((NestedRuntimeException) e).getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("calificacion/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> putCalificacion(@PathVariable Long id,@RequestParam Integer calificacion) throws IOException {
        Map<String, Object> response = new HashMap<>();
        SoporteTecnicoTicket ticket = soporteTecnicoTicketService.findOne(id);
        try {
            ticket.setCalificacion(calificacion);
            soporteTecnicoTicketService.save(ticket);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al insertar en la BD");
            response.put("error",
                    e.getMessage() + ": " + ((NestedRuntimeException) e).getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

}
