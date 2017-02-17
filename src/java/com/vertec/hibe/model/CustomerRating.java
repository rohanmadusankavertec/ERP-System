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
@Table(name = "customer_rating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerRating.findAll", query = "SELECT c FROM CustomerRating c"),
    @NamedQuery(name = "CustomerRating.findByRatingId", query = "SELECT c FROM CustomerRating c WHERE c.ratingId = :ratingId"),
    @NamedQuery(name = "CustomerRating.findByNoOfStars", query = "SELECT c FROM CustomerRating c WHERE c.noOfStars = :noOfStars"),
    @NamedQuery(name = "CustomerRating.findByOtherDes", query = "SELECT c FROM CustomerRating c WHERE c.otherDes = :otherDes")})
public class CustomerRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rating_id")
    private Integer ratingId;
    @Column(name = "no_of_stars")
    private Integer noOfStars;
    @Column(name = "other_des")
    private String otherDes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerRatingId")
    private Collection<Customer> customerCollection;

    public CustomerRating() {
    }

    public CustomerRating(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public Integer getNoOfStars() {
        return noOfStars;
    }

    public void setNoOfStars(Integer noOfStars) {
        this.noOfStars = noOfStars;
    }

    public String getOtherDes() {
        return otherDes;
    }

    public void setOtherDes(String otherDes) {
        this.otherDes = otherDes;
    }

    @XmlTransient
    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratingId != null ? ratingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerRating)) {
            return false;
        }
        CustomerRating other = (CustomerRating) object;
        if ((this.ratingId == null && other.ratingId != null) || (this.ratingId != null && !this.ratingId.equals(other.ratingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vertec.hibe.model.CustomerRating[ ratingId=" + ratingId + " ]";
    }
    
}
