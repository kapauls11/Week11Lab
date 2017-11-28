/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.AccountService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String path = getServletContext().getRealPath("/WEB-INF");

        AccountService as = new AccountService();
        if (action != null) {
            if (action.equals("forgot")) {
                boolean forgotPassword = false;
                try {
                    forgotPassword = as.forgotPassword(email, path);
                } catch (Exception ex) {
                    Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (forgotPassword == true) {
                    response.sendRedirect("login");
                    session.setAttribute("errormessage", "Password has been successfully sent to your email");
                }
            }
        }
    }
}
