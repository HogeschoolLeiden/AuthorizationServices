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
package nl.hsleiden.authorizationservices.servlet.consent;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author hl
 */
public class AuthorizeAfterConsent extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AuthorizeAfterConsent.class.getName());
        logger.debug("In authorizeAfterConsent servlet");
        response.setContentType("text/html;charset=UTF-8");

        String clientId = request.getParameter("clientId");
        logger.debug("client id: " + clientId);
        String state = request.getParameter("state");
        logger.debug("state: " + state);
        String code = request.getParameter("code");
       
        Client c = ClientBuilder.newClient().register(JacksonFeature.class);
        WebTarget queryTarget = c.target("http://localhost:8080/AuthorizationServices/v1/authorize/").path(clientId);
        logger.debug("Deze url wordt aangeroepen: " + queryTarget.getUri().toASCIIString());

        Invocation.Builder invocationBuilder = queryTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response apiresponse = invocationBuilder.get();
        String result = apiresponse.readEntity(String.class);
        
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(result);
        String authorizationCode = jsonObject.getString("authorizationcode");
        logger.debug("Opgehaalde authorizationCode: " + authorizationCode);
        request.setAttribute("code", authorizationCode);
        String redirectUri = jsonObject.getString("redirecturi");
        logger.debug("redirect uri: " + redirectUri);
        request.getRequestDispatcher(redirectUri).forward(request, response);
     }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
