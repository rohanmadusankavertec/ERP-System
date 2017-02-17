/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function selectRepType() {
    var reportType = document.getElementById('reportType').value;
    if (reportType === "1") {
        document.getElementById('dailyInvoice').className = 'item form-group';
        document.getElementById('periodicallyInvoice').className = 'hidden';
        document.getElementById('periodicallyOutstanding').className = 'hidden';
//        document.getElementById('vStockP').className = 'hidden';

    } else if (reportType === "2") {
        document.getElementById('dailyInvoice').className = 'hidden';
        document.getElementById('periodicallyInvoice').className = 'item form-group';
        document.getElementById('periodicallyOutstanding').className = 'hidden';
//        document.getElementById('vStockP').className = 'hidden';
    } else if (reportType === "3") {
        document.getElementById('dailyInvoice').className = 'hidden';
        document.getElementById('periodicallyInvoice').className = 'hidden';
        document.getElementById('periodicallyOutstanding').className = 'item form-group';
//        document.getElementById('vStockP').className = 'hidden';
//    } else if (reportType === "4") {
//        document.getElementById('dailyInvoice').className = 'hidden';
//        document.getElementById('periodicallyInvoice').className = 'hidden';
//        document.getElementById('periodicallyOutstanding').className = 'hidden';
//        document.getElementById('vStockP').className = 'item form-group';
    }
}
function selectStockRepType() {
    var reportType = document.getElementById('reportType').value;

    if (reportType === "1") {
        document.getElementById('branchP').className = 'item form-group';
        document.getElementById('branchPM').className = 'hidden';
        document.getElementById('branchRP').className = 'hidden';
        document.getElementById('vStockP').className = 'hidden';
        document.getElementById('selGroup').className = 'item form-group';

    } else if (reportType === "2") {
        document.getElementById('branchP').className = 'hidden';
        document.getElementById('branchPM').className = 'item form-group';
        document.getElementById('branchRP').className = 'hidden';
        document.getElementById('vStockP').className = 'hidden';
        document.getElementById('selGroup').className = 'item form-group';

    } else if (reportType === "3") {
        document.getElementById('branchP').className = 'hidden';
        document.getElementById('branchPM').className = 'hidden';
        document.getElementById('branchRP').className = 'item form-group';
        document.getElementById('vStockP').className = 'hidden';
        document.getElementById('selGroup').className = 'item form-group';

    } else if (reportType === "4") {
        document.getElementById('branchP').className = 'hidden';
        document.getElementById('branchPM').className = 'hidden';
        document.getElementById('branchRP').className = 'hidden';
        document.getElementById('vStockP').className = 'item form-group';
        document.getElementById('selGroup').className = 'hidden';

    }
}

function viewDateInvoices() {
    var selectDate = document.getElementById('selectDate').value;
    $.ajax({
        type: "POST",
        url: "../../Report?action=DailyInReport&selectDate=" + selectDate,
        success: function (msg) {
            if (msg === "Success") {
                location.reload();
            } else {
                sm_warning("Not Generated Report, Please Try again");
            }
        }
    });
}
function viewPeriodicallyInvoices() {
    var fromDate = document.getElementById('fromDate').value;
    var toDate = document.getElementById('toDate').value;
    var dateArr = [fromDate, toDate];
    $.ajax({
        type: "POST",
        url: "../../Report?action=PeriodicallyInReport&dateArr=" + dateArr,
        success: function (msg) {
            if (msg === "Success") {
                location.reload();

            } else {
                sm_warning("Not Generated Report, Please Try again");
            }




        }

    });
}
function viewPeriodicallyOutstanding() {

    $.ajax({
        type: "POST",
        url: "../../Report?action=PeriodicallyOutstanding",
        success: function (msg) {
            if (msg === "Success") {
                location.reload();

            } else {
                sm_warning("Not Generated Report, Please Try again");
            }

        }

    });
}
function viewBranchStock() {
    var branchId = document.getElementById('branchId').value;
    if (branchId === "") {
        sm_warning("Please Select Branch");
    } else {
        $.ajax({
            type: "POST",
            url: "Report?action=ProductStock&branchId=" + branchId,
            success: function (msg) {
                if (msg === "Success") {
                    location.reload();

                } else {
                    sm_warning("Not Generated Report, Please Try again");
                }




            }

        });
    }
}
function viewBranchPMStock() {
    var branchId = document.getElementById('branchId').value;
    if (branchId === "") {
        sm_warning("Please Select Branch");
    } else {
        $.ajax({
            type: "POST",
            url: "Report?action=ProductMasterStock&branchId=" + branchId,
            success: function (msg) {
                if (msg === "Success") {
                    location.reload();

                } else {
                    sm_warning("Not Generated Report, Please Try again");
                }




            }

        });
    }
}
function viewReleasePM() {
    var branchId = document.getElementById('branchId').value;
    var fromDate = document.getElementById('fromDate').value;
    var toDate = document.getElementById('toDate').value;
    if (branchId === "") {
        sm_warning("Please Select Branch");
    } else if (fromDate === "") {
        sm_warning("Please Select Start Date");
    } else if (toDate === "") {
        sm_warning("Please Select End Date");
    } else {
        var dateArr = [branchId, fromDate, toDate];
        $.ajax({
            type: "POST",
            url: "Report?action=ReleaseProductMPeriodically&dateArr=" + dateArr,
            success: function (msg) {
                if (msg === "Success") {
                    location.reload();

                } else {
                    sm_warning("Not Generated Report, Please Try again");
                }




            }

        });
    }
}
function rProductVehicle() {
    var fromDate = document.getElementById('fromADate').value;
    var toDate = document.getElementById('toADate').value;
    if (fromDate === "") {
        sm_warning("Please Select Start Date");
    } else if (toDate === "") {
        sm_warning("Please Select End Date");
    } else {
        var dateArr = [fromDate, toDate];
        $.ajax({
            type: "POST",
            url: "Report?action=RProductVehicle&dateArr=" + dateArr,
            success: function (msg) {
                if (msg === "Success") {
                    location.reload();

                } else {
                    sm_warning("Not Generated Report, Please Try again");
                }




            }

        });
    }
}