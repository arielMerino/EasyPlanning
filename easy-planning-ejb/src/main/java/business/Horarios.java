/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Horario;
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
    public Horario findBybloqueCarreraPlanNivelAñoYSemestre(String bloque, int codigo, String plan, int nivel, int año, int semestre){
        Query query = em.createNamedQuery("Horario.findBybloqueCarreraPlanNivelAñoYSemestre").setParameter("codigo", codigo);
        query.setParameter("plan", plan);
        query.setParameter("nivel", nivel);
        query.setParameter("año", año);
        query.setParameter("semestre",semestre);
        query.setParameter("bloque", bloque);
        try{
            return (entities.Horario) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
}