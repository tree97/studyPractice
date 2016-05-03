package by.bsu.fpmi.up;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/chat/login.html")
public class EnvVarsServlet extends HttpServlet {

    private static final String PARAM_USERNAME = "user";
    private static final String PARAM_PASSWORD = "pass";

    private boolean isCorrect(HttpServletResponse resp, String login, String password) throws ServletException, IOException {
        if(login.equals("Vadim") && password.equals("1234")) {
            return true;
        } else {
            resp.getOutputStream().println("Incorrect login or password, try again ( login: Vadim, password: 1234 )");
            resp.sendRedirect("localhost:8080");
            return false;
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(PARAM_USERNAME);
        if (username == null || username.trim().isEmpty()) {
            resp.getOutputStream().println(String.format("paramater %s is required", PARAM_USERNAME));
            resp.sendRedirect("localhost:8080");
            return;
        }

        String password = req.getParameter(PARAM_PASSWORD);
        if (password == null || password.trim().isEmpty()) {
            resp.getOutputStream().println(String.format("paramater %s is required", PARAM_PASSWORD));
            resp.sendRedirect("localhost:8080");
            return;
        }

        if( isCorrect(resp, username, password) ) {
            resp.sendRedirect("/chat/homepage.html");
            return;
        }
    }
}
