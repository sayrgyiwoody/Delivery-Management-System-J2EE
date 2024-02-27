package controller.driver;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

@WebServlet("/views/driver/account")
public class DriverAccountServlet extends HttpServlet {

    RequestDispatcher dispatcher = null;
    UserDAO userDAO = null;

    public DriverAccountServlet() {
	userDAO = new UserDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
	    dispatcher.forward(request, response);
    	
    }
    
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
	HttpSession session = request.getSession();

    String id = request.getParameter("id");
    User user = new User();
    user.setName(request.getParameter("name"));
    user.setEmail(request.getParameter("email"));
    user.setPhone(request.getParameter("phone"));
    user.setAddress(request.getParameter("address"));
    
    try {
        user.setId(Integer.parseInt(id));
        if (userDAO.update(user)) {
        	
        	user.setRole(userDAO.getRole(user.getEmail()));
            request.setAttribute("status", "updated");
            session.setAttribute("user", user);
            
        } else {
            request.setAttribute("status", "failed");
        }
    } catch (NumberFormatException e) {
        request.setAttribute("status", "failed");
        // Log or handle the exception appropriately
    }

    
    dispatcher.forward(request, response);
   
   	    	
    }

    
}