package com.altima.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.ComercialClienteSucursal;
import com.altima.springboot.app.models.entity.ComercialConcentradoTalla;
import com.altima.springboot.app.models.entity.DisenioCalidad;
import com.altima.springboot.app.models.entity.DisenioLookup;
import com.altima.springboot.app.repository.CatalogoRepository;
import com.altima.springboot.app.repository.ConcentradoTallaRepository;

@Service
public class ComercialConcentradoTallaServiceImpl implements IComercialConcentradoTallaService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ConcentradoTallaRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<ComercialConcentradoTalla> findAll() {
		// TODO Auto-generated method stub
		return (List<ComercialConcentradoTalla>) repository.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ComercialConcentradoTalla> findDuplicates(String Empleado, String Largo, String PrendaCliente,
			String Talla, String Pulgadas, String especificacion) {
		// TODO Auto-generated method stub
		return em.createQuery("from ComercialConcentradoTalla where IdEmpleadoPedido=" + Empleado
				+ " and IdPrendaCliente=" + PrendaCliente + " and IdTalla=" + Talla + " and IdLargo=" + Largo
				+ " and Pulgadas=" + Pulgadas + " and Especificacion=" + especificacion + " ").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ComercialConcentradoTalla> findDuplicates(String Empleado, String PrendaCliente,
			String especificacion) {
		// TODO Auto-generated method stub
		return em
				.createQuery("from ComercialConcentradoTalla where IdEmpleadoPedido=" + Empleado
						+ " and IdPrendaCliente=" + PrendaCliente + " and Especificacion=" + especificacion + " ")
				.getResultList();
	}

	@Override
	@Transactional
	public void save(ComercialConcentradoTalla comercialconcentradotalla) {
		// TODO Auto-generated method stub
		repository.save(comercialconcentradotalla);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteprenda(Long id_empleado, Long id_prenda) {
		// TODO Auto-generated method stub
		em.createNativeQuery("Delete from alt_comercial_concentrado_tallas where id_empleado_pedido=" + id_empleado
				+ " and id_prenda_cliente=" + id_prenda + " ").executeUpdate();

	}

	@Override
	@Transactional
	public void deleteall(Long id_empleado) {
		// TODO Auto-generated method stub
		em.createNativeQuery(
				"Delete from alt_comercial_concentrado_tallas where id_empleado_pedido=" + id_empleado + "")
				.executeUpdate();

	}

	@Override
	@Transactional
	public ComercialConcentradoTalla findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	public String genpivot(List<String> list) {
		String res = null;
		try {
			List<String> query = new ArrayList<>();
			for (String string : list) {
				query.add("MAX(case when nombre_prenda='" + string + "' then query3.talla else NULL end) as '" + string
						+ "'");

			}
			String delim = ",";

			StringBuilder sb = new StringBuilder();

			int i = 0;
			while (i < query.size() - 1) {
				sb.append(query.get(i));
				sb.append(delim);
				i++;
			}
			sb.append(query.get(i));

			res = sb.toString();
			return res;
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findPrendaCliente(Long idpedido) {
		// TODO Auto-generated method stub

		return em.createNativeQuery(
				"SELECT coor_prenda.id_coordinado_prenda,SUBSTRING_INDEX(look.nombre_lookup, ' ',1)as 'nombre_prenda', prenda.descripcion_prenda, tela.nombre_tela, IF(coor_prenda.estatus = true, '1', '0') \r\n"
						+ "			From alt_comercial_coordinado,alt_disenio_lookup as look, alt_disenio_prenda as prenda, alt_disenio_tela as tela, alt_comercial_coordinado_prenda as coor_prenda\r\n"
						+ "				where 1=1\r\n" + "				AND coor_prenda.id_tela = tela.id_tela\r\n"
						+ "				AND coor_prenda.id_prenda= prenda.id_prenda \r\n"
						+ "				AND coor_prenda.id_familia_genero=look.id_lookup \r\n"
						+ "				AND coor_prenda.estatus=1\r\n"
						+ "				and coor_prenda.id_coordinado=alt_comercial_coordinado.id_coordinado\r\n"
						+ "				and alt_comercial_coordinado.id_pedido=" + idpedido + "\r\n" + ///// cambiar por
																										///// id_pedido
						"                GROUP BY look.nombre_lookup ORDER BY FIELD(nombre_prenda,'Vestido','Sweater','Gabardina','Abrigo','Camisa','Blusa','Chaleco','Falda','Pantalón','Saco')DESC")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findTallasPrendaEspecificacion(Long idpedido, Long idempleado, Long idprenda) {
		return em.createNativeQuery("select \r\n" + "alt_comercial_concentrado_tallas.*, \r\n"
				+ "alt_servicio_cliente_lookup.nombre_lookup as especificaciones, \r\n" + "l2.nombre_lookup as talla, \r\n"
				+ "l3.nombre_lookup as largo, \r\n" 
				+ "query.nombre_prenda \r\n" 
				+ "from \r\n"
				+ "(SELECT coor_prenda.id_coordinado_prenda,SUBSTRING_INDEX(look.nombre_lookup, ' ',1)as 'nombre_prenda', prenda.descripcion_prenda, tela.nombre_tela, IF(coor_prenda.estatus = true, '1', '0') \r\n"
				+ "From alt_comercial_coordinado,alt_disenio_lookup as look, alt_disenio_prenda as prenda, alt_disenio_tela as tela, alt_comercial_coordinado_prenda as coor_prenda \r\n"
				+ "				where \r\n" + "				coor_prenda.id_tela = tela.id_tela \r\n"
				+ "				AND coor_prenda.id_prenda= prenda.id_prenda \r\n"
				+ "				AND coor_prenda.id_familia_genero=look.id_lookup \r\n"
				+ "				AND coor_prenda.estatus=1 \r\n"
				+ "				and coor_prenda.id_coordinado=alt_comercial_coordinado.id_coordinado \r\n"
				+ "				and alt_comercial_coordinado.id_pedido=" + idpedido + " \r\n"
				+ "               GROUP BY look.nombre_lookup)as query, \r\n" + "alt_comercial_cliente_empleado, \r\n"
				+ "alt_comercial_concentrado_tallas, \r\n" + "alt_servicio_cliente_lookup, \r\n"
				+ "alt_produccion_lookup l2, \r\n" + "alt_produccion_lookup l3 \r\n" + "where \r\n"
				+ "query.id_coordinado_prenda=alt_comercial_concentrado_tallas.id_prenda_cliente \r\n" + "and \r\n"
				+ "alt_comercial_concentrado_tallas.id_empleado_pedido=alt_comercial_cliente_empleado.id_empleado \r\n"
				+ "and \r\n" + "alt_comercial_cliente_empleado.id_pedido_informacion=" + idpedido + " \r\n" + "and \r\n"
				+ "alt_comercial_concentrado_tallas.especificacion=alt_servicio_cliente_lookup.id_lookup \r\n" + "and \r\n"
				+ "alt_comercial_concentrado_tallas.id_talla=l2.id_lookup \r\n" + "and \r\n"
				+ "alt_comercial_concentrado_tallas.id_largo=l3.id_lookup \r\n" + "and \r\n"
				+ "alt_comercial_concentrado_tallas.id_empleado_pedido=" + idempleado + " \r\n" + "AND \r\n"
				+ "alt_comercial_concentrado_tallas.id_prenda_cliente=" + idprenda + "").getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findPrenda(Long idpedido, Long idempleado) {

		return em.createNativeQuery("select\r\n" + 
				"   query2.id_empleado_pedido,\r\n" + 
				"   query2.id_prenda_cliente,\r\n" + 
				"   query2.nombre_prenda,\r\n" + 
				"   query2.talla,\r\n" + 
				"   query2.largo\r\n" + 
				"   #,GROUP_CONCAT(DISTINCT query2.pulgadas, query2.especificaciones \r\n" + 
				"#ORDER BY\r\n" + 
				" #  query2.especificaciones DESC SEPARATOR ', ')as especificacionesdesc \r\n" + 
				"from\r\n" + 
				"   (\r\n" + 
				"      select\r\n" + 
				"         alt_comercial_concentrado_tallas.*,\r\n" + 
				"        # alt_comercial_lookup.nombre_lookup as especificaciones,\r\n" + 
				"         l2.nombre_lookup as talla,\r\n" + 
				"         l3.nombre_lookup as largo,\r\n" + 
				"         query.nombre_prenda \r\n" + 
				"      from\r\n" + 
				"         (\r\n" + 
				"            SELECT\r\n" + 
				"               coor_prenda.id_coordinado_prenda,\r\n" + 
				"               SUBSTRING_INDEX(look.nombre_lookup, ' ', 1)as 'nombre_prenda',\r\n" + 
				"               prenda.descripcion_prenda,\r\n" + 
				"               tela.nombre_tela,\r\n" + 
				"               IF(coor_prenda.estatus = true, '1', '0') \r\n" + 
				"            From\r\n" + 
				"               alt_comercial_coordinado,\r\n" + 
				"               alt_disenio_lookup as look,\r\n" + 
				"               alt_disenio_prenda as prenda,\r\n" + 
				"               alt_disenio_tela as tela,\r\n" + 
				"               alt_comercial_coordinado_prenda as coor_prenda \r\n" + 
				"            where\r\n" + 
				"               coor_prenda.id_tela = tela.id_tela \r\n" + 
				"               AND coor_prenda.id_prenda = prenda.id_prenda \r\n" + 
				"               AND coor_prenda.id_familia_genero = look.id_lookup \r\n" + 
				"               AND coor_prenda.estatus = 1 \r\n" + 
				"               and coor_prenda.id_coordinado = alt_comercial_coordinado.id_coordinado \r\n" + 
				"               and alt_comercial_coordinado.id_pedido = "+idpedido+" \r\n" + 
				"            GROUP BY\r\n" + 
				"               look.nombre_lookup\r\n" + 
				"         )\r\n" + 
				"         as query,\r\n" + 
				"         alt_comercial_cliente_empleado,\r\n" + 
				"         alt_comercial_concentrado_tallas,\r\n" + 
				"         #alt_comercial_lookup,\r\n" + 
				"         alt_produccion_lookup l2,\r\n" + 
				"         alt_produccion_lookup l3 \r\n" + 
				"      where\r\n" + 
				"         query.id_coordinado_prenda = alt_comercial_concentrado_tallas.id_prenda_cliente \r\n" + 
				"         and alt_comercial_concentrado_tallas.id_empleado_pedido = alt_comercial_cliente_empleado.id_empleado \r\n" + 
				"         and alt_comercial_cliente_empleado.id_pedido_informacion = "+idpedido+" \r\n" + 
				"         #and alt_comercial_concentrado_tallas.especificacion = alt_comercial_lookup.id_lookup \r\n" + 
				"         and alt_comercial_concentrado_tallas.id_talla = l2.id_lookup \r\n" + 
				"         and alt_comercial_concentrado_tallas.id_largo = l3.id_lookup \r\n" + 
				"         and alt_comercial_concentrado_tallas.id_empleado_pedido = "+idempleado+"\r\n" + 
				"   )\r\n" + 
				"   as query2 \r\n" + 
				"GROUP by\r\n" + 
				"   query2.nombre_prenda\r\n" + 
				"").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findPrendapivote(Long idpedido, Long idempleado) {

		return em.createNativeQuery("select\r\n" + "   query2.id_empleado_pedido,\r\n"
				+ "   query2.id_prenda_cliente,\r\n" + "   query2.nombre_prenda,\r\n" + "   query2.talla,\r\n"
				+ "   query2.largo,\r\n" + "   GROUP_CONCAT(DISTINCT query2.pulgadas, query2.especificaciones \r\n"
				+ "ORDER BY\r\n" + "   query2.especificaciones DESC SEPARATOR ', ')as especificacionesdesc \r\n"
				+ "from\r\n" + "   (\r\n" + "      select\r\n" + "         alt_comercial_concentrado_tallas.*,\r\n"
				+ "         alt_servicio_cliente_lookup.nombre_lookup as especificaciones,\r\n"
				+ "         l2.nombre_lookup as talla,\r\n" + "         l3.nombre_lookup as largo,\r\n"
				+ "         query.nombre_prenda \r\n" + "      from\r\n" + "         (\r\n" + "            SELECT\r\n"
				+ "               coor_prenda.id_coordinado_prenda,\r\n"
				+ "               SUBSTRING_INDEX(look.nombre_lookup, ' ', 1)as 'nombre_prenda',\r\n"
				+ "               prenda.descripcion_prenda,\r\n" + "               tela.nombre_tela,\r\n"
				+ "               IF(coor_prenda.estatus = true, '1', '0') \r\n" + "            From\r\n"
				+ "               alt_comercial_coordinado,\r\n" + "               alt_disenio_lookup as look,\r\n"
				+ "               alt_disenio_prenda as prenda,\r\n" + "               alt_disenio_tela as tela,\r\n"
				+ "               alt_comercial_coordinado_prenda as coor_prenda \r\n" + "            where\r\n"
				+ "               coor_prenda.id_tela = tela.id_tela \r\n"
				+ "               AND coor_prenda.id_prenda = prenda.id_prenda \r\n"
				+ "               AND coor_prenda.id_familia_genero = look.id_lookup \r\n"
				+ "               AND coor_prenda.estatus = 1 \r\n"
				+ "               and coor_prenda.id_coordinado = alt_comercial_coordinado.id_coordinado \r\n"
				+ "               and alt_comercial_coordinado.id_pedido = " + idpedido + " \r\n"
				+ "            GROUP BY\r\n" + "               look.nombre_lookup\r\n" + "         )\r\n"
				+ "         as query,\r\n" + "         alt_comercial_cliente_empleado,\r\n"
				+ "         alt_comercial_concentrado_tallas,\r\n" + "         alt_servicio_cliente_lookup,\r\n"
				+ "         alt_produccion_lookup l2,\r\n" + "         alt_produccion_lookup l3 \r\n" + "      where\r\n"
				+ "         query.id_coordinado_prenda = alt_comercial_concentrado_tallas.id_prenda_cliente \r\n"
				+ "         and alt_comercial_concentrado_tallas.id_empleado_pedido = alt_comercial_cliente_empleado.id_empleado \r\n"
				+ "         and alt_comercial_cliente_empleado.id_pedido_informacion = " + idpedido + " \r\n"
				+ "         and alt_comercial_concentrado_tallas.especificacion = alt_servicio_cliente_lookup.id_lookup \r\n"
				+ "         and alt_comercial_concentrado_tallas.id_talla = l2.id_lookup \r\n"
				+ "         and alt_comercial_concentrado_tallas.id_largo = l3.id_lookup \r\n"
				+ "         and alt_comercial_concentrado_tallas.id_empleado_pedido = " + idempleado + "\r\n"
				+ "   )\r\n" + "   as query2 \r\n" + "GROUP by\r\n" + "   query2.nombre_prenda").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findPrendaTalla2(String genpivot, Long idpedido) {
		List<Object[]> queryresult;
		if (genpivot == null) {

			queryresult = em.createNativeQuery("select \r\n" + "query3.nombre_empleado\r\n" + "from(\r\n" + "select\r\n"
					+ "					alt_comercial_cliente_empleado.nombre_empleado,\r\n"
					+ "				   query2.id_empleado_pedido,\r\n"
					+ "				   query2.id_prenda_cliente,  \r\n" + "				   query2.nombre_prenda, \r\n"
					+ "                     \r\n"
					+ "					  GROUP_CONCAT(DISTINCT query2.talla, query2.largo   \r\n"
					+ "				ORDER BY  \r\n" + "				   query2.largo DESC SEPARATOR ', ')as talla  ,\r\n"
					+ "				 \r\n"
					+ "				   GROUP_CONCAT(DISTINCT query2.pulgadas, query2.especificaciones   \r\n"
					+ "				ORDER BY  \r\n"
					+ "				   query2.especificaciones DESC SEPARATOR ', ')as especificacionesdesc   \r\n"
					+ "				from  \r\n" + "				alt_comercial_cliente_empleado,\r\n"
					+ "				   (\r\n" + "				      select  \r\n"
					+ "				         alt_comercial_concentrado_tallas.*,\r\n"
					+ "				         alt_servicio_cliente_lookup.nombre_lookup as especificaciones,\r\n"
					+ "				         l2.nombre_lookup as talla,\r\n"
					+ "				         l3.nombre_lookup as largo,  \r\n"
					+ "				         query.nombre_prenda   \r\n" + "				      from  \r\n"
					+ "				         (\r\n" + "				            SELECT  \r\n"
					+ "				               coor_prenda.id_coordinado_prenda,\r\n"
					+ "				               SUBSTRING_INDEX(look.nombre_lookup, ' ', 1)as 'nombre_prenda',\r\n"
					+ "				               prenda.descripcion_prenda,\r\n"
					+ "				               tela.nombre_tela,\r\n"
					+ "				               IF(coor_prenda.estatus = true, '1', '0')   \r\n"
					+ "				            From  \r\n"
					+ "				               alt_comercial_coordinado, \r\n"
					+ "				               alt_disenio_lookup as look,\r\n"
					+ "				               alt_disenio_prenda as prenda,  \r\n"
					+ "				               alt_disenio_tela as tela,  \r\n"
					+ "				               alt_comercial_coordinado_prenda as coor_prenda   \r\n"
					+ "				            where  \r\n"
					+ "				               coor_prenda.id_tela = tela.id_tela   \r\n"
					+ "				               AND coor_prenda.id_prenda = prenda.id_prenda   \r\n"
					+ "				               AND coor_prenda.id_familia_genero = look.id_lookup   \r\n"
					+ "				               AND coor_prenda.estatus = 1   \r\n"
					+ "				               and coor_prenda.id_coordinado = alt_comercial_coordinado.id_coordinado   \r\n"
					+ "				               and alt_comercial_coordinado.id_pedido = " + idpedido + " \r\n"
					+ "				            GROUP BY  \r\n" + "				               look.nombre_lookup  \r\n"
					+ "				         )\r\n" + "				         as query,\r\n"
					+ "				         alt_comercial_cliente_empleado,\r\n"
					+ "				         alt_comercial_concentrado_tallas,  \r\n"
					+ "				         alt_servicio_cliente_lookup,\r\n"
					+ "				         alt_produccion_lookup l2,  \r\n"
					+ "				         alt_produccion_lookup l3   \r\n" + "				      where  \r\n"
					+ "				         query.id_coordinado_prenda = alt_comercial_concentrado_tallas.id_prenda_cliente   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.id_empleado_pedido = alt_comercial_cliente_empleado.id_empleado   \r\n"
					+ "				         and alt_comercial_cliente_empleado.id_pedido_informacion = " + idpedido
					+ "   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.especificacion = alt_servicio_cliente_lookup.id_lookup   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.id_talla = l2.id_lookup   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.id_largo = l3.id_lookup   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.id_pedido = "+idpedido+"   \r\n"
					+ "				        \r\n" + "				   )  \r\n" + "				   as query2   \r\n"
					+ "				   where  query2.id_empleado_pedido=alt_comercial_cliente_empleado.id_empleado\r\n"
					+ "				GROUP by  \r\n"
					+ "				   query2.nombre_prenda, query2.id_empleado_pedido) as query3\r\n"
					+ "                   GROUP BY query3.nombre_empleado").getResultList();
		} else {

			queryresult = em.createNativeQuery("select \r\n" + "query3.nombre_empleado,\r\n" + "" + genpivot + "\r\n"
					+ "from(\r\n" + "select\r\n"
					+ "					alt_comercial_cliente_empleado.nombre_empleado,\r\n"
					+ "				   query2.id_empleado_pedido,\r\n"
					+ "				   query2.id_prenda_cliente,  \r\n" + "				   query2.nombre_prenda, \r\n"
					+ "                     \r\n"
					+ "					  GROUP_CONCAT(DISTINCT query2.talla, query2.largo   \r\n"
					+ "				ORDER BY  \r\n" + "				   query2.largo DESC SEPARATOR ', ')as talla  ,\r\n"
					+ "				 \r\n"
					+ "				   GROUP_CONCAT(DISTINCT query2.pulgadas, query2.especificaciones   \r\n"
					+ "				ORDER BY  \r\n"
					+ "				   query2.especificaciones DESC SEPARATOR ', ')as especificacionesdesc   \r\n"
					+ "				from  \r\n" + "				alt_comercial_cliente_empleado,\r\n"
					+ "				   (\r\n" + "				      select  \r\n"
					+ "				         alt_comercial_concentrado_tallas.*,\r\n"
					+ "				         alt_servicio_cliente_lookup.nombre_lookup as especificaciones,\r\n"
					+ "				         l2.nombre_lookup as talla,\r\n"
					+ "				         l3.nombre_lookup as largo,  \r\n"
					+ "				         query.nombre_prenda   \r\n" + "				      from  \r\n"
					+ "				         (\r\n" + "				            SELECT  \r\n"
					+ "				               coor_prenda.id_coordinado_prenda,\r\n"
					+ "				               SUBSTRING_INDEX(look.nombre_lookup, ' ', 1)as 'nombre_prenda',\r\n"
					+ "				               prenda.descripcion_prenda,\r\n"
					+ "				               tela.nombre_tela,\r\n"
					+ "				               IF(coor_prenda.estatus = true, '1', '0')   \r\n"
					+ "				            From  \r\n"
					+ "				               alt_comercial_coordinado, \r\n"
					+ "				               alt_disenio_lookup as look,\r\n"
					+ "				               alt_disenio_prenda as prenda,  \r\n"
					+ "				               alt_disenio_tela as tela,  \r\n"
					+ "				               alt_comercial_coordinado_prenda as coor_prenda   \r\n"
					+ "				            where  \r\n"
					+ "				               coor_prenda.id_tela = tela.id_tela   \r\n"
					+ "				               AND coor_prenda.id_prenda = prenda.id_prenda   \r\n"
					+ "				               AND coor_prenda.id_familia_genero = look.id_lookup   \r\n"
					+ "				               AND coor_prenda.estatus = 1   \r\n"
					+ "				               and coor_prenda.id_coordinado = alt_comercial_coordinado.id_coordinado   \r\n"
					+ "				               and alt_comercial_coordinado.id_pedido = " + idpedido + " \r\n"
					+ "				            GROUP BY  \r\n" + "				               look.nombre_lookup  \r\n"
					+ "				         )\r\n" + "				         as query,\r\n"
					+ "				         alt_comercial_cliente_empleado,\r\n"
					+ "				         alt_comercial_concentrado_tallas,  \r\n"
					+ "				         alt_servicio_cliente_lookup,\r\n"
					+ "				         alt_produccion_lookup l2,  \r\n"
					+ "				         alt_produccion_lookup l3   \r\n" + "				      where  \r\n"
					+ "				         query.id_coordinado_prenda = alt_comercial_concentrado_tallas.id_prenda_cliente   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.id_empleado_pedido = alt_comercial_cliente_empleado.id_empleado   \r\n"
					+ "				         and alt_comercial_cliente_empleado.id_pedido_informacion = " + idpedido
					+ "   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.especificacion = alt_servicio_cliente_lookup.id_lookup   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.id_talla = l2.id_lookup   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.id_largo = l3.id_lookup   \r\n"
					+ "				         and alt_comercial_concentrado_tallas.id_pedido = "+idpedido+"   \r\n"
					+ "				        \r\n" + "				   )  \r\n" + "				   as query2   \r\n"
					+ "				   where  query2.id_empleado_pedido=alt_comercial_cliente_empleado.id_empleado\r\n"
					+ "				GROUP by  \r\n"
					+ "				   query2.nombre_prenda, query2.id_empleado_pedido) as query3\r\n"
					+ "                   GROUP BY query3.nombre_empleado").getResultList();

		}
		return queryresult;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findPrendaTalla3(Long idpedido) {
		return em.createNativeQuery("select \r\n" + "query3.id_empleado_pedido\r\n" + "from(\r\n" + "select\r\n"
				+ "					alt_comercial_cliente_empleado.nombre_empleado,\r\n"
				+ "				   query2.id_empleado_pedido,\r\n" + "				   query2.id_prenda_cliente,  \r\n"
				+ "				   query2.nombre_prenda, \r\n" + "                     \r\n"
				+ "					  GROUP_CONCAT(DISTINCT query2.talla, query2.largo   \r\n"
				+ "				ORDER BY  \r\n" + "				   query2.largo DESC SEPARATOR ', ')as talla  ,\r\n"
				+ "				 \r\n"
				+ "				   GROUP_CONCAT(DISTINCT query2.pulgadas, query2.especificaciones   \r\n"
				+ "				ORDER BY  \r\n"
				+ "				   query2.especificaciones DESC SEPARATOR ', ')as especificacionesdesc   \r\n"
				+ "				from  \r\n" + "				alt_comercial_cliente_empleado,\r\n"
				+ "				   (\r\n" + "				      select  \r\n"
				+ "				         alt_comercial_concentrado_tallas.*,\r\n"
				+ "				         alt_servicio_cliente_lookup.nombre_lookup as especificaciones,\r\n"
				+ "				         l2.nombre_lookup as talla,\r\n"
				+ "				         l3.nombre_lookup as largo,  \r\n"
				+ "				         query.nombre_prenda   \r\n" + "				      from  \r\n"
				+ "				         (\r\n" + "				            SELECT  \r\n"
				+ "				               coor_prenda.id_coordinado_prenda,\r\n"
				+ "				               SUBSTRING_INDEX(look.nombre_lookup, ' ', 1)as 'nombre_prenda',\r\n"
				+ "				               prenda.descripcion_prenda,\r\n"
				+ "				               tela.nombre_tela,\r\n"
				+ "				               IF(coor_prenda.estatus = true, '1', '0')   \r\n"
				+ "				            From  \r\n" + "				               alt_comercial_coordinado, \r\n"
				+ "				               alt_disenio_lookup as look,\r\n"
				+ "				               alt_disenio_prenda as prenda,  \r\n"
				+ "				               alt_disenio_tela as tela,  \r\n"
				+ "				               alt_comercial_coordinado_prenda as coor_prenda   \r\n"
				+ "				            where  \r\n"
				+ "				               coor_prenda.id_tela = tela.id_tela   \r\n"
				+ "				               AND coor_prenda.id_prenda = prenda.id_prenda   \r\n"
				+ "				               AND coor_prenda.id_familia_genero = look.id_lookup   \r\n"
				+ "				               AND coor_prenda.estatus = 1   \r\n"
				+ "				               and coor_prenda.id_coordinado = alt_comercial_coordinado.id_coordinado   \r\n"
				+ "				               and alt_comercial_coordinado.id_pedido = " + idpedido + " \r\n"
				+ "				            GROUP BY  \r\n" + "				               look.nombre_lookup  \r\n"
				+ "				         )\r\n" + "				         as query,\r\n"
				+ "				         alt_comercial_cliente_empleado,\r\n"
				+ "				         alt_comercial_concentrado_tallas,  \r\n"
				+ "				         alt_servicio_cliente_lookup,\r\n"
				+ "				         alt_produccion_lookup l2,  \r\n"
				+ "				         alt_produccion_lookup l3   \r\n" + "				      where  \r\n"
				+ "				         query.id_coordinado_prenda = alt_comercial_concentrado_tallas.id_prenda_cliente   \r\n"
				+ "				         and alt_comercial_concentrado_tallas.id_empleado_pedido = alt_comercial_cliente_empleado.id_empleado   \r\n"
				+ "				         and alt_comercial_cliente_empleado.id_pedido_informacion = " + idpedido
				+ "   \r\n"
				+ "				         and alt_comercial_concentrado_tallas.especificacion = alt_servicio_cliente_lookup.id_lookup   \r\n"
				+ "				         and alt_comercial_concentrado_tallas.id_talla = l2.id_lookup   \r\n"
				+ "				         and alt_comercial_concentrado_tallas.id_largo = l3.id_lookup   \r\n"
				+ "				         and alt_comercial_concentrado_tallas.id_pedido = "+idpedido+"   \r\n"
				+ "				        \r\n" + "				   )  \r\n" + "				   as query2   \r\n"
				+ "				   where  query2.id_empleado_pedido=alt_comercial_cliente_empleado.id_empleado\r\n"
				+ "				GROUP by  \r\n"
				+ "				   query2.nombre_prenda, query2.id_empleado_pedido) as query3\r\n"
				+ "                   GROUP BY query3.nombre_empleado").getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findPrendasEmpleado(Long idempleado, Long idpedido) {
		return em.createNativeQuery("select query5.*\r\n" + 
				"from(\r\n" + 
				"select\r\n" + 
				"  query1.id_empleado,query2.*\r\n" + 
				"from\r\n" + 
				"  (\r\n" + 
				"    SELECT\r\n" + 
				"      ccp.id_coordinado_prenda,\r\n" + 
				"      ccp.id_empleado\r\n" + 
				"    FROM\r\n" + 
				"      alt_comercial_concetrado_prenda ccp\r\n" + 
				"      INNER JOIN alt_comercial_cliente_empleado cce ON ccp.id_empleado = cce.id_empleado\r\n" + 
				"      INNER JOIN alt_comercial_coordinado_prenda ccp2 ON ccp.id_coordinado_prenda = ccp2.id_coordinado_prenda\r\n" + 
				"      INNER JOIN alt_comercial_coordinado cc ON ccp2.id_coordinado = cc.id_coordinado\r\n" + 
				"      INNER JOIN alt_disenio_prenda dp ON ccp2.id_prenda = dp.id_prenda\r\n" + 
				"      INNER JOIN alt_disenio_tela dt ON ccp2.id_tela = dt.id_tela\r\n" + 
				"    WHERE\r\n" + 
				"      ccp.id_empleado = "+idempleado+"\r\n" + 
				"  ) as query1,\r\n" + 
				"  (\r\n" + 
				"    select\r\n" + 
				"      query4.*\r\n" + 
				"    from(\r\n" + 
				"        SELECT\r\n" + 
				"          coor_prenda.id_coordinado_prenda,\r\n" + 
				"          SUBSTRING_INDEX(look.nombre_lookup, ' ', 1) as 'nombre prenda',\r\n" + 
				"          prenda.descripcion_prenda,\r\n" + 
				"          tela.nombre_tela,\r\n" + 
				"          IF(coor_prenda.estatus = true, '1', '0')\r\n" + 
				"        From\r\n" + 
				"          alt_comercial_coordinado,\r\n" + 
				"          alt_disenio_lookup as look,\r\n" + 
				"          alt_disenio_prenda as prenda,\r\n" + 
				"          alt_disenio_tela as tela,\r\n" + 
				"          alt_comercial_coordinado_prenda as coor_prenda\r\n" + 
				"        where\r\n" + 
				"          1 = 1\r\n" + 
				"          AND coor_prenda.id_tela = tela.id_tela\r\n" + 
				"          AND coor_prenda.id_prenda = prenda.id_prenda\r\n" + 
				"          AND coor_prenda.id_familia_genero = look.id_lookup\r\n" + 
				"          AND coor_prenda.estatus = 1\r\n" + 
				"          and coor_prenda.id_coordinado = alt_comercial_coordinado.id_coordinado\r\n" + 
				"          and alt_comercial_coordinado.id_pedido = "+idpedido+"\r\n" + 
				"        GROUP BY\r\n" + 
				"          look.nombre_lookup\r\n" + 
				"      ) as query4\r\n" + 
				"  ) as query2\r\n" + 
				"\r\n" + 
				"where\r\n" + 
				"   query1.id_coordinado_prenda = query2.id_coordinado_prenda) as query5\r\n" + 
				"  	left join\r\n" + 
				"		alt_comercial_concentrado_tallas on query5.id_coordinado_prenda=alt_comercial_concentrado_tallas.id_prenda_cliente\r\n" + 
				"		and query5.id_empleado=alt_comercial_concentrado_tallas.id_empleado_pedido\r\n" + 
				"		where alt_comercial_concentrado_tallas.id_empleado_pedido is null\r\n" + 
				"		and alt_comercial_concentrado_tallas.id_prenda_cliente is null").getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findPrendasEspecificaciones(Long idempleado, Long idpedido,Long idprenda) {
		
	return em.createNativeQuery("SELECT * FROM `alt_comercial_concentrado_tallas` where `id_pedido`="+idpedido+" and `id_empleado_pedido`="+idempleado+" and `id_prenda_cliente`="+idprenda+" and `pulgadas` IS NULL and `especificacion` IS NULL").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findPrendasEspecificaciones2(Long idempleado, Long idpedido,Long idprenda) {
		
	return em.createNativeQuery("SELECT * FROM `alt_comercial_concentrado_tallas` where `id_pedido`="+idpedido+" and `id_empleado_pedido`="+idempleado+" and `id_prenda_cliente`="+idprenda+"").getResultList();
	}
	
	@Override
	@Transactional
	public void updateall(Long talla, Long largo, Long idempleado, Long idpedido,Long idprenda) {
		// TODO Auto-generated method stub
		em.createNativeQuery(
				"Update  alt_comercial_concentrado_tallas set id_talla="+talla+" , id_largo="+largo+" where id_pedido="+idpedido+" and id_empleado_pedido=" + idempleado + " and id_prenda_cliente="+idprenda+" ")
				.executeUpdate();

	}	
	
	@Override
	@Transactional
	public void updateall(Long largo, Long idempleado, Long idpedido,Long idprenda) {
		// TODO Auto-generated method stub
		em.createNativeQuery(
				"Update  alt_comercial_concentrado_tallas set id_largo="+largo+" where id_pedido="+idpedido+" and id_empleado_pedido=" + idempleado + " and id_prenda_cliente="+idprenda+" ")
				.executeUpdate();

	}	
	
	@Override
	@Transactional
	public void updateall1(Long talla, Long idempleado, Long idpedido,Long idprenda) {
		// TODO Auto-generated method stub
		em.createNativeQuery(
				"Update  alt_comercial_concentrado_tallas set id_talla="+talla+"  where  id_pedido="+idpedido+" and id_empleado_pedido=" + idempleado + " and id_prenda_cliente="+idprenda+" ")
				.executeUpdate();

	}	

}

