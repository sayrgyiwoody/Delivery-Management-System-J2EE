package controller.shop;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.auth.Hash;
import dao.UserDAO;
import model.User;


@WebServlet("/views/shop/changePassword")
public class ShopChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userDAO = null;
	
    public ShopChangePassword() {
    	userDAO = new UserDAO();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("changePassword.jsp");
	    dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oldPass = request.getParameter("oldPassword");
		String newPass = request.getParameter("newPassword");
		String confirmPass = request.getParameter("confirmPassword");
		String hashed_password = Hash.hashPassword(newPass);
		System.out.print(oldPass);
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String email = user.getEmail();
		boolean isValid = userDAO.validateUser(email,oldPass);
		System.out.print("Valid: "+isValid);

		if(isValid) {
			if (newPass != null && confirmPass != null && newPass.equals(confirmPass)&& email != null) {
				UserDAO userDAO = new UserDAO();
				boolean passwordChange = false;
				
				passwordChange = userDAO.updatePassword(email,hashed_password);

				if(passwordChange) {
					request.setAttribute("status", "success");
					dispatcher = request.getRequestDispatcher("changePassword.jsp");
					dispatcher.forward(request, response);
				}else {
					request.setAttribute("status", "fail");
					dispatcher = request.getRequestDispatcher("changePassword.jsp");
					dispatcher.forward(request, response);
				}
			}else {
				request.setAttribute("status", "passwordNotMatch");
				dispatcher = request.getRequestDispatcher("changePassword.jsp");
				dispatcher.forward(request, response);
			}
		}else {
			request.setAttribute("status", "wrongOldPass");
			dispatcher = request.getRequestDispatcher("changePassword.jsp");	
		}
		dispatcher.forward(request, response);
	}

}