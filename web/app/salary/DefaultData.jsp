<%-- 
    Document   : addAllowance
    Created on : Sep 30, 2016, 9:09:08 AM
    Author     : Rohan Madusanka
--%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vertec.hibe.model.WorkingDays"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>


<script type="text/javascript">
    // calculate duration between two times 
    function CalculateWorkingHours(day) {
        var intime = document.getElementById(day + "intime").value;
        var out = document.getElementById(day + "outtime").value;
        var lh = document.getElementById(day + "lhrs").value;
        var timeStarth = new Date("01/01/2016 " + intime).getHours();
        var timeEndh = new Date("01/01/2016 " + out).getHours();

        var timeStartm = new Date("01/01/2016 " + intime).getMinutes();
        var timeEndm = new Date("01/01/2016 " + out).getMinutes();
        var timeStart = (timeStarth * 60) + timeStartm;
        var timeEnd = (timeEndh * 60) + timeEndm;

        var workingMinutes = timeEnd - timeStart - parseInt(lh);
        var hours = Math.round(workingMinutes / 60);
        var minutes = workingMinutes - (hours * 60);
        if (parseInt(minutes) < 0) {
            hours = hours - 1;
            var minutes = workingMinutes - (hours * 60);
        }

        document.getElementById(day + "tothour").value = hours + "Hr " + minutes + "Min";
    }

</script>



<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
<%
    List<WorkingDays> wd = (List<WorkingDays>) request.getAttribute("wd");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Default Working Hours
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
                    <form action="Salary?action=UpdateWorkingDays" method="post" class="form-horizontal form-label-left" novalidate>
                        <span class="section">Working Hours</span>
                        <table id="example" class="table table-striped responsive-utilities jambo_table">
                            <thead>
                                <tr class="headings">
                                    <th style="text-align: center;">Day</th>
                                    <th style="text-align: center;">In Time</th>
                                    <th style="text-align: center;">Out Time</th>
                                    <th style="text-align: center;">Lunch Time (Minutes)</th>
                                    <th style="text-align: center;">Total Working Hours</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (WorkingDays w : wd) {
                                %>
                                <tr>
                                    <td>
                                        <label class="control-label"><%=w.getDay().toUpperCase()%></label>
                                    </td>
                                    <td>
                                        <div class="col-md-12 col-sm-12 col-xs-12">
                                            <input class="form-control col-md-12 col-xs-12" onchange="CalculateWorkingHours('<%=w.getDay().toLowerCase().substring(0, 3)%>')" value="<%=w.getIntime()%>" id="<%=w.getDay().toLowerCase().substring(0, 3)%>intime" name="<%=w.getDay().toLowerCase().substring(0, 3)%>intime" required="required" type="time">
                                        </div>
                                    </td>
                                    <td>
                                        <div class="col-md-12 col-sm-12 col-xs-12">
                                            <input class="form-control col-md-12 col-xs-12" onchange="CalculateWorkingHours('<%=w.getDay().toLowerCase().substring(0, 3)%>')"  value="<%=w.getOuttime()%>" id="<%=w.getDay().toLowerCase().substring(0, 3)%>outtime" name="<%=w.getDay().toLowerCase().substring(0, 3)%>outtime" required="required" type="time">
                                        </div>
                                    </td>
                                    <td>
                                        <div class="col-md-12 col-sm-12 col-xs-12">
                                            <input class="form-control col-md-12 col-xs-12" onchange="CalculateWorkingHours('<%=w.getDay().toLowerCase().substring(0, 3)%>')"  value="<%=w.getLunchMinutes()%>" id="<%=w.getDay().toLowerCase().substring(0, 3)%>lhrs" name="<%=w.getDay().toLowerCase().substring(0, 3)%>lhrs" required="required" type="number">
                                        </div>
                                    </td>
                                    <td>
                                        <%
                                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                            Date date1 = format.parse(w.getIntime() + "");
                                            Date date2 = format.parse(w.getOuttime() + "");
                                            long minutes = ((date2.getTime() - date1.getTime()) / 60000) - w.getLunchMinutes();
                                            int minute = Integer.parseInt(minutes + "");

                                            int hour = minute / 60;
                                            minute -= (hour * 60);

                                            if (minute < 0) {
                                                hour--;
                                                minute -= (hour * 60);
                                            }
                                        %>
                                        <div class="col-md-6 col-sm-12 col-xs-12">
                                            <input class="form-control col-md-6 col-xs-12" value="<%out.write(hour + "Hr " + minute + "Min");%>" id="<%=w.getDay().toLowerCase().substring(0, 3)%>tothour" name="<%=w.getDay().toLowerCase().substring(0, 3)%>tothour" required="required" type="text">
                                        </div>
                                    </td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <button style="float: right;" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



<%} else {%>
<script type="text/javascript">
    window.location = '../../error403.jsp';</script>
    <%}%>


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>
<script>
    // initialize the validator function
    validator.message['date'] = 'not a real date';
    // validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
    $('form')
            .on('blur', 'input[required], input.optional, select.required', validator.checkField)
            .on('change', 'select.required', validator.checkField)
            .on('keypress', 'input[required][pattern]', validator.keypress);
    $('.multi.required')
            .on('keyup blur', 'input', function() {
                validator.checkField.apply($(this).siblings().last()[0]);
            });
    // bind the validation to the form submit event
    //$('#send').click('submit');//.prop('disabled', true);

    $('form').submit(function(e) {
        e.preventDefault();
        var submit = true;
        // evaluate the form using generic validaing
        if (!validator.checkAll($(this))) {
            submit = false;
        }

        if (submit)
            this.submit();
        return false;
    });
    /* FOR DEMO ONLY */
    $('#vfields').change(function() {
        $('form').toggleClass('mode2');
    }).prop('checked', false);
    $('#alerts').change(function() {
        validator.defaults.alerts = (this.checked) ? false : true;
        if (this.checked)
            $('form .alert').remove();
    }).prop('checked', false);
</script>
