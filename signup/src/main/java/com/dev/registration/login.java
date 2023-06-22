package com.dev.registration;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail = request.getParameter("username");
		String pwd = request.getParameter("password");
		HttpSession session = request.getSession();
		Connection con=null;
		RequestDispatcher dispatcher= null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?autoReconnect=true&useSSL=false","root","Deeplahane2001#1");
			PreparedStatement pst = (PreparedStatement) con.prepareStatement("SELECT * FROM users WHERE uemail=? AND upwd=? ");
			pst.setString(1, uemail);
			pst.setString(2, pwd);
			
			ResultSet rs=pst.executeQuery();
			
			if(rs.next()) {
				session.setAttribute("name",rs.getString("uname"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			}else {
				request.setAttribute("status","failed");
				dispatcher= request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
			
		}catch(Exception e) {
			e.getStackTrace();
		}		
	}

}
