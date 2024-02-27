package controller.shop;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;

import com.google.gson.JsonArray;

import model.Customer;
import model.Order;
import model.OrderItem;
import model.User;

import javax.servlet.RequestDispatcher;

import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/views/shop/order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        Object userObj = session.getAttribute("user");
        User user = (User) userObj;
        
        OrderDAO orderDAO = new OrderDAO();
        
        int user_id = user.getId();
        
		String action = request.getParameter("action");
		
		
    	if(action == null) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("createOrder.jsp");
    	    dispatcher.forward(request, response);
    	}
    	
    	if(action.equals("view")) {
    		String order_id = request.getParameter("orderId");
    		
        	boolean userExist = orderDAO.checkUser(user_id, Integer.parseInt(order_id));
        	
        	if(userExist) {
        		getOrderDetail(request,response);
        	}else {
        		response.sendRedirect("/DMS/views/shop/home");
        	}
    	}else if(action.equals("cancel")) {
    		String order_id = request.getParameter("orderId");
    		
    		boolean userExist = orderDAO.checkUser(user_id, Integer.parseInt(order_id));
    		if(userExist) {
    			
        		
                boolean cancelSuccess = orderDAO.changeStatus(order_id, "canceled");
        		
                response.sendRedirect("home");
        	}else {
        		response.sendRedirect("/DMS/views/shop/home");
        	}
    		
            
    		
    	}	
    	
    	
		
		
	}
	
	
	private void getOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String order_id = request.getParameter("orderId");

		OrderItemDAO orderItemDAO = new OrderItemDAO();
        List<OrderItem> orderItems = orderItemDAO.getOrderItems(order_id);
        
        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.get(order_id);
        int orderIdInt = Integer.parseInt(order_id);
        order.setId(orderIdInt);
        
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.get(order.getCustomer_id());
        
        request.setAttribute("customer", customer);
        request.setAttribute("order", order);
        request.setAttribute("orderItems", orderItems);
        
		RequestDispatcher dispatcher = request.getRequestDispatcher("orderDetail.jsp");
    	dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
        
        // Access the 'customer' property
        JsonObject customerObject = jsonObject.getAsJsonObject("customer");
        JsonArray orderItemArray = jsonObject.getAsJsonArray("orderItems");
        
      
        if(!customerObject.get("cusName").getAsString().isEmpty() && orderItemArray.size() > 0){
        	Customer customer = new Customer();
            customer.setCustomer_name(customerObject.get("cusName").getAsString());
            customer.setCustomer_phone(customerObject.get("cusPhone").getAsString());
            customer.setCity(customerObject.get("city").getAsString());
            customer.setTownship(customerObject.get("township").getAsString());
            customer.setDetail_address(customerObject.get("detailAddress").getAsString());
            
            CustomerDAO customerDAO = new CustomerDAO();
            int customer_id = customerDAO.add(customer);
            
            
            
            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            
            
            
            HttpSession session = request.getSession(false);
            Object userObj = session.getAttribute("user");
            User user = (User) userObj;
            
            
            
            Order order = new Order();
            order.setUser_id(user.getId());
            order.setCustomer_id(customer_id);
            order.setOrder_status("requesting");
            
            int order_id = orderDAO.add(order);
            
            boolean success = false;
            
            // Iterate over the orderItemArray
               for (int i = 0; i < orderItemArray.size(); i++) {
                   JsonObject orderItemObject = orderItemArray.get(i).getAsJsonObject();
                   // Parse order_item data from JSON and create an OrderItem object
                   OrderItem orderItem = new OrderItem();
                   
                   orderItem.setOrder_id(order_id);
                   orderItem.setProduct_name(orderItemObject.get("productName").getAsString());
                   orderItem.setPrice(orderItemObject.get("productPrice").getAsInt());
                   orderItem.setOrder_count(orderItemObject.get("orderCount").getAsInt());
                   
//                    Save the OrderItem to the database using the DAO
                   success = orderItemDAO.add(orderItem);
                   
               }
               
               response.setContentType("text/plain");
               response.setCharacterEncoding("UTF-8");
               
               if (success) {
                   response.getWriter().write("ordered");
               } else {
                   response.getWriter().write("fail");
               }
        }else {
        	response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("fail");
        }
          
        
        
        
 
	}

}
