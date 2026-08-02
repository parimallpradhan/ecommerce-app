package com.ecommerce;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CheckoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<html><head><title>Payment</title></head><body>");
        out.println("<h1>💳 Payment Page</h1>");

        out.println("<form action='payment-success' method='post'>");

        out.println("Name: <input type='text' name='name'><br><br>");
        out.println("Card Number: <input type='text'><br><br>");
        out.println("Amount: ₹100000<br><br>");

        out.println("<button type='submit'>Pay Now</button>");
        out.println("</form>");

        out.println("</body></html>");
    }
}
