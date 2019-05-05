/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.AprobacionLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
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
 * Clase de la logica que corre el test de la aprobacion
 * @author Sofia Alvarez
 */
@RunWith(Arquillian.class)
public class AprobacionLogicTest {
    /**
     * Factory de Podam
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de la aprobacionLogic
     */
    @Inject
    private AprobacionLogic aprobacionLogic;

   /**
    * Entity manager
    */
    @PersistenceContext
    private EntityManager em;

    /**
     * Inyección de la transacción
     */
    @Inject
    private UserTransaction utx;

    /**
     * Lista con datos de aprobacion entity
     */
    private List<AprobacionEntity> data = new ArrayList<>();
    
    /**
     * Un stakeholder 
     */
    StakeHolderEntity sh;
    
    /**
     * Un requisito
     */
    RequisitoEntity requisito;
    
    /**
     * Un objetiv
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
                .addPackage(AprobacionEntity.class.getPackage())
                .addPackage(AprobacionLogic.class.getPackage())
                .addPackage(AprobacionPersistence.class.getPackage())
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
            AprobacionEntity entity = factory.manufacturePojo(AprobacionEntity.class);
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
     * Prueba para crear una Aprobacion.
     * @throws BussinessLogicException . En este test nunca se lanza 
     */
    @Test
    public void createAprobacionTest() throws BusinessLogicException{
        AprobacionEntity newEntity = factory.manufacturePojo(AprobacionEntity.class);
        newEntity.setTipo("REQUISITO");
        aprobacionLogic.createAprobacion(newEntity);
        AprobacionEntity entity = em.find(AprobacionEntity.class, newEntity.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getComentario(), newEntity.getComentario());
    }
    
     /**
     * Prueba para consultar la lista de Aprobaciones.
     */
    @Test
    public void getAllAprobacionesTest(){
        List<AprobacionEntity> list = aprobacionLogic.findAllAprobaciones();
        Assert.assertEquals(data.size(), list.size());
        for (AprobacionEntity entity : list) {
            boolean found = false;
            for (AprobacionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
     /**
     * Prueba para consultar una Aprobacion.
     */
    @Test
    public void getAprobacionTest() {
        AprobacionEntity entity = data.get(0);
        AprobacionEntity resultEntity = aprobacionLogic.findAprobacionById(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
    }

    /**
     * Prueba para actualizar una Aprobacion.
     */
    @Test
    public void updateAprobacionTest() throws BusinessLogicException {
        AprobacionEntity entity = data.get(0);
        AprobacionEntity pojoEntity = factory.manufacturePojo(AprobacionEntity.class);
        pojoEntity.setId(entity.getId());
        ObjetivoEntity objetivo = factory.manufacturePojo(ObjetivoEntity.class);
        pojoEntity.setObjetivo(objetivo);
        pojoEntity.setTipo("OBJETIVO");
        aprobacionLogic.updateAprobacion(pojoEntity);
        AprobacionEntity resp = em.find(AprobacionEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
    }
    

    /**
     * Prueba para eliminar una Aprobacion.
     */
    @Test
    public void deleteAprobacionTest() {
        AprobacionEntity entity = data.get(1);
        aprobacionLogic.deleteAprobacion(entity.getId());
        AprobacionEntity deleted = em.find(AprobacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
     /**
     * Prueba para cambir el dueño de un cambio.
     */
    @Test
    public void changeStakeHolderTest(){
        AprobacionEntity entity = data.get(1);
        entity = aprobacionLogic.changeStakeHolder(entity.getId(), sh.getId());
        StakeHolderEntity entitySH = entity.getStakeHolder();
        Assert.assertEquals(sh.getId(), entitySH.getId());
    }
    
//     /**
//     * Prueba para cambir el objetivo de una aprobación
//     */
//    @Test
//    public void changeObjetivoTest(){
//        AprobacionEntity entity = data.get(1);
//        entity = aprobacionLogic.changeObjetivo(entity.getId(), objetivo.getId());
//        ObjetivoEntity entityObjetivo = entity.getObjetivo();
//        Assert.assertEquals(objetivo.getId(), entityObjetivo.getId());
//    }
    
     /**
     * Prueba para cambir el requisito de una aprobación
     */
    @Test
    public void changeRequisitoTest(){
        AprobacionEntity entity = data.get(1);
        entity = aprobacionLogic.changeRequisito(entity.getId(), requisito.getId());
        RequisitoEntity entityRequisito = entity.getRequisito();
        Assert.assertEquals(requisito.getId(), entityRequisito.getId());
    }
}
