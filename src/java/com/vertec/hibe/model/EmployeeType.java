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
@Table(name = "employee_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeType.findAll", query = "SELECT e FROM EmployeeType e"),
    @NamedQuery(name = "EmployeeType.findById", query = "SELECT e FROM EmployeeType e WHERE e.id = :id"),
    @NamedQuery(name = "EmployeeType.findByType", query = "SELECT e FROM EmployeeType e WHERE e.type = :type"),
    @NamedQuery(name = "EmployeeType.findByMonthlyShortLeaves", query = "SELECT e FROM EmployeeType e WHERE e.monthlyShortLeaves = :monthlyShortLeaves"),
    @NamedQuery(name = "EmployeeType.findByMonthlyLeaves", query = "SELECT e FROM EmployeeType e WHERE e.monthlyLeaves = :monthlyLeaves"),
    @NamedQuery(name = "EmployeeType.findByAnnualLeaves", query = "SELECT e FROM EmployeeType e WHERE e.annualLeaves = :annualLeaves"),
    @NamedQuery(name = "EmployeeType.findByEtfEpf", query = "SELECT e FROM EmployeeType e WHERE e.etfEpf = :etfEpf")})
public class EmployeeType implements Serializable {

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Company companyId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monthly_short_leaves")
    private Double monthlyShortLeaves;
    @Column(name = "monthly_leaves")
    private Double monthlyLeaves;
    @Column(name = "annual_leaves")
    private Double annualLeaves;
    @Column(name = "etf_epf")
    private Boolean etfEpf;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeTypeId")
    private Collection<Employee> employeeCollection;

    public EmployeeType() {
    }

    public EmployeeType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMonthlyShortLeaves() {
        return monthlyShortLeaves;
    }

    public void setMonthlyShortLeaves(Double monthlyShortLeaves) {
        this.monthlyShortLeaves = monthlyShortLeaves;
    }

    public Double getMonthlyLeaves() {
        return monthlyLeaves;
    }

    public void setMonthlyLeaves(Double monthlyLeaves) {
        this.monthlyLeaves = monthlyLeaves;
    }

    public Double getAnnualLeaves() {
        return annualLeaves;
    }

    public void setAnnualLeaves(Double annualLeaves) {
        this.annualLeaves = annualLeaves;
    }

    public Boolean getEtfEpf() {
        return etfEpf;
    }

    public void setEtfEpf(Boolean etfEpf) {
        this.etfEpf = etfEpf;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
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
        if (!(object instanceof EmployeeType)) {
            return false;
        }
        EmployeeType other = (EmployeeType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.EmployeeType[ id=" + id + " ]";
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
    
}
