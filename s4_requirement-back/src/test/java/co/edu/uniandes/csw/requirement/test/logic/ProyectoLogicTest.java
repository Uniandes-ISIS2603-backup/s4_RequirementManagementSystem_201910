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
 * @author jorgeandresesguerraalarcon
 */
@RunWith(Arquillian.class)
public class ProyectoLogicTest
{
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ProyectoLogic proyectoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ProyectoEntity> data = new ArrayList<>();
    
    private List<ObjetivoEntity> objetivoData = new ArrayList<>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProyectoEntity.class.getPackage())
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(ObjetivoLogic.class.getPackage())
                .addPackage(ProyectoLogic.class.getPackage())
                .addPackage(ObjetivoPersistence.class.getPackage())
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
        em.createQuery("delete from ProyectoEntity").executeUpdate();
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
        
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ProyectoEntity entity = factory.manufacturePojo(ProyectoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
        
    }

    /**
     * Prueba para crear un proyecto
     *
       * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     */
    @Test
    public void createProyectoTest() throws BusinessLogicException {
        ProyectoEntity newEntity = factory.manufacturePojo(ProyectoEntity.class);
        ProyectoEntity result = proyectoLogic.createProyecto(newEntity);
        Assert.assertNotNull(result);
        ProyectoEntity entity = em.find(ProyectoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getObjetivos(), entity.getObjetivos()); // aqui deben ser vacíos
    }

    /**
     * Prueba para crear un proyecto con una descripcion inválida
     *
       * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createProyectoConDescripcionInvalida() throws BusinessLogicException {
        ProyectoEntity newEntity = factory.manufacturePojo(ProyectoEntity.class);
        newEntity.setDescripcion("");
        proyectoLogic.createProyecto(newEntity);
    }
    
    /**
     * Prueba para crear un proyecto con un nombre inválida
     *
      * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createProyectoConNombreInvalido() throws BusinessLogicException {
        ProyectoEntity newEntity = factory.manufacturePojo(ProyectoEntity.class);
        newEntity.setDescripcion("");
        proyectoLogic.createProyecto(newEntity);
    }

    /**
     * Prueba para consultar la lista de proyectos
     */
    @Test
    public void getProyectosTest() {
        List<ProyectoEntity> list = proyectoLogic.getProyectos();
        Assert.assertEquals(data.size(), list.size());
        for (ProyectoEntity entity : list) {
            boolean found = false;
            for (ProyectoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un proyecto
     */
    @Test
    public void getProyectoTest() {
        ProyectoEntity entity = data.get(0);
        ProyectoEntity resultEntity = proyectoLogic.getProyecto(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getObjetivos(), resultEntity.getObjetivos()); // aqui deben ser vacíos
    }

    /**
     * Prueba para actualizar un Proyecto.
     *
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     */
    @Test
    public void updateProyectoTest() throws BusinessLogicException {
        ProyectoEntity entity = data.get(0);
        ProyectoEntity pojoEntity = factory.manufacturePojo(ProyectoEntity.class);
        pojoEntity.setId(entity.getId());
        proyectoLogic.updateProyecto(pojoEntity.getId(), pojoEntity);
        ProyectoEntity resp = em.find(ProyectoEntity.class, entity.getId());
        Assert.assertEquals(resp.getId(), pojoEntity.getId());
        Assert.assertEquals(resp.getId(), pojoEntity.getId());
        
        Assert.assertEquals(resp.getDescripcion(), pojoEntity.getDescripcion());
        Assert.assertEquals(resp.getNombre(), pojoEntity.getNombre());
        Assert.assertEquals(resp.getObjetivos(), pojoEntity.getObjetivos()); // aqui deben ser vacíos
    }

    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateProyectoConDescripcionInvalidaTest() throws BusinessLogicException {
        ProyectoEntity entity = data.get(0);
        ProyectoEntity  pojoEntity = factory.manufacturePojo(ProyectoEntity .class);
        pojoEntity.setDescripcion("");
        pojoEntity.setId(entity.getId());
        proyectoLogic.updateProyecto(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateProyectoConDescripcionInvalidaTest2() throws BusinessLogicException {
        ProyectoEntity entity = data.get(0);
        ProyectoEntity  pojoEntity = factory.manufacturePojo(ProyectoEntity .class);
        pojoEntity.setDescripcion(null);
        pojoEntity.setId(entity.getId());
        proyectoLogic.updateProyecto(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateProyectoConNombreInvalidoTest() throws BusinessLogicException {
        ProyectoEntity entity = data.get(0);
        ProyectoEntity  pojoEntity = factory.manufacturePojo(ProyectoEntity .class);
        pojoEntity.setNombre("");
        pojoEntity.setId(entity.getId());
        proyectoLogic.updateProyecto(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para actualizar un Book con ISBN inválido.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateProyectoConNombreInvalidoTest2() throws BusinessLogicException {
        ProyectoEntity entity = data.get(0);
        ProyectoEntity  pojoEntity = factory.manufacturePojo(ProyectoEntity .class);
        pojoEntity.setNombre(null);
        pojoEntity.setId(entity.getId());
        proyectoLogic.updateProyecto(pojoEntity.getId(), pojoEntity);
    }

    

    /**
     * Prueba para eliminar un Proyecto
     *
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     */
    @Test
    public void deleteBookTest() throws BusinessLogicException {
        ProyectoEntity entity = data.get(0);
        proyectoLogic.deleteProyecto(entity.getId());
        ProyectoEntity deleted = em.find(ProyectoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
