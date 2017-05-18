/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GekkoHKD
 */
@Entity
@Table(name = "bloque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bloque.findAll", query = "SELECT b FROM Bloque b")
    , @NamedQuery(name = "Bloque.findById", query = "SELECT b FROM Bloque b WHERE b.id = :id")
    , @NamedQuery(name = "Bloque.findByCantidadarmas", query = "SELECT b FROM Bloque b WHERE b.cantidadarmas = :cantidadarmas")})
public class Bloque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Cantidad armas")
    private int cantidadarmas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloque")
    private List<ArmaAcompañamiento> armaAcompañamientoList;

    public Bloque() {
    }

    public Bloque(Integer id) {
        this.id = id;
    }

    public Bloque(Integer id, int cantidadarmas) {
        this.id = id;
        this.cantidadarmas = cantidadarmas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidadarmas() {
        return cantidadarmas;
    }

    public void setCantidadarmas(int cantidadarmas) {
        this.cantidadarmas = cantidadarmas;
    }

    @XmlTransient
    public List<ArmaAcompañamiento> getArmaAcompañamientoList() {
        return armaAcompañamientoList;
    }

    public void setArmaAcompañamientoList(List<ArmaAcompañamiento> armaAcompañamientoList) {
        this.armaAcompañamientoList = armaAcompañamientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloque)) {
            return false;
        }
        Bloque other = (Bloque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Bloque[ id=" + id + " ]";
    }
    
}
