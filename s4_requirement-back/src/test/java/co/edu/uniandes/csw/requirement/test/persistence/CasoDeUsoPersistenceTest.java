/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
import java.util.ArrayList;
import java.util.List;
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
import javax.transaction.UserTransaction;
import org.junit.Before;

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
    
    @Inject
    UserTransaction utx;
    
    private List<CasoDeUsoEntity> data = new ArrayList<CasoDeUsoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CasoDeUsoEntity.class.getPackage())
                .addPackage(CasoDeUsoPersistence.class.getPackage())
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
        em.createQuery("delete from CasoDeUsoEntity").executeUpdate();
    }
    
    public void insertData(){
       PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CasoDeUsoEntity entity = factory.manufacturePojo(CasoDeUsoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
   }

    @Test
    public void createCasoDeUsoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CasoDeUsoEntity newEntity = factory.manufacturePojo(CasoDeUsoEntity.class);

        CasoDeUsoEntity ce = ep.create(newEntity);

        Assert.assertNotNull(ce);

        CasoDeUsoEntity entity = em.find(CasoDeUsoEntity.class, ce.getId());
        
        Assert.assertNotNull(entity);

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
   
    
   /*
   *Obtiene la lista de entidades de caso de uso de la base de datos
   */
    @Test
    public void getCasosDeUsoTest() {
        List<CasoDeUsoEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for(CasoDeUsoEntity ent : list) {
            boolean found = false;
            for (CasoDeUsoEntity entity : data) {
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
        CasoDeUsoEntity entity = data.get(0);
        CasoDeUsoEntity newEntity = ep.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
    @Test
    public void updateCasoDeUsoTest() {
        CasoDeUsoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CasoDeUsoEntity newEntity = factory.manufacturePojo(CasoDeUsoEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        CasoDeUsoEntity resp = em.find(CasoDeUsoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
    @Test
    public void deleteCasoDeUsoTest() {
        CasoDeUsoEntity entity = data.get(0);
        ep.delete(entity.getId());
        CasoDeUsoEntity deleted = em.find(CasoDeUsoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
