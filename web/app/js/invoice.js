var item_details = {};
var bpmDetails = {};
var totalInvoice = "";
var taxpercentage = 0;


function clearItem() {
//    document.getElementById("invoiceenter").style.display = "hidden";
    document.getElementById("invoiceenter").style.visibility = "hidden";
//    document.getElementById("invoiceenter").style.display = "block";
    window.print();

}

function loadBranchPM() {
    $("#bpmId").empty();
    var s1 = document.getElementById('bpmId');
    var t1 = document.createElement("option");

    t1.value = "";
    t1.innerHTML = "Select Price Master";
    s1.appendChild(t1);
    var productId = document.getElementById('productId').value;
    var branchId = document.getElementById('branchId').value;
    var customerId = document.getElementById('customerId').value;
    var pid = productId.split("_");
    var dataArr = [branchId, pid[0], customerId];
    $.ajax({
        type: "POST",
        url: "Invoice?action=LoadBPMToInvoice&dataArr=" + dataArr,
        success: function (msg) {
            var reply = eval('(' + msg + ')');
            var arrLn1 = reply.jArr1;
            var bpm = document.getElementById('bpmId');
            document.getElementById('pmasterDiv').className = 'form-group';
            document.getElementById('quanDiv').className = 'form-group';
            document.getElementById('discountDiv').className = 'form-group';
            document.getElementById('itemtot').className = 'form-group';
            document.getElementById('btnDiv').className = 'form-group';
            for (var f = 0; f < arrLn1.length; f++) {
                var t = document.createElement("option");
                var val = arrLn1[f].bpmid + "_" + arrLn1[f].sprice + "_" + arrLn1[f].branquan;
                t.value = val;
                t.innerHTML = arrLn1[f].pprice + "_" + arrLn1[f].sprice;
                bpm.appendChild(t);
            }


        }

    });
}
function checkAddToGrid() {
    var item_detail = {};
    var bpm_detail = {};
    var productId = document.getElementById('productId').value;
    var branchId = document.getElementById('branchId').value;
    var customerId = document.getElementById('customerId').value;
    var bpm = document.getElementById('bpmId').value;
    var quantity = document.getElementById('bpmQuantity').value;
    var disType = document.getElementById('disType').value;
    var disAmount = document.getElementById('disAmount').value;
    
    document.getElementById('tottableDiv').className = 'row';
    if (bpm === "") {
        sm_warning("Please Select Branch Product Master");
    } else if (quantity === "") {
        sm_warning("Please Enter Quantity");
    } else if (productId === "") {
        sm_warning("Please Select Product");
    } else if (disType === "") {
        sm_warning("Please Select Discount Type");
    } else {
        var bmpArr = bpm.split("_");
        var bmpId = bmpArr[0];
        var sellPrice = bmpArr[1];
        var availableQuantity = bmpArr[2];



        if (Number(quantity) <= Number(availableQuantity)) {
            var totalAmount = quantity * sellPrice;
            var discountAmount = 0;
            if (disType === "NODiscount") {
                discountAmount;
                addAllToGrid(productId, bmpId, sellPrice, quantity, totalAmount, discountAmount, item_detail);

            } else if (disType === "Percentage") {
                if (disAmount === "") {
                    sm_warning("Please Select Discount Percentage");

                } else {
                    discountAmount = (disAmount * totalAmount) / 100;
                    addAllToGrid(productId, bmpId, sellPrice, quantity, totalAmount, discountAmount, item_detail);
                }
            } else if (disType === "Price") {
                if (disAmount === "") {
                    sm_warning("Please Select Discount Amount");
                } else {
                    discountAmount = disAmount;
                    addAllToGrid(productId, bmpId, sellPrice, quantity, totalAmount, discountAmount, item_detail);
                }
            }




        } else {
            sm_warning("There are Not Enough Quantity");

        }
    }

}
function viewAvailableQuan() {
    var bpm = document.getElementById('bpmId').value;
    var bmpArr = bpm.split("_");
    var bmpId = bmpArr[0];
    var sellPrice = bmpArr[1];
    var availableQuantity = bmpArr[2];
    var str = "Available Quantity is :";
    var quan = availableQuantity;
    str = str.bold();
    quan = quan.fontcolor("Red");
    quan = quan.bold();
    document.getElementById('divAvaQuan').innerHTML = str + quan;
    document.getElementById('divAvaQuan').className = 'col-lg-3 col-md-3';
    setTimeout("document.getElementById('divAvaQuan').className='hidden';", 3000);
}

function addAllToGrid(productId, bmpId, sellPrice, quantity, totalAmount, discountAmount, item_detail, bpm_detail, taxvalue) {

    var taxvalue = document.getElementById('itemtax').innerHTML;
    taxvalue = parseFloat(taxvalue);


    var pid = productId.split("_");
    console.log(taxvalue);
    var grossAmount = totalAmount - discountAmount + taxvalue;
    console.log(grossAmount);
    item_detail["productId"] = pid[0];
    item_detail["bmpId"] = bmpId;
    item_detail["sellingPrice"] = sellPrice;
    item_detail["quantity"] = quantity;
    item_detail["totalAmount"] = totalAmount;
    item_detail["discount"] = discountAmount;
    item_detail["tax"] = taxvalue;
    item_detail["grossAmount"] = grossAmount;
    item_details[bmpId] = item_detail;

    var invoiceItemTable = document.getElementById('invoiceItemTable').getElementsByTagName('tbody')[0];
    var row = document.createElement("tr");
    row.id = bmpId;
    var col1 = document.createElement("td");
    col1.type = "text";
    col1.value = productId;
    col1.innerHTML = productId;
    var col2 = document.createElement("td");
    col2.type = "text";
    col2.value = sellPrice;
    col2.innerHTML = sellPrice;
    var col3 = document.createElement("td");
    col3.type = "text";
    col3.value = quantity;
    col3.innerHTML = quantity;
    var col4 = document.createElement("td");
    col4.type = "text";
    col4.value = totalAmount;
    col4.innerHTML = totalAmount;
    var col5 = document.createElement("td");
    col5.type = "text";
    col5.value = discountAmount;
    col5.innerHTML = discountAmount;
    var col8 = document.createElement("td");
    col8.type = "text";
    col8.value = taxvalue;
    col8.innerHTML = taxvalue;
    var col6 = document.createElement("td");
    col6.type = "text";
    col6.value = grossAmount;
    col6.innerHTML = grossAmount;
    var col7 = document.createElement("td");
    var elem1 = document.createElement("span");
    elem1.id = bmpId;
    elem1.name = bmpId;
    elem1.type = "button";
    elem1.className = "btn btn-default glyphicon glyphicon-remove text-center";
    col7.appendChild(elem1);
    row.appendChild(col1);
    row.appendChild(col2);
    row.appendChild(col3);
    row.appendChild(col4);
    row.appendChild(col5);
    row.appendChild(col8);
    row.appendChild(col6);
    row.appendChild(col7);
    invoiceItemTable.appendChild(row);
    $("#productId option[value='" + productId + "']").prop('disabled', true);

    var inDis = document.getElementById('inDis').value;
    if (inDis === "") {
        if (document.getElementById('subtot').innerHTML === "") {
            totalInvoice += grossAmount;

        } else {
            totalInvoice = Number(grossAmount) + Number(document.getElementById('subtot').innerHTML);
            $("#subtot").empty();

        }
        document.getElementById('subtot').innerHTML = totalInvoice;



        var tax = (totalInvoice * taxpercentage) / 100;

        document.getElementById('tax').innerHTML = tax;
        document.getElementById('gTot').innerHTML = Number(totalInvoice) + Number(tax);
    } else {
        alert(inDis);
        if (document.getElementById('subtot').innerHTML === "") {
            totalInvoice += grossAmount;

        } else {
            totalInvoice = Number(grossAmount) + Number(document.getElementById('subtot').innerHTML);
            $("#subtot").empty();

        }
        document.getElementById('subtot').innerHTML = totalInvoice;
        var substring = "%";
        if (inDis.indexOf(substring) > -1) {
            inDis = (totalInvoice * inDis.split("%")[0]) / 100;
        } else {
            inDis = document.getElementById('inDis').value;
        }
        alert(inDis);

        var totalAftDis = totalInvoice - Number(inDis);
        $("#subtot").empty();
        $("#totaftdis").empty();
        $("#tax").empty();
        $("#gTot").empty();
        $("#gTot").empty();
        document.getElementById('ittot').innerHTML = "0000.00";
        document.getElementById('itemtax').innerHTML = "0000.00";
        document.getElementById('subtot').innerHTML = totalInvoice;
        document.getElementById('totaftdis').innerHTML = totalAftDis;
        var tax = (totalAftDis * taxpercentage) / 100;

        document.getElementById('tax').innerHTML = tax;
        document.getElementById('gTot').innerHTML = Number(totalAftDis) + Number(tax);
    }



    clearFields();
}


function clearFields() {
    $("#bpmId").empty();
    var s1 = document.getElementById('bpmId');
    var t1 = document.createElement("option");

    t1.value = "";
    t1.innerHTML = "Select Price Master";
    s1.appendChild(t1);
    document.getElementById("productId").value = '';
    document.getElementById("bpmId").value = '';
    document.getElementById("bpmQuantity").value = '';
    document.getElementById("disType").value = 'NODiscount';
    document.getElementById("disAmount").value = '';
    document.getElementById('pmasterDiv').className = 'hidden';
    document.getElementById('quanDiv').className = 'hidden';
    document.getElementById('ittot').className = 'hidden';
    document.getElementById('discountDiv').className = 'hidden';
    document.getElementById('btnDiv').className = 'hidden';
    document.getElementById('taxfield').className = 'hidden';

}

$(document).on('click', '#invoiceItemTable span', function () {

    var r = confirm("Are you Sure You want to delete this?");
    if (r === true) {
        var tr = $(this).closest('tr');
        var productId = tr.find('td:first-child').text();
        var gross = tr.find('td:nth-child(6)').text();

        //
        totalInvoice = totalInvoice - Number(gross);
        $("#subtot").empty();
        document.getElementById('subtot').innerHTML = totalInvoice;



        var inDis = document.getElementById('inDis').value;
        var substring = "%";
        if (inDis.indexOf(substring) > -1) {
            inDis = (totalInvoice * inDis.split("%")[0]) / 100;
        } else {
            inDis = document.getElementById('inDis').value;
        }

        var totalAftDis = totalInvoice - Number(inDis);
        $("#subtot").empty();
        document.getElementById('subtot').innerHTML = totalInvoice;
        document.getElementById('totaftdis').innerHTML = totalAftDis;
        var tax = (totalAftDis * taxpercentage) / 100;

        document.getElementById('tax').innerHTML = tax;
        document.getElementById('gTot').innerHTML = Number(totalAftDis) + Number(tax);

        $("#productId option[value='" + productId + "']").prop('disabled', false);

        $(this).closest('tr').remove();

        delete item_details[this.id];

    } else {

    }

});
function addDiscount() {
    var inDis = document.getElementById('inDis').value;
    var substring = "%";
    if (inDis.indexOf(substring) > -1) {
        inDis = (totalInvoice * inDis.split("%")[0]) / 100;
    } else {
        inDis = document.getElementById('inDis').value;
    }

    var totalAftDis = totalInvoice - Number(inDis);
    $("#subtot").empty();
    document.getElementById('subtot').innerHTML = totalInvoice;
    document.getElementById('totaftdis').innerHTML = totalAftDis;
    var tax = (totalAftDis * taxpercentage) / 100;
    document.getElementById('inDis').value = inDis;
    document.getElementById('tax').innerHTML = tax;
    document.getElementById('gTot').innerHTML = Number(totalAftDis) + Number(tax);
}
function submitInvoice() {
    var customerId = document.getElementById('customerId').value;
    var branchId = document.getElementById('branchId').value;
    var catId = document.getElementById('categoryId').value;
    var billno = document.getElementById('billno').value;
//    var vehicleId = document.getElementById('vehicleId').value;
    var data = {};
    var totalInAmount = document.getElementById('subtot').innerHTML;
    var invoiceDiscount = document.getElementById('inDis').value;
    var totAmountAfterDiscount = document.getElementById('totaftdis').innerHTML;
    var tax = document.getElementById('tax').innerHTML;
    var gTot = document.getElementById('gTot').innerHTML;



    var chequeNo = document.getElementById('chequeNo').value;
    var bankName = document.getElementById('bankName').value;
    var chequeDate = document.getElementById('chequeDate').value;
    var payment = document.getElementById('payment').value;
    var pt = 0;
    var ptype = document.getElementById("cash");
    if (ptype.checked) {
        pt = 1;
    }

    data["chequeNo"] = chequeNo;
    data["bankName"] = bankName;
    data["chequeDate"] = chequeDate;
    data["payment"] = payment;
    data["pt"] = pt;

    data["customerId"] = customerId;
    data["billno"] = billno;
    data["branchId"] = branchId;
    data["catId"] = catId;
    data["totalInAmount"] = totalInAmount;
    data["invoiceDiscount"] = invoiceDiscount;
    data["totAmountAfterDiscount"] = totAmountAfterDiscount;
    data["tax"] = tax;
    data["gTot"] = gTot;
//    data["vehicleId"] = vehicleId;
    data["item_details"] = item_details;
    var jsonDetails = JSON.stringify(data);


    BootstrapDialog.show({
        message: 'Do you want to Submit ?',
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
                                nom_Success("Successfully Added");
                                setTimeout("location.href = 'Invoice?action=ToPrint';", 1500);
//                                window.location = "Invoice?action=ToCreateInvoice";
                            } else {

                                sm_warning("Invoice Not Correctly Entered Please Try Again");

                            }
                        }
                    };
                    xmlHttp.open("POST", "Invoice?action=SubmitInvoice&data=" + jsonDetails, true);
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

function selectInType() {
    var inType = document.getElementById('invoiceType').value;

    if (inType === "1") {
        document.getElementById('branchwise').className = 'item form-group';
        document.getElementById('vehiclewise').className = 'hidden';

        document.getElementById("branchId").required = 'required';
        document.getElementById("vehicleId").required = '';
//        $("branchId").prop('required',true);

    } else if (inType === "2") {
        document.getElementById('vehiclewise').className = 'item form-group';
        document.getElementById('branchwise').className = 'hidden';
        document.getElementById("vehicleId").required = 'required';
        document.getElementById("branchId").required = '';

    }
}


function loadVehiclePMToInvoice() {
    $("#bpmId").empty();
    var s1 = document.getElementById('bpmId');
    var t1 = document.createElement("option");

    t1.value = "";
    t1.innerHTML = "Select Price Master";
    s1.appendChild(t1);

    var productId = document.getElementById('productId').value;
    var branchId = document.getElementById('branchId').value;
    var customerId = document.getElementById('customerId').value;
    var vehicleId = document.getElementById('vehicleId').value;
    var dataArr = [branchId, productId, customerId, vehicleId];
    $.ajax({
        type: "POST",
        url: "Invoice?action=LoadVSToInvoice&dataArr=" + dataArr,
        success: function (msg) {
            // alert(msg);
            var reply = eval('(' + msg + ')');
            var arrLn1 = reply.jArr1;
            var bpm = document.getElementById('bpmId');
            document.getElementById('pmasterDiv').className = 'form-group';
            document.getElementById('quanDiv').className = 'form-group';
            document.getElementById('discountDiv').className = 'form-group';
            document.getElementById('btnDiv').className = 'form-group';
            for (var f = 0; f < arrLn1.length; f++) {
                var t = document.createElement("option");
                var val = arrLn1[f].bpmid + "_" + arrLn1[f].sprice + "_" + arrLn1[f].branquan;
                t.value = val;
                t.innerHTML = arrLn1[f].pprice + "_" + arrLn1[f].sprice;
                bpm.appendChild(t);
            }


        }

    });
}

function submitVInvoice() {
    var customerId = document.getElementById('customerId').value;
    var branchId = document.getElementById('branchId').value;
    var vehicleId = document.getElementById('vehicleId').value;
    var data = {};
    var totalInAmount = document.getElementById('subtot').innerHTML;
    var invoiceDiscount = document.getElementById('inDis').value;
    var totAmountAfterDiscount = document.getElementById('totaftdis').innerHTML;
    var tax = document.getElementById('tax').innerHTML;
    var gTot = document.getElementById('gTot').innerHTML;
    data["customerId"] = customerId;
    data["branchId"] = branchId;
    data["totalInAmount"] = totalInAmount;
    data["invoiceDiscount"] = invoiceDiscount;
    data["totAmountAfterDiscount"] = totAmountAfterDiscount;
    data["tax"] = tax;
    data["gTot"] = gTot;
    data["vehicleId"] = vehicleId;
    data["item_details"] = item_details;
    var jsonDetails = JSON.stringify(data);


    BootstrapDialog.show({
        message: 'Do you want to Submit ?',
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
                                nom_Success("Successfully Added");
                                setTimeout("location.href = 'Invoice?action=ToPrint';", 1500);
//                                window.location = "Invoice?action=ToCreateInvoice";
                            } else {

                                sm_warning("Invoice Not Correctly Entered Please Try Again");

                            }
                        }
                    };
                    xmlHttp.open("POST", "Invoice?action=SubmitVInvoice&data=" + jsonDetails, true);
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
function reqdeleteInvoice(invoiceId) {
    BootstrapDialog.show({
        message: 'Do you want to Submit ?',
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
                                nom_Success("Requested to delete Invoice.");
                                setTimeout("location.href = 'Invoice?action=MaintainInvoice';", 1500);
                            } else {

                                sm_warning("Invoice Not Correctly Deleted Please Try Again");

                            }
                        }
                    };
                    xmlHttp.open("POST", "Invoice?action=reqDeleteInvoice&invoiceId=" + invoiceId, true);
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

function ignoreInvoice(invoiceId) {
    alert("calling");
    BootstrapDialog.show({
        message: 'Do you want to Submit ?',
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
                                nom_Success("Successfully Deleted");
                                setTimeout("location.href = 'Invoice?action=MaintainInvoice';", 1500);
                            } else {
                                sm_warning("Invoice Not Correctly Entered Please Try Again");
                            }
                        }
                    };
                    xmlHttp.open("POST", "Invoice?action=ignoreDelete&invoiceId=" + invoiceId, true);
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


function deleteInvoice(invoiceId) {
    BootstrapDialog.show({
        message: 'Do you want to Submit ?',
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
                                nom_Success("Successfully Deleted");
                                setTimeout("location.href = 'Invoice?action=MaintainInvoice';", 1500);
//                                window.location = "Invoice?action=MaintainInvoice";
                            } else {

                                sm_warning("Invoice Not Correctly Entered Please Try Again");

                            }
                        }
                    };
                    xmlHttp.open("POST", "Invoice?action=DeleteInvoice&invoiceId=" + invoiceId, true);
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
function updateFields(i) {
    var pmIdd = "pmid" + i;
    var mastepricee = "masteprice" + i;
    var quantitye = "quantity" + i;
    var totaly = "amount" + i;
    var dis = "disAmount" + i;
    var tadamount = "totAftDis" + i;

    var pmId = document.getElementById(pmIdd).value;
    var masteprice = document.getElementById(mastepricee).value;
    var quantity = document.getElementById(quantitye).value;
    var totalOld = document.getElementById(totaly).value;
    var discount = document.getElementById(dis).value;
    var totalAfterDis = document.getElementById(tadamount).value;
    var newtot = quantity * masteprice;
    document.getElementById(totaly).value = newtot;
    document.getElementById(dis).value = '0';
    document.getElementById(tadamount).value = newtot;
    updateDisFields(i);
}
function updateDisFields(i) {
    var pmIdd = "pmid" + i;
    var mastepricee = "masteprice" + i;
    var quantitye = "quantity" + i;
    var totaly = "amount" + i;
    var dis = "disAmount" + i;
    var tadamount = "totAftDis" + i;

    var pmId = document.getElementById(pmIdd).value;
    var masteprice = document.getElementById(mastepricee).value;
    var quantity = document.getElementById(quantitye).value;
    var totalOld = document.getElementById(totaly).value;
    var discount = document.getElementById(dis).value;
    var totalAfterDis = totalOld - discount;
    var newtot = quantity * masteprice;
    document.getElementById(totaly).value = newtot;
    document.getElementById(dis).value = discount;
    document.getElementById(tadamount).value = totalAfterDis;

}
$(document).on('click', '#invDate span', function () {

    var pmIdd = "pmid" + this.id;
    var mastepricee = "masteprice" + this.id;
    var quantitye = "quantity" + this.id;
    var totaly = "amount" + this.id;
    var iiIdU = "iiId" + this.id;
    var itemIdd = "itemId" + this.id;
    var tadamount = "totAftDis" + this.id;
    var dis = "disAmount" + this.id;

    var pmId = document.getElementById(pmIdd).value;
    var masteprice = document.getElementById(mastepricee).value;
    var quantity = document.getElementById(quantitye).value;
    var totalOld = document.getElementById(totaly).value;
    var iiId = document.getElementById(iiIdU).value;
    var itemId = document.getElementById(itemIdd).value;
    var newtot = quantity * masteprice;
    var discount = document.getElementById(dis).value;
    var totAftDismount = document.getElementById(tadamount).value;


    document.getElementById(totaly).value = '';

    document.getElementById(totaly).value = newtot;
    document.getElementById(quantitye).readonly = true;
    document.getElementById(totaly).readonly = true;


    var item_detail = {};
    item_detail["pmId"] = pmId;
    item_detail["sellingPrice"] = masteprice;
    item_detail["quantity"] = quantity;
    item_detail["total"] = newtot;
    item_detail["iiId"] = iiId;
    item_detail["itemId"] = itemId;
    item_detail["discount"] = discount;
    item_detail["totAftDismount"] = totAftDismount;
    item_details[pmId] = item_detail;


    var arrSize = document.getElementById('arrSize').value;
    var totalYeah = 0;
    for (var i = 1; i <= arrSize; i = i + 1) {

        var totafly = "totAftDis" + i;
        var valnew = Number(document.getElementById(totafly).value);
        totalYeah = totalYeah + valnew;
    }
    document.getElementById('invoiceTotal').value = Number(totalYeah);
    document.getElementById('wholeDiscount').value = 0;
    changeToWholeDiscount();
//    console.log(item_details);
});

function changeToWholeDiscount() {
    var tot = Number(document.getElementById('invoiceTotal').value);
    var invoiceDiscount = Number(document.getElementById('wholeDiscount').value);
    var afterDis = tot - invoiceDiscount;
    var tax = (afterDis * taxpercentage) / 100;
//    alert(tax);
    var finalAmount = afterDis + tax;

    document.getElementById('taxAm').value = tax;
    document.getElementById('InToGross').value = finalAmount;
}

function updateInvoice() {

    var data = {};

    var tot = document.getElementById('invoiceTotal').value;
    var invoiceDiscount = document.getElementById('wholeDiscount').value;
    var taxAm = document.getElementById('taxAm').value;
    var InToGross = document.getElementById('InToGross').value;
    var invoiceId = document.getElementById('invoiceId').value;

    data["tot"] = tot;
    data["invoiceDiscount"] = invoiceDiscount;
    data["taxAm"] = taxAm;
    data["InToGross"] = InToGross;
    data["invoiceId"] = invoiceId;
    data["item_details"] = item_details;
    var jsonDetails = JSON.stringify(data);


    BootstrapDialog.show({
        message: 'Do you want to Update ?',
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
                                nom_Success("Successfully Added");
                                setTimeout("location.href = 'Invoice?action=MaintainInvoice';", 1500);
                            } else {

                                sm_warning("Invoice Not Correctly Entered Please Try Again");

                            }
                        }
                    };
                    xmlHttp.open("POST", "Invoice?action=UpdateAll&data=" + jsonDetails, true);
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

function openBillPDF(invoiceId) {

    $.ajax({
        type: "POST",
        url: "Invoice?action=LoadInvoicePDF&invoiceId=" + invoiceId,
        success: function (msg) {


            var reply = msg.responseText;


        }

    });
}




function ignoreInvoice(invoiceId) {
    BootstrapDialog.show({
        message: "Don't you want to Approve this invoice to delete  ?",
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
                            alert(reply);
                            if (reply === "Success") {
                                nom_Success("Successfully Deleted");
                                setTimeout("location.href = 'Invoice?action=MaintainInvoice';", 1500);
                            } else {
                                sm_warning("Invoice Not Correctly Entered Please Try Again");
                            }
                        }
                    };
                    xmlHttp.open("GET", "Invoice?action=ignoreDelete&invoiceId=" + invoiceId, true);
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


function deleteInvoice(invoiceId) {
    BootstrapDialog.show({
        message: 'Do you want to Approve this invoice to delete ?',
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
                                nom_Success("Successfully Deleted");
                                setTimeout("location.href = 'Invoice?action=MaintainInvoice';", 1500);
                            } else {
                                sm_warning("Invoice Not Correctly Entered Please Try Again");
                            }
                        }
                    };
                    xmlHttp.open("POST", "Invoice?action=DeleteInvoice&invoiceId=" + invoiceId, true);
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