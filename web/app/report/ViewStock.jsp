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
                PO (Purchasing Orders)
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






                            <div class="col-md-12 col-sm-12 col-xs-12">
                                <div class="x_panel">
                                    <div class="x_title">
                                        <h2>View PO <small>up to now</small></h2>
                                        <ul class="nav navbar-right panel_toolbox">
                                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                                            </li>

                                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                                            </li>
                                        </ul>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="x_content">








                                        <div class="table-responsive">
                                            <table id="example" class="table table-striped responsive-utilities jambo_table">
                                                <thead>
                                                    <tr class="headings">

                                                        <th>Branch</th>
                                                        <th>Product Code</th>
                                                        <th>Product Name</th>
                                                        <th>Current Qty</th>
                                                        <th>Re-Order Qty</th>
                                                        <th>Purchased Price</th>
                                                        <th>Selling Price</th>
                                                        <th>Local/Import</th>
                                                        </th>
                                                    </tr>
                                                </thead>

                                                <tbody>

                                                    <%
                                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                                        Session ses = sf.openSession();

                                                        String hql = "SELECT pi.po_info_id,s.company_name,pi.date,pi.total,su.first_name,su.last_name,pi.status,pi.received_date FROM po_info pi inner join supplier s on s.supplier_id=pi.supplier_supplier_id inner join sys_user su on pi.sys_user_sysuser_id=su.sysuser_id";

                                                        String type = request.getAttribute("type").toString();
                                                        if (type.equals("2")) {
                                                            String sup = request.getAttribute("supId").toString();

                                                            hql += " where pi.supplier_supplier_id ='" + sup + "' ";
                                                        } else if (type.equals("3")) {
                                                            String from = request.getAttribute("from").toString();
                                                            String to = request.getAttribute("to").toString();

                                                            hql += " where pi.date between '" + from + "' and '" + to + "'";
                                                        } else if (type.equals("4")) {
                                                            String from = request.getAttribute("date").toString();

                                                            hql += " where pi.date ='" + from + "'";
                                                        } else if (type.equals("5")) {
                                                            hql += " where pi.status ='0'";
                                                        } else if (type.equals("6")) {
                                                            hql += " where pi.status ='1'";
                                                        }

                                                        SQLQuery query = ses.createSQLQuery(hql);

                                                        List<Object[]> inList = query.list();

                                                        for (Object[] list : inList) {
                                                    %>
                                                    <tr onclick="getItem(<%= list[0].toString()%>)">

                                                        <td class=" "><%= list[0].toString()%></td>
                                                        <td class=" "><%= list[1].toString()%></td>
                                                        <td class=" "><%= list[2].toString()%></td>
                                                        <td class=" "><%= list[3].toString()%></td>
                                                        <td class=" "><%= list[4].toString()%> <%= list[5].toString()%></td>
                                                        <td class=" ">
                                                            <%

                                                                if (list[6].toString().equals("false")) {
                                                            %>
                                                            Not Received

                                                            <%
                                                            } else {
                                                            %>
                                                            Received
                                                            <%
                                                                }
                                                            %>

                                                        </td>
                                                        <td class=" ">
                                                            <%
                                                                if (Boolean.valueOf(list[6].toString()) == false) {
                                                            %>
                                                            Not Received

                                                            <%
                                                            } else {

                                                                if (list[7] == null) {
                                                            %>
                                                            Not Entered
                                                            <%
                                                            } else {


                                                            %>
                                                            <%= list[7].toString()%>

                                                            <%
                                                                    }
                                                                }
                                                            %>

                                                        </td>
                                                        <td class=" last"> 
                                                            <form name="form1" method="post" action="PO?action=ChangeStatus">
                                                                <%
                                                                    if (Boolean.valueOf(list[6].toString()) == false) {
                                                                %>
                                                                <input type="hidden" name="poId" value="<%= list[0].toString()%>"/>
                                                                <button type="submit" class="glyphicon glyphicon-edit">RECEIVED</button>
                                                                <%
                                                                    }

                                                                %>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>

                                            </table>
                                        </div> 
                                    </div>
                                </div>
                            </div>
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