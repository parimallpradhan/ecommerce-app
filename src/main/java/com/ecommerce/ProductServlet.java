package com.ecommerce;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<h1>Product List</h1>");
        out.println("<ul>");
        out.println("<li>iPhone - ₹80,000</li>");
        out.println("<li>Laptop - ₹60,000</li>");
        out.println("</ul>");
    }
}
