/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import co.edu.uniandes.csw.requirement.persistence.CondicionPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Sofia Sarmiento
 */
@RunWith(Arquillian.class)
public class CondicionPersistenceTest {

    @Inject
    private CondicionPersistence ep;

    @PersistenceContext
    private EntityManager em;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CondicionEntity.class.getPackage())
                .addPackage(CondicionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createCondicionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CondicionEntity newEntity = factory.manufacturePojo(CondicionEntity.class);

        CondicionEntity ce = ep.create(newEntity);

        Assert.assertNotNull(ce);

        CondicionEntity entity = em.find(CondicionEntity.class, ce.getId());

        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

}
