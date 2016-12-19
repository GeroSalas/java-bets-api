package com.gen.desafio.api.utils;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.gen.desafio.api.domain.model.Ticket;
import com.gen.desafio.api.domain.model.User;


@Component
public class EmailSender {
    
	private static final Logger log = LoggerFactory.getLogger(EmailSender.class);
	
	private static final String gmailUsername = "geronimo.perez.salas@gmail.com";
	private static final String gmailPassword = "Geronimo10@";
	
	private static Properties props;
	
    // Consultar Configuracion Cuenta Google: https://www.google.com/settings/security/lesssecureapps
	

  static {
	  props = new Properties();
	  props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
	  props.put("mail.smtp.port", "465");
	  // Via SSL
	  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	  props.put("mail.imaps.ssl.trust", "*");
      props.put("mail.smtp.socketFactory.port", "465");
	  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      props.put("mail.smtp.user", gmailUsername);
      props.put("mail.smtp.password", gmailPassword);
  }
    
	
  @Async    
  public static void notifyMobileUser(User mobileUser){  
     Session session = Session.getDefaultInstance(props,  
           new Authenticator() {  
             protected PasswordAuthentication getPasswordAuthentication() {  
              return new PasswordAuthentication(gmailUsername, gmailPassword);  
            }  
           });  
     session.setDebug(true);

    //Build Mail  
    try{
       MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(gmailUsername));  
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mobileUser.getUsername())); 
        message.setSubject("[NOTIFICACION] - DesafioApp");
        message.setText("Hola " + mobileUser.getFullName() + ", sus nuevas credenciales de acceso son: \n\n "
        		        + "Usuario: "    + mobileUser.getUsername() + "\n"
        		        + "Contraseña: " + mobileUser.getPassword() );  
        
       Transport.send(message);  
     }
     catch (MessagingException ex) {
          log.error("Error ocurred while sending Email " + ex.getMessage());
     }
    
   }
  
  
  
  @Async    
  public static void welcomeClient(User admin){
     Session session = Session.getDefaultInstance(props,  
           new Authenticator() {  
             protected PasswordAuthentication getPasswordAuthentication() {  
              return new PasswordAuthentication(gmailUsername, gmailPassword);  
            }  
           });  
     session.setDebug(true);

    //Build Mail  
    try{
       MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(gmailUsername));  
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(admin.getUsername())); 
        message.setSubject("BIENVENIDO A DESAFIO APP !");
        message.setText("Hola " + admin.getFullName() + ", " +
        		        "sus credenciales de acceso como Administrador para la Cuenta " +admin.getRelatedClient().getName()+ " son: \n\n "
        		        + "Usuario: "    + admin.getUsername() + "\n"
        		        + "Contraseña: " + admin.getPassword() );  
        
       Transport.send(message);  
     }
     catch (MessagingException ex) {
          log.error("Error ocurred while sending Email " + ex.getMessage());
     }
    
   }
  
  
  @Async    
  public static void notifyMobileUserPostCensure(User mobileUser){
     Session session = Session.getDefaultInstance(props,  
           new Authenticator() {  
             protected PasswordAuthentication getPasswordAuthentication() {  
              return new PasswordAuthentication(gmailUsername, gmailPassword);  
            }  
           });  
     session.setDebug(true);

    //Build Mail  
    try{
       MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(gmailUsername));  
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mobileUser.getUsername())); 
        message.setSubject("[NOTIFICACION] - DesafioApp");
        message.setText("Queremos informarle que una de sus publicaciones/comentarios fue editada o quizás censurada por el Administrador debido a su contenido inadecuado.");  
        
       Transport.send(message);         
     }
     catch (MessagingException ex) {
          log.error("Error ocurred while sending Email " + ex.getMessage());
     }
    
   }
  
  
  @Async    
  public static void notifyTicketsInCopy(User user, Ticket ticket){  
     Session session = Session.getDefaultInstance(props,  
           new Authenticator() {  
             protected PasswordAuthentication getPasswordAuthentication() {  
              return new PasswordAuthentication(gmailUsername, gmailPassword);  
            }  
           });  
     session.setDebug(true);

    //Build Mail  
    try{
       MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(gmailUsername));  
        if(ticket.getTicketType()==1){ 
        	// Contact Ticket
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getRelatedClient().getAdminUser().getUsername()));
        	message.setSubject("Nuevo Ticket de Contacto en DesafioApp");
        }
        else{ 
        	// Support Ticket
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress("geronimo.perez.salas@gmail.com"));
        	message.setSubject("Nuevo Ticket de Soporte en DesafioApp");
        }     
        
        message.setText("INFO TICKET #" + ticket.getId() + " \n\n"
        				+ "Titulo: "    + ticket.getTitle() + "\n"
        				+ "Descripcion: " + ticket.getDescription() + " \n"
        		        + "Usuario: "     + user.getUsername() + " \n"
        		        + "Reporta: "     + user.getFullName() + " \n");  
        
       Transport.send(message);  
     }
     catch (MessagingException ex) {
          log.error("Error ocurred while sending Email " + ex.getMessage());
     }
    
   }

  
}