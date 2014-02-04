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
import java.io.PrintWriter;
import java.net.URI;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author hl
 */
public class Consent extends HttpServlet {

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
        
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Consent.class.getName());
        logger.debug("In consent servlet");
        
        response.setContentType("text/html;charset=UTF-8");
        String clientId = request.getParameter("client_id");
        String state = request.getParameter("state");
        String code = request.getParameter("code");
        String redirecturi = request.getParameter("redirecturi");
        
        logger.debug("clientId: " + clientId);
        logger.debug("state: " + state);
        logger.debug("redirecturi: " + redirecturi);
        logger.debug("consent: " + request.getParameter("consent"));
        
        if (request.getParameter("consent") != null && request.getParameter("consent").trim().length() > 0 && request.getParameter("consent").equals("agree")) {
            URI uri = URI.create("http://localhost:8080/AuthorizationServices/v1/authorize");
            URI newUri = UriBuilder.fromUri(uri).queryParam("clientid", clientId).queryParam("state", state).queryParam("redirecturi", redirecturi).build();
            logger.debug("De uri: " + newUri);
            response.sendRedirect(newUri.toString());
        }
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consent</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Consent at " + request.getContextPath() + "</h1>");
            out.println("Staat u WebapisClient toe gebruik te maken van de gegevens in het IFW?");
            out.println("<form action=\"Consent\" method=POST>");
            out.println("<input type=text name=\"client_id\" value="+ clientId +">" );            
            out.println("<input type=text name=\"state\" value="+ state +">" );
            out.println("<input type=text name=\"redirecturi\" value="+ redirecturi +">" );
            out.println("<input type=hidden name=\"consent\" value=\"agree\">" );
            
            out.println("<input type=submit>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
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
