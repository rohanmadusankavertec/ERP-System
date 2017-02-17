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
@Table(name = "product_category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductCategory.findAll", query = "SELECT p FROM ProductCategory p"),
    @NamedQuery(name = "ProductCategory.findByProductCategoryId", query = "SELECT p FROM ProductCategory p WHERE p.productCategoryId = :productCategoryId"),
    @NamedQuery(name = "ProductCategory.findByProductCategoryName", query = "SELECT p FROM ProductCategory p WHERE p.productCategoryName = :productCategoryName"),
    @NamedQuery(name = "ProductCategory.findByDescription", query = "SELECT p FROM ProductCategory p WHERE p.description = :description")})
public class ProductCategory implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productCategoryId")
    private Collection<Product> productCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_category_id")
    private Integer productCategoryId;
    @Column(name = "product_category_name")
    private String productCategoryName;
    @Column(name = "description")
    private String description;

    public ProductCategory() {
    }

    public ProductCategory(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getDescription() {
        return description;
    }
public ProductCategory(String productCategoryName, String description,Company company) {
        this.productCategoryName = productCategoryName;
        this.description = description;
        this.companyId=company;
    }

    public ProductCategory(Integer productCategoryId, String productCategoryName, String description) {
        this.productCategoryId = productCategoryId;
        this.productCategoryName = productCategoryName;
        this.description = description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productCategoryId != null ? productCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCategory)) {
            return false;
        }
        ProductCategory other = (ProductCategory) object;
        if ((this.productCategoryId == null && other.productCategoryId != null) || (this.productCategoryId != null && !this.productCategoryId.equals(other.productCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.ProductCategory[ productCategoryId=" + productCategoryId + " ]";
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
    
}
