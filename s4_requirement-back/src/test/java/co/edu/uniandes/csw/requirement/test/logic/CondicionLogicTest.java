/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.CondicionLogic;
import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CondicionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author Sofia Sarmiento
 */
@RunWith(Arquillian.class)

public class CondicionLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
 
    
    @Inject
    private CondicionLogic condicionLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CondicionEntity> data = new ArrayList<CondicionEntity>();

    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CondicionEntity.class.getPackage())
                .addPackage(CondicionLogic.class.getPackage())
                .addPackage(CondicionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
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
        for (int i = 0; i < 3; i++) {

            CondicionEntity entity = factory.manufacturePojo(CondicionEntity.class);
            em.persist(entity);

            data.add(entity);
        }
   }

    @Test
    public void createCondicionTest() throws BusinessLogicException {
        CondicionEntity newEntity = factory.manufacturePojo(CondicionEntity.class);
        CondicionEntity result = condicionLogic.createCondicion(newEntity);
        Assert.assertNotNull(result);
        CondicionEntity entity = em.find(CondicionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());

    }
    
    /*@Test(expected = BusinessLogicException.class)
    public void createCondicionTestConIdInvalido() throws BusinessLogicException {
        CondicionEntity newEntity = factory.manufacturePojo(CondicionEntity.class);
        newEntity.setId(data.get(0).getId());
        casoDeUsoLogic.createCondicion(newEntity);
    }*/
    
    @Test(expected = BusinessLogicException.class)
    public void createCondicionTestConDescripcionInvalida() throws BusinessLogicException {
        CondicionEntity newEntity = factory.manufacturePojo(CondicionEntity.class);
        newEntity.setDescripcion(data.get(0).getDescripcion());
        condicionLogic.createCondicion(newEntity);
    }
    
     public void getCondicionesTest() {
        List<CondicionEntity> list = condicionLogic.getCondiciones();
        Assert.assertEquals(data.size(), list.size());
        for (CondicionEntity entity : list) {
            boolean found = false;
            for (CondicionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     
    @Test
    public void getCondicionTest() {
        CondicionEntity entity = data.get(0);
        CondicionEntity resultEntity = condicionLogic.getCondicion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        
    }
    
    @Test
    public void getCasoDeUsoPorDescripcionTest() {
        CondicionEntity entity = data.get(0);
        CondicionEntity resultEntity = condicionLogic.getCondicionDescripcion(entity.getDescripcion());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        
    }
    
     @Test
    public void updateCasoDeUsoTest() {
        CondicionEntity entity = data.get(0);
        CondicionEntity pojoEntity = factory.manufacturePojo(CondicionEntity.class);
        pojoEntity.setId(entity.getId());
        condicionLogic.updateCondicion(pojoEntity.getId(), pojoEntity);
        CondicionEntity resp = em.find(CondicionEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    
    @Test
    public void deleteCaminoTest() throws BusinessLogicException {
        CondicionEntity entity = data.get(1);
        condicionLogic.deleteCondicion(entity.getId());
        CondicionEntity deleted = em.find(CondicionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
