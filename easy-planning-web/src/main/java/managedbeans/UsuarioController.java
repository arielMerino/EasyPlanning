/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managedbeans.util.JsfUtil;

/**
 *
 * @author jano
 */
@Named(value = "usuarioController")
@RequestScoped
public class UsuarioController {

    private String nombre;
    private String password;

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
                FacesContext.getCurrentInstance().getExternalContext().redirect("/easy-planning-web/faces/index.xhtml");
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
    
    /**
     * Obtiene el nombre del usuario de la sesión
     */
    private void getDatosUsuario() {
        
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Object objPeticion = context.getRequest();
        if (!(objPeticion instanceof HttpServletRequest)) {
            System.out.println("Error objeto es: "
                    + objPeticion.getClass().toString());
            return;
        }
        HttpServletRequest peticion = (HttpServletRequest) objPeticion;
        
        nombre = peticion.getRemoteUser();
        System.out.println("UsuarioController: "+nombre);
        if (nombre == null) {
            JsfUtil.addErrorMessage("Nombre de usuario y contraseña no coinciden");
            System.out.println("FALLO EN EL LOGIN");
            //logout();
        }
        
        /*
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        
        try{
            request.login(nombre, nombre);
        }
        catch(Exception e){
            
        }
        */
    }
    /*
    public void logout() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        try {
            ec.redirect(ec.getRequestContextPath());
        } catch (IOException ex) {
            System.out.println("FALLO EN EL LOGIN-----");
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
}
