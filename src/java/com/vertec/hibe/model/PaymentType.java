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
import javax.persistence.Id;
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
@Table(name = "payment_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentType.findAll", query = "SELECT p FROM PaymentType p"),
    @NamedQuery(name = "PaymentType.findByPtId", query = "SELECT p FROM PaymentType p WHERE p.ptId = :ptId"),
    @NamedQuery(name = "PaymentType.findByPaymentTypeName", query = "SELECT p FROM PaymentType p WHERE p.paymentTypeName = :paymentTypeName")})
public class PaymentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pt_id")
    private Integer ptId;
    @Column(name = "payment_type_name")
    private String paymentTypeName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentTypeId")
    private Collection<Payment> paymentCollection;

    public PaymentType() {
    }

    public PaymentType(Integer ptId) {
        this.ptId = ptId;
    }

    public Integer getPtId() {
        return ptId;
    }

    public void setPtId(Integer ptId) {
        this.ptId = ptId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    @XmlTransient
    public Collection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public void setPaymentCollection(Collection<Payment> paymentCollection) {
        this.paymentCollection = paymentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ptId != null ? ptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentType)) {
            return false;
        }
        PaymentType other = (PaymentType) object;
        if ((this.ptId == null && other.ptId != null) || (this.ptId != null && !this.ptId.equals(other.ptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.PaymentType[ ptId=" + ptId + " ]";
    }
    
}
