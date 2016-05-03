package by.bsu.fpmi.up;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Scanner;

@WebServlet(value = "/chat/login.html")
public class EnvVarsServlet extends HttpServlet {

    private static final String PARAM_USERNAME = "user";
    private static final String PARAM_PASSWORD = "pass";

    private String encrypt(String s)
    {
        long h=0;
        for(int i=0;i<s.length();i++)
        {
            h*=13;
            h+=s.charAt(i);
            h%=171717171717379L;
        }
        return ((Long)h).toString();
    }

    private boolean isCorrect(HttpServletResponse resp, String login, String password) throws ServletException, IOException {
        if(login.equals("Vadim") && encrypt(password).equals("116818") ) {
            return true;
        }
        if(login.equals("Tonya") && encrypt(password).equals("126338") ) {
            return true;
        }
        if(login.equals("Egor") && encrypt(password).equals("507592871") ) {
            return true;
        }
        if(login.equals("Vlad") && encrypt(password).equals("3212008") ) {
            return true;
        }
        return false;
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
        } else {
            resp.sendRedirect("localhost:8080");
            return;
        }
    }
}
