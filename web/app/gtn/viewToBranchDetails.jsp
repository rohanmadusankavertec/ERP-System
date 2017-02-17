<%-- 
    Document   : viewGTNDetails
    Created on : Dec 29, 2016, 5:17:44 PM
    Author     : Java-Dev-Ruchira
--%>

<%@page import="com.vertec.hibe.model.GtnInfo"%>
<%@page import="com.vertec.hibe.model.Gtn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

 <%
     List<Gtn> gList = (List<Gtn>) request.getAttribute("g");
     GtnInfo gin = (GtnInfo) request.getAttribute("info");
 %>
 
 <div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                GTN Info Details
                <small>
                    
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
                    <h2>  <small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    
<!--                     <form action="GTN?action=actionByToBranch" method="post" class="form-horizontal form-label-left" validate>-->
<!--                        
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">From Branch
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="fbranch" name="fbranch" class="form-control col-md-7 col-xs-12" type="text" value="<%=gin.getFromBranch().getBranchName() %>">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Added BY
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="added" name="added" class="form-control col-md-7 col-xs-12"  type="text" value="<%=gin.getAddedBy().getFirstName()+" "+gin.getAddedBy().getLastName() %>">
                            </div>
                        </div>-->

                        


                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">From Branch
                            </label>
                            <label style=""class="control-label col-md-3 col-sm-3 col-xs-12" for="name"><%=gin.getFromBranch().getBranchName() %></label>
                            
                        </div>
                            <div class="clearfix"></div>
                            
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Added BY
                            </label>
                            <label style="" class="control-label col-md-3 col-sm-3 col-xs-12" for="name"><%=gin.getAddedBy().getFirstName()+" "+gin.getAddedBy().getLastName() %></label>
                            
                        </div>    

                       
                        <div class="clearfix"></div>
                        <div class="ln_solid"></div>

                    
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
                <div class="ln_solid"></div>       
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Total Amount
                            </label>
                            <label style="" class="control-label col-md-3 col-sm-3 col-xs-12" for="name"><%=gin.getTotal() %>
                            </label>
                            
                        </div>

                        <div class="clearfix"></div>
                        <div class="ln_solid"></div>        
                         <div class="form-group">
                            <div class="col-md-6 col-md-offset-3">
                                <!--                                <button type="submit" class="btn btn-primary">Cancel</button>-->
                                <form style="float: left;" name="fome1" action="GTN?action=actionByToBranch1" method="post" >
                                <input id="hidden" name="hidden"   type="hidden" value="<%=gin.getGtninfoId() %>">
                                <button style="float: right;" id="send" type="submit" class="btn btn-success">Accept</button>
                                </form>
                                
                                <form style="float: left;"  name="form2" action="GTN?action=actionByToBranch2" method="post" >
                                <input id="hidden" name="hidden"   type="hidden" value="<%=gin.getGtninfoId() %>">
                                <button style="" id="send" type="submit" class="btn btn-danger">Cancel</button>
                                </form>
                                
                                
                            </div>
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