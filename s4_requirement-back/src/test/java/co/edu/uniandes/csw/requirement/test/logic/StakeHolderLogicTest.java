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
    private StakeHolderLogic stakeholderLogic; 
    
    PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;
    
    private ArrayList<StakeHolderEntity> data; 
    
    @Inject
    private UserTransaction utx;
    
    /**
     * crea datos para ser probados
     */
    private void insertData(){
        for (int i = 0; i < 3; i++) {
            StakeHolderEntity entity = factory.manufacturePojo(StakeHolderEntity.class);
            em.persist(entity);
            data.add(entity);
        }  
    }
    
    /**
     * 
     * @return deployment creado
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

    /**
     * Configuracion de la prueba
     */
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
    
    /**
     * borra datos creados previamente
     */
    private void clearData(){
        em.createQuery("delete from OrganizacionEntity").executeUpdate();
    }
    
    /**
     * prueba creacion de stakeholder
     * @throws BusinessLogicException 
     */
    @Test
    public void createStakeHolderTest() throws BusinessLogicException {
        StakeHolderEntity newEntity = factory.manufacturePojo(StakeHolderEntity.class);
        StakeHolderEntity result = stakeholderLogic.createStakeHolder(newEntity);
        Assert.assertNotNull(result);
        StakeHolderEntity entity = em.find(StakeHolderEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
    }
    
    /**
     * Prueba para consultar un stakeholder.
     */
    @Test
    public void getStakeHolderTest() {
        StakeHolderEntity entity = data.get(0);
        StakeHolderEntity resultEntity = stakeholderLogic.getStakeHolder(entity.getId());
        org.junit.Assert.assertNotNull(resultEntity);
        org.junit.Assert.assertEquals(entity.getId(), resultEntity.getId());
        org.junit.Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        org.junit.Assert.assertEquals(entity.getTipo(), resultEntity.getTipo());
    }

    /**
     * Prueba para actualizar un stakeholder.
     */
    @Test
    public void updateStakeHolderTest() {
        StakeHolderEntity entity = data.get(0);
        StakeHolderEntity pojoEntity = factory.manufacturePojo(StakeHolderEntity.class);

        pojoEntity.setId(entity.getId());

        stakeholderLogic.updateStakeHolder(pojoEntity.getId(), pojoEntity);

        StakeHolderEntity resp = em.find(StakeHolderEntity.class, entity.getId());

        org.junit.Assert.assertEquals(pojoEntity.getId(), resp.getId());
        org.junit.Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        org.junit.Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
    }

/**
 * Prueba para eliminar un stakeholder.
 * @throws BusinessLogicException 
 */
    @Test
    public void deleteStakeHolderTest() throws BusinessLogicException {
        StakeHolderEntity entity = data.get(0);
        stakeholderLogic.deleteStakeHolder(entity.getId());
        StakeHolderEntity deleted = em.find(StakeHolderEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }  
   
}