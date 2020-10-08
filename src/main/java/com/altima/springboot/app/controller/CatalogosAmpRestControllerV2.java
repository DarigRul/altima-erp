package com.altima.springboot.app.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altima.springboot.app.models.entity.AmpAlmacenFisico;
import com.altima.springboot.app.models.entity.AmpAlmacenLogico;
import com.altima.springboot.app.models.entity.AmpLookup;
import com.altima.springboot.app.models.entity.HrEmpleado;
import com.altima.springboot.app.models.service.IAmpAlmacenFisicoService;
import com.altima.springboot.app.models.service.IAmpAlmacenLogicoService;
import com.altima.springboot.app.models.service.IAmpLoookupService;

@RestController
public class CatalogosAmpRestControllerV2 {
	@Autowired
	IAmpAlmacenFisicoService AlmacenFisicoService;
	@Autowired
	IAmpAlmacenLogicoService AlmacenLogicoService;
	@Autowired
	IAmpLoookupService LookupService;

	@GetMapping("/obtener-responsables-almacen")
	public List<HrEmpleado> ResponsablesAlmacen() {
		return AlmacenFisicoService.findEmployeeAMP();
	}

	@GetMapping("/obtener-almacenes-fisicos-select")
	public List<AmpAlmacenFisico> AlmacenFisicoSelect() {
		return AlmacenFisicoService.findAll();
	}

	@PostMapping("/guardar-movimiento")
	public Boolean GuardarMovimiento(String Movimiento, String Tipo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;

		if (LookupService.findMovementsDuplicate(Movimiento, Tipo).size() <= 0) {
			try {
				AmpLookup movimiento = new AmpLookup();
				movimiento.setNombreLookup(Movimiento);
				;
				movimiento.setTipoLookup(Tipo);
				movimiento.setEstatus(1);
				movimiento.setCreadoPor(auth.getName());
				movimiento.setFechaCreacion(dateFormat.format(date));
				LookupService.save(movimiento);
				result = true;
			} catch (Exception e) {
				// TODO: handle exception
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	@PostMapping("/editar-movimiento")
	public Boolean EditarMovimiento(Long Id, String Nombre, String Tipo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;

		if (LookupService.findMovementsDuplicate(Nombre, Tipo).size() <= 0) {
			try {
				AmpLookup movimiento = LookupService.findOne(Id);
				movimiento.setNombreLookup(Nombre);
				;
				movimiento.setTipoLookup(Tipo);
				movimiento.setActualizadoPor(auth.getName());
				movimiento.setUltimaFechaModificacion(dateFormat.format(date));
				LookupService.save(movimiento);
				result = true;
			} catch (Exception e) {
				// TODO: handle exception
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	@PostMapping("/baja-movimiento")
	public boolean BajaMovimiento(Long Id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;
		try {
			AmpLookup movimiento = LookupService.findOne(Id);
			movimiento.setEstatus(0);
			movimiento.setActualizadoPor(auth.getName());
			movimiento.setUltimaFechaModificacion(dateFormat.format(date));
			LookupService.save(movimiento);
			result = true;

		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@PostMapping("/alta-movimiento")
	public boolean AltaMovimiento(Long Id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;
		try {
			AmpLookup movimiento = LookupService.findOne(Id);
			movimiento.setEstatus(1);
			movimiento.setActualizadoPor(auth.getName());
			movimiento.setUltimaFechaModificacion(dateFormat.format(date));
			LookupService.save(movimiento);
			result = true;

		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@PostMapping("/guardar-almacen-fisico")
	public Boolean GuardarAlmacenFisico(String Nombre, Long Encargado) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;

		if (AlmacenFisicoService.findAMPFisicoDuplicate(Nombre, Encargado).size() <= 0) {
			try {
				AmpAlmacenFisico almacenfisico = new AmpAlmacenFisico();
				almacenfisico.setIdEmpleado(Encargado);
				almacenfisico.setNombreAlmacen(Nombre);
				almacenfisico.setEstatus("1");
				almacenfisico.setCreadoPor(auth.getName());
				almacenfisico.setFechaCreacion(dateFormat.format(date));
				AlmacenFisicoService.save(almacenfisico);
				result = true;
			} catch (Exception e) {
				// TODO: handle exception
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	@PostMapping("/editar-almacen-fisico")
	public Boolean EditarAlmacenFisico(String Nombre, Long Encargado, Long Id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;
		if (AlmacenFisicoService.findAMPFisicoDuplicate(Nombre, Encargado).size() <= 0) {
			try {
				AmpAlmacenFisico almacenfisico = AlmacenFisicoService.findOne(Id);
				almacenfisico.setIdEmpleado(Encargado);
				almacenfisico.setNombreAlmacen(Nombre);
				almacenfisico.setActualizadoPor(auth.getName());
				almacenfisico.setUltimaFechaModificacion(dateFormat.format(date));
				AlmacenFisicoService.save(almacenfisico);
				result = true;
			} catch (Exception e) {
				// TODO: handle exception
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	@PostMapping("/guardar-almacen-logico")
	public Boolean GuardarAlmacenLogico(Long AlmacenFisico, String Nombre, Long Entrada, Long Salida) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;
		if (AlmacenLogicoService.findAMPLogicoDuplicate(AlmacenFisico, Nombre, Entrada, Salida).size() <= 0) {
			try {
				AmpAlmacenLogico almacenlogico = new AmpAlmacenLogico();
				almacenlogico.setIdAlmacenFisico(AlmacenFisico);
				almacenlogico.setNombreAlmacenLogico(Nombre);
				almacenlogico.setIdMovimientoEntrada(Entrada);
				almacenlogico.setIdMovimientoSalida(Salida);
				almacenlogico.setCreadoPor(auth.getName());
				almacenlogico.setFechaCreacion(dateFormat.format(date));
				almacenlogico.setEstatus("1");
				AlmacenLogicoService.save(almacenlogico);
				result = true;
			} catch (Exception e) {
				// TODO: handle exception
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	@PostMapping("/editar-almacen-logico")
	public Boolean EditarAlmacenLogico(Long Id, Long AlmacenFisico, String Nombre, Long Entrada, Long Salida) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;
		if (AlmacenLogicoService.findAMPLogicoDuplicate(AlmacenFisico, Nombre, Entrada, Salida).size() <= 0) {
			try {
				AmpAlmacenLogico almacenlogico = AlmacenLogicoService.findOne(Id);
				almacenlogico.setIdAlmacenFisico(AlmacenFisico);
				almacenlogico.setNombreAlmacenLogico(Nombre);
				almacenlogico.setIdMovimientoEntrada(Entrada);
				almacenlogico.setIdMovimientoSalida(Salida);
				almacenlogico.setActualizadoPor(auth.getName());
				almacenlogico.setUltimaFechaModificacion(dateFormat.format(date));
				AlmacenLogicoService.save(almacenlogico);
				result = true;
			} catch (Exception e) {
				// TODO: handle exception
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}

	@GetMapping("/entradas-salidas")
	public List<AmpLookup> EntradasSalidas(String Tipo) {

		return LookupService.findAllLookup(Tipo);
	}

	@GetMapping("/get-all-amp-fisico")
	public List<Object[]> ObtenerAlmacenFisico() {

		return AlmacenFisicoService.findAllAMPFisico();
	}

	@GetMapping("/get-all-amp-logico")
	public List<Object[]> ObtenerAlmacenLogico() {

		return AlmacenLogicoService.findAllAMPLogico();
	}

	@GetMapping("/get-all-amp-movimientos")
	public List<AmpLookup> ObtenerMovimientos() {

		return LookupService.findAllMovements();
	}

	@PostMapping("/baja-almacen")
	public boolean BajaAlmacen(Long Id, String Tipo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;
		try {
			if (Tipo.contains("Fisico")) {
				AmpAlmacenFisico almacenfisico = AlmacenFisicoService.findOne(Id);
				almacenfisico.setEstatus("0");
				almacenfisico.setActualizadoPor(auth.getName());
				almacenfisico.setUltimaFechaModificacion(dateFormat.format(date));
				AlmacenFisicoService.save(almacenfisico);
				result = true;

			} else {
				AmpAlmacenLogico almacenlogico = AlmacenLogicoService.findOne(Id);
				almacenlogico.setEstatus("0");
				almacenlogico.setActualizadoPor(auth.getName());
				almacenlogico.setUltimaFechaModificacion(dateFormat.format(date));
				AlmacenLogicoService.save(almacenlogico);
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@PostMapping("/alta-almacen")
	public boolean AltaAlmacen(Long Id, String Tipo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		boolean result = false;
		try {
			if (Tipo.contains("Fisico")) {
				AmpAlmacenFisico almacenfisico = AlmacenFisicoService.findOne(Id);
				almacenfisico.setEstatus("1");
				almacenfisico.setActualizadoPor(auth.getName());
				almacenfisico.setUltimaFechaModificacion(dateFormat.format(date));
				AlmacenFisicoService.save(almacenfisico);
				result = true;

			} else {
				AmpAlmacenLogico almacenlogico = AlmacenLogicoService.findOne(Id);
				almacenlogico.setEstatus("1");
				almacenlogico.setActualizadoPor(auth.getName());
				almacenlogico.setUltimaFechaModificacion(dateFormat.format(date));
				AlmacenLogicoService.save(almacenlogico);
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

}
