package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.SoporteTecnicoEquipoMantenimiento;
import com.altima.springboot.app.models.entity.SoporteTecnicoInventario;

public interface ISoporteTecnicoInventarioService {


    void save (SoporteTecnicoInventario inven);
    
    
    SoporteTecnicoInventario findOne (Long id);

    List<Object []> view (); 

    void save (SoporteTecnicoEquipoMantenimiento obj);



}
