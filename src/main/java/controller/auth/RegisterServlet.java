package controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;


@WebServlet("/views/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userDAO = null;

    public RegisterServlet() {
	userDAO = new UserDAO();
    }
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	
    	
		String name = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String role = request.getParameter("role");
		String password = request.getParameter("password");
		String confirm_password = request.getParameter("confirm_password");
		String hashed_password = Hash.hashPassword(password);
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		
		if(name == null || name.equals("")) {
			request.setAttribute("status","invalidName");
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
		}
		if(email == null || email.equals("")) {
			request.setAttribute("status","invalidEmail");
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
		}
		if(role == null || role.equals("")) {
			request.setAttribute("status","invalidRole");
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
		}
		if(password == null || password.equals("")) {
			request.setAttribute("status", "invalidPassword");
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
		}else if(password.length()<=5) {
			request.setAttribute("status", "invalidLength");
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request,response);
		}else if(!password.equals(confirm_password)){
			request.setAttribute("status", "pswNotMatch");
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request,response);
		}
		
		boolean emailExist = userDAO.checkEmail(email);
		if(emailExist) {
			request.setAttribute("status", "emailExist");
			dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request,response);
		
			return;
		}
		
		try {
			
			User newUser = new User();
	    	newUser.setName(name);
	    	newUser.setEmail(email);
	    	if(role.equals("admin")) {
	    		role = "shop";
	    	}
	    	newUser.setRole(role);
	    	newUser.setPhone(phone);
	    	
	    	newUser.setHashed_password(hashed_password);
	    	
	    	User user = userDAO.createUser(newUser);
	    	
	    	session.setAttribute("user", user);
	    	
	    	
	    	if(role.equals("shop")) {
	    		session.setAttribute("role", "shop");
				response.sendRedirect("/DMS/views/shop/home");
	    	}else if(role.equals("driver")) {
	    		session.setAttribute("role", "driver");
				response.sendRedirect("/DMS/views/driver/home");
	    	}
			
			

		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
}
