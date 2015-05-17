/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author chien
 */
@Named(value = "coordinadorController")
@SessionScoped
public class CoordinadorController implements Serializable {
    
    int anio = 2015;
    int semestre = 2;
    
    public CoordinadorController() {
    }

    public int getAnio() {
        return anio;
    }

    public int getSemestre() {
        return semestre;
    }           
}
