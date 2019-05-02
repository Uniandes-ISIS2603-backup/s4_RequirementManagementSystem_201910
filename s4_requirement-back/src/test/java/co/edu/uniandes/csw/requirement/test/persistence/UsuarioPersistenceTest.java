package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import co.edu.uniandes.csw.requirement.persistence.UsuarioPersistence;
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
 * @author Mateo Pedroza
 */
@RunWith(Arquillian.class)
public class UsuarioPersistenceTest {

    /**
     * Se crea atributo tipo usuario
     */
    @Inject
    private UsuarioPersistence ep;

    /**
     * Se crea un entity manager
     */
    @PersistenceContext
    private EntityManager em;

    private ArrayList<UsuarioEntity> data;

    PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UserTransaction utx;

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     *
     * @return el deployment creado
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * configuracion del test
     */
    @Before
    public void configTest() {
        data = new ArrayList<UsuarioEntity>();

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
        em.createQuery("delete from OrganizacionEntity").executeUpdate();
    }

    /**
     * Test de crear usuario
     */
    @Test
    public void createUsuarioTest() {

        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity ee = ep.create(newEntity);
        Assert.assertNotNull(ee);
        UsuarioEntity entity = em.find(UsuarioEntity.class, ee.getId());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
    }

    /**
     * Prueba para consultar los usuarios
     */
    @Test
    public void getUsuarioTest() {
        List<UsuarioEntity> list;
        list = ep.findAll();
        org.junit.Assert.assertEquals(data.size(), list.size());
        for (UsuarioEntity ent : list) {
            boolean found = false;
            for (UsuarioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para eliminar un usuario.
     */
    @Test
    public void deleteUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        ep.delete(entity.getId());
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un usuario.
     */
    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);

        newEntity.setId(entity.getId());

        ep.update(newEntity);

        UsuarioEntity resp = em.find(UsuarioEntity.class, entity.getId());

        org.junit.Assert.assertEquals(newEntity.getUsuario(), resp.getUsuario());
        org.junit.Assert.assertEquals(newEntity.getTipo(), resp.getTipo());
    }
}
