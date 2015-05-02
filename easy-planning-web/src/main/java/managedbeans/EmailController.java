/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.Serializable;
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import managedbeans.util.JsfUtil;

/**
 *
 * @author alonso
 */

@Named("emailController")
@SessionScoped
public class EmailController implements Serializable {
    @Inject
    ProfesorController profesorController;

    public void enviarEmail(String origen, String pass, String destino, String asunto, String contenido) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", origen);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        
        Session session = Session.getInstance(props,null);
        MimeMessage message = new MimeMessage(session);
        
        System.out.println("Port: "+session.getProperty("mail.smtp.port"));
        
        try {
        InternetAddress from = new InternetAddress(origen);
        message.setSubject(asunto);   //asunto del correo
        message.setFrom(from);
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));

        // Create a multi-part to combine the parts
        Multipart multipart = new MimeMultipart("alternative");

        // Create your text message part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("some text to send");

        // Add the text part to the multipart
        multipart.addBodyPart(messageBodyPart);

        // Create the html part
        messageBodyPart = new MimeBodyPart();
        String htmlMessage = contenido;
        messageBodyPart.setContent(htmlMessage, "text/html");


        // Add html part to multi part
        multipart.addBodyPart(messageBodyPart);

        // Associate multi-part with message
        message.setContent(multipart);

        // Send message
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", origen, pass);
        System.out.println("Transport: "+transport.toString());
        transport.sendMessage(message, message.getAllRecipients());


    } catch (AddressException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (MessagingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        
        System.out.println("Enviar Email");
    }
    
    public void enviarNotificacion() throws Exception {
        String origen = "coordinador.docente.diinf@gmail.com";
        String pass = "coordinador";
        
        if(profesorController.getSelected() != null) {
            String emailProfesor = profesorController.getSelected().getMail();
            String profesor = profesorController.getSelected().getNombre() + " " + profesorController.getSelected().getApellido();
            String asunto = "Encuesta de disponibilidad horaria";
            String contenido = "Profesor" + " " + profesor + " " + "conteste la encuesta de disponibilidad horaria, por favor.";
            
            enviarEmail(origen, pass, emailProfesor, asunto, contenido);
            JsfUtil.addSuccessMessage("El mensaje ha sido enviado exitosamente.");
            
        }
        
        
        System.out.println("Hola");
    }
}