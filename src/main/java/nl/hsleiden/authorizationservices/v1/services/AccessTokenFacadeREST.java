/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.v1.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import nl.hsleiden.authorizationservices.model.AccessToken;
import org.apache.log4j.Logger;
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

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(AccessToken entity) {
        super.create(entity);
    }

    

    @GET
    @Path("/tokeninfo/{id}")
    @Produces({"application/json"})
    public AccessToken find(@PathParam("id") String id) {
        AccessToken at = super.find(id);
        
        boolean b = false;
        Date date = at.getExpires();
        Date d = Calendar.getInstance().getTime();

        if (date.compareTo(d) <= 0) {
            b = true;
        }
        
        if (b) {
            return at;
        } else {
            throw new WebApplicationException("{\"error\":\"invalid_token\"}", 400);
        }
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<AccessToken> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<AccessToken> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

   @Override
    protected EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("aut.hsleiden.nl");
        return emf.createEntityManager();
    }
    
}
