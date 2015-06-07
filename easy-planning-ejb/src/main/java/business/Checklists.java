/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Checklist;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ariel-linux
 */
@Stateless
public class Checklists implements ChecklistsLocal {
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    @Override
    public List<String> findProfesorByAsgAnioSemestre(long asg, int anio, int semestre){
        Query query = em.createNamedQuery("Checklist.findProfesoresByAsgAnioAndSemestre").setParameter("asg", asg);
        query.setParameter("anio", anio);
        query.setParameter("semestre", semestre);
        try{
            return (List<String>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Asignatura> findAsignaturasByEncuestaId(long id_encuesta){
        Query query = em.createNamedQuery("Checklist.findAsignaturaByEncuestaId").setParameter("idEncuesta", id_encuesta);
        try{
            return (List<Asignatura>) query.getResultList();
        }
        catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    @Override
    public void deleteChecklist(long encuesta_id, long asignatura_id){
        Query query = em.createNamedQuery("Checklist.deleteChecklist").setParameter("idEncuesta", encuesta_id);
        query.setParameter("idAsignatura", asignatura_id);
    }
    
    @Override
    public List<Checklist> findChecklistByIdEncuesta(long idEncuesta){
        Query query = em.createNamedQuery("Checklist.findChecklistByIdEncuesta");
        query.setParameter("idEncuesta", idEncuesta);        
        try{
            return (List<Checklist>) query.getResultList();
        }catch(NoResultException e){
            return null;
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
