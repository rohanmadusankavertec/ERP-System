<%-- 
    Document   : PrintAsset
    Created on : Nov 25, 2016, 12:40:30 PM
    Author     : vertec-r
--%>

<%@page import="org.hibernate.Query"%>
<%@page import="com.vertec.hibe.model.Account"%>
<%@page import="java.util.Date"%>
<%@page import="com.vertec.hibe.model.Transaction"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Management System </title>
        <link href="resources/css/bootstrap.min.css" rel="stylesheet">
        <style type="text/css">
            table {
                border-collapse: collapse;
            }
            td, th {
                border: 1px solid #999;
                text-align: left;
                padding: 0.5rem;
            }
        </style>
    </head>
    <body onload="window.print();">
    <center>
        <h1>Ledger Accounts</h1>
        <%
            Session ses = NewHibernateUtil.getSessionFactory().openSession();
            List<Account> account = (List<Account>) request.getAttribute("account");
            Date from = (Date) request.getAttribute("from");
            Date to = (Date) request.getAttribute("to");
        %>

        <h5>(Calculated Date <%=new Date()%>)</h5>
        <h5>Calculated From <%=from%> To <%=to%></h5>



        <%
            for (Account acc : account) {
                Double openbal = acc.getBalance();
                double bf = 0.0;
                double bb = 0.0;

                Double debit = 0.0;
                Double credit = 0.0;
                Query query3 = ses.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.debit = :account AND t.date < :date");
                query3.setParameter("account", acc);
                query3.setParameter("date", from);
                Object ob = query3.uniqueResult();
                if (ob == null) {
                } else {
                    debit = (Double) ob;
                }
                Query query4 = ses.createQuery("SELECT SUM(t.price) FROM Transaction t WHERE t.credit = :account AND t.date < :date");
                query4.setParameter("account", acc);
                query4.setParameter("date", from);
                Object ob2 = query4.uniqueResult();
                if (ob2 == null) {
                } else {
                    credit = (Double) ob2;
                }
                Double balanceback = debit - credit;


        %>
        <div class="clearfix"></div>

        <div style="width: 90%;" >

            <div style="width: 100%;"><h3><%=acc.getName()%></h3></div>

            <div style="width: 50%; float: left;">
                <h3><span style="float: left;">Debit</span></h3>
                <table style="width: 100%;border: 1px solid black;" >
                    <thead>
                        <tr>
                            <td>Date</td>
                            <td>Account</td>
                            <td>Amount</td>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            Query query = ses.createQuery("SELECT t FROM Transaction t WHERE t.debit = :account AND t.date BETWEEN :from AND :to");
                            query.setParameter("account", acc);
                            query.setParameter("from", from);
                            query.setParameter("to", to);
                            List<Transaction> Debitamount = query.list();
                            for (Transaction d : Debitamount) {%>
                        <tr style="font-size: 12px;">
                            <td><%=d.getDate()%></td>
                            <td><%=d.getDescription()%></td>
                            <td><%=d.getPrice()%></td>
                            <%
                                bf += d.getPrice();
                            %>
                        </tr>
                        <%}%>
                        <tr>
                            <td colspan="2">Debit Total</td>
                            <td><%=bf%></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div style="  width: 50%; float: left;">
                <h3><span style="float: right;">Credit</span></h3>
                <table style="width: 100%;border: 1px solid black;">
                    <thead>
                        <tr>
                            <td style="width: 70px;">Date</td>
                            <td>Description</td>
                            <td>Amount</td>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            Query query1 = ses.createQuery("SELECT t FROM Transaction t WHERE t.credit = :account AND t.date BETWEEN :from AND :to");
                            query1.setParameter("account", acc);
                            query1.setParameter("from", from);
                            query1.setParameter("to", to);
                            List<Transaction> creditamount = query1.list();
                            for (Transaction d : creditamount) {%>
                        <tr style="font-size: 12px;">
                            <td><%=d.getDate()%></td>
                            <td><%=d.getDescription()%></td>
                            <td><%=d.getPrice()%></td>
                            <%
                                bb += d.getPrice();
                            %>
                        </tr>
                        <%}%>
                        <tr>
                            <td colspan="2">Credit Total</td>
                            <td><%=bb%></td>
                        </tr>
                        <tr>
                            <td colspan="2">Debit Total</td>
                            <td><%=bf%></td>
                        </tr>
                        <tr>
                            <td colspan="2">Opening Balance</td>
                            <td><%=openbal%></td>
                        </tr>
                        <tr>
                            <td colspan="2">Balance Backward</td>
                            <td><%=balanceback%></td>
                        </tr>
                        <tr>
                            <td colspan="2"><strong>Balance Forward</strong></td>
                            <td><strong><%=bf - bb + openbal + balanceback%></strong></td>
                        </tr>
                    </tbody>
                </table>
            </div>      
        </div>
<div class="clearfix"></div>
        <div style="width: 100%; height: 50px; ">

        </div>
<div class="clearfix"></div>
        <%
            }
        %>





























    </center>

</body>
</html>
