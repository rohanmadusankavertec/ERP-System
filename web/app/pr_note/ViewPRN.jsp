<%-- 
    Document   : ViewPO
    Created on : Aug 23, 2016, 1:20:15 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.Company"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<!--<script type="text/javascript" src="app/js/po.js"></script>-->

<script type="text/javascript">
    //get prn items according to prn id
    function getItem(id) {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                document.getElementById("itemdetails").innerHTML = xmlHttp.responseText;
            }
        };
        xmlHttp.open("POST", "PRN?action=GetItems&id=" + id, true);
        xmlHttp.send();
    }
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                PRN (Purchase Requisition Note)
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
                                        <h2>View PRN <small>up to now</small></h2>
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
                                                        <th>PRN No.</th>
                                                        <th>Added Date</th>
                                                        <th>Added By</th>
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        HttpSession httpSession = request.getSession();
                                                        Company company = (Company) httpSession.getAttribute("company");
                                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                                        Session ses = sf.openSession();
                                                        String hql = "SELECT pi.prn_info_id,pi.date,su.first_name,su.last_name FROM prn_info pi inner join sys_user su on pi.sys_user_sysuser_id=su.sysuser_id WHERE pi.company_id='"+company.getId()+"'";
                                                        String type = request.getAttribute("type").toString();
                                                         if (type.equals("2")) {
                                                            String from = request.getAttribute("from").toString();
                                                            String to = request.getAttribute("to").toString();

                                                            hql += " AND pi.date between '" + from + "' and '" + to + "'";
                                                        } else if (type.equals("3")) {
                                                            String from = request.getAttribute("date").toString();

                                                            hql += " AND pi.date ='" + from + "'";
                                                        } 
                                                        SQLQuery query = ses.createSQLQuery(hql);
                                                        List<Object[]> inList = query.list();
                                                        for (Object[] list : inList) {
                                                    %>
                                                    <tr onclick="getItem(<%= list[0].toString()%>)">
                                                        <td class=" "><%= list[0].toString()%></td>
                                                        <td class=" "><%= list[1].toString()%></td>
                                                        <td class=" "><%= list[2].toString()%> <%= list[3].toString()%></td> 
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div> 
                                    </div>
                                </div>
                            </div>
                        </div>
                            <div class="table-responsive" style="margin-top: 100px;">
                                <table id="example" class="table table-striped responsive-utilities jambo_table">
                                    <thead>
                                        <tr class="headings">
                                            <th>Product</th>
                                            <th>Qty</th>
                                        </tr>
                                    </thead>
                                    <tbody id="itemdetails">
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