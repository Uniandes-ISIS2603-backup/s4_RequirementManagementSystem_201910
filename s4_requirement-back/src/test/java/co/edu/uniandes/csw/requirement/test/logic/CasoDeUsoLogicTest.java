/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.CasoDeUsoLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.ProyectoLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
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
 * @author Sofia Sarmiento
 */
@RunWith(Arquillian.class)
public class CasoDeUsoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CasoDeUsoLogic casoDeUsoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RequisitoEntity> dataRequisito = new ArrayList<RequisitoEntity>();

    private List<ProyectoEntity> dataProyecto = new ArrayList<ProyectoEntity>();

    private List<ObjetivoEntity> dataObjetivo = new ArrayList<ObjetivoEntity>();

    private List<CasoDeUsoEntity> data = new ArrayList<CasoDeUsoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CasoDeUsoEntity.class.getPackage())
                .addPackage(CasoDeUsoLogic.class.getPackage())
                .addPackage(CasoDeUsoPersistence.class.getPackage())
                .addPackage(RequisitoEntity.class.getPackage())
                .addPackage(RequisitoLogic.class.getPackage())
                .addPackage(RequisitoPersistence.class.getPackage())
                .addPackage(ProyectoEntity.class.getPackage())
                .addPackage(ProyectoLogic.class.getPackage())
                .addPackage(ProyectoPersistence.class.getPackage())
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(ObjetivoLogic.class.getPackage())
                .addPackage(ObjetivoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from AprobacionEntity").executeUpdate();
        em.createQuery("delete from CambioEntity").executeUpdate();
        em.createQuery("delete from CasoDeUsoEntity").executeUpdate();
        em.createQuery("delete from RequisitoEntity").executeUpdate();
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
        em.createQuery("delete from ProyectoEntity").executeUpdate();
    }

    public void insertData() {
        for (int i = 0; i < 3; i++) {
            ProyectoEntity entity = factory.manufacturePojo(ProyectoEntity.class);
            em.persist(entity);
            dataProyecto.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            ObjetivoEntity entity = factory.manufacturePojo(ObjetivoEntity.class);
            entity.setProyecto(dataProyecto.get(1));
            em.persist(entity);
            dataObjetivo.add(entity);
        }

        for (int i = 0; i < 3; i++) {
            RequisitoEntity entity = factory.manufacturePojo(RequisitoEntity.class);
            entity.setObjetivo(dataObjetivo.get(1));
            entity.setTipo("FUNCIONAL");
            em.persist(entity);
            dataRequisito.add(entity);
        }
        
        for (int i = 0; i < 3; i++)
        {
            CasoDeUsoEntity entity = factory.manufacturePojo(CasoDeUsoEntity.class);
            entity.setRequisito(dataRequisito.get(1));
            em.persist(entity);
            data.add(entity);
        }

        RequisitoEntity entity2 = factory.manufacturePojo(RequisitoEntity.class);
        entity2.setTipo("NOFUNCIONAL");
        entity2.setObjetivo(dataObjetivo.get(1));
        em.persist(entity2);
        dataRequisito.add(entity2);

        

    }

    /**
     * Creacion de caso de uso
     *
     * @throws BusinessLogicException La excepcionsilla
     */
    @Test
    public void createCasoDeUsoTest() throws BusinessLogicException {
        CasoDeUsoEntity newEntity = factory.manufacturePojo(CasoDeUsoEntity.class);
        newEntity.setRequisito(dataRequisito.get(1));
        CasoDeUsoEntity result = casoDeUsoLogic.createCasoDeUso(dataRequisito.get(1).getObjetivo().getId(), dataRequisito.get(1).getId(), newEntity);
        Assert.assertNotNull(result);
        CasoDeUsoEntity entity = em.find(CasoDeUsoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());

    }

    @Test(expected = BusinessLogicException.class)
    public void createCasoDeUsoTestConRequisitoInvalido() throws BusinessLogicException {
        CasoDeUsoEntity newEntity = factory.manufacturePojo(CasoDeUsoEntity.class);
        newEntity.setRequisito(dataRequisito.get(3));
        CasoDeUsoEntity result = casoDeUsoLogic.createCasoDeUso(dataRequisito.get(3).getObjetivo().getId(), dataRequisito.get(3).getId(), newEntity);
        Assert.assertNotNull(result);
        CasoDeUsoEntity entity = em.find(CasoDeUsoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }


    public void getCasosDeUsoTest() {
        List<CasoDeUsoEntity> list = casoDeUsoLogic.getCasosDeUso(dataRequisito.get(1).getObjetivo().getId(), dataRequisito.get(1).getId());
        Assert.assertEquals(data.size(), list.size());
        for (CasoDeUsoEntity entity : list) {
            boolean found = false;
            for (CasoDeUsoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getCasoDeUsoTest() {
        CasoDeUsoEntity entity = data.get(0);
        CasoDeUsoEntity resultEntity = casoDeUsoLogic.getCasoDeUso(entity.getRequisito().getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());

    }

    @Test
    public void updateCasoDeUsoTest() {
        CasoDeUsoEntity entity = data.get(0);
        CasoDeUsoEntity pojoEntity = factory.manufacturePojo(CasoDeUsoEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setRequisito(data.get(0).getRequisito());
        casoDeUsoLogic.updateCasoDeUso(pojoEntity.getRequisito().getObjetivo().getId(), pojoEntity.getRequisito().getId(), pojoEntity);
        CasoDeUsoEntity resp = em.find(CasoDeUsoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    @Test
    public void deleteCaminoTest() throws BusinessLogicException {
        CasoDeUsoEntity entity = data.get(1);
        casoDeUsoLogic.deleteCasoDeUso(entity.getRequisito().getId(), entity.getId());
        CasoDeUsoEntity deleted = em.find(CasoDeUsoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
