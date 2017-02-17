<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.util.NewHibernateUtil"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.vertec.hibe.model.Branch"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="../js/notAlert.js"></script>
<script src="../js/branch.js"></script>

<script type="text/javascript">
 function nom_Success(text) {
        BootstrapDialog.show({
            title: 'Notification',
            type: BootstrapDialog.TYPE_SUCCESS,
            message: text,
            size: BootstrapDialog.SIZE_NORMAL
        });
    }
    function sm_warning(text) {
        BootstrapDialog.show({
            title: 'Warning',
            type: BootstrapDialog.TYPE_WARNING,
            message: text,
            size: BootstrapDialog.SIZE_SMALL
        });
    }
    //Delete Expense by id
    function DeleteExpense(id) {
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = xmlHttp.responseText;
                if (reply === "Success") {
                    nom_Success("Successfully Deleted");
                    setTimeout("location.href = 'Branch?action=ViewBranchExpenses';", 1500);
                } else {
                    sm_warning("Expenses is Not Updated, Please Try again.");
                }
            }
        };
        xmlHttp.open("POST", "Branch?action=DeleteExpense&id=" + id, true);
        xmlHttp.send();
    }
    
    
</script>


<%
    SessionFactory sf = NewHibernateUtil.getSessionFactory();
    Session ses = sf.openSession();


%>

<%if (ca.checkUserAuth("MANAGE_BRANCH_EXPENSES", group) != null) {%>

<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Branch Expense Management
            </h3>
        </div>


    </div>
    <div class="clearfix"></div>
    <%if (ca.checkUserAuth("ADD_BRANCH_EXPENSE", group) != null) {%>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Add New Branch Expense<small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>

                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <form action="Branch?action=SaveBranchExpenses" method="post" class="form-horizontal form-label-left" novalidate>

                        </p>
                        <span class="section"></span>
                        <div style="padding-top: 10px;" id="branchwise">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Select Branch <span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <select class="form-control" name="branchId" id="branchId" >
                                    <option selected="true" disabled value="">Select Branch</option>

                                    <%

                                        String hql = "SELECT branch_id,branch_name FROM branch";

                                        if (ca.checkUserAuth("OTHER_BRANCH", group) == null) {
                                            hql += " branch_id='" + user.getBranchBranchId().getBranchId() + "'";
                                        }

                                        SQLQuery query = ses.createSQLQuery(hql);

                                        List<Object[]> inList = query.list();

                                        for (Object[] b : inList) {%>

                                    <option value="<%=b[0]%>"><%=b[1]%></option>
                                    <%
                                        }

                                    %>

                                </select>                              
                            </div>
                        </div>   
                        <div class="clearfix"></div>
                        <div class="item form-group" style="padding-top: 10px;">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Amount<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="branchName" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="amount" placeholder="Enter the amount" required="required" type="text">
                            </div>
                        </div>
                        <div class="item form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Description<span class="required"></span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input id="contactNo" class="form-control col-md-7 col-xs-12"  data-validate-words="1" name="desc"  required="required" placeholder="Enter the description" type="tel">
                            </div>
                        </div>

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-md-offset-4 col-lg-offset-4">
                                <button id="send" type="submit" class="btn btn-success">Submit</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
    <%}%>
    <div class="row">
        <%
//            List<Branch> bList = (List<Branch>) request.getAttribute("bList");
        %>
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <!--                    <h2>Registered users <small>up to now</small></h2>-->
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

                                    <th>Branch ID </th>
                                        <%    if (ca.checkUserAuth("OTHER_BRANCH", group) != null) {                   %>
                                    <th>Branch Name </th>
                                        <% }%>

                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Description</th>
                                        <%    if (ca.checkUserAuth("DELETE_BRANCH_EXPENSE", group) != null) {                   %>
                                    
                                    <th class=" no-link last"><span class="nobr">Action</span></th>
                                    <% }%>

                                </tr>
                            </thead>

                            <tbody>
                                <%                                    
                                    String hql = "SELECT e.branch_expenses_id,b.branch_name,e.date,e.amount,e.description FROM branch_expenses e inner join branch b on. e.branch_branch_id=b.branch_id ";

                                    if (ca.checkUserAuth("OTHER_BRANCH", group) != null) {

                                    } else {
                                        hql += "where b.branch_id='" + user.getBranchBranchId().getBranchId() + "'";
                                    }

                                    SQLQuery query = ses.createSQLQuery(hql);

                                    List<Object[]> inList = query.list();

                                    for (Object[] list : inList) {
                                %>


                                <tr>

                                    <td class=""> <%=list[0]%> </td>

                                    <%                                   if (ca.checkUserAuth("OTHER_BRANCH", group) != null) {
                                    %>
                                    <td class=""> <%=list[1]%> </td>
                                    <%
                                        }
                                    %>



                                    <td class=""> <%=list[2]%> </td>
                                    <td class=""> <%=list[3]%> </td>
                                    <td class=""> <%=list[4]%> </td>

                                    <%if (ca.checkUserAuth("DELETE_BRANCH_EXPENSE", group) != null) {%><td class="last"><input type="submit" value="Delete" class="btn btn-warning" onclick="DeleteExpense('<%=list[0]%>')" /></td><%}%> 
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
<script>
    $(document).ready(function() {
        $('input.tableflat').iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
        });
    });

    var asInitVals = new Array();
    $(document).ready(function() {
        var oTable = $('#example').dataTable({
            "oLanguage": {
                "sSearch": "Search all columns:"
            },
            "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0]
                } //disables sorting for column one
            ],
            'iDisplayLength': 12,
            "sPaginationType": "full_numbers",
            "dom": 'T<"clear">lfrtip',
            "tableTools": {
                "sSwfPath": "${context}/resources/js/datatables/tools/swf/copy_csv_xls_pdf.swf"
            }
        });
        $("tfoot input").keyup(function() {
            /* Filter on the column based on the index of this element's parent <th> */
            oTable.fnFilter(this.value, $("tfoot th").index($(this).parent()));
        });
        $("tfoot input").each(function(i) {
            asInitVals[i] = this.value;
        });
        $("tfoot input").focus(function() {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("tfoot input").blur(function(i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("tfoot input").index(this)];
            }
        });
    });
</script>