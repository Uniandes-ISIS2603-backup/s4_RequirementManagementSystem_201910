/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.CambioObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import java.util.ArrayList;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase de test de la relacion cambio - objetivo
 * @author sofiaalvarez
 */
@RunWith(Arquillian.class)
public class CambioObjetivoLogicTest {
     
    /**
     * Factoria de podam
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyeccion de dependencias de acambio-objetivo
     */
    @Inject
    private CambioObjetivoLogic cambioObjetivoLogic;

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
     * Un cambio
     */
    private CambioEntity apr = new CambioEntity();
    /**
     * Un objetivo
     */
    private ObjetivoEntity obj = new ObjetivoEntity();
    
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CambioEntity.class.getPackage())
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(CambioObjetivoLogic.class.getPackage())
                .addPackage(CambioPersistence.class.getPackage())
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
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        obj = factory.manufacturePojo(ObjetivoEntity.class);
        
        obj.setAprobaciones(new ArrayList<>());
        
        apr = factory.manufacturePojo(CambioEntity.class);
        
        
        
        
        em.persist(apr);
        em.persist(obj);
        
    }
    
    /**
     * Prueba para asociar un objetivo a un cambiop, genera la asociación bidireccional
     * @throws BusinessLogicException si hubo un error de creación 
     */
    @Test
    public void addObjetivoTest() throws BusinessLogicException
    {
        ObjetivoEntity newObj = factory.manufacturePojo(ObjetivoEntity.class);
        newObj.setEstabilidad(1);
        newObj.setImportancia(1);
        objetivoLogic.createObjetivo(newObj);
        
        ObjetivoEntity x = cambioObjetivoLogic.addObjetivo(obj.getId(), apr.getId());
        // Debería no ser null pues acabamos de agregarlo.
        Assert.assertNotNull(x);
        
        Assert.assertEquals(x.getAutor(), newObj.getAutor());
        Assert.assertArrayEquals(x.getAprobaciones().toArray(), newObj.getAprobaciones().toArray());
        Assert.assertEquals(x.getEstabilidad(), x.getEstabilidad());
    }
    
    /**
     * Prueba para desasociar un objetivo a un cambio
     */
    @Test
    public void removeObjetivoTest() throws BusinessLogicException
    {
        ObjetivoEntity newObj = factory.manufacturePojo(ObjetivoEntity.class);
        newObj.setEstabilidad(1);
        newObj.setImportancia(1);
        objetivoLogic.createObjetivo(newObj);
        
        ObjetivoEntity x = cambioObjetivoLogic.addObjetivo(obj.getId(), apr.getId());
        
        cambioObjetivoLogic.removeObjetivo(obj.getId(), apr.getId());
        Assert.assertNull(apr.getObjetivo());
        Assert.assertTrue(obj.getAprobaciones().isEmpty());
 
    }
     
    
}
