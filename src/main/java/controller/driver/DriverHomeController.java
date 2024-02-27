package controller.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dao.OrderDAO;
import dao.UserDAO;
import model.Order;
import model.User;
import util.EmailUtility;


@WebServlet("/views/driver/home")
public class DriverHomeController extends HttpServlet {

    RequestDispatcher dispatcher = null;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String searchKey = request.getParameter("searchKey");
    	
//    	for order status filter
    	String filterStatus = request.getParameter("filterStatus");
    	String township = request.getParameter("township");
    	
    	
    	
    	if(filterStatus == null) {
    		filterStatus = "";
    	}
    	
    	if(township == null) {
    		township = "";
    	}
    	
    	 HttpSession session = request.getSession(false);
         Object userObj = session.getAttribute("user");
         User user = (User) userObj;
         OrderDAO orderDAO = new OrderDAO();
         
         int driver_id = user.getId();
         
         

         if(user != null) {
        	 if (searchKey != null) {
         	    List<Order> orderList = orderDAO.getDriverOrder(driver_id,searchKey, filterStatus , township);
         	    request.setAttribute("searchKey", searchKey);
         	    request.setAttribute("orderList", orderList);
         	} else {
         		
         	    List<Order> orderList = orderDAO.getDriverOrder(driver_id,"", filterStatus , township);
         	    request.setAttribute("orderList", orderList);
         	}
          
          	if(!filterStatus.isEmpty()) {
          		request.setAttribute("filterStatus", filterStatus);
          	}else {
          		request.setAttribute("filterStatus", "all");
          	}
          	
          	if(!township.isEmpty()) {
          		request.setAttribute("township", township);
          	}else {
          		request.setAttribute("township", "all");
          	}
         }
         
         UserDAO userDAO = new UserDAO();
         
         List<User> driverList = userDAO.get("driver","");
     	
     		request.setAttribute("driverList", driverList);
         	

        	// Forward the request to the appropriate JSP page
        	RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        	dispatcher.forward(request, response);
    	
    		
    	
    	
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String orderId = request.getParameter("orderId");
    	String orderStatus = request.getParameter("orderStatus");
    	
    	
    	
    	
    	String host = "localhost";

		// Get the current port (assuming it's a web application)
		int port = request.getServerPort();
    	
    	if(orderId != null && orderStatus != null) {
    		OrderDAO orderDAO = new OrderDAO();
        	boolean status =  orderDAO.changeStatus(orderId, orderStatus);
        	
        	User shop = orderDAO.getShop(Integer.parseInt(orderId));
        	
        	if(status) {
        		request.setAttribute("status", "success");
        		
        		
        		String htmlContent = "<html><body>"
                        + "<h2>Order Status Update</h2>"
                        + "<p>Hello, "+ shop.getName() + " Your order is now " + orderStatus + "</p>"
                        + "<p>Order ID: " + orderId + "</p>"
                        + "</body></html>";
                
                
                //send email to each order owner shops
                try {
        			EmailUtility.sendEmail(shop.getEmail(), "Update info about your order", htmlContent);
        		} catch (AddressException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (MessagingException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}else {
        		request.setAttribute("status", "fail");
        	}
    	}else {
    		request.setAttribute("status", "require info");
    	}
    	
    	
    	
    	doGet(request,response);
    	
    	
    }

    }