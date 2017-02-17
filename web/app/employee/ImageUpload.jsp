<%-- 
    Document   : formValidation
    Created on : Mar 21, 2016, 4:09:08 PM
    Author     : User
--%>

<%@page import="com.vertec.hibe.model.Attendance"%>
<%@page import="com.vertec.hibe.model.Employee"%>
<%@page import="com.vertec.hibe.model.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../template/header.jsp"%>
<%@include file="../../template/sidebar.jsp"%>
<script src="app/js/notAlert.js"></script>
<style type="text/css">
    #progressBox{
        width: 500px;
        height: auto;
    }
    #nametag{
        width: 350px;

    }
    #fileUpload{
        position: relative;
        overflow: hidden;
        margin: 10px;

    }
    #fileUpload input.upload{
        position: absolute;
        cursor: pointer;
        opacity: 0;
        top: 0;
        right: 0;
        margin: 0;
        padding: 0;
        font-size: 20px;
    }
    #message{
        height: 50px;
        width: 350px;
    }
</style>
<script src="../js/jquery.js" type="text/javascript"></script>
<script src="../js/jquery.form.js" type="text/javascript"></script>
<script src="../js/ProcessProgressBar.js" type="text/javascript"></script>
<script type="text/javascript">
    //
    function hide() {
        document.getElementById("formarea").className = 'hidden';
        document.getElementById("loadingarea").className = '';
    }
    //Get file name from path
    function getFileName() {
        var f1 = document.getElementById("fileName").value;
        var f2 = f1.split("\\");
        document.getElementById("nametag").value = f2[f2.length - 1];
    }
    var xmlhttp;
    //send multipart form data to server side
    function Create() {
        try {
            var xml = new XMLHttpRequest();
            var args = arguments;
            var context = this;
            var multipart = "";
            xml.open(args[0].method, args[0].url, true);
            if (args[0].method.search(/post/i) !== -1) {
                var boundary = Math.random().toString().substr(2);
                xml.setRequestHeader("content-type",
                        "multipart/form-data; charset=utf-8; boundary=" + boundary);
                for (var key in args[0].data) {
                    multipart += "--" + boundary
                            + "\r\nContent-Disposition: form-data; name=" + key
                            + "\r\nContent-type: application/octet-stream"
                            + "\r\n\r\n" + args[0].data[key] + "\r\n";
                }
                multipart += "--" + boundary + "--\r\n";
            }
            xml.onreadystatechange = function () {
                try {
                    if (xml.readyState === 4) {
                        context.txt = xml.responseText;
                        context.xml = xml.responseXML;
                        args[0].callback();
                    }
                } catch (e) {
                }
            }
            xml.send(multipart);
        } catch (e) {
        }
    }

</script>
<%
    List<Employee> e = (List<Employee>) request.getAttribute("employee");
%>
<%if (ca.checkUserAuth("ADD_USER", group) != null) {%>
<div class="">
    <div class="page-title">
        <div class="title_left">
            <h3>
                Employee Image
            </h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Add Employee Image<small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                        </li>
                        <li><a class="close-link"><i class="fa fa-close"></i></a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <center>
                        <div id="formarea" class="">
                            <form method="POST" action="ImageUploader" id="UploadForm" enctype="multipart/form-data">

                                <div class="item form-group">
                                    <label  class="control-label col-md-3 col-sm-3 col-xs-12">Employee</label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <select class="form-control" name="employee" id="employee"  required="required" >
                                            <option selected="true" disabled="true" >Select Employee</option>
                                            <%for (Employee emp : e) {%>
                                            <option value="<%=emp.getId().toString()%>"><%= emp.getFname() + " " + emp.getLname()%></option>
                                            <%
                                                }
                                            %>
                                        </select>                            
                                    </div>
                                </div>
                                <div id="progressBox">
                                    <input id="nametag" placeholder="Choose File" disabled="disabled"/>
                                    <div id="fileUpload" class='btn btn-danger'>
                                        <span>Browse</span>
                                        <input name='mmm' onchange="getFileName()" class="upload" type="file" id="fileName"/>
                                    </div>
                                    <div class='progress'>
                                        <div class='progress-bar progress-bar-success progress-bar-striped active' id='progressBar'> 
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div id='message'>
                                </div>
                                <input class="btn btn-success" onclick="hide()" type="submit" value="Upload File"/>
                            </form>
                        </div>



                        <div id="loadingarea" class="hidden">

                            <h3>Please wait....<small>this will take some time...</small></h3>
                            <img src="resources/images/progress.gif" width="250" height="200"/>

                        </div>

                    </center>
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
<script>
    $(document).ready(function () {
        $('input.tableflat').iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
        });
    });

    var asInitVals = new Array();
    $(document).ready(function () {
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
        $("tfoot input").keyup(function () {
            /* Filter on the column based on the index of this element's parent <th> */
            oTable.fnFilter(this.value, $("tfoot th").index($(this).parent()));
        });
        $("tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("tfoot input").blur(function (i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("tfoot input").index(this)];
            }
        });
    });
</script>