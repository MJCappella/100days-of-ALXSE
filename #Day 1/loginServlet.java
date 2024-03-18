package com.laundry.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class loginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/arclaundrymanagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mjay0001";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean isAuthenticated = authenticateUser(email, password);

        if (isAuthenticated) {
            // Authentication successful
            response.sendRedirect("about.html"); // Redirect to home page
        } else {
            // Authentication failed
            response.sendRedirect("index.html?error=true"); // Redirect to login page with error parameter
        }
    }
    

    private boolean authenticateUser(String email, String password) {
        String sql = "SELECT * FROM logindata WHERE email=? AND password=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If there's a row, authentication is successful
            }
        } catch (SQLException e) {
            return false; // Authentication failed due to database error
        }
    }
}