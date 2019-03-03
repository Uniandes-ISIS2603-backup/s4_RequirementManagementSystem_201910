/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.persistence.CaminoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Sofia Alvarez
 */
@RunWith(Arquillian.class)
public class CaminoPersistenceTest {

    @Inject
    private CaminoPersistence caminoPersistence;

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CaminoEntity> data = new ArrayList<CaminoEntity>();

    @Deployment
    public static JavaArchive deployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CaminoEntity.class.getPackage())
                .addPackage(CaminoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void setUp(){
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
        em.createQuery("delete from CaminoEntity").executeUpdate();
    }
    
    public void insertData(){
       PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CaminoEntity entity = factory.manufacturePojo(CaminoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
   }

    @Test
    public void createCaminoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CaminoEntity newCaminoEntity = factory.manufacturePojo(CaminoEntity.class);
        CaminoEntity ce = caminoPersistence.create(newCaminoEntity);

        Assert.assertNotNull(ce);

        CaminoEntity entity = em.find(CaminoEntity.class, ce.getId());
        Assert.assertEquals(newCaminoEntity.getPasos(), entity.getPasos());
    }
    
    /*
   *Obtiene la lista de entidades de caminos de la base de datos
   */
    @Test
    public void getCaminosTest() {
        List<CaminoEntity> list = caminoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for(CaminoEntity ent : list) {
            boolean found = false;
            for (CaminoEntity entity : data) {
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
    public void getCaminoTest() {
        CaminoEntity entity = data.get(0);
        CaminoEntity newEntity = caminoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
    @Test
    public void updateCaminoTest() {
        CaminoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CaminoEntity newEntity = factory.manufacturePojo(CaminoEntity.class);

        newEntity.setId(entity.getId());

        caminoPersistence.update(newEntity);

        CaminoEntity resp = em.find(CaminoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
    @Test
    public void deleteCaminoTest() {
        CaminoEntity entity = data.get(0);
        caminoPersistence.delete(entity.getId());
        CaminoEntity deleted = em.find(CaminoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
