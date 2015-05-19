/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Usuario;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managedbeans.util.JsfUtil;
import sessionbeans.UsuarioFacadeLocal;

/**
 *
 * @author jano
 */
@Named(value = "usuarioController")
@RequestScoped
public class UsuarioController {

    private String nombre;
    private String password;
    @EJB
    private UsuarioFacadeLocal ejbUsuario;

    public UsuarioController(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNombre() {
        /*
        if (nombre == null) {
            getDatosUsuario();
        }
        return nombre == null ? "" : nombre;
        */
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void login(){
        System.out.println("Función login: Comenzando autenticación");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            if(!hasIdentity()) {
                request.login(nombre, password);
                System.out.println("SessionUtil: SessionScope created for " + nombre);
                JsfUtil.addSuccessMessage("Logeado con éxito");
                Usuario usuario = ejbUsuario.findByUsername(nombre);
                System.out.println("nombre de usuario: "+usuario.getUsername()+" - rol: "+usuario.getRoles().get(0).getTipo());
                if(usuario.getRoles().get(0).getTipo().equals("Coordinador Docente")){
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/easy-planning-web/faces/coordinador_docente/index.xhtml");
                }
                else if(usuario.getRoles().get(0).getTipo().equals("Profesor")){
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/easy-planning-web/faces/profesor/index.xhtml");
                }
                else{
                    System.out.println("no se sabe el rol");
                }
            } else {
                System.out.println("SessionUtil: User allready logged");
                
            }
            
        } 
        catch (Exception e) {
            System.out.println("SessionUtil: User or password not found");
            JsfUtil.addErrorMessage("El usuario y/o la contraseña no coinciden");
            // mc.mensajeRetroalimentacion("Error", "Usuario y/o contraseña incorrecta");
        }
    }
    
    public boolean hasIdentity(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        
        if(request.getRemoteUser() == null){
            return false;
        }
        return true;
    }
    
    public void logout() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect("/easy-planning-web/faces/login.xhtml");
    }
    
}
