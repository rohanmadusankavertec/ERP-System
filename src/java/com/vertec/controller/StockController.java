package com.vertec.controller;

import com.vertec.daoimpl.BranchDAOImpl;
import com.vertec.daoimpl.InvoiceDAOImpl;
import com.vertec.daoimpl.ProductDAOImpl;
import com.vertec.daoimpl.StockDAOImpl;
import com.vertec.hibe.model.Branch;
import com.vertec.hibe.model.BranchProductmaster;
import com.vertec.hibe.model.BranchStock;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.ProductMaster;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.VertecConstants;
import java.io.IOException;
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
 * @author Sandun
 */
public class StockController extends HttpServlet {

    /**
     * StockController URL=Stock This Controller all the action calls get from
     * app/stock/(branchPM.jsp or vehicleStock.jsp) and all functions call get
     * from app/js/stock.js
     */
    private final StockDAOImpl stockDAOImpl = new StockDAOImpl();
    private final ProductDAOImpl productDAOImpl = new ProductDAOImpl();
    private final BranchDAOImpl branchDAOImpl = new BranchDAOImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession httpSession = request.getSession();
        SysUser user1 = (SysUser) httpSession.getAttribute("user");
        Company company = (Company) httpSession.getAttribute("company");
        RequestDispatcher requestDispatcher;
        boolean isValidated = true;

        switch (action) {
            /**
             * Url for branch product master module
             */
            //Manage branch product master
            case "ManagePMS": {
                List<Branch> bList = branchDAOImpl.loadAllBranches(user1.getBranchBranchId());
                List<Product> pList = productDAOImpl.loadAllProducts(company);
                request.setAttribute("bList", bList);
                request.setAttribute("pList", pList);
                requestDispatcher = request.getRequestDispatcher("app/stock/branchPM.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            /**
             * aproductId combobox in ADD_BRANCH_PRODUCT_MASTER container
             * function loadPMToAdd() this one is use to load Product Master for
             * Add in Branch Product Master
             */
            
            //Load adjustment page
            case "Adjustment": {
                List<Product> product = productDAOImpl.loadAllProducts(company);
                request.setAttribute("product", product);
                requestDispatcher = request.getRequestDispatcher("app/stock/Adjustment.jsp");
                requestDispatcher.forward(request, response);
                break;
            }
            //load product's current qty according to product master
            case "loadProductFromStock": {
                String pmid = request.getParameter("pmid");
                BranchProductmaster bpm = stockDAOImpl.loadBranchProductMaster(user1.getBranchBranchId(), Integer.parseInt(pmid));
                JSONObject JOB = new JSONObject();
                JOB.put("qty", bpm.getQuantity());
                JOB.put("id", bpm.getBpmId());
                response.getWriter().write(JOB.toString());
                break;
            }
            // Update quantity
            case "SubmitAdjustment": {
                String bpmid = request.getParameter("bpmid");
                String qty = request.getParameter("qty");
                String result = stockDAOImpl.updateBranchStockByAdjustment(Integer.parseInt(qty), Integer.parseInt(bpmid));
                if (result.equals(VertecConstants.SUCCESS)) {
                    response.getWriter().write(VertecConstants.SUCCESS);
                } else {
                    response.getWriter().write(VertecConstants.ERROR);
                }
                break;
            }
//            get purchase price and selling price  
            case "ToAddBPM": {

                String dataAr = request.getParameter("dataArr");
                String dataArr[] = dataAr.split(",");
                String branId = dataArr[0];
                String proId = dataArr[1];

                System.out.println("_______________________________________________");
                System.out.println(dataAr);
                System.out.println("BRANCH ID : " + dataArr[0]);
                System.out.println("PRODUCT ID : " + dataArr[1]);
                System.out.println("_______________________________________________");

                int productId = 0;
                int branchId = 0;
                if (proId != null) {
                    productId = Integer.parseInt(proId);
                }
                if (branId != null) {
                    branchId = Integer.parseInt(branId);
                }
                int arr[] = {branchId, productId};

                List<Object[]> pList = stockDAOImpl.loadProductMasterToAddBPM(arr);
                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONObject job1 = null;

                for (Object[] p : pList) {

                    System.out.println("PM ID : " + p[0].toString());
                    System.out.println("BUYING PRICE : " + p[1].toString());
                    System.out.println("SELLING PRICE : " + p[2].toString());

                    job1 = new JSONObject();
                    job1.put("pmid", p[0].toString());
                    job1.put("pprice", p[2].toString());
                    job1.put("sprice", p[3].toString());
                    jar1.add(job1);
                }
                jOB.put("jArr1", jar1);
                response.getWriter().write(jOB.toString());
                break;
            }
            /**
             * branchId combobox in branchPm.jsp function loadPC() to load all
             * product masters with save for branches
             */
//            get product according to branch
            case "ToBranch": {
                String branch = request.getParameter("branchId");
                int branchId = 0;
                if (branch != null) {
                    branchId = Integer.parseInt(branch);
                }

                List<Object[]> pList = stockDAOImpl.loadProductFromBranchStock(branchId);
                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONObject job1 = null;

                for (Object[] p : pList) {
                    job1 = new JSONObject();
                    job1.put("pid", p[0].toString());
                    job1.put("pcode", p[1].toString());
                    job1.put("pname", p[2].toString());
                    jar1.add(job1);
                }
                jOB.put("jArr1", jar1);
                response.getWriter().write(jOB.toString());
                break;
            }
            /**
             * function loadPM() To create table of data that already available
             * product masters with branches
             */
            // get branch product master details
            case "ToBranchPM": {
                String dataAr = request.getParameter("dataArr");
                String dataArr[] = dataAr.split(",");
                String branId = dataArr[0];
                String proId = dataArr[1];

                int productId = 0;
                int branchId = 0;
                if (proId != null) {
                    productId = Integer.parseInt(proId);
                }
                if (branId != null) {
                    branchId = Integer.parseInt(branId);
                }
                int arr[] = {branchId, productId};

                List<Object[]> prList = stockDAOImpl.loadProductMasterFromBPM(arr);

                JSONObject jOB = new JSONObject();
                JSONArray jar1 = new JSONArray();
                JSONObject job1 = null;

                for (Object[] p : prList) {
                    job1 = new JSONObject();
                    job1.put("bpmid", p[0].toString());
                    job1.put("pcode", p[3].toString());
                    job1.put("pname", p[4].toString());
                    job1.put("quantity", p[5].toString());
                    job1.put("pprice", p[6].toString());
                    job1.put("sprice", p[7].toString());
                    jar1.add(job1);
                }
                jOB.put("jArr1", jar1);
                response.getWriter().write(jOB.toString());
                break;
            }
            /**
             * to save new branch product master form
             * action="Stock?action=SaveBPM" in branchPM.jsp
             */
            
            //Save branch product master
            case "SaveBPM": {

                String abranchId = request.getParameter("abranchId");
                String aproductId = request.getParameter("aproductId");
                String apmId = request.getParameter("apmId");
                String aquantity = request.getParameter("aquantity");

                int branchId = 0;
                int productId = 0;
                int pmId = 0;
                int quantity = 0;

                if (abranchId != null) {
                    branchId = Integer.parseInt(abranchId);
                }
                if (aproductId != null) {
                    productId = Integer.parseInt(aproductId);
                }
                if (apmId != null) {
                    pmId = Integer.parseInt(apmId);
                }
                if (aquantity != null) {
                    quantity = Integer.parseInt(aquantity);
                }
                Date date = new Date();
                Branch branch = new Branch(branchId);
                Product product = new Product(productId);
                ProductMaster productMaster = new ProductMaster(pmId);
//                BranchProductmaster branchProductmaster = new BranchProductmaster(quantity, date, branch, productMaster);
                BranchProductmaster branchProductmaster = new BranchProductmaster();
                branchProductmaster.setBranchId(branch);
                branchProductmaster.setLastUpdatedDate(date);
                branchProductmaster.setProductMasterId(productMaster);
                branchProductmaster.setQuantity(quantity);

                int arr[] = {branchId, productId};
                List<BranchStock> viewBS = stockDAOImpl.viewAvailability(arr);
                if (!viewBS.isEmpty()) {
                    int bsId = 0;
                    int avaquantity = 0;
                    for (BranchStock bs : viewBS) {
                        bsId = bs.getBranchStockId();
                        avaquantity = bs.getQuantity();
                    }
                    quantity = avaquantity + quantity;
                    BranchStock branchStock = new BranchStock(bsId, quantity, date, user1);
                    String result1 = stockDAOImpl.updateBranchStock(branchStock);
                } else {
                    BranchStock branchStock = new BranchStock(quantity, date, date, user1, user1, product, branch);
                    String result1 = stockDAOImpl.saveBranchStock(branchStock);
                }

                String result = stockDAOImpl.saveBranchPM(branchProductmaster);
                if (result.equals(VertecConstants.SUCCESS)) {
                    request.getSession().setAttribute("Success_Message", "Successfully Added");
                    response.sendRedirect("Stock?action=ManagePMS");
                } else {
                    request.getSession().setAttribute("Error_Message", "Not Added,Please Tri again");
                    response.sendRedirect("Stock?action=ManagePMS");
                }
                break;
            }
            /**
             * function updateBPM() To update Selected Stock
             */
            // Update branch product master
            case "SaveUpdatedStock": {

                String dataAr = request.getParameter("dataArr");
                String dataArr[] = dataAr.split(",");
                String bpmIdS = dataArr[0];
                String quant = dataArr[1];
                String branch = dataArr[2];
                String product = dataArr[3];

                int bpmId = 0;
                int quantity = 0;
                int branchId = 0;
                int productId = 0;

                if (bpmIdS != null) {
                    bpmId = Integer.parseInt(bpmIdS);
                }
                if (quant != null) {
                    quantity = Integer.parseInt(quant);
                }
                if (branch != null) {
                    branchId = Integer.parseInt(branch);
                }
                if (product != null) {
                    productId = Integer.parseInt(product);
                }
                Date date = new Date();

                int arr[] = {branchId, productId};
                List<BranchStock> viewBS = stockDAOImpl.viewAvailability(arr);
                int avaquantity = 0;
                int bsId = 0;
                for (BranchStock bs : viewBS) {
                    bsId = bs.getBranchStockId();
                    avaquantity = bs.getQuantity();
                }
                BranchProductmaster bp = stockDAOImpl.viewBranchPM(bpmId);
                int newquantity = avaquantity + quantity;
                BranchStock branchStock = new BranchStock(bsId, newquantity, date, user1);
                String result1 = stockDAOImpl.updateBranchStock(branchStock);

                BranchProductmaster branchProductmaster = new BranchProductmaster(bpmId, bp.getQuantity() + quantity, date);
                String result = stockDAOImpl.updateBranchPM(branchProductmaster);
                response.getWriter().write(result);
                break;
            }
            // Load branch stock
            case "branchStock": {
                System.out.println("");
                String type = request.getParameter("stockType");
                String branch = request.getParameter("branch");
                request.setAttribute("type", type);
                request.setAttribute("branch", branch);
                requestDispatcher = request.getRequestDispatcher("app/stock/BranchStock.jsp");
                requestDispatcher.forward(request, response);
                break;

            }
            // view reorder level
            case "reorderlevel": {
                List<Branch> bList = stockDAOImpl.loadAllBran(company);
                request.setAttribute("blist", bList);
                requestDispatcher = request.getRequestDispatcher("app/report/ReOrderLevel.jsp");
                requestDispatcher.forward(request, response);break;
            }
            //reorder level report
            case "reorderlevelreport": {
                String branchId  = request.getParameter("branchId").trim();
                List<String[]> productList = stockDAOImpl.viewProductByBranch(branchId);
                request.setAttribute("productList", productList);
                requestDispatcher = request.getRequestDispatcher("app/report/ReOrderLevelReport.jsp");
                requestDispatcher.forward(request, response);
                break;
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
