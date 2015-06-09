/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.AsignaturasLocal;
import business.CarrerasLocal;
import business.CoordinacionesLocal;
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
import sessionbeans.CarreraFacadeLocal;
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
    @EJB
    private AsignaturasLocal asignaturaBusiness;
    @EJB
    private CoordinacionesLocal coordinacionBusiness;
    @EJB
    private CarreraFacadeLocal carreraFacade;
    @EJB
    private CarrerasLocal carreraBusiness;
    
    int carrera;
    int planEstudio;
    String mensaje;
    int Semestre;
    int ano;

    public int getCarrera() {
        return carrera;
    }

    public CarrerasLocal getCarreraBusiness() {
        return carreraBusiness;
    }

    public void setCarreraBusiness(CarrerasLocal carreraBusiness) {
        this.carreraBusiness = carreraBusiness;
    }

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }

    public CarreraFacadeLocal getCarreraFacade() {
        return carreraFacade;
    }

    public void setCarreraFacade(CarreraFacadeLocal carreraFacade) {
        this.carreraFacade = carreraFacade;
    }
    
    public AsignaturasLocal getAsignaturaBusiness() {
        return asignaturaBusiness;
    }

    public void setAsignaturaBusiness(AsignaturasLocal asignaturaBusiness) {
        this.asignaturaBusiness = asignaturaBusiness;
    }
    
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    
    public int getPlanEstudio() {
        return planEstudio;
    }

    public CoordinacionesLocal getCoordinacionBusiness() {
        return coordinacionBusiness;
    }

    public void setCoordinacionBusiness(CoordinacionesLocal coordinacionBusiness) {
        this.coordinacionBusiness = coordinacionBusiness;
    }

    public void setPlanEstudio(int planEstudio) {
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
    
    public void generarSeccionesBasicas(int carrera, int plan, int semestre, int ano){
        List<Asignatura> asignaturas;
        ArrayList<Asignatura> asignaturasPlan = new ArrayList<>();
        try {
            asignaturas = asignaturaFacade.findAll();
            
            for (Asignatura a: asignaturas){
                System.out.println("ID: "+a.getId().toString());
                if (a.getVersionplan().getId().equals(Long.valueOf(plan+""))){
                    //asignaturas.remove(a);
                    asignaturasPlan.add(a);
                }
            }
            boolean seguir = true;
            System.out.println(asignaturas.size());
            System.out.println("plan: "+plan);
            for(Asignatura asg : asignaturasPlan){
                //para que no hayan 2 coordinaciones para el mismo ramo en el mismo semestre
                
                if(coordinacionBusiness.findByAsignaturaAndAnioAndSemestre(asg, ano, semestre) == null){
                    Coordinacion cord = new Coordinacion();
                    cord.setAsignatura(asg);
                    cord.setAnio(ano);
                    cord.setSemestre(semestre);
                    cord.setCantAlumnosEstimado(0);
                    cord.setCantAlumnosReal(0);
                    coordinacionFacade.create(cord);
                } 
                else{
                    seguir = false;
                    break;
                }
            }
            if (seguir == true){
                for(Asignatura asg : asignaturasPlan){
                    Coordinacion cord = coordinacionBusiness.findByAsignaturaAndAnioAndSemestre(asg, ano, semestre);
                    if (cord != null){
                        int t = asg.getTeoria();
                        int e = asg.getEjercicios();
                        int l = asg.getLaboratorio();

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
                mensaje = "Secciones creadas con éxito";
            }
            else{
                mensaje = "No se han creado secciones";
            }
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
