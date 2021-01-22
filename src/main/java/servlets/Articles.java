package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;
import services.ArticlesService;
import utils.Utils;


public class Articles extends HttpServlet {
    /**
     * POST /v1/articles
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.setContentType("application/json;charset=UTF-8");
        // JSONObject output = new JSONObject();
        // JSONObject data = new JSONObject();
        // output.put("code", 0);
        // output.put("msg", "创建成功");
        // output.put("data", data);
        // PrintWriter out = response.getWriter();
        // out.println(output);
        try {
            String json = IOUtils.toString(request.getInputStream(), "utf8");
            JSONObject input = new JSONObject(json);
            String title = input.optString("title");
            String content = input.optString("content");

            JSONObject session = Utils.parseSessionCookie(request.getCookies());
            Integer userId = session.optInt("userId");
            Integer articleId = ArticlesService.create(userId, title, content);
            JSONObject data = new JSONObject();
            data.put("articleId", articleId);
            Utils.buildResponse(response, 0, "创建成功", data);
        } catch (JSONException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            if (e.getMessage().equals("Access Denied")) {
                Utils.buildResponse(response, -1, "无访问权限", data);
            } else {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
        }
    }

}