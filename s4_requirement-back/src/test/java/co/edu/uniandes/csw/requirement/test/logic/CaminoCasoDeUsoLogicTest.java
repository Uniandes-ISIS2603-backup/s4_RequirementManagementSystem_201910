/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.*;
import co.edu.uniandes.csw.requirement.entities.*;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
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
 * @author davidmanosalva
 */
@RunWith(Arquillian.class)
public class CaminoCasoDeUsoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CaminoCasoDeUsoLogic caminoCasoLogic;

    @Inject
    private CasoDeUsoLogic casoUsoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CaminoEntity> data = new ArrayList<CaminoEntity>();
    private CasoDeUsoEntity casoUso = new CasoDeUsoEntity();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CaminoEntity.class.getPackage())
                .addPackage(CaminoLogic.class.getPackage())
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
        em.createQuery("delete from CaminoEntity").executeUpdate();
    }

    private void insertData() {
        casoUso = factory.manufacturePojo(CasoDeUsoEntity.class);
        em.persist(casoUso);
        for (int i = 0; i < 3; i++) {
            CaminoEntity camino = factory.manufacturePojo(CaminoEntity.class);

            em.persist(camino);
            data.add(camino);

        }
    }
    
    @Test
    public void addAuthorTest() {
        CaminoEntity caminEntity = data.get(0);
        CasoDeUsoEntity response = caminoCasoLogic.addCasoDeUso(casoUso.getId(), caminEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(casoUso.getId(), response.getId());
    }
}
