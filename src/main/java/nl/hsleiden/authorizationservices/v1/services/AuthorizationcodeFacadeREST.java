/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.v1.services;

import java.util.Calendar;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import nl.hsleiden.authorizationservices.model.Authorizationcode;
import nl.hsleiden.authorizationservices.model.OauthClient;
import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 *
 * @author hl
 */
@Path("authorize")
public class AuthorizationcodeFacadeREST extends AbstractFacade<Authorizationcode> {
    private EntityManagerFactory emf;
    private Logger logger = Logger.getLogger(AuthorizationcodeFacadeREST.class.getName());

    public AuthorizationcodeFacadeREST() {
        super(Authorizationcode.class);
    }

    @GET
    @Produces({"application/json"})
    public Authorizationcode find(@QueryParam("clientid") String clientId, @QueryParam("uid") String uid, @QueryParam("organisation") String organisation) {
        
        validateClient(clientId);
        Authorizationcode code = new Authorizationcode();
        
        Calendar now = Calendar.getInstance();
        code.setClientid(clientId);
        code.setUserid(uid);
        code.setOrganisation(organisation);
        code.setCreationdate(now.getTime());
        
        MD5Generator generator = new MD5Generator();
        try {
            String encryptedId = generator.generateValue(uid + now.getTime());
            code.setAuthorizationcode(encryptedId);
        } catch (OAuthSystemException ex) {
            java.util.logging.Logger.getLogger(AuthorizationcodeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        now.add(Calendar.MINUTE, 5);
        code.setExpires(now.getTime());
        code.setRedirecturi("");
        code.setScope("");
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(code);
        em.getTransaction().commit();
        em.close();
        return code;
    }

    private boolean validateClient(String clientId){
        boolean valid = false;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("OauthClient.findByClientid").setParameter("clientid", clientId);
        //OauthClient client = (OauthClient)query.getSingleResult();
        try {
            query.getSingleResult();
            logger.debug("Er is een client gevonden.");
            valid = true;
        } catch (NoResultException nre) {
            throw new WebApplicationException("No valid client", Response.Status.FORBIDDEN);
        }
        return valid;
    }
    

    @Override
    protected EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("aut.hsleiden.nl");
        return emf.createEntityManager();
    }
    
}
