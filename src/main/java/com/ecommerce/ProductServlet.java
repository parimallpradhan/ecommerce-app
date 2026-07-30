package com.ecommerce;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<style>");
        out.println("body { font-family: Arial; background:#f4f4f4; }");
        out.println(".container { display:flex; gap:20px; padding:20px; }");
        out.println(".card { background:white; padding:15px; border-radius:10px; box-shadow:0 0 10px gray; width:200px; text-align:center; }");
        out.println("img { width:150px; height:150px; }");
        out.println("</style>");
        out.println("</head>");

        out.println("<body>");
        out.println("<h1 style='text-align:center;'>🛒 Product List</h1>");
        out.println("<div class='container'>");

        // Product 1
        out.println("<div class='card'>");
        out.println("<img src='https://via.placeholder.com/150'/>");
        out.println("<h3>iPhone</h3>");
        out.println("<p>₹70,000</p>");
        out.println("</div>");

        // Product 2
        out.println("<div class='card'>");
        out.println("<img src='https://via.placeholder.com/150'/>");
        out.println("<h3>Laptop</h3>");
        out.println("<p>₹50,000</p>");
        out.println("</div>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
