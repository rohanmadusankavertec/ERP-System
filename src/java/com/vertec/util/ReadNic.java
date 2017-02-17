/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertec.util;

/**
 *
 * @author vertec-r
 */
public class ReadNic {

    int month[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    

    public int getYear(String nic) {
        return (1900 + Integer.parseInt(nic.substring(0, 2)));
    }

    public int getDays(String nic) {
        int d = Integer.parseInt(nic.substring(2, 5));
        if (d > 500) {
            return (d - 500);
        } else {
            return d;
        }
    }

    public String setMonth(String nic) {
        int mo = 0, da = 0;
        int days = getDays(nic);

        for (int i = 0; i < month.length; i++) {
            if (days < month[i]) {
                mo = i + 1;
                da = days;
                break;
            } else {
                days = days - month[i];
            }
        }
        
        String d="";
        if(da<10){
        d="0"+da;
        }else{
        d=da+"";
        }
        
        
        String m="";
        if(mo<10){
        m="0"+mo;
        }else{
        m=mo+"";
        }
        
        
        String date="";
//        System.out.println("Month : " + mo + "\nDate : " + da);
        
        date+=getYear(nic)+"-"+m+"-"+d;
        return date;
    }

    public String getSex(String nic) {
        String M = "Male", F = "Female";
        int d = Integer.parseInt(nic.substring(2, 5));
        if (d > 500) {
            return F;
        } else {
            return M;
        }
    }
public void GetNICData(){
    

}

    public static void main(String[] args) {
        ReadNic r = new ReadNic();
        System.out.println(r.setMonth("931531352v"));
        System.out.println(r.getSex("931531352v"));
        
    }






//    public static void main(String[] args) {
//        NIC_Details N = new NIC_Details();
//        System.out.println("Your Deatials of Date of Birth from NIC Number");
//        System.out.println("Year : " + N.getYear());
//        N.setMonth();
//        System.out.println("Sex : " + N.getSex());
//    }

}
