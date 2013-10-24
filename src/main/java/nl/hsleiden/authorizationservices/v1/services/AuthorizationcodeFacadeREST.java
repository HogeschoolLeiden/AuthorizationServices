/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.v1.services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nl.hsleiden.authorizationservices.model.Authorizationcode;
import org.apache.log4j.Logger;

/**
 *
 * @author hl
 */
@Path("v1/authorizationcode")
public class AuthorizationcodeFacadeREST extends AbstractFacade<Authorizationcode> {
    private EntityManagerFactory emf;
    private Logger logger = Logger.getLogger(ClientFacadeREST.class.getName());

    public AuthorizationcodeFacadeREST() {
        super(Authorizationcode.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Authorizationcode entity) {
        super.create(entity);
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Authorizationcode find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Authorizationcode> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Authorizationcode> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
