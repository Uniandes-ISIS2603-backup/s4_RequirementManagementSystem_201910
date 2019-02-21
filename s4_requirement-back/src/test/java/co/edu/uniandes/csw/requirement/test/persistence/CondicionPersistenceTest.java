/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import co.edu.uniandes.csw.requirement.persistence.CondicionPersistence;
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
 * @author Sofia Sarmiento
 */
@RunWith(Arquillian.class)
public class CondicionPersistenceTest {

    @Inject
    private CondicionPersistence ep;

    @PersistenceContext
    private EntityManager em;
    
     @Inject
    UserTransaction utx;
    
    private List<CondicionEntity> data = new ArrayList<CondicionEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CondicionEntity.class.getPackage())
                .addPackage(CondicionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void setUp() {
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
        em.createQuery("delete from CondicionEntity").executeUpdate();
    }
    
    public void insertData(){
       PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CondicionEntity entity = factory.manufacturePojo(CondicionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
   }

    @Test
    public void createCondicionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CondicionEntity newEntity = factory.manufacturePojo(CondicionEntity.class);

        CondicionEntity ce = ep.create(newEntity);

        Assert.assertNotNull(ce);

        CondicionEntity entity = em.find(CondicionEntity.class, ce.getId());
        
        Assert.assertNotNull(entity);

        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    /*
   *Obtiene la lista de entidades de condiciones de la base de datos
   */
    @Test
    public void getCasosDeUsoTest() {
        List<CondicionEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for(CondicionEntity ent : list) {
            boolean found = false;
            for (CondicionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /*
   *Obtiene la entidad de caso de uso con el id de la base de datos
   */
    @Test
    public void getCasoDeUsoTest() {
        CondicionEntity entity = data.get(0);
        CondicionEntity newEntity = ep.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
    @Test
    public void updateCasoDeUsoTest() {
        CondicionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CondicionEntity newEntity = factory.manufacturePojo(CondicionEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        CondicionEntity resp = em.find(CondicionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
    @Test
    public void deleteCasoDeUsoTest() {
        CondicionEntity entity = data.get(0);
        ep.delete(entity.getId());
        CondicionEntity deleted = em.find(CondicionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
