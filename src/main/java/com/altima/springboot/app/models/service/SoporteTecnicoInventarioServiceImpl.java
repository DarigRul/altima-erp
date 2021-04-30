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
        re = em.createNativeQuery(""
        		+ "SELECT\r\n" + 
        		"	look.nombre_lookup,\r\n" + 
        		"	inventario.provedor,\r\n" + 
        		"	inventario.fecha,\r\n" + 
        		"	inventario.marca,\r\n" + 
        		"	inventario.modelo,\r\n" + 
        		"	inventario.serie,\r\n" + 
        		"	inventario.procesador,\r\n" + 
        		"	inventario.disco_duro,\r\n" + 
        		"	inventario.pantalla,\r\n" + 
        		"	inventario.ns_pantalla,\r\n" + 
        		"	inventario.direccion_ip,\r\n" + 
        		"	(\r\n" + 
        		"	SELECT\r\n" + 
        		"		IFNULL( m.fecha, 'Sin fecha' ) \r\n" + 
        		"	FROM\r\n" + 
        		"		alt_soporte_tecnico_equipo_mantenimiento AS m \r\n" + 
        		"	WHERE\r\n" + 
        		"		m.id_equipo = inventario.id_inventario_equipo \r\n" + 
        		"	ORDER BY\r\n" + 
        		"		m.id_mantenimiento ASC \r\n" + 
        		"		LIMIT 1 \r\n" + 
        		"	),\r\n" + 
        		"	(\r\n" + 
        		"	SELECT\r\n" + 
        		"		IFNULL( m.fecha_proxima, 'Sin fecha' ) \r\n" + 
        		"	FROM\r\n" + 
        		"		alt_soporte_tecnico_equipo_mantenimiento AS m \r\n" + 
        		"	WHERE\r\n" + 
        		"		m.id_equipo = inventario.id_inventario_equipo \r\n" + 
        		"	ORDER BY\r\n" + 
        		"		m.id_mantenimiento ASC \r\n" + 
        		"		LIMIT 1 \r\n" + 
        		"	),\r\n" + 
        		"	empleado.nombre_persona,\r\n" + 
        		"	inventario.estatus,\r\n" + 
        		"	inventario.creado_por,\r\n" + 
        		"	inventario.fecha_creacion,\r\n" + 
        		"	inventario.actualizado_por,\r\n" + 
        		"	inventario.ultima_fecha_modificacion,\r\n" + 
        		"	inventario.id_inventario_equipo \r\n" + 
        		"FROM\r\n" + 
        		"	alt_soporte_tecnico_inventario AS inventario\r\n" + 
        		"	INNER JOIN alt_soporte_tecnico_lookup look ON look.id_lookup = inventario.tipo\r\n" + 
        		"	INNER JOIN alt_hr_empleado empleado ON empleado.id_empleado = inventario.asignado_a\r\n" + 
        		"	ORDER BY inventario.id_inventario_equipo desc").getResultList();
        return re;
    }

    @Override
    @Transactional
    public void save(SoporteTecnicoEquipoMantenimiento obj) {
        em.persist(obj);
        
        
    }
    
}
