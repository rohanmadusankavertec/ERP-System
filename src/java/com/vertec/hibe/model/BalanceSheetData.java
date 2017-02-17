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
public class BalanceSheetData {
    private Account a;
    private Subtype st;
    private Double amount;

    /**
     * @return the a
     */
    public Account getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(Account a) {
        this.a = a;
    }

    /**
     * @return the st
     */
    public Subtype getSt() {
        return st;
    }

    /**
     * @param st the st to set
     */
    public void setSt(Subtype st) {
        this.st = st;
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
}
