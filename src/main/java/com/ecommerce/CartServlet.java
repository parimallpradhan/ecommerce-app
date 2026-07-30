package com.ecommerce;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<String> cart = (List<String>) session.getAttribute("cart");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Your Cart</h1>");

        if (cart == null || cart.isEmpty()) {
            out.println("<p>Cart is empty</p>");
        } else {
            for (String item : cart) {
                out.println("<p>" + item + "</p>");
            }
        }

        out.println("<br><a href='products'>Back to Products</a>");
        out.println("</body></html>");
    }
}
