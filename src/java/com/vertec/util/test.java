/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author vertec-r
 */
public class test {

public static class Person {
    public String name;
    public int id;
    public Date hireDate;

    public Person(String iname, int iid, Date ihireDate) {
        name = iname;
        id = iid;
        hireDate = ihireDate;
    }

    public String toString() {
        return name + " " + id + " " + hireDate.toString();
    }


    public static class CompDate implements Comparator<Person> {
        private int mod = 1;
        public CompDate(boolean desc) {
            if (desc) mod =-1;
        }
        @Override
        public int compare(Person arg0, Person arg1) {
            return mod*arg0.hireDate.compareTo(arg1.hireDate);
        }
    }
}

public static void main(String[] args) {
    // TODO Auto-generated method stub
    SimpleDateFormat df = new SimpleDateFormat("mm-dd-yyyy");
    ArrayList<Person> people;
    people = new ArrayList<Person>();
    try {
        people.add(new Person("Joe", 92422, df.parse("12-12-2010")));
        people.add(new Person("Joef", 24122, df.parse("1-12-2010")));
        people.add(new Person("Joee", 24922, df.parse("12-2-2010")));
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


    Collections.sort(people, new Person.CompDate(false));
    System.out.println("BY Date asc");
    for (Person p : people) {
        System.out.println(p.toString());
    }
    

}

}
