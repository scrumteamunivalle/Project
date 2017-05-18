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
@Table(name = "actividades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividades.findAll", query = "SELECT a FROM Actividades a")
    , @NamedQuery(name = "Actividades.findByC\u00f3digo", query = "SELECT a FROM Actividades a WHERE a.c\u00f3digo = :c\u00f3digo")
    , @NamedQuery(name = "Actividades.findByNombre", query = "SELECT a FROM Actividades a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Actividades.findByParticipantes", query = "SELECT a FROM Actividades a WHERE a.participantes = :participantes")
    , @NamedQuery(name = "Actividades.findByFecha", query = "SELECT a FROM Actividades a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "Actividades.findByDetalles", query = "SELECT a FROM Actividades a WHERE a.detalles = :detalles")})
public class Actividades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "C\u00f3digo")
    private Integer código;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Participantes")
    private int participantes;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "Detalles")
    private String detalles;

    public Actividades() {
    }

    public Actividades(Integer código) {
        this.código = código;
    }

    public Actividades(Integer código, String nombre, int participantes, Date fecha, String detalles) {
        this.código = código;
        this.nombre = nombre;
        this.participantes = participantes;
        this.fecha = fecha;
        this.detalles = detalles;
    }

    public Integer getCódigo() {
        return código;
    }

    public void setCódigo(Integer código) {
        this.código = código;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getParticipantes() {
        return participantes;
    }

    public void setParticipantes(int participantes) {
        this.participantes = participantes;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (código != null ? código.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividades)) {
            return false;
        }
        Actividades other = (Actividades) object;
        if ((this.código == null && other.código != null) || (this.código != null && !this.código.equals(other.código))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Actividades[ c\u00f3digo=" + código + " ]";
    }
    
}
