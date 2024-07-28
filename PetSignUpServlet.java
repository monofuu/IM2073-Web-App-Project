
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;           
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/signup")   
public class PetSignUpServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/petdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         Statement stmt = conn.createStatement();
         ) {


        String sqlStr = "INSERT INTO user VALUES('" + request.getParameter("cust_name") + "',"
        + "'" + request.getParameter("cust_email") + "'," + "'" + request.getParameter("cust_pass") + "')";  
      

      out.println("<h3>Thank you for your query.</h3>");
      out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
      int rset = stmt.executeUpdate(sqlStr);  // Send the query to the server

        response.sendRedirect("signupsuccess.html");  
    } 

        catch(Exception ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            ex.printStackTrace();
        } 
 
      out.println("</body></html>");
      out.close();
   }
}  

