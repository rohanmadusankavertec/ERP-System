/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "Customer.findByCustomerName", query = "SELECT c FROM Customer c WHERE c.customerName = :customerName"),
    @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"),
    @NamedQuery(name = "Customer.findByHotline", query = "SELECT c FROM Customer c WHERE c.hotline = :hotline"),
    @NamedQuery(name = "Customer.findByOfficeNo", query = "SELECT c FROM Customer c WHERE c.officeNo = :officeNo"),
    @NamedQuery(name = "Customer.findByFaxNo", query = "SELECT c FROM Customer c WHERE c.faxNo = :faxNo"),
    @NamedQuery(name = "Customer.findByContactPerson", query = "SELECT c FROM Customer c WHERE c.contactPerson = :contactPerson"),
    @NamedQuery(name = "Customer.findByContactPersonNo", query = "SELECT c FROM Customer c WHERE c.contactPersonNo = :contactPersonNo"),
    @NamedQuery(name = "Customer.findByContactPersonEmail", query = "SELECT c FROM Customer c WHERE c.contactPersonEmail = :contactPersonEmail"),
    @NamedQuery(name = "Customer.findByRegisterdDate", query = "SELECT c FROM Customer c WHERE c.registerdDate = :registerdDate"),
    @NamedQuery(name = "Customer.findByIsActive", query = "SELECT c FROM Customer c WHERE c.isActive = :isActive")})
public class Customer implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<CustomerHasGroup> customerHasGroupCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<Invoice> invoiceCollection;

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "address")
    private String address;
    @Column(name = "hotline")
    private String hotline;
    @Column(name = "office_no")
    private String officeNo;
    @Column(name = "fax_no")
    private String faxNo;
    @Column(name = "contact_person")
    private String contactPerson;
    @Column(name = "contact_person_no")
    private String contactPersonNo;
    @Column(name = "contact_person_email")
    private String contactPersonEmail;
    @Column(name = "registerd_date")
    @Temporal(TemporalType.DATE)
    private Date registerdDate;
    @Column(name = "is_active")
    private Boolean isActive;
    @JoinColumn(name = "customer_rating_id", referencedColumnName = "rating_id")
    @ManyToOne(optional = false)
    private CustomerRating customerRatingId;
    @JoinColumn(name = "registered_by", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser registeredBy;

    public Customer() {
    }

    public Customer(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonNo() {
        return contactPersonNo;
    }

    public void setContactPersonNo(String contactPersonNo) {
        this.contactPersonNo = contactPersonNo;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public Date getRegisterdDate() {
        return registerdDate;
    }

    public void setRegisterdDate(Date registerdDate) {
        this.registerdDate = registerdDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public CustomerRating getCustomerRatingId() {
        return customerRatingId;
    }

    public void setCustomerRatingId(CustomerRating customerRatingId) {
        this.customerRatingId = customerRatingId;
    }

    public SysUser getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(SysUser registeredBy) {
        this.registeredBy = registeredBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Customer[ customerId=" + customerId + " ]";
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<Invoice> getInvoiceCollection() {
        return invoiceCollection;
    }

    public void setInvoiceCollection(Collection<Invoice> invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
    }

    @XmlTransient
    public Collection<CustomerHasGroup> getCustomerHasGroupCollection() {
        return customerHasGroupCollection;
    }

    public void setCustomerHasGroupCollection(Collection<CustomerHasGroup> customerHasGroupCollection) {
        this.customerHasGroupCollection = customerHasGroupCollection;
    }
    
}
