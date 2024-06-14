package com.inkmelo.mailsender;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Tag(name = "Mail Sender", description = "Mail Sender Management APIs")
@RestController
@RequiredArgsConstructor
public class SendEmailController {

	private final SendEmailService sendEmailService;
	
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
