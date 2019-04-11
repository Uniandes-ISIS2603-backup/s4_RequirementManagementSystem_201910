/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.CambioLogic;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
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
 *Clase que prueba la logica de un cambio
 * @author Sofia Alvarez
 */
@RunWith(Arquillian.class)
public class CambioLogicTest {
    /**
     * Factory de Podam
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de la logica de cambio
     */
    @Inject
    private CambioLogic cambioLogic;

    /**
     * Contexto de persistencia de la entity manager.
     */
    @PersistenceContext
    private EntityManager em;

    /**
    *Inyecciín de una transacción de usuario
    */
    @Inject
    private UserTransaction utx;

    /**
     * Lista con todos los cambios del sistema
     */
    private List<CambioEntity> data = new ArrayList<>();
    
    /**
    * Un stakeholder
    */
    StakeHolderEntity sh;
    
   /**
    * Un requisito
    */
    RequisitoEntity requisito;
    
    /**
     * Un
     */
    ObjetivoEntity objetivo;
    
        /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CambioEntity.class.getPackage())
                .addPackage(CambioLogic.class.getPackage())
                .addPackage(CambioPersistence.class.getPackage())
                .addPackage(StakeHolderEntity.class.getPackage())
                .addPackage(RequisitoEntity.class.getPackage())
                .addPackage(ObjetivoEntity.class.getPackage())
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
        em.createQuery("delete from CambioEntity").executeUpdate();
        em.createQuery("delete from StakeHolderEntity").executeUpdate();
        em.createQuery("delete from RequisitoEntity").executeUpdate();
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CambioEntity entity = factory.manufacturePojo(CambioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        sh = factory.manufacturePojo(StakeHolderEntity.class);
        requisito = factory.manufacturePojo(RequisitoEntity.class);
        objetivo = factory.manufacturePojo(ObjetivoEntity.class);
        em.persist(sh);
        em.persist(requisito);
        em.persist(objetivo);
    }
    
     /**
     * Prueba para crear un Cambio.
     */
    @Test
    public void createCambioTest() throws BusinessLogicException{
        CambioEntity newEntity = factory.manufacturePojo(CambioEntity.class);
        newEntity.setTipo("Modificación");
        cambioLogic.createCambio(newEntity);
        CambioEntity entity = em.find(CambioEntity.class, newEntity.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
    }
    
    /**
     * Prueba para crear un cambio que debe fallar.
     * @throws BusinessLogicException pues el tipo no concuerda con los definidos.
     */
    @Test(expected = BusinessLogicException.class)
    public void createCambioTestException() throws BusinessLogicException{
        CambioEntity newEntity = factory.manufacturePojo(CambioEntity.class);
        newEntity.setTipo("Hola");
        cambioLogic.createCambio(newEntity);
    }
    
    /**
     * Metodo que verifica que no haya aprobaciones para objetivos y requisitos.
     * @throws BusinessLogicException pues no puede haber una aprobacion para un objetivo y un requisito al tiempo.
     */
    @Test(expected = BusinessLogicException.class)
    public void createCambioConRequisitoYObjetivo() throws BusinessLogicException{
        CambioEntity newEntity = factory.manufacturePojo(CambioEntity.class);
        ObjetivoEntity objetivo = factory.manufacturePojo(ObjetivoEntity.class);
        RequisitoEntity requisito = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setRequisito(requisito);
        newEntity.setObjetivo(objetivo);
        cambioLogic.createCambio(newEntity);
    }
    
     /**
     * Prueba para consultar la lista de Cambios.
     */
    @Test
    public void getAllCambiosTest(){
        List<CambioEntity> list = cambioLogic.findAllCambios();
        Assert.assertEquals(data.size(), list.size());
        for (CambioEntity entity : list) {
            boolean found = false;
            for (CambioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     /**
     * Prueba para consultar un Cambio.
     */
    @Test
    public void getCambioTest() {
        CambioEntity entity = data.get(0);
        CambioEntity resultEntity = cambioLogic.findCambioById(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
    }

    /**
     * Prueba para actualizar un Cambio.
     */
    @Test
    public void updateCambioTest() throws BusinessLogicException {
        CambioEntity entity = data.get(0);
        CambioEntity pojoEntity = factory.manufacturePojo(CambioEntity.class);
        pojoEntity.setId(entity.getId());
        ObjetivoEntity objetivo = factory.manufacturePojo(ObjetivoEntity.class);
        pojoEntity.setObjetivo(objetivo);
        pojoEntity.setTipo("Modificación");
        cambioLogic.updateCambio(pojoEntity);
        CambioEntity resp = em.find(CambioEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
    }

    /**
     * Prueba para eliminar un Cambio.
     */
    @Test
    public void deleteCambioTest() {
        CambioEntity entity = data.get(1);
        cambioLogic.deleteCambio(entity.getId());
        CambioEntity deleted = em.find(CambioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para cambir el dueño de un cambio.
     */
    @Test
    public void changeStakeHolderTest(){
        CambioEntity entity = data.get(1);
        entity = cambioLogic.changeStakeHolder(entity.getId(), sh.getId());
        StakeHolderEntity entitySH = entity.getStakeholder();
        Assert.assertEquals(sh.getId(), entitySH.getId());
    }
    
     /**
     * Prueba para cambir el objetivo de una aprobación
     */
    @Test
    public void changeObjetivoTest(){
        CambioEntity entity = data.get(1);
        entity = cambioLogic.changeObjetivo(entity.getId(), objetivo.getId());
        ObjetivoEntity entityObjetivo = entity.getObjetivo();
        Assert.assertEquals(objetivo.getId(), entityObjetivo.getId());
    }
    
     /**
     * Prueba para cambir el requisito de una aprobación
     */
    @Test
    public void changeRequisitoTest(){
        CambioEntity entity = data.get(1);
        entity = cambioLogic.changeRequisito(entity.getId(), requisito.getId());
        RequisitoEntity entityRequisito = entity.getRequisito();
        Assert.assertEquals(requisito.getId(), entityRequisito.getId());
    }
}
