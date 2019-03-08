/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
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
 *
 * @author Emilio
 */
@RunWith(Arquillian.class)
public class AprobacionPersistenceTest {
    
    @Inject
    private AprobacionPersistence aprobacionPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;

    private List<AprobacionEntity> data = new ArrayList<AprobacionEntity>();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AprobacionEntity.class.getPackage())
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from AprobacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            AprobacionEntity entity = factory.manufacturePojo(AprobacionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void createAprobacionTest(){
        PodamFactory factory = new PodamFactoryImpl();
        AprobacionEntity newEntity = factory.manufacturePojo(AprobacionEntity.class);
        
        AprobacionEntity addedEntity = aprobacionPersistence.create(newEntity);
        
        Assert.assertNotNull(addedEntity);
        
        AprobacionEntity refEntity = em.find(AprobacionEntity.class, addedEntity.getId());
        Assert.assertEquals(newEntity.getId(), refEntity.getId());   
    }

    @Test
    public void findAprobacionByIdTest(){
        AprobacionEntity newEntity = data.get(0);
        AprobacionEntity foundEntity = aprobacionPersistence.find(newEntity.getId());
        Assert.assertNotNull(foundEntity);
        Assert.assertEquals(newEntity.getId(), foundEntity.getId());
    }
    
     /**
     * Prueba para consultar la lista de Aprobaciones.
     */
    @Test
    public void getAllAprobacionesTest() {
        List<AprobacionEntity> list;
        list = aprobacionPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (AprobacionEntity ent : list) {
            boolean found = false;
            for (AprobacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
         /**
     * Prueba para actualizar un Cambio.
     */
    @Test
    public void updateAprobacionTest() {
        AprobacionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AprobacionEntity newEntity = factory.manufacturePojo(AprobacionEntity.class);

        newEntity.setId(entity.getId());

        aprobacionPersistence.update(newEntity);

        AprobacionEntity resp = em.find(AprobacionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
    }
    
     /**
     * Prueba para eliminar un Cambio.
     */
    @Test
    public void deleteAprobacionTest() {
        AprobacionEntity entity = data.get(0);
        aprobacionPersistence.delete(entity.getId());
        AprobacionEntity deleted = em.find(AprobacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
