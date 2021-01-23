package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONException;
import services.ArticlesService;
import utils.Utils;


public class ArticlesServlet extends HttpServlet {
    /**
     * POST /v1/articles
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * GET /v1/articles?page=?&size=?
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String page = request.getParameter("page");
            String size = request.getParameter("size");
            JSONObject session = Utils.parseSessionCookie(request.getCookies());

            ArticlesService.getAll();

            JSONObject data = new JSONObject();
            List articles = new ArrayList();
            data.put("articles", articles);
            Utils.buildResponse(response, 0, "获取成功", data);
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