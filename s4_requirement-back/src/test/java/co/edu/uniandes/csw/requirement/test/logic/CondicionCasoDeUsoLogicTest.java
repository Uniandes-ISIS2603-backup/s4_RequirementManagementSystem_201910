/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.csw.requirement.ejb.*;
import co.edu.uniandes.csw.requirement.entities.*;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Sofia Sarmiento
 */
@RunWith(Arquillian.class)
public class CondicionCasoDeUsoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CondicionCasoDeUsoLogic condicionCasoLogic;

    @Inject
    private CasoDeUsoLogic casoUsoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CondicionEntity> data = new ArrayList<CondicionEntity>();
    private CasoDeUsoEntity casoUso = new CasoDeUsoEntity();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CondicionEntity.class.getPackage())
                .addPackage(CondicionLogic.class.getPackage())
                .addPackage(CasoDeUsoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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

    private void clearData() {
        em.createQuery("delete from CasoDeUsoEntity").executeUpdate();
        em.createQuery("delete from CondicionEntity").executeUpdate();
    }

    private void insertData() {
        casoUso = factory.manufacturePojo(CasoDeUsoEntity.class);
        em.persist(casoUso);
        for (int i = 0; i < 3; i++) {
            CondicionEntity condicion = factory.manufacturePojo(CondicionEntity.class);

            em.persist(condicion);
            data.add(condicion);

        }
    }
    
    @Test
    public void addCasoDeUsoTest() {
        CondicionEntity condicionEntity = data.get(0);
        CasoDeUsoEntity response = condicionCasoLogic.addCasoDeUso(casoUso.getId(), condicionEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(casoUso.getId(), response.getId());
    }
    
}
