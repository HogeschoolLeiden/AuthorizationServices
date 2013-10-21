/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hsleiden.authorizationservices.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author hl
 */
public class DateFormatterAdapter extends XmlAdapter<String, Date>{

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
      @Override
      public String marshal(Date v) {
          return dateFormat.format(v);
      }
      @Override
      public Date unmarshal(String v) {
          try {
                return dateFormat.parse(v);
            } catch (ParseException e) {
                
                throw new WebApplicationException();
            }
      }   
}
