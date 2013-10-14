/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
@Table(name = "clients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c"),
    @NamedQuery(name = "Clients.findByClientid", query = "SELECT c FROM Clients c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "Clients.findByClientsecret", query = "SELECT c FROM Clients c WHERE c.clientsecret = :clientsecret"),
    @NamedQuery(name = "Clients.findByRedirecturi", query = "SELECT c FROM Clients c WHERE c.redirecturi = :redirecturi"),
    @NamedQuery(name = "Clients.findByCreationdate", query = "SELECT c FROM Clients c WHERE c.creationdate = :creationdate"),
    @NamedQuery(name = "Clients.findByExpires", query = "SELECT c FROM Clients c WHERE c.expires = :expires"),
    @NamedQuery(name = "Clients.findByUserid", query = "SELECT c FROM Clients c WHERE c.userid = :userid"),
    @NamedQuery(name = "Clients.findByCreationsdate", query = "SELECT c FROM Clients c WHERE c.creationsdate = :creationsdate")})
public class Client implements Serializable {
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
    

    public Client() {
    }

    public Client(String clientid) {
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
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
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
