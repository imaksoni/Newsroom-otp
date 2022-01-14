package com.newsroom.otp.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class OtpControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException() {

		log.error("Exception: Send OTP Failed");
		
		return new ResponseEntity<String>("FAILURE", HttpStatus.ACCEPTED);
		
	}

}
