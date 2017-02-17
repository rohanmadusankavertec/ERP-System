<%-- 
    Document   : registerCustomer
    Created on : Oct 20, 2016, 8:40:14 AM
    Author     : User
--%>


<%@page import="com.vertec.hibe.model.EmployeeType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Employee Types Registration<small></small></h2>
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

                    <form action="Employee?action=EmployeeTypeRegister" method="post" class="form-horizontal form-label-left" validate>

                        <span class="section">Add Employee Type</span>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Employee Type
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12" name="employeeType" placeholder="Enter Employee Type" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Monthly Short Leave
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  name="shortLeaves" placeholder="Enter Monthly Short Leaves" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Monthly Leave
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  name="monthlyLeaves" placeholder="Enter Monthly Leaves" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Annual Leave
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  name="annual" placeholder="Enter Annual Leaves" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">EPF / ETF</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="epfetf" id="epfetf" required="required">
                                    <option selected="true" disabled="true" >Select payable</option>
                                    <option value="1">Yes</option>
                                    <option value="0">No</option>
                                </select>                            
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <button id="send" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <%

        List<EmployeeType> employeeTypeList = (List<EmployeeType>) request.getAttribute("employeetype");

    %>
    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Registered Employee Types <small>up to now</small></h2>
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
                                    <th>#</th>
                                    <th>Employee Type</th>
                                    <th>Monthly Short Leaves</th>
                                    <th>Monthly Leaves</th>
                                    <th>Annual Leaves</th>
                                    <th>EPF / ETF</th>
                                    <th class=" no-link last"><span class="nobr">Update</span>
                                    <th class=" no-link last"><span class="nobr">Delete</span>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <%                                    
                                    for (EmployeeType emp : employeeTypeList) {
                                %>
                                <tr>
                                    <td class=" "><%=emp.getId()%></td>
                                    <td class=" "><%=emp.getType() %></td>
                                    <td class=" "><%=emp.getMonthlyShortLeaves() %></td>
                                    <td class=" "><%=emp.getMonthlyLeaves() %></td>
                                    <td class=" "><%=emp.getAnnualLeaves() %></td>
                                    <td class=" "><%
                                    if(emp.getEtfEpf()){
                                    out.write("Yes");
                                    }else{
                                    out.write("No");
                                    }
                                    %></td>
                                    <td class=" last"> 
                                        <form name="form1" method="post" action="Employee?action=UpdateEmployeeType"><input type="hidden" name="employeeTypeId" value="<%=emp.getId()%>"/>
                                            <button type="submit" class="glyphicon glyphicon-edit">
                                            </button>
                                        </form>
                                    </td>
                                    <td class=" last"> 
                                        <form name="form1" method="post" action="Employee?action=RemoveEmployeeType"><input type="hidden" name="employeeTypeId" value="<%=emp.getId()%>"/>
                                            <button id="send" type="submit" class="btn btn-danger">Delete</button>
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
</div>
<%@include file="../../template/footer.jsp"%>