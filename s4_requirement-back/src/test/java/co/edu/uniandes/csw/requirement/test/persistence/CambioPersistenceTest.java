/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
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
 *Clase que evalúa los tests de persistencia para un cambio
 * @author Sofia Alvarez
 */
@RunWith(Arquillian.class)
public class CambioPersistenceTest {
    
//    /**
//     * Inyección de dependencias de la persistencia de cambio.
//     */
//    @Inject
//    private CambioPersistence cambioPersistence;
//    
//    /**
//     * Contexto de persistencia: entity manager.
//     */
//    @PersistenceContext
//    private EntityManager em;
//    
//    /**
//     * Inyección de una transacción de usuario.
//     */
//    @Inject
//    UserTransaction utx;
//
//    /**
//     * Lista con los datos de todos los cambios.
//     */
//    private List<CambioEntity> data = new ArrayList<>();
//    
//     /**
//     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
//     * El jar contiene las clases, el descriptor de la base de datos y el
//     * archivo beans.xml para resolver la inyección de dependencias.
//     */
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addPackage(CambioEntity.class.getPackage())
//                .addPackage(CambioPersistence.class.getPackage())
//                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
//                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
//    }
//    
//     /**
//     * Configuración inicial de la prueba.
//     */
//    @Before
//    public void configTest() {
//        try {
//            utx.begin();
//            em.joinTransaction();
//            clearData();
//            insertData();
//            utx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                utx.rollback();
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * Limpia las tablas que están implicadas en la prueba.
//     */
//    private void clearData() {
//        em.createQuery("delete from CambioEntity").executeUpdate();
//    }
//
//    /**
//     * Inserta los datos iniciales para el correcto funcionamiento de las
//     * pruebas.
//     */
//    private void insertData() {
//        PodamFactory factory = new PodamFactoryImpl();
//        for (int i = 0; i < 3; i++) {
//
//            CambioEntity entity = factory.manufacturePojo(CambioEntity.class);
//
//            em.persist(entity);
//
//            data.add(entity);
//        }
//    }
//    
//    /**
//     * Método que prueba la creación de un cambio
//     */
//    @Test
//    public void createCambioTest(){
//        PodamFactory factory = new PodamFactoryImpl();
//        CambioEntity newEntity = factory.manufacturePojo(CambioEntity.class);
//        
//        CambioEntity addedEntity = cambioPersistence.create(newEntity);
//        
//        Assert.assertNotNull(addedEntity);
//        
//        CambioEntity refEntity = em.find(CambioEntity.class, addedEntity.getId());
//        Assert.assertEquals(newEntity.getId(), refEntity.getId());   
//    }
//
//    /**
//     * Encontrar un cambio, dado su id.
//     */
//    @Test
//    public void findCambioByIdTest(){
//        CambioEntity newEntity = data.get(0);
//        CambioEntity foundEntity = cambioPersistence.find(newEntity.getId());
//        Assert.assertNotNull(foundEntity);
//        Assert.assertEquals(newEntity.getId(), foundEntity.getId());
//    }
//    
//     /**
//     * Prueba para consultar la lista de Cambios.
//     */
//    @Test
//    public void getAllCambiosTest() {
//        List<CambioEntity> list = cambioPersistence.findAll();
//        Assert.assertEquals(data.size(), list.size());
//        for (CambioEntity ent : list) {
//            boolean found = false;
//            for (CambioEntity entity : data) {
//                if (ent.getId().equals(entity.getId())) {
//                    found = true;
//                }
//            }
//            Assert.assertTrue(found);
//        }
//    }
//    
//     /**
//     * Prueba para actualizar un Cambio.
//     */
//    @Test
//    public void updateCambioTest() {
//        CambioEntity entity = data.get(0);
//        PodamFactory factory = new PodamFactoryImpl();
//        CambioEntity newEntity = factory.manufacturePojo(CambioEntity.class);
//
//        newEntity.setId(entity.getId());
//        cambioPersistence.update(newEntity);
//        CambioEntity resp = em.find(CambioEntity.class, entity.getId());
//        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
//    }
//    
//     /**
//     * Prueba para eliminar un Cambio.
//     */
//    @Test
//    public void deleteCambioTest() {
//        CambioEntity entity = data.get(0);
//        cambioPersistence.delete(entity.getId());
//        CambioEntity deleted = em.find(CambioEntity.class, entity.getId());
//        Assert.assertNull(deleted);
//    }
    
}
