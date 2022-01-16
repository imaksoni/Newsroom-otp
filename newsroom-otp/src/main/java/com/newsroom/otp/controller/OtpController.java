package com.newsroom.otp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.newsroom.otp.model.Mail;
import com.newsroom.otp.model.Mob;
import com.newsroom.otp.model.OTP;
import com.newsroom.otp.service.OtpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "*" })
@Controller
public class OtpController {

	private static final String USER_ID = "user-id";

	@Autowired
	private OtpService service;

	@PostMapping("/phoneOtp")
	public ResponseEntity<String> sendPhoneOtp(@RequestHeader Map<String, String> headers, @RequestBody Mob mob)
			throws Exception {

		String userID = headers.get(USER_ID);
		String phone = mob.getMob();

		System.out.println(headers);

		log.info("Request: Send OTP for userID: {}, Mob: {}", userID, phone);

		service.sendPhoneOtp(userID, phone);

		log.info("Response: Sent OTP for userID: {}, Mob: {}", userID, phone);

		return new ResponseEntity<String>("SUCCESS", HttpStatus.ACCEPTED);

	}

	@PostMapping("/mailOtp")
	public ResponseEntity<String> sendMailOtp(@RequestHeader Map<String, String> headers, @RequestBody Mail mail)
			throws Exception {

		String userID = headers.get(USER_ID);
		String toMail = mail.getMail();

		System.out.println(headers);

		log.info("Request: Send OTP for userID: {}, Mail: {}", userID, toMail);

		service.sendMailOtp(userID, toMail);

		log.info("Response: Sent OTP for userID: {}, Mail: {}", userID, toMail);

		return new ResponseEntity<String>("SUCCESS", HttpStatus.ACCEPTED);

	}

	@PostMapping({"/verifyPhoneOtp","/verifyMailOtp"})
	public ResponseEntity<String> verifyPhoneOtp(@RequestHeader Map<String, String> headers, @RequestBody OTP otp)
			throws Exception {

		String userID = headers.get(USER_ID);
		String oTP = otp.getOtp();

		log.info("Request: Verify OTP for userID: {}", userID);

		boolean status = service.verifyOtp(userID, oTP);

		log.info("Response: Verify OTP for userID: {}", userID);

		if (status) {
			return new ResponseEntity<String>("SUCCESS", HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<String>("FAILURE", HttpStatus.ACCEPTED);

	}

}
