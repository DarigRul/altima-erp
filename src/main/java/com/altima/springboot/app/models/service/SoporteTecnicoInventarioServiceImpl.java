package com.altima.springboot.app.models.service;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.altima.springboot.app.models.entity.SoporteTecnicoEquipoMantenimiento;
import com.altima.springboot.app.models.entity.SoporteTecnicoInventario;
import com.altima.springboot.app.repository.SoporteTecnicoInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoporteTecnicoInventarioServiceImpl implements ISoporteTecnicoInventarioService{

    @Autowired
    private SoporteTecnicoInventarioRepository inventario;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(SoporteTecnicoInventario inven) {
        inventario.save(inven);
        
    }

    @Override
    public SoporteTecnicoInventario findOne(Long id) {
        return inventario.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view() {
        List <Object []> re = null;
        re = em.createNativeQuery("SELECT "+
        "look.nombre_lookup,inventario.provedor,inventario.fecha,inventario.marca,inventario.modelo,inventario.serie ,inventario.procesador ,inventario.disco_duro,inventario.pantalla,inventario.ns_pantalla,inventario.direccion_ip,'fechaaaa' as fecha33,'fecheeeee' as fecha3333,empleado.nombre_persona,inventario.estatus,inventario.creado_por,inventario.fecha_creacion,inventario.actualizado_por,inventario.ultima_fecha_modificacion ,inventario.id_inventario_equipo "+
        "FROM "+
        "alt_soporte_tecnico_inventario as inventario "+
        "INNER JOIN alt_soporte_tecnico_lookup look on look.id_lookup = inventario.tipo "+
        "INNER JOIN alt_hr_empleado empleado on empleado.id_empleado = inventario.asignado_a").getResultList();
        return re;
    }

    @Override
    @Transactional
    public void save(SoporteTecnicoEquipoMantenimiento obj) {
        em.persist(obj);
        
        
    }
    
}
