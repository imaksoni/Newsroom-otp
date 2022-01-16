package com.newsroom.otp.config;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

	@Value("${newsroom.mail.host}")
	private String HOST;
	@Value("${newsroom.mail.port}")
	private String PORT;
	@Value("${newsroom.mail.address}")
	private String ADDRESS;
	@Value("${newsroom.mail.password}")
	private String PASSWD;

	@Bean
	public Session getSession() {
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(ADDRESS, PASSWD);

			}

		});

		session.setDebug(true);

		return session;
	}

}
