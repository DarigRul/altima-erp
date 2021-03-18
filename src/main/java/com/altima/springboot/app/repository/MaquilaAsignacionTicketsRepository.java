package com.altima.springboot.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.altima.springboot.app.models.entity.MaquilaAsignacionTickets;

public interface MaquilaAsignacionTicketsRepository extends CrudRepository<MaquilaAsignacionTickets,Long > {

	List<MaquilaAsignacionTickets> findByIdControlPedido(Long id);

	List<MaquilaAsignacionTickets> findByIdControlPedidoAndIdPrenda(Long idcontrol, Long idprenda);

	List<MaquilaAsignacionTickets> findByIdControlPedidoOrderByIdAsignacionTicketDesc(Long id);

	List<MaquilaAsignacionTickets> findByIdControlPedidoOrderByIdAsignacionTicketAsc(Long id);

	List<MaquilaAsignacionTickets> findByIdControlPedidoAndIdPrendaOrderByIdAsignacionTicketAsc(Long idcontrol,
			Long idprenda);

}
