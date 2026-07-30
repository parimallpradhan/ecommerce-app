package com.ecommerce;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int index = Integer.parseInt(request.getParameter("index"));

        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart != null && index < cart.size()) {
            cart.remove(index);
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("cart");
    }
}
