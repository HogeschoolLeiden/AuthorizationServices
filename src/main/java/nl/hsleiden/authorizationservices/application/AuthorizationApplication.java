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

