package controller.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/views/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get existing session, don't create new if it doesn't exist
        if (session != null) {
            session.invalidate(); // Invalidate the session, removes all session attributes
        }
        // Redirect to login page or any other page after logout
        response.sendRedirect("login.jsp"); // Replace "login.jsp" with your actual login page URL
    }

}
