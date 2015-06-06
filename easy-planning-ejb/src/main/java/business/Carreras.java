/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Carrera;
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
public class Carreras implements CarrerasLocal {
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    public Carrera findByNombre(String nombre){
        Query query = em.createNamedQuery("Carrera.findByNombre").setParameter("nombre", nombre);
        try{
            return (entities.Carrera) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    /*
    @Override
    public Carrera findByCodigo(int codigo){
        Query query = em.createNamedQuery("Carrera.findByCodigo").setParameter("codigo", codigo);
        try{
            return (entities.Carrera) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }*/
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
