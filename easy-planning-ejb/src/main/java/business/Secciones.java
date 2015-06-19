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
    
    @Override
    public List<String> findByIdVersion(long idVersion){
        Query query = em.createNamedQuery("Seccion.getByVerionPlan").setParameter("idVersion", idVersion);
        try{
            return (List<String>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
    
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
    
    @Override
    public List<Long> findEspejos(String codigo, String alias, int anio, int semestre){
        Query query = em.createNamedQuery("Seccion.getEspejosById").setParameter("codigo", codigo);
        query.setParameter("alias", alias);
        query.setParameter("anio", anio);
        query.setParameter("semestre", semestre);
        try{
            return (List<Long>) query.getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }
}
