package ca.sheridancollege.emails;

import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import ca.sheridancollege.beans.Equipment;
import ca.sheridancollege.database.DatabaseAccess;


@Component
public class EmailServiceImpl {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private DatabaseAccess da;
	
	public void sendSimpleMsg(String to,String subject,String text) {
		SimpleMailMessage msg=new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(text);
		emailSender.send(msg);
	}
	
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public void sendDynamicMsg(String to,String subject,String footer) throws MessagingException{
		
		ArrayList<Equipment> equs = da.getEquipments();
		
		//Prepare an evaluation context
		Context context=new Context();
		context.setVariable("equs", equs);
		context.setVariable("footer", footer);
		
		//Prepare the email message
		MimeMessage mimeMessage=this.emailSender.createMimeMessage();
		MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true,"UTF-8");
		
		//Set to and subject
		message.setTo(to);
		message.setSubject(subject);
		
		String htmlContent=this.templateEngine.process("emailTemplate.html",context);
		
		message.setText(htmlContent,true);
		
		this.emailSender.send(mimeMessage);
	}
}
