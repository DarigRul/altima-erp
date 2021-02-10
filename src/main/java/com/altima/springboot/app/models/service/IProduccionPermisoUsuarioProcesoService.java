package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ProduccionPermisoUsuarioProceso;

public interface IProduccionPermisoUsuarioProcesoService {

    void save(ProduccionPermisoUsuarioProceso permiso);

	void delete(Long id);

	ProduccionPermisoUsuarioProceso findOne(Long id);

    List<Object []> SelectUsuariosDisponibles(Long idProceso);

    List<Object []> view (Long idProceso);

    List<Object []> listarProcesosDisponiblesUser (Long idUser);

    List<Object []> listarProcesosDisponiblesAdmin ();
    
}
