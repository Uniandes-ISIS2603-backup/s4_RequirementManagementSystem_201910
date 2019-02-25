/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Mateo Pedroza
 */
@RunWith(Arquillian.class)
public class OrganizacionPersistenceTest {

   /**
    * Se crea atributo tipo organizacion persistence
    */ 
    @Inject
    private OrganizacionPersistence ep;

    /**
    * Se crea el entity manager
    */ 
    @PersistenceContext
    private EntityManager em;

    /**
     * 
     * @return el deployent creado
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizacionEntity.class.getPackage())
                .addPackage(OrganizacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Test de creacion de organizacion
     */
    @Test
    public void createOrganizacionTest() {

        PodamFactory factory = new PodamFactoryImpl();
        OrganizacionEntity newEntity = factory.manufacturePojo(OrganizacionEntity.class);
        OrganizacionEntity ee = ep.create(newEntity);
        Assert.assertNotNull(ee);
        OrganizacionEntity entity = em.find(OrganizacionEntity.class, ee.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
}
