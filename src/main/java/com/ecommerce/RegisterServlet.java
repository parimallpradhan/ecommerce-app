package com.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ps.executeUpdate();

            response.sendRedirect("login.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Registration failed ❌");
        }
    }
}
