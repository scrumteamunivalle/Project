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
@Table(name = "contenedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contenedor.findAll", query = "SELECT c FROM Contenedor c")
    , @NamedQuery(name = "Contenedor.findById", query = "SELECT c FROM Contenedor c WHERE c.id = :id")
    , @NamedQuery(name = "Contenedor.findByNumeroarmas", query = "SELECT c FROM Contenedor c WHERE c.numeroarmas = :numeroarmas")
    , @NamedQuery(name = "Contenedor.findByTipodearmas", query = "SELECT c FROM Contenedor c WHERE c.tipodearmas = :tipodearmas")})
public class Contenedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Numero armas")
    private int numeroarmas;
    @Basic(optional = false)
    @Column(name = "Tipo de armas")
    private String tipodearmas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenedor")
    private List<ArmaIndividual> armaIndividualList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contenedor")
    private List<Explosivo> explosivoList;

    public Contenedor() {
    }

    public Contenedor(Integer id) {
        this.id = id;
    }

    public Contenedor(Integer id, int numeroarmas, String tipodearmas) {
        this.id = id;
        this.numeroarmas = numeroarmas;
        this.tipodearmas = tipodearmas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroarmas() {
        return numeroarmas;
    }

    public void setNumeroarmas(int numeroarmas) {
        this.numeroarmas = numeroarmas;
    }

    public String getTipodearmas() {
        return tipodearmas;
    }

    public void setTipodearmas(String tipodearmas) {
        this.tipodearmas = tipodearmas;
    }

    @XmlTransient
    public List<ArmaIndividual> getArmaIndividualList() {
        return armaIndividualList;
    }

    public void setArmaIndividualList(List<ArmaIndividual> armaIndividualList) {
        this.armaIndividualList = armaIndividualList;
    }

    @XmlTransient
    public List<Explosivo> getExplosivoList() {
        return explosivoList;
    }

    public void setExplosivoList(List<Explosivo> explosivoList) {
        this.explosivoList = explosivoList;
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
        if (!(object instanceof Contenedor)) {
            return false;
        }
        Contenedor other = (Contenedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Contenedor[ id=" + id + " ]";
    }
    
}
