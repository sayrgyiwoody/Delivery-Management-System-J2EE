package controller.admin;

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
@WebServlet("/views/admin/order")
public class AdminOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminOrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		

		OrderDAO orderDAO = new OrderDAO();
		
    	if(action == null) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
    	    dispatcher.forward(request, response);
    	}
    	
    	if(action.equals("view")) {
    		getOrderDetail(request,response);
    		
    	}else if(action.equals("delete")) {
    		String order_id = request.getParameter("orderId");
        	
    		boolean deleteSuccess = orderDAO.delete(order_id);
    		
            if(deleteSuccess) {
            	response.sendRedirect("home");
            	
            }else {
            	request.setAttribute("status", "fail");
            	RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        	    dispatcher.forward(request, response);
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
        
        JsonArray orderArray = jsonObject.getAsJsonArray("selectedItems");
        
        OrderDAO orderDAO = new OrderDAO();
        
        boolean success = false;
        
            // Iterate over the orderItemArray
               for (int i = 0; i < orderArray.size(); i++) {
            	   String orderString = orderArray.get(i).getAsString();
            	   System.out.print(orderString);
            	   
            	   success = orderDAO.delete(orderString);
                   
                   
               }
               
               response.setContentType("text/plain");
               response.setCharacterEncoding("UTF-8");
               
               if (success) {
                   response.getWriter().write("success");
               } else {
                   response.getWriter().write("fail");
               }
        
          
        
        
        
 
	}

}
