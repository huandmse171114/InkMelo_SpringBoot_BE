package com.inkmelo.mailsender;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;

@RestController
public class SendEmailController {

	@Autowired
	private SendEmailService sendEmailService;
	
	@GetMapping("sendEmail")
	public String sendEmail() {
		try {
            sendEmailService.sendEmail("khanhnqse170545@fpt.edu.vn", "Đơn hàng của bạn đã thanh toán thành công", "Xác nhận thanh toán thành công");
            return "Email sent successfully";
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
	}
}
