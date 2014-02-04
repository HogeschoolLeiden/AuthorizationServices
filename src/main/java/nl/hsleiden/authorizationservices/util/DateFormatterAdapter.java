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
