/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
    private RequisitoPersistence ep; // como es stateless, no se puede crear. 
    // Se hace una inyección de dependencias, es un patrón de diseño para manejar los stateless.

    @PersistenceContext
    private EntityManager em;

    @Deployment
    public static JavaArchive deployment() // declaración de paquetes para correr pruebas.
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RequisitoEntity.class.getPackage())
                .addPackage(RequisitoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createEditorialTest() {
        PodamFactory factory = new PodamFactoryImpl();
        RequisitoEntity test = factory.manufacturePojo(RequisitoEntity.class);

        RequisitoEntity re = ep.create(test); //ep fue inyectada
        Assert.assertNotNull(re);
        // poder preguntar si el objeto quedó grabado en la DB.

        RequisitoEntity entity = em.find(RequisitoEntity.class, re.getId());
        Assert.assertEquals(test.getAutor(), entity.getAutor());
    }
}
