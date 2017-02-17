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
        <p>Calculated Date: <%=date %></p>
        <h4 style="margin-top: -10px;">Calculated From <%=from%> To <%=to%></h4>
        <table style="width: 90%"  class="table" border="1">
            <thead>
                <tr>
                    <th>Description</th>
                    <th>Income</th>
                    <th>Expenses</th>
                </tr>
            </thead>
            <tbody>
                <%                                            
                    Double debit = 0.0;
                    Double credit = 0.0;
                    for (String[] a : arr) {
                %>
                <tr>
                    <td><%=a[0]%></td>
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
                <%
                    for (DepreciationData d : dep) {
                        debit+=d.getDepreciation().getAmount()-d.getAmount()-d.getBeforedep();
                %>
                <tr>
                    <td><%=d.getAccount().getName() %></td>
                    
                    <td><%=d.getDepreciation().getAmount()-d.getAmount()-d.getBeforedep() %></td>
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
