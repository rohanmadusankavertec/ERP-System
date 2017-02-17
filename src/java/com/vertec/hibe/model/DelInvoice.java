/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vertec-r
 */
@Entity
@Table(name = "del_invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DelInvoice.findAll", query = "SELECT d FROM DelInvoice d"),
    @NamedQuery(name = "DelInvoice.findById", query = "SELECT d FROM DelInvoice d WHERE d.id = :id"),
    @NamedQuery(name = "DelInvoice.findByDate", query = "SELECT d FROM DelInvoice d WHERE d.date = :date"),
    @NamedQuery(name = "DelInvoice.findByIsDeleted", query = "SELECT d FROM DelInvoice d WHERE d.isDeleted = :isDeleted")})
public class DelInvoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @JoinColumn(name = "invoice_invoice_id", referencedColumnName = "invoice_id")
    @ManyToOne(optional = false)
    private Invoice invoiceInvoiceId;
    @JoinColumn(name = "sys_user_sysuser_id", referencedColumnName = "sysuser_id")
    @ManyToOne(optional = false)
    private SysUser sysUserSysuserId;

    public DelInvoice() {
    }

    public DelInvoice(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Invoice getInvoiceInvoiceId() {
        return invoiceInvoiceId;
    }

    public void setInvoiceInvoiceId(Invoice invoiceInvoiceId) {
        this.invoiceInvoiceId = invoiceInvoiceId;
    }

    public SysUser getSysUserSysuserId() {
        return sysUserSysuserId;
    }

    public void setSysUserSysuserId(SysUser sysUserSysuserId) {
        this.sysUserSysuserId = sysUserSysuserId;
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
        if (!(object instanceof DelInvoice)) {
            return false;
        }
        DelInvoice other = (DelInvoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.DelInvoice[ id=" + id + " ]";
    }
    
}
