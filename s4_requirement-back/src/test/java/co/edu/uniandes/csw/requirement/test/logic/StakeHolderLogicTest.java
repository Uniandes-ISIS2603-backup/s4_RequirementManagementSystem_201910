/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.StakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import java.util.ArrayList;
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
public class StakeHolderLogicTest {
    
    /**
     * Atributos 
     */
    @Inject
    private StakeHolderLogic organizacionLogic; 
    
    PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;
    
    private ArrayList<StakeHolderEntity> data; 
    
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
     * @return java archive creado
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(StakeHolderEntity.class.getPackage())
                .addPackage(StakeHolderLogic.class.getPackage())
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
    
    @Test
    public void createStakeHolderTest() throws BusinessLogicException {
        StakeHolderEntity newEntity = factory.manufacturePojo(StakeHolderEntity.class);
        StakeHolderEntity result = organizacionLogic.createStakeHolder(newEntity);
        Assert.assertNotNull(result);
        StakeHolderEntity entity = em.find(StakeHolderEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
   
}