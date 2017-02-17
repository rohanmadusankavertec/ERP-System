/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.hibe.model;

/**
 *
 * @author vertec-r
 */
public class DepreciationData {
    private Depreciation depreciation;
    private Double amount;
    private Double beforedep;
    private Account account;

    /**
     * @return the depreciation
     */
    public Depreciation getDepreciation() {
        return depreciation;
    }

    /**
     * @param depreciation the depreciation to set
     */
    public void setDepreciation(Depreciation depreciation) {
        this.depreciation = depreciation;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the beforedep
     */
    public Double getBeforedep() {
        return beforedep;
    }

    /**
     * @param beforedep the beforedep to set
     */
    public void setBeforedep(Double beforedep) {
        this.beforedep = beforedep;
    }
}
