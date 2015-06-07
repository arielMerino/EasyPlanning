/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Checklist;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ariel-linux
 */
@Local
public interface ChecklistsLocal {
    public List<String> findProfesorByAsgAnioSemestre(long asg, int anio, int semestre);
    public List<Asignatura> findAsignaturasByEncuestaId(long id_encuesta);
    public void deleteChecklist(long encuesta_id, long asignatura_id);
    public List<Checklist> findChecklistByIdEncuesta(long idEncuesta);

}
