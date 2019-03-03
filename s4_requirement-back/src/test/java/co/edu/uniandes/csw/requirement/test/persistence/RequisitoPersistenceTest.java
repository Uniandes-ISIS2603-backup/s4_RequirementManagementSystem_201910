/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
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
 * @author Jorge Esguerra
 */
@RunWith(Arquillian.class)
public class RequisitoPersistenceTest {

    @Inject
    private RequisitoPersistence persistence; // como es stateless, no se puede crear. 
    // Se hace una inyección de dependencias, es un patrón de diseño para manejar los stateless.

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<RequisitoEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive deployment() // declaración de paquetes para correr pruebas.
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequisitoEntity.class.getPackage())
                .addPackage(RequisitoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    private void clearData() {
        em.createQuery("delete from RequisitoEntity").executeUpdate();
    }

    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++)
        {
            RequisitoEntity entity = factory.manufacturePojo(RequisitoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    @Before
    public void configTest() {
        try 
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            try 
            {
                utx.rollback();
            } 
            catch (Exception e1) 
            {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Prueba de la creación de requisitos dentro de la base de datos.
     */
    @Test
    public void createRequisitoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        RequisitoEntity test = factory.manufacturePojo(RequisitoEntity.class);

        RequisitoEntity re = persistence.create(test); //persistence fue inyectada
        Assert.assertNotNull(re);
        // poder preguntar si el objeto quedó grabado en la DB.

        RequisitoEntity entity = em.find(RequisitoEntity.class, re.getId());
        Assert.assertEquals(test.getId(), entity.getId());
    }
    /**
     * Prueba para la obtención de todos los requisitos dentro de la base de datos. Se comparan uno por uno.
     */
    @Test
    public void getRequisitosTest()
    {
        List<RequisitoEntity> list = persistence.findAll();
        org.junit.Assert.assertEquals(data.size(), list.size());
        
        
        /*
        * Verificación de que la lista corresponde.
        */
        for (RequisitoEntity req : list) 
        {
            boolean found = false;
            for (RequisitoEntity req2 : data) 
            {
                if (req.getId().equals(req2.getId())) 
                {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }
    /**
     * Prueba si se encuentra un requisito que existe en la lista, en la base de datos. 
     */
    @Test
    public void getRequisitoTest() {
        RequisitoEntity x = data.get(0);
        RequisitoEntity newX = persistence.find(x.getId());
        org.junit.Assert.assertNotNull(newX);
        org.junit.Assert.assertEquals(x.getDescripcion(), newX.getDescripcion());
        org.junit.Assert.assertEquals(x.getEstabilidad(), newX.getEstabilidad());
    }
    
    /**
     * Prueba de la actualización de objetivos en la base de datos. 
     * Actualiza la primera entrada de la lista de RequisitoEntities con datos aleatorios,
     * y luego actualiza en la persistencia. Finalmente, verifica si la persistencia encuentra
     * el registro actualizado, y verifica con la nueva información guardada,
     * preguntando por la descripción.
     */
    @Test
    public void updateObjetivoTest() {
        RequisitoEntity x = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        RequisitoEntity newX = factory.manufacturePojo(RequisitoEntity.class);

        newX.setId(x.getId());

        persistence.update(newX);

        RequisitoEntity resp = em.find(RequisitoEntity.class, x.getId());

        org.junit.Assert.assertEquals(newX.getDescripcion(), resp.getDescripcion());
    }
    
    /**
     * Prueba que un objetivo se haya borrado correctamente de la base de datos.
     * Para el ejemplo, se borra el primer elemento de la base de datos.
     */
    @Test
    public void deleteObjetivoTest() {
        RequisitoEntity x = data.get(0);
        persistence.delete(x.getId());
        ObjetivoEntity deleted = em.find(ObjetivoEntity.class, x.getId());
        // Null es la default para cuando find falla.
        org.junit.Assert.assertNull(deleted);
    }
}
