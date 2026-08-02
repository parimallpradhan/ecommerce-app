package com.ecommerce;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String product = req.getParameter("product");

        HttpSession session = req.getSession();

        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(product);

        session.setAttribute("cart", cart);

        res.sendRedirect("cart");
    }
}
