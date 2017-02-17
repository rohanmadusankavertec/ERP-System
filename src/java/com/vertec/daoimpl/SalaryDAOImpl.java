/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;

import com.vertec.hibe.model.Advance;
import com.vertec.hibe.model.AllowanceDeduction;
import com.vertec.hibe.model.Attendance;
import com.vertec.hibe.model.BankAccounts;
import com.vertec.hibe.model.Cheque;
import com.vertec.hibe.model.Employee;
import com.vertec.hibe.model.HollyDay;
import com.vertec.hibe.model.Leaves;
import com.vertec.hibe.model.SalaryPaymentType;
import com.vertec.hibe.model.PayrollDefault;
import com.vertec.hibe.model.Salary;
import com.vertec.hibe.model.SalaryPayment;
import com.vertec.hibe.model.StaffLoan;
import com.vertec.hibe.model.SysUser;
import com.vertec.hibe.model.WorkingDays;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.Save;
import com.vertec.util.VertecConstants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vertec-r
 */
public class SalaryDAOImpl {

    public List<AllowanceDeduction> getAllowanceDeduction() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM AllowanceDeduction a WHERE isValid = '1'");
                List<AllowanceDeduction> ad = query.list();
                return ad;
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

    public List<Advance> getAdvance() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Advance a WHERE isValid = '1'");
                List<Advance> ad = query.list();
                return ad;
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

    public List<BankAccounts> getBankAccount() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("BankAccounts.findAll");
                List<BankAccounts> ad = query.list();
                return ad;
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

    public List<StaffLoan> getLoans() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("Loan.findAll");
                List<StaffLoan> ad = query.list();
                return ad;
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

    public StaffLoan getLoanById(int loanid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("Loan.findById");
                query.setParameter("id", loanid);
                StaffLoan ad = (StaffLoan) query.uniqueResult();
                return ad;
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

    public List<WorkingDays> getworkingDays() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("WorkingDays.findAll");
                List<WorkingDays> ad = query.list();
                return ad;
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

    public List<PayrollDefault> getDefaultPayments() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("PayrollDefault.findAll");
                List<PayrollDefault> ad = query.list();
                return ad;
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

    public List<PayrollDefault> getDefaultPayments(Employee emp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT p FROM PayrollDefault p WHERE p.employeeId=:eid");
                query.setParameter("eid", emp);
                List<PayrollDefault> ad = query.list();
                return ad;
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

    public List<SalaryPayment> getSalaryPayments() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("SalaryPayment.findAll");
                List<SalaryPayment> ad = query.list();
                return ad;
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

    public List<Salary> getSalary() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("Salary.findAll");
                List<Salary> ad = query.list();
                return ad;
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

    public List<Salary> getSalaryByEmployee(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Salary s WHERE s.employeeId=:emp");
                query.setParameter("emp", new EmployeeDAOImpl().getEmployee(id));
                List<Salary> ad = query.list();
                return ad;
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

    public Salary getSalaryByID(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Salary s WHERE s.id=:id");
                query.setParameter("id", id);
                Salary ad = (Salary) query.uniqueResult();
                return ad;
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

    public AllowanceDeduction getAllowanceDeductionbyId(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("AllowanceDeduction.findById");
                query.setParameter("id", id);
                AllowanceDeduction ad = (AllowanceDeduction) query.uniqueResult();
                return ad;
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

    public String deleteAllowance(int allid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE AllowanceDeduction as d set d.isValid='0' where d.id=:allId");
                query.setParameter("allId", allid);
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

    public String updateAllowance(AllowanceDeduction allid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE AllowanceDeduction as d set d.name=:name,d.value=:value where d.id=:allId");
                query.setParameter("value", allid.getValue());
                query.setParameter("name", allid.getName());
                query.setParameter("allId", allid.getId());
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

    public String updateWorkingDays(String day, String intime, String outtime, String lmin) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {

                SimpleDateFormat tf = new SimpleDateFormat("hh:mm"); //if 24 hour format

                Date intime2 = tf.parse(intime);
                Date outtime2 = tf.parse(outtime);

                long minutes = ((outtime2.getTime() - intime2.getTime()) / 60000) - Integer.parseInt(lmin);
                int minute = Integer.parseInt(minutes + "");

                int hour = minute / 60;
                minute -= (hour * 60);

                if (minute < 0) {
                    hour--;
                    minute -= (hour * 60);
                }

                Query query = session.createQuery("UPDATE WorkingDays set intime=:intime,outtime=:outtime,lunch_minutes=:lm,total_hours=:th where day=:day");
                query.setParameter("day", day);
                query.setParameter("intime", intime2);
                query.setParameter("outtime", outtime2);
                query.setParameter("outtime", outtime2);
                query.setParameter("lm", Integer.parseInt(lmin));
                query.setParameter("th", tf.parse(hour + ":" + minute + ":00"));
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

    public List<HollyDay> getHollyDays() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.getNamedQuery("HollyDay.findAll");
                List<HollyDay> ad = query.list();
                return ad;
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

    public String getSalaryPayable(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Salary s WHERE s.due > '0' AND s.employeeId= :emp");
                query.setParameter("emp", new EmployeeDAOImpl().getEmployee(id));
                List<Salary> ad = query.list();
                double sp = 0.0;
                for (Salary salary : ad) {
                    sp += salary.getDue();
                }

                return sp + "";
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

    public String UpdateSalary(int id, double amount, String payType, String bank, Cheque c) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Salary s WHERE s.due > '0' AND s.employeeId= :emp");
                query.setParameter("emp", new EmployeeDAOImpl().getEmployee(id));
                List<Salary> ad = query.list();
                for (Salary salary : ad) {

                    if (amount != 0) {
                        Query query2 = session.createQuery("UPDATE Salary p SET p.due=p.due-:due,p.paid=p.paid+:paid WHERE p.id=:id");
                        query2.setParameter("id", salary.getId());
                        query2.setParameter("due", amount);
                        query2.setParameter("paid", amount);

                        query2.executeUpdate();
                        transaction.commit();

                        SalaryPayment a = new SalaryPayment();
                        a.setPayment(amount);
                        a.setDate(new Date());
                        a.setIsValid(true);
                        a.setSalaryPaymentTypeId(new SalaryPaymentType(Integer.parseInt(payType)));
                        a.setSalaryId(salary);
                        if (payType.equals("3")) {
                            a.setChequeId(c);
                        } else {
                            a.setChequeId(c);
                        }
                        if (payType.equals("1")) {
                            a.setBankAccountsId(null);
                        } else {
                            a.setBankAccountsId(new BankAccounts(Integer.parseInt(bank)));
                        }
                        String result = Save.Save(a);

                        amount -= salary.getDue();
                    }

                }

                return VertecConstants.SUCCESS;
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

    public String deleteHolyday(String id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("DELETE FROM HollyDay where id=:id");
                query.setParameter("id", Integer.parseInt(id));
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

    public String deleteAdvancePayment(String id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("UPDATE Advance SET isValid=:bool WHERE id=:id");
                query.setParameter("bool", false);
                query.setParameter("id", Integer.parseInt(id));
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

    public String deleteDefaultPayment(String id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("DELETE FROM PayrollDefault WHERE id=:id");
                query.setParameter("id", Integer.parseInt(id));
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

    public String UpdateAdvanced(Salary sal) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Double payable = sal.getAdvancePaid();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Advance a WHERE a.due > '0.0'");
                List<Advance> payd = query.list();
                for (Advance advance : payd) {
                    Double ad = advance.getDue();
                    if (payable != 0) {
                        Query query2 = session.createQuery("UPDATE Advance p SET p.due=:due,p.paid=:paid WHERE p.id=:id");
                        query2.setParameter("id", advance.getId());
                        if (ad.equals(payable)) {
                            query2.setParameter("due", 0.0);
                            query2.setParameter("paid", ad);
                            payable = 0.0;
                        } else if (ad > payable) {
                            query2.setParameter("due", ad - payable);
                            query2.setParameter("paid", payable);
                            payable = 0.0;
                        } else if (ad < payable) {
                            query2.setParameter("due", 0.0);
                            query2.setParameter("paid", ad);
                            payable -= ad;
                        }
                        query2.executeUpdate();
                        transaction.commit();
                    }
                }
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

    public String UpdateLoan() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM StaffLoan a WHERE a.balance > '0.0'");
                List<StaffLoan> payd = query.list();
                for (StaffLoan loan : payd) {
                    Query query2 = session.createQuery("UPDATE Loan p SET p.balance=p.balance-:amount,p.paid=p.paid+:amount,p.paidTerm=p.paidTerm+'1' WHERE p.id=:id");
                    query2.setParameter("id", loan.getId());
                    query2.setParameter("amount", loan.getMonthlyPayment());
                    query2.executeUpdate();
                    transaction.commit();

                }
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

    public String GetAdvanceAmount(Employee emp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Advance a WHERE a.due > '0.0' AND a.isValid='1' AND a.employeeId=:employee");
                query.setParameter("employee", emp);
                List<Advance> payd = query.list();
                Double ad = 0.0;
                for (Advance advance : payd) {
                    ad += advance.getDue();
                }
                return ad + "";
            } catch (Exception e) {
                e.printStackTrace();
                return "0.0";
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    public String GetLoanAmount(Employee emp) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM Loan a WHERE a.balance > '0.0' AND a.employeeId=:employee");
                query.setParameter("employee", emp);
                List<StaffLoan> payd = query.list();
                Double ad = 0.0;
                for (StaffLoan loan : payd) {
                    if (loan.getBalance() < loan.getMonthlyPayment()) {
                        ad += loan.getBalance();
                    } else {
                        ad += loan.getMonthlyPayment();
                    }
                }
                return ad + "";
            } catch (Exception e) {
                e.printStackTrace();
                return "0.0";
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    public String SaveDefault(PayrollDefault pd) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT p FROM PayrollDefault p WHERE p.employeeId=:emp");
                query.setParameter("emp", pd.getEmployeeId());
                PayrollDefault payd = (PayrollDefault) query.uniqueResult();
                if (payd == null) {
                    Save.Save(pd);
                    transaction.commit();
                } else {
                    Query query2 = session.createQuery("UPDATE PayrollDefault p SET p.otHr=:ot,p.lateHr=:lh,p.noPayHr=:np WHERE p.employeeId=:emp");
                    query2.setParameter("emp", pd.getEmployeeId());
                    query2.setParameter("ot", pd.getOtHr());
                    query2.setParameter("lh", pd.getLateHr());
                    query2.setParameter("np", pd.getNoPayHr());
                    query2.executeUpdate();
                    transaction.commit();
                }
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

    public List<AllowanceDeduction> getEmployeeAllowance(int eid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM AllowanceDeduction a WHERE a.isValid = '1' AND a.isAllowance='1' AND a.employeeId='" + eid + "' ");
                List<AllowanceDeduction> ad = query.list();
                return ad;
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

    public List<AllowanceDeduction> getEmployeeDeduction(int eid) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT a FROM AllowanceDeduction a WHERE a.isValid = '1' AND a.isAllowance='0' AND a.employeeId='" + eid + "' ");
                List<AllowanceDeduction> ad = query.list();
                return ad;
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

    public List<Object> SalaryCalculation(int EmployeeId, String FromDate, String ToDate, SysUser user) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date From = format.parse(ToDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(From);
        cal.add(Calendar.DATE, 1);
        Date currentDatePlusOne = cal.getTime();

        ToDate=format.format(currentDatePlusOne);
        
        
        
        Employee employee = new EmployeeDAOImpl().getEmployee(EmployeeId);

        double EmployeeLeaves = getEmployeeLeave(employee, FromDate);
        double EmployeeShortLeaves = employee.getEmployeeTypeId().getMonthlyShortLeaves();
        double EmployeemonthlyLeave = employee.getEmployeeTypeId().getMonthlyLeaves();

        int TOTALWORKINGMINUTES = 0;
        int TOTALWORKEDMINUTES = 0;
        int EARLYARRIVALMINUTES = 0;
        int LATEARRIVALMINUTES = 0;
        int EARLYLEAVESMINUTES = 0;
        int LATELEAVEMINUTES = 0;

        int NOPAYLEAVE = 0;

        int LATEDATES = 0;
        double NOPAYDAYS = 0;
        double NOPAYDAYSBEFOREAPOINT = 0;

        int PAYLEAVE = 0;
        int OTMINUTES = 0;
        int LATELEAVE = 0;
        String thisDay = FromDate;
        int TotalDays = getdateCount(FromDate, ToDate);
        for (int i = 0; i < TotalDays; i++) {

            boolean is_Hollyday = true;
            boolean is_Leave = false;
            boolean is_Pay = false;
            int DailyTotalMINUTES = 0;//dawase weda kala yuthu welawa.
            int DailyWorkedMinutes = 0;//dawase weda kala welawa.

            int DailyINTIME = 0;//peminiya yuthu welawa
            int DailyOUTTIME = 0;//pita wiya yuthu welawa
            int DailyLunchTime = 0;//Lunch Sadaha wu kalaya

            int intime = 0;//pemini welawa
            int outtime = 0;//pita wu welawa

            int LateINMinutes = 0;
            int ERLYINMinutes = 0;

            int LateOUTMinutes = 0;
            int ERLYOUTMinutes = 0;

            int OverTimeMinutes = 0;
            int DailyLELeaves = 0;
            int NoPayMinutes = 0;
            int PayMinutes = 0;
            if (!isholyday(thisDay)) {
                is_Hollyday = false;
                //niwadu dawasak newei nam

//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date Currentday = format.parse(thisDay);
                String dayname = new SimpleDateFormat("EEE").format(Currentday);
                int dailydata[] = getDailyWorkingMinutes(dayname);
//            DailyTotalMINUTES = dailydata[0];
                DailyTotalMINUTES = dailydata[2] - dailydata[1] - dailydata[3];

                DailyINTIME = dailydata[1];
                DailyOUTTIME = dailydata[2];
                DailyLunchTime = dailydata[3];

                boolean checkisLeave = false;
                boolean checkispay = false;

                boolean[] boolarr = isLeave(employee, thisDay);

                checkisLeave = boolarr[0];
                checkispay = boolarr[1];

                if (checkisLeave) {
                    is_Leave = true;
                    //Employee niwadunam
                    if (checkispay) {
                        is_Pay = true;
                        //niwaduwata padi hambenawanam
                        PayMinutes += DailyTotalMINUTES;
                    } else {
                        //niwaduwata padiyen kepenawanam.
                        NoPayMinutes += DailyTotalMINUTES;

                    }
                } else {

                    //Employee niwadu neththan
                    int dailydata2[] = getDailyWorkedMinutes(employee, thisDay);
                    intime = dailydata2[0];
                    outtime = dailydata2[1];

                    if ((DailyINTIME - intime) > 0) {
                        //Early Arrival
                        ERLYINMinutes = DailyINTIME - intime;

                    } else if ((DailyINTIME - intime) < 10) {
                        //Late Arrival
                        LateINMinutes = intime - DailyINTIME;
                    } else {
                        //arrive ontime
                    }
                    if ((DailyOUTTIME - outtime) > 10) {
                        //erly leave
                        ERLYOUTMinutes = DailyOUTTIME - outtime;
                    } else if ((DailyOUTTIME - outtime) < 0) {
                        //late leave
                        LateOUTMinutes = outtime - DailyOUTTIME;
                    } else {
                        //leave ontime
                    }

                    DailyWorkedMinutes = outtime - intime;

                    if (DailyTotalMINUTES > 0) {

                        if ((LateINMinutes + ERLYOUTMinutes) >= 480) {
                            NOPAYDAYS += 1;
                            LeaveBalance(EmployeemonthlyLeave, NOPAYDAYS);

                        } else if (((LateINMinutes + ERLYOUTMinutes) < 480) && ((LateINMinutes + ERLYOUTMinutes) >= 60)) {
                            NOPAYDAYS += 0.5;

                        } else if (DailyTotalMINUTES > 0 & DailyWorkedMinutes == 0 & !is_Hollyday & !is_Leave & !is_Pay) {

                            if (employee.getAppoint().before(Currentday)) {
                                if (dayname.equals("Sat")) {
                                    NOPAYDAYS += 1;
                                } else {
                                    NOPAYDAYS += 1;
                                }
                            } else if (dayname.equals("Sat")) {
                                NOPAYDAYS += 0.5;
                            } else {
                                NOPAYDAYS += 1;
                            }
//                            NOPAYDAYS += 1;
                        } else if (LateINMinutes >= 10 && DailyWorkedMinutes > 0) {
                            LATEDATES += 1;
                        }
                    } else if (employee.getAppoint().after(Currentday)) {
                        NOPAYDAYSBEFOREAPOINT += 1;
                    }
                }
                System.out.println("Daily total " + DailyTotalMINUTES);
                System.out.println("Daily worked " + DailyWorkedMinutes);
                System.out.println("is holiday " + is_Hollyday);
                System.out.println("is leave " + is_Leave);
                System.out.println("is pay " + is_Pay);

//                if (DailyTotalMINUTES > 0 & DailyWorkedMinutes == 0 & !is_Hollyday & !is_Leave & !is_Pay) {
//                    System.out.println("________________________________________________________________***");
//                    NOPAYDAYS += 1;
//                        if (TOTALWORKINGMINUTES > 480) {
//                            if ((EmployeeLeaves + EmployeeShortLeaves + EmployeemonthlyLeave) > 0) {
//                                SaveLeaveAuto(employee, user, 1, true, Currentday);
//                            } else {
//                                NOPAYDAYS += 1;
//                            }
//
//                        } else if (TOTALWORKINGMINUTES > 300) {
//                            if ((EmployeeLeaves + EmployeeShortLeaves + EmployeemonthlyLeave) > 0) {
//                                SaveLeaveAuto(employee, user, 0.5, true, Currentday);
//                            } else {
//                                NOPAYDAYS += 1;
//                            }
//
//                        }else{
//                        NOPAYDAYS += 1;
//                        }
//                }
                if (DailyTotalMINUTES == 0) {
                    LateINMinutes = 0;
                    ERLYINMinutes = 0;
                    ERLYOUTMinutes = 0;
                    LateOUTMinutes = 0;
                    NoPayMinutes = 0;
                }

                if (DailyWorkedMinutes == 0) {
                    LateINMinutes = 0;
                    ERLYINMinutes = 0;
                    ERLYOUTMinutes = 0;
                    LateOUTMinutes = 0;
                }

            }

            System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
            System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
            System.out.println("DINAYA : " + thisDay);
            System.out.println("NIWADU DAWASAKDA? : " + is_Hollyday);
            System.out.println("EMPLOYEE NIWADUDA? : " + is_Leave);
            System.out.println("EMPLOYEEGE NIWADUWATA PAY KARANAWADA? : " + is_Pay);
            System.out.println("WEDA KALA YUTHU MINITHTHU GANANA : " + DailyTotalMINUTES);
            System.out.println("WEDA KALA MINITHTHU GANANA : " + DailyWorkedMinutes);
            System.out.println("OT MINITHTHU GANANA : " + OverTimeMinutes);
            System.out.println("LATE ARRIVAL MINITHTHU GANANA : " + DailyLELeaves);
            System.out.println("NO PAY MINITHTHU GANANA : " + NoPayMinutes);
            System.out.println("LATE DATES : " + LATEDATES);
            System.out.println("NO PAY DATES : " + NOPAYDAYS);
            System.out.println("");
            System.out.println("EARLY ARRIVED MINUTES :" + ERLYINMinutes);
            System.out.println("Late ARRIVED MINUTES : " + LateINMinutes);
            System.out.println("EARLY Leaved MINUTES : " + ERLYOUTMinutes);
            System.out.println("LATE LEAVE MINUTES : " + LateOUTMinutes);
            System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
            System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");

            TOTALWORKINGMINUTES += DailyTotalMINUTES;
            TOTALWORKEDMINUTES += DailyWorkedMinutes;
            EARLYARRIVALMINUTES += ERLYINMinutes;
            LATEARRIVALMINUTES += LateINMinutes;
            EARLYLEAVESMINUTES += ERLYOUTMinutes;
            LATELEAVEMINUTES += LateOUTMinutes;
            NOPAYLEAVE += NoPayMinutes;
            OTMINUTES += OverTimeMinutes;
            PAYLEAVE += PayMinutes;
            LATELEAVE += DailyLELeaves;
            thisDay = getNextDate(thisDay);
        }

        System.out.println("#########################################################");
        System.out.println("#########################################################");
        System.out.println("#########################################################");
        System.out.println("#########################################################");
        int nopayhalf = LATEDATES / 3;
        System.out.println("LATE DATES COUNT : " + LATEDATES);
        System.out.println("HALFDAYS FOR NOPAY : " + nopayhalf);
        System.out.println("NOPAY FOR LEAVES : " + NOPAYDAYS);
        
        double latenopay = 0;
        if((Double.parseDouble(nopayhalf + "") / 2)>=EmployeemonthlyLeave){
            latenopay = (Double.parseDouble(nopayhalf + "") / 2) - EmployeemonthlyLeave;
        }
        

//        NOPAYDAYS += nopayhalf / 2;
        System.out.println("FULL DAYS FOR Late NOPAY : " + latenopay);
        NOPAYDAYS = NOPAYDAYS + latenopay;
        System.out.println("Total : " + NOPAYDAYS);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date From_Date = format.parse(FromDate);

//        if (employee.getAppoint().before(From_Date)) {
//            if (NOPAYDAYS > (EmployeemonthlyLeave)) {
//                NOPAYDAYS -= (EmployeemonthlyLeave);
//            } else {
//                NOPAYDAYS = 0;
//            }
//        }
        NOPAYDAYS += NOPAYDAYSBEFOREAPOINT;
        System.out.println("Monthly Leaves: " + EmployeemonthlyLeave);

        System.out.println("Current NOPAY DAYS : " + NOPAYDAYS);
        System.out.println("Current Day : " + thisDay);
        System.out.println("#########################################################");
        System.out.println("#########################################################");
        System.out.println("#########################################################");
        System.out.println("#########################################################");

        Double TotalSalary = 0.0;
        List<AllowanceDeduction> allowance = getEmployeeAllowance(EmployeeId);
        List<AllowanceDeduction> deduction = getEmployeeDeduction(EmployeeId);
        List<PayrollDefault> defaultpayment = getDefaultPayments(employee);

        double allo = 0;
        double deduc = 0;
        for (AllowanceDeduction e : allowance) {
            allo += e.getValue();
        }

        for (AllowanceDeduction e : deduction) {
            deduc += e.getValue();
        }

        double othr = 0;
        double latehr = 0;
        double nphr = 0;
        for (PayrollDefault e : defaultpayment) {
            latehr = e.getLateHr();
            nphr = e.getNoPayHr();
            othr = e.getOtHr();
        }
        Double otpay2 = (Double.parseDouble(OTMINUTES + "") / 60) * othr;
        Double lateded2 = (Double.parseDouble(LATELEAVE + "") / 60) * latehr;
        Double npded2 = (Double.parseDouble(NOPAYLEAVE + "") / 60) * nphr;

        Double otpay = Math.round(otpay2 * 100) / 100.00;
        Double lateded = Math.round(lateded2 * 100) / 100.00;
//        Double npded = Math.round(npded2 * 100) / 100.00;

        Employee em = new EmployeeDAOImpl().getEmployee(EmployeeId);

//        double npforepf = (em.getBasicSalary() / (em.getBasicSalary() + (allo))) * npded;
//            NOPAYDAYS=0.4;
        double npforepf = (em.getBasicSalary() / 30) * NOPAYDAYS;
        double npforallowance = (allo / 30) * NOPAYDAYS;
        double npded3 = (em.getBasicSalary() + allo / 30) * NOPAYDAYS;
        Double npded = Math.round((npforallowance + npforepf) * 100) / 100.00;

        System.out.println("NOPAY SALARY " + npforepf);
        System.out.println("NOPAY Allowance " + npforallowance);

        Double etf = 0.0;
        Double epf = 0.0;
        Double epfp = 0.0;

        if (employee.getEmployeeTypeId().getEtfEpf()) {
            etf = Math.round(((em.getBasicSalary() - npforepf) * 0.03) * 100) / 100.00;
            epf = Math.round(((em.getBasicSalary() - npforepf) * 0.08) * 100) / 100.00;
            epfp = Math.round(((em.getBasicSalary() - npforepf) * 0.12) * 100) / 100.00;
        }

        System.out.println("******************* EPF ********************");
        System.out.println("**************************************************");
        System.out.println(epf);
        System.out.println("(" + em.getBasicSalary() + "-" + npded + ")*" + 0.08);
        System.out.println(epf);
        System.out.println("**************************************************");
        System.out.println("**************************************************");

        System.out.println("******************* ETF / EPF ********************");
        System.out.println("**************************************************");
        System.out.println(etf);
        System.out.println(epf);
        System.out.println(epfp);
        System.out.println("**************************************************");
        System.out.println("**************************************************");

        String s0 = em.getFname() + " " + em.getLname();
        String s1 = em.getNic();
        String s2 = ParseMinutestoHour(TOTALWORKINGMINUTES + "");
        String s3 = ParseMinutestoHour(TOTALWORKEDMINUTES + "");
        String s4 = ParseMinutestoHour(EARLYARRIVALMINUTES + "");
        String s5 = ParseMinutestoHour(LATEARRIVALMINUTES + "");
        String s6 = ParseMinutestoHour(EARLYLEAVESMINUTES + "");
        String s7 = ParseMinutestoHour(LATELEAVEMINUTES + "");
        String s8 = ParseMinutestoHour(NOPAYLEAVE + "");
        String s9 = ParseMinutestoHour(PAYLEAVE + "");
        String s10 = ParseMinutestoHour(OTMINUTES + "");

        String s11 = em.getBasicSalary() + "";
        String s12 = allo + "";
        String s13 = deduc + "";
        String s14 = lateded + "";
        String s15 = npded + "";
        String s16 = otpay + "";
        String s17 = etf + "";
        String s18 = epf + "";
        String s19 = epfp + "";
        TotalSalary = em.getBasicSalary() + allo + otpay - deduc - lateded - npded - epf;
        String s20 = TotalSalary + "";
        String s21 = em.getId() + "";
        String s22 = TOTALWORKINGMINUTES + "";
        String s23 = TOTALWORKEDMINUTES + "";
        String s24 = EARLYARRIVALMINUTES + "";
        String s25 = LATEARRIVALMINUTES + "";
        String s26 = EARLYLEAVESMINUTES + "";
        String s27 = LATELEAVEMINUTES + "";
        String s28 = NOPAYLEAVE + "";
        String s29 = PAYLEAVE + "";
        String s30 = OTMINUTES + "";
        String s31 = GetAdvanceAmount(employee);
        String s32 = GetLoanAmount(employee);
        System.out.println(s32);

        List<Object> FullArray = new ArrayList<>();

        
        
        cal.setTime(From);
        cal.add(Calendar.DATE, 0);
        currentDatePlusOne = cal.getTime();
        ToDate=format.format(currentDatePlusOne);
        
        
        
        String arr[] = {s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, FromDate, ToDate, s21, s22, s23, s24, s25, s26, s27, s28, s29, s30, s31, s32};

        FullArray.add(allowance);
        FullArray.add(deduction);
        FullArray.add(arr);

        return FullArray;
    }

    public String getNextDate(String curDate) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            final Date date = format.parse(curDate);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public boolean isholyday(String date) {
        try {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.getNamedQuery("HollyDay.findByDate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            query.setParameter("date", format.parse(date));
            HollyDay payd = (HollyDay) query.uniqueResult();
            if (payd != null) {
                if (payd.getHollydayId() > 0) {
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + payd.getHollydayId());
                    session.close();
                    return true;
                } else {
                    session.close();
                    return false;
                }
            } else {
                session.close();
                return false;
            }
        } catch (ParseException ex) {
            Logger.getLogger(SalaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int[] getDailyWorkingMinutes(String day) {

        String gday = "sunday";
        if (day.equals("Mon")) {
            gday = "monday";
        } else if (day.equals("Tue")) {
            gday = "tuesday";
        } else if (day.equals("Wed")) {
            gday = "wednesday";
        } else if (day.equals("Thu")) {
            gday = "thursday";
        } else if (day.equals("Fri")) {
            gday = "friday";
        } else if (day.equals("Sat")) {
            gday = "saturday";
        }
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query = session.getNamedQuery("WorkingDays.findByDay");
        query.setParameter("day", gday);
        WorkingDays payd = (WorkingDays) query.uniqueResult();
        int minutes = Integer.parseInt((payd.getTotalHours().getTime() / 60000) + "");
        int intime = Integer.parseInt((payd.getIntime().getTime() / 60000) + "");
        int outtime = Integer.parseInt((payd.getOuttime().getTime() / 60000) + "");
        int lunch = payd.getLunchMinutes();

        int data[] = {minutes, intime, outtime, lunch};

        return data;

    }

    public int[] getDailyWorkedMinutes(Employee employee, String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("SELECT a FROM Attendance a WHERE a.employeeId = :id AND a.date=:date");
            query.setParameter("date", format.parse(date));
            query.setParameter("id", employee);
            Attendance payd = (Attendance) query.uniqueResult();
            int intime = 0;
            int outtime = 0;
            if (payd != null) {
                intime = Integer.parseInt((payd.getIntime().getTime() / 60000) + "");
                if (payd.getOuttime() == null) {
                    outtime = Integer.parseInt((payd.getIntime().getTime() / 60000) + "");
                } else {
                    outtime = Integer.parseInt((payd.getOuttime().getTime() / 60000) + "");
                }
            }
            int[] data = {intime, outtime};

            return data;
        } catch (ParseException ex) {
            Logger.getLogger(SalaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public double[] LeaveBalance(Double EmployeemonthlyLeave, Double NOPAYDAYS) {

        double[] arr = {};
        if (1 <= EmployeemonthlyLeave) {
            EmployeemonthlyLeave -= 1;
        } else {
            NOPAYDAYS += (1 - EmployeemonthlyLeave);
            EmployeemonthlyLeave -= (1 - EmployeemonthlyLeave);
        }

        return arr;
    }

    public boolean[] isLeave(Employee employee, String date) {

        try {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
//            Query query = session.createQuery("SELECT l FROM Leaves l WHERE l.fromdate <= :date AND l.todate >=:date AND l.employeeId=:id AND l.isApproved=:approve");
            Query query = session.createQuery("SELECT l FROM Leaves l WHERE l.employeeId=:employeeid AND l.isApproved=:approve AND :date BETWEEN l.fromdate and l.todate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println(format.parse(date));
            System.out.println(employee.getFname());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

            query.setParameter("date", format.parse(date));
            query.setParameter("employeeid", employee);
            query.setParameter("approve", true);

            Leaves payd = (Leaves) query.uniqueResult();

            if (payd != null) {
                if (payd.getId() > 0) {
                    boolean[] arr2 = {true, payd.getIsPay()};
                    session.close();
                    return arr2;

                } else {
                    session.close();
                    boolean[] arr = {false, false};
                    return arr;
                }
            } else {
                session.close();
                boolean[] arr = {false, false};
                return arr;
            }
        } catch (ParseException ex) {
            Logger.getLogger(SalaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean[] arr = {false, false};
        return arr;
    }

    public boolean isPayable(int eId, String date) {
        try {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("SELECT l FROM Leaves l WHERE l.fromdate <= :date AND l.todate >=:date AND l.employeeId=:id AND l.isApproved=:approve");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            query.setParameter("date", format.parse(date));
            query.setParameter("id", new EmployeeDAOImpl().getEmployee(eId));
            query.setParameter("approve", true);
            Leaves payd = (Leaves) query.uniqueResult();
            if (payd != null) {
                if (payd.getId() > 0) {
                    session.close();
                    return true;
                } else {
                    session.close();
                    return false;
                }
            } else {
                session.close();
                return false;
            }
        } catch (ParseException ex) {
            Logger.getLogger(SalaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void SaveLeaveAuto(Employee employee, SysUser user, double leaves, boolean is_pay, Date date) {

        Leaves e = new Leaves();
        e.setDays(leaves);
        e.setType("Casual Leave");
        e.setDescription("Added By System");
        e.setFromdate(date);
        e.setTodate(date);
        e.setIsApproved(null);
        e.setIsApproved(true);
        e.setEmployeeId(employee);
        e.setAddedBy(user);
        e.setIsPay(is_pay);
        Save.Save(e);

    }

    public double getEmployeeLeave(Employee employee, String from) {

        Date from2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            from2 = format.parse(from);
        } catch (ParseException ex) {
            Logger.getLogger(SalaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
//        
        double leaves = 0;
        System.out.println(employee);
        System.out.println(employee.getEmployeeTypeId().getAnnualLeaves());
        if (employee.getEmployeeTypeId().getAnnualLeaves() != null) {
            if (employee.getEmployeeTypeId().getAnnualLeaves() > 0) {
                if (employee.getAppoint() != null) {
                    Date appoint = employee.getAppoint();
                    Date current = new Date();
                    if (current.getYear() == appoint.getYear()) {
//                        current date is before appoint
                        if (appoint.getMonth() <= 3) {
                            leaves = 14;
                        } else if (appoint.getMonth() <= 6) {
                            leaves = 10;
                        } else if (appoint.getMonth() <= 9) {
                            leaves = 7;
                        } else {
                            leaves = 4;
                        }
                    } else {
                        leaves = employee.getEmployeeTypeId().getAnnualLeaves();
                    }
                }
            }
        }

        double CurrentLeaves = 0;

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT SUM(l.days) FROM Leaves l WHERE l.fromdate <= :date AND l.employeeId=:id AND l.isApproved=:approve");
        query.setParameter("date", from2);
        query.setParameter("id", employee);
        query.setParameter("approve", true);
        if (query.uniqueResult() != null) {
            long payd = (long) query.uniqueResult();

            if (payd != 0) {
                CurrentLeaves = payd;

            } else {
                session.close();
            }
        }
        return leaves - CurrentLeaves;
    }

    public int getdateCount(String fromdate, String toDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date aDate = formatter.parse(fromdate);
            Date bDate = formatter.parse(toDate);

            Calendar with = Calendar.getInstance();
            with.setTime(aDate);
            Calendar to = Calendar.getInstance();
            to.setTime(bDate);
            to.set(Calendar.YEAR, with.get(Calendar.YEAR));
            int withDAY = with.get(Calendar.DAY_OF_YEAR);
            int toDAY = to.get(Calendar.DAY_OF_YEAR);

            int diffDay = toDAY - withDAY;
            return diffDay;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public String ParseMinutestoHour(String minutes) {
        int minute = Integer.parseInt(minutes + "");

        int hour = minute / 60;
        minute -= (hour * 60);

        if (minute < 0) {
            hour--;
            minute -= (hour * 60);
        }
        return hour + "Hr " + minute + "Min";
    }

    public String ParseTimetoMinutes(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date aDate = formatter.parse(date);

            return (aDate.getTime() / 60000) + "";
        } catch (ParseException ex) {
            Logger.getLogger(SalaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static int compareDatesByCompareTo(Date oldDate, Date newDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //how to check if date1 is equal to date2
        if (oldDate.compareTo(newDate) == 0) {
            System.out.println(df.format(oldDate) + " and " + df.format(newDate) + " are equal to each other");
            return 0;
        }

        //checking if date1 is less than date 2
        if (oldDate.compareTo(newDate) < 0) {
            System.out.println(df.format(oldDate) + " is less than " + df.format(newDate));
            return -1;
        }

        //how to check if date1 is greater than date2 in java
        if (oldDate.compareTo(newDate) > 0) {
            System.out.println(df.format(oldDate) + " is greater than " + df.format(newDate));
            return 1;
        }
        return 0;
    }

}
