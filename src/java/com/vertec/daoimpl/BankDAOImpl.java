/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Bank;
import com.vertec.hibe.model.BankAccounts;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.VertecConstants;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class BankDAOImpl {

    
    public String saveBank(Bank bank) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(bank);
                session.flush();

                transaction.commit();
                return VertecConstants.SUCCESS;

            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    public List<Bank> loadAllBank() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("Bank.findAll");

                List<Bank> bList = query.list();
                return bList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    public String saveBankAccount(BankAccounts bankAccount) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                session.save(bankAccount);
                session.flush();

                transaction.commit();
                return VertecConstants.SUCCESS;

            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    public List<BankAccounts> loadAllBankAccount() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.createQuery("SELECT ba FROM BankAccounts ba WHERE ba.accountStatus=:accountStatus ");
                query.setParameter("accountStatus", true);
                List<BankAccounts> baList = query.list();
                return baList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    public BankAccounts ViewBankAccount(int accountId) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();

        if (session != null) {
            try {
                Query query = session.getNamedQuery("BankAccounts.findById");
                query.setParameter("id", accountId);
                BankAccounts account = (BankAccounts) query.uniqueResult();
                return account;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    public String updateBankAccount(BankAccounts bankAccount) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (session != null) {
            try {

                Query query = session.createQuery("UPDATE BankAccounts as b set b.bankId=:bank,b.accountNo=:accountNo,b.accountType=:accountType,b.lastUpdatedBy=:lastUpdatedBy,b.lastUpdatedDate=:lastUpdatedDate where b.id=:accountId");

                query.setParameter("accountNo", bankAccount.getAccountNo());
                query.setParameter("accountType", bankAccount.getAccountType());
                query.setParameter("bank", bankAccount.getBankId());
                query.setParameter("lastUpdatedBy", bankAccount.getLastUpdatedBy());
                query.setParameter("lastUpdatedDate", bankAccount.getLastUpdatedDate());
                query.setParameter("accountId", bankAccount.getId());
                query.executeUpdate();
                transaction.commit();
                return VertecConstants.SUCCESS;

            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        return null;
    }

    

}
