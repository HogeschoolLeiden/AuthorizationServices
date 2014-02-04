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
@Table(name = "access_tokens")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessToken.findAll", query = "SELECT a FROM AccessToken a"),
    @NamedQuery(name = "AccessToken.findByAccesstoken", query = "SELECT a FROM AccessToken a WHERE a.access_token = :access_token"),
    @NamedQuery(name = "AccessToken.findByClientid", query = "SELECT a FROM AccessToken a WHERE a.clientid = :clientid"),
    @NamedQuery(name = "AccessToken.findByUserid", query = "SELECT a FROM AccessToken a WHERE a.userid = :userid"),
    @NamedQuery(name = "AccessToken.findByOrganisation", query = "SELECT a FROM AccessToken a WHERE a.organisation = :organisation"),
    @NamedQuery(name = "AccessToken.findByExpires", query = "SELECT a FROM AccessToken a WHERE a.expires_in = :expires_in"),
    @NamedQuery(name = "AccessToken.findByScope", query = "SELECT a FROM AccessToken a WHERE a.scope = :scope"),
    @NamedQuery(name = "AccessToken.findByCreationdate", query = "SELECT a FROM AccessToken a WHERE a.creationdate = :creationdate")})
public class AccessToken implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "accesstoken")
    private String access_token;
    @Size(max = 2147483647)
    @Column(name = "clientid")
    private String clientid;
    @Size(max = 2147483647)
    @Column(name = "userid")
    private String userid;
    @Size(max = 2147483647)
    @Column(name = "organisation")
    private String organisation;
    @Column(name = "expires")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires_in;
    @Size(max = 2147483647)
    @Column(name = "scope")
    private String scope;
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;

    public AccessToken() {
    }

    public AccessToken(String accesstoken) {
        this.access_token = accesstoken;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public Date getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Date expires_in) {
        this.expires_in = expires_in;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (access_token != null ? access_token.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessToken)) {
            return false;
        }
        AccessToken other = (AccessToken) object;
        if ((this.access_token == null && other.access_token != null) || (this.access_token != null && !this.access_token.equals(other.access_token))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nl.hsleiden.authorizationservices.model.AccessTokens[ accesstoken=" + access_token + " ]";
    }
    
}
