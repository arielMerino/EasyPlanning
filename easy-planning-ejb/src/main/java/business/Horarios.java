/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Horario;
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
public class Horarios implements HorariosLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public Horario findBybloqueCarreraPlanNivelAnioYSemestre(String bloque, int codigo, String plan, int nivel, int anio, int semestre){
        Query query = em.createNamedQuery("Horario.findBybloqueCarreraPlanNivelAnioYSemestre").setParameter("codigo", codigo);
        query.setParameter("plan", plan);
        query.setParameter("nivel", nivel);
        query.setParameter("anio", anio);
        query.setParameter("semestre",semestre);
        query.setParameter("bloque", bloque);
        try{
            return (entities.Horario) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Horario findByBloqueAndProfesor(String bloque, Long idProfesor) {
        Query query = em.createNamedQuery("Horario.findByBloqueAndProfesor").setParameter("bloque", bloque);
        query.setParameter("idProfesor", idProfesor);
        try{
            return (entities.Horario) query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }
    
    @Override
    public Horario findDisponibleByBloqueAndProfesor(String bloque, long idProfesor){
        Query query = em.createNamedQuery("Horario.findDisponibleByBloqueAndProfesor").setParameter("bloque", bloque);
        query.setParameter("idProfesor", idProfesor);
        query.setParameter("seccion", null);
        try{
            return (entities.Horario) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
    @Override
    public List<Horario> findDisponiblesByProfesorId(long profesorId){
        Query query = em.createNamedQuery("Horario.findHorariosDisponiblesByProfesor").setParameter("idProfesor", profesorId);
        try{
            return (List<Horario>) query.getResultList();
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }
    
}
