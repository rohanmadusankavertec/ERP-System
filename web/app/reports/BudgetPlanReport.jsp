<%-- 
    Document   : PrintAsset
    Created on : Nov 25, 2016, 12:40:30 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.BudgetPlan"%>
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
           Session ses = NewHibernateUtil.getSessionFactory().openSession();
//            List<Payment> payment = (List<Payment>) request.getAttribute("plist");
//            Company com = (Company)request.getAttribute("company");
            int year = Year.now().getValue();
            int year1 = year-1;
            int year2 = year-2;
            String[] months = new DateFormatSymbols().getMonths();
            
            String name = (String)request.getAttribute("name");
            String id = (String)request.getAttribute("accid");
//            System.out.println("account id........"+name);
//            System.out.println("account id........"+id);
//            List<Object> valuelist = (List<Object>) request.getAttribute("valueList");
            Company com = (Company)request.getAttribute("company");
//            double tot1 = (double)request.getAttribute("tot1");
//            double tot2 = (double)request.getAttribute("tot2");
//            double tot3 = (double)request.getAttribute("tot3");
            String tot1 = (String)request.getAttribute("yy");
            String tot2 = (String)request.getAttribute("yyy");
            String tot3 = (String)request.getAttribute("yyyy");
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
                            <td><strong>Month</strong></td>
                            <td><strong><%=year%></strong></td>
                            <td><strong><%=year1%></strong></td>
                            <td><strong><%=year2%></strong></td>
                            
                            
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            for(int i=1; i<=12; i++)
                            {
                                
//                                Query query = ses.createQuery("SELECT b FROM BudgetPlan b WHERE b.accountId.id=:acc AND b.year=:year AND b.companyId=:com AND b.month=:mth");
//                                query.setParameter("acc", Integer.parseInt(id));
//                                query.setParameter("year", Integer.toString(year));
//                                query.setParameter("com", com);
//                                query.setParameter("mth", Integer.toString(i));
//                                BudgetPlan bu=(BudgetPlan)query.uniqueResult();
                                
                        %>
                        <tr style="font-size: 12px;">
                            <td><%=months[i-1] %></td>
                            <td><%
                                Query query = ses.createQuery("SELECT b FROM BudgetPlan b WHERE b.accountId.id=:acc AND b.year=:year AND b.companyId=:com AND b.month=:mth");
                                query.setParameter("acc", Integer.parseInt(id));
                                query.setParameter("year", Integer.toString(year));
                                query.setParameter("com", com);
                                query.setParameter("mth", Integer.toString(i));
                                List<BudgetPlan> paln1 =(List<BudgetPlan>) query.list();
//                                BudgetPlan bu=(BudgetPlan)query.uniqueResult();
                                if(!paln1.isEmpty()){
                                    
                                    BudgetPlan bu=(BudgetPlan)query.uniqueResult();
//                                    System.out.println(".....current Year : "+bu.getYear()+"month : "+bu.getMonth()+" value : "+bu.getValue() );
                                    out.write(Double.toString(bu.getValue()));
                                }
                                
                                %></td>
                            <td><%
                                Query query1 = ses.createQuery("SELECT b FROM BudgetPlan b WHERE b.accountId.id=:acc AND b.year=:year AND b.companyId=:com AND b.month=:mth");
                                query.setParameter("acc", Integer.parseInt(id));
                                query.setParameter("year", Integer.toString(year1));
                                query.setParameter("com", com);
                                query.setParameter("mth", Integer.toString(i));
                                List<BudgetPlan> paln2 =(List<BudgetPlan>) query.list();
//                                BudgetPlan bu=(BudgetPlan)query.uniqueResult();
                                if(!paln2.isEmpty()){
                                    BudgetPlan bu2=(BudgetPlan)query.uniqueResult();
                                    out.write(Double.toString(bu2.getValue()));
                                }
                                %></td>
                            <td><%
                                Query query2 = ses.createQuery("SELECT b FROM BudgetPlan b WHERE b.accountId.id=:acc AND b.year=:year AND b.companyId=:com AND b.month=:mth");
                                query.setParameter("acc", Integer.parseInt(id));
                                query.setParameter("year", Integer.toString(year2));
                                query.setParameter("com", com);
                                query.setParameter("mth", Integer.toString(i));
                                List<BudgetPlan> paln3 =(List<BudgetPlan>) query.list();
//                                BudgetPlan bu=(BudgetPlan)query.uniqueResult();
                                if(!paln3.isEmpty()){
                                    BudgetPlan bu3=(BudgetPlan)query.uniqueResult();
                                    out.write(Double.toString(bu3.getValue()));
                                }
                                %></td>
                            
                        </tr>
                        <%}%>
                        <tr style="font-weight: bold;height: 15px;">
                            <td>Total</td>
                            <td><%=tot1 %></td>
                            <td><%=tot2 %></td>
                            <td><%=tot3 %></td>
                            
                        </tr>
                        
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
