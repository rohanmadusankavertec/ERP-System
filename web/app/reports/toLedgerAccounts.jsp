<%-- 
    Document   : AddIncome
    Created on : Nov 18, 2016, 3:12:41 PM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Ledger Accounts
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small>Search Ledger Accounts</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form id="invisible_form" action="Report?action=PrintLedgerAccounts" target="_blank" method="post" class="form-horizontal form-label-left" novalidate>


                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Account Type</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="acc" id="acc" required="required">
                                    <option selected="true" value="">ALL Accounts</option>
                                    <%
                                        List<Account> account = (List<Account>) request.getAttribute("account");
                                        for (Account a : account) {
                                            %>
                                            <option value="<%=a.getId()%>"><%=a.getName()%></option>
                                            <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">From Date</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="from" name="from" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">To Date</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="to" name="to" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="ln_solid"></div>        
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <button id="send" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>                              

<%@include file="../../template/footer.jsp"%>

