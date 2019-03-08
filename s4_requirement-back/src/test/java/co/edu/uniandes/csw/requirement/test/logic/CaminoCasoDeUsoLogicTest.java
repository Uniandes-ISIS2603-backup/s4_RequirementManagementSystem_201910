/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.CaminoCasoDeUsoLogic;
import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class CaminoCasoDeUsoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CaminoCasoDeUsoLogic caminoCasoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CasoDeUsoEntity> data = new ArrayList<CasoDeUsoEntity>();

    private List<CaminoEntity> caminosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CasoDeUsoEntity.class.getPackage())
                .addPackage(CaminoCasoDeUsoLogicTest.class.getPackage())
                .addPackage(CasoDeUsoPersistence.class.getPackage())
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
        em.createQuery("delete from CaminoEntity").executeUpdate();
        //em.createQuery("delete from OrganizationEntity ").executeUpdate();
        em.createQuery("delete from CasoDeUsoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CaminoEntity caminos = factory.manufacturePojo(CaminoEntity.class);

            em.persist(caminos);
            caminosData.add(caminos);
        }
        for (int i = 0; i < 3; i++) {
            CasoDeUsoEntity entity = factory.manufacturePojo(CasoDeUsoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                caminosData.get(i).setCasos(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Prizes existente a un Author.
     */
    @Test
    public void addCasoDeUsoTest() {
        CasoDeUsoEntity entity = data.get(0);
        CaminoEntity caminoEntity = caminosData.get(1);
        CasoDeUsoEntity response = caminoCasoLogic.addCasoDeUso(entity.getId(), caminoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Author.
     */
    @Test
    public void getCasoDeUsoTest() {
        CaminoEntity entity = caminosData.get(0);
        CasoDeUsoEntity resultEntity = caminoCasoLogic.getCasoDeUso(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getCasos().getId(), resultEntity.getId());
    }


    /**
     * Prueba para desasociar un Prize existente de un Author existente.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void removeCasoDeUsoTest() throws BusinessLogicException {
        caminoCasoLogic.removeCasoDeUso(caminosData.get(0).getId());
        Assert.assertNull(caminoCasoLogic.getCasoDeUso(caminosData.get(0).getId()));
    }

    /**
     * Prueba para desasociar un Prize existente de un Author existente.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeCaminoSinCasoDeUsoTest() throws BusinessLogicException {
        caminoCasoLogic.removeCasoDeUso(caminosData.get(1).getId());
    }
}
