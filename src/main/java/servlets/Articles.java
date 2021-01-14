package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONObject;


public class Articles extends HttpServlet {
    /**
     * POST /v1/articles
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject output = new JSONObject();
        JSONObject data = new JSONObject();
        output.put("code", 0);
        output.put("msg", "创建成功");
        output.put("data", data);
        PrintWriter out = response.getWriter();
        out.println(output);
    }
}