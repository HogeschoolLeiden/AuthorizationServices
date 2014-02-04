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

/**
 *
 * @author hl
 */
@Entity
@Table(name = "authorizationcode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authorizationcode.findAll", query = "SELECT a FROM Authorizationcode a"),
    @NamedQuery(name = "Authorizationcode.findByAuthorizationcode", query = "SELECT a FROM Authorizationcode a WHERE a.authorizationcode = :authorizationcode"),
    @NamedQuery(name = "Authorizationcode.findByClientid", query = "SELECT a FROM Authorizationcode a WHERE a.clientid = :clientid"),
    @NamedQuery(name = "Authorizationcode.findByUserid", query = "SELECT a FROM Authorizationcode a WHERE a.userid = :userid"),
    @NamedQuery(name = "Authorizationcode.findByRedirecturi", query = "SELECT a FROM Authorizationcode a WHERE a.redirecturi = :redirecturi"),
    @NamedQuery(name = "Authorizationcode.findByExpires", query = "SELECT a FROM Authorizationcode a WHERE a.expires = :expires"),
    @NamedQuery(name = "Authorizationcode.findByScope", query = "SELECT a FROM Authorizationcode a WHERE a.scope = :scope"),
    @NamedQuery(name = "Authorizationcode.findByCreationdate", query = "SELECT a FROM Authorizationcode a WHERE a.creationdate = :creationdate")})
public class Authorizationcode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "authorizationcode")
    private String authorizationcode;
    @Size(max = 2147483647)
    @Column(name = "clientid")
    private String clientid;
    @Size(max = 2147483647)
    @Column(name = "userid")
    private String userid;
    @Column(name = "organisation")
    private String organisation;
    @Size(max = 2147483647)
    @Column(name = "redirecturi")
    private String redirecturi;
    @Column(name = "expires")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;
    @Size(max = 2147483647)
    @Column(name = "scope")
    private String scope;
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;
    @Column(name = "state")
    private String state;
    
    public Authorizationcode() {
    }

    public Authorizationcode(String authorizationcode) {
        this.authorizationcode = authorizationcode;
    }

    public String getAuthorizationcode() {
        return authorizationcode;
    }

    public void setAuthorizationcode(String authorizationcode) {
        this.authorizationcode = authorizationcode;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    
    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRedirecturi() {
        return redirecturi;
    }

    public void setRedirecturi(String redirecturi) {
        this.redirecturi = redirecturi;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authorizationcode != null ? authorizationcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authorizationcode)) {
            return false;
        }
        Authorizationcode other = (Authorizationcode) object;
        if ((this.authorizationcode == null && other.authorizationcode != null) || (this.authorizationcode != null && !this.authorizationcode.equals(other.authorizationcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.hsleiden.authorizationservices.model.Authorizationcode[ authorizationcode=" + authorizationcode + " ]";
    }
    
}
