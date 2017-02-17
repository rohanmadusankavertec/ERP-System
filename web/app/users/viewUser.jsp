<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Company"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="js/sysuser.js"></script>
<script src="js/notAlert.js"></script>



<%if (ca.checkUserAuth("UPDATE_USER", group) != null) {%>
<%
    List<Company> company = (List<Company>) request.getAttribute("company");

%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                User Update
            </h3>
        </div>
        <%SysUser su = (SysUser) request.getAttribute("su");%>
    </div>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form action="User?action=UpSel" method="post" class="form-horizontal form-label-left" novalidate >

                        <span class="section">User Update</span>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"> Name <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="name" class="form-control col-md-7 col-xs-12"  name="name" data-validate-words="2" required="required" type="text" value="<%=su.getFirstName() + " " + su.getLastName()%>">
                                <input id="userId" name="userId" required="required" type="hidden" value="<%=su.getSysuserId()%>">
                            </div>
                        </div>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Email <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="email" id="email" name="email" required="required" class="form-control col-md-7 col-xs-12" value="<%=su.getEmailAdd()%>">
                            </div>
                        </div>

                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">Contact No <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="tel" id="contactNo" name="contactNo" required="required" placeholder="Contact Number" class="form-control col-md-7 col-xs-12" value="<%=su.getContactNo()%>">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">NIC No <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" id="nicNo" name="nicNo" required="required" placeholder="NIC Number" class="form-control col-md-7 col-xs-12" value="<%=su.getNicNo()%>">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="contactNo">Date of Birth <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="date" id="dob" name="dob" required="required" placeholder="Date of Birth" class="form-control col-md-7 col-xs-12" value="<%=su.getDob()%>">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Select User Group</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="userGroup" id="userGroup"  required="required" >
                                    <option selected="true" disabled="true" >Select User Group</option>

                                    <%
                                        SessionFactory sf = NewHibernateUtil.getSessionFactory();
                                        Session ses = sf.openSession();

                                        SQLQuery query = ses.createSQLQuery("SELECT user_group_id,user_group_name FROM user_group");

                                        List<Object[]> inList = query.list();

                                        for (Object[] list : inList) {%>

                                    <option value="<%= list[0].toString()%>"><%= list[1].toString()%></option>


                                    <%
                                        }

                                    %>
                                </select>                            </div>
                        </div>

                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Select Branch</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="userBranch" id="userBranch"  required="required" >
                                    <option selected="true" disabled="true" >Select Branch</option>


                                    <%                                         SQLQuery query2 = ses.createSQLQuery("SELECT branch_id,branch_name FROM branch");

                                        List<Object[]> inList2 = query2.list();

                                         for (Object[] list2 : inList2) {%>

                                    <option value="<%= list2[0].toString()%>"><%= list2[1].toString()%></option>


                                    <%
                                        }

                                    %>



                                </select>                            </div>
                        </div>
                        <div class="item form-group">
                            <label for="password2" class="control-label col-md-3 col-sm-3 col-xs-12">Select Company</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">

                                <select class="form-control" name="company" id="company"  required="required" >
                                    <option selected="true" disabled="true" >Select Company</option>
                                    <%                                        for (Company c : company) {
                                    %>
                                    <option value="<%=c.getId()%>"><%=c.getCompanyName()%></option>
                                    <%
                                        }
                                    %>
                                </select>                            
                            </div>
                        </div>

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <button id="send" type="submit" class="btn btn-success">Update</button>
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
    window.location = 'error403.jsp';
</script>
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
            .on('keyup blur', 'input', function () {
                validator.checkField.apply($(this).siblings().last()[0]);
            });

    // bind the validation to the form submit event
    //$('#send').click('submit');//.prop('disabled', true);

    $('form').submit(function (e) {
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
    $('#vfields').change(function () {
        $('form').toggleClass('mode2');
    }).prop('checked', false);

    $('#alerts').change(function () {
        validator.defaults.alerts = (this.checked) ? false : true;
        if (this.checked)
            $('form .alert').remove();
    }).prop('checked', false);
</script>
