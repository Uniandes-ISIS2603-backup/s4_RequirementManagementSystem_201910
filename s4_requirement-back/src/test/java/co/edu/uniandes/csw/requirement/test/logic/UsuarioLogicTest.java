package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.UsuarioLogic;
import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.UsuarioPersistence;
import java.util.ArrayList;
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
public class UsuarioLogicTest {
    
    /**
     * Atributos 
     */
    @Inject
    private UsuarioLogic usuarioLogic; 
    
    PodamFactory factory = new PodamFactoryImpl();
    
    @PersistenceContext
    private EntityManager em;
    
    private ArrayList<UsuarioEntity> data; 
    
    @Inject
    private UserTransaction utx;
    
    /**
     * crea datos para ser probados
     */
    private void insertData(){
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
        }  
    }
    
    /**
     * 
     * @return deployment creado
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuracion de la prueba
     */
    @Before
    public void configTest(){
        data = new ArrayList<UsuarioEntity>();
        
        try{
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch(Exception e){
            e.printStackTrace();
            try{
                utx.rollback();
            } catch(Exception e1){
                e1.printStackTrace();
            }
        }        
    }
    
    /**
     * borra datos creados previamente
     */
    private void clearData(){
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }
    
    /**
     * prueba creacion de usuario
     * @throws BusinessLogicException 
     */
    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioLogic.createUsuario(newEntity);
        Assert.assertNotNull(result);
        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
    }
    
    /**
     * Prueba para consultar un usuario.
     */
    @Test
    public void getUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity resultEntity = usuarioLogic.getUsuario(entity.getId());
        org.junit.Assert.assertNotNull(resultEntity);
        org.junit.Assert.assertEquals(entity.getId(), resultEntity.getId());
        org.junit.Assert.assertEquals(entity.getUsuario(), resultEntity.getUsuario());
        org.junit.Assert.assertEquals(entity.getTipo(), resultEntity.getTipo());
    }

    /**
     * Prueba para actualizar un usuario.
     */
    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);

        UsuarioEntity resp = em.find(UsuarioEntity.class, entity.getId());

        org.junit.Assert.assertEquals(pojoEntity.getId(), resp.getId());
        org.junit.Assert.assertEquals(pojoEntity.getUsuario(), resp.getUsuario());
        org.junit.Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
    }

/**
 * Prueba para eliminar un usuario.
 * @throws BusinessLogicException 
 */
    @Test
    public void deleteUsuarioTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        usuarioLogic.deleteUsuario(entity.getId());
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }  
   
}