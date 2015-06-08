/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author jano
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Usuario.findByUsername",
            query="SELECT u FROM Usuario u WHERE u.username = :username")
})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String username;
    private String nombre_usuario;
    private String apellido_usuario;
    private String password;
    private boolean activo = true;

    @OneToMany
    private List<TipoUsuario> tipos;

    public List<TipoUsuario> getRoles() {
        return tipos;
    }

    public void setRoles(List<TipoUsuario> roles) {
        this.tipos = roles;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getApellido_usuario() {
        return apellido_usuario;
    }

    public void setApellido_usuario(String apellido_usuario) {
        this.apellido_usuario = apellido_usuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
