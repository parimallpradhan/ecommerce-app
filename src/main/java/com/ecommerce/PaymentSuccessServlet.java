package com.ecommerce;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PaymentSuccessServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession();
        session.invalidate(); // clear cart

        out.println("<h1>✅ Payment Successful!</h1>");
        out.println("<a href='products'>Continue Shopping</a>");
    }
}
