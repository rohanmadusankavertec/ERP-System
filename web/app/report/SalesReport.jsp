
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
                Sales Report
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
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
               
            </div>
        </div>
    </div>

Date range from <%=request.getParameter("fromDate")%> to <%=request.getParameter("toDate")%>



    <center>
        <table class="table table-bordered table-hover table-striped">
            <tr style="font-weight: bold;">
                <td>Product Code</td>
                <td>Product Name</td>
                <td>Selling Price</td>
                <td>Qty</td>
                <td>Total</td>
                <td>Discount</td>
                <td>Grand Total</td>
            </tr>

            <%
                SessionFactory sf = NewHibernateUtil.getSessionFactory();
                Session ses = sf.openSession();
                Transaction tr = ses.beginTransaction();

                SQLQuery query = ses.createSQLQuery("SELECT p.product_code,p.product_name,pm.selling_price,SUM(ii.quantity),SUM(ii.tot_amount),SUM(ii.discount),SUM(ii.tot_after_dis) FROM product p INNER JOIN product_master pm on pm.product_id=p.product_id INNER JOIN invoice_item ii ON ii.product_master_id=pm.product_master_id INNER JOIN invoice i ON i.invoice_id=ii.invoice_id WHERE i.invoiced_date between :fromDate and :toDate GROUP BY p.product_id");
                query.setParameter("fromDate", request.getParameter("fromDate"));
                query.setParameter("toDate", request.getParameter("toDate"));
                List<Object[]> inList = query.list();

                for (Object[] list : inList) {
            %>
            <tr>
                <td><%= list[0].toString()%></td>
                <td><%=list[1].toString()%></td>
                <td><%=list[2].toString()%></td>
                <td><%=list[3].toString()%></td>
                <td><%=list[4].toString()%></td>
                <td><%=list[5].toString()%></td>
                <td><%=list[6].toString()%></td>
            <tr>

                <%
                    }
                %>

        </table>
    </center>
                <button class="btn btn-default" onclick="window.print();"><i class="fa fa-print"></i> Print</button>
                                
    <div class="clearfix"></div>
    <div class="clearfix"></div>
</div>




<%@include file="../../template/footer.jsp"%>