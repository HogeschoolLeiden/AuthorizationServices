/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.v1.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import nl.hsleiden.authorizationservices.model.AccessToken;
import nl.hsleiden.authorizationservices.model.Authorizationcode;
import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import service.AbstractFacade;

/**
 *
 * @author hl
 */

@Path("accesstoken")
public class AccessTokenFacadeREST extends AbstractFacade<AccessToken> {
    private EntityManagerFactory emf;
    private Logger logger = Logger.getLogger(AccessTokenFacadeREST.class.getName());

    
    @Context HttpServletRequest request;
    @Context HttpServletResponse response;

    public AccessTokenFacadeREST() {
        super(AccessToken.class);
    }

    @GET
    @Path("/tokeninfo/{id}")
    @Produces({"application/json"})
    public AccessToken validate(@PathParam("id") String id) {
        AccessToken at = super.find(id);
        
        boolean b = false;
        Date date = at.getExpires_in();
        Date d = Calendar.getInstance().getTime();

        if (date.compareTo(d) >= 0) {
            b = true;
        }
        
        if (b) {
            return at;
        } else {
            throw new WebApplicationException("{\"error\":\"invalid_token\"}", 404);
        }
    }

    @POST
    @Produces({"application/json"})
    public AccessToken find(@FormParam("code") String code, @FormParam("client_id") String client_id, @FormParam("client_secret") String client_secret,
            @FormParam("redirect_uri") String redirect_uri, @FormParam("grant_type") String grant_type) {
        
        
        logger.debug("In de get accesstoken methode " + code);
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Authorizationcode.findByAuthorizationcode").setParameter("authorizationcode", code);
        Authorizationcode authorizationCode = (Authorizationcode)query.getSingleResult();
        if (authorizationCode == null) {
            throw new WebApplicationException(404);
        }
        
        AccessToken at = new AccessToken();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
        Calendar now = Calendar.getInstance();
        Date date = now.getTime();
        logger.debug("Current date: " + formatter.format(date));
        MD5Generator generator = new MD5Generator();
        try {
            logger.debug("een waarde genereren");
            String encryptedId = generator.generateValue(code);
            at.setAccess_token(encryptedId);
        } catch (OAuthSystemException ex) {
            java.util.logging.Logger.getLogger(AuthorizationcodeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        at.setClientid(authorizationCode.getClientid());
        now.add(Calendar.YEAR, 1);
        Date future = now.getTime();
        logger.debug("Expire date: " + formatter.format(future));
        at.setCreationdate(date);
        at.setExpires_in(future);
        at.setOrganisation(authorizationCode.getOrganisation());
        at.setScope(authorizationCode.getScope());
        at.setUserid(authorizationCode.getUserid());
        em.getTransaction().begin();
        em.persist(at);
        em.getTransaction().commit();
        em.close();
        
        return at;
    }

    @Override
    protected EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("aut.hsleiden.nl");
        return emf.createEntityManager();
    }
    
}
