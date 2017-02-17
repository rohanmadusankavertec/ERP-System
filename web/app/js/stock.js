/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function loadPC() {
    $("#productId").empty();
    $("#bpmtable").empty();

    var branchId = document.getElementById('branchId').value;


    var s1 = document.getElementById('productId');
    var t1 = document.createElement("option");

    t1.value = "Select Product";
    t1.innerHTML = "Select Product";
    s1.appendChild(t1);
    document.getElementById('bpmtablediv').className = 'hidden';


    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = eval('(' + xmlHttp.responseText + ')');

            var myArr = reply.jArr1;
            var s = document.getElementById('productId');
            document.getElementById('selPro').className = 'item form-group';

            for (var i = 0; i < myArr.length; i++) {
                var t = document.createElement("option");

                t.value = myArr[i].pid;
                t.innerHTML = myArr[i].pcode + "_" + myArr[i].pname;
                s.appendChild(t);
            }



        }
    };
    xmlHttp.open("POST", "Stock?action=ToBranch&branchId=" + branchId, true);
    xmlHttp.send();

}
function loadPM() {
    $("#bpmtable").empty();

    var branchId = document.getElementById('branchId').value;
    
    var productId = document.getElementById('productId').value;
    
    var dataArr = [branchId, productId];

    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = eval('(' + xmlHttp.responseText + ')');
            console.log(reply);
            var arrLn1 = reply.jArr1.length;

            var bpmtable = document.getElementById('bpmtable');
            document.getElementById('bpmtablediv').className = '';

            for (var f = 0; arrLn1 > f; f++) {
                var bpmId = reply.jArr1[f].bpmid;
                var product_code = reply.jArr1[f].pcode;
                var product_name = reply.jArr1[f].pname;
                var product = product_code + "_" + product_name;
                var quantity = reply.jArr1[f].quantity;
                var pprice = reply.jArr1[f].pprice;
                var sprice = reply.jArr1[f].sprice;
                var row = document.createElement("tr");
                row.id = bpmId;
                var col1 = document.createElement("td");
                col1.type = "text";
                col1.value = bpmId;
                col1.innerHTML = bpmId;
                var col2 = document.createElement("td");
                col2.type = "text";
                col2.value = product;
                col2.innerHTML = product;
                var col3 = document.createElement("td");
                col3.type = "text";
                col3.value = pprice;
                col3.innerHTML = pprice;
                var col4 = document.createElement("td");
                col4.type = "text";
                col4.value = sprice;
                col4.innerHTML = sprice;
                var col5 = document.createElement("td");
                col5.type = "text";
                col5.value = quantity;
                col5.innerHTML = quantity;
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
            }



        }
    };
    xmlHttp.open("POST", "Stock?action=ToBranchPM&dataArr=" + dataArr, true);
    xmlHttp.send();
}

$(document).on('click', '#bpmtableOr span', function () {
    var tr = $(this).closest('tr');
    var bpmId = tr.find('td:first-child').text();
    var product = tr.find('td:nth-child(2)').text();
    var purchasedPrice = tr.find('td:nth-child(3)').text();
    var sellingPrice = tr.find('td:nth-child(4)').text();
    var quantity = tr.find('td:nth-child(5)').text();
    var updatedquantity = tr.find('td:nth-child(6)').text();


    BootstrapDialog.show({
        message: 'Do you want Update this Product Master?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();
                    document.getElementById('updateBPM').className = '';
                    document.getElementById('uproduct').value = product;
                    document.getElementById('ubpmId').value = bpmId;
                    document.getElementById('uquantity').value = quantity;
                    document.getElementById('pPrice').value = purchasedPrice;
                    document.getElementById('sPrice').value = sellingPrice;
                    document.getElementById('updatedquantity').value = updatedquantity;

                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });


});

function loadPMToAdd() {
    var abranchId = document.getElementById('abranchId').value;
    var aproductId = document.getElementById('aproductId').value;
    $("#apmId").empty();
    var s1 = document.getElementById('apmId');
    var t1 = document.createElement("option");

    t1.value = "";
    t1.innerHTML = "Select Product Master";
    s1.appendChild(t1);

    var dataArr = [abranchId, aproductId];

    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = eval('(' + xmlHttp.responseText + ')');

            var myArr = reply.jArr1;
            var s = document.getElementById('apmId');
            //            
            for (var i = 0; i < myArr.length; i++) {
                var t = document.createElement("option");

                t.value = myArr[i].pmid;
                t.innerHTML = myArr[i].pmid + "- PP is:" + myArr[i].pprice + "_ SP is:" + myArr[i].sprice;
                s.appendChild(t);
            }



        }
    };
    xmlHttp.open("POST", "Stock?action=ToAddBPM&dataArr=" + dataArr, true);
    xmlHttp.send();

}

function AddNewBPM() {
    var abranchId = document.getElementById('abranchId').value;
    var aproductId = document.getElementById('aproductId').value;

    var apmId = document.getElementById('apmId').value;
    var aquantity = document.getElementById('aquantity').value;

    if (abranchId === "") {

    } else if (aproductId === "") {

    } else if (apmId === "") {

    } else if (aquantity === "") {

    } else {



        var dataArr = [abranchId, aproductId, apmId, aquantity];





        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = eval('(' + xmlHttp.responseText + ')');

                console.log(reply);




            }
        };
        xmlHttp.open("POST", "Stock?action=SaveBPM&dataArr=" + dataArr, true);
        xmlHttp.send();

    }
}

function updateBPM() {
    var product = document.getElementById('uproduct').value;
    var bpmId = document.getElementById('ubpmId').value;
    var uquantity = document.getElementById('uquantity').value;
    var updatedquantity = document.getElementById('updatedquantity').value;
    var branch = document.getElementById('branchId').value;
    var productId = document.getElementById('productId').value;

    var dataArr = [bpmId, updatedquantity, branch, productId];

    BootstrapDialog.show({
        message: 'Do you want Update this Product Master?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();
                    $.ajax({
                        type: "POST",
                        url: "Stock?action=SaveUpdatedStock&dataArr=" + dataArr,
                        success: function (msg) {
                            // alert(msg);
                            if (msg === "Success") {
                                nom_Success("Successfully Updated");
                                setTimeout("location.href = 'Stock?action=ManagePMS';", 1500);
//                                window.location = "Stock?action=ManagePMS";
                            } else {
                                sm_warning("Stock is Not Updated, Please Try again");
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

function clearBPM() {
    document.getElementById('updateBPM').className = 'hidden';
    document.getElementById('uproduct').value = '';
    document.getElementById('ubpmId').value = '';
    document.getElementById('uquantity').value = '';
    document.getElementById('pPrice').value = '';
    document.getElementById('sPrice').value = '';

}

function loadVehiclePM() {
    $("#vehicleId").empty();
    $("#productId").empty();
    var branchId = document.getElementById('branchId').value;

    var s2 = document.getElementById('productId');
    var t2 = document.createElement("option");

    t2.value = "";
    t2.innerHTML = "Select Product";
    s2.appendChild(t2);

    var s3 = document.getElementById('vehicleId');
    var t3 = document.createElement("option");

    t3.value = "";
    t3.innerHTML = "Select Vehicle";
    s3.appendChild(t3);
    $("#bpmId").empty();
    document.getElementById('addDiv').className = 'hidden';
    document.getElementById('updateDiv').className = 'hidden';

    var s3 = document.getElementById('bpmId');
    var t3 = document.createElement("option");

    t3.value = "";
    t3.innerHTML = "Select Price Master";
    s3.appendChild(t3);
    document.getElementById('addQuantity').value = '';


    var xmlHttp = getAjaxObject();
    xmlHttp.onreadystatechange = function ()
    {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            var reply = eval('(' + xmlHttp.responseText + ')');

            var myArr = reply.jArr1;
            var myArr2 = reply.jarV;
            var s = document.getElementById('productId');
            var s1 = document.getElementById('vehicleId');
            document.getElementById('selPro').className = 'item form-group';
            document.getElementById('vehiDiv').className = 'item form-group';
            document.getElementById('btnHide').className = 'item form-group';

            for (var i = 0; i < myArr.length; i++) {
                var t = document.createElement("option");

                t.value = myArr[i].pid;
                t.innerHTML = myArr[i].pcode + "_" + myArr[i].pname;
                s.appendChild(t);
            }
            for (var i = 0; i < myArr2.length; i++) {
                var t1 = document.createElement("option");

                t1.value = myArr2[i].vid;
                t1.innerHTML = myArr2[i].vno;
                s1.appendChild(t1);
            }



        }
    };
    xmlHttp.open("POST", "Stock?action=ToVehicle&branchId=" + branchId, true);
    xmlHttp.send();

}
function openUpdateWindow() {
    $("#updateVSBody").empty();

    var branchId = document.getElementById('branchId').value;
    var vehicleId = document.getElementById('vehicleId').value;
    var productId = document.getElementById('productId').value;
    if (branchId === '') {
        sm_warning("Please Select Branch");
    } else if (vehicleId === '') {
        sm_warning("Please Select Vehicle");
    } else if (productId === '') {
        sm_warning("Please Select Product");
    } else {
        document.getElementById('updateDiv').className = '';
        document.getElementById('addDiv').className = 'hidden';
        var dataArr = [branchId, vehicleId, productId];
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = eval('(' + xmlHttp.responseText + ')');

                console.log(reply);
                var arrLn1 = reply.jArr1.length;

                var updateVSBody = document.getElementById('updateVSBody');

                for (var f = 0; arrLn1 > f; f++) {
                    var vsId = reply.jArr1[f].vsId;
                    var bpmId = reply.jArr1[f].bpmId;
                    var cvQuantity = reply.jArr1[f].cvQuantity;
                    var pPrice = reply.jArr1[f].pPrice;
                    var sPrice = reply.jArr1[f].sPrice;
                    var aQuantity = reply.jArr1[f].aQuantity;

                    var row = document.createElement("tr");
                    row.id = vsId;
                    var col1 = document.createElement("td");
                    col1.type = "text";
                    col1.value = vsId;
                    col1.innerHTML = vsId;
                    var col2 = document.createElement("td");
                    col2.type = "text";
                    col2.value = bpmId;
                    col2.innerHTML = bpmId;
                    var col3 = document.createElement("td");
                    col3.type = "text";
                    col3.value = pPrice;
                    col3.innerHTML = pPrice;
                    var col4 = document.createElement("td");
                    col4.type = "text";
                    col4.value = sPrice;
                    col4.innerHTML = sPrice;
                    var col5 = document.createElement("td");
                    col5.type = "text";
                    col5.value = aQuantity;
                    col5.innerHTML = aQuantity;
                    var col6 = document.createElement("td");
                    col6.type = "text";
                    col6.value = cvQuantity;
                    col6.innerHTML = cvQuantity;
                    var col7 = document.createElement("td");
                    var elem1 = document.createElement("span");
                    elem1.id = 'update';
                    elem1.name = 'update';
                    elem1.type = "button";
                    elem1.className = "btn btn-default glyphicon glyphicon-edit";
                    col7.appendChild(elem1);


                    row.appendChild(col1);
                    row.appendChild(col2);
                    row.appendChild(col3);
                    row.appendChild(col4);
                    row.appendChild(col5);
                    row.appendChild(col6);
                    row.appendChild(col7);
                    updateVSBody.appendChild(row);
                }




            }
        };
        xmlHttp.open("POST", "Stock?action=ToUpdateVehicleStock&dataArr=" + dataArr, true);
        xmlHttp.send();

    }



}
function openAddWindow() {

    var branchId = document.getElementById('branchId').value;
    var vehicleId = document.getElementById('vehicleId').value;
    var productId = document.getElementById('productId').value;

    if (branchId === '') {
        sm_warning("Please Select Branch");
    } else if (vehicleId === '') {
        sm_warning("Please Select Vehicle");
    } else if (productId === '') {
        sm_warning("Please Select Product");
    } else {
        document.getElementById('addDiv').className = '';
        document.getElementById('updateDiv').className = 'hidden';

        var dataArr = [branchId, vehicleId, productId];
        var xmlHttp = getAjaxObject();
        xmlHttp.onreadystatechange = function ()
        {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
            {
                var reply = eval('(' + xmlHttp.responseText + ')');


                var myArr = reply.jArr1;
                var s = document.getElementById('bpmId');

                for (var i = 0; i < myArr.length; i++) {
                    var t = document.createElement("option");

                    t.value = myArr[i].bpmId + "_" + myArr[i].aQuantity;
                    t.innerHTML = myArr[i].sPrice;
                    s.appendChild(t);
                }



            }
        };
        xmlHttp.open("POST", "Stock?action=ToAddNewVehicleStock&dataArr=" + dataArr, true);
        xmlHttp.send();

    }

}

function saveVStock() {
    var branchId = document.getElementById('branchId').value;
    var vehicleId = document.getElementById('vehicleId').value;
    var productId = document.getElementById('productId').value;
    var bmp = document.getElementById('bpmId').value;
    var bmpArr = bmp.split("_");
    var bmpId = bmpArr[0];
    var typequantity = document.getElementById('addQuantity').value;
    var availablequantity = bmpArr[1];
    if (branchId === '') {
        sm_warning("Please Select Branch");
    } else if (vehicleId === '') {
        sm_warning("Please Select Vehicle");
    } else if (productId === '') {
        sm_warning("Please Select Product");
    } else if (bmp === '') {
        sm_warning("Please Select Price Master");
    } else if (typequantity === '') {
        sm_warning("Please Enter Quantity");
    } else {
        if (Number(availablequantity) < Number(typequantity)) {
            sm_warning("There are not enough product quantity on stores.");
            document.getElementById('addQuantity').value = '';
        } else {
            var dataArr = [branchId, vehicleId, productId, bmpId, typequantity];
            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = xmlHttp.responseText;

                    console.log(reply);
                    if (reply === "Success") {
                        $("#bpmId").empty();
                        document.getElementById('addDiv').className = 'hidden';
                        document.getElementById('updateDiv').className = 'hidden';
                        var s2 = document.getElementById('bpmId');
                        var t2 = document.createElement("option");

                        t2.value = "";
                        t2.innerHTML = "Select Price Master";
                        s2.appendChild(t2);
                        document.getElementById('addQuantity').value = '';

                        nom_Success("Saved Successfully");


                    } else {
                        sm_warning("Product is Not Updated, Please Try again");
                    }





                }
            };
            xmlHttp.open("POST", "Stock?action=SaveVehicleStock&dataArr=" + dataArr, true);
            xmlHttp.send();


        }
    }
}

$(document).on('click', '#updateVSTable span', function () {
    var tr = $(this).closest('tr');
    var vsId = tr.find('td:first-child').text();
    var bpmId = tr.find('td:nth-child(2)').text();
    var purchasedPrice = tr.find('td:nth-child(3)').text();
    var sellingPrice = tr.find('td:nth-child(4)').text();
    var aquantity = tr.find('td:nth-child(5)').text();
    var cquantity = tr.find('td:nth-child(6)').text();


    BootstrapDialog.show({
        message: 'Do you want Update this Product Master?',
        closable: false,
        buttons: [{
                label: 'Yes',
                action: function (dialogRef) {
                    dialogRef.close();
                    document.getElementById('updateVSPM').className = '';
                    document.getElementById('vsId').value = vsId;
                    document.getElementById('bpmIdNew').value = bpmId;
                    document.getElementById('pPrice').value = purchasedPrice;
                    document.getElementById('sPrice').value = sellingPrice;
                    document.getElementById('aquantity').value = aquantity;
                    document.getElementById('cquantity').value = cquantity;


                }
            }, {
                label: 'No',
                action: function (dialogRef) {
                    dialogRef.close();
                }
            }]
    });


});

function clearVS() {
    document.getElementById('updateVSPM').className = 'hidden';
    document.getElementById('vsId').value = '';
    document.getElementById('bpmId').value = '';
    document.getElementById('pPrice').value = '';
    document.getElementById('sPrice').value = '';
    document.getElementById('aquantity').value = '';
    document.getElementById('cquantity').value = '';
}
function updateAllVS() {
    var branchId = document.getElementById('branchId').value;
    var vehicleId = document.getElementById('vehicleId').value;
    var productId = document.getElementById('productId').value;
    var vsId = document.getElementById('vsId').value;
    var bpmId = document.getElementById('bpmIdNew').value;

    var pPrice = document.getElementById('pPrice').value;
    var sPrice = document.getElementById('sPrice').value;
    var aquantity = document.getElementById('aquantity').value;
    var cquantity = document.getElementById('cquantity').value;
    var lquantity = document.getElementById('lquantity').value;
    if (branchId === '') {
        sm_warning("Please Select Branch");
    } else if (vehicleId === '') {
        sm_warning("Please Select Vehicle");
    } else if (productId === '') {
        sm_warning("Please Select Product");
    } else if (cquantity === '') {
        sm_warning("Please Enter Quantity");
    } else {
        if (Number(aquantity) < Number(cquantity)) {
            sm_warning("There are not enough product quantity on stores.");
            document.getElementById('addQuantity').value = '';
        } else {
            var dataArr = [branchId, vehicleId, productId, bpmId, lquantity, vsId];
            var xmlHttp = getAjaxObject();
            xmlHttp.onreadystatechange = function ()
            {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                {
                    var reply = xmlHttp.responseText;

                    console.log(reply);
                    if (reply === "Success") {
//                        $("#bpmId").empty();
                        document.getElementById('addDiv').className = 'hidden';
                        document.getElementById('updateDiv').className = 'hidden';

                        document.getElementById('updateVSPM').className = 'hidden';
                        document.getElementById('vsId').value = '';
                        document.getElementById('bpmId').value = '';
                        document.getElementById('pPrice').value = '';
                        document.getElementById('sPrice').value = '';
                        document.getElementById('aquantity').value = '';
                        document.getElementById('cquantity').value = '';
                        document.getElementById('lquantity').value = '';

                        nom_Success("Saved Successfully");


                    } else {
                        sm_warning("Product is Not Updated, Please Try again");
                    }





                }
            };
            xmlHttp.open("POST", "Stock?action=SaveUpdatedVehicleStock&dataArr=" + dataArr, true);
            xmlHttp.send();

        }
    }

}