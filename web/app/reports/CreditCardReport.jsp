<%-- 
    Document   : PrintAsset
    Created on : Nov 25, 2016, 12:40:30 PM
    Author     : vertec-r
--%>

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
        <h1>Credit Card Payment Report</h1>
        <%
            Session ses = NewHibernateUtil.getSessionFactory().openSession();
            List<Payment> payment = (List<Payment>) request.getAttribute("plist");
            Date from = (Date) request.getAttribute("fd");
            Date to = (Date) request.getAttribute("td");
            SystemData sd = (SystemData)request.getAttribute("sysData");
            Company com = (Company)request.getAttribute("company");
            System.out.println("call me1......."+from);
            System.out.println("call me1......."+to);
            
        %>

        <h5>(Calculated Date <%=new Date()%>)</h5>
        <h5>Calculated From <%=from%> To <%=to%></h5>



        
        <div class="clearfix"></div>

        <div style="width: 90%;" >

            

            <div style="width: 80%;">
<!--                <h3><span style="float: left;">Debit</span></h3>-->
                <table style="width: 100%;border: 1px solid black;" >
                    <thead>
                        <tr>
                            <td>Payment Id</td>
                            <td>Invoice #</td>
                            <td>Customer</td>
                            <td>Total Amount</td>
                            <td>Sale Amount</td>
                            <td>Bank Amount</td>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            
                            for (Payment p: payment) {
//                                System.out.println(p.getPaymentId());
                                Query query = ses.createQuery("SELECT i FROM InvoicePayment i WHERE i.paymentId.paymentId =:pid AND i.invoiceId.companyId=:com ");
                                query.setParameter("pid", p.getPaymentId());
                                query.setParameter("com", com);
                                List<InvoicePayment> inp = (List<InvoicePayment>)query.list();
//                                System.out.println(inp);
                                        
                                if(!inp.isEmpty()){
                                InvoicePayment ip = (InvoicePayment)query.uniqueResult();
                                
                                
                        
                        %>
                        <tr style="font-size: 12px;">
                            <td><%=p.getPaymentId() %></td>
                            <td><%=ip.getInvoiceId().getInvoiceId() %></td>
                            <td><%=ip.getInvoiceId().getCustomerId().getCustomerName() %></td>
                            <td><%=p.getAmount() %></td>
                            <td><%=p.getAmount()-(p.getAmount()*(sd.getValue()/100)) %></td>
                            <td><%=p.getAmount()*(sd.getValue()/100) %></td>
                            
                        </tr>
                        <%}}%>
                        
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
