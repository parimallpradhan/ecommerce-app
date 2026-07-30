package com.ecommerce;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>🛒 Your Cart</h1>");

        double total = 0;

        if (cart == null || cart.isEmpty()) {
            out.println("<p>Cart is empty</p>");
        } else {
            int index = 0;
            for (Product p : cart) {
                out.println("<div>");
                out.println(p.getName() + " - ₹" + p.getPrice());
                out.println(" <a href='remove-from-cart?index=" + index + "'>Remove</a>");
                out.println("</div>");
                total += p.getPrice();
                index++;
            }

            out.println("<h3>Total: ₹" + total + "</h3>");
            out.println("<a href='checkout'>Proceed to Checkout</a>");
        }

        out.println("<br><a href='products'>Back to Products</a>");
    }
}
