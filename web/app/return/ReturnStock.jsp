

<%@page import="com.vertec.hibe.model.ReturnStock"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                View Return Stock
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
                    <h2>View Return Stock <small> </small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>

                <div class="x_content">
                    <%List<ReturnStock> rs = (List<ReturnStock>) request.getAttribute("returnstock");
                    %>

                    
                    <div class="table-responsive">
                        <table id="example" class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">

                                    <th>Product Code </th>
                                    <th>Product Name </th>
                                    <th>Qty</th>
                                    <th>Selling Price</th>
                                    <th>Total</th>

                                </tr>
                            </thead>

                            <tbody>
                                <%
                                if(rs!=null){
                                    for(ReturnStock r:rs) {                      
                                %>

                                <tr>

                                    <td><%=r.getProductMasterId().getProductId().getProductCode() %></td>
                                    <td><%=r.getProductMasterId().getProductId().getProductName() %></td>
                                    <td><%=r.getQty() %></td>
                                    <td><%=r.getProductMasterId().getSellingPrice() %></td>
                                    <td><%=(r.getProductMasterId().getSellingPrice()*r.getQty()) %></td>

                                    
                                </tr>
                                <%}}%>
                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>
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

