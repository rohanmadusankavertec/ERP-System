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
@Table(name = "salary_attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SalaryAttendance.findAll", query = "SELECT s FROM SalaryAttendance s"),
    @NamedQuery(name = "SalaryAttendance.findById", query = "SELECT s FROM SalaryAttendance s WHERE s.id = :id"),
    @NamedQuery(name = "SalaryAttendance.findByWorkingMin", query = "SELECT s FROM SalaryAttendance s WHERE s.workingMin = :workingMin"),
    @NamedQuery(name = "SalaryAttendance.findByWorkedMin", query = "SELECT s FROM SalaryAttendance s WHERE s.workedMin = :workedMin"),
    @NamedQuery(name = "SalaryAttendance.findByEarlyArrival", query = "SELECT s FROM SalaryAttendance s WHERE s.earlyArrival = :earlyArrival"),
    @NamedQuery(name = "SalaryAttendance.findByLateArrival", query = "SELECT s FROM SalaryAttendance s WHERE s.lateArrival = :lateArrival"),
    @NamedQuery(name = "SalaryAttendance.findByEarlyLeave", query = "SELECT s FROM SalaryAttendance s WHERE s.earlyLeave = :earlyLeave"),
    @NamedQuery(name = "SalaryAttendance.findByLateLeave", query = "SELECT s FROM SalaryAttendance s WHERE s.lateLeave = :lateLeave"),
    @NamedQuery(name = "SalaryAttendance.findByNopayLeave", query = "SELECT s FROM SalaryAttendance s WHERE s.nopayLeave = :nopayLeave"),
    @NamedQuery(name = "SalaryAttendance.findByPayLeave", query = "SELECT s FROM SalaryAttendance s WHERE s.payLeave = :payLeave"),
    @NamedQuery(name = "SalaryAttendance.findByOtMin", query = "SELECT s FROM SalaryAttendance s WHERE s.otMin = :otMin")})
public class SalaryAttendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "working_min")
    private Integer workingMin;
    @Column(name = "worked_min")
    private Integer workedMin;
    @Column(name = "early_arrival")
    private Integer earlyArrival;
    @Column(name = "late_arrival")
    private Integer lateArrival;
    @Column(name = "early_leave")
    private Integer earlyLeave;
    @Column(name = "late_leave")
    private Integer lateLeave;
    @Column(name = "nopay_leave")
    private Integer nopayLeave;
    @Column(name = "pay_leave")
    private Integer payLeave;
    @Column(name = "ot_min")
    private Integer otMin;
    @JoinColumn(name = "salary_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Salary salaryId;

    public SalaryAttendance() {
    }

    public SalaryAttendance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkingMin() {
        return workingMin;
    }

    public void setWorkingMin(Integer workingMin) {
        this.workingMin = workingMin;
    }

    public Integer getWorkedMin() {
        return workedMin;
    }

    public void setWorkedMin(Integer workedMin) {
        this.workedMin = workedMin;
    }

    public Integer getEarlyArrival() {
        return earlyArrival;
    }

    public void setEarlyArrival(Integer earlyArrival) {
        this.earlyArrival = earlyArrival;
    }

    public Integer getLateArrival() {
        return lateArrival;
    }

    public void setLateArrival(Integer lateArrival) {
        this.lateArrival = lateArrival;
    }

    public Integer getEarlyLeave() {
        return earlyLeave;
    }

    public void setEarlyLeave(Integer earlyLeave) {
        this.earlyLeave = earlyLeave;
    }

    public Integer getLateLeave() {
        return lateLeave;
    }

    public void setLateLeave(Integer lateLeave) {
        this.lateLeave = lateLeave;
    }

    public Integer getNopayLeave() {
        return nopayLeave;
    }

    public void setNopayLeave(Integer nopayLeave) {
        this.nopayLeave = nopayLeave;
    }

    public Integer getPayLeave() {
        return payLeave;
    }

    public void setPayLeave(Integer payLeave) {
        this.payLeave = payLeave;
    }

    public Integer getOtMin() {
        return otMin;
    }

    public void setOtMin(Integer otMin) {
        this.otMin = otMin;
    }

    public Salary getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Salary salaryId) {
        this.salaryId = salaryId;
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
        if (!(object instanceof SalaryAttendance)) {
            return false;
        }
        SalaryAttendance other = (SalaryAttendance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.SalaryAttendance[ id=" + id + " ]";
    }
    
}
