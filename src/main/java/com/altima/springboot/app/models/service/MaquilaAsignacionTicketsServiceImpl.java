package com.altima.springboot.app.models.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.MaquilaAsignacionTickets;
import com.altima.springboot.app.repository.MaquilaAsignacionTicketsRepository;

@Service
public class MaquilaAsignacionTicketsServiceImpl implements IMaquilaAsignacionTicketsService {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired 
	MaquilaAsignacionTicketsRepository repository;
	
	@Override
	@Transactional
	public List<MaquilaAsignacionTickets> findByControlPedido(Long id){
		return repository.findByIdControlPedido(id);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HrEmpleado> ListarOperarios(){
		return  em.createQuery("Select empleado.idEmpleado,concat(empleado.nombrePersona,' ',empleado.apellidoPaterno,' ',empleado.apellidoMaterno) From HrEmpleado empleado,HrPuesto puesto,HrDepartamento departamento WHERE empleado.idPuesto=puesto.idPuesto AND puesto.nombrePuesto='OPERARIO' AND empleado.estatus=1 AND puesto.idDepartamento=departamento.idDepartamento and departamento.nombreDepartamento='TALLER DE MAQUILA'").getResultList();

	
	}

	@Override
	@Transactional
	public void saveTickets(String idcontrol, String idprenda) {
		
		
		em.createNativeQuery(" insert into alt_maquila_asignacion_tickets\r\n"
				+ "SELECT \r\n"
				+ "null,\r\n"
				+ "amcpb.id_control_pedido_embultado,\r\n"
				+ " amcp.id_control_pedido,\r\n"
				+ "PO.id_prenda,\r\n"
				+ "amcp.pedido,\r\n"
				+ "amcp.modelo,\r\n"
				+ "null\r\n"
				+ ",\r\n"
				+ "amcpb.cantidad_prenda_bulto,\r\n"
				+ "familia.nombre_lookup as familia,\r\n"
				+ "operacion.nombre_lookup as operacion,\r\n"
				+ "operacion.atributo_2 as sam,\r\n"
				+ "ROUND((60/ operacion.atributo_2)) as hrs,\r\n"
				+ "ROUND(((60/ operacion.atributo_2)* operacion.atributo_3)) as turno,round(amcpb.cantidad_prenda_bulto*operacion.atributo_2,2) as tiempo_estimado\r\n"
				+ ",CONCAT('TICK-',(FLOOR( 1 + RAND( ) *10000 ))) AS ticket\r\n"
				+ ",null\r\n"
				+ ",null\r\n"
				+ " \r\n"
				+ "FROM\r\n"
				+ "	alt_maquila_prenda_operacion AS PO\r\n"
				+ "	INNER JOIN alt_maquila_lookup operacion ON operacion.id_lookup = PO.id_operacion\r\n"
				+ "	INNER JOIN alt_maquila_lookup familia ON operacion.descripcion_lookup = familia.id_lookup\r\n"
				+ "	INNER JOIN alt_maquila_control_pedidos as amcp\r\n"
				+ "INNER JOIN alt_maquila_control_pedidos_bulto as amcpb	on amcpb.id_control_pedido=amcp.id_control_pedido\r\n"
				+ "	WHERE \r\n"
				+ "	1=1\r\n"
				+ "	and PO.id_prenda=amcp.id_prenda\r\n"
				+ "	AND PO.id_prenda="+idprenda+"\r\n"
				+ "	AND amcp.id_control_pedido="+idcontrol+"\r\n"
				+ "").executeUpdate();
	}

	

	@Override
	@Transactional
	public void save(MaquilaAsignacionTickets maquilaAsignacion) {
		repository.save(maquilaAsignacion);
		
	}

	@Override
	@Transactional
	public List<MaquilaAsignacionTickets> ImprimirTickets(Long idcontrol, Long idprenda) {
		return repository.findByIdControlPedidoAndIdPrenda(idcontrol,idprenda);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> ListarTicketsOperarios(Long idoperario) {
		// TODO Auto-generated method stub
		return em.createNativeQuery("select amat.*,CONCAT(ahe.nombre_persona,' ',ahe.apellido_paterno,' ',ahe.apellido_materno) from alt_maquila_asignacion_tickets amat\r\n"
				+ "INNER JOIN alt_hr_empleado ahe on amat.operario=ahe.id_empleado\r\n"
				+ "where amat.operario="+idoperario+"").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaquilaAsignacionTickets> ListarTickesNoAsignados() {
		// TODO Auto-generated method stub
		return em.createQuery("Select idAsignacionTicket,ticket From MaquilaAsignacionTickets where operario IS NULL").getResultList();
	}
    
	@Transactional
	@Override
	public MaquilaAsignacionTickets findOne(Long idticket, Long idoperario) {
		// TODO Auto-generated method stub
		
		return repository.findById(idticket).orElse(null);
	}
 
	@Transactional
	@Override
	public MaquilaAsignacionTickets findOne(Long idticket) {
		// TODO Auto-generated method stub
		return repository.findById(idticket).orElse(null);
	}

	
}
