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

/**
 *
 * @author jano
 */
@Entity
public class TipoUsuario implements Serializable {    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_tipo;
    private String nombre_tipo;
    private String pass;

    public Long getId() {
        return id_tipo;
    }

    public void setId(Long id) {
        this.id_tipo = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public String getTipo() {
        return nombre_tipo;
    }

    public void setTipo(String tipo) {
        this.nombre_tipo = tipo;
    }

}
