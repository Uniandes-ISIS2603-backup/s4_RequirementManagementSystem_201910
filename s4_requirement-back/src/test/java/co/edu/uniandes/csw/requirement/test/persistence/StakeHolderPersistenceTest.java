/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
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

    
    private ArrayList<StakeHolderEntity> data; 
    
    PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private UserTransaction utx;
    
    private void insertData(){
        for (int i = 0; i < 3; i++) {
            StakeHolderEntity entity = factory.manufacturePojo(StakeHolderEntity.class);
            em.persist(entity);
            data.add(entity);
        }  
    }
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
    
    @Before
    public void configTest(){
        data = new ArrayList<StakeHolderEntity>();
        
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            } catch(Exception e1){
                e1.printStackTrace();
            }
        }        
    }
    
    private void clearData(){
        em.createQuery("delete from OrganizacionEntity").executeUpdate();
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
    
     /**
     * Prueba para consultar la lista de premios.
     */
    @Test
    public void getStakeHolderTest() {
        List<StakeHolderEntity> list;
        list = ep.findAll();
        org.junit.Assert.assertEquals(data.size(), list.size());
        for (StakeHolderEntity ent : list) {
            boolean found = false;
            for (StakeHolderEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para eliminar un Organization.
     */
    @Test
    public void deleteStakeHolderTest() {
        StakeHolderEntity entity = data.get(0);
        ep.delete(entity.getId());
        StakeHolderEntity deleted = em.find(StakeHolderEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }
    
     /**
     * Prueba para actualizar un Organization.
     */
    @Test
    public void updateStakeHolderTest() {
        StakeHolderEntity entity = data.get(0);
        StakeHolderEntity newEntity = factory.manufacturePojo(StakeHolderEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        StakeHolderEntity resp = em.find(StakeHolderEntity.class, entity.getId());

        org.junit.Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        org.junit.Assert.assertEquals(newEntity.getTipo(), resp.getTipo());
    }
}
