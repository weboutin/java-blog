
package utils;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtils {

    static SqlSessionFactory sqlSessionFactory = null;

    public static SqlSession getSqlSession() throws Exception {
        if (sqlSessionFactory == null) {
            String resource = "mybatis/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        SqlSession session = sqlSessionFactory.openSession(true);
        return session;
    }

    public static SqlSession getSqlSession(boolean autoCommit) throws Exception {
        if (sqlSessionFactory == null) {
            String resource = "mybatis/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        SqlSession session = sqlSessionFactory.openSession(autoCommit);
        return session;
    }
}