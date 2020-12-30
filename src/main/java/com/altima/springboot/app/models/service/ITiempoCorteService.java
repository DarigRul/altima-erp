package com.altima.springboot.app.models.service;
import java.util.List;
public interface ITiempoCorteService {

    List<Object []> view ();
    
    List<Object []> detallesFolio (String folio);
    
}
