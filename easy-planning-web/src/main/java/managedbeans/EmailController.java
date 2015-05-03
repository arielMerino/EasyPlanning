/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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

    public void enviarEmail(String origen, String nombre, String pass, String destino, String asunto, String contenido) throws UnsupportedEncodingException {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", origen);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);
        
        Session session = Session.getInstance(props,null);
        MimeMessage message = new MimeMessage(session);
        
        try {
        
        // Direccion origen
        InternetAddress from = new InternetAddress(origen, nombre);
        
        message.setFrom(from);
        
        // Direccion destino
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
        
        // Asunto del email
        message.setSubject(asunto);
        
        Multipart mp = new MimeMultipart();
        
        // Contenido del email
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(contenido, "text/html");
        mp.addBodyPart(htmlPart);
        message.setContent(mp);

        // Enviar mensaje
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", origen, pass);
        transport.sendMessage(message, message.getAllRecipients());

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public void enviarNotificacion() throws Exception {
        String origen = "coordinador.docente.diinf@gmail.com";
        String pass = "coordinador";
        
        if(profesorController.getSelected() != null) {
            String emailProfesor = profesorController.getSelected().getMail();
            String nombre = "PAMELA AGUIRRE GUZMÁN";
            String profesor = profesorController.getSelected().getNombre() + " " + profesorController.getSelected().getApellido();
            String asunto = "Encuesta de disponibilidad horaria";
            String contenido = "Profesor" + " " + profesor + " " + "conteste la encuesta de disponibilidad horaria, por favor.";
            
            enviarEmail(origen, nombre, pass, emailProfesor, asunto, contenido);
            JsfUtil.addSuccessMessage("El mensaje se ha enviado.");
            
        }
    }
}