
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%@page import="com.vertec.hibe.model.Branch"%>
<%@page import="com.vertec.hibe.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/invoice.js"></script>
<script src="app/js/notAlert.js"></script>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Invoice
                <small>
                    Some examples to get you started
                </small>
            </h3>
        </div>

    </div>
    <%
        List<Object[]> invoiceList = (List<Object[]>) request.getAttribute("invoiceList");

    %>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Maintain Invoice<small>to update,view and delete invoices</small></h2>
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
                        <table class="table table-bordered table-hover table-striped" id="invDate" >
                            <thead>
                                <tr>
                                    <th bgcolor="#EFEFEF" >Invoice ID</th>
                                    <th bgcolor="#EFEFEF">Date</th>
                                    <th bgcolor="#EFEFEF">Amount</th>
                                    <th bgcolor="#EFEFEF">View</th>
                                    <!--<th bgcolor="#EFEFEF">Update</th>-->
                                    <th bgcolor="#EFEFEF">Delete</th>

                                </tr>
                            </thead>
                            <tbody id="gendata">
                                <%                                            
                                    for (Object[] i : invoiceList) {
                                %>
                                <tr>
                                    <td width="20%" align="center" > <% out.println(i[0].toString());%></td>
                                    <td width="20%" align="center" > <% out.println(i[1].toString());%></td>
                                    <td width="30%" align="center" > <% out.println(i[2].toString());%></td>
                                    <td width="15%" align="center" > 
                                        <form action="Invoice?action=ViewInvoice" method="POST">
                                            <input type="hidden" name="invoiceId" value="<% out.println(i[0].toString());%>"/>
                                            <input type="submit" value="View" name="viewInvoice" />
                                        </form>
                                    </td>
<!--                                    <td width="15%" align="center" >
                                      

                                        <form action="Invoice?action=UpdateInvoice" method="POST">
                                            <input type="hidden" name="invoiceId" value="<% //out.println(i[0].toString());%>"/>
                                            <input type="submit" value="Update" name="updateInvoice" />
                                        </form>

                                    </td>-->
                                    <td width="15%" align="center" >
                                        <input type="button" value="Delete" name="deleteInvoice" onclick="reqdeleteInvoice(<%=i[0].toString()%>);" />
                                        
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

<!-- footer content -->
<%@include file="../../template/footer.jsp"%>

