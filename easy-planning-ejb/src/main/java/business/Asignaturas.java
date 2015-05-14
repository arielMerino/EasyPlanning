/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

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
        return (entities.Asignatura) query.getSingleResult();
    }

    @Override
    public List<Asignatura> getAllProfesorAsignatura() {
        //CriteriaBuilder cb = em.getCriteriaBuilder();
        //CriteriaQuery<Asignatura> query = (CriteriaQuery<Asignatura>) query.from(Asignatura.class);
        Query query = em.createNamedQuery("Asignatura.getAllProfesorAsignatura");
        return query.getResultList();
    }

    
}
