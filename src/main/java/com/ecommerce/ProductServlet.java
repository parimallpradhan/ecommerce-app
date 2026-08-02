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

    response.setContentType("text/html;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<html><head>");
    out.println("<link rel='stylesheet' href='style.css'>");
    out.println("</head><body>");

    out.println("<div class='header'><h1>🛒 My Store</h1></div>");
    out.println("<div class='container'>");

    for (Product p : products) {

        String image = "";
        if (p.getId() == 1)
            image = "https://via.placeholder.com/250x180?text=iPhone";
        else if (p.getId() == 2)
            image = "https://via.placeholder.com/250x180?text=Laptop";

        out.println("<div class='card'>");
        out.println("<img src='" + image + "'>");
        out.println("<h3>" + p.getName() + "</h3>");
        out.println("<p class='price'>₹" + p.getPrice() + "</p>");
        out.println("<a class='btn' href='add-to-cart?id=" + p.getId() + "'>Add to Cart</a>");
        out.println("</div>");
    }

    out.println("</div>");
    out.println("<div style='text-align:center;'>");
    out.println("<a class='btn' href='cart'>Go to Cart</a>");
    out.println("</div>");

    out.println("</body></html>");
}
}
