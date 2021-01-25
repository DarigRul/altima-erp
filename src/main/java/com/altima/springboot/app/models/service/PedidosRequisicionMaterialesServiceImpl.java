package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.dto.MaterialFaltanteListDto;
import com.altima.springboot.app.dto.TelaFaltanteListDto;
import com.altima.springboot.app.models.entity.AmpMaterialFaltante;
import com.altima.springboot.app.models.entity.ComprasOrden;
import com.altima.springboot.app.models.entity.ComprasOrdenDetalle;
import com.altima.springboot.app.repository.AmpMaterialFaltanteRepository;
import com.altima.springboot.app.repository.ComprasOrdenDetalleRepository;
import com.altima.springboot.app.repository.ComprasOrdenRepository;
import com.altima.springboot.app.repository.AmpMaterialFaltanteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidosRequisicionMaterialesServiceImpl implements IPedidosRequisicionMaterialesService {

    @Autowired
    AmpMaterialFaltanteRepository repository;
    
    @Autowired
    ComprasOrdenRepository repositoryordencompras;
    
    @Autowired
    ComprasOrdenDetalleRepository repositoryordendetalle;

    @PersistenceContext
    EntityManager em;

    
	@Override
    @Transactional(readOnly = true)
    public List<AmpMaterialFaltante> findAll() {
        // TODO Auto-generated method stub
        return (List<AmpMaterialFaltante>) repository.findAll();
    }

    
	@Override
    @Transactional
    public void save(AmpMaterialFaltante material) {
        // TODO Auto-generated method stub
        repository.save(material);
    }

    
	@Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    
	@Override
    @Transactional
    public AmpMaterialFaltante findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    
	@SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<MaterialFaltanteListDto> findAllMaterialesFaltantes() {
        // TODO Auto-generated method stub
        return em.createNativeQuery("select * from alt_view_material_faltante;",MaterialFaltanteListDto.class).getResultList();
    }
	
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<MaterialFaltanteListDto> findAllMaterialesFaltantes(String ids) {
		// TODO Auto-generated method stub
		String[] idsArray=ids.split(",");
		String where="";
		for (String id : idsArray) {
			where=where+" or id_material_faltante="+id;
		}

		return em.createNativeQuery("SELECT `id_material_faltante`,`id_text_pedido`,`clave_material`,`cliente`,`fecha_entrega`,sum(`cantidad`) cantidad,`fecha_oc`,`folio_oc`,`fecha_promesa`,`estatus`,`estatus_comercial`,`nombre_material`,`id_material`,`id_proveedor`,`nombre_proveedor`,'color','fecha_requisicion','id_text_proveedor','tamanio','precio_unitario' FROM `alt_view_material_faltante` where 1=2"+where+" GROUP by `id_material`",MaterialFaltanteListDto.class).getResultList();	
	}


	@Override
	public void save(ComprasOrden orden) {
		// TODO Auto-generated method stub
		repositoryordencompras.save(orden);
	}


	@Override
	public void save(ComprasOrdenDetalle ordenDetalle) {
		// TODO Auto-generated method stub
		repositoryordendetalle.save(ordenDetalle);
	}
    
}
