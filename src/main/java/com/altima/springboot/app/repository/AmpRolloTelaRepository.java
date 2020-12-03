package com.altima.springboot.app.repository;

import java.util.List;

import com.altima.springboot.app.models.entity.AmpRolloTela;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmpRolloTelaRepository extends CrudRepository<AmpRolloTela,Long>{
    
    List<AmpRolloTela> findByIdAlmacenFisicoAndEstatusAndIdTela(Long idAlmacenFisico,String estatus,Long idTela);

    List<AmpRolloTela> findByIdAlmacenLogicoAndEstatusAndIdTela(Long idAlmacenLogico,String estatus,Long idTela);

    List<AmpRolloTela> findByEstatusAndIdTela(String estatus,Long idTela);

    List<AmpRolloTela> findByEstatusAndIdPedidoAndIdTela(String estatus,Long idPedido,Long idTela);

}
