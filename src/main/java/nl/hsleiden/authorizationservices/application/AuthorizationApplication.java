/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.application;

import nl.hsleiden.authorizationservices.util.ObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author hl
 */
public class AuthorizationApplication extends ResourceConfig {
    public AuthorizationApplication() {
        packages("nl.hsleiden.authorizationservices.v1.services");
        packages("org.glassfish.jersey.examples.jackson");
        register(ObjectMapperProvider.class);
        register(JacksonFeature.class);
    }
}    

