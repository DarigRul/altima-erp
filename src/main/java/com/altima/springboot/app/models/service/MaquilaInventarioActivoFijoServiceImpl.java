package com.altima.springboot.app.models.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.altima.springboot.app.models.entity.MaquilaComponenteInventario;
import com.altima.springboot.app.models.entity.MaquilaInventarioActivoFijo;
import com.altima.springboot.app.repository.MaquilaComponenteInventarioRepository;
import com.altima.springboot.app.repository.MaquilaInventarioActivoFijoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaquilaInventarioActivoFijoServiceImpl implements IMaquilaInventarioActivoFijoService {

    @PersistenceContext
	private EntityManager em;

	@Autowired
    private MaquilaInventarioActivoFijoRepository repository;

    @Autowired
    private MaquilaComponenteInventarioRepository repository2;

    @Override
	@Transactional(readOnly = true)
    public List<MaquilaInventarioActivoFijo> findAll() {
        return (List<MaquilaInventarioActivoFijo>) repository.findAll();
    }

    @Override
	@Transactional
    public void save(MaquilaInventarioActivoFijo obj) {
        repository.save(obj);

    }

    @Override
	@Transactional
    public void delete(Long id) {
        repository.deleteById(id);

    }

    @Override
	@Transactional
    public MaquilaInventarioActivoFijo findOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> view() {
        List<Object[]> re = null;
            re = em.createNativeQuery(""+
                "SELECT\r\n" + 
                    "inven.id_inventario,\r\n" + 
                    "inven.clave,\r\n" + 
                    "inven.activo_fijo,\r\n" + 
                    "look.nombre_lookup,\r\n" + 
                    "inven.modelo,\r\n" + 
                    "inven.marca,\r\n" + 
                    "inven.serie,\r\n" + 
                    "inven.motor,\r\n" + 
                    "inven.fecha_ingreso,\r\n" + 
                    "inven.fecha_bajo,\r\n" + 
                "CASE\r\n" + 
                        "inven.estatus\r\n" + 
                       "WHEN 1 THEN\r\n" + 
                        "'Activo' ELSE 'Inactivo'\r\n" + 
                "END\r\n" + 
                    "FROM\r\n" + 
                    "alt_maquila_inventario_activo_fijo AS inven\r\n" + 
                    "INNER JOIN alt_maquila_lookup look ON look.id_lookup = inven.id_lookup").getResultList();

		return re;
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Object[]> viewComponentes(Long id) {
        List<Object[]> re = null;
            re = em.createNativeQuery(""+
                "SELECT\r\n" + 
                    "inven.id_componente_inventario,\r\n" + 
                    "look.nombre_lookup,\r\n" + 
                    "inven.marca,\r\n" + 
                    "inven.cantidad\r\n" + 
                "FROM\r\n" + 
                    "alt_maquila_componente_inventario as inven\r\n" + 
                    "INNER JOIN alt_maquila_lookup look on look.id_lookup = inven.id_lookup\r\n" + 
                "WHERE\r\n" + 
                    "1=1\r\n" + 
                    "AND look.tipo_lookup='Componente'\r\n" + 
                    "AND inven.id_inventario="+id).getResultList();

		return re;
    }

    @Override
	@Transactional
    public void saveComponente(MaquilaComponenteInventario obj) {
        repository2.save(obj);

    }

    @Override
	@Transactional
    public void deleteComponentes(Long id) {
        repository2.deleteById(id);

    }
    
}
