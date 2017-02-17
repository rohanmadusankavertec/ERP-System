<%-- 
    Document   : FilterPO
    Created on : Aug 23, 2016, 12:31:21 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.AllowanceDeduction"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/po.js"></script>
<script src="app/js/notAlert.js"></script>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Salary Calculation
                <%
                    List<Object> list = (List) request.getAttribute("array");
                    List<AllowanceDeduction> allowance = (List<AllowanceDeduction>) list.get(0);
                    List<AllowanceDeduction> deduction = (List<AllowanceDeduction>) list.get(1);
                    String sa[] = (String[]) list.get(2);
                %>
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
                    <form action="Salary?action=SaveSalary" method="post" validate>
                        <div class="ln_solid"></div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Employee</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[0]%></label>
                            <input type="hidden" name="eid" value="<%=sa[23]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">NIC</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[1]%></label>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Date Range</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name">FROM <%=sa[21]%> - TO <%=sa[22]%></label>
                            <input type="hidden" name="fromdate" value="<%=sa[21]%>"/>
                            <input type="hidden" name="todate" value="<%=sa[22]%>"/>
                        </div>
                        <br>
                        <br>
                        <div class="clearfix"></div>
                        <div class="ln_solid"></div>
                        <h4 style="padding-top: 50px;">Attendance</h4>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Total Working Hours</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[2]%></label>
                            <input type="hidden" name="totworkingHours" value="<%=sa[24]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Attendance</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[3]%></label>
                            <input type="hidden" name="totworkedMin" value="<%=sa[25]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Early Arrivals</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[4]%></label>
                            <input type="hidden" name="EA" value="<%=sa[26]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Late Arrivals</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[5]%></label>
                            <input type="hidden" name="LA" value="<%=sa[27]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Early Leaves</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[6]%></label>
                            <input type="hidden" name="EL" value="<%=sa[28]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Late Leaves</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[7]%></label>
                            <input type="hidden" name="LL" value="<%=sa[29]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">No Pay Leaves</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[8]%></label>
                            <input type="hidden" name="NPL" value="<%=sa[30]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Pay Leaves</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[9]%></label>
                            <input type="hidden" name="PL" value="<%=sa[31]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Over Time Hours</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[10]%></label>
                            <input type="hidden" name="OTM" value="<%=sa[32]%>"/>
                        </div>
                        <br>
                        <br>
                        <div class="clearfix"></div>
                        <div class="ln_solid"></div>
                        <h4 style="padding-top: 50px;">Payments</h4>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Basic Salary</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[11]%></label>
                            <input type="hidden" name="basicsal" value="<%=sa[11]%>"/>
                        </div>
                        <h5 style="padding-top: 50px;">Allowance</h5>
                        <table class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">
                                    <th>Allowance</th>
                                    <th>Amount</th>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (AllowanceDeduction e : allowance) {
                                %>
                                <tr>
                                    <td><%=e.getName()%></td>
                                    <td><%=e.getValue()%></td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Total Allowance</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[12]%></label>
                            <input type="hidden" name="allowance" value="<%=sa[12]%>"/>
                        </div>
                        <h5 style="padding-top: 50px;">Deduction</h5>
                        <table class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">
                                    <th>Deduction</th>
                                    <th>Amount</th>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (AllowanceDeduction e : deduction) {
                                %>
                                <tr>
                                    <td><%=e.getName()%></td>
                                    <td><%=e.getValue()%></td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Total Deductions</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[13]%></label>
                            <input type="hidden" name="deduction" value="<%=sa[13]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Late Arrival Deductions</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[14]%></label>
                            <input type="hidden" name="Latededuction" value="<%=sa[14]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">No Pay Deductions</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[15]%></label>
                            <input type="hidden" name="nopaydeduction" value="<%=sa[15]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">OT Payments</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[16]%></label>
                            <input type="hidden" name="otpay" value="<%=sa[16]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">ETF Payable (3%)</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[17]%></label>
                            <input type="hidden" name="etf" value="<%=sa[17]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">EPF Deduction (8%)</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[18]%></label>
                            <input type="hidden" name="epf" value="<%=sa[18]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">EPF Payable (12%)</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=sa[19]%></label>
                            <input type="hidden" name="epfpay" value="<%=sa[19]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Total EPF Payable</label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><%=Math.round((Double.parseDouble(sa[18])+Double.parseDouble(sa[19]))*100)/100.00      %></label>
                            <input type="hidden" name="epfpay" value="<%=sa[19]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name"><h2><strong>TOTAL SALARY</strong></h2></label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><h2><strong><%=sa[20]%></strong></h2></label>
                            <input type="hidden" name="totsalary" value="<%=sa[20]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name"><h2><strong>Advance Paid</strong></h2></label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><h2><strong><%=sa[33]%></strong></h2></label>
                            <input type="hidden" name="advancepaid" value="<%=sa[33]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name"><h2><strong>Loan Paid</strong></h2></label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><h2><strong><%=sa[34]%></strong></h2></label>
                            <input type="hidden" name="loanpaid" value="<%=sa[34]%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 30px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name"><h2><strong>Salary Payable</strong></h2></label>
                            <label class="control-label col-md-6 col-sm-12 col-xs-12" for="name"><h2><strong><%=(Double.parseDouble(sa[20])-Double.parseDouble(sa[33])-Double.parseDouble(sa[34]))%></strong></h2></label>
                            <input type="hidden" name="salaryPayable" value="<%=(Double.parseDouble(sa[20])-Double.parseDouble(sa[33])-Double.parseDouble(sa[34]))%>"/>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" type="submit">Save Salary</button>
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
