/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

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
    public List<Long> findProfesorByAsgAñoSemestre(long asg, int año, int semestre){
        Query query = em.createNamedQuery("Checklist.findProfesoresByAsgAñoAndSemestre").setParameter("asg", asg);
        query.setParameter("año", año);
        query.setParameter("semestre", semestre);
        try{
            return (List<Long>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
