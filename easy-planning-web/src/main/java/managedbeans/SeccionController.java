/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Asignatura;
import entities.Coordinacion;
import entities.Seccion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.AsignaturaFacadeLocal;
import sessionbeans.CoordinacionFacadeLocal;
import sessionbeans.SeccionFacadeLocal;

/**
 *
 * @author ariel-linux
 */
@Named(value = "seccionController")
@SessionScoped
public class SeccionController implements Serializable {
    @EJB
    private AsignaturaFacadeLocal asignaturaFacade;
    @EJB
    private CoordinacionFacadeLocal coordinacionFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    
    String planEstudio;
    int Semestre;
    int ano;

    public String getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(String planEstudio) {
        this.planEstudio = planEstudio;
    }

    public int getSemestre() {
        return Semestre;
    }

    public void setSemestre(int Semestre) {
        this.Semestre = Semestre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
    
    
    
    public AsignaturaFacadeLocal getAsignaturaFacade() {
        return asignaturaFacade;
    }
    
    public CoordinacionFacadeLocal getCoordinacionFacade(){
        return coordinacionFacade;
    }
    
    public SeccionFacadeLocal getSeccionFacade(){
        return seccionFacade;
    }
    
    public void generarSeccionesBasicas(String plan, int semestre, int ano){
        List<Asignatura> asignaturas = asignaturaFacade.findAll();
        ArrayList<Asignatura> asignaturasPlan = new ArrayList<>();
        try {
            for(Asignatura asg : asignaturas){
                if(asg.getPlanEstudio().equals(plan)){
                    asignaturasPlan.add(asg);
                    Coordinacion cord = new Coordinacion();
                    cord.setAsignatura(asg);
                    cord.setAño(ano);
                    cord.setSemestre(semestre);
                    cord.setCantAlumnosEstimado(0);
                    cord.setCantAlumnosReal(0);
                    coordinacionFacade.create(cord);
                }
            }
            for(Asignatura asg : asignaturasPlan){
                int t = asg.getTeoria();
                int e = asg.getEjercicios();
                int l = asg.getLaboratorio();
                List<Coordinacion> coordinaciones = asg.getCoordinaciones();
                for (Coordinacion cord : coordinaciones) {
                    if(cord.getAño()==ano && cord.getSemestre()==semestre){
                        if(t>0){
                            Seccion teo = new Seccion();
                            teo.setCodigo("A1");
                            teo.setCoordinacion(cord);
                            getSeccionFacade().create(teo);
                        }
                        if(e>0){
                            Seccion ejs = new Seccion();
                            ejs.setCodigo("E1");
                            ejs.setCoordinacion(cord);
                            getSeccionFacade().create(ejs);
                        }
                        if(l>0){
                            Seccion lab = new Seccion();
                            lab.setCodigo("L1");
                            lab.setCoordinacion(cord);
                            getSeccionFacade().create(lab);
                        }
                    }
                }
            }
            System.out.println("creación de secciones completada");
        }catch (Exception e) {
            System.out.println("creación de secciones terminada con errores");
        }
    }
    /**
     * Creates a new instance of SeccionController
     */
    public SeccionController() {
    }
    
}
