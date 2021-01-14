package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONObject;


public class Images extends HttpServlet {
    /**
     * POST /v1/images
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject output = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("imageurl", "xxx.jpg");
        output.put("code", 0);
        output.put("msg", "上传成功");
        output.put("data", data);
        PrintWriter out = response.getWriter();
        out.println(output);
    }
}