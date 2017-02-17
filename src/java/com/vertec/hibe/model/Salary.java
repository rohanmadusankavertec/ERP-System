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
@Table(name = "salary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salary.findAll", query = "SELECT s FROM Salary s"),
    @NamedQuery(name = "Salary.findById", query = "SELECT s FROM Salary s WHERE s.id = :id"),
    @NamedQuery(name = "Salary.findByFromdate", query = "SELECT s FROM Salary s WHERE s.fromdate = :fromdate"),
    @NamedQuery(name = "Salary.findByTodate", query = "SELECT s FROM Salary s WHERE s.todate = :todate"),
    @NamedQuery(name = "Salary.findByTotalSalary", query = "SELECT s FROM Salary s WHERE s.totalSalary = :totalSalary"),
    @NamedQuery(name = "Salary.findByPaid", query = "SELECT s FROM Salary s WHERE s.paid = :paid"),
    @NamedQuery(name = "Salary.findByDue", query = "SELECT s FROM Salary s WHERE s.due = :due"),
    @NamedQuery(name = "Salary.findByAllowance", query = "SELECT s FROM Salary s WHERE s.allowance = :allowance"),
    @NamedQuery(name = "Salary.findByDeduction", query = "SELECT s FROM Salary s WHERE s.deduction = :deduction"),
    @NamedQuery(name = "Salary.findByLateDeduct", query = "SELECT s FROM Salary s WHERE s.lateDeduct = :lateDeduct"),
    @NamedQuery(name = "Salary.findByNopayDeduct", query = "SELECT s FROM Salary s WHERE s.nopayDeduct = :nopayDeduct"),
    @NamedQuery(name = "Salary.findByOtpay", query = "SELECT s FROM Salary s WHERE s.otpay = :otpay"),
    @NamedQuery(name = "Salary.findByEtf", query = "SELECT s FROM Salary s WHERE s.etf = :etf"),
    @NamedQuery(name = "Salary.findByEpf", query = "SELECT s FROM Salary s WHERE s.epf = :epf"),
    @NamedQuery(name = "Salary.findByEpfPayable", query = "SELECT s FROM Salary s WHERE s.epfPayable = :epfPayable"),
    @NamedQuery(name = "Salary.findByDate", query = "SELECT s FROM Salary s WHERE s.date = :date"),
    @NamedQuery(name = "Salary.findBySalaryPayable", query = "SELECT s FROM Salary s WHERE s.salaryPayable = :salaryPayable"),
    @NamedQuery(name = "Salary.findByAdvancePaid", query = "SELECT s FROM Salary s WHERE s.advancePaid = :advancePaid"),
    @NamedQuery(name = "Salary.findByLoanPaid", query = "SELECT s FROM Salary s WHERE s.loanPaid = :loanPaid")})
public class Salary implements Serializable {

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employee employeeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salaryId")
    private Collection<SalaryPayment> salaryPaymentCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fromdate")
    @Temporal(TemporalType.DATE)
    private Date fromdate;
    @Column(name = "todate")
    @Temporal(TemporalType.DATE)
    private Date todate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_salary")
    private Double totalSalary;
    @Column(name = "paid")
    private Double paid;
    @Column(name = "due")
    private Double due;
    @Column(name = "allowance")
    private Double allowance;
    @Column(name = "deduction")
    private Double deduction;
    @Column(name = "late_deduct")
    private Double lateDeduct;
    @Column(name = "nopay_deduct")
    private Double nopayDeduct;
    @Column(name = "otpay")
    private Double otpay;
    @Column(name = "etf")
    private Double etf;
    @Column(name = "epf")
    private Double epf;
    @Column(name = "epf_payable")
    private Double epfPayable;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "salary_payable")
    private Double salaryPayable;
    @Column(name = "advance_paid")
    private Double advancePaid;
    @Column(name = "loan_paid")
    private Double loanPaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salaryId")
    private Collection<SalaryAttendance> salaryAttendanceCollection;

    public Salary() {
    }

    public Salary(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public Double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getDue() {
        return due;
    }

    public void setDue(Double due) {
        this.due = due;
    }

    public Double getAllowance() {
        return allowance;
    }

    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }

    public Double getDeduction() {
        return deduction;
    }

    public void setDeduction(Double deduction) {
        this.deduction = deduction;
    }

    public Double getLateDeduct() {
        return lateDeduct;
    }

    public void setLateDeduct(Double lateDeduct) {
        this.lateDeduct = lateDeduct;
    }

    public Double getNopayDeduct() {
        return nopayDeduct;
    }

    public void setNopayDeduct(Double nopayDeduct) {
        this.nopayDeduct = nopayDeduct;
    }

    public Double getOtpay() {
        return otpay;
    }

    public void setOtpay(Double otpay) {
        this.otpay = otpay;
    }

    public Double getEtf() {
        return etf;
    }

    public void setEtf(Double etf) {
        this.etf = etf;
    }

    public Double getEpf() {
        return epf;
    }

    public void setEpf(Double epf) {
        this.epf = epf;
    }

    public Double getEpfPayable() {
        return epfPayable;
    }

    public void setEpfPayable(Double epfPayable) {
        this.epfPayable = epfPayable;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getSalaryPayable() {
        return salaryPayable;
    }

    public void setSalaryPayable(Double salaryPayable) {
        this.salaryPayable = salaryPayable;
    }

    public Double getAdvancePaid() {
        return advancePaid;
    }

    public void setAdvancePaid(Double advancePaid) {
        this.advancePaid = advancePaid;
    }

    public Double getLoanPaid() {
        return loanPaid;
    }

    public void setLoanPaid(Double loanPaid) {
        this.loanPaid = loanPaid;
    }

    @XmlTransient
    public Collection<SalaryAttendance> getSalaryAttendanceCollection() {
        return salaryAttendanceCollection;
    }

    public void setSalaryAttendanceCollection(Collection<SalaryAttendance> salaryAttendanceCollection) {
        this.salaryAttendanceCollection = salaryAttendanceCollection;
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
        if (!(object instanceof Salary)) {
            return false;
        }
        Salary other = (Salary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Salary[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<SalaryPayment> getSalaryPaymentCollection() {
        return salaryPaymentCollection;
    }

    public void setSalaryPaymentCollection(Collection<SalaryPayment> salaryPaymentCollection) {
        this.salaryPaymentCollection = salaryPaymentCollection;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }
    
}
