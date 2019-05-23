/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.ProyectoLogic;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
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
 * @author jorgeandresesguerraalarcon
 */
@RunWith(Arquillian.class)
public class ObjetivoLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ObjetivoLogic objetivoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ObjetivoEntity> data = new ArrayList<ObjetivoEntity>();

    private List<ProyectoEntity> dataProyecto = new ArrayList<ProyectoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(ObjetivoLogic.class.getPackage())
                .addPackage(ObjetivoPersistence.class.getPackage())
                .addPackage(ProyectoEntity.class.getPackage())
                .addPackage(ProyectoLogic.class.getPackage())
                .addPackage(ProyectoPersistence.class.getPackage())
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
            data.add(entity);
        }
        System.out.println(dataProyecto.size());
    }

    /**
     * Prueba para crear un Objetivo.
     *
     * @throws co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test
    public void createObjetivoTest() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(2);
        newEntity.setImportancia(2);
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());

    }
    
    /**
     * Prueba para crear un Objetivo que falla por estabilidad incorrecta
     *
     * @throws co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createObjetivoTestFail1() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(4);
        newEntity.setImportancia(2);
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());

    }
    
    /**
     * Prueba para crear un Objetivo que falla por estabilidad incorrecta
     *
     * @throws co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createObjetivoTestFail2() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(0);
        newEntity.setImportancia(2);
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createObjetivoTestFail3() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(4);
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());

    }
    
    @Test(expected = BusinessLogicException.class)
    public void createObjetivoTestFail4() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(0);
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createObjetivoTestFail5() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(1);
        newEntity.setAutor(null);
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }
    
     @Test(expected = BusinessLogicException.class)
    public void createObjetivoTestFail6() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(1);
        newEntity.setAutor("");
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }
    
     @Test(expected = BusinessLogicException.class)
    public void createObjetivoTestFail7() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(1);
        newEntity.setDescripcion("");
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createObjetivoTestFail8() throws BusinessLogicException {
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);
        newEntity.setProyecto(dataProyecto.get(1));
        newEntity.setEstabilidad(1);
        newEntity.setImportancia(1);
        newEntity.setDescripcion(null);
        ObjetivoEntity result = objetivoLogic.createObjetivo(dataProyecto.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        ObjetivoEntity entity = em.find(ObjetivoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(newEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(newEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(newEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(newEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(newEntity.getFuentes(), entity.getFuentes());
    }
    
    
    
    
    

    /**
     * Prueba para consultar la lista de Objetivos.
     *
     * @throws co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test
    public void getObjetivosTest() throws BusinessLogicException {
        List<ObjetivoEntity> list = objetivoLogic.getObjetivos(dataProyecto.get(1).getId());
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

    /**
     * Prueba para consultar un Objetivo.
     */
    @Test
    public void getObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity resultEntity = objetivoLogic.getObjetivo(dataProyecto.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(resultEntity.getCambios(), entity.getCambios());
        Assert.assertEquals(resultEntity.getComentarios(), entity.getComentarios());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getImportancia(), entity.getImportancia());
        Assert.assertEquals(resultEntity.getEstabilidad(), entity.getEstabilidad());
        Assert.assertNotNull(resultEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(resultEntity.getProyecto(), entity.getProyecto());
        Assert.assertEquals(resultEntity.getFuentes(), entity.getFuentes());
    }

    /**
     * Prueba para actualizar un Objetivo.
     */
    @Test
    public void updateObjetivoTest() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(2);
        pojoEntity.setImportancia(2);
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    
    /**
     * Prueba para actualizar un Objetivo y que falle.
     */
     @Test(expected = BusinessLogicException.class)
    public void updateObjetivoTest2() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(4);
        pojoEntity.setImportancia(2);
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    
    /**
     * Prueba para actualizar un Objetivo y que falle.
     */
     @Test(expected = BusinessLogicException.class)
    public void updateObjetivoTest3() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(0);
        pojoEntity.setImportancia(2);
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    
    /**
     * Prueba para actualizar un Objetivo y que falle.
     */
     @Test(expected = BusinessLogicException.class)
    public void updateObjetivoTest4() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(0);
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    
    /**
     * Prueba para actualizar un Objetivo y que falle.
     */
     @Test(expected = BusinessLogicException.class)
    public void updateObjetivoTest5() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(4);
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    
    /**
     * Prueba para actualizar un Objetivo y que falle.
     */
     @Test(expected = BusinessLogicException.class)
    public void updateObjetivoTest6() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(2);
        pojoEntity.setDescripcion(null);
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    
    /**
     * Prueba para actualizar un Objetivo y que falle.
     */
     @Test(expected = BusinessLogicException.class)
    public void updateObjetivoTest7() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(2);
        pojoEntity.setDescripcion("");
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    
     /**
     * Prueba para actualizar un Objetivo y que falle.
     */
     @Test(expected = BusinessLogicException.class)
    public void updateObjetivoTest8() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(2);
        pojoEntity.setAutor("");
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    
    /**
     * Prueba para actualizar un Objetivo y que falle.
     */
     @Test(expected = BusinessLogicException.class)
    public void updateObjetivoTest9() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity pojoEntity = factory.manufacturePojo(ObjetivoEntity.class);

        pojoEntity.setId(entity.getId());

        pojoEntity.setEstabilidad(3);
        pojoEntity.setImportancia(2);
        pojoEntity.setAutor(null);
        objetivoLogic.updateObjetivo(dataProyecto.get(1).getId(), pojoEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(pojoEntity.getCambios(), resp.getCambios());
        Assert.assertEquals(pojoEntity.getComentarios(), resp.getComentarios());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getImportancia(), resp.getImportancia());
        Assert.assertEquals(pojoEntity.getEstabilidad(), resp.getEstabilidad());
        Assert.assertNotNull(pojoEntity.getProyecto());
        Assert.assertNotNull(entity.getProyecto());
        Assert.assertEquals(pojoEntity.getProyecto(), resp.getProyecto());
        Assert.assertEquals(pojoEntity.getFuentes(), resp.getFuentes());
    }
    

    /**
     * Prueba para eliminar un Objetivo.
     *
     * @throws co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteObjetivoTest() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        objetivoLogic.deleteObjetivo(dataProyecto.get(1).getId(), entity.getId());
        ObjetivoEntity deleted = em.find(ObjetivoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminarle un objetivo a un proyecto del cual no pertenece.
     *
     * @throws co.edu.uniandes.csw.proyectostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteObjetivoConProyectoNoAsociadoTest() throws BusinessLogicException {
        ObjetivoEntity entity = data.get(0);
        objetivoLogic.deleteObjetivo(dataProyecto.get(0).getId(), entity.getId());
    }
}



