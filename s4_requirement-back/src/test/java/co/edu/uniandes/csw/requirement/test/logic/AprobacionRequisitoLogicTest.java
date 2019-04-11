/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.AprobacionRequisitoLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author jorgeandresesguerraalarcon & Sofia Alvarez
 */
@RunWith(Arquillian.class)
public class AprobacionRequisitoLogicTest 
{
    /**
     * Factoria de podam
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyeccion de dependencias de aprobacionrequisitologic.
     */
    @Inject
    private AprobacionRequisitoLogic aprobacionRequisitoLogic;

    /**
     * Inyeccion de dependencias de requisito logic.
     */
    @Inject
    private RequisitoLogic requisitoLogic;

    /**
     * Persistencecontext del entity manager
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * transaccion de usuario
     */
    @Inject
    private UserTransaction utx;

    /**
     * una aprobacion
     */
    private AprobacionEntity apr = new AprobacionEntity();
    
    /**
     * un requisito
     */
    private RequisitoEntity obj = new RequisitoEntity();
    
    // Considerar: No se incluyen relaciones a otras clases desde Requisito!
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AprobacionEntity.class.getPackage())
                .addPackage(RequisitoEntity.class.getPackage())
                .addPackage(AprobacionRequisitoLogic.class.getPackage())
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
        em.createQuery("delete from RequisitoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        obj = factory.manufacturePojo(RequisitoEntity.class);
        
        obj.setAprobaciones(new ArrayList<AprobacionEntity>());
        
        apr = factory.manufacturePojo(AprobacionEntity.class);
        
        
        em.persist(apr);
        em.persist(obj);
        
    }
    
    /**
     * Prueba para asociar un requisito a una aprobacion, genera la asociación bidireccional
     * @throws BusinessLogicException si hubo un error de creación 
     */
    @Test
    public void addRequisitoTest() throws BusinessLogicException
    {
        RequisitoEntity newObj = factory.manufacturePojo(RequisitoEntity.class);
        newObj.setEstabilidad(1);
        newObj.setImportancia(1);
        newObj.setTipo("FUNCIONAL");
        requisitoLogic.createRequisito(newObj);
        
        RequisitoEntity x = aprobacionRequisitoLogic.addRequisito(obj.getId(), apr.getId());
        // Debería no ser null pues acabamos de agregarlo.
        Assert.assertNotNull(x);
        
        Assert.assertEquals(x.getAutor(), newObj.getAutor());
        Assert.assertArrayEquals(x.getCambios().toArray(), newObj.getCambios().toArray());
        Assert.assertEquals(x.getEstabilidad(), x.getEstabilidad());
    }
    
    /**
     * Prueba para desasociar un requisito a una aprobacion
     */
    @Test
    public void removeRequisitoTest() throws BusinessLogicException
    {
        RequisitoEntity newObj = factory.manufacturePojo(RequisitoEntity.class);
        newObj.setEstabilidad(1);
        newObj.setImportancia(1);
        newObj.setTipo("FUNCIONAL");
        requisitoLogic.createRequisito(newObj);
        
        RequisitoEntity x = aprobacionRequisitoLogic.addRequisito(obj.getId(), apr.getId());
        
        aprobacionRequisitoLogic.removeRequisito(obj.getId(), apr.getId());
        Assert.assertNull(apr.getRequisito());
        Assert.assertTrue(obj.getAprobaciones().isEmpty());
 
    }
    
    

      
}
    
