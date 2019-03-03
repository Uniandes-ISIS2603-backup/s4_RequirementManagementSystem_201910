/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;
import co.edu.uniandes.csw.requirement.ejb.DRSLogic;
import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.DRSPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
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
public class DRSLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DRSLogic drsLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<DRSEntity> data = new ArrayList<DRSEntity>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DRSEntity.class.getPackage())
                .addPackage(DRSLogic.class.getPackage())
                .addPackage(DRSPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from DRSEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DRSEntity entity = factory.manufacturePojo(DRSEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Editorial.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createDRSTest() throws BusinessLogicException {
        DRSEntity newEntity = factory.manufacturePojo(DRSEntity.class);
        DRSEntity result = drsLogic.createDRS(newEntity);
        Assert.assertNotNull(result);
        DRSEntity entity = em.find(DRSEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para crear un Editorial con el mismo nombre de un Editorial que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createDRSConMismaVersionTest() throws BusinessLogicException {
        DRSEntity newEntity = factory.manufacturePojo(DRSEntity.class);
        newEntity.setVersion(data.get(0).getVersion());
        drsLogic.createDRS(newEntity);
    }

    /**
     * Prueba para consultar la lista de Editorials.
     */
    @Test
    public void getDRSsTest() {
        List<DRSEntity> list = drsLogic.getDRSs();
        Assert.assertEquals(data.size(), list.size());
        for (DRSEntity entity : list) {
            boolean found = false;
            for (DRSEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Editorial.
     */
    @Test
    public void getDRSTest() {
        DRSEntity entity = data.get(0);
        DRSEntity resultEntity = drsLogic.getDRS(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }

    /**
     * Prueba para actualizar un Editorial.
     */
    @Test
    public void updateDRSTest() {
        DRSEntity entity = data.get(0);
        DRSEntity pojoEntity = factory.manufacturePojo(DRSEntity.class);
        pojoEntity.setId(entity.getId());
        drsLogic.updateDRS(pojoEntity.getId(), pojoEntity);
        DRSEntity resp = em.find(DRSEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    /**
     * Prueba para eliminar un Editorial.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteDRSTest() throws BusinessLogicException {
        DRSEntity entity = data.get(1);
        drsLogic.deleteDRS(entity.getId());
        DRSEntity deleted = em.find(DRSEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
