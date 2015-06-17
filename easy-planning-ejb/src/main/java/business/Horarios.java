/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Horario;
import entities.ParamSemestreAno;
import entities.Seccion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sessionbeans.ParamSemestreAnioFacadeLocal;

/**
 *
 * @author ariel-linux
 */
@Stateless
public class Horarios implements HorariosLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public Horario findByBloqueAndProfesor(String bloque, String rutProfesor) {
        Query query = em.createNamedQuery("Horario.findByBloqueAndProfesor").setParameter("bloque", bloque);
        query.setParameter("rutProfesor", rutProfesor);
        try{
            return (entities.Horario) query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }
    
    @Override
    public List<Horario> AsignadoByBloqueAndProfesor(String bloque, String rutProfesor) {
        Query query = em.createNamedQuery("Horario.findAsignadoByBloqueAndProfesor").setParameter("bloque", bloque);
        query.setParameter("rutProfesor", rutProfesor);
        try{
            return (List<Horario>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
    @Override
    public Horario findDisponibleByBloqueAndProfesor(String bloque, String rutProfesor){
        Query query = em.createNamedQuery("Horario.findDisponibleByBloqueAndProfesor").setParameter("bloque", bloque);
        query.setParameter("rutProfesor", rutProfesor);
        query.setParameter("seccion", null);
        try{
            return (entities.Horario) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
    @Override
    public List<Horario> findDisponiblesByProfesorId(String rutProfesor){
        Query query = em.createNamedQuery("Horario.findHorariosDisponiblesByProfesor").setParameter("rutProfesor", rutProfesor);
        try{
            return (List<Horario>) query.getResultList();
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<Horario> findByHorariosNoDisponibles() {
        Query query = em.createNamedQuery("Horario.findByHorariosNoDisponibles");
        try{
            return query.getResultList();
        }
        catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<Horario> findBySeleccionados(String rutProfesor) {
        Query query = em.createNamedQuery("Horario.findBySeleccionados").setParameter("rutProfesor", rutProfesor);
        try{
            return query.getResultList();
        }
        catch(Exception e){
            return null;
        }
    }
    
    @Override
    public List<Horario> findAsignadosByProfesorId(String rutProfesor){
        Query query = em.createNamedQuery("Horario.findHorariosAsignadosByProfesor").setParameter("rutProfesor", rutProfesor);
        try{
            return (List<Horario>) query.getResultList();
        }catch (NoResultException e){
            return null;
        }
    }
    @Override  
    public Horario findBybloqueCarreraPlanNivelAnioYSemestre(String bloque, long idPlan, int nivel, int anio, int semestre) {
        Query query = em.createNamedQuery("Horario.findByversionPlanAndSemestreAndAnioAndBloque").setParameter("idPlan", idPlan);
        query.setParameter("bloque", bloque);
        query.setParameter("nivel",nivel);
        query.setParameter("semestre",semestre);
        query.setParameter("anio",anio);
        try{
            return (entities.Horario) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
        
    }
    
    @Override
    public List<Horario> findAsignadosActualesByProfesorId(String rutProfesor, int anio, int semestre){
        Query query = em.createNamedQuery("Horario.findHorariosAsignadosByProfesorAndSemestre").setParameter("rutProfesor", rutProfesor);
        query.setParameter("anio", anio);
        query.setParameter("semestre", semestre);
        try{
            return (List<Horario>) query.getResultList();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Horario> findByVersionPlanAndSemestreAndAnioAndNivel(Long idPlan, int semestre, int anio, int nivel){
        Query query = em.createNamedQuery("Horario.findByVersionPlanAndSemestreAndAnio");
        query.setParameter("idPlan", idPlan);
        query.setParameter("semestre", semestre);
        query.setParameter("anio", anio);
        query.setParameter("nivel", nivel);
        try{
            return (List<Horario>) query.getResultList();
        }
        catch(NoResultException e){
            return null;
        }
    }
}
