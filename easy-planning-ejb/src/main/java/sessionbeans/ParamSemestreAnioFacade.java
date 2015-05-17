/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ParamSemestreAno;
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
public class ParamSemestreAnioFacade extends AbstractFacade<ParamSemestreAno> implements ParamSemestreAnioFacadeLocal {
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamSemestreAnioFacade() {
        super(ParamSemestreAno.class);
    }

    @Override
    public ParamSemestreAno findById(Long id) {
        Query query = em.createNamedQuery("ParamSemestreAno.findById").setParameter("id", id);
        try{
            return (ParamSemestreAno) query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }
    
}
