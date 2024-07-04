package com.inkmelo.mailsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class SendEmailService {

    @Value("${spring.mail.username}")
    private String fromEmailId;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ResourceLoader resourceLoader;


    public void sendPaymentConfirmEmail(String receipt, String body, String subject) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String signature =
                "<br><br>" +
                        "<div style='font-family: Arial, sans-serif; color: #333;'>" +
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

        Resource resource = resourceLoader.getResource("classpath:Logo.jpg");
        helper.addInline("logoImage", resource);

        javaMailSender.send(mimeMessage);
    }
    
    public void sendOrderSuccessfulEmail(String receipt, String body, String subject) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        String signature =
        	    "<br>" +
        	    "Mã vận đơn của bạn là: <strong style='font-weight: bold;'>" + /*orderCode + */"</strong>" +
        	    "<br>" + 
        	    "<div style='font-family: Arial, sans-serif; color: #333;'>" +
        	    "Bất cứ lúc nào, quý khách có thể tra cứu tình trạng đơn hàng " +
        	    "<a href='http://example.com' style='color: #1a73e8;'>TẠI ĐÂY</a> bằng cách cung cấp mã vận đơn.<br>" +
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

        Resource resource = resourceLoader.getResource("classpath:Logo.jpg");
        helper.addInline("logoImage", resource);

        javaMailSender.send(mimeMessage);
    }
    
    public void sendResetPasswordEmail(String receipt, String token) throws MessagingException {
		String subject = "Password Reset Request";
//		String url = "http://localhost:8080/store/api/v1/users/reset-password-confirm?token=" + token;
		String url = token;
		String message = "<p>Hi,</p>" + 
						 "<p>Bạn đã yêu cầu thay đổi mật khẩu.</p>" +
						 "<p>Vui lòng truy cập vào link dưới đây để thay đổi mật khẩu:</p>" +
						 "<p><a href=\"" + url + "\">Đổi mật khẩu</a></p>" +
						 "<br>" +
						 "<p>Nếu bạn chưa từng yêu cầu thay đổi mật khẩu, vui lòng bỏ qua email này.</p>";

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setText(message, true);
		helper.setTo(receipt);
		helper.setSubject("YÊU CẦU THAY ĐỔI MẬT KHẨU");
		helper.setFrom(fromEmailId);
		
		Resource resource = resourceLoader.getResource("classpath:Logo.jpg");
        helper.addInline("logoImage", resource);

        javaMailSender.send(mimeMessage);
		
		javaMailSender.send(mimeMessage);
	}
}
