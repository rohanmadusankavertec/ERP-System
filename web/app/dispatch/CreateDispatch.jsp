

<%@page import="com.vertec.hibe.model.Invoice"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="../../app/js/notAlert.js"></script>

<%
    List<Invoice> invoice = (List<Invoice>) request.getAttribute("invoice");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Dispatch Note
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
                    <form action="Dispatch?action=toDispatch" method="post">
                        <div class="item form-group">
                            <label class="control-label col-md-5 col-sm-3 col-xs-12">Pending Invoice</label>
                            <div class="col-md-7 col-sm-6 col-xs-12">
                                <select class="form-control" name="id" id="id"  required="required" >
                                    <option selected="true" value="">Select Invoice</option>
                                    <%
                                        for (Invoice i : invoice) {
                                    %>
                                    <option value="<%=i.getInvoiceId()%>" ><%=i.getInvoiceId() + " ~ " + i.getCustomerId().getCustomerName() + " ~ " + i.getInvoicedDate()%></option>
                                    <%
                                        }
                                    %>
                                </select>                            
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="viewInvoices"><i class="fa fa-arrow-right"></i>  Next </button>
                            </div>
                        </div>   
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
