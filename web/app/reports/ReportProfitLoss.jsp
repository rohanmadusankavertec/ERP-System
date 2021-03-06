<%-- 
    Document   : ReportProfitLoss
    Created on : Nov 26, 2016, 9:14:56 AM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="java.util.Date"%>
<%@page import="com.vertec.hibe.model.DepreciationData"%>
<%@page import="java.util.List"%>
<%@page import="com.vertec.hibe.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Account Management System </title>

<%
//    List<Account> acList = (List<Account>) request.getAttribute("aalist");
    List<String[]> arr = (List<String[]>) request.getAttribute("arr");
    List<DepreciationData> dep = (List<DepreciationData>) request.getAttribute("dep");
    Date date = (Date) request.getAttribute("date");
    String from = (String) request.getAttribute("from");
    String to = (String) request.getAttribute("to");

%>
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

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profit And Loss</title>
    </head>
    <body onload="window.print()">
    <center>
        <h1>Profit And Loss</h1>
        <p>Calculated Date: <%=date%></p>
        <h4 style="margin-top: -10px;">Calculated From <%=from%> To <%=to%></h4>
        <table style="width: 90%"  class="table" border="1">
            <thead>
                <tr>
                    <th style="text-align: center;">Description</th>
                    <th style="text-align: center;">Income</th>
                    <th style="text-align: center;">Expenses</th>
                </tr>
            </thead>
            <tbody>





                <tr>
                    <td colspan="3"><strong>Purchasing </strong></td>
                </tr>
                <%
                    Double debit = 0.0;
                    Double credit = 0.0;
                    for (String[] a : arr) {
                %>
                <%if (a[2].equals("Purchase")) {%>
                <tr>
                    <td> <span style="margin-left: 20px;"><%=a[0]%></span></td>
                    <%
                        if (Double.parseDouble(a[1]) < 0) {
                            credit += ((-1) * Double.parseDouble(a[1]));
                    %>
                    <td></td>
                    <td><%=((-1) * Double.parseDouble(a[1]))%></td>
                    <%
                    } else {
                        debit += Double.parseDouble(a[1]);
                    %>
                    <td><%=a[1]%></td>
                    <td></td>
                    <%
                        }
                    %>
                </tr>

                <%}%>
                <%}%>



                <tr>
                    <td colspan="3"><strong>Sales </strong></td>
                </tr>
                <%
                    for (String[] a : arr) {
                %>
                <%if (a[2].equals("Sell")) {%>
                <tr>
                    <td><span style="margin-left: 20px;"><%=a[0]%></span></td>
                    <%
                        if (Double.parseDouble(a[1]) < 0) {
                            credit += ((-1) * Double.parseDouble(a[1]));
                    %>
                    <td></td>
                    <td><%=((-1) * Double.parseDouble(a[1]))%></td>
                    <%
                    } else {
                        debit += Double.parseDouble(a[1]);
                    %>
                    <td><%=a[1]%></td>
                    <td></td>
                    <%
                        }
                    %>
                </tr>

                <%}%>
                <%}%>



                <tr>
                    <td colspan="3"><strong>Other Income </strong></td>
                </tr>
                <%
                    for (String[] a : arr) {
                %>
                <%if (a[2].equals("Income")) {%>
                <tr>
                    <td><span style="margin-left: 20px;"><%=a[0]%></span></td>
                    <%
                        if (Double.parseDouble(a[1]) < 0) {
                            credit += ((-1) * Double.parseDouble(a[1]));
                    %>
                    <td></td>
                    <td><%=((-1) * Double.parseDouble(a[1]))%></td>
                    <%
                    } else {
                        debit += Double.parseDouble(a[1]);
                    %>
                    <td><%=a[1]%></td>
                    <td></td>
                    <%
                        }
                    %>
                </tr>

                <%}%>
                <%}%>



                <tr>
                    <td colspan="3"><strong>Other Expenses </strong></td>
                </tr>
                <%
                    for (String[] a : arr) {
                %>
                <%if (a[2].equals("Expense")) {%>
                <tr>
                    <td><span style="margin-left: 20px;"><%=a[0]%></span></td>
                    <%
                        if (Double.parseDouble(a[1]) < 0) {
                            credit += ((-1) * Double.parseDouble(a[1]));
                    %>
                    <td></td>
                    <td><%=((-1) * Double.parseDouble(a[1]))%></td>
                    <%
                    } else {
                        debit += Double.parseDouble(a[1]);
                    %>
                    <td><%=a[1]%></td>
                    <td></td>
                    <%
                        }
                    %>
                </tr>

                <%}%>
                <%}%>

                <tr>
                    <td colspan="3"><strong>Other </strong></td>
                </tr>
                <%
                    for (String[] a : arr) {
                %>
                <%if (!a[2].equals("Expense") && !a[2].equals("Income") && !a[2].equals("Sell") && !a[2].equals("Purchase")) {%>
                <tr>
                    <td><span style="margin-left: 20px;"><%=a[0]%></span></td>
                    <%
                        if (Double.parseDouble(a[1]) < 0) {
                            credit += ((-1) * Double.parseDouble(a[1]));
                    %>
                    <td></td>
                    <td><%=((-1) * Double.parseDouble(a[1]))%></td>
                    <%
                    } else {
                        debit += Double.parseDouble(a[1]);
                    %>
                    <td><%=a[1]%></td>
                    <td></td>
                    <%
                        }
                    %>
                </tr>

                <%}%>
                <%}%>





                <%
                    for (DepreciationData d : dep) {

                        double depreamount = 0;
                        double damount = 0;
                        double Beforedep = 0;

                        if (d.getDepreciation().getAmount() != null) {
                            depreamount = d.getDepreciation().getAmount();
                        }
                        if (d.getAmount() != null) {
                            damount = d.getAmount();
                        }
                        if (d.getBeforedep() != null) {
                            Beforedep = d.getBeforedep();
                        }

                        debit += depreamount - damount - Beforedep;
//                        debit+=d.getDepreciation().getAmount()-d.getAmount()-d.getBeforedep();
%>
                <tr>
                    <td><%=d.getAccount().getName()%></td>

                    <td><%=depreamount - damount - Beforedep%></td>
                    <td></td>
                </tr>
                <%

                    }
                %>
                <tr>
                    <td><strong>Balance</strong></td>
                    <td><strong><%=debit%></strong></td>
                    <td><strong><%=credit%></strong></td>
                </tr>
                <tr>
                    <td colspan="2"> <strong>Net Profit</strong></td>
                    <td><strong><%=debit - credit%></strong></td>
                </tr>
            </tbody>
        </table>
    </center>    
</body>
</html>
