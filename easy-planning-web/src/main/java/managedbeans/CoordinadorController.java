/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.ParamSemestreAno;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import sessionbeans.ParamSemestreAnioFacadeLocal;

/**
 *
 * @author chien
 */
@Named(value = "coordinadorController")
@SessionScoped
public class CoordinadorController implements Serializable {
    @EJB
    private ParamSemestreAnioFacadeLocal anioSem ;
    private int anio;
    private int semestre;
    
    public CoordinadorController() {
    }

    public int getAnio() {
        return anio;
    }

    public int getSemestre() {
        return semestre;
    }           
    
    public int getAnioActual(){
        this.anio = anioSem.find(1L).getAnoActual();
        return this.anio;
    }
    
    public int getSemestreActual(){
        this.semestre = anioSem.find(1L).getSemestreActual();
        return this.semestre;
    }
    
    public void setSemestreActual(int semestre){
        ParamSemestreAno SemAn = anioSem.find(1L);
        this.semestre = semestre;
        SemAn.setSemestreActual(semestre);
        anioSem.edit(SemAn);
    }
    
    public void setAnioActual(int anio){
        ParamSemestreAno SemAn = anioSem.find(1L);
        this.anio = anio;
        SemAn.setAnoActual(anio);
        anioSem.edit(SemAn);
    }
}
