// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/login")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class PetLoginServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/petdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "1234");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
         ) {
            String username = request.getParameter("cust_name");
            String password = request.getParameter("cust_pass");
         // Step 3: Execute a SQL SELECT query

        String sqlStr = "SELECT * FROM user WHERE username ='" + request.getParameter("cust_name") + "' AND password ='"
       + request.getParameter("cust_pass") + "'";  // Single-quote SQL string
      

      out.println("<h3>Thank you for your query.</h3>");
      out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
      ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server


      if (rset.next()){
         response.sendRedirect("petquery.html");   
      } else {
         response.sendRedirect("errorlogin.html");
      }
        }

        catch(Exception ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            ex.printStackTrace();
        } 

      // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}  

