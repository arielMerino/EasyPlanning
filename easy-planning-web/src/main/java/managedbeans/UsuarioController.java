/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
//@RequestScoped
@SessionScoped
public class UsuarioController implements Serializable{

    private String nombre;
    private String password;
    private boolean error = false;
    @EJB
    private UsuarioFacadeLocal ejbUsuario;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

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
                error = false;
            } 
            else {
                System.out.println("SessionUtil: User allready logged");
                error = false;
            }
            
        } 
        catch (Exception e) {
            System.out.println("SessionUtil: User or password not found");
            JsfUtil.addErrorMessage("El usuario y/o la contraseña no coinciden");
            error = true;
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
