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
        <title>Income Statement</title>
    </head>
    <body onload="window.print()">
    <center>
        <h1>Income Statement</h1>
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


                <%
                    Double debit = 0.0;
                    Double credit = 0.0;
                    
                    Double tdebit = 0.0;
                    Double tcredit = 0.0;
                    
                    for (String[] a : arr) {
                %>
                <%if (a[2].equals("Purchase")) {%>
                <%
                    if (Double.parseDouble(a[1]) < 0) {
                        tcredit += ((-1) * Double.parseDouble(a[1]));
                %>

                <%
                } else {
                    tdebit += Double.parseDouble(a[1]);
                %>

                <%
                    }
                %>

                <%}%>
                <%}%>
                <tr>
                    <td><span style="margin-left: 20px;">Purchasing</span></td>
                    <%
                        
                        Double tot = tdebit - tcredit;
                        if (tot < 0) {
                    %>
                    <td></td>
                    <td><%=tot * (-1)%></td>
                    <%
                    } else {
                    %>
                    <td><%=tot%></td>
                    <td></td>
                    <%}%>
                </tr>








                <%
                    credit+=tcredit;
                    debit+=tdebit;
                    tdebit = 0.0;
                    tcredit = 0.0;
                    for (String[] a : arr) {
                %>
                <%if (a[2].equals("Sell")) {%>
                <%
                    if (Double.parseDouble(a[1]) < 0) {
                        tcredit += ((-1) * Double.parseDouble(a[1]));
                %>

                <%
                } else {
                    tdebit += Double.parseDouble(a[1]);
                %>

                <%
                    }
                %>

                <%}%>
                <%}%>
                <tr>
                    <td><span style="margin-left: 20px;">Sales</span></td>
                    <%
                        tot = tdebit - tcredit;
                        if (tot < 0) {
                    %>
                    <td></td>
                    <td><%=tot * (-1)%></td>
                    <%
                    } else {
                    %>
                    <td><%=tot%></td>
                    <td></td>
                    <%}%>
                </tr>






                <%
                    credit+=tcredit;
                    debit+=tdebit;
                    tdebit = 0.0;
                    tcredit = 0.0;
                    for (String[] a : arr) {
                %>
                <%if (a[2].equals("Income")) {%>
                <%
                    if (Double.parseDouble(a[1]) < 0) {
                        tcredit += ((-1) * Double.parseDouble(a[1]));
                %>

                <%
                } else {
                    tdebit += Double.parseDouble(a[1]);
                %>

                <%
                    }
                %>

                <%}%>
                <%}%>
                <tr>
                    <td><span style="margin-left: 20px;">Other Income</span></td>
                    <%
                        tot = tdebit - tcredit;
                        if (tot < 0) {
                    %>
                    <td></td>
                    <td><%=tot * (-1)%></td>
                    <%
                    } else {
                    %>
                    <td><%=tot%></td>
                    <td></td>
                    <%}%>
                </tr>





                <%
                    credit+=tcredit;
                    debit+=tdebit;
                    tdebit = 0.0;
                    tcredit = 0.0;
                    for (String[] a : arr) {
                %>
                <%if (a[2].equals("Expense")) {%>
                <%
                    if (Double.parseDouble(a[1]) < 0) {
                        tcredit += ((-1) * Double.parseDouble(a[1]));
                %>

                <%
                } else {
                    tdebit += Double.parseDouble(a[1]);
                %>

                <%
                    }
                %>

                <%}%>
                <%}%>
                <tr>
                    <td><span style="margin-left: 20px;">Other Expenses</span></td>
                    <%
                        tot = tdebit - tcredit;
                        if (tot < 0) {
                    %>
                    <td></td>
                    <td><%=tot * (-1)%></td>
                    <%
                    } else {
                    %>
                    <td><%=tot%></td>
                    <td></td>
                    <%}%>
                </tr>






                <%
                    credit+=tcredit;
                    debit+=tdebit;
                    tdebit = 0.0;
                    tcredit = 0.0;
                    for (String[] a : arr) {
                %>
                <%if (!a[2].equals("Expense") && !a[2].equals("Income") && !a[2].equals("Sell") && !a[2].equals("Purchase")) {%>
                <%
                    if (Double.parseDouble(a[1]) < 0) {
                        tcredit += ((-1) * Double.parseDouble(a[1]));
                %>

                <%
                } else {
                    tdebit += Double.parseDouble(a[1]);
                %>

                <%
                    }
                %>

                <%}%>
                <%}%>
                <tr>
                    <td><span style="margin-left: 20px;">Other</span></td>
                    <%
                        tot = tdebit - tcredit;
                        if (tot < 0) {
                    %>
                    <td></td>
                    <td><%=tot * (-1)%></td>
                    <%
                    } else {
                    %>
                    <td><%=tot%></td>
                    <td></td>
                    <%}%>
                </tr>








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
