/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import entities.Profesor;
import entities.Asignatura;
import entities.Checklist;

/**
 *
 * @author yerko
 */
@Named(value = "checklistController")
@SessionScoped
public class ChecklistController implements Serializable {

    /**
     * Creates a new instance of ChecklistController
     */
    public ChecklistController() {
    }
    
}
