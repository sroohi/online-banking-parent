package com.rab3tech.email.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.rab3tech.vo.EmailVO;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
    private SpringTemplateEngine templateEngine;

	
	@Override
	@Async("threadPool")
	public String sendEquiryEmail(EmailVO mail)  {
		
		 try {
		    MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                StandardCharsets.UTF_8.name());
	        helper.addAttachment("logo1.png", new ClassPathResource("google-logo.png"));
	        helper.addAttachment("logo2.png", new ClassPathResource("email-better-color.png"));
	        Context context = new Context();
	        Map<String,Object> props=new HashMap<>();
	        props.put("name", mail.getName());
	        props.put("sign", "Banking Application");
	        props.put("location", "Fremont CA100 , USA");
	        context.setVariables(props);
	        String html = templateEngine.process("newsletter-template", context);
	        helper.setTo(mail.getTo());
	        helper.setText(html, true);
	        helper.setSubject("Regarding Account enquiry to open an account.");
	        helper.setFrom(mail.getFrom());
	        javaMailSender.send(message);
		 }catch (Exception e) {
			e.printStackTrace();
		 }   
	        return "done";
	}

}
