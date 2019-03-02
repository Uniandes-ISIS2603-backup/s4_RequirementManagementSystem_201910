/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.CasoDeUsoLogic;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
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
public class CasoDeUsoLogicTest {
    
   private PodamFactory factory = new PodamFactoryImpl();
 
    
    @Inject
    private CasoDeUsoLogic casoDeUsoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CasoDeUsoEntity> data = new ArrayList<CasoDeUsoEntity>();

    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CasoDeUsoEntity.class.getPackage())
                .addPackage(CasoDeUsoLogic.class.getPackage())
                .addPackage(CasoDeUsoPersistence.class.getPackage())
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
        em.createQuery("delete from CasoDeUsoEntity").executeUpdate();
    }
    
    public void insertData(){
        for (int i = 0; i < 3; i++) {

            CasoDeUsoEntity entity = factory.manufacturePojo(CasoDeUsoEntity.class);
            em.persist(entity);

            data.add(entity);
        }
   }

    @Test
    public void createCasoDeUsoTest() throws BusinessLogicException {
        CasoDeUsoEntity newEntity = factory.manufacturePojo(CasoDeUsoEntity.class);
        CasoDeUsoEntity result = casoDeUsoLogic.createCasoDeUso(newEntity);
        Assert.assertNotNull(result);
        CasoDeUsoEntity entity = em.find(CasoDeUsoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());

    }
    
    /*@Test(expected = BusinessLogicException.class)
    public void createCasoDeUsoTestConIdInvalido() throws BusinessLogicException {
        CasoDeUsoEntity newEntity = factory.manufacturePojo(CasoDeUsoEntity.class);
        newEntity.setId(data.get(0).getId());
        casoDeUsoLogic.createCasoDeUso(newEntity);
    }*/
    
    @Test(expected = BusinessLogicException.class)
    public void createCasoDeUsoTestConNombreInvalido() throws BusinessLogicException {
        CasoDeUsoEntity newEntity = factory.manufacturePojo(CasoDeUsoEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        casoDeUsoLogic.createCasoDeUso(newEntity);
    }
    
     public void getCasosDeUsoTest() {
        List<CasoDeUsoEntity> list = casoDeUsoLogic.getCasosDeUso();
        Assert.assertEquals(data.size(), list.size());
        for (CasoDeUsoEntity entity : list) {
            boolean found = false;
            for (CasoDeUsoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     
    @Test
    public void getCasoDeUsoTest() {
        CasoDeUsoEntity entity = data.get(0);
        CasoDeUsoEntity resultEntity = casoDeUsoLogic.getCasoDeUso(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        
    }
    
    @Test
    public void getCasoDeUsoPorNombreTest() {
        CasoDeUsoEntity entity = data.get(0);
        CasoDeUsoEntity resultEntity = casoDeUsoLogic.getCasoDeUsoPorNombre(entity.getNombre());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        
    }
    
     @Test
    public void updateCasoDeUsoTest() {
        CasoDeUsoEntity entity = data.get(0);
        CasoDeUsoEntity pojoEntity = factory.manufacturePojo(CasoDeUsoEntity.class);
        pojoEntity.setId(entity.getId());
        casoDeUsoLogic.updateCasoDeUso(pojoEntity.getId(), pojoEntity);
        CasoDeUsoEntity resp = em.find(CasoDeUsoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    
    @Test
    public void deleteCaminoTest() throws BusinessLogicException {
        CasoDeUsoEntity entity = data.get(1);
        casoDeUsoLogic.deleteCasoDeUso(entity.getId());
        CasoDeUsoEntity deleted = em.find(CasoDeUsoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

}
