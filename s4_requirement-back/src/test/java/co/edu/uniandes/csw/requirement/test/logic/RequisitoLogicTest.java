/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
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


/**
 *
 * @author jorgeandresesguerraalarcon
 */
@RunWith(Arquillian.class)
public class RequisitoLogicTest {
    
    /**
     * Atributos 
     */
    @Inject
    private RequisitoLogic reqLogic; 
    
    PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;
    
    private ArrayList<RequisitoEntity> data = new ArrayList<>(); 
    
    @Inject
    UserTransaction utx;

    /**
     * 
     * @return El jar que Arquillian va a desplegar en payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive deployment() // declaración de paquetes para correr pruebas.
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequisitoEntity.class.getPackage())
                .addPackage(RequisitoLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    /**
     * Escribir pruebas, por ejemplo: 
     * 1. Creación de requisito
     * 2. Creación de requisito violando cada una de las reglas de negocio.
     */
    
    private void insertData(){
        
        
        for (int i = 0; i < 3; i++) {
            RequisitoEntity entity = factory.manufacturePojo(RequisitoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    

    @Before
    public void configTest(){
        try
        {
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
        em.createQuery("delete from RequisitoEntity").executeUpdate();
    }
    
    @Test
    public void createRequisitoTest() throws BusinessLogicException {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        RequisitoEntity result = reqLogic.createRequisito(newEntity);
        Assert.assertNotNull(result);
        RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
        
    // Requisito no tiene nombre, no se implementa el test de ejemplo del video.   
}
