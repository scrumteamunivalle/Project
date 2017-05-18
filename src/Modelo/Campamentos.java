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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GekkoHKD
 */
@Entity
@Table(name = "campamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campamentos.findAll", query = "SELECT c FROM Campamentos c")
    , @NamedQuery(name = "Campamentos.findById", query = "SELECT c FROM Campamentos c WHERE c.id = :id")
    , @NamedQuery(name = "Campamentos.findByCantidadcombatientes", query = "SELECT c FROM Campamentos c WHERE c.cantidadcombatientes = :cantidadcombatientes")})
public class Campamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Cantidad combatientes")
    private int cantidadcombatientes;

    public Campamentos() {
    }

    public Campamentos(Integer id) {
        this.id = id;
    }

    public Campamentos(Integer id, int cantidadcombatientes) {
        this.id = id;
        this.cantidadcombatientes = cantidadcombatientes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidadcombatientes() {
        return cantidadcombatientes;
    }

    public void setCantidadcombatientes(int cantidadcombatientes) {
        this.cantidadcombatientes = cantidadcombatientes;
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
        if (!(object instanceof Campamentos)) {
            return false;
        }
        Campamentos other = (Campamentos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Campamentos[ id=" + id + " ]";
    }
    
}
