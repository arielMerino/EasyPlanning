/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Usuario;
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
public class Usuarios implements UsuariosLocal {
    @PersistenceContext(unitName = "cl.G2Pingeso_easy-planning-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    public Usuario findByUid(String uid) {
        Query query = em.createNamedQuery("Usuario.findByUid").setParameter("uid", uid);
        try{
            return (Usuario) query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

}