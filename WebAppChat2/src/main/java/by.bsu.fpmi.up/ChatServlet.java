package by.bsu.fpmi.up;

import org.json.simple.JSONArray;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@WebServlet(value = "/chat")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/html\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <link rel='StyleSheet' href='" + req.getContextPath() + "/chat/css/styleChat.css' TYPE='text/css' /> " +
                "    <link rel='StyleSheet' href='" + req.getContextPath() + "/chat/css/responsive.css' TYPE='text/css' />     ");
   //     printWriter.println("<script language=\"text/javascript\" src=\"/chat/js/script3.js\">");
   //     RequestDispatcher dispatcher = req.getRequestDispatcher("/chat/js/script3.js");
   //     dispatcher.include(req,resp);    // onload="run();"  //"
        printWriter.println("    <title>Chat beta</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action=\"/chat\" method=\"post\">\n" +
                "    <main class = \"chatContainer\">\n" +
                "        <header class = \"header\">Chat beta</header>\n" +
                "        <div class =\"sectionActions\">\n" +
                "            <button id=\"exitButton\" type=\"submit\" class=\"buttonExit\">Exit</button>\n" +
                "            <img src=\"http://findicons.com/files/icons/2443/bunch_of_cool_bluish_icons/256/edit.png\" width=\"20\" height=\"20\">\n" +
                "            <input id = \"author\" type = \"text\" class = \"inputName\" readonly>\n" +
                "            <img id=\"errorIcon\" class=\"errorIcon\" src=\"https://cdn0.iconfinder.com/data/icons/shift-free/32/Error-128.png\" width=\"25\" height=\"25\" align=\"right\" style=\"padding: 5px\">   \n" +
                "\t    </div>\n" +
                "        <div class = \"sectionHistory\">\n" +
                "            <ul id=\"listMessage\" class=\"listMessage\" >\n" +
                "\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "        <div class= \"sectionAddMessage\">\n" +
                "            <input id = \"message\" type = \"text\" class=\"inputMessage\" placeholder=\"Enter you message\">\n" +
                "            <button id=\"addButton\" type=\"button\" class=\"buttonSend\">Send</button>\n" +
                "        </div>\n" +
                "        <div style=\"display:none\" id=\"messageTemplate\">\n" +
                "                <li class=\"outputName\" data-message-id = \"identifier\">\n" +
                "                    <img src=\"http://findicons.com/files/icons/2443/bunch_of_cool_bluish_icons/256/edit.png\" width=\"20\" height=\"20\" onclick=\"onItemClickToEdit(this)\">\n" +
                "                    <img src=\"http://www.edge-online.org/wp-content/uploads/2014/11/garbage.png\" width=\"20\" height=\"20\" onclick=\"onItemClickToDelete(this)\">\n" +
                "                    <br/>\n" +
                "                </li>\n" +
                "        </div>\n" +
                "        <div id=\"overlay\" class=\"overlay\">\n" +
                "            <div>\n" +
                "                <input class=\"inputNewMessage\" id=\"newText\" type=\"text\" placeholder=\"Enter message\">\n" +
                "                <button class=\"buttonOk\" id=\"ok\" type=\"button\">Ok</button>\n" +
                "                <button class=\"buttonOk\" id=\"exit\">Cancel</button>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </main>\n" +
                "</form>\n" +
                "    <footer><p>Contact information: <a href=\"mailto:matskevich1997@gmail.com\">\n" +
                "            matskevich1997gmail.com</a></p>\n" +
                "        <p>FAMCS 2016</p>\n" +
                "    </footer>\n" +
                "</body>\n" +
                "</html>");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //  resp.setContentType("text/html/JSON");
     //   PrintWriter printWriter = resp.getWriter();
     //   printWriter.println((req.getParameter("message")));
        resp.sendRedirect("/chat/login.html?token=logout");
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
