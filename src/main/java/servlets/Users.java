package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;
import utils.Utils;
import services.UsersService;

public class Users extends HttpServlet {
    /**
     * POST /v1/users
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String json = IOUtils.toString(request.getInputStream(), "utf8");
            JSONObject input = new JSONObject(json);
            String account = input.optString("account");
            String password = input.optString("password");
            Integer userId = UsersService.register(account, password);
            JSONObject data = new JSONObject();
            data.put("userId", userId);
            Utils.buildResponse(response, 0, "注册成功", data);
        } catch (JSONException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            if (e.getMessage().equals("user already exist")) {
                Utils.buildResponse(response, -1, "用户已存在", data);
            } else {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
        }
    }
}