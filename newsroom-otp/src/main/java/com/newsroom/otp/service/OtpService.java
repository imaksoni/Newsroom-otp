package com.newsroom.otp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OtpService {
	@Value("${nesroom.otp.account.phone}")
	private String PHONE;
	@Value("${newsroom.mail.address}")
	private String fromMail;

	@Autowired
	private Session session;

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

	public boolean verifyOtp(String userID, String otpFromUser) {

		String otpGenerated = otpMap.get(userID);
		if (otpFromUser != null && otpFromUser.equals(otpGenerated)) {
			log.info("OTP Matched!!");
			return true;
		}

		log.info("Wrong OTP Entered!!");
		return false;

	}

	public void sendMailOtp(String userID, String toMail) throws Exception {

		try {

			String otp = generateOtp();

			String text = "Your Newsroom OTP is : " + otp;

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(fromMail));

			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toMail));

			message.setSubject("Welcome to Newsroom :)");

			message.setText(text);

			log.info("Sending.. userID-{}", userID);

			Transport.send(message);

			log.info("OTP has been sent succesfully.. userID-{}", userID);

			otpMap.put(userID, otp);
		}

		catch (MessagingException mex) {
			log.error("Exception:{} for userID-{}", mex.getMessage(), userID);
			mex.printStackTrace();
		}

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
