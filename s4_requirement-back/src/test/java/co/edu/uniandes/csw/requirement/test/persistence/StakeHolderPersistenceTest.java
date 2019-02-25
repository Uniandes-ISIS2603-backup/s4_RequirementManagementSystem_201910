/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
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
public class StakeHolderPersistenceTest {

   /**
    * Se crea atributo tipo stakeholder
    */ 
    @Inject
    private StakeHolderPersistence ep;

    /**
     * Se crea un entity manager
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * 
     * @return el deployment creado
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(StakeHolderEntity.class.getPackage())
                .addPackage(StakeHolderPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Test de crear stakeholder
     */
    @Test
    public void createStakeHolderTest() {

        PodamFactory factory = new PodamFactoryImpl();
        StakeHolderEntity newEntity = factory.manufacturePojo(StakeHolderEntity.class);

        StakeHolderEntity ee = ep.create(newEntity);

        Assert.assertNotNull(ee);

        StakeHolderEntity entity = em.find(StakeHolderEntity.class, ee.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
}
