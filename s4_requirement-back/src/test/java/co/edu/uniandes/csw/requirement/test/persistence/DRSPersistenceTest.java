/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.persistence.DRSPersistence;
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
 * @author Sofia Alvarez
 */
@RunWith(Arquillian.class)

public class DRSPersistenceTest {

    @Inject
    private DRSPersistence drsep;

    @PersistenceContext
    private EntityManager em;

    @Deployment
    public static JavaArchive deployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DRSEntity.class.getPackage())
                .addPackage(DRSPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createDRSTest() {

        PodamFactory factory = new PodamFactoryImpl();
        DRSEntity newDrse = factory.manufacturePojo(DRSEntity.class);
        DRSEntity drse = drsep.create(newDrse);

        Assert.assertNotNull(drse);
        DRSEntity entity = em.find(DRSEntity.class, drse.getId());
        Assert.assertEquals(newDrse.getVersion(), entity.getVersion());

    }

}
