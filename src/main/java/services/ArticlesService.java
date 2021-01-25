package services;

import utils.DBUtils;
import entitys.Article;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticlesService {

    public static Integer create(Integer userId, String title, String content) throws Exception {
        Connection conn = DBUtils.connect();
        Integer articleId = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "insert into `sbs-articles` (user_id,title,content,created_at,modified_at) values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, content);
            Date date = new Date();
            ps.setLong(4, date.getTime());
            ps.setLong(5, date.getTime());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            while (rs.next()) {
                articleId = rs.getInt(1);
                return articleId;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();
        }
        return articleId;
    }

    public static List<Article> getAll(int page, int size) throws Exception {
        Connection conn = DBUtils.connect();
        List<Article> articles = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select * from `sbs-articles` limit ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, page);
            ps.setInt(2, size);
            if (ps.execute()) {
                rs = ps.getResultSet();
                while (rs.next()) {
                    Article article = new Article();
                    Integer articleId = rs.getInt("id");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Long createdAt = rs.getLong("created_at");
                    Long modifiedAt = rs.getLong("modified_at");
                    article.articleId = articleId;
                    article.title = title;
                    article.content = content;
                    article.createdAt = createdAt;
                    article.modifiedAt = modifiedAt;
                    articles.add(article);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();
        }
        return articles;
    }

    public static Article getDetail(int articleId) throws Exception {
        Connection conn = DBUtils.connect();
        Article article = new Article();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select * from `sbs-articles` where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, articleId);
            if (ps.execute()) {
                rs = ps.getResultSet();
                if (rs.next()) {
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Long createdAt = rs.getLong("created_at");
                    Long modifiedAt = rs.getLong("modified_at");
                    article.articleId = articleId;
                    article.title = title;
                    article.content = content;
                    article.createdAt = createdAt;
                    article.modifiedAt = modifiedAt;
                } else {
                    article = null;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();
        }
        return article;
    }

    public static void edit(Integer userId, Integer articleId, String title, String content) throws Exception {
        Connection conn = DBUtils.connect();

        PreparedStatement ps  = null;

        try {
            String sql = "update `sbs-articles` set title=?, content=?,modified_at=? where id = ? and user_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, content);
            Date date = new Date();
            ps.setLong(3, date.getTime());
            ps.setInt(4, articleId);
            ps.setInt(5, userId);
            int effectRow = ps.executeUpdate();
            if (effectRow == 0) {
                throw new Exception("update error");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }
    }

    public static void remove(Integer userId, Integer articleId) throws Exception {
        Connection conn = DBUtils.connect();

        PreparedStatement ps = null;

        try {
            String sql = "delete from `sbs-articles` where user_id=? and id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, articleId);
            int effectRow = ps.executeUpdate();
            if (effectRow == 0) {
                throw new Exception("Remove error");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
        }
    }
}