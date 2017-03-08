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
@Table(name = "company")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c"),
    @NamedQuery(name = "Company.findById", query = "SELECT c FROM Company c WHERE c.id = :id"),
    @NamedQuery(name = "Company.findByCompanyName", query = "SELECT c FROM Company c WHERE c.companyName = :companyName"),
    @NamedQuery(name = "Company.findByAddress", query = "SELECT c FROM Company c WHERE c.address = :address"),
    @NamedQuery(name = "Company.findByContactNo", query = "SELECT c FROM Company c WHERE c.contactNo = :contactNo"),
    @NamedQuery(name = "Company.findByEmail", query = "SELECT c FROM Company c WHERE c.email = :email")})
public class Company implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Adtype> adtypeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Bank> bankCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<EmployeeType> employeeTypeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Fpfiles> fpfilesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<WorkingDays> workingDaysCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Designation> designationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Department> departmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Employee> employeeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<HollyDay> hollyDayCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Product> productCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Invoice> invoiceCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<CustomerGroup> customerGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<MrnInfo> mrnInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<GrnInfo> grnInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<PrnInfo> prnInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<PoInfo> poInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<GinInfo> ginInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<SupplierGroup> supplierGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<ProductCategory> productCategoryCollection;

    @JoinColumn(name = "currency_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CurrencyType currencyTypeId;

    @Column(name = "is_valid")
    private Boolean isValid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<BranchProductmaster> branchProductmasterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Branch> branchCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Supplier> supplierCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Customer> customerCollection;

    @JoinColumn(name = "company_group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CompanyGroup companyGroupId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "address")
    private String address;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<UserGroup> userGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<PrivilegeItem> privilegeItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<SysUser> sysUserCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Priviledge> priviledgeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Account> accountCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Transaction> transactionCollection;

    public Company() {
    }

    public Company(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<UserGroup> getUserGroupCollection() {
        return userGroupCollection;
    }

    public void setUserGroupCollection(Collection<UserGroup> userGroupCollection) {
        this.userGroupCollection = userGroupCollection;
    }

    @XmlTransient
    public Collection<PrivilegeItem> getPrivilegeItemCollection() {
        return privilegeItemCollection;
    }

    public void setPrivilegeItemCollection(Collection<PrivilegeItem> privilegeItemCollection) {
        this.privilegeItemCollection = privilegeItemCollection;
    }

    @XmlTransient
    public Collection<SysUser> getSysUserCollection() {
        return sysUserCollection;
    }

    public void setSysUserCollection(Collection<SysUser> sysUserCollection) {
        this.sysUserCollection = sysUserCollection;
    }

    @XmlTransient
    public Collection<Priviledge> getPriviledgeCollection() {
        return priviledgeCollection;
    }

    public void setPriviledgeCollection(Collection<Priviledge> priviledgeCollection) {
        this.priviledgeCollection = priviledgeCollection;
    }

    @XmlTransient
    public Collection<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
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
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.Company[ id=" + id + " ]";
    }

    public CompanyGroup getCompanyGroupId() {
        return companyGroupId;
    }

    public void setCompanyGroupId(CompanyGroup companyGroupId) {
        this.companyGroupId = companyGroupId;
    }

    @XmlTransient
    public Collection<BranchProductmaster> getBranchProductmasterCollection() {
        return branchProductmasterCollection;
    }

    public void setBranchProductmasterCollection(Collection<BranchProductmaster> branchProductmasterCollection) {
        this.branchProductmasterCollection = branchProductmasterCollection;
    }

    @XmlTransient
    public Collection<Branch> getBranchCollection() {
        return branchCollection;
    }

    public void setBranchCollection(Collection<Branch> branchCollection) {
        this.branchCollection = branchCollection;
    }

    @XmlTransient
    public Collection<Supplier> getSupplierCollection() {
        return supplierCollection;
    }

    public void setSupplierCollection(Collection<Supplier> supplierCollection) {
        this.supplierCollection = supplierCollection;
    }

    @XmlTransient
    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public CurrencyType getCurrencyTypeId() {
        return currencyTypeId;
    }

    public void setCurrencyTypeId(CurrencyType currencyTypeId) {
        this.currencyTypeId = currencyTypeId;
    }

    @XmlTransient
    public Collection<CustomerGroup> getCustomerGroupCollection() {
        return customerGroupCollection;
    }

    public void setCustomerGroupCollection(Collection<CustomerGroup> customerGroupCollection) {
        this.customerGroupCollection = customerGroupCollection;
    }

    @XmlTransient
    public Collection<MrnInfo> getMrnInfoCollection() {
        return mrnInfoCollection;
    }

    public void setMrnInfoCollection(Collection<MrnInfo> mrnInfoCollection) {
        this.mrnInfoCollection = mrnInfoCollection;
    }

    @XmlTransient
    public Collection<GrnInfo> getGrnInfoCollection() {
        return grnInfoCollection;
    }

    public void setGrnInfoCollection(Collection<GrnInfo> grnInfoCollection) {
        this.grnInfoCollection = grnInfoCollection;
    }

    @XmlTransient
    public Collection<PrnInfo> getPrnInfoCollection() {
        return prnInfoCollection;
    }

    public void setPrnInfoCollection(Collection<PrnInfo> prnInfoCollection) {
        this.prnInfoCollection = prnInfoCollection;
    }

    @XmlTransient
    public Collection<PoInfo> getPoInfoCollection() {
        return poInfoCollection;
    }

    public void setPoInfoCollection(Collection<PoInfo> poInfoCollection) {
        this.poInfoCollection = poInfoCollection;
    }

    @XmlTransient
    public Collection<GinInfo> getGinInfoCollection() {
        return ginInfoCollection;
    }

    public void setGinInfoCollection(Collection<GinInfo> ginInfoCollection) {
        this.ginInfoCollection = ginInfoCollection;
    }

    @XmlTransient
    public Collection<SupplierGroup> getSupplierGroupCollection() {
        return supplierGroupCollection;
    }

    public void setSupplierGroupCollection(Collection<SupplierGroup> supplierGroupCollection) {
        this.supplierGroupCollection = supplierGroupCollection;
    }

    @XmlTransient
    public Collection<ProductCategory> getProductCategoryCollection() {
        return productCategoryCollection;
    }

    public void setProductCategoryCollection(Collection<ProductCategory> productCategoryCollection) {
        this.productCategoryCollection = productCategoryCollection;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @XmlTransient
    public Collection<Invoice> getInvoiceCollection() {
        return invoiceCollection;
    }

    public void setInvoiceCollection(Collection<Invoice> invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
    }

    @XmlTransient
    public Collection<Adtype> getAdtypeCollection() {
        return adtypeCollection;
    }

    public void setAdtypeCollection(Collection<Adtype> adtypeCollection) {
        this.adtypeCollection = adtypeCollection;
    }

    @XmlTransient
    public Collection<Bank> getBankCollection() {
        return bankCollection;
    }

    public void setBankCollection(Collection<Bank> bankCollection) {
        this.bankCollection = bankCollection;
    }

    @XmlTransient
    public Collection<EmployeeType> getEmployeeTypeCollection() {
        return employeeTypeCollection;
    }

    public void setEmployeeTypeCollection(Collection<EmployeeType> employeeTypeCollection) {
        this.employeeTypeCollection = employeeTypeCollection;
    }

    @XmlTransient
    public Collection<Fpfiles> getFpfilesCollection() {
        return fpfilesCollection;
    }

    public void setFpfilesCollection(Collection<Fpfiles> fpfilesCollection) {
        this.fpfilesCollection = fpfilesCollection;
    }

    @XmlTransient
    public Collection<WorkingDays> getWorkingDaysCollection() {
        return workingDaysCollection;
    }

    public void setWorkingDaysCollection(Collection<WorkingDays> workingDaysCollection) {
        this.workingDaysCollection = workingDaysCollection;
    }

    @XmlTransient
    public Collection<Designation> getDesignationCollection() {
        return designationCollection;
    }

    public void setDesignationCollection(Collection<Designation> designationCollection) {
        this.designationCollection = designationCollection;
    }

    @XmlTransient
    public Collection<Department> getDepartmentCollection() {
        return departmentCollection;
    }

    public void setDepartmentCollection(Collection<Department> departmentCollection) {
        this.departmentCollection = departmentCollection;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @XmlTransient
    public Collection<HollyDay> getHollyDayCollection() {
        return hollyDayCollection;
    }

    public void setHollyDayCollection(Collection<HollyDay> hollyDayCollection) {
        this.hollyDayCollection = hollyDayCollection;
    }
    
}
