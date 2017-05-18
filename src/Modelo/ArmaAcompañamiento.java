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
@Table(name = "arma acompa\u00f1amiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArmaAcompa\u00f1amiento.findAll", query = "SELECT a FROM ArmaAcompa\u00f1amiento a")
    , @NamedQuery(name = "ArmaAcompa\u00f1amiento.findById", query = "SELECT a FROM ArmaAcompa\u00f1amiento a WHERE a.id = :id")
    , @NamedQuery(name = "ArmaAcompa\u00f1amiento.findByContenedor", query = "SELECT a FROM ArmaAcompa\u00f1amiento a WHERE a.contenedor = :contenedor")})
public class ArmaAcompañamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Contenedor")
    private int contenedor;
    @JoinColumn(name = "Bloque", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Bloque bloque;

    public ArmaAcompañamiento() {
    }

    public ArmaAcompañamiento(Integer id) {
        this.id = id;
    }

    public ArmaAcompañamiento(Integer id, int contenedor) {
        this.id = id;
        this.contenedor = contenedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getContenedor() {
        return contenedor;
    }

    public void setContenedor(int contenedor) {
        this.contenedor = contenedor;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
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
        if (!(object instanceof ArmaAcompañamiento)) {
            return false;
        }
        ArmaAcompañamiento other = (ArmaAcompañamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.ArmaAcompa\u00f1amiento[ id=" + id + " ]";
    }
    
}
