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
 * @author Ruchira
 */
@Entity
@Table(name = "product_has_tax")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductHasTax.findAll", query = "SELECT p FROM ProductHasTax p"),
    @NamedQuery(name = "ProductHasTax.findById", query = "SELECT p FROM ProductHasTax p WHERE p.id = :id")})
public class ProductHasTax implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tax taxId;
    @JoinColumn(name = "product_product_id", referencedColumnName = "product_id")
    @ManyToOne(optional = false)
    private Product productProductId;

    public ProductHasTax() {
    }

    public ProductHasTax(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tax getTaxId() {
        return taxId;
    }

    public void setTaxId(Tax taxId) {
        this.taxId = taxId;
    }

    public Product getProductProductId() {
        return productProductId;
    }

    public void setProductProductId(Product productProductId) {
        this.productProductId = productProductId;
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
        if (!(object instanceof ProductHasTax)) {
            return false;
        }
        ProductHasTax other = (ProductHasTax) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.ProductHasTax[ id=" + id + " ]";
    }
    
}
