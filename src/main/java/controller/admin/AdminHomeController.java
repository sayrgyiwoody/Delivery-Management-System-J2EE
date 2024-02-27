package controller.admin;

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


@WebServlet("/views/admin/home")
public class AdminHomeController extends HttpServlet {

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
         
         

         if(user != null) {
        	 if (searchKey != null) {
         	    List<Order> orderList = orderDAO.get(searchKey, filterStatus , township);
         	    request.setAttribute("searchKey", searchKey);
         	    request.setAttribute("orderList", orderList);
         	} else {
         		
         	    List<Order> orderList = orderDAO.get("", filterStatus , township);
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
        // Read the data from the request body
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        
        // Convert the JSON data to a Java object   
        String jsonData = stringBuilder.toString();
        
        // Parse JSON using Gson
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        
        // Access the 'driverId' property as string
        int driverId = jsonObject.get("driverId").getAsInt();
        JsonArray selectedItemsArray = jsonObject.getAsJsonArray("selectedItems");
       

       
        OrderDAO orderDAO = new OrderDAO();
        
        
        
        boolean success = true; // Assume success initially
        
        String host = "localhost";

		// Get the current port (assuming it's a web application)
		int port = request.getServerPort(); // Assuming you have access to the request object

		
		// Construct the base URL
		
		
		StringBuilder ordersStringBuilder = new StringBuilder();	
		
        // Assign driver to each selected order item
        for (int i = 0; i < selectedItemsArray.size(); i++) {
            
        	int orderId = selectedItemsArray.get(i).getAsInt();
        	
        	ordersStringBuilder.append(orderId).append(" , ");
        	// Assuming you have a method like assignDriverToOrderItem that assigns the driver to the order item
            if (!orderDAO.assignDriver(orderId,driverId)) {
                // If assigning driver to order item fails, set success to false
                success = false;
                break; // Break the loop if any assignment fails
            }
        	
        	
        }
        
        String ordersString = ordersStringBuilder.toString();
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        if (success) {
            out.write("ordered");
        } else {
            out.write("fail");
        }
        out.flush();
        
        UserDAO userDAO = new UserDAO();
        User driver = userDAO.get(driverId);
        
        String driver_email = driver.getEmail();
        
//        selectedItemsArray
        
        String htmlContent = "<html><body>"
                + "<h2>Order received</h2>"
                + "<p>Hello you received orders to deliver.</p>"
                + "<p>Order ID: " + ordersString + "</p>"
                + "</body></html>";
        
        
        //send email to each order owner shops
        try {
			EmailUtility.sendEmail(driver_email, "Order received to deliver", htmlContent);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
    }

    }