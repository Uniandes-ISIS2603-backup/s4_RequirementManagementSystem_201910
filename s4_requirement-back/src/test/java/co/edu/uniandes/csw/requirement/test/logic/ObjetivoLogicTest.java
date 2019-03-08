/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
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
import static org.junit.Assert.fail;
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
public class ObjetivoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ObjetivoLogic objetivoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ObjetivoEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(ObjetivoLogic.class.getPackage())
                .addPackage(ObjetivoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Before
    public void configTest() {
        try {
            utx.begin();
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
        for (int i = 0; i < 3; i++) {
            ObjetivoEntity entity = factory.manufacturePojo(ObjetivoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createObjetivoTest() {
        try {
            ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
            newEntity.setEstabilidad(1);
            newEntity.setImportancia(3);
            ObjetivoEntity result = objetivoLogic.createObjetivo(newEntity);
            Assert.assertNotNull(result);
            ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
            Assert.assertEquals(newEntity.getId(), entity.getId());
            Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
            Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        } catch (BusinessLogicException e) {
            e.printStackTrace();
            fail("No deberia generar excepcion");
        }
    }

    @Test
    public void getObjetivosTest() {
        List<ObjetivoEntity> list = objetivoLogic.getObjetivos();
        Assert.assertEquals(data.size(), list.size());
        for (ObjetivoEntity entity : list) {
            boolean found = false;
            for (ObjetivoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity resultEntity = objetivoLogic.getObjetivo(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getImportancia(), resultEntity.getImportancia());
    }

    @Test
    public void updateObjetivoTest() {
        try {
            ObjetivoEntity entity = data.get(0);
            ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

            pojoEntity.setEstabilidad(2);
            pojoEntity.setImportancia(2);
            
            pojoEntity.setId(entity.getId());

            objetivoLogic.updateObjetivo(pojoEntity.getId(), pojoEntity);

            ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

            Assert.assertEquals(pojoEntity.getId(), resp.getId());
            Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
            Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        } catch (BusinessLogicException e) {
            e.printStackTrace();
            fail("No deberia generar excepcion");
        }
    }

    @Test
    public void deleteObjetivoTest() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        objetivoLogic.deleteObjetivo(entity.getId());
        ObjetivoEntity deleted = em.find(ObjetivoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test(expected = BusinessLogicException.class)
    public void createObjetivoConDescripcionNulaTest() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setDescripcion("");
        ObjetivoEntity result = objetivoLogic.createObjetivo(newEntity);
        Assert.assertNull(result);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createObjetivoConImportanciaInvalidaTest() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setImportancia(7);
        ObjetivoEntity result = objetivoLogic.createObjetivo(newEntity);
        Assert.assertNull(result);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createObjetivoConEstabilidadInvalidaTest() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setEstabilidad(7);
        ObjetivoEntity result = objetivoLogic.createObjetivo(newEntity);
        Assert.assertNull(result);
    }
}
