package com.altima.springboot.app.models.service;

import java.util.List;

import com.altima.springboot.app.dto.SoporteTecnicoTicketDto;
import com.altima.springboot.app.models.entity.SoporteTecnicoTicketSeguimiento;
import com.altima.springboot.app.repository.SoporteTecnicoTicketSeguimientoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SoporteTecnicoTicketSeguimientoServiceImpl implements ISoporteTecnicoTicketSeguimientoService {

    @Autowired
    private SoporteTecnicoTicketSeguimientoRepository repository;

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    @Transactional(readOnly = true)
    public List<SoporteTecnicoTicketSeguimiento> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(SoporteTecnicoTicketSeguimiento ticketSeguimiento) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ticketSeguimiento.setIdEmpleado(0l);
        if (!auth.getName().equals("ADMIN")) {
            ticketSeguimiento
                    .setIdEmpleado(Long.parseLong(usuarioService.findEmpleadoByUserName(auth.getName())[0].toString()));
        }
        repository.save(ticketSeguimiento);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public SoporteTecnicoTicketSeguimiento findOne(Long id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SoporteTecnicoTicketDto> findSeguimientoByIdTicket(Long idTicket) {
        // TODO Auto-generated method stub
        return repository.findSeguimientoByIdTicket(idTicket);
    }

}
