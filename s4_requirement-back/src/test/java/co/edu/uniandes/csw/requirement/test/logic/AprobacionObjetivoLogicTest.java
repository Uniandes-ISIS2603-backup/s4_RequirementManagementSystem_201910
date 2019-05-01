/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.AprobacionObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
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
 *Clase de test de relacion de aprobacion y objetivo
 * @author jorgeandresesguerraalarcon & Sofia Alvarez
 */
@RunWith(Arquillian.class)
public class AprobacionObjetivoLogicTest 
{
    /**
     * Factoria de podam
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyeccion de dependencias de aprobacion-objetivo
     */
    @Inject
    private AprobacionObjetivoLogic aprobacionObjetivoLogic;

    /**
     * Inyeccion de dependencias de objetivo 
     */
    @Inject
    private ObjetivoLogic objetivoLogic;

    /**
     * Inyeccion de la persistencia del entity manager
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Inyeccion de una transaccion de usuario
     */
    @Inject
    private UserTransaction utx;

    /**
     * Una aprobacion
     */
    private AprobacionEntity apr = new AprobacionEntity();
    /**
     * Un objetivo
     */
    private ObjetivoEntity obj = new ObjetivoEntity();
    
    // Considerar: No se incluyen relaciones a otras clases desde Objetivo!
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AprobacionEntity.class.getPackage())
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(AprobacionObjetivoLogic.class.getPackage())
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
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        obj = factory.manufacturePojo(ObjetivoEntity.class);
        
        obj.setAprobaciones(new ArrayList<AprobacionEntity>());
        
        apr = factory.manufacturePojo(AprobacionEntity.class);
        
        
        
        
        em.persist(apr);
        em.persist(obj);
        
    }
    
    /**
     * Prueba para asociar un objetivo a una aprobacion, genera la asociación bidireccional
     * @throws BusinessLogicException si hubo un error de creación 
     */
    @Test
    public void addObjetivoTest() throws BusinessLogicException
    {
        ObjetivoEntity newObj = factory.manufacturePojo(ObjetivoEntity.class);
        newObj.setEstabilidad(1);
        newObj.setImportancia(1);
        objetivoLogic.createObjetivo(newObj);
        
        ObjetivoEntity x = aprobacionObjetivoLogic.addObjetivo(obj.getId(), apr.getId());
        // Debería no ser null pues acabamos de agregarlo.
        Assert.assertNotNull(x);
        
        Assert.assertEquals(x.getAutor(), newObj.getAutor());
        Assert.assertArrayEquals(x.getCambios().toArray(), newObj.getCambios().toArray());
        Assert.assertEquals(x.getEstabilidad(), x.getEstabilidad());
    }
    
    /**
     * Prueba para desasociar un objetivo a una aprobacion
     */
    @Test
    public void removeObjetivoTest() throws BusinessLogicException
    {
        ObjetivoEntity newObj = factory.manufacturePojo(ObjetivoEntity.class);
        newObj.setEstabilidad(1);
        newObj.setImportancia(1);
        objetivoLogic.createObjetivo(newObj);
        
        ObjetivoEntity x = aprobacionObjetivoLogic.addObjetivo(obj.getId(), apr.getId());
        
        aprobacionObjetivoLogic.removeObjetivo(obj.getId(), apr.getId());
        Assert.assertNull(apr.getObjetivo());
        Assert.assertTrue(obj.getAprobaciones().isEmpty());
 
    }
    
    

      
}
    
