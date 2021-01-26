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
    + "values(#{user_id},#{title},#{content},#{created_at},#{modified_at})")
    @Options(useGeneratedKeys = true,keyProperty = "article.articleId",keyColumn = "id")
    Integer insertArticle(
        @Param("user_id") Integer userId, 
        @Param("title") String title, 
        @Param("content") String content,
        @Param("created_at") Long created_at, 
        @Param("modified_at") Long modified_at
    );

    @Select("select *,id articleId, created_at createdAt, modified_at modifiedAt from `sbs-articles` limit #{page},#{size}")
    List<Article> getArticles(@Param("page") Integer page, @Param("size") Integer size);

    @Select("select *,id articleId, created_at createdAt, modified_at modifiedAt from `sbs-articles` where id = #{articleId}")
    Article getArticleById(@Param("articleId") Integer article);

    @Update("update `sbs-articles` set title=#{title}, content=#{content},modified_at=#{modified_at} where id = #{articleId} and user_id=#{userId}")
    Integer updateArticle(
        @Param("userId") Integer userId, 
        @Param("articleId") Integer articleId, 
        @Param("title") String title, 
        @Param("content") String content,
        @Param("modified_at") Long modified_at
    );

}