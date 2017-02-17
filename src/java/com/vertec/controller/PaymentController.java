/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.controller;

import com.vertec.daoimpl.BranchDAOImpl;
import com.vertec.daoimpl.CustomerDAOImpl;
import com.vertec.daoimpl.GRNDAOImpl;
import com.vertec.daoimpl.InvoiceDAOImpl;
import com.vertec.daoimpl.PaymentDAOImpl;
import com.vertec.daoimpl.ProductDAOImpl;
import com.vertec.daoimpl.StockDAOImpl;
import com.vertec.daoimpl.SupplierDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Customer;
import com.vertec.hibe.model.GrnInfo;
import com.vertec.hibe.model.GrnPayment;
import com.vertec.hibe.model.Invoice;
import com.vertec.hibe.model.InvoicePayment;
import com.vertec.hibe.model.OutstandigInvoice;
import com.vertec.hibe.model.Payment;
import com.vertec.hibe.model.PaymentType;
import com.vertec.hibe.model.Supplier;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.VertecConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class PaymentController extends HttpServlet {

   private final PaymentDAOImpl paymentDAOImpl = new PaymentDAOImpl();
    private final BranchDAOImpl branchDAOImpl = new BranchDAOImpl();
    private final CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
    private final ProductDAOImpl productDAOImpl = new ProductDAOImpl();
    private final InvoiceDAOImpl invoiceDAOImpl = new InvoiceDAOImpl();
    private final StockDAOImpl stockDAOImpl = new StockDAOImpl();
    private final SupplierDAOImpl supplierDAOImpl = new SupplierDAOImpl();
    private final GRNDAOImpl GRNDAOImpl  = new GRNDAOImpl();
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        Company company = (Company) httpSession.getAttribute("company");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;

        switch (action) {
            case "ToDoPayment": {
                List<Customer> customerList = customerDAOImpl.getListofUsers(company);
                request.setAttribute("customerList", customerList);
                requestDispatcher = request.getRequestDispatcher("app/payment/doPayment.jsp");
                
                requestDispatcher.forward(request, response);
                break;
            }
            
            case "ToDoMultiPayment": {
                List<Customer> customerList = customerDAOImpl.getListofUsers(company);
                request.setAttribute("customerList", customerList);
                requestDispatcher = request.getRequestDispatcher("app/payment/multiPayment.jsp");
                
                requestDispatcher.forward(request, response);
                break;
            }
            case "LoadCusBal": {
                String customer = request.getParameter("customerId");

                int customerId = 0;
                if (customer != null) {
                    customerId = Integer.parseInt(customer.trim());
                }

                List<Object[]> inList = paymentDAOImpl.loadAccordigCus(customerId);

                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONObject job1 = null;

                for (Object[] p : inList) {
                    job1 = new JSONObject();
                    job1.put("oi_id", p[0].toString());
                    job1.put("balance_amount", p[1].toString());
                    job1.put("inv_id", p[2].toString());
                    job1.put("total_invoice", p[3].toString());
                    job1.put("date", p[4].toString());
                    jar1.add(job1);
                }
                jOB.put("jArr1", jar1);
                response.getWriter().write(jOB.toString());
                break;
            }
            case "SubmitPayment": {
                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split(",");
                String oiid = arr[0];
                String invId = arr[1];
                String inAmount = arr[2];
                String balAmount = arr[3];
                String paymentType = arr[4];
                String payAmount = arr[5];

                int pType = 0;
                if (paymentType != null) {
                    pType = Integer.parseInt(paymentType.trim());
                }

                String bank = "";
                String chkNo = "";
                String datechk = "";

                if (pType == 2) {
                    chkNo = arr[6];
                    bank = arr[7];
                    datechk = arr[8];

                }
                int outInvId = 0;
                if (oiid != null) {
                    outInvId = Integer.parseInt(oiid.trim());
                }
                int invoiceId = 0;
                if (invId != null) {
                    invoiceId = Integer.parseInt(invId.trim());
                }

                double balanceBef = 0;
                if (balAmount != null) {
                    balanceBef = Double.parseDouble(balAmount.trim());
                }
                double paymentAmount = 0;
                if (payAmount != null) {
                    paymentAmount = Double.parseDouble(payAmount.trim());
                }
                Date date = new Date();

                Collection<InvoicePayment> ipList = new ArrayList<>();
                Payment payment = new Payment();
                if (pType == 1) {
                    OutstandigInvoice outstandigInvoice = new OutstandigInvoice(balanceBef - paymentAmount, date, new Invoice(invoiceId));
                    String result = invoiceDAOImpl.updateOutstanding(outstandigInvoice);
                    payment = new Payment(date, paymentAmount, isValidated, user1, new PaymentType(1));
                    InvoicePayment invoicePayment = new InvoicePayment(balanceBef - paymentAmount, new Invoice(invoiceId), payment);
                    ipList.add(invoicePayment);
                } else if (pType == 2) {
                    payment = new Payment(date, paymentAmount, chkNo, bank, Boolean.FALSE, user1, new PaymentType(2), datechk);
                    InvoicePayment invoicePayment = new InvoicePayment(balanceBef, new Invoice(invoiceId), payment);
                    ipList.add(invoicePayment);
                }
                payment.setInvoicePaymentCollection(ipList);
                String result = paymentDAOImpl.savePayment(payment);
                response.getWriter().write(result);
                break;
            }
            
            case "savePaymeny":{
                
                String dataArr = request.getParameter("dataArr");
                
                String arr[] = dataArr.split(",");
                String amount = arr[1];
                String cid = arr[2];
                String pT = arr[0];
                
                int pType = Integer.parseInt(pT);

                double am = Integer.parseInt(amount);
                double amt = Integer.parseInt(amount);
//                System.out.println(cid);
//                System.out.println(amount);
                Date date = new Date();

//                Collection<InvoicePayment> ipList = new ArrayList<>();
                Payment payment = new Payment();
                String result22="";
                if(Integer.parseInt(pT) == 1){
                    payment.setAmount(am);
                    payment.setPaymentDate(date);
                    payment.setPaymentTypeId(new PaymentType(Integer.parseInt(pT)));
                    payment.setSysUserSysuserId(user1);
                    payment.setIsCleared(true);
                    
                    result22 = paymentDAOImpl.savePayment(payment);
//                    response.getWriter().write(result);
                    
                }else if(pType==2){
                    payment.setAmount(am);
                    payment.setPaymentDate(date);
                    payment.setPaymentTypeId(new PaymentType(Integer.parseInt(pT)));
                    payment.setSysUserSysuserId(user1);
                    payment.setBankName(arr[4]);
                    payment.setChequeDate(arr[5]);
                    payment.setChequeNo(arr[3]);
                    payment.setIsCleared(false);
                    result22 = paymentDAOImpl.savePayment(payment);
                    
                }
                
                List<Object[]> list = paymentDAOImpl.loadAccordigCus(Integer.parseInt(cid));
                int newbal = 0;
                for(Object[] obj: list){
                    int invid = Integer.parseInt(obj[2].toString());
                    double bal =Double.parseDouble(obj[1].toString());
//                    System.out.println(bal);
                    InvoicePayment inv = new InvoicePayment();

                    if(amt >= bal){
                        System.out.println("1-----------"+pType);
                        if(pType !=2){
//                            String result = paymentDAOImpl.updateBalance(invid, am);
                              String result = paymentDAOImpl.updateBalance(invid, 0.0);
                              inv.setBalanceLast(0.0);
                        }else{
                            inv.setBalanceLast(bal);
                        }
//                        String result = paymentDAOImpl.updateBalance(invid, 0.0);
                        amt = amt - bal;
                        
                        
                        
                        inv.setPaymentId(payment);
                        inv.setInvoiceId(new Invoice(invid));
                        
                        String result1 = paymentDAOImpl.saveInvoicePayment(inv);
//                        System.out.println("....."+amt+"......");
                    }else{
                        System.out.println("2-----------"+pType);
                        double at =bal-amt;
                        if(pType != 2){
//                            String result = paymentDAOImpl.updateBalance(invid, am);
                            String result = paymentDAOImpl.updateBalance(invid, at);
                            inv.setBalanceLast(at);
                        }else{
                            inv.setBalanceLast(bal);
                        }
//                        String result = paymentDAOImpl.updateBalance(invid, at);
                        
//                        InvoicePayment inv = new InvoicePayment();
                        
                        inv.setPaymentId(payment);
                        inv.setInvoiceId(new Invoice(invid));
                        
                        String result1 = paymentDAOImpl.saveInvoicePayment(inv);
                        
//                        System.out.println("....."+at+"");
                        break;
                        
                    }
                    
//                    System.out.println("-------"+newbal+1);
                }
                response.getWriter().write(result22);
                break;
                
            }
            case "ClearCheque": {
                List<Customer> customerList = customerDAOImpl.getListofUsers(company);
                request.setAttribute("customerList", customerList);
                requestDispatcher = request.getRequestDispatcher("app/payment/chequeStatus.jsp");
                requestDispatcher.forward(request, response);
                break;
            }

            case "ChequeStatus": {
                String customer = request.getParameter("customerId");

                int customerId = 0;
                if (customer != null) {
                    customerId = Integer.parseInt(customer.trim());
                }

                List<Object[]> inList = paymentDAOImpl.loadChequeDetails(customerId);

                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONObject job1 = null;

                for (Object[] p : inList) {
                    job1 = new JSONObject();
                    job1.put("pId", p[0].toString());
                    job1.put("chkNo", p[1].toString());
                    job1.put("bank", p[2].toString());
                    job1.put("amount", p[3].toString());
                    job1.put("iId", p[4].toString());
                    job1.put("date", p[5].toString());
                    jar1.add(job1);
                }
                jOB.put("jArr1", jar1);
                response.getWriter().write(jOB.toString());
                break;
            }
            case "DoClearCheque": {
                String dataArr = request.getParameter("dataArr");
                String arr[] = dataArr.split(",");
                String pid = arr[0];
                String invId = arr[1];
                String amount = arr[2];

                int paymentId = 0;
                if (pid != null) {
                    paymentId = Integer.parseInt(pid.trim());
                }
                System.out.println("'''''''''''''''''"+paymentId);
                int invoiceId = 0;
                if (invId != null) {
                    invoiceId = Integer.parseInt(invId.trim());
                }

                double cAmt = 0;
                if (amount != null) {
                    cAmt = Double.parseDouble(amount.trim());
                }
                boolean bool = true;
                List<InvoicePayment> invoiceList = paymentDAOImpl.loadInvoiceByPayid(paymentId);
                
                    for(InvoicePayment inv: invoiceList){
                        int inId = inv.getInvoiceId().getInvoiceId();
                        double bal = inv.getBalanceLast();
                        
                        if(cAmt >= bal){
                            cAmt = cAmt - bal;
                            String result = paymentDAOImpl.updateBalance(inId, 0.0);
                            String result1 = paymentDAOImpl.updateInvoiceBal(inId, 0.0, paymentId);
                            if(result.equals("Success") && result1.equals("Success") ){
                                bool = true;
                            }else{
                                bool = false;
                            }
                            
                        }else{
                            double at = bal - cAmt;
                            String result = paymentDAOImpl.updateBalance(inId, at);
                            String result1 = paymentDAOImpl.updateInvoiceBal(inId, at, paymentId);
                            if(result.equals("Success") && result1.equals("Success") ){
                                bool = true;
                            }else{
                                bool = false;
                            }
                        }
                    }
                if(bool){
                    String result = paymentDAOImpl.updateClearCheque(paymentId);
                    response.getWriter().write(result);
                }    

                break;
            }
            case "ReturnCheque": {
                String dataArr = request.getParameter("pId");
                
                response.getWriter().write(VertecConstants.SUCCESS);

                break;
            }
            case "SelectDates": {
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");

                String[] neArr = {fromDate, toDate};

                List<Object[]> iiList = paymentDAOImpl.paymentsAccordingToPeriod(neArr,company);

                request.setAttribute("iiList", iiList);

                requestDispatcher = request.getRequestDispatcher("app/payment/showPayment.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
//-----------------------------------------------------------------------------------------
            
            case "ToDoGRNPayment": {
                List<Supplier> suppList = supplierDAOImpl.getListofUsers(company);
                request.setAttribute("suppList", suppList);
                requestDispatcher = request.getRequestDispatcher("app/payment/doGRNPayment.jsp");
                
                requestDispatcher.forward(request, response);
                break;
            }
            
            case "ToDoGRNMultiPay": {
                List<Supplier> suppList = supplierDAOImpl.getListofUsers(company);
                request.setAttribute("suppList", suppList);
                requestDispatcher = request.getRequestDispatcher("app/payment/multiGrnPayment.jsp");
                
                requestDispatcher.forward(request, response);
                break;
            }
            
            case "doGRNPayment": {
                String SuppId = request.getParameter("supplierId").trim();
//                List<GrnInfo> suppList = paymentDAOImpl.loadGRNBySuppid(Integer.parseInt(SuppId));
                List<GrnInfo> suppList = paymentDAOImpl.loadGRNBySuppidWithBalance(Integer.parseInt(SuppId));
                JSONObject JOB = new JSONObject();
                JSONArray jar = new JSONArray();
                JSONObject job = null;
                for (GrnInfo g : suppList) {
                    job = new JSONObject();
                    job.put("id", g.getGrnInfoId());
                    job.put("date", g.getDate());
                    job.put("total", g.getTotal());
                    job.put("addname", g.getSysUserSysuserId().getFirstName());
                    job.put("outstanding", g.getOutstanding());
                    
                    jar.add(job);
                }
                
                JOB.put("grninfo", jar);
                response.getWriter().write(JOB.toString());
                break;
            }
            
            case "doGRNMultiPayment": {
                String SuppId = request.getParameter("supplierId").trim();
//                List<GrnInfo> suppList = paymentDAOImpl.loadGRNBySuppid(Integer.parseInt(SuppId));
                List<GrnInfo> suppList = paymentDAOImpl.loadGRNBySuppidWithBalance(Integer.parseInt(SuppId));
                JSONObject JOB = new JSONObject();
                JSONArray jar = new JSONArray();
                JSONObject job = null;
                for (GrnInfo g : suppList) {
                    job = new JSONObject();
                    job.put("id", g.getGrnInfoId());
                    job.put("date", g.getDate());
                    job.put("total", g.getTotal());
                    job.put("addname", g.getSysUserSysuserId().getFirstName());
                    job.put("outstanding", g.getOutstanding());
                    
                    jar.add(job);
                }
                
                JOB.put("grninfo", jar);
                response.getWriter().write(JOB.toString());
                break;
            }
            
            case "saveGRNpayment":{
                
                String dataArr = request.getParameter("dataArr");
                
                String arr[] = dataArr.split(",");
                String pT = arr[0];
                String pamount = arr[1];
                String tot = arr[2];
                String bal = arr[3];
                String grnId = arr[4];
                String bank="";
                String cheNo="";
                String cheDate="";
                    if(pT.equals("2")){
                        cheNo = arr[5];
                        bank = arr[6];
                        cheDate = arr[7];
                    }
                
                
                System.out.println(".........."+grnId);
                System.out.println(".........."+pamount);
                System.out.println(".........."+bal);
                
                int pType = Integer.parseInt(pT);

//                double am = Integer.parseInt(pamount);
                double amt = Integer.parseInt(pamount);
                double balance = Integer.parseInt(bal);
                
                String result = paymentDAOImpl.updateGRNinfo(Integer.parseInt(grnId),balance-amt);
                Date date = new Date();
                Payment payment = new Payment();
                String result22="";
                if(pType == 1){
                    payment.setAmount(amt);
                    payment.setPaymentDate(date);
                    payment.setPaymentTypeId(new PaymentType(pType));
                    payment.setSysUserSysuserId(user1);
                    payment.setIsCleared(true);
                    
                    result22 = paymentDAOImpl.savePayment(payment);
//                    response.getWriter().write(result);
                    
                }else if(pType==2){
                    payment.setAmount(amt);
                    payment.setPaymentDate(date);
                    payment.setPaymentTypeId(new PaymentType(pType));
                    payment.setSysUserSysuserId(user1);
                    payment.setBankName(bank);
                    payment.setChequeDate(cheDate);
                    payment.setChequeNo(cheNo);
                    payment.setIsCleared(true);
                    result22 = paymentDAOImpl.savePayment(payment);
                    
                }
                GrnPayment gp = new GrnPayment();
                gp.setGrnInfoId(new GrnInfo(Integer.parseInt(grnId)));
                gp.setPaymentId(payment);
                String result1 = GRNDAOImpl.SaveGRNPayment(gp);
                if(result1.equals("Success") && result22.equals("Success") && result.equals("Success") ){
                    response.getWriter().write(VertecConstants.SUCCESS);
                }
                
                break;
                
            }
            
            case "saveGRNMultipayment":{
                
                String dataArr = request.getParameter("dataArr");
                
                String arr[] = dataArr.split(",");
                String pT = arr[0];
                String pamount = arr[1];
                String tot = arr[2];
                String supId = arr[3];
                
                String bank="";
                String cheNo="";
                String cheDate="";
                    if(pT.equals("2")){
                        cheNo = arr[4];
                        bank = arr[5];
                        cheDate = arr[6];
                    }
                int pType = Integer.parseInt(pT);

//                double am = Integer.parseInt(pamount);
                double amt = Integer.parseInt(pamount);
                
                Date date = new Date();
                Payment payment = new Payment();
                String result22="";
                if(pType == 1){
                    payment.setAmount(amt);
                    payment.setPaymentDate(date);
                    payment.setPaymentTypeId(new PaymentType(pType));
                    payment.setSysUserSysuserId(user1);
                    payment.setIsCleared(true);
                    
                    result22 = paymentDAOImpl.savePayment(payment);
//                    response.getWriter().write(result);
                    
                }else if(pType==2){
                    payment.setAmount(amt);
                    payment.setPaymentDate(date);
                    payment.setPaymentTypeId(new PaymentType(pType));
                    payment.setSysUserSysuserId(user1);
                    payment.setBankName(bank);
                    payment.setChequeDate(cheDate);
                    payment.setChequeNo(cheNo);
                    payment.setIsCleared(true);
                    result22 = paymentDAOImpl.savePayment(payment);
                    
                }
                
//                System.out.println(".........."+grnId);
//                System.out.println(".........."+pamount);
//                System.out.println(".........."+bal);
                
                
//                double balance = Integer.parseInt(bal);
                String result="Success";
                List<GrnInfo> suppList = paymentDAOImpl.loadGRNBySuppidWithBalance(Integer.parseInt(supId));
                    for(GrnInfo g: suppList){
                        GrnPayment gp = new GrnPayment();
                        if(amt >= g.getOutstanding()){
                            amt = amt-g.getOutstanding();
                            result = paymentDAOImpl.updateGRNinfo(g.getGrnInfoId(),0.0);
                            gp.setGrnInfoId(new GrnInfo(g.getGrnInfoId()));
                            gp.setPaymentId(payment);
                            result = GRNDAOImpl.SaveGRNPayment(gp);
                            
                        }else{
                            double at = g.getOutstanding() - amt;
                            result = paymentDAOImpl.updateGRNinfo(g.getGrnInfoId(),at);
                            gp.setGrnInfoId(new GrnInfo(g.getGrnInfoId()));
                            gp.setPaymentId(payment);
                            result = GRNDAOImpl.SaveGRNPayment(gp);
                            break;
                        }
                        
                    }
                
                if(result.equals("Success") && result22.equals("Success") ){
                    response.getWriter().write(VertecConstants.SUCCESS);
                }
                
                break;
                
            }
//------------------------------------------------------------------------------------
            
            case "receipt": {
//                System.out.println("//////////////calling");
                String invid = request.getParameter("invid");
//                System.out.println("...... "+invid);
                String bal = request.getParameter("balAmount");
//                System.out.println("...... "+bal);
                String pay = request.getParameter("payAmount");
//                System.out.println("...... "+pay);
                Invoice invoice = paymentDAOImpl.viewInvoice(Integer.parseInt(invid));
                System.out.println("..."+invoice.getInvoiceId());
                request.setAttribute("invoice", invoice);
                double tot = paymentDAOImpl.getPaidTooal(Integer.parseInt(invid));
                System.out.println("...... "+tot);
                request.setAttribute("tot", tot);
                request.setAttribute("pay", pay);
                request.setAttribute("bal", bal);
                requestDispatcher = request.getRequestDispatcher("app/payment/receipt.jsp");
                requestDispatcher.forward(request, response);
                
            }
//------------------------------------------------------------------------------------
            case "multiReceipt": {
                
                String arrList = request.getParameter("arr");
                request.setAttribute("arrList", arrList);
                String arr[] = arrList.split(",");
                
                for(int i=0 ; i<arr.length;i++){
                    System.out.print(arr[i]);
                }
                
                System.out.println("");
                
                String c = request.getParameter("customer");
                System.out.println(".......  :"+c);
                String pay = request.getParameter("payment");
                System.out.println(".......  :"+pay);
                Payment payment = paymentDAOImpl.getFirstPayment(user1);
                List<InvoicePayment> list = paymentDAOImpl.getInvoiceByPaymnet(payment);
                request.setAttribute("invoicePay", list);
                Customer cus = customerDAOImpl.viewCustomer(Integer.parseInt(c));
                request.setAttribute("customer", cus);
                request.setAttribute("pay", pay);
                requestDispatcher = request.getRequestDispatcher("app/payment/multiReceipt.jsp");
                requestDispatcher.forward(request, response);
                
                
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
