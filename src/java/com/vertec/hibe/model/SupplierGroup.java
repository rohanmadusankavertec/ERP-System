/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "supplier_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupplierGroup.findAll", query = "SELECT s FROM SupplierGroup s"),
    @NamedQuery(name = "SupplierGroup.findById", query = "SELECT s FROM SupplierGroup s WHERE s.id = :id"),
    @NamedQuery(name = "SupplierGroup.findByName", query = "SELECT s FROM SupplierGroup s WHERE s.name = :name")})
public class SupplierGroup implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierGroupId")
    private Collection<SupplierHasGroup> supplierHasGroupCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;

    public SupplierGroup() {
    }

    public SupplierGroup(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof SupplierGroup)) {
            return false;
        }
        SupplierGroup other = (SupplierGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.SupplierGroup[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<SupplierHasGroup> getSupplierHasGroupCollection() {
        return supplierHasGroupCollection;
    }

    public void setSupplierHasGroupCollection(Collection<SupplierHasGroup> supplierHasGroupCollection) {
        this.supplierHasGroupCollection = supplierHasGroupCollection;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
    
}
