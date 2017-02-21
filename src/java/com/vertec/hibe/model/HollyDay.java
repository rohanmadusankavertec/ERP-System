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
@Table(name = "holly_day")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HollyDay.findAll", query = "SELECT h FROM HollyDay h"),
    @NamedQuery(name = "HollyDay.findByHollydayId", query = "SELECT h FROM HollyDay h WHERE h.hollydayId = :hollydayId"),
    @NamedQuery(name = "HollyDay.findByName", query = "SELECT h FROM HollyDay h WHERE h.name = :name"),
    @NamedQuery(name = "HollyDay.findByDate", query = "SELECT h FROM HollyDay h WHERE h.date = :date")})
public class HollyDay implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hollyday_id")
    private Integer hollydayId;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public HollyDay() {
    }

    public HollyDay(Integer hollydayId) {
        this.hollydayId = hollydayId;
    }

    public Integer getHollydayId() {
        return hollydayId;
    }

    public void setHollydayId(Integer hollydayId) {
        this.hollydayId = hollydayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hollydayId != null ? hollydayId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HollyDay)) {
            return false;
        }
        HollyDay other = (HollyDay) object;
        if ((this.hollydayId == null && other.hollydayId != null) || (this.hollydayId != null && !this.hollydayId.equals(other.hollydayId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.HollyDay[ hollydayId=" + hollydayId + " ]";
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
    
}
