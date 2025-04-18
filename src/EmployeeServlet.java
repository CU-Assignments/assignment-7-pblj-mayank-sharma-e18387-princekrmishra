import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class EmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");

        // Connect to the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "password")) {
            if (employeeId != null && !employeeId.isEmpty()) {
                // Search employee by ID
                String sql = "SELECT * FROM employees WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, Integer.parseInt(employeeId));
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        response.getWriter().println("Employee Found: " + rs.getString("name") + " - " + rs.getString("department"));
                    }
                }
            } else {
                // Display all employees
                String sql = "SELECT * FROM employees";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        response.getWriter().println("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name") + " | Department: " + rs.getString("department"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
