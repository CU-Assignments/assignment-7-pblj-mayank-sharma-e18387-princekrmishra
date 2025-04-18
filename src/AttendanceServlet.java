import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "password")) {
            String sql = "INSERT INTO attendance (studentId, date, status) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(studentId));
                stmt.setString(2, date);
                stmt.setString(3, status);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    response.sendRedirect("attendance-success.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
