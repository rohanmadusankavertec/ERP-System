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
@Table(name = "supplier_has_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupplierHasGroup.findAll", query = "SELECT s FROM SupplierHasGroup s"),
    @NamedQuery(name = "SupplierHasGroup.findById", query = "SELECT s FROM SupplierHasGroup s WHERE s.id = :id")})
public class SupplierHasGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    @ManyToOne(optional = false)
    private Supplier supplierId;
    @JoinColumn(name = "supplier_group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SupplierGroup supplierGroupId;

    public SupplierHasGroup() {
    }

    public SupplierHasGroup(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }

    public SupplierGroup getSupplierGroupId() {
        return supplierGroupId;
    }

    public void setSupplierGroupId(SupplierGroup supplierGroupId) {
        this.supplierGroupId = supplierGroupId;
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
        if (!(object instanceof SupplierHasGroup)) {
            return false;
        }
        SupplierHasGroup other = (SupplierHasGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.SupplierHasGroup[ id=" + id + " ]";
    }
    
}
