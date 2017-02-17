package com.vertec.controller;

import com.vertec.daoimpl.BranchDAOImpl;
import com.vertec.daoimpl.CustomerDAOImpl;
import com.vertec.daoimpl.InvoiceDAOImpl;
import com.vertec.daoimpl.ProductDAOImpl;
import com.vertec.daoimpl.StockDAOImpl;
import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.BranchStock;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Customer;
import com.vertec.hibe.model.Invoice;
import com.vertec.hibe.model.InvoiceItem;
import com.vertec.hibe.model.InvoicePayment;
import com.vertec.hibe.model.OutstandigInvoice;
import com.vertec.hibe.model.Payment;
import com.vertec.hibe.model.PaymentType;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.VertecConstants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * InvoiceController URL=Invoice This Controller all the action calls get from
 * app/invoice/(createInvoice.jsp,viewInvoice.jsp) and all functions call get
 * from app/js/branch.js
 *
 */
public class InvoiceController extends HttpServlet {

    private final BranchDAOImpl branchDAOImpl = new BranchDAOImpl();
    private final CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
    private final ProductDAOImpl productDAOImpl = new ProductDAOImpl();
    private final InvoiceDAOImpl invoiceDAOImpl = new InvoiceDAOImpl();
    private final StockDAOImpl stockDAOImpl = new StockDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, FileNotFoundException {

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        Company company = (Company) httpSession.getAttribute("company");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;
        String path = getServletContext().getInitParameter("pdftemp");

        switch (action) {
            /**
             * This action is url for createInvoice.jsp
             */
            case "ToCreateInvoice": {
                List<Branch> branchList = branchDAOImpl.loadAllBranches(user1.getBranchBranchId());
                List<Customer> customerList = customerDAOImpl.getListofUsers(company);
                request.setAttribute("branchList", branchList);
                request.setAttribute("customerList", customerList);
                requestDispatcher = request.getRequestDispatcher("app/invoice/createInvoice.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * get Parameters from createInvoice.jsp This was used for open
             * Invoice Page dispathch viewInvoice.jsp or viewVInvoice.jsp
             * according to invoiceType
             */

            case "ToInvoice": {
                String branch = request.getParameter("branchId");
                String customer = request.getParameter("customerId");
                String billno = request.getParameter("billno");

                int customerId = 0;
                int branchId = 0;

                if (branch != null) {
                    System.out.println(branch);
                    branchId = Integer.parseInt(branch.trim());
                }
                if (customer != null) {
                    customerId = Integer.parseInt(customer.trim());
                }
                List<Object[]> pList = stockDAOImpl.loadProductFromBranchStock(branchId);

                Branch b = branchDAOImpl.viewBranch(branchId);

                Customer c = customerDAOImpl.viewCustomer(customerId);

                System.out.println(b.getAddress());
                System.out.println(c.getAddress());
                request.setAttribute("branch", b);
                request.setAttribute("customer", c);
                request.setAttribute("pList", pList);
                request.setAttribute("billno", billno);
                requestDispatcher = request.getRequestDispatcher("app/invoice/viewInvoice.jsp");
                requestDispatcher.forward(request, response);

                break;
            }

            case "ToWInvoice": {
                String customer = request.getParameter("customerId");
                String billno = request.getParameter("billno");
                String category = request.getParameter("categoryId");

                int customerId = 0;

                if (customer != null) {
                    customerId = Integer.parseInt(customer.trim());
                }
                int categoryId = 0;
                if (category != null) {
                    categoryId = Integer.parseInt(category.trim());
                }
                List<Object[]> pList = stockDAOImpl.loadProductFromWarehouseStock();

                Customer c = customerDAOImpl.viewCustomer(customerId);

                request.setAttribute("customer", c);
                request.setAttribute("pList", pList);
                request.setAttribute("billno", billno);
                requestDispatcher = request.getRequestDispatcher("app/winvoice/viewInvoice.jsp");
                requestDispatcher.forward(request, response);

                break;
            }

            /**
             * This request come from loadBranchPM() function of invoice.js
             *
             */
            case "LoadBPMToInvoice": {
                String dataAr = request.getParameter("dataArr").trim();
                String dataArr[] = dataAr.split(",");
                String branId = dataArr[0];
                String proId = dataArr[1];

                int branchId = 0;
                int productId = 0;

                if (branId != null) {
                    branchId = Integer.parseInt(branId);
                }
                if (proId != null) {
                    productId = Integer.parseInt(proId);
                }
                int arr[] = {branchId, productId};
                List<Object[]> bpmList = stockDAOImpl.loadBPMForInvoice(arr);
                System.out.println(bpmList.size() + "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" + branId + proId);
                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONObject job1 = null;

                for (Object[] p : bpmList) {
                    job1 = new JSONObject();
                    job1.put("bpmid", p[0].toString());
                    job1.put("pmid", p[1].toString());
                    job1.put("branquan", p[2].toString());
                    job1.put("pprice", p[3].toString());
                    job1.put("sprice", p[4].toString());
                    jar1.add(job1);
                }
                jOB.put("jArr1", jar1);
                response.getWriter().write(jOB.toString());
                break;
            }

            case "LoadWSToInvoice": {
                String dataAr = request.getParameter("dataArr").trim();
                String dataArr[] = dataAr.split(",");
                String proId = dataArr[1];

                int productId = 0;

                if (proId != null) {
                    productId = Integer.parseInt(proId);
                }
                List<Object[]> bpmList = stockDAOImpl.loadWSForInvoice(productId);
                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONObject job1 = null;

                for (Object[] p : bpmList) {
                    job1 = new JSONObject();
                    job1.put("wsid", p[0].toString());
                    job1.put("branquan", p[1].toString());
                    job1.put("pprice", p[2].toString());
                    job1.put("sprice", p[3].toString());
                    jar1.add(job1);
                }
                jOB.put("jArr1", jar1);
                response.getWriter().write(jOB.toString());
                break;
            }

            /**
             * This request coming from viewInvoice.jsp
             *
             */
            case "SubmitInvoice": {
                String data = request.getParameter("data");
                System.out.println(data);
                JSONParser parser = new JSONParser();
                Object obj;

                obj = parser.parse(data);
                JSONObject jSONObject = (JSONObject) obj;

                String custId = jSONObject.get("customerId").toString();
                String branId = jSONObject.get("branchId").toString();
                String totalInAmount = jSONObject.get("totalInAmount").toString();
                String invoiceDiscount = jSONObject.get("invoiceDiscount").toString();
                String totAmountAfterDiscount = jSONObject.get("totAmountAfterDiscount").toString();
                String tax = jSONObject.get("tax").toString();
                String gTot = jSONObject.get("gTot").toString();

                String chequeNo = "";
                if (jSONObject.get("chequeNo") != null) {
                    chequeNo = jSONObject.get("chequeNo").toString();
                }

                String bankName = "";
                if (jSONObject.get("bankName") != null) {
                    bankName = jSONObject.get("bankName").toString();
                }

                String chequeDate = "";
                if (jSONObject.get("chequeDate") != null) {
                    chequeDate = jSONObject.get("chequeDate").toString();
                }

                String payment = "0";
                if (jSONObject.get("payment") != null) {

                    System.out.println("Payment is : " + payment);
                    if (!payment.equals("")) {
                        payment = jSONObject.get("payment").toString();
                    }
                }

                String pt = "";
                int paymentType = 2;
                if (jSONObject.get("pt") != null) {
                    pt = jSONObject.get("pt").toString();
                    if (pt.equals("1")) {
                        paymentType = 1;
                    }
                }

                Date date = new Date();

                double invoiceTotal = 0;
                double inDiscount = 0;
                double totalAfterDiscount = 0;
                double taxAmount = 0;
                double grossTotal = 0;
//                int vehicleId = 0;

                int customerId = 0;
                if (custId.isEmpty() || custId.equals("")) {

                } else {
                    customerId = Integer.parseInt(custId);
                }
                int branchId = 0;
                if (branId.isEmpty() || branId.equals("")) {

                } else {
                    branchId = Integer.parseInt(branId);
                }

                if (totalInAmount.isEmpty() || totalInAmount.equals("")) {

                } else {
                    invoiceTotal = Double.parseDouble(totalInAmount);
                }
                if (invoiceDiscount.isEmpty() || invoiceDiscount.equals("")) {

                } else {
                    inDiscount = Double.parseDouble(invoiceDiscount);
                }
                if (gTot.isEmpty() || gTot.equals("")) {

                } else {
                    grossTotal = Double.parseDouble(gTot);
                }

                Customer customer = new Customer(customerId);
                Branch branch = new Branch(branchId);

//                Invoice invoice = new Invoice(branch, date, invoiceTotal, inDiscount, grossTotal, isValidated, false, customer);
                Invoice invoice = new Invoice();
                System.out.println("Adding Branch");
                invoice.setBranchId(branch);
                System.out.println("Branch : " + branch.getBranchId());
                invoice.setInvoicedDate(date);
                invoice.setInvoiceTotal(invoiceTotal);
                invoice.setDiscount(inDiscount);
                invoice.setTotAfterDiscount(grossTotal);
                invoice.setIsValid(isValidated);
                invoice.setCustomerId(customer);
                invoice.setIsPending(true);
                invoice.setCompanyId(company);

                JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");
                Collection<InvoiceItem> invoiceItemList = new ArrayList<>();
                Collection<BranchProductmaster> bpmList = new ArrayList<>();

                for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {

                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails.get(key);

                    String product = jSONObject1.get("productId").toString();
                    String bmp = jSONObject1.get("bmpId").toString();
                    String sellPrice = jSONObject1.get("sellingPrice").toString();
                    String quan = jSONObject1.get("quantity").toString();
                    String totAmount = jSONObject1.get("totalAmount").toString();
                    String disc = jSONObject1.get("discount").toString();
                    String groAmount = jSONObject1.get("grossAmount").toString();

                    int productId = 0;
                    if (product.isEmpty() || product.equals("")) {

                    } else {
                        productId = Integer.parseInt(product);
                    }
                    int bmpId = 0;
                    if (bmp.isEmpty() || bmp.equals("")) {

                    } else {
                        bmpId = Integer.parseInt(bmp);
                    }
                    double sellingPrice = 0;
                    if (sellPrice.isEmpty() || sellPrice.equals("")) {

                    } else {
                        sellingPrice = Double.parseDouble(sellPrice);
                    }
                    int quantity = 0;
                    if (quan.isEmpty() || quan.equals("")) {

                    } else {
                        quantity = Integer.parseInt(quan);
                    }
                    double totalAmount = 0;
                    if (totAmount.isEmpty() || totAmount.equals("")) {
                    } else {
                        totalAmount = Double.parseDouble(totAmount);
                    }
                    double discount = 0;
                    if (disc.isEmpty() || disc.equals("")) {

                    } else {
                        discount = Double.parseDouble(disc);
                    }
                    double grossAmount = 0;
                    if (groAmount.isEmpty() || groAmount.equals("")) {

                    } else {
                        grossAmount = Double.parseDouble(groAmount);
                    }

                    BranchProductmaster branchProductmaster = stockDAOImpl.viewBranchPM(bmpId);
                    ProductMaster pmId = branchProductmaster.getProductMasterId();

                    bpmList.add(new BranchProductmaster(bmpId, quantity, date, branch, pmId));

                    InvoiceItem invoiceItem = new InvoiceItem(sellingPrice, quantity, totalAmount, discount, grossAmount, pmId, invoice);
                    invoiceItemList.add(invoiceItem);
                }
                invoice.setInvoiceItemCollection(invoiceItemList);
                Collection<OutstandigInvoice> outstandigInvoiceList = new ArrayList<>();
//                OutstandigInvoice outstandigInvoice = new OutstandigInvoice(true, grossTotal, date, date, invoice);
                OutstandigInvoice outst = new OutstandigInvoice();

                double CurrentOutAmount = 0;

                if (paymentType == 1) {
                    CurrentOutAmount = Double.valueOf(gTot) - Double.valueOf(payment);
                } else {
                    CurrentOutAmount = Double.valueOf(gTot);
                }

                outst.setBalanceAmount(CurrentOutAmount);
                outst.setLastUpdateDate(date);
                outst.setStartedDate(date);
                if (CurrentOutAmount > 0) {
                    outst.setOutStatus(true);
                } else {
                    outst.setOutStatus(false);
                }
                outst.setInvoiceId(invoice);

                outstandigInvoiceList.add(outst);
                invoice.setOutstandigInvoiceCollection(outstandigInvoiceList);

                String result = invoiceDAOImpl.saveInvoice(invoice);
                if (result.equals(VertecConstants.SUCCESS)) {
                    for (BranchProductmaster bp : bpmList) {

                        int arr[] = {branchId, bp.getProductMasterId().getProductId().getProductId()};
                        List<BranchStock> viewBS = stockDAOImpl.viewAvailability(arr);
                        int avaquantity = 0;
                        int bsId = 0;
                        for (BranchStock bs : viewBS) {
                            bsId = bs.getBranchStockId();

                            avaquantity = bs.getQuantity();
                        }
                        int newquantity = avaquantity - bp.getQuantity();
                        BranchStock branchStock = new BranchStock(bsId, newquantity, date, user1);
                        //this was used to update branchstock
                        String result1 = stockDAOImpl.updateBranchStock(branchStock);

                        BranchProductmaster bp1 = stockDAOImpl.viewBranchPM(bp.getBpmId());

                        BranchProductmaster branchProductmaster = new BranchProductmaster(bp1.getBpmId(), bp1.getQuantity() - bp.getQuantity(), date);
                        //this was used to update branchproductmaster
                        String result2 = stockDAOImpl.updateBranchPM(branchProductmaster);
                    }
                }
                Payment p = new Payment();
                p.setAmount(Double.valueOf(payment));
                p.setBankName(bankName);
                p.setChequeDate(chequeDate);
                p.setChequeNo(chequeNo);
                p.setPaymentDate(date);
                PaymentType ptype = new PaymentType(paymentType);
                p.setPaymentTypeId(ptype);
                p.setSysUserSysuserId(user1);
                if (paymentType == 1) {
                    p.setIsCleared(true);
                } else {
                    p.setIsCleared(false);
                }
                invoiceDAOImpl.savePayment(p);
                InvoicePayment ip = new InvoicePayment();
                ip.setInvoiceId(invoice);
                ip.setPaymentId(p);
                ip.setBalanceLast(Double.valueOf(gTot) - Double.valueOf(payment));
                invoiceDAOImpl.saveInvoicePayment(ip);
                System.gc();
                response.getWriter().write(result);
                break;
            }

            /**
             *
             * this is url action for maintain invoice
             */
            case "MaintainInvoice": {
                List<Customer> customerList = customerDAOImpl.getListofUsers(company);
                request.setAttribute("customerList", customerList);
                requestDispatcher = request.getRequestDispatcher("app/invoice/maintainInvoice.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             *
             * this is request coming from maintainInvoice.jsp and dispatch to
             * viewSelected.jsp
             */
            case "LoadCustomerInvoice": {
                String customerId = request.getParameter("customerId");
                String branch = request.getParameter("branch");
                String from = request.getParameter("from");
                String to = request.getParameter("to");
                String type = request.getParameter("type");
                Date date = new Date();
//                List<Object[]> invoiceList = invoiceDAOImpl.invoiceForCustomers(cusId);
                List<Object[]> invoiceList = invoiceDAOImpl.invoiceForAll(customerId, branch, type, from, to, company);

                request.setAttribute("invoiceList", invoiceList);
                requestDispatcher = request.getRequestDispatcher("app/invoice/viewSelected.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * This is used to view selected invoice
             *
             */
            case "ViewInvoice": {
                String invoiceI = request.getParameter("invoiceId").trim();
                int invoiceId = Integer.parseInt(invoiceI);
                Invoice invoice = invoiceDAOImpl.getInvoice(invoiceId);
                List<InvoiceItem> iiList = invoiceDAOImpl.getInvoiceItem(invoiceId);
                OutstandigInvoice outs = invoiceDAOImpl.getOutstanding(invoiceId);
                request.setAttribute("invoice", invoice);
                request.setAttribute("iiList", iiList);
                request.setAttribute("outs", outs);
                requestDispatcher = request.getRequestDispatcher("app/invoice/PrintInvoice.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * this was used to update selected invoice
             */
            case "UpdateInvoice": {
                String invoiceI = request.getParameter("invoiceId").trim();
                int invoiceId = Integer.parseInt(invoiceI);

                Invoice invoice = invoiceDAOImpl.getInvoice(invoiceId);
                List<InvoiceItem> iiList = invoiceDAOImpl.getInvoiceItem(invoiceId);
                request.setAttribute("invoice", invoice);
                request.setAttribute("iiList", iiList);
                requestDispatcher = request.getRequestDispatcher("app/invoice/toUpdate.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * this was used to delete selected invoice
             */
            case "DeleteInvoice": {
                String invoiceI = request.getParameter("invoiceId").trim();
                int invoiceId = Integer.parseInt(invoiceI);

                Invoice invoice = invoiceDAOImpl.getInvoice(invoiceId);
                List<InvoiceItem> iiList = invoiceDAOImpl.getInvoiceItem(invoiceId);
                String result12 = invoiceDAOImpl.updateOutstandingStatus(invoiceId);
                String result = invoiceDAOImpl.updateInvoiceStatus(invoiceId);
                Date date = new Date();
                if (result.equals(VertecConstants.SUCCESS)) {
                    int branchId = invoice.getBranchId().getBranchId();
                    Collection<BranchProductmaster> bpmList = new ArrayList<>();
                    for (InvoiceItem ii : iiList) {
                        if (branchId > 0) {
                            int arr[] = {ii.getProductMasterId().getProductMasterId(), branchId};
                            BranchProductmaster bpm = stockDAOImpl.loadBranchPM(arr);
                            BranchProductmaster branchProductmaster1 = new BranchProductmaster(bpm.getBpmId(), bpm.getQuantity() + ii.getQuantity(), date);
                            String result2 = stockDAOImpl.updateBranchPM(branchProductmaster1);
                            int type = 0;
                            if (bpm.getType()) {
                                type = 1;
                            }
//                                int arrNew[] = {branchId, ii.getProductMasterId().getProductId().getProductId(),type};
//                                BranchStock bs = invoiceDAOImpl.viewBranchStock(arrNew);
//                                BranchStock branchStock = new BranchStock(bs.getBranchStockId(), bs.getQuantity() + ii.getQuantity(), date, user1);
//                                String result1 = stockDAOImpl.updateBranchStock(branchStock);
                        } else {
                            invoiceDAOImpl.updateWarehouse(ii.getProductMasterId(), ii.getQuantity());
                        }
                    }
                }
                invoiceDAOImpl.updateReqDelete(invoiceId, true);

                requestDispatcher = request.getRequestDispatcher("app/invoice/ApproveDelete.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "ignoreDelete": {
                System.out.println("IN IGNORE.....");
                String invoiceI = request.getParameter("invoiceId").trim();
                int invoiceId = Integer.parseInt(invoiceI);
                String result = invoiceDAOImpl.updateReqDelete(invoiceId, true);
                System.out.println(result);
                requestDispatcher = request.getRequestDispatcher("app/invoice/ApproveDelete.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * Update to selected Invoice
             *
             */
            case "UpdateAll": {
                System.out.println("IN UPDATE ALL CASE");
                String data = request.getParameter("data");
                System.out.println(data);
                JSONParser parser = new JSONParser();
                Object obj;

                obj = parser.parse(data);
                JSONObject jSONObject = (JSONObject) obj;

                String invId = jSONObject.get("invoiceId").toString();
                String tot = jSONObject.get("tot").toString();
                String invoiceDiscount = jSONObject.get("invoiceDiscount").toString();
                String taxAm = jSONObject.get("taxAm").toString();
                String InToGross = jSONObject.get("InToGross").toString();

                int invoiceId = 0;
                double invoiceTotal = 0;
                double inDiscount = 0;
                double taxAmount = 0;
                double grossTotal = 0;
                Date date = new Date();

                if (invId.isEmpty() || invId.equals("")) {

                } else {
                    invoiceId = Integer.parseInt(invId);
                }
                if (tot.isEmpty() || tot.equals("")) {

                } else {
                    invoiceTotal = Double.parseDouble(tot);
                }
                if (invoiceDiscount.isEmpty() || invoiceDiscount.equals("")) {

                } else {
                    inDiscount = Double.parseDouble(invoiceDiscount);
                }
                if (taxAm.isEmpty() || taxAm.equals("")) {

                } else {
                    taxAmount = Double.parseDouble(taxAm);
                }
                if (InToGross.isEmpty() || InToGross.equals("")) {

                } else {
                    grossTotal = Double.parseDouble(InToGross);
                }

                Invoice invoice = invoiceDAOImpl.getInvoice(invoiceId);

                int branchId = invoice.getBranchId().getBranchId();

                JSONObject itemDetails = (JSONObject) jSONObject.get("item_details");
                System.out.println(itemDetails);
                Collection<InvoiceItem> invoiceItemList = new ArrayList<>();
                Collection<BranchProductmaster> bpmList = new ArrayList<>();
                for (Iterator iterator = itemDetails.keySet().iterator(); iterator.hasNext();) {

                    String key = (String) iterator.next();
                    JSONObject jSONObject1 = (JSONObject) itemDetails.get(key);

                    String pId = jSONObject1.get("pmId").toString();
                    String sellPrice = jSONObject1.get("sellingPrice").toString();
                    String quan = jSONObject1.get("quantity").toString();
                    String totIn = jSONObject1.get("total").toString();
                    String iiId = jSONObject1.get("iiId").toString();
                    String itemId = jSONObject1.get("itemId").toString();
                    String disc = jSONObject1.get("discount").toString();
                    String totAftDismount = jSONObject1.get("totAftDismount").toString();

                    int pmId = 0;
                    if (pId.isEmpty() || pId.equals("")) {

                    } else {
                        pmId = Integer.parseInt(pId);
                    }

                    double sellingPrice = 0;
                    if (sellPrice.isEmpty() || sellPrice.equals("")) {

                    } else {
                        sellingPrice = Double.parseDouble(sellPrice);
                    }

                    int quantity = 0;
                    if (quan.isEmpty() || quan.equals("")) {

                    } else {
                        quantity = Integer.parseInt(quan);
                    }

                    double total = 0;
                    if (totIn.isEmpty() || totIn.equals("")) {

                    } else {
                        total = Double.parseDouble(totIn);
                    }

                    int invoiceItemId = 0;
                    if (iiId.isEmpty() || iiId.equals("")) {

                    } else {
                        invoiceItemId = Integer.parseInt(iiId);
                    }
                    int productId = 0;
                    if (itemId.isEmpty() || itemId.equals("")) {

                    } else {
                        productId = Integer.parseInt(itemId);
                    }

                    double discount = 0;
                    if (disc.isEmpty() || disc.equals("")) {

                    } else {
                        discount = Double.parseDouble(disc);
                    }
                    double totalAftDismount = 0;
                    if (totAftDismount.isEmpty() || totAftDismount.equals("")) {

                    } else {
                        totalAftDismount = Double.parseDouble(totAftDismount);
                    }
                    InvoiceItem ii = invoiceDAOImpl.loadInvoiceItem(invoiceItemId);

                    InvoiceItem invoiceItem = new InvoiceItem(invoiceItemId, quantity, total, discount, totalAftDismount);
                    String res = invoiceDAOImpl.updateInvoiceItem(invoiceItem);

                    System.out.println("__________________________INVOICE UPDATE START__________________________________");
                    System.out.println("PRODUCT ID = " + productId);
                    System.out.println("QUANTITY = " + quantity);
                    System.out.println("DISCOUNT = " + discount);
//                    System.out.println("INVOICE TYPE = " + invoice.getInvoiceType());
                    System.out.println("__________________________INVOICE UPDATE END__________________________________");

                    int arr[] = {ii.getProductMasterId().getProductMasterId(), branchId};
                    BranchProductmaster bpm = stockDAOImpl.loadBranchPM(arr);
                    BranchProductmaster branchProductmaster1 = new BranchProductmaster(bpm.getBpmId(), bpm.getQuantity() + ii.getQuantity() - quantity, date);
                    String result2 = stockDAOImpl.updateBranchPM(branchProductmaster1);

                    int arrNew[] = {branchId, ii.getProductMasterId().getProductId().getProductId()};
                    BranchStock bs = invoiceDAOImpl.viewBranchStock(arrNew);
                    BranchStock branchStock = new BranchStock(bs.getBranchStockId(), bs.getQuantity() + ii.getQuantity() - quantity, date, user1);
                    String result1 = stockDAOImpl.updateBranchStock(branchStock);

                }

                Invoice invoice1 = new Invoice(invoiceId, invoiceTotal, inDiscount, grossTotal);

//                OutstandigInvoice outstandigInvoice = new OutstandigInvoice(grossTotal, date, invoice1);
                OutstandigInvoice outstandigInvoice = new OutstandigInvoice();
                outstandigInvoice.setLastUpdateDate(date);
                outstandigInvoice.setBalanceAmount(grossTotal);
                outstandigInvoice.setInvoiceId(invoice1);

                String resul = invoiceDAOImpl.updateOutstanding(outstandigInvoice);
                String result = invoiceDAOImpl.updateInvoice(invoice1);
                response.getWriter().write(result);
                break;
            }
            /**
             * Load Inovices According to specific period
             *
             */
            case "SelectDates": {
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");
                String branch = request.getParameter("branch");
                String[] neArr = {fromDate, toDate, branch};
                List<Object[]> iiList = invoiceDAOImpl.invoiceAccordingToPeriod(neArr,company);
                request.setAttribute("iiList", iiList);
                requestDispatcher = request.getRequestDispatcher("app/invoice/checkInvoices.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "SelectBranches": {
                String branch = request.getParameter("branch");
                System.out.println(branch);

                List<Object[]> iiList = invoiceDAOImpl.invoiceAccordingToBranch(branch,company);

                request.setAttribute("iiList", iiList);

                requestDispatcher = request.getRequestDispatcher("app/invoice/checkInvoices.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * This was used to load pdf file
             *
             */
            case "LoadInvoicePDF": {
                String invId = request.getParameter("invoiceId");
                System.out.println("INVOICE ID : " + invId);

                int invoiceId = 0;
                if (invId.isEmpty() || invId.equals("")) {

                } else {
                    invoiceId = Integer.parseInt(invId);
                }
                System.out.println("IID : " + invoiceId);
//                try {
//                    new CreateInvoice().generatePDFInvoice(invoiceId, path);
//                } catch (DocumentException ex) {
//                    Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
//                }
                response.getWriter().write("Success");
                break;
            }
            /**
             * Load Inovices Too Print
             *
             */
            case "ToPrint": {

                Object[] invoice = invoiceDAOImpl.getLastInvoice();

                request.setAttribute("invoice", invoice);

                requestDispatcher = request.getRequestDispatcher("app/invoice/toPrint.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "reqDeleteInvoice": {
                String invoiceI = request.getParameter("invoiceId").trim();
                int invoiceId = Integer.parseInt(invoiceI);
                String result = invoiceDAOImpl.reqDelete(invoiceId, user1);
                response.getWriter().write(result);
                break;
            }
            case "ProductFromCategory": {
                String branch = request.getParameter("branchId");
                System.out.println("in product from category");
                String cid = request.getParameter("cid");
                String html = invoiceDAOImpl.ProductFromCategory(cid, Integer.parseInt(branch));
                response.getWriter().write(html);
                break;
            }
            case "ProductFromCategoryW": {
                System.out.println("in product from category WAREHOUSE");
                String cid = request.getParameter("cid");
                String html = invoiceDAOImpl.ProductFromCategoryWarehouse(cid);
                response.getWriter().write(html);
                break;
            }
            case "toCanceledInvoice": {
                requestDispatcher = request.getRequestDispatcher("app/invoice/toCanceledInvoice.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            case "ViewCanceledInvoiceReport": {
                String branch = request.getParameter("branch").trim();
                String fromDate = request.getParameter("fromDate").trim();
                String toDate = request.getParameter("toDate").trim();

                String[] neArr = {fromDate, toDate, branch};

                List<Object[]> iiList = invoiceDAOImpl.CanceledinvoiceAccordingToPeriod(neArr);

                request.setAttribute("iiList", iiList);

                requestDispatcher = request.getRequestDispatcher("app/invoice/checkInvoices.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileNotFoundException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
