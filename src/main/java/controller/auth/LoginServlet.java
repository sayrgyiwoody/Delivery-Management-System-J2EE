package controller.auth;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;


@WebServlet("/views/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserDAO userDAO = null;

    public LoginServlet() {
	userDAO = new UserDAO();
    }
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		 HttpSession session = request.getSession();
		 
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		session.setAttribute("email", email);
		if(email == null || email.equals("")) {
			request.setAttribute("status", "invalidEmail");
			dispatcher.forward(request, response);
		}
		if(password == null || password.equals("")) { 
			request.setAttribute("status", "invalidPassword");	
			dispatcher.forward(request, response);
		}
		
		
		try {

			boolean isValid = userDAO.validateUser(email,password);
			
			if(isValid) {
				
				User user = userDAO.getUser(email);
				session.setAttribute("user", user);
				if(user.getRole().equals("shop")) {
					
					response.sendRedirect("/DMS/views/shop/home");
				}else if(user.getRole().equals("admin")) {
					
					response.sendRedirect("/DMS/views/admin/home");
				}else if(user.getRole().equals("driver")) {
					
					response.sendRedirect("/DMS/views/driver/home");
				}
				
			}else {
				//login fail
				dispatcher = request.getRequestDispatcher("login.jsp");
				request.setAttribute("status", "fail");	
				dispatcher.forward(request,response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
