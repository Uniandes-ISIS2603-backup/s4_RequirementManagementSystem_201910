/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.OrganizacionLogic;
import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import java.util.ArrayList;
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
public class OrganizacionLogicTest {
    
    /**
     * Atributos 
     */
    @Inject
    private OrganizacionLogic organizacionLogic; 
    
    PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;
    
    private ArrayList<OrganizacionEntity> data; 
    
    @Inject
    UserTransaction utx;

    /**
     * 
     * @return java archive creado
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizacionEntity.class.getPackage())
                .addPackage(OrganizacionLogic.class.getPackage())
                .addPackage(OrganizacionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
        
    private void insertData(){
        
        data = new ArrayList<OrganizacionEntity>();
        
        for (int i = 0; i < 3; i++) {
            OrganizacionEntity entity = factory.manufacturePojo(OrganizacionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    

    @Before
    public void configTest(){
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            } catch(Exception e1){
                e1.printStackTrace();
            }
        }        
    }
    
    private void clearData(){
        em.createQuery("delete from OrganizacionEntity").executeUpdate();
    }
    
    @Test
    public void createOrganizacionTest() throws BusinessLogicException {
        OrganizacionEntity newEntity = factory.manufacturePojo(OrganizacionEntity.class);
        OrganizacionEntity result = organizacionLogic.createOrganizacion(newEntity);
        Assert.assertNotNull(result);
        OrganizacionEntity entity = em.find(OrganizacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
        
    @Test(expected = BusinessLogicException.class)
    public void createOrganizacionConMismoNombreTest() throws BusinessLogicException{
        
        OrganizacionEntity newEntity = factory.manufacturePojo(OrganizacionEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        organizacionLogic.createOrganizacion(newEntity);

    }

    /**
     * Prueba para consultar un Organization.
     */
    @Test
    public void getOrganizationTest() {
        OrganizacionEntity entity = data.get(0);
        OrganizacionEntity resultEntity = organizacionLogic.getOrganizacion(entity.getId());
        org.junit.Assert.assertNotNull(resultEntity);
        org.junit.Assert.assertEquals(entity.getId(), resultEntity.getId());
        org.junit.Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        org.junit.Assert.assertEquals(entity.getSector(), resultEntity.getSector());
    }

    /**
     * Prueba para actualizar un Organization.
     */
    @Test
    public void updateOrganizationTest() {
        OrganizacionEntity entity = data.get(0);
        OrganizacionEntity pojoEntity = factory.manufacturePojo(OrganizacionEntity.class);

        pojoEntity.setId(entity.getId());

        organizacionLogic.updateOrganizacion(pojoEntity.getId(), pojoEntity);

        OrganizacionEntity resp = em.find(OrganizacionEntity.class, entity.getId());

        org.junit.Assert.assertEquals(pojoEntity.getId(), resp.getId());
        org.junit.Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        org.junit.Assert.assertEquals(pojoEntity.getSector(), resp.getSector());
    }

    /**
     * Prueba para eliminar un Organization.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteOrganizationTest() throws BusinessLogicException {
        OrganizacionEntity entity = data.get(0);
        organizacionLogic.deleteOrganizacion(entity.getId());
        OrganizacionEntity deleted = em.find(OrganizacionEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }    
}
