/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Checklist;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface ChecklistsLocal {
    public List<Long> findProfesorByAsgAnioSemestre(long asg, int anio, int semestre);
    public List<Checklist> findChecklistByIdEncuesta(long idEncuesta);
}
