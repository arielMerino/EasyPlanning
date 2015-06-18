/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.ProfesoresLocal;
import entities.Asignatura;
import entities.Checklist;
import entities.Encuesta;
import entities.Profesor;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
import sessionbeans.ParamSemestreAnioFacadeLocal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author alonso
 */

@Named("emailController")
@SessionScoped
public class EmailController implements Serializable {
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    @Inject
    ProfesorController profesorController;    
    @EJB
    private ParamSemestreAnioFacadeLocal ejbSemAnio;
    @EJB
    private EncuestaFacadeLocal ejbEncuesta;
    @EJB
    private ChecklistFacadeLocal ejbCheck;
    @EJB
    private ProfesoresLocal profesorBusiness;

    public EncuestaFacadeLocal getEjbEncuesta() {
        return ejbEncuesta;
    }

    public void setEjbEncuesta(EncuestaFacadeLocal ejbEncuesta) {
        this.ejbEncuesta = ejbEncuesta;
    }

    public ParamSemestreAnioFacadeLocal getEjbSemAnio() {
        return ejbSemAnio;
    }

    public void setEjbSemAnio(ParamSemestreAnioFacadeLocal ejbSemAnio) {
        this.ejbSemAnio = ejbSemAnio;
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
            logger.debug("Error con la dirección del correo", e);
            System.out.println(e.getMessage());
            JsfUtil.addSuccessMessage("AddressException");
        } catch (MessagingException e) {
            logger.debug("Error en la red", e);
            System.out.println(e.getMessage());
            JsfUtil.addErrorMessage("Error en la red");
        }
    }
    
    public void enviarNotificacion() throws Exception {
        String origen = "coordinador.docente.diinf@gmail.com";
        String pass = "coordinador";                
        List<Profesor> listaProfesor = profesorController.getProfesorSeleccionado();
        
        if(!listaProfesor.isEmpty()) {
            int semestre = ejbSemAnio.find(1L).getSemestreActual();
            int anio = ejbSemAnio.find(1L).getAnoActual();
            String nombre = "PAMELA AGUIRRE GUZMÁN";
            String asunto = "Encuesta de disponibilidad horaria";
            
            for(Profesor p : listaProfesor){                
                
                String emailProfesor = p.getMail();               
                String contenido = "Estimado Profesor:<br><br>";
                contenido = contenido +
                "El Comité de Docencia estará muy agradecido de que conteste la "
                + "encuesta que se adjunta en el enlace para poder conocer su "
                + "disponibilidad horaria y tener esta información para realizar"
                + " la Planificación Docente del semestre " + semestre + " - " + 
                anio + ".<br><br>http://localhost:8080/easy-planning-web/"
                + "<br><br>Si el enlace no funciona correctamente, contáctese a la "
                + "brevedad con su Coordinador(a) Docente<br><br>De antemano, "
                + "gracias por su colaboración.<br>Saludos cordiales";                        

                String rutProfesor = p.getRutProfesor();                       
                Encuesta encuesta = profesorBusiness.getEncuestaBySemestreAndAnio(rutProfesor, semestre, anio);

                if(encuesta == null){
                    encuesta = new Encuesta();
                    encuesta.setProfesor(p);            
                    encuesta.setAnio(anio);
                    encuesta.setSemestre(semestre);
                    getEjbEncuesta().create(encuesta);

                    for(Asignatura asignatura : profesorController.getAsignaturasProfesor(rutProfesor)){
                        Checklist check = new Checklist();
                        check.setAceptado(false);
                        check.setAsignatura(asignatura);
                        check.setEncuesta(encuesta);
                        ejbCheck.create(check);
                    }                            
                }
                
                if(emailProfesor != null){                    
                    enviarEmail(origen, nombre, pass, emailProfesor, asunto, contenido);                    
                }
                else{                    
                    String mensaje = "Profesor " + p.getNombre() + " " + p.getApellido();
                    mensaje += " no tiene mail asociado";
                    JsfUtil.addErrorMessage(mensaje);
                }
            }
        }else{
            JsfUtil.addErrorMessage("No has seleccionado ningún profesor");
        }
    }
}