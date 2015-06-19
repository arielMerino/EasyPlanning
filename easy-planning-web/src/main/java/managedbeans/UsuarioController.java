/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.UsuariosLocal;
import entities.TipoUsuario;
import entities.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import managedbeans.util.JsfUtil;
import org.apache.http.HttpResponse;
import sessionbeans.UsuarioFacadeLocal;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.primefaces.json.JSONObject;

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
    private List<TipoUsuario> roles;
    @EJB
    private UsuarioFacadeLocal ejbUsuario;
    @EJB
    private UsuariosLocal usuarioBussines;

    public List<TipoUsuario> getRoles() {
        return roles;
    }

    public void setRoles(List<TipoUsuario> roles) {
        this.roles = roles;
    }

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
    
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    
    public void login(){
        System.out.println("Función login: Comenzando autenticación");
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            if(!hasIdentity()) {
                //Autenticación con LDAP
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://inicio.diinf.usach.cl/webservice.php");

                // Add your data
                List<BasicNameValuePair> nameValuePairs = new ArrayList<>(2);
                nameValuePairs.add(new BasicNameValuePair("user", nombre));
                nameValuePairs.add(new BasicNameValuePair("pass", password));
                nameValuePairs.add(new BasicNameValuePair("keyapi", MD5("c55ecd5c60a5a5b2bea1c92bbc45f8ab")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                String responseString = new BasicResponseHandler().handleResponse(response);
                System.out.println(responseString);
                
                /*
                JSONParser parser = new JSONParser(null, null)

                Object obj = parser.parse(responseString);
                */
                
                JSONObject jsonObject = new JSONObject(responseString);

                Boolean valido_response = (Boolean) jsonObject.get("pass_ok");
                if(valido_response == null) {
                    valido_response = false;
                }
                System.out.println("Datos Validos: " + valido_response);               
                
                //FIN AUTENTICACIÓN LDAP
                
                
                request.login(nombre, jsonObject.getString("pass_ok"));
                System.out.println("SessionUtil: SessionScope created for " + nombre);
                JsfUtil.addSuccessMessage("Logeado con éxito");
                Usuario usuario = usuarioBussines.findByUid(nombre);
                setRoles(usuario.getRoles());
                System.out.println("nombre de usuario: "+usuario.getNombre_usuario()+" - rol: "+usuario.getRoles().get(0).getTipo());
                if(usuario.getRoles().get(0).getTipo().equals("COORDINADOR DOCENTE")){
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/easy-planning-web/faces/coordinador_docente/index.xhtml");
                }
                else if(usuario.getRoles().get(0).getTipo().equals("PROFESOR")){
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
    
    public boolean compruebaRoles(){
        boolean roles = false;
        if(getRoles().size() > 1){
            if(getRoles().get(0).getTipo().equals("COORDINADOR DOCENTE") && getRoles().get(1).getTipo().equals("PROFESOR")){
                roles = true;
            }
            else if(getRoles().get(1).getTipo().equals("COORDINADOR DOCENTE") && getRoles().get(0).getTipo().equals("PROFESOR")){
                roles = true;
            }
        }
        return roles;
    }
    
    public String getRut(){
        Usuario user = usuarioBussines.findByUid(nombre);
        return user.getRut_usuario();
    }
    
}
