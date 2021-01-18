package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;

public class Users extends HttpServlet {
    /**
     * POST /v1/users
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String json = IOUtils.toString(request.getInputStream(), "utf8");
            JSONObject input = new JSONObject(json);
            System.out.println(input);
            String account = input.optString("account");
            String password = input.optString("password");
            System.err.println(account);
            System.err.println(password);

            response.setContentType("application/json;charset=UTF-8");
            JSONObject output = new JSONObject();
            JSONObject data = new JSONObject();
            output.put("code", 0);
            output.put("msg", "注册成功");
            output.put("data", data);
            PrintWriter out = response.getWriter();
            out.println(output);
        } catch (JSONException e) {
            response.setContentType("application/json;charset=UTF-8");
            JSONObject output = new JSONObject();
            JSONObject data = new JSONObject();
            output.put("code", 1);
            output.put("msg", "参数异常");
            output.put("data", data);
            PrintWriter out = response.getWriter();
            out.println(output);
        } catch (Exception e) {
            JSONObject output = new JSONObject();
            JSONObject data = new JSONObject();
            output.put("code", -1);
            output.put("msg", "系统异常");
            output.put("data", data);
            PrintWriter out = response.getWriter();
            out.println(output);
        }
    }
}