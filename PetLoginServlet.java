
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/login")  
public class PetLoginServlet extends HttpServlet {

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
            String username = request.getParameter("cust_name");
            String password = request.getParameter("cust_pass");
         // Step 3: Execute a SQL SELECT query

        String sqlStr = "SELECT * FROM user WHERE username ='" + request.getParameter("cust_name") + "' AND password ='"
       + request.getParameter("cust_pass") + "'"; 
      

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

 
      out.println("</body></html>");
      out.close();
   }
}  

