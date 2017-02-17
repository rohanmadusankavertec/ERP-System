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
@Table(name = "return_by_customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReturnByCustomer.findAll", query = "SELECT r FROM ReturnByCustomer r"),
    @NamedQuery(name = "ReturnByCustomer.findById", query = "SELECT r FROM ReturnByCustomer r WHERE r.id = :id"),
    @NamedQuery(name = "ReturnByCustomer.findByQty", query = "SELECT r FROM ReturnByCustomer r WHERE r.qty = :qty")})
public class ReturnByCustomer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    @JoinColumn(name = "customer_return_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CustomerReturn customerReturnId;
    @JoinColumn(name = "invoice_item_id", referencedColumnName = "invoice_item_id")
    @ManyToOne(optional = false)
    private InvoiceItem invoiceItemId;

    public ReturnByCustomer() {
    }

    public ReturnByCustomer(Integer id) {
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

    public CustomerReturn getCustomerReturnId() {
        return customerReturnId;
    }

    public void setCustomerReturnId(CustomerReturn customerReturnId) {
        this.customerReturnId = customerReturnId;
    }

    public InvoiceItem getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(InvoiceItem invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
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
        if (!(object instanceof ReturnByCustomer)) {
            return false;
        }
        ReturnByCustomer other = (ReturnByCustomer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.ReturnByCustomer[ id=" + id + " ]";
    }
    
}
