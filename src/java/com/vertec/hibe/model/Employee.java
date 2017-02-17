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
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.id = :id"),
    @NamedQuery(name = "Employee.findByFname", query = "SELECT e FROM Employee e WHERE e.fname = :fname"),
    @NamedQuery(name = "Employee.findByLname", query = "SELECT e FROM Employee e WHERE e.lname = :lname"),
    @NamedQuery(name = "Employee.findByDob", query = "SELECT e FROM Employee e WHERE e.dob = :dob"),
    @NamedQuery(name = "Employee.findByNic", query = "SELECT e FROM Employee e WHERE e.nic = :nic"),
    @NamedQuery(name = "Employee.findByGender", query = "SELECT e FROM Employee e WHERE e.gender = :gender"),
    @NamedQuery(name = "Employee.findByBasicSalary", query = "SELECT e FROM Employee e WHERE e.basicSalary = :basicSalary"),
    @NamedQuery(name = "Employee.findByAddress", query = "SELECT e FROM Employee e WHERE e.address = :address"),
    @NamedQuery(name = "Employee.findByIsValid", query = "SELECT e FROM Employee e WHERE e.isValid = :isValid"),
    @NamedQuery(name = "Employee.findByFpid", query = "SELECT e FROM Employee e WHERE e.fpid = :fpid"),
    @NamedQuery(name = "Employee.findByContactNo", query = "SELECT e FROM Employee e WHERE e.contactNo = :contactNo"),
    @NamedQuery(name = "Employee.findByEmergency", query = "SELECT e FROM Employee e WHERE e.emergency = :emergency"),
    @NamedQuery(name = "Employee.findByCivilStatus", query = "SELECT e FROM Employee e WHERE e.civilStatus = :civilStatus"),
    @NamedQuery(name = "Employee.findByWeight", query = "SELECT e FROM Employee e WHERE e.weight = :weight"),
    @NamedQuery(name = "Employee.findByHeight", query = "SELECT e FROM Employee e WHERE e.height = :height"),
    @NamedQuery(name = "Employee.findByQualification", query = "SELECT e FROM Employee e WHERE e.qualification = :qualification"),
    @NamedQuery(name = "Employee.findByExperience", query = "SELECT e FROM Employee e WHERE e.experience = :experience"),
    @NamedQuery(name = "Employee.findByImage", query = "SELECT e FROM Employee e WHERE e.image = :image"),
    @NamedQuery(name = "Employee.findByAppoint", query = "SELECT e FROM Employee e WHERE e.appoint = :appoint")})
public class Employee implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<Salary> salaryCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<Advance> advanceCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<AllowanceDeduction> allowanceDeductionCollection;

    @Column(name = "educational")
    private String educational;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fname")
    private String fname;
    @Column(name = "lname")
    private String lname;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "nic")
    private String nic;
    @Column(name = "gender")
    private Boolean gender;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "basic_salary")
    private Double basicSalary;
    @Column(name = "address")
    private String address;
    @Column(name = "is_valid")
    private Boolean isValid;
    @Column(name = "fpid")
    private String fpid;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "emergency")
    private String emergency;
    @Column(name = "civil_status")
    private Boolean civilStatus;
    @Column(name = "weight")
    private String weight;
    @Column(name = "height")
    private String height;
    @Column(name = "qualification")
    private String qualification;
    @Column(name = "experience")
    private String experience;
    @Column(name = "image")
    private String image;
    @Column(name = "appoint")
    @Temporal(TemporalType.DATE)
    private Date appoint;
    @JoinColumn(name = "designation_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Designation designationId;
    @JoinColumn(name = "employee_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EmployeeType employeeTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<Leaves> leavesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<PayrollDefault> payrollDefaultCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<StaffLoan> staffLoanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private Collection<Attendance> attendanceCollection;

    public Employee() {
    }

    public Employee(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getFpid() {
        return fpid;
    }

    public void setFpid(String fpid) {
        this.fpid = fpid;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public Boolean getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(Boolean civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }


    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getAppoint() {
        return appoint;
    }

    public void setAppoint(Date appoint) {
        this.appoint = appoint;
    }

    public Designation getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Designation designationId) {
        this.designationId = designationId;
    }

    public EmployeeType getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(EmployeeType employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    @XmlTransient
    public Collection<Leaves> getLeavesCollection() {
        return leavesCollection;
    }

    public void setLeavesCollection(Collection<Leaves> leavesCollection) {
        this.leavesCollection = leavesCollection;
    }

    @XmlTransient
    public Collection<PayrollDefault> getPayrollDefaultCollection() {
        return payrollDefaultCollection;
    }

    public void setPayrollDefaultCollection(Collection<PayrollDefault> payrollDefaultCollection) {
        this.payrollDefaultCollection = payrollDefaultCollection;
    }

    @XmlTransient
    public Collection<StaffLoan> getStaffLoanCollection() {
        return staffLoanCollection;
    }

    public void setStaffLoanCollection(Collection<StaffLoan> staffLoanCollection) {
        this.staffLoanCollection = staffLoanCollection;
    }

    @XmlTransient
    public Collection<Attendance> getAttendanceCollection() {
        return attendanceCollection;
    }

    public void setAttendanceCollection(Collection<Attendance> attendanceCollection) {
        this.attendanceCollection = attendanceCollection;
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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Employee[ id=" + id + " ]";
    }

    public String getEducational() {
        return educational;
    }

    public void setEducational(String educational) {
        this.educational = educational;
    }

    @XmlTransient
    public Collection<AllowanceDeduction> getAllowanceDeductionCollection() {
        return allowanceDeductionCollection;
    }

    public void setAllowanceDeductionCollection(Collection<AllowanceDeduction> allowanceDeductionCollection) {
        this.allowanceDeductionCollection = allowanceDeductionCollection;
    }

    @XmlTransient
    public Collection<Advance> getAdvanceCollection() {
        return advanceCollection;
    }

    public void setAdvanceCollection(Collection<Advance> advanceCollection) {
        this.advanceCollection = advanceCollection;
    }

    @XmlTransient
    public Collection<Salary> getSalaryCollection() {
        return salaryCollection;
    }

    public void setSalaryCollection(Collection<Salary> salaryCollection) {
        this.salaryCollection = salaryCollection;
    }
    
}
