package controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Otp;
import dao.OtpDAO;

@WebServlet("/views/validateOtp")
public class OtpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Otp newOtp = null;
	OtpDAO otpDAO = null;
	
	public OtpServlet() {
		otpDAO = new OtpDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("otp.jsp");
	    dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession mySession = request.getSession();
		
		int value=Integer.parseInt( request.getParameter("otp"));
		
		
		String email = (String) mySession.getAttribute("email");
		
		Boolean checkOtp = otpDAO.checkOTP(email, value);
		
	
		RequestDispatcher dispatcher=null;

		
		if (checkOtp){
			
			
			mySession.setAttribute("otpVerified",true);
			dispatcher=request.getRequestDispatcher("newPassword.jsp");
			dispatcher.forward(request, response);						
			
		}else{
			
			request.setAttribute("status","wrong");
			
		    dispatcher=request.getRequestDispatcher("otp.jsp");
			dispatcher.forward(request, response);
		}
		
	}
}