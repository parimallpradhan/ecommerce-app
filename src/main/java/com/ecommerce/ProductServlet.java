package com.ecommerce;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    List<Product> products = new ArrayList<>();

    @Override
    public void init() {
        products.add(new Product(1, "iPhone", 80000));
        products.add(new Product(2, "Laptop", 60000));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Products</h1>");

        for (Product p : products) {
            out.println("<div>");
            out.println("<h3>" + p.getName() + "</h3>");
            out.println("<p>Price: ₹" + p.getPrice() + "</p>");
            out.println("<a href='add-to-cart?id=" + p.getId() + "'>Add to Cart</a>");
            out.println("</div><hr>");
        }

        out.println("<a href='cart'>View Cart</a>");
    }
}
