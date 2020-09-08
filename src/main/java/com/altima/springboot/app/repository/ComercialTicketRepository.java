package com.altima.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.altima.springboot.app.models.entity.ComercialTicket;



public interface ComercialTicketRepository extends CrudRepository<ComercialTicket, Long> {

}
