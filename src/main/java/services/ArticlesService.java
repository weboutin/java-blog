package services;

import utils.DBUtils;
import entitys.Article;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

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

    public static List<Article> getAll() throws Exception {
        Connection conn = DBUtils.connect();
        List<Article> articles = new ArrayList<>();
        try {
            String sql = "select * from `sbs-articles`";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = null;
            if (ps.execute()) {
                rs = ps.getResultSet();
                while (rs.next()) {
                    Article article = new Article();
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    Long createdAt = rs.getLong("created_at");
                    Long modifiedAt = rs.getLong("modified_at");
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

}