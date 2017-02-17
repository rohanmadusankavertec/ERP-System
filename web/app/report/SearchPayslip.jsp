<%-- 
    Document   : FilterPO
    Created on : Aug 23, 2016, 12:31:21 PM
    Author     : vertec-r
--%>

<%@page import="com.vertec.hibe.model.Salary"%>
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

<script type="text/javascript">
    //load salary period according to employee
    function loadSalaryPeriod() {
        $("#salary").empty();
        var desi = document.getElementById('salary');
        var t1 = document.createElement("option");

        t1.innerHTML = "Select Salary Period";
        desi.appendChild(t1);
        var emp = document.getElementById('employee').value;
        $.ajax({
            type: "POST",
            url: "Report?action=getSalaryperiod&id=" + emp,
            success: function(msg) {
                var reply = eval('(' + msg + ')');

                var arrLn1 = reply.des;
                for (var f = 0; f < arrLn1.length; f++) {
                    var t = document.createElement("option");
                    var val = arrLn1[f].id;
                    t.value = val;
                    t.innerHTML = arrLn1[f].from +" - "+ arrLn1[f].to;
                    desi.appendChild(t);
                }
            }
        });
    }

</script>



<%
    List<Employee> e = (List<Employee>) request.getAttribute("employee");
%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                View Pay Slip
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
                    <form action="Report?action=Viewpayslip" method="post">
                        <div class="item form-group" style="padding-top: 50px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Employee </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="employee" id="employee" onchange="loadSalaryPeriod()" required="required">
                                    <option selected="true" disabled value="">Select Employee</option>
                                    <% for (Employee emp : e) {%>
                                    <option value="<%=emp.getId()%>"><%=emp.getFname() + "_" + emp.getLname()%></option>
                                    <%}%>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;" id="supl">
                            <label class="control-label col-md-4 col-sm-12 col-xs-12" for="name">Select Salary </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="salary" id="salary"  required="required">
                                    <option selected="true" disabled value="">Select Salary Period</option>
                                </select>                              
                            </div>
                        </div>
                        <div class="item form-group" style="padding-top: 50px;">
                            <div class="col-xs-12 col-lg-offset-3">
                                <button class="btn btn-success" type="submit">View Pay Slip</button>
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
