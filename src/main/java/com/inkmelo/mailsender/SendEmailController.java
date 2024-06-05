package com.inkmelo.mailsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendEmailController {

	@Autowired
	private SendEmailService sendEmailService;
	
	@GetMapping("sendEmail")
	public String sendEmail() {
		sendEmailService.sendEmail("khanhnqse170545@fpt.edu.vn", "Body", "Xác nhận thanh toán thành công");
		return "Sent successfully";
	}
	
	
}
