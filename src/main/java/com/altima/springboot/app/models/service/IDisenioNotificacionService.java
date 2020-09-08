package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.models.entity.DisenioNotificacion;
import com.altima.springboot.app.models.entity.ProduccionPedido;
import com.altima.springboot.app.models.entity.Usuario;

public interface IDisenioNotificacionService {

	Usuario findBynombreUsuario(String username);

	List<DisenioNotificacion> findAll();

	void save(DisenioNotificacion disenionotificacion);

	void delete(Long id);

	DisenioNotificacion findOne(Long id);

	List<Object[]> findAllWithApplicantName();

	List<ProduccionPedido> findOneNotification(Long solicitud);

	Integer notificationnumber();

}
