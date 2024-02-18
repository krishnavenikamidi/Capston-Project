package com.task.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
	private static final String query = "SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		
		//set content type
		res.setContentType("text/html");
		pw.println("<!DOCTYPE html>");
        pw.println("<html lang=\"en\">");
        pw.println("<head>");
        pw.println("<meta charset=\"UTF-8\">");
        pw.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        pw.println("<style>");
        pw.println("body {");
        pw.println("    margin: 0;");
        pw.println("    padding: 0;");
        pw.println("    background-image: url('https://c1.wallpaperflare.com/preview/932/467/825/books-education-school-literature.jpg');");
        pw.println("    background-size: cover;");
        pw.println("    background-repeat: no-repeat;");
        pw.println("    background-attachment: fixed;");
        pw.println("    height: 200vh;");
        pw.println("}");
        pw.println("</style>");
        pw.println("<title>Your Book List</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1>Welcome to Your Book List</h1>");
        // Add your book list content here
        pw.println("</body>");
        pw.println("</html>");
    
		//LOAD jdbc driver
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		//generate the connection
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","root");
				PreparedStatement ps = con.prepareStatement(query);){
			ResultSet rs = ps.executeQuery();
			pw.println("<table border='1' align='center'>");
			pw.println("<tr>");
			pw.println("   <th>   Book Id   </th>   ");
			pw.println("<th>Book Name</th>");
			pw.println("<th>Book Edition</th>");
			pw.println("<th>Book Price</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");
			while(rs.next()) {
				pw.println("<tr>");
				pw.println("<td>"+rs.getInt(1)+"</td>");
				pw.println("<td>"+rs.getString(2)+"</td>");
				pw.println("<td>"+rs.getString(3)+"</td>");
				pw.println("<td>"+rs.getFloat(4)+"</td>");
				pw.println("<td><a href='editScreen?id="+rs.getInt(1)+"'>Edit</a></td>");
				pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");

				pw.println("</tr>");
			}
			pw.println("</table>");
		}catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h1>"+se.getMessage()+"</h2>");
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h2>");
		}
		pw.println("<a href='home.html'>Home</a>");
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}


}
