
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>




<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Outstanding Report
                <small>

                </small>
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>

                    <div class="clearfix"></div>
                </div>

            </div>
        </div>
    </div>

    <%
        String brid = "";
        String brname = "";
        String br[] = request.getParameter("branchId").split("_");
        if (!br[0].equals("All")) {
            brid = br[0];
            brname = br[1];
        } else {
            brname = "All Branches";
        }


    %>





    <h4>DATE RANGE FROM <%=request.getParameter("fromDate")%> TO <%=request.getParameter("toDate")%> | BRANCH NAME : <%= brname%></h4>
    <br>
    <br>
    <br>


    <%
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session ses = sf.openSession();

    %>
    <center>
        <table class="table table-bordered table-hover table-striped">
            <tr style="font-weight: bold;">
                <td>Invoice No</td>
                <td>Customer Name</td>
                <td>Invoiced Date</td>
                <td>Invoice Total</td>
                <td>Discount</td>
                <td>Total</td>
                <td>Paid</td>
                <td>Outstanding</td>
            </tr>

            <%                SQLQuery query2 = ses.createSQLQuery("SELECT i.invoice_id,c.customer_name,i.invoiced_date,i.invoice_total,i.discount,i.tot_after_discount,o.balance_amount FROM invoice i INNER JOIN customer c ON c.customer_id=i.customer_id INNER JOIN outstandig_invoice o ON o.invoice_id=i.invoice_id WHERE i.is_valid='1' AND i.branch_id like '%" + brid + "%' AND o.balance_amount>0 AND i.invoiced_date BETWEEN :fromDate AND :toDate");
                query2.setParameter("fromDate", request.getParameter("fromDate"));
                query2.setParameter("toDate", request.getParameter("toDate"));
                List<Object[]> inList2 = query2.list();
                double totsale = 0;
                double totpaid = 0;
                double totout = 0;
                for (Object[] list2 : inList2) {
                    totsale += Double.parseDouble(list2[5].toString());
                    totout += Double.parseDouble(list2[6].toString());
                    totpaid += (Double.parseDouble(list2[5].toString()) - Double.parseDouble(list2[6].toString()));


            %>
            <tr>
                <td><%= list2[0].toString()%></td>
                <td><%=list2[1].toString()%></td>
                <td><%=list2[2].toString()%></td>
                <td><%=list2[3].toString()%></td>
                <td><%=list2[4].toString()%></td>
                <td><%=list2[5].toString()%></td>
                <td><%= Double.parseDouble(list2[5].toString()) - Double.parseDouble(list2[6].toString())%></td>
                <td><%=list2[6].toString()%></td>
            </tr>
            <%
                }
            %>
        </table>
    </center>
    <h5>Total sales : <%=totsale%>  </h5>
    <h5>Total Paid : <%=totpaid%>  </h5>
    <h5>Total Outstanding : <%=totout%>  </h5>
    <br>
    <br>











    <button class="btn btn-default" onclick="window.print();"><i class="fa fa-print"></i> Print</button>

    <div class="clearfix"></div>
    <div class="clearfix"></div>
</div>




<%@include file="../../template/footer.jsp"%>