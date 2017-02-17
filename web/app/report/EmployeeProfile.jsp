<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page import="com.vertec.hibe.model.Designation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>




<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>

<%
    Employee e = (Employee) request.getAttribute("employee");
    System.out.print(e.getImage());
    System.out.print("_______________________");
    // String path = getServletContext().getRealPath(e.getImage());
    String path = e.getImage();
    System.out.print(path);
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Employee Profile
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

                    <span class="section">Employee Profile</span>
                    <div >
                        <h4>
                            <div class="table-responsive">
                                <center>
                                    <img src="<%=path%>" width="200" height="200"/>


                                    <table class="table" style=" width: 500px;">
                                        <tbody>
                                            <tr>
                                                <th>First Name</th>
                                                <td><%=e.getFname()%></td>
                                            </tr>
                                            <tr>
                                                <th >Last Name</th>
                                                <td><%=e.getLname()%></td>
                                            </tr>
                                            <tr>
                                                <th>NIC</th>
                                                <td><%=e.getNic()%></td>
                                            </tr>
                                            <tr>
                                                <th>DOB</th>
                                                <td><%=e.getDob()%></td>
                                            </tr>
                                            <tr>
                                                <th>Gender</th>
                                                <td><%
                                                    if (e.getGender()) {
                                                        out.write("Male");
                                                    } else {
                                                        out.write("Female");
                                                    }
                                                    %></td>
                                            </tr>

                                            <tr>
                                                <th>Contact No</th>
                                                <td><%=e.getContactNo()%></td>
                                            </tr>
                                            <tr>
                                                <th>In an Emergency</th>
                                                <td><%=e.getEmergency()%></td>
                                            </tr>
                                            <tr>
                                                <th>Weight (Kg)</th>
                                                <td><%=e.getWeight()%></td>
                                            </tr>
                                            <tr>
                                                <th>Height</th>
                                                <td><%=e.getHeight()%></td>
                                            </tr>
                                            <tr>
                                                <th>Civil Status</th>
                                                <td><%
                                                    if (e.getCivilStatus()) {
                                                        out.write("Married");
                                                    } else {
                                                        out.write("Single");
                                                    }
                                                    %></td>
                                            </tr>
                                            <tr>
                                                <th>Address</th>
                                                <td><%=e.getAddress()%></td>
                                            </tr>
                                            <tr>
                                                <th>Department</th>
                                                <td><%=e.getDesignationId().getDepartmentId().getName()%></td>
                                            </tr>
                                            <tr>
                                                <th>Designation</th>
                                                <td><%=e.getDesignationId().getName()%></td>
                                            </tr>
                                            <tr>
                                                <th>Educational/ Professional</th>
                                                <td><%=e.getEducational()%></td>
                                            </tr>
                                            <tr>
                                                <th>Qualification</th>
                                                <td><%=e.getQualification()%></td>
                                            </tr>
                                            <tr>
                                                <th>Working Experience</th>
                                                <td><%=e.getExperience()%></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </center>
                            </div>

                        </h4>
                    </div>
                </div>
                <input type="button" onclick="window.print()" value="Print Report"/>
            </div>
        </div>
    </div>
</div>



<%} else {%>
<script type="text/javascript">
    window.location = '../../error403.jsp';
</script>
<%}%>


<!-- footer content -->
<%@include file="../../template/footer.jsp"%>

