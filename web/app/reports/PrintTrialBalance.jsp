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
    <body onload="window.print()">
    <center>
        <%
            String from = (String) request.getAttribute("from");
            String to = (String) request.getAttribute("to");
            String date = (String) request.getAttribute("date");
            List<String[]> arr = (List<String[]>) request.getAttribute("data");
        %>

        <h1>Trial Balance</h1>
        <h4>(Calculated Date <%=date%>)</h4>
        <h4 style="margin-top: -10px;">Calculated From <%=from%> To <%=to%></h4>
        <br>
        <div style="width: 90%;">
            <table style="width: 90%;">
                <thead>
                    <tr style="font-size: 18px;">
                        <th><strong>Description</strong></th>
                        <th style="width: 10%;"><strong>Debit</strong></th>
                        <th style="width: 10%;"><strong>Credit</strong></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        double debit = 0.0;
                        double credit = 0.0;

                        for (String[] a : arr) {

                            double amount = Double.parseDouble(a[1].toString());

                            boolean gate = false;
                            if (amount < 0) {
                                gate = true;
                            }
                    %>
                    <tr>
                        <td><%= a[0]%></td>
                        <%
                            if (gate) {
                                credit += amount;
                        %>
                        <td></td>
                        <td><%= (amount * (-1))%></td>
                        <%
                        } else {
                            debit+= amount;
                        %>
                        <td><%=amount%></td>
                        <td></td>
                        <% 
                     }
                        %>
                    </tr>

                    <%
                                                }
                    %>
                    <tr>
                        <td><strong>Balance</strong></td>
                        <td><strong><%=debit%></strong></td>
                        <td><strong><%=credit*(-1)%></strong></td>
                    </tr>


                </tbody>
            </table>

        </div>

    </center>

</body>
</html>
