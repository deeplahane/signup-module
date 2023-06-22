package com.dev.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispatcher=null;
		Connection con = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			con= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?autoReconnect=true&useSSL=false","root","Deeplahane2001#1");
			PreparedStatement pst = (PreparedStatement) con.prepareStatement("INSERT INTO users(uname,uemail,upwd,umobile) VALUES(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, uemail);
			pst.setString(3, upwd);
			pst.setString(4, umobile);
			
			int rowCount = pst.executeUpdate();
			dispatcher =request.getRequestDispatcher("registration.jsp");
			
			if(rowCount>0) {
				request.setAttribute("status","success");
			}else {
				request.setAttribute("status","failed");
			}
			dispatcher.forward(request,response);
		}catch(Exception e) {
		e.printStackTrace();	
		}
	}

}
