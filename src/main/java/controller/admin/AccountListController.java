package controller.admin;

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

@WebServlet("/views/admin/accountList")
public class AccountListController extends HttpServlet {

    RequestDispatcher dispatcher = null;
    UserDAO userDAO = null;

    public AccountListController() {
	userDAO = new UserDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String role = request.getParameter("role");
    	String searchKey = request.getParameter("searchKey");
    	
    	if(searchKey == null) {
    		searchKey = "";
    	}
    	
    	List<User> accountList = userDAO.get(role,searchKey);
    	
    	request.setAttribute("accountList", accountList);
    	request.setAttribute("role", role);
    	request.setAttribute("searchKey", searchKey);
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("accountList.jsp");
	    dispatcher.forward(request, response);
    	
    }
    
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String action = request.getParameter("action");
	if(action.equals("delete")) {
		String account_id = request.getParameter("acc_id");
		String account_role = request.getParameter("acc_role");
		
		boolean status = userDAO.delete(account_id);
		if(status) {
	    	request.setAttribute("status", "deleteSuccess");
	    }else {
	    	request.setAttribute("status", "fail");
	    }
		
		response.sendRedirect("/DMS/views/admin/accountList?role="+account_role);
		
	}
	
	
	
	
	}
    
}