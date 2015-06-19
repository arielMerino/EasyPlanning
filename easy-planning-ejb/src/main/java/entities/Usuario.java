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
import javax.validation.constraints.Size;

/**
 *
 * @author jano
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Usuario.findByUid",
            query = "SELECT u FROM Usuario u WHERE u.uid = :uid")
})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Size(min=1, max=20)
    private String rut_usuario;
    @Size(min=1, max=20)
    private String uid;
    @Size(min=1, max=50)
    private String nombre_usuario;
    @Size(min=1, max=50)
    private String apellido_usuario;
    private boolean activo = true;

    @OneToMany
    private List<TipoUsuario> tipos;

    public String getRut_usuario() {
        return rut_usuario;
    }

    public void setRut_usuario(String rut_usuario) {
        this.rut_usuario = rut_usuario;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<TipoUsuario> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoUsuario> tipos) {
        this.tipos = tipos;
    }

    public List<TipoUsuario> getRoles() {
        return tipos;
    }

    public void setRoles(List<TipoUsuario> roles) {
        this.tipos = roles;
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
}
