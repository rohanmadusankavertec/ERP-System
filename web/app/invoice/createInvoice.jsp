
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
                Invoice
            </h3>
        </div>
    </div>
    <%
        List<Branch> branchList = (List<Branch>) request.getAttribute("branchList");
        List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
    %>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Invoice Design </h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="Invoice?action=ToInvoice" method="post">
                        <div class="item form-group" style="padding-top: 50px;" id="branchwise">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Branch <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="branchId" id="branchId" >
                                    <option selected="true" disabled value="">Select Branch</option>
                                    <%if (ca.checkUserAuth("OTHER_BRANCH", group) != null) {%>
                                    <%for (Branch b : branchList) {%>
                                    <option value="<%=b.getBranchId()%>"><%=b.getBranchName()%></option>
                                    <%}
                                    } else {
                                    %>
                                    <option value="<%= user.getBranchBranchId().getBranchId()%>"><%= user.getBranchBranchId().getBranchName()%></option>
                                    <%
                                        }
                                    %>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Customer <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="customerId" id="customerId"  required="required">
                                    <option selected="true" disabled value="">Select Customer</option>

                                    <%for (Customer c : customerList) {%>
                                    <option value="<%=c.getCustomerId()%>"><%=c.getCustomerName()%></option>
                                    <%}%>
                                </select>                              
                            </div>
                        </div>  
                               
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" id="updateP"><i class="fa fa-arrow-right"></i>  Go </button>
                            </div>
                        </div> 
                    </form>
                    <div class="container">
                        <a id="modal_trigger" href="#modal" class="btn btn-success pull-right">Add Customer</a>

                        <div id="modal" class="popupContainer" style="display:none;">
                            <header class="popupHeader">
                                <span class="header_title">Login</span>
                                <span class="modal_close"><i class="fa fa-times"></i></span>
                            </header>
                            <section class="popupBody">
                                <!-- Register Form -->
                                <div class="user_register col-md-12 col-sm-12 col-xs-12">
                                    <form action="Customer?action=SaveQuickCustomer" method="post">
                                        <div class="item form-group">
                                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Enter Customer Name<span class="required"></span>
                                            </label>
                                            <div class="col-md-6 col-sm-6 col-xs-12">
                                                <input type="text" id="cusName" name="cusName" required="required" class="form-control col-md-7 col-xs-12">
                                            </div>
                                        </div>    
                                        <div class="action_btns" style="padding-top: 30px;">
                                            <div class="one_half last col-md-offset-3"><button type="submit" class="btn btn-success pull-right"><i class="fa fa-briefcase"></i>Quick Save Customer</button></div>
                                        </div>
                                    </form>
                                </div>
                            </section>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
<script type="text/javascript">
    $("#modal_trigger").leanModal({top: 200, overlay: 0.6, closeButton: ".modal_close"});

    $(function () {
        // Calling Register Form
        $("#modal_trigger").click(function () {
            $(".user_register").show();
            $(".header_title").text('Add Quick Customer');
            return false;
        });



    });
</script>