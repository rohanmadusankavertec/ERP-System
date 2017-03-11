/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vertec.util;

import com.vertec.daoimpl.ProductDAOImpl;
import com.vertec.hibe.model.Company;
import com.vertec.hibe.model.Product;
import com.vertec.hibe.model.ProductHasTax;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Rohan Madusanka @Contact 071 - 9085504 @E-mail
 * rohanmadusanka72@gmail.com
 */
public class testingClass {

    public static void main(String[] args) {
        List<Product> p = new ProductDAOImpl().loadAllProducts(new Company(1));

        for (Product product : p) {
            System.out.println(product.getProductName());
            Collection<ProductHasTax> c =  product.getProductHasTaxCollection();
            for (ProductHasTax pht : c) {
                System.out.println(pht.getTaxId().getName());
            }
        }
    }
}
