package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.altima.springboot.app.models.entity.HrLookup;
@Repository
public interface HrLookupRepository extends CrudRepository<HrLookup, Long>{
    List<HrLookup> findBytipoLookup(String tipoLookup);
}
