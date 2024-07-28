
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/adoptionqueryresults")   
public class OrderFormServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>Query Response</title></head>");
      out.println("<body>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/petdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
         String sqlStr = "select * from pets where type = "
               + "'" + request.getParameterValues("type") + "'"   // Single-quote SQL string
               + " and qty > 0 order by price desc";

         out.println("<h3>Thank you for your query.</h3>");
         out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Step 4: Process the query result set
         out.println("<form method='get' action='petadopt'>");
         
         while(rset.next()) {
            out.println("<p><input type='checkbox' name='id' value="
                  + "'" + rset.getString("id") + "' />"
                  + rset.getString("name") + ", "
                  + rset.getString("type") + ", $"
                  + rset.getString("price") + "</p>");
         }
 
         // Print the submit button and </form> end-tag
         out.println("<p><input type='submit' value='Adopt Now!' />");
         out.println("</form>");

      } catch(SQLException ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      } 
 
      out.println("</body></html>");
      out.close();
   }
}
