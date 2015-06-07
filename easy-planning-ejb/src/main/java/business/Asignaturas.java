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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    /*
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
    */
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
    /*
    @Override
    public List<String> findPlanesByCodigoCarrera(int codigo){
        Query query = em.createNamedQuery("Asignatura.findPlanesByCarrera").setParameter("carrera", codigo);
        try{
            return (List<String>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Integer> findNivelesByCodigoCarreraAndPlan(int codigoCarrera, String plan){
        Query query = em.createNamedQuery("Asignatura.findNivelesByCodigoCarreraAndPlan").setParameter("codigoCarrera", codigoCarrera);
        query.setParameter("plan", plan);
        try{
            List<Integer> salida = (List<Integer>) query.getResultList();
            //salida.sort(null);
            return salida;
            
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    */
    @Override
    public Asignatura findByAsignaturaAsignada(String rutProfesor, int anio, int semestre) {
        Query query = em.createNamedQuery("Asignatura.findByAsignaturaAsignada").setParameter("rutProfesor", rutProfesor);
        query.setParameter("anio", anio);
        query.setParameter("semestre", semestre);
        
        try{
            return (Asignatura) query.getSingleResult();
        }
        catch(Exception e){
            return null;
        }
    }
}
