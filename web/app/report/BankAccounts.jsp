<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.BankAccounts"%>
<%@page import="com.vertec.hibe.model.Bank"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>




<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>

<%
    List<BankAccounts> emp = (List<BankAccounts>) request.getAttribute("bankaccounts");
%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Bank Accounts
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
                    <span class="section">Bank Details</span>
                    <div >
                       
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Bank Name</th>
                                            <th>Branch</th>
                                            <th>Address</th>
                                            <th>Contact No</th>
                                            <th>Account No</th>
                                            <th>Account Type</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        
                                        <%
                                        for(BankAccounts e : emp){
                                        
                                        %>
                                        
                                        <tr>
                                            <td><%=e.getBankId().getBankName() %></td>
                                            <td><%=e.getBankId().getBranch() %></td>
                                            <td><%=e.getBankId().getAddress() %></td>
                                            <td><%=e.getBankId().getOfficeNo() %></td>
                                            <td><%=e.getAccountNo() %></td>
                                            <td><%=e.getAccountType() %></td>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                            </div>

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

