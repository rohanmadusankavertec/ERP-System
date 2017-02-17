/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function checkUsernameValid() {

    var username = document.getElementById('username').value;
    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = xmlHttp.responseText;
            console.log(reply);
            if (reply === "not exist") {
//                    nom_Success("Entered Email Address is correct");
//                    document.getElementById("otherData").className = '';
            } else if (reply === "alreadyExistUser") {
                sm_warning("Username already exist");
                document.getElementById('username').value = "";
            } else {
                sm_alert("Entered Username is not correct");
                document.getElementById('username').value = "";
            }

        }
    };
    xmlHttp.open("POST", "../../User?action=CheckUsername&username=" + username, true);
    xmlHttp.send();
}

function saveSysUser() {
    alert("ASASA");
    var name = document.getElementById('name').value;
    var email = document.getElementById('email').value;
    var contactNo = document.getElementById('contactNo').value;
    var nicNo = document.getElementById('nicNo').value;
    var dob = document.getElementById('dob').value;
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var userGroup = document.getElementById('userGroup').value;

    var dataArr = [name, email, contactNo, nicNo, dob, username, password, userGroup];
    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = xmlHttp.responseText;
            console.log(reply);


        }
    };
    xmlHttp.open("POST", "../../User?action=Save&dataArr=" + dataArr, true);
    xmlHttp.send();

}

function emailValid() {
    nom_Success("Entered Email Address is correct");
//        var emailAdd = document.getElementById('emailAdd').value;
//        var xmlHttp = getAjaxObject();
//        xmlHttp.onreadystatechange = function ()
//        {
//            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
//            {
//                var reply = xmlHttp.responseText;
//                console.log(reply);
//                if (reply === "not exist") {
////                    nom_Success("Entered Email Address is correct");
////                    document.getElementById("otherData").className = '';
//                } else if (reply === "alreadyExistUser") {
//                    sm_warning("Email address already exist");
//                    document.getElementById('emailAdd').value = "";
//                } else {
//                    sm_alert("Entered Email Address is not correct");
//                    document.getElementById('emailAdd').value = "";
//                }
//
//            }
//        };
//        xmlHttp.open("POST", "UserController?action=CheckDomain&emailAdd=" + emailAdd, true);
//        xmlHttp.send();
}

function deleteUser(userId) {
    BootstrapDialog.show({
        message: 'Do you want Deactivate this User ?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();

                    var xmlHttp = getAjaxObject();
                    xmlHttp.onreadystatechange = function ()
                    {
                        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                        {
                            var reply = xmlHttp.responseText;
                            console.log(reply);
                            if (reply === "UserDeleted") {
                                nom_Success("Successfully Deleted");
                                setTimeout("location.href = 'User?action=ViewUsers';", 1500);
//                                window.location = "User?action=ViewUsers";
                            } else {
                                sm_warning("User is Not Deactivated, Please Try again");
                            }

                        }
                    };
                    xmlHttp.open("POST", "User?action=RemoveUser&userId=" + userId, true);
                    xmlHttp.send();




                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });

}
function updateUser() {
    alert("SS");
    document.MyForm.action = "User?action=UpdateUser";
    document.MyForm.submit();
    return;


}

function checkPW() {
    var currentPass = document.getElementById('currentPass').value;
    var userId = document.getElementById('userId').value;

    var dataArr = [currentPass, userId];

    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = eval('(' + xmlHttp.responseText + ')');

            if (reply.result === 1) {

            } else if (reply.result === 0) {
                sm_warning("Please Enter Correct Current Password");
                document.getElementById('currentPass').value = '';
            }


        }
    };

    xmlHttp.open("POST", "../../User?action=CheckPW&dataArr=" + dataArr, true);
    xmlHttp.send();
}
