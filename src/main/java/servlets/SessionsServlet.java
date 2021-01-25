package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;
import utils.Utils;
import services.SessionsService;


public class SessionsServlet extends HttpServlet {
    /**
     * POST /v1/sessions
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = IOUtils.toString(request.getInputStream(), "utf8");
            JSONObject input = new JSONObject(json);
            String account = input.optString("account");
            String password = input.optString("password");

            Integer userId = SessionsService.auth(account, password);
            JSONObject sessionObj = new JSONObject();
            sessionObj.put("userId", userId);

            Utils.createAndSetSessionCookie(response, sessionObj);
            
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 0, "登录成功", data);
        } catch (JSONException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            if (e.getMessage() == null) {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
            if (e.getMessage().equals("user not exist")) {
                Utils.buildResponse(response, 2, "账号或密码异常", data);
            } else if (e.getMessage().equals("password error")) {
                Utils.buildResponse(response, 2, "账号或密码异常", data);
            } else {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
        }
    }
}