<%-- 
    Document   : ReportJounal
    Created on : Nov 25, 2016, 3:54:23 PM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="java.util.List"%>
<%@page import="com.vertec.hibe.model.Transaction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<title>Account Management System </title>

<%
     List<Transaction> tList =(List<Transaction>) request.getAttribute("transaction");
     String date =(String) request.getAttribute("date");
     String from = (String) request.getAttribute("from");
            String to = (String) request.getAttribute("to");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Management System </title>
    </head>
    <body onload="window.print();">
    <center>
        <h1>General Journal Entries</h1>
        <p>Calculated Date:<%=date %></p>
        <h4 style="margin-top: -10px;">Calculated From <%=from%> To <%=to%></h4>
        <table style="width: 90%;border-collapse: collapse;" class="table" id="maintable">
                                    <thead>
                                        <tr>
                                            <th style="border: 1px solid black;">Date</th>
                                            <th style="border: 1px solid black;">Description</th>
                                            <th style="border: 1px solid black;">Debit</th>
                                            <th style="border: 1px solid black;">Credit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                         <%
                                        for(Transaction t : tList){
                                        %>
                                        <tr>
                                            <td style="border: 1px solid black;"><%=t.getDate() %></td>
                                            <td style="border: 1px solid black;">
                                                <table>
                                                    <tr style="height: 25px;">
                                                        <td><%=t.getDebit().getName()%></td>
                                                    </tr>
                                                    <tr style="height: 25px;">
                                                        <td><%=t.getCredit().getName()%></td>
                                                    </tr>
                                                    <tr style="height: 70px;">
                                                        <td><%=t.getDescription()%></td>
                                                    </tr>
                                                </table>
                                                
                                                
                                                
                                                </td>
                                            <td style="border: 1px solid black;">
                                                <table>
                                                    <tr style="height: 25px;">
                                                        <td><%=t.getPrice()%></td>
                                                    </tr>
                                                    <tr style="height: 25px;">
                                                        <td></td>
                                                    </tr>
                                                    <tr style="height: 70px;">
                                                        <td></td>
                                                    </tr>
                                                </table>
                                                </td>
                                                <td style="border: 1px solid black;">
                                                <table>
                                                    <tr style="height: 25px;">
                                                        <td></td>
                                                    </tr>
                                                    <tr style="height: 25px;">
                                                        <td><%=t.getPrice()%></td>
                                                    </tr>
                                                    <tr style="height: 70px;">
                                                        <td></td>
                                                    </tr>
                                                </table>
                                                </td>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                                    </center>
    </body>
</html>
