package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.SoporteTecnicoTicketDto;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicket;
import com.altima.springboot.app.repository.SoporteTecnicoTicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SoporteTecnicoTicketServiceImpl implements ISoporteTecnicoTicketService{

    @Autowired
    private SoporteTecnicoTicketRepository repository;

    @Autowired
    private IUsuarioService usuarioService; 

    @Override
    @Transactional(readOnly = true)
    public List<SoporteTecnicoTicket> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(SoporteTecnicoTicket ticket) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (ticket.getIdTicket()==null) {
            ticket.setCreadoPor(auth.getName());
            ticket.setActualizadoPor(auth.getName());
            ticket.setIdEmpleado(0l);
            if (!auth.getName().equals("ADMIN")) {
                ticket.setIdEmpleado(Long.parseLong(usuarioService.findEmpleadoByUserName(auth.getName())[0].toString()));
            }
            
        }
        else{
            ticket.setUltimaFechaModificacion(null);
            ticket.setActualizadoPor(auth.getName());
        }
        repository.save(ticket);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public SoporteTecnicoTicket findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SoporteTecnicoTicketDto> findAllTicket() {
        // TODO Auto-generated method stub
        return repository.findAllTicket(0l);
    }

    @Transactional(readOnly = true)
    @Override
    public SoporteTecnicoTicketDto findTicket(Long id) {
        // TODO Auto-generated method stub
        return repository.findTicket(id);
    }
}
