
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vertec.hibe.model.Invoice"%>
<%@page import="java.util.Date"%>
<%@page import="com.vertec.hibe.model.BranchProductmaster"%>

<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>

<%
    List<Object[]> bpm = (List<Object[]>) request.getAttribute("oblist");
%>


<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Credit Limit Report
                <small>

                </small>
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2><small></small></h2>



                    <h4>DATE :<%=new Date()%></h4>
                    <br>
                    <br>
                    <br>



                    <center>
                        <div class="table-responsive">
                            <table id="example" class="table table-bordered table-hover table-striped">
                                <thead>
                                    <tr class="headings">
                                        <th>Customer </th>
                                        <th>Invoiced Date</th>
                                        <th>Invoice Total</th>
                                        <th>Paid</th>
                                        <th>Outstanding</th>
                                        <th>Credit Limit</th>
                                        <th>Credit Period</th>
                                        <th>Period End Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (Object[] b : bpm) {
                                            Invoice i = (Invoice) b[0];
                                            Double outs = (Double) b[1];

                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            Calendar c = Calendar.getInstance();
                                            c.setTime(i.getInvoicedDate());
                                            c.add(Calendar.DATE, i.getCustomerId().getCreditPeriod());
                                            String output = sdf.format(c.getTime());
                                            Date date1 = sdf.parse(output);
                                            if (date1.before(new Date())) {
                                    %>
                                    <tr style="background-color: #fce3ef;">
                                        <%} else {%>
                                    <tr>
                                        <%}%>
                                        <td><%=i.getCustomerId().getCustomerName()%></td>
                                        <td><%=i.getInvoicedDate()%></td>
                                        <td><%=i.getTotAfterDiscount()%></td>
                                        <td><%=(i.getTotAfterDiscount() - outs)%></td>
                                        <td><%=outs%></td>
                                        <td><%= i.getCustomerId().getCreditLimit()%></td>
                                        <td><%=i.getCustomerId().getCreditPeriod()%></td>
                                        <td><%=output%></td>
                                    </tr>
                                    <%}%>
                                </tbody>

                            </table>
                        </div>
                    </center>

                    <br>
                    <br>





                    <div class="clearfix"></div>
                </div>

            </div>
        </div>
    </div>











    <button class="btn btn-default" onclick="window.print();"><i class="fa fa-print"></i> Print</button>

    <div class="clearfix"></div>
    <div class="clearfix"></div>
</div>




<%@include file="../../template/footer.jsp"%>