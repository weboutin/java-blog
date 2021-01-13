package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Users extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("helloworld");
    }
}