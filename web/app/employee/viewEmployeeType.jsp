<%-- 
    Document   : viewCustomer
    Created on : Oct 20, 2016, 1:14:40 PM
    Author     : User
--%>


<%@page import="com.vertec.hibe.model.EmployeeType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<%
    EmployeeType et = (EmployeeType) request.getAttribute("employeeType");
    int etftype=0;
    if(et.getEtfEpf()){
    etftype=1;
    }
%>
<script type="text/javascript">
 setTimeout("document.getElementById('epfetf').value='<%=etftype%>';", 500);   
</script>
<div class="">

    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="Employee?action=UpdateEmpType" method="post" class="form-horizontal form-label-left" novalidate >
                        <span class="section">Employee Type Update</span>
                        <input type="hidden" value="<%=et.getId()%>" name="employeeTypeId"/>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Employee Type
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12" name="employeeType" value="<%=et.getType()%>" placeholder="Enter Employee Type" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Monthly Short Leave
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  data-validate-words="1" value="<%=et.getMonthlyShortLeaves()%>" name="shortLeaves" placeholder="Enter Monthly Short Leaves" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Monthly Leave
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  data-validate-words="1" value="<%=et.getMonthlyLeaves()%>" name="monthlyLeaves" placeholder="Enter Monthly Leaves" required="required" type="number">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Annual Leave
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input class="form-control col-md-7 col-xs-12"  data-validate-words="1" value="<%=et.getAnnualLeaves()%>" name="annual" placeholder="Enter Annual Leaves" required="required" type="number">
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
                                <button id="send" type="submit" class="btn btn-success">Update</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../../template/footer.jsp"%>