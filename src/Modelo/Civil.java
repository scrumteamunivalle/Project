/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GekkoHKD
 */
@Entity
@Table(name = "civil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Civil.findAll", query = "SELECT c FROM Civil c")
    , @NamedQuery(name = "Civil.findByCedula", query = "SELECT c FROM Civil c WHERE c.cedula = :cedula")
    , @NamedQuery(name = "Civil.findByNombre", query = "SELECT c FROM Civil c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Civil.findByFechadeingreso", query = "SELECT c FROM Civil c WHERE c.fechadeingreso = :fechadeingreso")
    , @NamedQuery(name = "Civil.findByMotivoingreso", query = "SELECT c FROM Civil c WHERE c.motivoingreso = :motivoingreso")})
public class Civil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cedula")
    private Integer cedula;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Fecha de ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechadeingreso;
    @Basic(optional = false)
    @Column(name = "Motivo ingreso")
    private String motivoingreso;
    @JoinColumn(name = "Zona", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private ZonaVeredal zona;

    public Civil() {
    }

    public Civil(Integer cedula) {
        this.cedula = cedula;
    }

    public Civil(Integer cedula, String nombre, Date fechadeingreso, String motivoingreso) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechadeingreso = fechadeingreso;
        this.motivoingreso = motivoingreso;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechadeingreso() {
        return fechadeingreso;
    }

    public void setFechadeingreso(Date fechadeingreso) {
        this.fechadeingreso = fechadeingreso;
    }

    public String getMotivoingreso() {
        return motivoingreso;
    }

    public void setMotivoingreso(String motivoingreso) {
        this.motivoingreso = motivoingreso;
    }

    public ZonaVeredal getZona() {
        return zona;
    }

    public void setZona(ZonaVeredal zona) {
        this.zona = zona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Civil)) {
            return false;
        }
        Civil other = (Civil) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Civil[ cedula=" + cedula + " ]";
    }
    
}
