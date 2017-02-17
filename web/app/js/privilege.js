/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function savePrivilege() {
    var privilege = document.getElementById('privilege').value;
    BootstrapDialog.show({
        message: 'Do you want Save this Privilege ?',
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

                            if (reply === "Success") {
                                nom_Success("Successfully Saved");
                                setTimeout("location.href = 'Privilege?action=ViewPrivilege';", 1500);
//                                window.location = "Privilege?action=ViewPrivilege";
                            } else {
                                sm_warning("Privilege is Not Added, Please Try again");
                            }

                        }
                    };
                    xmlHttp.open("POST", "Privilege?action=SavePrivilege&privilege=" + privilege, true);
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



$(document).on('click', '#privilegeTable span', function () {

    var r = confirm("Are you Sure You want to Update this?");
    if (r === true) {
        var tr = $(this).closest('tr');
        var id = tr.find('td:first-child').text();
        var name = tr.find('td:nth-child(2)').text();

//        document.getElementById('privilegeId').type=false;
        document.getElementById('privilegeId').setAttribute("readonly", "true");
        document.getElementById('privilegeId').setAttribute("hidden", "false");
        document.getElementById('privilegeId').readonly = true;
        document.getElementById('privilegeId').value = id;
        document.getElementById('privilege').value = name;
        document.getElementById('Update').className = 'btn btn-round btn-warning';
        document.getElementById('Add').className = 'btn btn-round btn-success disabled';



    } else {

    }

});
$(document).on('click', '#exampleHell span', function () {

    var r = confirm("Are you Sure You want to Update this?");
    if (r === true) {
        var tr = $(this).closest('tr');
        var id = tr.find('td:first-child').text();
        var name = tr.find('td:nth-child(2)').text();
        var code = tr.find('td:nth-child(2)').text();

//        document.getElementById('privilegeId').type=false;
        document.getElementById('privilegeId').value = id;
        document.getElementById('privilegeName').value = name;
        document.getElementById('privilegeCode').value = code;
        document.getElementById('Update').className = 'btn btn-round btn-warning';
        document.getElementById('Add').className = 'btn btn-round btn-success disabled';



    } else {

    }

});

function clearForms() {
    document.getElementById('Update').className = 'btn btn-round btn-warning disabled';
    document.getElementById('Add').className = 'btn btn-round btn-success ';
    document.getElementById('privilegeId').readonly = true;
    document.getElementById('privilegeId').value = '';
    document.getElementById('privilege').value = '';
}

function updateForms() {
    BootstrapDialog.show({
        message: 'Do you want Update this Privilege ?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();
                    var prId = document.getElementById('privilegeId').value;
                    var prName = document.getElementById('privilege').value;
                    var dataArr = [prId, prName];
                    var xmlHttp = getAjaxObject();
                    xmlHttp.onreadystatechange = function ()
                    {
                        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                        {
                            var reply = xmlHttp.responseText;
                            if (reply === "Success") {
                                nom_Success("Successfully Updated");
                                setTimeout("location.href = 'Privilege?action=ViewPrivilege';", 1500);
//                                window.location = "Privilege?action=ViewPrivilege";
                            } else {
                                sm_warning("Privilege is Not Updated, Please Try again");
                            }

                        }
                    };
                    xmlHttp.open("POST", "Privilege?action=UpdatePrivilege&dataArr=" + dataArr, true);
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

$(document).on('click', '#viewDetials_tbl tbody tr', function () {
    viewUserDetails($(this).find('td:first').text().trim());
});


function viewUserDetails(prid) {
    $("#allPut").empty();
    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = eval('(' + xmlHttp.responseText + ')');
            console.log(reply);
            var arrLn1 = reply.jArr1.length;
            var arrLn2 = reply.jArr2.length;
            var permission = document.getElementById('allPut');

            for (var f = 0; arrLn1 > f; f++) {
                var id = reply.jArr1[f].pid;
                var name = reply.jArr1[f].pname;

                var row = document.createElement("div");
                row.className = "form-group";

                var td1 = document.createElement("label");
                td1.className = "control-label";
                td1.innerHTML = name;
                td1.for = id;

                var td2 = document.createElement("input");
                td2.type = "checkbox";
                td2.name = "permission";
                td2.className = "pull-right";
                td2.value = id;
                td2.checked = false;

                row.appendChild(td1);
                row.appendChild(td2);
                permission.appendChild(row);
            }
            for (var f = 0; arrLn2 > f; f++) {
                var id = reply.jArr2[f].pid;
                var name = reply.jArr2[f].pname;

                var row = document.createElement("div");
                row.className = "form-group";

                var td1 = document.createElement("label");
                td1.className = "control-label";
                td1.innerHTML = name;
                td1.for = id;

                var td2 = document.createElement("input");
                td2.type = "checkbox";
                td2.name = "permission";
                td2.className = "pull-right";
                td2.value = id;
                td2.checked = true;

                row.appendChild(td1);
                row.appendChild(td2);
                permission.appendChild(row);
            }

            if (arrLn1 !== 0 || arrLn2 !== 0) {

                var row2 = document.createElement("div");
                row2.className = "form-froup";
                var hidden = document.createElement("input");
                hidden.type = "hidden";
                hidden.value = prid;
                hidden.name = "prid";
                hidden.id = "prid";
                var elem1 = document.createElement("span");
                elem1.id = "updateBtn";
                elem1.name = "updateBtn";
                elem1.type = "button";
                elem1.className = "btn btn-default glyphicon glyphicon-edit text-center col-md-offset-5 col-lg-offset-5";

                row2.appendChild(hidden);
                row2.appendChild(elem1);
                permission.appendChild(row2);
            }


        }
    };
    xmlHttp.open("POST", "Privilege?action=SetPrivilege&groupId=" + prid, true);
    xmlHttp.send();
}
$(document).on('click', '#allPut span', function () {

    var r = confirm("Are you Sure You want to Update this?");
    if (r === true) {

        var prgid = document.getElementById("prid").value;

        var checkboxes = document.getElementsByName('permission');


        var checkboxesChecked = [];
        checkboxesChecked.push(prgid);
        for (var i = 0; i < checkboxes.length; i++) {
            // And stick the checked ones onto an array...
            if (checkboxes[i].checked) {
                checkboxesChecked.push(checkboxes[i].value);
            }
        }
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = xmlHttp.responseText;
                if (reply === "Success") {
                    nom_Success("Successfully Updated");
                    setTimeout("location.href = 'Privilege?action=LoadUserGroups';", 1500);

                } else {
                    sm_warning("Privilege is Not Updated, Please Try again");
                }

            }
        };
        xmlHttp.open("POST", "Privilege?action=UpdatePriviledge&dataArr=" + checkboxesChecked, true);
        xmlHttp.send();

    } else {

    }

});



//$(document).on('click', '#viewDetials_tbl2 tbody tr', function () {
//    document.getElementById('permissiondiv').className = 'x_panel tile';
//
//    viewUserDetails2($(this).find('td:first').text().trim());
//});

function viewUserDetails2(prgid) {
    $("#usermanage").empty();
    $("#privilegemanage").empty();
    $("#customermanage").empty();
    $("#servicemanage").empty();
    $("#employeemanage").empty();
    $("#projectmanage").empty();
    $("#quotationmanage").empty();
    $("#estimatecostManage").empty();
    $("#agreementManage").empty();
    $("#projectdetailsManage").empty();
    $("#invoiceManage").empty();
    $("#receiptManage").empty();
    $("#reportManage").empty();
    
    $("#upbtn").empty();

    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = eval('(' + xmlHttp.responseText + ')');
            console.log(reply);
            var arrLn1 = reply.jArr1.length;

            var permission = document.getElementById('allPutty');
            
            var usermanage = document.getElementById('usermanage');
            var privilegemanage = document.getElementById('privilegemanage');
            var customermanage = document.getElementById('customermanage');
            var servicemanage = document.getElementById('servicemanage');
            var employeemanage = document.getElementById('employeemanage');
            var projectmanage = document.getElementById('projectmanage');
            var quotationmanage = document.getElementById('quotationmanage');
            var estimatecostManage = document.getElementById('estimatecostManage');
            var agreementManage = document.getElementById('agreementManage');
            var projectdetailsManage = document.getElementById('projectdetailsManage');
            var invoiceManage = document.getElementById('invoiceManage');
            var receiptManage = document.getElementById('receiptManage');
            var reportManage = document.getElementById('reportManage');
            var gin = document.getElementById('ginManage');

            if (arrLn1 !== 0) {
                for (var f = 0; arrLn1 > f; f++) {
                    var prid = reply.jArr1[f].pid;
                    var pritId = reply.jArr1[f].piid;
                    var piname = reply.jArr1[f].piname;
                    var pname = reply.jArr1[f].pname;
                    var status = reply.jArr1[f].status;
                    var row = document.createElement("div");
                    row.className = "form-group";

                    var td1 = document.createElement("label");
                    td1.className = "control-label";
                    td1.innerHTML = piname;
                    td1.for = pritId;

                    var td2 = document.createElement("input");
                    td2.type = "checkbox";
                    td2.name = "permission";
                    td2.className = "pull-right";
                    if (status === "NO") {

                        td2.value = pritId;
                        td2.checked = false;
                    } else if (status === "YES") {

                        td2.value = pritId;
                        td2.checked = true;
                    }
                    alert(prid);
                    if (prid === "1") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        usermanage.appendChild(row);
                    } else if (prid === "2") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        privilegemanage.appendChild(row);
                    } else if (prid === "3") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        customermanage.appendChild(row);
                    } else if (prid === "4") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        servicemanage.appendChild(row);
                    } else if (prid === "5") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        employeemanage.appendChild(row);
                    } else if (prid === "6") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        projectmanage.appendChild(row);
                    } else if (prid === "7") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        quotationmanage.appendChild(row);
                    } else if (prid === "8") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        estimatecostManage.appendChild(row);
                    } else if (prid === "9") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        agreementManage.appendChild(row);
                    } else if (prid === "10") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        projectdetailsManage.appendChild(row);
                    }else if (prid === "11") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        invoiceManage.appendChild(row);
                    }else if (prid === "12") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        receiptManage.appendChild(row);
                    }else if (prid === "13") {
                        row.appendChild(td1);
                        row.appendChild(td2);
                        reportManage.appendChild(row);
                    }
                }
            }


            if (arrLn1 !== 0) {

                var row2 = document.getElementById('upbtn');
                var hidden = document.createElement("input");
                hidden.type = "hidden";
                hidden.value = prgid;
                hidden.name = "prgid";
                hidden.id = "prgid";
                var elem1 = document.createElement("span");
                elem1.id = "updateBtn";
                elem1.name = "updateBtn";
                elem1.type = "button";
                elem1.className = "btn btn-success glyphicon glyphicon-edit col-md-offset-5 col-lg-offset-5";

                row2.appendChild(hidden);
                row2.appendChild(elem1);
                permission.appendChild(row2);
            }


        }
    };
    xmlHttp.open("POST", "Privilege?action=SetPrivilegeItem&groupId=" + prgid, true);
    xmlHttp.send();
}


$(document).on('click', '#allPutty span', function () {

    var r = confirm("Are you Sure You want to Update this?");
    if (r === true) {

        var prgid = document.getElementById("prgid").value;

        var checkboxes = document.getElementsByName('permission');


        var checkboxesChecked = [];
        checkboxesChecked.push(prgid);
        for (var i = 0; i < checkboxes.length; i++) {
            // And stick the checked ones onto an array...
            if (checkboxes[i].checked) {
                checkboxesChecked.push(checkboxes[i].value);
            }
        }
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = xmlHttp.responseText;
                if (reply === "Success") {
                    nom_Success("Successfully Updated");
                    setTimeout("location.href = 'Privilege?action=LoadUserGroupsForPI';", 1500);
//                    window.location = "Privilege?action=LoadUserGroupsForPI";
                } else {
                    sm_warning("Privilege is Not Updated, Please Try again");
                }

            }
        };
        xmlHttp.open("POST", "Privilege?action=UpdatePriviledgeItem&dataArr=" + checkboxesChecked, true);
        xmlHttp.send();

    } else {

    }

});

//function savePrivilegeItem() {
//    var id = document.getElementById('privilegeId').value;
//    var name = document.getElementById('privilegeName').value;
//    var code = document.getElementById('privilegeCode').value;
//    var privilegeStatus = document.getElementById('privilegeStatus').value;
//    var privilege = document.getElementById('privilege').value;
//    var dataArr = [name, code, privilegeStatus, privilege];
//    var xmlHttp = getAjaxObject();
//    xmlHttp.onreadystatechange = function ()
//    {
//        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
//        {
//            var reply = xmlHttp.responseText;
//            if (reply === "Success") {
//                window.location = "Privilege?action=LoadUserGroups";
//            } else {
//                sm_warning("Privilege is Not Updated, Please Try again");
//            }
//
//        }
//    };
//    xmlHttp.open("POST", "Privilege?action=SavePI&dataArr=" + dataArr, true);
//    xmlHttp.send();
//}

function updateSelectedPI() {

    var id = document.getElementById('privilegeId').value;
    var name = document.getElementById('privilegeName').value;
    var code = document.getElementById('privilegeCode').value;
    var privilegeStatus = document.getElementById('privilegeStatus').value;
    var privilege = document.getElementById('privilege').value;
    var dataArr = [id, name, code, privilegeStatus, privilege];


    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = xmlHttp.responseText;
            if (reply === "Success") {
                nom_Success("Successfully Updated");
                setTimeout("location.href = 'Privilege?action=ForPrivilegeItem';", 1500);
//                window.location = "Privilege?action=ForPrivilegeItem";
            } else {
                sm_warning("Privilege is Not Updated, Please Try again");
            }

        }
    };
    xmlHttp.open("POST", "Privilege?action=SaveUpdatedPI&dataArr=" + dataArr, true);
    xmlHttp.send();
}