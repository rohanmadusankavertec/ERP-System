<%-- 
    Document   : AddIncome
    Created on : Nov 18, 2016, 3:12:41 PM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<%
    List<Account> accList = (List<Account>) request.getAttribute("account");
%>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Budget Plan Report
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small>Create Budget Plan Report</small></h2>
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

                    <form id="invisible_form" action="Report?action=BudgetPlanReport" target="_blank" method="post" class="form-horizontal form-label-left" novalidate>
                        
                        <div class="item form-group" style="padding-top: 50px;">
                            <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Select Account <span class="required"></span>
                            </label>
                            
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="accountId" id="accountId"  required="required" >
                                    <option selected="true" disabled value="">Select Account....</option>

                                    <%for (Account c : accList) {%>
                                    <option value="<%=c.getId()+"-"+c.getName() %>"><%=c.getName() %></option>
                                    <%}%>

                                </select>

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

