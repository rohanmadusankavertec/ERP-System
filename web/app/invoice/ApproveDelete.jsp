

<%@page import="com.vertec.hibe.model.Company"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<!--<script src="app/js/invoice.js"></script>-->
<script src="../js/notAlert.js"></script>



<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Temporary Deleted Invoice

            </h3>
        </div>

        <div class="title_right">
            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Deleted Invoices <small>up to now</small></h2>
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

                                    <th>Invoice No</th>
                                    <th>Deleted user</th>
                                    <th>Deleted Date</th>
                                    <th>Customer</th>
                                    <th>Branch</th>
                                    <th>Invoice Total</th>
                                    <th class=" no-link last"><span class="nobr">Action</span>
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                <%
                                    HttpSession httpSession = request.getSession();
                                    Company company = (Company) httpSession.getAttribute("company");
                                    SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                    Session ses = sf.openSession();
                                    String hql = "SELECT i.invoice_id,u.first_name,u.last_name,di.date,c.customer_name,b.branch_name,i.invoice_total FROM del_invoice di inner join invoice i on i.invoice_id=di.invoice_invoice_id inner join sys_user u on u.sysuser_id=di.sys_user_sysuser_id inner join branch b on b.branch_id=i.branch_id inner join customer c on c.customer_id=i.customer_id where di.is_deleted='0' and i.company_id='"+company.getId()+"' and i.is_valid='1'";
                                    SQLQuery query = ses.createSQLQuery(hql);
                                    List<Object[]> inList = query.list();
                                    for (Object[] list : inList) {
                                %>
                                <tr>
                                    <td><%=list[0].toString()%></td>
                                    <td><%=list[1].toString() + " " + list[2].toString()%></td>
                                    <td><%=list[3].toString()%></td>
                                    <td><%=list[4].toString()%></td>
                                    <td><%=list[5].toString()%></td>
                                    <td><%=list[6].toString()%></td>
                                    <td class=" last"> 
                                        <form action="${context}/Invoice" action="POST">
                                            <input type="hidden" name="action" value="DeleteInvoice"/>
                                            <input type="hidden" name="invoiceId" value="<%=list[0].toString()%>"/>
                                            <button type="submit">Approve</button>
                                        </form>   
                                        <form action="${context}/Invoice" action="POST">
                                            <input type="hidden" name="action" value="ignoreDelete"/>
                                            <input type="hidden" name="invoiceId" value="<%=list[0].toString()%>"/>
                                            <button type="submit">Ignore</button>
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

        <br />
        <br />
        <br />

    </div>
</div>
<script>
    $(document).ready(function () {
        $('input.tableflat').iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
        });
    });

    var asInitVals = new Array();
    $(document).ready(function () {
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
        $("tfoot input").keyup(function () {
            /* Filter on the column based on the index of this element's parent <th> */
            oTable.fnFilter(this.value, $("tfoot th").index($(this).parent()));
        });
        $("tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("tfoot input").blur(function (i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("tfoot input").index(this)];
            }
        });
    });
</script>




<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
