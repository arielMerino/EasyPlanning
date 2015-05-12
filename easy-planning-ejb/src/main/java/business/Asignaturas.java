/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jano
 */
@Stateless
public class Asignaturas implements AsignaturasLocal {
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public Asignatura findByCodigoAndPlan(String codigo, String plan) {
        Query query = em.createNamedQuery("Asignatura.findByCodigoAndPlan").setParameter("codigo", codigo);
        query.setParameter("plan", plan);
        
        try{
            return (entities.Asignatura) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
    @Override
    public Asignatura findByCarreraAndCodigoAndPlan(int carrera, String codigo, String plan) {
        Query query = em.createNamedQuery("Asignatura.findByCarreraAndCodigoAndPlan").setParameter("carrera", carrera);
        query.setParameter("codigo", codigo);
        query.setParameter("plan", plan);
        try{
            return (entities.Asignatura) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
    @Override
    public List<Asignatura> findByCarreraAndPlan(String carrera, String plan) {
        try{
        Query query = em.createNamedQuery("Asignatura.findByCarreraAndPlan").setParameter("carrera", carrera);
        query.setParameter("plan", plan);
        return (List<Asignatura>) query.getResultList();
        }catch(Exception e){
            List<Asignatura> error = new ArrayList<>();
            return error;
        }
    }

    @Override
    public List<Asignatura> findByNivelAndCarreraAndPlan(int nivel, String carrera, String plan){
        Query query = em.createNamedQuery("Asignatura.findByNivelAndCarreraAndPlan").setParameter("nivel", nivel);
        query.setParameter("carrera", carrera);
        query.setParameter("plan", plan);
        return (List<Asignatura>) query.getResultList();
    }
}
