/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author davidmanosalva
 */
@RunWith(Arquillian.class)
public class ObjetivoPersistenceTest {

    @Inject
    private ObjetivoPersistence objetivoPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<ObjetivoEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(ObjetivoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void clearData() {
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ObjetivoEntity entity = factory.manufacturePojo(ObjetivoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createObjetivoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ObjetivoEntity param = factory.manufacturePojo(ObjetivoEntity.class);
        ObjetivoEntity oe = objetivoPersistence.create(param);

        Assert.assertNotNull(oe);

        ObjetivoEntity entity = em.find(ObjetivoEntity.class, oe.getId());

        Assert.assertEquals(param.getAutor(), entity.getAutor());
    }

    @Test
    public void getObjetivosTest() {
        List<ObjetivoEntity> list = objetivoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        
        System.out.println("TAMAÑO" + data.size());
        for (ObjetivoEntity ent : list) {
            boolean found = false;
            for (ObjetivoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity newEntity = objetivoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(entity.getEstabilidad(), newEntity.getEstabilidad());
    }
    
    @Test
    public void updateObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);

        newEntity.setId(entity.getId());

        objetivoPersistence.update(newEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
    }
    
    @Test
    public void deleteObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        objetivoPersistence.delete(entity.getId());
        ObjetivoEntity deleted = em.find(ObjetivoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
