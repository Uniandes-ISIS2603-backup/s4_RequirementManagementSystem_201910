/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
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
 * @author Mateo Pedroza
 */
@RunWith(Arquillian.class)
public class OrganizacionPersistenceTest {

   /**
    * Se crea atributo tipo organizacion persistence
    */ 
    @Inject
    private OrganizacionPersistence ep;

    /**
    * Se crea el entity manager
    */ 
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private ArrayList<OrganizacionEntity> data; 
    
    PodamFactory factory = new PodamFactoryImpl();

    /**
     * 
     * @return el deployent creado
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizacionEntity.class.getPackage())
                .addPackage(OrganizacionPersistence.class.getPackage())
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
        em.createQuery("delete from OrganizacionEntity").executeUpdate();
    }
    
     /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData(){
        
        data = new ArrayList<OrganizacionEntity>();
        
        for (int i = 0; i < 3; i++) {
            OrganizacionEntity entity = factory.manufacturePojo(OrganizacionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Test de creacion de organizacion
     */
    @Test
    public void createOrganizacionTest() {

        PodamFactory factory = new PodamFactoryImpl();
        OrganizacionEntity newEntity = factory.manufacturePojo(OrganizacionEntity.class);
        OrganizacionEntity ee = ep.create(newEntity);
        Assert.assertNotNull(ee);
        OrganizacionEntity entity = em.find(OrganizacionEntity.class, ee.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
     /**
     * Prueba para consultar la lista de premios.
     */
    @Test
    public void getOrganizationsTest() {
        List<OrganizacionEntity> list;
        list = ep.findAll();
        org.junit.Assert.assertEquals(data.size(), list.size());
        for (OrganizacionEntity ent : list) {
            boolean found = false;
            for (OrganizacionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para eliminar un Organization.
     */
    @Test
    public void deleteOrganizationTest() {
        OrganizacionEntity entity = data.get(0);
        ep.delete(entity.getId());
        OrganizacionEntity deleted = em.find(OrganizacionEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }
    
     /**
     * Prueba para actualizar un Organization.
     */
    @Test
    public void updateOrganizationTest() {
        OrganizacionEntity entity = data.get(0);
        OrganizacionEntity newEntity = factory.manufacturePojo(OrganizacionEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        OrganizacionEntity resp = em.find(OrganizacionEntity.class, entity.getId());

        org.junit.Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        org.junit.Assert.assertEquals(newEntity.getSector(), resp.getSector());
    }
 
}
