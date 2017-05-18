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
@Table(name = "zona_veredal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZonaVeredal.findAll", query = "SELECT z FROM ZonaVeredal z")
    , @NamedQuery(name = "ZonaVeredal.findById", query = "SELECT z FROM ZonaVeredal z WHERE z.id = :id")
    , @NamedQuery(name = "ZonaVeredal.findByNombre", query = "SELECT z FROM ZonaVeredal z WHERE z.nombre = :nombre")
    , @NamedQuery(name = "ZonaVeredal.findByZonageograica", query = "SELECT z FROM ZonaVeredal z WHERE z.zonageograica = :zonageograica")
    , @NamedQuery(name = "ZonaVeredal.findByTipo", query = "SELECT z FROM ZonaVeredal z WHERE z.tipo = :tipo")
    , @NamedQuery(name = "ZonaVeredal.findByCantidadcampamentos", query = "SELECT z FROM ZonaVeredal z WHERE z.cantidadcampamentos = :cantidadcampamentos")})
public class ZonaVeredal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Zona geograica")
    private String zonageograica;
    @Basic(optional = false)
    @Column(name = "Tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "Cantidad campamentos")
    private int cantidadcampamentos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zonaveredal")
    private List<IntegranteFarcep> integranteFarcepList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zona")
    private List<Civil> civilList;

    public ZonaVeredal() {
    }

    public ZonaVeredal(Integer id) {
        this.id = id;
    }

    public ZonaVeredal(Integer id, String nombre, String zonageograica, String tipo, int cantidadcampamentos) {
        this.id = id;
        this.nombre = nombre;
        this.zonageograica = zonageograica;
        this.tipo = tipo;
        this.cantidadcampamentos = cantidadcampamentos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getZonageograica() {
        return zonageograica;
    }

    public void setZonageograica(String zonageograica) {
        this.zonageograica = zonageograica;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidadcampamentos() {
        return cantidadcampamentos;
    }

    public void setCantidadcampamentos(int cantidadcampamentos) {
        this.cantidadcampamentos = cantidadcampamentos;
    }

    @XmlTransient
    public List<IntegranteFarcep> getIntegranteFarcepList() {
        return integranteFarcepList;
    }

    public void setIntegranteFarcepList(List<IntegranteFarcep> integranteFarcepList) {
        this.integranteFarcepList = integranteFarcepList;
    }

    @XmlTransient
    public List<Civil> getCivilList() {
        return civilList;
    }

    public void setCivilList(List<Civil> civilList) {
        this.civilList = civilList;
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
        if (!(object instanceof ZonaVeredal)) {
            return false;
        }
        ZonaVeredal other = (ZonaVeredal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.ZonaVeredal[ id=" + id + " ]";
    }
    
}
