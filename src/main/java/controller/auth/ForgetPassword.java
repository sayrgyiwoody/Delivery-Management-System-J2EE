package controller.auth;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dao.OtpDAO;
import model.Otp;
import util.EmailUtility;

@WebServlet("/views/forgetPassword")
public class ForgetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userDAO = null;
	OtpDAO otpDAO =null;
	int otp ;
	public ForgetPassword() {
		userDAO = new UserDAO();
		otpDAO = new OtpDAO();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("forgetPassword.jsp");
	    dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String email = request.getParameter("email");
		Boolean emailExit = userDAO.checkEmail(email);
		
		
		
		HttpSession mySession = request.getSession();

		if(emailExit) {
			
			Random rand = new Random();								 
			long currentTime = System.currentTimeMillis();

			int otp = 100000 + rand.nextInt(900000);
			
			
			String to = email;
			
			String host = "localhost";

			// Get the current port (assuming it's a web application)
			int port = request.getServerPort(); // Assuming you have access to the request object

			
			// Construct the base URL
			String baseURL = "http://" + host + ":" + port + "/DMS/views/validateOtp";
			
			
			String htmlContent = "<html><body>"
	                + "<h2>OTP for Reset Password</h2>"
	                + "<p>OTP : " + otp + "</p>"
	                + "<p>for your email : " + email + "</p>"
	                + "<p>You can also <a href='" + baseURL + "'>click here</a> to fill OTP.</p>"
	                + "</body></html>";
			
			
			try {
				EmailUtility.sendEmail(email, "OTP for reset password", htmlContent);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("otp.jsp");
			request.setAttribute("message","OTP is sent to your email id");

			mySession.setAttribute("email",email); 
			dispatcher.forward(request, response);	
			
				Otp otpUser = otpDAO.getByEmail(email);
				if (otpUser != null) {
					otpUser.setOtp(otp);
					otpDAO.update(otpUser);
		    	}else {
		    		otpUser = new Otp();
		    	    otpUser.setEmail(email);
		    	    otpUser.setOtp(otp);
		    	    otpDAO.createUser(otpUser);
		    	}
		
		}else {			
				
				request.setAttribute("status", "emailNotExit");
				RequestDispatcher dispatcher = request.getRequestDispatcher("forgetPassword.jsp");
				dispatcher.forward(request, response);
		}

	}

	}