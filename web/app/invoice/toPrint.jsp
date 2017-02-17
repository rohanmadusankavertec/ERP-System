

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>

<script type="text/javascript">
    function OpenPrintView(iid){
        window.open("Invoice?action=ViewInvoice&invoiceId="+iid, "_blank");
    }
    
</script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                View Invoices
            </h3>
        </div>
    </div>

    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>View Invoices <small> </small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>

                <div class="x_content">
                    <%Object[] invoice = (Object[]) request.getAttribute("invoice");
                    %>
                    <div class="table-responsive">
                        <table id="example" class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">
                                    <th>Invoice NO </th>
                                    <th>Invoice Date</th>
                                    <th>Customer</th>
                                    <th>Total Amount</th>
                                    <th class=" no-link last"><span class="nobr">Action</span></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><%=invoice[0].toString()%></td>
                                    <td><%=invoice[1].toString()%></td>
                                    <td><%=invoice[2].toString()%></td>
                                    <td><%=invoice[3].toString()%></td>
                                    <td class="last"><button  onclick="OpenPrintView(<%=invoice[0].toString()%>)" type="submit" class="glyphicon glyphicon-search"></button> </td>
                                </tr>
                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>

