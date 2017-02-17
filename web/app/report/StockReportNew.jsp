
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
                Stock Report
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


    <center>
        <table class="table table-bordered table-hover table-striped">
            <tr style="font-weight: bold;">
                <td>Branch</td>
                <td>Product Code</td>
                <td>Product Name</td>
                <td>Re order level</td>
                <td>Buying Price</td>
                <td>Selling Price</td>
                <td>Qty</td>
            </tr>

            <%
                SessionFactory sf = NewHibernateUtil.getSessionFactory();
                Session ses = sf.openSession();
                Transaction tr = ses.beginTransaction();

//                SQLQuery query = ses.createSQLQuery("SELECT b.branch_name,p.product_code,p.product_name,p.re_order_level,pm.purchased_price,pm.selling_price,bs.quantity FROM branch_stock bs INNER JOIN branch b ON b.branch_id=bs.branch_id INNER JOIN product p ON p.product_id=bs.product_id INNER JOIN product_master pm ON pm.product_id=bs.product_id ORDER BY bs.branch_id");
                SQLQuery query = ses.createSQLQuery("SELECT b.branch_name,p.product_code,p.product_name,p.re_order_level,pm.purchased_price,pm.selling_price,bpm.quantity FROM branch_productmaster bpm INNER JOIN product_master pm ON pm.product_master_id=bpm.product_master_id INNER JOIN branch b ON b.branch_id=bpm.branch_id INNER JOIN product p ON p.product_id=pm.product_id");
                
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