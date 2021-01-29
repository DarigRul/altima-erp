package com.altima.springboot.app.models.service;

import com.altima.springboot.app.models.entity.MaquilaPrendaOperacion;
import java.util.List;
public interface IMaquilaPrendaOperacionService {
    
    void save(MaquilaPrendaOperacion lookup);

    void delete(Long id);
    
    List<Object []> view(Long id);

    List<Object []> SelectOperacion(Long famila, Long prenda);

}
