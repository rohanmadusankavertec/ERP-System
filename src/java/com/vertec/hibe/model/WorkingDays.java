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
@Table(name = "working_days")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkingDays.findAll", query = "SELECT w FROM WorkingDays w"),
    @NamedQuery(name = "WorkingDays.findByIdworkingDays", query = "SELECT w FROM WorkingDays w WHERE w.idworkingDays = :idworkingDays"),
    @NamedQuery(name = "WorkingDays.findByDay", query = "SELECT w FROM WorkingDays w WHERE w.day = :day"),
    @NamedQuery(name = "WorkingDays.findByIntime", query = "SELECT w FROM WorkingDays w WHERE w.intime = :intime"),
    @NamedQuery(name = "WorkingDays.findByOuttime", query = "SELECT w FROM WorkingDays w WHERE w.outtime = :outtime"),
    @NamedQuery(name = "WorkingDays.findByLunchMinutes", query = "SELECT w FROM WorkingDays w WHERE w.lunchMinutes = :lunchMinutes"),
    @NamedQuery(name = "WorkingDays.findByTotalHours", query = "SELECT w FROM WorkingDays w WHERE w.totalHours = :totalHours")})
public class WorkingDays implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idworking_days")
    private Integer idworkingDays;
    @Column(name = "day")
    private String day;
    @Column(name = "intime")
    @Temporal(TemporalType.TIME)
    private Date intime;
    @Column(name = "outtime")
    @Temporal(TemporalType.TIME)
    private Date outtime;
    @Column(name = "lunch_minutes")
    private Integer lunchMinutes;
    @Column(name = "total_hours")
    @Temporal(TemporalType.TIME)
    private Date totalHours;

    public WorkingDays() {
    }

    public WorkingDays(Integer idworkingDays) {
        this.idworkingDays = idworkingDays;
    }

    public Integer getIdworkingDays() {
        return idworkingDays;
    }

    public void setIdworkingDays(Integer idworkingDays) {
        this.idworkingDays = idworkingDays;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public Date getOuttime() {
        return outtime;
    }

    public void setOuttime(Date outtime) {
        this.outtime = outtime;
    }

    public Integer getLunchMinutes() {
        return lunchMinutes;
    }

    public void setLunchMinutes(Integer lunchMinutes) {
        this.lunchMinutes = lunchMinutes;
    }

    public Date getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Date totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idworkingDays != null ? idworkingDays.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkingDays)) {
            return false;
        }
        WorkingDays other = (WorkingDays) object;
        if ((this.idworkingDays == null && other.idworkingDays != null) || (this.idworkingDays != null && !this.idworkingDays.equals(other.idworkingDays))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.WorkingDays[ idworkingDays=" + idworkingDays + " ]";
    }
    
}
