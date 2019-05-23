/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.ejb.ProyectoLogic;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
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
import static org.junit.Assert.fail;

/**
 *
 * @author jorgeandresesguerraalarcon
 */
@RunWith(Arquillian.class)
public class RequisitoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RequisitoLogic requisitoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RequisitoEntity> data = new ArrayList<RequisitoEntity>();

    private List<ProyectoEntity> dataProyecto = new ArrayList<ProyectoEntity>();

    private List<ObjetivoEntity> dataObjetivo = new ArrayList<ObjetivoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequisitoEntity.class.getPackage())
                .addPackage(RequisitoLogic.class.getPackage())
                .addPackage(RequisitoPersistence.class.getPackage())
                .addPackage(ProyectoEntity.class.getPackage())
                .addPackage(ProyectoLogic.class.getPackage())
                .addPackage(ProyectoPersistence.class.getPackage())
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(ObjetivoLogic.class.getPackage())
                .addPackage(ObjetivoPersistence.class.getPackage())
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
        em.createQuery("delete from AprobacionEntity").executeUpdate();
        em.createQuery("delete from CambioEntity").executeUpdate();
        em.createQuery("delete from RequisitoEntity").executeUpdate();
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
        em.createQuery("delete from ProyectoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ProyectoEntity entity = factory.manufacturePojo(ProyectoEntity.class);
            em.persist(entity);
            dataProyecto.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            ObjetivoEntity entity = factory.manufacturePojo(ObjetivoEntity.class);
            entity.setProyecto(dataProyecto.get(1));
            em.persist(entity);
            dataObjetivo.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            RequisitoEntity entity = factory.manufacturePojo(RequisitoEntity.class);
            entity.setObjetivo(dataObjetivo.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Requisito.
     *
     * @throws
     * co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test
    public void createRequisitoTest() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(2);
        newEntity.setImportancia(2);
        newEntity.setTipo("NOFUNCIONAL");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());

    }

    /**
     * Prueba para crear un Requisito que falla por estabilidad incorrecta
     *
     * @throws
     * co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail1() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(4);
        newEntity.setImportancia(2);
        newEntity.setTipo("NOFUNCIONAL");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());

    }

    /**
     * Prueba para crear un Requisito que falla por estabilidad incorrecta
     *
     * @throws
     * co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail2() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(0);
        newEntity.setImportancia(2);
        newEntity.setTipo("NOFUNCIONAL");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail3() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(4);
        newEntity.setTipo("NOFUNCIONAL");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());

    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail4() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(0);
        newEntity.setTipo("NOFUNCIONAL");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail5() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(2);
        newEntity.setTipo("NONAL");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail6() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(2);
        newEntity.setTipo("NONAL");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail7() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(2);
        newEntity.setTipo("FUNCIONAL");
        newEntity.setDescripcion("");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail8() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(2);
        newEntity.setTipo("FUNCIONAL");
        newEntity.setDescripcion(null);
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail9() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(2);
        newEntity.setTipo("FUNCIONAL");
        newEntity.setAutor(null);
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail10() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(dataObjetivo.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(2);
        newEntity.setTipo("FUNCIONAL");
        newEntity.setAutor("");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    @Test(expected = BusinessLogicException.class)
    public void createRequisitoTestFail11() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setObjetivo(null);
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(2);
        newEntity.setTipo("FUNCIONAL");
        newEntity.setAutor("");
        RequisitoEntity result = requisitoLogic.createRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }

    /**
     * Prueba para consultar la lista de Requisitos.
     *
     * @throws
     * co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test
    public void getRequisitosTest() throws BusinessLogicException {
        List<RequisitoEntity> list = requisitoLogic.getRequisitos(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (RequisitoEntity entity : list) {
            boolean found = false;
            for (RequisitoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Requisito.
     */
    @Test
    public void getRequisitoTest() {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity resultEntity = requisitoLogic.getRequisito(dataObjetivo.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(resultEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(resultEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(resultEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertEquals(resultEntity.getTipo(), entity.getTipo());
        Assert.assertEquals(resultEntity.getFuentes(), entity.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito.
     */
    @Test
    public void updateRequisitoTest() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(2);
        pojoEntity.setImportancia(2);
        pojoEntity.setTipo("NOFUNCIONAL");
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito y que falle.
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequisitoTest2() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(4);
        pojoEntity.setImportancia(2);
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito y que falle.
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequisitoTest3() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(0);
        pojoEntity.setImportancia(2);
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito y que falle.
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequisitoTest4() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(0);
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito y que falle.
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequisitoTest5() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(4);
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito y que falle.
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequisitoTest6() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(2);
        pojoEntity.setDescripcion(null);
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito y que falle.
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequisitoTest7() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(2);
        pojoEntity.setDescripcion("");
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito y que falle.
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequisitoTest8() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(2);
        pojoEntity.setAutor("");
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para actualizar un Requisito y que falle.
     */
    @Test(expected = BusinessLogicException.class)
    public void updateRequisitoTest9() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        RequisitoEntity pojoEntity = factory.manufacturePojo(RequisitoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(2);
        pojoEntity.setAutor(null);
        requisitoLogic.updateRequisito(dataObjetivo.get(1).getProyecto().getId(), dataObjetivo.get(1).getId(), entity.getId(), pojoEntity);

        RequisitoEntity resp = em.find(RequisitoEntity.class, entity.getId());

        Assert.assertNotNull(resp);
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }

    /**
     * Prueba para eliminar un Requisito.
     *
     * @throws
     * co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteRequisitoTest() throws BusinessLogicException {
        RequisitoEntity entity = data.get(0);
        requisitoLogic.deleteRequisito(dataObjetivo.get(1).getId(), entity.getId());
        RequisitoEntity deleted = em.find(RequisitoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminarle un objetivo a un proyecto del cual no pertenece.
     *
     * @throws
     * co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteRequisitoConObjetivoNoAsociadoTest() throws BusinessLogicException {
//        RequisitoEntity entity = data.get(0);
//        requisitoLogic.deleteRequisito(dataProyecto.get(0).getId(), entity.getId());
//    }
}
