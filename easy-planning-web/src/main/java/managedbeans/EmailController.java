/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Asignatura;
import entities.Checklist;
import entities.Encuesta;
import entities.ParamSemestreAno;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.ejb.EJB;
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
import sessionbeans.ChecklistFacadeLocal;
import sessionbeans.EncuestaFacadeLocal;
import sessionbeans.ParamSemestreAñoFacadeLocal;

/**
 *
 * @author alonso
 */

@Named("emailController")
@SessionScoped
public class EmailController implements Serializable {
    @Inject
    ProfesorController profesorController;
    
    @EJB
    private ParamSemestreAñoFacadeLocal ejbSemAño;
    @EJB
    private EncuestaFacadeLocal ejbEncuesta;
    @EJB
    private ChecklistFacadeLocal ejbCheck;

    public EncuestaFacadeLocal getEjbEncuesta() {
        return ejbEncuesta;
    }

    public void setEjbEncuesta(EncuestaFacadeLocal ejbEncuesta) {
        this.ejbEncuesta = ejbEncuesta;
    }

    public ParamSemestreAñoFacadeLocal getEjbSemAño() {
        return ejbSemAño;
    }

    public void setEjbSemAño(ParamSemestreAñoFacadeLocal ejbSemAño) {
        this.ejbSemAño = ejbSemAño;
    }
    
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
        
        JsfUtil.addSuccessMessage("El mensaje se ha enviado");
        } catch (AddressException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            JsfUtil.addSuccessMessage("AddressException");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            JsfUtil.addErrorMessage("Error en la red");
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
            String contenido = "Profesor" + " " + profesor + " " + "conteste la encuesta de disponibilidad horaria, por favor. http://localhost:8080/easy-planning-web/faces/profesor/encuesta.xhtml";
            
            Encuesta encuesta = new Encuesta();
            encuesta.setProfesor(profesorController.getSelected());
            ParamSemestreAno semAnho = getEjbSemAño().find(Long.parseLong(1+""));
            encuesta.setAño(semAnho.getAnoActual());
            encuesta.setSemestre(semAnho.getSemestreActual());
            getEjbEncuesta().create(encuesta);
            
            for(Asignatura asignatura : profesorController.getAsignaturasProfesor(profesorController.getSelected().getId())){
                Checklist check = new Checklist();
                check.setAceptado(false);
                check.setAsignatura(asignatura);
                check.setEncuesta(encuesta);
                ejbCheck.create(check);
            }
            
            enviarEmail(origen, nombre, pass, emailProfesor, asunto, contenido);
        }
    }
}