/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GekkoHKD
 */
@Entity
@Table(name = "integrante_farcep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntegranteFarcep.findAll", query = "SELECT i FROM IntegranteFarcep i")
    , @NamedQuery(name = "IntegranteFarcep.findByCedula", query = "SELECT i FROM IntegranteFarcep i WHERE i.cedula = :cedula")
    , @NamedQuery(name = "IntegranteFarcep.findBySalidas", query = "SELECT i FROM IntegranteFarcep i WHERE i.salidas = :salidas")
    , @NamedQuery(name = "IntegranteFarcep.findByNombre", query = "SELECT i FROM IntegranteFarcep i WHERE i.nombre = :nombre")
    , @NamedQuery(name = "IntegranteFarcep.findByAlias", query = "SELECT i FROM IntegranteFarcep i WHERE i.alias = :alias")
    , @NamedQuery(name = "IntegranteFarcep.findByFechanacimiento", query = "SELECT i FROM IntegranteFarcep i WHERE i.fechanacimiento = :fechanacimiento")
    , @NamedQuery(name = "IntegranteFarcep.findByFechaingreso", query = "SELECT i FROM IntegranteFarcep i WHERE i.fechaingreso = :fechaingreso")
    , @NamedQuery(name = "IntegranteFarcep.findByRango", query = "SELECT i FROM IntegranteFarcep i WHERE i.rango = :rango")
    , @NamedQuery(name = "IntegranteFarcep.findByEdad", query = "SELECT i FROM IntegranteFarcep i WHERE i.edad = :edad")})
public class IntegranteFarcep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cedula")
    private Integer cedula;
    @Basic(optional = false)
    @Column(name = "Salidas")
    private String salidas;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Alias")
    private String alias;
    @Basic(optional = false)
    @Column(name = "Fecha nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Basic(optional = false)
    @Column(name = "Fecha ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Basic(optional = false)
    @Column(name = "Rango")
    private String rango;
    @Basic(optional = false)
    @Column(name = "Edad")
    private int edad;
    @JoinColumn(name = "Zona veredal", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private ZonaVeredal zonaveredal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cedula")
    private List<Pqr> pqrList;

    public IntegranteFarcep() {
    }

    public IntegranteFarcep(Integer cedula) {
        this.cedula = cedula;
    }

    public IntegranteFarcep(Integer cedula, String salidas, String nombre, String alias, Date fechanacimiento, Date fechaingreso, String rango, int edad) {
        this.cedula = cedula;
        this.salidas = salidas;
        this.nombre = nombre;
        this.alias = alias;
        this.fechanacimiento = fechanacimiento;
        this.fechaingreso = fechaingreso;
        this.rango = rango;
        this.edad = edad;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getSalidas() {
        return salidas;
    }

    public void setSalidas(String salidas) {
        this.salidas = salidas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public ZonaVeredal getZonaveredal() {
        return zonaveredal;
    }

    public void setZonaveredal(ZonaVeredal zonaveredal) {
        this.zonaveredal = zonaveredal;
    }

    @XmlTransient
    public List<Pqr> getPqrList() {
        return pqrList;
    }

    public void setPqrList(List<Pqr> pqrList) {
        this.pqrList = pqrList;
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
        if (!(object instanceof IntegranteFarcep)) {
            return false;
        }
        IntegranteFarcep other = (IntegranteFarcep) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.IntegranteFarcep[ cedula=" + cedula + " ]";
    }
    
}
