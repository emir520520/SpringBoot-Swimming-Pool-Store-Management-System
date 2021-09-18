package ca.sheridancollege.emails;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SpringMail {
	
	//Create the connection into our gmail account
	@Bean
	public JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl	mailSender=new JavaMailSenderImpl();
		//Identify the email server
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		
		//Connect to that email
		mailSender.setUsername("emir520520@gmail.com");
		mailSender.setPassword("fyx20010111");
		
		//Set up the properties for that connection
		Properties properties=mailSender.getJavaMailProperties();
		properties.put("mail.transport.protocol","smtp");
		properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.debug","true");
		
		return mailSender;
	}
}