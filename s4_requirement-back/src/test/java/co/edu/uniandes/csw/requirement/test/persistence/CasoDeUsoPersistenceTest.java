/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import javax.inject.Inject;
import org.junit.Assert;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sofia Sarmiento
 */
@RunWith(Arquillian.class)

public class CasoDeUsoPersistenceTest {

    @Inject
    private CasoDeUsoPersistence ep;

    @PersistenceContext
    private EntityManager em;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CasoDeUsoEntity.class.getPackage())
                .addPackage(CasoDeUsoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createCasoDeUsoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CasoDeUsoEntity newEntity = factory.manufacturePojo(CasoDeUsoEntity.class);

        CasoDeUsoEntity ce = ep.create(newEntity);

        Assert.assertNotNull(ce);

        CasoDeUsoEntity entity = em.find(CasoDeUsoEntity.class, ce.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
}
