/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;
import co.edu.uniandes.csw.requirement.ejb.CaminoLogic;
import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CaminoPersistence;
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
import static org.junit.Assert.fail;
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
public class CaminoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CaminoLogic caminoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CaminoEntity> data = new ArrayList<CaminoEntity>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CaminoEntity.class.getPackage())
                .addPackage(CaminoLogic.class.getPackage())
                .addPackage(CaminoPersistence.class.getPackage())
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
        em.createQuery("delete from CaminoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CaminoEntity entity = factory.manufacturePojo(CaminoEntity.class);
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
    public void createCaminoTest(){
        try{
        CaminoEntity newEntity = factory.manufacturePojo(CaminoEntity.class);
         newEntity.setTipoPaso("BASICO");
        Assert.assertEquals("BASICO", newEntity.getTipoPaso());
        CaminoEntity result = caminoLogic.createCamino(newEntity);
        result.setTipoPaso("BASICO");
        Assert.assertNotNull(result);
        CaminoEntity entity = em.find(CaminoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        }
        catch (BusinessLogicException e)   
        {
            e.printStackTrace();
            fail("No debería generar excepción");
        }
        
    }

    /**
     * Prueba para crear un Editorial con el mismo nombre de un Editorial que ya
     * existe.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCaminoConMismoIdTest() throws BusinessLogicException {
        CaminoEntity newEntity = factory.manufacturePojo(CaminoEntity.class);
        newEntity.setId(data.get(0).getId());
        caminoLogic.createCamino(newEntity);
    }

    /**
     * Prueba para consultar la lista de Editorials.
     */
    @Test
    public void getCaminosTest() {
        List<CaminoEntity> list = caminoLogic.getCaminos();
        Assert.assertEquals(data.size(), list.size());
        for (CaminoEntity entity : list) {
            boolean found = false;
            for (CaminoEntity storedEntity : data) {
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
    public void getCaminoTest() {
        CaminoEntity entity = data.get(0);
        CaminoEntity resultEntity = caminoLogic.getCamino(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }

    /**
     * Prueba para actualizar un Editorial.
     */
    @Test
    public void updateCaminoTest() {
        CaminoEntity entity = data.get(0);
        CaminoEntity pojoEntity = factory.manufacturePojo(CaminoEntity.class);
        pojoEntity.setId(entity.getId());
        caminoLogic.updateCamino(pojoEntity.getId(), pojoEntity);
        CaminoEntity resp = em.find(CaminoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    /**
     * Prueba para eliminar un Editorial.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteCaminoTest() throws BusinessLogicException {
        CaminoEntity entity = data.get(1);
        caminoLogic.deleteCamino(entity.getId());
        CaminoEntity deleted = em.find(CaminoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
