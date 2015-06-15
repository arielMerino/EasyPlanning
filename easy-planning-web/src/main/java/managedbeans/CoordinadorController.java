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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
    
    public String obtenerURL(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getRequestURI();
    }
    
    public boolean coordinadorOProfesor(){
        String[] url = obtenerURL().split("/");
        try{
            if (url[3].equals("coordinador_docente"))
                return true;
            return false;
        }catch(Exception e){
            return true;
        }
    }
}
