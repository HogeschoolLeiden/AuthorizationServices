/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.v1.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
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
        Date date = at.getExpires();
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

    @GET
    @Produces({"application/json"})
    public AccessToken find(@QueryParam("authorizationcode") String authorizationcode) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Authorizationcode.findByAuthorizationcode").setParameter("authorizationcode", authorizationcode);
        Authorizationcode code = (Authorizationcode)query.getSingleResult();
        if (code == null) {
            throw new WebApplicationException(404);
        }
        
        AccessToken at = new AccessToken();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
        Calendar now = Calendar.getInstance();
        Date date = now.getTime();
        logger.debug("Current date: " + formatter.format(date));
        MD5Generator generator = new MD5Generator();
        try {
            String encryptedId = generator.generateValue(code.getUserid() + date);
            at.setAccesstoken(encryptedId);
        } catch (OAuthSystemException ex) {
            java.util.logging.Logger.getLogger(AuthorizationcodeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        at.setClientid(code.getClientid());
        now.add(Calendar.YEAR, 1);
        Date future = now.getTime();
        logger.debug("Expire date: " + formatter.format(future));
        at.setCreationdate(date);
        at.setExpires(future);
        at.setOrganisation(code.getOrganisation());
        at.setScope(code.getScope());
        at.setUserid(code.getUserid());
        em.getTransaction().begin();
        em.persist(at);
        em.getTransaction().commit();
        em.close();
        at.setClientid(null);
        at.setUserid(null);
        at.setOrganisation(null);
        return at;
    }

    @Override
    protected EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("aut.hsleiden.nl");
        return emf.createEntityManager();
    }
    
}
