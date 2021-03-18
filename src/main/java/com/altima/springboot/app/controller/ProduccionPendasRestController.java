package com.altima.springboot.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.altima.springboot.app.models.entity.ProduccionMiniTrazo;
import com.altima.springboot.app.models.entity.ProduccionPrendaHistorico;
import com.altima.springboot.app.models.service.IProduccionMiniTrazoService;
import com.altima.springboot.app.models.service.IProduccionPrendaHistoricoService;
import com.altima.springboot.app.models.service.UploadServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProduccionPendasRestController {

    @Autowired
    IProduccionPrendaHistoricoService prendaHistoricoService;

    @Autowired
    UploadServiceImpl uService;

    @Autowired
    IProduccionMiniTrazoService miniTrazoService;

    @PostMapping("/guardarMiniTrazo")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<?> guardarImagenes(@RequestParam Long idPrenda, @RequestParam String descripcion,
            @RequestParam MultipartFile imagenMiniTrazo) {

        ProduccionMiniTrazo miniTrazo = new ProduccionMiniTrazo();
        System.out.println(idPrenda + " " + descripcion);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Cloudinary cloudinary = uService.CloudinaryApi();
        String uniqueFilename;
        Map<String, Object> response = new HashMap<>();
        try {
            uniqueFilename = uService.copy2(imagenMiniTrazo);
            miniTrazo.setIdPrenda(idPrenda);
            miniTrazo.setRuta(uniqueFilename);
            miniTrazo.setDescripcion(descripcion);
            miniTrazo.setCreadoPor(auth.getName());
            miniTrazo.setActualizadoPor(auth.getName());
            cloudinary.uploader().upload(uService.filePrenda(uniqueFilename), ObjectUtils.asMap("public_id",
                    "miniTrazo/" + uniqueFilename.substring(0, uniqueFilename.length() - 4)));
            miniTrazoService.save(miniTrazo);
        }

        catch (IOException e) {
            response.put("mensaje", "Error al insertar en la BD");
            response.put("error", e.getMessage() + ": " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProduccionMiniTrazo>(miniTrazo, HttpStatus.CREATED);
    }

    @GetMapping("getPrendaHistorico/{id}")
    public List<ProduccionPrendaHistorico> getPrendaHistorico(@PathVariable Long id) {

        return prendaHistoricoService.findByIdPrenda(id);
    }

    @GetMapping("getMiniTrazoByIdPrenda/{id}")
    public ResponseEntity<?> getMiniTrazoByIdPrenda(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        List<ProduccionMiniTrazo> miniTrazo = null;
        try {
            miniTrazo = miniTrazoService.findByIdPrenda(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (miniTrazo.size() == 0) {
            response.put("mensaje", "La prenda con el id " + id + " no tiene mini trazo");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<ProduccionMiniTrazo>>(miniTrazo, HttpStatus.OK);
    }

    @DeleteMapping("deleteMiniTrazo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteEmpresa(@PathVariable(name = "id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Cloudinary cloudinary = uService.CloudinaryApi();
        try {
            ProduccionMiniTrazo miniTrazo=miniTrazoService.findOne(id);
            cloudinary.uploader().destroy("miniTrazo/" + miniTrazo.getRuta().substring(0, miniTrazo.getRuta().length() - 4)
            , ObjectUtils.asMap("resourceType", "image"));
            miniTrazoService.delete(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar mini trazo en la BD");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            response.put("mensaje", "Error al subir la imagen");
            response.put("error", e.getMessage() + ": " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La mini trazo fue eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
