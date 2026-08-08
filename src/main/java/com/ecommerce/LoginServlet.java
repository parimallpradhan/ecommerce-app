package com.yourpackage;  // 👈 IMPORTANT: match your existing package

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        // Fake login (no DB)
        if ("admin".equals(user) && "admin123".equals(pass)) {
            req.getSession().setAttribute("user", user);
            res.sendRedirect("index.jsp"); // or home page
        } else {
            res.getWriter().println("Invalid login ❌");
        }
    }
}
