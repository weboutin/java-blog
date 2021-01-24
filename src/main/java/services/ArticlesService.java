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
        try {
            String sql = "insert into `sbs-articles` (user_id,title,content,created_at,modified_at) values(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, content);
            Date date = new Date();
            ps.setLong(4, date.getTime());
            ps.setLong(5, date.getTime());
            ResultSet rs = null;
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            while (rs.next()) {
                articleId = rs.getInt(1);
                return articleId;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        }
        return articleId;
    }

    public static List<Article> getAll(int page, int size) throws Exception {
        Connection conn = DBUtils.connect();
        List<Article> articles = new ArrayList<>();
        try {
            String sql = "select * from `sbs-articles` limit ?,?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, page);
            ps.setInt(2, size);
            ResultSet rs = null;
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
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        }
        return articles;
    }

    public static Article getDetail(int articleId) throws Exception {
        Connection conn = DBUtils.connect();
        Article article = new Article();
        try {
            String sql = "select * from `sbs-articles` where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, articleId);
            ResultSet rs = null;
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
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        }
        return article;
    }

    public static void edit(Integer userId, Integer articleId, String title, String content) throws Exception {
        Connection conn = DBUtils.connect();
        try {
            String sql = "update `sbs-articles` set title=?, content=?,modified_at=? where id = ? and user_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, content);
            Date date = new Date();
            ps.setLong(3, date.getTime());
            ps.setInt(4, articleId);
            ps.setInt(5, userId);
            int effectRow = ps.executeUpdate();
            ps.close();
            if (effectRow == 0) {
                throw new Exception("update error");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void remove(Integer userId, Integer articleId) throws Exception {
        Connection conn = DBUtils.connect();
        try {
            String sql = "delete from `sbs-articles` where user_id=? and id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, articleId);
            int effectRow = ps.executeUpdate();
            ps.close();
            if (effectRow == 0) {
                throw new Exception("Remove error");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}