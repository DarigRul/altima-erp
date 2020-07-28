package com.altima.springboot.app.models.service;

public class AlmacenamientoExcepcionService extends RuntimeException {
	public AlmacenamientoExcepcionService(String message) {
		super(message);
	}

	public AlmacenamientoExcepcionService(String message, Throwable cause) {
		super(message, cause);
	}

}
