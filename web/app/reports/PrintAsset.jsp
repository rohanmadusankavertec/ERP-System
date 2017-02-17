<%-- 
    Document   : PrintAsset
    Created on : Nov 25, 2016, 12:40:30 PM
    Author     : vertec-r
--%>

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
    <body onload="window.print();" >
    <center>
        <%
            String accname = (String) request.getAttribute("accname");
            String from = (String) request.getAttribute("from");
            String to = (String) request.getAttribute("to");
            String date = (String) request.getAttribute("date");
            Double openbal = (Double) request.getAttribute("openbal");
            Double balanceback = (Double) request.getAttribute("balanceback");
            List<Transaction> credit = (List<Transaction>) request.getAttribute("credit");
            List<Transaction> Debit = (List<Transaction>) request.getAttribute("debit");
            double bf = 0.0;
            double bb = 0.0;
        %>

        <h1><%=accname%></h1>
        <h5>(Calculated Date <%=date%>)</h5>
        <h5 style="margin-top: -10px;">Calculated From <%=from%> To <%=to%></h5>
        <br>
        <div style="width: 90%">
            <div style="height: 200px; width: 45%; float: left;">
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
                    <% for (Transaction d : Debit) {%>
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
            <div style=" height: 500px; width: 45%; float: left;">
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
                    <% for (Transaction d : credit) {%>
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
                        <td><%=openbal %></td>
                    </tr>
                    <tr>
                        <td colspan="2">Balance Backward</td>
                        <td><%=balanceback %></td>
                    </tr>
                    <tr>
                        <td colspan="2"><strong>Balance Forward</strong></td>
                        <td><strong><%=bf-bb+openbal+balanceback %></strong></td>
                    </tr>
                    </tbody>
                </table>


            </div>




        </div>

    </center>

</body>
</html>
