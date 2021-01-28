package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.MaquilaComponenteInventario;
import com.altima.springboot.app.models.entity.MaquilaInventarioActivoFijo;


public interface IMaquilaInventarioActivoFijoService {
    
    List<MaquilaInventarioActivoFijo> findAll();
    void save(MaquilaInventarioActivoFijo obj);
    void delete(Long id);
    MaquilaInventarioActivoFijo findOne(Long id);
    List<Object []> view();
    List<Object []> viewComponentes(Long id);

    void saveComponente(MaquilaComponenteInventario obj);
    void deleteComponentes(Long id);

}
