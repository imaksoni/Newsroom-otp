package com.newsroom.otp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OtpService {
	@Value("${nesroom.otp.account.phone}")
	private String PHONE;

	private Map<String, String> otpMap = new HashMap<String, String>();
	private Random random = new Random();

	public void sendPhoneOtp(String userID, String userPhone) throws Exception {
		String otp = generateOtp();

		userPhone = "+91" + userPhone;
		String sms = "Your Newsroom OTP is : " + otp;

		Message message = Message
				.creator(new com.twilio.type.PhoneNumber(userPhone), new com.twilio.type.PhoneNumber(PHONE), sms)
				.create();

		log.info("OTP Delivery Status: ", message.getStatus());

		otpMap.put(userID, otp);

	}

	public boolean verifyPhoneOtp(String userID, String otpFromUser) {

		String otpGenerated = otpMap.get(userID);
		if (otpFromUser != null && otpFromUser.equals(otpGenerated)) {
			log.info("OTP Matched!!");
			return true;
		}

		log.info("Wrong OTP Entered!!");
		return false;

	}

	public String generateOtp() {

		String ret = "";
		for (int i = 0; i < 6; i++) {
			ret += random.nextInt(10);
		}
		System.out.println("Generated OTP: " + ret);
		return ret;

	}

}
