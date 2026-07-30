package com.ecommerce;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    List<Product> products = List.of(
        new Product(1, "iPhone", 80000),
        new Product(2, "Laptop", 60000)
    );

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        for (Product p : products) {
            if (p.getId() == id) {
                cart.add(p);
                break;
            }
        }

        session.setAttribute("cart", cart);

        response.sendRedirect("cart");
    }
}
