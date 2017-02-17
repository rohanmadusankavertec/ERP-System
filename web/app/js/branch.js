/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
function updateSelectedBranch() {

    var id = document.getElementById('branchId').value;
    var branchName = document.getElementById('branchName').value;
    var contactNo = document.getElementById('contactNo').value;
    var address = document.getElementById('address').value;

    var dataArr = [id + "_", branchName + "_", contactNo + "_", address];


    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = xmlHttp.responseText;
            if (reply === "Success") {
                nom_Success("Successfully Updated");
                setTimeout("location.href = 'Branch?action=ViewBranches';", 1500);
//                window.location = "Branch?action=ViewBranches";
            } else {
                sm_warning("Privilege is Not Updated, Please Try again");
            }

        }
    };
    xmlHttp.open("POST", "Branch?action=SaveUpdatedBranch&dataArr=" + dataArr, true);
    xmlHttp.send();
}
function updateSelectedWarehouse() {

    var id = document.getElementById('branchId').value;
    var branchName = document.getElementById('branchName').value;
    var contactNo = document.getElementById('contactNo').value;
    var address = document.getElementById('address').value;

    var dataArr = [id + "_", branchName + "_", contactNo + "_", address];


    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = xmlHttp.responseText;
            if (reply === "Success") {
                nom_Success("Successfully Updated");
                setTimeout("location.href = 'Branch?action=ViewWarehouses';", 1500);
//                window.location = "Branch?action=ViewWarehouses";
            } else {
                sm_warning("Privilege is Not Updated, Please Try again");
            }

        }
    };
    xmlHttp.open("POST", "Branch?action=SaveUpdatedWarehouse&dataArr=" + dataArr, true);
    xmlHttp.send();
}
// initialize the validator function
   