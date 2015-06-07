/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import business.AsignaturasLocal;
import entities.Asignatura;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import sessionbeans.AsignaturaFacadeLocal;

/**
 *
 * @author jano
 */
@Named(value = "profesorAsignaturaController")
@Dependent
public class ProfesorAsignaturaController {
    
    private List<Asignatura> profesorAsignatura;
    @EJB
    private AsignaturasLocal ejbFacade;

    //Constructor
    public ProfesorAsignaturaController() {
    }

    public AsignaturasLocal getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(AsignaturasLocal ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    public List getProfesorAsignatura() {
        return profesorAsignatura;
    }

    public void setProfesorAsignatura(List profesorAsignatura) {
        this.profesorAsignatura = profesorAsignatura;
    }
    
    public List<Asignatura> getAllProfesorAsignatura(){
        return getEjbFacade().getAllProfesorAsignatura();
    }
}
