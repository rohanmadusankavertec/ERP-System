<%-- 
    Document   : viewGTNDetails
    Created on : Dec 29, 2016, 5:17:44 PM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.Gtn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

 <%
     List<Gtn> gList = (List<Gtn>) request.getAttribute("g");
 %>
 
 <div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                GTN Info Details
                <small>
                    All GTN info Details Here 
                </small>
            </h3>
        </div>
        <div class="title_right">
            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">   
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2> GTN info <small>up to now</small></h2>
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

                                <th>Product Name </th>
                                <th>QTY </th>
                                <th>Selling Price</th>
                                <th>Total </th>
                                
                                
                            </tr>
                        </thead>

                        <tbody>

                            <% for (Gtn g : gList) {%>
                            <tr>

                                <td class=" "><%=g.getProductMasterId().getProductId().getProductName() %></td>
                                <td class=" "><%=g.getQty()%></td>
                                <td class=" "><%=g.getProductMasterId().getSellingPrice() %></td>
                                <td class=" "><%=g.getTotal()%></td>
                                
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