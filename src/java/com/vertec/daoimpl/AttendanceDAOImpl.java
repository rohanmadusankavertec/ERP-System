/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.daoimpl;


import com.vertec.hibe.model.Attendance;
import com.vertec.hibe.model.Employee;
import com.vertec.hibe.model.Fpfiles;
import com.vertec.hibe.model.Leaves;
import com.vertec.hibe.model.SysUser;
import com.vertec.util.NewHibernateUtil;
import com.vertec.util.Save;
import com.vertec.util.VertecConstants;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vertec-r
 */
public class AttendanceDAOImpl{

    public List<Leaves> getLeave() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.getNamedQuery("Leaves.findAll");
            List<Leaves> leave = query.list();
            return leave;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
    
    public List<Leaves> getLeaveReportByEmployee(int i) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("SELECT l FROM Leaves l WHERE l.employeeId=:emp");
            query.setParameter("emp", new EmployeeDAOImpl().getEmployee(i));
            List<Leaves> leave = query.list();
            return leave;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
    
    public List<Leaves> getLeaveReportByDateRange(Date from, Date to) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("SELECT l FROM Leaves l WHERE (l.fromdate>=:from AND l.todate<=:from) OR  (l.fromdate<=:to AND l.todate>=:to)");
            query.setParameter("from",from);
            query.setParameter("to", to);
            List<Leaves> leave = query.list();
            return leave;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<Leaves> getLeaveReportByDate(Date date) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("SELECT l FROM Leaves l WHERE l.fromdate<=:date AND l.todate>=:date");
            query.setParameter("date",date);
            List<Leaves> leave = query.list();
            return leave;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
    public String ApproveLeave(Leaves em) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("Update Leaves set remark=:remark,is_pay=:isPay,is_approved=:isApproved where id=:id");
                query.setParameter("remark", em.getRemark());
                query.setParameter("isPay", em.getIsPay());
                query.setParameter("isApproved", em.getIsApproved());
                query.setParameter("id", em.getId());
                query.executeUpdate();
                transaction.commit();
                return VertecConstants.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }
 public Attendance getattendance(String id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Attendance s WHERE s.id=:id");
                query.setParameter("id", Integer.parseInt(id));
                System.out.println("");
                Attendance cuList =(Attendance) query.uniqueResult();
                return cuList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }
 
  public List<Attendance> getattendanceByDate(Date date) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Attendance s WHERE s.date:date");
                query.setParameter("date", date);
                 List<Attendance> cuList =(List<Attendance>) query.list();
                return cuList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }
  public List<Attendance> getattendanceByDateRange(Date from,Date to) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Attendance s WHERE s.date>=:from AND s.date<=:to");
                query.setParameter("from", from);
                query.setParameter("to", to);
                List<Attendance> cuList =(List<Attendance>) query.list();
                return cuList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }
   public List<Attendance> getattendanceByEmployee(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT s FROM Attendance s WHERE s.employeeId=:emp");
                query.setParameter("emp", new EmployeeDAOImpl().getEmployee(id));
                List<Attendance> cuList =(List<Attendance>) query.list();
                return cuList;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }
 public String UpdateAttendance(Attendance a) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                Query query = session.createQuery("Update Attendance a set a.intime=:in,a.outtime=:out,a.date=:date where a.id=:id");
                query.setParameter("id", a.getId());
                query.setParameter("in", a.getIntime());
                query.setParameter("out", a.getOuttime());
                query.setParameter("date", a.getDate());
                query.executeUpdate();
                transaction.commit();
                return VertecConstants.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }
    public String IgnoreLeave(int em) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (session != null) {
            try {
                SQLQuery query = session.createSQLQuery("Update Leaves set is_approved=:isApproved where id=:id");
                query.setParameter("isApproved", false);
                query.setParameter("id", em);
                query.executeUpdate();
                transaction.commit();
                return VertecConstants.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return VertecConstants.ERROR;
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    public List<Attendance> getAttendance() {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.getNamedQuery("Attendance.findAll");
            List<Attendance> att = query.list();
            return att;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public String UpdateAttendance(SysUser user) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            String filename = "";
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat timeformat = new SimpleDateFormat("hh:mm");
            EmployeeDAOImpl ed = new EmployeeDAOImpl();
            Query query = session.createQuery("SELECT f FROM Fpfiles f");
            List<Fpfiles> fp = query.list();
            for (Fpfiles file : fp) {
                filename = file.getFilename();
            }
            
            List<Object> data = readexcel(filename);
            int UIDcol = 0;
            int DateTimecol = 0;
            for (Object object : data) {
                List<String> rows = (List<String>) object;
                int i = 0;
                for (String string : rows) {
                    i++;
                    System.out.println(i+">>>>"+string);
                    if (string.contains("UID")) {
                        System.out.println("GOT UID as :"+i +" "+string);
                        UIDcol = i;
                    } else if (string.contains("DateTime")) {
                        System.out.println("GOT DATETIME as :"+i +" "+string);
                        DateTimecol = i;
                    }
                }
            }

            boolean bool = false;
            for (Object object : data) {
                if (bool) {
                    List<String> rows = (List<String>) object;
                    int incr = 0;
                    int UID = 0;
                    Date Datetime = null;
                    for (String string : rows) {
                        incr++;
                        
                        System.out.println(incr+">>>>"+string);
                        
                        if (incr == UIDcol) {
                            
                            UID = Integer.parseInt(string.replace(".0", ""));
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>UID "+string);
                        } else if (incr==DateTimecol) {
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Date "+string);
                            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                            Date date = (Date) formatter.parse(string);
//                            System.out.println(date);

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
//                            System.out.println(cal.get(Calendar.DATE));
//                            System.out.println(cal.get(Calendar.HOUR));
//                            System.out.println(cal.get(Calendar.MINUTE));
//
//                            System.out.println(cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE));
//                            System.out.println(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));

                            String cmonth = "0";
                            if (cal.get(Calendar.MONTH) <= 9) {
                                cmonth += cal.get(Calendar.MONTH)+1;
                            } else {
                                cmonth = cal.get(Calendar.MONTH) +1+ "";
                            }

                            String cdate = "0";
                            if (cal.get(Calendar.DATE) <= 9) {
                                cdate += cal.get(Calendar.DATE);
                            } else {
                                cdate = cal.get(Calendar.DATE) + "";
                            }

                            String chour = "0";
                            if (cal.get(Calendar.HOUR) <= 9) {
                                chour += cal.get(Calendar.HOUR);
                            } else {
                                chour = cal.get(Calendar.HOUR) + "";
                            }

                            String cminutes = "0";
                            if (cal.get(Calendar.MINUTE) <= 9) {
                                cminutes += cal.get(Calendar.MINUTE);
                            } else {
                                cminutes = cal.get(Calendar.MINUTE) + "";
                            }

                            String cdate2 = cal.get(Calendar.YEAR) + "-" + cmonth + "-" + cdate;

                            String ctime = chour + ":" + cminutes;
                            Employee emp=ed.getEmployeeByUID(UID + "");
                            
                            if(emp!=null){
                            
                            
                            Session session1 = NewHibernateUtil.getSessionFactory().openSession();
                            Query query1 = session1.createQuery("SELECT a FROM Attendance a WHERE a.date=:date AND a.employeeId=:eid");
                            query1.setParameter("eid", emp);
                            query1.setParameter("date", dateformat.parse(cdate2));
                            Attendance att = (Attendance) query1.uniqueResult();
                            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                            System.out.println(att);
                            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                            session1.close();
                            if (att == null) {
                                System.out.println("Intime");
                                Attendance a = new Attendance();
                                a.setDate(dateformat.parse(cdate2));
                                a.setMarkedDate(new Date());
                                System.out.println("><<><><><<><><><><><><><><><><><<><>");
                                System.out.println("____________________________________________________________");
                                System.out.println(UID);
                                System.out.println(emp.getFname());
                                System.out.println("____________________________________________________________");
                                System.out.println("><<><><><<><><><><><><><><><><><<><>");
                                a.setEmployeeId(ed.getEmployeeByUID(UID + ""));
                                a.setIsValid(true);
                                a.setOuttime(null);
                                a.setIntime(cal.getTime());
                                a.setMarkedBy(user);
                                Save.Save(a);
                                System.out.println("Saved as New Row");
                            } else {
                                System.out.println("Out Time");
                                Session session2 = NewHibernateUtil.getSessionFactory().openSession();
                                
//                                Query query2 = session.createQuery("SELECT a FROM Attendance a WHERE a.date='" + cdate2 + "' AND a.employeeId=(select id from Employee where fpid='" + UID + "') AND a.intime like '" + ctime + "%' AND a.outtime='' ");
//                                List<Attendance> att2 = query2.list();
//                                if (att2 != null) {
                                    SQLQuery query3 = session2.createSQLQuery("Update attendance a set a.outtime=:outtime where a.id=:id");
                                    query3.setParameter("outtime", cal.getTime());
                                    query3.setParameter("id", att);
                                    query3.executeUpdate();
                                    Transaction tr = session2.beginTransaction();
                                    tr.commit();
                                    System.out.println("Updated a Row");
//                                }
                                    session2.close();
                            }
                            }
                        }
                        
                    }

                } else {
                    bool = true;
                }
            }
//            System.out.println("UID COLUMN : " + UIDcol);
//            System.out.println("Date Time COLUMN : " + DateTimecol);
        } catch (Exception e) {
            e.printStackTrace();
            return VertecConstants.ERROR;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return VertecConstants.SUCCESS;
    }

    public List<Object> readexcel(String path) {
//        String data = "";
        List<Object> table = new ArrayList<Object>();
        try {
            // Get the workbook instance for XLSX file
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path));

            // Get first sheet from the workbook
            XSSFSheet sheet = wb.getSheetAt(0);

            Row row;
            Cell cell;

            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                row = rowIterator.next();

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> rows = new ArrayList<String>();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            rows.add(cell.getRichStringCellValue().getString());
//                            data+=cell.getRichStringCellValue().getString();
//                            System.out.print(cell.getRichStringCellValue().getString());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {

                                rows.add(cell.getDateCellValue() + "");
//                                data+=cell.getDateCellValue();
//                                System.out.print(cell.getDateCellValue());
                            } else {
                                rows.add(cell.getNumericCellValue() + "");
//                                data+=cell.getNumericCellValue();
//                                System.out.print(cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            rows.add(cell.getBooleanCellValue() + "");
//                            data+=cell.getBooleanCellValue();
//                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            rows.add(cell.getCellFormula() + "");
//                            data+=cell.getCellFormula();
//                            System.out.print(cell.getCellFormula());
                            break;
                        default:
//                            System.out.print("");
                    }
//                    data += "-";
//                    System.out.print(" - ");
                }
                table.add(rows);
//                data += ";;;";
//                System.out.println(";;;");
            }
        } catch (Exception e) {
            System.err.println("Exception :" + e.getMessage());
        }
        return table;
    }

    public String readexcel2(String path) {
        try {
            // Get the workbook instance for XLSX file
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path));

            // Get first sheet from the workbook
            XSSFSheet sheet = wb.getSheetAt(0);

            Row row;
            Cell cell;

            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                row = rowIterator.next();

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getRichStringCellValue().getString());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                System.out.print(cell.getDateCellValue());
                            } else {
                                System.out.print(cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            System.out.print(cell.getCellFormula());
                            break;
                        default:
                            System.out.print("");
                    }
                    System.out.print(" - ");
                }
                System.out.println(";;;");
            }
        } catch (Exception e) {
            System.err.println("Exception :" + e.getMessage());
        }
        return null;
    }
}
