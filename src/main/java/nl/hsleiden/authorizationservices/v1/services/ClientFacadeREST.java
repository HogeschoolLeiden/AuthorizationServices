/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.v1.services;

import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import nl.hsleiden.authorizationservices.model.Client;
import org.apache.log4j.Logger;

/**
 *
 * @author hl
 */
@Path("v1/client")
public class ClientFacadeREST extends AbstractFacade<Client> {
    
    private EntityManagerFactory emf;
    private Logger logger = Logger.getLogger(ClientFacadeREST.class.getName());

    public ClientFacadeREST() {
        super(Client.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Client entity) {
        super.create(entity);
    }

    @GET
    @Path("/register")
    @Produces({"application/xml", "application/json"})
    public Client create(@QueryParam("id") String userId, @QueryParam("uri") String reDirectURI)  {
        
        
        //MD5Generator generator = new MD5Generator();
        
        Client c = new Client(userId);
        c.setClientsecret(userId);
        c.setUserid(userId);
        c.setCreationdate(Calendar.getInstance().getTime());
        c.setExpires(null);
        c.setRedirecturi(userId);
        logger.debug("userid: " + userId);
        logger.debug("generated clientid: " + c.getClientid());
        EntityManager em = getEntityManager();
        em.persist(c);
        
        return c;
        //super.create(entity);
    }
    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Client entity) {
        super.edit(entity);
    }

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") String id) {
//        super.remove(super.find(id));
//    }

//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public Client find(@PathParam("id") String id) {
//        return super.find(id);
//    }
//
//    @GET
//    @Override
//    @Produces({"application/xml", "application/json"})
//    public List<Client> findAll() {
//        return super.findAll();
//    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Clients> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }

    @Override
    protected EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("aut.hsleiden.nl");
        return emf.createEntityManager();
    }
    
}
