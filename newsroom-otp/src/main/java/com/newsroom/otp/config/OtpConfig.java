package com.newsroom.otp.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class OtpConfig {

	@Value("${newsroom.otp.account.sid}")
	private String ACCOUNT_SID;
	@Value("${newsroom.otp.account.token}")
	private String AUTH_TOKEN;

	@PostConstruct
	public void TwilioInitializer() {
		try {
			log.info("Connecting to SMS Server..");

			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			log.info("Connected to SMS Server!!");
		}

		catch (Exception ex) {
			log.error("SMS server issue: {}", ex.getMessage());
		}
	}

}
