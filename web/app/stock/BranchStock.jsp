<%-- 
    Document   : ViewPO
    Created on : Aug 23, 2016, 1:20:15 PM
    Author     : vertec-r
--%>

<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script type="text/javascript" src="app/js/po.js"></script>



<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Branch Stock
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
                <div class="x_content">


                    <section class="content invoice">
                        <!-- title row -->
                        <div class="row">
                            <div class="table-responsive">
                                <table id="example" class="table table-striped responsive-utilities jambo_table">
                                    <thead>
                                        <tr class="headings">

                                            <th>Product ID</th>
                                            <th>Product</th>
                                            <th>Quantity</th>
                                            <th>Buying Price</th>
                                            <th>Selling Price</th>
                                            <th>Last Updated Date</th>
                                            <th>Local/Import</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <%
                                            SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                            Session ses = sf.openSession();

                                            String hql = "SELECT p.product_id,p.product_code,p.product_name,w.quantity,pm.purchased_price,pm.selling_price,w.last_updated_date,w.type FROM branch_productmaster w INNER JOIN product_master pm ON pm.product_master_id=w.product_master_id INNER JOIN product p ON pm.product_id=p.product_id WHERE w.quantity>0";

                                            String type = request.getAttribute("type").toString();
                                            Boolean bool = true;
                                            if (type.equals("2")) {
                                                bool = false;
                                                hql += " AND w.type ='1' ";
                                            } else if (type.equals("3")) {
                                                bool = false;
                                                hql += " AND w.type ='0' ";
                                            } else if (type.equals("4")) {
                                                bool = false;
                                                hql += " AND p.re_order_level > w.quantity ";
                                            }

                                            String branch = request.getAttribute("branch").toString();

                                            if (!branch.equals("0")) {
                                                if (bool) {
                                                    hql += " AND w.branch_id ='" + branch + "' ";
                                                } else {
                                                    hql += "AND w.branch_id ='" + branch + "' ";
                                                }
                                            }

                                            SQLQuery query = ses.createSQLQuery(hql);

                                            List<Object[]> inList = query.list();

                                            for (Object[] list : inList) {
                                        %>
                                        <tr>

                                            <td class=" "><%= list[0].toString()%></td>
                                            <td class=" "><%= list[1].toString() + " " + list[2].toString()%></td>
                                            <td class=" "><%= list[3].toString()%></td>
                                            <td class=" "><%= list[4].toString()%></td>
                                            <td class=" "><%= list[5].toString()%> </td>
                                            <td class=" "><%= list[6].toString()%></td>
                                            <td class=" ">
                                                <%
                                                if(list[7]!=null){
                                                    if (list[7].toString().equals("false")) {
                                                        
                                                %>
                                                Local
                                                <%
                                                } else {
                                                %>
                                                Import
                                                <%
                                                    }
                                                }
                                                %>

                                            </td>

                                        </tr>
                                        <%}%>
                                    </tbody>

                                </table>
                            </div> 


                    </section>
                </div>
            </div>
        </div>
    </div>
</div>







<%@include file="../../template/footer.jsp"%>





<script>
    $(document).ready(function() {
        $('input.tableflat').iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
        });
    });

    var asInitVals = new Array();
    $(document).ready(function() {
        var oTable = $('#example').dataTable({
            "oLanguage": {
                "sSearch": "Search all columns:"
            },
            "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0]
                } //disables sorting for column one
            ],
            'iDisplayLength': 12,
            "sPaginationType": "full_numbers",
            "dom": 'T<"clear">lfrtip',
            "tableTools": {
                "sSwfPath": "${context}/resources/js/datatables/tools/swf/copy_csv_xls_pdf.swf"
            }
        });
        $("tfoot input").keyup(function() {
            /* Filter on the column based on the index of this element's parent <th> */
            oTable.fnFilter(this.value, $("tfoot th").index($(this).parent()));
        });
        $("tfoot input").each(function(i) {
            asInitVals[i] = this.value;
        });
        $("tfoot input").focus(function() {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("tfoot input").blur(function(i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("tfoot input").index(this)];
            }
        });
    });
</script>