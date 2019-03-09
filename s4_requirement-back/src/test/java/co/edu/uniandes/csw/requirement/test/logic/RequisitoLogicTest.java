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
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
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
import static org.junit.Assert.fail;

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
                .addPackage(RequisitoPersistence.class.getPackage())
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
    
    /**********************************************
    **** Verificación de Reglas de Negocio. *******
    **********************************************/
    
    /**
     * Creación de Requisito correcta, cumpliendo con las siguientes reglas de negocio:
     * /**
     * Método para crear un requisito, validando sus reglas de negocio.
     * Reglas de negocio: 
     * 1. La estabilidad de un requisito debe estar en el rango [1,3], discreto.
     * 2. La importancia de un requisito debe estar en el rango [1,3], discreto.
     * 3. El tipo solo puede ser "FUNCIONAL" o "NOFUNCIONAL", no es nullable.
     * 4. La descripción no puede ser null, o vacía. 
     * 
     * Reglas de negocio a verificar en otras dos clases de lógica con las asociaciones respectivas:
     * 5. Solamente los requisitos con tipo FUNCIONAL pueden tener casos de uso 
     * 6. El autor no puede ser nulo (autor es un Stakeholder)
     */
    
    @Test
    public void createRequisitoTest() 
    {
        RequisitoEntity newEntity = factory.manufacturePojo(RequisitoEntity.class);
        newEntity.setEstabilidad(1);
        newEntity.setDescripcion("Hola, soy correcta");
        newEntity.setImportancia(2);
        newEntity.setTipo("FUNCIONAL");
        
        try 
        {
            RequisitoEntity result = reqLogic.createRequisito(newEntity);
            Assert.assertNotNull(result);

            RequisitoEntity entity = em.find(RequisitoEntity.class, result.getId());
            Assert.assertEquals(newEntity.getId(), entity.getId());
            Assert.assertEquals(newEntity.getComentarios(), (entity.getComentarios()));
        }
        catch (BusinessLogicException e)
        {
            e.printStackTrace();
            fail("Error, no debería haber excepción");
            
        }
    }
    
    
    
        
    // Requisito no tiene nombre, no se implementa el test de ejemplo del video.   
}
