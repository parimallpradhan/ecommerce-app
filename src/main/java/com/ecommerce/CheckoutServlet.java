package com.ecommerce;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("cart");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>✅ Payment Successful!</h1>");
        out.println("<a href='products'>Continue Shopping</a>");
        out.println("<br><a href='checkout'>Proceed to Payment 💳</a>");
    }
}
