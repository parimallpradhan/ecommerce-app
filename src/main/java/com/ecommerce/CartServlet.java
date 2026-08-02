package com.ecommerce;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class CartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession();
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        out.println("<h1>🛒 Your Cart</h1>");

        if (cart == null || cart.isEmpty()) {
            out.println("<p>Cart is empty</p>");
        } else {
            out.println("<ul>");
            for (String item : cart) {
                out.println("<li>" + item + "</li>");
            }
            out.println("</ul>");
        }

        out.println("<a href='products'>⬅ Back to Products</a>");
    }
}
