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
package nl.hsleiden.authorizationservices.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import nl.hsleiden.authorizationservices.util.DateFormatterAdapter;

/**
 *
 * @author hl
 */
@Entity
@Table(name = "clients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OauthClient.findAll", query = "SELECT c FROM OauthClient c"),
    @NamedQuery(name = "OauthClient.findByClientid", query = "SELECT c FROM OauthClient c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "OauthClient.findByClientsecret", query = "SELECT c FROM OauthClient c WHERE c.clientsecret = :clientsecret"),
    @NamedQuery(name = "OauthClient.findByRedirecturi", query = "SELECT c FROM OauthClient c WHERE c.redirecturi = :redirecturi"),
    @NamedQuery(name = "OauthClient.findByCreationdate", query = "SELECT c FROM OauthClient c WHERE c.creationdate = :creationdate"),
    @NamedQuery(name = "OauthClient.findByExpires", query = "SELECT c FROM OauthClient c WHERE c.expires = :expires"),
    @NamedQuery(name = "OauthClient.findByUserid", query = "SELECT c FROM OauthClient c WHERE c.userid = :userid"),
    })
public class OauthClient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "clientid")
    private String clientid;
    @Size(max = 2147483647)
    @Column(name = "clientsecret")
    private String clientsecret;
    @Size(max = 2147483647)
    @Column(name = "redirecturi")
    private String redirecturi;
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;
    @Column(name = "expires")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;
    @Size(max = 2147483647)
    @Column(name = "userid")
    private String userid;
    

    public OauthClient() {
    }

    public OauthClient(String clientid) {
        this.clientid = clientid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientsecret() {
        return clientsecret;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public String getRedirecturi() {
        return redirecturi;
    }

    public void setRedirecturi(String redirecturi) {
        this.redirecturi = redirecturi;
    }

    @XmlJavaTypeAdapter( DateFormatterAdapter.class)
    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientid != null ? clientid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OauthClient)) {
            return false;
        }
        OauthClient other = (OauthClient) object;
        if ((this.clientid == null && other.clientid != null) || (this.clientid != null && !this.clientid.equals(other.clientid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.hsleiden.authorizationservices.model.Clients[ clientid=" + clientid + " ]";
    }
    
}
