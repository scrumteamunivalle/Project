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
@Table(name = "pqr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pqr.findAll", query = "SELECT p FROM Pqr p")
    , @NamedQuery(name = "Pqr.findById", query = "SELECT p FROM Pqr p WHERE p.id = :id")
    , @NamedQuery(name = "Pqr.findByTipoDeSolicitud", query = "SELECT p FROM Pqr p WHERE p.tipoDeSolicitud = :tipoDeSolicitud")
    , @NamedQuery(name = "Pqr.findByDescripcion", query = "SELECT p FROM Pqr p WHERE p.descripcion = :descripcion")})
public class Pqr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TipoDeSolicitud")
    private String tipoDeSolicitud;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @JoinColumn(name = "Cedula", referencedColumnName = "Cedula")
    @ManyToOne(optional = false)
    private IntegranteFarcep cedula;

    public Pqr() {
    }

    public Pqr(Integer id) {
        this.id = id;
    }

    public Pqr(Integer id, String tipoDeSolicitud, String descripcion) {
        this.id = id;
        this.tipoDeSolicitud = tipoDeSolicitud;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDeSolicitud() {
        return tipoDeSolicitud;
    }

    public void setTipoDeSolicitud(String tipoDeSolicitud) {
        this.tipoDeSolicitud = tipoDeSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public IntegranteFarcep getCedula() {
        return cedula;
    }

    public void setCedula(IntegranteFarcep cedula) {
        this.cedula = cedula;
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
        if (!(object instanceof Pqr)) {
            return false;
        }
        Pqr other = (Pqr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Pqr[ id=" + id + " ]";
    }
    
}
