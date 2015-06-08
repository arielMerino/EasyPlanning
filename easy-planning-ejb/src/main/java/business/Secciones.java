/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Seccion;
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
public class Secciones implements SeccionesLocal {
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    /*@Override
    public List<Seccion> findBySemestreAnioCarreraPlan(int codCarrera, String plan, int anio, int semestre){
        Query query = em.createNamedQuery("Seccion.findByCarreraPlanAnioSemestre").setParameter("codigo", codCarrera);
        query.setParameter("plan", plan);
        query.setParameter("anio", anio);
        query.setParameter("semestre", semestre);
        try{
            return (List<Seccion>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }*/
    
    @Override
    public List<Seccion> findByAsignaturaAnioYSemestre(long asg, int anio, int semestre){
        Query query = em.createNamedQuery("Seccion.findByAsignaturaAnioYSemestre").setParameter("idAsg", asg);
        query.setParameter("anio", anio);
        query.setParameter("semestre", semestre);
        try{
            return (List<Seccion>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
}
