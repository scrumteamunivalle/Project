/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GekkoHKD
 */
@Entity
@Table(name = "explosivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Explosivo.findAll", query = "SELECT e FROM Explosivo e")
    , @NamedQuery(name = "Explosivo.findById", query = "SELECT e FROM Explosivo e WHERE e.id = :id")
    , @NamedQuery(name = "Explosivo.findByUbicacion", query = "SELECT e FROM Explosivo e WHERE e.ubicacion = :ubicacion")})
public class Explosivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Ubicacion")
    private Integer ubicacion;
    @JoinColumn(name = "Contenedor", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Contenedor contenedor;

    public Explosivo() {
    }

    public Explosivo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Contenedor getContenedor() {
        return contenedor;
    }

    public void setContenedor(Contenedor contenedor) {
        this.contenedor = contenedor;
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
        if (!(object instanceof Explosivo)) {
            return false;
        }
        Explosivo other = (Explosivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Explosivo[ id=" + id + " ]";
    }
    
}
