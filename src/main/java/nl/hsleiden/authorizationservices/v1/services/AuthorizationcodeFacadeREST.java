/*
 * Copyright 2014 Hogeschool Leiden.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.hsleiden.authorizationservices.v1.services;

import java.net.URI;
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
import javax.ws.rs.core.UriBuilder;
import nl.hsleiden.authorizationservices.model.Authorizationcode;
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
    public Response find(@QueryParam("clientid") String clientid, @QueryParam("state") String state, @QueryParam("scope") String scope, 
            @QueryParam("redirecturi") String redirecturi) {
        
        logger.debug("ClientId: " + clientid );
        validateClient(clientid);
        Authorizationcode code = new Authorizationcode();
        String uid = "";
        Calendar now = Calendar.getInstance();
        code.setClientid(clientid);
        code.setUserid(uid);
        code.setOrganisation("hsleiden.nl");
        code.setCreationdate(now.getTime());
        code.setState(state);
               
        MD5Generator generator = new MD5Generator();
        try {
            String encryptedId = generator.generateValue(uid + now.getTime());
            code.setAuthorizationcode(encryptedId);
        } catch (OAuthSystemException ex) {
            java.util.logging.Logger.getLogger(AuthorizationcodeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        now.add(Calendar.MINUTE, 5);
        code.setExpires(now.getTime());
        //code.setRedirecturi("http://localhost:8080/WebapisClient/Employee");
        code.setRedirecturi(redirecturi);
        code.setScope("");
        
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(code);
        em.getTransaction().commit();
        em.close();
        
        URI uri = URI.create(code.getRedirecturi());
        URI newUri = UriBuilder.fromUri(uri).queryParam("state", code.getState()).queryParam("code", code.getAuthorizationcode()).build();
        logger.debug("This is where we're going: " + newUri);
        return Response.seeOther(newUri).build();     
    }

    private boolean validateClient(String clientId){
        boolean valid = false;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("OauthClient.findByClientid").setParameter("clientid", clientId);
        try {
            query.getSingleResult();
            logger.debug("A client found.");
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
