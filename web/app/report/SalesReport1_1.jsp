
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

    <center>
        <table class="table table-bordered table-hover table-striped">
            <tr style="font-weight: bold;">
                <td>Product ID</td>
                <td>Product Code</td>
                <td>Product Name</td>
                <td>Selling Price</td>
                <td>Purchased Price</td>
                <td>Issued QTY</td>
                <td>Sold QTY</td>
                <td>ISSUED-SOLD</td>
            </tr>

            <%
                SessionFactory sf = NewHibernateUtil.getSessionFactory();
                Session ses = sf.openSession();

                SQLQuery query = ses.createSQLQuery("SELECT p.product_id,p.product_code,p.product_name,gin.s_price,gin.p_price,SUM(gin.qty),pm.product_master_id FROM product p INNER JOIN product_master pm on pm.product_id=p.product_id INNER JOIN warehouse_stock ws ON ws.product_master_product_master_id=pm.product_master_id INNER JOIN gin gin ON ws.warehouse_stock_id=gin.warehouse_stock_warehouse_stock_id GROUP BY pm.product_master_id");
                List<Object[]> inList = query.list();
                
                for (Object[] list : inList) {
                    int cqty=Integer.parseInt(list[5].toString());
            %>
            <tr>
                <td><%= list[0].toString()%></td>
                <td><%=list[1].toString()%></td>
                <td><%=list[2].toString()%></td>
                <td><%=list[3].toString()%></td>
                <td><%=list[4].toString()%></td>
                <td><%=list[5].toString()%></td>

                <%
                    SQLQuery query2 = ses.createSQLQuery("SELECT SUM(ii.quantity),SUM(i.invoice_id) FROM invoice i INNER JOIN invoice_item ii on ii.invoice_id=i.invoice_id WHERE ii.product_master_id='"+list[5].toString()+"' and i.is_valid='1'");
                    List<Object[]> inList2 = query2.list();
                    
                    for (Object[] list2 : inList2) {
                        if(list2[0]==null){
                        out.write("<td>0</td>");
                        }else{
                            cqty+=Integer.parseInt(list2[0].toString());
                %>
                
                
                
                
                <td><%=list2[0].toString()%></td>



                <%
                    }
                    }
                %>
                
                <td><%=cqty%></td>

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