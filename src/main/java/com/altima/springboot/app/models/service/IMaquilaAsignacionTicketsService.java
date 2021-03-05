package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.entity.MaquilaAsignacionTickets;

public interface IMaquilaAsignacionTicketsService {

	List<HrEmpleado> ListarOperarios();

	void saveTickets(String idcontrol, String idprenda);

	List<MaquilaAsignacionTickets> findByControlPedido(Long id);

	void save(MaquilaAsignacionTickets maquilaAsignacion);

	List<MaquilaAsignacionTickets> ImprimirTickets(Long idcontrol, Long idprenda);

	List<Object[]> ListarTicketsOperarios(Long idoperario);

	List<MaquilaAsignacionTickets> ListarTickesNoAsignados();

	MaquilaAsignacionTickets findOne(Long idticket, Long idoperario);

	MaquilaAsignacionTickets findOne(Long idticket);

}
