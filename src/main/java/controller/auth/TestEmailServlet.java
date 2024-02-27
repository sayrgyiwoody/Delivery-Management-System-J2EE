package controller.auth;

import java.io.IOException;
import java.net.InetAddress;
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

import model.Otp;
import util.EmailUtility;

/**
 * Servlet implementation class TestEmailServlet
 */
@WebServlet("/TestEmailServlet")
public class TestEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = "waiyanwoody@gmail.com";
		String order_id = "3";
		
		String to = email;
		
		String host = "localhost";

		// Get the current port (assuming it's a web application)
		int port = request.getServerPort(); // Assuming you have access to the request object

		
		// Construct the base URL
		String baseURL = "http://" + host + ":" + port + "/DMS/views/shop/order?action=view&orderId="+order_id;
		
		String htmlContent = "<html><body>"
                + "<h2>Driver Assigned to Order</h2>"
                + "<p>Order ID: " + "1" + "</p>"
                + "<p>Driver Name: " + "aung ko" + "</p>"
                + "<p>Driver Phone Number: " + "09950214146" + "</p>"
                + "<p><a href='" + baseURL + "'>click here to view order</a></p>"
                + "</body></html>";
		
		try {
			EmailUtility.sendEmail(email, "Update about your order", htmlContent);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		response.getWriter().append("Mail send to: "+ email).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("driver_name");
		
		
		RequestDispatcher dispatcher = null;
		
		
			String to = email;
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("pkkoo239@gmail.com", "dtpy ecgm ygkh wpog");
				}
			});
			
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("Hello");
				String htmlContent = "<html><body>"
	                    + "<h2>Driver Assigned to Order</h2>"
	                    + "<p>Driver: " + "aung ko" + "</p>"
	                    + "<p>Order Number: " + "1" + "</p>"
	                    + "</body></html>";
				message.setContent(htmlContent, "text/html");
				Transport.send(message);
				
			}

			catch (MessagingException e) {
				throw new RuntimeException(e);
			}
			dispatcher = request.getRequestDispatcher("test.jsp");
			 
			dispatcher.forward(request, response);	
					
	}

}
