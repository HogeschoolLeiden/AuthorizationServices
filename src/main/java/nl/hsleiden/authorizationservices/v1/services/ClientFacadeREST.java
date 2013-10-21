/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.v1.services;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import nl.hsleiden.authorizationservices.model.OauthClient;
import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 *
 * @author hl
 */
@Path("client")
public class ClientFacadeREST extends AbstractFacade<OauthClient>{
    
    private EntityManagerFactory emf;
    private Logger logger = Logger.getLogger(ClientFacadeREST.class.getName());

    public ClientFacadeREST() {
        super(OauthClient.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(OauthClient entity)  {
        
        logger.debug("In de create methode");
        
        MD5Generator generator = new MD5Generator();
        try {
            String encryptedId = generator.generateValue(entity.getClientid());
            entity.setClientid(encryptedId);
        } catch (OAuthSystemException ex) {
            java.util.logging.Logger.getLogger(ClientFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.debug(entity.getClientid());
        
        
        entity.setCreationdate(Calendar.getInstance().getTime());
        entity.setExpires(null);
        entity.setUserid("moet uit saml komen");
        logger.debug("userid: " + entity.getUserid());
        logger.debug("uri: " + entity.getRedirecturi());
        EntityManager em = getEntityManager();
        
        em.getTransaction().begin();
        
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        
    }
    
    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(OauthClient entity) {
        super.edit(entity);
    }

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") String id) {
//        super.remove(super.find(id));
//    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public OauthClient find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<OauthClient> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("aut.hsleiden.nl");
        return emf.createEntityManager();
    }
    
}
