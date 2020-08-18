package com.altima.springboot.app.models.service;

import java.io.ByteArrayOutputStream;

import javax.mail.MessagingException;

public interface IEnviarCorreoService {
	
	void enviarCorreoSencillo(String From, String To, String Subject, String Text);
	
	void enviarCorreoArchivoAdjunto(String From, String To, String Subject, String Text, String pathFile, String nameFile);
	
	void enviarCorreoArchivoAdjuntoConMime(String From, String To, String Subject, String Text, ByteArrayOutputStream baos, String nameFile) throws MessagingException;
}
