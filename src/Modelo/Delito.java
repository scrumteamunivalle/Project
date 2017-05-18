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
@Table(name = "delito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delito.findAll", query = "SELECT d FROM Delito d")
    , @NamedQuery(name = "Delito.findById", query = "SELECT d FROM Delito d WHERE d.id = :id")
    , @NamedQuery(name = "Delito.findByFechadelapena", query = "SELECT d FROM Delito d WHERE d.fechadelapena = :fechadelapena")
    , @NamedQuery(name = "Delito.findByA\u00f1osdepena", query = "SELECT d FROM Delito d WHERE d.a\u00f1osdepena = :a\u00f1osdepena")
    , @NamedQuery(name = "Delito.findByCondenado", query = "SELECT d FROM Delito d WHERE d.condenado = :condenado")
    , @NamedQuery(name = "Delito.findByDescripcion", query = "SELECT d FROM Delito d WHERE d.descripcion = :descripcion")})
public class Delito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Fecha de la pena")
    @Temporal(TemporalType.DATE)
    private Date fechadelapena;
    @Basic(optional = false)
    @Column(name = "A\u00f1os de pena")
    private int añosdepena;
    @Basic(optional = false)
    @Column(name = "Condenado")
    private int condenado;
    @Column(name = "Descripcion")
    private String descripcion;

    public Delito() {
    }

    public Delito(Integer id) {
        this.id = id;
    }

    public Delito(Integer id, Date fechadelapena, int añosdepena, int condenado) {
        this.id = id;
        this.fechadelapena = fechadelapena;
        this.añosdepena = añosdepena;
        this.condenado = condenado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechadelapena() {
        return fechadelapena;
    }

    public void setFechadelapena(Date fechadelapena) {
        this.fechadelapena = fechadelapena;
    }

    public int getAñosdepena() {
        return añosdepena;
    }

    public void setAñosdepena(int añosdepena) {
        this.añosdepena = añosdepena;
    }

    public int getCondenado() {
        return condenado;
    }

    public void setCondenado(int condenado) {
        this.condenado = condenado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Delito)) {
            return false;
        }
        Delito other = (Delito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Delito[ id=" + id + " ]";
    }
    
}
