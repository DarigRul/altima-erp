package com.altima.springboot.app.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {
        //Metodo que asigna credenciales
	@Bean
    public JavaMailSender getJavaMailSender() {
        
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.uniformes-altima.com.mx");
        mailSender.setPort(26);
        mailSender.setUsername("dtu_test@uniformes-altima.com.mx");
        mailSender.setPassword("ForeverAltima123");
 
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
 
        return mailSender;
    }
    
}