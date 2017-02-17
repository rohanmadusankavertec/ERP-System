<%-- 
    Document   : PrintAsset
    Created on : Nov 25, 2016, 12:40:30 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.DepreciationData"%>
<%@page import="com.vertec.hibe.model.BalanceSheetData"%>
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
            double profit = (double) request.getAttribute("profit");
            List<BalanceSheetData> arr = (List<BalanceSheetData>) request.getAttribute("data");
            List<DepreciationData> dep = (List<DepreciationData>) request.getAttribute("dep");
            double Assets = 0.0;
            double Liabilities = 0.0;
        %>


        <h1>Balance Sheet</h1>
        <h4>(Calculated Date <%=date%>)</h4>
        <h4 style="margin-top: -10px;">Calculated From <%=from%> To <%=to%></h4>
        <br>
        <div style="width: 90%;">
            <div style="width: 100%;">
                <table style="width: 100%;">
                    <tr>
                        <td colspan="4"><h2>Assets</h2></td>
                    </tr>
                    <%

                        for (DepreciationData dd : dep) {
                             Assets += dd.getDepreciation().getAmount()-dd.getAmount()-dd.getBeforedep();
                    %>
                    <tr>
                        <td><%=dd.getAccount().getName() %></td>
                        <td><%=dd.getDepreciation().getAmount()-dd.getBeforedep() %></td>
                        <td><%=dd.getAmount() %></td>
                        <td><%=dd.getDepreciation().getAmount()-dd.getAmount()-dd.getBeforedep() %></td>
                    </tr>
                    <%
                        }
                    %>

                    <tr>
                        <td colspan="4"><strong>Cash Accounts</strong></td>
                    </tr>
                    <%                            
                        for (BalanceSheetData bsd : arr) {
                    %> 

                    <%
                        if (bsd.getA().getSubtypeId().getName().equals("Cash")) {
                            Assets += bsd.getAmount();
                    %>
                    <tr>
                        <td><%=bsd.getA().getName()%></td>
                    <td></td>
                    <td></td>
                        <td><%=bsd.getAmount()%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    <tr>
                        <td colspan="4"><strong>Bank Accounts</strong></td>
                    </tr>
                    <%
                        for (BalanceSheetData bsd : arr) {
                    %> 
                    <%
                        if (bsd.getA().getSubtypeId().getName().equals("Bank")) {
                            Assets += bsd.getAmount();
                    %>
                    <tr>
                        <td><%=bsd.getA().getName()%></td>
                        <td></td>
                    <td></td>
                        <td><%=bsd.getAmount()%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    <tr>
                        <td colspan="4"><strong>Debtors</strong></td>
                    </tr>
                    <%
                        for (BalanceSheetData bsd : arr) {
                    %> 
                    <%
                        if (bsd.getA().getSubtypeId().getName().equals("Debtors")) {
                            Assets += bsd.getAmount();
                    %>
                    <tr>
                        <td><%=bsd.getA().getName()%></td>
                        <td></td>
                    <td></td>
                        <td><%=bsd.getAmount()%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    <tr>
                        <td><strong>Total Assets</strong></td>
                        <td></td>
                    <td></td>
                        <td><strong><%=Assets%></strong></td>
                    </tr>

                    <tr>
                        <td colspan="4"><h2>Liabilities & Owner's Equity</h2></td>
                    </tr>
                    <tr>
                        <td colspan="4"><strong>Profit</strong></td>
                    </tr>
                    <tr>
                        <td>Net Profit</td>
                        <td></td>
                    <td></td>
                        <td><%=profit%></td>
                    </tr>
                    <tr>
                        <td colspan="4"><strong>Owners Equity</strong></td>
                    </tr>
                    <%
                        Liabilities += profit;
                        for (BalanceSheetData bsd : arr) {
                    %> 



                    <%
                        if (bsd.getA().getSubtypeId().getTypeId().getName().equals("Capital")) {
                            Liabilities += bsd.getAmount() * (-1);


                    %>
                    <tr>
                        <td><%=bsd.getA().getName()%></td>
                        <td></td>
                    <td></td>
                        <td><%if (bsd.getAmount() != 0) {
                                out.write((bsd.getAmount() * (-1)) + "");

                            } else {
                                out.write("0.0");
                            }%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    <tr>
                        <td colspan="4"><strong>Creditors</strong></td>
                    </tr>
                    <%
                        for (BalanceSheetData bsd : arr) {
                    %> 



                    <%
                        if (bsd.getA().getSubtypeId().getName().equals("Creditors")) {
                            Liabilities += bsd.getAmount() * (-1);
                    %>
                    <tr>
                        <td><%=bsd.getA().getName()%></td>
                        <td></td>
                    <td></td>
                        <td><%

                            if (bsd.getAmount() != 0) {
                                out.write((bsd.getAmount() * (-1)) + "");

                            } else {
                                out.write("0.0");
                            }

                            %></td>
                    </tr>
                    <%                                }
                        }
                    %>
                    <tr>
                        <td><strong>Total Liabilities & Owners Equity</strong></td>
                        <td></td>
                    <td></td>
                        <td><strong><%=Liabilities%></strong></td>
                    </tr>
                </table>
            </div>

        </div>

    </center>

</body>
</html>
