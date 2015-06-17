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
    public Asignatura findByCodigoAsgAndIdVersion(String codigo, long idVersion){
        Query query = em.createNamedQuery("Asignatura.findByCodigoAsgAndIdVersion").setParameter("codigo", codigo);
        query.setParameter("idVersion", idVersion);
        try{
            return (entities.Asignatura) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<Asignatura> getAllProfesorAsignatura() {
        //CriteriaBuilder cb = em.getCriteriaBuilder();
        //CriteriaQuery<Asignatura> query = (CriteriaQuery<Asignatura>) query.from(Asignatura.class);
        Query query = em.createNamedQuery("Asignatura.getAllProfesorAsignatura");
        return query.getResultList();
    }
    
    @Override //al final es findByCarreraAndVersion
    public List<Asignatura> findByCarreraAndPlan(long version) {
        try{
        Query query = em.createNamedQuery("Asignatura.findByVersionPlan").setParameter("plan", version);
        return (List<Asignatura>) query.getResultList();
        }catch(Exception e){
            List<Asignatura> error = new ArrayList<>();
            return error;
        }
    }
    
    @Override
    public List<Asignatura> findByNivelAndPlan(int nivel, long versionPlan){
        Query query = em.createNamedQuery("Asignatura.findByNivelAndPlan").setParameter("nivel", nivel);
        query.setParameter("idVersion", versionPlan);
        try{
            return (List<Asignatura>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Integer> findNivelesByPlan(long idVersionPlan){
        Query query = em.createNamedQuery("Asignatura.findNivelesByPlan").setParameter("versionPlan", idVersionPlan);
        try{
            List<Integer> result = (List<Integer>) query.getResultList();
            result.sort(null);
            return result;
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Asignatura> findByAsignaturaAsignada(String rutProfesor, int anio, int semestre) {
        Query query = em.createNamedQuery("Asignatura.findByAsignaturaAsignada").setParameter("rutProfesor", rutProfesor);
        query.setParameter("anio", anio);
        query.setParameter("semestre", semestre);
        
        try{
            return (List<Asignatura>) query.getResultList();
        }
        catch(Exception e){
            return new ArrayList<>();
        }
    }
}
