<%-- 
    Document   : customerDetails
    Created on : Oct 20, 2016, 11:17:04 AM
    Author     : User
--%>


<%@page import="com.vertec.hibe.model.Company"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<div class="">

    <div class="clearfix"></div>
    <%
        List<Company> CList = (List<Company>) request.getAttribute("cList");
    %>
    <div class="row">

        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Registered Company <small>up to now</small></h2>
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

                                    <th>Company Name </th>
                                    <th>Address </th>
                                    <th>Contact NO </th>
                                    <th>E mail </th>

                                    <th class=" no-link last"><span class="nobr">Update</span>
                                    </th>
                                    <th class=" no-link last"><span class="nobr">Delete</span>
                                    </th>
                                </tr>
                            </thead>

                            <tbody>

                                <% for (Company c : CList) {

                                %>
                                <tr>

                                    <td class=" "><%=c.getCompanyName()%></td>
                                    <td class=" "><%=c.getAddress()%></td>
                                    <td class=" "><%=c.getContactNo()%></td>
                                    <td class=" "><%=c.getEmail()%></td>
                                    

                                    <td class=" last">
                                        
                                        <form name="form1" method="post" action="Company?action=updateCompany2"><input type="hidden" name="cId" value="<%=c.getId()%>"/>
                                            <button type="submit" class="glyphicon glyphicon-edit">
                                            </button>
                                        </form>
                                       
                                    </td>
                                    <td class=" last"> 
                                        
                                        <form name="form1" method="post" action="Customer?action=RemoveCustomer"><input type="hidden" name="cId" value="<%=c.getId()%>"/>
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

        <br />
        <br />
        <br />

    </div>
</div>
                           
<%@include file="../../template/footer.jsp"%>