/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Encuesta;
import entities.Profesor;
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
public class Profesores implements ProfesoresLocal{
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public Profesor findByRut(String rut) {
        Query query = em.createNamedQuery("Profesor.findByRut").setParameter("rut", rut);
        try{
            return (Profesor) query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Encuesta getEncuestaBySemestreAndAnio(Long id, int semestre, int anio) {
        Query query = em.createNamedQuery("Profesor.getEncuestaBySemestreAndAnio").setParameter("semestre", semestre);
        query.setParameter("anio", anio);
        query.setParameter("id", id);
        try{
            return (Encuesta) query.getSingleResult();            
        }
        catch(NoResultException e){
            //System.out.println(e.getLocalizedMessage()+" --> Query");
            return null;
        }
    }
    
    @Override
    public List<Profesor> findDisponiblesByBloque(String bloque){
        Query query = em.createNamedQuery("Profesor.findDisponiblesByBloque").setParameter("bloque", bloque);
        try{
            return (List<Profesor>) query.getResultList();
        }catch (NoResultException e){
            return new ArrayList<>();
        }
    }
    
}
