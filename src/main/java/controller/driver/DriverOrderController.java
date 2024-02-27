package controller.driver;
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
@WebServlet("/views/driver/order")
public class DriverOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriverOrderController() {
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        }
        if(action.equals("view")) {
        	String order_id = request.getParameter("orderId");
        	boolean userExist = orderDAO.checkUser(user_id, Integer.parseInt(order_id));
        	if(userExist) {
        		getOrderDetail(request,response);
        	}else {
        		response.sendRedirect("/DMS/views/driver/home");
        	}
            
        }else if(action.equals("delete")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
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
    }
}