package by.bsu.fpmi.up;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        if(req.getQueryString().equals("token=empty_login"))
            printWriter.println("empty login");
        if(req.getQueryString().equals("token=empty_password"))
            printWriter.println("empty password");
        if(req.getQueryString().equals("token=wrong"))
            printWriter.println("wrong password or login");
        if(req.getQueryString().equals("token=logout"))
            printWriter.println("Goodbye. Please, login for continue chatting");
        printWriter.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/html\">\n" +
                "<head>\n" +
                "    <title>Login page</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div>\n" +
                "        <h3>Please login</h3>\n" +
                "    </div>\n" +
                "    <div>\n" +
                "        <form action=\"/chat/login.html\" method=\"post\">\n" +
                "            <input name=\"user\"> Username </input>\n" +
                "            <br>\n" +
                "            <input type=\"password\" name=\"pass\"> Password </input>\n" +
                "            <br>\n" +
                "            <button type=”submit”>Submit</button>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(PARAM_USERNAME);
        if (username == null || username.trim().isEmpty()) {
            resp.sendRedirect("/chat/login.html?token=empty_login");
            return;
        }

        String password = req.getParameter(PARAM_PASSWORD);
        if (password == null || password.trim().isEmpty()) {
            resp.sendRedirect("/chat/login.html?token=empty_password");
            return;
        }

        if( isCorrect(resp, username, password) ) {
            resp.sendRedirect("/chat");
        } else {
            resp.sendRedirect("/chat/login.html?token=wrong");
        }
    }
}
