/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "gin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gin.findAll", query = "SELECT g FROM Gin g"),
    @NamedQuery(name = "Gin.findById", query = "SELECT g FROM Gin g WHERE g.id = :id"),
    @NamedQuery(name = "Gin.findByQty", query = "SELECT g FROM Gin g WHERE g.qty = :qty"),
    @NamedQuery(name = "Gin.findByTotal", query = "SELECT g FROM Gin g WHERE g.total = :total")})
public class Gin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @JoinColumn(name = "gin_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GinInfo ginInfoId;
    @JoinColumn(name = "bpm_id", referencedColumnName = "bpm_id")
    @ManyToOne(optional = false)
    private BranchProductmaster bpmId;

    public Gin() {
    }

    public Gin(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public GinInfo getGinInfoId() {
        return ginInfoId;
    }

    public void setGinInfoId(GinInfo ginInfoId) {
        this.ginInfoId = ginInfoId;
    }

    public BranchProductmaster getBpmId() {
        return bpmId;
    }

    public void setBpmId(BranchProductmaster bpmId) {
        this.bpmId = bpmId;
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
        if (!(object instanceof Gin)) {
            return false;
        }
        Gin other = (Gin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Gin[ id=" + id + " ]";
    }
    
}
