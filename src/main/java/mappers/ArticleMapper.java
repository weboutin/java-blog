package mappers;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import entitys.Article;
import java.util.List;

public interface ArticleMapper {
    @Insert("insert into `sbs-articles` (user_id,title,content,created_at,modified_at) "
            + "values(#{article.userId},#{article.title},#{article.content},#{article.createdAt},#{article.modifiedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "article.articleId", keyColumn = "id")
    Integer insertArticle(@Param("article") Article article);

    @Select("select *,id articleId, created_at createdAt, modified_at modifiedAt from `sbs-articles` limit #{page},#{size}")
    List<Article> getArticles(@Param("page") Integer page, @Param("size") Integer size);

    @Select("select *,id articleId, created_at createdAt, modified_at modifiedAt from `sbs-articles` where id = #{articleId}")
    Article getArticleById(@Param("articleId") Integer article);

    @Update("update `sbs-articles` set title=#{article.title}, content=#{article.content},modified_at=#{article.modifiedAt} where id = #{article.articleId} and user_id=#{article.userId}")
    @Options(useGeneratedKeys = true, keyProperty = "article.articleId", keyColumn = "id")
    Integer updateArticle(@Param("article") Article article);

    @Delete("delete from `sbs-articles` where user_id=#{article.userId} and id=#{article.articleId}")
    Integer removeArticleById(@Param("article") Article article);
}