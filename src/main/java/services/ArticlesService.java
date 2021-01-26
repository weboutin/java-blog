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
import org.apache.ibatis.session.SqlSession;
import mappers.ArticleMapper;
import utils.MyBatisUtils;

public class ArticlesService {

    public static Integer create(Integer userId, String title, String content) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        Date date = new Date();
        int articleId = mapper.insertArticle(userId, title, content, date.getTime(), date.getTime());
        return articleId;
    }

    public static List<Article> getAll(int page, int size) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        List<Article> articles = mapper.getArticles(page, size);
        return articles;
    }

    public static Article getDetail(int articleId) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        Article article =  mapper.getArticleById(articleId);
        return article;
    }

    public static void edit(Integer userId, Integer articleId, String title, String content) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        Date date = new Date();
        Integer effectRow = mapper.updateArticle(userId, articleId, title, content, date.getTime());
        System.out.println(effectRow);
        if (effectRow == 0) {
            throw new Exception("Remove error");
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