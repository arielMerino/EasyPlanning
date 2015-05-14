/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Coordinacion;
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
public class Coordinaciones implements CoordinacionesLocal {
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public Coordinacion findByAsignaturaAndAñoAndSemestre(Asignatura asignatura, int año, int semestre) {
        Query query = em.createNamedQuery("Coordinacion.findByAsignaturaAndAñoAndSemestre").setParameter("asg", asignatura);
        query.setParameter("año", año);
        query.setParameter("sem",semestre);
        try{
            return (entities.Coordinacion) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
    @Override
    public List<Coordinacion> findByCarreraAndPlanAndAñoAndSemestre(int carrera, String plan, int año, int semestre){
        Query query = em.createNamedQuery("Coordinacion.findByCarreraAndPlanAndAñoAndSemestre").setParameter("carrera", carrera);
        query.setParameter("plan", plan);
        query.setParameter("año", año);
        query.setParameter("semestre", semestre);
        try{
            return (List<Coordinacion>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
