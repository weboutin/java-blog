package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import services.ArticlesService;
import utils.Utils;
import entitys.Article;

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

            List<Article> articles = ArticlesService.getAll();

            JSONArray jArr = new JSONArray();
            for (int i = 0; i < articles.size(); i++) {
                JSONObject jArticle = new JSONObject();
                jArticle.put("title", articles.get(i).title);
                jArticle.put("content", articles.get(i).content);
                jArticle.put("createdAt", articles.get(i).createdAt);
                jArticle.put("modifiedAt", articles.get(i).modifiedAt);
                jArr.put(jArticle);
            }

            JSONObject data = new JSONObject();
            data.put("articles", jArr);
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