<%-- 
    Document   : PrintAsset
    Created on : Nov 25, 2016, 12:40:30 PM
    Author     : vertec-r
--%>

<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.time.Year"%>
<%@page import="com.vertec.hibe.model.Company"%>
<%@page import="com.vertec.hibe.model.SystemData"%>
<%@page import="com.vertec.hibe.model.InvoicePayment"%>
<%@page import="com.vertec.hibe.model.Payment"%>
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
        <title></title>
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
        <h1>Budget Plan Report</h1>
        <%
           
//            List<Payment> payment = (List<Payment>) request.getAttribute("plist");
//            Company com = (Company)request.getAttribute("company");
            int year = Year.now().getValue();
            int year1 = year-1;
            int year2 = year-2;
            String[] months = new DateFormatSymbols().getMonths();
            
            String name = (String)request.getAttribute("name");
            
        %>

        <h5>(Calculated Date <%=new Date()%>)</h5>
       



        
        <div class="clearfix"></div>
        <h1><%=name %></h1>
        <div style="width: 90%;" >

            

            <div style="width: 80%;">
<!--                <h3><span style="float: left;">Debit</span></h3>-->
                <table style="width: 100%;border: 1px solid black;" >
                    <thead>
                        <tr>
                            <td>Month</td>
                            <td><%=year%></td>
                            <td><%=year1%></td>
                            <td><%=year2%></td>
                            
                            
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            for (String m: months) {
                        %>
                        <tr style="font-size: 12px;">
                            <td><%=m %></td>
                            
                            
                        </tr>
                        <%}%>
                        
                    </tbody>
                </table>
            </div>
            
        </div>
<div class="clearfix"></div>
        <div style="width: 100%; height: 50px; ">

        </div>
<div class="clearfix"></div>
        




























    </center>

</body>
</html>
