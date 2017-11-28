package servlets;

import businesslogic.AccountService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("logout") != null) {
            HttpSession session = request.getSession();
            session.invalidate();
            request.setAttribute("errorMessage", "logged out");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMessage", "Please enter a username and password");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp/").forward(request, response);
        }

        AccountService as = new AccountService();

        String path = getServletContext().getRealPath("/WEB-INF");

        if (as.checkLogin(username, password, path) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("home");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password entered");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp/").forward(request, response);
        }
    }
}
