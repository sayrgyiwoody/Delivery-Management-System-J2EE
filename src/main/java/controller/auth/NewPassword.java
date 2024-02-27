package controller.auth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;



@WebServlet("/views/newPassword")
public class NewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("newPassword.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String newPassword = request.getParameter("password");
		String confirmPass = request.getParameter("confirm_password");
		String hashPassword = Hash.hashPassword(newPassword);
		
		RequestDispatcher dispatcher = null;
		
		if (newPassword != null && confirmPass != null && newPassword.equals(confirmPass)) {
			String email = (String) session.getAttribute("email");
			System.out.println("opt email : "+ email);
			UserDAO userDAO = new UserDAO();
			boolean passwordUpdated = userDAO.updatePassword(email,hashPassword);
			
			if(passwordUpdated) {
				session.removeAttribute("otpVerified");
				session.removeAttribute("email");
				request.setAttribute("status", "passwordChanged");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}else {
				request.setAttribute("status", "fail");
				dispatcher = request.getRequestDispatcher("newPassword.jsp");
				dispatcher.forward(request, response);
			}
		}else {
			request.setAttribute("status", "password not match");
			dispatcher = request.getRequestDispatcher("newPassword.jsp");
			dispatcher.forward(request, response);
		}
	}
}