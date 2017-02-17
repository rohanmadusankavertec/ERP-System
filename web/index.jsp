<!DOCTYPE html>
<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Enterprise Resource Planning System</title>

        <!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/css/form-elements.css">
        <link rel="stylesheet" href="assets/css/style.css">
        
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">

        <link rel="shortcut icon" href="resources/images/erp_icon.ico" />
        <script src="assets/js/jquery-1.11.1.min.js" type="text/javascript">
        </script>
        <script src="assets/js/jquery.leanModal.min.js" type="text/javascript">
        </script>

        <style type="text/css">
            #backgrad {
                /*background: #00aeef;  For browsers that do not support gradients */
                background: #6a10a7;
                background: -webkit-linear-gradient(#00aeef, #6a10a7); /* For Safari 5.1 to 6.0 */
                background: -o-linear-gradient(#00aeef, #6a10a7); /* For Opera 11.1 to 12.0 */
                background: -moz-linear-gradient(#00aeef, #6a10a7); /* For Firefox 3.6 to 15 */
                background: linear-gradient(#00aeef,#6a10a7); /* Standard syntax */
            }
            #main {
                background: #6a10a7; 
                background: -webkit-linear-gradient(#00aeef, #6a10a7); /* For Safari 5.1 to 6.0 */
                background: -o-linear-gradient(#00aeef, #6a10a7); /* For Opera 11.1 to 12.0 */
                background: -moz-linear-gradient(#00aeef, #6a10a7); /* For Firefox 3.6 to 15 */
                background: linear-gradient(#00aeef, #6a10a7); /* Standard syntax */
            }
            #main2 {
                background: #6a10a7;  
                background: -webkit-linear-gradient(#00aeef, #6a10a7); /* For Safari 5.1 to 6.0 */
                background: -o-linear-gradient(#00aeef, #6a10a7); /* For Opera 11.1 to 12.0 */
                background: -moz-linear-gradient(#00aeef, #6a10a7); /* For Firefox 3.6 to 15 */
                background: linear-gradient(#00aeef, #6a10a7); /* Standard syntax */
            }
        </style>


        <script type="text/javascript">

            getBrowser();
            function getBrowser() {
                var isChrome = !!window.chrome && !!window.chrome.webstore;
                if (!isChrome) {
                    alert("This Application correctly working on chrome browser. Please change the browser");
                }
            }
            function sm_warning(text) {
                BootstrapDialog.show({
                    title: 'Warning',
                    type: BootstrapDialog.TYPE_WARNING,
                    message: text,
                    size: BootstrapDialog.SIZE_SMALL
                });
            }
            function SignUp() {
                var co = document.getElementById("companyname").value;
                var ad = document.getElementById("address").value;
                var cn = document.getElementById("contactNo").value;
                var em = document.getElementById("email").value;

                var fn = document.getElementById("firstName").value;
                var ln = document.getElementById("lastName").value;
                var ue = document.getElementById("uEmail").value;
                var su = document.getElementById("sUsername").value;
                var sp = document.getElementById("sPassword").value;
                var sr = document.getElementById("sRepeat").value;
                if (sp !== sr) {
                    alert("Passwords didn't match........");
                } else {
                    var xmlHttp;
                    if (window.XMLHttpRequest) {
                        xmlHttp = new XMLHttpRequest();
                    } else
                    {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    xmlHttp.onreadystatechange = function ()
                    {
                        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                        {
                            var reply = xmlHttp.responseText;
                            if (reply === "Success") {
                                document.getElementById("signupok").innerHTML = "Account Created Successfully.....";
                                document.getElementById("companyname").value = "";
                                document.getElementById("address").value = "";
                                document.getElementById("contactNo").value = "";
                                document.getElementById("email").value = "";
                            } else {
                                document.getElementById("signuperror").innerHTML = "Oopz.. Something went wronge........";
                            }
                        }
                    };
                    xmlHttp.open("POST", "Signup?company=" + co + "&address=" + ad + "&contact=" + cn + "&email=" + em + "&first=" + fn + "&last=" + ln + "&uemail=" + ue + "&username=" + su + "&password=" + sp, true);
                    xmlHttp.send();
                }
            }



            function validateSignup() {
                var co = document.getElementById("companyname").value;
                var ad = document.getElementById("address").value;
                var cn = document.getElementById("contactNo").value;
                var em = document.getElementById("email").value;
                document.getElementById("signuperror").innerHTML = "";
                if (co === "") {
                    document.getElementById("signuperror").innerHTML = "Please Enter the company Name....";
                } else if (ad === "") {
                    document.getElementById("signuperror").innerHTML = "Please Enter the company Address....";
                } else if (cn === "") {
                    document.getElementById("signuperror").innerHTML = "Please Enter the Contact No....";

                } else if (ValidateMobNumber("contactNo")) {

//                    document.getElementById("signuperror").innerHTML = "Please Enter Valid Contact No....";
                } else if (em === "") {
                    document.getElementById("signuperror").innerHTML = "Please Enter the Company E-mail....";

                } else if (check_email(em)) {
                    document.getElementById("signuperror").innerHTML = "Please Enter Valid E-mail Address....";
                } else {
                    $('#main,#context,#main2').show();
                }
            }

            function validateSignup2() {
                var fn = document.getElementById("firstName").value;
                var ln = document.getElementById("lastName").value;
                var ue = document.getElementById("uEmail").value;
                var su = document.getElementById("sUsername").value;
                var sp = document.getElementById("sPassword").value;
                var sr = document.getElementById("sRepeat").value;
                document.getElementById("signuperror2").innerHTML = "Please Enter the First Name....";

                if (fn === "") {
                    document.getElementById("signuperror2").innerHTML = "Please Enter the First Name....";
                } else if (ln === "") {
                    document.getElementById("signuperror2").innerHTML = "Please Enter the Last Name....";
                } else if (ue === "") {
                    document.getElementById("signuperror2").innerHTML = "Please Enter the E-Mail Address....";

                } else if (su === "") {
                    document.getElementById("signuperror2").innerHTML = "Please Enter the Username....";

                } else if (sp === "") {
                    document.getElementById("signuperror2").innerHTML = "Please Enter the Password....";

                } else if (sr === "") {
                    document.getElementById("signuperror2").innerHTML = "Please Enter the Password....";

                } else if (check_email2()) {
                    document.getElementById("signuperror2").innerHTML = "Please Enter Valid E-mail Address....";
                } else {
                    $('#main,#context,#main2').hide();
                    SignUp();
                }
            }

            function check_email() {
                var email = document.getElementById("email");
                var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                if (!filter.test(email.value)) {
                    email.focus;
                    return true;
                } else {
                    return false;
                }
            }
            function check_email2() {
                var email = document.getElementById("uEmail");
                var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                if (!filter.test(email.value)) {
                    email.focus;
                    return true;
                } else {
                    return false;
                }
            }
            function ValidateMobNumber(txtMobId) {
                var fld = document.getElementById(txtMobId);

                if (fld.value == "") {
                    document.getElementById("signuperror").innerHTML = "Please Enter the Contact No....";
                    fld.focus();
                    return true;
                } else if (isNaN(fld.value)) {
                    document.getElementById("signuperror").innerHTML = "The phone number contains illegal characters....";
                    fld.focus();
                    return true;
                } else if (!(fld.value.length == 10)) {
                    document.getElementById("signuperror").innerHTML = "Please Enter 10 digit Mobile Number....";
                    fld.focus();
                    return true;
                }
                return false;
            }

        </script>





        <script type="text/javascript">
//            $(document).ready(function (e) {
//                $('#btn1').click(function () {
//                    $('#main,#context,#main2').show();
//                });
//                $('#btn2').click(function (e) {
//                    $('#main,#context,#main2').hide();
//                });
//            });


        </script>


    </head>

    <body id="backgrad">

        <!-- Top content -->
        <div class="top-content" style="height: 790px;">

            <div class="inner-bg">
                <div class="container">

                    <div  class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1><strong>Vertec</strong> Enterprise Resource Planning  System</h1>
                            <div class="description">
                                <p>
                                    The best way to manage your business.  
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-5" id="logindivr">

                            <div  class="form-box">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3>Login to our site</h3>
                                        <p>Enter username and password to log on:</p>
                                    </div>
                                    <div class="form-top-right">
                                        <i class="fa fa-lock"></i>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form action="Login" method="post">

                                        <div class="form-group">
                                            <label class="sr-only" for="form-username">Username</label>
                                            <input type="text" name="username" placeholder="Username..." class="form-username form-control" id="form-username">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-password">Password</label>
                                            <input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password">
                                        </div>
                                        <button type="submit" class="btn">Sign in!</button>
                                    </form>



                                    <div style="margin-top: 10px" class="form-group">
                                        <!-- Button -->
                                        <%
                                            String success_message = (String) request.getAttribute("Success_Message");
                                            String error_message = (String) request.getAttribute("Error_Message");
                                        %>
                                        <div class="col-sm-12 controls ">
                                            <strong><font color="red">
                                                <% if (success_message != null) {
                                                        out.println(success_message);
                                                    }%>
                                                <% if (error_message != null) {
                                                        out.println(error_message);
                                                    }%>
                                                </font>
                                            </strong> 

                                        </div>
                                    </div>



                                </div>
                            </div>

                            <div class="social-login" style="margin-top: -10px;">
                                <div class="description">
                                    <p style="color: #ffffff; line-height: 1.5;text-align: left; font-size: 13px;">
                                        <strong>Vertec IT Solutions</strong><br>
                                        Registered Address : No 11/B, Mawala Junction, Wadduwa,Sri Lanka. (12560)<br>
                                        Telephone : (+94) 38 22 96 305 /  (+94) 38 22 85 601<br>
                                        Hotline : (+94) 71 81 57 57 5<br>
                                        Email : vertecitsolutions@gmail.com 
                                    </p>
                                </div>
                                <!--	                        	<h3>...or login with:</h3>-->
                                <!--	                        	<div class="social-login-buttons">
                                                                                <a class="btn btn-link-2" href="#">
                                                                                        <i class="fa fa-facebook"></i> Facebook
                                                                                </a>
                                                                                <a class="btn btn-link-2" href="#">
                                                                                        <i class="fa fa-twitter"></i> Twitter
                                                                                </a>
                                                                                <a class="btn btn-link-2" href="#">
                                                                                        <i class="fa fa-google-plus"></i> Google Plus
                                                                                </a>
                                                                        </div>-->
                            </div>

                        </div>

                        <div class="col-sm-1 middle-border"></div>
                        <div class="col-sm-1"></div>

                        <div class="col-sm-5" id="signupdivr">

                            <div class="form-box" id="div11">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3>Sign up now</h3>
                                        <p>Fill in the form below to get instant access:</p>
                                    </div>
                                    <div class="form-top-right">
                                        <i class="fa fa-pencil"></i>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <!--<form role="form" action="" method="post" class="registration-form">-->
                                    <div class="form-group">
                                        <label class="sr-only" for="form-first-name">Company name</label>
                                        <input type="text" id="companyname" name="companyname" placeholder="Company name..." class="form-first-name form-control">
                                    </div>
                                    <div class="form-group">
                                        <label class="sr-only" for="form-last-name">Address</label>
                                        <input type="text" id="address" name="address" placeholder="Address..." class="form-last-name form-control">
                                    </div>
                                    <div class="form-group">
                                        <label class="sr-only" for="form-last-name">Contact No.</label>
                                        <input type="text" id="contactNo" name="contactNo" placeholder="Contact Number..." class="form-last-name form-control">
                                    </div>
                                    <div class="form-group">
                                        <label class="sr-only" for="form-email">Email</label>
                                        <input type="text" id="email" name="email" placeholder="Email..." class="form-email form-control">
                                    </div>

                                    <button type="button" onclick="validateSignup()" id="btn1" class="btn" style="width: 100%;">Next</button>
                                    <!--</form>-->

                                    <div style="margin-top: 10px" class="form-group">

                                        <div class="col-sm-12 controls ">
                                            <strong>
                                                <font style="color: #19b9e7;" id="signupok">

                                                </font>


                                                <font color="red" id="signuperror">

                                                </font>
                                            </strong> 

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </div>
        <!-- Footer -->

        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  -->
        <div id="main2" style="position:absolute; width:500px; height:600px;margin-left: 30%;margin-right: auto; z-index:1;display: none; top:0; background-color: #ffffff;top:100px;" ></div>
        <!--<div style="background-color: #0063DC; display: none; width:100%; height:100%;z-index:1;opacity: 0.6"  id="backdiv">-->
        <div id="main" style="position:absolute; width:100%; height:100%; z-index:2;opacity:0.75; display: none; top:0">

            <div id="context" class="form-box" style="position: absolute;z-index:5; display: none;width: 500px;margin-left: 30%; margin-right: auto; display:none; top:80px;border: 2px solid #ffffff;border-radius: 4px;" >
                <div class="form-top">
                    <div class="form-top-left">
                        <h3>User Details</h3>
                        <p>Fill in the form below to get instant access:</p>
                    </div>
                    <div class="form-top-right">
                        <i class="fa fa-user-plus"></i>
                    </div>
                </div>
                <div class="form-bottom">
                    <form role="form" action="" method="post" class="registration-form">
                        <div class="form-group">
                            <label class="sr-only" for="form-first-name">First Name</label>
                            <input type="text" name="form-first-name" placeholder="First Name..." class="form-first-name form-control" id="firstName">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="form-last-name">Last Name</label>
                            <input type="text" name="form-last-name" placeholder="Last Name..." class="form-last-name form-control" id="lastName">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="form-last-name">E-mail</label>
                            <input type="text" name="form-last-name" placeholder="E-mail..." class="form-last-name form-control" id="uEmail">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="form-email">User Name</label>
                            <input type="text" name="form-email" placeholder="User Name..." class="form-email form-control" id="sUsername">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="form-email">Password</label>
                            <input type="password" name="form-email" placeholder="Password..." class="form-email form-control" id="sPassword">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="form-email">Confirm Password</label>
                            <input type="password" name="form-email" placeholder="Confirm Password..." class="form-email form-control" id="sRepeat">
                        </div>
                        <button type="button" id="btn2" onclick="validateSignup2();" class="btn">Sign me up!</button>
                    </form>
                    <div style="margin-top: 10px" class="form-group">

                        <div class="col-sm-12 controls ">
                            <strong>


                                <font color="red" id="signuperror2">

                                </font>
                            </strong> 

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--</div>-->
        <!-- ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  -->

        <!--             <div class="container">
                                <a id="modal_trigger" href="#modal" class="btn btn-success pull-right" style="width: 200px;">Add New Feature</a>
        
                                <div id="modal" class="popupContainer" style="display:none;">
                                    <header class="popupHeader">
                                        <span class="header_title">Add new Feature</span>
                                        <span class="modal_close"><i class="fa fa-times"></i></span>
                                    </header>
                                    <section class="popupBody">
                                         Register Form 
                                        <div class="user_register col-md-12 col-sm-12 col-xs-12">
                                            <form action="Quotation?action=SaveFeature" method="post">
                                                <div class="item form-group">
                                                    <label class="control-label col-md-4 col-sm-4 col-xs-12" for="name">Enter Feature Name<span class="required"></span>
                                                    </label>
                                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                                        <input type="text" id="newfeature" name="newfeature" required="required" class="form-control col-md-7 col-xs-12">
                                                    </div>
                                                </div>   
                                                <br/>
                                                <br/>
                                                <div class="action_btns" style="padding-top: 30px;">
                                                    <div class="one_half last col-md-offset-3"><button type="button" onclick="AddFeature()" class="btn btn-success pull-right"><i class="fa fa-briefcase"></i>Save Feature</button></div>
                                                </div>
                                            </form>
                                        </div>
                                    </section>
                                </div>
                            </div> 
        -->




        <!-- Footer -->
        <footer style="margin-top: -50px;">
            <div class="container">
                <div class="row">

                    <div class="col-sm-8 col-sm-offset-2">
                        <div class="footer-border"></div>
                        <p>©2016 All Rights Reserved. Vertec IT Solutions!</p> 

                    </div>

                </div>
            </div>
            <div style="margin-top: 50px;">

            </div>
        </footer>

        <!-- Javascript -->

        <!--        <script type="text/javascript">
        
                    $("#modal_trigger").leanModal({top: 200, overlay: 0.6, closeButton: ".modal_close"});
        
                    $(function () {
        
        
                        // Calling Register Form
                        $("#modal_trigger").click(function () {
                            $(".user_register").show();
                            $(".header_title").text('Add New Feature');
                            return false;
                        });
        
        
        
                    });
                </script>-->

        <!--<script src="assets/js/jquery-1.11.1.min.js" type="text/javascript">-->


        <!--</script>-->
        <!--<script src="assets/bootstrap/js/bootstrap.min.js"></script>-->
        <!--<script src="assets/js/jquery.backstretch.min.js"></script>-->
        <!--<script src="assets/js/scripts.js"></script>-->

        <!--[if lt IE 10]>
            <script src="assets/js/placeholder.js"></script>
        <![endif]-->

    </body>

</html>