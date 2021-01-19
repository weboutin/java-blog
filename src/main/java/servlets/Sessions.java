package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;
import utils.Utils;


public class Sessions extends HttpServlet {
    /**
     * POST /v1/sessions
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = IOUtils.toString(request.getInputStream(), "utf8");
            JSONObject input = new JSONObject(json);
            System.out.println(input);
            String account = input.optString("account");
            String password = input.optString("password");
            System.out.println(account);
            System.out.println(password);

            JSONObject sessionObj = new JSONObject();
            sessionObj.put("userId", 1);
            sessionObj.put("account", account);
            Utils.createAndSetSessionCookie(response, sessionObj);
            
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 0, "登录成功", data);
        } catch (JSONException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, -1, "系统异常", data);
        }
    }
}