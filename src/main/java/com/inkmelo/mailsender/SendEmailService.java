package com.inkmelo.mailsender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    public void sendConfirmEmail(String receipt, String body, String subject) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String signature = 
        		"<br><br>" +
                        "<div style='font-family: Arial, sans-serif; color: #333;'>" +
                        "Bất cứ lúc nào, bạn có thể tra cứu trạng thái các đơn hàng đã mua " +
                        "<a href='http://example.com' style='color: #1a73e8;'>TẠI ĐÂY</a> bằng cách cung cấp thông tin về đơn hàng theo yêu cầu.<br>" +
                        "<hr style='border:none; border-top:1px solid #ccc;'>" +
                        "Đây là email được gửi tự động, vui lòng không phản hồi email này. Để tìm hiểu thêm các quy định về đơn hàng hay các chính sách sau bán hàng của InkMelo, " +
                        "vui lòng truy cập <a href='http://example.com' style='color: #1a73e8;'>TẠI ĐÂY</a> hoặc gọi đến 0955 123 456 (trong giờ hành chính) để được hướng dẫn.<br><br>" +
                        "<div style='display: flex; align-items: center;'>" +
                        "<img src='cid:logoImage' style='float: left; width: 100px; height: auto; margin-right: 10px;'>" +
                        "</div>" +
                        "</div>";

        helper.setFrom(fromEmailId);
        helper.setTo(receipt);
        helper.setSubject(subject);
        helper.setText(body + signature, true);

        File imageFile = new File("D:/SEMESTER 7/SWD392/InkMelo_SpringBoot_BE/src/main/resources/Logo.jpg");
        helper.addInline("logoImage", imageFile);

        javaMailSender.send(mimeMessage);
    }
    public void sendResetPasswordEmail(String to, String token) throws MessagingException {
		String subject = "Password Reset Request";
//		String url = "http://localhost:8080/store/api/v1/users/reset-password-confirm?token=" + token;
		String url = token;
		String message = "<p>Hi,</p>" + 
						 "<p>You have requested to reset your password.</p>" +
						 "<p>Click the link below to change your password:</p>" +
						 "<p><a href=\"" + url + "\">Change my password</a></p>" +
						 "<br>" +
						 "<p>If you did not request this, please ignore this email.</p>";

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setText(message, true);
		helper.setTo(to);
		helper.setSubject("YÊU CẦU THAY ĐỔI MẬT KHẨU");
		helper.setFrom(fromEmailId);
		
		javaMailSender.send(mimeMessage);
	}
}
