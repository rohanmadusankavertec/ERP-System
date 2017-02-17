/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loadAccordingCus() {
    $("#paymentAvaTable").empty();

    var customerId = document.getElementById('customerId').value;
    $.ajax({
        type: "POST",
        url: "Payment?action=LoadCusBal&customerId=" + customerId,
        success: function (msg) {
            // alert(msg);
            var reply = eval('(' + msg + ')');
            var arrLn1 = reply.jArr1;


            var bpmtable = document.getElementById('paymentAvaTable');


//            var bpmhead = document.getElementById('chequeStaHead');
            var thead = document.createElement("thead");
            var row1 = document.createElement("tr");
            row1.id = 'headRow';
            var colu1 = document.createElement("td");
            colu1.align = "center";
            colu1.type = "text";
            colu1.innerHTML = 'Out_In_Id';
            var colu2 = document.createElement("td");
            colu2.align = "center";
            colu2.type = "text";
            colu2.innerHTML = 'Invoice ID';
            var colu3 = document.createElement("td");
            colu3.align = "center";
            colu3.type = "text";
            colu3.innerHTML = 'Invoiced Date';
            var colu4 = document.createElement("td");
            colu4.align = "center";
            colu4.type = "text";
            colu4.innerHTML = 'Total Invoice Amount';
            var colu5 = document.createElement("td");
            colu5.align = "center";
            colu5.type = "text";
            colu5.innerHTML = 'Balance Outstanding';
            var colu6 = document.createElement("td");
            colu6.type = "text";
            colu6.innerHTML = 'Action';


            row1.appendChild(colu1);
            row1.appendChild(colu2);
            row1.appendChild(colu3);
            row1.appendChild(colu4);
            row1.appendChild(colu5);
            row1.appendChild(colu6);
            thead.appendChild(row1);
            bpmtable.appendChild(thead);
            var totOutstanding = 0;

            for (var f = 0; arrLn1.length > f; f++) {
                var oiId = reply.jArr1[f].oi_id;
                var balance = reply.jArr1[f].balance_amount;
                var iId = reply.jArr1[f].inv_id;
                var iTotal = reply.jArr1[f].total_invoice;
                var dateI = reply.jArr1[f].date;
                var row = document.createElement("tr");
                row.id = oiId;
                var col1 = document.createElement("td");
                col1.align = "center";
                col1.type = "text";
                col1.value = oiId;
                col1.innerHTML = oiId;
                var col2 = document.createElement("td");
                col2.align = "center";
                col2.type = "text";
                col2.value = iId;
                col2.innerHTML = iId;
                var col3 = document.createElement("td");
                col3.align = "center";
                col3.type = "text";
                col3.value = dateI;
                col3.innerHTML = dateI;
                var col4 = document.createElement("td");
                col4.align = "center";
                col4.type = "text";
                col4.value = iTotal;
                col4.innerHTML = iTotal;
                var col5 = document.createElement("td");
                col5.align = "center";
                col5.type = "text";
                col5.value = balance;
                col5.innerHTML = balance;
                var col6 = document.createElement("td");
                var elem1 = document.createElement("span");
                elem1.id = 'update';
                elem1.name = 'update';
                elem1.type = "button";
                elem1.className = "btn btn-default glyphicon glyphicon-edit";
                col6.appendChild(elem1);


                row.appendChild(col1);
                row.appendChild(col2);
                row.appendChild(col3);
                row.appendChild(col4);
                row.appendChild(col5);
                row.appendChild(col6);
                bpmtable.appendChild(row);
                totOutstanding = Number(totOutstanding) + Number(balance);
            }

            var rowFootBef = document.createElement("tr");
            rowFootBef.id = 'footRowBef';
            var coluFootBef1 = document.createElement("td");
            coluFootBef1.align = "left";
            coluFootBef1.setAttribute("colspan", 6);

            rowFootBef.appendChild(coluFootBef1);
            bpmtable.appendChild(rowFootBef);
            
            
            var rowFoot = document.createElement("tr");
            rowFoot.id = 'footRow';
            var coluFoot1 = document.createElement("td");
            coluFoot1.align = "left";
            coluFoot1.setAttribute("colspan", 4);
            coluFoot1.type = "text";
            coluFoot1.innerHTML = 'Total Outstanding';
            var coluFoot2 = document.createElement("td");
            coluFoot2.align = "center";
            coluFoot2.type = "text";
            coluFoot2.innerHTML = totOutstanding + ".0";



            rowFoot.appendChild(coluFoot1);
            rowFoot.appendChild(coluFoot2);

            bpmtable.appendChild(rowFoot);




        }

    });

}
$(document).on('click', '#paymentAvaTable span', function () {
    var tr = $(this).closest('tr');
    var oiid = tr.find('td:first-child').text();
    var inid = tr.find('td:nth-child(2)').text();
    var date = tr.find('td:nth-child(3)').text();
    var intot = tr.find('td:nth-child(4)').text();
    var bal = tr.find('td:nth-child(5)').text();


    BootstrapDialog.show({
        message: 'Are you select Correct Invoice to Pay?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();

                    document.getElementById('paymentTable').className = 'row';
                    document.getElementById('oiId').value = oiid;
                    document.getElementById('invoiceId').value = inid;
                    document.getElementById('inAmount').value = intot;
                    document.getElementById('balAmount').value = bal;

                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });


});
function loadChekDetails() {
    var paymentType = document.getElementById('paymentType').value;

    if (paymentType === "1") {
        document.getElementById('chequeNo').className = 'hidden';
        document.getElementById('bank').className = 'hidden';
        document.getElementById('dateChk').className = 'hidden';
    } else if (paymentType === "2") {
        document.getElementById('chequeNo').className = '';
        document.getElementById('bank').className = '';
        document.getElementById('dateChk').className = '';
    }
}

function checkAmunt() {
    var payAmount = document.getElementById('payAmount').value;
    var balAmount = document.getElementById('balAmount').value;

    if (payAmount > balAmount) {
        nom_notify('Payment Amount is Higher than Balance Amount');
        document.getElementById('payAmount').value = '';


    } 
}

function submitPayment() {
    var oiid = document.getElementById('oiId').value;
    var invoiceId = document.getElementById('invoiceId').value;
    var inAmount = document.getElementById('inAmount').value;
    var balAmount = document.getElementById('balAmount').value;
    var paymentType = document.getElementById('paymentType').value;
    var payAmount = document.getElementById('payAmount').value;
    var dataArr = [];
    if(payAmount !== "" && paymentType !== ""){
        if (paymentType === "2") {
            var chkNo = document.getElementById('chkNo').value;
            var bankName = document.getElementById('bankName').value;
            var chDate = document.getElementById('chDate').value;
                if(chDate !== "" && chkNo !== "" && bankName !== ""){
                    dataArr = [oiid, invoiceId, inAmount, balAmount, paymentType, payAmount, chkNo, bankName, chDate];
                }else{
                    nom_notify('Add Cheque Details ....');
                }
        } else {
            dataArr = [oiid, invoiceId, inAmount, balAmount, paymentType, payAmount];
        } 
    }else{
        nom_notify('Select the Fields.....');
    }
    
    BootstrapDialog.show({
        message: 'Are you select Correct Invoice to Pay?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();

                    $.ajax({
                        type: "POST",
                        url: "Payment?action=SubmitPayment&dataArr=" + dataArr,
                        success: function (msg) {
                            // alert(msg);
                            if (msg === "Success") {
                                nom_Success("Successfully Payment Added");
                                setTimeout("location.href = 'Payment?action=ToDoPayment';", 1500);

                            } else {
                                sm_warning("Not Submited Payment, Please Try again");
                            }
                        }
                    });
                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });

}
function loadChequeCust() {
    var customerId = document.getElementById('customerId').value;

    $("#chequeStaTable").empty();

    $.ajax({
        type: "POST",
        url: "Payment?action=ChequeStatus&customerId=" + customerId,
        success: function (msg) {
            var reply = eval('(' + msg + ')');
            var arrLn1 = reply.jArr1;



            var bpmtable = document.getElementById('chequeStaTable');
            var thead = document.createElement("thead");
            var row1 = document.createElement("tr");
            row1.id = 'headRow';
            var colu1 = document.createElement("td");
            colu1.type = "text";
            colu1.innerHTML = 'PaymentId';
            var colu2 = document.createElement("td");
            colu2.type = "text";
            colu2.innerHTML = 'Invoice ID';
            var colu3 = document.createElement("td");
            colu3.type = "text";
            colu3.innerHTML = 'Bank';
            var colu4 = document.createElement("td");
            colu4.type = "text";
            colu4.innerHTML = 'Cheque No';
            var colu5 = document.createElement("td");
            colu5.type = "text";
            colu5.innerHTML = 'Amount';
            var colu6 = document.createElement("td");
            colu6.type = "text";
            colu6.innerHTML = 'Date';
            var colu7 = document.createElement("td");
            colu7.type = "text";
            colu7.innerHTML = 'Clear Cheque';
            var colu8 = document.createElement("td");
            colu8.type = "text";
            colu8.innerHTML = 'Return Cheque';

            row1.appendChild(colu1);
            row1.appendChild(colu2);
            row1.appendChild(colu3);
            row1.appendChild(colu4);
            row1.appendChild(colu5);
            row1.appendChild(colu6);
            row1.appendChild(colu7);
            row1.appendChild(colu8);
            thead.appendChild(row1);
            bpmtable.appendChild(thead);


            for (var f = 0; arrLn1.length > f; f++) {
                var pId = reply.jArr1[f].pId;
                var chkNo = reply.jArr1[f].chkNo;
                var bank = reply.jArr1[f].bank;
                var amount = reply.jArr1[f].amount;
                var iId = reply.jArr1[f].iId;
                var date = reply.jArr1[f].date;
                var row = document.createElement("tr");
                row.id = pId;
                var col1 = document.createElement("td");
                col1.type = "text";
                col1.value = pId;
                col1.innerHTML = pId;
                var col2 = document.createElement("td");
                col2.type = "text";
                col2.value = iId;
                col2.innerHTML = iId;
                var col3 = document.createElement("td");
                col3.type = "text";
                col3.value = bank;
                col3.innerHTML = bank;
                var col4 = document.createElement("td");
                col4.type = "text";
                col4.value = chkNo;
                col4.innerHTML = chkNo;
                var col5 = document.createElement("td");
                col5.type = "text";
                col5.value = amount;
                col5.innerHTML = amount;
                var col6 = document.createElement("td");
                col6.type = "text";
                col6.value = date;
                col6.innerHTML = date;
                var col7 = document.createElement("td");
                var elem1 = document.createElement("span");
                elem1.id = 'update';
                elem1.name = 'update';
                elem1.type = "button";
                
                elem1.className = "btn btn-default glyphicon glyphicon-edit";
                col7.appendChild(elem1);
                
                var col8 = document.createElement("td");
                var elem2 = document.createElement("input");
                elem2.id = 'return';
                elem2.name = 'return';
                elem2.type = "button";
                elem2.value = "Return";
                elem2.addEventListener("click", RemoveCheck, false);
                col8.appendChild(elem2);

                row.appendChild(col1);
                row.appendChild(col2);
                row.appendChild(col3);
                row.appendChild(col4);
                row.appendChild(col5);
                row.appendChild(col6);
                row.appendChild(col7);
                row.appendChild(col8);
                bpmtable.appendChild(row);
            }
        }
    });
}
function RemoveCheck() {
    var paymentId = tr.find('td:first-child').text();
   BootstrapDialog.show({
        message: 'Are you Sure you want to mark this Cheque as return?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();
                    $.ajax({
                        type: "POST",
                        url: "Payment?action=ReturnCheque&pId=" + paymentId,
                        success: function (msg) {
                            if (msg === "Success") {
                                nom_Success("Successfully Cleared Cheque");
                                setTimeout("location.href = 'Payment?action=ClearCheque';", 1500);
                            } else {
                                sm_warning("Not Submited Payment, Please Try again");
                            }
                        }
                    });
                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });
}

$(document).on('click', '#chequeStaTable span', function () {
    var tr = $(this).closest('tr');
    var paymentId = tr.find('td:first-child').text();
    var inid = tr.find('td:nth-child(2)').text();
    var amount = tr.find('td:nth-child(5)').text();

    var dataArr = [paymentId, inid, amount];
    BootstrapDialog.show({
        message: 'Are you Sure you want to clear selected Cheque?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();
                    $.ajax({
                        type: "POST",
                        url: "Payment?action=DoClearCheque&dataArr=" + dataArr,
                        success: function (msg) {
                            if (msg === "Success") {
                                nom_Success("Successfully Cleared Cheque");
                                setTimeout("location.href = 'Payment?action=ClearCheque';", 1500);
                            } else {
                                sm_warning("Not Submited Payment, Please Try again");
                            }
                        }
                    });
                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });
});
