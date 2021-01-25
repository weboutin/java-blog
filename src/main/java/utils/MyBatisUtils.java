
package utils;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import entitys.User;

public class MyBatisUtils {
    public MyBatisUtils() throws Exception{
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            User user = (User) session.selectOne("mybatis.mappers.UsersMapper.selectUserByAccount", "itgo");
            System.out.println(user.account);
          }
    }
}