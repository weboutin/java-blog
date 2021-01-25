package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.List;
import java.lang.NumberFormatException;

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
            if (e.getMessage() == null) {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
            if (e.getMessage().equals("Access Denied")) {
                Utils.buildResponse(response, -1, "无访问权限", data);
            } else {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
        }
    }

    /**
     * GET /v1/articles?page=?
     * 
     * GET /v1/articles/:articleId
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            Integer articleId = null;
            if (pathInfo != null) {
                String[] pathParts = pathInfo.split("/");
                articleId = Integer.parseInt(pathParts[1]);
            }
            if (articleId == null) {
                String pageArg = request.getParameter("page");
                int page = 0;
                int size = 10;
                if (pageArg != null) {
                    page = Integer.parseInt(pageArg);
                }
                List<Article> articles = ArticlesService.getAll(page, size);
                JSONArray jArr = new JSONArray();
                for (int i = 0; i < articles.size(); i++) {
                    JSONObject jArticle = new JSONObject();
                    jArticle.put("articleId", articles.get(i).articleId);
                    jArticle.put("title", articles.get(i).title);
                    jArticle.put("content", articles.get(i).content);
                    jArticle.put("createdAt", articles.get(i).createdAt);
                    jArticle.put("modifiedAt", articles.get(i).modifiedAt);
                    jArr.put(jArticle);
                }
                JSONObject data = new JSONObject();
                data.put("articles", jArr);
                Utils.buildResponse(response, 0, "获取成功", data);
            } else {
                Article article = ArticlesService.getDetail(articleId);
                JSONObject data = new JSONObject();
                data.put("articleId", article.articleId);
                data.put("title", article.title);
                data.put("content", article.content);
                data.put("createdAt", article.createdAt);
                data.put("modifiedAt", article.modifiedAt);
                Utils.buildResponse(response, 0, "获取成功", data);
            }

        } catch (NumberFormatException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (JSONException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            if (e.getMessage() == null) {
                Utils.buildResponse(response, -1, "系统异常", data);
            }
            if (e.getMessage().equals("Access Denied")) {
                Utils.buildResponse(response, -1, "无访问权限", data);
            } else {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
        }
    }

    /**
     * PUT /v1/articles/:articleId
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            Integer articleId = null;
            String[] pathParts = pathInfo.split("/");
            articleId = Integer.parseInt(pathParts[1]);
            String json = IOUtils.toString(request.getInputStream(), "utf8");
            JSONObject input = new JSONObject(json);
            String title = input.optString("title");
            String content = input.optString("content");
            JSONObject data = new JSONObject();
            JSONObject session = Utils.parseSessionCookie(request.getCookies());
            Integer userId = session.optInt("userId");
            ArticlesService.edit(userId, articleId, title, content);
            Utils.buildResponse(response, 0, "更新成功", data);

        } catch (NumberFormatException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (JSONException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            if (e.getMessage() == null) {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
            if (e.getMessage().equals("Access Denied")) {
                Utils.buildResponse(response, -1, "无访问权限", data);
            } else if (e.getMessage().equals("Update error")) {
                Utils.buildResponse(response, -1, "更新失败", data);
            } else if (e.getMessage().equals("Cookie parse error")) {
                Utils.buildResponse(response, -1, "无访问权限", data);
            } else {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
        }
    }

    /**
     * DELETE /v1/articles/:articleId
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            Integer articleId = null;
            String[] pathParts = pathInfo.split("/");
            articleId = Integer.parseInt(pathParts[1]);

            JSONObject session = Utils.parseSessionCookie(request.getCookies());
            Integer userId = session.optInt("userId");
            ArticlesService.remove(userId, articleId);
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 0, "删除成功", data);
        } catch (NumberFormatException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (JSONException e) {
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, 1, "参数异常", data);
        } catch (Exception e) {
            JSONObject data = new JSONObject();
            if (e.getMessage() == null) {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
            if (e.getMessage().equals("Access Denied")) {
                Utils.buildResponse(response, -1, "无访问权限", data);
            } else if (e.getMessage().equals("Remove error")) {
                Utils.buildResponse(response, -1, "删除失败", data);
            } else if (e.getMessage().equals("Cookie parse error")) {
                Utils.buildResponse(response, -1, "无访问权限", data);
            } else {
                e.printStackTrace();
                Utils.buildResponse(response, -1, "系统异常", data);
            }
        }
    }
}