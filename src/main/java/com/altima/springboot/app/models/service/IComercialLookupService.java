package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.ComercialLookup;

public interface IComercialLookupService {

    List<ComercialLookup> findAll();

    ComercialLookup findOne(Long id);

    void save(ComercialLookup comercialLookup);

    void delete(Long id);

    List<ComercialLookup> findByTipoLookup(String tipoLookup);

    public List<ComercialLookup> findByTipoLookupAndEstatus(String tipoLookup);
}
