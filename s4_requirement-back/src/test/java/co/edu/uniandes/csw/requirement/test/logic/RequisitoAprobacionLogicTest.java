/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.AprobacionLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoAprobacionLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
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
 * @author jorgeandresesguerraalarcon
 */
@RunWith(Arquillian.class)
public class RequisitoAprobacionLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RequisitoAprobacionLogic raLogic;

    @Inject
    private AprobacionLogic aprLogic;
    
    @Inject
    private RequisitoLogic reqLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<AprobacionEntity> aprs = new ArrayList<AprobacionEntity>();
    
    private RequisitoEntity req = new RequisitoEntity();
    
    
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
                .addPackage(RequisitoAprobacionLogic.class.getPackage())
                .addPackage(RequisitoPersistence.class.getPackage())
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
    private void insertData() throws BusinessLogicException
    {
        
        req = factory.manufacturePojo(RequisitoEntity.class);
        req.setEstabilidad(2);
        req.setImportancia(2);
        req.setAprobaciones(new ArrayList<AprobacionEntity>());
        
        // Se crea una lista de aprobaciones paralela y que aun no ha sido asociada con el requisito
        for(int i = 0; i < 3; i++)
        {
            AprobacionEntity nuevo = factory.manufacturePojo(AprobacionEntity.class);
            aprs.add(nuevo);
            em.persist(nuevo);
        }
        em.persist(req);
        
        
    }
    
    /**
     * Prueba para asociar una aprobacion a un requisito, genera la asociación bidireccional, ingresando la aprobacion a la lista de atributo del requisito.
     * @throws BusinessLogicException si hubo un error de creación 
     */
    @Test
    public void addAprobacionTest() throws BusinessLogicException
    {
        //System.out.println("ACAAAA " + req.getId() + " "+ aprs.get(0).getId());
        AprobacionEntity x = factory.manufacturePojo(AprobacionEntity.class);
        x.setRequisito(new RequisitoEntity());
        x.setTipo("REQUISITO");
        aprLogic.createAprobacion(x);
        
        AprobacionEntity y = raLogic.addAprobacion(req.getId(), aprs.get(0).getId());
        // Debería no ser null pues acabamos de agregarlo de una lista existente
        Assert.assertNotNull(y);
        
        Assert.assertFalse(y.getRequisito().getAprobaciones().isEmpty());

    }
    /*
    /**
     * Prueba para desasociar una aprobación a un requisito
     */
    @Test
    public void removeOneAprobacionTest() throws BusinessLogicException
    {
        Assert.assertTrue(req.getAprobaciones().isEmpty());
        AprobacionEntity x = raLogic.addAprobacion(req.getId(), aprs.get(0).getId());
        Assert.assertFalse(x.getRequisito().getAprobaciones().isEmpty());
        raLogic.removeOneAprobacion(req.getId(), aprs.get(0).getId());
        Assert.assertTrue(req.getAprobaciones().isEmpty());
 
    }
    
    /**
     * Prueba para desasociar una aprobación a un requisito
     */
    @Test
    public void getAprobacionesTest() throws BusinessLogicException
    {
        raLogic.addAprobacion(req.getId(), aprs.get(0).getId());
        
        
        raLogic.removeOneAprobacion(req.getId(), aprs.get(0).getId());
        List<AprobacionEntity> x = raLogic.getAprobaciones(req.getId());
        Assert.assertEquals(0, req.getAprobaciones().size());
 
    }
    
    /**
     * Prueba para desasociar todas las aprobaciones de un requisito
     */
    @Test
    public void clearAprobacionesTest() throws BusinessLogicException
    {
        AprobacionEntity x = null;
        for(int i = 0; i < 3; i++)
        {
            x = raLogic.addAprobacion(req.getId(), aprs.get(i).getId());
        }
        
        Assert.assertTrue(!x.getRequisito().getAprobaciones().isEmpty());
        
        raLogic.clearAprobaciones(req.getId());
        List<AprobacionEntity> xy = raLogic.getAprobaciones(req.getId());
        Assert.assertTrue(xy.isEmpty());
 
    }
    
}
