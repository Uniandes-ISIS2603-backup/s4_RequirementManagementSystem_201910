/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jorge Esguerra
 */
@Stateless
public class RequisitoPersistence
{
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em; // esta hace el acceso a la base de datos.
    public RequisitoEntity create (RequisitoEntity e)
    {
        em.persist(e);
        return e;
    }
}
