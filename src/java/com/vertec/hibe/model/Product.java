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
@Table(name = "product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productId = :productId"),
    @NamedQuery(name = "Product.findByProductCode", query = "SELECT p FROM Product p WHERE p.productCode = :productCode"),
    @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName"),
    @NamedQuery(name = "Product.findByProductDescription", query = "SELECT p FROM Product p WHERE p.productDescription = :productDescription"),
    @NamedQuery(name = "Product.findByReOrderLevel", query = "SELECT p FROM Product p WHERE p.reOrderLevel = :reOrderLevel"),
    @NamedQuery(name = "Product.findByIsAvailable", query = "SELECT p FROM Product p WHERE p.isAvailable = :isAvailable")})
public class Product implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Mrn> mrnCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productProductId")
    private Collection<PrnItem> prnItemCollection;

    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<BranchStock> branchStockCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productProductId")
    private Collection<Grn> grnCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productProductId")
    private Collection<PurchasingOrder> purchasingOrderCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "re_order_level")
    private Integer reOrderLevel;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @JoinColumn(name = "product_category_id", referencedColumnName = "product_category_id")
    @ManyToOne(optional = false)
    private ProductCategory productCategoryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<ProductMaster> productMasterCollection;

    public Product() {
    }

    public Product(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
public Product(Integer productId, String productCode, int reOrderLevel, boolean isAvailable) {
        this.productId = productId;
        this.productCode = productCode;
        this.reOrderLevel = reOrderLevel;
        this.isAvailable = isAvailable;
    }

    public Product(String productCode, String productName, String productDescription, int reOrderLevel, boolean isAvailable, ProductCategory productCategoryId,Company com) {
        this.productCode = productCode;
        this.productName = productName;
        this.productDescription = productDescription;
        this.reOrderLevel = reOrderLevel;
        this.isAvailable = isAvailable;
        this.productCategoryId = productCategoryId;
        this.companyId=com;
    }

    public Product(Integer productId, String productCode, String productName, String productDescription, int reOrderLevel) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.productDescription = productDescription;
        this.reOrderLevel = reOrderLevel;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(Integer reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public ProductCategory getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(ProductCategory productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @XmlTransient
    public Collection<ProductMaster> getProductMasterCollection() {
        return productMasterCollection;
    }

    public void setProductMasterCollection(Collection<ProductMaster> productMasterCollection) {
        this.productMasterCollection = productMasterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Product[ productId=" + productId + " ]";
    }

    @XmlTransient
    public Collection<PurchasingOrder> getPurchasingOrderCollection() {
        return purchasingOrderCollection;
    }

    public void setPurchasingOrderCollection(Collection<PurchasingOrder> purchasingOrderCollection) {
        this.purchasingOrderCollection = purchasingOrderCollection;
    }

    @XmlTransient
    public Collection<Grn> getGrnCollection() {
        return grnCollection;
    }

    public void setGrnCollection(Collection<Grn> grnCollection) {
        this.grnCollection = grnCollection;
    }

    @XmlTransient
    public Collection<BranchStock> getBranchStockCollection() {
        return branchStockCollection;
    }

    public void setBranchStockCollection(Collection<BranchStock> branchStockCollection) {
        this.branchStockCollection = branchStockCollection;
    }

    @XmlTransient
    public Collection<PrnItem> getPrnItemCollection() {
        return prnItemCollection;
    }

    public void setPrnItemCollection(Collection<PrnItem> prnItemCollection) {
        this.prnItemCollection = prnItemCollection;
    }

    @XmlTransient
    public Collection<Mrn> getMrnCollection() {
        return mrnCollection;
    }

    public void setMrnCollection(Collection<Mrn> mrnCollection) {
        this.mrnCollection = mrnCollection;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    
}
