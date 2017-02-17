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
@Table(name = "outstandig_invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OutstandigInvoice.findAll", query = "SELECT o FROM OutstandigInvoice o"),
    @NamedQuery(name = "OutstandigInvoice.findByOiId", query = "SELECT o FROM OutstandigInvoice o WHERE o.oiId = :oiId"),
    @NamedQuery(name = "OutstandigInvoice.findByBalanceAmount", query = "SELECT o FROM OutstandigInvoice o WHERE o.balanceAmount = :balanceAmount"),
    @NamedQuery(name = "OutstandigInvoice.findByStartedDate", query = "SELECT o FROM OutstandigInvoice o WHERE o.startedDate = :startedDate"),
    @NamedQuery(name = "OutstandigInvoice.findByLastUpdateDate", query = "SELECT o FROM OutstandigInvoice o WHERE o.lastUpdateDate = :lastUpdateDate"),
    @NamedQuery(name = "OutstandigInvoice.findByOutStatus", query = "SELECT o FROM OutstandigInvoice o WHERE o.outStatus = :outStatus")})
public class OutstandigInvoice implements Serializable {

    @Column(name = "out_status")
    private Boolean outStatus;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "oi_id")
    private Integer oiId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance_amount")
    private Double balanceAmount;
    @Column(name = "started_date")
    @Temporal(TemporalType.DATE)
    private Date startedDate;
    @Column(name = "last_update_date")
    @Temporal(TemporalType.DATE)
    private Date lastUpdateDate;
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    @ManyToOne(optional = false)
    private Invoice invoiceId;

    public OutstandigInvoice() {
    }

    public OutstandigInvoice(Integer oiId) {
        this.oiId = oiId;
    }

    public OutstandigInvoice(Integer oiId, double balanceAmount, Date lastUpdateDate) {
        this.oiId = oiId;
        this.balanceAmount = balanceAmount;
        this.lastUpdateDate = lastUpdateDate;
    }

    public OutstandigInvoice(boolean outStatus, double balanceAmount, Date startedDate, Date lastUpdateDate, Invoice invoiceId) {
        this.outStatus = outStatus;
        this.balanceAmount = balanceAmount;
        this.startedDate = startedDate;
        this.lastUpdateDate = lastUpdateDate;
        this.invoiceId = invoiceId;
    }
     public OutstandigInvoice(double balanceAmount, Date lastUpdateDate, Invoice invoiceId) {
        this.balanceAmount = balanceAmount;
        this.lastUpdateDate = lastUpdateDate;
        this.invoiceId = invoiceId;
    }
    public Integer getOiId() {
        return oiId;
    }

    public void setOiId(Integer oiId) {
        this.oiId = oiId;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oiId != null ? oiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OutstandigInvoice)) {
            return false;
        }
        OutstandigInvoice other = (OutstandigInvoice) object;
        if ((this.oiId == null && other.oiId != null) || (this.oiId != null && !this.oiId.equals(other.oiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.OutstandigInvoice[ oiId=" + oiId + " ]";
    }

    public Boolean getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(Boolean outStatus) {
        this.outStatus = outStatus;
    }
    
}
