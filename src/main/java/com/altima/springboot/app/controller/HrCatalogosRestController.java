package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.altima.springboot.app.models.entity.HrCalendario;
import com.altima.springboot.app.models.entity.HrDepartamento;
import com.altima.springboot.app.models.entity.HrHorario;
import com.altima.springboot.app.models.entity.HrLookup;
import com.altima.springboot.app.models.entity.HrPuesto;
import com.altima.springboot.app.models.service.IHrCalendariosService;
import com.altima.springboot.app.models.service.IHrDepartamentoService;
import com.altima.springboot.app.models.service.IHrHorarioService;
import com.altima.springboot.app.models.service.IHrLookupService;
import com.altima.springboot.app.models.service.IHrPuestoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HrCatalogosRestController {

    @Autowired
    IHrLookupService hrLookupService;

    @Autowired
    IHrDepartamentoService hrDepartamentoService;

    @Autowired
    IHrPuestoService hrPuestoService;

    @Autowired
    IHrHorarioService hrHorarioService;

    @Autowired
    IHrCalendariosService hrCalendarioService;

    // Método para guardar Empresas
    @PostMapping("/postEmpresa")
    public int postEmpresa(@RequestParam(name = "nombreEmpresa") String nombreEmpresa,
            @RequestParam(name = "idLookup") Long idLookup) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            HrLookup hr = hrLookupService.findOne(idLookup);
            hr.setNombreLookup(nombreEmpresa);
            hrLookupService.save(hr);
            return 1;
        } catch (Exception e) {
            try {
                HrLookup empresa = new HrLookup();
                empresa.setNombreLookup(nombreEmpresa);
                empresa.setTipoLookup("Empresa");
                empresa.setCreadoPor(auth.getName());
                empresa.setFechaCreacion(dtf.format(now));
                empresa.setEstatus("1");
                empresa.setIdText("EMP");
                hrLookupService.save(empresa);
                empresa.setIdText("EMP" + (10000 + empresa.getIdLookup()));
                hrLookupService.save(empresa);
                return 2;
            } catch (Exception p) {
                p.printStackTrace();
                return 3;
            }
        } finally {
        }
    }

    // Método para listar Empresas
    @GetMapping("/getLookupHR")
    public List<HrLookup> getEmpresa(@RequestParam(name = "tipo") String tipo) {
        return hrLookupService.findAllByTipoLookup(tipo);
    }

    // Método para dar de baja empresa
    @GetMapping("/darBajaEmpresa")
    public Object darBajaEmpresa(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrLookup EhrE = hrLookupService.findOne(idLookup);
        EhrE.setEstatus("0");
        hrLookupService.save(EhrE);
        return hrLookupService.findOne(idLookup);
    }

    // Método para dar de alta empresa
    @GetMapping("/darAltaEmpresa")
    public Object darAltaEmpresa(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrLookup EhrE = hrLookupService.findOne(idLookup);
        EhrE.setEstatus("1");
        hrLookupService.save(EhrE);
        return hrLookupService.findOne(idLookup);
    }

    // Método para editar Empresas
    @GetMapping("/editarEmpresa")
    public HrLookup editarEmpresa(@RequestParam(name = "idLookup") Long idLookup) {
        return hrLookupService.findOne(idLookup);
    }

    // Método para guardar Áreas
    @PostMapping("/postArea")
    public int postArea(@RequestParam(name = "nombreArea") String nombreArea,
            @RequestParam(name = "idLookup") Long idLookup) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            HrLookup hr = hrLookupService.findOne(idLookup);
            hr.setNombreLookup(nombreArea);
            hrLookupService.save(hr);
            return 1;
        } catch (Exception e) {
            try {
                HrLookup area = new HrLookup();
                area.setNombreLookup(nombreArea);
                area.setTipoLookup("Area");
                area.setCreadoPor(auth.getName());
                area.setEstatus("1");
                area.setFechaCreacion(dtf.format(now));
                area.setIdText("AREA");
                hrLookupService.save(area);
                area.setIdText("AREA" + (10000 + area.getIdLookup()));
                return 2;
            } catch (Exception p) {
                p.printStackTrace();
                return 3;
            }
        }
    }

    // Método para listar Áreas
    @GetMapping("/rh-listarAreas")
    public List<HrLookup> listarAreas(@RequestParam(name = "tipo") String tipo) {
        return hrDepartamentoService.findAllEmpresas(tipo);
    }

    // Método para dar de baja area
    @GetMapping("/darBajaArea")
    public Object darBajaArea(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrLookup EhrA = hrLookupService.findOne(idLookup);
        EhrA.setEstatus("0");
        hrLookupService.save(EhrA);
        return hrLookupService.findOne(idLookup);
    }

    // Método para dar de alta area
    @GetMapping("/darAltaArea")
    public Object darAltaArea(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrLookup EhrA = hrLookupService.findOne(idLookup);
        EhrA.setEstatus("1");
        hrLookupService.save(EhrA);
        return hrLookupService.findOne(idLookup);
    }

    // Método para editar Áreas
    @GetMapping("/editarArea")
    public HrLookup editarArea(@RequestParam(name = "idLookup") Long idLookup) {
        return hrLookupService.findOne(idLookup);
    }

    // Método para guardar departamentos
    @PostMapping("/postDepartamento")
    public int postDepartamento(@RequestParam(name = "idDepartamento") Long idDepartamento,
            @RequestParam(name = "nombreDepartamento") String nombreDepartamento,
            @RequestParam(name = "nomArea") Long nomArea) {
        Authentication authDepa = SecurityContextHolder.getContext().getAuthentication();
        Date date1 = new Date();
        DateFormat fechaCreacion = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            HrDepartamento hrD = hrDepartamentoService.findOne(idDepartamento);
            hrD.setNombreDepartamento(nombreDepartamento);
            hrD.setIdArea(nomArea);
            hrDepartamentoService.save(hrD);
            return 1;
        } catch (Exception e) {
            try {
                HrDepartamento departamento = new HrDepartamento();
                departamento.setEstatus("1");
                departamento.setCreado_por(authDepa.getName());
                departamento.setFechaCreacion(fechaCreacion.format(date1));
                departamento.setActualizadoPor(authDepa.getName());
                departamento.setIdText("DEP");
                departamento.setNombreDepartamento(nombreDepartamento);
                departamento.setIdArea(nomArea);
                hrDepartamentoService.save(departamento);
                departamento.setIdText("DEP" + (10000 + departamento.getIdDepartamento()));
                hrDepartamentoService.save(departamento);
                return 2;
            } catch (Exception p) {
                p.printStackTrace();
                return 3;
            }
        } finally {
        }
    }

    // Método para listar Departamentos
    @GetMapping("/getListarDepartamentos")
    public List<Object[]> listarDepartamentos(Model model) {
        List<Object[]> listarDepartamentos = hrDepartamentoService.listarDepartamentos();
        model.addAttribute("listarDepartamentos", listarDepartamentos);
        return hrDepartamentoService.listarDepartamentos();
    }

    // Método para dar de baja departamento
    @GetMapping("/darBajaDepartamento")
    public Object darBajaDepartamento(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrDepartamento EhrD = hrDepartamentoService.findOne(idLookup);
        EhrD.setEstatus("0");
        hrDepartamentoService.save(EhrD);
        return hrDepartamentoService.findOne(idLookup);
    }

    // Método para dar de alta departamento
    @GetMapping("/darAltaDepartamento")
    public Object darAltaDepartamento(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrDepartamento EhrD = hrDepartamentoService.findOne(idLookup);
        EhrD.setEstatus("1");
        hrDepartamentoService.save(EhrD);
        return hrDepartamentoService.findOne(idLookup);
    }

    // Método para editar Departamento
    @GetMapping("/editarDepartamento")
    public Object editarDepartamento(@RequestParam(name = "idLookup") Long idLookup) {
        return hrDepartamentoService.obtenerDepartamento(idLookup);
    }

    // Método para guardar Puestos
    @PostMapping("/postPuesto")
    public int postPuesto(@RequestParam(name = "nombrePuesto") String nombrePuesto,
            @RequestParam(name = "nomPlazas") String nomPlazas, @RequestParam(name = "sueldos") String sueldos,
            @RequestParam(name = "perfiles") String perfiles, @RequestParam(name = "departamento") Long departamento,
            @RequestParam(name = "checkbox") Boolean checkbox, @RequestParam(name = "idPuesto") Long idPuesto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            HrPuesto hrP = hrPuestoService.findOne(idPuesto);
            hrP.setIdDepartamento(departamento);
            hrP.setTiempoExtra(checkbox);
            hrP.setNombrePlaza(nomPlazas);
            hrP.setSueldo(sueldos);
            hrP.setPerfil(perfiles);
            hrPuestoService.save(hrP);
            return 1;
        } catch (Exception e) {
            try {
                HrPuesto nuevoPuesto = new HrPuesto();
                nuevoPuesto.setCreadoPor(auth.getName());
                nuevoPuesto.setFechaCreacion(dtf.format(now));
                nuevoPuesto.setEstatus("1");
                nuevoPuesto.setCreadoPor(auth.getName());
                nuevoPuesto.setIdText("PTO");
                nuevoPuesto.setNombrePuesto(nombrePuesto);
                nuevoPuesto.setNombrePlaza(nomPlazas);
                nuevoPuesto.setSueldo(sueldos);
                nuevoPuesto.setPerfil(perfiles);
                nuevoPuesto.setIdDepartamento(departamento);
                nuevoPuesto.setTiempoExtra(checkbox);
                hrPuestoService.save(nuevoPuesto);
                nuevoPuesto.setIdText("PTO" + (10000 + nuevoPuesto.getIdPuesto()));
                hrPuestoService.save(nuevoPuesto);
                return 2;
            } catch (Exception p) {
                e.printStackTrace();
                return 3;
            }
        }
    }

    // Método para mostrar las opciones de departamentos en puestos
    @GetMapping("/getMostrarDepartamentos")
    public List<HrDepartamento> listarDepartamentos() {
        return hrPuestoService.findAllDepartamentos();
    }

    // Método para listar los puestos insertados
    @GetMapping("/getListarPuestos")
    public List<Object[]> listarPuestos(Model model) {
        List<Object[]> listarPuestos = hrDepartamentoService.listarDepartamentos();
        model.addAttribute("listarPuestos", listarPuestos);
        return hrPuestoService.listarPuestos();
    }

    // Método para dar de baja puesto
    @GetMapping("/darBajaPuesto")
    public Object darBajaPuesto(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrPuesto EhrP = hrPuestoService.findOne(idLookup);
        EhrP.setEstatus("0");
        hrPuestoService.save(EhrP);
        return hrPuestoService.findOne(idLookup);
    }

    // Método para dar de alta puesto
    @GetMapping("/darAltaPuesto")
    public Object darAltaPuesto(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrPuesto EhrP = hrPuestoService.findOne(idLookup);
        EhrP.setEstatus("1");
        hrPuestoService.save(EhrP);
        return hrPuestoService.findOne(idLookup);
    }

    // Método para editar puestos
    @GetMapping("/editarPuesto")
    public Object editarPuesto(@RequestParam(name = "idLookup") Long idLookup) {
        return hrPuestoService.obtenerPuesto(idLookup);
    }

    // Método para guardar Horarios
    @PostMapping("/postHorarioLaboral")
    public int postPuesto(@RequestParam(name = "idHorario") Long idHorario,
            @RequestParam(name = "horaInicio") String horaInicio, @RequestParam(name = "horaSalida") String horaSalida,
            @RequestParam(name = "horaComida") String horaComida,
            @RequestParam(name = "horaRegresoComida") String horaRegresoComida) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            HrHorario hrH = hrHorarioService.findOne(idHorario);
            hrH.setHoraInicial(horaInicio);
            hrH.setHoraFinal(horaSalida);
            hrH.setInicioComida(horaComida);
            hrH.setFinalComida(horaRegresoComida);
            hrHorarioService.save(hrH);
            return 1;
        } catch (Exception e) {
            try {
                HrHorario horarioLaboral = new HrHorario();
                horarioLaboral.setHoraInicial(horaInicio);
                horarioLaboral.setHoraFinal(horaSalida);
                horarioLaboral.setInicioComida(horaComida);
                horarioLaboral.setFinalComida(horaRegresoComida);
                horarioLaboral.setCreadoPor(auth.getName());
                horarioLaboral.setFechaCreacion(dtf.format(now));
                horarioLaboral.setActualizadoPor(auth.getName());
                horarioLaboral.setEstatus("1");
                hrHorarioService.save(horarioLaboral);
                return 2;
            } catch (Exception p) {
                e.printStackTrace();
                return 3;
            }
        }
    }

    // Método para listar horarios insertados
    @GetMapping("/getListarHorarios")
    public List<HrHorario> listarHorariosInsertados() {
        return hrHorarioService.findAllHorarios();
    }

    // Método para dar de baja horario
    @GetMapping("/darBajaHorario")
    public Object darBajaHorario(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrHorario EhrH = hrHorarioService.findOne(idLookup);
        EhrH.setEstatus("0");
        hrHorarioService.save(EhrH);
        return hrHorarioService.findOne(idLookup);
    }

    // Método para dar de alta horario
    @GetMapping("/darAltaHorario")
    public Object darAltaHorario(@RequestParam(name = "idLookup") Long idLookup) throws Exception {
        HrHorario EhrH = hrHorarioService.findOne(idLookup);
        EhrH.setEstatus("1");
        hrHorarioService.save(EhrH);
        return hrHorarioService.findOne(idLookup);
    }

    // Método para editar horarios
    @GetMapping("/editarHorario")
    public List<HrHorario> editarHorario(@RequestParam(name = "idHorario") Long idHorario) {
        return hrHorarioService.obtenerHorario(idHorario);
    }

    // Método para guardar Calendarios
    @PostMapping("/postCalendarios")
    public int postPuesto(@RequestParam(name = "idCalendario") Long idCalendario,
            @RequestParam(name = "fechaFestivo") String fechaFestivo,
            @RequestParam(name = "festividad") String festividad,
            @RequestParam(name = "estatusFestivo") String estatusFestivo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            HrCalendario hrC = hrCalendarioService.findOne(idCalendario);
            hrC.setFecha(fechaFestivo);
            hrC.setNombreCalendario(festividad);
            hrC.setEstatus(estatusFestivo);
            hrCalendarioService.save(hrC);
            return 1;
        } catch (Exception e) {
            try {
                HrCalendario nuevosFestivos = new HrCalendario();
                nuevosFestivos.setFecha(fechaFestivo);
                nuevosFestivos.setNombreCalendario(festividad);
                nuevosFestivos.setCreadoPor(auth.getName());
                nuevosFestivos.setFechaCreacion(dtf.format(now));
                nuevosFestivos.setEstatus(estatusFestivo);
                nuevosFestivos.setActualizadoPor(auth.getName());
                hrCalendarioService.save(nuevosFestivos);
                return 2;
            } catch (Exception p) {
                e.printStackTrace();
                return 3;
            }
        }
    }

    // Método para listar calendarios insertados
    @GetMapping("/getListarCalendarios")
    public List<HrCalendario> listarCalendariosInsertados() {
        return hrCalendarioService.findAllCalendarios();
    }

    // Método para dar de baja Calendario
    @GetMapping("/darBajaCalendario")
    public Object darBajaCalendario(@RequestParam(name = "idCalendario") Long idCalendario) throws Exception {
        HrCalendario EhrC = hrCalendarioService.findOne(idCalendario);
        EhrC.setEstatus("0");
        hrCalendarioService.save(EhrC);
        return hrCalendarioService.findOne(idCalendario);
    }

    // Método para dar de alta Calendario
    @GetMapping("/darAltaCalendario")
    public Object darAltaCalendario(@RequestParam(name = "idCalendario") Long idCalendario) throws Exception {
        HrCalendario EhrC = hrCalendarioService.findOne(idCalendario);
        EhrC.setEstatus("1");
        hrCalendarioService.save(EhrC);
        return hrCalendarioService.findOne(idCalendario);
    }

    // Método para editar calendarios
    @GetMapping("editarCalendario")
    public Object editarCalendario(@RequestParam(name = "idCalendario") Long idCalendario) {
        return hrCalendarioService.findOne(idCalendario);
    }
}